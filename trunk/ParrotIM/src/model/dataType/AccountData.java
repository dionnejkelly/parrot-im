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
    	    this.ownUserData = new GoogleTalkUserData(this.accountName, this);
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
	
	public ArrayList<UserData> getFriends() {
	    return this.friends;
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
