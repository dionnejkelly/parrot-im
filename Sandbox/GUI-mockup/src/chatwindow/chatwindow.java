package chatwindow;

import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jivesoftware.smack.RosterEntry;

import styles.chatWindowListener;

import ChatClient.ChatClient;
import model.Model;

public class chatwindow extends JFrame{
	//private ArrayList<Conversation> conversations;
	public JPanel main;
	private Model model;
	private boolean windowIsOpen;
	
	public chatwindow(ChatClient c, Model model)
	{
		super("chatWindow Mockup");
		this.model = model;
		

		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setIconImage(new ImageIcon(System.getProperty("user.dir") + "/src/mainwindow/logo.png").getImage());
		
		this.addWindowListener(new chatWindowListener(this.model));
		
		//main = new mainPanel(conversations, c, model);
		main = new mainPanel(c, model);
		
		getContentPane().add(main);
		
		pack();
		setVisible(true);
	}
	
	public void setWindowIsOpen(boolean isOpen){
		this.windowIsOpen = isOpen;
	}
	
	public boolean getWindowIsOpen(){
		return this.windowIsOpen;
	}
	
/* Conversation Type is now in the model.	
	public void createNewConversation(ChatData chatData){
		conversations.add(new Conversation());
	    conversations.get(0).addName(chatData.getYourUsername());  // Temporary
	    conversations.get(0).addName(chatData.getOtherUsername());
	}
	
	public void addToConversation(String name){
		conversations.get(0).addName(name);
	}
 */
}
