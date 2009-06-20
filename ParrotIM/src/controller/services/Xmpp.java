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
import model.dataType.ConversationData;
import model.dataType.GoogleTalkUserData;
import model.dataType.MessageData;
import model.dataType.ServerType;
import model.dataType.UpdatedType;
import model.dataType.UserData;

/**
 * Handles all connections involving XMPP protocol.
 */
public class Xmpp {

    /** The connection to the XMPP server. */
    private XMPPConnection connection;

    /** Delete this variable. */
    // private String userName;
    /** Allows the ChatClient to store data for the GUI. */
    private Model model;

    /** Holds a list of friends for the current connection. */
    private Roster roster;

    /** Handles all chat message events, receipt and submission. */
    private ChatManager chatManager;

    /** Add comments here. */
    private ArrayList<Chat> chats;

    /** Delete it? */
    // private String tempID;
    /** Add comments here. */
    private UserData user = null;
    private MessageData m = null;
    private Chat chat = null;

    /**
     * This is the constructor of Xmpp.
     * 
     */
    public Xmpp(Model model) {
        this.model = model;
        this.roster = null;
        this.chatManager = null;
        this.chats = new ArrayList<Chat>();

    }

    /**
     * These are setter and getter of Xmpp class.
     * 
     */

    /** This method is using to set the status of the user. */
    public void setStatus(String status) {
        Presence presence = new Presence(Presence.Type.available);
        if (status.equals("Available")) {
            presence.setMode(Presence.Mode.available);
        } else if (status.equals("Away")) {
            presence.setMode(Presence.Mode.away);
        } else if (status.equals("Busy")) {
            presence.setMode(Presence.Mode.dnd);
        } else {
            presence.setMode(Presence.Mode.chat);
        }
        System.out.println("Called");
        connection.sendPacket(presence);
    }

    /** This method actually set presence of the user. */
    public void setPresence(String presenceStatus) throws InterruptedException {
        Presence presence = new Presence(Presence.Type.available);
        presence.setStatus(presenceStatus);
        connection.sendPacket(presence);
    }

    /** This method is using to get the Roster of someone. */
    public Roster getRoster() {
        return this.roster;
    }

    /** Phase this method out */
    // public String getUserName() {
    // return userName;
    // }
    /** This method is using to get the presence of the user. */
    public String getUserPresence(String userID) {
        String status = "offline";
        if (connection != null && connection.getRoster() != null) {
            Presence presence = connection.getRoster().getPresence(userID);
            if (presence.isAvailable()) {
                if (presence.isAvailable()) {

                    if (presence.getMode() == Presence.Mode.dnd) {
                        status = presence.getStatus();
                        if (status != null) {
                            status = "Busy";
                        }
                    } else if (presence.getMode() != Presence.Mode.dnd) {
                        status = presence.getStatus();
                        if (status != null) {
                            status = "Online";
                        }
                    }

                }
            }
        }
        return userID + " = " + status;
    }

    /**
     * These are other utility methods of Xmpp class.
     * 
     */

    /**
     * This is the helper method using to determine if we connect to the sever
     * successfully.
     */
    public boolean isConnected() {
        return connection.isConnected();
    }

    /** This is the method using to break the connection. */
    public void disconnect() {
        connection.disconnect();
    }

    /** This method is using to log in to Parrot of the user. */
    public void login(String userName, String password, int server)
            throws XMPPException {// the "return"s are temporary
        if (server == 0) {// MSN
            return;
        } else if (server == 1) {// AIM
            return;
        } else if (server == 2) {// Twitter
            return;
        } else if (server == 3) {// ICQ
            return;
        } else if (server == 4) {// google talk
            ConnectionConfiguration config = new ConnectionConfiguration(
                    "talk.google.com", 5222, "gmail.com");
            connection = new XMPPConnection(config);
            connection.connect();
            connection.login(userName, password);
            // this.userName = userName;
        }
    }

    /** This method Overloaded the login method,but it is temporary. */
    public void login(AccountData account) throws XMPPException {
        if (account.getServer() == ServerType.GOOGLE_TALK) {
            ConnectionConfiguration config = new ConnectionConfiguration(
                    "talk.google.com", 5222, "gmail.com");
            connection = new XMPPConnection(config);
            connection.connect();
            connection.login(account.getAccountName(), account.getPassword());

            // If connected...
            model.connectAccount(account);

            connection.addPacketListener(new MessagePacketListener(),
                    new MessagePacketFilter());

            // this.chatManager = connection.getChatManager();
            // this.chatManager.addChatListener(new ChatListener());

            /* Get roster updated after the login */
            this.roster = connection.getRoster();
            this.roster.addRosterListener(new BuddyListener());
            // this.userName = account.getAccountName();
        } else {
            // handle other types of servers
        }
        return;
    }

