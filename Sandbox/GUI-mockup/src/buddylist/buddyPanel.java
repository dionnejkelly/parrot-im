package buddylist;

import java.awt.*;
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
	
	public buddyPanel()
	{
		setLayout(new BorderLayout());
		
		buddyList = new JPanel();
		buddyList.setBackground(Color.WHITE);
		buddyList.setLayout(new BorderLayout());
		Box boxes[] = new Box[1];
		boxes[0] = Box.createVerticalBox();
		boxes[0].add(FriendItem("Jordan","PlayingXbox"));
		boxes[0].add(FriendItem("PersonA","Eating Lunch"));
		boxes[0].add(FriendItem("PersonB","Away From keyboard"));
		boxes[0].add(FriendItem("PersonC","Is going to see a movie tonight! who wants to come."));
		boxes[0].add(FriendItem("PersonD",""));
		buddyList.add(boxes[0], BorderLayout.NORTH);
		JScrollPane scroller = new JScrollPane(buddyList);
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
		friendItem.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		JLabel friendName = new JLabel(name);
		JLabel friendStatus = new JLabel(" - \"" + status + "\"");
		
		friendItem.add(friendName,BorderLayout.WEST);
		friendItem.add(friendStatus,BorderLayout.CENTER);
		
		return friendItem;
	}
}
