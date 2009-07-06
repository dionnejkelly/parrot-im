package model.dataType;

import java.util.ArrayList;

import model.enumerations.UserStateType;
import controller.services.GenericConnection;
import controller.services.JabberManager;
import controller.services.TwitterManager;

public class TwitterAccountData extends AccountData implements TwitterPerson {

    /**
     * A friend list of this account.
     */
    private ArrayList<TwitterUserData> friends;

    /**
     * Holds the object that allows for connection to the server.
     */
    private TwitterManager connection;

    private int minutesSinceUpdate;
    
    public TwitterAccountData(String userID, String password,
            TwitterManager connection) {
        super(userID, password);

        if (connection == null) {
            throw new IllegalArgumentException();
        }

        this.friends = new ArrayList<TwitterUserData>();
        this.connection = connection;
    }

    public TwitterAccountData(String userID, String nickname, String status,
            String password, UserStateType state, TwitterManager connection) {
        super(userID, nickname, status, state, password);

        if (connection == null) {
            throw new IllegalArgumentException();
        }
        
        this.friends = new ArrayList<TwitterUserData>();
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
        TwitterUserData twitterFriend = null;

        if (friend != null && friend instanceof TwitterUserData) {
            twitterFriend = (TwitterUserData) friend;
            if (!friendExists(twitterFriend)) {
                this.friends.add(twitterFriend);
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

        for (TwitterUserData u : this.friends) {
            if (u.isDuplicate(exFriendUserID)) {
                removed = this.friends.remove(u);
                break;
            }
        }

        return removed;
    }

    public void setMinutesSinceUpdate(int minutesSinceUpdate) {
        this.minutesSinceUpdate = minutesSinceUpdate;
    }

    public int getMinutesSinceUpdate() {
        return minutesSinceUpdate;
    }


}
