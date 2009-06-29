package controller.services;

import java.util.ArrayList;
import java.util.Collection;

import javax.net.ssl.SSLSocketFactory;

import model.dataType.MessageData;
import model.dataType.UserData;
import model.enumerations.UpdatedType;
import model.enumerations.UserStateType;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.RosterListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.packet.Presence.Mode;
import org.jivesoftware.smack.util.StringUtils;

import controller.MainController;

public class GoogleTalkManager implements GenericConnection {

    private static final String GOOGLE_SERVER = "talk.google.com";
    private static final int GOOGLE_PORT = 5223;
    private static final String GOOGLE_DOMAIN = "gmail.com";

    private XMPPConnection connection;

    private MainController controller;

    private GenericConnection genericConnection;

    private ArrayList<Chat> chats;

    public GoogleTalkManager(MainController controller) {
        this.connection = null;
        this.controller = controller;
        this.genericConnection = this;
        this.chats = new ArrayList<Chat>();
    }

    public void addFriend(String userID) throws BadConnectionException {
        // TODO Auto-generated method stub

    }

    public void disconnect() {
        connection.disconnect();

        return;
    }

    public void login(String userID, String password)
            throws BadConnectionException {
        ConnectionConfiguration config = null;

        config = new ConnectionConfiguration(GOOGLE_SERVER, GOOGLE_PORT,
                GOOGLE_DOMAIN);
        config.setSocketFactory(SSLSocketFactory.getDefault());

        connection = new XMPPConnection(config);
        try {
            connection.connect();
            connection.login(userID, password);
        } catch (XMPPException e) {
            System.err.println("Connection error in logging in.");
            throw new BadConnectionException();
        }

        // Setup the listeners for messages and buddy changes
        connection.addPacketListener(new MessagePacketListener(),
                new MessagePacketFilter());
        connection.getRoster().addRosterListener(new BuddyListener());

        return;
    }

    public void removeFriend(String userID) throws BadConnectionException {
        // TODO Auto-generated method stub

    }

    public String retrieveStatus(String userID) {
        String userStatus = ""; // default return value

        try {
            userStatus = this.connection.getRoster().getPresence(userID)
                    .getStatus();
        } catch (NullPointerException e) {
            System.err.println("Invalid connection or "
                    + "user in retrieveStatus()");
            userStatus = null;
        }

        return userStatus;
    }

    public UserStateType retrieveState(String userID) {
        UserStateType userState = UserStateType.OFFLINE; // default return value
        Presence userFromServer = null;
        Mode userStateFromServer = null;

        try {
            userFromServer = this.connection.getRoster().getPresence(userID);
            userStateFromServer = userFromServer.getMode();

            if (userStateFromServer == Presence.Mode.dnd) {
                userState = UserStateType.BUSY;
            } else if (userStateFromServer == Presence.Mode.away
                    || userStateFromServer == Presence.Mode.xa) {
                userState = UserStateType.AWAY;
            } else if (userFromServer.isAvailable()) {
                userState = UserStateType.ONLINE;
            } else { // user is offline
                userState = UserStateType.OFFLINE;
            }
        } catch (NullPointerException e) {
            System.err.println("Invalid connection or "
                    + "user in retrieveState()");
            userState = UserStateType.OFFLINE;
        }

        return userState;
    }

    public void sendMessage(String toUserID, String message)
            throws BadConnectionException {
        Chat ourChat = null;

        for (Chat c : this.chats) {
            if (c.getParticipant().equalsIgnoreCase(toUserID)) {
                ourChat = c;
                break;
            }
        }

        if (ourChat == null) {
            ourChat = connection.getChatManager().createChat(toUserID,
                    new MessageListener() {
                        public void processMessage(Chat chat, Message message) {
                            // Do nothing

                            return;
                        }
                    });
        }

        try {
            ourChat.sendMessage(message);
        } catch (XMPPException e) {
            System.err.println("Error in sending message.");
            throw new BadConnectionException();
        }

        return;
    }

    private class MessagePacketFilter implements PacketFilter {
        public boolean accept(Packet packet) {
            // TODO Is this the source of the name is null bug? check
            // if we are receiving packets that aren't messages that we need
            // to deal with.
            return (packet instanceof Message);
        }
    }

    /**
     * Changes to the roster, that is, changes to friends' statuses or
     * availability, are handled by this class.
     */
    private class BuddyListener implements RosterListener {

        /**
         * Displays which user is added to the entry.
         * 
         * @param addresses
         */
        public void entriesAdded(Collection<String> addresses) {
            // Fix me!
            System.out.println(addresses + " from entriesAdded");
            return;
        }

        /**
         * Displays which user is updated in the entry.
         * 
         * @param addresses
         */
        public void entriesUpdated(Collection<String> addresses) {
            // Fix me!
            System.out.println(addresses + " from entriesUpdated");
            return;
        }

        /**
         * Displays which user is deleted in the entry.
         * 
         * @param addresses
         */
        public void entriesDeleted(Collection<String> addresses) {
            // Fix me!
            System.out.println(addresses + " from entriesDeleted");
            return;
        }

        /**
         * Updates user's changed presence
         * 
         * @param presence
         */
        public void presenceChanged(Presence presence) {
            String bareAddress = StringUtils.parseBareAddress(presence
                    .getFrom());
            controller.friendUpdated(genericConnection, bareAddress);
            return;
        }
    }

    private class MessagePacketListener implements PacketListener {

        /**
         * Processes the incoming packet upon new arrival.
         * 
         * @param packet
         */

        public void processPacket(Packet packet) {
            Message message = (Message) packet;
            String fromUserID = StringUtils.parseBareAddress(message.getFrom());
            String toUserID = StringUtils
                    .parseBareAddress(connection.getUser());

            if (message.getBody() != null) {
                controller.messageReceived(fromUserID, toUserID, message
                        .getBody());
            }

            return;
        }
    }

}
