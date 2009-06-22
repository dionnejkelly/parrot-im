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

import controller.services.Xmpp;

import view.profileManager.profileManager;
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
                    profileManager popup = new profileManager(model, core,
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
        ServerType serverType = ServerType.GOOGLE_TALK; // temporary
        String username = (String) account_select.getSelectedItem();
        String password = model.getPassword(username);

        try {
            // Login with server and set model info
            core.login(serverType, username, password);

            // Handle the GUI changes
            new buddylist(core, model);
            mainFrame.dispose();
        } catch (XMPPException e1) {
            header.loadMain();
            System.out.println("sign in failed!");
        }
    }
}
