package mainwindow;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
import javax.swing.ScrollPaneConstants;
import javax.swing.WindowConstants;
import model.DatabaseFunctions;
import java.sql.*;

import model.Model;

/*                            SORRY TESTING PROFILE MANAGER LAYOUT                         */


public class manageAccountFrame extends JFrame
{
	private JPanel accMANPanel;
	private Model model;
	private mainwindow mainFrame;
	
	private JList profileList;
	private JList accList;
	

//////////////  WORKING CODE BELOW THIS COMMENT 



	
	private JTextField UNField;
	private JPasswordField pwdField;
	private JComboBox serviceField;
	
	protected manageAccountFrame (Model model, mainwindow frame) throws ClassNotFoundException, SQLException{
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.model = model;
		mainFrame = frame;
		
		setTitle("Account Manager");
		setLocation(100, 100);
		setPreferredSize(new Dimension(500,300));
		setResizable(false);
		setIconImage(new ImageIcon(System.getProperty("user.dir") + "/src/mainwindow/logo.png").getImage());

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
	
	
	
	private void leftPanelMAN() throws ClassNotFoundException, SQLException{
		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new BorderLayout());

		//saved account list
		accList = new JList(model.getAccountList());
		System.out.println(model.getAccountList().size());
		accList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        
		JScrollPane listScroller = new JScrollPane(accList);
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
		JButton removeButton = new JButton ("-");
		removeButton.setPreferredSize(new Dimension(40, 25));
		removeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	int selected = accList.getSelectedIndex();
            	try {
					if (selected>=0 && selected < model.getAccountList().size()){
						model.getAccountList().remove(selected);
						accList.updateUI();
						//TODO: update the JComboBox in siginPanel too!
					}
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
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
		//////////////TOP PART
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
		setupFieldLayout.setVgap(5);
		setupFieldPanel.setLayout(setupFieldLayout);
		serviceField = new JComboBox (model.getServerList());
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

		//BOTTOM PART : OK and Cancel Button
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
            	try {
					addAccount_actionPerform(evt) ;
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				}
            }
		});
		buttonsPanel.add(okButton);

		//Cancel Button
		JButton cancelButton = new JButton ("Cancel");
		cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	setVisible(false);
            	mainFrame.setEnabled(true);
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


	private void addAccount_actionPerform(ActionEvent evt) throws ClassNotFoundException, SQLException {
		if (UNField.getText().length() != 0 && pwdField.getPassword().length != 0){
			//search if it exists or not
			//TODO: newACC is supposed to be an Object that includes server, username, password
			boolean match = false;
			
			
			//Ahmad TESTING TESTING TESTING
			
			  DatabaseFunctions db = new DatabaseFunctions();
			  db.addUsers(new String(model.getServerList().get(serviceField.getSelectedIndex())), new String(UNField.getText()), new String(pwdField.getPassword()));
			
			//Ahmad TESTING TESTING TESTING
			  // check if account already exists
			
			if (match) {
				//if found, then edit the password as manage
				//TODO:edit password
			}else {
				//insert new
				UNField.setText("");
				pwdField.setText("");
				accList.updateUI();
        		//TODO: update the JComboBox in siginPanel too!
			}
		}
	}

}
