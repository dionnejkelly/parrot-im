/* Model.java
 * 
 * Programmed By:
 *     Kevin Fahy
 *     Ahmad Sidiqi
 *     Jordan Fox
 *     Vera Lukman
 *     William Chen
 *     Jihoon Choi
 *     
 * Change Log:
 *     2009-June-3, KF
 *         Initial write.
 *     2009-June-8, AS
 *         Implemented database functionality inside class.
 *     2009-June-9, KF
 *         Made class implement Observable, so that the View could
 *         update when model variables are changed.
 *     2009-June-12, VL
 *         Fixed code in startConversation().
 *     2009-June-13, WC
 *         Transferred file over to new project, ParrotIM.  
 *     2009-June-17, KF/WC/JC
 *         Added addFriend() to add a friend to the friend list. 
 *     2009-June-18, KF/JC
 *         Added findFriendByAccountName(), removeFriend(), 
 *         changeFriendStatus(), blockFriend(), unblockFriend().
 *         
 *         
 * Known Issues:
 *     1. Currently has methods that are scheduled to be phased out.
 * 
 * Copyright (C) 2009  Pirate Captains
 * 
 * Full license can be found in ParrotIM/LICENSE.txt.
 */

package model;

import java.util.*;
import java.sql.*;

import model.dataType.AccountData;
import model.dataType.ConversationData;
import model.dataType.CurrentProfileData;
import model.dataType.GoogleTalkUserData;
import model.dataType.MessageData;
import model.dataType.ServerType;
import model.dataType.UpdatedType;
import model.dataType.UserData;

/**
 * The model stores all data and provides it for the view and controllers.
 */
public class Model extends Observable {

    private ArrayList<ConversationData> conversations;
    private ConversationData activeConversation;
    private CurrentProfileData currentProfile;
    private DatabaseFunctions db;
    public boolean chatWindowOpen;

    public Model() throws ClassNotFoundException, SQLException {
        currentProfile = new CurrentProfileData();
        conversations = new ArrayList<ConversationData>();

        /* Test code to create an entry in the friendList */
        db = new DatabaseFunctions(); // initialize database

    }

    /**
     * overriding notifyObservers in the parent class combining setchange and
     * noifyObservers into one method
     * 
     * @return void
     * @parm o object that sends to observer
     * @see notifyObservers
     */
    public void forceNotify(Object o) {
        this.setChanged();
        this.notifyObservers(o);
    }

    /**
     * overriding notifyObservers in the parent class combining setchange and
     * noifyObservers into one method
     * 
     * @return void
     */
    public void forceNotify() {
        this.setChanged();
        this.notifyObservers();
    }

    public Vector<String> getAccountList() throws ClassNotFoundException,
            SQLException {
        DatabaseFunctions db = new DatabaseFunctions();
        return db.getUserList();
    }

    public Vector<String> getBannedAccountList() throws ClassNotFoundException,
            SQLException {
        DatabaseFunctions db = new DatabaseFunctions();
        return db.getBannedUserList();

    }

    /* Phase this method out in favour of the next one */
    public Vector<String> getServerList() {
        return ServerType.getServerList();
    }

    public Vector<ServerType> getServerListv2() {
        Vector<ServerType> servers = new Vector<ServerType>();
        for (ServerType s : ServerType.values()) {
            servers.add(s);
        }
        return servers;
    }

    public String getPassword(String username) throws ClassNotFoundException,
            SQLException {
        Class.forName("org.sqlite.JDBC");
        Connection conn = DriverManager.getConnection("jdbc:sqlite:test.db");
        Statement stat = conn.createStatement();
        ResultSet rs = null;
        rs = stat.executeQuery("select * from people where email='" + username
                + "'");
        return rs.getString("password");

    }

    public int numberOfConversations() {
        return conversations.size();
    }

    public void setActiveConversation(UserData user) {
        for (ConversationData c : this.conversations) {
            if (c.getUser() == user) {
                this.activeConversation = c;
                break;
            }
        }

        notifyObservers(UpdatedType.CHAT);
        return;
    }

    public void setActiveConversation(ConversationData conversation) {
        this.activeConversation = conversation;

        notifyObservers(UpdatedType.CHAT);
        return;
    }

    public void removeActiveConversation() {
        this.conversations.remove(this.activeConversation);
        return;
    }

    public ConversationData getActiveConversation() {
        return this.activeConversation;
    }

