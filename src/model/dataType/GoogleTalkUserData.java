/* GoogleTalkUserData.java
 * 
 * Programmed By:
 *     Kevin Fahy
 *     William Chen
 *     
 * Change Log:
 *     2009-June-7, KF
 *         Initial write. Inherits UserData
 *     2009-June-13, WC
 *         Moved over to ParrotIM project.
 *     2009-June-24, KF
 *         Added JavaDoc Documentation
 *         
 * Known Issues:
 *     1. Currently not too useful. Should be revised after other
 *        UserData classes are created for other protocols.
 *     2. No toString() method.
 * 
 * Copyright (C) 2009  Pirate Captains
 * 
 * License: GNU General Public License version 2.
 * Full license can be found in ParrotIM/LICENSE.txt.
 */

package model.dataType;

/**
 * Holds data about an external user on Google Talk.
 */
public class GoogleTalkUserData extends UserData {

    // Section
    // I - Data Members

    /**
     * Indicates whether the user is online or not.
     */
    private boolean online;

    // Section
    // II - Constructors

    /**
     * Creates a new user with a userID, nickname, and status.
     * 
     * @param accountName
     * @param nickname
     * @param status
     */
    public GoogleTalkUserData(String accountName, String nickname, String status) {
        super(accountName, nickname, status);
    }

    /**
     * Creates a new user with only a userID.
     * 
     * @param accountName
     */
    public GoogleTalkUserData(String accountName) {
        super(accountName);
    }

    // Section
    // III - Accessors and Mutators

    /**
     * Sets the online status of the user.
     * 
     * @param online
     */
    public void setOnline(boolean online) {
        this.online = online;
        return;
    }

    /**
     * Gets whether the user is online or not.
     * 
     * @return True if online; false otherwise.
     */
    public boolean isOnline() {
        return online;
    }
}