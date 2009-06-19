package view.chatwindow;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreePath;

import controller.services.Xmpp;

import model.*;
import model.dataType.ConversationData;
import model.dataType.UpdatedType;
import java.util.*;

public class sidePanel extends JPanel implements Observer {
	private JPanel chattingWith;
	private Box boxes[] = new Box[1];
	private Model model;
	private JTree tree;
	private Xmpp c;
	private DefaultMutableTreeNode top;
	
	public sidePanel(Xmpp c, Model model) { 
	    this.model = model;
	    this.c = c;
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
        	
        	refreshTree();
	    }
	    this.add(tree, BorderLayout.CENTER);
	}
	
	private void refreshTree(){
		//sets Tree Icons
    	ImageIcon leafIcon = new ImageIcon("images/chatwindow/personal.png");
    	if (leafIcon != null) {
    	    DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();
    	    renderer.setLeafIcon(leafIcon);
    	    tree.setCellRenderer(renderer);
    	}
    	    
    	top.removeAllChildren();
        for (ConversationData cd1 : model.getConversations()) {
                DefaultMutableTreeNode con = new DefaultMutableTreeNode("Conversation");
                top.add(con);
                tree.addMouseListener(new SelectListener());
                
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
                // Temporary fix for early update bug
                if (tree != null) {
                    refreshTree();
                }
	    }
	    return;	
	}
	
	private class SelectListener implements MouseListener{
		public void mousePressed(MouseEvent event) {
			//int selRow = tree.getRowForLocation(event.getX(), event.getY());
			TreePath selPath = tree.getPathForLocation(event.getX(), event.getY());
			
			//System.out.println("in sidePanel" + selPath.getLastPathComponent().toString());
			c.changeConversation(selPath.getLastPathComponent().toString());
			
			
		}
		public void mouseEntered(MouseEvent event) {}
		public void mouseExited(MouseEvent event) {}
		public void mouseClicked(MouseEvent event){	}
		public void mouseReleased(MouseEvent event) {}
	}
}