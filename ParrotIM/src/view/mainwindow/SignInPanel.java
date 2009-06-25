/* SignInPanel.java
 * 
 * Programmed By:
 *     Vera Lukman
 *     Kevin Fahy
 *     Ahmad Sidiqi
 *     William Chen
 *     
 * Change Log:
 *     2009-June-4, VL
 *         Initial write to increase modularity.
 *     2009-June-5, KF
 *         Integrated with model.
 *     2009-June-6, KF
 *         Integrated core with model.
 *     2009-June-6, VL
 *         Changed the type of Label to LinkLabel.
 *         The label will be underlined when hovered.
 *         Moved ManageAccount into a ManageAccountFrame.java, and 
 *         GuestAccount into GuestAccountFrame.java.
 *     2009-June-8, AS
 *         Integrated with database.
 *     2009-June-9, KF
 *         Edited the sign-in page to be consistent with the guest sign in 
 *         login. This sign-in needs to account for multiple accounts, 
 *         not implemented yet.
 *     2009-June-10, VL
 *         Changed some layout so that the account options don't move
 *         when the window is resized
 *     2009-June-13, WC
 *         Transferred file over to new project, ParrotIM.
 *     2009-June-15, KF
 *         Started to implement a MainController class to handle all protocol.
 *     2009-June-19, KF
 *         Added Jabber functionality.
 *     2009-June-22, KF
 *         Relayout database.
 *     2009-June-23, KF
 *         Naming convention updates. Changed all class names.
 *     2009-June-24, VL
 *         Fixed multiple add profile/account windows. Not fully fixed yet.
 *         
 * Known Issues:
 *     None
 * 
 * Copyright (C) 2009  Pirate Captains
 * 
 * Full license can be found in ParrotIM/LICENSE.txt.
 */

package view.mainwindow;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import model.Model;
import model.dataType.AccountData;
import model.dataType.GoogleTalkUserData;
import model.enumerations.ServerType;
import model.enumerations.UpdatedType;

import org.jivesoftware.smack.XMPPException;

import controller.MainController;

import view.profileManager.ProfileManager;
import view.styles.LinkLabel;

import view.buddylist.BuddyList;

public class SignInPanel extends JPanel implements Observer {
    protected MainController core;
    private MainWindow mainFrame;
    private Model model;
    private JComboBox account_select;

    // part of the whole panel
    protected SignInPanel signin;
    protected HeaderPanel header;
    protected JPanel accPanel;
    protected MiscPanel misc;

    // Account Options part (in Sign In Panel)
    private LinkLabel manageAccount;
    private LinkLabel guestAccount;

    public SignInPanel(MainWindow frame, MainController chatClient, Model model)
            throws ClassNotFoundException, SQLException {
        mainFrame = frame;
        core = chatClient;// CORE
        this.model = model;
        this.model.addObserver(this);
        signin = this;

        setLayout(new BorderLayout());
        manageAccountPanel();

        header = new HeaderPanel();
        misc = new MiscPanel();
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
        account_select = new JComboBox(model.getProfileList());
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
        manageAccount = new LinkLabel("Add/Manage Account");
        manageAccount.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                try {
                    new ProfileManager(model, core, mainFrame);
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
        guestAccount = new LinkLabel("Connect Guest Account");
        guestAccount.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                new GuestAccountFrame(model, core, mainFrame, signin);
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
            //core.login(serverType, username, password);
            core.loginProfile((String) account_select.getSelectedItem());

            // Handle the GUI changes
            new BuddyList(core, model);
            mainFrame.dispose();
        } catch (XMPPException e1) {
            header.loadMain();
            System.out.println("sign in failed!");
        }
    }
    
    public void update(Observable o, Object arg) {
        if (arg == UpdatedType.PROFILE) {
            this.account_select.removeAllItems();
            for (String s : model.getProfileList()) {
                this.account_select.addItem(s);
            }
            
            //= new JComboBox(model.getProfileList());
        }
        
        return;
    }
}
