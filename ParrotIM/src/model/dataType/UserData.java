package model.dataType;


/**
 * Holds all data pertaining to users, including the account name,
 * nickname, and status message.
 */
public abstract class UserData {
    protected String accountName;
    protected String nickname;
    protected String status;
    protected AccountData friendOf;
	
    public UserData(String accountName, String nickname, String status, AccountData friendOf) {
 	this.accountName = accountName;
 	this.nickname = nickname;
	this.status = status;
	this.friendOf = friendOf;
    }
    
    public UserData(String accountName, AccountData friendOf) {
        this.accountName = accountName;
        this.nickname = this.accountName;
        this.status = "";
        this.friendOf = friendOf;
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
    
    public AccountData getFriendOf() {
        return this.friendOf;
    }
}
