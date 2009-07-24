package view.styles;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import view.buddylist.BuddyPanel;

import controller.MainController;
import controller.services.BadConnectionException;

import model.dataType.AccountData;

public class AccountJMenu extends JMenu{
	MainController controller;
	AccountData account;
	BuddyPanel buddies;
	JMenuItem signMenu;
	
	public AccountJMenu(AccountData account, MainController c, BuddyPanel buddies){
		super(account.getUserID());
		this.account = account;
		this.buddies = buddies;
		controller = c;
		this.setMnemonic(KeyEvent.VK_S);

		//SIGN MENU//
		signMenu= new JMenuItem();
		if (account.isOnline())
			signMenu.setText("Sign out");
		else
			signMenu.setText("Sign in");
		signMenu.setMnemonic(KeyEvent.VK_S);
		signMenu.addActionListener(new SignMenuActionListener());
		this.add(signMenu);

	}
	
	public void connectAccount(boolean b){
		if (b){ //connecting
			try {
				controller.login(account);
				System.out.println("account is now online");
				signMenu.setText("Sign out");
			} catch (BadConnectionException e) {
				//throw warning
//				new JOptionPane (account.getUserID()+ " failed signing in.");
			}
		} else { //disconnect
			controller.disconnect(account);
			System.out.println("account is now offline");
			signMenu.setText("Sign in");
		}
		buddies.listRepopulate();
	}
	private class SignMenuActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if (account.isConnected()){
				System.out.println("account is online and we have to disconnect it");
				connectAccount(false);
			}
			else{
				System.out.println("account is offline and we have to connect it");	
				connectAccount(true);	
			}
		}
		
	}
}
