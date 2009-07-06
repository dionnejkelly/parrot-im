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

package view.mainwindow;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import model.Model;

public class NewProfileFrame extends JFrame{
	private Model model;
	private JFrame popupFrame;
	private JCheckBox defaultCheck;
	private JCheckBox passwordCheck;
	private JPanel passwordOption;
	private JPasswordField passwordField;
	private JTextField profileName;
	
	public NewProfileFrame (Model model){
		popupFrame = this;
		this.model = model;
		
		/*PROFILE*/
		profileName = new JTextField();
		profileName.setPreferredSize(new Dimension(280,20));
		JPanel profilePanel = new JPanel();
		profilePanel.add(new JLabel ("Profile Name: "));
		profilePanel.add(profileName);
		
		/*PASSWORD*/
		passwordCheck = new JCheckBox("Enable Password (recommended)");
		passwordCheck.addChangeListener(new passwordCheckListener());
		
		passwordField = new JPasswordField();
		passwordField.setPreferredSize(new Dimension(330,20));
		passwordOption = new JPanel();
		passwordOption.setAlignmentX(LEFT_ALIGNMENT);
		passwordOption.add(passwordField);
		passwordOption.setVisible(false);
		
		/*DEFAULT PROFILE*/
		defaultCheck = new JCheckBox("Default Profile");
		JPanel optionPanel = new JPanel();
		optionPanel.setAlignmentX(LEFT_ALIGNMENT);
		optionPanel.setLayout(new BoxLayout(optionPanel, BoxLayout.Y_AXIS));
		optionPanel.add(defaultCheck);
		optionPanel.add(passwordCheck);
		optionPanel.add(passwordOption);
		
		/*BUTTONS*/
		JButton nextButton = new JButton ("Next");
		nextButton.addActionListener(new nextButtonActionListener());
		JButton cancelButton = new JButton ("Cancel");
		cancelButton.addActionListener(new cancelButtonActionListener());
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.add(nextButton);
		buttonsPanel.add(cancelButton);
		
		/*LAYOUT*/
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
		mainPanel.add(profilePanel, BorderLayout.NORTH);
		mainPanel.add(optionPanel, BorderLayout.CENTER);
		mainPanel.add(buttonsPanel, BorderLayout.SOUTH);
		
		
		this.setLocationRelativeTo(null);
		this.setTitle("New Profile");
		this.setResizable(false);
		this.setPreferredSize(new Dimension(400,200));
		this.getContentPane().add(mainPanel);
		
		this.pack();
		this.setVisible(true);
		
	}
	private class passwordCheckListener implements ChangeListener{

		public void stateChanged(ChangeEvent e) {
			if (passwordCheck.isSelected())
				passwordOption.setVisible(true);
			else
				passwordOption.setVisible(false);
		}	
	}
	
	private class cancelButtonActionListener implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			popupFrame.dispose();	
		}
	}
	
	private class nextButtonActionListener implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			model.addProfile(profileName.getText(), password(passwordField.getPassword()), defaultCheck.isSelected());
			popupFrame.dispose();
		}
		
	}
	
    /**
     * This method is used to get the String of password from the user.
     * 
     * @param pass
     * @return the string of password
     */
    private String password(char[] pass) {
        String str = new String();
        str = "";

        for (int i = 0; i < pass.length; i++) {
            str += pass[i];
        }
        return str;

    }
}
