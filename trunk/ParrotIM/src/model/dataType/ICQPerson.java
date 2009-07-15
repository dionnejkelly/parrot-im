package model.dataType;

import model.enumerations.UserStateType;

public interface ICQPerson {
    
    public UserStateType getState();
    
    public void setState(UserStateType state);
    
}
