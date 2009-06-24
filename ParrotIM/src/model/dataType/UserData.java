/* UserData.java
 * 
 * Programmed By:
 *     Kevin Fahy
 *     William Chen
 *     
 * Change Log:
 *     2009-June-7, KF
 *         Initial write. Holds all information about a user that could
 *         appear on the friend list.
 *     2009-June-13, WC
 *         Moved over to ParrotIM project.
 *     2009-June-18, KF
 *         Added a boolean field, blocked, to show whether the friend
 *         can see your status and chat with you.
 *     2009-June-24, KF
 *         Added JavaDoc documentation.
 *         
 * Known Issues:
 *     1. The data members may not apply to every protocol.
 * 
 * Copyright (C) 2009  Pirate Captains
 * 
 * License: GNU General Public License version 2.
 * Full license can be found in ParrotIM/LICENSE.txt.
 */

package model.dataType;

/**
 * Holds all data pertaining to users, including the account name, nickname, and
 * status message.
 */
public abstract class UserData {

    // Section
    // I - Data Members
    
    /**
     * The userID of the external user.
     */
    protected String accountName;

    /**
     * The nickname of the external user.
     */
    protected String nickname;

    /**
     * The current status message of the user.
     */
    protected String status;

    /**
     * The blocked status of the user.
     */
    protected boolean blocked;

    /**
     * The state of the user (e.g. Available, Away, Offline).
     */
    protected String state;

    // Section
    // II - Constructors
    
    /**
     * Creates a new user with their userID, nickname, and status.
     * 
     * @param accountName
     * @param nickname
     * @param status
     */
    public UserData(String accountName, String nickname, String status) {
        this.accountName = accountName;
        this.nickname = nickname;
        this.status = status;
        this.blocked = false;
        this.state = "Offline";
    }

    /**
     * Creates a new user with only a userID defined.
     * 
     * @param accountName
     */
    public UserData(String accountName) {
        this.accountName = accountName;
        this.nickname = this.accountName;
        this.status = "";
        this.blocked = false;
        this.state = "Offline";
    }

    // Section
    // III - Accessors and Mutators
    
    /**
     * Sets the userID.
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
     * @return The userID of the user.
     */
    public String getAccountName() {
        return accountName;
    }

    /**
     * Changes the nickname.
     * 
     * @param nickname
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
        return;
    }

    /**
     * Gets the nickname.
     * 
     * @return A String of the nickname for the user.
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * Changes the user's status.
     * 
     * @param status
     */
    public void setStatus(String status) {
        this.status = status;
        return;
    }

    /**
     * Retrieves the user's status.
     * 
     * @return A String of the user's status.
     */
    public String getStatus() {
        return status;
    }

    /**
     * Changes the blocked property of the user.
     * 
     * @param blocked
     */
    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
        return;
    }

    /**
     * Gets the blocked status.
     * 
     * @return True if blocked, false otherwise.
     */
    public boolean isBlocked() {
        return this.blocked;
    }

    /**
     * Sets the state of the user.
     * 
     * @param state
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * Gets the user's state.
     * 
     * @return The state of the user as a String.
     */
    public String getState() {
        return this.state;
    }
    
    // Section 
    // IV - Information Methods

    /**
     * Determines a hierarchy of states, that is, returns true if this user has
     * a more online state than the passed-in user. The hierarchy is: online -->
     * away/busy --> offline --> blocked. This method is useful in ordering
     * the buddy list to show the more online people at the top.
     * 
     * @param user
     * @return true if this user is more online than the passed-in user
     */
    public boolean isMoreOnline(UserData user) {
        int ownPriority = 0;
        int userPriority = 0;

        if (this.blocked) {
            ownPriority = 1;
        } else if (this.state.equalsIgnoreCase("Available")) {
            ownPriority = 4;
        } else if (this.state.equalsIgnoreCase("Offline")) {
            ownPriority = 2;
        } else {
            ownPriority = 3;
        }

        if (user.isBlocked()) {
            userPriority = 1;
        } else if (user.getState().equalsIgnoreCase("Available")) {
            userPriority = 4;
        } else if (user.getState().equalsIgnoreCase("Offline")) {
            userPriority = 2;
        } else {
            userPriority = 3;
        }

        return (ownPriority > userPriority);
    }

    /**
     * Converts the user to a String, returning the nickname.
     * 
     * @return The string representation for a user.
     */
    @Override
    public String toString() {
        return nickname;
    }
}