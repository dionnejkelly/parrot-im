package view.styles;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
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


public class CustomListPane extends GPanel{

    private int groupIndex;
	private Component lastClickedComponent;
	private int lastClickedIndex;
    private ArrayList<String> nicknames = new ArrayList<String>();
    private ArrayList<UserDataWrapper> userWrappers = new ArrayList<UserDataWrapper>();
    private ArrayList<JPanel> userPanels;
    private ArrayList<JLabel> userLabels = new ArrayList<JLabel>();
    Box boxes[] = new Box[1];
    private ImageIcon defaultIcon = new ImageIcon(this.getClass().getResource(
            "/images/chatwindow/personal.png"));
    private int lastSelected = 0;
    public boolean modifiableColors = false;
    private Color textColor = Color.BLACK;
    
    public CustomListPane(int groupIndex) {
    	this.groupIndex = groupIndex;
    	lastClickedComponent = null;
    	lastClickedIndex = -1;
        setGradientColors(Color.WHITE, Color.WHITE);
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 10));
        //setOpaque(false);
        
        userPanels = new ArrayList<JPanel>();
        boxes[0] = Box.createVerticalBox();

        add(boxes[0], BorderLayout.NORTH);
    }

    public void setGroupIndex(int index){
    	groupIndex = index;
    }
    private JPanel friendPanel(String nickname, ImageIcon icon) {
        JPanel friendPanel = new JPanel();
        friendPanel.setLayout(new BorderLayout());
        //friendPanel.setBackground(Color.WHITE);
        friendPanel.setOpaque(false);
        
        if (icon == null) {
            icon = defaultIcon;
        }
        Image img = icon.getImage();
        img = img.getScaledInstance(32, 32, java.awt.Image.SCALE_SMOOTH);
        ImageIcon newIcon = new ImageIcon(img);

        JLabel IconLabel = new JLabel(newIcon);
        IconLabel.setOpaque(false);
        friendPanel.add(IconLabel, BorderLayout.WEST);
        JLabel nicknameLabel = new JLabel(" " + nickname);
        userLabels.add(nicknameLabel);
        friendPanel.add(new JLabel(" " + nickname), BorderLayout.CENTER);
        return friendPanel;
    }

    private JPanel friendPanel(UserDataWrapper user, ImageIcon icon) {
        JPanel friendPanel = new JPanel();
        friendPanel.setLayout(new BorderLayout());
        //friendPanel.setOpaque(false);

        if (icon == null) {
            icon = defaultIcon;
        }
        Image img = icon.getImage();
        img = img.getScaledInstance(32, 32, java.awt.Image.SCALE_SMOOTH);
        ImageIcon newIcon = new ImageIcon(img);

        JLabel imageLabel = new JLabel(newIcon);
        
        JLabel usernameLabel = user.getLabelRepresentation();
        usernameLabel.setForeground(textColor);
        userLabels.add(usernameLabel);
        friendPanel.add(imageLabel, BorderLayout.WEST);
        friendPanel.add(usernameLabel, BorderLayout.CENTER);
        friendPanel.setToolTipText(user.getToolTipText());
        
        return friendPanel;
    }

    public boolean nicknameListContains(String name) {
        return nicknames.contains(name);
    }

    public ArrayList<String> getNicknameList() {
        return nicknames;
    }

    public void setNickname(String str, int i) {
        nicknames.add(i, str);
    }

    public String getNickname(int i) {
        return nicknames.get(i);
    }

    public int getIndex(String nickname) {
        for (int i = 0; i < nicknames.size(); i++) {
            if (nicknames.get(i).equals(nickname)) {
                return i;
            }
        }
        return -1;
    }

    public UserDataWrapper getUserWrapper(int i) {
        return userWrappers.get(i);
    }

    public void addElement(JPanel externalFriendPanel) {
    	externalFriendPanel.setOpaque(false);
        userPanels.add(externalFriendPanel);
        boxes[0].add(externalFriendPanel);
        boxes[0].getComponent(boxes[0].getComponentCount() - 1)
                .addMouseListener(new SelectListener());
        updateUI();
        // this.repaint();
    }

    public void addElement(JPanel externalFriendPanel, int index) {
    	externalFriendPanel.setOpaque(false);
        userPanels.add(externalFriendPanel);
        boxes[0].add(externalFriendPanel, index);
        boxes[0].getComponent(index).addMouseListener(new SelectListener());
        updateUI();
        // this.repaint();
    }

    public void addElement(String nickname, ImageIcon img,
            UserDataWrapper userWrapper, MouseListener externalListener) {
    	System.out.println("SidePanel:" + nickname);
        nicknames.add(nickname);
        userWrappers.add(userWrapper);
        JPanel panel = friendPanel(userWrapper, img);
        panel.setOpaque(false);
        userPanels.add(panel);

        boxes[0].add(panel);
        boxes[0].getComponent(boxes[0].getComponentCount() - 1)
                .addMouseListener(externalListener);
        boxes[0].getComponent(boxes[0].getComponentCount() - 1)
                .addMouseListener(new SelectListener());
        updateUI();
        this.repaint();
    }

    public void removeSidePanelUser(UserDataWrapper userWrapper) {
        int index = -1;

        index = userWrappers.indexOf(userWrapper);

        if (index >= 0) {
            userWrappers.remove(index);
            boxes[0].remove(userPanels.get(index));
            userPanels.remove(index);
        }

        return;
    }

    public boolean sidePanelUserExists(UserDataWrapper userWrapper) {
        return userWrappers.contains(userWrapper);
    }

    public void addElement(String nickname, ImageIcon img) {
        nicknames.add(nickname);
        JPanel panel = friendPanel(nickname, img);
        panel.setOpaque(false);
        boxes[0].add(panel);
        boxes[0].getComponent(boxes[0].getComponentCount() - 1)
                .addMouseListener(new SelectListener());
        updateUI();
    }

    public void addUserWrapperData(UserDataWrapper userWrapper) {
        userWrappers.add(userWrapper);
    }

    public Component getElement(int i) {
        return boxes[0].getComponent(i);
    }

    public JPanel getPanel(int i) {
        return userPanels.get(i);
    }

    public void removeElement(JPanel panel) {
        userPanels.remove(panel);
        boxes[0].remove(panel);
        updateUI();
        
        if (userPanels.size() == 0 || userPanels.size() <= lastClickedIndex){
        	lastClickedComponent = null;
    		lastClickedIndex = -1;
        }
        return;
    }

    public void removeAllElements() {
        userPanels.clear();
        boxes[0].removeAll();
        updateUI();
        this.resetClickedSelection();
    }

    public int getClickedIndex() {
        return lastSelected;
    }

    public JScrollPane getWithScroller() {
        JScrollPane scroller = new JScrollPane(this);

        scroller
                .setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        return scroller;
    }

    public Box getBoxes() {
        return boxes[0];
    }
    
	public void resetClickedSelection(){
		if (lastClickedComponent != null && lastClickedIndex != -1){
			lastClickedComponent.setBackground(Color.WHITE);
			userPanels.get(lastClickedIndex).setOpaque(false);
		}
		lastClickedComponent = null;
		lastClickedIndex = -1;
	}
	
    private class SelectListener implements MouseListener {
    	boolean clicked;
    	
    	public SelectListener(){
    		clicked = false;
    	}
    	

        /**
         * highlights clicked element
         */
        public void mouseClicked(MouseEvent event) {
        	if (groupIndex > -1){
        		if (GroupedListPane.getLastClickedGroup() != groupIndex){
        			GroupedListPane.resetSelectionLastClickedGroup();
        			GroupedListPane.setLastClickedGroup(groupIndex);
        		}
        	}
        	System.out.println("lastSelected "+ lastSelected);
        	System.out.println("lastClickedIndex "+ lastClickedIndex);
        	System.out.println("userPanel.size() "+ userPanels.size());
        	clicked = true;
        	if (lastClickedComponent != null && lastClickedIndex < userPanels.size() 
        			&& userPanels.size() != 1
        			&& !lastClickedComponent.equals(boxes[0].getComponent(lastSelected))){
        		System.out.println("I GOT HERE VERA");
        		lastClickedComponent.setBackground(Color.WHITE);
            	userPanels.get(lastClickedIndex).setOpaque(false);
        	} else {
    	        lastClickedIndex = lastSelected;
        	}
        	if (lastSelected < userPanels.size()){
        		System.out.println("GOT IN HERE NNN");
        		boxes[0].getComponent(lastSelected).setBackground(
	                    new Color(145, 200, 200));
        		lastClickedComponent = boxes[0].getComponent(lastSelected);
        	} 
        }

        /**
         * change background to color when mouse Entered
         */
        public void mouseEntered(MouseEvent event) {
//        	if (userPanels.size() == 1){
//        		lastClickedIndex = -1;
//        	}
//        	System.out.println("lastSelected MOUSEENTER "+lastSelected);
//        	System.out.println("lastClicked MOUSEENTER "+lastClickedIndex);
            for (int i = 0; i < boxes[0].getComponentCount(); i++) {

                if (event.getSource().equals(boxes[0].getComponent(i))) {
                	System.out.println("lastClicked MOUSEENTER "+lastClickedIndex);
                	System.out.println("this is i MOUSEENTER "+i);
                	System.out.println("clicked "+clicked);
                	if (!clicked && lastClickedIndex != i){
                		System.out.println("me ish called from "+groupIndex);
	                    boxes[0].getComponent(i).setBackground(
	                            new Color(225, 247, 247));
	                    try {
	                        userPanels.get(i).setOpaque(true);
	                    } catch (IndexOutOfBoundsException e) {
	                        System.err.println("userPanel does not exist... i = " + i);
	                    }
                	} else if (lastClickedIndex == i){
                		clicked = true;
                	}
	                lastSelected = i;
                	
                }
            }
            
           
        }

        /**
         * change background to white when mouse Exited
         */
        public void mouseExited(MouseEvent event) {
        	if (!clicked && lastSelected < userPanels.size()){
        		boxes[0].getComponent(lastSelected).setBackground(Color.WHITE);
            	userPanels.get(lastSelected).setOpaque(false);
        	} else {
        		lastClickedIndex = lastSelected;
        	}
//        	if (userPanels.size() == 1 && clicked){
//        		clicked = true;
//        	}else if (userPanels.size() == 1 && !clicked){
//        		boxes[0].getComponent(0).setBackground(Color.WHITE);
//            	userPanels.get(0).setOpaque(false);
//            	clicked = false;
//        	} else {
        		clicked = false;
//        	}
        }

        /**
         * Unused methods
         */
        public void mousePressed(MouseEvent e) {
        }

        public void mouseReleased(MouseEvent e) {
        }
    }
    
    public void updateTextColor(Color color){
    	if(modifiableColors){
	    	for(JLabel l : userLabels){
	    		l.setForeground(color);
	    		l.updateUI();
	    	}
    	}
    }
}