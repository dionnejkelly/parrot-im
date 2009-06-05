package buddylist;

import java.awt.*;

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
	
	JTextField statusMessage;
	
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
		
		JLabel name = new JLabel(c);
		name.setForeground(Color.WHITE);
		
		JLabel status = new JLabel("(Online)");
		status.setForeground(Color.WHITE);
		
		info.add(name);
		info.add(status);
		
		add(avatarDisplay, BorderLayout.WEST);
		add(info, BorderLayout.CENTER);
	}
}
