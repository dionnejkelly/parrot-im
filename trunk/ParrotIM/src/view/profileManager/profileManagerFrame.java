package view.profileManager;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import java.sql.*;

import model.Model;


/*
 ***********************************************************************************************************
 ***********************************************************************************************************
 *********************************************************************************************************** 
 * *********************************************************************************************************
 *           																							   *
 *           																							   *
 *  DONT TOUCH THIS FILE YO!!  																			   *
 *           																							   *
 * 					THANKS																				   *
 *           																							   *
 * 										- AARON --														   *
 *           																							   *
 * **********************************************************************************************************
 * **********************************************************************************************************
 * **********************************************************************************************************
 * **********************************************************************************************************
 * */

class profileManagerFrame extends JFrame
{
	private JPanel accMANPanel;
	private Model model;
	
	private JList profileList;
	private JList accList;
	
	protected profileManagerFrame(Model model) throws ClassNotFoundException, SQLException
	{
		this.model = model;
		
		setTitle("Profile Manager");
		setLocation(100, 100);
		setPreferredSize(new Dimension(500,300));
		setResizable(false);
		setIconImage(new ImageIcon("imagesimages/mainwindow/logo.png").getImage());

		//set main panel
		accMANPanel = new JPanel ();
		accMANPanel.setLayout(new BorderLayout());
		accMANPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
		//manage account panel
		//TODO: split them into different panels
		leftPanelMAN();
		rightPanelMAN();

		getContentPane().add(accMANPanel);
		pack();
		setVisible(true);
	}
	
	private void leftPanelMAN() throws ClassNotFoundException, SQLException
	{
		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new BorderLayout());

		//saved account list
		profileList = new JList(model.getAccountList());
		profileList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        
		//TODO:   ADD NEW PROFILE LIST TO MODEL
		
		JScrollPane listScroller = new JScrollPane(profileList);
		listScroller.setPreferredSize(new Dimension(180, 200));
		listScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		//add-remove button panel
		JPanel addremovePanel = new JPanel();
		GridLayout ARlayout= new GridLayout(1,2);
		addremovePanel.setLayout(ARlayout);
		ARlayout.setHgap(5);
		addremovePanel.setBorder(BorderFactory.createEmptyBorder(20, 12, 0, 12));

		//add button
		JButton addButton = new JButton("+");
		addButton.setPreferredSize(new Dimension(40, 25));

		//remove button
		JButton removeButton = new JButton ("-");
		removeButton.setPreferredSize(new Dimension(40, 25));

		//pack the whole thing
		addremovePanel.add(addButton);
		addremovePanel.add(removeButton);

		//add to leftpanel
		leftPanel.add(listScroller,BorderLayout.NORTH);
		leftPanel.add(addremovePanel,BorderLayout.SOUTH);

		//add to account manager pop up main panel
		accMANPanel.add(leftPanel,BorderLayout.WEST);
	}

	private void rightPanelMAN() throws ClassNotFoundException, SQLException 
	{
		//setting right panel
		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new BorderLayout());
		
		/*TOP PART*/
		//List of accounts on the profile
		accList = new JList(model.getAccountList());
		accList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        
		JScrollPane acctListScroller = new JScrollPane(accList);
		acctListScroller.setPreferredSize(new Dimension(300, 200));
		acctListScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER); 


		/*CENTRE PART : Add/Remove Buttons */
		
		JPanel addRemoveAcctPanel = new JPanel();
		GridLayout ARlayout= new GridLayout(1,3);
		addRemoveAcctPanel.setLayout(ARlayout);
		ARlayout.setHgap(5);
		addRemoveAcctPanel.setBorder(BorderFactory.createEmptyBorder(20, 12, 0, 12));
		
		JButton newAcctButton = new JButton ("+");
		newAcctButton.setPreferredSize(new Dimension(40, 25));
		JButton remAcctButton = new JButton ("-");
		remAcctButton.setPreferredSize(new Dimension(40, 25));
		JButton editAcctButton = new JButton ("e");
		editAcctButton.setPreferredSize(new Dimension(40, 25));
		
		addRemoveAcctPanel.add(newAcctButton);
		addRemoveAcctPanel.add(remAcctButton);
		addRemoveAcctPanel.add(editAcctButton);

		/*BOTTOM PART : OK and Cancel Button*/
		//set ok-cancel button
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 10, 10));
		GridLayout buttonsLayout = new GridLayout(1,2);
		buttonsLayout.setHgap(5);
		buttonsPanel.setLayout(buttonsLayout);

		//OK Button
		JButton okButton = new JButton ("OK");
		buttonsPanel.add(okButton);

		//Cancel Button
		JButton cancelButton = new JButton ("Cancel");
		buttonsPanel.add(cancelButton);


		//adding to rightPanel
		rightPanel.add(acctListScroller, BorderLayout.NORTH);
		rightPanel.add(addRemoveAcctPanel, BorderLayout.CENTER);
		rightPanel.add(buttonsPanel, BorderLayout.SOUTH);

		//add to account manager pop up main panel
		accMANPanel.add(rightPanel,BorderLayout.EAST);
	}


}
