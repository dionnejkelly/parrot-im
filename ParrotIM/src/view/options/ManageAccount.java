/* FeaturesPanel.java
 * 
 * Programmed By:
 *     Chenny Huang
 *     Vera Lukman
 *     
 * Change Log:
 *     2009-June-20, CH
 *         Initial write.
 *     2009-June-29, VL
 *         Reorganized code.
 *         
 * Known Issues:
 *     adding/removing might not work
 *     
 * Copyright (C) 2009  Pirate Captains
 * 
 * License: GNU General Public License version 2.
 * Full license can be found in ParrotIM/LICENSE.txt.
 */

package view.options;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import controller.MainController;

import model.Model;
import model.dataType.AccountData;
import model.dataType.ProfileData;
import model.enumerations.ServerType;
import model.enumerations.UpdatedType;

public class ManageAccount extends JPanel implements Observer {
    private ProfileData profile;
    private JList accList;
    private JTextField UNField;
    private JPasswordField pwdField;
    private JComboBox server;
    private JButton addButton;

    protected JPanel serverPanel;
    protected JTextField jabberServer;
    protected JButton removeButton;

    public ManageAccount(Model model, MainController controller,
            ProfileData profile) {

        this.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        this.profile = profile;
        this.profile.addObserver(this);

        // set main panel
        setLayout(new BorderLayout());
        // manage account panel
        leftPanelMAN();
        rightPanelMAN();

    }

    private void leftPanelMAN() {
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout());

