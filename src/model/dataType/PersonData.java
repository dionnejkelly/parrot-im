package model.dataType;

import model.enumerations.UserStateType;

public abstract class PersonData {

    protected String userID;

    protected String nickname;

    protected UserStateType state;

    protected String status;

    public PersonData(String userID) {
        this.userID = userID;
        this.nickname = userID;
        this.state = UserStateType.OFFLINE;
        this.status = "";
    }

    public PersonData(String userID, String nickname, UserStateType state,
            String status) {
        this.userID = userID;
        this.nickname = nickname;
        this.state = state;
        this.status = status;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public UserStateType getState() {
        return state;
    }

    public void setState(UserStateType state) {
        this.state = state;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Section break

    public boolean isOnline() {
        return state != UserStateType.OFFLINE;
    }

    @Override
    public String toString() {
        return this.userID;
    }

    @Override
    public boolean equals(Object person) {
        boolean areEqual = false;
        PersonData otherPerson = null;

        if (person != null && person instanceof PersonData) {
            otherPerson = (PersonData) person;
            areEqual = this.userID.equalsIgnoreCase(otherPerson.getUserID());
        }

        return areEqual;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = hash * 31 + this.userID.toLowerCase().hashCode();

        return hash;
    }

}