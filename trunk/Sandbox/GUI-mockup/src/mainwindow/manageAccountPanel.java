package mainwindow;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Scrollbar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
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

public class manageAccountPanel extends JPanel {
	protected Vector <String> account_list;
	JButton manageAccount;
	JButton guestAccount;
	
	public manageAccountPanel (){
		setLayout(new GridLayout(4,1));
		setBorder(BorderFactory.createEmptyBorder(0, 50, 20, 50));
		
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
		add(account_select);
		add(manageAccount);
		add(guestAccount);
		add(connectButton);

	}
	public void manageAccount_ActionPerformed(ActionEvent e) {
		JFrame accMAN = new JFrame ("Account Manager");
		//accMAN.getContentPane().add(new accountManager());
		
		setLayout(new BorderLayout());
		JList accountList = new JList(account_list);
		account_list.add("added");
		accMAN.add (new JLabel("Hello world"));
		
		accMAN.pack();
		accMAN.setVisible(true);
	} 
	public void guestAccount_ActionPerformed(ActionEvent e) {
		//set Frame
		JFrame GAL = new JFrame ("Guest Account Login");
		GAL.setPreferredSize(new Dimension (400,200));
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
		accountInfoPanel.add(new JLabel("Username:"));
		accountInfoPanel.add(new JTextField());
		//set password
		accountInfoPanel.add(new JLabel("Password:"));
		accountInfoPanel.add(new JPasswordField());
		
		//set ok-cancel button
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setBorder(BorderFactory.createEmptyBorder(10, 50, 10, 50));
		GridLayout buttonsLayout = new GridLayout(1,2);
		buttonsLayout.setHgap(10);
		buttonsPanel.setLayout(buttonsLayout);
		//OK Button
		JButton okButton = new JButton ("OK");
		buttonsPanel.add(okButton);
		//Cancel Button
		JButton cancelButton = new JButton ("Cancel");
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
	
	public void connectButton_ActionPerformed(ActionEvent e) {
		//TODO:set this
		
	     
	} 
}
