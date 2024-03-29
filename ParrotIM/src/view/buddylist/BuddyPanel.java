/* BuddyPanel.java
 * 
 * Copyright (C) 2009  Pirate Captains
 * 
 * Full license can be found in ParrotIM/LICENSE.txt.
 */

package view.buddylist;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.ScrollPaneConstants;

import model.Model;
import model.dataType.UserData;
import model.enumerations.ServerType;
import model.enumerations.UpdatedType;
import model.enumerations.UserStateType;

import org.jivesoftware.smack.XMPPException;

import view.blockManager.BlockManager;
import view.chatwindow.ChatWindow;
import view.mainwindow.HelpPanel;
import view.options.GroupChatConfigurationFrame;
import view.options.MusicPlayer;
import view.styles.GPanel;
import view.styles.GroupedListPane;
import view.styles.PopupWindowListener;
import controller.MainController;

/**
 * BuddyPanel display FriendList and Account information for Parrot IM users.
 */
public class BuddyPanel extends GPanel implements Observer {
    /*
     * TODO: BUDDY PANEL HAS Center: Buddy List South: Buddy Options
     */
    // Selection
    // I - non-static member
    /**
     * select source of Buddylist
     */
    protected Object lastSelectedSource;
    /**
     * buddy window frame
     */
    protected JFrame buddyWindow;
    /**
     * chat window
     */
    ChatWindow chat;
    /**
     * options tool
     */
    JToolBar options;
    /**
     * scroller of Buddy list
     */
    JScrollPane scroller;
    /**
     * friend contact list.
     */
    JPanel friendList;
    /**
     * right click pop up menu.
     */
    JPopupMenu rightClickMenu;
    /**
     * menuItems in pop up menu
     */
    JMenuItem menuItem1, menuItem2, menuItem3, menuItem4, menuItem5;
    /**
     * box to store friend list
     */
    Box boxes[] = new Box[4];

    GroupedListPane buddyListPane;

    /**
     * selected name
     */
    String selectedName;
    /**
     * variable to handles all inputs from user interaction
     */
    MainController chatClient;
    /**
     * model for extracting buddy list, each buddy's information and ,
     * conversation
     */
    Model model;
    /**
     * selected friend
     */
    UserData selectedFriend;

    /**
     * list of buddies
     */
    private ArrayList<UserData> buddies;

    private ArrayList<ArrayList<UserData>> buddyArray;

    private JTextField search;

    private boolean searchEnabled;

    private JButton searchButton;

    private JButton googleSearchButton;

    private PictureUpdateThread pictureUpdateThread;

    private long lastUpdate;

    private ArrayList<FriendWrapper> friendWrappers;

    private ArrayList<FriendPanel> friendPanels;

    private SelectListener selectListener;

    private ArrayList<JLabel> groupNames;

