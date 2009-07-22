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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Model;

import view.chatwindow.ThemeOptionsComboBox;
import view.styles.GPanel;
import view.styles.WindowColors;
import view.theme.LookAndFeelManager;

import controller.MainController;

public class PreferencePanel extends GPanel {
    private ThemeOptionsComboBox themeMenu;

    public PreferencePanel(MainController c, JFrame mainframe, Model model)
            throws ClassNotFoundException, SQLException {
    	this.setGradientColors(colors.PRIMARY_COLOR_MED, Color.WHITE);
    	
        /* THEME SELECTOR */
        themeMenu = new ThemeOptionsComboBox();
        themeMenu.setMaximumSize(new Dimension(200, 30));
        themeMenu.setToolTipText("Select your own Theme");
        themeMenu.setMaximumSize(new Dimension(200,30));
        themeMenu.setAutoscrolls(true);
        themeMenu.addActionListener(new ThemeMenuListener());
        JLabel themeLabel = new JLabel("Theme: ");
        themeLabel.setForeground(colors.SECONDARY_COLOR_DARK);
        JPanel themePanel = new JPanel();
        themePanel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        themePanel.setAlignmentX(LEFT_ALIGNMENT);
        themePanel.setLayout(new BoxLayout(themePanel, BoxLayout.X_AXIS));
        themePanel.setBackground(colors.SECONDARY_COLOR_LT);
        themePanel.add(themeLabel);
        themePanel.add(themeMenu);
        
        this.setLayout(new BorderLayout());
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 225));
        this.add(themePanel, BorderLayout.NORTH);
       
    }
    
    private class ThemeMenuListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			System.out.println("Combo = " + themeMenu.getSelectedIndex());
			LookAndFeelManager themeSelector = new LookAndFeelManager();
			themeSelector.setLookAndFeel(themeMenu.getSelectedIndex());
		}
    	
    }

    

}
