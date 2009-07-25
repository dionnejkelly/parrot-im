/* ProfileData.java
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
 *     2009-June-24, KF
 *         Completed JavaDoc documentation. Added duplicate protection
 *         for accounts. Created a method to search for an existing
 *         account by the account's userID, and added ability to remove
 *         accounts.
 *         
 * Known Issues:
 *     1. An exception should be thrown upon adding a duplicate account.
 *     2. No methods to add an account; currently handled above this class.
 *        May want to add methods to make the program flow simpler.
 * 
 * Copyright (C) 2009  Pirate Captains
 * 
 * License: GNU General Public License version 2.
 * Full license can be found in ParrotIM/LICENSE.txt.
 */

package model.dataType;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Vector;

import controller.services.BadConnectionException;

import model.DatabaseFunctions;
import model.Model;
import model.dataType.tempData.AccountTempData;
import model.enumerations.ServerType;
import model.enumerations.UserStateType;

/**
 * Holds the account information of the current account being used, whether it
 * is one stored in the database or a guest account.
 */
public class ProfileData extends Observable {

    // Section:
    // II - Data Members

    /**
     * All accounts attached to the profile. Stored in an ArrayList.
     */
    private ArrayList<AccountData> accountData;

    /**
     * The name of the current profile. Not necessarily a userID of an account.
     */
    private String name;

    private String profilePassword;

    private boolean chatWindowHistoryEnabled;

    private boolean autoSignInEnabled;

    private boolean guestAccount;

    private boolean chatLogEnabled;

    private boolean soundsEnabled;
    
    private boolean emailEnabled;

    /**
     * Determines whether chatbot is on or not. Defaults to off.
     */
    private boolean chatbotEnabled;

    // Section:
    // III - Constructors

    /**
     * Creates a new profile with no valid accounts or information. The name is
     * set to the passed in name.
     */
    public ProfileData(String name) {
        this.accountData = new ArrayList<AccountData>();
        this.name = name;
        this.profilePassword = "";
        this.chatWindowHistoryEnabled = true;
        this.autoSignInEnabled = false;
        this.guestAccount = false;
        this.chatLogEnabled = true;
        this.soundsEnabled = true;
        this.chatbotEnabled = false;
        this.emailEnabled = true;
    }

    /**
     * Full constructor.
     */
    public ProfileData(String name, String profilePassword,
            boolean chatWindowHistoryEnabled, boolean autoSignInEnabled,
            boolean guestAccount, boolean chatLogEnabled,
            boolean soundsEnabled, boolean chatbotEnabled) {
        this.accountData = new ArrayList<AccountData>();
        this.name = name;
        this.profilePassword = profilePassword;
        this.chatWindowHistoryEnabled = chatWindowHistoryEnabled;
        this.autoSignInEnabled = autoSignInEnabled;
        this.guestAccount = guestAccount;
        this.chatLogEnabled = chatLogEnabled;
        this.soundsEnabled = soundsEnabled;
        this.chatbotEnabled = chatbotEnabled;
        this.emailEnabled = true;
    }

    // Section:
    // IV - Accessors and Mutators

    /**
     * Sets the account data. Changes the entire ArrayList. All old account data
     * is removed.
     * 
     * @param accountData
     *            An ArrayList of AccountData.
     */
    public void setAccountData(ArrayList<AccountData> accountData) {
        this.accountData = accountData;
        return;
    }

    /**
     * Gets all account data.
     * 
     * @return An ArrayList of all AccountData in the profile.
     */
    public ArrayList<AccountData> getAccountData() {
        return accountData;
    }

    /**
     * Changes the name of the profile.
     * 
     * @param profileName
     */
    public void setName(String name) {
        this.name = name;
        return;
    }

    /**
     * Gets the name of the profile.
     * 
     * @return A string of the name of the profile.
     */
    public String getName() {
        return this.name;
    }

    public void setStatus(String status) {
        for (AccountData a : this.accountData) {
            a.setStatus(status);
        }

        return;
    }

    public String getStatus() {
        // Temporary, need to incorporate all accounts
        return this.accountData.get(0).getStatus();
    }

