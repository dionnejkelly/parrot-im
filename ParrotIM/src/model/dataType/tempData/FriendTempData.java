/* FriendTempData.java
 * 
 * Programmed By:
 *     Kevin Fahy
 *     
 * Change Log:
 *     2009-June-20, KF
 *         Initial write. Created for use of database to pass back partial
 *         friend data to the controller. Completed JavaDoc documentation.
 *     2009-June-29, KF
 *         Added the nickname field to transfer from the server to the
 *         model. Also added state and status fields.
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

import model.enumerations.UserStateType;

/**
 * Holds partial UserData objects for use in transferring from the database to
 * other locations. This is the raw data that should be stored in the database.
 * All other data about a user should be fetched from the server.
 */
public class FriendTempData {

    /**
     * The accountName/email address/username of a user. Includes the
     * "@email.com" part of the name.
     */
    private String userID;

    private String nickname;

    // TODO maybe implement group field?

    private String status;

    private UserStateType state;

    /**
     * Indicates whether the user is blocked or not.
     */
    private boolean blocked;

    private String group;

    /**
     * Empty constructor.
     */
    public FriendTempData() {
        this(null, null, null, null, null, false);
        // this.setUserID(null);
        // this.nickname = null;
        // this.status = null;
        // this.state = null;
        // this.setBlocked(false);
    }

    /**
     * Semi-full constructor
     * 
     * @param userID
     * @param blocked
     */
    public FriendTempData(String userID, boolean blocked) {
        this(userID, null, null, null, null, blocked);
        // this.setUserID(userID);
        // this.nickname = null;
        // this.status = null;
        // this.state = null;
        //        
        // this.setBlocked(blocked);
    }

    /**
     * Full constructor
     * 
     * @param userID
     * @param blocked
     */
    public FriendTempData(String userID, String nickname, String status,
            UserStateType state, boolean blocked) {
        this(userID, nickname, status, state, null, blocked);
        // this.setUserID(userID);
        // this.nickname = nickname;
        // this.status = status;
        // this.state = state;
        // this.setBlocked(blocked);
    }

    public FriendTempData(String userID, String nickname, String status,
            UserStateType state, String group, boolean blocked) {
        this.setUserID(userID);
        this.nickname = nickname;
        this.status = status;
        this.state = state;
        this.group = group;
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

    public String getGroup() {
        return group;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;

        return;
    }

    public String getNickname() {
        return nickname;
    }

    public void setStatus(String status) {
        this.status = status;

        return;
    }

    public String getStatus() {
        return status;
    }

    public void setState(UserStateType state) {
        this.state = state;

        return;
    }

    public UserStateType getState() {
        return state;
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