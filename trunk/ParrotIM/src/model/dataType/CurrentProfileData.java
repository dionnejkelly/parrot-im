/* CurrentProfileData.java
 * 
 * Programmed By:
 *     Kevin Fahy
 *     Vera Lukman
 *     William Chen
 *     
 * Change Log:
 *     2009-June-7, KF
 *         Initial write. Holds array of accounts.
 *     2009-June-9, KF
 *         Added functionality to search UserData friends from each 
 *         account inside of the CurrentProfile.
 *     2009-June-13, WC
 *         Moved over to ParrotIM project.
 *     2009-June-18, KF
 *         Added removeFriend().
 *         
 * Known Issues:
 *     1. Not documented thoroughly.
 * 
 * Copyright (C) 2009  Pirate Captains
 * 
 * Full license can be found in ParrotIM/LICENSE.txt.
 */


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
    
    /**
     * Determines whether chat bot is on or not. Defaults to off.
     */
    private boolean chatbotEnabled;
    
    public CurrentProfileData() {
        this.accountData = new ArrayList<AccountData>();
        this.profileName = EMPTY_NAME;
        this.chatbotEnabled = false;
}
    
    public CurrentProfileData(ArrayList<AccountData> accountData,
                              String profileName) {
    	this.accountData = accountData;
    	this.profileName = profileName;
    	this.chatbotEnabled = false;
    }
    
    public CurrentProfileData(AccountData account, String profileName) {
        this.accountData = new ArrayList<AccountData>();
        this.accountData.add(account);
        this.profileName = profileName;
        this.chatbotEnabled = false;
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
    
    @Override
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
    
    public AccountData getAccountFromServer(ServerType server) {
        AccountData toReturn = null;
        for (AccountData a : this.accountData) {
            if (a.getServer() == server) {
                toReturn = a;
                break;
            }
        }
        
        return toReturn; 
    }
    
    /**
     * Searches all accounts for a friend, and removes it upon
     * finding the first occurrence.
     * 
     * @param user  The UserData representation of the friend to remove.
     * @return true if removed, false otherwise.
     */
    public boolean removeFriend(UserData user) {
        boolean success = false;
        
        for (AccountData a : this.accountData) {
            if (a.friendExists(user)) {
                success = a.removeFriend(user);
                break;
            }
        }
       
        return success;
    }

    /**
     * Sets chat bot. This variable should be checked whenever
     * a message is received to determine whether a chat bot replies
     * to it.
     * 
     * @param chatbotEnabled
     */
    public void setChatbotEnabled(boolean chatbotEnabled) {
        this.chatbotEnabled = chatbotEnabled;
        
        return;
    }

    /**
     * Gets whether chatbot is on or not. Check while receiving a
     * message.
     * 
     * @return
     */
    public boolean isChatbotEnabled() {
        return chatbotEnabled;
    }
}