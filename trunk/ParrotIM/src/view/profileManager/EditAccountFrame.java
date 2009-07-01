/* EditAccountFrame.java
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
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import controller.MainController;

import model.Model;
import model.enumerations.ServerType;

/**
 * The EditAccountFrame is currently responsible for providing the edit account
 * frame for Parrot IM users.
 * 
 */

public class EditAccountFrame extends JFrame {
    /*
     * LEAVING OUT EDIT ACCOUNT FUNCTION FOR ALPHA
     */

    // Section
    // I - Protected Data Member
    /**
     * The EditAccountFrame frame.
     */

    protected EditAccountFrame popup;

    protected JTextField jabberServer;
    protected JPanel serverPanel;

    // Section
    // II - Non-Static Data Members

    /**
     * The JPanel account data.
     */

    private JPanel modAcctPanel;

    /**
     * The database.
     */
    private Model model;

    /**
     * The manager frame.
     */

    private ProfileManager managerFrame;

    /**
     * The JTextField user name field.
     */

    private JTextField UNField;

    /**
     * The JPasswordField password field.
     */

    private JPasswordField pwdField;

    /**
     * The JComboBox service field.
     */

    private JComboBox serviceField;

    /**
     * Maintains the Parrot IM XMPP Protocol .
     */

    private MainController controller;

    /**
     * The profile selected from the profile selection window.
     */
    private String profile;

    // Instance 1 -- New Account (empty forms)

    // Section
    // III - Constructor

    /**
     * EditAccountFrame() connects you to the Edit Account Frame. Every time you
     * want to run a EditAccountFrame window you have to"EditAccountFrame editAccountFrame = new EditAccountFrame(Model model, ProfileManager pManager, MainController controller, String profile);"
     * 
     * @param model
     * @param pManager
     * @param controller
     * @param profile
     */

    public EditAccountFrame(Model model, ProfileManager pManager,
            MainController controller, String profile) {

        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.model = model;
        this.controller = controller;
        this.profile = profile;
        managerFrame = pManager;
        // this.addWindowListener(new PopupWindowListener(managerFrame,this));
        popup = this;
        this.setResizable(false);
        this.setLocationRelativeTo(managerFrame);

        setTitle("Add New Account");
        modAcctPanel = new JPanel();
        modAcctPanel.setLayout(new BorderLayout());
        modAcctPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        modAcctPanel.setPreferredSize(new Dimension(300, 300));
        // ////////////TOP PART
        // account setupLabel setting Panel
        JPanel setupLabelPanel = new JPanel();
        GridLayout setupLabelLayout = new GridLayout(3, 1);
        setupLabelLayout.setVgap(10);
        setupLabelPanel.setLayout(setupLabelLayout);
        setupLabelPanel.setPreferredSize(new Dimension(75, 75));
        JLabel serviceLabel = new JLabel("Service:");
        JLabel UNLabel = new JLabel("Username:");
        JLabel pwdLabel = new JLabel("Password:");
        setupLabelPanel.add(serviceLabel);
        setupLabelPanel.add(UNLabel);
        setupLabelPanel.add(pwdLabel);

        // account setupField setting Panel
        JPanel setupFieldPanel = new JPanel();
        // BoxLayout setupFieldLayout = new BoxLayout(setupFieldPanel,
        // BoxLayout.Y_AXIS);
        GridLayout setupFieldLayout = new GridLayout(3, 1);
        setupFieldLayout.setVgap(5);
        setupFieldPanel.setLayout(setupFieldLayout);
        serviceField = new JComboBox(model.getServerList());
        serviceField.setPreferredSize(new Dimension(170, 27));
        serviceField.addItemListener(new serverListener());

        UNField = new JTextField();
        UNField.setPreferredSize(new Dimension(85, 20));
        pwdField = new JPasswordField();
        pwdField.setPreferredSize(new Dimension(100, 20));
        setupFieldPanel.add(serviceField);
        setupFieldPanel.add(UNField);
        setupFieldPanel.add(pwdField);

        // account setup Panel
        JPanel setupPanel = new JPanel();
        setupPanel.setLayout(new BoxLayout(setupPanel, BoxLayout.X_AXIS));
        setupPanel.add(setupLabelPanel);
        setupPanel.add(setupFieldPanel);

        // *CENTRE PART : remember password + auto sign in
        // other Checkboxes setup Panel
        JPanel otherCheckPanel = new JPanel();
        otherCheckPanel.setLayout(new GridLayout(2, 1));
        JCheckBox rememberPWDCheck = new JCheckBox();
        JCheckBox autoSignCheck = new JCheckBox();
        otherCheckPanel.add(rememberPWDCheck);
        otherCheckPanel.add(autoSignCheck);

        // other Labels setup Panel
        GridLayout otherLabelLayout = new GridLayout(2, 1);
        JPanel otherLabelPanel = new JPanel();
        otherLabelPanel.setLayout(otherLabelLayout);
        otherLabelLayout.setVgap(7);
        JLabel rememberPWDLabel = new JLabel("Remember password");
        JLabel autoSignLabel = new JLabel("Auto Sign-in");
        otherLabelPanel.add(rememberPWDLabel);
        otherLabelPanel.add(autoSignLabel);

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
        okButton.addActionListener(new AddListener());
        buttonsPanel.add(okButton);

        // Cancel Button
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new CancelListener());
        buttonsPanel.add(cancelButton);

