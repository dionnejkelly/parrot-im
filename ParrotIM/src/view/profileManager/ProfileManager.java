/* ProfileManager.java
 * 
 * Programmed By:
 *     Aaron Siu
 *     Vera Lukman
 *     
 * Change Log:
 *         
 * Known Issues:
 *     none
 *     
 * Copyright (C) 2009  Pirate Captains
 * 
 * License: GNU General Public License version 2.
 * Full license can be found in ParrotIM/LICENSE.txt.
 */


package view.profileManager;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import controller.MainController;

import view.mainwindow.MainWindow;
import view.styles.PopupWindowListener;

import java.sql.*;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

import model.Model;
import model.enumerations.UpdatedType;

/*
 * Known issue:
 * 	You are able to click on the "add" and "+" and pop 2 windows sometimes. Seems like
 * 	thread issue.
 * DEVELOPER NOTES:
 * - OK/CANCEL BUTTON HAS NOT BEEN IMPLEMENTED YET
 * MUST FIX THIS FUNCTIONALITY PRIOR TO REPLACING MANAGEACCOUNTFRAME .
 * 
 * - EDIT BUTTON FOR 'IM ACCOUNTS' WILL NOT BE ADDED FOR ALPHA VERSION, TOO COMPLICATED
 */

/**
 * The ProfileManager is currently responsible for providing the 
 * profile management frame for Parrot IM users.
 * 
 */

public class ProfileManager extends JFrame implements Observer {
    
	// Section
    // I - Protected Data Member

    /**
     * The ProfileManager frame.
    */
	
	protected ProfileManager popup;
	
	// Section
    // II - Non-Static Data Members

    /**
     * The JPanel account main data.
     */
	
	private JPanel accMANPanel;
	
	/**
     * The database.
     */
	
    private Model model;
    
    /**
     * Maintains the Parrot IM XMPP Protocol.
     */
    
    private MainController controller;
    
    /**
     * Main frame.
     */
    
    private MainWindow mainFrame;
    
    /**
     * Profile list model.
     */
    
    private DefaultListModel profileListModel;
    
    /**
     * account list model.
     */
    
    private DefaultListModel acctListModel;
    
    /**
     * Profile list.
     */
    
    private JList profileList;
    
    /**
     * Account list.
     */
    
    private JList accList;
    
    /**
     * Account scroll pane.
     */
    
    private JScrollPane acctListScroller;

    // Section
    // III - Constructor

    /**
     * ProfileManager() connects you to the Profile Manager Frame. 
     * Every time you want to run a ProfileManager window you have to
     * "ProfileManager profileManager = new ProfileManager(Model model, MainController controller, MainWindow frame);"
     * 
     * @param model
     * @param controller
     * @param frame
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    
    public ProfileManager(Model model, MainController controller, MainWindow frame)
            throws ClassNotFoundException, SQLException {
//        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        mainFrame = frame;
//        frame.setFocusable(false);
        this.addWindowListener(new PopupWindowListener(mainFrame, this));

        this.model = model;
        this.model.addObserver(this);
        this.controller = controller;
        // mainFrame = frame;
        popup = this;

        setTitle("Profile Manager");
        setLocation(100, 100);
        setPreferredSize(new Dimension(450, 350));
        setResizable(false);
        setIconImage(new ImageIcon("imagesimages/mainwindow/logo.png")
                .getImage());

        // set main panel
        accMANPanel = new JPanel();
        accMANPanel.setLayout(new BorderLayout());
        // accMANPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20,
        // 30));
        // manage account panel
        // TODO: split them into different panels
        leftPanelMAN();
        rightPanelMAN();

        getContentPane().add(accMANPanel);
        pack();
        setVisible(true);
    }
    
    /**
     * leftPanelMAN() connects you with the Left Panel Profile Manager handler
     * that allows users to create a new profile.
     * 
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    

    private void leftPanelMAN() throws ClassNotFoundException, SQLException {
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout());

        // saved account list
        profileListModel = new DefaultListModel();
        Vector<String> profileListArray = model.getProfileList();
        for (int i = 0; i < model.getProfileList().size(); i++) {
            profileListModel.addElement(profileListArray.elementAt(i));
        }
        // profileList = new JList(model.getAccountList());
        profileList = new JList(profileListModel);
        profileList
                .setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);

        // TODO: ADD NEW PROFILE LIST TO MODEL

        JScrollPane listScroller = new JScrollPane(profileList);
        listScroller.setPreferredSize(new Dimension(120, 255));
        listScroller
                .setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        // add-remove button panel
        JPanel addremovePanel = new JPanel();

        // add and remove Profiles button
        JButton addButton = new JButton("+");
        addButton.setPreferredSize(new Dimension(50, 25));
        JButton removeButton = new JButton("-");
        removeButton.setPreferredSize(new Dimension(50, 25));
        // Removing Padding from buttons so text can be displayed properly
        Insets buttonInset = new Insets(0, 0, 0, 0);
        addButton.setMargin(buttonInset);
        removeButton.setMargin(buttonInset);

        // +/- BUTTON FUNCTIONALITY
        addButton.addActionListener(new AddProfileListener());
        removeButton.addActionListener(new RemoveProfileListener());

        // pack the whole thing
        addremovePanel.add(addButton);
        addremovePanel.add(removeButton);

        // add to leftpanel
        leftPanel.add(listScroller, BorderLayout.NORTH);
        leftPanel.add(addremovePanel, BorderLayout.SOUTH);
        TitledBorder profTitle;
        profTitle = BorderFactory.createTitledBorder(BorderFactory
                .createEmptyBorder(), "Profiles");
        profTitle.setTitleJustification(TitledBorder.CENTER);
        leftPanel.setBorder(profTitle);

        // add to account manager pop up main panel
        accMANPanel.add(leftPanel, BorderLayout.WEST);
    }
    
    /**
     * rightPanelMAN() connects you with the Right Panel Profile Manager handler
     * that allows users to add their contacts.
     * 
     * @throws ClassNotFoundException
     * @throws SQLException
     */

