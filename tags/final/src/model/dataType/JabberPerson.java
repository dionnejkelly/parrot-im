package model.dataType;

import model.enumerations.UserStateType;

public interface JabberPerson {

    public UserStateType getState();

    public void setState(UserStateType state);
}
