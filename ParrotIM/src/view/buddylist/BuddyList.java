/* BuddyList.java
 * 
 * Copyright (C) 2009  Pirate Captains
 * 
 * Full license can be found in ParrotIM/LICENSE.txt.
 */

package view.buddylist;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.sql.SQLException;

import javax.swing.*;

import org.jivesoftware.smack.XMPPException;

import view.mainwindow.HelpPanel;
import view.mainwindow.MainWindow;
import view.mainwindow.AboutFrame;
import view.options.OptionMenu;

import view.chatLog.ChatLogFrame;

import controller.MainController;

import model.Model;
/**
 * BuddyList display Friend contact list for Parrot IM users.
 */
public class BuddyList extends JFrame {
    /**
     * menu bar in buddy list
     */
    JMenuBar menu;
    /**
     * variable model for extracting buddy list, each buddy's information and , conversation 
     */
    protected Model model;
    /**
     * variable controller for setting user status and instantiates conversations.
     */
    private MainController controller;
    /**
     * buddy window frame
     */
    protected JFrame buddywindow;

    /**
     * BuddyList display friend contact list, status, information and conversation.
     * @param c
     * @param model
     */
    public BuddyList(MainController c, Model model) {
    	buddywindow = this;
        this.setTitle("Buddy List");
        this.model = model;
        this.controller = c;

        this.setMinimumSize(new Dimension(300, 600));

        // Attach the top text menu
        this.setJMenuBar(this.createMenu());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setIconImage(new ImageIcon("src/images/mainwindow/logo.png").getImage());

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
    /**
     * createMenu(), create menu on top of buddy list. It provide access to functions such as files, contact, options and help.
     * @return menuBar
     */
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
        helpItem1.addActionListener(new helpListener());
        JMenuItem helpItem2 = new JMenuItem("Report Bug", KeyEvent.VK_R);
        helpItem2.addActionListener(new reportBugListener());
        JMenuItem helpItem3 = new JMenuItem("About Parrot.IM", KeyEvent.VK_A);
        helpItem3.addActionListener(new aboutListener());
        helpMenu.add(helpItem1);
        helpMenu.add(helpItem2);
        helpMenu.addSeparator();
        helpMenu.add(helpItem3);
        

        return menuBar;
    }
    
    /**
     * about listener
     *
     */
    private class aboutListener implements ActionListener {
        /* 
         * 
         */
        public void actionPerformed(ActionEvent e) {
        	if (!model.aboutWindowOpen){
					new AboutFrame(model);
        	}
            return;
        }        
    }
    /**
     * helpListener
     *
     */
    private class helpListener implements ActionListener {
        /* 
         * 
         */
        public void actionPerformed(ActionEvent e) {
            new HelpPanel("http://code.google.com/p/parrot-im/wiki/NewTutorial_Exit");

            return;
        }
    }
    /**
    * reportBugListener
    *
    */
   private class reportBugListener implements ActionListener {
       /* 
        * 
        */
       public void actionPerformed(ActionEvent e) {
           new HelpPanel("http://code.google.com/p/parrot-im/issues/entry");

           return;
       }
   }
    

    /**
     * chatLog listener
     *
     */
    private class chatLogListener implements ActionListener {
        /* 
         * 
         */
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

    /**
     * option listener
     *
     */
    private class optionListener implements ActionListener {
        /* (non-Javadoc)
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        public void actionPerformed(ActionEvent e) {
            new OptionMenu();

            return;
        }
    }
    
    /**
     * @author TOSHIBA
     *
     */
    private class ChatbotToggleListener implements ActionListener {
        /* (non-Javadoc)
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        public void actionPerformed(ActionEvent e) {
            controller.toggleChatbot();
            
            return;
        }
    }
    
    /**
     * @author TOSHIBA
     *
     */
    private class exitActionListener implements ActionListener {

		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent e) {
			
			try {
				controller.disconnect();
				buddywindow.dispose();
			} catch (XMPPException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
    	
    }
    
    /**
     * @author TOSHIBA
     *
     */
    private class signoutActionListener implements ActionListener {

		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent e) {
			try {
				controller.disconnect();
				new MainWindow(controller, model);
				//TODO: might want to reset the data/variables/list in model
				buddywindow.dispose();
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			} catch (SQLException e1) {
				e1.printStackTrace();
			} catch (XMPPException e2) {
				e2.printStackTrace();
			}
		}
    	
    }
}