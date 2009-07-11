package view.styles;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GroupedListPane extends JPanel{
	Box boxes[] = new Box[1];
	private ArrayList<CustomListPane> groups = new ArrayList<CustomListPane>();
	
	public GroupedListPane() {
		setBackground(Color.WHITE);
		setLayout(new BorderLayout());
		//setPreferredSize(new Dimension(200, 200));
		
		boxes[0] = Box.createVerticalBox();
		
		add(boxes[0], BorderLayout.NORTH);
	}
	
	public void addGroup(String name, ImageIcon img){
		JPanel newPanel = new JPanel();
		newPanel.setLayout(new BorderLayout());
		newPanel.add(new JLabel(img), BorderLayout.WEST);
		newPanel.add(new JLabel(name), BorderLayout.CENTER);
		newPanel.setBackground(Color.green.brighter());
		
		CustomListPane collapsableListPane = new CustomListPane();
		groups.add(collapsableListPane);
		
		newPanel.addMouseListener(new CollapseListner());
		boxes[0].add(newPanel);
		boxes[0].add(collapsableListPane);
	}
	
	public void addGroupTop(String name){
		JPanel newPanel = new JPanel();
		newPanel.add(new JLabel(name));
		newPanel.setBackground(Color.green.brighter());
		
		CustomListPane collapsableListPane = new CustomListPane();
		groups.add(0, collapsableListPane);
		
		newPanel.addMouseListener(new CollapseListner());
		boxes[0].add(newPanel, 0);
		boxes[0].add(collapsableListPane, 1);
	}
	
	public void addElement(int group, String name, ImageIcon img){
		groups.get(group).addElement(name, img);
	}
	
	public void addElement(int group, JPanel externalFriendPanel){
		groups.get(group).addElement(externalFriendPanel);
	}
	
	public void addExternalMouseListener(int group, int componentIndex,
											MouseListener externalListener){
		groups.get(group).boxes[0].getComponent(componentIndex).addMouseListener(externalListener);
	}
	
	public Component getComponent(int group, int componentIndex){
		return groups.get(group).boxes[0].getComponent(componentIndex);
	}
	
	public void removeAllElements(int group){
		groups.get(group).removeAllElements();
	}
	
	private void setGroupVisible(int group, boolean visible){
		groups.get(group).setVisible(visible);
	}
	
	private boolean isGroupVisible(int group){
		return groups.get(group).isVisible();
	}
	
	/**
	 * @author Jordan
	 *
	 * 
	 */
    private class CollapseListner implements MouseListener {
    	
        /**
         * toggles the minizations of the sublists
         */
        public void mouseClicked(MouseEvent event) {
        	for (int i = 0; i < boxes[0].getComponentCount(); i++) {
                if (event.getSource().equals(boxes[0].getComponent(i))) {
                    setGroupVisible(i/2, !isGroupVisible(i/2));
                }
            }
        }

        /**
         * Unused methods 
         */
        public void mouseEntered(MouseEvent event) {}
        public void mouseExited(MouseEvent event) {}
        public void mousePressed(MouseEvent e) {}
        public void mouseReleased(MouseEvent e) {}
    }
}