    /**
     * BuddyPanel , display friend contact list in buddy panel.
     * 
     * @param c
     * @param model
     * @param buddyWindow
     */
    // SELECTION
    // II-Constructors
    public BuddyPanel(MainController c, Model model, JFrame buddyWindow) {
        this.buddyWindow = buddyWindow;
        model.addObserver(this);
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        setBackground(Color.WHITE);
        setGradientColors(model.primaryColor, model.secondaryColor);

        this.chatClient = c;
        this.model = model;
        this.searchEnabled = false;
        this.lastUpdate = System.currentTimeMillis();
        this.friendWrappers = new ArrayList<FriendWrapper>();
        this.friendPanels = new ArrayList<FriendPanel>();
        this.selectListener = new SelectListener();
        this.groupNames = new ArrayList<JLabel>();

        buddyArray = new ArrayList<ArrayList<UserData>>();
        for (int i = 0; i < 4; i++) {
            buddyArray.add(new ArrayList<UserData>());
        }

        buddies = null;

        friendList = new JPanel();
        friendList.setBackground(Color.WHITE);
        friendList.setLayout(new BorderLayout());

        // Place all friends from currentProfile into buddy list
        buddies = model.getCurrentProfile().getAllFriends();
        buddies = UserData.sortAlphabetical(buddies);
        buddies = UserData.sortMostOnline(buddies);

        // add friends to the buddy list
        for (int i = 0; i < 4; i++) {
            boxes[i] = Box.createVerticalBox();
        }

        ImageIcon userOnlineImage =
                new ImageIcon(this.getClass().getResource(
                        "/images/status/user_green.png"));
        ImageIcon userOfflineImage =
                new ImageIcon(this.getClass().getResource(
                        "/images/status/user_red.png"));
        ImageIcon userBlockImage =
                new ImageIcon(this.getClass().getResource(
                        "/images/status/user_gray.png"));
        ImageIcon userAwayImage =
                new ImageIcon(this.getClass().getResource(
                        "/images/status/user_orange.png"));

        groupNames.add(new JLabel("     Online      ?/?"));
        groupNames.add(new JLabel("     Away/Busy     ?/?"));
        groupNames.add(new JLabel("     Offline      ?/?"));
        groupNames.add(new JLabel("     Blocked      ?/?"));

        buddyListPane = new GroupedListPane(model);

        buddyListPane.addGroup(groupNames.get(0), userOnlineImage);
        buddyListPane.addGroup(groupNames.get(1), userAwayImage);
        buddyListPane.addGroup(groupNames.get(2), userOfflineImage);
        buddyListPane.addGroup(groupNames.get(3), userBlockImage);


        pictureUpdateThread = new PictureUpdateThread();
        pictureUpdateThread.start();

        listRepopulate();

        // rightclick menu
        rightClickMenu = new JPopupMenu();
        menuItem1 =
                new JMenuItem("Start New Conversation", new ImageIcon(this
                        .getClass().getResource("/images/popup/comment.png")));
        menuItem2 =
                new JMenuItem("Add to open Conversation", new ImageIcon(this
                        .getClass().getResource(
                                "/images/popup/comments_add.png")));
        menuItem3 =
                new JMenuItem("Remove Friend", new ImageIcon(this.getClass()
                        .getResource("/images/buddylist/delete_user.png")));
        menuItem4 =
                new JMenuItem("Block Friend", new ImageIcon(this.getClass()
                        .getResource("/images/buddylist/block_user.png")));

        menuItem1.addMouseListener(new RightCickMenuListener());
        menuItem2.addMouseListener(new inviteFriendListener());
        menuItem3.addMouseListener(new RightClickMenuRemoveFriendListener());
        menuItem4.addMouseListener(new RightClickMenuBlockFriendListener());

        rightClickMenu.add(menuItem1);
        rightClickMenu.add(menuItem2);
        rightClickMenu.addSeparator();
        rightClickMenu.add(menuItem3);
        rightClickMenu.add(menuItem4);
        rightClickMenu.addSeparator();
        
        scroller = new JScrollPane(buddyListPane);
        scroller
                .setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scroller.getVerticalScrollBar().setUnitIncrement(16);
        options = OptionsBar();
        options.setBackground(model.tertiaryColor);
        options.setForeground(model.tertiaryColor);

        add(scroller, BorderLayout.CENTER);
        add(options, BorderLayout.SOUTH);
    }

    private String groupToName(int group) {
        String toReturn = null;

        switch (group) {
        case 0:
            toReturn = "     Online       ";
            break;
        case 1:
            toReturn = "     Away/Busy    ";
            break;
        case 2:
            toReturn = "     Offline      ";
            break;
        case 3:
            toReturn = "     Blocked      ";
            break;
        default:
            toReturn = "";
            break;
        }

        return toReturn;
    }

