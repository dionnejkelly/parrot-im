package view.buddylist;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.ScrollPaneConstants;




import controller.services.Xmpp;

import view.chatwindow.chatwindow;

import model.Model;
import model.dataType.ConversationData;
import model.dataType.UpdatedType;
import model.dataType.UserData;

public class buddyPanel extends JPanel implements Observer
{
	/*TODO: BUDDY PANEL HAS 
	 * Center: Buddy List
	 * South: Buddy Options 
	 */
	protected SelectListener lastSelectedListener; //selectedIndex of Buddylist
	protected Object lastSelectedSource;
	
	chatwindow chat;
	JToolBar options;
	JScrollPane scroller;
	JPanel friendList;
	JPopupMenu rightClickMenu;
	JMenuItem menuItem1, menuItem2, menuItem3, menuItem4, menuItem5;
	Box boxes[] = new Box[1];
	String selectedName;
	Xmpp c;
	Model model;
	ArrayList<UserData> buddies;
	UserData selectedFriend;
	
	public buddyPanel(Xmpp c, Model model)
	{
		model.addObserver(this);
	        setLayout(new BorderLayout());
		setBorder(BorderFactory.createEmptyBorder(5,10,5,10));
		
		this.c = c;
		this.model = model;
		this.chat = null;
		buddies = null;
		
		model.chatWindowOpen = false;
		
		friendList = new JPanel();
		friendList.setBackground(Color.WHITE);
		friendList.setLayout(new BorderLayout());
		
		// Place all friends from currentProfile into buddy list
		buddies = model.getCurrentProfile().getAllFriends();
		
		//add friends to the buddy list
		boxes[0] = Box.createVerticalBox();
		for(int i = 0; i < buddies.size(); i++){
			boxes[0].add(FriendItem(buddies.get(i)));
		}
		
		for(int i=0; i < boxes[0].getComponentCount(); i++){
			boxes[0].getComponent(i).addMouseListener(new SelectListener());
		}
		
		//rightclick menu
		rightClickMenu = new JPopupMenu();
		menuItem1 = new JMenuItem("Start New Conversation");
		menuItem2 = new JMenuItem("Add to open Conversation");
		menuItem3 = new JMenuItem("Remove Friend");
		menuItem4 = new JMenuItem("Block Friend");
		menuItem5 = new JMenuItem("View Profile");
		
		menuItem1.addMouseListener(new RightCickMenuListener());
		menuItem2.addMouseListener(new RightCickMenuListener());
		menuItem3.addMouseListener(new RightClickMenuRemoveFriendListener());
		
		rightClickMenu.add(menuItem1);
		rightClickMenu.add(menuItem2);
		rightClickMenu.addSeparator();
		rightClickMenu.add(menuItem3);
		rightClickMenu.add(menuItem4);
		rightClickMenu.addSeparator();
		rightClickMenu.add(menuItem5);
		
		friendList.add(boxes[0], BorderLayout.NORTH);
		JScrollPane scroller = new JScrollPane(friendList);
		scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		options = OptionsBar();
		
        add(scroller, BorderLayout.CENTER);
        add(options, BorderLayout.SOUTH);
	}
	

	
	public JToolBar OptionsBar(){
		JToolBar options = new JToolBar();
		
        JTextField search = new JTextField();
        JButton addF = new JButton(new ImageIcon("images/buddylist/add_user.png"));
        JButton removeF = new JButton(new ImageIcon("images/buddylist/delete_user.png"));
        JButton blockF = new JButton(new ImageIcon("images/buddylist/button_cancel.png"));
        JButton searchButton = new JButton(new ImageIcon("images/buddylist/document_preview.png"));
        
        //add components
        options.add(addF);
        options.add(removeF);
        options.add(blockF);
        options.add(search);
        options.add(searchButton);
        
        
        addF.addMouseListener(new addFriendListener());
        removeF.addMouseListener(new removeFriendListener());
        
        return options;
	}
	
	class RightClickMenuRemoveFriendListener extends MouseAdapter {
	    public void mousePressed(MouseEvent event) {
            System.out.println("Remove this user from the buddy list = " + selectedFriend.toString());
            c.removeFriend(selectedFriend.toString());  	
	    }
	}
	
	class removeFriendListener extends MouseAdapter {
	    public void mousePressed(MouseEvent event) {
	    	 System.out.println("Remove this user from the button = " + selectedFriend.toString());
	            
	         c.removeFriend(selectedFriend.toString());
	    	
	    }
	}
	
