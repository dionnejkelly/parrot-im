package view.styles;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import model.Model;
import model.dataType.AccountData;
import model.enumerations.ServerType;
import model.enumerations.StatusType;

import org.jivesoftware.smack.XMPPException;

import view.buddylist.AddBuddyFrame;
import view.buddylist.BuddyList;
import view.buddylist.BuddyPanel;
import controller.MainController;
import controller.services.BadConnectionException;

public class AccountJMenu extends JMenu {
    MainController controller;
    AccountData account;
    BuddyPanel buddies;
    JMenuItem signMenu;

    private JMenuItem signOutMenu;

    private Model model;

    protected AccountData userAccount;

    private URL url;

    public AccountJMenu(AccountData account, MainController c,
            BuddyPanel buddies, Model model) {
        super(account.getServer() + " - " + account.getUserID());

        this.model = model;
        this.userAccount = account;

        if (account.getServer() == ServerType.GOOGLE_TALK) {
            setIcon(new ImageIcon(
                    this
                            .getClass()
                            .getResource(
                                    "/images/buddylist/statusIcons/GoogleTalk/GoogleTalk-Available.png")));
        }

        else if (account.getServer() == ServerType.JABBER) {
            setIcon(new ImageIcon(
                    this
                            .getClass()
                            .getResource(
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

        // SIGN MENU//
        signMenu =
                new JMenuItem("Sign In", new ImageIcon(this.getClass()
                        .getResource("/images/menu/account/status.png")));
        signMenu.setEnabled(false);
        signMenu.setMnemonic(KeyEvent.VK_S);
        signMenu.addActionListener(new SignMenuActionListener());

        signOutMenu =
                new JMenuItem("Sign Out", new ImageIcon(this.getClass()
                        .getResource("/images/menu/account/status-busy.png")));
        signOutMenu.setEnabled(true);
        signOutMenu.setMnemonic(KeyEvent.VK_S);
        signOutMenu.addActionListener(new SignMenuActionListener());

        JMenuItem addFriendMenu =
                new JMenuItem("Add a friend", new ImageIcon(this.getClass()
                        .getResource("/images/buddylist/add_user.png")));
        addFriendMenu.setMnemonic(KeyEvent.VK_A);
        addFriendMenu.addActionListener(new AddFriendListener());
        JMenuItem removeMenu =
                new JMenuItem("Remove account", new ImageIcon(this.getClass()
                        .getResource("/images/mainwindow/remove.png")));
        removeMenu.setMnemonic(KeyEvent.VK_R);
        removeMenu.addActionListener(new RemoveMenuActionListener());

        this.add(signMenu);
        this.add(signOutMenu);
        this.add(addFriendMenu);
        this.addSeparator();
        this.add(removeMenu);

    }

    public AccountData getAccount() {
        return account;
    }

    public void connectAccount(boolean connect) {
        if (connect) { // connecting
            try {
                controller.login(account);
                try {
                    controller.setStatus(model.getStatusMessage(model
                            .getCurrentProfile().getName()), false);
                    controller.setPresence(StatusType.intToStatusType(model
                            .getStatus(model.getCurrentProfile().getName())));

                    if (model.getAvatarDirectory(model.getCurrentProfile()
                            .getName()) != null
                            && model.getAvatarDirectory(
                                    model.getCurrentProfile().getName())
                                    .length() > 0) {
                        url =
                                new URL(model.getAvatarDirectory(model
                                        .getCurrentProfile().getName()));

                        PollingThread thread = new PollingThread();

                        thread.start();
                    }

                } catch (ClassNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                signMenu.setEnabled(false);
                signOutMenu.setEnabled(true);
            } catch (BadConnectionException e) {
                // throw warning
                // new JOptionPane (account.getUserID()+ " failed signing in.");
            }
        } else { // disconnect
            signMenu.setEnabled(true);
            signOutMenu.setEnabled(false);
        }
        buddies.listRepopulate();
    }

    // Section
    // Polling methods

    private class PollingThread extends Thread {

        public void run() {
            try {
                sleep(10000); // Delay for 4 seconds
            } catch (InterruptedException e) {
                System.err.println("Threading error");
                e.printStackTrace();
            }

           
            try {
            	
            	ImageIcon avatarPicture = new ImageIcon(url);
            
            	
            	// the file is missing
                if (avatarPicture.getIconHeight() != -1 && avatarPicture.getIconWidth() != -1) {
                	controller.setAvatarPicture(url);
                   
                }
                
            } catch (XMPPException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
    }

    private class AddFriendListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            if (account.isConnected()) {
                AddBuddyFrame addbuddyFrame =
                        new AddBuddyFrame(model, controller, account
                                .getServer());
                addbuddyFrame.addWindowListener(new PopupWindowListener(buddies
                        .getBuddyList(), addbuddyFrame));
            } else {
                String resultMessage =
                        account.getUserID()
                                + " is not connected, please get connected to add a buddy.";
                JOptionPane.showMessageDialog(null, resultMessage,
                        "Information", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    private class SignMenuActionListener implements ActionListener {

        public void actionPerformed(ActionEvent arg0) {
            if (account.isConnected()) {
                controller.disconnect(account);
                connectAccount(false);
            } else {
                connectAccount(true);
            }
        }

    }

    private class RemoveMenuActionListener implements ActionListener {

        public void actionPerformed(ActionEvent arg0) {
            BuddyList.removeAccountJMenu(account);
            controller.disconnect(account);
            model.getCurrentProfile().removeAccount(userAccount);

        }

    }
}