    /*
     * Note, please phase out the other log-in methods. The AccountData creation
     * should occur within this class, not the view.
     */
    /**
     * Attempts to log a user into the server based on the given account
     * information. If the current profile already exists, this account
     * information is added to it.
     */
    public void login(ServerType server, String accountName, String password)
            throws XMPPException {
        AccountData account = null;
        UserData user = null;
        ConnectionConfiguration config = null;

        /* Create an AccountData from entered information */
        account = new AccountData(server, accountName, password);

        if (account.getServer() == ServerType.GOOGLE_TALK) {
            config = new ConnectionConfiguration("talk.google.com", 5222,
                    "gmail.com");
        } else if (account.getServer() == ServerType.JABBER) {
            config = new ConnectionConfiguration(StringUtils
                    .parseServer(accountName), 5222, StringUtils
                    .parseServer(accountName));
            System.out.println(StringUtils.parseServer(accountName));
        } else {
            // Other protocols
        }

        connection = new XMPPConnection(config);
        connection.connect();
        connection.login(account.getAccountName(), account.getPassword());

        // If connected...
        model.connectAccount(account);

        connection.addPacketListener(new MessagePacketListener(),
                new MessagePacketFilter());

        /* Get roster updated after the login */
        this.roster = connection.getRoster();
        this.roster.addRosterListener(new BuddyListener());
        // this.userName = account.getAccountName();

        /* Set up own user data. TODO REMOVE THIS */
        user = new GoogleTalkUserData(account.getAccountName());
        account.setOwnUserData(user);

        /* Set up friends' user data */
        this.populateBuddyList(account);
        //for (String s : this.getBuddyList()) {
        //    model.addFriend(account, s);
        //}

        // StringUtils.parseServer(accountName);

        /* Handle the current profile */
        if (model.currentProfileExists()) {
            model.addAccountToCurrentProfile(account);
        } else { // current profile does not exist
            model.createCurrentProfile(account, "<Profile Name>");
        }

        return;

    }

