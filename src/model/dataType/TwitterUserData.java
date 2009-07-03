package model.dataType;

import model.enumerations.ServerType;

public class TwitterUserData extends UserData {
    // Section
    // I - Data Members

    // Section
    // II - Constructors

    /**
     * Creates a new user with a userID, nickname, and status.
     * 
     * @param userID
     * @param nickname
     * @param status
     */
    public TwitterUserData(String userID, String status) {
        super(userID, userID, status);
    }

    /**
     * Creates a new user with only a userID.
     * 
     * @param userID
     */
    public TwitterUserData(String userID) {
        super(userID);
    }

    // Section
    // III - Accessors and Mutators

    public String serverTypeToString() {
        return ServerType.TWITTER.toString();
    }
    
}
