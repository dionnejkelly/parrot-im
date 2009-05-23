package mainwindow;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class headerPanel extends JPanel{
	protected JLabel status;

	public headerPanel() {
		setLayout(new BorderLayout());
		
		//logo-avatar
		JLabel avatar = new JLabel ();
		avatar.setHorizontalAlignment(JLabel.CENTER);
		System.out.println(getcwd());
		avatar.setIcon(new ImageIcon (getcwd() + "/src/mainwindow/logo.png"));
		avatar.setSize(100,100);
		
		//status
		status = new JLabel("You are not signed in", JLabel.CENTER);
		
		//list of accounts
		
		
		//add to panel
		add(status, BorderLayout.NORTH);
		add(avatar, BorderLayout.CENTER);
		
	}
	
	public static String getcwd() { 
	    String cwd = System.getProperty("user.dir"); 
	    return cwd; 
	}
}
