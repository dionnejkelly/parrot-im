package model.dataType;

import java.util.*;


/**
 * Holds the account information of the current account being used,
 * whether it is one stored in the database or a guest account.
 */
public class CurrentProfileData {
    private final static String EMPTY_NAME = "<empty>";
    
    private ArrayList<AccountData> accountData;
    private String profileName;
    
    public CurrentProfileData() {
        this.accountData = new ArrayList<AccountData>();
        this.profileName = EMPTY_NAME; 
}
    
    public CurrentProfileData(ArrayList<AccountData> accountData,
                              String profileName) {
    	this.accountData = accountData;
    	this.profileName = profileName;
    }
    
    public CurrentProfileData(AccountData account, String profileName) {
        this.accountData = new ArrayList<AccountData>();
        this.accountData.add(account);
        this.profileName = profileName;
    }
    
    public void setAccountData(ArrayList<AccountData> accountData) {
	this.accountData = accountData;
	return;
    }

    public ArrayList<AccountData> getAccountData() {
	return accountData;
    }

    public void setProfileName(String profileName) {
	this.profileName = profileName;
	return;
    }

    public String getProfileName() {
        return profileName;
    }
    
    public String toString() {
        return profileName;
    }
    
    public void addAccount(AccountData account) {
        if (!this.accountData.contains(account)) {
            this.accountData.add(account);
        }
        else {
            // Exception? There is a duplicate account.
        }
        return;
    }
    
    /**
     * Used to gather friends from all accounts inside of
     * this class.
     * 
     * @return UserData of friends from all associated accounts. 
     */
    public ArrayList<UserData> getAllFriends() {
        ArrayList<UserData> friends = new ArrayList<UserData>();         
        for (AccountData account : this.accountData) {
            for (UserData user : account.getFriends()) {
                friends.add(user);
            }
        }
        return friends;
    }
}
