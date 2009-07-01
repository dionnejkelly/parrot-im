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

import controller.MainController;
import controller.services.BadConnectionException;

import view.buddylist.BuddyList;
import view.styles.PopupWindowListener;

/**
 * The container frame of the guest account. User can use this frame to log in
 * parrot-im as a guest.
 * 
 * This object inherits JFrame variables and methods
 */
public class GuestAccountFrame extends JFrame {

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

    /**
     * This is the constructor of the Guest Account frame.
     * 
     * @param model
     * @param c
     * @param frame
     * @param signin
     */
    public GuestAccountFrame(
            Model model, MainController c, MainWindow frame,
            SignInPanel signin) {
        popup = this;
        this.model = model;
        core = c;
        mainFrame = frame;
        setLocationRelativeTo(mainFrame);
        mainPanel = signin;
        this.addWindowListener(new PopupWindowListener(mainFrame, this));

        // set Frame
        setTitle("Guest Account Login");
//        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(400, 250));
        setResizable(false);
        setIconImage(new ImageIcon("imagesimage/mainwindow/logo.png")
                .getImage());

        // select server
        JPanel GALPanel = new JPanel();
		GALPanel.setLayout(new BorderLayout());
        // select server
        server = new JComboBox(model.getServerList());
        server.setPreferredSize(new Dimension(200, 30));
        server.addItemListener(new serverListener());
        //server name for jabber
        //textfield
        jabberServer = new JTextField();
        jabberServer.setPreferredSize(new Dimension(200, 20));
        jabberServer.setToolTipText("specify jabber server");
        JPanel jabberServerPanel = new JPanel();
        jabberServerPanel.setLayout(new BorderLayout());
        jabberServerPanel.add(jabberServer, BorderLayout.NORTH);
        //label
        JPanel jabberServerLabel = new JPanel();
        jabberServerLabel.setLayout(new BorderLayout());
        jabberServerLabel.add(new JLabel("Jabber server:  "), BorderLayout.NORTH);

        serverPanel = new JPanel();
        serverPanel.setLayout(new BorderLayout());
        serverPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 5));
        serverPanel.add(jabberServerLabel, BorderLayout.WEST);
        serverPanel.add(jabberServerPanel, BorderLayout.CENTER);

        // set username
        JPanel usernamePanel = new JPanel();
        usernamePanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        UNFieldGuest = new JTextField();
        UNFieldGuest.setPreferredSize(new Dimension(180, 20));
        usernamePanel.add(new JLabel("Username:     "));
        usernamePanel.add(UNFieldGuest);
        // set password
        JPanel passwordPanel = new JPanel();
        PwdFieldGuest = new JPasswordField();
        PwdFieldGuest.setPreferredSize(new Dimension(180, 20));
        passwordPanel.add(new JLabel ("Password:      "));
        passwordPanel.add(PwdFieldGuest);

		//account setup Panel
		JPanel setupPanel = new JPanel();
		setupPanel.setLayout(new BoxLayout (setupPanel, BoxLayout.Y_AXIS));
		setupPanel.add(server);
		setupPanel.add(serverPanel);
		setupPanel.add(usernamePanel);
		setupPanel.add(passwordPanel);


		/*BOTTOM PART: OK + CANCEL BUTTONs*/
		JButton okButton = new JButton("OK");
		okButton.addActionListener(new okButtonActionListener());
		JButton cancelButton = new JButton ("Cancel");
		cancelButton.addActionListener(new cancelButtonActionListener());
		JPanel buttonsPanel = new JPanel ();
		buttonsPanel.setBorder(BorderFactory.createEmptyBorder(0,90,0,0));
		buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.X_AXIS));
		buttonsPanel.add(okButton);
		buttonsPanel.add(cancelButton);


		//adding to rightPanel
		GALPanel.setPreferredSize(new Dimension(280, 400));
		GALPanel.setBorder(BorderFactory.createEmptyBorder(25, 40, 30, 40));
		GALPanel.add(setupPanel, BorderLayout.NORTH);
		GALPanel.add(buttonsPanel, BorderLayout.SOUTH);

		//add to account manager pop up main panel
		add(GALPanel,BorderLayout.EAST);
        
        getContentPane().add(GALPanel);
        pack();
        setVisible(true);

    }

    /**
     * This method is called when the guest login into parrot-im. The user will
     * allow to login to Google talk account only.
     */
    private void signIn_ActionPerformed() {
        ServerType serverType = (ServerType) server.getSelectedItem();
        String username = UNFieldGuest.getText();
        String password = password(PwdFieldGuest.getPassword());

        if (serverType == ServerType.GOOGLE_TALK) {
            try {
                core.loginAsGuest(serverType, username, password);

                new BuddyList(core, model);// pops buddylist window
                mainFrame.dispose(); // TODO: consider if the sign in fails
            } catch (BadConnectionException e1) {
                // e1.printStackTrace();
                mainPanel.header.displaySystemStatus("Sign in failed!");
                mainFrame.setEnabled(true);
                System.out.println("sign in failed!");
            }

        } else if (serverType == ServerType.JABBER) {
            try {
                core.loginAsGuest(serverType, username, password);
                new BuddyList(core, model);// pops buddylist window
                mainFrame.dispose(); // TODO: consider if the sign in fails
            } catch (BadConnectionException e1) {
                // e1.printStackTrace();
            	mainPanel.header.displaySystemStatus("Sign in failed!");
                mainFrame.setEnabled(true);
                System.out.println("sign in failed!");
            }
        } else {
            String resultMessage =
                    "We are only supporting XMPP Protocol for the Alpha Version. Sorry for the inconvenience.";
            JOptionPane.showMessageDialog(null, resultMessage);
            mainFrame.setEnabled(true);
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
    
    private class serverListener implements ItemListener{

		public void itemStateChanged(ItemEvent e) {
			if (server.getSelectedIndex()==0){
				serverPanel.setVisible(true);
			}else{
				serverPanel.setVisible(false);
			}
		}
    	
    }
    
    private class okButtonActionListener implements ActionListener{

		/**
         * When the OK button is clicked, the system will try to connect to
         * the server
         * 
         * @param evt
         */
        public void actionPerformed(ActionEvent evt) {
            if (UNFieldGuest.getText().length() != 0
                    && PwdFieldGuest.getPassword().length != 0) {
                setVisible(false);
                signIn_ActionPerformed();
            }

            else {
                String resultMessage =
                        "Please provide appropriate user ID and password in the field.";
                JOptionPane.showMessageDialog(null, resultMessage);

            }
        }
    }
    
    private class cancelButtonActionListener implements ActionListener{
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
}