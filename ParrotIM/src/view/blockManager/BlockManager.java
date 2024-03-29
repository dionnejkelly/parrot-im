/* BlockManager.java
 * 
 * Programmed By:
 *     Jihoon Choi
 *     
 * Change Log:
 *         
 * Known Issues:
 *     none
 *     
 * Copyright (C) 2009  Pirate Captains
 * 
 * License: GNU General Public License version 2.
 * Full license can be found in ParrotIM/LICENSE.txt.
 */

package view.blockManager;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;

import model.Model;
import model.dataType.UserData;
import model.enumerations.UpdatedType;
import view.styles.GPanel;
import controller.MainController;

/**
 * Provides auto-mated messaging capability to users in the chat window. For the
 * Alpha Version, the Chatbot is capable of handling simple queries, and respond
 * to some extent.
 */

public class BlockManager extends JFrame implements Observer {

    // Section
    // I - Protected Data Member

    /**
     * Holds the main frame of the Block Manager that is used to dispose the
     * window when its task is finished.
     */

    protected JFrame popup;

    // Section
    // II - Non-Static Data Members

    /**
     * The Block Manager main panel.
     */

    private GPanel accMANPanel;

    /**
     * Holds the integrity of our database.
     */

    private Model model;

    /**
     * Temporarily holds the integrity of user's buddylist model.
     */
    private DefaultListModel usersBuddyListModel;

    /**
     * Temporarily holds the integrity of blocked users.
     */
    private DefaultListModel blockedBuddyListModel;

    /**
     * Temporarily holds the integrity of Parrot IM user's buddylist model.
     */

    private JList usersBuddyList;

    /**
     * Temporarily holds the integrity of blocked Parrot IM user's buddylist
     * model.
     */

    private JList usersBannedBuddyList;

    /**
     * Temporarily holds the integrity of Parrot IM user's buddylist in
     * ArrayList.
     */

    private ArrayList<UserData> usersProfileBuddyList;

    /**
     * Temporarily holds the integrity of blocked Parrot IM user's buddylist in
     * ArrayList.
     */

    private Vector<UserData> bannedAccountList;

    /**
     * Maintains the Parrot IM XMPP Protocol .
     */

    private MainController chatClient;

    private JLabel usersLabel, blockedLabel;

    // Section
    // III - Constructors

    /**
     * BlockManager() connects you to the Block Manager handler. Every time you
     * want to run a BlockManager window you have to
     * "BlockManager blockManager = new BlockManager(MainController c, Model model);"
     */

    public BlockManager(MainController c, Model model) {
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.model = model;
        this.chatClient = c;
        this.usersProfileBuddyList =
                this.model.getCurrentProfile().getAllFriends();
        this.usersProfileBuddyList =
                UserData.cullBlocked(this.usersProfileBuddyList);
        this.usersProfileBuddyList =
                UserData.sortAlphabetical(this.usersProfileBuddyList);
        // this.bannedAccountList = bannedUsersList.getBannedUserList();
        this.bannedAccountList = model.getBannedUserList();

        this.model.addObserver(this);

        // mainFrame = frame;
        popup = this;

        setTitle("Block Manager");
        setLocation(100, 100);
        setPreferredSize(new Dimension(510, 360));
        setResizable(false);
        setIconImage(new ImageIcon(this.getClass().getResource(
                "/images/mainwindow/logo.png")).getImage());

        // set main panel
        accMANPanel = new GPanel();
        accMANPanel.setLayout(new BorderLayout());
        accMANPanel.setGradientColors(model.primaryColor, model.secondaryColor);

        // manage account panel
        leftPanelManager();
        rightPanelManager();

        getContentPane().add(accMANPanel);
        pack();
        setVisible(true);
    }

    /**
     * leftPanelManager() connects you with the Left Panel Block Manager handler
     * that allows users to block their friends.
     */

