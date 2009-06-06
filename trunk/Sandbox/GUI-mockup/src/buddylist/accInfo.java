package buddylist;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import ChatClient.ChatClient;

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
	
	protected JTextField statusMessage;
	
	protected JTextArea textArea;
	
	protected String text;
	
	public accInfo(ChatClient c) 
	{
		setBackground(Color.DARK_GRAY);
		setPreferredSize(new Dimension(300,100));
		setBorder(BorderFactory.createEmptyBorder(5,15,5,5));
		setLayout(new BorderLayout());
		
		//avatar
		avatar = new ImageIcon(System.getProperty("user.dir") + "/src/mainwindow/logo.png");
		avatarDisplay = new JLabel();
		avatarDisplay.setIcon(avatar);
		
		//name and status
		JPanel info = new JPanel();
		info.setBorder(BorderFactory.createEmptyBorder(15,25,25,25));
		info.setBackground(Color.DARK_GRAY);
		
		JLabel name = new JLabel(c.getUserName());
		name.setForeground(Color.WHITE);
		
		// Allowing users to change their status.
		// Need to make this text field more intelligent (text field + dropdown box?)
		statusMessage = new JTextField(13); 
		//JLabel status = new JLabel("(Online)");
		
		statusMessage.setText("<How do you feel today?>");
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
