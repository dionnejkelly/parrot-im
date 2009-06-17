package view.buddylist;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

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
	JLabel status;
	
	protected pmLabel statusMessage;
	protected JTextArea textArea;
	protected String text;
	
	public accInfo(Xmpp c) 
	{
		setBackground(Color.DARK_GRAY);
		setPreferredSize(new Dimension(300,100));
		setBorder(BorderFactory.createEmptyBorder(5,15,5,5));
		setLayout(new BorderLayout());
		
		//avatar
		avatar = new ImageIcon("images/mainwindow/logo.png");
		avatarDisplay = new JLabel();
		avatarDisplay.setIcon(avatar);
		
		//name, status message (personal message) and status
		JPanel textInfo = new JPanel();
		GridLayout infoLayout = new GridLayout (2,1);
		infoLayout.setVgap(2);
		textInfo.setBorder(BorderFactory.createEmptyBorder(0,0,4,0));
		textInfo.setLayout(infoLayout);
		textInfo.setBackground(Color.DARK_GRAY);
		
		JLabel name = new JLabel(c.getUserName());
		name.setForeground(Color.WHITE);
		
		// Allowing users to change their status.
		pmLabel statusMessage = new pmLabel(c);
		statusMessage.setForeground(Color.black);
		textInfo.add(name);
		textInfo.add(statusMessage);
		// new Listener
		//statusMessage.addActionListener(new statusTestListener());
		
		//combobox to change presence
		JPanel info = new JPanel ();
		info.setBackground(Color.DARK_GRAY);
		info.setLayout(new BorderLayout ());
		info.setBorder(BorderFactory.createEmptyBorder(13,15,13,5));
		statusCombo presence = new statusCombo();
		
		info.add(textInfo, BorderLayout.NORTH);
		info.add(presence, BorderLayout.WEST);
		
		
		add(avatarDisplay, BorderLayout.WEST);
		add(info, BorderLayout.CENTER);
	}
   
}
