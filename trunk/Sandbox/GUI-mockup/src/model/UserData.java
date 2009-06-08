package model;

/**
 * Holds all data pertaining to users, including the account name,
 * nickname, and status message.
 */
public abstract class UserData {
    protected String accountName;
    protected String nickname;
    protected String status;
	
    public UserData(String accountName, String nickname, String status) {
 	this.accountName = accountName;
 	this.nickname = nickname;
	this.status = status;
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
}
