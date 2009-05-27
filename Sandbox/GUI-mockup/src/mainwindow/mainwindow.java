package mainwindow;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class mainwindow extends JFrame{
	private Vector<String> account_list;
	private Vector<String> serverList;
	private JPanel signinPanel;
	//Account Options part (in Sign In Panel)
	private JButton manageAccount;
	private JButton guestAccount;
	//Account Manager pop-up window
	private JFrame accMAN;
	private JPanel accPanel;
	private JList accList;
	private JTextField UNField; 	//Account Manager - rightPanelMAN
	private JPasswordField pwdField;//Account Manager - rightPanelMAN
	private JComboBox serviceField;	//Account Manager - rightPanelMAN
	//Guest Account login pop-up window
	private JFrame GAL;
	
	public mainwindow (){
		//set server list
		serverList = new Vector<String>();
		serverList.add ("msn");
		serverList.add ("aim");
		serverList.add ("twitter");
		serverList.add ("icq");
		
		//list of accounts
		account_list = new Vector<String>();
		account_list.add("msn: test@hotmail.com");
		account_list.add("aim: test");
		account_list.add("twitter: test@sth");
		account_list.add("icq: 712859");
		account_list.add("connect all");
		
		//set Main Window Frame
		setTitle("Parrot-IM");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setPreferredSize(new Dimension (300,500));
		setResizable(false);
		setIconImage(new ImageIcon(getcwd() + "/src/mainwindow/logo.png").getImage());
		//frame.setMinimumSize(new Dimension (300,500)); // how to set the minimum size to be resized?
		
		//call SignIn Panel
		init_SignInPanel ();
		
		pack();
		setVisible(true);
	}
	
	private void init_SignInPanel (){
		//set signin Panel
		signinPanel = new JPanel ();
		signinPanel.setLayout (new BorderLayout());
		manageAccountPanel();
		
		signinPanel.add(new headerPanel(), BorderLayout.NORTH);
		signinPanel.add(accPanel, BorderLayout.CENTER);
		signinPanel.add(new miscPanel(), BorderLayout.SOUTH);
		getContentPane().add(signinPanel);
	}
	
	private void manageAccountPanel (){
		accPanel = new JPanel ();
		accPanel.setLayout(new GridLayout(4,1));
		accPanel.setBorder(BorderFactory.createEmptyBorder(0, 50, 20, 50));
		
		//list of accounts
		JComboBox account_select = new JComboBox (account_list);
		
		//connect button
		JPanel connectPanel = new JPanel();
		connectPanel.setBorder(BorderFactory.createEmptyBorder(5, 50, 5, 50));
		JButton connectButton = new JButton("Sign In");
		connectPanel.add(connectButton);
		
		//somehow try to set the button size
		connectButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                signIn_ActionPerformed(evt);
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
		accPanel.add(connectPanel);

	}
	private void manageAccount_ActionPerformed(ActionEvent e) {
		accMAN = new JFrame ("Account Manager");
		accMAN.setLocation(100, 100);
		accMAN.setPreferredSize(new Dimension(500,300));
		accMAN.setResizable(false);
		accMAN.setIconImage(new ImageIcon(getcwd() + "/src/mainwindow/logo.png").getImage());
		
		//set main panel
		accPanel = new JPanel ();
		accPanel.setLayout(new BorderLayout());
		accPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
		//manage account panel
		leftPanelMAN();
		rightPanelMAN();
		
		accMAN.getContentPane().add(accPanel);
		accMAN.pack();
		accMAN.setVisible(true);
	} 

	private void guestAccount_ActionPerformed(ActionEvent e) {
		//set Frame
		GAL = new JFrame ("Guest Account Login");
		GAL.setPreferredSize(new Dimension (400,250));
		GAL.setLocation(100, 100);
		GAL.setResizable(false);
		GAL.setIconImage(new ImageIcon(getcwd() + "/src/mainwindow/logo.png").getImage());
		
		//select server
		JComboBox server = new JComboBox (serverList);
		
		//username + password
		JPanel accountInfoPanel = new JPanel();
		GridLayout accountLayout = new GridLayout(2,2);
		accountLayout.setVgap(5);
		accountInfoPanel.setLayout(accountLayout);
		//set username
		final JTextField UNFieldGuest = new JTextField();
		accountInfoPanel.add(new JLabel("Username:"));
		accountInfoPanel.add(UNFieldGuest);
		//set password
		final JPasswordField PwdFieldGuest = new JPasswordField();
		accountInfoPanel.add(new JLabel("Password:"));
		accountInfoPanel.add(PwdFieldGuest);
		
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
            	if (UNFieldGuest.getText().length() != 0 && PwdFieldGuest.getPassword().length != 0){
        			GAL.setVisible(false);
        			signIn_ActionPerformed(evt);
        		}
            }
		});
		buttonsPanel.add(okButton);
		//Cancel Button
		JButton cancelButton = new JButton ("Cancel");
		cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	GAL.setVisible(false);
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
	
	private void leftPanelMAN(){
		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new BorderLayout());
		
		//saved account list
		accList = new JList(account_list);
		accList.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		accList.setPreferredSize(new Dimension (150,200));
		
		//add-remove button panel
		JPanel addremovePanel = new JPanel();
		GridLayout ARlayout= new GridLayout(1,2);
		addremovePanel.setLayout(ARlayout);
		ARlayout.setHgap(5);
		addremovePanel.setBorder(BorderFactory.createEmptyBorder(20, 12, 0, 12));
		
		//add button
		JButton addButton = new JButton("+");
		addButton.setPreferredSize(new Dimension(50, 30));
		addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	//TODO: sorry, I have no idea what this thing is supposed to do
            	addACC_ActionPerformed(evt);
            	
            }
		});
		
		//remove button
		JButton removeButton = new JButton ("-");
		removeButton.setPreferredSize(new Dimension(50, 30));
		removeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	int selected = accList.getSelectedIndex();
            	if (selected>=0 && selected < account_list.size()-1){
            		account_list.remove(selected);
            		accList.updateUI();
            	}
            }
		});
		
		//pack the whole thing
		addremovePanel.add(addButton);
		addremovePanel.add(removeButton);
		
		//add to leftpanel
		leftPanel.add(accList,BorderLayout.NORTH);
		leftPanel.add(addremovePanel,BorderLayout.SOUTH);
		
		//add to account manager pop up main panel
		accPanel.add(leftPanel,BorderLayout.WEST);
	}
	
	private void rightPanelMAN() {
		//setting right panel
		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new BorderLayout());
		
		/*TOP PART*/
		//account setupLabel setting Panel
		GridLayout setupLabelLayout = new GridLayout (3,1);
		setupLabelLayout.setVgap(10);
		JPanel setupLabelPanel = new JPanel();
		setupLabelPanel.setLayout(setupLabelLayout);
		setupLabelPanel.setPreferredSize(new Dimension (75,75));
		JLabel serviceLabel = new JLabel("Service:");
		JLabel UNLabel = new JLabel("Username:");
		JLabel pwdLabel = new JLabel("Password:");
		setupLabelPanel.add(serviceLabel);
		setupLabelPanel.add(UNLabel);
		setupLabelPanel.add(pwdLabel);
		
		//account setupField setting Panel
		GridLayout setupFieldLayout = new GridLayout (3,1);
		setupFieldLayout.setVgap(2);
		JPanel setupFieldPanel = new JPanel();
		setupFieldPanel.setLayout(setupFieldLayout);
		serviceField = new JComboBox (serverList);
		serviceField.setPreferredSize(new Dimension(170,27));
		UNField = new JTextField();
		pwdField = new JPasswordField();
		setupFieldPanel.add(serviceField);
		setupFieldPanel.add(UNField);
		setupFieldPanel.add(pwdField);
		
		//account setup Panel
		JPanel setupPanel = new JPanel();
		setupPanel.setLayout(new FlowLayout());
		setupPanel.add(setupLabelPanel);
		setupPanel.add(setupFieldPanel);
		
		
		/*CENTRE PART : remember password + auto sign in*/
		//other Checkboxes setup Panel
		JPanel otherCheckPanel = new JPanel ();
		otherCheckPanel.setLayout(new GridLayout (2,1));
		JCheckBox rememberPWDCheck = new JCheckBox();
		JCheckBox autoSignCheck = new JCheckBox();
		otherCheckPanel.add(rememberPWDCheck);
		otherCheckPanel.add(autoSignCheck);
		
		//other Labels setup Panel
		GridLayout otherLabelLayout = new GridLayout (2,1);
		JPanel otherLabelPanel = new JPanel ();
		otherLabelPanel.setLayout(otherLabelLayout);
		otherLabelLayout.setVgap(7);
		JLabel rememberPWDLabel = new JLabel("Remember password");
		JLabel autoSignLabel = new JLabel("Auto Sign-in");
		otherLabelPanel.add(rememberPWDLabel);
		otherLabelPanel.add(autoSignLabel);
		
		//other setups Panel
		JPanel otherSetupPanel = new JPanel();
		otherSetupPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 50, 0));
		otherSetupPanel.setLayout (new FlowLayout());
		otherSetupPanel.add(otherCheckPanel);
		otherSetupPanel.add(otherLabelPanel);
		
		/*BOTTOM PART : OK and Cancel Button*/
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
            	addACC_ActionPerformed(evt);
            }
		});
		buttonsPanel.add(okButton);
		
		//Cancel Button
		JButton cancelButton = new JButton ("Cancel");
		cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	accMAN.setVisible(false);
            }
		});
		buttonsPanel.add(cancelButton);
		
		
		//adding to rightPanel
		rightPanel.add(setupPanel, BorderLayout.NORTH);
		rightPanel.add(otherSetupPanel, BorderLayout.CENTER);
		rightPanel.add(buttonsPanel, BorderLayout.SOUTH);
		
		//add to account manager pop up main panel
		accPanel.add(rightPanel,BorderLayout.EAST);
	}
	private void signIn_ActionPerformed(ActionEvent e) {
		//TODO: set frame to signing in panel or to buddylist panel     
	} 
	private void addACC_ActionPerformed(ActionEvent e) {
		if (UNField.getText().length() != 0 && pwdField.getPassword().length != 0){
			//search if it exists or not
			String newACC = serverList.get(serviceField.getSelectedIndex())+": "+UNField.getText();
			boolean match = false;
			for (int i=0; i < account_list.size()-1; i++){
				if (account_list.get(i).compareTo(newACC)==0) match = true;
			}
			if (match) return;
    		account_list.add(account_list.size()-1, newACC);
    		accList.updateUI();
			//accMAN.setVisible(false);
		}
	}
	
	private String getcwd() { 
	    String cwd = System.getProperty("user.dir"); 
	    return cwd; 
	}
}

