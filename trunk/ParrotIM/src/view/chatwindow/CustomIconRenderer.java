package view.chatwindow;

import java.awt.Component;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

public class CustomIconRenderer extends DefaultTreeCellRenderer {
	private ImageIcon defaultIcon = new ImageIcon(this.getClass().getResource(
					"/images/chatwindow/personal.png"));
	private ArrayList<ImageIcon> avatars = new ArrayList<ImageIcon>();
	private ArrayList<String> customUserAvatar = new ArrayList<String>();
	
	public CustomIconRenderer() {}
	
	public void setUserAvatar(String username, ImageIcon avatar){
		avatars.add(avatar);
		customUserAvatar.add(username);
	}
	
	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, 
			boolean expanded, boolean leaf, int row,boolean hasFocus) {         
		super.getTreeCellRendererComponent(tree, value, sel,           
				expanded, leaf, row, hasFocus);         
		Object nodeObj = ((DefaultMutableTreeNode)value).getUserObject();    
		
		// check whatever you need to on the node user object
		boolean matched = false;
		for(int i = 0; i < avatars.size(); i++){
			if(nodeObj.toString() == customUserAvatar.get(i)){
				Image img = avatars.get(i).getImage(); 
				img = img.getScaledInstance(32, 32,  java.awt.Image.SCALE_SMOOTH);   
				ImageIcon newIcon = new ImageIcon(img);  

				setIcon(newIcon); 
				matched = true;
			}
		}
		if(matched == false){
			setIcon(defaultIcon);
		}
		
		return this;    
	}
}