    public void setState(UserStateType state) {
        for (AccountData a : this.accountData) {
            a.setState(state);
        }

        return;
    }

    public UserStateType getState() {
        return this.accountData.get(0).getState();
    }

    /**
     * Sets chatbot. This variable should be checked whenever a message is
     * received to determine whether a chat bot replies to it.
     * 
     * @param chatbotEnabled
     */
    public void setChatbotEnabled(boolean chatbotEnabled) {
        this.chatbotEnabled = chatbotEnabled;

        return;
    }

    /**
     * Gets whether chatbot is on or not. Check while receiving a message.
     * 
     * @return true if chatbot is on; false otherwise.
     */
    public boolean isChatbotEnabled() {
        return chatbotEnabled;
    }

    public boolean isChatWindowHistoryEnabled() {
        return chatWindowHistoryEnabled;
    }

    public void setChatWindowHistoryEnabled(boolean chatWindowHistoryEnabled) {
        this.chatWindowHistoryEnabled = chatWindowHistoryEnabled;
    }

    public boolean isPasswordEnabled() {
        return this.profilePassword != null
                && this.profilePassword.length() > 0;
    }

    public void disablePassword() {
        this.profilePassword = "";

        return;
    }

    public String getPassword() {
        return profilePassword;
    }

    public void setPassword(String profilePassword) {
        this.profilePassword = profilePassword != null ? profilePassword : "";
    }

    public boolean isAutoSignInEnabled() {
        return autoSignInEnabled;
    }

    public void setAutoSignInEnabled(boolean autoSignInEnabled) {
        DatabaseFunctions db = null;
        this.autoSignInEnabled = autoSignInEnabled;

        try {
            db = new DatabaseFunctions();
            db.setAutoSignIn(this.name, this.autoSignInEnabled);
        } catch (Exception e) {
            System.err.println("Database--ProfileData error.");
            e.printStackTrace();
        }

        return;
    }

    // Section
    // V - Account Manipulation Methods

    public boolean isGuestAccount() {
        return guestAccount;
    }

    public void setGuestAccount(boolean guestAccount) {
        this.guestAccount = guestAccount;

        // Do not allow guests to save chat logs
        if (guestAccount) {
            this.chatLogEnabled = false;
        }

        return;
    }

    public boolean isChatLogEnabled() {
        return chatLogEnabled;
    }

    public void setChatLogEnabled(boolean chatLogEnabled) {

        // Do not allow guests to save chat logs
        if (this.guestAccount) {
            chatLogEnabled = false;
        }

        this.chatLogEnabled = chatLogEnabled;

        return;
    }

    public boolean isSoundsEnabled() {
        return soundsEnabled;
    }

    public void setSoundsEnabled(boolean soundsEnabled) {
        this.soundsEnabled = soundsEnabled;
    }

    /**
     * Checks whether the profile already holds an account with the same userID.
     * 
     * @return True if a match is found; false otherwise.
     */
    public boolean accountExists(AccountData account) {
        boolean foundAccount = false;

        for (AccountData a : this.accountData) {
            if (a.equals(account)) {
                foundAccount = true;
                break;
            }
        }

        return foundAccount;
    }

