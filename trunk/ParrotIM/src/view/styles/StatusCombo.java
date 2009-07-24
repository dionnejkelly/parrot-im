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
import java.awt.event.KeyEvent;
import java.sql.SQLException;

import javax.swing.JComboBox;

import view.options.PersonalProfileTab;

import model.enumerations.StatusType;

import controller.MainController;

/**
 * This object shows the combobox status options for buddylist window.
 * 
 * It inherits JComboBox methods and variables.
 */
public class StatusCombo extends JComboBox{
	/**
	 * chatClient is a MainController object. It helps the user to change their status on the server.
	 */
	MainController chatClient;
	private boolean optionStatus;
	
	/**
	 * StatusCombo constructor. It takes a MainController object as its argument.
	 * It also sets up some settings for the object.
	 * @param c 
	 * @param optionStatus*/
	public StatusCombo(MainController c, boolean optionStatus){
		super(StatusType.getStatusList());
		this.optionStatus = optionStatus;
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
	     * Changes the status of the user on the server.
	     * It takes an ActionEvent argument and returns null.
	     * @param e */
		public void actionPerformed(ActionEvent e) {
			if (getSelectedIndex() > -1){
				String userStatus = getSelectedItem().toString();
				
				try {
					chatClient.setPresence(userStatus);
					
		        	if (PersonalProfileTab.isPersonalTabOpened && !optionStatus) {
		             	PersonalProfileTab.status.setSelectedIndex(getSelectedIndex());
		            }
		        	 
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	}
}
