package view.mainwindow;
import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JLabel;

public class HelpPanel extends JPanel{
	
	public HelpPanel() {
		
		setLayout(new BorderLayout());
		add (new JLabel("Help here"));
	}
}
