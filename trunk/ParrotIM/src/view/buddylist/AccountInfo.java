/* accInfo.java
 * 
 * Programmed By:
 *     Aaron Siu (ANS)
 *     Jordan Fox
 *     Jihoon Choi
 *     Vera Lukman
 *     William Chen
 *     Kevin Fahy
 *     
 * Change Log:
 *     2009-June-1, ANS
 *         Initial write.
 *     2009-June-2, JF
 *         Set the background, added avatar, status message, display name.
 *     2009-June-4, JF
 *         Integrated with control.
 *     2009-June-6, JC
 *         Integrated status message with control. 
 *         Now able to set status message on the actual server.
 *     2009-June-7, VL
 *         Modified status message look and feel.
 *         Now users can edit the status message and hit enter to change it.
 *     2009-June-13, VL
 *         Fixed bug on pmLabel.
 *     2009-June-13, WC
 *         Transferred file over to new project, ParrotIM.
 *     2009-June-17, VL
 *         Added presence JCombobox.
 *     2009-June-17, JC
 *         Integrated presence with control.
 *         Now users are able to set their status mode.
 *     2009-June-19, KF
 *         Updated name to profile name.
 *     2009-June-22, JF
 *         Changed avatar to parrot-IM logo.
 *     2009-June-22, VL
 *         Now users click anywhere outside status message box to change it.
 *     2009-June-23, VL
 *         Now users can clicked on the display picture to modify it.
 *         
 * Known Issues:
 *     None
 * 
 * Copyright (C) 2009  Pirate Captains
 * 
 * Full license can be found in ParrotIM/LICENSE.txt.
 */

package view.buddylist;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import model.Model;

import controller.MainController;

import view.styles.AvatarLabel;
import view.styles.StatusCombo;
import view.styles.PmLabel;

/**
 * accountInfo display user name, avatar picture, account information for Parrot IM users.
 */
public class AccountInfo extends JPanel
{
	/* TODO AccInfo has:
	 * WEST -- Avatar (and avatar settings)
	 * Center -- New embedded Panel
	 * 					Top: User Display name
	 * 					Bottom: Status, and status messages (if applicable)			
	 */
   
	// Selection
    // I - non-static member
	/**
	 * avatarDisplay is for displaying user picture.
	 */
	public AvatarLabel avatarDisplay;
	/**
	 * displayName displays user's name.
	 */
	JLabel displayName;
	/**
	 * variable to handles all inputs from user interaction
	 */
	MainController chatClient;
	
	/**
	 * user status message
	 */
	public PmLabel statusMessage;
	/**
	 * text area
	 */
	protected JTextArea textArea;
	/**
	 * text
	 */
	protected String text;
	
	public StatusCombo presence;
	private JPanel presencePanel;
	
	/**
	 * variable model for extracting buddy list, each buddy's information and , conversation 
	 */
	private Model model;
	//SELECTION
	//II-Constructors
	/**
	 * Account information area, display user's information and avatar picture.
	 * @param c
	 * @param model
	 */
	public AccountInfo(MainController c, Model model) throws ClassNotFoundException 
	{
	    this.model = model;
	    this.chatClient = c;
	    setBackground(Color.DARK_GRAY);
		setBorder(BorderFactory.createEmptyBorder(10,10,5,10));
		setLayout(new BorderLayout());
		
		//user diplay picture
		try {
			avatarDisplay = new AvatarLabel(chatClient,
					model.getAvatarDirectory(model.getCurrentProfile().getName()), 65);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		//name, status message (personal message) and status
		JPanel textInfo = new JPanel();
		//GridLayout infoLayout = new GridLayout (2,1);
		//infoLayout.setVgap(2);
		textInfo.setBorder(BorderFactory.createEmptyBorder(0,0,4,0));
		textInfo.setLayout(new BorderLayout());
		textInfo.setBackground(Color.DARK_GRAY);
			
		
		JLabel name = new JLabel(model.getCurrentProfile().getName());
		name.setForeground(Color.WHITE);
		//name.setSize(50, 10);
		
		// Allowing users to change their status.
		statusMessage = new PmLabel(c);
		statusMessage.setForeground(Color.CYAN);
//		statusMessage.setText(model.getCurrentProfile().get)
		textInfo.add(name,BorderLayout.NORTH);
		textInfo.add(statusMessage,BorderLayout.CENTER);
		// new Listener
		
		//combobox to change presence
		JPanel info = new JPanel ();
		info.setBackground(Color.DARK_GRAY);
		info.setLayout(new BorderLayout ());
		info.setBorder(BorderFactory.createEmptyBorder(0,15,0,0));
		presence = new StatusCombo(chatClient);
		presencePanel = new JPanel();
		presencePanel.setBackground(Color.DARK_GRAY);
		presencePanel.add(presence);
		
		info.add(textInfo, BorderLayout.NORTH);
		info.add(presencePanel, BorderLayout.WEST);
		
		add(avatarDisplay, BorderLayout.WEST);
		add(info, BorderLayout.CENTER);
		
		this.addMouseListener(new statusMouseListener());
	}
   
	// Section
    // III - Accessors and Mutators
	void IMpanelSelected(){
		presencePanel.setVisible(true);
		statusMessage.setPreferredSize(new Dimension(70,20));
	}
	
	void TwitterpanelSelected(){
		presencePanel.setVisible(false);
		statusMessage.setPreferredSize(new Dimension(70,80));
	}
	
	
	/**
	 * mouse listener for mouse to do actions
	 *
	 */
	private class statusMouseListener implements MouseListener{

		/* 
		 * mouse click action
		 */
		public void mouseClicked(MouseEvent e) {
			if (statusMessage.isEditable()){
				statusMessage.changePM(false);
			}
		}

		/* 
		 * mouse entered action
		 * mouse exited action
		 * mouse pressed action
		 * mouse released action
		 * 
		 */
		public void mouseEntered(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}
		public void mousePressed(MouseEvent e) {}
		public void mouseReleased(MouseEvent e) {}
		
	}
}
