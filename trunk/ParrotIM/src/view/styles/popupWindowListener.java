/* popupWindowListener.java
 * 
 * Programmed By:
 * 	   Vera Lukman
 *     Jordan Fox
 * 	   William Chen
 *     
 * Change Log:
 *     2009-June-8, VL
 *         Initial write.
 *     2009-June-8, JC
 *     	   Fixed windowClosed()
 *     2009-June-13, WC
 *         Transferred file over to new project, ParrotIM.
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

import javax.swing.JFrame;

import view.mainwindow.mainwindow;

public class popupWindowListener implements WindowListener {
	mainwindow mainFrame;
	JFrame popup;
	
	public popupWindowListener(JFrame frame, JFrame popup){
		mainFrame = (mainwindow)frame;
		this.popup = popup;
	}

	public void windowClosed(WindowEvent e) {
		mainFrame.setEnabled(true);
		mainFrame.setAlwaysOnTop(true);
	    mainFrame.setAlwaysOnTop(false);
	}

	public void windowClosing(WindowEvent e) {}
	public void windowDeactivated(WindowEvent e) {}
	public void windowDeiconified(WindowEvent e) {}
	public void windowIconified(WindowEvent e) {}
	public void windowOpened(WindowEvent e) {}
	public void windowActivated(WindowEvent e) {}
}
