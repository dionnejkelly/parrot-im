package chatwindow;

import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import ChatClient.ChatClient;

public class chatwindow extends JFrame{
	private ArrayList<Conversation> conversations;
	
	public chatwindow()
	{
		super("chatWindow Mockup");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setIconImage(new ImageIcon(System.getProperty("user.dir") + "/src/mainwindow/logo.png").getImage());
		
		ArrayList<Conversation> conversations = new ArrayList<Conversation>();
		
		getContentPane().add(new mainPanel(conversations));
		
		pack();
		setVisible(true);
	}
	
	public void addNewConversation(String[] users){
		
	}
}
