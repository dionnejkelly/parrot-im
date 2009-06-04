package buddylist;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.*;

public class buddyPanel extends JPanel
{
	/*TODO: BUDDY PANEL HAS 
	 * Center: Buddy List
	 * South: Buddy Options 
	 */
	JToolBar options;
	JScrollPane scroller;
	JPanel friendList;
	JPopupMenu rightClickMenu;
	Box boxes[] = new Box[1];
	
	public buddyPanel()
	{
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createEmptyBorder(5,10,5,10));
		
		friendList = new JPanel();
		friendList.setBackground(Color.WHITE);
		friendList.setLayout(new BorderLayout());
		
		//add friends to the buddy list
		boxes[0] = Box.createVerticalBox();
		boxes[0].add(FriendItem("Jordan","PlayingXbox"));
		boxes[0].add(FriendItem("PersonA","Eating Lunch"));
		boxes[0].add(FriendItem("PersonB","Away From keyboard"));
		boxes[0].add(FriendItem("PersonB","Away From keyboard"));
		boxes[0].add(FriendItem("PersonB","Away From keyboard"));
		boxes[0].add(FriendItem("PersonC","Is going to see a movie tonight! who wants to come."));
		boxes[0].add(FriendItem("PersonD",""));
		
		for(int i=0; i < boxes[0].getComponentCount(); i++){
			boxes[0].getComponent(i).addMouseListener(new SelectListener());
		}
		
		//rightclick menu
		rightClickMenu = new JPopupMenu();
		JMenuItem menuItem1 = new JMenuItem("Start New Conversation");
		JMenuItem menuItem2 = new JMenuItem("Add to open Conversation");
		JMenuItem menuItem3 = new JMenuItem("Remove Friend");
		JMenuItem menuItem4 = new JMenuItem("Block Friend");
		JMenuItem menuItem5 = new JMenuItem("View Profile");
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
	
	public JPanel FriendItem(String name, String status){
		JPanel friendItem = new JPanel();
		friendItem.setLayout(new BorderLayout());
		friendItem.setBackground(Color.WHITE);
		
		JLabel friendName = new JLabel(name);
		JLabel friendStatus = new JLabel(" - \"" + status + "\"");
		
		friendItem.add(friendName,BorderLayout.WEST);
		friendItem.add(friendStatus,BorderLayout.CENTER);
		
		return friendItem;
	}
	
	private class SelectListener implements MouseListener{
		public void mouseClicked(MouseEvent event){
			//Left click
			if(event.getButton() == event.BUTTON1){
				for(int i=0; i < boxes[0].getComponentCount(); i++){
					if(event.getSource().equals(boxes[0].getComponent(i))){
						boxes[0].getComponent(i).setBackground(Color.BLUE);
					}
				}
			}
			//Right click
			if(event.getButton() == event.BUTTON3){
				for(int i=0; i < boxes[0].getComponentCount(); i++){
					if(event.getSource().equals(boxes[0].getComponent(i))){
						rightClickMenu.show(boxes[0].getComponent(i), event.getX(), event.getY());
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
}