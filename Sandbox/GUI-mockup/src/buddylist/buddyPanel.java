package buddylist;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.*;

import javax.swing.*;

import ChatClient.ChatClient;
import model.*;

import chatwindow.chatwindow;

public class buddyPanel extends JPanel implements Observer
{
	/*TODO: BUDDY PANEL HAS 
	 * Center: Buddy List
	 * South: Buddy Options 
	 */
	chatwindow chat;
	JToolBar options;
	JScrollPane scroller;
	JPanel friendList;
	JPopupMenu rightClickMenu;
	JMenuItem menuItem1, menuItem2, menuItem3, menuItem4, menuItem5;
	Box boxes[] = new Box[1];
	String selectedName;
	ChatClient c;
	Model model;
	ArrayList<UserData> buddies;
	UserData selectedFriend;
	
	public buddyPanel(ChatClient c, Model model)
	{
		model.addObserver(this);
	        setLayout(new BorderLayout());
		setBorder(BorderFactory.createEmptyBorder(5,10,5,10));
		
		this.c = c;
		this.model = model;
		this.chat = null;
		buddies = null;
		
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
        JButton addF = new JButton(new ImageIcon(System.getProperty("user.dir") + "/src/buddylist/add_user.png"));
        JButton removeF = new JButton(new ImageIcon(System.getProperty("user.dir") + "/src/buddylist/delete_user.png"));
        JButton blockF = new JButton(new ImageIcon(System.getProperty("user.dir") + "/src/buddylist/button_cancel.png"));
        JButton searchButton = new JButton(new ImageIcon(System.getProperty("user.dir") + "/src/buddylist/document_preview.png"));
        
        //add components
        options.add(addF);
        options.add(removeF);
        options.add(blockF);
        options.add(search);
        options.add(searchButton);
        
        return options;
	}
	
	public JPanel FriendItem(UserData user){
		JPanel friendItem = new JPanel();
		friendItem.setLayout(new BorderLayout());
		friendItem.setBackground(Color.WHITE);
		friendItem.setName(user.getNickname());
		friendItem.setToolTipText("Right click to see options for this item");
		
		JLabel friendName = new JLabel(user.getNickname());
		//JLabel friendStatus = new JLabel(" - \"" + status + "\"");
		
		friendItem.add(friendName,BorderLayout.WEST);
		//friendItem.add(friendStatus,BorderLayout.CENTER);
		
		return friendItem;
	}
	
	public void update(Observable o, Object arg) {
	    /* If chat window has not been made, make it if message sent */
	    if (arg == UpdatedType.BUDDY || arg == UpdatedType.CHAT_AND_BUDDY) {
	        if (chat == null) {
	            //model.startConversation(selectedFriend.getFriendOf(),
                    //                       selectedFriend);
                    chat = new chatwindow(c, model);  
	        } else {
	            // add code for if multiple windows exist.
	        }
	        
	    }
	}
	
	private class SelectListener implements MouseListener{
		public void mouseClicked(MouseEvent event){	
			//FriendItems
			for(int i=0; i < boxes[0].getComponentCount(); i++){
				if(event.getSource().equals(boxes[0].getComponent(i))){
					if(event.getButton() == event.BUTTON1){
						
						//Left Click
						boxes[0].getComponent(i).setBackground(new Color(145, 200, 200));
						/* Fix this to directly reference the GUI */
						selectedFriend = buddies.get(i);
						if(event.getClickCount() == 2){
						        
						        /* Is the chat window already open? */
							if (model.numberOfConversations() < 1) {
							    model.startConversation(selectedFriend.getFriendOf(),
							            selectedFriend);
							    chat = new chatwindow(c, model);
							    
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
			for(int i=0; i < boxes[0].getComponentCount(); i++){
				if(event.getSource().equals(boxes[0].getComponent(i))){
					boxes[0].getComponent(i).setBackground(Color.WHITE);
				}
			}
		}
		
		public void mousePressed(MouseEvent e) {}
		public void mouseReleased(MouseEvent e) {}
	}
	
	class RightCickMenuListener extends MouseAdapter {
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