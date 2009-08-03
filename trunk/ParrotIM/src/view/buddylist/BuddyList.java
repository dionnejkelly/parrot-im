/* BuddyList.java
 * 
 * Copyright (C) 2009  Pirate Captains
 * 
 * Full license can be found in ParrotIM/LICENSE.txt.
 */

package view.buddylist;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.Point;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import model.Model;
import model.dataType.AccountData;
import model.enumerations.UpdatedType;
import model.enumerations.UserStateType;
import view.chatLog.ChatLogFrame;
import view.chatwindow.ChatWindow;
import view.mainwindow.AboutFrame;
import view.mainwindow.HelpPanel;
import view.mainwindow.MainWindow;
import view.options.BugReportFrame;
import view.options.MusicPlayer;
import view.options.OptionFrame;
import view.styles.AccountJMenu;
import view.styles.GPanel;
import controller.MainController;

/**
 * BuddyList display Friend contact list for Parrot IM users.
 */
public class BuddyList extends JFrame implements Observer {
    private JTabbedPane contactList;
    private static ArrayList<AccountJMenu> accountMenuList;
    private BuddyPanel mainListPanel;
    private TwitterPanel mainTwitterPanel;
    private TrayIcon trayIcon;
    private SystemTray tray;
    /**
     * menu bar in buddy list
     */
    JMenuBar menu;
    static JMenu contactMenu;
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

    private GPanel buddylistPanel;

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
        model.addObserver(this);
        this.controller = c;
        accountMenuList = new ArrayList<AccountJMenu>();

