package model;

import java.util.ArrayList;

/**
 * Holds the account information of the current account being used,
 * whether it is one stored in the database or a guest account.
 */
public class CurrentProfileData {
    private ArrayList<AccountData> accountData;
    private String profileName;

    public CurrentProfileData(ArrayList<AccountData> accountData) {
    	this.accountData = accountData;
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
}
