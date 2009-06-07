package mainwindow;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.jivesoftware.smack.XMPPException;

import styles.closeListener;

import ChatClient.ChatClient;
import buddylist.buddylist;
import model.Model;

public class guestAccountFrame extends JFrame{
	
	private Model model;
	private ChatClient core;
	private mainwindow mainFrame;
	private JFrame popup;
	
	private JTextField UNFieldGuest;
	private JPasswordField PwdFieldGuest;
	private JComboBox server;
	
	public guestAccountFrame(Model model, ChatClient c, mainwindow frame){
		popup=this;
		this.model = model;
		core = c;
		mainFrame = frame;
		
		//set Frame
		setTitle("Guest Account Login");
		setPreferredSize(new Dimension (400,250));
		setLocation(100, 100);
		setResizable(false);
		setIconImage(new ImageIcon(System.getProperty("user.dir") + "/src/mainwindow/logo.png").getImage());

		//select server
		server = new JComboBox (model.getServerList());
		server.setPreferredSize(new Dimension (200,20));

		//username + password
		JPanel accountInfoPanel = new JPanel();
		//set username
		JPanel usernamePanel = new JPanel();
		UNFieldGuest = new JTextField();
		UNFieldGuest.setPreferredSize(new Dimension(200,20));
		usernamePanel.add(new JLabel("Username:     "));
		usernamePanel.add(UNFieldGuest);
		accountInfoPanel.add(usernamePanel);
		//set password
		PwdFieldGuest = new JPasswordField();
		PwdFieldGuest.setPreferredSize(new Dimension(200,20));
		accountInfoPanel.add(new JLabel("Password:      "));
		accountInfoPanel.add(PwdFieldGuest);

		//set ok-cancel button
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setPreferredSize(new Dimension(20, 90));
		buttonsPanel.setBorder(BorderFactory.createEmptyBorder(15, 40, 15, 40));
		GridLayout buttonsLayout = new GridLayout(1,2);
		buttonsLayout.setHgap(5);
		buttonsPanel.setLayout(buttonsLayout);
		//OK Button
		JButton okButton = new JButton ("OK");
		okButton.setPreferredSize(new Dimension(20, 40));
		okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	if (UNFieldGuest.getText().length() != 0 && PwdFieldGuest.getPassword().length != 0){
        			setVisible(false);
        			signIn_ActionPerformed(evt);
        		}
            }
		});
		buttonsPanel.add(okButton);
		//Cancel Button
		JButton cancelButton = new JButton ("Cancel");
		cancelButton.setPreferredSize(new Dimension(20, 40));
		cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	mainFrame.setEnabled(true);
            	popup.removeAll();
            	popup.dispose();
            }
		});
		buttonsPanel.add(cancelButton);

		//set main panel
		JPanel GALPanel = new JPanel();
		GALPanel.setLayout(new GridLayout(3,1));
		GALPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
		GALPanel.add (server); //server dropdown menu
		GALPanel.add (accountInfoPanel); //username+password
		GALPanel.add (buttonsPanel); //ok+cancel buttons

		getContentPane().add(GALPanel);
		pack();
		setVisible(true);

	}
	
	private void signIn_ActionPerformed(ActionEvent e) {
		/*THIS IS FOR CHAT CLIENT : modified ChatClient c*/
		try {
			core.login(UNFieldGuest.getText(), password(PwdFieldGuest.getPassword()), server.getSelectedIndex());
			buddylist buddyWin = new buddylist(core, model);//pops buddylist window
			mainFrame.dispose();
		} catch (XMPPException e1) {
			// TODO: throw a warning if password is incorrect or account does not exist - core, please provide this
			//e1.printStackTrace();
			System.out.println("sign in failed!");
		}	
	}

	private String password (char [] pass){
		String str = new String();
		str="";
		
		for (int i = 0; i < pass.length; i++){
			str += pass[i];
		}
		return str;
		
	}
}
