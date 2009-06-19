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
 *         
 * Known Issues:
 *     1. Not documented thoroughly.
 *     2. The data members may not apply to every protocol.
 * 
 * Copyright (C) 2009  Pirate Captains
 * 
 * Full license can be found in ParrotIM/LICENSE.txt.
 */

package model.dataType;

import java.sql.SQLException;

import model.DatabaseFunctions;

/**
 * Holds all data pertaining to users, including the account name, nickname, and
 * status message.
 */
public abstract class UserData {
    protected String accountName;
    protected String nickname;
    protected String status;
    protected boolean blocked;

    public UserData(String accountName, String nickname, String status) {
        this.accountName = accountName;
        this.nickname = nickname;
        this.status = status;
        try {
            this.blocked = DatabaseFunctions
                    .checkBlockedByAccountName(accountName);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    public UserData(String accountName) {
        this.accountName = accountName;
        this.nickname = this.accountName;
        this.status = "";
        try {
            this.blocked = DatabaseFunctions
                    .checkBlockedByAccountName(accountName);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
        return;
    }

    public String getAccountName() {
        return accountName;
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

    @Override
    public String toString() {
        return nickname;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
        try {
            if (!DatabaseFunctions.friendExists(this.accountName)) {
                DatabaseFunctions.addFriend(this.accountName, this.blocked);
            } else { // exists, just add the status
                DatabaseFunctions.changeBlocked(this.accountName,
                        this.blocked);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return;
    }

    public boolean isBlocked() {
        return this.blocked;
    }
    
}