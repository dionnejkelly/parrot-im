package model.dataType;

import java.util.ArrayList;

import model.enumerations.ServerType;

import controller.services.BadConnectionException;
import controller.services.GenericConnection;
import controller.services.GoogleTalkManager;

public class GoogleTalkAccountData extends AccountData {

    private GoogleTalkManager connection;

    private ArrayList<GoogleTalkUserData> friends;

    public GoogleTalkAccountData(String userID, String password,
            GoogleTalkManager connection) {
        super(userID, password);
        this.connection = connection;
    }

    @Override
    public GenericConnection getConnection() {
        return this.connection;
    }

    @Override
    public void setConnection(GenericConnection connection)
            throws BadConnectionException {

        try {
            this.connection = (GoogleTalkManager) connection;
        } catch (ClassCastException e) {
            System.err.println("Wrong GenericConnection type");
            e.printStackTrace();
            throw new BadConnectionException();
        }

        return;
    }

    @Override
    public ServerType getServer() {
        return ServerType.GOOGLE_TALK;
    }

    @Override
    public boolean addFriend(UserData friend) throws UserMismatchException {
        boolean isUniqueFriend = false;

        try {
            if (!this.friends.contains(friend)) {
                this.friends.add((GoogleTalkUserData) friend);
                isUniqueFriend = true;
            }
        } catch (ClassCastException e) {
            System.err.println("Wrong friend type to add");
            e.printStackTrace();
            throw new UserMismatchException();
        }

        return isUniqueFriend;
    }


    @Override
    public boolean removeFriend(UserData exFriend) {
        return this.friends.remove(exFriend);
    }

    @Override
    public ArrayList<UserData> getFriends() {
        ArrayList<UserData> friendsToReturn = new ArrayList<UserData>();
        
        friendsToReturn.addAll(friendsToReturn);
        
        return friendsToReturn;
    }
    
}
