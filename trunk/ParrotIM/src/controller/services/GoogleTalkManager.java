package controller.services;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

public class GoogleTalkManager implements GenericConnection {
    
    private static final String GOOGLE_SERVER = "talk.google.com";
    private static final int GOOGLE_PORT = 5223;
    private static final String GOOGLE_DOMAIN = "gmail.com";
    
    private XMPPConnection connection;

    public GoogleTalkManager() {
        this.connection = null;
    }

    @Override
    public void addFriend(String userID) throws BadConnectionException {
        // TODO Auto-generated method stub

    }

    @Override
    public void disconnect() throws BadConnectionException {
        // TODO Auto-generated method stub

    }

    @Override
    public void login(String userID, String password)
            throws BadConnectionException {
        ConnectionConfiguration config = null;
        
        config = new ConnectionConfiguration(GOOGLE_SERVER, 5223, GOOGLE_DOMAIN);
        // Security enable?

        connection = new XMPPConnection(config);
        try {
            connection.connect();
            connection.login(userID, password);
        } catch (XMPPException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return;
    }

    @Override
    public void removeFriend(String userID) throws BadConnectionException {
        // TODO Auto-generated method stub

    }

}
