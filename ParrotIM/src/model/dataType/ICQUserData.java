package model.dataType;

import model.enumerations.ServerType;

public class ICQUserData extends UserData implements ICQPerson{

	public ICQUserData(String userID) {
		super(userID);
		// TODO Auto-generated constructor stub
	}

	@Override
	public ServerType getServer() {
		// TODO Auto-generated method stub
		return null;
	}

}
