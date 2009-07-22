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

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import view.options.ManageAccount;

import model.dataType.ProfileData;

public class ManageAccountFrame extends JFrame {
	JFrame manageaccount;
    public ManageAccountFrame(ProfileData profile) {
    	manageaccount = this;
        setLocationRelativeTo(null);

        setTitle(profile + "\'s Account Manager");
        setLocationRelativeTo(null);
        setPreferredSize(new Dimension(530, 300));
        setResizable(false);
        ManageAccount accountPanel = new ManageAccount(profile);
        accountPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        getContentPane().add(accountPanel);
        
        //DONE BUTTON
        JButton doneButton = new JButton ("Done", new ImageIcon(this.getClass()
                .getResource("/images/buddylist/button_ok.png")));
        doneButton.addActionListener(new doneActionListener());
        JPanel donePanel = new JPanel ();
        donePanel.setOpaque (false);
        donePanel.add(doneButton);
        accountPanel.getRightPanel().add(donePanel, BorderLayout.SOUTH);
        
        pack();
        setVisible(true);
        setIconImage(new ImageIcon("src/images/mainwindow/logo.png").getImage());
    }
    
    private class doneActionListener implements ActionListener{

		public void actionPerformed(ActionEvent arg0) {
			manageaccount.dispose();
		}
    	
    }
}