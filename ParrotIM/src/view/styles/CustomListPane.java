package view.styles;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import view.chatwindow.UserDataWrapper;

public class CustomListPane extends JPanel{
	private ArrayList<String> nicknames = new ArrayList<String>();
	private ArrayList<UserDataWrapper> userWrappers = new ArrayList<UserDataWrapper>();
	Box boxes[] = new Box[1];
	private ImageIcon defaultIcon = new ImageIcon(this.getClass().getResource(
				"/images/chatwindow/personal.png"));
	private int lastSelected = 0; 
	
	public CustomListPane() {
		setBackground(Color.WHITE);
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 10));
		
		boxes[0] = Box.createVerticalBox();
		
		add(boxes[0], BorderLayout.NORTH);
	}
	
	private JPanel friendPanel(String nickname, ImageIcon icon){
		JPanel friendPanel = new JPanel();
		friendPanel.setLayout(new BorderLayout());
		friendPanel.setBackground(Color.WHITE);
		
		if(icon == null){
			icon = defaultIcon;
		}
		Image img = icon.getImage();
		img = img.getScaledInstance(32, 32,  java.awt.Image.SCALE_SMOOTH);
		ImageIcon newIcon = new ImageIcon(img);
		
		friendPanel.add(new JLabel(newIcon), BorderLayout.WEST);
		friendPanel.add(new JLabel(" " + nickname), BorderLayout.CENTER);
		return friendPanel;
	}
	
	public boolean nicknameListContains(String name){
		return nicknames.contains(name);
	}
	
	public ArrayList<String> getNicknameList(){
		return nicknames;
	}
	
	public UserDataWrapper getUserWrapper(int i){
		return userWrappers.get(i);
	}
	
	public void addElement(JPanel externalFriendPanel){
		boxes[0].add(externalFriendPanel);
		boxes[0].getComponent(boxes[0].getComponentCount() - 1)
				.addMouseListener(new SelectListener());
		updateUI();
		//this.repaint();
	}
	
	public void addElement(String nickname, ImageIcon img, UserDataWrapper userWrapper, 
			MouseListener externalListener){
		nicknames.add(nickname);
		userWrappers.add(userWrapper);
		
		boxes[0].add(friendPanel(nickname, img));
		boxes[0].getComponent(boxes[0].getComponentCount() - 1)
				.addMouseListener(externalListener);
		boxes[0].getComponent(boxes[0].getComponentCount() - 1)
				.addMouseListener(new SelectListener());
		updateUI();
		this.repaint();
	}
	
	public void addElement(String nickname, ImageIcon img){
		nicknames.add(nickname);
		boxes[0].add(friendPanel(nickname, img));
		boxes[0].getComponent(boxes[0].getComponentCount() - 1)
				.addMouseListener(new SelectListener());
		updateUI();
	}
	
	public void addUserWrapperData(UserDataWrapper userWrapper){
		userWrappers.add(userWrapper);
	}
	
	public Component getElement(int i){
		return boxes[0].getComponent(i);
	}
	
	public void removeAllElements(){
		nicknames = new ArrayList();
		boxes[0].removeAll();
		updateUI();
	}
	
	public int getClickedIndex(){
		return lastSelected;
	}
	
	public JScrollPane getWithScroller(){
		JScrollPane scroller = new JScrollPane(this);

		scroller.setHorizontalScrollBarPolicy(
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		return scroller;
	}
	
    private class SelectListener implements MouseListener {
    	
        /**
         * highlights clicked element
         */
        public void mouseClicked(MouseEvent event) {
        	boxes[0].getComponent(lastSelected).setBackground(
        			new Color(145, 200, 200));
        }

        /**
         * change background to color when mouse Entered
         */
        public void mouseEntered(MouseEvent event) {
        	for (int i = 0; i < boxes[0].getComponentCount(); i++) {
                if (event.getSource().equals(boxes[0].getComponent(i))) {
                    boxes[0].getComponent(i).setBackground(
                            new Color(225, 247, 247));
                    lastSelected = i;
                }
            }
        }

        /**
         * change background to white when mouse Exited
         */
        public void mouseExited(MouseEvent event) {
        	boxes[0].getComponent(lastSelected).setBackground(
                    Color.WHITE);
        }

        /**
         * Unused methods 
         */
        public void mousePressed(MouseEvent e) {}
        public void mouseReleased(MouseEvent e) {}
    }
}
