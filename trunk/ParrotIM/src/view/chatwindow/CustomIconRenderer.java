package view.chatwindow;

import java.awt.Component;
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
	
	public CustomIconRenderer() {	}
	
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
		System.out.println("From rendererComponent:" +nodeObj.toString());
		boolean matched = false;
		for(int i = 0; i < avatars.size(); i++){
			if(nodeObj.toString() == customUserAvatar.get(i)){
				setIcon(avatars.get(i)); 
				matched = true;
			}
		}
		if(matched == false){
			setIcon(defaultIcon); 
		}
		
		return this;    
	}
}