    private void reorderFriends() {
        ArrayList<FriendPanel> outOfOrderBoxes = null;
        ArrayList<UserData> outOfOrderBuddyArray = null;
        ArrayList<FriendPanel> outOfOrderPane = null;
        FriendPanel toRemove = null;
        UserData current = null;
        boolean found = false;
        int group = 0;

        outOfOrderBoxes = new ArrayList<FriendPanel>();
        outOfOrderBuddyArray = new ArrayList<UserData>();
        outOfOrderPane = new ArrayList<FriendPanel>();

        // Remove all conflicting users
        for (group = 0; group < boxes.length; group++) {
            for (int i = 0; i < boxes[group].getComponentCount(); i++) {
                current =
                        ((FriendPanel) boxes[group].getComponent(i))
                                .getWrapper().getUser();

                if (group != this.userToBoxIndex(current)) {
                    toRemove = (FriendPanel) boxes[group].getComponent(i);

                    outOfOrderBoxes.add(toRemove);
                    boxes[group].remove(toRemove);
                    outOfOrderBuddyArray.add(current);
                    buddyArray.get(group).remove(current);
                    toRemove =
                            (FriendPanel) buddyListPane.getGroup(group)
                                    .getBoxes().getComponent(i);
                    outOfOrderPane.add(toRemove);
                    buddyListPane.removeElement(group, toRemove);
                }
            }
        }

        // Re-add the users in the appropriate locations
        for (int i = 0; i < outOfOrderBuddyArray.size(); i++) {
            found = false;
            current = outOfOrderBuddyArray.get(i);
            group = this.userToBoxIndex(current);

            for (int j = 0; j < buddyArray.get(group).size() && !found; j++) {
                if (current.compareTo(buddyArray.get(group).get(j)) < 0) {
                    // Means that the out-of-order user is more online
                    // than current... we need to replace!
                    buddyArray.get(group).add(j, outOfOrderBuddyArray.get(i));
                    boxes[group].add(outOfOrderBoxes.get(i), j);
                    buddyListPane.addElement(group, outOfOrderPane.get(i), j);

                    found = true;
                }
            }
            if (!found) {
                // Still not found, add to end;
                buddyArray.get(group).add(outOfOrderBuddyArray.get(i));
                boxes[group].add(outOfOrderBoxes.get(i));
                buddyListPane.addElement(group, outOfOrderPane.get(i));
            }
        }

        // Refresh the user count
        for (group = 0; group < boxes.length; group++) {
            groupNames.get(group).setText(
                    this.groupToName(group) + boxes[group].getComponentCount());
        }
    }

    public void listRepopulate() {
        FriendPanel tempPanel = null;
        int boxIndex = -1;

        for (int i = 0; i < 4; i++) {
            buddyListPane.removeAllElements(i);
            boxes[i].removeAll();
        }

        for (ArrayList<UserData> a : buddyArray) {
            a.clear();
        }

        // Create the friend wrapper
        // Compare the model friends with the GUI friends
        for (UserData u : this.buddies) {
            tempPanel = FriendItem(u);
            this.friendPanels.add(tempPanel); // adds wrapper
            if (!u.getServer().equals(ServerType.TWITTER)) {
                boxIndex = this.userToBoxIndex(u);
                buddyArray.get(boxIndex).add(u);
                boxes[boxIndex].add(FriendItem(u));
                buddyListPane.addElement(boxIndex, tempPanel);
            }
        }

        // add mouse listeners
        for (int j = 0; j < boxes.length; j++) {
            groupNames.get(j).setText(
                    this.groupToName(j) + boxes[j].getComponentCount());
            for (int i = 0; i < boxes[j].getComponentCount(); i++) {
                buddyListPane.addExternalMouseListener(j, i,
                        this.selectListener);
            }
        }
    }

    private int userToBoxIndex(UserData user) {
        int boxIndex = -1;

        if (user.isBlocked()) {
            boxIndex = 3;
        } else {
            if (user.getState() == UserStateType.ONLINE) {
                boxIndex = 0;
            } else if (user.getState() == UserStateType.AWAY
                    || user.getState() == UserStateType.BUSY
                    || user.getState() == UserStateType.PHONE
                    || user.getState() == UserStateType.BRB
                    || user.getState() == UserStateType.LUNCH) {
                boxIndex = 1;
            } else if (user.getState() == UserStateType.OFFLINE) {
                boxIndex = 2;
            } else {
                boxIndex = 3;
            }
        }

        return boxIndex;
    }

