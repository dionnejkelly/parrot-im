package controller.services;

import com.aol.acc.*;
import java.util.*;
import java.util.regex.*;

import model.dataType.tempData.FriendTempData;
import model.enumerations.UserStateType;



public class ICQManager implements GenericConnection {

	@Override
	public void addFriend(String userID) throws BadConnectionException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void changeStatus(UserStateType state, String status)
			throws BadConnectionException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void disconnect() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void isTyping() throws BadConnectionException{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void login(String userID, String password, String server, int port)
			throws BadConnectionException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean removeFriend(String userID) throws BadConnectionException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ArrayList<FriendTempData> retrieveFriendList()
			throws BadConnectionException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserStateType retrieveState(String userID)
			throws BadConnectionException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String retrieveStatus(String userID) throws BadConnectionException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void sendMessage(String toUserID, String message)
			throws BadConnectionException {
		// TODO Auto-generated method stub
		
	}

}
