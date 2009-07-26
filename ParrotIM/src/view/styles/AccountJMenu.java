package view.styles;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import view.buddylist.AddBuddyFrame;
import view.buddylist.BuddyPanel;
import view.options.MusicPlayer;

import controller.MainController;
import controller.services.BadConnectionException;

import model.Model;
import model.dataType.AccountData;
import model.enumerations.ServerType;

public class AccountJMenu extends JMenu{
	MainController controller;
	AccountData account;
	BuddyPanel buddies;
	JMenuItem signMenu;
	
	private JMenuItem signOutMenu;
	
	private Model model;
	
	public AccountJMenu(AccountData account, MainController c, BuddyPanel buddies, Model model){
		super(account.getServer() + " - " + account.getUserID());
		
		this.model = model;
		
		if (account.getServer() == ServerType.GOOGLE_TALK) {
			setIcon(new ImageIcon(this.getClass().getResource(
	        "/images/buddylist/statusIcons/GoogleTalk/GoogleTalk-Available.png")));
		}
		
		else if (account.getServer() == ServerType.JABBER) {
			setIcon(new ImageIcon(this.getClass().getResource(
	        "/images/buddylist/statusIcons/Jabber/Jabber-AvailableSM.png")));
		}
		
		else if (account.getServer() == ServerType.AIM) {
			setIcon(new ImageIcon(this.getClass().getResource(
	        "/images/buddylist/statusIcons/AIM/AIM-AvailableSM.png")));
		}
		
		else if (account.getServer() == ServerType.ICQ) {
			setIcon(new ImageIcon(this.getClass().getResource(
	        "/images/buddylist/statusIcons/ICQ/ICQ-AvailableSM.png")));
		}
		
		else if (account.getServer() == ServerType.MSN) {
			setIcon(new ImageIcon(this.getClass().getResource(
	        "/images/buddylist/statusIcons/MSN/MSN-AvailableSM.png")));
		}
		
		else {
			setIcon(new ImageIcon(this.getClass().getResource(
	        "/images/buddylist/twitter_logo.png")));
		}
		
		
		this.account = account;
		this.buddies = buddies;
		controller = c;
		this.setMnemonic(KeyEvent.VK_S);
		
		//SIGN MENU//
		signMenu= new JMenuItem("Sign In", new ImageIcon(this.getClass().getResource(
        "/images/menu/account/status.png")));
		signMenu.setEnabled(false);
		signMenu.setMnemonic(KeyEvent.VK_S);
		signMenu.addActionListener(new SignMenuActionListener());
	
		signOutMenu= new JMenuItem("Sign Out", new ImageIcon(this.getClass().getResource(
        "/images/menu/account/status-busy.png")));
		signOutMenu.setEnabled(true);
		signOutMenu.setMnemonic(KeyEvent.VK_S);
		
		JMenuItem addFriendMenu= new JMenuItem("Add a friend", new ImageIcon(this.getClass().getResource(
        "/images/buddylist/add_user.png")));
		addFriendMenu.setMnemonic(KeyEvent.VK_A);
		addFriendMenu.addActionListener(new AddFriendListener());
		JMenuItem removeMenu = new JMenuItem("Remove account", new ImageIcon(this.getClass().getResource(
        "/images/mainwindow/remove.png")));
		removeMenu.setMnemonic(KeyEvent.VK_R);
		signOutMenu.addActionListener(new SignMenuActionListener());
		
		this.add(signMenu);
		this.add(signOutMenu);
		this.add(addFriendMenu);
		this.addSeparator();
		this.add(removeMenu);

	}
	public AccountData getAccount(){
		return account;
	}
	public void connectAccount(boolean connect){
		if (connect){ //connecting
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
			//controller.disconnect(account);
			System.out.println("account is now offline");
			//signMenu.setText("Sign in");
			signMenu.setEnabled(true);
			signOutMenu.setEnabled(false);
		}
		buddies.listRepopulate();
	}
	
	private class AddFriendListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			AddBuddyFrame addbuddyFrame = new AddBuddyFrame (model, controller, account.getServer());
        	addbuddyFrame.addWindowListener(new PopupWindowListener (buddies.getBuddyList(), addbuddyFrame));
		}
		
	}
	private class SignMenuActionListener implements ActionListener{

		public void actionPerformed(ActionEvent arg0) {
			if (account.isConnected()){
				System.out.println("account is online and we have to disconnect it");
				controller.disconnect(account);
				connectAccount(false);
			}
			else{
				System.out.println("account is offline and we have to connect it");	
				connectAccount(true);	
			}
		}
		
	}
}
