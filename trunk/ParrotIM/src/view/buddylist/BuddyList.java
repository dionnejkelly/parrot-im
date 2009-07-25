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
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import view.mainwindow.HelpPanel;
import view.mainwindow.MainWindow;
import view.mainwindow.AboutFrame;
import view.options.BugReportFrame;
import view.options.MusicPlayer;
import view.options.OptionFrame;
import view.styles.AccountJMenu;
import view.styles.GPanel;

import view.chatLog.ChatLogFrame;
import view.chatwindow.ChatWindow;

import controller.MainController;

import model.Model;
import model.dataType.AccountData;
import model.enumerations.ServerType;

/**
 * BuddyList display Friend contact list for Parrot IM users.
 */
public class BuddyList extends JFrame {
	private ArrayList<AccountJMenu> accountMenuList;
	private BuddyPanel mainListPanel;
    /**
     * menu bar in buddy list
     */
    JMenuBar menu;
    JMenu contactMenu;
    /**
     * variable model for extracting buddy list, each buddy's information and ,
     * conversation
     */
    protected Model model;
    /**
     * variable controller for setting user status and instantiates
     * conversations.
     */
    private MainController controller;

    private ChatWindow chat;

    private ChatLogFrame chatlog;
    private static OptionFrame options;
    /**
     * buddy window frame
     */
    protected BuddyList buddywindow;

    private AccountInfo accountInfo;