    public void receiveMessage(AccountData account, MessageData message)
            throws SQLException, ClassNotFoundException {
        ConversationData modifiedConversation = null;
        UserData fromUser = message.getFromUser();

        DatabaseFunctions db = new DatabaseFunctions();
        db.addChat(fromUser.getAccountName(), account.getAccountName(), message
                .getMessage());
        db.printChats();

        for (ConversationData c : conversations) {
            if (c.getUser() == fromUser) {
                modifiedConversation = c;
                break;
            }
        }

        /*
         * Case 1: We found a matching conversation Case 2: No match found;
         * create conversation and add it to the list
         */
        if (modifiedConversation != null) {
            modifiedConversation.addMessage(message);
        } else {
            modifiedConversation = new ConversationData(account, fromUser);
            this.conversations.add(modifiedConversation);
            this.activeConversation = modifiedConversation;
            modifiedConversation.addMessage(message);

        }

        setChanged();
        notifyObservers(UpdatedType.CHAT);
        return;
    }

    public void sendMessage(ConversationData modifiedConversation,
            MessageData message) throws ClassNotFoundException, SQLException {
        modifiedConversation.addMessage(message);
        DatabaseFunctions db = new DatabaseFunctions();
        db.addChat(message.getFromUser().getAccountName(), modifiedConversation
                .getUser().getAccountName(), message.getMessage());
        setChanged();
        notifyObservers(UpdatedType.CHAT);
        return;
    }

    public ConversationData startConversation(UserData user) {
        // account = local user (eg. cmpt275testing@gmail.com)
        // user = buddy
        AccountData account = this.findAccountByFriend(user);
        ConversationData conversation = new ConversationData(account, user);
        System.out.println("account: " + account.getAccountName() + " user:"
                + user.toString());
        String user_address = user.toString();

        // check if the conversation exists
        boolean conversation_found = false;
        int conv;
        for (conv = 0; conv < conversations.size(); conv++) {
            if (conversations.get(conv).getUser().toString().compareTo(
                    user_address) == 0) {
                conversation_found = true;
                break; // if conversation found, exit loop
            }
        }

        if (!conversation_found) {// if conversation is not found, then add
            this.conversations.add(conversation);
        } else { // if found, replace conversation with the one found
            conversation = conversations.get(conv);
        }

        this.activeConversation = conversation;

        setChanged();
        notifyObservers(UpdatedType.CHAT);
        return conversation;
    }

    public ArrayList<ConversationData> getConversations() {
        return this.conversations;
    }

    /* Current Profile manipulation */

    public void createCurrentProfile(AccountData account, String profileName) {
        currentProfile = new CurrentProfileData(account, profileName);
        return;
    }

    public void addAccountToCurrentProfile(AccountData account) {
        currentProfile.addAccount(account);
        return;
    }

    public CurrentProfileData getCurrentProfile() {
        return currentProfile;
    }

    public boolean currentProfileExists() {
        return (currentProfile != null);
    }

    public void connectAccount(AccountData accountData) {
        accountData.setConnected(true);
        setChanged();
        notifyObservers();
        return;
    }

    public void disconnectAccount(AccountData accountData) {
        accountData.setConnected(false);
        setChanged();
        notifyObservers();
        return;
    }

    public void forceUpdate(UpdatedType updatedType) {
        setChanged();
        notifyObservers(updatedType);
        return;
    }

    public UserData findUserByAccountName(String accountName) {
        UserData found = null;
        ArrayList<UserData> friends = this.currentProfile.getAllFriends();
        for (UserData user : friends) {
            if (user.getAccountName().equals(accountName)) {
                found = user;
                break;
            }
        }
        return found;
    }

    /**
     * Adds a friend to the friend list. Takes a server type and a String
     * representing the account name as parameters. Searches the accounts for a
     * matching server, creates a new UserData, and adds this UserData to the
     * account.
     * 
     * @param server
     *            A constant representing the server type to search for in
     *            accounts. This parameter effectively specifies which account
     *            to add the friend to.
     * 
     * @param accountName
     *            A string with the account name of the user to add. This method
     *            is responsible to place this user into a UserData object.
     */
    public void addFriend(ServerType server, String accountName) {
        /*
         * In future, change ServerType to AccountData... should be passed into
         * the controller. This solution will not work if there are multiple
         * gmail accounts, for example.
         */

        AccountData account = null;
        UserData userToAdd = null;

        if (server == ServerType.GOOGLE_TALK) {
            account = currentProfile.getAccountFromServer(server);
            userToAdd = new GoogleTalkUserData(accountName);
            account.addFriend(userToAdd);
        }

        this.setChanged();
        this.notifyObservers(UpdatedType.BUDDY);

        return;
    }

