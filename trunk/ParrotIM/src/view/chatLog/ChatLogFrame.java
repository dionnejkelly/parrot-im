/* chatLogFrame.java
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
 *     2009-June-24, VL
 *         Added chatLogWindowListener.
 *         
 * Known Issues:
 *     1. Missing search bar.
 * 
 * Copyright (C) 2009  Pirate Captains
 * 
 * Full license can be found in ParrotIM/LICENSE.txt.
 */

package view.chatLog;

import java.awt.Dimension;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import model.Model;

/**
 * The container frame of ChatLogPanel.
 * 
 * This class inherits JFrame methods and variables.
 */
public class ChatLogFrame extends JFrame {

    /**
     * model allows the ChatLogFrame to store the state of ChatLogFrame (ie.
     * whether it is opened or not).
     */
    Model model;

    /**
     * Sets the title of the window, size, and default close operation.
     * 
     * @param model
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public ChatLogFrame(Model model) throws SQLException,
            ClassNotFoundException {
        super("ParrotIM - Chat Log Viewer");
        this.model = model;
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        this.getContentPane().add(
                new ChatLogPanel(model, model.getCurrentProfile().getName()));
        this.setPreferredSize(new Dimension(700, 500));
        this.setMinimumSize(new Dimension(400, 300));
        this.pack();
        this.setVisible(true); // might want to change this
        setIconImage(new ImageIcon("src/images/mainwindow/logo.png").getImage());
    }
}
