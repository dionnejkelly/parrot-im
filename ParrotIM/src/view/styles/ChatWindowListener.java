/* chatWindowListener.java
 * 
 * Programmed By:
 * 	   William Chen
 *     Jordan Fox
 *     
 * Change Log:
 *     2009-June-12, JF
 *         Initial write.
 *     2009-June-13, WC
 *         Transferred file over to new project, ParrotIM.
 *     2009-June-23, KF
 *         Naming convention updates. Changed all class names.
 *         
 * Known Issues:
 *     None
 * 
 * Copyright (C) 2009  Pirate Captains
 * 
 * Full license can be found in ParrotIM/LICENSE.txt.
 */

package view.styles;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import model.Model;

/**
 * This object is a WindowListener for ChatWindow objects. It controls
 * chatWindowOpen variable in model class.
 * 
 * This object inherits WindowListener variables and methods.
 */
public class ChatWindowListener implements WindowListener {
    /**
     * model allows to store the state of ChatWindow (ie. whether it is opened
     * or not).
     */
    Model model;

    /**
     * ChatWindowListener takes Model object as an argument.
     * 
     * @param model
     */
    public ChatWindowListener(Model model) {
        this.model = model;
    }

    /**
     * Sets chatWindowOpen to false. It takes a WindowEvent argument.
     * 
     * @param e
     */
    public void windowClosed(WindowEvent e) {
        try {
            model.clearAllConversations();
        } catch (NullPointerException nullEvent) {
            System.out.println("No chat windows were opened");
        }

    }

    /**
     * Sets chatWindowOpen to false. It takes a WindowEvent argument.
     * 
     * @param e
     */
    public void windowClosing(WindowEvent e) {
        model.clearAllConversations();
    }

    public void windowDeactivated(WindowEvent e) {
    }

    public void windowDeiconified(WindowEvent e) {
    }

    public void windowIconified(WindowEvent e) {
    }

    public void windowOpened(WindowEvent e) {
    }

    public void windowActivated(WindowEvent e) {
    }
}
