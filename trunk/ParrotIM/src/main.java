import java.sql.SQLException;
import java.util.ArrayList;

import org.jivesoftware.smack.XMPPConnection;

import ChatClient.ChatClient;
import buddylist.buddylist;
import chatwindow.chatwindow;
import mainwindow.mainwindow;
import model.Model;

public class main {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		Model model = new Model();
		ChatClient chatClient = new ChatClient(model);
		mainwindow mainWindow = new mainwindow(chatClient, model);
		//buddylist b = new buddylist();
		//chatwindow chat = new chatwindow();
		
		/* Make this into some sort of controller class that
		 * will constantly check for status updates and report
		 * back to Model.
		 */
                
		//XMPPConnection.DEBUG_ENABLED = true;
		
		Thread t = new Thread();
		while (true) {
			
			ArrayList<String> buddies = chatClient.getBuddyList();
			
        		
			System.out.println("USERS STATUS:");
			System.out.println("----------------------------------------------");
			for (int i = 0; i < buddies.size(); i++) {
				 System.out.println(chatClient.getUserPresence(buddies.get(i)));
				
			}
			System.out.println("----------------------------------------------");
			System.out.println();
			
        	try {

        		t.sleep(50000);
        	}
        	catch (InterruptedException e) {
        	
        	}
		}
	}
	

}
