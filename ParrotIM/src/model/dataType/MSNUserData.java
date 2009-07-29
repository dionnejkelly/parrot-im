package model.dataType;

import model.enumerations.ServerType;
import model.enumerations.UserStateType;

public class MSNUserData extends UserData implements MSNPerson {

    public MSNUserData(String userID) {
        super(userID);
    }

    /**
     * Creates a new user with a userID, nickname, and status.
     * 
     * @param accountName
     * @param nickname
     * @param status
     */
    public MSNUserData(String userID, String nickname, String status,
            UserStateType state, boolean blocked) {
        super(userID, nickname, status, state, blocked);
    }

    // Section
    // III - Accessors and Mutators

    public ServerType getServer() {
        return ServerType.MSN;
    }

}
