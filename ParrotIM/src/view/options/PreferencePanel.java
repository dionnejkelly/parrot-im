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
 *     incomplete features
 *     
 * Copyright (C) 2009  Pirate Captains
 * 
 * License: GNU General Public License version 2.
 * Full license can be found in ParrotIM/LICENSE.txt.
 */

package view.options;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import model.Model;

import view.chatwindow.ThemeOptionsComboBox;
import view.theme.LookAndFeelManager;

import controller.MainController;

public class PreferencePanel extends JPanel {
    private ThemeOptionsComboBox themeMenu;

    public PreferencePanel(MainController c, JFrame mainframe, Model model)
            throws ClassNotFoundException, SQLException {
        
        /* THEME SELECTOR */
        themeMenu = new ThemeOptionsComboBox();
        themeMenu.setToolTipText("Select your own Theme");
        themeMenu.setAutoscrolls(true);
        themeMenu.addActionListener(new ThemeMenuListener());
        
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        this.add(themeMenu);
       
    }
    
    private class ThemeMenuListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			System.out.println("Combo = " + themeMenu.getSelectedIndex());
			LookAndFeelManager themeSelector = new LookAndFeelManager();
			themeSelector.setLookAndFeel(themeMenu.getSelectedIndex());
		}
    	
    }

    

}