        jabberServer = new JTextField();
        jabberServer.setPreferredSize(new Dimension(200, 20));
        jabberServer.setToolTipText("specify jabber server");

        JPanel jabberServerPanel = new JPanel();
        jabberServerPanel.setLayout(new BorderLayout());
        jabberServerPanel.add(jabberServer, BorderLayout.NORTH);

        JPanel jabberServerLabel = new JPanel();
        jabberServerLabel.setLayout(new BorderLayout());
        jabberServerLabel.add(new JLabel("Jabber server:  "),
                BorderLayout.NORTH);

        serverPanel = new JPanel();
        serverPanel.setLayout(new BorderLayout());
        serverPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 5));
        serverPanel.add(jabberServerLabel, BorderLayout.WEST);
        serverPanel.add(jabberServerPanel, BorderLayout.CENTER);

        // Add Content to main Panel and display
        modAcctPanel.add(setupPanel, BorderLayout.NORTH);
        modAcctPanel.add(otherSetupPanel, BorderLayout.CENTER);
        modAcctPanel.add(buttonsPanel, BorderLayout.SOUTH);
        modAcctPanel.add(serverPanel, BorderLayout.CENTER);

        getContentPane().add(modAcctPanel);
        pack();
        setVisible(true);
    }

    private class serverListener implements ItemListener {

        public void itemStateChanged(ItemEvent e) {
            if (serviceField.getSelectedIndex() == 0) {
                serverPanel.setVisible(true);
            } else {
                serverPanel.setVisible(false);
            }
        }

    }

    // Instance 2 -- Edit Account (User/Pass already filled in, Disable username
    // box)
    /*
     * private editAccountFrame(Model model, String name) {
     * 
     * }
     */

    /*
     * SECTION: Add/Cancel Listeners
     */

    /**
     * The AddListener is currently responsible for listening the OK button.
     * 
     */

    private class AddListener implements ActionListener {

        /**
         * Listens to user's action.
         * 
         * @param evt
         * 
         */

        public void actionPerformed(ActionEvent evt) {
            String accountName = UNField.getText();
            String password = String.copyValueOf(pwdField.getPassword());
            ServerType server = (ServerType) serviceField.getSelectedItem();

            System.out.println("Server = " + server.toString());
            // Only accept non-empty strings for the account. An empty
            // string for the password is perfectly all right.
            if (accountName != null && accountName.length() > 0) {
                if (server == ServerType.GOOGLE_TALK
                        || server == ServerType.TWITTER) {
                    controller.addAccount(profile, server, accountName,
                            password);
                } else if (server == ServerType.JABBER) {
                    controller.addAccount(profile, server, jabberServer
                            .getText(), accountName, password);
                }

                popup.setVisible(false);
                popup.dispose();
            }

            else if (accountName.equals("") || password.equals("")) {
                String resultMessage = "Sorry for the inconvenience but please provide appropriate USER ID and Password in the field. Thank you for your co-operation.";
                JOptionPane.showMessageDialog(null, resultMessage);

            }

            else {
                String resultMessage = "Sorry for the inconvenience but we only support XMPP, ICQ, and Twitter for the beta. Thank you for your co-operation.";
                JOptionPane.showMessageDialog(null, resultMessage);
            }

            return;
        }
    }

    /**
     * The CancelListener is currently responsible for listening the Cancel
     * button.
     * 
     */

    private class CancelListener implements ActionListener {

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
