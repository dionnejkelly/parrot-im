package mainwindow;
import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JList;

public class accountManager extends JPanel{
	
	public accountManager() {
		
		setLayout(new BorderLayout());
		//JList accountList = new JList(account_list);
		add (new JLabel("Hello world"));
	}
}
