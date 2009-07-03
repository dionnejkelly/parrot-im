/* PopupWindowListener.java
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
 *     2009-June-24, VL
 *         Edited windowOpened()
 *     2009-June-24, KF
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

import javax.swing.JFrame;


/**
 * This object is a WindowListener for popup window objects.
 * It will disable the calling object and set enable the calling object when it's closed.
 * 
 * It inherits WindowListener methods and variables, and implements WindowListener methods.
 */
public class PopupWindowListener implements WindowListener {
	/**mainFrame is a JFrame object that calls the popup window.*/
	JFrame mainFrame;
	/**popup is a JFrame object that is called (by mainFrame)*/
	JFrame popup;
	
	/**
	 * PopupWindowListener takes frame and popup as arguments.
	 * frame is a JFrame object that calls the popup window.
	 * popup is a JFrame object that is called (by frame).
	 * @param frame
	 * @param popup
	 */
	public PopupWindowListener(JFrame frame, JFrame popup){
		mainFrame = frame;
		this.popup = popup;
		this.popup.setAlwaysOnTop(true);
		this.popup.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	/**mainFrame is enabled when the popup window is closed.
	 * It takes a WindowEvent argument.
	 * @param e */
	public void windowClosed(WindowEvent e) {
		mainFrame.setEnabled(true);
		mainFrame.setAlwaysOnTop(true);
	    mainFrame.setAlwaysOnTop(false);
	}

	public void windowClosing(WindowEvent e) {}
	public void windowDeactivated(WindowEvent e) {}
	public void windowDeiconified(WindowEvent e) {}
	public void windowIconified(WindowEvent e) {}
	
	/** mainFrame is disabled when the popup window is opened.
	 * It takes a WindowEvent argument.
	 * @param e */
	public void windowOpened(WindowEvent e) {
		mainFrame.setEnabled(false);
	}
	public void windowActivated(WindowEvent e) {}
}
