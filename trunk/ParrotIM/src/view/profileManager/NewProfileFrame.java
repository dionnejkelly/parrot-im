/* NewProfileFrame.java
 * 
 * Programmed By:
 *     Aaron Siu
 *     Vera Lukman
 *     
 * Change Log:
 *         
 * Known Issues:
 *     none
 *     
 * Copyright (C) 2009  Pirate Captains
 * 
 * License: GNU General Public License version 2.
 * Full license can be found in ParrotIM/LICENSE.txt.
 */


package view.profileManager;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import controller.MainController;

import model.Model;

/**
 * The NewProfileFrame is currently responsible for providing the 
 * new profile frame for Parrot IM users.
 * 
 */

public class NewProfileFrame extends JFrame {
	
	// Section
    // I - Protected Data Member

    /**
     * The NewProfileFrame frame.
    */
	
	protected NewProfileFrame popup;
	
	// Section
    // II - Non-Static Data Members

    /**
     * The database.
     */
	
    private Model model;
    
    /**
     * Maintains the Parrot IM XMPP Protocol .
     */
    private MainController controller;
    
    
    /**
     * The manager frame.
     */
    
    private ProfileManager managerFrame;
    
    /**
     * The JPanel new profile data.
     */
    
    private JPanel newProfPanel;
    
    /**
     * The JTextField profile name field.
     */
    
    private JTextField ProfNameField;
    
    /**
     * The JPasswordField password field.
     */
    
    private JPasswordField pwdField;

    // Section
    // III - Constructor

    /**
     * NewProfileFrame() connects you to the New Profile Frame. 
     * Every time you want to run a NewProfileFrame window you have to
     * "NewProfileFrame newProfileFrame = new NewProfileFrame(Model model, MainController controller, ProfileManager pManager);"
     * 
     * @param model
     * @param controller
     * @param pManager
     */
	
    
    public NewProfileFrame(
            Model model, MainController controller, ProfileManager pManager) {
    	
    	this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    	this.model = model;
        this.controller = controller;
        managerFrame = pManager;
//        this.addWindowListener(new PopupWindowListener(managerFrame,this));
        popup = this;
        this.setAlwaysOnTop(true);
        this.setResizable(false);
        this.setLocationRelativeTo(managerFrame);

        setTitle("Create new Profile");
        newProfPanel = new JPanel();
        newProfPanel.setLayout(new BorderLayout());
        newProfPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        // ////////////TOP PART
        // account setupLabel setting Panel
        JPanel setupLabelPanel = new JPanel();
        GridLayout setupLabelLayout = new GridLayout(2, 1);
        setupLabelLayout.setVgap(10);
        setupLabelPanel.setLayout(setupLabelLayout);
        setupLabelPanel.setPreferredSize(new Dimension(75, 75));
        JLabel UNLabel = new JLabel("Profile Name:");
        JLabel pwdLabel = new JLabel("Password:");
        setupLabelPanel.add(UNLabel);
        setupLabelPanel.add(pwdLabel);

        pwdLabel.setVisible(false);

        // account setupField setting Panel
        JPanel setupFieldPanel = new JPanel();
        // BoxLayout setupFieldLayout = new BoxLayout(setupFieldPanel,
        // BoxLayout.Y_AXIS);
        GridLayout setupFieldLayout = new GridLayout(2, 1);
        setupFieldLayout.setVgap(5);
        setupFieldPanel.setLayout(setupFieldLayout);
        ProfNameField = new JTextField();
        ProfNameField.setPreferredSize(new Dimension(85, 20));
        pwdField = new JPasswordField();
        pwdField.setPreferredSize(new Dimension(100, 20));
        setupFieldPanel.add(ProfNameField);
        setupFieldPanel.add(pwdField);

        pwdField.setVisible(false);

        // account setup Panel
        JPanel setupPanel = new JPanel();
        setupPanel.setLayout(new BoxLayout(setupPanel, BoxLayout.X_AXIS));
        setupPanel.add(setupLabelPanel);
        setupPanel.add(setupFieldPanel);

        // *CENTRE PART : remember password + auto sign in
        // other Checkboxes setup Panel
        JPanel otherCheckPanel = new JPanel();
        otherCheckPanel.setLayout(new GridLayout(2, 1));
        JCheckBox enablePWDCheck = new JCheckBox();
        JCheckBox defaultProfCheck = new JCheckBox();
        otherCheckPanel.add(enablePWDCheck);
        otherCheckPanel.add(defaultProfCheck);

        // other Labels setup Panel
        GridLayout otherLabelLayout = new GridLayout(2, 1);
        JPanel otherLabelPanel = new JPanel();
        otherLabelPanel.setLayout(otherLabelLayout);
        otherLabelLayout.setVgap(7);
        JLabel enablePWDLabel = new JLabel("Enable Profile Password");
        JLabel defaultProfLabel = new JLabel("Default Profile");
        otherLabelPanel.add(enablePWDLabel);
        otherLabelPanel.add(defaultProfLabel);

        // other setups Panel
        JPanel otherSetupPanel = new JPanel();
        otherSetupPanel
                .setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        otherSetupPanel.setLayout(new FlowLayout());
        otherSetupPanel.add(otherCheckPanel);
        otherSetupPanel.add(otherLabelPanel);

        // BOTTOM PART : OK and Cancel Button
        // set ok-cancel button
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 10, 10));
        GridLayout buttonsLayout = new GridLayout(1, 2);
        buttonsLayout.setHgap(5);
        buttonsPanel.setLayout(buttonsLayout);

        // OK Button
        JButton okButton = new JButton("OK");
        okButton.addActionListener(new OkButtonListener());
        buttonsPanel.add(okButton);

        // Cancel Button
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new CancelButtonListener());
        buttonsPanel.add(cancelButton);

        // Add Content to main Panel and display
        newProfPanel.add(setupPanel, BorderLayout.NORTH);
        newProfPanel.add(otherSetupPanel, BorderLayout.CENTER);
        newProfPanel.add(buttonsPanel, BorderLayout.SOUTH);
        getContentPane().add(newProfPanel);
        pack();
        setVisible(true);
        setIconImage(new ImageIcon("src/images/mainwindow/logo.png").getImage());
    }

    /*
     * SECTION: Listener classes
     */

    /**
     * The OkButtonListener is currently responsible for listening the OK button.
     * 
     */
    
    
    private class OkButtonListener implements ActionListener {
    	
    	/**
         * Listens to user's action.
         * 
         * @param evt
         * 
         */
    	
        public void actionPerformed(ActionEvent evt) {
            String name = ProfNameField.getText();
            String password = String.copyValueOf(pwdField.getPassword());
            boolean defaultProfile = false;

            // Only work if the profile is not an empty string
            if (name != null && name.length() > 0) {
                controller.addProfile(name, password, defaultProfile);

                popup.setVisible(false);
                popup.dispose();
            }

            return;
        }
    }

    
    /**
     * The CancelButtonListener is currently responsible for listening the Cancel button.
     * 
     */
    
    
    private class CancelButtonListener implements ActionListener {
    	
    	/**
         * Listens to user's action.
         * 
         * @param evt
         * 
         */
    	 
    	
        public void actionPerformed(ActionEvent evt) {
            popup.setVisible(false);
            popup.dispose();

            return;
        }
    }
}
