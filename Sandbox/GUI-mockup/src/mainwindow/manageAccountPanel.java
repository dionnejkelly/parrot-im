package mainwindow;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JSeparator;

public class manageAccountPanel extends JPanel {
	Vector <String> account_list;
	
	public manageAccountPanel (){
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createEmptyBorder(0, 50, 50, 50));
		
		//list of accounts
		account_list = new Vector<String>();
		account_list.add("user1");
		account_list.add("user2");
		System.out.println(account_list.size());
		JComboBox account_select = new JComboBox (account_list);
		
		JSeparator line = new JSeparator();
		
		//add components
		add(account_select, BorderLayout.NORTH);
	}

}
