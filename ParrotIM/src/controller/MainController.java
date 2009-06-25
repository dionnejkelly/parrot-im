package controller;

import java.sql.SQLException;
import java.util.*;

import javax.net.ssl.SSLSocketFactory;

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
import model.dataType.JabberUserData;
import model.dataType.MessageData;
import model.dataType.UserData;
import model.dataType.tempData.AccountTempData;
import model.dataType.tempData.FriendTempData;
import model.enumerations.ServerType;
import model.enumerations.UpdatedType;

/**
 * Handles all connections involving XMPP protocol.
 */
public class MainController {

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

    /** Holds an array list of chats. */
    private ArrayList<Chat> chats;

    /** Holds the user data that is initially set to null. */
    private UserData user = null;

    /** Holds the message data that is initially set to null. */
    private MessageData m = null;

    /** Holds the chat data that is initially set to null. */
    private Chat chat = null;

    /**
     * This is the constructor of Xmpp.
     * 
     * @param model
     */
    public MainController(Model model) {
        this.model = model;
        this.roster = null;
        this.chatManager = null;
        this.chats = new ArrayList<Chat>();

    }

    /**
     * These are setter and getter of Xmpp class.
     * 
     */

    /**
     * This method is using to set the status of the user.
     * 
     * @param status
     */

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

    /**
     * This method actually set presence of the user.
     * 
     * @param presenceStatus
     * @throws InterruptedException
     */
    public void setPresence(String presenceStatus) throws InterruptedException {
        Presence presence = new Presence(Presence.Type.available);
        presence.setStatus(presenceStatus);
        connection.sendPacket(presence);
    }

    /**
     * This method is using to get the Roster of someone.
     * 
     * @returns Roster
     */
    public Roster getRoster() {
        return this.roster;
    }

