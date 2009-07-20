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
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import controller.MainController;

import view.styles.GPanel;
import view.styles.PopupWindowListener;

import model.Model;
import model.dataType.ProfileData;

public class NewProfileFrame extends JFrame {
    private Model model;
    private JFrame popupFrame;
    private JCheckBox defaultCheck;
    private JCheckBox passwordCheck;
    private JPasswordField passwordField;
    private JTextField profileName;
    private MainController core;
    private JFrame mainFrame;
    private JButton nextButton;
    private JCheckBox autoSigninCheck;

    public NewProfileFrame(Model model, MainController core, JFrame mainFrame) {
        popupFrame = this;
        this.model = model;
        this.core = core;
        this.mainFrame = mainFrame;
        this.addWindowListener(new PopupWindowListener(this.mainFrame, this));

        /* PROFILE Name is limited to __ characters */
        profileName = new JTextField();
        profileName.setPreferredSize(new Dimension(280, 20));
        profileName.addKeyListener(new profileNameKeyListener());
        JLabel profileLabel = new JLabel("Profile Name: ");
        JPanel profilePanel = new JPanel();
        profilePanel.setOpaque(false);
        profilePanel.add(profileLabel);
        profilePanel.add(profileName);

        /* DEFAULT PROFILE */
        defaultCheck = new JCheckBox("Default Profile (Auto-Signin)");
        defaultCheck.setPreferredSize(new Dimension(375, 20));
        defaultCheck.setAlignmentX(LEFT_ALIGNMENT);
        defaultCheck
                .setToolTipText("<html>Enables auto-login to this profile whenever ParrotIM runs."
                        + "<br>There can only be one Default Profile, checking this will set this profile as the new default");

        autoSigninCheck = new JCheckBox("Auto Signin");
        autoSigninCheck.setPreferredSize(new Dimension(330, 20));
        autoSigninCheck.setOpaque(false);
        JPanel autoSigninPanel = new JPanel();
        autoSigninPanel.setAlignmentX(LEFT_ALIGNMENT);
        autoSigninPanel.add(autoSigninCheck);
        autoSigninPanel.setOpaque(false);
        autoSigninPanel.setVisible(false);

        defaultCheck.addChangeListener(new CheckListener(defaultCheck,
                autoSigninPanel));
        defaultCheck.setOpaque(false);

        /* PASSWORD */
        passwordCheck = new JCheckBox("Enable Password (recommended)");
        passwordCheck.setPreferredSize(new Dimension(375, 20));
        passwordCheck.setAlignmentX(LEFT_ALIGNMENT);
        passwordCheck
                .setToolTipText("Lock your profile account settings by setting a password");
        passwordCheck.setOpaque(false);
        passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(330, 20));
        JPanel passwordOption = new JPanel();
        passwordOption.setAlignmentX(LEFT_ALIGNMENT);
        passwordOption.add(passwordField);
        passwordOption.setVisible(false);

        passwordCheck.addChangeListener(new CheckListener(passwordCheck,
                passwordOption));

        JPanel optionPanel = new JPanel();
        optionPanel.setAlignmentX(LEFT_ALIGNMENT);
        // optionPanel.setLayout(new BoxLayout(optionPanel, BoxLayout.Y_AXIS));
        optionPanel.setOpaque(false);
        optionPanel.setLayout(new FlowLayout());
        optionPanel.add(defaultCheck);
        optionPanel.add(autoSigninPanel);
        optionPanel.add(passwordCheck);
        optionPanel.add(passwordOption);

        /* BUTTONS */
        nextButton = new JButton("Next", new ImageIcon(this.getClass()
                .getResource("/images/mainwindow/next.png")));
        nextButton.setEnabled(false);
        nextButton.addActionListener(new nextButtonActionListener());
        JButton cancelButton = new JButton("Cancel", new ImageIcon(this
                .getClass().getResource("/images/mainwindow/cancel.png")));
        cancelButton.addActionListener(new cancelButtonActionListener());
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setOpaque(false);
        buttonsPanel.add(nextButton);
        buttonsPanel.add(cancelButton);

        /* LAYOUT */
        GPanel mainPanel = new GPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        mainPanel.setGradientColors(mainPanel.colors.PRIMARY_COLOR_MED, Color.WHITE);
        mainPanel.add(profilePanel, BorderLayout.NORTH);
        mainPanel.add(optionPanel, BorderLayout.CENTER);
        mainPanel.add(buttonsPanel, BorderLayout.SOUTH);

        this.setLocationRelativeTo(null);
        this.setTitle("New Profile");
        this.setResizable(false);
        this.setPreferredSize(new Dimension(420, 260));
        this.getContentPane().add(mainPanel);

        this.pack();
        this.setVisible(true);
        this.setIconImage(new ImageIcon("src/images/mainwindow/logo.png")
                .getImage());

    }

    private class CheckListener implements ChangeListener {
        private JCheckBox check;
        private JPanel panel;

        public CheckListener(JCheckBox c, JPanel p) {
            check = c;
            panel = p;
        }

        public void stateChanged(ChangeEvent e) {
            if (check.isSelected())
                panel.setVisible(true);
            else
                panel.setVisible(false);
        }
    }

    private class cancelButtonActionListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            popupFrame.dispose();
        }
    }

    private class nextButtonActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            ProfileData newProfile = null;
            if (profileName.getText().length() > 15) {
                popupFrame.setAlwaysOnTop(false);
                String warning = "Please provide a profile name of no longer than 15 characters.";
                JOptionPane.showMessageDialog(null, warning);
            } else if (profileName.getText().length() > 0) {
                newProfile = new ProfileData(profileName.getText());
                if (passwordCheck.isEnabled()) {
                    newProfile.setPassword(String.copyValueOf(passwordField
                            .getPassword()));
                }
                model.getProfileCollection().addProfile(newProfile);
                if (defaultCheck.isSelected()) {
                    model.getProfileCollection().setDefaultProfile(newProfile);
                }
                if (autoSigninCheck.isSelected()) {
                    newProfile.setAutoSignInEnabled(true);
                }

                ManageAccountFrame manageAccount = new ManageAccountFrame(newProfile);
                manageAccount.addWindowListener(new PopupWindowListener(
                        mainFrame, manageAccount));
                popupFrame.dispose();
            }

        }
    }

    private class profileNameKeyListener implements KeyListener {

        public void keyPressed(KeyEvent arg0) {
            // Will not be implemented.
        }

        public void keyReleased(KeyEvent arg0) {
            if (profileName.getText().length() > 0)
                nextButton.setEnabled(true);
            else
                nextButton.setEnabled(false);
        }

        public void keyTyped(KeyEvent arg0) {
            // Will not be implemented.
        }

    }

}
