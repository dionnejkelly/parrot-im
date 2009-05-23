package mainwindow;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class headerPanel extends JPanel{
	protected JLabel status;

	public headerPanel() {
		setLayout(new BorderLayout());
		
		JLabel avatar = new JLabel ();
		avatar.setHorizontalAlignment(JLabel.CENTER);
		avatar.setIcon(new ImageIcon ("logo.png"));
		avatar.setSize(100,100);
		status = new JLabel("You are not signed in", JLabel.CENTER);
		status.setAlignmentX((float)100);
		
		
		//add to panel
		add(status, BorderLayout.NORTH);
		add(avatar, BorderLayout.CENTER);
		
	}

}
