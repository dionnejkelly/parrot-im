package model.dataType;

import java.util.ArrayList;


import model.enumerations.ServerType;
import model.enumerations.UserStateType;
import controller.services.BadConnectionException;
import controller.services.GenericConnection;
import controller.services.ICQManager;

public class ICQAccountData extends AccountData implements ICQPerson {
    /**
     * A friend list of this account.
     */
    private ArrayList<ICQUserData> friends;

    /**
     * Holds the object that allows for connection to the server.
     */
    private ICQManager connection;

    public ICQAccountData(String userID, String password) {
        super(userID, password);

        this.friends = new ArrayList<ICQUserData>();
        this.connection = null;
    }

    public ICQAccountData(String userID, String nickname, String status,
            String password, UserStateType state, ICQManager connection) {
        super(userID, nickname, status, state, password);

        if (connection == null) {
            throw new IllegalArgumentException();
        }

        this.friends = new ArrayList<ICQUserData>();
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
        if (connection != null && connection instanceof ICQManager) {
            this.connection = (ICQManager) connection;
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
        ICQUserData googleFriend = null;

        if (friend != null && friend instanceof GoogleTalkUserData) {
            googleFriend = (ICQUserData) friend;
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

        for (ICQUserData u : this.friends) {
            if (u.isDuplicate(exFriendUserID)) {
                removed = this.friends.remove(u);
                break;
            }
        }

        return removed;
    }
    
    public int hashCode() {
        int hash = "ICQ".hashCode();
        
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
        return ServerType.ICQ;
    }

	@Override
	public ArrayList<UserData> getTweets() throws BadConnectionException {
		return null;
	}
}
