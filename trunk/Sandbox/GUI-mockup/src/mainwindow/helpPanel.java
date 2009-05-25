package mainwindow;
import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JLabel;

public class helpPanel extends JPanel{
	
	public helpPanel() {
		
		setLayout(new BorderLayout());
		add (new JLabel("Help here"));
	}
}
