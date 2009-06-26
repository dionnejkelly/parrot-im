/* statusCombo.java
 * 
 * Programmed By:
 * 	   Vera Lukman
 *     Jihoon Choi
 *     
 * Change Log:
 *     2009-June-16, VL
 *         Initial write.
 *     2009-June-17, JC
 *     	   Integrated with control.
 *     2009-June-23, KF
 *         Naming convention updates. Changed all class names.
 *         
 * Known Issues:
 * 	   None
 * 
 * Copyright (C) 2009  Pirate Captains
 * 
 * Full license can be found in ParrotIM/LICENSE.txt.
 */

package view.styles;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

import controller.MainController;

/**
 * This object shows the combobox status options for buddylist window.
 * 
 * It inherits JComboBox methods and variables.
 */
public class StatusCombo extends JComboBox{
	/**
	 * status is an array of Strings which provides the status options the user can choose from.
	 */
	private static String status[] = {"Available", "Away", "Busy", "Chatty"};
	
	/**
	 * chatClient is a MainController object. It helps the user to change their status on the server.
	 */
	MainController chatClient;
	
	/**
	 * StatusCombo constructor. It takes a MainController object as its argument.
	 * It also sets up some settings for the object.
	 */
	public StatusCombo(MainController c){
		super(status);
		chatClient = c;
		this.addActionListener(new statusComboListener());
		this.setMaximumSize(new Dimension(200, 30));
	}
	
	/**
     * Sets the behaviour when the status is changed.
     * 
     * This class inherits ActionListener methods and variables.
     */
	private class statusComboListener implements ActionListener{
		//TODO: Change this to inputmethodlistener!!!

		/**
	     * Changes the status of the user on the server. It takes an ActionEvent argument
	     * and returns null.
	     */
		public void actionPerformed(ActionEvent e) {
			if (getSelectedIndex() > -1){
				System.out.println("-----------------------------------Coming from here");
				String userStatus = getSelectedItem().toString();
				System.out.println("Status changed to: " + userStatus);
				
				chatClient.setStatus(userStatus);
			}
		}
	}
}
