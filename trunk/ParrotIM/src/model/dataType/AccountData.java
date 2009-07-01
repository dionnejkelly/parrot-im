/* AccountData.java
 * 
 * Programmed By:
 *     Kevin Fahy
 *     William Chen
 *     Rakan Alkheliwi
 *     
 * Change Log:
 *     2009-June-7, KF
 *         Initial write. Added base data for an account.
 *     2009-June-13, WC
 *         Moved over to ParrotIM project.
 *     2009-June-18, KF
 *         Added removeFriend(), friendExists().
 *     2009-June-23, KF
 *         Refused the addition of a friend if a duplicate exists.
 *         Duplicates are checked by their userID string.
 *     2009-June-24, KF
 *         Finished JavaDoc documentation of the class.
 *     2009-June-25, KF, RA
 *         Added equals() method to resolve an issue found in 
 *         testing by Rakan.
 *     2009-June-28, KF
 *         Removed ownUserData, which was scheduled to be phased out. Added
 *         a field to hold a GenericConnection. It uses a getter to modify
 *         its information, and this class isn't designed to specifically
 *         change connection information; that's the job of the controller.
 *         
 * Known Issues:
 *     1. Bad implementation of adding ownUserData. Could use inheritance,
 *        or store enough information that no other UserData object
 *        is required.
 *     2. An exception should be thrown upon adding a duplicate friend.
 *     
 * Copyright (C) 2009  Pirate Captains
 * 
 * License: GNU General Public License version 2.
 * Full license can be found in ParrotIM/LICENSE.txt.
 */

package model.dataType;

import java.util.ArrayList;

import controller.services.GenericConnection;
import controller.services.GoogleTalkManager;
import controller.services.JabberManager;
import controller.services.TwitterManager;

import model.enumerations.ServerType;

/**
 * Holds username, password, and account type. Info is used in user profiles and
 * guest accounts for log-in purposes.
 */
public class AccountData {

    /**
     * The accountName, or more specifically, the userID for the account.
     */
    private String accountName;

    /**
     * The password for the account. Non-encrypted!
     */
    private String password;

    /**
     * A friend list of this account.
     */
    private ArrayList<UserData> friends;

    /**
     * A boolean to indicate whether the account is connected or not.
     */
    private boolean connected;

    /**
     * Holds the object that allows for connection to the server.
     */
    private GenericConnection connection;

    /**
     * Constructs for the basic required information: server, userID, and
     * password.
     * 
     * @param server
     * @param accountName
     * @param password
     */
    public AccountData(String accountName, String password) {
        this.accountName = accountName;
        this.password = password;
        this.friends = new ArrayList<UserData>();
        this.connected = false;
        this.connection = null;
    }

    /**
     * Constructs for the basic required information: server, userID, and
     * password.
     * 
     * @param server
     * @param accountName
     * @param password
     */
    public AccountData(String accountName, String password,
            GenericConnection connection) {
        this.accountName = accountName;
        this.password = password;
        this.friends = new ArrayList<UserData>();
        this.connected = false;
        this.connection = connection;
    }

    /**
     * Returns the server type as a ServerType enumeration.
     * 
     * @return The ServerType of the account.
     */
    public ServerType getServer() {
        ServerType serverToReturn = null;
        if (this.connection instanceof GoogleTalkManager) {
            serverToReturn = ServerType.GOOGLE_TALK;
        } else if (this.connection instanceof JabberManager) {
            serverToReturn = ServerType.JABBER;
        } else if (this.connection instanceof TwitterManager) {
            serverToReturn = ServerType.TWITTER;
        }
        return serverToReturn;
    }

    /**
     * Changes the userID of the account.
     * 
     * @param accountName
     */
    public void setAccountName(String accountName) {
        this.accountName = accountName;
        return;
    }

    /**
     * Gets the userID.
     * 
     * @return The userID as a String.
     */
    public String getAccountName() {
        return accountName;
    }

    /**
     * Changes the password.
     * 
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
        return;
    }

    /**
     * Gets the password.
     * 
     * @return The password, not encrypted, for the account. Can be an empty
     *         string.
     * 
     */
    public String getPassword() {
        return password;
    }

    /**
     * Adds a friend by UserData. Will not add duplicate entries, checked by
     * userID.
     * 
     * @param friend
     * @return true if successful, false otherwise. May return false due to a
     *         duplciate friend.
     */
    public boolean addFriend(UserData friend) {
        boolean notDuplicate = false;

        if (!friendExists(friend)) {
            this.friends.add(friend);
            notDuplicate = true;
        }

        return notDuplicate;
    }

    /**
     * Removes a friend from the account. Searches by UserData.
     * 
     * @param exFriend
     *            The friend to remove.
     * 
     * @return true if removed, false otherwise
     */
    public boolean removeFriend(UserData exFriend) {
        return this.friends.remove(exFriend);
    }

    /**
     * Gets a list of all friends in UserData format.
     * 
     * @return An ArrayList of all friends in UserData objects.
     */
    public ArrayList<UserData> getFriends() {
        return this.friends;
    }

    /**
     * Checks whether a friend is in the list or not.
     * 
     * @param friend
     *            The friend to check.
     * @return true if found, false otherwise
     */
    public boolean friendExists(UserData friend) {
        return this.findFriendByUserID(friend.getAccountName()) != null;
    }

    /**
     * Searches for a friend by their userID, and returns true if found.
     * 
     * @return true if found, false otherwise.
     */
    public UserData findFriendByUserID(String userID) {
        UserData foundFriend = null; // Default return value

        for (UserData u : this.friends) {
            if (u.getAccountName().equalsIgnoreCase(userID)) {
                foundFriend = u;
                break;
            }
        }

        return foundFriend;
    }

    /**
     * Sets the connected field.
     * 
     * @param connected
     */
    public void setConnected(boolean connected) {
        this.connected = connected;
        return;
    }

    /**
     * Returns whether it is connected or not.
     * 
     * @return true if connected, false otherwise.
     */
    public boolean isConnected() {
        return connected;
    }

    /**
     * Changes the connection object.
     * 
     * @param connection
     */
    public void setConnection(GenericConnection connection) {
        this.connection = connection;

        return;
    }

    /**
     * Sets the connection object that communicates with the server.
     * 
     * @return The connection object for this account.
     */
    public GenericConnection getConnection() {
        return connection;
    }

    /**
     * Checks if the accounts are the same. Determines so by whether their
     * userID is the same.
     * 
     * @param account
     * @return true if they have the same userID, false otherwise.
     */
    @Override
    public boolean equals(Object account) {
        boolean areEqual = false;
        AccountData externalAccount = null;

        if (account != null && account instanceof AccountData) {
            externalAccount = (AccountData) account;
            areEqual = this.accountName.equalsIgnoreCase(externalAccount
                    .getAccountName())
                    && (this.getServer() == externalAccount.getServer());
        }

        return areEqual;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = hash * 31 + this.accountName.toLowerCase().hashCode();
        hash = hash * 31 + this.connection.hashCode();

        return hash;
    }
}