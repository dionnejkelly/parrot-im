/* BuddyPanel.java
 * 
 * Copyright (C) 2009  Pirate Captains
 * 
 * Full license can be found in ParrotIM/LICENSE.txt.
 */

package view.buddylist;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

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

import controller.MainController;

import view.blockManager.BlockManager;
import view.styles.PopupWindowListener;
import view.chatwindow.ChatWindow;

import model.DatabaseFunctions;
import model.Model;
import model.dataType.ConversationData;
import model.dataType.GoogleTalkUserData;
import model.dataType.UserData;
import model.enumerations.UpdatedType;
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
    Box boxes[] = new Box[1];
    /**
     * selected name
     */
    String selectedName;
    /**
     * variable to handles all inputs from user interaction
     */
    MainController chatClient;
    /**
     * model for extracting buddy list, each buddy's information and , conversation
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

    /**
     * BuddyPanel , display friend contact list in buddy panel.
     * @param c
     * @param model
     * @param buddyWindow
     */
    //SELECTION
	//II-Constructors
    public BuddyPanel(MainController c, Model model, JFrame buddyWindow) {
        this.buddyWindow = buddyWindow;
        model.addObserver(this);
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        this.chatClient = c;
        this.model = model;
        this.chat = null;
        buddies = null;

        model.chatWindowOpen = false;

        friendList = new JPanel();
        friendList.setBackground(Color.WHITE);
        friendList.setLayout(new BorderLayout());

        // Place all friends from currentProfile into buddy list
        buddies = model.getOrderedFriendList();

        // add friends to the buddy list
        boxes[0] = Box.createVerticalBox();
        for (int i = 0; i < buddies.size(); i++) {
            boxes[0].add(FriendItem(buddies.get(i)));
        }

        for (int i = 0; i < boxes[0].getComponentCount(); i++) {
            boxes[0].getComponent(i).addMouseListener(new SelectListener());
        }

        // rightclick menu
        rightClickMenu = new JPopupMenu();
        menuItem1 = new JMenuItem("Start New Conversation");
        menuItem2 = new JMenuItem("Add to open Conversation");
        menuItem3 = new JMenuItem("Remove Friend");
        menuItem4 = new JMenuItem("Block Friend");
        menuItem5 = new JMenuItem("View Profile");

        menuItem1.addMouseListener(new RightCickMenuListener());
        menuItem2.addMouseListener(new RightCickMenuListener());
        menuItem3.addMouseListener(new RightClickMenuRemoveFriendListener());
        menuItem4.addMouseListener(new RightClickMenuBlockFriendListener());

        rightClickMenu.add(menuItem1);
        rightClickMenu.add(menuItem2);
        rightClickMenu.addSeparator();
        rightClickMenu.add(menuItem3);
        rightClickMenu.add(menuItem4);
        rightClickMenu.addSeparator();
        rightClickMenu.add(menuItem5);

        friendList.add(boxes[0], BorderLayout.NORTH);
        JScrollPane scroller = new JScrollPane(friendList);
        scroller
                .setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        options = OptionsBar();

        add(scroller, BorderLayout.CENTER);
        add(options, BorderLayout.SOUTH);
    }

    /**
     * optionsBar(), provide options tool for user to add, delete, and block friend from buddy list
     * @return options
     */
    public JToolBar OptionsBar() {
        JToolBar options = new JToolBar();

        JTextField search = new JTextField();
        JButton addF = new JButton(new ImageIcon(this.getClass().getResource(
                "/images/buddylist/add_user.png")));
        JButton removeF = new JButton(new ImageIcon(this.getClass()
                .getResource("/images/buddylist/delete_user.png")));
        JButton blockF = new JButton(new ImageIcon(this.getClass().getResource(
                "/images/buddylist/button_cancel.png")));
        JButton searchButton = new JButton(new ImageIcon(this.getClass()
                .getResource("/images/buddylist/document_preview.png")));

        // add components
        options.add(addF);
        options.add(removeF);
        options.add(blockF);
        options.add(search);
        options.add(searchButton);

        addF.addMouseListener(new addFriendListener());
        removeF.addMouseListener(new removeFriendListener());
        blockF.addMouseListener(new blockFriendListener());

        return options;
    }

    // Section
    // III - Accessors and Mutator
    /**
     * block friend listener for user to block friend
     *
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
            chatClient.blockFriend(selectedFriend.getAccountName());

            // buddies.remove(selectedFriend);

            /*
             * boxes[0].removeAll();
             * 
             * for (int i = 0; i < buddies.size(); i++) {
             * boxes[0].add(FriendItem(buddies.get(i))); }
             * 
             * for (int i = 0; i < boxes[0].getComponentCount(); i++) {
             * boxes[0].getComponent(i).addMouseListener(new SelectListener());
             * // System.out.println("What is contained the box? " + //
             * boxes[0].getComponent(i)); }
             * 
             * friendList.updateUI();
             * 
             * // model.getBannedAccountList().add(selectedFriend.toString());
             * bannedAccountList.setBannedUserList(selectedFriend.toString());
             * try { for (int i = 0; i < bannedAccountList.getBannedUserList()
             * .size(); i++) { System.out.println("Banned users = " +
             * bannedAccountList.getBannedUserList().get(i));
             * 
             * } } catch (SQLException e) { // TODO Auto-generated catch block
             * e.printStackTrace(); }
             */
        }
    }

    /**
     * for user to delete friend by right click menu bar
     *
     */
    class RightClickMenuRemoveFriendListener extends MouseAdapter {
        public void mousePressed(MouseEvent event) {

            System.out.println("Remove this user from the buddy list = "
                    + selectedFriend.toString());
            chatClient.removeFriend(selectedFriend.getAccountName());

            // weird this wasn't used in the past but it seems non-functional
            // without them
            // Please consider this code!

            buddies.remove(selectedFriend);
            boxes[0].removeAll();

            for (int i = 0; i < buddies.size(); i++) {
                boxes[0].add(FriendItem(buddies.get(i)));
            }

            for (int i = 0; i < boxes[0].getComponentCount(); i++) {
                boxes[0].getComponent(i).addMouseListener(new SelectListener());
            }

            friendList.updateUI();
        }
    }

    /**
     * remove friend listener for user to delete friend
     *
     */
    class removeFriendListener extends MouseAdapter {
        public void mousePressed(MouseEvent event) {

            if (selectedFriend != null) {
                System.out.println("Remove this user from the button = "
                        + selectedFriend.toString());

                chatClient.removeFriend(selectedFriend.getAccountName());

                for (int i = 0; i < model.getOrderedFriendList().size(); i++) {
                }

                buddies.remove(selectedFriend);
                boxes[0].removeAll();

                for (int i = 0; i < buddies.size(); i++) {
                    boxes[0].add(FriendItem(buddies.get(i)));
                }

                for (int i = 0; i < boxes[0].getComponentCount(); i++) {
                    boxes[0].getComponent(i).addMouseListener(
                            new SelectListener());
                }

                friendList.updateUI();

            }

        }
    }

    /**
     * add friend listener for user to add friend
     *
     */
    class addFriendListener extends MouseAdapter {
        /* 
         * mousePressed, display feedback when pressing mouse
         */
        public void mousePressed(MouseEvent event) {
            System.out.println("Add Friend Clicked");
            String userFriendID, userInput;
            String result = "Ay Ay Captain! One person will be invited to your Parrot IM Buddy List.";

            // not able to cancel it for now

            userFriendID = JOptionPane
                    .showInputDialog("Enter an email address: ");

            if ((userFriendID != null && !userFriendID.equals(""))
                    && !userExist(userFriendID)) {
                chatClient.addFriend(userFriendID);
                JOptionPane.showMessageDialog(null, result);

                // buddies.add(new GoogleTalkUserData(userFriendID));

                // add friends to the buddy list

                // boxes[0].add(FriendItem(buddies.get(buddies.size() - 1)));

                // boxes[0].getComponent(buddies.size() - 1).addMouseListener(
                // new SelectListener());

                // friendList.updateUI();

            }

            else {
                String redundancy = "Argh, the friend's email address you have provided is already an existing contact. Please provide a non-existing friend's email address.";
                JOptionPane.showMessageDialog(null, redundancy);

            }
            System.out.println("User Input = " + userFriendID);

        }

        /**
         * check if userID exist
         * @param userID
         * @return boolean
         */
        public boolean userExist(String userID) {
            for (int i = 0; i < buddies.size(); i++) {
                if (buddies.get(i).getAccountName().equals(userID)) {
                    return true;
                }
            }

            return false;
        }
    }

    /**
     * FriendItem to check friend status.
     * @param user
     * @return friendItem
     */
    public JPanel FriendItem(UserData user) {
        JPanel friendItem = new JPanel();
        friendItem.setLayout(new BorderLayout());
        friendItem.setBackground(Color.WHITE);

        friendItem.setName(user.getNickname());

        // end it
        friendItem.setToolTipText("Right click to see options for this item");

        JLabel friendName;

        // Colour our world! Sets colours for our friends.
        // Note: Separating the name and the status colours would be great.
        if (user.isBlocked()) {
            friendName = new JLabel("* Blocked: " + user.getAccountName()
                    + " *");
            friendName.setForeground(Color.LIGHT_GRAY.darker());
        } else if (user.getState().toString().equals("Available")) {
            friendName = new JLabel(user.getNickname() + " - "
                    + user.getStatus() + " (" + user.getState() + ")");
            friendName.setForeground(Color.GREEN.darker());
        } else if (user.getState().toString().equals("dnd")) {
            friendName = new JLabel(user.getNickname() + " - "
                    + user.getStatus() + " (Busy)");
            friendName.setForeground(Color.ORANGE.darker());
        } else if (user.getState().toString().equals("away")) {
            friendName = new JLabel(user.getNickname() + " - "
                    + user.getStatus() + " (Away)");
            friendName.setForeground(Color.ORANGE.darker());
        } else {
            friendName = new JLabel(user.getNickname() + " - "
                    + user.getStatus() + " (" + user.getState() + ")");
            friendName.setForeground(Color.RED.darker());
        }

        friendItem.add(friendName, BorderLayout.WEST);
        // friendItem.add(friendStatus,BorderLayout.CENTER);

        return friendItem;
    }

    /* 
     * update current friend status on the buddy list.
     */
    public void update(Observable o, Object arg) {
        /* If chat window has not been made, make it if message sent */
        if (arg == UpdatedType.CHAT) {
            if (chat == null) {
                // model.startConversation(selectedFriend.getFriendOf(),
                // selectedFriend);
                chat = new ChatWindow(chatClient, model);
            } else {
                System.out.println("darn");
                // add code for if multiple windows exist.
            }

        } else if (arg == UpdatedType.BUDDY) {
            boxes[0].removeAll();
            System.out.println("I'm updating");
            buddies = model.getOrderedFriendList();

            for (int i = 0; i < buddies.size(); i++) {
                boxes[0].add(FriendItem(buddies.get(i)));
            }

            for (int i = 0; i < boxes[0].getComponentCount(); i++) {
                boxes[0].getComponent(i).addMouseListener(new SelectListener());
            }
            friendList.updateUI();
        }
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

        /* 
         * mouseClicked to unhighlight the last selected
         */
        public void mouseClicked(MouseEvent event) {
            if (lastSelectedListener != null) { // unhighlight the last selected
                lastSelectedListener.whiteBackground(event);
            }

            // FriendItems
            for (int i = 0; i < boxes[0].getComponentCount(); i++) {

                if (event.getSource().equals(boxes[0].getComponent(i))) {
                    if (event.getButton() == event.BUTTON1) {
                        selected = true;
                        lastSelectedListener = this;

                        // Left Click
                        boxes[0].getComponent(i).setBackground(
                                new Color(145, 200, 200));

                        /* Fix this to directly reference the GUI */
                        selectedFriend = buddies.get(i);

                        if (event.getClickCount() == 2) {
                            selected = false;

                            /* Is the chat window already open? */
                            if (model.chatWindowOpen == false) {
                                model.startConversation(selectedFriend);
                                if (chat == null) {
                                    chat = new ChatWindow(chatClient, model);
                                }
                                model.chatWindowOpen = true;
                            } else {
                                model.startConversation(selectedFriend);
                            }
                        }
                    } else if (event.getSource().equals(
                            boxes[0].getComponent(i))) {
                        // Right Click
                        boxes[0].getComponent(i).setBackground(
                                new Color(145, 200, 200));
                        rightClickMenu.show(boxes[0].getComponent(i), event
                                .getX(), event.getY());
                        selectedName = boxes[0].getComponent(i).getName();
                        selectedFriend = buddies.get(i);
                    }
                }
            }
        }

        /* 
         * change background to color when mouse Entered
         */
        public void mouseEntered(MouseEvent event) {
            for (int i = 0; i < boxes[0].getComponentCount(); i++) {
                if (event.getSource().equals(boxes[0].getComponent(i))) {
                    boxes[0].getComponent(i).setBackground(
                            new Color(225, 247, 247));
                }
            }
        }

        /* 
         * change background to white when mouse Exited
         */
        public void mouseExited(MouseEvent event) {
            Color c;
            if (selected) {
                c = new Color(145, 200, 200);
                lastSelectedSource = event.getSource();
            } else {
                c = Color.WHITE;
            }

            for (int i = 0; i < boxes[0].getComponentCount(); i++) {
                if (event.getSource().equals(boxes[0].getComponent(i))) {
                    boxes[0].getComponent(i).setBackground(c);
                }
            }
        }

        /**
         * set Background white if last select source is not null 
         * @param event
         */
        public void whiteBackground(MouseEvent event) {
            selected = false;
            if (lastSelectedSource == null)
                return;
            else {
                for (int i = 0; i < boxes[0].getComponentCount(); i++) {
                    if (lastSelectedSource.equals(boxes[0].getComponent(i))) {
                        boxes[0].getComponent(i).setBackground(Color.WHITE);
                    }
                }
            }
        }

        /* 
         * mouse Pressed
         */
        public void mousePressed(MouseEvent e) {
        }

        /* 
         * mouse Released
         */
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
            ConversationData conversation = null;
            if (event.getSource().equals(menuItem1)) {
                /* Is the chat window already open? */
                if (model.numberOfConversations() < 1) {
                    conversation = model.startConversation(selectedFriend);
                    model.setActiveConversation(conversation);
                    if (chat == null) {
                        chat = new ChatWindow(chatClient, model);
                    }
                } else {
                    // TODO Add conversation to the window
                }
            } else if (event.getSource().equals(menuItem2)) {
                // chat.addToConversation(selectedName);
                // TODO Group chat not yet implemented.
            }

        }
    }
}