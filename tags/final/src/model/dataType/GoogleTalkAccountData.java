package model.dataType;

import java.util.ArrayList;

import model.enumerations.ServerType;
import model.enumerations.UserStateType;
import controller.services.BadConnectionException;
import controller.services.GenericConnection;
import controller.services.GoogleTalkManager;

public class GoogleTalkAccountData extends AccountData implements
        GoogleTalkPerson {

    /**
     * A friend list of this account.
     */
    private ArrayList<GoogleTalkUserData> friends;

    /**
     * Holds the object that allows for connection to the server.
     */
    private GoogleTalkManager connection;

    public GoogleTalkAccountData(String userID, String password) {
        super(userID, password);

        this.friends = new ArrayList<GoogleTalkUserData>();
        this.connection = null;
    }

    public GoogleTalkAccountData(String userID, String nickname, String status,
            String password, UserStateType state, GoogleTalkManager connection) {
        super(userID, nickname, status, state, password);

        if (connection == null) {
            throw new IllegalArgumentException();
        }

        this.friends = new ArrayList<GoogleTalkUserData>();
        this.connection = connection;
    }

    public ArrayList<UserData> getFriends() {
        ArrayList<UserData> genericFriends = new ArrayList<UserData>();

        genericFriends.addAll(this.friends);

        return genericFriends;
    }

    /**
     * Sets the connection object that communicates with the server.
     * 
     * @return The connection object for this account.
     */
    public GenericConnection getConnection() {
        return connection;
    }

    public void setConnection(GenericConnection connection) {
        if (connection != null && connection instanceof GoogleTalkManager) {
            this.connection = (GoogleTalkManager) connection;
        }

        return;
    }

    /**
     * Adds a friend by UserData. Will not add duplicate entries, checked by
     * userID.
     * 
     * @param friend
     * @return true if successful, false otherwise. May return false due to a
     *         duplciate friend.
     */
    public boolean addFriend(UserData friend) {
        boolean notDuplicate = false;
        GoogleTalkUserData googleFriend = null;

        if (friend != null && friend instanceof GoogleTalkUserData) {
            googleFriend = (GoogleTalkUserData) friend;
            if (!friendExists(googleFriend)) {
                this.friends.add(googleFriend);
                notDuplicate = true;
            }
        }

        return notDuplicate;
    }

    /**
     * Removes a friend from the account. Searches by UserData.
     * 
     * @param exFriend
     *            The friend to remove.
     * 
     * @return true if removed, false otherwise
     */
    public boolean removeFriend(UserData exFriend) {
        return this.friends.remove(exFriend);
    }

    public boolean removeFriend(String exFriendUserID) {
        boolean removed = false;

        for (GoogleTalkUserData u : this.friends) {
            if (u.isDuplicate(exFriendUserID)) {
                removed = this.friends.remove(u);
                break;
            }
        }

        return removed;
    }

    public void removeAllFriends() {
        this.friends.clear();

        return;
    }

    public int hashCode() {
        int hash = "Google Talk".hashCode();

        hash = hash * 31 + super.hashCode();

        return hash;
    }

    public boolean equals(Object o) {
        boolean areEqual = super.equals(o);

        if (areEqual) {
            areEqual = o instanceof GoogleTalkAccountData;
        }

        return areEqual;
    }

    public ServerType getServer() {
        return ServerType.GOOGLE_TALK;
    }

    public ArrayList<UserData> getTweets() throws BadConnectionException {
        return null;
    }
}
