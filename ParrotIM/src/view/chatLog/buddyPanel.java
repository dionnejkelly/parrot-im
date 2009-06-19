package view.chatLog;
import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;

import model.chatLogModel.modeldummy;



public class buddyPanel extends JPanel{
	//dummy model
	//private modeldummy model;
	protected JList buddyList;

	public buddyPanel(modeldummy model){
		
		//list of buddies who has logged chat
		buddyList = new JList(model.getBuddyList());
		buddyList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		buddyList.setSelectedIndex(0);
		JScrollPane scrollBuddy = new JScrollPane(buddyList);
		scrollBuddy.setAutoscrolls(true);
		scrollBuddy.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		//setting panel
		this.setLayout(new BorderLayout());
		this.setMinimumSize(new Dimension (100, this.getHeight()));
		this.add(scrollBuddy, BorderLayout.CENTER);
		
	}

}
