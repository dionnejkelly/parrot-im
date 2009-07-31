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

public class CustomListPane extends GPanel {
    private Component lastSelectedComponent;
    private JPanel lastSelectedPanel;
  
    private Component lastClickedComponent;
    private JPanel lastClickedPanel;

    private int groupIndex;
    private GroupedListPane groups;
    
    private ArrayList<String> nicknames = new ArrayList<String>();
    private ArrayList<UserDataWrapper> userWrappers =
            new ArrayList<UserDataWrapper>();
    private ArrayList<JPanel> userPanels;
    private ArrayList<JLabel> userLabels = new ArrayList<JLabel>();
    Box boxes[] = new Box[1];
    private ImageIcon defaultIcon =
            new ImageIcon(this.getClass().getResource(
                    "/images/chatwindow/personal.png"));
    private int lastSelected = 0;
    public boolean modifiableColors = false;
    public Color textColor = Color.BLACK;
    
    public boolean opaque = true;

    /**
     * This constructor is used if you use groups
     * @param groupIndex */
    public CustomListPane(int groupIndex, GroupedListPane groups) {
        this.groupIndex = groupIndex;
        this.groups = groups;
        setUpCustomListPane();
    }
    
    /**
     * This constructor is used if you don't use groups
     * */
    public CustomListPane() {
        this.groupIndex = -1;
        setUpCustomListPane();
    }

    private void setUpCustomListPane(){
        setGradientColors(Color.WHITE, Color.WHITE);
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 10));

        userPanels = new ArrayList<JPanel>();
        boxes[0] = Box.createVerticalBox();

        add(boxes[0], BorderLayout.NORTH);
    }
    private JPanel friendPanel(String nickname, ImageIcon icon) {
        JPanel friendPanel = new JPanel();
        friendPanel.setLayout(new BorderLayout());

        if (icon == null) {
            icon = defaultIcon;
        }
        Image img = icon.getImage();
        img = img.getScaledInstance(32, 32, java.awt.Image.SCALE_SMOOTH);
        ImageIcon newIcon = new ImageIcon(img);

        JLabel IconLabel = new JLabel(newIcon);
        friendPanel.add(IconLabel, BorderLayout.WEST);
        JLabel nicknameLabel = new JLabel(" " + nickname);
        userLabels.add(nicknameLabel);
        friendPanel.add(new JLabel(" " + nickname), BorderLayout.CENTER);
        return friendPanel;
    }

    private JPanel friendPanel(UserDataWrapper user, ImageIcon icon) {
        JPanel friendPanel = new JPanel();
        friendPanel.setLayout(new BorderLayout());

        if (icon == null) {
            icon = defaultIcon;
        }
        Image img = icon.getImage();
        img = img.getScaledInstance(32, 32, java.awt.Image.SCALE_SMOOTH);
        ImageIcon newIcon = new ImageIcon(img);

        JLabel imageLabel = new JLabel(newIcon);

        JLabel usernameLabel = user.getLabelRepresentation();
        usernameLabel.setForeground(this.textColor);
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
    	externalFriendPanel.setBackground(Color.WHITE);
    	if (!opaque)
    		externalFriendPanel.setOpaque(false);
        userPanels.add(externalFriendPanel);
        boxes[0].add(externalFriendPanel);
        boxes[0].getComponent(boxes[0].getComponentCount() - 1)
                .addMouseListener(new SelectListener());
        updateUI();
    }

    public void addElement(JPanel externalFriendPanel, int index) {
    	externalFriendPanel.setBackground(Color.WHITE);
    	if (!opaque)
    		externalFriendPanel.setOpaque(false);
        userPanels.add(externalFriendPanel);
        boxes[0].add(externalFriendPanel, index);
        boxes[0].getComponent(index).addMouseListener(new SelectListener());
        updateUI();
    }

    public void addElement(String nickname, ImageIcon img,
            UserDataWrapper userWrapper, MouseListener externalListener) {
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

        if (userPanels.size() == 0 ){
            lastClickedComponent = null;
        }

        return;
    }

    public void removeAllElements() {
        userPanels.clear();
        boxes[0].removeAll();
        updateUI();
        lastClickedComponent = null;
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
    
    public void setGroupIndex(int index){
        groupIndex = index;
    }

    public void resetClickedSelection() {
        if (lastClickedComponent != null) {
            lastClickedComponent.setBackground(Color.WHITE);
        }
        lastClickedComponent = null;
    }

    private class SelectListener implements MouseListener {

    	private boolean clicked;
        
        public SelectListener(){
                clicked = false;
        }

        /**
         * highlights clicked element
         */
        public void mouseClicked(MouseEvent event) {
            clicked = true;
            if (groupIndex > -1){
                if (groups.getLastClickedGroup() != groupIndex){
                        groups.resetSelectionLastClickedGroup();
                        groups.setLastClickedGroup(groupIndex);
                }
            }
            Component source = (Component) event.getSource();
            if (lastClickedComponent != null && !lastClickedComponent.equals(source)) {
                lastClickedComponent.setBackground(Color.WHITE);
                if(!opaque){
                	lastClickedPanel.setOpaque(false);
                }
            }
            if (lastSelectedComponent != null) {
                lastSelectedComponent.setBackground(new Color(145, 200, 200));
                lastClickedComponent = lastSelectedComponent;
                lastClickedPanel = lastSelectedPanel;
            }
        }

        /**
         * change background to color when mouse Entered
         */
        public void mouseEntered(MouseEvent event) {
            for (int i = 0; i < boxes[0].getComponentCount(); i++) {
                if (event.getSource().equals(boxes[0].getComponent(i))) {
                    lastSelectedComponent = boxes[0].getComponent(i);
                    lastSelectedPanel = userPanels.get(i);
                    lastSelected = i;
                    
                    if ((lastClickedComponent == null) ||
                    		(!clicked && !lastClickedComponent.equals(lastSelectedComponent))){
	                    lastSelectedComponent.setBackground(new Color(225, 247, 247));
	                    lastSelectedPanel.setOpaque(true);
	
	                    try {
	                        lastSelectedPanel.setOpaque(true);
	                    } catch (IndexOutOfBoundsException e) {
	                        System.err.println("userPanel does not exist... i = "
	                                + i);
	                    }
                    } else if (lastClickedComponent.equals(lastSelectedComponent)){
                        clicked = true;
                    }

                }
            }
        }

        /**
         * change background to white when mouse Exited
         */
        public void mouseExited(MouseEvent event) {
            if (lastSelectedComponent != null && !clicked) {
                lastSelectedComponent.setBackground(Color.WHITE);
                if(!opaque){
                	lastSelectedPanel.setOpaque(false);
                }
            } else {
            	lastClickedComponent = lastSelectedComponent;
            }
            clicked = false;
        }

        /**
         * Unused methods
         */
        public void mousePressed(MouseEvent e) {
        }

        public void mouseReleased(MouseEvent e) {
        }
    }

    public void updateTextColor(Color color) {
        for (JLabel l : userLabels) {
            l.setForeground(color);
            l.updateUI();
        }
    }
}