    /**
     * optionsBar(), provide options tool for user to add, delete, and block
     * friend from buddy list
     * 
     * @return options
     */
    public JToolBar OptionsBar() {
        JToolBar options = new JToolBar();
        options.setFloatable(false);

        this.search = new JTextField();
        JButton addF =
                new JButton(new ImageIcon(this.getClass().getResource(
                        "/images/buddylist/add_user.png")));
        addF.setToolTipText("Add a friend");

        JButton removeF =
                new JButton(new ImageIcon(this.getClass().getResource(
                        "/images/buddylist/delete_user.png")));
        removeF.setToolTipText("Remove a friend");

        JButton blockF =
                new JButton(new ImageIcon(this.getClass().getResource(
                        "/images/buddylist/block_user.png")));
        blockF.setToolTipText("Block a friend");

        searchButton =
                new JButton(new ImageIcon(this.getClass().getResource(
                        "/images/buddylist/document_preview.png")));
        searchButton.setToolTipText("Start searching for a contact");

        googleSearchButton =
                new JButton(new ImageIcon(this.getClass().getResource(
                        "/images/buddylist/google_search.png")));
        googleSearchButton.setToolTipText("Click me to start Googling");

        // add components
        options.add(addF);
        options.add(removeF);
        options.add(blockF);
        options.add(search);
        options.add(searchButton);
        options.add(googleSearchButton);

        addF.addMouseListener(new addFriendListener());
        removeF.addMouseListener(new removeFriendListener());
        blockF.addMouseListener(new blockFriendListener());
        search.addKeyListener(new SearchKeyListener());
        searchButton.addMouseListener(new searchListener());
        googleSearchButton.addMouseListener(new searchListener());

        return options;
    }

    public JFrame getBuddyList() {
        return buddyWindow;
    }

    // Section
    // III - Accessors and Mutator

