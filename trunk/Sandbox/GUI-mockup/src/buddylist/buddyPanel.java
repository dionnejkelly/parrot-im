package buddylist;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.*;

import ChatClient.ChatClient;
import model.*;

import chatwindow.chatwindow;

public class buddyPanel extends JPanel
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
	
	public buddyPanel(ChatClient c, Model model)
	{
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createEmptyBorder(5,10,5,10));
		
		this.c = c;
		this.model = model;
		
		friendList = new JPanel();
		friendList.setBackground(Color.WHITE);
		friendList.setLayout(new BorderLayout());
		
		ArrayList<String> buddies = c.getBuddyList();
		
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
        JButton addF = new JButton("Add");
        JButton removeF = new JButton("Del");
        JButton blockF = new JButton("Blk");
        JButton searchButton = new JButton("Srch");
        
        //add components
        options.add(addF);
        options.add(removeF);
        options.add(blockF);
        options.add(search);
        options.add(searchButton);
        
        return options;
	}
	
	public JPanel FriendItem(String name){
		JPanel friendItem = new JPanel();
		friendItem.setLayout(new BorderLayout());
		friendItem.setBackground(Color.WHITE);
		friendItem.setName(name);
		friendItem.setToolTipText("Right click to see options for this item");
		
		JLabel friendName = new JLabel(name);
		//JLabel friendStatus = new JLabel(" - \"" + status + "\"");
		
		friendItem.add(friendName,BorderLayout.WEST);
		//friendItem.add(friendStatus,BorderLayout.CENTER);
		
		return friendItem;
	}
	
	private class SelectListener implements MouseListener{
		public void mouseClicked(MouseEvent event){	
			int id = 0;
			ChatData chatData = null;
			//FriendItems
			for(int i=0; i < boxes[0].getComponentCount(); i++){
				if(event.getSource().equals(boxes[0].getComponent(i))){
					if(event.getButton() == event.BUTTON1){
						
						//Left Click
						boxes[0].getComponent(i).setBackground(new Color(145, 200, 200));
						if(event.getClickCount() == 2){
							model.incrementOpenChatWindows();
							id = model.getOpenChatWindows();
							selectedName = boxes[0].getComponent(i).getName();
							chatData = new ChatData(id, c.getUserName(),
									                selectedName);
							model.storeChatParticipants(chatData);
							chat = new chatwindow(id, c, model);
						}
					}else if(event.getSource().equals(boxes[0].getComponent(i))){

						//Right Click
						boxes[0].getComponent(i).setBackground(new Color(145, 200, 200));
						rightClickMenu.show(boxes[0].getComponent(i), event.getX(), event.getY());
						selectedName = boxes[0].getComponent(i).getName();
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
	    	int id = 0;
	    	ChatData chatData = null;
	    	if(event.getSource().equals(menuItem1)){
	    		model.incrementOpenChatWindows();
				id = model.getOpenChatWindows();
				chatData = new ChatData(id, c.getUserName(),
						                selectedName);
				model.storeChatParticipants(chatData);
				chat = new chatwindow(id, c, model);
	    	}else if(event.getSource().equals(menuItem2)){
	    		chat.addToConversation(selectedName);
	    	}
	    }
	}
}