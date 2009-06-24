/* Model.java
 * 
 * Programmed By:
 *     Kevin Fahy
 *     Ahmad Sidiqi
 *     Jordan Fox
 *     Vera Lukman
 *     William Chen
 *     Jihoon Choi
 *     Aaron Siu (AAS)
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
 *     2009-June-19, VL
 *     	   Added getBuddyLogList(), getBuddyDateList(), getLogMessage()
 *         Those functions are linked with DatabaseFunctions.java
 *         These functions are used for chat log window
 *     2009-June-20, AAS
 *         Added getProfileList(), getProfilesUserList()
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
import model.dataType.tempData.AccountTempData;
import model.dataType.tempData.ChatLogMessageTempData;
import model.dataType.tempData.FriendTempData;

/**
 * The model stores all data and provides it for the view and controllers.
 */
public class Model extends Observable {

    private ArrayList<ConversationData> conversations;
    private ConversationData activeConversation;
    private CurrentProfileData currentProfile;

    public boolean chatWindowOpen;

    public Model() throws ClassNotFoundException, SQLException {
        currentProfile = new CurrentProfileData();
        conversations = new ArrayList<ConversationData>();
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

    public ArrayList<AccountTempData> getAccountsForProfile(String profile) {
        ArrayList<AccountTempData> accounts = null;
        DatabaseFunctions db = null;

        try {
            db = new DatabaseFunctions();
            accounts = db.getAccountList(profile);
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return accounts;
    }

    public Vector<String> getProfileList() throws ClassNotFoundException,
            SQLException {
        DatabaseFunctions db = new DatabaseFunctions();
        return db.getProfileList();
    }

    public Vector<String> getProfilesUserList(String name)
            throws ClassNotFoundException, SQLException {
        DatabaseFunctions db = new DatabaseFunctions();
        return db.getProfilesUserList(name);
    }

    public Vector<String> getBannedAccountList() throws ClassNotFoundException,
            SQLException {
        DatabaseFunctions db = new DatabaseFunctions();
        return db.getBannedUserList();

    }

    public Vector<ServerType> getServerList() {
        Vector<ServerType> servers = new Vector<ServerType>();
        for (ServerType s : ServerType.values()) {
            servers.add(s);
        }
        return servers;
    }

    public String getPassword(String username) throws ClassNotFoundException,
            SQLException {
        DatabaseFunctions db = new DatabaseFunctions();
        return db.getAccountPassword(username);
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

        super.setChanged();
        super.notifyObservers(UpdatedType.CHAT);

        return;
    }

    public void setActiveConversation(ConversationData conversation) {
        this.activeConversation = conversation;

        super.setChanged();
        super.notifyObservers(UpdatedType.CHAT);

        return;
    }

    public void removeActiveConversation() {
        this.conversations.remove(this.activeConversation);
        return;
    }

    public ConversationData getActiveConversation() {
        return this.activeConversation;
    }

    /**
     * Searches the current list of conversations for a conversation with the
     * specified user. Returns the ConversationData object if found; null
     * otherwise.
     * 
     * @param friend
     *            A string with the friend's account name.
     * @return A ConversationData Object.
     */
    public ConversationData findConversationByFriend(String friend) {
        ConversationData conversation = null;

        for (ConversationData c : this.conversations) {
            if (c.getUser().getAccountName().equals(friend)) {
                conversation = c;
                break;
            }
        }

        return conversation;
    }

    public void receiveMessage(AccountData account, MessageData message)
            throws SQLException, ClassNotFoundException {
        UserData user = null;

        ConversationData modifiedConversation = null;
        String fromUser = message.getFromUser();

        DatabaseFunctions db = new DatabaseFunctions();
        db.addChat(currentProfile.getProfileName(), fromUser, account
                .getAccountName(), message.getMessage());

        user = this.findUserByAccountName(fromUser);

        for (ConversationData c : conversations) {
            if (c.getUser() == user) {
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
            modifiedConversation = new ConversationData(account, user);
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
        db.addChat(currentProfile.getProfileName(), message.getFromUser(),
                modifiedConversation.getUser().getAccountName(), message
                        .getMessage());
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

    /*
     * SECTION: Account manipulation - Remove account - Add account
     */

    public void addAccount(String profile, String server, String accountName,
            String password) {
        DatabaseFunctions db;
        try {
            db = new DatabaseFunctions();
            db.addUsers(profile, server, accountName, password);
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        super.setChanged();
        super.notifyObservers(UpdatedType.PROFILE);

        return;
    }

    public void removeAccount(String profile, String account) {
        DatabaseFunctions db;
        try {
            db = new DatabaseFunctions();
            db.removeAccountFromProfile(profile, account);
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        super.setChanged();
        super.notifyObservers(UpdatedType.PROFILE);

        return;
    }

    /* Current Profile manipulation */
    public void addProfile(String name, String pwd, boolean defaultProfile) {
        DatabaseFunctions db;
        try {
            db = new DatabaseFunctions();
            db.addProfiles(name, pwd, defaultProfile);
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        super.setChanged();
        super.notifyObservers(UpdatedType.PROFILE);

        return;
    }

    public void removeProfile(String name) {
        DatabaseFunctions db;
        try {
            db = new DatabaseFunctions();
            db.removeProfile(name);
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        super.setChanged();
        super.notifyObservers(UpdatedType.PROFILE);

        return;
    }

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

    /**
     * Searches for a friend in all the accounts of the current profile by
     * account name.
     * 
     * @param accountName
     *            The String representing the account name.
     * @return A UserData with the friend info. Can be null if the friend is not
     *         found.
     */
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
        FriendTempData friend = null;
        DatabaseFunctions db = null;
        AccountData account = null;
        UserData userToAdd = null;

        if (server == ServerType.GOOGLE_TALK) {
            account = currentProfile.getAccountFromServer(server);
            userToAdd = new GoogleTalkUserData(accountName);
            account.addFriend(userToAdd);
        }

        // Database manipulation
        try {
            db = new DatabaseFunctions();
            if (!db.checkFriendExists(account.getAccountName(), accountName)) {
                friend = new FriendTempData(accountName, false);
                db = new DatabaseFunctions();
                db.addFriend(account.getAccountName(), friend);
            }
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        this.setChanged();
        this.notifyObservers(UpdatedType.BUDDY);

        return;
    }

    /**
     * Overloaded version of addFriend. Takes a UserData instead of a String
     * representing a friend.
     * 
     * @param account
     *            The account to add the friend to.
     * 
     * @param userToAdd
     *            The new UserData friend to add to the account.
     */
    public void addFriend(AccountData account, UserData userToAdd) {
        FriendTempData friend = null;
        DatabaseFunctions db = null;

        account.addFriend(userToAdd);

        // Database manipulation
        try {
            db = new DatabaseFunctions();
            if (!db.checkFriendExists(account.getAccountName(), userToAdd
                    .getAccountName())) {
                friend = new FriendTempData(userToAdd.getAccountName(), false);
                db = new DatabaseFunctions();
                db.addFriend(account.getAccountName(), friend);
            }
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
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
        AccountData account = null;
        String friendName = null;
        DatabaseFunctions db = null;
        boolean success = false;

        // Database manipulation
        try {
            db = new DatabaseFunctions();
            account = this.findAccountByFriend(exFriend);
            friendName = exFriend.getAccountName();
            if (db.checkFriendExists(account.getAccountName(), friendName)) {
                db = new DatabaseFunctions();
                db.removeFriend(account.getAccountName(), friendName);
            }
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

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
     * Searches for an account that matches with the passed in UserData
     * representation of a friend.
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
        DatabaseFunctions db = null;
        friend.setBlocked(true); // TODO: Make exception for if null

        try {
            db = new DatabaseFunctions();
            db.changeBlocked(friend.getAccountName(), true);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

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
        DatabaseFunctions db = null;
        friend.setBlocked(false); // TODO: Make exception for if null

        try {
            db = new DatabaseFunctions();
            db.changeBlocked(friend.getAccountName(), false);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        super.setChanged();
        super.notifyObservers(UpdatedType.BUDDY);

        return;
    }

    public Vector<UserData> getBannedUserList() {
        Vector<UserData> blockedFriends = new Vector<UserData>();

        for (AccountData account : this.currentProfile.getAccountData()) {
            for (UserData user : account.getFriends()) {
                if (user.isBlocked()) {
                    blockedFriends.add(user);
                }
            }
        }
        return blockedFriends;

    }

    public ArrayList<UserData> getOrderedFriendList() {
        ArrayList<UserData> unsortedFriends = new ArrayList<UserData>();
        ArrayList<UserData> friends = new ArrayList<UserData>();
        UserData candidate = null;

        for (AccountData account : this.currentProfile.getAccountData()) {
            for (UserData user : account.getFriends()) {
                unsortedFriends.add(user);
            }
        }

        // Sort the friends. Terribly inefficient. Please implement
        // a O(nlog(n)) algorithm when time permits.

        // Sorts the unsortedFriends alphabetically, no regard for
        // online/offline statuses
        while (!unsortedFriends.isEmpty()) {
            for (UserData user : unsortedFriends) {
                if (candidate == null) {
                    candidate = user;
                } else if (user.getNickname().compareToIgnoreCase(
                        candidate.getNickname()) < 0) {
                    candidate = user;
                } else {
                    // Do nothing, look at next user.
                }
            }
            unsortedFriends.remove(candidate);
            friends.add(candidate);
            candidate = null;
        }

        unsortedFriends = friends;
        friends = new ArrayList<UserData>();
        candidate = null;

        // Sort with regard to online/busy/offline/blocked
        while (!unsortedFriends.isEmpty()) {
            for (UserData user : unsortedFriends) {
                if (candidate == null) {
                    candidate = user;
                } else if (user.isMoreOnline(candidate)) {
                    candidate = user;
                } else {
                    // do nothing, next iteration
                }
            }
            unsortedFriends.remove(candidate);
            friends.add(candidate);
            candidate = null;
        }

        return friends;
    }

    public Vector<FriendTempData> getSavedFriends(String accountName) {
        Vector<FriendTempData> friends = null;
        DatabaseFunctions db = null;

        try {
            db = new DatabaseFunctions();
            friends = db.getFriendListByAccountName(accountName);
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            friends = new Vector<FriendTempData>();
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            friends = new Vector<FriendTempData>();
            e.printStackTrace();
        }

        return friends;
    }

    /*
     * ChatLog functions
     */
    public Vector<String> getBuddyLogList(String profile) {
        // returns list of buddies (that have chat log)
        // We might need to consider the case in which the same
        // user is on multiple accounts. With this scheme, the
        // user will be returned twice.
        Vector<String> buddies = null;
        DatabaseFunctions db = null;

        try {
            db = new DatabaseFunctions();
            buddies = new Vector<String>();
            for (AccountData account : this.getCurrentProfile()
                    .getAccountData()) {

                // Iterates over all accounts, and adds the messages from the
                // database of all accounts into messages
                buddies.addAll(db.getChatNameList(account.getAccountName()));
            }
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return buddies;
    }

    public Vector<String> getBuddyDateList(String profile, String buddyname) {
        // returns history date list
        Vector<String> chats = null;

        DatabaseFunctions db = null;
        try {
            db = new DatabaseFunctions();
            chats = db.getChatDatesFromName(profile, buddyname);
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return chats;
    }

    public ArrayList<ChatLogMessageTempData> getLogMessage(String username,
            String buddyname, String date) {
        DatabaseFunctions db = null;
        ArrayList<ChatLogMessageTempData> messages = new ArrayList<ChatLogMessageTempData>();

        try {
            db = new DatabaseFunctions();
            for (AccountData account : this.getCurrentProfile()
                    .getAccountData()) {

                // Iterates over all accounts, and adds the messages from the
                // database of all accounts into messages
                messages.addAll(db.getMessageFromDate(account.getAccountName(),
                        buddyname, date));
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return messages;
    }

}
