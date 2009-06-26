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
 *         
 * Known Issues:
 *     None
 * 
 * Copyright (C) 2009  Pirate Captains
 * 
 * Full license can be found in ParrotIM/LICENSE.txt.
 */

package view.mainwindow;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import model.Model;
import model.dataType.AccountData;
import model.dataType.GoogleTalkUserData;
import model.enumerations.ServerType;

import org.jivesoftware.smack.XMPPException;

import controller.MainController;

import view.buddylist.BuddyList;
import view.styles.PopupWindowListener;

/**
 * The container frame of the guest account. User can use this frame 
 * to log in parrot-im as a guest.
 * 
 * This object inherits JFrame variables and methods
 */
public class GuestAccountFrame extends JFrame {

    /** model stores the needed data of the system. It also connects it with database */
	private Model model;
    
	/**
	 * core is a MainController object. It helps the user to login.
	 */
	private MainController core;
    
    /** mainFrame is a MainWindow object which is a container of this panel. */
    private MainWindow mainFrame;
    
    /** popup is this JFrame object */
    private JFrame popup;
    
    /** mainPanel is a SignInPanel object. It sets the GUI component of MainWindow.*/
    private SignInPanel mainPanel;

    /** UNFieldGuest is a JTextField object. It is the text field for the user's username. */
    private JTextField UNFieldGuest;
    
    /** PwdFieldGuest is a JPasswordField object. It is the text field for the user's password. */
    private JPasswordField PwdFieldGuest;
    
    /** server is a JComboBox object. It shows the listed server the user can connect to. */
    private JComboBox server;

    /**
     * This is the constructor of the Guest Account frame.
     * @param model
     * @param c
     * @param frame
     * @param signin
     */
    public GuestAccountFrame(Model model, MainController c, MainWindow frame,
            SignInPanel signin) {
    	this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setAlwaysOnTop(true);
        popup = this;
        this.model = model;
        core = c;
        mainFrame = frame;
        mainPanel = signin;
        this.addWindowListener(new PopupWindowListener(mainFrame, this));

        // set Frame
        setTitle("Guest Account Login");
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(400, 250));
        setLocation(100, 100);
        setResizable(false);
        setIconImage(new ImageIcon("imagesimage/mainwindow/logo.png")
                .getImage());

        // select server
        server = new JComboBox(model.getServerList());
        server.setPreferredSize(new Dimension(200, 20));
        server.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        // username + password
        JPanel accountInfoPanel = new JPanel();
        // set username
        JPanel usernamePanel = new JPanel();
        UNFieldGuest = new JTextField();
        UNFieldGuest.setPreferredSize(new Dimension(200, 20));
        usernamePanel.add(new JLabel("Username:     "));
        usernamePanel.add(UNFieldGuest);
        accountInfoPanel.add(usernamePanel);
        // set password
        PwdFieldGuest = new JPasswordField();
        PwdFieldGuest.setPreferredSize(new Dimension(200, 20));
        accountInfoPanel.add(new JLabel("Password:      "));
        accountInfoPanel.add(PwdFieldGuest);

        // set ok-cancel button
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setPreferredSize(new Dimension(20, 90));
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(15, 40, 15, 40));
        GridLayout buttonsLayout = new GridLayout(1, 2);
        buttonsLayout.setHgap(5);
        buttonsPanel.setLayout(buttonsLayout);
        // OK Button
        JButton okButton = new JButton("OK");
        okButton.setPreferredSize(new Dimension(20, 40));
        okButton.addActionListener(new ActionListener() {
        	/**
        	 * When the OK button is clicked, the system will try to connect to the server
        	 * @param evt
        	 */
            public void actionPerformed(ActionEvent evt) {
                if (UNFieldGuest.getText().length() != 0
                        && PwdFieldGuest.getPassword().length != 0) {
                    setVisible(false);
                    signIn_ActionPerformed(evt);
                }
                
                else {
                	String resultMessage = "Please provide appropriate user ID and password in the field.";
                	JOptionPane.showMessageDialog(null, resultMessage);
                	
                }
            }
        });
        buttonsPanel.add(okButton);
        // Cancel Button
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setPreferredSize(new Dimension(20, 40));
        cancelButton.addActionListener(new ActionListener() {
        	/**
        	 * When the CANCEL button is clicked, then go back to MainWindow
        	 * @param evt
        	 */
            public void actionPerformed(ActionEvent evt) {
                popup.removeAll();
                popup.dispose();
            }
        });
        buttonsPanel.add(cancelButton);

        // set main panel
        JPanel GALPanel = new JPanel();
        GALPanel.setLayout(new GridLayout(3, 1));
        GALPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        GALPanel.add(server); // server dropdown menu
        GALPanel.add(accountInfoPanel); // username+password
        GALPanel.add(buttonsPanel); // ok+cancel buttons

        getContentPane().add(GALPanel);
        pack();
        setVisible(true);

    }
    /**
     * This method is called when the guest login into parrot-im. The user will allow to login
     * to Google talk account only.
     * @param e
     */
    private void signIn_ActionPerformed(ActionEvent e) {
        ServerType serverType = (ServerType) server.getSelectedItem();
        String username = UNFieldGuest.getText();
        String password = password(PwdFieldGuest.getPassword());

        if (serverType.toString().equals("Google Talk")) {
        	 try {
                 core.login(serverType, username, password);

                 new BuddyList(core, model);// pops buddylist window
                 mainFrame.dispose(); // TODO: consider if the sign in fails
             } catch (XMPPException e1) {
                 // e1.printStackTrace();
                 mainPanel.header.loadMain();
                 mainFrame.setEnabled(true);
                 System.out.println("sign in failed!");
             }
        	
        }
           
        else {
        	String resultMessage = "Sorry for the inconvenience but for the Alpha Version, we are only supporting XMPP Protocol. Thank you for your co-operation.";
        	JOptionPane.showMessageDialog(null, resultMessage);
        	mainFrame.setEnabled(true);
        }
       
    }
    
    /**
     * This method is used to get the String of password from the user.
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