    /**
     * This method is using to get the presence of the user.
     * 
     * @param userID
     * @returns String
     */
    public String getUserPresence(String userID) {
        String status = "offline";
        if (connection != null && connection.getRoster() != null) {
            Presence presence = connection.getRoster().getPresence(userID);
            if (presence.isAvailable()) {
                if (presence.isAvailable()) {

                    if (presence.getMode() == Presence.Mode.away) {
                        status = presence.getStatus();
                        if (status != null) {
                            status = "Away";
                        }
                    } else if (presence.getMode() == Presence.Mode.dnd) {
                        status = presence.getStatus();
                        if (status != null) {
                            status = "Busy";
                        }
                    } else { // if (presence.getMode() != Presence.Mode.dnd) {
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

    /*
     * These are other utility methods of Xmpp class.
     */

    /**
     * This is the helper method using to determine if we connect to the sever
     * successfully.
     * 
     * @return boolean
     */
    public boolean isConnected() {
        return connection.isConnected();
    }

    /**
     * Attempts to log a user into the server based on the given account
     * information. If the current profile already exists, this account
     * information is added to it.
     * 
     * @param server
     * @param accountName
     * @param password
     * @throws XMPPException
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
            // Test code, please replace with jabber server selection
            config = new ConnectionConfiguration("jabber.sfu.ca", 5223,
                    "jabber.sfu.ca");

            // Enable SSL to let us connect to jabber.sfu.ca
            config.setSocketFactory(SSLSocketFactory.getDefault());
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

        // Handle the current profile
        if (model.currentProfileExists()) {
            model.addAccountToCurrentProfile(account);
        } else { // current profile does not exist
            model.createCurrentProfile(account, "Guest");
        }

        /* Set up friends' user data */
        this.populateBuddyList(account);

        return;
    }

    /**
     * This method is used to login to XMPP through a profile .
     * 
     * @param profile
     * @throws XMPPException
     */

    public void loginProfile(String profile) throws XMPPException {
        ArrayList<AccountTempData> accounts = null;

        accounts = model.getAccountsForProfile(profile);

        // May not work for multiple accounts yet
        for (AccountTempData a : accounts) {
            login(a.getServer(), a.getUserID(), a.getPassword());
        }

        model.getCurrentProfile().setProfileName(profile);

        return;
    }

    /**
     * This method is used to disconnect the XMPP connection.
     * 
     * @throws XMPPException
     */

    public void disconnect() throws XMPPException {
        for (AccountData a : model.getCurrentProfile().getAccountData()) {
            if (a.getServer() == ServerType.GOOGLE_TALK
                    || a.getServer() == ServerType.JABBER) {
                this.connection.disconnect(); // Change to have connection
                // stored elsewhere.

                this.model.clearCurrentProfile();
            }
        }

    }

    /**
     * This method is used to add a friend to the friend list.
     * 
     * @param userID
     */

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

    /**
     * This method is used to remove a friend to the friend list.
     * 
     * @param userID
     */
    public void removeFriend(String userID) {
        UserData user = null;
        Roster roster = connection.getRoster();
        Collection<RosterEntry> entries = roster.getEntries();
        Iterator<RosterEntry> i = entries.iterator();
        while (i.hasNext()) {
            RosterEntry nextEntry = (i.next());
            // remove entries
            if (nextEntry.getUser().equals(userID))
                try {
                    roster.removeEntry(nextEntry);
                    user = model.findUserByAccountName(userID);
                    model.removeFriend(user);
                } catch (XMPPException e) {
                    e.printStackTrace();
                }
        }
    }

    /**
     * This method is used to block a friend to the friend list.
     * 
     * @param userID
     */

    public void blockFriend(String userID) {
        UserData user = null;
        Roster roster = connection.getRoster();

        for (RosterEntry r : roster.getEntries()) {
            if (r.getUser().equals(userID)) {
                try {
                    roster.removeEntry(r);
                    user = model.findUserByAccountName(userID);
                    model.blockFriend(user);
                } catch (XMPPException e) {
                    e.printStackTrace();
                }
            }
        }

        return;
    }

    /**
     * This method is used to unblock a friend to the friend list.
     * 
     * @param userID
     */

    public void unblockFriend(String userID) {
        UserData user = null;
        Roster roster = connection.getRoster();
        String nickname = StringUtils.parseBareAddress(userID);

        try {
            roster.createEntry(userID, nickname, null);
            user = model.findUserByAccountName(userID);
            model.unblockFriend(user);
        } catch (XMPPException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return;
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

    /**
     * This method is used to get the buddy list of the user.
     * 
     * @return ArrayList<String>
     */
    public ArrayList<String> getBuddyList() {
        ArrayList<String> buddies = new ArrayList<String>();

        if (connection != null && connection.getRoster() != null) {
            Roster roster = connection.getRoster();
            Collection<RosterEntry> entries = roster.getEntries();

            for (RosterEntry r : entries) {
                buddies.add(r.getUser());
            }
        }
        Collections.sort(buddies);
        return buddies;
    }

    /**
     * This method is used to populate the buddy list of the user.
     * 
     * @param account
     */

    public void populateBuddyList(AccountData account) {
        UserData user = null;
        String accountName = null;
        String nickname = null;
        String status = null;

        if (connection != null && connection.getRoster() != null) {
            Vector<FriendTempData> savedFriends = null;
            Roster roster = connection.getRoster();
            Collection<RosterEntry> entries = roster.getEntries();

            // Get friends from the database to cross reference against
            // those found in Roster.
            savedFriends = model.getSavedFriends(account.getAccountName());

            for (RosterEntry r : entries) {
                // Decide which type of user to use
                accountName = r.getUser();
                nickname = r.getName();
                if (nickname == null || nickname.equalsIgnoreCase(accountName)) {
                    nickname = StringUtils.parseName(accountName);
                    r.setName(nickname);
                }

                if (account.getServer() == ServerType.GOOGLE_TALK) {
                    user = new GoogleTalkUserData(accountName, nickname, status);
                } else if (account.getServer() == ServerType.JABBER) {
                    user = new JabberUserData(accountName, nickname, status);
                } else { // some other user
                    // TODO implement me!
                }
                updateStateAndStatus(user, accountName);

                /* Search the savedFriends to find if was saved locally */
                for (FriendTempData f : savedFriends) {
                    if (accountName.equalsIgnoreCase(f.getUserID())) {
                        user.setBlocked(f.isBlocked());
                        savedFriends.remove(f);
                        break;
                    }
                }

                model.addFriend(account, user);
            }

            // Check if there are still saved friends to be added
            // and adds the roster friend if not blocked.
            for (FriendTempData f : savedFriends) {
                if (!f.isBlocked()) {
                    this.addFriend(f.getUserID());
                } else { // is blocked, need to add not on server
                    user = new GoogleTalkUserData(f.getUserID());
                    user.setBlocked(true);
                    model.addFriend(account, user);
                }
            }
        }
        return;
    }

    /**
     * This method is using to send the message to friend. Explicitly sets a
     * receiver. If the receiver is unknown, then use the overloaded
     * sendMessage() which automatically sends the reply to the open
     * conversation.
     * 
     * @param messageString
     * @param to
     * @throws XMPPException
     * 
     */
    public void sendMessage(String messageString, String to)
            throws XMPPException {
        Chat chat = null;
        boolean chatExists = false;
        ConversationData conversation = null;
        MessageData messageObject = null;
        String fromUser = null;
        String font = "Arial"; // temp values
        String size = "4";

        conversation = model.findConversationByFriend(to);

        // TEMP FIX TO MAKE CONVERSATION
        if (conversation == null) {
            model.startConversation(model.findUserByAccountName(to));
            conversation = model.findConversationByFriend(to);
        }

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
     * @param messageString
     * @param font
     * @param size
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

    /**
     * Changes a conversation to another user.
     * 
     * @param accountName
     */

    public void changeConversation(String accountName) {
        UserData user = null;

        user = model.findUserByAccountName(accountName);
        if (user != null) {
            model.setActiveConversation(user);
        }

        return;
    }

    /**
     * Updates the Parrot IM user's state and status.
     * 
     * @param userToUpdate
     * @param bareAddress
     */

    public void updateStateAndStatus(UserData userToUpdate, String bareAddress) {
        Presence truePresence = null;

        truePresence = roster.getPresence(bareAddress);

        /* Update state and status */
        if (truePresence.getStatus() != null) {
            userToUpdate.setStatus(truePresence.getStatus());
        } else {
            userToUpdate.setStatus("");
        }

        /* WEIRD BUG FIX BELOW */
        if (truePresence.getMode() == Presence.Mode.dnd
                || truePresence.getMode() == Presence.Mode.away
                || truePresence.getMode() == Presence.Mode.xa) {
            userToUpdate.setState(roster.getPresence(bareAddress).getMode()
                    .toString());
        } else if (truePresence.isAvailable()) {
            userToUpdate.setState("Available");
        } else { // offline
            userToUpdate.setState("Offline");
        }

        model.forceNotify(UpdatedType.BUDDY);

        return;
    }

    /* This is another class called BuddyListener. */

    /**
     * Changes to the roster, that is, changes to friends' statuses or
     * availability, are handled by this class.
     */
    private class BuddyListener implements RosterListener {

        /**
         * Displays which user is added to the entry.
         * 
         * @param addresses
         */

        public void entriesAdded(Collection<String> addresses) {
            // Fix me!
            System.out.println(addresses + " from entriesAdded");
            return;
        }

        /**
         * Displays which user is updated in the entry.
         * 
         * @param addresses
         */

        public void entriesUpdated(Collection<String> addresses) {
            // Fix me!
            System.out.println(addresses + " from entriesUpdated");
            return;
        }

        /**
         * Displays which user is deleted in the entry.
         * 
         * @param addresses
         */

        public void entriesDeleted(Collection<String> addresses) {
            // Fix me!
            System.out.println(addresses + " from entriesDeleted");
            return;
        }

        /**
         * Updates user's changed presence
         * 
         * @param presence
         */

        public void presenceChanged(Presence presence) {
            UserData userToUpdate = null;

            String bareAddress = StringUtils.parseBareAddress(presence
                    .getFrom());
            userToUpdate = model.findUserByAccountName(bareAddress);
            updateStateAndStatus(userToUpdate, bareAddress);
            return;
        }
    }

    /** This is another class call ChatListener. */

    /**
     * Controls program flow upon new chats being created.
     */
    private class ChatListener implements ChatManagerListener {

        /**
         * Adds net chat to the Chat Array List.
         * 
         * @param chat
         * @param createdLocally
         */

        public void chatCreated(Chat chat, boolean createdLocally) {
            System.out.println("CREATED CHAT!!");
            /* Set up listener for the new Chat */
            chat.addMessageListener(new MsgListener());

            chats.add(chat);

            return;
        }
    }

    /** This is another class call MsgListener. */

    /**
     * Controls receiving message upon new arrival.
     */

    private class MsgListener implements MessageListener {

        /**
         * Processes the incoming message upon new arrival.
         * 
         * @param chat
         * @param message
         */

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

        /**
         * Accepts the incoming packet upon new arrival.
         * 
         * @param packet
         */

        public boolean accept(Packet packet) {
            // System.out.println(packet + "It's YOU, I KNOW IT!");

            return (packet instanceof Message);
        }
    }

    /** This is another class call MessagepacketListener. */

    private class MessagePacketListener implements PacketListener {

        /**
         * Processes the incoming packet upon new arrival.
         * 
         * @param packet
         */

        public void processPacket(Packet packet) {
            Chatbot chatbot = null;

            /* packet is a new message, make chat if from new person */

            boolean chatExists = false;
            Message message = (Message) packet;
            String bareAddress = StringUtils
                    .parseBareAddress(message.getFrom());

            if ((message.getType() == Message.Type.normal || message.getType() == Message.Type.chat)
                    && message.getBody() != null) {
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
                }
                // tempID = "";
                user = model.findUserByAccountName(chat.getParticipant());
                m = new MessageData(user.getAccountName(), message.getBody(),
                        "font", "4");

                model.receiveMessage(model.findAccountByFriend(user), m);

                if (model.getCurrentProfile().isChatbotEnabled()) {
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
                }
            }

            return;
        }
    }

    /*
     * SECTION: Profile manipulation
     */

    /**
     * Adds profile to the database.
     * 
     * @param name
     * @param password
     * @param defaultProfile
     */

    public void addProfile(String name, String password, boolean defaultProfile) {
        this.model.addProfile(name, password, defaultProfile);

        return;
    }

    /**
     * Removes profile from the database.
     * 
     * @param name
     */

    public void removeProfile(String name) {
        this.model.removeProfile(name);

        return;
    }

    /**
     * Adds account to the database.
     * 
     * @param profile
     * @param server
     * @param account
     * @param password
     */

    public void addAccount(String profile, ServerType server, String account,
            String password) {
        String serverName = null;

        if (server == ServerType.GOOGLE_TALK) {
            serverName = "talk.google.com";
        } else {
            // Fill in data for other servers.
            // If jabber, we need to get the server name, also.
            // May need to get SSL info if we can't auto guess it.
        }

        this.model.addAccount(profile, serverName, account, password);
    }

    /**
     * Adds account to the database.
     * 
     * @param profile
     * @param account
     */

    public void removeAccount(String profile, String account) {
        this.model.removeAccount(profile, account);

        return;
    }

    /**
     * Toggles through Chatbot.
     */

    public void toggleChatbot() {
        this.model.getCurrentProfile().setChatbotEnabled(
                this.model.getCurrentProfile().isChatbotEnabled() ? false
                        : true);

        return;
    }

}