    /**
     * BuddyList display friend contact list, status, information and
     * conversation.
     * 
     * @param c
     * @param model
     * @param location
     * @throws ClassNotFoundException 
     */
    public BuddyList(MainController c, Model model, Point location) 
    									throws ClassNotFoundException {
    	
        // setLocationRelativeTo(null);
        buddywindow = this;
        this.setTitle("Buddy List");
        this.model = model;
        this.controller = c;
        accountMenuList = new ArrayList <AccountJMenu> ();
        
        // INSIDE PANEL
        try {
			accountInfo = new AccountInfo(c, model);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        // Create chat window
        this.chat = new ChatWindow(c, model, this);
        
        this.setLocation(location);
        this.setMinimumSize(new Dimension(300, 600));

        GPanel buddylistPanel = new GPanel();
        buddylistPanel.setLayout(new BorderLayout());
        buddylistPanel.setPreferredSize(new Dimension(300, 600));
        buddylistPanel.setGradientColors(buddylistPanel.colors.PRIMARY_COLOR_MED, Color.WHITE);


        mainListPanel = new BuddyPanel(c, model, this, chat);

        //If twitter exists, make tabbed buddy frame ; add to buddylistpanel
        if (model.getCurrentProfile().getAccountFromServer(ServerType.TWITTER) != null){
        	JTabbedPane contactList = new JTabbedPane();
        	contactList.addTab("IM", new ImageIcon(this.getClass().getResource(
    		"/images/buddylist/statusIcons/GoogleTalk/GoogleTalk-Available.png")), mainListPanel, "Instant Messaging Contact List");
        	contactList.addTab("Twitter", new ImageIcon(this.getClass().getResource(
        	"/images/buddylist/twitter_logo.png")), new TwitterPanel(c,model,this,mainListPanel), "Twitter Feed");
        	buddylistPanel.add(contactList, BorderLayout.CENTER);
        	
        	contactList.addChangeListener(new ChangeListener(){
        		//Modifies info panel according to twitter/IM tab
        		public void stateChanged(ChangeEvent evt){
        			JTabbedPane contactPane = (JTabbedPane)evt.getSource();
        			if (contactPane.getSelectedIndex() == 1)
        				accountInfo.TwitterpanelSelected();
        			else 
        				accountInfo.IMpanelSelected();
        		}
        	});
        }
        else{
        	buddylistPanel.add(mainListPanel, BorderLayout.CENTER);
        }

        buddylistPanel.setStartPosition(92);
        // add to buddylistPanel
        buddylistPanel.add(accountInfo, BorderLayout.NORTH);
        
        // Attach the top text menu
        this.setJMenuBar(this.createMenu());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setIconImage(new ImageIcon("src/images/mainwindow/logo.png").getImage());
        
        // buddylistPanel.add(mainListPanel, BorderLayout.CENTER);
        getContentPane().add(buddylistPanel);
        pack();
        setVisible(true);


        MusicPlayer receiveMusic =
                new MusicPlayer("/audio/startup/parrotOpening.wav", model);

    }

    // Creates top Text Menu
    /**
     * createMenu(), create menu on top of buddy list. It provide access to
     * functions such as files, contact, options and help.
     * 
     * @return menuBar
     */
    public JMenuBar createMenu() {
        JMenuBar menuBar;
        JMenu fileMenu, optionsMenu, helpMenu;

        // Create the menu bar.
        menuBar = new JMenuBar();

        //FILE//
        //Mac already has a File menu, create this only on PC/Linux
        String lcOSName = System.getProperty("os.name").toLowerCase();
        if (!(lcOSName.startsWith("mac"))) {
        	fileMenu = new JMenu("Home");
            fileMenu.setMnemonic(KeyEvent.VK_H);
            menuBar.add(fileMenu);
       
            JMenuItem exitItem1 = new JMenuItem("Exit",  new ImageIcon(this.getClass().getResource(
            "/images/menu/stop.png")));
            fileMenu.add(exitItem1);
            exitItem1.addActionListener(new exitActionListener());
        }

        //ACCOUNTS//
        contactMenu = new JMenu("Accounts");
        contactMenu.setMnemonic(KeyEvent.VK_A);
        
        JMenuItem logoutItem = new JMenuItem("Sign Out " + model.getCurrentProfile().getName()
        		+"\'s Profile",  new ImageIcon(this.getClass().getResource(
        "/images/menu/sign_out.png")));
        logoutItem.addActionListener(new signoutActionListener());
        contactMenu.add(logoutItem);
        
        contactMenu.addSeparator();
        
        for (AccountData account : model.getCurrentProfile().getAccountData()){
        	accountMenuList.add(new AccountJMenu(account, controller, mainListPanel));
        	contactMenu.add(accountMenuList.get(accountMenuList.size()-1));
        }
        
        menuBar.add(contactMenu);
        
        //OPTIONS//
        optionsMenu = new JMenu("Options");
        optionsMenu.setMnemonic(KeyEvent.VK_O);
        menuBar.add(optionsMenu);
        
        JMenuItem optionsItem1 =
                new JMenuItem("Parrot Preferences",  new ImageIcon(this.getClass().getResource(
                "/images/menu/tick.png")));
        optionsItem1.addActionListener(new optionListener());
        optionsMenu.add(optionsItem1);
        
        JMenuItem viewChatLog = new JMenuItem("View Chat Log", new ImageIcon(this.getClass().getResource(
        "/images/menu/note.png")));
        viewChatLog.addActionListener(new chatLogListener());
        optionsMenu.add(viewChatLog);
        
        //HELP//
        helpMenu = new JMenu("Help");
        helpMenu.setMnemonic(KeyEvent.VK_H);
        menuBar.add(helpMenu);
        JMenuItem helpItem1 = new JMenuItem("User Guide",  new ImageIcon(this.getClass().getResource(
        "/images/menu/information.png")));
        // KeyEvent.VK_G
        helpItem1.addActionListener(new helpListener());
        JMenuItem helpItem2 = new JMenuItem("Report Bug",  new ImageIcon(this.getClass().getResource(
        "/images/menu/bug.png")));
        //KeyEvent.VK_R
        helpItem2.addActionListener(new reportBugListener());
        JMenuItem helpItem3 = new JMenuItem("About Parrot.IM",  new ImageIcon(this.getClass().getResource(
        "/images/menu/report.png")));
        // KeyEvent.VK_A
        helpItem3.addActionListener(new aboutListener());
        helpMenu.add(helpItem1);
        helpMenu.add(helpItem2);
        helpMenu.addSeparator();
        helpMenu.add(helpItem3);

        return menuBar;
    }

    public void removeAccountJMenu(int i){
    	accountMenuList.get(i).connectAccount(false);
    	contactMenu.remove(accountMenuList.get(i));
    	accountMenuList.remove(i);
    }
    public void addAccountJMenu(AccountData account){
    	accountMenuList.add(new AccountJMenu(account, controller, mainListPanel));
    	accountMenuList.get(accountMenuList.size()-1).connectAccount(true);
    	contactMenu.add(accountMenuList.get(accountMenuList.size()-1));
    }
    
    public AccountInfo getAccountInfo(){
    	return accountInfo;
    }
    
    public OptionFrame getOptions(){
    	return options;
    }
    public static boolean optionsIsVisible(){
    	return options != null;
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
            if (!model.aboutWindowOpen) {
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

        public void actionPerformed(ActionEvent e) {
            new HelpPanel(
                    "http://sites.google.com/site/parrotimhelp/Home");

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
            // new HelpPanel("http://code.google.com/p/parrot-im/issues/entry");
        	
            if (!model.bugReportWindowOpen) {
            	new BugReportFrame(model);
            }
            
            return;
        }
    }

    /**
     * chatLog listener
     * 
     */
    private class chatLogListener implements ActionListener {
        /**
         * Listens for the uesr's event.
         * 
         * @param e
         */
        public void actionPerformed(ActionEvent e) {
            if (chatlog == null) {
                try {
                    chatlog = new ChatLogFrame(model);
                    chatlog.addWindowListener(new ChatLogWindowListener());
                } catch (SQLException e1) {
                    e1.printStackTrace();
                } catch (ClassNotFoundException e1) {
                    e1.printStackTrace();
                }
            } else {
            	chatlog.setAlwaysOnTop(true);
            	chatlog.setAlwaysOnTop(false);
            }
            return;
        }
    }
	private class ChatLogWindowListener implements WindowListener{

		public void windowActivated(WindowEvent e) {}

		public void windowClosed(WindowEvent e) {
			chatlog = null;
		}

		public void windowClosing(WindowEvent e) {}
		public void windowDeactivated(WindowEvent e) {}
		public void windowDeiconified(WindowEvent e) {}
		public void windowIconified(WindowEvent e) {}
		public void windowOpened(WindowEvent e) {}
		
	}

    /**
     * option listener
     * 
     */
    private class optionListener implements ActionListener {
        /*
         * (non-Javadoc)
         * 
         * @see
         * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent
         * )
         */
        public void actionPerformed(ActionEvent e) {
        	if (!optionsIsVisible()){
	            try {
	            	options = new OptionFrame(controller, model, buddywindow);
//	                options.addWindowListener(new PopupWindowListener(buddywindow, options));
	                options.addWindowListener(new OptionsWindowListener());
	            } catch (ClassNotFoundException e1) {
	                e1.printStackTrace();
	            } catch (SQLException e1) {
	                e1.printStackTrace();
	            }
        	} else {
            	options.setAlwaysOnTop(true);
            	options.setAlwaysOnTop(false);
            }
        	
            return;
        }
    }

	private class OptionsWindowListener implements WindowListener{

		public void windowActivated(WindowEvent e) {}

		public void windowClosed(WindowEvent e) {
			accountInfo.statusMessage.changePM(false, true);
			options = null;
		}

		public void windowClosing(WindowEvent e) {}
		public void windowDeactivated(WindowEvent e) {}
		public void windowDeiconified(WindowEvent e) {}
		public void windowIconified(WindowEvent e) {}
		public void windowOpened(WindowEvent e) {}
		
	}

    /**
     * Listens for the exit action.
     * 
     */
    private class exitActionListener implements ActionListener {

        /**
         * Listens for the uesr's event.
         * 
         * @param e
         */
        public void actionPerformed(ActionEvent e) {
            controller.disconnect();
            buddywindow.dispose();
            new MusicPlayer("/audio/exit/parrotExit.wav", model);

            System.exit(0);
            return;
        }

    }

    /**
     * Listens for the signout action
     * 
     */
    private class signoutActionListener implements ActionListener {

        /**
         * Listens for the uesr's event.
         * 
         * @param e
         */
        public void actionPerformed(ActionEvent e) {
            controller.disconnect();
            model.deleteObserver(chat);
            chat.dispose();
            if (options != null) options.dispose();
            if (chatlog != null) chatlog.dispose();
            Model newModel = new Model();
            new MainWindow(new MainController(newModel), newModel, buddywindow.getLocation());
            // TODO: might want to reset the data/variables/list in model
            buddywindow.dispose();
            new MusicPlayer("/audio/exit/parrotExit.wav", model);

            return;
        }

    }
}