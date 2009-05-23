package mainwindow;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JPanel;

public class signinPanel extends JPanel{

	public signinPanel(){
		setLayout (new BorderLayout());
		
		add(new headerPanel(), BorderLayout.NORTH);
		add(new manageAccountPanel(), BorderLayout.CENTER);
	}

}
