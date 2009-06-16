package controller.services;

import java.sql.SQLException;
import java.util.*;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.RosterListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

import org.jivesoftware.smack.util.StringUtils;

import controller.chatbot.Chatbot;

import model.*;
import model.dataType.AccountData;
import model.dataType.MessageData;
import model.dataType.ServerType;
import model.dataType.UpdatedType;
import model.dataType.UserData;

/**
 * Handles all connections involving XMPP protocol. 
 */
public class Xmpp {
    
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
    
    private ArrayList<Chat> chats;
    
   // private String tempID;

    private UserData user = null;
    private MessageData m = null;
    private Chat chat = null;
    
    
    
    
    public Xmpp(Model model){
        this.model = model;
        this.roster = null;
        this.chatManager = null;
        this.chats = new ArrayList<Chat>();
        
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
        
        	
            if (account.getServer() == ServerType.GOOGLE_TALK) {
                ConnectionConfiguration config = new ConnectionConfiguration("talk.google.com", 5222, "gmail.com");
                connection = new XMPPConnection(config);
                connection.connect();
                connection.login(account.getAccountName(), account.getPassword());
                    
                // If connected...
                model.connectAccount(account);
                
                connection.addPacketListener(new MessagePacketListener(),
                                             new MessagePacketFilter());
                
                //this.chatManager = connection.getChatManager();
                //this.chatManager.addChatListener(new ChatListener());
                
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
       
        public void sendMessage(String message, String to) throws XMPPException {
            Chat chat = null;
            boolean chatExists = false;
            
            /* Check for existing chats */
            for (Chat c : chats) {
                if (c.getParticipant().equalsIgnoreCase(to)) {
                    chatExists = true;
                    chat = c;
                    break;
                }
            }
                        
            /* Create if doesn't exist */
            if (!chatExists) {
                chat = connection.getChatManager().createChat(to, 
                        new MsgListener());
                chats.add(chat);
            }
            
            chat.sendMessage(message);
            return;                
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
       
/* Moved into MsgListener below
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
 */
        
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

    public Roster getRoster() {
        return this.roster;
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
            System.out.println(addresses + " from entriesAdded");
            return;
        }
        
        public void entriesUpdated(Collection<String> addresses) {
            // Fix me!
            System.out.println(addresses + " from entriesUpdated");
            return;
        }
        
        public void entriesDeleted(Collection<String> addresses) {
            // Fix me!
            System.out.println(addresses + " from entriesDeleted");
            return;
        }
        
        public void presenceChanged(Presence presence) {
            UserData userToUpdate = null;
            
            String bareAddress = StringUtils.parseBareAddress(presence.getFrom());
            userToUpdate = model.findUserByAccountName(bareAddress);
            userToUpdate.setStatus(presence.getStatus());
            model.forceNotify(UpdatedType.BUDDY);
            // System.out.println(presence.getFrom() + ", that is, "
           //                    + bareAddress + " status change:"
           //                    + presence.getStatus());
            return;
        }
    }
    
    /**
     * Controls program flow upon new chats being created.
     */
    private class ChatListener implements ChatManagerListener {
        public void chatCreated(Chat chat, boolean createdLocally) {
            System.out.println("CREATED CHAT!!");
            /* Set up listener for the new Chat */
            chat.addMessageListener(new MsgListener());
            
            chats.add(chat);
            
            
            return;
        }
    }
    
    private class MsgListener implements MessageListener {
    	
    	
        public void processMessage(Chat chat, Message message) {
            UserData user = null;
            MessageData m = null;
            
              System.out.println("In processMessage");
//            System.out.println(message.getFrom());
//            System.out.println("Process message = " + message.getBody());
//            System.out.println("            Process Message Type = " + message.getType());
//           
//            System.out.println("            Process message ID ()  = " + message.getThread());
//            System.out.println("            Process FROM PACKET ID = " + tempID);
//     
            System.out.println("------------------");
//                
//      
//            
//            
//            
//            if (message.getType() == Message.Type.chat &&
//                (!message.getThread().equals(tempID))) {
//                user = model.findUserByAccountName(chat.getParticipant());
//                m = new MessageData(user, message.getBody(), "font", "4");
//                model.receiveMessage(user.getFriendOf(), m);
//                
//                System.out.println("This should be printed!!!!");
// 
//            }
//                    
            return;
        }
    }
    
    private class MessagePacketFilter implements PacketFilter {
        public boolean accept(Packet packet) {
            System.out.println(packet);
            return (packet instanceof Message);
        }
    }
    
    private class MessagePacketListener implements PacketListener {
    	
        public void processPacket(Packet packet) {
            Chatbot chatbot = null;
            
            
            /* packet is a new message, make chat if from new person */
           
            boolean chatExists = false;
            Message message = (Message) packet;
            String bareAddress = StringUtils.parseBareAddress(message.getFrom());
            
            
            if (message.getType() == Message.Type.normal ||
                message.getType() == Message.Type.chat) {
                for (Chat c : chats) {
                    //System.out.println(c.getParticipant());
                    //System.out.println(bareAddress);
                    if (c.getParticipant().equalsIgnoreCase(bareAddress)) {
                        chatExists = true;
                        chat = c;
                        break;
                    }
                }
                
                /* If no chats exist with the sender of the new message... */
                if (!chatExists) {
                    chat = connection.getChatManager().createChat(bareAddress, 
                            new MsgListener());
                    chats.add(chat);
                    //tempID = chat.getThreadID();
                    
                    
                    /* Display first message bug FIX */
                    user = model.findUserByAccountName(chat.getParticipant());
                    m = new MessageData(user, message.getBody(), "font", "4");
                    try {
						model.receiveMessage(user.getFriendOf(), m);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                    
          
                   
                } else {
                    //tempID = "";
                    user = model.findUserByAccountName(chat.getParticipant());
                    m = new MessageData(user, message.getBody(), "font", "4");
                    try {
						model.receiveMessage(user.getFriendOf(), m);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
               
                    
                    
                   
               	
                 
                }
                chatbot = new Chatbot();
                try {
                    chatbot.get_input(message.getBody());
                    String respond = chatbot.respond();
                    sendMessage(chat.getParticipant(), respond);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                
                
                
                /* Else, the message listener handles it automatically */
                
            }
            
            System.out.println("------------------");
            System.out.println("In processPacket");
//            System.out.println(message.getFrom());
            System.out.println("Packet message = " + message.getBody());
//            System.out.println(message.getType());
           // System.out.println("Packet message ID = " + tempID);
            
            System.out.println("------------------");
            
            return;
        }
    }
}

