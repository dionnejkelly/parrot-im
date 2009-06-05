package mainwindow;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
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

import model.Model;

public class manageAccountFrame extends JFrame{
	private JPanel accMANPanel;
	private Model model;
	
	private JTextField UNField;
	private JPasswordField pwdField;
	private JList accList;
	private JComboBox serviceField;
	
	protected manageAccountFrame (Model model){
		this.model = model;
		
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
	
	private void leftPanelMAN(){
		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new BorderLayout());

		//saved account list
		accList = new JList(model.getAccountList());
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
            	addAccount_actionPerform(evt) ;
            }
		});

		//remove button
		JButton removeButton = new JButton ("-");
		removeButton.setPreferredSize(new Dimension(40, 25));
		removeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	int selected = accList.getSelectedIndex();
            	if (selected>=0 && selected < model.getAccountList().size()-1){
            		model.getAccountList().remove(selected);
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
            	setVisible(false);
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


	private void addAccount_actionPerform(ActionEvent evt) {
		if (UNField.getText().length() != 0 && pwdField.getPassword().length != 0){
			//search if it exists or not
			String newACC = model.getServerList().get(serviceField.getSelectedIndex())+": "+UNField.getText();
			boolean match = false;
			for (int i=0; i < model.getAccountList().size()-1; i++){
				if (model.getAccountList().get(i).compareTo(newACC)==0) match = true;
			}
			
			if (match) {
				//if found, then edit the password as manage
				//TODO:edit password
			}else {
				//insert new
				UNField.setText("");
				pwdField.setText("");
				model.getAccountList().add(model.getAccountList().size()-1, newACC);
				accList.updateUI();
			}
			//accMAN.setVisible(false);
		}
	}

}