    private void leftPanelManager() {
        JPanel leftPanel = new JPanel();
        leftPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        leftPanel.setLayout(new BorderLayout());
        leftPanel.setOpaque(false);

        // saved account list
        usersBuddyListModel = new DefaultListModel();
        // usersProfileBuddyList = model.getCurrentProfile().getAllFriends();
        for (int i = 0; i < usersProfileBuddyList.size(); i++) {
            usersBuddyListModel.addElement(usersProfileBuddyList.get(i));
        }
        // profileList = new JList(model.getAccountList());
        usersBuddyList = new JList(usersBuddyListModel);
        usersBuddyList
                .setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);

        // TODO: ADD NEW PROFILE LIST TO MODEL

        JScrollPane listScroller = new JScrollPane(usersBuddyList);
        listScroller.setPreferredSize(new Dimension(205, 250));
        listScroller
                .setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        // add-remove button panel
        JPanel addremovePanel = new JPanel();
        addremovePanel.setOpaque(false);

        JButton blockButton =
                new JButton("Block", new ImageIcon(this.getClass().getResource(
                        "/images/buddylist/block_user.png")));
        blockButton.setPreferredSize(new Dimension(120, 25));
        // Removing Padding from buttons so text can be displayed properly
        Insets buttonInset = new Insets(0, 0, 0, 0);

        blockButton.setMargin(buttonInset);
        blockButton.addActionListener(new blockUserListener());

        // pack the whole thing
        addremovePanel.add(blockButton);

        // add to leftpanel
        usersLabel = new JLabel("Parrot IM Users");
        usersLabel.setForeground(model.primaryTextColor);
        leftPanel.add(usersLabel, BorderLayout.NORTH);
        leftPanel.add(listScroller, BorderLayout.CENTER);
        leftPanel.add(addremovePanel, BorderLayout.SOUTH);

