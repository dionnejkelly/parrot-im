package controller.services;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;

import javax.net.ssl.SSLSocketFactory;
import javax.swing.ImageIcon;

import model.Model;
import model.dataType.tempData.FriendTempData;
import model.enumerations.ServerType;
import model.enumerations.TypingStateType;
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

import view.styles.ProgressMonitorScreen;
import controller.MainController;

public class JabberManager implements GenericConnection {

    private static final int DEFAULT_PORT = 5223;

    private XMPPConnection connection;

    private MainController controller;

    private GenericConnection genericConnection;

    private ArrayList<Chat> chats;

    private String server;

    private Chat lastChat;

    private String domain;

    private VCard vcard;

    public JabberManager(MainController controller, Model model) {
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

    public void login(String userID, String password)
            throws BadConnectionException {
        ConnectionConfiguration config = null;

        this.domain = StringUtils.parseServer(userID); // will this work?
        this.server = StringUtils.parseServer(userID);
        userID = StringUtils.parseName(userID); // to make it work with sfu...
        // check for others
        // port currently not assigned

        config =
                new ConnectionConfiguration(this.server, DEFAULT_PORT,
                        this.domain);
        config.setSocketFactory(SSLSocketFactory.getDefault());

        System.out.println("userID: " + userID);
        System.out.println("server " + server);
        System.out.println("domain " + domain);

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
            userStatus =
                    this.connection.getRoster().getPresence(userID).getStatus();
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

    /**
     * Set the avatar for the VCard by specifying the url to the image.
     * 
     * @param avatarURL
     *            the url to the image(png,jpeg,gif,bmp)
     * @throws XMPPException
     */
    public void setAvatarPicture(URL avatarURL) throws XMPPException {
        byte[] bytes = new byte[0];
        try {
            bytes = getBytes(avatarURL);
        } catch (IOException e) {
            e.printStackTrace();
        }

        setAvatarPicture(bytes);
    }

    /**
     * Specify the bytes for the avatar to use.
     * 
     * @param bytes
     *            the bytes of the avatar.
     * @throws XMPPException
     */
    public void setAvatarPicture(File file) throws XMPPException {
        vcard = new VCard();
        vcard.load(connection);

        // Otherwise, add to mappings.
        byte[] bytes;
        try {
            bytes = getFileBytes(file);
            String encodedImage = StringUtils.encodeBase64(bytes);
            vcard.setAvatar(bytes, encodedImage);
            vcard.setEncodedImage(encodedImage);
            vcard.setField("PHOTO", "<TYPE>image/jpg</TYPE><BINVAL>"
                    + encodedImage + "</BINVAL>", true);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("---------------------------Done and done!!!");

        vcard.save(connection);
    }

    /**
     * Specify the bytes for the avatar to use.
     * 
     * @param bytes
     *            the bytes of the avatar.
     * @throws XMPPException
     */
    public void setAvatarPicture(byte[] bytes) throws XMPPException {

        vcard = new VCard();
        vcard.load(connection);

        String encodedImage = StringUtils.encodeBase64(bytes);

        vcard.setField("Avatar", "<TYPE>image/jpg</TYPE><BINVAL>"
                + encodedImage + "</BINVAL>", true);

    }

    /**
     * Common code for getting the bytes of a url.
     * 
     * @param url
     *            the url to read.
     */
    public byte[] getBytes(URL url) throws IOException {
        final String path = url.getPath();
        final File file = new File(path);
        if (file.exists()) {
            return getFileBytes(file);
        }

        return null;
    }

    private byte[] getFileBytes(File file) throws IOException {
        BufferedInputStream bis = null;
        try {
            bis = new BufferedInputStream(new FileInputStream(file));
            int bytes = (int) file.length();
            byte[] buffer = new byte[bytes];
            int readBytes = bis.read(buffer);
            if (readBytes != buffer.length) {
                throw new IOException("Entire file not read");
            }
            return buffer;
        } finally {
            if (bis != null) {
                bis.close();
            }
        }
    }

    public ImageIcon getAvatarPicture(String userID) throws XMPPException {
        // vcard = new VCard();
        ImageIcon icon;

        try {
            vcard.load(connection, userID); // load someone's VCard

            byte[] avatarBytes = vcard.getAvatar();

            if (avatarBytes == null) {
                icon =
                        new ImageIcon(this.getClass().getResource(
                                "/images/chatwindow/personal.png"));
            } else {
                icon = new ImageIcon(avatarBytes);
            }

        }

        catch (XMPPException e) {
            icon =
                    new ImageIcon(this.getClass().getResource(
                            "/images/chatwindow/personal.png"));
        }

        return icon;

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
            friendToAdd =
                    new FriendTempData(userID, r.getName(), this
                            .retrieveStatus(userID),
                            this.retrieveState(userID), false);
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
                    connection.getChatManager().createChat(toUserID,
                            new MessageListener() {
                                public void processMessage(Chat chat,
                                        Message message) {
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

    public ServerType getServerType() {
        return ServerType.JABBER;
    }

    // Section
    // Listeners

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
            String fromUserID = StringUtils.parseBareAddress(message.getFrom());
            String toUserID =
                    StringUtils.parseBareAddress(connection.getUser());

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

    /**
     * 
     * set typing state
     * 
     * @param state
     *            int that represents different state 1 = active 2 = composing 3
     *            = gone 4 = inactive 5 = paused
     */
    public void setTypingState(int state, String userID)
            throws BadConnectionException, XMPPException {
        ChatStateManager curState = ChatStateManager.getInstance(connection);
        if (lastChat == null) {
            lastChat =
                    connection.getChatManager().createChat(userID,
                            new DefaultChatStateListener());
        }

        if (state == 1) {
            curState.setCurrentState(ChatState.active, lastChat);
        } else if (state == 2) {
            curState.setCurrentState(ChatState.composing, lastChat);

        } else if (state == 3) {
            curState.setCurrentState(ChatState.gone, lastChat);
        } else if (state == 4) {
            curState.setCurrentState(ChatState.inactive, lastChat);
        } else if (state == 5) {
            curState.setCurrentState(ChatState.paused, lastChat);
        }
    }

    private class DefaultChatStateListener implements ChatStateListener {
        public void stateChanged(Chat user, ChatState event) {
            String state = event.name();
            TypingStateType typingState = null;
            if (state.equals("active")) {
                typingState = TypingStateType.ACTIVE;
            } else if (state.equals("composing")) {
                typingState = TypingStateType.TYPING;
            } else if (state.equals("paused")) {
                typingState = TypingStateType.PAUSED;
            } else if (state.equals("inactive")) {
                typingState = TypingStateType.INACTIVE;
            } else if (state.equals("gone")) {
                typingState = TypingStateType.GONE;
            }
            controller.typingStateUpdated(genericConnection, typingState, user
                    .getParticipant().toString());
        }

        public void processMessage(Chat arg0, Message arg1) {
            // Do nothing
        }
    }

    public String getUserEmailHome() throws XMPPException {
        // TODO Auto-generated method stub
        return null;
    }

    public String getUserEmailWork() throws XMPPException {
        // TODO Auto-generated method stub
        return null;
    }

    public String getUserFirstName() throws XMPPException {
        // TODO Auto-generated method stub
        return null;
    }

    public String getUserLastName() throws XMPPException {
        // TODO Auto-generated method stub
        return null;
    }

    public String getUserMiddleName() throws XMPPException {
        // TODO Auto-generated method stub
        return null;
    }

    public String getUserNickName() throws XMPPException {
        // TODO Auto-generated method stub
        return null;
    }

    public String getUserOrganization() throws XMPPException {
        // TODO Auto-generated method stub
        return null;
    }

    public String getUserOrganizationUnit() throws XMPPException {
        // TODO Auto-generated method stub
        return null;
    }

    public String getUserPhoneHome() throws XMPPException {
        // TODO Auto-generated method stub
        return null;
    }

    public String getUserPhoneWork() throws XMPPException {
        // TODO Auto-generated method stub
        return null;
    }

    public void load(String userID) throws XMPPException {
        // TODO Auto-generated method stub

    }

    public void load() throws XMPPException {
        // TODO Auto-generated method stub

    }

    public void setUserEmailHome(String name) throws XMPPException {
        // TODO Auto-generated method stub

    }

    public void setUserEmailWork(String name) throws XMPPException {
        // TODO Auto-generated method stub

    }

    public void setUserFirstName(String name) throws XMPPException {
        // TODO Auto-generated method stub

    }

    public void setUserLastName(String name) throws XMPPException {
        // TODO Auto-generated method stub

    }

    public void setUserMiddleName(String name) throws XMPPException {
        // TODO Auto-generated method stub

    }

    public void setUserNickName(String name) throws XMPPException {
        // TODO Auto-generated method stub

    }

    public void setUserOrganization(String name) throws XMPPException {
        // TODO Auto-generated method stub

    }

    public void setUserOrganizationUnit(String name) throws XMPPException {
        // TODO Auto-generated method stub

    }

    public void setUserPhoneHome(String name) throws XMPPException {
        // TODO Auto-generated method stub

    }

    public void setUserPhoneWork(String name) throws XMPPException {
        // TODO Auto-generated method stub

    }

    public void sendFile(String filePath, String userID,
            ProgressMonitorScreen progress) throws XMPPException {
        // TODO Auto-generated method stub

    }

    public boolean isValidUserID(String userID) {
        // TODO Auto-generated method stub
        return false;
    }

    public void createRoom(String room) throws XMPPException {
        // TODO Auto-generated method stub

    }

    public void inviteFriend(String userID, String roomName)
            throws XMPPException {
        // TODO Auto-generated method stub

    }

    public boolean isConferenceChat() {
        return false;
    }

    public void sendMultMessage(String message, String roomName)
            throws BadConnectionException {
        // TODO Auto-generated method stub

    }

    public boolean doesExist(String userID) {
        // TODO Auto-generated method stub
        return false;
    }

    public boolean isFollowing(String userID) {
        // TODO Auto-generated method stub
        return false;
    }

}
