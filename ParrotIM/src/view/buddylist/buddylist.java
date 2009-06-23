package view.buddylist;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.*;

import view.options.OptionMenu;

import view.chatLog.chatLogFrame;

import controller.services.Xmpp;

import model.Model;

public class buddylist extends JFrame{
	JMenuBar menu;
	protected Model model;
	
	public buddylist(Xmpp c, Model model)
	{
		this.setTitle("Buddy List");
		this.model=model;
		
		this.setMinimumSize(new Dimension(300,600));
		
		// Attach the top text menu
		this.setJMenuBar(this.createMenu());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setIconImage(new ImageIcon("images/mainwindow/logo.png").getImage());

		JPanel buddylistPanel = new JPanel();
		buddylistPanel.setLayout(new BorderLayout());
		buddylistPanel.setPreferredSize(new Dimension(300,600));
		
	    //INSIDE PANEL
		JPanel mainListPanel = new buddyPanel(c, model);
		JPanel accountInfo = new accInfo(c, model);
	    
	    //add to buddylistPanel
		buddylistPanel.add(accountInfo, BorderLayout.NORTH);
		buddylistPanel.add(mainListPanel, BorderLayout.CENTER);
		getContentPane().add(buddylistPanel);   
		pack();
		setVisible(true);
	}

	//Creates top Text Menu
	public JMenuBar createMenu()
	{
	       	JMenuBar menuBar;
			JMenu fileMenu, acctsMenu, contactMenu, optionsMenu, helpMenu;
			JMenuItem menuItem;

	        //Create the menu bar.
	        menuBar = new JMenuBar();

	        fileMenu = new JMenu("File");
		    fileMenu.setMnemonic(KeyEvent.VK_F);
		    menuBar.add(fileMenu);
		    JMenuItem exitItem1 = new JMenuItem("Exit", KeyEvent.VK_N);
		    fileMenu.add(exitItem1);
		    
			acctsMenu = new JMenu("Accounts");
		    acctsMenu.setMnemonic(KeyEvent.VK_A);
		    menuBar.add(acctsMenu);
		    JMenuItem accountsItem1 = new JMenuItem("Edit Accounts", KeyEvent.VK_E);
		    acctsMenu.add(accountsItem1);
			
			contactMenu = new JMenu("Contact");
			contactMenu.setMnemonic(KeyEvent.VK_C);
			JMenuItem viewChatLog = new JMenuItem("View Chat Log", KeyEvent.VK_C);
			viewChatLog.addActionListener(new chatLogListener());
		    contactMenu.add(viewChatLog);
			menuBar.add(contactMenu);
		    
			optionsMenu = new JMenu("Options");
		    optionsMenu.setMnemonic(KeyEvent.VK_O);
		    menuBar.add(optionsMenu);
		    JMenuItem optionsItem1 = new JMenuItem("Parrot Preferences", KeyEvent.VK_P);
		    optionsItem1.addActionListener(new optionListener());
		    optionsMenu.add(optionsItem1);
		    
			helpMenu = new JMenu("Help");
		    helpMenu.setMnemonic(KeyEvent.VK_H);
		    menuBar.add(helpMenu);
		    JMenuItem helpItem1 = new JMenuItem("User Guide", KeyEvent.VK_G);
		    JMenuItem helpItem2 = new JMenuItem("Report Bug", KeyEvent.VK_R);
		    JMenuItem helpItem3 = new JMenuItem("About Parrot.IM", KeyEvent.VK_A);
		    helpMenu.add(helpItem1);
		    helpMenu.add(helpItem2);
		    helpMenu.addSeparator();
		    helpMenu.add(helpItem3); 
	        
	        return menuBar;
	}
	
	private class chatLogListener implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			new chatLogFrame(model); 
			
		}
		
	}private class optionListener implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			new OptionMenu();
			
		}
		
	}
}