        // add to account manager pop up main panel
        accMANPanel.add(leftPanel, BorderLayout.WEST);
    }

    /**
     * Provides Block Button Listener capability to users in the Left Panel
     * Block Manager.
     */

    class blockUserListener implements ActionListener {

        /**
         * Listens for the user's blocking event.
         * 
         * @param event
         */

        public void actionPerformed(ActionEvent event) {

            int selected = usersBuddyList.getSelectedIndex();

            if (selected != -1) {

                UserData blockedUser = usersProfileBuddyList.get(selected);
                chatClient.blockFriend(blockedUser);

            }

            else {
                String resultMessage =
                        "There is selected user to block. Please click on the user you want to block on the list.";
                JOptionPane.showMessageDialog(null, resultMessage);

            }

        }
    }

    /**
     * Provides Unblock Button Listener capability to users in the Right Panel
     * Unblock Manager.
     */

    class unBlockUserListener implements ActionListener {

        /**
         * Listens for the user's unblocking event.
         * 
         * @param event
         */

        public void actionPerformed(ActionEvent event) {

            int selected = usersBannedBuddyList.getSelectedIndex();

            if (selected != -1) {
                UserData unBlockedUser = bannedAccountList.get(selected);
                chatClient.unblockFriend(unBlockedUser);

            }

            else {
                String resultMessage =
                        "There is selected user to unblock. Please click on the user you want to block on the list.";
                JOptionPane.showMessageDialog(null, resultMessage);
            }

        }
    }

    /**
     * rightPanelManager() connects you with the Right Panel Unblock Manager
     * handler that allows users to unblock their friends.
     */

    private void rightPanelManager() {
        // setting right panel
        JPanel rightPanel = new JPanel();
        rightPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        rightPanel.setLayout(new BorderLayout());
        rightPanel.setOpaque(false);

        /* TOP PART */
        // List of accounts on the profile
        JPanel topRight = new JPanel();
        topRight.setLayout(new BorderLayout());
        topRight.setOpaque(false);

        blockedBuddyListModel = new DefaultListModel();
        for (int i = 0; i < bannedAccountList.size(); i++) {
            blockedBuddyListModel.addElement(bannedAccountList.get(i));
        }

        usersBannedBuddyList = new JList(blockedBuddyListModel);
        usersBannedBuddyList
                .setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);

        JScrollPane acctListScroller = new JScrollPane(usersBannedBuddyList);
        acctListScroller.setPreferredSize(new Dimension(250, 210));
        acctListScroller
                .setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        /* CENTRE PART : Add/Remove Buttons */

        JPanel addRemoveAcctPanel = new JPanel();
        addRemoveAcctPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        addRemoveAcctPanel.setOpaque(false);

        Insets buttonInset = new Insets(0, 0, 0, 0);
        JButton unBlockButton =
                new JButton("Unblock", new ImageIcon(this.getClass()
                        .getResource("/images/buddylist/unblock_user.png")));
        unBlockButton.setPreferredSize(new Dimension(120, 25));
        unBlockButton.setMargin(buttonInset);

        addRemoveAcctPanel.add(unBlockButton);
        unBlockButton.addActionListener(new unBlockUserListener());

        // Piece together Right Side Panel and Add Border
        topRight.add(acctListScroller, BorderLayout.NORTH);
        topRight.add(addRemoveAcctPanel, BorderLayout.CENTER);

        /* BOTTOM PART : OK and Cancel Button */
        // set ok-cancel button
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setOpaque(false);
        buttonsPanel
                .setBorder(BorderFactory.createEmptyBorder(10, 120, 10, 10));
        GridLayout buttonsLayout = new GridLayout(1, 2);
        buttonsLayout.setHgap(5);
        buttonsPanel.setLayout(buttonsLayout);

        // OK and CANCEL Buttons
        JButton okButton =
                new JButton("Done", new ImageIcon(this.getClass().getResource(
                        "/images/buddylist/button_ok.png")));
        buttonsPanel.add(okButton);
        okButton.addMouseListener(new okCancelButtonListener());

        // adding to rightPanel
        blockedLabel = new JLabel("Blocked Parrot IM Accounts");
        blockedLabel.setForeground(model.primaryTextColor);
        rightPanel.add(blockedLabel, BorderLayout.NORTH);
        rightPanel.add(topRight, BorderLayout.CENTER);
        rightPanel.add(buttonsPanel, BorderLayout.SOUTH);

        // add to account manager pop up main panel
        accMANPanel.add(rightPanel, BorderLayout.EAST);
    }

    /**
     * Provides OK Button Listener capability to users in the Block Manager.
     */

    class okCancelButtonListener extends MouseAdapter {

        /**
         * Listens for the user's mouse click event.
         * 
         * @param event
         */

        public void mousePressed(MouseEvent event) {
            popup.removeAll();
            popup.dispose();
        }
    }

    public void update(Observable o, Object arg) {
        if (arg == UpdatedType.BUDDY_BLOCK_MANAGER) {

            // Refresh the lists after a change
            this.usersProfileBuddyList =
                    this.model.getCurrentProfile().getAllFriends();
            this.usersProfileBuddyList =
                    UserData.cullBlocked(this.usersProfileBuddyList);
            this.usersProfileBuddyList =
                    UserData.sortAlphabetical(this.usersProfileBuddyList);
            this.bannedAccountList = model.getBannedUserList();

            usersBuddyListModel.removeAllElements();
            for (int i = 0; i < usersProfileBuddyList.size(); i++) {
                usersBuddyListModel.addElement(usersProfileBuddyList.get(i));
            }

            blockedBuddyListModel.removeAllElements();
            for (int i = 0; i < bannedAccountList.size(); i++) {
                blockedBuddyListModel.addElement(bannedAccountList.get(i));
            }

            return;
        } else if (arg == UpdatedType.COLOR) {
            usersLabel.setForeground(model.primaryTextColor);
            blockedLabel.setForeground(model.primaryTextColor);
            usersLabel.updateUI();
            blockedLabel.updateUI();

            accMANPanel.setGradientColors(model.primaryColor,
                    model.secondaryColor);
            accMANPanel.updateUI();
        }
    }
}
