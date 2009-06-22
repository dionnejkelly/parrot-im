/* mainwindow.java
 * 
 * Programmed By:
 * 	   Vera Lukman
 *     Jordan Fox
 *     Kevin Fahy
 * 	   William Chen
 * 	   Aaron Siu (ANS)
 *     
 * Change Log:
 *     2009-May-23, VL (r443)
 *         Initial write.
 *     2009-May-24, VL 
 *     	   Added more components: user combobox, manage account,
 *         guest account login, help, options, sign in button
 *         (manage account and guest account login frame were
 *         provided as well)
 *     2009-May-24, JF
 *     	   Set icon to default parrotIM icon.
 *     2009-May-26, VL
 *     	   Changed the class into JFrame's child.
 *         Connected to buddylist (by clicking sign in button)
 *     2009-May-27, VL
 *         Fixed layout, changed buttons into labels
 *     2009-June-1, ANS | 2009-June-3, VL
 *         Reorganized code.
 *     2009-June-4, JF
 *     	   Integrated sign in panel with control. Now able to sign
 *         in using the GUI and connect it to the buddylist
 *     2009-June-4, VL
 *         Reorganized code, separated some components into different
 *         classes.
 *     2009-June-5, KF
 *     	   Integrated GUI with model.
 *     2009-June-8, KF/WC
 *     	   Implemented observer
 *     2009-June-8, AS
 *     	   Integrated with database
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

package view.mainwindow;

import java.awt.Dimension;
import java.sql.SQLException;
import java.util.*;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import controller.services.Xmpp;

import model.*;
import model.dataType.CurrentProfileData;
import model.dataType.UpdatedType;

public class mainwindow extends JFrame implements Observer {
	private signinPanel signPanel;
	public mainwindow (Xmpp chatClient, Model model) 
	        throws ClassNotFoundException, SQLException {

		//set Main Window Frame
		setTitle("Parrot-IM");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setMinimumSize(new Dimension (300,500));
		setPreferredSize(new Dimension (300,500));
		setIconImage(new ImageIcon(getcwd() + "/images/mainwindow/logo.png").getImage());

		//call SignIn Panel
		signPanel = new signinPanel(this, chatClient, model);
		getContentPane().add(signPanel);
		
		pack();
		setVisible(true);
		
		// Testing for model observers
		model.addObserver(this);
		CurrentProfileData a = new CurrentProfileData();
	}

	public String getcwd() {
	    String cwd = System.getProperty("user.dir");
	    return cwd;
	}
	
	public void update(Observable t, Object o) {
	    if (o == UpdatedType.ALL && o == UpdatedType.MAIN) {
	        System.out.println("Observed!" + o);
	    }else if (o == UpdatedType.PROFILE){
	    	
	    }
	}
}