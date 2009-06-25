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

import controller.MainController;

import model.*;
import model.dataType.ConversationData;
import model.enumerations.UpdatedType;

import java.util.*;

public class SidePanel extends JPanel implements Observer {
    private JPanel chattingWith;
    private Box boxes[] = new Box[1];
    private Model model;
    private JTree tree;
    private MainController c;
    private DefaultMutableTreeNode top;

    public SidePanel(MainController c, Model model) {
        this.model = model;
        this.c = c;
        this.model.addObserver(this);

        //Panel Properties
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(150, 500));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        // Tree preferences
        DefaultMutableTreeNode person1 = null;
        DefaultMutableTreeNode person2 = null;
        for (ConversationData cd : model.getConversations()) {
            top = new DefaultMutableTreeNode("Root");
            tree = new JTree(top);

            refreshTree();

            tree.addMouseListener(new SelectListener());
        }
        this.add(tree, BorderLayout.CENTER);
    }

    private void refreshTree() {
        // sets Tree Icons
        ImageIcon leafIcon =
                new ImageIcon(this.getClass().getResource(
                        "/images/chatwindow/personal.png"));
        if (leafIcon != null) {
            DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();
            renderer.setLeafIcon(leafIcon);
            tree.setCellRenderer(renderer);
        }

        top.removeAllChildren();
        for (ConversationData cd1 : model.getConversations()) {
            top.add(new DefaultMutableTreeNode(cd1.getUser().getNickname()));
            tree.expandRow(1);
            tree.setRootVisible(false);

            tree.expandRow(0);

            // refreshes the tree
            tree.updateUI();
        }
    }

    public void update(Observable t, Object o) {
        if (o == UpdatedType.CHAT || o == UpdatedType.ALL) {
            // Temporary fix for early update bug
            if (tree != null) {
                refreshTree();
            }
        }
        return;
    }

    private class SelectListener implements MouseListener {
        public void mousePressed(MouseEvent event) {
            // int selRow = tree.getRowForLocation(event.getX(), event.getY());
            TreePath selPath =
                    tree.getPathForLocation(event.getX(), event.getY());

            // System.out.println("in sidePanel" +
            // selPath.getLastPathComponent().toString());

            // Temporary code to try and fix null bug
            if (selPath != null) {
                c.changeConversation(selPath.getLastPathComponent().toString());
                System.out.println("TESTING: Probably changing conversation");
            } else {
                System.out.println("TESTING: selPath is null");
            }

        }

        public void mouseEntered(MouseEvent event) {
        }

        public void mouseExited(MouseEvent event) {
        }

        public void mouseClicked(MouseEvent event) {
        }

        public void mouseReleased(MouseEvent event) {
        }
    }
}