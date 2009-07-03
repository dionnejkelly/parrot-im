package testing;

import java.util.Scanner;

import javax.net.ssl.SSLSocketFactory;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;

public class main {

    private static final String GOOGLE_SERVER = "talk.google.com";
    private static final int GOOGLE_PORT = 5223;
    private static final String GOOGLE_DOMAIN = "gmail.com";
    
    private static final String username = "parrotim.test@gmail.com";
    private static final String password = "abcdefghi";
    private static final String buddy = "shichan.karachu@gmail.com";
    
    private static XMPPConnection connection;
    private static String yourMessage = "";
    private static Scanner scan = new Scanner(System.in);
    private static Chat newChat;
    
	public static void main (String [] args) throws XMPPException, InterruptedException{
		ConnectionConfiguration config = new ConnectionConfiguration(GOOGLE_SERVER, GOOGLE_PORT, GOOGLE_DOMAIN);
		config.setSocketFactory(SSLSocketFactory.getDefault());

		
		connection = new XMPPConnection(config);
	
		connection.connect();
		connection.login(username, password);
		
		ChatManager chatmanager = connection.getChatManager();
		newChat = chatmanager.createChat(buddy, new MessageListener() {
		    public void processMessage(Chat chat, Message message) {
		    	System.out.println("\n"+buddy+": " +message.getBody());
		        System.out.print(username + ": ");
		        if (message == null) System.out.println("I am here bebeh!");
		    }
		});

		while (true){
			try {

				System.out.print(username + ": ");
				yourMessage = scan.nextLine();
				if (yourMessage.compareToIgnoreCase("quit")==0) break;
		    	newChat.sendMessage(yourMessage);
			}
			catch (XMPPException e) {
		    	System.out.println("Error Delivering block");
			}
		}

		connection.disconnect();
	}


}
