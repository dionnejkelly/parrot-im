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

import java.util.*;

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
import model.enumerations.UserStateType;

/**
 * Handles all connections involving XMPP protocol.
 */
public class MainController {

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
        // Updates status for all accounts
        // TODO may not wanted for twitter?
        for (AccountData a : model.getCurrentProfile().getAccountData()) {
            a.getConnection().changeStatus(
                    model.getCurrentProfile().getState(), status);
        }
        model.getCurrentProfile().setStatus(status);

        return;
    }

    /**
     * This method actually set presence of the user.
     * 
     * @param presenceStatus
     * @throws InterruptedException
     */
    public void setPresence(String stateString) {
        // Iterates over all accounts and sets the state
        // TODO may a user wants to go "invisible" in just one account?
        // TODO handle input from GUI, maybe enum type input?
        UserStateType state = null;

        if (stateString.equalsIgnoreCase("Online")) {
            state = UserStateType.ONLINE;
        } else if (stateString.equalsIgnoreCase("Away")) {
            state = UserStateType.AWAY;
        } else if (stateString.equalsIgnoreCase("Busy")) {
            state = UserStateType.BUSY;
        } else {
            // TODO implement me
            state = UserStateType.ONLINE;
        }

        for (AccountData a : model.getCurrentProfile().getAccountData()) {
            a.getConnection().changeStatus(state,
                    model.getCurrentProfile().getStatus());
        }
        model.getCurrentProfile().setState(state);

        return;
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
        account = new AccountData(accountName, password);

        // Determine which type of connection the account requires, and add it
        switch (server) {
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

        connection.login(accountName, password);
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
            createdAccount =
                    login(a.getServer(), a.getUserID(), a.getPassword());
            model.addAccountToCurrentProfile(createdAccount);
        }

        return;
    }

    public void loginAsGuest(ServerType server, String userID, String password)
            throws BadConnectionException {
        AccountData createdAccount = null;

        // Disconnect in case already connected
        this.disconnect();
        model.createCurrentProfile("Guest Profile");

        createdAccount = login(server, userID, password);
        model.addAccountToCurrentProfile(createdAccount);

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
        // TODO create an account selection GUI
        AccountData account = null; // Should be passed in!!
        GenericConnection connection = null;

        try {
            // connection should be found from account!!
            account = model.getCurrentProfile().getAccountData().get(0);
            connection = account.getConnection();
            connection.addFriend(userID);

            // TODO make a more accurate Model.addFriend
            model.addFriend(account.getServer(), userID);
        } catch (BadConnectionException e) {
            // TODO throw another exception to prompt for re-entry
            e.printStackTrace();
        }

        return;
    }

    /**
     * This method is used to remove a friend to the friend list.
     * 
     * @param userID
     */
    public void removeFriend(UserData friendToRemove) {
        boolean removed = false;
        GenericConnection connection = null;

        connection = model.findAccountByFriend(friendToRemove).getConnection();

        try {
            removed = connection.removeFriend(friendToRemove.getAccountName());
        } catch (BadConnectionException e) {
            // TODO Make the GUI know the friend doesn't exist?
            e.printStackTrace();
        }
        if (removed) {
            model.removeFriend(friendToRemove);
        }

        return;
    }

    /**
     * This method is used to block a friend to the friend list.
     * 
     * @param userID
     */
    public void blockFriend(UserData friendToBlock) {
        // TODO convert this to use privacy list for XMPP
        boolean removed = false;
        GenericConnection connection = null;

        connection = model.findAccountByFriend(friendToBlock).getConnection();

        try {
            removed = connection.removeFriend(friendToBlock.getAccountName());
        } catch (BadConnectionException e) {
            // TODO Make the GUI know the friend doesn't exist?
            e.printStackTrace();
        }
        if (removed) {
            model.blockFriend(friendToBlock);
        }

        return;
    }

    /**
     * This method is used to unblock a friend to the friend list.
     * 
     * @param userID
     */

    public void unblockFriend(UserData friendToUnblock) {
        // TODO convert this to use privacy list for XMPP
        AccountData account = null; // Should be passed in!!
        GenericConnection connection = null;

        try {
            account = model.findAccountByFriend(friendToUnblock);
            connection = account.getConnection();
            connection.addFriend(friendToUnblock.getAccountName());

            model.unblockFriend(friendToUnblock);
        } catch (BadConnectionException e) {
            System.err.println("Error in unblocking friend.");
            e.printStackTrace();
        }

        return;
    }

    /**
     * This method is used to populate the buddy list of the user.
     * 
     * @param account
     */
    public void populateBuddyList(AccountData account) {
        Vector<FriendTempData> savedFriends = null;
        UserData user = null;
        String userID = null;
        String nickname = null;
        GenericConnection connection = null;

        // Get friends from the database to cross reference against
        // those found in Roster.
        savedFriends = model.getSavedFriends(account.getAccountName());

        connection = account.getConnection();
        for (FriendTempData f : connection.retrieveFriendList()) {
            // Decide which type of user to use
            userID = f.getUserID();
            nickname = f.getNickname();
            if (nickname == null || nickname.equalsIgnoreCase(userID)) {
                nickname = StringUtils.parseName(userID);
            }

            if (account.getServer() == ServerType.GOOGLE_TALK) {
                user = new GoogleTalkUserData(userID, nickname, f.getStatus());
                user.setState(f.getState());
            } else if (account.getServer() == ServerType.JABBER) {
                user = new JabberUserData(userID, nickname, f.getStatus());
                user.setState(f.getState());
            } else { // some other user
                // TODO implement me!
            }

            /* Search the savedFriends to find if was saved locally */
            for (FriendTempData databaseFriend : savedFriends) {
                if (userID.equalsIgnoreCase(databaseFriend.getUserID())) {
                    user.setBlocked(databaseFriend.isBlocked());
                    savedFriends.remove(databaseFriend);
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

                // this.addFriend(f.getUserID());
            } else { // is blocked, need to add not on server

                // TODO, separate the strings better
                if (StringUtils.parseServer(f.getUserID()).equals("gmail.com")) {
                    user = new GoogleTalkUserData(f.getUserID());
                } else {
                    user = new JabberUserData(f.getUserID());
                }

                user.setBlocked(true);
                model.addFriend(account, user);
            }
        }

        model.forceNotify(UpdatedType.BUDDY);

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
            throws BadConnectionException {
        // TODO we may want a conversation object to be passed in
        ConversationData conversation = null;
        MessageData messageObject = null;
        String fromUser = null;
        String font = "Arial"; // temp values
        String size = "4";
        boolean bold = false;
        boolean italics = false;
        boolean underlined = false;
        String color = "#000000";
        GenericConnection connection = null;

        conversation = model.findConversationByFriend(to);

        if (conversation == null) {
            model.startConversation(model.findUserByAccountName(to));
            conversation = model.findConversationByFriend(to);
        }
        connection = conversation.getAccount().getConnection();

        fromUser = conversation.getAccount().getAccountName();
        to = conversation.getUser().getAccountName();

        messageObject =
                new MessageData(fromUser, messageString, font, size, bold,
                        italics, underlined, color);

        connection.sendMessage(to, messageString);
        model.sendMessage(conversation, messageObject);

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
            throws BadConnectionException {
        String to = null;
        MessageData messageObject = null;
        ConversationData conversation = null;
        String fromUser = null;
        GenericConnection connection = null;

        // Default to sending to the active user
        conversation = model.getActiveConversation();
        connection = conversation.getAccount().getConnection();

        fromUser = conversation.getAccount().getAccountName();
        to = conversation.getUser().getAccountName();

        messageObject =
                new MessageData(fromUser, messageString, font, size, bold,
                        italics, underlined, color);

        connection.sendMessage(to, messageString);
        model.sendMessage(conversation, messageObject);

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

        messageData =
                new MessageData(fromUserID, message, "font", "4", false, false,
                        false, "#000000");
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
