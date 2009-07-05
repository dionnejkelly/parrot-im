package controller.services;

import java.util.ArrayList;
import java.util.Collection;

import javax.net.ssl.SSLSocketFactory;
import javax.swing.ImageIcon;

import model.dataType.tempData.FriendTempData;
import model.enumerations.UserStateType;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
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

public class JabberManager implements GenericConnection {

    private static final int DEFAULT_PORT = 5223;

    private XMPPConnection connection;

    private MainController controller;

    private GenericConnection genericConnection;

    private ArrayList<Chat> chats;

    private String server;

    private String domain;

    public JabberManager(MainController controller) {
        this.connection = null;
        this.controller = controller;
        this.genericConnection = this;
        this.chats = new ArrayList<Chat>();
    }

    public void addFriend(String userID) throws BadConnectionException {
        Roster roster = null;
        String nickname = null;

        nickname = StringUtils.parseBareAddress(userID);

        roster = connection.getRoster();
        if (!roster.contains(userID)) {
            try {
                roster.createEntry(userID, nickname, null);
            } catch (XMPPException e) {
                System.err.println("Error in adding friend");
                throw new BadConnectionException();
            }
        }

        return;
    }

    public void disconnect() {
        connection.disconnect();

        return;
    }

    public void login(String userID, String password, String server, int port)
            throws BadConnectionException {
        ConnectionConfiguration config = null;

        this.domain = StringUtils.parseServer(userID); // will this work?
        this.server = server;
        userID = StringUtils.parseName(userID); // to make it work with sfu...
                                                // check for others
        // port currently not assigned

        config = new ConnectionConfiguration(this.server, DEFAULT_PORT,
                this.domain);
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

    public boolean removeFriend(String userID) throws BadConnectionException {
        boolean removed = false; // Default return value
        Roster roster = this.connection.getRoster();

        for (RosterEntry r : roster.getEntries()) {
            if (r.getUser().equalsIgnoreCase(userID)) {
                try {
                    roster.removeEntry(r);
                } catch (XMPPException e) {
                    System.err.println("Error in removing friend.");
                    throw new BadConnectionException();
                }
                removed = true;
                break;
            }
        }

        return removed;
    }

    public void changeStatus(UserStateType state, String status) {
        Presence presence = new Presence(Presence.Type.available);
        if (state == UserStateType.ONLINE) {
            presence.setMode(Presence.Mode.available);
        } else if (state == UserStateType.AWAY) {
            presence.setMode(Presence.Mode.away);
        } else if (state == UserStateType.BUSY) {
            presence.setMode(Presence.Mode.dnd);
        } else {
            presence.setMode(Presence.Mode.chat);
        }

        presence.setStatus(status);
        connection.sendPacket(presence);

        return;
    }

    public String retrieveStatus(String userID) {
        String userStatus = ""; // default return value

        try {
            userStatus = this.connection.getRoster().getPresence(userID)
                    .getStatus();
        } catch (NullPointerException e) {
            System.err.println("Invalid connection or "
                    + "user in retrieveStatus()");
            userStatus = "";
        }

        // Server may set their status to null; we want empty string
        if (userStatus == null) {
            userStatus = "";
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

    public ArrayList<FriendTempData> retrieveFriendList() {
        ArrayList<FriendTempData> friends = new ArrayList<FriendTempData>();
        FriendTempData friendToAdd = null;
        String userID = null;
        Roster roster = null;

        roster = this.connection.getRoster();

        for (RosterEntry r : roster.getEntries()) {
            userID = r.getUser();
            friendToAdd = new FriendTempData(userID, r.getName(), this
                    .retrieveStatus(userID), this.retrieveState(userID), false);
            friends.add(friendToAdd);
        }

        return friends;
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

    @Override
    public int hashCode() {
        int hash = 7;

        hash = hash * 31 + "Google".hashCode();
        hash = hash * 31 + this.connection.hashCode();

        return hash;
    }

//	@Override
	public void isTyping() throws BadConnectionException, XMPPException {
		// TODO Auto-generated method stub
		
	}

	public ImageIcon getAvatarPicture(String userID) throws XMPPException {
		// TODO Auto-generated method stub
		return null;
	}
}
