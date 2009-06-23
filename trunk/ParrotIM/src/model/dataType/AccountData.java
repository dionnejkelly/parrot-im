/* AccountData.java
 * 
 * Programmed By:
 *     Kevin Fahy
 *     William Chen
 *     
 * Change Log:
 *     2009-June-7, KF
 *         Initial write. Added base data for an account.
 *     2009-June-13, WC
 *         Moved over to ParrotIM project.
 *     2009-June-18, KF
 *         Added removeFriend(), friendExists().
 *         
 * Known Issues:
 *     1. Bad implementation of adding ownUserData. Could use inheritance,
 *        or store enough information that no other UserData object
 *        is required.
 *     2. Not documented thoroughly.
 * 
 * Copyright (C) 2009  Pirate Captains
 * 
 * Full license can be found in ParrotIM/LICENSE.txt.
 */


package model.dataType;

import java.util.ArrayList;


/**
 * Holds username, password, and account type. Info is used in user profiles
 * and guest accounts for log-in purposes.
 */
public class AccountData {
    private ServerType server;
    private String accountName;
    private String password;
    
    /* Phase this field out */
    private UserData ownUserData;
    
    private ArrayList<UserData> friends;
    
    private boolean connected;
    
    public AccountData(ServerType server, String accountName, 
                       String password) {
    	this.server = server;
    	this.accountName = accountName;
    	this.password = password;
    	this.ownUserData = null;
    	this.friends = new ArrayList<UserData>();
    	this.connected = false;
    	
    	if (this.server == ServerType.GOOGLE_TALK) {
    	    this.ownUserData = new GoogleTalkUserData(this.accountName);
    	}
    }

    public void setServer(ServerType server) {
        this.server = server;
        return;
    }

    public ServerType getServer() {
        return server;
    }
	
    public void setAccountName(String accountName) {
        this.accountName = accountName;
        return;
    }

    public String getAccountName() {
        return accountName;
    }
	
    public void setPassword(String password) {
        this.password = password;
        return;
    }

    public String getPassword() {
        return password;
    }

    public void addFriend(UserData friend) {
        // Might be called to populate the buddy list.
        this.friends.add(friend);
        return;
    }
    
    /**
     * Removes a friend from the account. Searches by UserData.
     * 
     * @param exFriend   The friend to remove.
     * 
     * @return true if removed, false otherwise
     */
    public boolean removeFriend(UserData exFriend) {
        return this.friends.remove(exFriend);
    }
	
    public ArrayList<UserData> getFriends() {
        return this.friends;
    }
    
    /**
     * Checks whether a friend is in the list or not.
     * 
     * @param friend  The friend to check.
     * @return true if found, false otherwise
     */
    public boolean friendExists(UserData friend) {
        return this.friends.contains(friend);
    }

    public void setOwnUserData(UserData ownUserData) {
        this.ownUserData = ownUserData;
        return;
    }

    public UserData getOwnUserData() {
        return ownUserData;
    }
	
    public void setConnected(boolean connected) {
        this.connected = connected;
        return;		
    }

    public boolean isConnected() {
        return connected;
    }
	
}
