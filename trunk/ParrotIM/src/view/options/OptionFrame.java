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

import java.awt.Dimension;
import java.sql.SQLException;

import javax.swing.JFrame;

import model.Model;

import controller.MainController;

public class OptionFrame extends JFrame {
	public OptionFrame(MainController c, Model model) throws ClassNotFoundException, SQLException{
		this.setTitle("User Preferences");
		
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		 
        getContentPane().add(new OptionPanel(c, model));
        setPreferredSize(new Dimension(500,300));
        pack();
   
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
	}
}
