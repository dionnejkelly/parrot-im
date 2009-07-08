package controller.services;

//import com.aol.acc.*;
import java.io.File;
import java.net.URL;
import java.util.*;
import java.util.regex.*;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;

import org.jivesoftware.smack.XMPPException;

import model.dataType.tempData.FriendTempData;
import model.enumerations.ServerType;
import model.enumerations.UserStateType;

public class ICQManager implements GenericConnection {

    // @Override
    public void addFriend(String userID) throws BadConnectionException {
        // TODO Auto-generated method stub

    }

    // @Override
    public void changeStatus(UserStateType state, String status)
            throws BadConnectionException {
        // TODO Auto-generated method stub

    }

    // @Override
    public void disconnect() {
        // TODO Auto-generated method stub

    }

    // @Override
//    public boolean isTyping() {
//		return false;
//        // TODO Auto-generated method stub
//
//    }

    // @Override
    public void login(String userID, String password, String server, int port)
            throws BadConnectionException {
        // TODO Auto-generated method stub

    }

    // @Override
    public boolean removeFriend(String userID) throws BadConnectionException {
        // TODO Auto-generated method stub
        return false;
    }

    // @Override
    public ArrayList<FriendTempData> retrieveFriendList()
            throws BadConnectionException {
        // TODO Auto-generated method stub
        return null;
    }

    // @Override
    public UserStateType retrieveState(String userID)
            throws BadConnectionException {
        // TODO Auto-generated method stub
        return null;
    }

    // @Override
    public String retrieveStatus(String userID) throws BadConnectionException {
        // TODO Auto-generated method stub
        return null;
    }

    // @Override
    public void sendMessage(String toUserID, String message)
            throws BadConnectionException {
        // TODO Auto-generated method stub

    }

    public ImageIcon getAvatarPicture(String userID) throws XMPPException {
        // TODO Auto-generated method stub
        return null;
    }

    public ServerType getServerType() {
        return ServerType.ICQ;
    }

	public void setTypingState(int state, String UserID) throws BadConnectionException,
			XMPPException {
		// TODO Auto-generated method stub
		
	}

	public void setAvatarPicture(byte[] byeArray) throws XMPPException {
		// TODO Auto-generated method stub
		
	}

	public void setAvatarPicture(File file) throws XMPPException {
		// TODO Auto-generated method stub
		
	}

	public void setAvatarPicture(URL url) throws XMPPException {
		// TODO Auto-generated method stub
		
	}

}
