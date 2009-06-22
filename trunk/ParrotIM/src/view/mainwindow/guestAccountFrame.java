/* guestAccountFrame.java
 * 
 * Programmed By:
 * 	   Vera Lukman
 *     Jihoon Choi
 *     Jordan Fox
 * 	   William Chen
 *     
 * Change Log:
 *     2009-June-6, VL
 *         Initial write.
 *     2009-June-7, JC
 *     	   Integrated with control.
 *     2009-June-8, JF
 *     	   Fixed keyPressed()
 *     2009-June-13, WC/VL
 *         Transferred file over to new project, ParrotIM.
 *         Fixed alignment to left.
 *         
 * Known Issues:
 *     None
 * 
 * Copyright (C) 2009  Pirate Captains
 * 
 * Full license can be found in ParrotIM/LICENSE.txt.
 */

package view.mainwindow;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import model.Model;
import model.dataType.AccountData;
import model.dataType.GoogleTalkUserData;
import model.dataType.ServerType;

import org.jivesoftware.smack.XMPPException;

import controller.services.Xmpp;

import view.buddylist.buddylist;
import view.styles.popupWindowListener;

public class guestAccountFrame extends JFrame {

    private Model model;
    private Xmpp core;
    private mainwindow mainFrame;
    private JFrame popup;
    private signinPanel mainPanel;

    private JTextField UNFieldGuest;
    private JPasswordField PwdFieldGuest;
    private JComboBox server;

    public guestAccountFrame(Model model, Xmpp c, mainwindow frame,
            signinPanel signin) {
        this.setAlwaysOnTop(true);
        popup = this;
        this.model = model;
        core = c;
        mainFrame = frame;
        mainPanel = signin;
        this.addWindowListener(new popupWindowListener(mainFrame, this));

        // set Frame
        setTitle("Guest Account Login");
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(400, 250));
        setLocation(100, 100);
        setResizable(false);
        setIconImage(new ImageIcon("imagesimage/mainwindow/logo.png")
                .getImage());

        // select server
        server = new JComboBox(model.getServerList());
        server.setPreferredSize(new Dimension(200, 20));
        server.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        // username + password
        JPanel accountInfoPanel = new JPanel();
        // set username
        JPanel usernamePanel = new JPanel();
        UNFieldGuest = new JTextField();
        UNFieldGuest.setPreferredSize(new Dimension(200, 20));
        usernamePanel.add(new JLabel("Username:     "));
        usernamePanel.add(UNFieldGuest);
        accountInfoPanel.add(usernamePanel);
        // set password
        PwdFieldGuest = new JPasswordField();
        PwdFieldGuest.setPreferredSize(new Dimension(200, 20));
        accountInfoPanel.add(new JLabel("Password:      "));
        accountInfoPanel.add(PwdFieldGuest);

        // set ok-cancel button
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setPreferredSize(new Dimension(20, 90));
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(15, 40, 15, 40));
        GridLayout buttonsLayout = new GridLayout(1, 2);
        buttonsLayout.setHgap(5);
        buttonsPanel.setLayout(buttonsLayout);
        // OK Button
        JButton okButton = new JButton("OK");
        okButton.setPreferredSize(new Dimension(20, 40));
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                if (UNFieldGuest.getText().length() != 0
                        && PwdFieldGuest.getPassword().length != 0) {
                    setVisible(false);
                    signIn_ActionPerformed(evt);
                }
            }
        });
        buttonsPanel.add(okButton);
        // Cancel Button
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setPreferredSize(new Dimension(20, 40));
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                mainFrame.setEnabled(true);
                popup.removeAll();
                popup.dispose();
                mainFrame.setAlwaysOnTop(true);
                mainFrame.setAlwaysOnTop(false);
            }
        });
        buttonsPanel.add(cancelButton);

        // set main panel
        JPanel GALPanel = new JPanel();
        GALPanel.setLayout(new GridLayout(3, 1));
        GALPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        GALPanel.add(server); // server dropdown menu
        GALPanel.add(accountInfoPanel); // username+password
        GALPanel.add(buttonsPanel); // ok+cancel buttons

        getContentPane().add(GALPanel);
        pack();
        setVisible(true);

    }

    private void signIn_ActionPerformed(ActionEvent e) {
        ServerType serverType = (ServerType) server.getSelectedItem();
        String username = UNFieldGuest.getText();
        String password = password(PwdFieldGuest.getPassword());

        try {
            core.login(serverType, username, password);

            new buddylist(core, model);// pops buddylist window
            mainFrame.dispose(); // TODO: consider if the sign in fails
        } catch (XMPPException e1) {
            // TODO: throw a warning if password is incorrect or account does
            // not exist - core, please provide this
            // e1.printStackTrace();
            mainPanel.header.loadMain();
            // mainPanel.accPanel.setVisible(true);
            // mainPanel.misc.setVisible(true);
            mainFrame.setEnabled(true);
            System.out.println("sign in failed!");
        }
    }

    private String password(char[] pass) {
        String str = new String();
        str = "";

        for (int i = 0; i < pass.length; i++) {
            str += pass[i];
        }
        return str;

    }
}