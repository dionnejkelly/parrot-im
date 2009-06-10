package ChatClient;

import java.util.*;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.RosterListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

import org.jivesoftware.smack.util.StringUtils;

import model.*;

/**
 * Handles all connections involving XMPP protocol. 
 */
public class ChatClient implements MessageListener {
    
    /**
     * The connection to the XMPP server.
     */
    private XMPPConnection connection;
    
    private String userName; // Delete this variable
    
    /**
     * Allows the ChatClient to store data for the GUI.
     */
    private Model model;
    
    /**
     * Holds a list of friends for the current connection.
     */
    private Roster roster;
    
    /**
     * Handles all chat message events, receipt and submission. 
     */
    private ChatManager chatManager;
    
    
    public ChatClient(Model model){
        this.model = model;
        this.roster = null;
        this.chatManager = null;
    }
        
        public void setPresence(String status) throws InterruptedException {
    		Presence presence = new Presence(Presence.Type.available);
    		
    		presence.setStatus(status);
    	
    		connection.sendPacket(presence);
    	

    		
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
        
        // Overloaded, temporary
        public void login(AccountData account) throws XMPPException {
            // TODO FIX THIS METHOD, should work with all protocols.
        	
            if (account.getServer() == ServerType.GOOGLE_TALK) {
                ConnectionConfiguration config = new ConnectionConfiguration("talk.google.com", 5222, "gmail.com");
                connection = new XMPPConnection(config);
                connection.connect();
                connection.login(account.getAccountName(), account.getPassword());
                    
                // If connected...
                model.connectAccount(account);
                
                /* Get roster updated after the login */
                this.roster = connection.getRoster();
                this.roster.addRosterListener(new BuddyListener());
                    
                this.userName = account.getAccountName();
            }       
            else {
                // handle other types of servers
            }
     	
            return;            
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
        	ArrayList<String> buddies = new ArrayList<String>();
        	
        	if (connection != null && connection.getRoster() != null) {
        		  Roster roster = connection.getRoster();
                  Collection<RosterEntry> entries = roster.getEntries();
               
                 
                  for(RosterEntry r:entries)
                  {
                      buddies.add(r.getUser());
                  }
        		
        	}
              
               
                return buddies;
        }

        public void disconnect()
        {
                connection.disconnect();
        }
       
        public void processMessage(Chat chat, Message message)
        {
            UserData user = null;
            MessageData m = null;
            
            if (message.getType() == Message.Type.chat) {
                user = model.findUserByAccountName(chat.getParticipant());
                m = new MessageData(user, message.getBody(), "font", "4");
                model.receiveMessage(user.getFriendOf(), m);
            }
                
            return;        
            //System.out.println(chat.getParticipant() + " says: " + message.getBody());
                
            //displayPanel.addMessage(msg, fontSelect.getSelectedItem().toString(), "4");
        }
        
        /* PLEASE FIX ME */
        /* I change some codes here, so I think now it can keep track the status of the people.
         * 
         */
        public String getUserPresence(String userID) {
            String status = "offline";
            if (connection != null && connection.getRoster() != null) {
                Presence presence = connection.getRoster().getPresence(userID);
                
                if (presence.isAvailable()) {
                    status = presence.getStatus();
                    
                    if(status=="")
                    	status="online";
                }
            }
            return userID + " = " + status;
            
        }

    /* Phase this method out */
    public String getUserName(){
            return userName;
    }

    /**
     * Changes to the roster, that is, changes to friends' statuses
     * or availability, are handled by this class.
     */
    private class BuddyListener implements RosterListener {
        public void entriesAdded(Collection<String> addresses) {
            // Fix me!
            System.out.println(addresses);
            return;
        }
        
        public void entriesUpdated(Collection<String> addresses) {
            // Fix me!
            System.out.println(addresses);
            return;
        }
        
        public void entriesDeleted(Collection<String> addresses) {
            // Fix me!
            System.out.println(addresses);
            return;
        }
        
        public void presenceChanged(Presence presence) {
            String bareAddress = StringUtils.parseBareAddress(presence.getFrom());
            System.out.println(presence.getFrom() + ", that is, "
                               + bareAddress + " status change:"
                               + presence.getStatus());
            return;
        }
    }
}