        // INSIDE PANEL
        try {
            accountInfo = new AccountInfo(c, model);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Create chat window
        this.chat = new ChatWindow(c, model, this);

        this.setLocation(location);
        this.setMinimumSize(new Dimension(300, 600));

        buddylistPanel = new GPanel();
        buddylistPanel.setLayout(new BorderLayout());
        buddylistPanel.setPreferredSize(new Dimension(300, 600));
        buddylistPanel.setGradientColors(model.primaryColor,
                model.secondaryColor);

        mainListPanel = new BuddyPanel(c, model, this);
        mainTwitterPanel = new TwitterPanel(c, model, this);

        // If twitter exists, make tabbed buddy frame ; add to buddylistpanel
        contactList = new JTabbedPane();
        contactList.addTab("IM", new ImageIcon(this.getClass().getResource
                        		("/images/buddylist/statusIcons/GoogleTalk/GoogleTalk-Available.png")),
                        mainListPanel, "Instant Messaging Contact List");
        contactList.addTab("Twitter", new ImageIcon(this.getClass()
                .getResource("/images/buddylist/twitter_logo.png")),
                mainTwitterPanel, "Twitter Feed");
        contactList.addChangeListener(new ContactListChangeListener());

        if (model.getCurrentProfile().hasTwitterOnly()) {
            contactList.setSelectedIndex(1);
        }
        buddylistPanel.add(contactList, BorderLayout.CENTER);

        buddylistPanel.setStartPosition(92);
        // add to buddylistPanel
        buddylistPanel.add(accountInfo, BorderLayout.NORTH);

        // Attach the top text menu
        this.setJMenuBar(this.createMenu());
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setIconImage(new ImageIcon(this.getClass().getResource(
                "/images/mainwindow/logo.png")).getImage());

        getContentPane().add(buddylistPanel);
        pack();
        setVisible(true);

        new MusicPlayer("/audio/startup/parrotOpening.wav", model);
        
        //SYSTEM TRAY
        if (SystemTray.isSupported()) {

            tray = SystemTray.getSystemTray();
            Image image = new ImageIcon(this.getClass().getResource("/images/mainwindow/logo.png")).getImage();
   
            PopupMenu popup = new PopupMenu();
            MenuItem defaultItem = new MenuItem("Exit");
            defaultItem.addActionListener(new SysTrayExitListener());
            popup.add(defaultItem);

            trayIcon = new TrayIcon(image, "Parrot IM - " + model.getCurrentProfile().getName(), popup);
            trayIcon.setImageAutoSize(true);
            trayIcon.addMouseListener(new SysTrayMouseListener());
            try {
                tray.add(trayIcon);
            } catch (AWTException e) {
                System.err.println("TrayIcon could not be added.");
            }
        }

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

        // FILE//
        // Mac already has a File menu, create this only on PC/Linux
        String lcOSName = System.getProperty("os.name").toLowerCase();
        if (!(lcOSName.startsWith("mac"))) {
            fileMenu = new JMenu("Home");
            fileMenu.setMnemonic(KeyEvent.VK_H);
            menuBar.add(fileMenu);

            JMenuItem exitItem1 =
                    new JMenuItem("Exit", new ImageIcon(this.getClass()
                            .getResource("/images/menu/stop.png")));
            fileMenu.add(exitItem1);
            exitItem1.addActionListener(new exitActionListener());
        }

        // ACCOUNTS//
        contactMenu = new JMenu("Accounts");
        contactMenu.setMnemonic(KeyEvent.VK_A);

        JMenuItem logoutItem =
                new JMenuItem("Sign Out " + model.getCurrentProfile().getName()
                        + "\'s Profile", new ImageIcon(this.getClass()
                        .getResource("/images/menu/sign_out.png")));
        logoutItem.addActionListener(new signoutActionListener());
        contactMenu.add(logoutItem);

        contactMenu.addSeparator();

        for (AccountData account : model.getCurrentProfile().getAccountData()) {
            accountMenuList.add(new AccountJMenu(account, controller,
                    mainListPanel, model));
            contactMenu.add(accountMenuList.get(accountMenuList.size() - 1));
        }

        menuBar.add(contactMenu);

        // OPTIONS//
        optionsMenu = new JMenu("Options");
        optionsMenu.setMnemonic(KeyEvent.VK_O);
        menuBar.add(optionsMenu);

        JMenuItem optionsItem1 =
                new JMenuItem("Parrot Preferences", new ImageIcon(this
                        .getClass().getResource("/images/menu/tick.png")));
        optionsItem1.addActionListener(new optionListener());
        optionsMenu.add(optionsItem1);

        JMenuItem viewChatLog =
                new JMenuItem("View Chat Log", new ImageIcon(this.getClass()
                        .getResource("/images/menu/note.png")));
        viewChatLog.addActionListener(new chatLogListener());
        optionsMenu.add(viewChatLog);

        // HELP//
        helpMenu = new JMenu("Help");
        helpMenu.setMnemonic(KeyEvent.VK_H);
        menuBar.add(helpMenu);
        JMenuItem helpItem1 =
                new JMenuItem("User Guide", new ImageIcon(this.getClass()
                        .getResource("/images/menu/information.png")));
        // KeyEvent.VK_G
        helpItem1.addActionListener(new helpListener());
        JMenuItem helpItem2 =
                new JMenuItem("Report Bug", new ImageIcon(this.getClass()
                        .getResource("/images/menu/bug.png")));
        // KeyEvent.VK_R
        helpItem2.addActionListener(new reportBugListener());
        JMenuItem helpItem3 =
                new JMenuItem("About Parrot.IM", new ImageIcon(this.getClass()
                        .getResource("/images/menu/report.png")));
        // KeyEvent.VK_A
        helpItem3.addActionListener(new aboutListener());
        helpMenu.add(helpItem1);
        helpMenu.add(helpItem2);
        helpMenu.addSeparator();
        helpMenu.add(helpItem3);

        return menuBar;
    }

    private String getStatusMessage() throws ClassNotFoundException, SQLException{
    	String status = model.getStatusMessage(model.getCurrentProfile().getName());
    	if (status == null)
    		return "(Type your status message)";
    	return status;
    }
    private String getStatus() throws ClassNotFoundException, SQLException{
    	int state = model.getStatus(model.getCurrentProfile().getName());
    	switch(state){
    	case 0 : return UserStateType.ONLINE.toString();
    	case 1 : return UserStateType.AWAY.toString();
    	case 2 : return UserStateType.BUSY.toString();
    	case 3 : return UserStateType.PHONE.toString();
    	case 4 : return UserStateType.LUNCH.toString();
    	case 5 : return UserStateType.BRB.toString();
    	case 6 : return UserStateType.INVISIBLE.toString();
    	}
    	return UserStateType.ONLINE.toString();
    }
    public static void removeAccountJMenu(AccountData account) {
        int pos = 0;
        for (pos = 0; pos < accountMenuList.size(); pos++) {
            if (accountMenuList.get(pos).getAccount().equals(account)) {
                break;
            }
        }
        contactMenu.remove(accountMenuList.get(pos));
        accountMenuList.remove(pos);
    }

    public void addAccountJMenu(AccountData account) {
        accountMenuList.add(new AccountJMenu(account, controller,
                mainListPanel, model));
        accountMenuList.get(accountMenuList.size() - 1).connectAccount(true);
        contactMenu.add(accountMenuList.get(accountMenuList.size() - 1));
    }

