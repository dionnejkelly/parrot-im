/* BuddyPanel.java
 * 
 * Copyright (C) 2009  Pirate Captains
 * 
 * Full license can be found in ParrotIM/LICENSE.txt.
 */

package view.buddylist;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
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
import javax.swing.JComboBox;
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

import org.jivesoftware.smack.XMPPException;

import controller.MainController;

import view.blockManager.BlockManager;
import view.mainwindow.HelpPanel;
import view.options.GroupChatConfigurationFrame;
import view.options.MusicPlayer;
import view.styles.GroupedListPane;
import view.styles.PopupWindowListener;
import view.chatwindow.ChatWindow;

import model.Model;
import model.dataType.TwitterUserData;
import model.dataType.UserData;
import model.enumerations.ServerType;
import model.enumerations.UpdatedType;
import model.enumerations.UserStateType;

/**
 * BuddyPanel display FriendList and Account information for Parrot IM users.
 */
public class BuddyPanel extends JPanel implements Observer {
    /*
     * TODO: BUDDY PANEL HAS Center: Buddy List South: Buddy Options
     */
    // Selection
    // I - non-static member
    /**
     * selectedIndex of Buddylist
     */
    protected SelectListener lastSelectedListener;
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
    Box boxes[] = new Box[2];

    GroupedListPane buddyListPane = new GroupedListPane();

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

        this.chatClient = c;
        this.model = model;
        this.chat = null;
        this.searchEnabled = false;
        this.lastUpdate = System.currentTimeMillis();

        // Test code, make it hide at the start
        // this.chat = new ChatWindow(chatClient, model);
        buddies = null;

        friendList = new JPanel();
        friendList.setBackground(Color.WHITE);
        friendList.setLayout(new BorderLayout());

        // Place all friends from currentProfile into buddy list
        buddies = model.getCurrentProfile().getAllFriends();
        buddies = UserData.sortAlphabetical(buddies);
        buddies = UserData.sortMostOnline(buddies);

        // add friends to the buddy list
        boxes[0] = Box.createVerticalBox();
        boxes[1] = Box.createVerticalBox();

        ImageIcon googleTalkImage =
                new ImageIcon(
                        this
                                .getClass()
                                .getResource(
                                        "/images/buddylist/statusIcons/GoogleTalk/GoogleTalk-Available.png"));
        ImageIcon aimImage =
                new ImageIcon(
                        this
                                .getClass()
                                .getResource(
                                        "/images/buddylist/statusIcons/AIM/AIM-AvailableSM.png"));
        ImageIcon jabberImage =
                new ImageIcon(
                        this
                                .getClass()
                                .getResource(
                                        "/images/buddylist/statusIcons/Jabber/Jabber-AvailableSM.png"));
        ImageIcon icqImage =
                new ImageIcon(
                        this
                                .getClass()
                                .getResource(
                                        "/images/buddylist/statusIcons/ICQ/ICQ-AvailableSM.png"));
        ImageIcon msnImage =
                new ImageIcon(
                        this
                                .getClass()
                                .getResource(
                                        "/images/buddylist/statusIcons/MSN/MSN-AvailableSM.png"));
        ImageIcon twitterImage =
                new ImageIcon(this.getClass().getResource(
                        "/images/buddylist/twitter_logo.png"));

        buddyListPane.addGroup("     GoogleTalk     "
                + UserData.getCountOnline() + "/" + buddies.size(),
                googleTalkImage);
        buddyListPane.addGroup("     Twitter", twitterImage);
        buddyListPane.addGroup("     AIM", aimImage);
        buddyListPane.addGroup("     Jabber", jabberImage);
        buddyListPane.addGroup("     ICQ", icqImage);
        buddyListPane.addGroup("     MSN", msnImage);

        pictureUpdateThread = new PictureUpdateThread();
        pictureUpdateThread.start();

        listRepopulate();

        // rightclick menu
        rightClickMenu = new JPopupMenu();
        menuItem1 = new JMenuItem("Start New Conversation",  new ImageIcon(this.getClass().getResource(
        "/images/popup/user.png")));
        menuItem2 = new JMenuItem("Add to open Conversation",  new ImageIcon(this.getClass().getResource(
        "/images/popup/group_add.png")));
        menuItem3 = new JMenuItem("Remove Friend", new ImageIcon(this.getClass().getResource(
        "/images/buddylist/delete_user.png")));
        menuItem4 = new JMenuItem("Block Friend",  new ImageIcon(this.getClass().getResource(
        "/images/buddylist/button_cancel.png")));
        menuItem5 = new JMenuItem("View Profile", new ImageIcon(this.getClass().getResource(
        "/images/popup/pictures.png")));

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
        rightClickMenu.add(menuItem5);

