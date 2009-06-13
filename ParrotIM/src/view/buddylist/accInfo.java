package view.buddylist;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import controller.services.Xmpp;

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
		avatar = new ImageIcon("imagesimages/mainwindow/logo.png");
		avatarDisplay = new JLabel();
		avatarDisplay.setIcon(avatar);
		
		//name and status
		JPanel info = new JPanel();
		info.setLayout(new GridLayout(2,1));
		info.setBorder(BorderFactory.createEmptyBorder(15,15,25,5));
		info.setBackground(Color.DARK_GRAY);
		
		JLabel name = new JLabel(c.getUserName());
		name.setForeground(Color.WHITE);
		
		// Allowing users to change their status.
		// Need to make this text field more intelligent (text field + dropdown box?)
		statusMessage = new pmLabel(c); 
		//JLabel status = new JLabel("(Online)");
		
		statusMessage.setForeground(Color.black);
		
		// new Listener
		statusMessage.addActionListener(new statusTestListener());
		
		info.add(name);
		info.add(statusMessage);
		
		add(avatarDisplay, BorderLayout.WEST);
		add(info, BorderLayout.CENTER);
	}
	
	
	
	// new listener inorder to change the user's status
	private class statusTestListener implements ActionListener {
		 public void actionPerformed(ActionEvent evt) {
		        text = statusMessage.getText();
		        System.out.println("This has to be displayed in the status screen: " + text);
		        
		        // need this method to change the user's status	        
		        //c.setPresence(text);
		        
		    }
		 

		
	}
   
}
