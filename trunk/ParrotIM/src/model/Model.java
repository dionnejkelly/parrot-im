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
 *     2009-June-24, KF
 *         Completed JavaDoc documentation.
 *     2009-June-24, VL
 *         Added logWindowOpen variable. Changed the structure of getLogMessage()
 *        
 * Known Issues:
 *     1. Currently has methods that are scheduled to be phased out.
 * 
 * Copyright (C) 2009  Pirate Captains
 * 
 * License: GNU General Public License version 2.
 * Full license can be found in ParrotIM/LICENSE.txt.
 */

package model;

import java.util.*;
import java.sql.*;

import controller.services.GenericConnection;

import model.dataType.AccountData;
import model.dataType.ChatCollectionData;
import model.dataType.ConversationData;
import model.dataType.ProfileData;
import model.dataType.GoogleTalkUserData;
import model.dataType.JabberUserData;
import model.dataType.MessageData;
import model.dataType.UserData;
import model.dataType.tempData.AccountTempData;
import model.dataType.tempData.ChatLogMessageTempData;
import model.dataType.tempData.FriendTempData;
import model.enumerations.TypingStateType;
import model.enumerations.PopupEnableWindowType;
import model.enumerations.ServerType;
import model.enumerations.UpdatedType;

/**
 * The model stores all data and provides it for the view and controllers. Also,
 * the model is the only class to access the database, and does so by employing
 * DatabaseFunctions objects to retrieve and set data. Holds a reference to the
 * current profile used, which references all accounts and friends. Also
 * references conversation data to provide the GUI with the current active
 * conversation to display.
 */
public class Model extends Observable {

    // Section
    // I - Data Members

    private ChatCollectionData chatCollection;

    /**
     * A reference to the profile currently being used. Holds references to all
     * accounts and friends involved.
     */
    private ProfileData currentProfile;

    // This is clumsy. Can we integrate this into conversations? The chat
    // window should not show if there are no conversations active. This
    // variable should be phased out.
    // TODO Phase this variable out.
    public boolean logWindowOpen;
    public boolean aboutWindowOpen;

    // Section
    // II - Constructors

    /**
     * The main constructor. Sets up the local variables. Only one model should
     * exist for the program's execution.
     * 
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public Model() throws ClassNotFoundException, SQLException {
        this.currentProfile = null;
        this.chatCollection = new ChatCollectionData();
        aboutWindowOpen = false;
        logWindowOpen = false;
    }

    // Section
    // III - Profile and Account Manipulation Methods

    /**
     * Gets all profile names from the database.
     * 
     * @return A Vector of profile names in String format.
     */
    public Vector<String> getProfileList() {
        Vector<String> profiles = null;

        DatabaseFunctions db;
        try {
            db = new DatabaseFunctions();
            profiles = db.getProfileList();
        } catch (Exception e) {
            System.err.println("Database error. Returning "
                    + "a blank list of profiles.");
            e.printStackTrace();
            profiles = new Vector<String>();
        }
        
        if (profiles.size() == 0){
        	profiles.add(0, "Create a profile");
        } else {
        	profiles.add(0, "Select a profile");
        }

        return profiles;
    }

    /**
     * Gets a list of all accounts stored in the database.
     * 
     * @return A Vector of account userIDs in String format.
     */
    public Vector<String> getAccountList() {
        Vector<String> accounts = null;
        DatabaseFunctions db = null;

        try {
            db = new DatabaseFunctions();
            accounts = db.getUserList();
        } catch (Exception e) {
            System.err.println("Database failure. "
                    + "Returning a blank account list.");
            e.printStackTrace();
            accounts = new Vector<String>();
        }

        return accounts;
    }

    /**
     * Gets all accounts for a given profile. These accounts are returned in
     * objects that hold all parameters that were inside the database.
     * 
     * @param profile
     * @return An ArrayList of AccountTempData objects.
     */
    public ArrayList<AccountTempData> getAccountsForProfile(String profile) {
        ArrayList<AccountTempData> accounts = null;
        DatabaseFunctions db = null;

        try {
            db = new DatabaseFunctions();
            accounts = db.getAccountList(profile);
        } catch (Exception e) {
            System.err.println("Database error. Returning "
                    + "A blank list of accounts.");
            e.printStackTrace();
            accounts = new ArrayList<AccountTempData>();
        }

        return accounts;
    }

    /**
     * Gets all accounts associated with a given profile. Returns only the
     * userID in a list.
     * 
     * @param name
     * @return A Vector of account userIDs associated with a given profile.
     */
    public Vector<String> getProfilesUserList(String name) {
        Vector<String> accounts = null;
        DatabaseFunctions db = null;

        try {
            db = new DatabaseFunctions();
            accounts = db.getProfilesUserList(name);
        } catch (Exception e) {
            System.err.println("Database error. Returning "
                    + "a blank list of accounts");
            e.printStackTrace();
            accounts = new Vector<String>();
        }

        return accounts;
    }

