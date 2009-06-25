package view.buddylist;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.sql.SQLException;

import javax.swing.*;

import view.mainwindow.MainWindow;
import view.options.OptionMenu;

import view.chatLog.ChatLogFrame;

import controller.MainController;

import model.Model;

public class BuddyList extends JFrame {
    JMenuBar menu;
    protected Model model;
    private MainController controller;
    protected JFrame buddywindow;

    public BuddyList(MainController c, Model model) {
    	buddywindow = this;
        this.setTitle("Buddy List");
        this.model = model;
        this.controller = c;

        this.setMinimumSize(new Dimension(300, 600));

        // Attach the top text menu
        this.setJMenuBar(this.createMenu());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setIconImage(new ImageIcon("/images/mainwindow/logo.png").getImage());

        JPanel buddylistPanel = new JPanel();
        buddylistPanel.setLayout(new BorderLayout());
        buddylistPanel.setPreferredSize(new Dimension(300, 600));

        // INSIDE PANEL
        JPanel mainListPanel = new BuddyPanel(c, model, this);
        JPanel accountInfo = new AccountInfo(c, model);

        // add to buddylistPanel
        buddylistPanel.add(accountInfo, BorderLayout.NORTH);
        buddylistPanel.add(mainListPanel, BorderLayout.CENTER);
        getContentPane().add(buddylistPanel);
        pack();
        setVisible(true);
    }

    // Creates top Text Menu
    public JMenuBar createMenu() {
        JMenuBar menuBar;
        JMenu fileMenu, acctsMenu, contactMenu, optionsMenu, helpMenu;
        JMenuItem menuItem;

        // Create the menu bar.
        menuBar = new JMenuBar();

        fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);
        menuBar.add(fileMenu);
        JMenuItem logoutItem = new JMenuItem("Sign Out", KeyEvent.VK_L);
        logoutItem.addActionListener(new signoutActionListener());
        fileMenu.add(logoutItem);
        JMenuItem exitItem1 = new JMenuItem("Exit", KeyEvent.VK_N);
        fileMenu.add(exitItem1);
        exitItem1.addActionListener(new exitActionListener());

       /* acctsMenu = new JMenu("Accounts");
        acctsMenu.setMnemonic(KeyEvent.VK_A);
        menuBar.add(acctsMenu);
        JMenuItem accountsItem1 = new JMenuItem("Edit Accounts", KeyEvent.VK_E);
        acctsMenu.add(accountsItem1);*/

        contactMenu = new JMenu("Contacts");
        contactMenu.setMnemonic(KeyEvent.VK_C);
        JMenuItem viewChatLog = new JMenuItem("View Chat Log", KeyEvent.VK_C);
        viewChatLog.addActionListener(new chatLogListener());
        contactMenu.add(viewChatLog);
        
        JCheckBoxMenuItem chatbotEnabler = new JCheckBoxMenuItem("Chatbot Enabled");
        chatbotEnabler.setMnemonic(KeyEvent.VK_H);
        chatbotEnabler.addActionListener(new ChatbotToggleListener());
        contactMenu.add(chatbotEnabler);
        menuBar.add(contactMenu);

        optionsMenu = new JMenu("Options");
        optionsMenu.setMnemonic(KeyEvent.VK_O);
        menuBar.add(optionsMenu);
        JMenuItem optionsItem1 = new JMenuItem("Parrot Preferences",
                KeyEvent.VK_P);
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

    private class chatLogListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
        	if (!model.logWindowOpen){
        		try {
					new ChatLogFrame(model);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
        	}
            return;
        }        
    }

    private class optionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            new OptionMenu();

            return;
        }
    }
    
    private class ChatbotToggleListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            controller.toggleChatbot();
            
            return;
        }
    }
    
    private class exitActionListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			
			controller.signout();
			buddywindow.dispose();
			
		}
    	
    }
    
    private class signoutActionListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			controller.signout();
			try {
				new MainWindow(controller, model);
				//TODO: might want to reset the data/variables/list in model
				buddywindow.dispose();
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
    	
    }
}