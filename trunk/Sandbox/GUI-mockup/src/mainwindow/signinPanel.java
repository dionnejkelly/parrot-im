package chatwindow;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import org.jivesoftware.smack.XMPPException;

import buddylist.buddylist;

import ChatClient.ChatClient;

import mainwindow.headerPanel;
import mainwindow.miscPanel;

public class signinPanel extends JPanel {
	private JFrame mainFrame;
	
	//MODEL STUB//
	private Vector<String> account_list;
	private Vector<String> serverList;
	private String username;
	private String password;
	
	//Account Options part (in Sign In Panel)
	private JLabel manageAccount;
	private JLabel guestAccount;
	//Account Manager pop-up window
	private JFrame accMAN;
	private JPanel accMANPanel;
	private JList accList;
	private JTextField UNField; 	//Account Manager - rightPanelMAN
	private JPasswordField pwdField;//Account Manager - rightPanelMAN
	private JComboBox serviceField;	//Account Manager - rightPanelMAN
	//Guest Account login pop-up window
	private JFrame GAL;
	
	
	public signinPanel(JFrame frame){
		mainFrame = frame;
		init_model();
		setLayout (new BorderLayout());
		manageAccountPanel();

		add(new headerPanel(), BorderLayout.NORTH);
		add(new miscPanel(), BorderLayout.SOUTH);
	}
	
