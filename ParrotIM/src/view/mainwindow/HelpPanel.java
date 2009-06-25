/* ManageAccountFrame.java
 * 
 * Programmed By:
 *     Vera Lukman
 *     Kevin Fahy
 *     William Chen
 *     
 * Change Log:
 *     2009-May-23, VL
 *         Initial write, stub help window. 
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

package view.mainwindow;
import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JLabel;

public class HelpPanel extends JPanel{
	
	public HelpPanel() {
		
		setLayout(new BorderLayout());
		add (new JLabel("Help here"));
	}
}
