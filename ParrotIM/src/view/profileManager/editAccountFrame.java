package view.profileManager;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Observable;
import java.util.Observer;

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

import model.Model;

import view.mainwindow.mainwindow;
import view.styles.popupWindowListener;

public class editAccountFrame extends JFrame
{
	/*
	 * LEAVING OUT EDIT ACCOUNT FUNCTION FOR ALPHA
	 */
	
	private JPanel modAcctPanel;
	private Model model;
	private profileManager managerFrame;
	protected JFrame popup;
	
	private JTextField UNField;
	private JPasswordField pwdField;
	private JComboBox serviceField;
	
	//Instance 1 -- New Account (empty forms)
	private editAccountFrame(Model model,profileManager pManager)
	{
		this.model = model;
		managerFrame = pManager;
		popup = this;
		
		setTitle("add New Account");
		modAcctPanel = new JPanel();
		modAcctPanel.setLayout(new BorderLayout());
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
//            	try {
//					addAccount_actionPerform(evt) ;
//				} catch (ClassNotFoundException e) {
//					e.printStackTrace();
//				} catch (SQLException e) {
//					e.printStackTrace();
//				}
            }
		});
		buttonsPanel.add(okButton);

		//Cancel Button
		JButton cancelButton = new JButton ("Cancel");
		cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	managerFrame.setEnabled(true);
            	popup.removeAll();
            	popup.dispose();

        		managerFrame.setAlwaysOnTop(true);
        	    managerFrame.setAlwaysOnTop(false);
            }
		});
		buttonsPanel.add(cancelButton);


		//Add Content to main Panel and display
		modAcctPanel.add(setupPanel, BorderLayout.NORTH);
		modAcctPanel.add(otherSetupPanel, BorderLayout.CENTER);
		modAcctPanel.add(buttonsPanel, BorderLayout.SOUTH);
		getContentPane().add(modAcctPanel);
		pack();
		setVisible(true);	
	}
	
	//Instance 2 -- Edit Account (User/Pass already filled in, Disable username box)
	/*private editAccountFrame(Model model, String name)
	{
		
	}*/

}