    private void rightPanelMAN() throws ClassNotFoundException, SQLException {
        // setting right panel
        final JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout());

        /* TOP PART */
        // List of accounts on the profile
        JPanel topRight = new JPanel();
        topRight.setLayout(new BorderLayout());

        acctListModel = new DefaultListModel();
        accList = new JList(acctListModel);
        accList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);

        ListSelectionListener profileListSelection = new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                boolean adjust = listSelectionEvent.getValueIsAdjusting();
                if (!adjust) {
                    JList list = (JList) listSelectionEvent.getSource();
                    int selections[] = list.getSelectedIndices();
                    Object selectionValues[] = list.getSelectedValues();
                    String selectionString[] = new String[selectionValues.length];
                    for (int i = 0; i < selectionValues.length; i++) {
                        selectionString[i] = selectionValues[i].toString();
                    }

                    for (int i = 0, n = selections.length; i < n; i++) {
                        if (i == 0) {
                            acctListModel.clear();
                            Vector<String> acctListArray = model
                                    .getProfilesUserList(selectionString[i]);
                            for (int j = 0; j < model.getProfilesUserList(
                                    selectionString[i]).size(); j++) {
                                acctListModel.addElement(acctListArray
                                        .elementAt(j));
                            }
                        }
                    }
                }
            }
        };
        profileList.addListSelectionListener(profileListSelection);

        acctListScroller = new JScrollPane(accList);
        acctListScroller.setPreferredSize(new Dimension(300, 210));
        acctListScroller
                .setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        /* CENTRE PART : Add/Remove Buttons */

        JPanel addRemoveAcctPanel = new JPanel();
        addRemoveAcctPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        // addRemoveAcctPanel.setBorder(BorderFactory.createEmptyBorder(5, 12,
        // 0, 12));

        Insets buttonInset = new Insets(0, 0, 0, 0);
        JButton newAcctButton = new JButton("Add");
        newAcctButton.setPreferredSize(new Dimension(80, 25));
        newAcctButton.setMargin(buttonInset);
        JButton remAcctButton = new JButton("Delete");
        remAcctButton.setPreferredSize(new Dimension(80, 25));
        remAcctButton.setMargin(buttonInset);

        // Add button listener
        newAcctButton.addActionListener(new AddAccountListener());
        remAcctButton.addActionListener(new RemoveAccountListener());

        addRemoveAcctPanel.add(newAcctButton);
        addRemoveAcctPanel.add(remAcctButton);

        // Piece together Right Side Panel and Add Border
        topRight.add(acctListScroller, BorderLayout.NORTH);
        topRight.add(addRemoveAcctPanel, BorderLayout.CENTER);
        TitledBorder acctTitle;
        acctTitle = BorderFactory.createTitledBorder(BorderFactory
                .createEmptyBorder(), "IM Accounts");
        acctTitle.setTitleJustification(TitledBorder.RIGHT);
        topRight.setBorder(acctTitle);

        /* BOTTOM PART : OK and Cancel Button */
        // set ok-cancel button
        JPanel buttonsPanel = new JPanel();
        buttonsPanel
                .setBorder(BorderFactory.createEmptyBorder(10, 220, 10, 10));
        GridLayout buttonsLayout = new GridLayout(1, 2);
        buttonsLayout.setHgap(5);
        buttonsPanel.setLayout(buttonsLayout);

        // CLOSE PROFILE MANAGER Button
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                mainFrame.setEnabled(true);
                popup.removeAll();
                popup.dispose();

                mainFrame.setAlwaysOnTop(true);
                mainFrame.setAlwaysOnTop(false);
                // popup.setVisible(false);
                // popup.dispose();
            }
        });
        buttonsPanel.add(closeButton);

        // adding to rightPanel
        rightPanel.add(topRight, BorderLayout.CENTER);
        rightPanel.add(buttonsPanel, BorderLayout.SOUTH);

        // add to account manager pop up main panel
        accMANPanel.add(rightPanel, BorderLayout.EAST);
    }

    /*
     * SECTION: Button Listeners Add profile (+ button) Remove profile(- button)
     * Add account button Remove account button
     */

    /**
     * The AddProfileListener is currently responsible for listening the + button.
     * 
     */
    
    private class AddProfileListener implements ActionListener {
    	
    	/**
         * Listens to user's action.
         * 
         * @param evt
         * 
         */
    	
        public void actionPerformed(ActionEvent evt) {
            NewProfileFrame newProf = new NewProfileFrame(model, controller,
                    popup);
            popup.setEnabled(false);
            newProf.addWindowListener(new PopupWindowListener(popup,newProf));

            return;
        }
    }
    
    /**
     * The RemoveProfileListener is currently responsible for listening the - button.
     * 
     */

    private class RemoveProfileListener implements ActionListener {
    	
    	/**
         * Listens to user's action.
         * 
         * @param evt
         * 
         */
    	
        public void actionPerformed(ActionEvent evt) {
            String name = (String) profileList.getSelectedValue();
            controller.removeProfile(name);

            return;
        }
    }
    
    /**
     * The AddAccountListener is currently responsible for listening the Add button.
     * 
     */

    private class AddAccountListener implements ActionListener {
    	/**
         * Listens to user's action.
         * 
         * @param evt
         * 
         */
    	
        public void actionPerformed(ActionEvent evt) {
            String profile = (String) profileList.getSelectedValue();
            EditAccountFrame newAcct = new EditAccountFrame(model, popup,
                    controller, profile);
            popup.setEnabled(false);
            newAcct.addWindowListener(new PopupWindowListener(popup,newAcct));
            return;
        }
    }

    /**
     * The AddAccountListener is currently responsible for listening the Remove button.
     * 
     */
    
    private class RemoveAccountListener implements ActionListener {
    	/**
         * Listens to user's action.
         * 
         * @param evt
         * 
         */
    	
        public void actionPerformed(ActionEvent evt) {
            String account = (String) accList.getSelectedValue();
            String profile = (String) profileList.getSelectedValue();
            System.out.println("in removeAccountListener: " + account
                    + " for profile: " + profile);
            controller.removeAccount(profile, account);

            return;
        }
    }
    
    /**
     * Responsible for notifying observers.
     * 
     * @param o
     * @param arg
     * 
     */
    

    public void update(Observable o, Object arg) {
        if (arg == UpdatedType.PROFILE) {
            String profile = null;

            // Update profiles on the left-hand side
            profileListModel.removeAllElements();
            for (String s : model.getProfileList()) {
                profileListModel.addElement(s);
            }

            // Update accounts on the right-hand side
            acctListModel.removeAllElements();
            profile = (String) profileList.getSelectedValue();
            for (String s : model.getProfilesUserList(profile)) {
                acctListModel.addElement(s);
            }

            return;
        }
    }
}
