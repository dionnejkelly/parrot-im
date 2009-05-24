package mainwindow;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class manageAccountPanel extends JPanel {
	protected Vector <String> account_list;
	JButton manageAccount;
	JButton guestAccount;
	
	public manageAccountPanel (){
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 50));
		
		//list of accounts
		account_list = new Vector<String>();
		account_list.add("user1");
		account_list.add("user2");
		JComboBox account_select = new JComboBox (account_list);
		account_select.setBorder(BorderFactory.createEmptyBorder(0,0,20,0));
		
		//manage account
		manageAccount = new JButton ("Add/Manage Accounts");
		manageAccount.setBorderPainted(false);
		manageAccount.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                manageAccount_ActionPerformed(evt);
            }
        });
		
		//guest account
		guestAccount = new JButton ("Connect Guest Account"); //underline?
		guestAccount.setBorderPainted(false);
		guestAccount.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                guestAccount_ActionPerformed(evt);
            }
        });
		
		//add components
		add(account_select, BorderLayout.NORTH);
		add(manageAccount, BorderLayout.CENTER);
		add(guestAccount, BorderLayout.SOUTH);

	}
	public void manageAccount_ActionPerformed(ActionEvent e) {
		JFrame accMAN = new JFrame ("Account Manager");
		accMAN.getContentPane().add(new accountManager());
		
		
		accMAN.pack();
		accMAN.setVisible(true);
	} 
	public void guestAccount_ActionPerformed(ActionEvent e) {
		guestAccount.setEnabled(false);
	     
	} 
}
