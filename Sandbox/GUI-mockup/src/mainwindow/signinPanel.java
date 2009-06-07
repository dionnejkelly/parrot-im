package mainwindow;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.jivesoftware.smack.XMPPException;

import styles.linkLabel;

import buddylist.buddylist;

import ChatClient.ChatClient;

import model.Model;


public class signinPanel extends JPanel {
	protected ChatClient core;
	private JFrame mainFrame;
	private Model model;
	
	//Account Options part (in Sign In Panel)
	private linkLabel manageAccount;
	private linkLabel guestAccount;
	
	public signinPanel(JFrame frame, ChatClient chatClient, Model model){
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
		JComboBox account_select = new JComboBox (model.getAccountList());
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
		manageAccount = new linkLabel ("Add/Manage Account"); 
		manageAccount.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
            	//TODO: set this later!!!!!! - shicmap
            	//enableFrame(false);
            	new manageAccountFrame(model);
                //manageAccount_MouseClicked(evt);
                //enableFrame(true);
            }
        });

		//guest account
		guestAccount = new linkLabel ("Connect Guest Account"); 
		guestAccount.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
            	//enableFrame(false);
                new guestAccountFrame(model, core, mainFrame);
                //enableFrame(true);
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
	
	private void signIn_ActionPerformed(ActionEvent e) {
		/*THIS IS FOR CHAT CLIENT : modified ChatClient c*/
		try {
			core.login(model.getUsername(), model.getPassword(), 4); //change 4 to the actual server from model later on
			mainFrame.setVisible(false);
			buddylist buddyWin = new buddylist(core, model);//pops buddylist window
		} catch (XMPPException e1) {
			// TODO: throw a warning pop up
			e1.printStackTrace();
			System.out.println("sign in failed!");
		}	
	}
	
	private void enableFrame(boolean b){
		//enable or disable mainFrame (or the components inside)
		//TODO: true(enable) false(disable)
	}
}
