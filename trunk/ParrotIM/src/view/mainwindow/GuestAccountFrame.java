/* guestAccountFrame.java
 * 
 * Programmed By:
 * 	   Vera Lukman
 *     Jihoon Choi
 *     Jordan Fox
 * 	   William Chen
 *     
 * Change Log:
 *     2009-June-6, VL
 *         Initial write.
 *     2009-June-7, JC
 *     	   Integrated with control.
 *     2009-June-8, JF
 *     	   Fixed keyPressed()
 *     2009-June-13, WC/VL
 *         Transferred file over to new project, ParrotIM.
 *         Fixed alignment to left.
 *     2009-June-23, KF
 *         Naming convention updates. Changed all class names.
 *     2009-June-30, VL
 *         Added Jabber server field. Code clean up.
 *         
 * Known Issues:
 *     None
 * 
 * Copyright (C) 2009  Pirate Captains
 * 
 * Full license can be found in ParrotIM/LICENSE.txt.
 */

package view.mainwindow;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import model.Model;
import model.enumerations.ServerType;
import model.enumerations.UpdatedType;
import view.buddylist.BuddyList;
import view.styles.GPanel;
import view.styles.PopupWindowListener;
import controller.MainController;
import controller.services.BadConnectionException;

/**
 * The container frame of the guest account. User can use this frame to log in
 * parrot-im as a guest.
 * 
 * This object inherits JFrame variables and methods
 */
public class GuestAccountFrame extends JFrame implements Observer {

    protected JTextField jabberServer;
    protected JPanel serverPanel;

    /**
     * model stores the needed data of the system. It also connects it with
     * database
     */
    private Model model;

    /**
     * core is a MainController object. It helps the user to login.
     */
    private MainController core;

    /** mainFrame is a MainWindow object which is a container of this panel. */
    private MainWindow mainFrame;

    /** popup is this JFrame object */
    private JFrame popup;

    /**
     * mainPanel is a SignInPanel object. It sets the GUI component of
     * MainWindow.
     */
    private SignInPanel mainPanel;

    /**
     * UNFieldGuest is a JTextField object. It is the text field for the user's
     * username.
     */
    private JTextField UNFieldGuest;

    /**
     * PwdFieldGuest is a JPasswordField object. It is the text field for the
     * user's password.
     */
    private JPasswordField PwdFieldGuest;

    /**
     * server is a JComboBox object. It shows the listed server the user can
     * connect to.
     */
    private JComboBox server;

    private GPanel GALPanel;

