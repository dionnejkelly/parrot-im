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
import java.sql.SQLException;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

import model.Model;

import controller.MainController;

public class OptionPanel extends JPanel{
	protected JTextArea personalMessage;
	
	public OptionPanel(MainController c, Model model) throws ClassNotFoundException, SQLException{
		this.setLayout(new BorderLayout());
		
		//tabbed options
		JTabbedPane tabbedOptions = new JTabbedPane(JTabbedPane.TOP);
		PersonalProfileTab personalProfile = new PersonalProfileTab();
		personalMessage = personalProfile.personalMessage;
		tabbedOptions.addTab("Personal Profile", personalProfile);
		tabbedOptions.addTab("Manage Accounts", new ManageAccount(model, c));
		tabbedOptions.addTab("Features Settings", new FeaturesPanel(c));
		//setting layout
		this.add(tabbedOptions);
	}

}