        // friendList.add(boxes[0], BorderLayout.NORTH);
        scroller = new JScrollPane(buddyListPane);
        scroller
                .setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        options = OptionsBar();

        add(scroller, BorderLayout.CENTER);
        add(options, BorderLayout.SOUTH);
    }

    private void listRepopulate() {
        buddyListPane.removeAllElements(0);
        buddyListPane.removeAllElements(1);
        boxes[0].removeAll();
        boxes[1].removeAll();
        buddyArray = new ArrayList<ArrayList<UserData>>();
        

        // System.out.println("Starting adding Buddies: " + buddies.size());

        buddyArray.add(new ArrayList<UserData>());
        buddyArray.add(new ArrayList<UserData>());
        for (int i = 0; i < buddies.size(); i++) {
            if (buddies.get(i).getServer().toString().equals("Google Talk")) {
                buddyArray.get(0).add(buddies.get(i));
                boxes[0].add(FriendItem(buddies.get(i)));
                buddyListPane.addElement(0, FriendItem(buddies.get(i)));
                // System.out.println(buddies.get(i).getUserID() +
                // " added to googleTalk");
            } else if (buddies.get(i).getServer().toString().equals("Twitter")) {
                buddyArray.get(1).add(buddies.get(i));
                boxes[1].add(FriendItem(buddies.get(i)));
                buddyListPane.addElement(1, FriendItem(buddies.get(i)));
                // System.out.println(buddies.get(i).getUserID() +
                // " added to twitter");
            }
        }
        // System.out.println("Ending adding Buddies");

        // add mouse listeners to googleTalk
        for (int i = 0; i < boxes[0].getComponentCount(); i++) {
            // System.out.println(boxes[0].getComponentCount() + ":" + i);
            buddyListPane.addExternalMouseListener(0, i, new SelectListener());
        }
        // add mouse listeners to Twitter
        for (int i = 0; i < boxes[1].getComponentCount(); i++) {
            // System.out.println(boxes[1].getComponentCount() + ":" + i);
            buddyListPane.addExternalMouseListener(1, i, new SelectListener());
        }

        /*
         * System.out.println("total buddies: " + buddies.size()); int count =
         * boxes[0].getComponentCount() + boxes[1].getComponentCount();
         * System.out.println("Google: " + boxes[0].getComponentCount());
         * System.out.println("Twitter: " + boxes[1].getComponentCount());
         */
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
                        "/images/buddylist/button_cancel.png")));
        blockF.setToolTipText("Block a friend");

        searchButton =
                new JButton(new ImageIcon(this.getClass().getResource(
                        "/images/buddylist/document_preview.png")));
        searchButton.setToolTipText("Start searching");

        googleSearchButton =
                new JButton(new ImageIcon(this.getClass().getResource(
                        "/images/buddylist/google_search.png")));
        googleSearchButton.setToolTipText("Start Googling");

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

    // Section
    // III - Accessors and Mutator

    /**
     * listener for the search button
     */
    class searchListener extends MouseAdapter {
        public void mousePressed(MouseEvent event) {
            Object source = event.getSource();

            System.out.println("Event = " + event);
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
            chatClient.blockFriend(selectedFriend);
        }
    }
    
    /**
     * for user to invite a friend to a group conversation
     * 
     */
    
    class inviteFriendListener extends MouseAdapter {
    	
    	public void mousePressed(MouseEvent event) {
    		GroupChatConfigurationFrame groupConfig = new GroupChatConfigurationFrame(model);
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
            System.out.println("Add Friend Clicked");
            String userFriendID;
            String result =
                    "Ay Ay Captain! One person will be invited to your Parrot IM Buddy List.";

            // not able to cancel it for now

            userFriendID =
                    JOptionPane.showInputDialog("Enter an email address: ");

            if (userFriendID.equals(chatClient.getAccount())) {
                String redundancy =
                        "Argh, you cannot add yourself! Please provide a different email address.";
                JOptionPane.showMessageDialog(null, redundancy);

            }

            else if ((userFriendID != null && !userFriendID.equals(""))
                    && !userExist(userFriendID)) {
                chatClient.addFriend(userFriendID);
                MusicPlayer addMusic =
                        new MusicPlayer("src/audio/buddy/addFriend.wav", model);
                JOptionPane.showMessageDialog(null, result);

                // buddies.add(new GoogleTalkUserData(userFriendID));

                // add friends to the buddy list

                // boxes[0].add(FriendItem(buddies.get(buddies.size() - 1)));

                // boxes[0].getComponent(buddies.size() - 1).addMouseListener(
                // new SelectListener());

                // friendList.updateUI();

            }

            else if (userFriendID == null || userFriendID.equals("")) {
                String redundancy =
                        "Argh, please provide an appropriate user email address. Thank you for your co-operation.";
                JOptionPane.showMessageDialog(null, redundancy);

            }

            else {
                String redundancy =
                        "Argh, the friend's email address you have provided is already an existing contact. Please provide a non-existing friend's email address.";
                JOptionPane.showMessageDialog(null, redundancy);

            }
            System.out.println("User Input = " + userFriendID);

        }

        /**
         * check if userID exist
         * 
         * @param userID
         * @return boolean
         */
        public boolean userExist(String userID) {
            for (int i = 0; i < buddies.size(); i++) {
                if (buddies.get(i).getUserID().equals(userID)) {
                    return true;
                }
            }

            return false;
        }
    }

    /**
     * FriendItem to check friend status.
     * 
     * @param user
     * @return friendItem
     */
    public JPanel FriendItem(UserData user) {
        ImageIcon defaultIcon =
                new ImageIcon(this.getClass().getResource(
                        "/images/chatwindow/personal.png"));
        JLabel label = null;

        JPanel friendItem = new JPanel();
        String server = null;
        int minutesSinceUpdate = 16384;
        friendItem.setLayout(new BorderLayout());
        friendItem.setBackground(Color.WHITE);

        friendItem.setName(user.getNickname());
        server = user.getServer().toString();

        // end it
        friendItem.setToolTipText("<html>  " + user.getNickname() + "("
                + user.getUserID() + ")" + "<br>" + user.getStatus()
                + "<br> Status:" + user.getState() + "<br>" + user.getServer()
                + "<hr>" + "Right-click for more options");

        JLabel friendName;

        // Colour our world! Sets colours for our friends.
        // Note: Separating the name and the status colours would be great.
        if (user.isBlocked()) {
            friendName = new JLabel("  Blocked: " + user.getUserID() + " *");
            friendName.setForeground(Color.LIGHT_GRAY.darker());
        } else if (user instanceof TwitterUserData) {
            minutesSinceUpdate =
                    ((TwitterUserData) user).getMinutesSinceUpdate();

            friendName =
                    new JLabel("  " + user.getNickname() + user.getStatus()
                            + " (Changed: " + minutesSinceUpdate
                            + " minutes ago)");
            if (minutesSinceUpdate < 60) {
                friendName.setForeground(Color.GREEN.darker());
            } else if (minutesSinceUpdate < 300) {
                friendName.setForeground(Color.ORANGE.darker());
            } else {
                friendName.setForeground(Color.RED.darker());
            }
        } else if (user.getState() == UserStateType.ONLINE) {
            if (user.getNickname().trim().equals("")) {
                friendName =
                        new JLabel("  " + user.getUserID() + user.getStatus()
                                + " (" + user.getState() + ")");
            } else {
                friendName =
                        new JLabel("  " + user.getNickname() + user.getStatus()
                                + " (" + user.getState() + ")");
            }
            friendName.setForeground(Color.GREEN.darker());
        } else if (user.getState() == UserStateType.BUSY) {
            if (user.getNickname().trim().equals("")) {
                friendName =
                        new JLabel("  " + user.getUserID() + user.getStatus()
                                + " (Busy)");
            } else {
                friendName =
                        new JLabel("  " + user.getNickname() + user.getStatus()
                                + " (Busy)");
            }
            friendName.setForeground(Color.ORANGE.darker());
        } else if (user.getState() == UserStateType.AWAY) {
            if (user.getNickname().trim().equals("")) {
                friendName =
                        new JLabel("  " + user.getUserID() + user.getStatus()
                                + " (Away)");
            } else {
                friendName =
                        new JLabel("  " + user.getNickname() + user.getStatus()
                                + " (Away)");
            }
            friendName.setForeground(Color.ORANGE.darker());
        } else {
            if (user.getNickname().trim().equals("")) {
                friendName =
                        new JLabel("  " + user.getUserID() + user.getStatus()
                                + " (" + user.getState() + ")");
            } else {
                friendName =
                        new JLabel("  " + user.getNickname() + user.getStatus()
                                + " (" + user.getState() + ")");
            }
            friendName.setForeground(Color.RED.darker());
        }

        ImageIcon avatarImage = defaultIcon;
        Image img = avatarImage.getImage();
        img = img.getScaledInstance(25, 25, java.awt.Image.SCALE_SMOOTH);
        ImageIcon newIcon = new ImageIcon(img);
        label = new JLabel(newIcon);
        friendItem.add(label, BorderLayout.WEST);
        pictureUpdateThread.addUserAndLabel(user, label);

        /*
         * if(server == "Google Talk"){ if(user.getState() ==
         * UserStateType.ONLINE){ friendItem.add(new JLabel(new
         * ImageIcon(this.getClass().getResource(
         * "/images/buddylist/statusIcons/GoogleTalk/GoogleTalk-Available.png"
         * ))), BorderLayout.WEST); }else if(user.getState() ==
         * UserStateType.AWAY || user.getState() == UserStateType.BUSY){
         * friendItem.add(new JLabel(new ImageIcon(this.getClass().getResource(
         * "/images/buddylist/statusIcons/GoogleTalk/GoogleTalk-Away.png"))),
         * BorderLayout.WEST); }else{ friendItem.add(new JLabel(new
         * ImageIcon(this.getClass().getResource(
         * "/images/buddylist/statusIcons/GoogleTalk/GoogleTalk-Offline.png"))),
         * BorderLayout.WEST); } }else{ friendName.setText(server +
         * friendName.getText()); }
         */

        friendItem.add(friendName, BorderLayout.CENTER);
        // friendItem.add(friendStatus,BorderLayout.CENTER);

        return friendItem;
    }

    private class PictureUpdateThread extends Thread {
        private ArrayList<JLabel> labels;
        private ArrayList<UserData> users;

        public void addUserAndLabel(UserData user, JLabel label) {
            users.add(user);
            labels.add(label);

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
                }
            }
        }
    }

    /**
     * update current friend status on the buddy list.
     */
    public void update(Observable o, Object arg) {
        if (arg == UpdatedType.BUDDY) {
            if (System.currentTimeMillis() - this.lastUpdate > 10000) {
                this.refreshBuddyList();
                this.lastUpdate = System.currentTimeMillis();
                System.out.println("YESSS!!!");
            } else {
                System.out.println("NO!!!");
            }

        }

        return;
    }

    public void refreshBuddyList() {
        boxes[0].removeAll();
        pictureUpdateThread.clearUpdateQueue();

        buddies = model.getCurrentProfile().getAllFriends();
        buddies = UserData.sortAlphabetical(buddies);
        buddies = UserData.sortMostOnline(buddies);
        // buddyListPane.removeAllElements(0);

        // Now check to see if there is a search going on
        if (this.searchEnabled) {
            this.searchBuddies();
        }

        listRepopulate();

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
        protected boolean selected;

        /**
         * SelectListener()
         */
        public SelectListener() {
            selected = false;
        }

        /**
         * mouseClicked to unhighlight the last selected
         */
        public void mouseClicked(MouseEvent event) {
            for (int j = 0; j < buddyListPane.getGroupCount(); j++) {
                for (int i = 0; i < boxes[0].getComponentCount(); i++) {
                    if (event.getSource().equals(
                            buddyListPane.getComponent(j, i))) {
                        if (event.getButton() == event.BUTTON1) {
                            // Left Click
                            selected = true;
                            lastSelectedListener = this;

                            /* Fix this to directly reference the GUI */
                            selectedFriend = buddyArray.get(j).get(i);

                            if (event.getClickCount() == 2) {
                                selected = false;
                                chatClient.startConversation(selectedFriend,
                                        true);
                            }
                        } else if (event.getSource().equals(
                                buddyListPane.getComponent(j, i))) {
                            // Right Click
                            rightClickMenu.show(buddyListPane
                                    .getComponent(j, 1), event.getX(), event
                                    .getY()
                                    + 25 * i);
                            selectedName =
                                    buddyListPane.getComponent(j, 1).getName();
                            selectedFriend = buddyArray.get(j).get(i);
                        }
                    }
                }
            }

            MusicPlayer highlightMusic =
                    new MusicPlayer(
                            "src/audio/buddy/buddyHighlightedSound.wav", model);
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
            if (search.getText().length() > 0 && e.getKeyCode() == e.VK_ENTER) {
                chatClient.startConversation(buddies.get(0), true);
            }
            return;
        }

        public void keyReleased(KeyEvent e) {
            if (search.getText().length() < 1) {
                searchEnabled = false;
                boxes[0].getComponent(0).setBackground(Color.WHITE);
                refreshBuddyList();
            } else { // we have text! search!
                searchEnabled = true;

                refreshBuddyList();
                boxes[0].getComponent(0)
                        .setBackground(new Color(145, 200, 200));

            }

            // listRepopulate();
            // refreshBuddyList(); // automatically factors in the search

            return;
        }

        public void keyTyped(KeyEvent e) {
            // Do nothing

            return;
        }
    }

    private void searchBuddies() {
        buddies = UserData.sortByStringMatch(buddies, search.getText());

        return;
    }
}