        // saved account list
        accList = new JList(new Vector<AccountData>(profile.getAccountData())){
        	public String getToolTipText(MouseEvent e) {
        		int index = locationToIndex(e.getPoint());
        		if (-1 < index) {
        			String item = "<html>Account Type: " + profile.getAccountData().get(index).getServer().toString()
        			+ "<br>UserID: " + profile.getAccountData().get(index).getUserID();
        			return item;
        		} else {
        			return null;
        		}
        	}
        };
        accList.addListSelectionListener(new accListSelectionListener());
        accList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);

        accList.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent e) {
                // ((JList) e.getSource()).clearSelection();
            }
        });

        JScrollPane listScroller = new JScrollPane(accList);
        
        
        listScroller.setPreferredSize(new Dimension(180, 185));
        listScroller
                .setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        // add button
        addButton = new JButton("Add", new ImageIcon(this.getClass().getResource(
        "/images/mainwindow/add.png")));
        addButton.setEnabled(false);
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                try {
                    addAccount_actionPerform(evt);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        // remove button
        removeButton = new JButton("Remove",  new ImageIcon(this.getClass().getResource(
        "/images/mainwindow/remove.png")));
        removeButton.setEnabled(false);
        removeButton.addActionListener(new removeActionListener());

        // add-remove button panel
        JPanel addremovePanel = new JPanel();
        addremovePanel.add(addButton);
        addremovePanel.add(removeButton);

        // add to leftpanel
        leftPanel.add(listScroller, BorderLayout.NORTH);
        leftPanel.add(addremovePanel, BorderLayout.SOUTH);

        // add to account manager pop up main panel
        this.add(leftPanel, BorderLayout.WEST);
    }

    private void rightPanelMAN() {

        ProfileKeyListener keyListener = new ProfileKeyListener();

        // setting right panel
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout());
        // select server
        server = new JComboBox(ServerType.getServerList());
        server.setPreferredSize(new Dimension(180, 30));
        server.addItemListener(new serverListener());
        // server name for jabber
        // textfield
        jabberServer = new JTextField();
        jabberServer.addKeyListener(keyListener);
        jabberServer.setPreferredSize(new Dimension(180, 20));
        jabberServer.setToolTipText("specify jabber server");
        JPanel jabberServerPanel = new JPanel();
        jabberServerPanel.setLayout(new BorderLayout());
        jabberServerPanel.add(jabberServer, BorderLayout.NORTH);
        // label
        JLabel jabberServerLabel = new JLabel("Jabber server:  ");
        jabberServerLabel.setForeground(Color.red.darker());
        JPanel jabberServerLabelPanel = new JPanel();
        jabberServerLabelPanel.setLayout(new BorderLayout());
        jabberServerLabelPanel.add(jabberServerLabel,
                BorderLayout.NORTH);

        serverPanel = new JPanel();
        serverPanel.setLayout(new BorderLayout());
        serverPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 15, 5));
        serverPanel.add(jabberServerLabelPanel, BorderLayout.WEST);
        serverPanel.add(jabberServerPanel, BorderLayout.CENTER);

        // set username
        JPanel usernamePanel = new JPanel();
        UNField = new JTextField();
        UNField.addKeyListener(keyListener);
        UNField.setPreferredSize(new Dimension(160, 20));
        JLabel UNLabel = new JLabel("Username:     ");
        UNLabel.setForeground(Color.red.darker());
        usernamePanel.add(UNLabel);
        usernamePanel.add(UNField);
        usernamePanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        // set password
        JPanel passwordPanel = new JPanel();
        pwdField = new JPasswordField();
        pwdField.addKeyListener(keyListener);
        pwdField.setPreferredSize(new Dimension(160, 20));
        JLabel pwdLabel = new JLabel("Password:      ");
        pwdLabel.setForeground(Color.red.darker());
        passwordPanel.add(pwdLabel);
        passwordPanel.add(pwdField);

        // account setup Panel
        JPanel setupPanel = new JPanel();
        setupPanel.setLayout(new BoxLayout(setupPanel, BoxLayout.Y_AXIS));
        setupPanel.add(server);
        setupPanel.add(serverPanel);
        setupPanel.add(usernamePanel);
        setupPanel.add(passwordPanel);

        // adding to rightPanel
        rightPanel.setPreferredSize(new Dimension(260, 400));
        rightPanel.add(setupPanel, BorderLayout.NORTH);

        // add to account manager pop up main panel
        add(rightPanel, BorderLayout.EAST);
    }

    private void addAccount_actionPerform(ActionEvent evt)
            throws ClassNotFoundException, SQLException {
    	System.out.println("Server Type = " + (ServerType) server.getSelectedItem());
    	
    	if ((ServerType) server.getSelectedItem() == ServerType.GOOGLE_TALK ||
    			(ServerType) server.getSelectedItem() == ServerType.TWITTER) {
    		
    		 if (UNField.getText().length() != 0
    	                && pwdField.getPassword().length != 0) {
    	            // search if it exists or not
    	            // TODO: newACC is supposed to be an Object that includes server,
    	            // username, password
    	            boolean match = false; // someone deleted my code here. or will
    	            // model provide this method?
    	            // (if there the account has already beed added, then do something.)

    	            if (match) {
    	                // if found, then edit the password as manage
    	                // TODO:edit password
    	            } else {
    	                // insert new
    	                profile.addAccount(Model.createAccount(UNField.getText(),
    	                        String.copyValueOf(pwdField.getPassword()),
    	                        (ServerType) server.getSelectedItem()));
    	                UNField.setText("");
    	                pwdField.setText("");
    	                addButton.setEnabled(false);
    	            }
    	        }
    				
    	}
    	
    	else {
    		String resultMessage =
                "We are only supporting XMPP and Twitter for the beta version. Sorry for the inconvenience.";
        JOptionPane.showMessageDialog(null, resultMessage, "Information", JOptionPane.INFORMATION_MESSAGE);
    	}
    	
       
    }

    public void update(Observable arg0, Object o) {
        accList.setListData(new Vector<AccountData>(profile.getAccountData()));
        accList.updateUI();

        return;
    }

    private class serverListener implements ItemListener {

        public void itemStateChanged(ItemEvent e) {
            UNField.setText(null);
            pwdField.setText(null);
            if (server.getSelectedIndex() == 0) {
                serverPanel.setVisible(true);
            } else {
                serverPanel.setVisible(false);
            }
        }
    }

    private class removeActionListener implements ActionListener {
        public void actionPerformed(ActionEvent evt) {
            int selected = accList.getSelectedIndex();
            if (selected >= 0) {
                profile.removeAccount((AccountData) accList.getSelectedValue());
                removeButton.setEnabled(false);
            }
        }
    }

    private class accListSelectionListener implements ListSelectionListener {

        public void valueChanged(ListSelectionEvent e) {

            if (accList.getSelectedIndex() > -1) {
                removeButton.setEnabled(true);
            }
        }
    }

    private class ProfileKeyListener implements KeyListener {

        public void keyPressed(KeyEvent e) {
        }

        public void keyReleased(KeyEvent e) {
            if (UNField.getText().length() > 0
                    && pwdField.getPassword().length > 0) {
                if (server.getSelectedIndex() > 0)
                    addButton.setEnabled(true);
                else {
                    if (jabberServer.getText().length() > 0)
                        addButton.setEnabled(true);
                    else
                        addButton.setEnabled(false);
                }
            } else
                addButton.setEnabled(false);

        }

        public void keyTyped(KeyEvent e) {
            // if (UNField.getText().length() > 0 &&
            // pwdField.getPassword().length > 0){
            // if (server.getSelectedIndex() > 0)
            // addButton.setEnabled(true);
            // else{
            // if ( jabberServer.getText().length() > 0 )
            // addButton.setEnabled(true);
            // else
            // addButton.setEnabled(false);
            // }
            // } else
            // addButton.setEnabled(false);
        }

    }
}
