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

/** HeaderPanel sets the top part of the MainWindow, which includes the avatar and the status of the system.
 * This object inherits JPanel variables and methods */
public class HeaderPanel extends JPanel{
	
	/** avatarDisplay is a JLabel object that displays the avatar*/
	protected JLabel avatarDisplay;
	
	/** status is a JLabel object that shows the system's status (ie. whether it is connected or not) */
	protected JLabel status;
	
	/** avatar is an ImageIcon object of the user's display picture */
	protected ImageIcon avatar;
	
	/** MiscPanel constructor. It sets up the panel layout.*/
	public HeaderPanel() {
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createEmptyBorder(50,50,0,50));
		
		//logo-avatar
		avatarDisplay = new JLabel ();
		avatarDisplay.setHorizontalAlignment(SwingConstants.CENTER);
		avatar = new ImageIcon (this.getClass().getResource("images/buddylist/logoBox.png"));
	
		//TODO: set auto scaling + border later
		avatarDisplay.setIcon(avatar);
		
		status = new JLabel("Not signed in", SwingConstants.CENTER);
		status.setBorder(BorderFactory.createEmptyBorder(15,0,0,0));
		
		//add to panel
		add(avatarDisplay, BorderLayout.NORTH);
		add(status, BorderLayout.CENTER);
	}
	
	/*
	protected void setAnimation(){
		avatarDisplay.setIcon(new ImageIcon(getcwd() + "/src/mainwindow/animation.gif"));
		status.setText("Signing in");
		this.setVisible(true);
		System.out.println("headerPanel here");
	}*/
	
	/** sets the status text to "Sign in failed" when the user fails to sign in*/
	protected void loadMain(){
		//avatarDisplay.setIcon(avatar);
		status.setText("<html><FONT COLOR=RED>Sign in failed!</FONT></html>");
	}
}
