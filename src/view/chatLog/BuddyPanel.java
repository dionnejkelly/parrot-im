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
 *     2009-June-23, KF
 *         Naming convention updates. Changed all class names.
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
import java.util.Vector;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;

import model.Model;

/**
 * The panel that sets the right component of ChatLogPanel.
 *
 * This class inherits JPanel methods and variables.
 */
public class BuddyPanel extends JPanel {
	/** 
     * buddyList is a JList object. It is the left component of ChatLogPanel.
     * buddyList shows the list of buddies whom the user has talked to.
     */
    protected JList buddyList;

    /** 
     * The constructor of BuddyPanel. It takes model and currently used profile name as arguments.
     * It sets up the right component of ChatLogPanel.
     * @param model
     * @param profile
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public BuddyPanel(Model model, String profile) throws SQLException, ClassNotFoundException {

        // list of buddies who has logged chat
        buddyList = new JList(model.getBuddyLogList(profile, ""));
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
