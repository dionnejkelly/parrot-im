/* MainController.java
 * 
 * Programmed By:
 *     Jihoon Choi
 *     
 * Change Log:
 *         
 * Known Issues:
 *     1. Needs to be more modular; functionality needs to be split off.
 *     2. Unclear how to handle multiple connections; are there multiple
 *        controller objects?
 *        
 * Copyright (C) 2009  Pirate Captains
 * 
 * License: GNU General Public License version 2.
 * Full license can be found in ParrotIM/LICENSE.txt.
 */

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
import controller.services.BadConnectionException;
import controller.services.GenericConnection;
import controller.services.GoogleTalkManager;

import model.Model;
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

    /**
     * The connection to the XMPP server.
     */
    private XMPPConnection connection;

    /**
     * Allows the ChatClient to store data for the GUI.
     */
    private Model model;

    /**
     * Holds the Chatbot data that is initially set to null.
     */
    private Chatbot chatbot;

    /**
     * This is the constructor of Xmpp.
     * 
     * @param model
     */
    public MainController(Model model) {
        this.model = model;
        this.connection = null;
        this.chatbot = new Chatbot();
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
    public AccountData login(ServerType server, String accountName,
            String password) throws BadConnectionException {
        AccountData account = null; // Default return value
        GenericConnection connection = null;

        // Create an AccountData from entered information
        account = new AccountData(server, accountName, password);

        // Determine which type of connection the account requires, and add it
        switch (account.getServer()) {
        case GOOGLE_TALK:
            connection = new GoogleTalkManager(this);
            break;

        case JABBER:
            // TODO Implement me!
            break;

        case TWITTER:
            // TODO Implement me!
            break;

        default:
            // Other servers
            break;
        }

        // While we implement....
        if (connection == null) {
            throw new BadConnectionException(); // ... until we implement
        }

        account.setConnection(connection);

        // If connected...
        // TODO connect this with GUI sign-in animation?
        // It not, we can just use account.setConnected() instead of model
        model.connectAccount(account);

        // Set up friends' user data
        this.populateBuddyList(account);

        return account;
    }

    /**
     * This method is used to login to XMPP through a profile .
     * 
     * @param profile
     * @throws XMPPException
     */

    public void loginProfile(String profile) throws BadConnectionException {
        ArrayList<AccountTempData> accounts = null;
        AccountData createdAccount = null;

        // Disconnect in case already connected
        this.disconnect();
        model.createCurrentProfile(profile);

        accounts = model.getAccountsForProfile(profile);
        for (AccountTempData a : accounts) {
            createdAccount = login(a.getServer(), a.getUserID(), a
                    .getPassword());
            model.addAccountToCurrentProfile(createdAccount);
        }

        return;
    }

    /**
     * Disconnects all accounts associated with the current profile from the
     * server, and sets the current profile to null.
     */
    public void disconnect() {
        if (model.currentProfileExists()) {
            for (AccountData a : model.getCurrentProfile().getAccountData()) {
                a.getConnection().disconnect();
            }
        }
        this.model.clearCurrentProfile();

        return;
    }

    /**
     * This method is used to add a friend to the friend list.
     * 
     * @param userID
     */

    public void addFriend(String userID) {
        ServerType server = null;
        Roster roster = connection.getRoster();
        String nickname = StringUtils.parseBareAddress(userID);
        System.out.println(userID + " 000");

        // Temporary filter to find jabber/Google servers
        if (StringUtils.parseServer(userID).equals("gmail.com")) {
            server = ServerType.GOOGLE_TALK;
        } else {
            server = ServerType.JABBER;
        }

        try {

            System.out.println(roster.getEntryCount());
            roster.createEntry(userID, nickname, null);
            model.addFriend(server, userID);
        } catch (XMPPException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        }

        return;
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
                    // Temp fix for the smack "name is null" error
                    // Now removes the friend from the database...

                    // this.addFriend(f.getUserID());
                } else { // is blocked, need to add not on server

                    // TODO, separate the strings better
                    if (StringUtils.parseServer(f.getUserID()).equals(
                            "gmail.com")) {
                        user = new GoogleTalkUserData(f.getUserID());
                    } else {
                        user = new JabberUserData(f.getUserID());
                    }

                    user.setBlocked(true);
                    model.addFriend(account, user);
                }
            }
        }
        return;
    }

    public void startConversation(UserData friend, boolean focus) {
        ConversationData conversation = null;

        conversation = model.startConversation(friend);
        if (focus || model.numberOfConversations() < 2) {
            model.setActiveConversation(conversation);
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
        
        // TODO, this next!
        Chat chat = null;
        boolean chatExists = false;
        ConversationData conversation = null;
        MessageData messageObject = null;
        String fromUser = null;
        String font = "Arial"; // temp values
        String size = "4";
        boolean bold = false;
        boolean italics = false;
        boolean underlined = false;
        String color = "#000000";

        conversation = model.findConversationByFriend(to);

        // TEMP FIX TO MAKE CONVERSATION
        if (conversation == null) {
            model.startConversation(model.findUserByAccountName(to));
            conversation = model.findConversationByFriend(to);
        }

        fromUser = conversation.getAccount().getAccountName();
        to = conversation.getUser().getAccountName();

        messageObject = new MessageData(fromUser, messageString, font, size,
                bold, italics, underlined, color);

        try {
            model.sendMessage(conversation, messageObject);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
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
    public void sendMessage(String messageString, String font, String size,
            boolean bold, boolean italics, boolean underlined, String color)
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

        messageObject = new MessageData(fromUser, messageString, font, size,
                bold, italics, underlined, color);

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

    public void updateStateAndStatus(UserData userToUpdate,
            GenericConnection connection) {
        String userID = userToUpdate.getAccountName();

        userToUpdate.setStatus(connection.retrieveStatus(userID));
        if (connection instanceof GoogleTalkManager) {
            userToUpdate.setState(((GoogleTalkManager) connection)
                    .retrieveState(userID));
        }

        model.forceNotify(UpdatedType.BUDDY);

        return;
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
            // Temp, make different than sfu
            serverName = "jabber.sfu.ca";
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

    // Response methods

    public void friendUpdated(GenericConnection connection, String userID) {
        AccountData account = null;
        UserData userToUpdate = null;

        account = model.findAccountByConnection(connection);
        if (account != null) {
            userToUpdate = account.findFriendByUserID(userID);
        }
        updateStateAndStatus(userToUpdate, connection);

        return;
    }

    public void messageReceived(String fromUserID, String toUserID,
            String message) {
        MessageData messageData = null;
        AccountData account = null;

        messageData = new MessageData(fromUserID, message, "font", "4", false,
                false, false, "#000000");
        account = model.findAccountByUserID(toUserID);

        model.receiveMessage(account, messageData);

        // Automatically add chatbot reply, if enabled
        if (model.getCurrentProfile().isChatbotEnabled()) {
            try {
                chatbot.get_input(message);
                String response = chatbot.respond();
                sendMessage(response, fromUserID);
            } catch (Exception e) {
                System.err.println("Error with chatbot: messageReceived()");
                e.printStackTrace();
            }
        }
        
        return;
    }
}
