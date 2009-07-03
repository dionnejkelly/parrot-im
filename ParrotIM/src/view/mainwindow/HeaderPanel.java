/* HeaderPanel.java
 * 
 * Programmed By:
 *     Vera Lukman
 *     Kevin Fahy
 *     Jordan Fox
 *     William Chen
 *     
 * Change Log:
 *     2009-May-23, VL
 *         Initial write.
 *     2009-May-23, JF
 *         Added parrot icon.
 *     2009-June-5, VL
 *         Removed connect all option.
 *     2009-June-6, KF
 *         Integrated core with model.
 *     2009-June-8, AS
 *         Integrated with database.
 *     2009-June-8, JF
 *         Added popupWindowListener.
 *     2009-June-13, VL
 *         Now can detect if the checkbox is checked. (requested by Rakan)
 *     2009-June-13, WC
 *         Transferred file over to new project, ParrotIM.
 *     2009-June-22, JF
 *         Changed display picture into ParrotIM logo.
 *     2009-June-23, KF
 *         Naming convention updates. Changed all class names.
 *     2009-June-24, KF
 *         Changed the image path.
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

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import view.styles.AvatarLabel;

/** HeaderPanel sets the top part of the MainWindow, which includes the avatar and the status of the system.
 * This object inherits JPanel variables and methods */
public class HeaderPanel extends JPanel{
	/** status is a JLabel object that shows the system's status (ie. whether it is connected or not) */
	private JLabel status;
	
	/** MiscPanel constructor. It sets up the panel layout.*/
	public HeaderPanel() {
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createEmptyBorder(50,50,0,50));
		
		//logo-avatar
//		JLabel avatarDisplay = new JLabel ();
		JLabel avatarDisplay = new AvatarLabel(getClass().getClassLoader().getResource("images/buddylist/logoBox.png"));
		avatarDisplay.setHorizontalAlignment(SwingConstants.CENTER);
//		ImageIcon avatar = new ImageIcon (getClass().getClassLoader().getResource("images/buddylist/logoBox.png"));
	
		//TODO: set auto scaling + border later
//		avatarDisplay.setIcon(avatar);
		
		status = new JLabel("Not signed in", SwingConstants.CENTER);
		status.setBorder(BorderFactory.createEmptyBorder(7,0,8,0));
		
		//add to panel
		add(avatarDisplay, BorderLayout.NORTH);
		add(status, BorderLayout.CENTER);
	}
	
	/** sets the status text to errormsg when the user fails to sign in
	 * @param errormsg*/
	protected void displaySystemStatus(String errormsg){
		//avatarDisplay.setIcon(avatar);
		status.setText("<html><FONT COLOR=RED>"+errormsg+"</FONT></html>");
	}
}
