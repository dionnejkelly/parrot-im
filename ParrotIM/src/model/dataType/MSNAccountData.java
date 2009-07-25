package model.dataType;

import java.util.ArrayList;

import model.enumerations.ServerType;
import model.enumerations.UserStateType;
import controller.services.BadConnectionException;
import controller.services.GenericConnection;
import controller.services.MSNManager;


public class MSNAccountData extends AccountData implements MSNPerson {

    /**
     * A friend list of this account.
     */
    private ArrayList<MSNUserData> friends;

    /**
     * Holds the object that allows for connection to the server.
     */
    private MSNManager connection;

    private int minutesSinceUpdate;
    
    public MSNAccountData(String userID, String password) {
        super(userID, password);

        this.friends = new ArrayList<MSNUserData>();
        this.connection = null;
    }

    public MSNAccountData(String userID, String nickname, String status,
            String password, UserStateType state, MSNManager connection) {
        super(userID, nickname, status, state, password);

        if (connection == null) {
            throw new IllegalArgumentException();
        }
        
        this.friends = new ArrayList<MSNUserData>();
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
        if (connection != null && connection instanceof MSNManager) {
            this.connection = (MSNManager) connection;
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
        MSNUserData msnFriend = null;

        if (friend != null && friend instanceof MSNUserData) {
            msnFriend = (MSNUserData) friend;
            if (!friendExists(msnFriend)) {
                this.friends.add(msnFriend);
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

        for (MSNUserData u : this.friends) {
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

    public void setMinutesSinceUpdate(int minutesSinceUpdate) {
        this.minutesSinceUpdate = minutesSinceUpdate;
    }

    public int getMinutesSinceUpdate() {
        return minutesSinceUpdate;
    }

    public int hashCode() {
        int hash = "MSN".hashCode();
        
        hash = hash * 31 + super.hashCode();
        
        return hash;
    }
    
    public boolean equals(Object o) {
        boolean areEqual = super.equals(o);
        
        if (areEqual) {
            areEqual = o instanceof MSNAccountData;
        }
        
        return areEqual;
    }

    public ServerType getServer() {
        return ServerType.MSN;
    }

	@Override
	public ArrayList<UserData> getTweets() throws BadConnectionException {
		return null;
	}
}
