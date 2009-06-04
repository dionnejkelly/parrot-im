package chatwindow;

import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import org.jivesoftware.smack.RosterEntry;

import ChatClient.ChatClient;

public class chatwindow extends JFrame{
	private ArrayList<Conversation> conversations;
	
	public chatwindow(String[] names, ChatClient c)
	{
		super("chatWindow Mockup");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setIconImage(new ImageIcon(System.getProperty("user.dir") + "/src/mainwindow/logo.png").getImage());
		
		ArrayList<Conversation> conversations = new ArrayList<Conversation>();
		
		createNewConversation(conversations, names);
		
		getContentPane().add(new mainPanel(conversations, c));
		
		pack();
		setVisible(true);
	}
	
	public void createNewConversation(ArrayList<Conversation> conversations, String[] names){
		conversations.add(new Conversation());
		conversations.get(0).addName("You");
		for(String name: names)
		{
			conversations.get(0).addName(name);
		}
	}
}
