/* SignInPanel.java
 * 
 * Programmed By:
 *     Vera Lukman
 *     Kevin Fahy
 *     Ahmad Sidiqi
 *     William Chen
 *     
 * Change Log:
 *     2009-June-4, VL
 *         Initial write to increase modularity.
 *     2009-June-5, KF
 *         Integrated with model.
 *     2009-June-6, KF
 *         Integrated core with model.
 *     2009-June-6, VL
 *         Changed the type of Label to LinkLabel.
 *         The label will be underlined when hovered.
 *         Moved ManageAccount into a ManageAccountFrame.java, and 
 *         GuestAccount into GuestAccountFrame.java.
 *     2009-June-8, AS
 *         Integrated with database.
 *     2009-June-9, KF
 *         Edited the sign-in page to be consistent with the guest sign in 
 *         login. This sign-in needs to account for multiple accounts, 
 *         not implemented yet.
 *     2009-June-10, VL
 *         Changed some layout so that the account options don't move
 *         when the window is resized
 *     2009-June-13, WC
 *         Transferred file over to new project, ParrotIM.
 *     2009-June-15, KF
 *         Started to implement a MainController class to handle all protocol.
 *     2009-June-19, KF
 *         Added Jabber functionality.
 *     2009-June-22, KF
 *         Relayout database.
 *     2009-June-23, KF
 *         Naming convention updates. Changed all class names.
 *     2009-June-24, VL
 *         Fixed multiple add profile/account windows. Not fully fixed yet.
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
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import model.Model;
import model.enumerations.UpdatedType;

import controller.MainController;
import controller.services.BadConnectionException;

import view.styles.LinkLabel;
import view.styles.PopupWindowListener;

import view.buddylist.BuddyList;

/**
 * Sets the GUI component of MainWindow.
 * 
 * This class inherits JPanel methods and variables.
 */
public class SignInPanel extends JPanel implements Observer {
    /**
     * core is a MainController object. It helps the user to create a new
     * profile and login using the existing account.
     */
    protected MainController core;

    /** 
     * mainFrame is a MainWindow object which is a container of this panel. */
    private MainWindow mainFrame;

    /**
     * model stores the needed data of the system. It also connects it with
     * database
     */
    private Model model;

    /** 
     * account_select is a JComboBox object. It shows the listed saved profiles */
    private JComboBox account_select;

    // part of the whole panel
    /**
     * signin is this SignInPanel object. Used for some objects constructor
     * argument.
     */
    protected SignInPanel signin;

    /**
     * header is a HeaderPanel object which extends JPanel. It sets the top part
     * of the MainWindow, which includes the avatar and the status of the
     * system.
     */
    protected HeaderPanel header;

    /**
     * accPanel is a JPanel object. It sets the center part of the MainWindow,
     * which includes account_select JComboBox, manageAccount LinkLabel, and
     * guestAccount LinkLabel.
     */
    protected JPanel accPanel;

    /**
     * misc is a JPanel object. It sets the bottom part of the MainWindow, which
     * includes a separator and help LinkLabel.
     */
    protected MiscPanel misc;
    
    private LinkLabel manageAccount;
    
    private LinkLabel removeProfile;

    private JButton connectButton;
    
    private int lastSelectedIndex;
    
