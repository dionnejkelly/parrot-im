package view.mainwindow;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class HeaderPanel extends JPanel{
	protected JLabel avatarDisplay;
	protected JLabel status;
	protected ImageIcon avatar;
	
	public HeaderPanel() {
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createEmptyBorder(50,50,0,50));
		
		//logo-avatar
		avatarDisplay = new JLabel ();
		avatarDisplay.setHorizontalAlignment(SwingConstants.CENTER);
		avatar = new ImageIcon (this.getClass().getResource("/images/buddylist/logoBox.png"));
	
		//TODO: set auto scaling + border later
		avatarDisplay.setIcon(avatar);
		
		status = new JLabel("Not signed in", SwingConstants.CENTER);
		status.setBorder(BorderFactory.createEmptyBorder(15,0,0,0));
		
		//add to panel
		add(avatarDisplay, BorderLayout.NORTH);
		add(status, BorderLayout.CENTER);
	}
	
	public static String getcwd() { 
	    String cwd = System.getProperty("user.dir"); 
	    return cwd; 
	}
	
	/*
	protected void setAnimation(){
		avatarDisplay.setIcon(new ImageIcon(getcwd() + "/src/mainwindow/animation.gif"));
		status.setText("Signing in");
		this.setVisible(true);
		System.out.println("headerPanel here");
	}*/
	
	protected void loadMain(){
		//avatarDisplay.setIcon(avatar);
		status.setText("<html><FONT COLOR=RED>Sign in failed!</FONT></html>");
	}
}
