package chatwindow;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import model.Model;
import ChatClient.ChatClient;
import java.util.*;

public class sidePanel extends JPanel implements Observer {
	private JPanel chattingWith;
	private Box boxes[] = new Box[1];
	private ArrayList<Conversation> conversations;
	private Model model;
	private JTree tree;
	
	public sidePanel(ArrayList<Conversation> conversations, ChatClient c, Model model) {
	    this.model = model;
		
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(150, 500));
		setBackground(Color.WHITE);
		setBorder(BorderFactory.createEmptyBorder(5,10,5,10));
		
		this.conversations = conversations;
		
		//Tree preferences
	    DefaultMutableTreeNode top =
	        new DefaultMutableTreeNode("Conversation1");
	    tree = new JTree(top);
	    
	    //sets Tree Icons
	    ImageIcon leafIcon = new ImageIcon(System.getProperty("user.dir") + "/src/chatwindow/personal.png");
	    if (leafIcon != null) {
	        DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();
	        renderer.setLeafIcon(leafIcon);
	        tree.setCellRenderer(renderer);
	    }
	    
	    //original Tutorial
	    DefaultMutableTreeNode person1 = new DefaultMutableTreeNode(c.getUserName().replace("@gmail.com", ""));
	    DefaultMutableTreeNode person2 = new DefaultMutableTreeNode(conversations.get(0).getName(1).replace("@gmail.com", ""));
	    top.add(person1);
	    top.add(person2);
	    tree.expandRow(0);
			
		//add to panel
		add(tree, BorderLayout.CENTER);
	    //add(chattingWith, BorderLayout.CENTER);
	}
	
	public void update(Observable t, Object o) {
	    // Add code here to change the namePanels
		return;	
	}
	
	private class SelectListener implements MouseListener{
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
		public void mouseClicked(MouseEvent event){	}
		public void mousePressed(MouseEvent e) {}
		public void mouseReleased(MouseEvent e) {}
	}
}