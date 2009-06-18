package view.blockManager;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import controller.services.Xmpp;

import model.Model;
import model.dataType.UserData;
import view.mainwindow.mainwindow;

public class blockManager extends JFrame {
	
	private JPanel accMANPanel;
	private Model model;

	protected JFrame popup;
	
	private DefaultListModel usersBuddyListModel;
	private JList usersBuddyList;
	private JList usersBannedBuddyList;
	
	private ArrayList<UserData> buddies;
	
	private ArrayList<UserData> usersProfileBuddyList; 
	private Vector<String> bannedAccountList;
	
	private Xmpp chatClient;
	
	public blockManager(Xmpp c, Model model) throws ClassNotFoundException, SQLException
	{
		this.model = model;
		this.chatClient = c;
		//mainFrame = frame;
		popup = this;
		
		setTitle("Profile Manager");
		setLocation(100, 100);
		setPreferredSize(new Dimension(500,350));
		setResizable(false);
		setIconImage(new ImageIcon("imagesimages/mainwindow/logo.png").getImage());

		//set main panel
		accMANPanel = new JPanel ();
		accMANPanel.setLayout(new BorderLayout());
		//accMANPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
		//manage account panel
		//TODO: split them into different panels
		leftPanelManager();
		rightPanelManager();

		getContentPane().add(accMANPanel);
		pack();
		setVisible(true);
	}
	
	private void leftPanelManager() throws ClassNotFoundException, SQLException
	{
		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new BorderLayout());

		//saved account list
		usersBuddyListModel = new DefaultListModel();
		usersProfileBuddyList = model.getCurrentProfile().getAllFriends();
		for(int i = 0; i < usersProfileBuddyList.size(); i++)
		{
			usersBuddyListModel.addElement(usersProfileBuddyList.get(i));
		}
		//profileList = new JList(model.getAccountList());
		usersBuddyList = new JList(usersBuddyListModel);
		usersBuddyList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        
		//TODO:   ADD NEW PROFILE LIST TO MODEL
		
		JScrollPane listScroller = new JScrollPane(usersBuddyList);
		listScroller.setPreferredSize(new Dimension(205, 250));
		listScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		//add-remove button panel
		JPanel addremovePanel = new JPanel();

	
		JButton blockButton = new JButton ("Block");
		blockButton.setPreferredSize(new Dimension(120, 25));
		//Removing Padding from buttons so text can be displayed properly
		Insets buttonInset = new Insets(0,0,0,0);
	
		blockButton.setMargin(buttonInset);
		blockButton.addActionListener(new blockUserListener());

		//pack the whole thing
		addremovePanel.add(blockButton);

		//add to leftpanel
		leftPanel.add(listScroller,BorderLayout.NORTH);
		leftPanel.add(addremovePanel,BorderLayout.SOUTH);
		TitledBorder profTitle;
		profTitle = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Parrot IM Users");
		profTitle.setTitleJustification(TitledBorder.CENTER);
		leftPanel.setBorder(profTitle);

		//add to account manager pop up main panel
		accMANPanel.add(leftPanel,BorderLayout.WEST);
	}
	
	
	class blockUserListener implements ActionListener {
       
		public void actionPerformed(ActionEvent arg0) {
			
			
			int selected = usersBuddyList.getSelectedIndex();
			String blockedUser = usersProfileBuddyList.get(selected).toString();
			
			System.out.println("Blocked user = " + blockedUser);
			
			
			chatClient.removeFriend(blockedUser);
			usersBuddyListModel.remove(selected);
			usersBuddyList.updateUI();
			
			bannedAccountList.add(blockedUser);
			usersBannedBuddyList.updateUI();
			
			
			
		}
    }
	
	class unBlockUserListener implements ActionListener {
		
	       
		public void actionPerformed(ActionEvent arg0) {
		
			
			int selected = usersBannedBuddyList.getSelectedIndex();
			String unBlockedUser = bannedAccountList.get(selected).toString();
			
			System.out.println("Unblocked user = " + unBlockedUser);
			
			chatClient.addFriend(unBlockedUser);
			bannedAccountList.remove(selected);
			usersBannedBuddyList.updateUI();
			
			usersBuddyListModel.addElement(unBlockedUser);
			usersBuddyList.updateUI();
			
			
		}
    }
	

	private void rightPanelManager() throws ClassNotFoundException, SQLException 
	{
		//setting right panel
		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new BorderLayout());
		
		/*TOP PART*/
		//List of accounts on the profile
		JPanel topRight = new JPanel();
		topRight.setLayout(new BorderLayout());
		bannedAccountList = model.getBannedAccountList();
		usersBannedBuddyList = new JList(bannedAccountList);
		usersBannedBuddyList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        
		JScrollPane acctListScroller = new JScrollPane(usersBannedBuddyList);
		acctListScroller.setPreferredSize(new Dimension(250, 210));
		acctListScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER); 


		/*CENTRE PART : Add/Remove Buttons */
		
		JPanel addRemoveAcctPanel = new JPanel();
		addRemoveAcctPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
	//	addRemoveAcctPanel.setBorder(BorderFactory.createEmptyBorder(5, 12, 0, 12));
		
		Insets buttonInset = new Insets(0,0,0,0);
		JButton unBlockButton = new JButton ("Unblock");
		unBlockButton.setPreferredSize(new Dimension(120, 25));
		unBlockButton.setMargin(buttonInset);
		
		
		addRemoveAcctPanel.add(unBlockButton);
		unBlockButton.addActionListener(new unBlockUserListener());

		//Piece together Right Side Panel and Add Border
		topRight.add(acctListScroller, BorderLayout.NORTH);
		topRight.add(addRemoveAcctPanel, BorderLayout.CENTER);
		TitledBorder acctTitle;
		acctTitle = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Blocked Parrot IM Accounts");
		acctTitle.setTitleJustification(TitledBorder.CENTER);
		topRight.setBorder(acctTitle);
		
		/*BOTTOM PART : OK and Cancel Button*/
		//set ok-cancel button
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setBorder(BorderFactory.createEmptyBorder(10, 120, 10, 10));
		GridLayout buttonsLayout = new GridLayout(1,2);
		buttonsLayout.setHgap(5);
		buttonsPanel.setLayout(buttonsLayout);

		//OK and CANCEL Buttons
		JButton okButton = new JButton ("OK");
		buttonsPanel.add(okButton);
		JButton cancelButton = new JButton ("Cancel");
	
		okButton.addMouseListener(new okCancelButtonListener());
		cancelButton.addMouseListener(new okCancelButtonListener());
		
		buttonsPanel.add(cancelButton);

		//adding to rightPanel
		rightPanel.add(topRight, BorderLayout.CENTER);
		rightPanel.add(buttonsPanel, BorderLayout.SOUTH);

		//add to account manager pop up main panel
		accMANPanel.add(rightPanel,BorderLayout.EAST);
	}
	
	 class okCancelButtonListener extends MouseAdapter {
	        public void mousePressed(MouseEvent event) {
	        	popup.removeAll();
            	popup.dispose(); 
	        }
	    }
	 
	 

}
