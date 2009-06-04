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
	JPanel buddyList;
	Box boxes[] = new Box[1];
	
	public buddyPanel()
	{
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createEmptyBorder(5,10,5,10));
		
		buddyList = new JPanel();
		buddyList.setBackground(Color.WHITE);
		buddyList.setLayout(new BorderLayout());
		
		boxes[0] = Box.createVerticalBox();
		boxes[0].add(FriendItem("Jordan","PlayingXbox"));
		boxes[0].add(FriendItem("PersonA","Eating Lunch"));
		boxes[0].add(FriendItem("PersonB","Away From keyboard"));
		boxes[0].add(FriendItem("PersonB","Away From keyboard"));
		boxes[0].add(FriendItem("PersonB","Away From keyboard"));
		boxes[0].add(FriendItem("PersonC","Is going to see a movie tonight! who wants to come."));
		boxes[0].add(FriendItem("PersonD",""));
		
		
		boxes[0].getComponent(0).addMouseListener(new SelectListener());
		boxes[0].getComponent(1).addMouseListener(new SelectListener());
		boxes[0].getComponent(2).addMouseListener(new SelectListener());
		boxes[0].getComponent(3).addMouseListener(new SelectListener());
		boxes[0].getComponent(4).addMouseListener(new SelectListener());
		boxes[0].getComponent(5).addMouseListener(new SelectListener());
		boxes[0].getComponent(6).addMouseListener(new SelectListener());
		
		
		buddyList.add(boxes[0], BorderLayout.NORTH);
		JScrollPane scroller = new JScrollPane(buddyList);
		//scroller.createVerticalScrollBar();
		scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		options = OptionsBar();
        
		//scroller.add(buddyList);
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
		friendItem.setBackground(new Color(225, 247, 247));
		
		JLabel friendName = new JLabel(name);
		JLabel friendStatus = new JLabel(" - \"" + status + "\"");
		
		friendItem.add(friendName,BorderLayout.WEST);
		friendItem.add(friendStatus,BorderLayout.CENTER);
		
		return friendItem;
	}
	
	private class SelectListener implements MouseListener{
		
		public void mouseClicked(MouseEvent arg0){}

		public void mouseEntered(MouseEvent event) {
			for(int i=0; i < boxes[0].getComponentCount(); i++){
				if(event.getSource().equals(boxes[0].getComponent(i))){
					boxes[0].getComponent(i).setBackground(Color.GREEN);
				}
			}
		}

		public void mouseExited(MouseEvent e) {
		}

		public void mousePressed(MouseEvent e) {}

		public void mouseReleased(MouseEvent e) {}
	}
}