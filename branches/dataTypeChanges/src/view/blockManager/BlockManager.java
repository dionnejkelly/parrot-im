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
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import controller.MainController;

import model.Model;
import model.dataType.UserData;
import model.enumerations.UpdatedType;

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

    private JPanel accMANPanel;

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
        this.usersProfileBuddyList = this.model.getCurrentProfile().getAllFriends();
        this.usersProfileBuddyList = UserData.cullBlocked(this.usersProfileBuddyList);
        this.usersProfileBuddyList = UserData.sortAlphabetical(this.usersProfileBuddyList);
        // this.bannedAccountList = bannedUsersList.getBannedUserList();
        this.bannedAccountList = model.getBannedUserList();

        this.model.addObserver(this);

        // mainFrame = frame;
        popup = this;

        setTitle("Block Manager");
        setLocation(100, 100);
        setPreferredSize(new Dimension(500, 350));
        setResizable(false);
        setIconImage(new ImageIcon("src/images/mainwindow/logo.png").getImage());

        // set main panel
        accMANPanel = new JPanel();
        accMANPanel.setLayout(new BorderLayout());
        // accMANPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20,
        // 30));
        // manage account panel
        // TODO: split them into different panels
        leftPanelManager();
        rightPanelManager();

        getContentPane().add(accMANPanel);
        pack();
        setVisible(true);
        setIconImage(new ImageIcon("src/images/mainwindow/logo.png").getImage());
    }

    /**
     * leftPanelManager() connects you with the Left Panel Block Manager handler
     * that allows users to block their friends.
     */

    private void leftPanelManager() {
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout());

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

        JButton blockButton = new JButton("Block");
        blockButton.setPreferredSize(new Dimension(120, 25));
        // Removing Padding from buttons so text can be displayed properly
        Insets buttonInset = new Insets(0, 0, 0, 0);

        blockButton.setMargin(buttonInset);
        blockButton.addActionListener(new blockUserListener());

        // pack the whole thing
        addremovePanel.add(blockButton);

        // add to leftpanel
        leftPanel.add(listScroller, BorderLayout.NORTH);
        leftPanel.add(addremovePanel, BorderLayout.SOUTH);
        TitledBorder profTitle;
        profTitle =
                BorderFactory.createTitledBorder(
                        BorderFactory.createEtchedBorder(EtchedBorder.LOWERED),
                        "Parrot IM Users");
        profTitle.setTitleJustification(TitledBorder.CENTER);
        leftPanel.setBorder(profTitle);

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

            System.out.println("What are you clicking = " + selected);

            if (selected != -1) {

                UserData blockedUser = usersProfileBuddyList.get(selected);

                System.out.println("Blocked user = " + blockedUser);

                chatClient.blockFriend(blockedUser);
                // usersBuddyListModel.remove(selected);
                // usersBuddyList.updateUI();

                // bannedAccountList.add(blockedUser);
                // usersBannedBuddyList.updateUI();

            }

            else {
                String resultMessage =
                        "Sorry for the inconvenience but there is no one to block. Please click on the users you want to block on the list. Thank you for your co-operation.";
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

                System.out.println("Unblocked user = " + unBlockedUser);

                chatClient.unblockFriend(unBlockedUser);
                // bannedAccountList.remove(selected);
                // usersBannedBuddyList.updateUI();

                // usersProfileBuddyList.add(unBlockedUser);
                // usersBuddyListModel.addElement(unBlockedUser);
                // usersBuddyList.updateUI();

            }

            else {
                String resultMessage =
                        "Sorry for the inconvenience but there is no one to unblock. Please click on the users you want to unblock on the list. Thank you for your co-operation.";
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
        rightPanel.setLayout(new BorderLayout());

        /* TOP PART */
        // List of accounts on the profile
        JPanel topRight = new JPanel();
        topRight.setLayout(new BorderLayout());
        // bannedAccountList = bannedUsersList.getBannedUserList();

        blockedBuddyListModel = new DefaultListModel();
        // usersProfileBuddyList = model.getCurrentProfile().getAllFriends();
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
        // addRemoveAcctPanel.setBorder(BorderFactory.createEmptyBorder(5, 12,
        // 0, 12));

        Insets buttonInset = new Insets(0, 0, 0, 0);
        JButton unBlockButton = new JButton("Unblock");
        unBlockButton.setPreferredSize(new Dimension(120, 25));
        unBlockButton.setMargin(buttonInset);

        addRemoveAcctPanel.add(unBlockButton);
        unBlockButton.addActionListener(new unBlockUserListener());

        // Piece together Right Side Panel and Add Border
        topRight.add(acctListScroller, BorderLayout.NORTH);
        topRight.add(addRemoveAcctPanel, BorderLayout.CENTER);
        TitledBorder acctTitle;
        acctTitle =
                BorderFactory.createTitledBorder(
                        BorderFactory.createEtchedBorder(EtchedBorder.LOWERED),
                        "Blocked Parrot IM Accounts");
        acctTitle.setTitleJustification(TitledBorder.CENTER);
        topRight.setBorder(acctTitle);

        /* BOTTOM PART : OK and Cancel Button */
        // set ok-cancel button
        JPanel buttonsPanel = new JPanel();
        buttonsPanel
                .setBorder(BorderFactory.createEmptyBorder(10, 120, 10, 10));
        GridLayout buttonsLayout = new GridLayout(1, 2);
        buttonsLayout.setHgap(5);
        buttonsPanel.setLayout(buttonsLayout);

        // OK and CANCEL Buttons
        JButton okButton = new JButton("OK");
        buttonsPanel.add(okButton);
        JButton cancelButton = new JButton("Cancel");

        okButton.addMouseListener(new okCancelButtonListener());
        cancelButton.addMouseListener(new okCancelButtonListener());

        buttonsPanel.add(cancelButton);

        // adding to rightPanel
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
        if (arg == UpdatedType.BUDDY) {

            // Refresh the lists after a change
            this.usersProfileBuddyList = this.model.getCurrentProfile().getAllFriends();
            this.usersProfileBuddyList = UserData.cullBlocked(this.usersProfileBuddyList);
            this.usersProfileBuddyList = UserData.sortAlphabetical(this.usersProfileBuddyList);
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
        }
    }
}
