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

import controller.services.BadConnectionException;
import controller.services.GenericConnection;
import controller.services.GoogleTalkManager;
import controller.services.JabberManager;
import controller.services.TwitterManager;

import model.enumerations.ServerType;

/**
 * Holds username, password, and account type. Info is used in user profiles and
 * guest accounts for log-in purposes.
 */
public abstract class AccountData extends PersonData {

    /**
     * The password for the account. Pairs up with the userID.
     */
    private String password;

    public AccountData(String userID, String password) {
        super(userID);
        this.password = password;
    }

    /**
     * Returns the server type as a ServerType enumeration.
     * 
     * @return The ServerType of the account.
     */
    public abstract ServerType getServer();

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
    public abstract boolean addFriend(UserData friend)
            throws UserMismatchException;

    /**
     * Removes a friend from the account. Searches by UserData.
     * 
     * @param exFriend
     *            The friend to remove.
     * 
     * @return true if removed, false otherwise
     */
    public abstract boolean removeFriend(UserData exFriend);

    /**
     * Gets a list of all friends in UserData format.
     * 
     * @return An ArrayList of all friends in UserData objects.
     */
    public abstract ArrayList<UserData> getFriends();

    /**
     * Checks whether a friend is in the list or not.
     * 
     * @param friend
     *            The friend to check.
     * @return true if found, false otherwise
     */
    public boolean friendExists(UserData friend) {
        return this.findFriendByUserID(friend.getUserID()) != null;
    }

    /**
     * Searches for a friend by their userID, and returns true if found.
     * 
     * @return true if found, false otherwise.
     */
    public UserData findFriendByUserID(String userID) {

        UserData foundFriend = null; // Default return value

        for (UserData u : this.getFriends()) {
            if (u.getUserID().equalsIgnoreCase(userID)) {
                foundFriend = u;
                break;
            }
        }

        return foundFriend;
    }

    /**
     * Changes the connection object.
     * 
     * @param connection
     */
    public abstract void setConnection(GenericConnection connection)
            throws BadConnectionException;

    /**
     * Sets the connection object that communicates with the server.
     * 
     * @return The connection object for this account.
     */
    public abstract GenericConnection getConnection();

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
        AccountData otherAccount = null;

        if (account != null && account instanceof AccountData) {
            otherAccount = (AccountData) account;
            areEqual =
                    this.userID.equalsIgnoreCase(otherAccount.getUserID())
                            && (this.getServer().equals(otherAccount
                                    .getServer()));
        }

        return areEqual;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = hash * 31 + this.userID.toLowerCase().hashCode();
        hash = hash * 31 + this.getServer().hashCode();

        return hash;
    }
}