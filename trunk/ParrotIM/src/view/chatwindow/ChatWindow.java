/* ChatWindow.java
 *  
 * Copyright (C) 2009  Pirate Captains
 * 
 * License: GNU General Public License version 2.
 * Full license can be found in ParrotIM/LICENSE.txt.
 */

package view.chatwindow;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import controller.MainController;

import java.util.Observable;
import java.util.Observer;

import view.styles.ChatWindowListener;

import model.Model;
import model.enumerations.UpdatedType;

/**
 * The ChatWindow contains the frame that holds the integrity of the chat
 * window.
 * 
 * This object inherits from JFrame
 */

public class ChatWindow extends JFrame implements Observer {

    /** A main panel. */

    private JPanel main;

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
    public ChatWindow(MainController c, Model model) {
        super("Chat Window");

        model.addObserver(this);

        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        this.model = model;

        setIconImage(new ImageIcon(this.getClass().getResource(
                "/images/mainwindow/logo.png")).getImage());

        this.addWindowListener(new ChatWindowListener(this.model));

        main = new MainPanel(c, model);

        getContentPane().add(main);

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
            if (model.numberOfConversations() > 0 && !this.isVisible()) {
                this.setVisible(true);
            }
        }
        return;
    }
}