    /**
     * This is the constructor of the Guest Account frame.
     * 
     * @param model
     * @param c
     * @param frame
     * @param signin
     */
    public GuestAccountFrame(Model model, MainController c, MainWindow frame,
            SignInPanel signin) {
        popup = this;
        this.model = model;
        model.addObserver(this);
        core = c;
        mainFrame = frame;
        setLocationRelativeTo(mainFrame);
        mainPanel = signin;
        this.addWindowListener(new PopupWindowListener(mainFrame, this));

        // set Frame
        setTitle("Guest Account Login");
        setPreferredSize(new Dimension(400, 250));
        setResizable(false);
        setIconImage(new ImageIcon("imagesimage/mainwindow/logo.png")
                .getImage());

        // select server
        GALPanel = new GPanel();
        GALPanel.setLayout(new BorderLayout());
        GALPanel.setGradientColors(model.primaryColor, model.secondaryColor);

        // select server
        server = new JComboBox(model.getServerList());
        server.setPreferredSize(new Dimension(200, 30));
        server.addItemListener(new serverListener());
        // server name for jabber
        // textfield
        jabberServer = new JTextField();
        jabberServer.setPreferredSize(new Dimension(200, 20));
        jabberServer.setToolTipText("Specify jabber server");
        JPanel jabberServerPanel = new JPanel();
        jabberServerPanel.setOpaque(false);
        jabberServerPanel.setLayout(new BorderLayout());
        jabberServerPanel.add(jabberServer, BorderLayout.NORTH);
        // label
        JPanel jabberServerLabel = new JPanel();
        jabberServerLabel.setLayout(new BorderLayout());
        jabberServerLabel.setOpaque(false);
        jabberServerLabel.add(new JLabel("Jabber server:  "),
                BorderLayout.NORTH);

        serverPanel = new JPanel();
        serverPanel.setLayout(new BorderLayout());
        serverPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 5));
        serverPanel.setOpaque(false);
        serverPanel.add(jabberServerLabel, BorderLayout.WEST);
        serverPanel.add(jabberServerPanel, BorderLayout.CENTER);

        // set username
        JPanel usernamePanel = new JPanel();
        usernamePanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        usernamePanel.setOpaque(false);
        UNFieldGuest = new JTextField();
        UNFieldGuest.setPreferredSize(new Dimension(220, 20));
        JLabel usernameLabel = new JLabel("Username:     ");
        usernameLabel.setForeground(model.primaryTextColor);
        usernamePanel.add(usernameLabel);
        usernamePanel.add(UNFieldGuest);
        // set password
        JPanel passwordPanel = new JPanel();
        passwordPanel.setOpaque(false);
        PwdFieldGuest = new JPasswordField();
        PwdFieldGuest.setPreferredSize(new Dimension(220, 20));
        JLabel passwordLabel = new JLabel("Password:      ");
        passwordLabel.setForeground(model.primaryTextColor);
        passwordPanel.add(passwordLabel);
        passwordPanel.add(PwdFieldGuest);

        // account setup Panel
        JPanel setupPanel = new JPanel();
        setupPanel.setOpaque(false);
        setupPanel.setLayout(new BoxLayout(setupPanel, BoxLayout.Y_AXIS));
        setupPanel.add(server);
        setupPanel.add(serverPanel);
        setupPanel.add(usernamePanel);
        setupPanel.add(passwordPanel);

        /* BOTTOM PART: OK + CANCEL BUTTONs */
        JButton okButton =
                new JButton("OK", new ImageIcon(this.getClass().getResource(
                        "/images/buddylist/button_ok.png")));
        okButton.addActionListener(new okButtonActionListener());
        JButton cancelButton =
                new JButton("Cancel", new ImageIcon(this.getClass()
                        .getResource("/images/mainwindow/cancel.png")));
        cancelButton.addActionListener(new cancelButtonActionListener());
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setOpaque(false);
        buttonsPanel.add(okButton);
        buttonsPanel.add(cancelButton);

        // adding to rightPanel
        GALPanel.setPreferredSize(new Dimension(280, 400));
        GALPanel.setBorder(BorderFactory.createEmptyBorder(25, 40, 30, 40));
        GALPanel.add(setupPanel, BorderLayout.NORTH);
        GALPanel.add(buttonsPanel, BorderLayout.SOUTH);

        // add to account manager pop up main panel
        add(GALPanel, BorderLayout.EAST);

        getContentPane().add(GALPanel);
        pack();
        setVisible(true);
        setIconImage(new ImageIcon("src/images/mainwindow/logo.png").getImage());

    }

    /**
     * This method is called when the guest login into parrot-im. The user will
     * allow to login to Google talk account only.
     * 
     * @throws ClassNotFoundException
     */
    private void signIn_ActionPerformed() throws ClassNotFoundException {
        ServerType serverType = (ServerType) server.getSelectedItem();
        String username = UNFieldGuest.getText();
        String password = String.copyValueOf(PwdFieldGuest.getPassword());

        try {
            if (serverType == ServerType.GOOGLE_TALK
                    || serverType == ServerType.TWITTER
                    || serverType == ServerType.MSN
                    || serverType == ServerType.ICQ
                    || serverType == ServerType.AIM) {

                // depends on the ICQManager implementation
                // || serverType == ServerType.ICQ
                // || serverType == ServerType.AIM) {
                if (serverType == ServerType.AIM) {
                    serverType = ServerType.ICQ;
                }
                core.loginAsGuest(serverType, username, password);
            } else if (serverType == ServerType.JABBER) {
                core.loginAsGuest(serverType, username, password, jabberServer
                        .getText());
            } else {
                String resultMessage =
                        "We are only support XMPP, Twitter, MSN, ICQ, and AIM. Sorry for the inconvenience.";
                JOptionPane.showMessageDialog(null, resultMessage,
                        "Information", JOptionPane.INFORMATION_MESSAGE);
                throw new BadConnectionException();
            }
            new BuddyList(core, model, mainFrame.getLocation());// pops
            // buddylist
            // window
            mainFrame.dispose(); // TODO: consider if the sign in fails
        } catch (BadConnectionException e1) {
            // e1.printStackTrace();
            mainPanel.header.displaySystemStatus("Sign in failed!");
            mainFrame.setEnabled(true);
            System.out.println("sign in failed!");

        }

    }

    private class serverListener implements ItemListener {

        public void itemStateChanged(ItemEvent e) {
            if (server.getSelectedIndex() == 0) {
                serverPanel.setVisible(true);
            } else {
                serverPanel.setVisible(false);
            }
        }

    }

    private class okButtonActionListener implements ActionListener {

        /**
         * When the OK button is clicked, the system will try to connect to the
         * server
         * 
         * @param evt
         */
        public void actionPerformed(ActionEvent evt) {
            if (UNFieldGuest.getText().length() != 0
                    && PwdFieldGuest.getPassword().length != 0) {
                setVisible(false);
                try {
                    signIn_ActionPerformed();
                } catch (ClassNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            else {
                String resultMessage =
                        "Please provide appropriate user ID and password in the field.";
                JOptionPane.showMessageDialog(null, resultMessage);

            }
        }
    }

    private class cancelButtonActionListener implements ActionListener {
        /**
         * When the CANCEL button is clicked, then go back to MainWindow
         * 
         * @param evt
         */
        public void actionPerformed(ActionEvent evt) {
            popup.removeAll();
            popup.dispose();
        }
    }

    public void update(Observable o, Object arg) {
        if (arg == UpdatedType.COLOR) {
            GALPanel
                    .setGradientColors(model.primaryColor, model.secondaryColor);
            GALPanel.updateUI();
        }
    }
}