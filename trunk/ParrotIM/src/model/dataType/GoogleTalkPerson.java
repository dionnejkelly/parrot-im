package model.dataType;

import model.enumerations.UserStateType;

public interface GoogleTalkPerson {
    
    public UserStateType getState();
    
    public void setState(UserStateType state);
}
