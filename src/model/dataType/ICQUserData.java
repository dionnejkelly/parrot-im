package model.dataType;

import model.enumerations.ServerType;
import model.enumerations.UserStateType;

public class ICQUserData extends UserData implements ICQPerson {

    public ICQUserData(String userID) {
        super(userID);
    }

    public ICQUserData(String userID, String nickname, String status,
            UserStateType state, boolean blocked) {
        super(userID, nickname, status, state, blocked);
    }

    public ServerType getServer() {
        return ServerType.ICQ;
    }

}
