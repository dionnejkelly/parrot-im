package view.mainwindow;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class headerPanel extends JPanel{

	public headerPanel() {
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createEmptyBorder(50,50,25,50));
		
		//logo-avatar
		JLabel avatarDisplay = new JLabel ();
		avatarDisplay.setHorizontalAlignment(JLabel.CENTER);
		ImageIcon avatar = new ImageIcon (getcwd() + "/src/mainwindow/fb.jpg");
		//set auto scaling + border later
		avatarDisplay.setIcon(avatar);
		
		
		//add to panel
		add(avatarDisplay, BorderLayout.NORTH);

		
	}
	
	public static String getcwd() { 
	    String cwd = System.getProperty("user.dir"); 
	    return cwd; 
	}
}
