package controller.services;


import java.io.File;
import java.net.URL;
import java.util.*;
import java.util.regex.*;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;

import net.kano.joscar.rv.RvProcessor;
import net.kano.joustsim.Screenname;
import net.kano.joustsim.oscar.AimConnection;
import net.kano.joustsim.oscar.AimConnectionProperties;
import net.kano.joustsim.oscar.AimSession;
import net.kano.joustsim.oscar.AppSession;
import net.kano.joustsim.oscar.DefaultAimSession;
import net.kano.joustsim.oscar.oscar.service.icbm.IcbmListener;

import org.jivesoftware.smack.XMPPException;

import model.dataType.tempData.FriendTempData;
import model.enumerations.ServerType;
import model.enumerations.UserStateType;

public class ICQManager implements GenericConnection {
	private static final String ICQ_SERVER = "login.messaging.aol.com";
    private static final int ICQ_PORT = 5190;
    
    private AimConnection connection;
    
	private AimConnectionProperties connectionProperties = new AimConnectionProperties(null, null);
	
	private RvProcessor rvProcessor;
	
	private IcbmListener lastIcbmListener;
    
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
    public void login(String userID, String password, String server, int port)
            throws BadConnectionException {
    	AppSession appSession = new AppSession() {
            public AimSession openAimSession(Screenname sn) {
                return new DefaultAimSession(this, sn) {
                    // todo finish off secure stuff
                    //                    public TrustPreferences getTrustPreferences() {
                    //                        return new PermanentSignerTrustManager(screenName);
                    //                    }
                };
            }
        };
		Screenname screenName = new Screenname(userID);
		AimSession session = appSession.openAimSession(screenName);
        connectionProperties.setScreenname(screenName);
        connectionProperties.setPassword(password);
        connectionProperties.setLoginHost(System.getProperty("OSCAR_HOST", server));
        connectionProperties.setLoginPort(Integer.getInteger("OSCAR_PORT", port));
        connection = session.openConnection(connectionProperties);
        connection.connect();

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

    public void login(String userID, String password)
            throws BadConnectionException {
        // TODO Auto-generated method stub
        
    }

}
