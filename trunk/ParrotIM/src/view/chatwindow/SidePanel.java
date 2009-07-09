/* SidePanel.java
 *  
 * Copyright (C) 2009  Pirate Captains
 * 
 * License: GNU General Public License version 2.
 * Full license can be found in ParrotIM/LICENSE.txt.
 */

package view.chatwindow;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreePath;

import org.jivesoftware.smack.XMPPException;

import view.styles.CustomListPane;

import controller.MainController;

import model.*;
import model.dataType.ConversationData;
import model.dataType.UserData;
import model.enumerations.UpdatedType;

import java.util.*;

/**
 * The SidePanel contains the list of users that are currently visible in chat
 * window .
 * 
 * This object inherits from JPanel
 */

public class SidePanel extends JPanel implements Observer {

    // private JPanel chattingWith;
    // private Box boxes[] = new Box[1];

    /**
     * Model stores the needed data of the system. It also connects it with
     * database.
     */

    private Model model;

    /** Tree stores the users as a leaf. */

    private JTree tree;

    /** The List Pane that stores the users you are currently talking to*/
    
    private CustomListPane listPane;
    
    /**
     * Maintains the Parrot IM XMPP Protocol.
     */

    private MainController c;

    /** The default top node. */
    private DefaultMutableTreeNode top;

    private ArrayList<UserDataWrapper> users;

    /**
     * This is the constructor of the SidePanel.
     * 
     * @param c
     * @param model
     */

    public SidePanel(MainController c, Model model) {
        this.model = model;
        this.c = c;
        this.model.addObserver(this);
        this.users = new ArrayList<UserDataWrapper>();

        // Panel Properties
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(150, 500));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        // List preferences
        listPane = new CustomListPane();
        //listPane.addMouseListener(new SelectListener());
        
        // Tree preferences
        top = new DefaultMutableTreeNode("Root");
        tree = new JTree(top);
        //tree.addMouseListener(new SelectListener());

        this.add(listPane, BorderLayout.CENTER);
    }

    /**
     * Refreshes the tree.
     * 
     * @throws XMPPException
     * 
     */
    private void refreshTree() throws XMPPException {
        /*
         * Initializations This is where the renderer is re-initialized and then
         * added to the tree. This only needs to be called once for it will
         * affect the preformance of the program and cause mysterious
         * exceptions.
         */
        UserDataWrapper userWrapper = null;
        
        //CustomIconRenderer renderer = new CustomIconRenderer();
        //tree.setCellRenderer(renderer);

        // removes all of the old elements from the tree before refreshing the
        // tree
        top.removeAllChildren();

        for (ConversationData cd1 : model.getConversations()) {
            /*
             * Adding the nodes to the tree This loop is for adding the nodes to
             * the the tree. And another actions that need to be preformed once
             * for each node in the tree. NOTE: Please to not put functions into
             * this loop that only need to be run once. They will cause strange
             * exceptions.
             */

            userWrapper = null;
            for (UserDataWrapper u : this.users) {
            	u.getConversation().getUser().getTypingState();
                if (u.getConversation() == cd1) {
                    userWrapper = u;
                    break;
                }
            }
            if (userWrapper == null) {
                userWrapper = new UserDataWrapper(cd1, this.model);
                this.users.add(userWrapper);
            }
            
            top.add(new DefaultMutableTreeNode(userWrapper));

            ImageIcon leafIcon = c.getAvatarPicture(cd1.getUser().getUserID());
            //if (leafIcon != null) {
            //    renderer.setUserAvatar(userWrapper.toString(), leafIcon);
            //}
            
            if(!listPane.nicknameListContains(userWrapper.toString())){
            	listPane.addElement(userWrapper.toString(), leafIcon, userWrapper, new SelectListener());
            }
            
            System.out.println(userWrapper.toString() + " added to the sidepanel");
        }
        
        listPane.updateUI();

        /*
         * Side Panel Tree Properties These are the properties of the tree that
         * modify certain aspects of the tree after all of the nodes have been
         * added to it. Note: These functions only need to be called once after
         * all of the nodes are added. calling these multiple times will only
         * slow down the refreshing of the tree and cause strange exceptions.
         */
        //tree.expandRow(1);
        //tree.setRootVisible(false);
        //tree.expandRow(0);

        // refreshes the tree
        //tree.updateUI();
    }

    /**
     * Update according to the UpdatedType.
     * 
     * @param t
     * @param o
     */

    public void update(Observable t, Object o) {
        if ((o == UpdatedType.CHAT && o != UpdatedType.CHATNOTSIDEPANEL)
        		||o == UpdatedType.CHAT_STATE) {
            // Temporary fix for early update bug
            if (tree != null) {
                try {
                    refreshTree();
                } catch (XMPPException e) {
                    e.printStackTrace();
                }
            }
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
        	/*
            TreePath selPath =
                    tree.getPathForLocation(event.getX(), event.getY());
            UserData user = null;

            // Temporary code to try and fix null bug
            if (selPath != null
                    && selPath.getLastPathComponent() instanceof DefaultMutableTreeNode) {
            	c.setTypingState(1); //set to the default typing state before switching
            	c.changeConversation(((UserDataWrapper) ((DefaultMutableTreeNode) selPath
                        .getLastPathComponent()).getUserObject()).getConversation());
            }
            */
            
        	System.out.println("Box: " + listPane.getClickedIndex() + "Clicked");
        	c.setTypingState(1); //set to the default typing state before switching
            c.changeConversation(listPane.getUserWrapper(listPane.getClickedIndex()).getConversation());
            
            //listPane.getComponent(listPane.getClickedIndex()).setBackground(Color.GREEN);
            
            return;
        }

        /**
         * Unimplemented MouseListener Methods
         */
        public void mouseEntered(MouseEvent event) {}
        public void mouseExited(MouseEvent event) {}
        public void mouseClicked(MouseEvent event) {}
        public void mouseReleased(MouseEvent event) {}
    }
}