	class addFriendListener extends MouseAdapter {
	    public void mousePressed(MouseEvent event) {
	        System.out.println("Add Friend Clicked");
	        String userFriendID, userInput;
	        String result = "Argh, one person will be invited to your Parrot IM Buddy List.";
	
	        
	        // not able to cancel it for now
	        
	        userFriendID = JOptionPane.showInputDialog("Enter an email address: ");

	        
			    
	        
	        if (userFriendID != null && !userFriendID.equals("")) {
	        	 c.addFriend(userFriendID);
	        	 JOptionPane.showMessageDialog(null, result);
	        	
	        }
	        System.out.println("User Input = " + userFriendID);
	       
	     
	    }
	}
	public JPanel FriendItem(UserData user){
		JPanel friendItem = new JPanel();
		friendItem.setLayout(new BorderLayout());
		friendItem.setBackground(Color.WHITE);

		friendItem.setName(user.getNickname());
		
		//end it
		friendItem.setToolTipText("Right click to see options for this item");
		
		JLabel friendName;
		if(c.getUserPresence(user.getAccountName()).contains("offline")){
			friendName = new JLabel(user.getNickname() + " (Offline)");
		}else{
			//JLabel friendStatus = new JLabel(" - \"" + status + "\"");
			friendName = new JLabel(user.getNickname());
		}
		
		friendItem.add(friendName,BorderLayout.WEST);
		//friendItem.add(friendStatus,BorderLayout.CENTER);
		
		return friendItem;
	}
	
	public void update(Observable o, Object arg) {
	    /* If chat window has not been made, make it if message sent */
	    if (arg == UpdatedType.BUDDY || arg == UpdatedType.CHAT_AND_BUDDY) {
	        
	        System.out.println("From update in buddyPanel");
	        for (UserData u : model.getCurrentProfile().getAllFriends()) {
	            //System.out.println(u.getStatus());
	        }
	        System.out.println("end buddyPanel");
	        
	        
	        
	        if (chat == null) {
	            model.startConversation(selectedFriend.getFriendOf(),
                                            selectedFriend);
                    //chat = new chatwindow(c, model);  
	        } else {
	            // add code for if multiple windows exist.
	        }
	        
	    }
	}
	
	private class SelectListener implements MouseListener{
		protected boolean selected;
		
		public SelectListener (){
			selected = false;
		}
		
		public void mouseClicked(MouseEvent event){
			if (lastSelectedListener != null){ //unhighlight the last selected 
				lastSelectedListener.whiteBackground(event);
			}
			
			//FriendItems
			for(int i=0; i < boxes[0].getComponentCount(); i++){
				
				if(event.getSource().equals(boxes[0].getComponent(i))){
					if(event.getButton() == event.BUTTON1){
						selected = true;
						lastSelectedListener = this;
						
						//Left Click
						boxes[0].getComponent(i).setBackground(new Color(145, 200, 200));
						
						/* Fix this to directly reference the GUI */
						selectedFriend = buddies.get(i);
						
						if(event.getClickCount() == 2){
						       selected = false;
						       
						        /* Is the chat window already open? */
							if (model.chatWindowOpen == false) {
							    model.startConversation(selectedFriend.getFriendOf(),
							            selectedFriend);
							    chat = new chatwindow(c, model);
							    model.chatWindowOpen = true;
							}
							else {
							    model.startConversation(selectedFriend.getFriendOf(),
                                                                    selectedFriend);                   
							}
						}
					}else if(event.getSource().equals(boxes[0].getComponent(i))){
						//Right Click
						boxes[0].getComponent(i).setBackground(new Color(145, 200, 200));
						rightClickMenu.show(boxes[0].getComponent(i), event.getX(), event.getY());
						selectedName = boxes[0].getComponent(i).getName();
						selectedFriend = buddies.get(i); 
					}
				}
			}
		}
		
		public void mouseEntered(MouseEvent event) {
			for(int i=0; i < boxes[0].getComponentCount(); i++){
				if(event.getSource().equals(boxes[0].getComponent(i))){
					boxes[0].getComponent(i).setBackground(new Color(225, 247, 247));
				}
			}
		}
		
		public void mouseExited(MouseEvent event) {
			Color c;
			if (selected){
				c = new Color(145, 200, 200);
				lastSelectedSource = event.getSource();
			}else{
				c = Color.WHITE;
			}
			
			for(int i=0; i < boxes[0].getComponentCount(); i++){
				if(event.getSource().equals(boxes[0].getComponent(i))){
					boxes[0].getComponent(i).setBackground(c);
				}
			}
		}
		
		public void whiteBackground(MouseEvent event){
			selected = false;
			if (lastSelectedSource == null) return;
			else{
				for(int i=0; i < boxes[0].getComponentCount(); i++){
					if(lastSelectedSource.equals(boxes[0].getComponent(i))){
						boxes[0].getComponent(i).setBackground(Color.WHITE);
					}
				}
			}
		}
		
		public void mousePressed(MouseEvent e) {}
		public void mouseReleased(MouseEvent e) {}
	}
	
	
	
	class RightCickMenuListener extends MouseAdapter {
	    @Override
		public void mousePressed(MouseEvent event) {
	        ConversationData conversation = null;
	    	if (event.getSource().equals(menuItem1)) {
                    /* Is the chat window already open? */
                    if (model.numberOfConversations() < 1) {
                    conversation = model.startConversation(selectedFriend.getFriendOf(),
                                                           selectedFriend);
                    model.setActiveConversation(conversation);
                    chat = new chatwindow(c, model);   
                    }
                    else {
                        // TODO Add conversation to the window
                    }	
	    	}else if(event.getSource().equals(menuItem2)){
	    		//chat.addToConversation(selectedName);
	    	        // TODO Group chat not yet implemented.
	    	}
	    }
	}
}