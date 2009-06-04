package chatwindow;

import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jivesoftware.smack.RosterEntry;

import ChatClient.ChatClient;

public class chatwindow extends JFrame{
	private ArrayList<Conversation> conversations;
	public JPanel main;
	
	public chatwindow(String[] names, ChatClient c)
	{
		super("chatWindow Mockup");
		
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setIconImage(new ImageIcon(System.getProperty("user.dir") + "/src/mainwindow/logo.png").getImage());
		
		conversations = new ArrayList<Conversation>();
		
		createNewConversation(names);
		
		main = new mainPanel(conversations, c);
		
		getContentPane().add(main);
		
		pack();
		setVisible(true);
	}
	
	public void createNewConversation(String[] names){
		conversations.add(new Conversation());
		conversations.get(0).addName("You");
		for(String name: names)
		{
			conversations.get(0).addName(name);
		}
	}
	
	public void addToConversation(String name){
		conversations.get(0).addName(name);
	}
}
