/* SidePanel.java
 *  
 * Copyright (C) 2009  Pirate Captains
 * 
 * License: GNU General Public License version 2.
 * Full license can be found in ParrotIM/LICENSE.txt.
 */

package view.chatwindow;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import model.Model;
import model.dataType.ChatCollectionData;
import model.dataType.Conversation;
import model.enumerations.UpdatedType;

import org.jivesoftware.smack.XMPPException;

import view.styles.CustomListPane;
import view.styles.GPanel;
import controller.MainController;

/**
 * The SidePanel contains the list of users that are currently visible in chat
 * window .
 * 
 * This object inherits from JPanel
 */

public class SidePanel extends GPanel implements Observer {


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
    
    private Conversation conversationToRemove;     

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
        listPane.opaque = false;

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

        for (Conversation c : this.chatCollection.getVisibleConversations()) {
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
        if (t instanceof ChatCollectionData) {
            try {
                addNewConversationsToList();
                removeClosedConversations();
            } catch (XMPPException e) {
                e.printStackTrace();
            }
        }

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
        	if (event.getButton() == MouseEvent.BUTTON3 || (event.isControlDown() == true && event.getButton() == MouseEvent.BUTTON1)) {
                conversationToRemove = listPane.getUserWrapper(
                        listPane.getClickedIndex()).getConversation();
                rightClickMenu.show(listPane, event.getX() + 5, event.getYOnScreen());
                rightClickMenu.setLocation(event.getXOnScreen(), event.getYOnScreen());
        	}   else if (event.getButton() == MouseEvent.BUTTON1) {
                controller.setTypingState(1); // set to the default typing state
                // before
                rightClickMenu.setVisible(false);
                // switching
                controller.changeConversation(listPane.getUserWrapper(
                        listPane.getClickedIndex()).getConversation());
                
            } 
            return;
        }

        // Unimplemented MouseListener Methods
        public void mouseEntered(MouseEvent event) {}
        public void mouseExited(MouseEvent event) {}
        public void mouseClicked(MouseEvent event) {}
        public void mouseReleased(MouseEvent event) {}
    }

    private class removeConversationListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (chatCollection.getConversations().size() > 1) {
                chatCollection.removeConversation(conversationToRemove);
            }

            return;
        }
    }

}