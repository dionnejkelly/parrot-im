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
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JTextArea;

import model.Model;

import controller.MainController;

public class OptionFrame extends JFrame {
	private JTextArea personalMessage;
	private MainController controller;
	
	public OptionFrame(MainController c, Model model) throws ClassNotFoundException, SQLException{
		this.setTitle("User Preferences");
		this.addWindowListener(new OptionWindowListener());
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		controller = c;
		OptionPanel mainPanel =  new OptionPanel(c, model);
		personalMessage = mainPanel.personalMessage;
        getContentPane().add(mainPanel);
        setPreferredSize(new Dimension(500,300));
        pack();
   
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
	}
	
	private class OptionWindowListener implements WindowListener{

		public void windowActivated(WindowEvent e) {}

		public void windowClosed(WindowEvent e) {
			controller.setStatus(personalMessage.getText());
		}

		public void windowClosing(WindowEvent e) {}
		public void windowDeactivated(WindowEvent e) {}
		public void windowDeiconified(WindowEvent e) {}
		public void windowIconified(WindowEvent e) {}
		public void windowOpened(WindowEvent e) {}
		
	}
}