	private void manageAccountPanel (){
		JPanel accPanel = new JPanel();
		accPanel.setLayout(new BoxLayout(accPanel, BoxLayout.Y_AXIS));
		accPanel.setBorder(BorderFactory.createEmptyBorder(10, 50, 50, 50));
		//list of accounts
		JComboBox account_select = new JComboBox (account_list);
		account_select.setAlignmentY(JComboBox.CENTER_ALIGNMENT);
		//connect button
		JPanel connectPanel = new JPanel();
		JButton connectButton = new JButton("Sign In");
		connectButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
		connectPanel.add(connectButton);
		connectButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                signIn_ActionPerformed(evt);
            }
        });

		//accOPTPanel (part of accPanel)
		JPanel accOptPanel = new JPanel ();
		accOptPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
		GridLayout accLayout = new GridLayout(2,1);
		accLayout.setVgap(15);
		accOptPanel.setLayout (accLayout);

		//manage account
		manageAccount = new JLabel ("Add/Manage Accounts", JLabel.CENTER); //underline?
		manageAccount.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                manageAccount_MouseClicked(evt);
            }
        });

		//guest account
		guestAccount = new JLabel ("Connect Guest Account", JLabel.CENTER); //underline?
		guestAccount.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                guestAccount_MouseClicked(evt);
            }
        });

		//add components to accOptPanel
		accOptPanel.add(manageAccount);
		accOptPanel.add(guestAccount);

		//add components to accPanel
		accPanel.add(account_select);
		accPanel.add(accOptPanel);
		accPanel.add(connectPanel);

		add(accPanel, BorderLayout.CENTER);
	}
	private void manageAccount_MouseClicked(java.awt.event.MouseEvent e) {
		accMAN = new JFrame ("Account Manager");
		accMAN.setLocation(100, 100);
		accMAN.setPreferredSize(new Dimension(500,300));
		accMAN.setResizable(false);
		accMAN.setIconImage(new ImageIcon(System.getProperty("user.dir") + "/src/mainwindow/logo.png").getImage());

		//set main panel
		accMANPanel = new JPanel ();
		accMANPanel.setLayout(new BorderLayout());
		accMANPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
		//manage account panel
		leftPanelMAN();
		rightPanelMAN();

		accMAN.getContentPane().add(accMANPanel);
		accMAN.pack();
		accMAN.setVisible(true);
	}

	private void guestAccount_MouseClicked(java.awt.event.MouseEvent e) {
		//set Frame
		GAL = new JFrame ("Guest Account Login");
		GAL.setPreferredSize(new Dimension (400,250));
		GAL.setLocation(100, 100);
		GAL.setResizable(false);
		GAL.setIconImage(new ImageIcon(System.getProperty("user.dir") + "/src/mainwindow/logo.png").getImage());

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
		accList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        
		JScrollPane listScroller = new JScrollPane(accList);
		listScroller.setPreferredSize(new Dimension(180, 200));

		//add-remove button panel
		JPanel addremovePanel = new JPanel();
		GridLayout ARlayout= new GridLayout(1,2);
		addremovePanel.setLayout(ARlayout);
		ARlayout.setHgap(5);
		addremovePanel.setBorder(BorderFactory.createEmptyBorder(20, 12, 0, 12));

		//add button
		JButton addButton = new JButton("+");
		addButton.setPreferredSize(new Dimension(40, 25));
		addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	addAccount_actionPerform(evt) ;
            }
		});

		//remove button
		JButton removeButton = new JButton ("-");
		removeButton.setPreferredSize(new Dimension(40, 25));
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
		leftPanel.add(listScroller,BorderLayout.NORTH);
		leftPanel.add(addremovePanel,BorderLayout.SOUTH);

		//add to account manager pop up main panel
		accMANPanel.add(leftPanel,BorderLayout.WEST);
	}

	private void rightPanelMAN() {
		//setting right panel
		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new BorderLayout());
		/*TOP PART*/
		//account setupLabel setting Panel
		JPanel setupLabelPanel = new JPanel();
		GridLayout setupLabelLayout = new GridLayout (3,1);
		setupLabelLayout.setVgap(10);
		setupLabelPanel.setLayout(setupLabelLayout);
		setupLabelPanel.setPreferredSize(new Dimension (75,75));
		JLabel serviceLabel = new JLabel("Service:");
		JLabel UNLabel = new JLabel("Username:");
		JLabel pwdLabel = new JLabel("Password:");
		setupLabelPanel.add(serviceLabel);
		setupLabelPanel.add(UNLabel);
		setupLabelPanel.add(pwdLabel);

		//account setupField setting Panel
		JPanel setupFieldPanel = new JPanel();
		//BoxLayout setupFieldLayout = new BoxLayout(setupFieldPanel, BoxLayout.Y_AXIS);
		GridLayout setupFieldLayout = new GridLayout (3,1);
		setupFieldLayout.setVgap(2);
		setupFieldPanel.setLayout(setupFieldLayout);
		serviceField = new JComboBox (serverList);
		serviceField.setPreferredSize(new Dimension(170,27));
		UNField = new JTextField();
		UNField.setPreferredSize(new Dimension (85,20));
		pwdField = new JPasswordField();
		pwdField.setPreferredSize(new Dimension (100,20));
		setupFieldPanel.add(serviceField);
		setupFieldPanel.add(UNField);
		setupFieldPanel.add(pwdField);

		//account setup Panel
		JPanel setupPanel = new JPanel();
		setupPanel.setLayout(new BoxLayout (setupPanel, BoxLayout.X_AXIS));
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
            	addAccount_actionPerform(evt) ;
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
		accMANPanel.add(rightPanel,BorderLayout.EAST);
	}
	private void signIn_ActionPerformed(ActionEvent e) {
		//TODO: link with chatclient
		/*THIS IS FOR CHAT CLIENT : modified ChatClient c*/
		
		ChatClient c = new ChatClient();//CORE
		try {
			c.login(username, password);
			this.setVisible(false);
			buddylist buddyWin = new buddylist();//pops buddylist window
		} catch (XMPPException e1) {
			// TODO: throw a warning pop up
			e1.printStackTrace();
			System.out.println("sign in failed!");
		}
		
	}
	private void addAccount_actionPerform(ActionEvent evt) {
		if (UNField.getText().length() != 0 && pwdField.getPassword().length != 0){
			//search if it exists or not
			String newACC = serverList.get(serviceField.getSelectedIndex())+": "+UNField.getText();
			boolean match = false;
			for (int i=0; i < account_list.size()-1; i++){
				if (account_list.get(i).compareTo(newACC)==0) match = true;
			}
			if (match) 
			{
				//if found, then edit the password as manage
				//TODO:edit password
			}
			else
			{
				//insert new
				//UNField.setText("");
				//pwdField.setText("");
				account_list.add(account_list.size()-1, newACC);
				accList.updateUI();
			}
			//accMAN.setVisible(false);
		}
	}

	
	private void editAccount(){
       	int selected = accList.getSelectedIndex();
    	if (selected>=0 && selected < account_list.size()-1){
			String tempAccount = account_list.get(selected);
			StringTokenizer token = new StringTokenizer(tempAccount, " :");
			String serviceName = token.nextToken();
			String userId = token.nextToken();
			int serviceNum = 0;
			if (serviceName.compareTo("msn") == 0){
				serviceNum = 0;
			}else if (serviceName.compareTo("aim") == 0){
				serviceNum = 1;
			}else if (serviceName.compareTo("twitter") == 0){
				serviceNum = 2;
			}else if (serviceName.compareTo("icq") == 0){
				serviceNum = 3;
			}else if (serviceName.compareTo("googleTalk") == 0){
				serviceNum = 4;
			}
			UNField.setText(userId);
			serviceField.setSelectedIndex(serviceNum);
    	}
	}
	
	private void init_model(){
		username = new String ("cmpt275testing@gmail.com");
		password = new String ("abcdefghi");
		
		//set server list
		serverList = new Vector<String>();
		serverList.add ("msn");
		serverList.add ("aim");
		serverList.add ("twitter");
		serverList.add ("icq");
		serverList.add ("googleTalk");

		//list of accounts
		account_list = new Vector<String>();
		account_list.add("googleTalk: " + username);
		account_list.add("connect all");
	}
}
