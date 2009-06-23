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

import javax.swing.JFrame;

import model.Model;

public class chatLogFrame extends JFrame {

    public chatLogFrame(Model model) {
        super("ParrotIM - Chat Log Viewer");

        this.getContentPane().add(
                new chatLogPanel(model, model.getCurrentProfile()
                        .getProfileName()));
        this.setPreferredSize(new Dimension(700, 500));
        this.setMinimumSize(new Dimension(400, 300));
        this.pack();
        this.setVisible(true); // might want to change this
    }
}