    /**
     * Returns the password for a given user account.
     * 
     * @param accountUserID
     * @return The password for the account.
     */
    public String getPassword(String accountUserID) {
        String password = null;
        DatabaseFunctions db = null;

        try {
            db = new DatabaseFunctions();
            password = db.getAccountPassword(accountUserID);
        } catch (Exception e) {
            System.err.println("Database error. Returning an "
                    + "empty string as a password");
            e.printStackTrace();
            password = "";
        }

        return password;
    }

    // Section
    // IV = Conversation Manipulation Methods

    public ConversationData findConversation(AccountData account, UserData user) {
        ConversationData conversation = null;

        for (ConversationData c : this.chatCollection.getAllConversations()) {
            if (c.getAccount().equals(account) && c.getUser().equals(user)) {
                conversation = c;
                break;
            }
        }

        return conversation;
    }

    public ConversationData findConversation(UserData user) {
        ConversationData conversation = null;

        for (ConversationData c : this.chatCollection.getAllConversations()) {
            if (c.getUser().equals(user)) {
                conversation = c;
                break;
            }
        }

        return conversation;
    }

    /**
     * Returns the number of conversations in existence.
     * 
     * @return The number of conversations open in the chat.
     */
    public int numberOfConversations() {
        return this.chatCollection.getConversations().size();
    }

    /**
     * Switches the current active conversation. Should make messages sent in
     * the chat window go to this user. Also, should be the window shown in the
     * chat window.
     * 
     * @param conversation
     */
    public void setActiveConversation(ConversationData conversation) {
        this.chatCollection.setActiveConversation(conversation);

        super.setChanged();
        super.notifyObservers(UpdatedType.CHAT);

        return;
    }

    /**
     * Removes the current active conversation from the list.
     */
    public void removeActiveConversation() {
        this.chatCollection.removeConversation(this.chatCollection
                .getActiveConversation());

        return;
    }

    /**
     * Gets the current active conversation.
     * 
     * @return The current active conversation object.
     */
    public ConversationData getActiveConversation() {
        return this.chatCollection.getActiveConversation();
    }

    /**
     * Handles the receiving message.
     * 
     * @param account
     * @param message
     */

    public void receiveMessage(AccountData account, MessageData message) {
        UserData user = null;
        ConversationData modifiedConversation = null;
        DatabaseFunctions db = null;

        String fromUser = message.getFromUser();

        if (this.getCurrentProfile().isChatLogEnabled()) {
            try {
                db = new DatabaseFunctions();
                db.addChat(currentProfile.getProfileName(), fromUser, account
                        .getUserID(), message.getMessage());
            } catch (Exception e) {
                System.err.println("Database error. Chat not saved "
                        + "in the chat log.");
                e.printStackTrace();
            }
        }

        user = this.findUserByAccountName(fromUser);
        modifiedConversation = this.findConversation(account, user);

        if (modifiedConversation == null) {
            // No match found; create conversation and add it to the
            // list
            modifiedConversation = new ConversationData(account, user);

        }
        modifiedConversation.addMessage(message);
        this.chatCollection.addConversation(modifiedConversation);
        setChanged();
        notifyObservers(UpdatedType.CHAT);
        // notifyObservers(UpdatedType.RECEIVECHAT);

        return;
    }

    /**
     * Sends the outgoing message.
     * 
     * @param modifiedConversation
     * @param message
     * @throws ClassNotFoundException
     * @throws SQLException
     */

    public void sendMessage(ConversationData modifiedConversation,
            MessageData message) {
        DatabaseFunctions db = null;

        modifiedConversation.addMessage(message);

        if (this.getCurrentProfile().isChatLogEnabled()) {
            try {
                db = new DatabaseFunctions();
                db.addChat(currentProfile.getProfileName(), message
                        .getFromUser(), modifiedConversation.getUser()
                        .getUserID(), message.getMessage());
            } catch (Exception e) {
                System.err.println("Database error, adding chat.");
                e.printStackTrace();
            }
        }

        setChanged();
        notifyObservers(UpdatedType.CHATNOTSIDEPANEL);
        return;
    }

    /**
     * Starts new conversation.
     * 
     * @param user
     */

    public ConversationData startConversation(AccountData account, UserData user) {
        ConversationData conversation = null;

        conversation = this.findConversation(account, user);
        if (conversation == null) {
            conversation = new ConversationData(account, user);
        }
        this.chatCollection.addConversation(conversation);

        this.chatCollection.setActiveConversation(conversation);

        setChanged();
        notifyObservers(UpdatedType.CHATNOTSIDEPANEL);

        return conversation;
    }

