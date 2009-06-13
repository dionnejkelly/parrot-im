package view.chatwindow;
import java.awt.*;
import javax.swing.*;

public class sidePanel extends JPanel {

	public sidePanel() {
		setLayout(new BorderLayout());
		
		JTabbedPane tabs = new JTabbedPane();
		
		JPanel panel1 = new JPanel();
		panel1.setBackground(Color.WHITE);
		String[] names = {"PersonA (You)", "PersonB"};
		JList thisConversation = new JList(names);
		thisConversation.setLayoutOrientation(JList.VERTICAL);
		panel1.add(thisConversation, BorderLayout.CENTER);
		tabs.addTab("Chating with", panel1);
		
		JPanel panel2 = new JPanel();
		panel2.setBackground(Color.WHITE);
		String[] titles = {"Conversation1 (2)", "Conversation2 (5)", "Conversation3 (4)"};
		JList allConversations = new JList(titles);
		panel2.add(allConversations, BorderLayout.CENTER);
		tabs.addTab("All Conversations", panel2);
		
		//add to panel
		add(tabs, BorderLayout.CENTER);
	}
}