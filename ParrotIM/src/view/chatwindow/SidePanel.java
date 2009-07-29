/* SidePanel.java
 *  
 * Copyright (C) 2009  Pirate Captains
 * 
 * License: GNU General Public License version 2.
 * Full license can be found in ParrotIM/LICENSE.txt.
 */

package view.chatwindow;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;

import org.jivesoftware.smack.XMPPException;

import view.mainwindow.HelpPanel;
import view.styles.CustomListPane;
import view.styles.GPanel;

import controller.MainController;

import model.*;
import model.dataType.ChatCollectionData;
import model.dataType.Conversation;
import model.enumerations.UpdatedType;

import java.util.*;

/**
 * The SidePanel contains the list of users that are currently visible in chat
 * window .
 * 
 * This object inherits from JPanel
 */

public class SidePanel extends GPanel implements Observer {

    // private JPanel chattingWith;
    // private Box boxes[] = new Box[1];

    /**
     * Model stores the needed data of the system. It also connects it with
     * database.
     */

    private Model model;

    /** The List Pane that stores the users you are currently talking to */

    public CustomListPane listPane;

    private JPopupMenu rightClickMenu;

    /**
     * Maintains the Parrot IM XMPP Protocol.
     */

    private MainController controller;

    private ArrayList<UserDataWrapper> users;

    private ChatCollectionData chatCollection;

    /**
     * This is the constructor of the SidePanel.
     * 
     * @param c
     * @param model
     */

    public SidePanel(MainController controller, Model model) {
        this.model = model;
        this.controller = controller;
        this.model.addObserver(this);
        this.model.getChatCollection().addObserver(this);
        this.users = new ArrayList<UserDataWrapper>();
        this.chatCollection = this.model.getChatCollection();

        // Panel Properties
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(150, 500));
        setGradientColors(Color.BLACK, Color.WHITE);

        // List preferences
        listPane = new CustomListPane();
        listPane.setGradientColors(model.primaryColor, model.secondaryColor);
        listPane.textColor = model.primaryTextColor;

        // rightclick menu
        rightClickMenu = new JPopupMenu();

        JMenuItem menuItem4 =
                new JMenuItem("Remove Conversation", new ImageIcon(this
                        .getClass().getResource(
                                "/images/buddylist/delete_user.png")));
        menuItem4.addActionListener(new removeConversationListener());
        rightClickMenu.add(menuItem4);

        this.add(listPane.getWithScroller(), BorderLayout.CENTER);
    }

    /**
     * Adds new conversations to the list when the list is refreshed
     * 
     * @throws XMPPException
     * 
     */
    private void addNewConversationsToList() throws XMPPException {
        UserDataWrapper userWrapper = null;

        // int loopIterationNumber = 0;
        // for (ConversationData cd1 : model.getConversations()) {
        for (Conversation c : this.chatCollection.getVisibleConversations()) {
            // if (loopIterationNumber > listPane.getNicknameList().size() - 1)
            // {
            userWrapper = null;
            for (UserDataWrapper u : this.users) {
                if (u.getConversation().getUser() != null) {
                    u.getConversation().getUser().getTypingState();
                }

                if (u.getConversation() == c) {
                    userWrapper = u;
                    break;
                }
            }
            if (userWrapper == null) {
                userWrapper = new UserDataWrapper(c, this.model);
                this.users.add(userWrapper);
            }

            if (!listPane.sidePanelUserExists(userWrapper)) {
                ImageIcon leafIcon = controller.getAvatarPicture(c.getUser());
                listPane.addElement(userWrapper.toString(), leafIcon,
                        userWrapper, new SelectListener());
            }

            // }
            // loopIterationNumber++;
        }

        // refreshes the list on the screen with the new data
        listPane.updateUI();
    }

    private void removeClosedConversations() {
        UserDataWrapper foundUser = null;
        ArrayList<UserDataWrapper> usersToRemove =
                new ArrayList<UserDataWrapper>();

        // Remove from the sidePanel, and make a list
        for (UserDataWrapper u : this.users) {
            foundUser = null;
            for (Conversation c : this.chatCollection.getVisibleConversations()) {
                if (c == u.getConversation()) {
                    foundUser = u;
                    break;
                }
            }
            if (foundUser == null) {
                foundUser = u;
                listPane.removeSidePanelUser(foundUser);
                usersToRemove.add(u);
            }
        }

        // Remove the users from the list
        for (UserDataWrapper u : usersToRemove) {
            this.users.remove(u);
        }

        listPane.updateUI();

        return;
    }

    /**
     * Update according to the UpdatedType.
     * 
     * @param t
     * @param o
     */

    public void update(Observable t, Object o) {
        // if ((o == UpdatedType.CHAT && o != UpdatedType.CHATNOTSIDEPANEL)
        // || o == UpdatedType.CHAT_STATE) {
        if (t instanceof ChatCollectionData) {
            try {
                addNewConversationsToList();
                removeClosedConversations();
            } catch (XMPPException e) {
                e.printStackTrace();
            }
        }
        // }

        if (o == UpdatedType.COLOR) {
            listPane.textColor = model.primaryTextColor;
            listPane.updateTextColor(model.primaryTextColor);
        }

        return;
    }

    /**
     * Listens for the tree's add button.
     * 
     */

    private class SelectListener implements MouseListener {

        /**
         * Check for user's action
         * 
         * @param event
         */
        public void mousePressed(MouseEvent event) {
            if (event.getButton() == event.BUTTON1) {
                System.out.println("left clicked");
                controller.setTypingState(1); // set to the default typing state
                // before
                rightClickMenu.setVisible(false);
                // switching
                controller.changeConversation(listPane.getUserWrapper(
                        listPane.getClickedIndex()).getConversation());
            } else if (event.getButton() == event.BUTTON3) {
                System.out.println("right clicked");

                // rightClickMenu.setVisible(true);
                rightClickMenu.show(listPane, event.getX(), event.getY());
            }
            return;
        }

        /**
         * Unimplemented MouseListener Methods
         */
        public void mouseEntered(MouseEvent event) {

        }

        public void mouseExited(MouseEvent event) {
        }

        public void mouseClicked(MouseEvent event) {
        }

        public void mouseReleased(MouseEvent event) {
        }
    }

    private class removeConversationListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (chatCollection.getConversations().size() > 1) {
                chatCollection.removeConversation(listPane.getUserWrapper(
                        listPane.getClickedIndex()).getConversation());
            }

            return;
        }
    }

}