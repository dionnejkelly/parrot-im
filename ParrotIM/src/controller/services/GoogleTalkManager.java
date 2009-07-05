package controller.services;

import java.util.ArrayList;
import java.util.Collection;

import javax.net.ssl.SSLSocketFactory;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

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
import org.jivesoftware.smackx.ChatState;
import org.jivesoftware.smackx.ChatStateListener;
import org.jivesoftware.smackx.ChatStateManager;
import org.jivesoftware.smackx.packet.VCard;

import view.options.MusicPlayer;

import controller.MainController;

public class GoogleTalkManager implements GenericConnection {

    private static final String GOOGLE_SERVER = "talk.google.com";
    private static final int GOOGLE_PORT = 5223;
    private static final String GOOGLE_DOMAIN = "gmail.com";

    private XMPPConnection connection;

    private MainController controller;

    private GenericConnection genericConnection;

    private ArrayList<Chat> chats;
    
    private Chat lastChat;
    
    private VCard vcard;

    public GoogleTalkManager(MainController controller) {
        this.connection = null;
        this.controller = controller;
        this.genericConnection = this;
        this.chats = new ArrayList<Chat>();
    }
    
    public ImageIcon getAvatarPicture(String userID) throws XMPPException {
		vcard = new VCard();

		vcard.load(connection,userID); // load someone's VCard

		 
		byte[] avatarBytes = vcard.getAvatar();
		ImageIcon icon = new ImageIcon(avatarBytes);
		
		return icon;

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

        // server and port currently not assigned
        
        config =
                new ConnectionConfiguration(
                        GOOGLE_SERVER, GOOGLE_PORT, GOOGLE_DOMAIN);
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
        connection.addPacketListener(
                new MessagePacketListener(), new MessagePacketFilter());
        connection.getRoster().addRosterListener(new BuddyListener());

        return;
    }

    public boolean removeFriend(String userID)
            throws BadConnectionException {
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
            userStatus =
                    this.connection
                            .getRoster().getPresence(userID).getStatus();
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
            userFromServer =
                    this.connection.getRoster().getPresence(userID);
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
            friendToAdd =
                    new FriendTempData(userID, r.getName(), this
                            .retrieveStatus(userID), this
                            .retrieveState(userID), false);
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
            ourChat =
                    connection.getChatManager().createChat(
                            toUserID, new MessageListener() {
                                public void processMessage(
                                        Chat chat, Message message) {
                                    // Do nothing

                                    return;
                                }
                            });
        }
        this.lastChat = ourChat;
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
            String bareAddress =
                    StringUtils.parseBareAddress(presence.getFrom());
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
            String fromUserID =
                    StringUtils.parseBareAddress(message.getFrom());
            String toUserID =
                    StringUtils.parseBareAddress(connection.getUser());

            if (message.getBody() != null) {
                controller.messageReceived(fromUserID, toUserID, message
                        .getBody());
                
                MusicPlayer receiveMusic = new MusicPlayer("src/audio/message/receiveMessage.wav");
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
	//istyping  doesn't work yet
	//will have nullpointer error for every new chat
	//cuz the chat object is not created until the first msg is send
	//and it should send to every friends instead of just the current chat
	public void isTyping() throws BadConnectionException, XMPPException {
		ChatStateManager state = ChatStateManager.getInstance(connection);
        state.setCurrentState(ChatState.composing, lastChat);

        System.out.println("hey! I'm typing");
		
	}
	/**
	 * use ChatSession to connect to MessageEventNotificationListener
	 */
	
	/**
	 * another class that listens chat state is MessageEventNotificationListener
	 * try this later....this also includes offline notification
	 */
	

	
	/**
	 * 
	 * this is for listening for chat state changed
	 * but without implementing processMessage, it will break this class
	 * 
	 * 
	 private class DefaultChatStateListener implements ChatStateListener {

		@Override
		public void stateChanged(Chat arg0, ChatState arg1) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void processMessage(Chat arg0, Message arg1) {
			// TODO Auto-generated method stub
			
		}
		 
	 }
**/
}
