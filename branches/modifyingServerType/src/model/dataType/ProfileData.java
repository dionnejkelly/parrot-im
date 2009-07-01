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

import java.util.ArrayList;

import model.enumerations.ServerType;
import model.enumerations.UserStateType;

/**
 * Holds the account information of the current account being used, whether it
 * is one stored in the database or a guest account.
 */
public class ProfileData {

    // Section:
    // I - Constants

    private final static String EMPTY_NAME = "<empty>";

    // Section:
    // II - Data Members

    /**
     * All accounts attached to the profile. Stored in an ArrayList.
     */
    private ArrayList<AccountData> accountData;

    /**
     * The name of the current profile. Not necessarily a userID of an account.
     */
    private String profileName;

    private String status;

    private UserStateType state;

    /**
     * Determines whether chatbot is on or not. Defaults to off.
     */
    private boolean chatbotEnabled;

    // Section:
    // III - Constructors

    /**
     * Creates a new profile with no valid accounts or information. The name is
     * set to a default "empty" name.
     */
    public ProfileData() {
        this.accountData = new ArrayList<AccountData>();
        this.profileName = EMPTY_NAME;
        this.chatbotEnabled = false;
    }

    /**
     * Creates a new profile with no valid accounts or information. The name is
     * set to the passed in name.
     */
    public ProfileData(String profileName) {
        this.accountData = new ArrayList<AccountData>();
        this.profileName = profileName;
        this.chatbotEnabled = false;
    }

    /**
     * Creates a new profile with all valid accounts and a name. The accounts
     * are passed in as an ArrayList. This list is not checked for validity.
     * 
     * @param accountData
     * @param profileName
     */
    public ProfileData(
            ArrayList<AccountData> accountData, String profileName) {
        this.accountData = accountData;
        this.profileName = profileName;
        this.chatbotEnabled = false;
    }

    /**
     * Creates a new profile with only one account, and a name.
     * 
     * @param account
     * @param profileName
     */
    public ProfileData(AccountData account, String profileName) {
        this.accountData = new ArrayList<AccountData>();
        this.accountData.add(account);
        this.profileName = profileName;
        this.chatbotEnabled = false;
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
    public void setProfileName(String profileName) {
        this.profileName = profileName;
        return;
    }

    /**
     * Gets the name of the profile.
     * 
     * @return A string of the name of the profile.
     */
    public String getProfileName() {
        return profileName;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setState(UserStateType state) {
        this.state = state;
    }

    public UserStateType getState() {
        return state;
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

    // Section
    // V - Account Manipulation Methods

    /**
     * Checks whether the profile already holds an account with the same userID.
     * 
     * @return True if a match is found; false otherwise.
     */
    public boolean accountExists(String userID) {
        boolean foundAccount = false;

        for (AccountData a : this.accountData) {
            if (a.getAccountName().equalsIgnoreCase(userID)) {
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
        if (!this.accountExists(account.getAccountName())) {
            this.accountData.add(account);
        } else {
            // TODO throw a DuplicateAccountException, or something
            // similar.
        }

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
        return this.accountData.remove(account);
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
            for (UserData user : account.getFriends()) {
                friends.add(user);
            }
        }
        return friends;
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
     * Gets a string representation of the profile. Set to return the name only.
     * 
     * @return The name of the profile.
     */
    @Override
    public String toString() {
        return profileName;
    }
}