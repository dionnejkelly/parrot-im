package view.mainwindow;

//TODO: set so that when the popup window (manage account and guest account popups) 
//is closed using the close button, mainFrame is set to enable.
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import model.Model;
import model.dataType.AccountData;
import model.dataType.GoogleTalkUserData;
import model.dataType.ServerType;

import org.jivesoftware.smack.XMPPException;

import controller.MainController;
import controller.services.Xmpp;

import view.styles.linkLabel;

import view.buddylist.buddylist;

public class signinPanel extends JPanel {
    protected Xmpp core;
    private mainwindow mainFrame;
    private Model model;
    private JComboBox account_select;

    // part of the whole panel
    protected signinPanel signin;
    protected headerPanel header;
    protected JPanel accPanel;
    protected miscPanel misc;

    // Account Options part (in Sign In Panel)
    private linkLabel manageAccount;
    private linkLabel guestAccount;

    public signinPanel(mainwindow frame, Xmpp chatClient, Model model)
            throws ClassNotFoundException, SQLException {
        mainFrame = frame;
        core = chatClient;// CORE
        this.model = model;
        signin = this;

        setLayout(new BorderLayout());
        manageAccountPanel();

        header = new headerPanel();
        misc = new miscPanel();
        add(header, BorderLayout.NORTH);
        add(misc, BorderLayout.SOUTH);
    }

    private void manageAccountPanel() throws ClassNotFoundException,
            SQLException {

        accPanel = new JPanel();
        FlowLayout accLayout = new FlowLayout();
        accLayout.setAlignment(FlowLayout.CENTER);
        accPanel.setLayout(accLayout);
        accPanel.setBorder(BorderFactory.createEmptyBorder(10, 50, 10, 50));

        // list of accounts
        account_select = new JComboBox(model.getAccountList());
        account_select.setAlignmentY(Component.CENTER_ALIGNMENT);
        // connect button
        JPanel connectPanel = new JPanel();
        JButton connectButton = new JButton("Sign In");
        connectButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        connectPanel.add(connectButton);
        connectButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                try {
                    signIn_ActionPerformed(evt);
                } catch (ClassNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });

        // accOPTPanel (part of accPanel)
        JPanel accOptPanel = new JPanel();
        accOptPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        GridLayout accOptLayout = new GridLayout(2, 1);
        accOptLayout.setVgap(15);
        accOptPanel.setLayout(accOptLayout);

        // manage account
        manageAccount = new linkLabel("Add/Manage Account");
        manageAccount.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                mainFrame.setEnabled(false);
                try {
                    manageAccountFrame popup = new manageAccountFrame(model,
                            mainFrame);
                } catch (ClassNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });

        // guest account
        guestAccount = new linkLabel("Connect Guest Account");
        guestAccount.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                mainFrame.setEnabled(false);
                new guestAccountFrame(model, core, mainFrame, signin);
            }
        });

        // add components to accOptPanel
        accOptPanel.add(manageAccount);
        accOptPanel.add(guestAccount);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(account_select, BorderLayout.NORTH);
        panel.add(accOptPanel, BorderLayout.CENTER);
        panel.add(connectPanel, BorderLayout.SOUTH);

        // add components to accPanel
        accPanel.add(panel, BorderLayout.NORTH);

        add(accPanel, BorderLayout.CENTER);
    }

    private void signIn_ActionPerformed(ActionEvent e)
            throws ClassNotFoundException, SQLException {

        // loading window
        /*
         * header.setAnimation(); accPanel.setVisible(false);
         * misc.setVisible(false);
         * 
         * System.out.println("signinPanel : after setting animation");
         */

        /* THIS IS FOR CHAT CLIENT : modified ChatClient c */

        /*
         * TEMPORARY CODE to create currentProfile. Need to consider multiple
         * accounts later.
         */
        AccountData account = null;
        ArrayList<String> friendList = null;
        GoogleTalkUserData user = null;

        try {
            String username = (String) account_select.getSelectedItem();
            // core.login(username, model.getPassword(username), 4); //change 4
            // to the actual server from model later on

            /* Log into the server */
            account = new AccountData(ServerType.GOOGLE_TALK, username, model
                    .getPassword(username));
            model.createCurrentProfile(account, "tempName");
            // System.out.println("signinPanel : before login");
            core.login(account);
            // System.out.println("signinPanel : after login");

            /* Populate the buddy list */
            if (account.getServer() == ServerType.GOOGLE_TALK) {

                /* Set up own user data */

                // user = new GoogleTalkUserData(account.getAccountName());
                // user.setOnline(true);
                // account.setOwnUserData(user);
                /* Set up friends' user data */
                friendList = core.getBuddyList();
                for (String s : friendList) {
                    user = new GoogleTalkUserData(s); // Add account name
                    account.addFriend(user);
                }
            }

            new buddylist(core, model);// pops buddylist window
            // mainFrame.setVisible(true);
            mainFrame.dispose(); // TODO: consider if the sign in fails

        } catch (XMPPException e1) {
            // TODO: throw a warning pop up

            header.loadMain();
            // accPanel.setVisible(true);
            // misc.setVisible(true);
            // mainFrame.setEnabled(true);
            System.out.println("sign in failed!");
        }
    }
}
