package model.dataType;

import java.util.Date;

import model.enumerations.ServerType;
import model.enumerations.UserStateType;

public class TwitterUserData extends UserData implements TwitterPerson {

    // Section
    // I - Data Members

    private int minutesSinceUpdate;
    private Date datePosted;

    // Section
    // II - Constructors

    /**
     * Creates a new user with only a userID.
     * 
     * @param userID
     */
    public TwitterUserData(String userID) {
        super(userID);
        this.minutesSinceUpdate = 16384;
    }

    /**
     * Creates a new user with a userID, nickname, and status.
     * 
     * @param userID
     * @param nickname
     * @param status
     */
    public TwitterUserData(String userID, String status, Date date) {
        super(userID, userID, status, UserStateType.OFFLINE, false);
        this.minutesSinceUpdate = 16384;
        this.datePosted = date;
    }

    // Section
    // III - Accessors and Mutators

    public void setMinutesSinceUpdate(int minutesSinceUpdate) {
        this.minutesSinceUpdate = minutesSinceUpdate;
    }

    public int getMinutesSinceUpdate() {
        return minutesSinceUpdate;
    }

    public ServerType getServer() {
        return ServerType.TWITTER;
    }

    public Date getDatePosted() {
        return datePosted;
    }

}
