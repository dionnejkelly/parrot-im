package chatwindow;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import model.*;
import ChatClient.ChatClient;
import java.util.*;

public class sidePanel extends JPanel implements Observer {
	private JPanel chattingWith;
	private Box boxes[] = new Box[1];
	private Model model;
	private JTree tree;
	private DefaultMutableTreeNode top;
	
	public sidePanel(ChatClient c, Model model) { 
	    this.model = model;
	    this.model.addObserver(this);
	    DefaultMutableTreeNode person1 = null;
	    DefaultMutableTreeNode person2 = null;
		
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(150, 500));
		setBackground(Color.WHITE);
		setBorder(BorderFactory.createEmptyBorder(5,10,5,10));

		//Tree preferences
	    for (ConversationData cd : model.getConversations()) {
	        top = new DefaultMutableTreeNode("Root");
	        tree = new JTree(top);
        	
	        //top.getChildAt(0).getChildAt(0).toString(); --> check this VERA
        	refreshTree();
	    }
	    this.add(tree, BorderLayout.CENTER);
	}
	
	private void refreshTree(){
		//sets Tree Icons
    	ImageIcon leafIcon = new ImageIcon(System.getProperty("user.dir") + "/src/chatwindow/personal.png");
    	if (leafIcon != null) {
    	    DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();
    	    renderer.setLeafIcon(leafIcon);
    	    tree.setCellRenderer(renderer);
    	}
    	    
    	top.removeAllChildren();
        for (ConversationData cd1 : model.getConversations()) {
                DefaultMutableTreeNode con = new DefaultMutableTreeNode("Conversation");
                top.add(con);
            	con.add(new DefaultMutableTreeNode(cd1.getAccount().getAccountName()));
            	con.add(new DefaultMutableTreeNode(cd1.getUser().getNickname()));
            	tree.expandRow(1);
            	tree.setRootVisible(false);
            	
                tree.expandRow(0);
                
                //refreshes the tree
                tree.updateUI();
        }
	}
	
	public void update(Observable t, Object o) {         
        if (o == UpdatedType.CHAT || o == UpdatedType.ALL) {
            
            refreshTree();
	    }
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