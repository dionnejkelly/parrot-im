/* FeaturesPanel.java
 * 
 * Programmed By:
 *     Chenny Huang
 *     Vera Lukman
 *     
 * Change Log:
 *     2009-June-20, CH
 *         Initial write.
 *     2009-June-29, VL
 *         Reorganized code.
 *         
 * Known Issues:
 *     none
 *     
 * Copyright (C) 2009  Pirate Captains
 * 
 * License: GNU General Public License version 2.
 * Full license can be found in ParrotIM/LICENSE.txt.
 */

package view.options;

import java.awt.BorderLayout;
import java.awt.Color;
import java.sql.SQLException;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import view.buddylist.BuddyList;
import view.styles.GPanel;
import view.styles.WindowColors;

import model.Model;
import model.dataType.ProfileData;
import model.enumerations.UpdatedType;

import controller.MainController;

public class OptionPanel extends JPanel {
	private PersonalProfileTab personalProfile;
	public WindowColors colors = new WindowColors();
	
	private Model model;
	
	public OptionPanel
		(MainController c, Model model, JFrame optionframe, BuddyList buddyFrame) 
									throws ClassNotFoundException, SQLException{
		this.setLayout(new BorderLayout());
		this.model = model;
		ProfileData profile = model.getCurrentProfile();
		//tabbed options
		JTabbedPane tabbedOptions = new JTabbedPane(JTabbedPane.TOP);
		
		//tab color settings
		tabbedOptions.setForeground(Color.WHITE);
		tabbedOptions.setBackground(model.secondaryColor);
		
		personalProfile = new PersonalProfileTab(c,buddyFrame.getAccountInfo(), model);
		tabbedOptions.addTab("Personal Profile", personalProfile);
		tabbedOptions.addTab("Manage Accounts", new ManageAccount(profile, buddyFrame, model));
		tabbedOptions.addTab("Features Settings", new FeaturesPanel(c, optionframe, model));
		tabbedOptions.addTab("Preference", new PreferencePanel(c, optionframe, model));
		//setting layout
		this.add(tabbedOptions);
	}
	protected void updatePersonalProfileTab(){
		personalProfile.updateStatus();
	}
	
}
