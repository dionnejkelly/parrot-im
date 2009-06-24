package view.mainwindow;
import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JLabel;

public class OptionPanel extends JPanel{
	
	public OptionPanel () {
		
		setLayout(new BorderLayout());
		add (new JLabel("Option here"));
	}
}
