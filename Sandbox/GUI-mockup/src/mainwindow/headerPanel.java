package mainwindow;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class headerPanel extends JPanel{
	protected JLabel avatarDisplay;
	protected JLabel status;
	
	public headerPanel() {
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createEmptyBorder(50,50,0,50));
		
		//logo-avatar
		avatarDisplay = new JLabel ();
		avatarDisplay.setHorizontalAlignment(JLabel.CENTER);
		ImageIcon avatar = new ImageIcon (getcwd() + "/src/mainwindow/fb.jpg");
	
		//TODO: set auto scaling + border later
		avatarDisplay.setIcon(avatar);
		
		status = new JLabel("Not signed in", JLabel.CENTER);
		status.setBorder(BorderFactory.createEmptyBorder(15,0,0,0));
		
		//add to panel
		add(avatarDisplay, BorderLayout.NORTH);
		add(status, BorderLayout.CENTER);
	}
	
	public static String getcwd() { 
	    String cwd = System.getProperty("user.dir"); 
	    return cwd; 
	}
	protected void setAnimation(){
		avatarDisplay.setIcon(new ImageIcon(getcwd() + "/src/mainwindow/animation.gif"));
		status.setText("Signing in");
		this.setVisible(true);
		System.out.println("headerPanel here");
	}
}