    /** This method is using to add a friend to the friend list. */
    public void addFriend(String userID) {
        Roster roster = connection.getRoster();
        String nickname = StringUtils.parseBareAddress(userID);
        try {
            roster.createEntry(userID, nickname, null);
            model.addFriend(ServerType.GOOGLE_TALK, userID);
        } catch (XMPPException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /** This method is using to remove a friend to the friend list. */
    public void removeFriend(String userID) {
        UserData user = null;
        Roster roster = connection.getRoster();
        Collection<RosterEntry> entries = roster.getEntries();
        Iterator i = entries.iterator();
        while (i.hasNext()) {
            RosterEntry nextEntry = ((RosterEntry) i.next());
            // remove entries
            if (nextEntry.getUser().equals(userID))
                try {
                    roster.removeEntry(nextEntry);
                    user = model.findUserByAccountName(nextEntry.getUser());
                    model.removeFriend(user);
                } catch (XMPPException e) {
                    e.printStackTrace();
                }
        }
    }

    /** This method is the helper method to display the buddylist. */
    public void displayBuddyList() {
        Roster roster = connection.getRoster();
        Collection<RosterEntry> entries = roster.getEntries();

        System.out.println("\n\n" + entries.size() + " buddy(ies):");
        for (RosterEntry r : entries) {
            System.out.println(r.getUser());
        }
    }

    /** This method is using to get the buddy list of the user. */
    public ArrayList<String> getBuddyList() {
        ArrayList<String> buddies = new ArrayList<String>();

        if (connection != null && connection.getRoster() != null) {
            Roster roster = connection.getRoster();
            Collection<RosterEntry> entries = roster.getEntries();

            for (RosterEntry r : entries) {
                buddies.add(r.getUser());
            }
        }
        return buddies;
    }
    
    public void populateBuddyList(AccountData account) {
        UserData user = null;
        
        if (connection != null && connection.getRoster() != null) {
            Roster roster = connection.getRoster();
            Collection<RosterEntry> entries = roster.getEntries();

            for (RosterEntry r : entries) {
                /* Decide which type of user to use */
                if (account.getServer() == ServerType.GOOGLE_TALK) {
                    user = new GoogleTalkUserData(r.getUser(), r.getName(),
                            this.getUserPresence(r.getUser()));
                    System.out.println(r.getName());
                } else if (account.getServer() == ServerType.JABBER) {
                 //   user = new JabberUserData(r.getUser(), r.getName(),
                 //           this.getUserPresence(r.getUser()));
                } else { // some other user
                    // TODO implement me!
                }
                
                model.addFriend(account, user);
            }
        }
        return;  
    }

    /**
     * This method is using to send the message to friend. Explicitly sets a
     * receiver. If the receiver is unknown, then use the overloaded
     * sendMessage() which automatically sends the reply to the open
     * conversation.
     */
    public void sendMessage(String messageString, String to) throws XMPPException {
        Chat chat = null;
        boolean chatExists = false;
        ConversationData conversation = null;
        MessageData messageObject = null;
        String fromUser = null;
        String font = "Arial"; // temp values
        String size = "4";
        
        conversation = model.findConversationByFriend(to);
        fromUser = conversation.getAccount().getAccountName();
        to = conversation.getUser().getAccountName();

        messageObject = new MessageData(fromUser, messageString, font, size);

        try {
            model.sendMessage(conversation, messageObject);
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

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
            chat = connection.getChatManager()
                    .createChat(to, new MsgListener());
            chats.add(chat);
        }
        chat.sendMessage(messageString);
        return;
    }

    /**
     * Sends a message to the person in the current active conversation.
     * 
     * @param message
     * @throws XMPPException
     */
    public void sendMessage(String messageString, String font, String size)
            throws XMPPException {
        Chat chat = null;
        boolean chatExists = false;
        String to = null;        
        MessageData messageObject = null;
        ConversationData conversation = null;
        String fromUser = null;

        /* Default to sending to the active user */
        conversation = model.getActiveConversation();
        fromUser = conversation.getAccount().getAccountName();
        to = conversation.getUser().getAccountName();

        messageObject = new MessageData(fromUser, messageString, font, size);

        try {
            model.sendMessage(conversation, messageObject);
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
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
            chat = connection.getChatManager()
                    .createChat(to, new MsgListener());
            chats.add(chat);
        }
        chat.sendMessage(messageString);
        
        return;
    }

    /* Manipulation of conversations */

    public void changeConversation(String accountName) {
        UserData user = null;

        user = model.findUserByAccountName(accountName);
        if (user != null) {
            model.setActiveConversation(user);
        }

        return;
    }

    /* This is another class called BuddyListener. */

    /**
     * Changes to the roster, that is, changes to friends' statuses or
     * availability, are handled by this class.
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

            String bareAddress = StringUtils.parseBareAddress(presence
                    .getFrom());
            userToUpdate = model.findUserByAccountName(bareAddress);
            userToUpdate.setStatus(presence.getStatus());
            model.forceNotify(UpdatedType.BUDDY);
            // System.out.println(presence.getFrom() + ", that is, "
            // + bareAddress + " status change:"
            // + presence.getStatus());
            return;
        }
    }

    /** This is another class call ChatListener. */

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

    /** This is another class call MsgListener. */

    private class MsgListener implements MessageListener {

        public void processMessage(Chat chat, Message message) {
            UserData user = null;
            MessageData m = null;

            System.out.println("In processMessage");
            // System.out.println(message.getFrom());
            // System.out.println("Process message = " + message.getBody());
            // System.out.println("            Process Message Type = " +
            // message.getType());
            //           
            // System.out.println("            Process message ID ()  = " +
            // message.getThread());
            // System.out.println("            Process FROM PACKET ID = " +
            // tempID);
            //     
            System.out.println("------------------");
            // if (message.getType() == Message.Type.chat &&
            // (!message.getThread().equals(tempID))) {
            // user = model.findUserByAccountName(chat.getParticipant());
            // m = new MessageData(user, message.getBody(), "font", "4");
            // model.receiveMessage(user.getFriendOf(), m);
            //                
            // System.out.println("This should be printed!!!!");
            // 
            // }
            return;
        }
    }

    /** This is another class call MessagepackFilter. */

    private class MessagePacketFilter implements PacketFilter {
        public boolean accept(Packet packet) {
            System.out.println(packet);
            return (packet instanceof Message);
        }
    }

    /** This is another class call MessagepacketListener. */

    private class MessagePacketListener implements PacketListener {

        public void processPacket(Packet packet) {
            Chatbot chatbot = null;

            /* packet is a new message, make chat if from new person */

            boolean chatExists = false;
            Message message = (Message) packet;
            String bareAddress = StringUtils
                    .parseBareAddress(message.getFrom());

            if (message.getType() == Message.Type.normal
                    || message.getType() == Message.Type.chat) {
                for (Chat c : chats) {
                    // System.out.println(c.getParticipant());
                    // System.out.println(bareAddress);
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
                    // tempID = chat.getThreadID();

                    /* Display first message bug FIX */
                    user = model.findUserByAccountName(chat.getParticipant());
                    m = new MessageData(user.getAccountName(), message
                            .getBody(), "font", "4");
                    try {
                        model
                                .receiveMessage(
                                        model.findAccountByFriend(user), m);
                    } catch (SQLException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                } else {
                    // tempID = "";
                    user = model.findUserByAccountName(chat.getParticipant());
                    m = new MessageData(user.getAccountName(), message
                            .getBody(), "font", "4");
                    try {
                        model
                                .receiveMessage(
                                        model.findAccountByFriend(user), m);
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
                    String response = chatbot.respond();
                    sendMessage(response, chat.getParticipant());

                    // temporary to display in the chat window
                    // ConversationData conversation =
                    // model.getActiveConversation();
                    // UserData fromUser =
                    // conversation.getAccount().getOwnUserData();
                    // MessageData msg = new MessageData(fromUser, response,
                    // chatPanel.getFontSelect().getSelectedItem().toString(),
                    // "4");
                    //                    
                    // model.sendMessage(conversation, msg);

                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                /* Else, the message listener handles it automatically */

            }

            System.out.println("------------------");
            System.out.println("In processPacket");
            // System.out.println(message.getFrom());
            System.out.println("Packet message = " + message.getBody());
            // System.out.println(message.getType());
            // System.out.println("Packet message ID = " + tempID);

            System.out.println("------------------");

            return;
        }
    }
}
