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

public class chatWindowListener implements WindowListener {
	Model model;
	
	public chatWindowListener(Model model){
		this.model = model;
	}

	public void windowClosed(WindowEvent e) {
		model.chatWindowOpen = false;
	}

	public void windowClosing(WindowEvent e) {
		model.chatWindowOpen = false;
	}
	public void windowDeactivated(WindowEvent e) {}
	public void windowDeiconified(WindowEvent e) {}
	public void windowIconified(WindowEvent e) {}
	public void windowOpened(WindowEvent e) {}
	public void windowActivated(WindowEvent e) {}
}