    /**
     * Adds a friend to the friend list. Takes an AccountData and a String
     * representing the account name as parameters. Searches the accounts for a
     * matching server, creates a new UserData, and adds this UserData to the
     * account.
     * 
     * @param account
     *            The account to add the friend to.
     * 
     * @param accountName
     *            A string with the account name of the user to add. This method
     *            is responsible to place this user into a UserData object.
     */
    public void addFriend(AccountData account, String accountName) {
        UserData userToAdd = null;

        if (account.getServer() == ServerType.GOOGLE_TALK) {
            userToAdd = new GoogleTalkUserData(accountName);
            account.addFriend(userToAdd);
        }

        this.setChanged();
        this.notifyObservers(UpdatedType.BUDDY);

        return;
    }

    /**
     * Removes a friend in one of the accounts based on the UserData
     * representation of the friend. Removes only the first friend that is
     * found.
     * 
     * It is advisable to run a findFriendByAccountName() first to obtain the
     * UserData representation, and then call this method to remove the friend.
     * 
     * @param exFriend
     *            The friend to remove.
     * @return true if removed, false otherwise.
     */
    public boolean removeFriend(UserData exFriend) {
        boolean success = false;
        System.out.println("------------------ Went through here");
        /* Can throw NullPointerException if exFriend is null */
        try {
            /* CurrentProfile automatically finds the account for us */
            currentProfile.removeFriend(exFriend);
            success = true;
        } catch (NullPointerException e) {
            success = false; // Should already be false, but, hey, whatever
        }

        super.setChanged();
        super.notifyObservers(UpdatedType.BUDDY);

        return success;
    }

    /**
     * Searches for a friend in all the accounts of the current profile by
     * account name.
     * 
     * @param accountName
     *            The String representing the account name.
     * @return A UserData with the friend info. Can be null if the friend is not
     *         found.
     */
    public UserData findFriendByAccountName(String accountName) {
        UserData foundFriend = null;

        /* Find the UserData representation of the friend */
        for (UserData u : currentProfile.getAllFriends()) {
            if (u.getAccountName().equalsIgnoreCase(accountName)) {
                foundFriend = u;
                break;
            }
        }

        /* Possible that foundFriend is still null--not found */
        return foundFriend;
    }

    /**
     * Searches for an account that matches with the passed in
     * UserData representation of a friend.
     * 
     * @param userToBeFound
     * @return
     */
    public AccountData findAccountByFriend(UserData userToBeFound) {
        AccountData foundAccount = null;
        
        /* Find the AccountData by searching through all accounts */
        for (AccountData account : currentProfile.getAccountData()) {
            for (UserData user : account.getFriends()) {
                if (user == userToBeFound) {
                    foundAccount = account;
                    break;  
                }
            }
            if (foundAccount != null) {
                break;
            }
        }
        
        return foundAccount;
    }

    /**
     * Updates a friend's status, and notifies the BUDDY portion of the view on
     * the change. Requires a UserData object to be passed in. In practice, this
     * model method is not required to change the status, but it is important to
     * do so as to update the view.
     * 
     * @param friend
     *            The UserData representation of the friend.
     * @param status
     *            The new status message to display.
     */
    public void changeFriendStatus(UserData friend, String status) {
        /*
         * It's possible that not all protocols allow us to update statuses.
         * Also, Twitter is "special" in that it can only be 140 characters in
         * length. We need to implement this checking later.
         */
        friend.setStatus(status);

        super.setChanged();
        super.notifyObservers(UpdatedType.BUDDY);

        return;
    }

    /**
     * Blocks a user and updates the view.
     * 
     * @param friend
     *            UserData representation of the friend to block.
     */
    public void blockFriend(UserData friend) {
        friend.setBlocked(true); // TODO: Make exception for if null

        super.setChanged();
        super.notifyObservers(UpdatedType.BUDDY);

        return;
    }

    /**
     * Unblocks a user and updates the view.
     * 
     * @param friend
     *            UserData representation of the friend to unblock.
     */
    public void unblockFriend(UserData friend) {
        friend.setBlocked(false); // TODO: Make exception for if null

        super.setChanged();
        super.notifyObservers(UpdatedType.BUDDY);

        return;
    }
}
