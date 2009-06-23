/* buddyPanel.java
 * 
 * Programmed By:
 *     Vera Lukman
 *     
 * Change Log:
 *     2009-June-16, VL
 *         Initial write. All data was stub, not connected yet to database.
 *         Skeleton of the GUI was provided, but not fully functioning yet.
 *     2009-June-19, VL
 *         Integrated to access the real database
 *         
 * Known Issues:
 *     1. Missing search bar.
 * 
 * Copyright (C) 2009  Pirate Captains
 * 
 * Full license can be found in ParrotIM/LICENSE.txt.
 */

package view.chatLog;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.sql.SQLException;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;

import model.Model;

public class buddyPanel extends JPanel {
    protected JList buddyList;

    public buddyPanel(Model model, String profile) {

        // list of buddies who has logged chat

        buddyList = new JList(model.getBuddyLogList(profile));

        buddyList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollBuddy = new JScrollPane(buddyList);
        scrollBuddy.setAutoscrolls(true);
        scrollBuddy
                .setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        // setting panel
        this.setLayout(new BorderLayout());
        this.setMinimumSize(new Dimension(100, this.getHeight()));
        this.add(scrollBuddy, BorderLayout.CENTER);

    }

}
