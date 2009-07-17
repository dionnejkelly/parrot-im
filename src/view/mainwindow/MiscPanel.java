/* MiscPanel.java
 * 
 * Programmed By:
 *     Vera Lukman
 *     Jordan Fox
 *     Kevin Fahy
 *     William Chen
 *     
 * Change Log:
 *     2009-May-23, VL
 *         Initial write.
 *     2009-May-23, JF
 *         Added parrot icon.
 *     2009-June 10, VL
 *         Added avatar display and connection status.
 *     2009-June-13, WC
 *         Transferred file over to new project, ParrotIM.
 *     2009-June-19, WC
 *         Removed Options linklabel. It's redundant with managed account
 *     2009-June-23, KF
 *         Naming convention updates. Changed all class names.
 *     2009-June-24, VL
 *         Fixed multiple add profile/account windows. Not fully fixed yet.
 *         
 * Known Issues:
 *     None
 * 
 * Copyright (C) 2009  Pirate Captains
 * 
 * Full license can be found in ParrotIM/LICENSE.txt.
 */

package view.mainwindow;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import view.styles.LinkLabel;

/** accPanel is a JPanel object.
 * It sets the bottom part of the MainWindow, which includes a separator and help LinkLabel.*/
public class MiscPanel extends JPanel{
	
	/** MiscPanel constructor. It sets up the panel layout.*/
	public MiscPanel(){
		setLayout (new BoxLayout(this, BoxLayout.Y_AXIS));
		setBorder(BorderFactory.createEmptyBorder(0, 40, 40, 40));
		this.setOpaque(false);
		
		//line separator
		JSeparator line = new JSeparator();
		
		//guest account
		LinkLabel help = new LinkLabel ("Help", true);
		help.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            
            /**
        	 * When help is clicked, help frame will pop up.
        	 * @param evt
        	 */
			public void mouseClicked(java.awt.event.MouseEvent evt) {

            	HelpPanel helpPanel = new HelpPanel("http://sites.google.com/site/parrotimhelp/Home");
            }
        });
		
		add (line);
		add (help);
	}

}