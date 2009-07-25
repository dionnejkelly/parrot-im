/* AddBuddyFrame.java
 * 
 * Programmed By:
 *     Kevin Fahy
 *     Vera Lukman
 *     
 * Change Log:
 *     2009-July-25, KF
 *         Initialized code.
 *     2009-July-25, VL
 *         Reorganized code.
 *         
 *     
 * Copyright (C) 2009  Pirate Captains
 * 
 * License: GNU General Public License version 2.
 * Full license can be found in ParrotIM/LICENSE.txt.
*/

package view.buddylist;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controller.MainController;

import view.options.MusicPlayer;
import view.styles.GPanel;

import model.Model;
import model.dataType.AccountData;
import model.dataType.UserData;
import model.enumerations.ServerType;
import model.enumerations.UpdatedType;

public class AddBuddyPanel extends GPanel implements Observer {
	private JFrame popup;
    private JTextField buddyField;
    private JComboBox selectableServer;
    private JPanel setupPanel;
    private JButton okButton;
    private MainController controller;
    private Model model;

    public AddBuddyPanel(Model model, MainController controller, JFrame popup) {
    	this.popup = popup;
        this.model = model;
        this.controller = controller;
        this.model.getCurrentProfile().addObserver(this);
        this.setGradientColors(model.primaryColor, model.secondaryColor);
        this.setBorder(BorderFactory.createEmptyBorder(20, 15, 20, 15));
        
        // set main panel
        setLayout(new BorderLayout());
        
        // manage account panel
        rightPanelMAN();
        
        model.addObserver(this);

    }
    
    private void rightPanelMAN() {
    	/*TOP PART*/
        // select server
        selectableServer = new JComboBox(model.getCurrentProfile().getAllAccountsServer());
        selectableServer.setOpaque(false);
        selectableServer.setBorder(BorderFactory.createEmptyBorder(0,0,10,0));

        // buddy's address
        buddyField = new JTextField();
        buddyField.addKeyListener(new buddyFieldKeyListener());
        JPanel buddyInfoPanel = new JPanel ();
        buddyInfoPanel.setLayout(new BoxLayout(buddyInfoPanel, BoxLayout.Y_AXIS));
        buddyInfoPanel.setOpaque(false);
        buddyInfoPanel.add(new JLabel("         Select server to add:"));
        buddyInfoPanel.add(selectableServer);
        buddyInfoPanel.add(new JLabel("Enter your buddy's email address:"));
        buddyInfoPanel.add(buddyField);

        
        /* BOTTOM PART: OK + CANCEL BUTTONs */
        okButton = new JButton("OK", new ImageIcon(this.getClass()
                .getResource("/images/buddylist/button_ok.png")));
        okButton.setEnabled(false);
        okButton.addActionListener(new okButtonActionListener());
        JButton cancelButton = new JButton("Cancel", new ImageIcon(this.getClass()
                .getResource("/images/mainwindow/cancel.png")));
        cancelButton.addActionListener(new cancelButtonActionListener());
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setOpaque(false);
        buttonsPanel.add(okButton);
        buttonsPanel.add(cancelButton);
        
        // account setup Panel
        this.setLayout(new BorderLayout());
        this.add(buddyInfoPanel, BorderLayout.NORTH);
        this.add(buttonsPanel, BorderLayout.SOUTH);

    }
    
    public void update(Observable o, Object arg) {
		if(arg == UpdatedType.COLOR){
			
			setupPanel.setBackground(model.tertiaryColor);
	        
			setGradientColors(model.primaryColor, model.secondaryColor);
			
			this.updateUI();
		}
		
        return;
	}

    private class buddyFieldKeyListener implements KeyListener{

		public void keyPressed(KeyEvent arg0) {}

		public void keyReleased(KeyEvent arg0) {
			if (buddyField.getText().length() > 0)
				okButton.setEnabled(true);
			else
				okButton.setEnabled(false);
		}

		public void keyTyped(KeyEvent arg0) {}
    	
    }
    private class okButtonActionListener implements ActionListener{

		public void actionPerformed(ActionEvent arg0) {
			ServerType selectedServer = (ServerType)selectableServer.getSelectedItem();
			AccountData account = model.getCurrentProfile().getAccountFromServer(selectedServer);
			String buddyID = buddyField.getText().toLowerCase();
			if (buddyID.equals(account.getUserID())) {
				JOptionPane.showMessageDialog(null, 
	            		   "Argh, you cannot add yourself! Please provide a different email address.");
			} else if (userExist (buddyID, account.getFriends())){
				JOptionPane.showMessageDialog(null, buddyField.getText()+" is an existing contact.\n"
                      + "Please provide a non-existing friend's email address.");
			} else {
				 controller.addFriend(account, buddyField.getText());
				 new MusicPlayer("/audio/buddy/addFriend.wav", model);
				 popup.dispose();
			}
		}
		
		/**
         * check if buddyID exists
         * 
         * @param buddyID
         * @return boolean
         */
        private boolean userExist(String buddyID, ArrayList<UserData> buddies) {
        	
            for (UserData buddy : buddies) {
                if (buddy.getUserID().equals(buddyID)) {
                    return true;
                }
            }

            return false;
        }
    }
    
    private class cancelButtonActionListener implements ActionListener{

		public void actionPerformed(ActionEvent arg0) {
			popup.dispose();
		}
    	
    }
}
