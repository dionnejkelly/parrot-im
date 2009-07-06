/* ManageAccountFrame.java
 * 
 * Programmed By:
 *     Vera Lukman
 *     Kevin Fahy
 *     Ahmad Sidiqi
 *     Jordan Fox
 *     Jihoon Choi
 *     William Chen
 *     
 * Change Log:
 *     2009-June-5, VL
 *         Initial write to increase modularity.
 *     2009-June-5, KF
 *         Integrated with model.
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
 *     2009-June-15, AS
 *         Fixed database.
 *     2009-June-16, VL
 *         Fixed refresh problem after delete/add account.
 *     2009-June-22, KF
 *         Relayout database. Account adding/remove now works for all profiles.
 *     2009-June-23, KF
 *         Naming convention updates. Changed all class names.
 *     2009-June-24, VL/JC
 *         Removed redundant code.
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

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import controller.MainController;

import view.options.ManageAccount;

import model.Model;

public class ManageAccountFrame extends JFrame{
	
	protected ManageAccountFrame (Model model,  MainController controller, String profilename) 
											throws ClassNotFoundException, SQLException {

		setLocationRelativeTo(null);
		
		setTitle(profilename + " Account Manager");
		setLocation(100, 100);
		setPreferredSize(new Dimension(520,300));
		setResizable(false);
		setIconImage(new ImageIcon("imagesimages/mainwindow/logo.png").getImage());
		JPanel accountPanel = new ManageAccount(model, controller, profilename);
		accountPanel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
		getContentPane().add(accountPanel);
		pack();
		setVisible(true);
		setIconImage(new ImageIcon("src/images/mainwindow/logo.png").getImage());
	}
}