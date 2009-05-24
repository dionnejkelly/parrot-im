package mainwindow;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class mainwindow {
	private static Vector <String> account_list;
	private static JButton manageAccount;
	private static JButton guestAccount;
	private static JPanel accPanel;
	//Account Manager variables
	private static JFrame GAL;
	private static JTextField UNField;
	private static JPasswordField PwdField;
	
	public static void main (String [] args){
		//set Main Window Frame
		JFrame frame = new JFrame ("Parrot-IM");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension (300,500));
		frame.setResizable(false);
		//frame.setMinimumSize(new Dimension (300,500)); // how to set the minimum size to be resized?
		
		//set signin Panel
		JPanel signinPanel = new JPanel ();
		signinPanel.setLayout (new BorderLayout());
		manageAccountPanel();
		
		signinPanel.add(new headerPanel(), BorderLayout.NORTH);
		signinPanel.add(accPanel, BorderLayout.CENTER);
		signinPanel.add(new miscPanel(), BorderLayout.SOUTH);
		frame.getContentPane().add(signinPanel);
		
		frame.pack();
		frame.setVisible(true);
	}
	
	public static void manageAccountPanel (){
		accPanel = new JPanel ();
		accPanel.setLayout(new GridLayout(4,1));
		accPanel.setBorder(BorderFactory.createEmptyBorder(0, 50, 20, 50));
		
		//list of accounts
		account_list = new Vector<String>();
		account_list.add("msn: test@hotmail.com");
		account_list.add("aim: test");
		account_list.add("twitter: test@sth");
		account_list.add("icq: 712859");
		account_list.add("connect all");
		JComboBox account_select = new JComboBox (account_list);
		
		//connect button
		JButton connectButton = new JButton("Sign In");
		//somehow try to set the button size
		connectButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                connectButton_ActionPerformed(evt);
            }
        });
		
		//manage account
		manageAccount = new JButton ("Add/Manage Accounts"); //underline?
		manageAccount.setBorderPainted(false);
		manageAccount.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                manageAccount_ActionPerformed(evt);
            }
        });
		manageAccount.setBorder(BorderFactory.createEmptyBorder(30,0,20,0));
		
		//guest account
		guestAccount = new JButton ("Connect Guest Account"); //underline?
		guestAccount.setBorderPainted(false);
		guestAccount.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                guestAccount_ActionPerformed(evt);
            }
        });
		guestAccount.setBorder(BorderFactory.createEmptyBorder(20,0,30,0));
		
		//add components
		accPanel.add(account_select);
		accPanel.add(manageAccount);
		accPanel.add(guestAccount);
		accPanel.add(connectButton);

	}
	private static void manageAccount_ActionPerformed(ActionEvent e) {
		JFrame accMAN = new JFrame ("Account Manager");
		accMAN.setLocation(100, 100);
		accMAN.setResizable(false);
	
		//saved account list
		JList accList = new JList(account_list);
		account_list.add("added");
		accMAN.add (new JLabel("Hello world"));
		
		//set main panel
		JPanel accPanel = new JPanel ();
		accPanel.setLayout(new GridLayout(3,1));
		accPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
		
		accMAN.getContentPane().add(accPanel);
		accMAN.pack();
		accMAN.setVisible(true);
	} 
	private static void guestAccount_ActionPerformed(ActionEvent e) {
		//set Frame
		GAL = new JFrame ("Guest Account Login");
		GAL.setPreferredSize(new Dimension (400,250));
		GAL.setLocation(100, 100);
		GAL.setResizable(false);
		
		//select server
		Vector<String> serverList = new Vector<String>();
		serverList.add ("msn");
		serverList.add ("aim");
		serverList.add ("twitter");
		serverList.add ("icq");
		JComboBox server = new JComboBox (serverList);
		
		//username + password
		JPanel accountInfoPanel = new JPanel();
		GridLayout accountLayout = new GridLayout(2,2);
		accountLayout.setVgap(5);
		accountInfoPanel.setLayout(accountLayout);
		//set username
		UNField = new JTextField();
		accountInfoPanel.add(new JLabel("Username:"));
		accountInfoPanel.add(UNField);
		//set password
		PwdField = new JPasswordField();
		accountInfoPanel.add(new JLabel("Password:"));
		accountInfoPanel.add(PwdField);
		
		//set ok-cancel button
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 10, 10));
		GridLayout buttonsLayout = new GridLayout(1,2);
		buttonsLayout.setHgap(5);
		buttonsPanel.setLayout(buttonsLayout);
		//OK Button
		JButton okButton = new JButton ("OK");
		okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	okButtonGAL_ActionPerformed(evt);
            }
		});
		buttonsPanel.add(okButton);
		//Cancel Button
		JButton cancelButton = new JButton ("Cancel");
		cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	cancelButtonGAL_ActionPerformed(evt);
            }
		});
		buttonsPanel.add(cancelButton);
		
		//set main panel
		JPanel GALPanel = new JPanel();
		GALPanel.setLayout(new GridLayout(3,1));
		GALPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
		GALPanel.add (server);
		GALPanel.add(accountInfoPanel);
		GALPanel.add (buttonsPanel);
		
		GAL.getContentPane().add(GALPanel);
		GAL.pack();
		GAL.setVisible(true);
		
	} 
	
	private static void connectButton_ActionPerformed(ActionEvent e) {
		//TODO:set this
		
	     
	} 
	
	private static void okButtonGAL_ActionPerformed(ActionEvent e) {
		
		if (UNField.getText().length() != 0 && PwdField.getPassword().length != 0)
			GAL.setVisible(false);
		
	} 
	private static void cancelButtonGAL_ActionPerformed(ActionEvent e) {
		GAL.setVisible(false);
		
	} 
}