    /**
     * Returns the current conversation.
     * 
     * @return ArrayList<ConversationData>
     */
    public ArrayList<ConversationData> getConversations() {
        return this.chatCollection.getConversations();
    }

    public void clearAllConversations() {

        // Determine whether to save chat history or not
        if (this.getCurrentProfile().isChatWindowHistoryEnabled()) {
            this.chatCollection.hideAllConversations();
        } else {
            this.chatCollection.removeAllConversations();
        }

        setChanged();
        notifyObservers(UpdatedType.CHAT);

        return;
    }

    public void removeAllConversations() {
        this.chatCollection.removeAllConversations();

        setChanged();
        notifyObservers(UpdatedType.CHAT);
    }

    /*
     * SECTION: Account manipulation - Remove account - Add account
     */

    /**
     * Add account to the database.
     * 
     * @param profile
     * @param server
     * @param accountName
     * @param password
     */

    public void addAccount(String profile, String serverType,
            String serverAddress, String accountName, String password) {
        DatabaseFunctions db;
        try {
            db = new DatabaseFunctions();
            if (!db.checkAccountExists(profile, accountName)) {
                db = new DatabaseFunctions();
                db.addUsers(profile, serverType, serverAddress, accountName,
                        password);
            }
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

    /**
     * Removes account from the database.
     * 
     * @param profile
     * @param account
     */

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

    /**
     * Adds profile to the the database.
     * 
     * @param name
     * @param pwd
     * @param defaultProfile
     */

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

    /**
     * Removes profile from the the database.
     * 
     * @param name
     */

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

    /**
     * Creates current profile.
     * 
     * @param account
     * @param profileName
     */

    public void createCurrentProfile(AccountData account, String profileName) {
        currentProfile = new ProfileData(profileName);
        currentProfile.addAccount(account);
        return;
    }

    public void createCurrentProfile(String profileName) {
        currentProfile = new ProfileData(profileName);
        return;
    }

    /**
     * Adds account to current profile.
     * 
     * @param account
     */

    public void addAccountToCurrentProfile(AccountData account) {
        currentProfile.addAccount(account);
        return;
    }

    /**
     * Returns current profile.
     * 
     * @return CurrentProfileData
     */

    public ProfileData getCurrentProfile() {
        return currentProfile;
    }

    /**
     * Clears current profile.
     * 
     */

    public void clearCurrentProfile() {
        this.currentProfile = null;

        return;
    }

    /**
     * Check whether current profile exists.
     * 
     * @return boolean
     */

    public boolean currentProfileExists() {
        return (currentProfile != null);
    }

    /**
     * Force to update.
     * 
     * @param updatedType
     */

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
            // Temp fix for switching conversations
            if (user.getUserID().equals(accountName)
                    || user.getNickname().equals(accountName)) {
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

        account = currentProfile.getAccountFromServer(server);

        // Temp fix for user bug in jabber
        // Shouldn't actually cycle through all of the accounts
        if (account == null) {
            server = ServerType.JABBER;
            account = currentProfile.getAccountFromServer(server);
            if (account == null) {
                server = ServerType.GOOGLE_TALK;
                account = currentProfile.getAccountFromServer(server);
            }
        }

        if (server == ServerType.GOOGLE_TALK) {
            userToAdd = new GoogleTalkUserData(accountName);
        } else if (server == ServerType.JABBER) {
            userToAdd = new JabberUserData(accountName);
        }

        account.addFriend(userToAdd);

        // Database manipulation
        try {
            db = new DatabaseFunctions();
            if (!db.checkFriendExists(account.getUserID(), accountName)) {
                friend = new FriendTempData(accountName, false);
                db = new DatabaseFunctions();
                db.addFriend(account.getUserID(), friend);
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
            if (!db.checkFriendExists(account.getUserID(), userToAdd
                    .getUserID())) {
                friend = new FriendTempData(userToAdd.getUserID(), false);
                db = new DatabaseFunctions();
                db.addFriend(account.getUserID(), friend);
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
            friendName = exFriend.getUserID();
            if (friendName != null && account != null
                    && db.checkFriendExists(account.getUserID(), friendName)) {
                db = new DatabaseFunctions();
                db.removeFriend(account.getUserID(), friendName);
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
                if (user.equals(userToBeFound)) {
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

    public AccountData findAccountByUserID(String userID) {
        AccountData foundAccount = null; // Default return value

        for (AccountData account : currentProfile.getAccountData()) {
            if (account.getUserID().equalsIgnoreCase(userID)) {
                foundAccount = account;
                break;
            }
        }

        return foundAccount;
    }

    public AccountData findAccountByConnection(GenericConnection connection) {
        AccountData foundAccount = null; // Default return value

        for (AccountData account : currentProfile.getAccountData()) {
            if (account.getConnection() == connection) {
                foundAccount = account;
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
            db.changeBlocked(friend.getUserID(), true);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        super.setChanged();
        super.notifyObservers(UpdatedType.BUDDY);
        super.setChanged();
        super.notifyObservers(UpdatedType.BUDDY_BLOCK_MANAGER);

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
            db.changeBlocked(friend.getUserID(), false);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        super.setChanged();
        super.notifyObservers(UpdatedType.BUDDY_BLOCK_MANAGER);
        super.setChanged();
        super.notifyObservers(UpdatedType.BUDDY);

        return;
    }

    /**
     * Sets if a user is typing and updates the view.
     * 
     * @param friend
     *            UserData representation of the friend to unblock.
     */

    public void setTypingState(UserData friend, TypingStateType state) {
        friend.setTypingState(state);
        for (ConversationData cd1 : this.getConversations()) {
            if (cd1.getUser().getUserID().equals(friend.getUserID())) {
                break;
            }
        }

        super.setChanged();
        super.notifyObservers(UpdatedType.CHAT_STATE);
    }

    /**
     * Returns the banned user list.
     * 
     * @return Vector<UserData> representation of the blocked friends.
     */

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

    /**
     * Returns the saved friend list.
     * 
     * @param accountName
     * @return Vector<FriendTempData> representation of the saved friends.
     */

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

    /**
     * Returns the buddy log list.
     * 
     * @param profile
     * @return Vector<String> representation of the buddy log list.
     */

    public Vector<String> getBuddyLogList(String profile) throws SQLException,
            ClassNotFoundException {
        // returns list of buddies (that have chat log)
        // We might need to consider the case in which the same
        // user is on multiple accounts. With this scheme, the
        // user will be returned twice.
        Vector<String> buddies = new Vector<String>();
        DatabaseFunctions db = null;
        db = new DatabaseFunctions();

        // Iterates over all accounts, and adds the messages from the
        // database of all accounts into messages
        buddies.addAll(db.getChatNameList(profile, ""));

        return buddies;
    }

    /**
     * Returns the deleted buddy date list.
     * 
     * @param profile
     * @param buddyname
     * @return Vector<String> representation of the buddy date list.
     */

    public Vector<String> getBuddyDateList(String profile, String buddyname) {
        // returns history date list
        Vector<String> chats = null;

        DatabaseFunctions db = null;
        try {
            db = new DatabaseFunctions();
            chats = db.getChatDatesFromName(profile, buddyname, "");
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return chats;
    }

    /**
     * Returns the log list.
     * 
     * @param username
     * @param buddyname
     * @param date
     * @return Vector<ChatLogMessageTempData> representation of the log list.
     */

    public Vector<ChatLogMessageTempData> getLogMessage(String username,
            String buddyname, String date) {
        DatabaseFunctions db = null;
        Vector<ChatLogMessageTempData> messages =
                new Vector<ChatLogMessageTempData>();

        try {
            db = new DatabaseFunctions();
            for (AccountData account : this.getCurrentProfile()
                    .getAccountData()) {

                // Iterates over all accounts, and adds the messages from the
                // database of all accounts into messages
                messages.addAll(db.getMessageFromDate(account.getUserID(),
                        buddyname, date, ""));
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

    // Section
    // ? - Utility Methods

    /**
     * Forces the observers to be notified. Can specify which UpdatedType to
     * pass through to the GUI.
     * 
     * @param o
     *            UpdatedType object sent to the GUI.
     */
    public void forceNotify(Object o) {
        this.setChanged();
        this.notifyObservers(o);

        return;
    }

    /**
     * Returns a list of all valid servers to connect with.
     * 
     * @return A Vector of ServerType objects that hold a string representation
     *         of the server type.
     */
    public Vector<ServerType> getServerList() {
        Vector<ServerType> servers = new Vector<ServerType>();
        for (ServerType s : ServerType.values()) {
            servers.add(s);
        }

        return servers;
    }

    /**
     * sets the boolean of the popup window either true or false true if the
     * window is opened false if the closed
     * 
     * @param popupInt
     * @param state
     */
    public void updatePopupState(PopupEnableWindowType popupInt, boolean state) {
        if (popupInt == PopupEnableWindowType.CHATLOG) {
            System.out.println("Model:  ChatWindow");
            this.logWindowOpen = state;
        } else if (popupInt == PopupEnableWindowType.ABOUT) {
            System.out.println("Model: About");
            this.aboutWindowOpen = state;
        }
    }
}