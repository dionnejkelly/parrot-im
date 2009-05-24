package mainwindow;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

public class headerPanel extends JPanel{

	public headerPanel() {
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createEmptyBorder(50,50,25,50));
		
		//logo-avatar
		JLabel avatar = new JLabel ();
		avatar.setHorizontalAlignment(JLabel.CENTER);
		avatar.setIcon(new ImageIcon (getcwd() + "/src/mainwindow/logo.png"));
		avatar.setPreferredSize(new Dimension(100,100));
		//avatar.setBorder(BorderFactory.createLineBorder(Color.black));
		
		
		//add to panel
		add(avatar, BorderLayout.NORTH);

		
	}
	
	public static String getcwd() { 
	    String cwd = System.getProperty("user.dir"); 
	    return cwd; 
	}
}
