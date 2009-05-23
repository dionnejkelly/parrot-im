package mainwindow;

import java.awt.BorderLayout;

import javax.swing.JComboBox;
import javax.swing.JPanel;

public class manageAccountPanel extends JPanel {
	
	public manageAccountPanel (){
		setLayout(new BorderLayout());
		
		//list of accounts
		JComboBox account_select = new JComboBox ();
		
		add(account_select, BorderLayout.NORTH);
	}

}