    /**
     * Adds a new account to the account list. Should throw an exception if a
     * duplicate account is found. Duplicate is defined as: new account has the
     * same userID.
     * 
     * @param account
     */
    public void addAccount(AccountData account) {
        DatabaseFunctions db = null;

        if (!this.accountExists(account)) {
            this.accountData.add(account);

            try {
                db = new DatabaseFunctions();
                db.addUsers(this.getName(), account.getServer().toString(), "",
                        account.getUserID(), account.getPassword());
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        this.setChanged();
        this.notifyObservers();

        return;
    }

    /**
     * Removes an account by the AccountData passed in. If the same object
     * reference is not found, the account is not removed.
     * 
     * @param account
     * @return True is removed, false otherwise.
     */
    public boolean removeAccount(AccountData account) {
        DatabaseFunctions db = null;
        boolean removed = false;
        System.out.println("hello we got here: " + account.getUserID());
        if (account.isConnected())
        	account.getConnection().disconnect();
        removed = this.accountData.remove(account);

        if (removed) {
            try {
                db = new DatabaseFunctions();
                db
                        .removeAccountFromProfile(this.getName(), account
                                .getUserID());
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        this.setChanged();
        this.notifyObservers();

        return removed;
    }

    /**
     * Finds the first account that matches the ServerType. It is possible for
     * the account list to have more than one account from the same server--this
     * method only returns the first account it encounters.
     * 
     * @param server
     *            The ServerType of an account.
     * @return An AccountData object of the first account found, or null if not
     *         found.
     */
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

    // Section:
    // VI - Friend Manipulation Methods

    /**
     * Used to gather friends from all accounts inside of this class.
     * 
     * @return UserData of friends from all associated accounts.
     */
    public ArrayList<UserData> getAllFriends() {
        ArrayList<UserData> friends = new ArrayList<UserData>();
        for (AccountData account : this.accountData) {
        	if (account.isConnected()){
	            for (UserData user : account.getFriends()) {
	            	if (user.getServer() != ServerType.TWITTER)
	                	friends.add(user);
	            }
        	}
        }
        return friends;
    }
    
    public ArrayList<UserData> getTwitterFriends() {
        ArrayList<UserData> friends = new ArrayList<UserData>();
        for (AccountData account : this.accountData) {
            for (UserData user : account.getFriends()) {
            	if (user.getServer() == ServerType.TWITTER)
                	friends.add(user);
            }
        }
        return friends;
    }
    
    public ArrayList<UserData> getTweets() throws BadConnectionException {
        ArrayList<UserData> tweets = new ArrayList<UserData>();
        AccountData temp = this.getAccountFromServer(ServerType.TWITTER);
        if (temp != null && temp instanceof TwitterAccountData)
        	return temp.getTweets();
        else
        	return null;
    }

    /**
     * Searches all accounts for a friend, and removes it upon finding the first
     * occurrence.
     * 
     * @param user
     *            The UserData representation of the friend to remove.
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

    // Section
    // VII - Other Methods

    /**
     * Gets all accounts for a given profile. These accounts are returned in
     * objects that hold all parameters that were inside the database.
     * 
     * @param profile
     * @return An ArrayList of AccountTempData objects.
     */
    public void loadAccounts() {
        ArrayList<AccountTempData> accounts = null;
        DatabaseFunctions db = null;
        AccountData newAccount = null;

        // Grab accounts from the database
        try {
            db = new DatabaseFunctions();
            accounts = db.getAccountList(this.getName());
        } catch (Exception e) {
            System.err.println("Database error. No accounts added.");
            e.printStackTrace();
            accounts = new ArrayList<AccountTempData>();
        }

        // Add accounts to the profile.
        for (AccountTempData a : accounts) {
            newAccount = Model.createAccount(a.getUserID(), a.getPassword(), a
                    .getServer());
            this.addAccount(newAccount);
        }

        return;
    }

    /**
     * Gets a string representation of the profile. Set to return the name only.
     * 
     * @return The name of the profile.
     */
    public String toString() {
        return this.name;
    }

    public int hashCode() {
        int hash = 7;

        hash = hash * 31 + this.name.hashCode();

        return hash;
    }

    public boolean equals(Object o) {
        // Equal if same name
        boolean areEqual = false;
        ProfileData otherProfile = null;

        if (o != null && o instanceof ProfileData) {
            otherProfile = (ProfileData) o;
            areEqual = (this.name.equals(otherProfile.getName()));
        }

        return areEqual;
    }
    
    /**
     * Sets email notification. This variable should be checked whenever a new email
     * notification is receieved to determine whether the program display it.
     * 
     * @param chatbotEnabled
     */
    public void setEmailEnabled(boolean emailEnabled) {
        this.emailEnabled = emailEnabled;

        return;
    }

	public boolean isEmailEnabled() {
		
		return this.emailEnabled;
	}

	public Vector <ServerType> getAllAccountsServer(){
		Vector<ServerType> serverList = new Vector<ServerType>();
		
		//FIXME: right now I only consider about one account per server
		for (AccountData account : getAccountData()){
			serverList.add(account.getServer());
		}
		return serverList;
	}
}