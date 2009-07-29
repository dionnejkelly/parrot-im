/* FriendTempData.java
 * 
 * Programmed By:
 *     Kevin Fahy
 *     
 * Change Log:
 *     2009-June-20, KF
 *         Initial write. Created for use of database to pass back partial
 *         friend data to the controller. Completed JavaDoc documentation.
 *         
 * Known Issues:
 *     none
 *     
 * Copyright (C) 2009  Pirate Captains
 * 
 * License: GNU General Public License version 2.
 * Full license can be found in ParrotIM/LICENSE.txt.
 */

package model.dataType.tempData;

/**
 * Holds partial UserData objects for use in transferring from
 * the database to other locations. This is the raw data that
 * should be stored in the database. All other data about a user
 * should be fetched from the server.
 */
public class FriendTempData {
    
    /**
     * The accountName/email address/username of a user. Includes
     * the "@email.com" part of the name.
     */
    private String userID;
    
    /**
     * Indicates whether the user is blocked or not. 
     */
    private boolean blocked;

    /**
     * Empty constructor.
     */
    public FriendTempData() {
        this.setUserID(null);
        this.setBlocked(false);
    }

    /**
     * Full constructor
     * 
     * @param userID
     * @param blocked
     */
    public FriendTempData(String userID, boolean blocked) {
        this.setUserID(userID);
        this.setBlocked(blocked);
    }

    /**
     * Sets userID.
     * 
     * @param userID
     */
    public void setUserID(String userID) {
        this.userID = userID;
        return;
    }

    /**
     * Returns userID.
     * 
     * @return
     */
    public String getUserID() {
        return userID;
    }

    /**
     * Sets blocked.
     * 
     * @param blocked
     */
    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
        return;
    }

    /**
     * Returns blocked.
     * 
     * @return
     */
    public boolean isBlocked() {
        return blocked;
    }
}