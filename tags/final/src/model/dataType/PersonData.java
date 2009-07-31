/* PersonData.java
 * 
 * Programmed By:
 *     Kevin Fahy
 *     
 * Change Log:
 *     2009-July-6, KF
 *         First write. Holds base data for Users and Accounts.
 *         
 * Known Issues:
 *     none
 *     
 * Copyright (C) 2009  Pirate Captains
 * 
 * License: GNU General Public License version 2.
 * Full license can be found in ParrotIM/LICENSE.txt.
 */

package model.dataType;

import java.util.Observable;

import model.enumerations.ServerType;
import model.enumerations.TypingStateType;
import model.enumerations.UserStateType;

/**
 * Holds all base data and is the parent of AccountData and UserData classes.
 * Contains the common attributes of all person objects, such as userIDs,
 * nicknames, and statuses. Also contains utility methods for searching and
 * equivalence tests.
 */
public abstract class PersonData extends Observable {

    /**
     * The screen name, or username, of the person. May be in the form of an
     * email address. This userID is used to log-in to the server.
     */
    private String userID;

    /**
     * A shortened, or customized, name that represents a person. May or may not
     * be equal to the userID. Is unrelated to log-in information.
     */
    private String nickname;

    private String group;
    /**
     * A person's customized status message. May be empty.
     */
    private String status;

    /**
     * The state of the user (e.g. Available, Away, Offline).
     */
    private UserStateType state;
    private TypingStateType typingState;
    private boolean connected;

    /**
     * Base constructor. The userID is required since it is needed to log-in and
     * authenticate users. The nickname defaults to equal the userID. It is
     * recommended that a separate nickname is chosen.
     * 
     * @param userID
     *            The screen name or username used for log-in.
     */
    public PersonData(String userID) {
        if (userID == null || userID.length() == 0) {
            throw new IllegalArgumentException();
        }
        this.connected = false;
        this.userID = userID;
        this.nickname = userID;
        this.status = "";
        this.state = UserStateType.OFFLINE;
        this.typingState = TypingStateType.ACTIVE;
        this.setGroup("");
    }

    /**
     * The full constructor. A valid userID is required. The nickname and status
     * are typically grabbed from a server, but can be set to anything.
     * 
     * @param userID
     *            The screen name or username used for log-in.
     * @param nickname
     *            A less formal name to represent the person.
     * @param status
     *            A custom status message for the person.
     * @param state
     *            The online state of the user.
     */
    public PersonData(String userID, String nickname, String status,
            UserStateType state) {
        if (userID == null || userID.length() == 0) {
            throw new IllegalArgumentException();
        }

        this.userID = userID;
        this.setNickname(nickname);
        this.setStatus(status);
        this.setState(state);
        this.typingState = TypingStateType.ACTIVE;
        this.setGroup("");
    }

    public PersonData(String userID, String nickname, String status,
            UserStateType state, String group) {
        this.userID = userID;
        this.setNickname(nickname);
        this.setStatus(status);
        this.setState(state);
        this.typingState = TypingStateType.ACTIVE;
        this.setGroup(group);
    }

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean b) {
        connected = b;
    }

    /**
     * Gets the userID as a String. Cannot be null or empty.
     * 
     * @return A String representing the userID.
     */
    public String getUserID() {
        return this.userID;
    }

    /**
     * Gets the nickname as a String. Will not return null; an empty String will
     * be returned if the nickname is null.
     * 
     * @return The nickname as a String.
     */
    public String getNickname() {
        return this.nickname;
    }

    /**
     * Sets the nickname by passing in a String.
     * 
     * @param nickname
     *            A String representing the nickname.
     */
    public void setNickname(String nickname) {
        this.nickname = nickname != null ? nickname : "";

        super.setChanged();
        super.notifyObservers();

        return;
    }

    /**
     * Gets the status as a String. Will not return a null string; a null string
     * will be changed to the empty string.
     * 
     * @return The status as a String.
     */
    public String getStatus() {
        return this.status;
    }

    /**
     * Sets the status by passing in a String. Can be null, but the returned
     * string in PersonData.getString() will never be null.
     * 
     * @param status
     *            The status as a String.
     */
    public void setStatus(String status) {
        this.status = status != null ? status : "";

        super.setChanged();
        super.notifyObservers();

        return;
    }

    /**
     * Sets the state of the user.
     * 
     * @param state
     *            This is similar to the status, but shows a state, such as
     *            online, away, or busy, that a user is in. A user can be both
     *            busy (state) and have a "Working on STAT" message (status)
     *            displayed. Requires a UserStateType enum to be passed in; that
     *            is, the types of states are limited.
     */
    public void setState(UserStateType state) {
        this.state = state != null ? state : UserStateType.OFFLINE;

        super.setChanged();
        super.notifyObservers();

        return;
    }

    /**
     * Gets the user's state.
     * 
     * @return The state of the user as a UserStateType enum.
     */
    public UserStateType getState() {
        return this.state;
    }

    public boolean hasEmptyStatus() {
        return this.status == null || this.status.length() == 0;
    }

    /**
     * get Chat State like is typing, active and pause
     * 
     * @return a string of the state have different state depends on the service
     */
    public String getTypingState() {
        return typingState.toString();
    }

    public void setTypingState(TypingStateType state) {
        this.typingState = state != null ? state : TypingStateType.ACTIVE;
    }

    public boolean isDuplicate(PersonData person) {
        return this.userID.equalsIgnoreCase(person.getUserID());
    }

    public boolean isDuplicate(String userID) {
        return this.userID.equalsIgnoreCase(userID);
    }

    public boolean isOnline() {
        return this.state != UserStateType.OFFLINE;
    }

    public abstract ServerType getServer();

    public static String delimitUserBack(String from, char character) {
        String subString = from.substring(from.indexOf(character) + 1);

        return subString;
    }

    public static String delimitUserFront(String from, char character) {
        String subString = from.substring(0, from.indexOf(character));

        return subString;
    }

    /**
     * Converts the user to a String, returning the userID.
     * 
     * @return The string representation for a user. We have chosen the userID
     *         to be returned by default.
     */
    public String toString() {
        return this.userID;
    }

    public int hashCode() {
        int hash = 7;

        hash = hash * 31 + this.userID.toLowerCase().hashCode();

        return hash;
    }

    public boolean equals(Object o) {
        boolean areEqual = false;
        PersonData otherPerson = null;

        if (o != null && o instanceof PersonData) {
            otherPerson = (PersonData) o;
            areEqual = this.userID.equalsIgnoreCase(otherPerson.getUserID());
        }

        return areEqual;
    }

    public void setGroup(String group) {
        this.group = group;

    }

    public String getGroup() {

        return group;
    }
}