    /**
     * listener for the search button
     */
    class searchListener extends MouseAdapter {
        public void mousePressed(MouseEvent event) {
            Object source = event.getSource();

            if (search.getText().length() > 0) {

                if (source == searchButton) {
                    chatClient.startConversation(buddies.get(0), true);
                }

                else {
                    HelpPanel googleSearch = new HelpPanel();
                    try {
                        googleSearch.googlePanel(search.getText());
                    } catch (UnsupportedEncodingException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            } else {
                String resultMessage =
                        "Please provide a key word in the search field.";
                JOptionPane.showMessageDialog(null, resultMessage);
            }
        }
    }

    /**
     * listener for the block button block a buddy when event occurs
     */
    class blockFriendListener extends MouseAdapter {
        public void mousePressed(MouseEvent event) {

            BlockManager blockedUser = new BlockManager(chatClient, model);
            blockedUser.addWindowListener(new PopupWindowListener(buddyWindow,
                    blockedUser));

        }
    }

    /**
     * for user to block friend by right click menu bar
     * 
     */
    class RightClickMenuBlockFriendListener extends MouseAdapter {
        public void mousePressed(MouseEvent event) {
            if (selectedFriend.isBlocked()) {
                chatClient.unblockFriend(selectedFriend);
            } else { // currently unblocked; block it
                chatClient.blockFriend(selectedFriend);
            }

            return;
        }
    }

    /**
     * for user to invite a friend to a group conversation
     * 
     */

    class inviteFriendListener extends MouseAdapter {

        public void mousePressed(MouseEvent event) {

            if (chatClient.getAvailableRoom().size() > 0) {
                if (!model.groupChatWindowOpen) {
                    new GroupChatConfigurationFrame(chatClient, model, chat,
                            buddyWindow);
                }
            }

            else {
                JOptionPane.showMessageDialog(null,
                        "Could not find any conference room.", "Failed",
                        JOptionPane.ERROR_MESSAGE);
            }

        }

    }

    /**
     * for user to delete friend from right click menu bar
     * 
     */
    class RightClickMenuRemoveFriendListener extends MouseAdapter {
        public void mousePressed(MouseEvent event) {
            chatClient.removeFriend(selectedFriend);

            // weird this wasn't used in the past but it seems non-functional
            // without them
            // Please consider this code!

            buddies.remove(selectedFriend);
            boxes[0].removeAll();
            buddyListPane.removeAllElements(0);

            listRepopulate();

            friendList.updateUI();
        }
    }

    /**
     * listener for the remove button delete a buddy when the event occurs
     * 
     */
    class removeFriendListener extends MouseAdapter {
        public void mousePressed(MouseEvent event) {

            if (selectedFriend != null) {
                chatClient.removeFriend(selectedFriend);

                buddies.remove(selectedFriend);
                boxes[0].removeAll();
                buddyListPane.removeAllElements(0);

                listRepopulate();

                friendList.updateUI();

            }

        }
    }

    /**
     * listener for the add button add a new buddy when event occurs
     */
    class addFriendListener extends MouseAdapter {
        /**
         * mousePressed, display feedback when pressing mouse
         */
        public void mousePressed(MouseEvent event) {
            if (model.getCurrentProfile().getAllAccountsServer().size() > 0) {
                AddBuddyFrame addbuddyFrame =
                        new AddBuddyFrame(model, chatClient, null);
                addbuddyFrame.addWindowListener(new PopupWindowListener(
                        buddyWindow, addbuddyFrame));
            } else {
                String resultMessage =
                        "You don't have any account right now, please create one.";
                JOptionPane.showMessageDialog(null, resultMessage,
                        "Information", JOptionPane.INFORMATION_MESSAGE);
            }

        }
    }

    /**
     * FriendItem to check friend status.
     * 
     * @param user
     * @return friendItem
     */
    public FriendPanel FriendItem(UserData user) {
        ImageIcon defaultIcon =
                new ImageIcon(this.getClass().getResource(
                        "/images/chatwindow/personal.png"));
        JLabel label = null;
        FriendPanel friendItem = new FriendPanel();
        FriendWrapper tempWrapper = null;

        friendItem.setLayout(new BorderLayout());
        friendItem.setBackground(Color.WHITE);

        friendItem.setName(user.getNickname());

        // end it
        friendItem.setToolTipText("<html>  " + user.getNickname() + "("
                + user.getUserID() + ")" + "<br>" + user.getStatus()
                + "<br> Status:" + user.getState() + "<br>" + user.getServer()
                + "<hr>" + "Right-click for more options");

        // Colour our world! Sets colours for our friends.
        // Note: Separating the name and the status colours would be great.

        tempWrapper = new FriendWrapper(user);
        this.friendWrappers.add(tempWrapper);

        ImageIcon avatarImage = defaultIcon;
        Image img = avatarImage.getImage();
        img = img.getScaledInstance(25, 25, java.awt.Image.SCALE_SMOOTH);
        ImageIcon newIcon = new ImageIcon(img);
        label = new JLabel(newIcon);
        friendItem.add(label, BorderLayout.WEST);
        pictureUpdateThread.addUserAndLabel(user, label);

        friendItem.add(tempWrapper.getLabelRepresentation(),
                BorderLayout.CENTER);
        friendItem.setWrapper(tempWrapper);
        return friendItem;
    }

    private class PictureUpdateThread extends Thread {
        private ArrayList<JLabel> labels;
        private ArrayList<UserData> users;

        public void addUserAndLabel(UserData user, JLabel label) {
            if (users != null && labels != null) {
                users.add(user);
                labels.add(label);
            }

            return;
        }

        public void clearUpdateQueue() {
            this.users.clear();
            this.labels.clear();

            return;
        }

        private boolean more() {
            return users.size() > 0 && labels.size() > 0;
        }

        public void run() {
            this.setName("Picture thread muahha!");
            users = new ArrayList<UserData>();
            labels = new ArrayList<JLabel>();
            UserData user = null;
            JLabel label = null;

            while (true) {
                if (more()) {
                    user = users.remove(0);
                    label = labels.remove(0);
                    ImageIcon avatarImage = null;
                    try {
                        avatarImage = chatClient.getAvatarPicture(user);
                        Image img = avatarImage.getImage();
                        img =
                                img.getScaledInstance(25, 25,
                                        java.awt.Image.SCALE_SMOOTH);
                        ImageIcon newIcon = new ImageIcon(img);
                        label.setIcon(newIcon);
                    } catch (XMPPException e) {
                        System.err.println("Error in picture thread");
                        e.printStackTrace();
                    }
                } else {
                    // Rests thread to prevent cpu overload
                    try {
                        Thread.currentThread();
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * update current friend status on the buddy list.
     */
    public void update(Observable o, Object arg) {
        if (arg == UpdatedType.BUDDY) {
            this.refreshBuddyList();
        } else if (arg == UpdatedType.COLOR) {
            setGradientColors(model.primaryColor, model.secondaryColor);
            this.options.setBackground(model.tertiaryColor);
            updateUI();
        }

        return;
    }

    public void refreshBuddyList() {
        int guiFriendCount = 0;

        // boxes[0].removeAll();
        pictureUpdateThread.clearUpdateQueue();

        buddies = model.getCurrentProfile().getAllFriends();
        buddies = UserData.sortAlphabetical(buddies);
        buddies = UserData.sortMostOnline(buddies);

        // Now check to see if there is a search going on
        if (this.searchEnabled) {
            this.searchBuddies();
        }

        // Count the GUI buddies, and compare to the real amount
        guiFriendCount = 0;
        for (int i = 0; i < buddyArray.size(); i++) {
            for (UserData u : buddyArray.get(i)) {
                guiFriendCount++;
            }
        }

        // Only repopulate if count changed
        if (guiFriendCount != buddies.size()) {
            this.listRepopulate();
        } else {
            // Simply reorder, if not changed
            this.reorderFriends();
        }

        friendList.updateUI();

        return;
    }

    /**
     * SelectListener, select friend from buddy list.
     * 
     */
    private class SelectListener implements MouseListener {
        /**
         * boolean variable, indicate whether selected.
         */

        /**
         * SelectListener()
         */
        public SelectListener() {
        }

        /**
         * mouseClicked to unhighlight the last selected
         */
        public void mouseClicked(MouseEvent event) {
            for (int j = 0; j < buddyArray.size(); j++) {
                for (int i = 0; i < boxes[j].getComponentCount(); i++) {
                    if (event.getSource().equals(
                            buddyListPane.getComponent(j, i))) {
                        if (event.getButton() == MouseEvent.BUTTON1) {
                            // Left Click

                            /* Fix this to directly reference the GUI */
                            selectedFriend = buddyArray.get(j).get(i);

                            if (event.getClickCount() == 2) {
                                chatClient.startConversation(selectedFriend,
                                        true);
                            }
                        } else if (event.getSource().equals(
                                buddyListPane.getComponent(j, i))) {
                            // Right Click
                            rightClickMenu.show(buddyListPane
                                    .getComponent(j, i), event.getX(), event
                                    .getY());
                            // + 25 * i);
                            selectedName =
                                    buddyListPane.getComponent(j, i).getName();
                            selectedFriend = buddyArray.get(j).get(i);
                            if (selectedFriend.isBlocked()) {
                                menuItem4.setText("Unblock Friend");
                                menuItem4
                                        .setIcon(new ImageIcon(
                                                this
                                                        .getClass()
                                                        .getResource(
                                                                "/images/buddylist/unblock_user.png")));
                            } else { // is not blocked; block it
                                menuItem4.setText("Block Friend");
                                menuItem4
                                        .setIcon(new ImageIcon(
                                                this
                                                        .getClass()
                                                        .getResource(
                                                                "/images/buddylist/block_user.png")));
                            }
                        }
                    }
                }
            }

            new MusicPlayer("/audio/buddy/buddyHighlightedSound.wav", model);
        }

        // unimplemented mouselistener methods
        public void mouseEntered(MouseEvent event) {
        }

        public void mouseExited(MouseEvent event) {
        }

        public void mousePressed(MouseEvent e) {
        }

        public void mouseReleased(MouseEvent e) {
        }
    }

    /**
     * RightCickMenuListener , user right click mouse to do actions.
     * 
     */
    class RightCickMenuListener extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent event) {
            if (event.getSource().equals(menuItem1)) {
                /* Is the chat window already open? */
                chatClient.startConversation(selectedFriend, true);
            } else if (event.getSource().equals(menuItem2)) {
                // chat.addToConversation(selectedName);
                // TODO Group chat not yet implemented.
            }

        }
    }

    class SearchKeyListener implements KeyListener {

        public void keyPressed(KeyEvent e) {
            // Do nothing
            if (search.getText().length() > 0
                    && e.getKeyCode() == KeyEvent.VK_ENTER) {
                chatClient.startConversation(buddies.get(0), true);
            }
            return;
        }

        public void keyReleased(KeyEvent e) {
            if (search.getText().length() < 1) {
                searchEnabled = false;
                refreshBuddyList();
            } else { // we have text! search!
                searchEnabled = true;

                refreshBuddyList();

            }
            return;
        }

        public void keyTyped(KeyEvent e) {
        }
    }

    private void searchBuddies() {
        buddies = UserData.cullByStringMatch(buddies, search.getText());

        return;
    }
}