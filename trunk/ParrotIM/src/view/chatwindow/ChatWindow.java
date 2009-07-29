/* ChatWindow.java
 *  
 * Copyright (C) 2009  Pirate Captains
 * 
 * License: GNU General Public License version 2.
 * Full license can be found in ParrotIM/LICENSE.txt.
 */

package view.chatwindow;

import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

import model.Model;
import model.enumerations.UpdatedType;
import view.buddylist.BuddyList;
import view.styles.ChatWindowListener;
import controller.MainController;

/**
 * The ChatWindow contains the frame that holds the integrity of the chat
 * window.
 * 
 * This object inherits from JFrame
 */

public class ChatWindow extends JFrame implements Observer {

    /** A main panel. */

    private MainPanel main;

    /**
     * Model stores the needed data of the system. It also connects it with
     * database.
     */

    private Model model;

    /** A variable that checks if the window is open. */
    private boolean windowIsOpen;

    /**
     * This is the constructor of the ChatWindow.
     * 
     * @param c
     * @param model
     */
    public ChatWindow(MainController c, Model model, BuddyList buddyFrame) {
        super("Chat Window");

        model.addObserver(this);

        this.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);

        this.model = model;

        setIconImage(new ImageIcon(this.getClass().getResource(
                "/images/mainwindow/logo.png")).getImage());

        this.addWindowListener(new ChatWindowListener(this.model));

        main = new MainPanel(c, model, this, buddyFrame);// ////////////////////

        getContentPane().add(main);

        setLocation(325, 0);
        pack();
        setVisible(false);
    }

    // Getters

    /**
     * Check if the window is open.
     * 
     * @return boolean
     */

    public boolean getWindowIsOpen() {
        return this.windowIsOpen;
    }

    // Setters

    /**
     * Sets the window's open status.
     * 
     * @param isOpen
     */

    public void setWindowIsOpen(boolean isOpen) {
        this.windowIsOpen = isOpen;
    }

    // Update Method

    /**
     * Update according to the UpdatedType.
     * 
     * @param o
     * @param arg
     */
    public void update(Observable o, Object arg) {
        if (arg == UpdatedType.CHAT) {
        	this.setAlwaysOnTop(true);
            this.setAlwaysOnTop(false);
            if (model.numberOfConversations() > 0 && !this.isVisible()) {
                this.setVisible(true);
            }
        } else if (arg == UpdatedType.COLOR) {
            main.side.listPane.setGradientColors(model.primaryColor,
                    model.secondaryColor);
            main.chat.setGradientColors(model.primaryColor,
                    model.secondaryColor);
            main.updateUI();
        }
        return;
    }
}
