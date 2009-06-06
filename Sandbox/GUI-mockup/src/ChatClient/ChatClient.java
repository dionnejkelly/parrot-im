package ChatClient;

import java.util.*;
import java.io.*;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

import model.Model;


public class ChatClient implements MessageListener
{
        private XMPPConnection connection;
        private String userName;
        private Model model; // Unsure if the model is needed here yet.
       
        public ChatClient(Model model){
            this.model = model;
        }
       
        public void login(String userName, String password, int server) throws XMPPException
        {//the "return"s are temporary
        	if (server==0){//MSN
        		return;
        	}
        	else if (server==1){//AIM
        		return;
        	}
        	else if (server==2){//Twitter
        		return;
        	}
        	else if (server==3){//ICQ
        		return;
        	}
        	else if (server==4){//google talk
                ConnectionConfiguration config = new ConnectionConfiguration("talk.google.com", 5222, "gmail.com");
                connection = new XMPPConnection(config);
                connection.connect();
                connection.login(userName, password);
               
                this.userName = userName;
        	}
        }
       
        public void sendMessage(String message, String to) throws XMPPException
        {
                Chat chat = connection.getChatManager().createChat(to, this);
                chat.sendMessage(message);
        }
       
        public void displayBuddyList()
        {
                Roster roster = connection.getRoster();
                Collection<RosterEntry> entries = roster.getEntries();
               
                System.out.println("\n\n" + entries.size() + " buddy(ies):");
                for(RosterEntry r:entries)
                {
                        System.out.println(r.getUser());
                }
        }
       
        public ArrayList<String> getBuddyList()
        {
                Roster roster = connection.getRoster();
                Collection<RosterEntry> entries = roster.getEntries();
                ArrayList<String> buddies = new ArrayList<String>();
               
                for(RosterEntry r:entries)
                {
                        buddies.add(r.getUser());
                }
               
                return buddies;
        }

        public void disconnect()
        {
                connection.disconnect();
        }
       
        public void processMessage(Chat chat, Message message)
        {
                if(message.getType() == Message.Type.chat)
                System.out.println(chat.getParticipant() + " says: " + message.getBody());
    }
       
/* Main class unneeded now.
        public static void main(String args[]) throws XMPPException, IOException
       {
                // declare variables
                ChatClient c = new ChatClient();
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                String msg;


                // turn on the enhanced debugger
                XMPPConnection.DEBUG_ENABLED = true;


                // provide your login information here
                c.login("cmpt275testing@gmail.com", "abcdefghi");


                c.displayBuddyList();
                System.out.println("-----");
                System.out.println("Enter your message in the console.");
                System.out.println("All messages will be sent to kevin.fahy@gmail.com");
                System.out.println("-----\n");

                while( !(msg=br.readLine()).equals("bye"))
                {
                        // your buddy's gmail address goes here
                        c.sendMessage(msg, "kevin.fahy@gmail.com");
                }

                c.disconnect();
                System.exit(0);
        }
  */
        public String getUserName(){
                return userName;
        }
}

