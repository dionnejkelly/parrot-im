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
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.SQLException;

import javax.swing.JFrame;

import model.Model;

public class ChatLogFrame extends JFrame {
	Model model;
	
    public ChatLogFrame(Model model) throws SQLException, ClassNotFoundException {
        super("ParrotIM - Chat Log Viewer");
    	this.model = model;
    	this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        this.getContentPane().add(
                new ChatLogPanel(model, model.getCurrentProfile()
                        .getProfileName()));
        this.setPreferredSize(new Dimension(700, 500));
        this.setMinimumSize(new Dimension(400, 300));
        this.pack();
        this.setVisible(true); // might want to change this
        this.addWindowListener(new chatLogWindowListener());
    }
    
    private class chatLogWindowListener implements WindowListener{

		public void windowActivated(WindowEvent e) {}

		public void windowClosed(WindowEvent e) {
			model.logWindowOpen = false;
		}

		public void windowClosing(WindowEvent e) {}
		public void windowDeactivated(WindowEvent e) {}
		public void windowDeiconified(WindowEvent e) {}
		public void windowIconified(WindowEvent e) {}
		
		public void windowOpened(WindowEvent e) {
			model.logWindowOpen = true;
		}
    	
    }
}
