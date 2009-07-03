/* FeaturesPanel.java
 * 
 * Programmed By:
 *     Chenny Huang
 *     Vera Lukman
 *     
 * Change Log:
 *     2009-June-20, CH
 *         Initial write.
 *     2009-June-29, VL
 *         Reorganized code.
 *         
 * Known Issues:
 *     adding/removing might not work
 *     
 * Copyright (C) 2009  Pirate Captains
 * 
 * License: GNU General Public License version 2.
 * Full license can be found in ParrotIM/LICENSE.txt.
 */

package view.options;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.SQLException;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import controller.MainController;

import model.Model;
import model.enumerations.ServerType;
import model.enumerations.UpdatedType;

public class ManageAccount extends JPanel implements Observer
{
	
	private Model model;
	private MainController controller;
	private Vector<String> accountArray;
	private JList accList;
	private JTextField UNField;
	private JPasswordField pwdField;
	private JComboBox server;
	
	protected JPanel serverPanel;
	protected JTextField jabberServer;
	protected JButton removeButton;
	
	protected ManageAccount (Model model, MainController controller) throws ClassNotFoundException, SQLException {

		this.model = model;
		this.controller = controller;

		//set main panel
		setLayout(new BorderLayout());
		//manage account panel
		leftPanelMAN();
		rightPanelMAN();

	}
	
	
	
	private void leftPanelMAN() throws ClassNotFoundException, SQLException{
		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new BorderLayout());

		//saved account list
		accountArray = model.getAccountList();
		accList = new JList(accountArray);
		accList.addListSelectionListener(new accListSelectionListener());
		System.out.println(model.getAccountList().size());
		accList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        
		JScrollPane listScroller = new JScrollPane(accList);
		listScroller.setPreferredSize(new Dimension(180, 190));
		listScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);


		//add button
		JButton addButton = new JButton("Add");
		addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	try {
					addAccount_actionPerform(evt) ;
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				}
            }
		});

		//remove button
		removeButton = new JButton ("Remove");
		removeButton.setEnabled(false);
		removeButton.addActionListener(new removeActionListener());

		//add-remove button panel
		JPanel addremovePanel = new JPanel();
		addremovePanel.add(addButton);
		addremovePanel.add(removeButton);

		//add to leftpanel
		leftPanel.add(listScroller,BorderLayout.NORTH);
		leftPanel.add(addremovePanel,BorderLayout.SOUTH);

		//add to account manager pop up main panel
		this.add(leftPanel,BorderLayout.WEST);
	}

	private void rightPanelMAN() {
		//setting right panel
		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new BorderLayout());
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
        serverPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 15, 5));
        serverPanel.add(jabberServerLabel, BorderLayout.WEST);
        serverPanel.add(jabberServerPanel, BorderLayout.CENTER);

        // set username
        JPanel usernamePanel = new JPanel();
        UNField = new JTextField();
        UNField.setPreferredSize(new Dimension(180, 20));
        usernamePanel.add(new JLabel("Username:     "));
        usernamePanel.add(UNField);
        // set password
        JPanel passwordPanel = new JPanel();
        pwdField = new JPasswordField();
        pwdField.setPreferredSize(new Dimension(180, 20));
        passwordPanel.add(new JLabel ("Password:      "));
        passwordPanel.add(pwdField);

		//account setup Panel
		JPanel setupPanel = new JPanel();
		setupPanel.setLayout(new BoxLayout (setupPanel, BoxLayout.Y_AXIS));
		setupPanel.add(server);
		setupPanel.add(serverPanel);
		setupPanel.add(usernamePanel);
		setupPanel.add(passwordPanel);


		//*CENTRE PART : remember password + auto sign in
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

		//adding to rightPanel
		rightPanel.setPreferredSize(new Dimension(280, 400));
		rightPanel.add(setupPanel, BorderLayout.NORTH);
		rightPanel.add(otherSetupPanel, BorderLayout.SOUTH);

		//add to account manager pop up main panel
		add(rightPanel,BorderLayout.EAST);
	}


	private void addAccount_actionPerform(ActionEvent evt) throws ClassNotFoundException, SQLException {
		if (UNField.getText().length() != 0 && pwdField.getPassword().length != 0){
			//search if it exists or not
			//TODO: newACC is supposed to be an Object that includes server, username, password
			boolean match = false; //someone deleted my code here. or will model provide this method?
			//(if there the account has already beed added, then do something.)

			if (match) {
				//if found, then edit the password as manage
				//TODO:edit password
			}
			else {
				//insert new
			//	model.addAccountToCurrentProfile
			//		(new AccountData((ServerType)server.getSelectedItem(), UNField.getText(), password(pwdField.getPassword())));
			        controller.addAccount(model.getCurrentProfile().getProfileName(), (ServerType)server.getSelectedItem(), UNField.getText(), password(pwdField.getPassword()));
			        accountArray.add(UNField.getText());
		
		                 
			        accList.updateUI();
				UNField.setText("");
				pwdField.setText("");
        		//TODO: update the JComboBox in siginPanel too!
			}
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



	public void update(Observable arg0, Object o) {
		if (o == UpdatedType.PROFILE){
			
		}
		
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
	
	private class removeActionListener implements ActionListener{
		public void actionPerformed(ActionEvent evt) {
        	int selected = accList.getSelectedIndex();
			if (selected>=0 && selected < accountArray.size()){
				model.removeAccount(model.getCurrentProfile().getProfileName(), accList.getSelectedValue().toString());
				accountArray.remove(selected);
				accList.updateUI();
			}
        }
	}
	
	private class accListSelectionListener implements ListSelectionListener{

		public void valueChanged(ListSelectionEvent e) {
			if (accList.getSelectedIndex()>-1){
				removeButton.setEnabled(true);
			}
		}
		
	}
}
