package model.dataType;

import java.util.ArrayList;

import model.enumerations.ServerType;
import model.enumerations.UserStateType;
import controller.services.BadConnectionException;
import controller.services.GenericConnection;
import controller.services.GoogleTalkManager;
import controller.services.JabberManager;

public class JabberAccountData extends AccountData implements JabberPerson {

    /**
     * A friend list of this account.
     */
    private ArrayList<JabberUserData> friends;

    /**
     * Holds the object that allows for connection to the server.
     */
    private JabberManager connection;

    public JabberAccountData(String userID, String password) {
        super(userID, password);

        this.friends = new ArrayList<JabberUserData>();
        this.connection = null;
    }

    public JabberAccountData(String userID, String nickname, String status,
            String password, UserStateType state, JabberManager connection) {
        super(userID, nickname, status, state, password);

        if (connection == null) {
            throw new IllegalArgumentException();
        }
        
        this.friends = new ArrayList<JabberUserData>();
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
        if (connection != null && connection instanceof JabberManager) {
            this.connection = (JabberManager) connection;
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
        JabberUserData jabberFriend = null;

        if (friend != null && friend instanceof JabberUserData) {
            jabberFriend = (JabberUserData) friend;
            if (!friendExists(jabberFriend)) {
                this.friends.add(jabberFriend);
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

        for (JabberUserData u : this.friends) {
            if (u.isDuplicate(exFriendUserID)) {
                removed = this.friends.remove(u);
                break;
            }
        }

        return removed;
    }

    public int hashCode() {
        int hash = "Jabber".hashCode();
        
        hash = hash * 31 + super.hashCode();
        
        return hash;
    }
    
    public boolean equals(Object o) {
        boolean areEqual = super.equals(o);
        
        if (areEqual) {
            areEqual = o instanceof JabberAccountData;
        }
        
        return areEqual;
    }

    public ServerType getServer() {
        return ServerType.JABBER;
    }

	@Override
	public ArrayList<UserData> getTweets() throws BadConnectionException {
		return null;
	}
}
