package view.styles;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
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
	
	private JMenuItem signOutMenu;
	
	public AccountJMenu(AccountData account, MainController c, BuddyPanel buddies){
		super("GoogleTalk - " + account.getUserID());
		setIcon(new ImageIcon(this.getClass().getResource(
        "/images/buddylist/statusIcons/GoogleTalk/GoogleTalk-Available.png")));
		this.account = account;
		this.buddies = buddies;
		controller = c;
		this.setMnemonic(KeyEvent.VK_S);
		
		//SIGN MENU//
		signMenu= new JMenuItem();
		signMenu.setText("Sign In");
		signMenu.setEnabled(false);
		
		signOutMenu= new JMenuItem();
		signOutMenu.setText("Sign out");
		signOutMenu.setEnabled(true);
		
		JMenuItem addFriendMenu= new JMenuItem("Add a friend");
//		if (account.isOnline())
//			signMenu.setText("Sign out");
//		else
//			signMenu.setText("Sign in");
		
		JMenuItem removeMenu = new JMenuItem("Remove account");
		
		signMenu.setMnemonic(KeyEvent.VK_S);
		signOutMenu.setMnemonic(KeyEvent.VK_S);
		addFriendMenu.setMnemonic(KeyEvent.VK_A);
		removeMenu.setMnemonic(KeyEvent.VK_R);
		signMenu.addActionListener(new SignMenuActionListener());
		signOutMenu.addActionListener(new SignMenuActionListener());
		this.add(signMenu);
		this.add(signOutMenu);
		this.add(addFriendMenu);
		this.addSeparator();
		this.add(removeMenu);

	}
	
	public void connectAccount(boolean b){
		if (b){ //connecting
			try {
				controller.login(account);
				System.out.println("account is now online");
				//signMenu.setText("Sign out");
				signMenu.setEnabled(false);
				signOutMenu.setEnabled(true);
			} catch (BadConnectionException e) {
				//throw warning
//				new JOptionPane (account.getUserID()+ " failed signing in.");
			}
		} else { //disconnect
			controller.disconnect(account);
			System.out.println("account is now offline");
			//signMenu.setText("Sign in");
			signMenu.setEnabled(true);
			signOutMenu.setEnabled(false);
		}
		buddies.listRepopulate();
	}
	private class SignMenuActionListener implements ActionListener{

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