    public BuddyPanel getBuddyPanel() {
        return mainListPanel;
    }

    public AccountInfo getAccountInfo() {
        return accountInfo;
    }

    public OptionFrame getOptions() {
        return options;
    }

    public static boolean optionsIsVisible() {
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
            new HelpPanel("http://sites.google.com/site/parrotimhelp/Home");

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
            // disable chatlog account if it's guest account
            if (model.getCurrentProfile().isGuestAccount()) {
                JOptionPane.showMessageDialog(null,
                        "Please create a profile to use the Chatlog feature.",
                        "Information", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
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

    private class ChatLogWindowListener implements WindowListener {

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
            if (!optionsIsVisible()) {
                try {
                    options = new OptionFrame(controller, model, buddywindow);
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

    private class OptionsWindowListener implements WindowListener {

        public void windowActivated(WindowEvent e) {
        }

        public void windowClosed(WindowEvent e) {
            accountInfo.statusMessage.changePM(false, true);
            options = null;
        }

        public void windowClosing(WindowEvent e) {
        }

        public void windowDeactivated(WindowEvent e) {
        }

        public void windowDeiconified(WindowEvent e) {
        }

        public void windowIconified(WindowEvent e) {
        }

        public void windowOpened(WindowEvent e) {
        }
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
            if (options != null) {
                options.dispose();
            }
            if (chatlog != null) {
                chatlog.dispose();
            }
            Model newModel = new Model();
            new MainWindow(new MainController(newModel), newModel, buddywindow
                    .getLocation());
            // TODO: might want to reset the data/variables/list in model
            buddywindow.removeTray();
            buddywindow.dispose();
            new MusicPlayer("/audio/exit/parrotExit.wav", model);

            return;
        }
    }

    public void update(Observable o, Object arg) {
    	if (arg == UpdatedType.COLOR) {
            buddylistPanel.setGradientColors(model.primaryColor,
                    model.secondaryColor);
            mainListPanel.setGradientColors(model.primaryColor,
                    model.secondaryColor);
            mainTwitterPanel.setGradientColors(model.primaryColor,
                    model.secondaryColor);
            mainListPanel.updateUI();
            mainTwitterPanel.updateUI();
            buddylistPanel.updateUI();
            contactList.updateUI();
        }
    }

    private class ContactListChangeListener implements ChangeListener {

        public void stateChanged(ChangeEvent arg0) {
            // TODO Auto-generated method stub
            if (model.getCurrentProfile().isEmptyProfile()) {
                return;
            }

            if (contactList.getSelectedIndex() == 1
                    && !model.getCurrentProfile().hasTwitter()) {
                JOptionPane.showMessageDialog(null,
                        "You don't have any Twitter account.", "Information",
                        JOptionPane.INFORMATION_MESSAGE);
                contactList.setSelectedIndex(0);
            } else if (contactList.getSelectedIndex() == 0
                    && model.getCurrentProfile().hasTwitterOnly()) {
                JOptionPane.showMessageDialog(null,
                        "You don't have any Instant Messenger account.",
                        "Information", JOptionPane.INFORMATION_MESSAGE);
                contactList.setSelectedIndex(1);
            }

        }

        public void update(Observable o, Object arg) {
            if (arg == UpdatedType.COLOR) {
                buddylistPanel.setGradientColors(model.primaryColor,
                        model.secondaryColor);
                buddylistPanel.updateUI();
            }
        }
    }
    protected void removeTray (){
    	if (tray != null){
            tray.remove(trayIcon);
    	}
    }
    private class SysTrayExitListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }
    private class SysTrayMouseListener implements MouseListener{
        public void mouseClicked(MouseEvent e) {
        	try {
				trayIcon.displayMessage("ParrotIM - " + model.getCurrentProfile().getName(), 
					"Status: " + getStatusMessage() + "\n" + 
					"(" + getStatus() + ")",
				    TrayIcon.MessageType.INFO);
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        }

        public void mouseEntered(MouseEvent e) {
        	try {
				trayIcon.displayMessage("ParrotIM - " + model.getCurrentProfile().getName(), 
					"Status: " + getStatusMessage() + "\n" + 
					"(" + getStatus() + ")",
				    TrayIcon.MessageType.INFO);
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        }

        public void mouseExited(MouseEvent e) {}

        public void mousePressed(MouseEvent e) {}

        public void mouseReleased(MouseEvent e) {
        	buddywindow.setVisible(true);                
        }
    }
}