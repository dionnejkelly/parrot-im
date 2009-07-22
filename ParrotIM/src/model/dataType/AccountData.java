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
 *     2009-July-6, KF
 *         Changed implementation to inherit from PersonData.
 *         
 * Known Issues:
 *     1. An exception should be thrown upon adding a duplicate friend.
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

import model.enumerations.ServerType;
import model.enumerations.UserStateType;

/**
 * Holds username, password, and account type. Info is used in user profiles and
 * guest accounts for log-in purposes.
 */
public abstract class AccountData extends PersonData {

    /**
     * The password that links to the userID to authenticate the account.
     */
    private String password;

    /**
     * Base constructor. Provides a userID and a password for logging in, but
     * does not assign any other values.
     * 
     * @param userID
     *            The screen name or username of the user to log in.
     * @param password
     *            The password paired with the user ID for authentication.
     */
    public AccountData(String userID, String password) {
        super(userID);
        this.setPassword(password);
    }

    /**
     * Full constructor. Provides the userID and password , and also gives the
     * status and nickname of the account.
     * 
     * @param userID
     *            The screen name or username of the user to log in.
     * @param nickname
     *            A nickname for the account.
     * @param status
     *            The custom status message of the account.
     * @param password
     *            The password paired with the userID for authentication.
     */
    public AccountData(String userID, String nickname, String status,
            UserStateType state, String password) {
        super(userID, nickname, status, state);
        this.setPassword(password);
    }

    /**
     * Changes the password. Does not allow a null password; if null, the
     * password will be set to the empty String.
     * 
     * @param password
     *            A password as a String.
     */
    public void setPassword(String password) {
        this.password = password != null ? password : "";

        return;
    }

    /**
     * Gets the password. Cannot be null.
     * 
     * @return The password, not encrypted, for the account. Can be an empty
     *         string.
     * 
     */
    public String getPassword() {
        return this.password;
    }

    public abstract GenericConnection getConnection();
    
    public abstract void setConnection(GenericConnection connection);
    
    /**
     * Returns the server type as a ServerType enumeration.
     * 
     * @return The ServerType of the account.
     */
    public abstract ServerType getServer();

    /**
     * Adds a friend by UserData. Will not add duplicate entries, checked by
     * userID.
     * 
     * @param friend
     * @return true if successful, false otherwise. May return false due to a
     *         duplciate friend.
     */
    public abstract boolean addFriend(UserData friend);

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
    
    public abstract ArrayList<UserData> getTweets() throws BadConnectionException;

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
            if (u.isDuplicate(userID)) {
                foundFriend = u;
                break;
            }
        }

        return foundFriend;
    }
    
    
}