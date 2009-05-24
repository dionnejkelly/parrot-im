package mainwindow;
import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JList;

public class optionPanel extends JPanel{
	
	public optionPanel () {
		
		setLayout(new BorderLayout());
		add (new JLabel("Option here"));
	}
}
