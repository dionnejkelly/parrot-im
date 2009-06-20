package view.chatLog;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.sql.SQLException;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;

import model.Model;



public class buddyPanel extends JPanel{
	protected JList buddyList;

	public buddyPanel(Model model, String username){
		
		//list of buddies who has logged chat
		try {
			buddyList = new JList(model.getBuddyLogList(username));
		} catch (SQLException e) {
			buddyList = new JList();
		}
		buddyList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		//buddyList.setSelectedIndex(0);
		JScrollPane scrollBuddy = new JScrollPane(buddyList);
		scrollBuddy.setAutoscrolls(true);
		scrollBuddy.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		//setting panel
		this.setLayout(new BorderLayout());
		this.setMinimumSize(new Dimension (100, this.getHeight()));
		this.add(scrollBuddy, BorderLayout.CENTER);
		
	}

}
