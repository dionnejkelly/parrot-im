package model.dataType;

import java.util.ArrayList;

import model.enumerations.ServerType;
import model.enumerations.UserStateType;
import winterwell.jtwitter.Twitter.Status;
import controller.services.BadConnectionException;
import controller.services.GenericConnection;
import controller.services.TwitterManager;

public class TwitterAccountData extends AccountData implements TwitterPerson {

    /**
     * A friend list of this account.
     */
    private ArrayList<TwitterUserData> friends;
    private ArrayList<UserData> tweets;

    /**
     * Holds the object that allows for connection to the server.
     */
    private TwitterManager connection;

    private int minutesSinceUpdate;

    public TwitterAccountData(String userID, String password) {
        super(userID, password);

        this.friends = new ArrayList<TwitterUserData>();
        this.connection = null;
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

    public ArrayList<UserData> getTweets() throws BadConnectionException {
        tweets = new ArrayList<UserData>();
        for (Status temp : connection.retrieveRecentTweets()) {
            tweets.add(new TwitterUserData(temp.getUser().getName(), temp
                    .getText(), temp.getCreatedAt()));
        }
        return tweets;
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
        if (connection != null && connection instanceof TwitterManager) {
            this.connection = (TwitterManager) connection;
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
        TwitterUserData twitterFriend = null;

        if (friend != null && friend instanceof TwitterUserData) {
            twitterFriend = (TwitterUserData) friend;
            if (!friendExists(twitterFriend)) {
                this.friends.add(twitterFriend);
                notDuplicate = true;
            }
        }

        setChanged();
        notifyObservers();

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
        setChanged();
        notifyObservers();

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

        setChanged();
        notifyObservers();

        return removed;
    }

    public void removeAllFriends() {
        this.friends.clear();
        this.tweets.clear();

        return;
    }

    public void setMinutesSinceUpdate(int minutesSinceUpdate) {
        this.minutesSinceUpdate = minutesSinceUpdate;
    }

    public int getMinutesSinceUpdate() {
        return minutesSinceUpdate;
    }

    public int hashCode() {
        int hash = "Twitter".hashCode();

        hash = hash * 31 + super.hashCode();

        return hash;
    }

    public boolean equals(Object o) {
        boolean areEqual = super.equals(o);

        if (areEqual) {
            areEqual = o instanceof TwitterAccountData;
        }

        return areEqual;
    }

    public ServerType getServer() {
        return ServerType.TWITTER;
    }
}
