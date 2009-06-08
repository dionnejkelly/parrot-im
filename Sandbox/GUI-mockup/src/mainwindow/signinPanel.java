package mainwindow;
//TODO: set so that when the popup window (manage account and guest account popups) 
//is closed using the close button, mainFrame is set to enable.
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import org.jivesoftware.smack.XMPPException;

import styles.linkLabel;
import buddylist.buddylist;
import ChatClient.ChatClient;
import model.Model;


public class signinPanel extends JPanel {
	protected ChatClient core;
	private mainwindow mainFrame;
	private Model model;
	private JComboBox account_select;
	
	//Account Options part (in Sign In Panel)
	private linkLabel manageAccount;
	private linkLabel guestAccount;
	
	public signinPanel(mainwindow frame, ChatClient chatClient, Model model){
		mainFrame = frame;
		core = chatClient;//CORE
		this.model = model;
		
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
		account_select = new JComboBox (model.getAccountList());
		account_select.setAlignmentY(JComboBox.CENTER_ALIGNMENT);
		//connect button
		JPanel connectPanel = new JPanel();
		JButton connectButton = new JButton("Sign In");
		connectButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
		connectPanel.add(connectButton);
		connectButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                try {
					signIn_ActionPerformed(evt);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        });

		//accOPTPanel (part of accPanel)
		JPanel accOptPanel = new JPanel ();
		accOptPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
		GridLayout accLayout = new GridLayout(2,1);
		accLayout.setVgap(15);
		accOptPanel.setLayout (accLayout);

		//manage account
		manageAccount = new linkLabel ("Add/Manage Account"); 
		manageAccount.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
            	mainFrame.setEnabled(false);
            	manageAccountFrame popup = new manageAccountFrame(model, mainFrame);
            }
        });

		//guest account
		guestAccount = new linkLabel ("Connect Guest Account"); 
		guestAccount.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
            	mainFrame.setEnabled(false);
                new guestAccountFrame(model, core, mainFrame);
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
	
	private void signIn_ActionPerformed(ActionEvent e) throws ClassNotFoundException, SQLException {
		/*THIS IS FOR CHAT CLIENT : modified ChatClient c*/
		try {
			String username = (String)account_select.getSelectedItem();
			core.login(username, model.getPassword(username), 4); //change 4 to the actual server from model later on
			buddylist buddyWin = new buddylist(core, model);//pops buddylist window
			mainFrame.dispose();
		} catch (XMPPException e1) {
			// TODO: throw a warning pop up
			e1.printStackTrace();
			System.out.println("sign in failed!");
		}	
	}
}
