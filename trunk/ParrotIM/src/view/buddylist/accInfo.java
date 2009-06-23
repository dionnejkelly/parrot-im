/* chatLogPanel.java
 * 
 * Programmed By:
 * 	   Aaron Siu (ANS)
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
 *	   2009-June-7, VL
 *         Modified status message look and feel.
 *         Now users can edit the status message and hit enter to change it.
 *	   2009-June-13, VL
 *         Fixed bug on pmLabel.
 *	   2009-June-13, WC
 *         Transferred file over to new project, ParrotIM.
 *	   2009-June-17, VL
 *         Added presence JCombobox.
 *	   2009-June-17, JC
 *         Integrated presence with control.
 *         Now users are able to set their status mode.
 *     2009-June-19, KF
 *         Updated name to profile name.
 *     2009-June-22, JF
 *         Changed avatar to parrot-IM logo.
 *     2009-June-22, VL
 *         Now users click anywhere outside status message box to change it.
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
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import model.Model;

import controller.services.Xmpp;

import view.styles.statusCombo;
import view.styles.pmLabel;


public class accInfo extends JPanel
{
	/* TODO AccInfo has:
	 * WEST -- Avatar (and avatar settings)
	 * Center -- New embedded Panel
	 * 					Top: User Display name
	 * 					Bottom: Status, and status messages (if applicable)			
	 */
	ImageIcon avatar;
	JLabel avatarDisplay, displayName;
	Xmpp chatClient;
	
	protected pmLabel statusMessage;
	protected JTextArea textArea;
	protected String text;
	
	private Model model;
	
	public accInfo(Xmpp c, Model model) 
	{
	    this.model = model;
	    setBackground(Color.DARK_GRAY);
		setPreferredSize(new Dimension(300,100));
		setBorder(BorderFactory.createEmptyBorder(5,15,5,5));
		setLayout(new BorderLayout());
		
		//avatar
		avatar = new ImageIcon("images/buddylist/logoBox.png");
		avatarDisplay = new JLabel();
		avatarDisplay.setIcon(avatar);
		
		//name, status message (personal message) and status
		JPanel textInfo = new JPanel();
		GridLayout infoLayout = new GridLayout (2,1);
		infoLayout.setVgap(2);
		textInfo.setBorder(BorderFactory.createEmptyBorder(0,0,4,0));
		textInfo.setLayout(infoLayout);
		textInfo.setBackground(Color.DARK_GRAY);
			
		chatClient = c;
		JLabel name = new JLabel(model.getCurrentProfile().getProfileName());
		name.setForeground(Color.WHITE);
		
		// Allowing users to change their status.
		statusMessage = new pmLabel(c);
		statusMessage.setForeground(Color.black);
		textInfo.add(name);
		textInfo.add(statusMessage);
		// new Listener
		
		//combobox to change presence
		JPanel info = new JPanel ();
		info.setBackground(Color.DARK_GRAY);
		info.setLayout(new BorderLayout ());
		info.setBorder(BorderFactory.createEmptyBorder(13,15,13,5));
		statusCombo presence = new statusCombo(chatClient);
		
		info.add(textInfo, BorderLayout.NORTH);
		info.add(presence, BorderLayout.WEST);
		
		
		add(avatarDisplay, BorderLayout.WEST);
		add(info, BorderLayout.CENTER);
		
		this.addMouseListener(new statusMouseListener());
	}
   
	private class statusMouseListener implements MouseListener{

		public void mouseClicked(MouseEvent e) {
			if (statusMessage.isEditable()){
				statusMessage.changePM(false);
			}
		}

		public void mouseEntered(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}
		public void mousePressed(MouseEvent e) {}
		public void mouseReleased(MouseEvent e) {}
		
	}
}