    /**
     * SignInPanel constructor.It takes a Model, MainController, and MainWindow
     * object as arguments. It sets up the panel.
     * 
     * @param model
     * @param chatClient
     * @param frame
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public SignInPanel(MainWindow frame, MainController chatClient, Model model) {
        mainFrame = frame;
        core = chatClient;// CORE
        this.model = model;
        this.model.addObserver(this);
        signin = this;

        setLayout(new BorderLayout());
        manageAccountPanel();

        header = new HeaderPanel();
        misc = new MiscPanel();
        add(header, BorderLayout.NORTH);
        add(misc, BorderLayout.SOUTH);
    }

    /**
     * manageAccountPanel manages accPanel JPanel. It sets the center part of
     * the MainWindow, which includes account_select JComboBox, manageAccount
     * LinkLabel, and guestAccount LinkLabel.
     * 
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    private void manageAccountPanel() {
        accPanel = new JPanel();
        FlowLayout accLayout = new FlowLayout();
        accLayout.setAlignment(FlowLayout.CENTER);
        accPanel.setLayout(accLayout);
        accPanel.setBorder(BorderFactory.createEmptyBorder(5, 50, 10, 50));

        // list of accounts
        account_select = new JComboBox(model.getProfileList());
        account_select.addActionListener((new AccountSelectItemListener()));
        account_select.setAlignmentY(Component.CENTER_ALIGNMENT);
        // connect button
        JPanel connectPanel = new JPanel();
        connectButton = new JButton("Sign In");
        connectButton.setEnabled(false);
        connectButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        connectPanel.add(connectButton);
        connectButton.addActionListener(new signInButtonActionListener());

        // accOPTPanel (part of accPanel)
        JPanel accOptPanel = new JPanel();
        accOptPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        accOptPanel.setLayout(new BoxLayout(accOptPanel, BoxLayout.Y_AXIS));

        // create account
        LinkLabel createProfile = new LinkLabel("Create New Profile", true);
        createProfile.addMouseListener(new createProfileListener());
        
        // manage account
        manageAccount = new LinkLabel("Manage Profile", false);
        manageAccount.addMouseListener(new manageProfileListener());

        // remove account
        removeProfile = new LinkLabel("Remove Profile", false);
        removeProfile.addMouseListener(new removeProfileListener());
        
        // guest account
        LinkLabel guestAccount = new LinkLabel("Connect Guest Account", true);
        guestAccount.setBorder(BorderFactory.createEmptyBorder(10,0,10,0));
        guestAccount.addMouseListener(new guestAccountListener());

        // add components to accOptPanel
        accOptPanel.add(createProfile);
        accOptPanel.add(manageAccount);
        accOptPanel.add(removeProfile);
        accOptPanel.add(guestAccount);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(account_select, BorderLayout.NORTH);
        panel.add(accOptPanel, BorderLayout.CENTER);
        panel.add(connectPanel, BorderLayout.SOUTH);

        // add components to accPanel
        accPanel.add(panel, BorderLayout.NORTH);

        add(accPanel, BorderLayout.CENTER);
    }

    /**
     * signIn_ActionPerformed is a function that helps the user to sign in to
     * the server
     * 
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    private void guestSignIn() throws ClassNotFoundException,
            SQLException {
        String username = (String) account_select.getSelectedItem();

        try {
            // Login with server and set model info
            
            // TODO think of how to implement profile password
            core.loginProfile(username);

            // Handle the GUI changes
            new BuddyList(core, model);
            mainFrame.dispose();
        } catch (BadConnectionException e1) {
            header.displaySystemStatus("Sign in failed!");
            System.out.println("sign in failed!");
            mainFrame.setEnabled(true);
        }
    }

    /**
     * Links the MainWindow with the observer.
     * 
     * @param t
     * @param o
     */
    public void update(Observable o, Object arg) {
        if (arg == UpdatedType.PROFILE) {
            this.account_select.removeAllItems();
            for (String s : model.getProfileList()) {
                this.account_select.addItem(s);
            }
        }

        return;
    }
    private class signInButtonActionListener implements ActionListener{
    	/**
         * When the sign in button is clicked, the accounts of the currently
         * chosen profile will be connected to the server.
         * 
         * @param evt
         */
        public void actionPerformed(ActionEvent evt) {
            try {
            	guestSignIn();
            } catch (ClassNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
    private class manageProfileListener implements MouseListener{

    	/** 
    	 * if manageAccount is clicked, it will launched the Profile
         * Manager
         * 
         * @param evt
         */
        public void mouseClicked(MouseEvent evt) {
        	if (manageAccount.isEnabled()){
        		System.out.println("enabled");
				try {
					ManageAccountFrame manageAccount = 
						new ManageAccountFrame(model, core, account_select.getSelectedItem().toString());
					manageAccount.addWindowListener(new PopupWindowListener(mainFrame, manageAccount));
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        		

        	}
        		
        }

		public void mouseEntered(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}
		public void mousePressed(MouseEvent e) {}
		public void mouseReleased(MouseEvent e) {}
    }
    private class createProfileListener implements MouseListener{

    	/**
    	 * if createProfile is clicked, it will launched the Profile
         * creator wizard
         * 
         * @param evt
         */
        public void mouseClicked(MouseEvent evt) {
            //TODO: set this
        	NewProfileFrame profile = new NewProfileFrame(model, core, mainFrame);
        	profile.addWindowListener(new PopupWindowListener(mainFrame, profile));
        }

		public void mouseEntered(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}
		public void mousePressed(MouseEvent e) {}
		public void mouseReleased(MouseEvent e) {}
    }
    private class removeProfileListener implements MouseListener{

    	/**
         * if removeProfile is clicked, it will prompt for password and verify
         * 
         * @param evt
         */
        public void mouseClicked(MouseEvent evt) {
        	if (removeProfile.isEnabled()){
        		System.out.println("enabled");
        		model.removeProfile(account_select.getSelectedItem().toString());
        	}
        }

		public void mouseEntered(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}
		public void mousePressed(MouseEvent e) {}
		public void mouseReleased(MouseEvent e) {}
    }
    private class guestAccountListener implements MouseListener{

    	public void mouseClicked(MouseEvent evt) {
            new GuestAccountFrame(model, core, mainFrame, signin);
        }
		public void mouseEntered(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}
		public void mousePressed(MouseEvent e) {}
		public void mouseReleased(MouseEvent e) {}
    	
    }
	private class AccountSelectItemListener implements ActionListener{
		
		public AccountSelectItemListener(){
			lastSelectedIndex = account_select.getSelectedIndex();
		}
		
		public void actionPerformed(ActionEvent e) {
			int selectedIndex = account_select.getSelectedIndex();
			if (selectedIndex>0 && lastSelectedIndex != selectedIndex){
				System.out.println("Checking...");
				new SimplifiedPasswordPrompt(account_select.getSelectedItem().toString());
			} else if (selectedIndex <=0 ){
				manageAccount.setEnabled(false);
				removeProfile.setEnabled(false);
				connectButton.setEnabled(false);
			}
			
		}
	}
	
	private class SimplifiedPasswordPrompt extends JFrame{
		private JFrame passwordFrame;
		private JPasswordField passwordPrompt;
		private JButton OKbutton;
		
		/**
		 * asks for password
		 * use this for deleting the account
		 * @param profileName
		 * */
		public SimplifiedPasswordPrompt (String profileName){
			passwordFrame = this;
		
			setIconImage(new ImageIcon("src/images/mainwindow/logo.png").getImage());
			
			//password prompt
			passwordPrompt = new JPasswordField();
			passwordPrompt.addKeyListener(new passwordKeyListener());
			passwordPrompt.setPreferredSize(new Dimension(355, 20));
			JPanel passwordPanel = new JPanel();
			passwordPanel.add(passwordPrompt);
		
			//buttons
			OKbutton = new JButton ("OK");
			OKbutton.addActionListener(new OKActionListener());
			OKbutton.setEnabled(false);
			JButton cancelButton = new JButton ("Cancel");
			cancelButton.addActionListener(new cancelActionListener());
			JPanel buttonsPanel = new JPanel ();
			buttonsPanel.add (OKbutton);
			buttonsPanel.add(cancelButton);
			
			//panel
			JPanel mainPanel = new JPanel();
			mainPanel.setLayout(new BorderLayout());
			mainPanel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
			mainPanel.add(new JLabel("Enter password for " + profileName + "\'s profile:"), BorderLayout.NORTH);
			mainPanel.add(passwordPanel, BorderLayout.CENTER);
			mainPanel.add(buttonsPanel, BorderLayout.SOUTH);
			
			//frame
			this.addWindowListener(new PopupWindowListener(mainFrame, passwordFrame));
			this.setLocationRelativeTo(null);
			this.setResizable(false);
			this.setPreferredSize(new Dimension(400,150));
			this.getContentPane().add(mainPanel);
			this.pack();
			this.setVisible(true);
		}

		private class passwordKeyListener implements KeyListener{

			public void keyPressed(KeyEvent e) {}
			public void keyReleased(KeyEvent e) {
				if (passwordPrompt.getPassword().length > 0)
					OKbutton.setEnabled(true);
				else
					OKbutton.setEnabled(false);
			}

			public void keyTyped(KeyEvent e) {
				
			}
		}
		private class cancelActionListener implements ActionListener{

			public void actionPerformed(ActionEvent e) {
				account_select.setSelectedIndex(0);
				lastSelectedIndex = 0;
				passwordFrame.dispose();
			}
		}
		private class OKActionListener implements ActionListener{

			public void actionPerformed(ActionEvent e) {
				//if (password is correct) {
					//enables
				
				
					System.out.println("password is correct");
					lastSelectedIndex = account_select.getSelectedIndex();
					passwordFrame.dispose();
					manageAccount.setEnabled(true);
				    removeProfile.setEnabled(true);
				    connectButton.setEnabled(true);
				    
				//else {
					//gives warning
					//cannot select that profile
					//account_select.setSelectedIndex(0);
				//}
			}
		}
	}
    
}
