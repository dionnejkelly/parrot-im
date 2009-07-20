package model.dataType;

import model.enumerations.UserStateType;

public interface MSNPerson {
	
	public UserStateType getState();
    
    public void setState(UserStateType state);

}
