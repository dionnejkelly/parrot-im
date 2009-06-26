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
import model.*;
import model.enumerations.PopupEnableWindowType;

/**
 * This object is a WindowListener for any popup window objects.
 * It controls the boolean variable in Model so the user wouldn't be able to 
 * open the same popup multiple times.
 * 
 * This object inherits WindowListener variables and methods.
 */
public class PopupEnableMainWindowListener implements WindowListener {
	/** model allows to store the state of ChatWindow (ie. whether it is opened or not). */
	private Model model;
	/** popupInt is an integer that codes the popup window name*/
	private PopupEnableWindowType popupInt;
	
	/** ChatWindowListener takes Model object as an argument.
	 * @param model
	 * @param popupInt */
	public PopupEnableMainWindowListener(Model model, PopupEnableWindowType popupInt){
		this.model = model;
		this.popupInt = popupInt;
	}

	public void windowActivated(WindowEvent e) {}

    /**
     * When the chatLogWindow is closed, logWindowOpen will be set to false.
     * It returns nothing.
     */
	public void windowClosed(WindowEvent e) {
		model.updatePopupState(popupInt, false);
	}

	public void windowClosing(WindowEvent e) {}
	public void windowDeactivated(WindowEvent e) {}
	public void windowDeiconified(WindowEvent e) {}
	public void windowIconified(WindowEvent e) {}
	
    /**
     * When the chatLogWindow is opened, logWindowOpen will be set to true.
     * It returns nothing.
     */
	public void windowOpened(WindowEvent e) {
		model.updatePopupState(popupInt, true);
	}
}
