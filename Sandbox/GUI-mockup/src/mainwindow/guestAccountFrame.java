package mainwindow;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

import ChatClient.ChatClient;
import buddylist.buddylist;
import model.Model;

public class guestAccountFrame extends JFrame{
	Model model;
	ChatClient core;
	JFrame mainFrame;
	
	public guestAccountFrame(Model model, ChatClient c, JFrame main){
		this.model = model;
		core = c;
		mainFrame = main;
		
		//set Frame
		setTitle("Guest Account Login");
		setPreferredSize(new Dimension (400,250));
		setLocation(100, 100);
		setResizable(false);
		setIconImage(new ImageIcon(System.getProperty("user.dir") + "/src/mainwindow/logo.png").getImage());

		//select server
		JComboBox server = new JComboBox (model.getServerList());

		//username + password
		JPanel accountInfoPanel = new JPanel();
		//set username
		final JTextField UNFieldGuest = new JTextField();
		UNFieldGuest.setPreferredSize(new Dimension(200,20));
		accountInfoPanel.add(new JLabel("Username:     "));
		accountInfoPanel.add(UNFieldGuest);
		//set password
		final JPasswordField PwdFieldGuest = new JPasswordField();
		PwdFieldGuest.setPreferredSize(new Dimension(200,20));
		accountInfoPanel.add(new JLabel("Password:      "));
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
        			setVisible(false);
        			signIn_ActionPerformed(evt);
        		}
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

		//set main panel
		JPanel GALPanel = new JPanel();
		GALPanel.setLayout(new GridLayout(3,1));
		GALPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
		GALPanel.add (server);
		GALPanel.add(accountInfoPanel);
		GALPanel.add (buttonsPanel);

		getContentPane().add(GALPanel);
		pack();
		setVisible(true);

	}
	
	private void signIn_ActionPerformed(ActionEvent e) {
		/*THIS IS FOR CHAT CLIENT : modified ChatClient c*/
		try {
			core.login(model.getUsername(), model.getPassword());
			mainFrame.setVisible(false);
			buddylist buddyWin = new buddylist(core);//pops buddylist window
		} catch (XMPPException e1) {
			// TODO: throw a warning pop up
			e1.printStackTrace();
			System.out.println("sign in failed!");
		}	
	}

}
