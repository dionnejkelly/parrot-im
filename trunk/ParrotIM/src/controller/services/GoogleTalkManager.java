package controller.services;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Vector;

import javax.net.ssl.SSLSocketFactory;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

import model.Model;
import model.dataType.ChatCollectionData;
import model.dataType.Conversation;
import model.dataType.MessageData;
import model.dataType.tempData.FriendTempData;
import model.enumerations.ServerType;
import model.enumerations.TypingStateType;
import model.enumerations.UpdatedType;
import model.enumerations.UserStateType;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.PacketCollector;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.RosterListener;
import org.jivesoftware.smack.SmackConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.filter.PacketIDFilter;
import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.packet.Presence.Mode;
import org.jivesoftware.smack.util.StringUtils;
import org.jivesoftware.smackx.ChatState;
import org.jivesoftware.smackx.ChatStateListener;
import org.jivesoftware.smackx.ChatStateManager;
import org.jivesoftware.smackx.Form;
import org.jivesoftware.smackx.filetransfer.FileTransfer;
import org.jivesoftware.smackx.filetransfer.FileTransferManager;
import org.jivesoftware.smackx.filetransfer.OutgoingFileTransfer;
import org.jivesoftware.smackx.muc.InvitationListener;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.jivesoftware.smackx.muc.ParticipantStatusListener;
import org.jivesoftware.smackx.muc.RoomInfo;
import org.jivesoftware.smackx.muc.SubjectUpdatedListener;
import org.jivesoftware.smackx.packet.VCard;

import com.sun.image.codec.jpeg.ImageFormatException;

import view.options.MusicPlayer; //import view.styles.ProgressMonitorScreen;
import view.styles.ProgressMonitorScreen;

import controller.MainController;

public class GoogleTalkManager implements GenericConnection {

    private static final String GOOGLE_SERVER = "talk.google.com";
    private static final int GOOGLE_PORT = 5223;
    private static final String GOOGLE_DOMAIN = "gmail.com";

    private MultiUserChat multiUserChat;

    private XMPPConnection connection;

    private MainController controller;

    private GenericConnection genericConnection;

    private ArrayList<Chat> chats;

    private Chat lastChat;

    private VCard vcard;

    private Roster roster;

    private FileTransferManager fileTransferManager;

    private DecimalFormat transferringProgress = new DecimalFormat("#");

    private ArrayList<XMPPFileManager> receiveManager;

    protected List subscribedUsers = new ArrayList();

    private Model model;

    private boolean isConferenceChat = false;

    // private Vector<String> availableRoom = new Vector<String>();

    private String groupRoom;

    private int countRoom = 0;

    private Vector<MultiUserChatManager> multiChatList = new Vector<MultiUserChatManager>();
    private Vector<String> availableRoom = new Vector<String>();

    // private class joinedListener implements ParticipantStatusListener {
    //
    // public void adminGranted(String arg0) {
    // // TODO Auto-generated method stub
    //			
    // }
    //
    // public void adminRevoked(String arg0) {
    // // TODO Auto-generated method stub
    //			
    // }
    //
    // public void banned(String arg0, String arg1, String arg2) {
    // // TODO Auto-generated method stub
    //			
    // }
    //
    // public void joined(String user) {
    // // Kevin is there a better function we can use from the StringUtil ?
    // System.out.println("Who joined in the room: " + delimitUserBack(user));
    // //int roomCount = multiUserChat.getOccupantsCount();
    // //System.out.println("Count: " + roomCount);
    //			
    // }
    //
    // public void kicked(String arg0, String arg1, String arg2) {
    // // TODO Auto-generated method stub
    //			
    // }
    //
    // public void left(String user) {
    // // Kevin is there a better function we can use from the StringUtil ?
    // System.out.println("Who left in the room: " + delimitUserBack(user));
    // //int roomCount = multiUserChat.getOccupantsCount();
    // //System.out.println("Count: " + roomCount);
    //			
    // }
    //
    // public void membershipGranted(String arg0) {
    // // TODO Auto-generated method stub
    //			
    // }
    //
    // public void membershipRevoked(String arg0) {
    // // TODO Auto-generated method stub
    //			
    // }
    //
    // public void moderatorGranted(String arg0) {
    // // TODO Auto-generated method stub
    //			
    // }
    //
    // public void moderatorRevoked(String arg0) {
    // // TODO Auto-generated method stub
    //			
    // }
    //
    // public void nicknameChanged(String arg0, String arg1) {
    // // TODO Auto-generated method stub
    //			
    // }
    //
    // public void ownershipGranted(String arg0) {
    // // TODO Auto-generated method stub
    //			
    // }
    //
    // public void ownershipRevoked(String arg0) {
    // // TODO Auto-generated method stub
    //			
    // }
    //
    // public void voiceGranted(String arg0) {
    // // TODO Auto-generated method stub
    //			
    // }
    //
    // public void voiceRevoked(String arg0) {
    // // TODO Auto-generated method stub
    //			
    // }
    //    	
    //    	
    //    	
    // }

    public GoogleTalkManager(MainController controller, Model model) {
        this.connection = null;
        this.controller = controller;
        this.model = model;
        this.genericConnection = this;
        this.chats = new ArrayList<Chat>();
        this.vcard = new VCard();
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

        try {

            String encodedImage = StringUtils.encodeBase64(bytes);
            vcard.setAvatar(bytes, encodedImage);
            vcard.setEncodedImage(encodedImage);
            vcard.setField("PHOTO", "<TYPE>image/jpg</TYPE><BINVAL>"
                    + encodedImage + "</BINVAL>", true);
        } catch (NullPointerException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        vcard.save(connection);

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
        ImageIcon icon = new ImageIcon(this.getClass().getResource(
                "/images/chatwindow/personal.png"));

        if (connection.isAuthenticated()) {
            try {
                vcard.load(connection, userID); // load someone's VCard

                byte[] avatarBytes = vcard.getAvatar();

                if (avatarBytes == null) {
                    icon = new ImageIcon(this.getClass().getResource(
                            "/images/chatwindow/personal.png"));
                } else {
                    icon = new ImageIcon(avatarBytes);
                }

            }

            catch (XMPPException e) {
                icon = new ImageIcon(this.getClass().getResource(
                        "/images/chatwindow/personal.png"));
            }
        }

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
        try {

            connection.disconnect();

        }

        catch (IllegalArgumentException exception) {
            System.out.println("Not disconnecting properly");
        }

        return;
    }

    public void login(String userID, String password)
            throws BadConnectionException {
        ConnectionConfiguration config = null;

        // server and port currently not assigned

        config = new ConnectionConfiguration(GOOGLE_SERVER, GOOGLE_PORT,
                GOOGLE_DOMAIN);
        config.setSocketFactory(SSLSocketFactory.getDefault());
        
        System.out.println("userid = " + userID);

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
        roster = connection.getRoster();
        roster.setSubscriptionMode(Roster.SubscriptionMode.manual);
        addSubscriptionListener();

        multiUserChat.addInvitationListener(connection,
                new invitationListener());

        fileTransferManager = new FileTransferManager(connection);

        setReceiveListener();

        return;
    }

    private class invitationListener implements InvitationListener {

        public void invitationReceived(XMPPConnection con, String room,
                String inviter, String reason, String password, Message message) {
            String result = delimitUserFront(inviter)
                    + " has been invited you to the multiple chat room!";

            int option = JOptionPane.showConfirmDialog(null, result);
            // System.out.println("Room: " + room);
            // System.out.println("Inviter: " + inviter);
            // System.out.println("Reason: " + reason);
            // System.out.println("Password: " + password);
            // System.out.println("Message: " + message.getBody());

            if (option == JOptionPane.OK_OPTION) {
                try {
                    joinRoom(room);
                    controller.setAvailableRoom(room);
                    controller.messageReceived(delimitUserFront(inviter),
                            delimitUserFront(connection.getUser()),
                            " has been invited you to the multiple chat room!");
                    lastChat = connection.getChatManager().createChat(
                            connection.getHost(),
                            new DefaultChatStateListener());

                    // multiUserChat.addParticipantStatusListener(new
                    // joinedListener());
                    // MusicPlayer receiveMusic =
                    // new MusicPlayer(
                    // "src/audio/message/receiveMessage.wav");
                } catch (XMPPException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            else {

                String validateReason = JOptionPane
                        .showInputDialog("What would be your reason?");
                multiUserChat.decline(connection, room,
                        delimitUserFront(inviter), validateReason);
            }

        }

    }

    // *********************** GROUP CHAT *************************

    /**
     * The New Way
     * 
     * Kevin you can decide which way is easier for you to handle it.
     * 
     * 
     */
    public void createRoom(String room) throws XMPPException {
        MultiUserChatManager multiChat = new MultiUserChatManager(connection,
                room + "@conference.jabber.org", model);
        try {
            multiChat.createRoom();
        } catch (Exception e) {
            System.err.println("room already exists, joining instead");
            multiChat.joinRoom();
        }
        
        multiChatList.add(multiChat);
        availableRoom.add(room + "@conference.jabber.org");

        // multiChat.addParticipantStatusListener(new joinedListener());

    }

    public void joinRoom(String room) throws XMPPException {      
      
        // *** Dev note: Does this need the @conference... added to room?
        
        MultiUserChatManager multiChat = new MultiUserChatManager(connection,
                room, model);
        multiChat.joinRoom();
        multiChatList.add(multiChat);
        availableRoom.add(room);

        // multiChat.addParticipantStatusListener(new joinedListener());

    }

    public void inviteFriend(String userID, String roomName)
            throws XMPPException {
        int roomNumber = availableRoom.indexOf(roomName);
        multiChatList.get(roomNumber).inviteFriend(userID);

    }

    public void leaveRoom(String roomName) {
        int roomNumber = availableRoom.indexOf(roomName);

        multiChatList.get(roomNumber).leaveRoom();
        multiChatList.remove(roomNumber);
        availableRoom.remove(roomNumber);
    }

    public void sendMultMessage(String message, String roomName)
            throws BadConnectionException {
        int roomNumber = availableRoom.indexOf(roomName
                + "@conference.jabber.org");
        System.out.println("Room number = " + roomNumber);
        try {
            multiChatList.get(roomNumber).sendMessage(message);
        } catch (XMPPException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * The Previous Way: Kevin you can decide which way is the best to handle
     * it.
     * 
     */
    // public void inviteFriend(String userID, String roomName) throws
    // XMPPException {
    // MultiUserChat multiUserChat = new MultiUserChat(connection, roomName);
    // System.out.println("== From GoogleTalkManager Class == ");
    // System.out.println("Invited User: " + userID);
    // System.out.println("Room Name: " + roomName);
    // multiUserChat.invite(userID, "Let's have fun");
    //		
    // multiUserChat.addParticipantStatusListener(new joinedListener());
    // //multiUserChat.addMessageListener(new multipleMessagePacketListener());
    //	
    // }
    //    
    // public void join(String room, final String nickname) {
    // join(false, room, nickname);
    // }
    //
    // public void create(String room, String nickname) {
    // groupRoom = "Parrot" + countRoom;
    // availableRoom.add(groupRoom);
    //		
    // countRoom++;
    //		
    // join(true, room, nickname);
    // }
    //
    // private void join(boolean create, String room, final String nickname) {
    // try {
    // multiUserChat = new MultiUserChat(connection, room +
    // "@conference.jabber.org");
    //
    // // The room service will decide the amount of history to send
    // if (create) {
    // multiUserChat.create(nickname);
    // multiUserChat.sendConfigurationForm(new Form(Form.TYPE_SUBMIT));
    // }
    //                
    // else
    // multiUserChat.join(nickname);
    //
    // multiUserChat.changeSubject("Parrot Conversation");
    // multiUserChat.changeNickname("Parrot IM");
    //          
    // multiUserChat
    // .addSubjectUpdatedListener(new SubjectUpdatedListener() {
    // public void subjectUpdated(String subject, String from) {
    // System.out.println("Room Subject: " + subject);
    // }
    // });
    //            
    // multiUserChat.addParticipantStatusListener(new joinedListener());
    // //multiUserChat.addMessageListener(new multipleMessagePacketListener());
    // } catch (XMPPException e) {
    // System.out.println("Error connecting to the room: "
    // + e.getMessage());
    // }
    //
    // // multiUserChat.addInvitationListener(connection, new
    // // invitationListener());
    //
    // }
    //    
    // public void sendMucMessage(String message) throws XMPPException {
    // multiUserChat.sendMessage(message);
    //    	
    // }
    //
    // private void join(String room) throws XMPPException {
    // multiUserChat = new MultiUserChat(connection, room);
    // multiUserChat.join(room);
    //
    // //multiUserChat.addMessageListener(new multipleMessagePacketListener());
    // }
    // can be handled by the single message packet listener
    // private class multipleMessagePacketListener implements PacketListener {
    //
    // public void processPacket(Packet packet) {
    // System.out.println("From multipleMessagePacketListener, Conference chat received!!");
    //        	
    // System.out.println("I received the conference room message listener but some how it is interfered by single message packet listener?");
    //        	
    // if (packet instanceof org.jivesoftware.smack.packet.Message) {
    //
    // Message message = (Message) packet;
    // String fromUserID =
    // StringUtils.parseBareAddress(message.getFrom());
    // String toUserID =
    // StringUtils.parseBareAddress(connection.getUser());
    // if (message.getBody() != null) {
    // controller.messageReceived(fromUserID, toUserID, message
    // .getBody());
    // lastChat =
    // connection.getChatManager().createChat(fromUserID,
    // new DefaultChatStateListener());
    // // MusicPlayer receiveMusic = new
    // // MusicPlayer("src/audio/message/receiveMessage.wav");
    // }
    // // org.jivesoftware.smack.packet.Message message =
    // // (org.jivesoftware.smack.packet.Message) packet;
    // System.out.println(delimitUserBack(message.getFrom()) + ": "
    // + message.getBody());
    //
    // }
    //
    // }
    //
    // }
    private String delimitUserBack(String from) {
        String subString = from.substring(from.indexOf('/') + 1);

        return subString;
    }

    private String delimitUserFront(String from) {

        String subString = from.substring(0, from.indexOf('/'));

        return subString;
    }

    private String delimitRoom(String from) {
        String subString = from.substring(0, from.indexOf('@'));

        return subString;
    }

    /**
     * By adding this method (right now added to the contructor) we do have auto
     * subscription. All subscribe packets get automatically answered by a
     * subscribed packet.
     */
    private void addSubscriptionListener() {
        PacketFilter filter = new org.jivesoftware.smack.filter.PacketTypeFilter(
                Presence.class);
        connection.createPacketCollector(filter);
        PacketListener myListener = new PacketListener() {
            public void processPacket(Packet packet) {
                Presence presence = (Presence) packet;

                if (presence.getType() == Presence.Type.subscribe) {
                    String subscriptionRequest = parseName(packet.getFrom())
                            + " wants to add you as a friend. Add as a friend?";
                    int option = JOptionPane.showConfirmDialog(null,
                            subscriptionRequest);

                    if (option == JOptionPane.OK_OPTION) {
                        Presence response = new Presence(
                                Presence.Type.subscribe);
                        response.setTo(presence.getFrom());

                        connection.sendPacket(response);
                        // ask also for subscription
                        if (!subscribedUsers.contains(presence.getFrom())) {
                            response = null;
                            response = new Presence(Presence.Type.subscribe);
                            connection.sendPacket(response);
                            // update the roster with the new user
                            org.jivesoftware.smack.packet.RosterPacket rosterPacket = new org.jivesoftware.smack.packet.RosterPacket();
                            rosterPacket.setType(IQ.Type.SET);
                            org.jivesoftware.smack.packet.RosterPacket.Item item = new org.jivesoftware.smack.packet.RosterPacket.Item(
                                    presence.getFrom(), parseName(presence
                                            .getFrom()));
                            // item.addGroupName(OLATBUDDIES);
                            item
                                    .setItemType(org.jivesoftware.smack.packet.RosterPacket.ItemType.both);

                            rosterPacket.addRosterItem(item);
                            connection.sendPacket(rosterPacket);

                            
                            subscribedUsers.add(presence.getFrom());
                            
                            // is there a better way to handle it than this
                            controller.modelAddFriend(parseName(packet.getFrom()) + "@gmail.com");
                            MusicPlayer addMusic =
                                new MusicPlayer("/audio/buddy/addFriend.wav", model);
//                            System.out.println("Updated the roster ========================== " + parseName(packet.getFrom()));
                        }

                        else {
                            response = new Presence(Presence.Type.unsubscribe);
                            response.setTo(presence.getFrom());
                            connection.sendPacket(response);

                        }

                    }
                }

            }
        };
        connection.addPacketListener(myListener, filter);
    }

    /**
     * @param xmppAddress
     *            jabber jid like cmpt275testing@jabber.sfu.ca
     * @return returns just the name "cmpt275testing" without the rest
     */
    protected String parseName(String xmppAddress) {
        if (xmppAddress == null) {
            return null;
        }
        int atIndex = xmppAddress.indexOf("@");
        if (atIndex <= 0) {
            return "";
        } else {
            return xmppAddress.substring(0, atIndex);
        }
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
        } else if (state == UserStateType.AWAY || state == UserStateType.BRB 
        		|| state == UserStateType.LUNCH ||
        		state == UserStateType.NOT_AVAILABLE) {
            presence.setMode(Presence.Mode.away);
        } else if (state == UserStateType.BUSY || state == UserStateType.PHONE 
        		|| state == UserStateType.NOT_BE_DISTURBED) {
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

    public void setReceiveListener() {
        receiveManager = new ArrayList<XMPPFileManager>();
        int count = 0;

        for (RosterEntry r : roster.getEntries()) {
            // System.out.println("From the File Transfer User = " +
            // r.getUser());
            receiveManager.add(new XMPPFileManager(connection,
                    fileTransferManager, transferringProgress));
            receiveManager.get(count).fileReceiver(r.getUser());
            count++;
        }
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
        System.out.println("ToUserID = " + toUserID);

        if (ourChat == null) {
            ourChat = connection.getChatManager().createChat(toUserID,
                    new DefaultChatStateListener());
        }
        lastChat = ourChat;
        try {
            ourChat.sendMessage(message);
        } catch (XMPPException e) {
            System.err.println("Error in sending message.");
            throw new BadConnectionException();
        }

        return;
    }

    public ServerType getServerType() {
        return ServerType.GOOGLE_TALK;
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
            MessageData messageObject = null;
            String fromUserID = null;
            String roomName = null;
            String toUserID = StringUtils
                    .parseBareAddress(connection.getUser());
            ChatCollectionData chatCollection = model.getChatCollection();
            Conversation conversation = null;
            
            if (message.getType() == Message.Type.groupchat) {
                isConferenceChat = true;
                roomName = delimitRoom(message.getFrom());
                fromUserID = delimitUserBack(message.getFrom());
                System.out.println("From Group Chat:");
                System.out.println(fromUserID + ": " + message.getBody());

                conversation = chatCollection.findByRoomName(roomName);
                messageObject = new MessageData(fromUserID, message.getBody());

                if (chatCollection.isHidden(conversation)) {
                    chatCollection.activateConversation(conversation);
                }
                
                conversation.addMessage(messageObject);
                chatCollection.forceUpdate();

            } else {
                fromUserID = StringUtils.parseBareAddress(message.getFrom());
                if (message.getBody() != null) {
                    controller.messageReceived(fromUserID, toUserID, message
                            .getBody());

                }
                lastChat = connection.getChatManager().createChat(fromUserID,
                        new DefaultChatStateListener());

                isConferenceChat = false;
            }

            return;
        }
    }

    public int hashCode() {
        int hash = 7;

        hash = hash * 31 + "Google".hashCode();
        hash = hash * 31 + this.connection.hashCode();

        return hash;
    }

    public boolean isValidUserID(String userID) {
        String checkJID = roster.getPresence(userID).getFrom();

        return checkJID.contains(userID);
    }

    public void sendFile(String userID, String path, ProgressMonitorScreen progress) throws XMPPException {

        String convertToJID = roster.getPresence(userID).getFrom();

        System.out.println("Start Transfering File... " + convertToJID);

        OutgoingFileTransfer fileTransfer = fileTransferManager
                .createOutgoingFileTransfer(convertToJID);

        fileTransfer.sendFile(new File(path), "");
        System.out.println("Start Transfering File...");

        while (!fileTransfer.isDone()) {
            if (fileTransfer.getStatus() == FileTransfer.Status.refused) {
                JOptionPane.showMessageDialog(null,
                        "Could not send the file to " + userID + ".", "Failed",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (fileTransfer.getStatus() == FileTransfer.Status.error) {
                JOptionPane
                        .showMessageDialog(
                                null,
                                "Cannot send the file because an error occured during the process.",
                                "Failed", JOptionPane.ERROR_MESSAGE);
                return;
            }

            System.out.println("Transferring Progress: "
                    + transferringProgress
                            .format(fileTransfer.getProgress() * 100)
                    + "% transferred.");

            try {
                Thread.sleep(100);
                progress.counter = (int)fileTransfer.getProgress() * 100;
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

        System.out.println(fileTransfer.getFileName()
                + "has been successfully transferred.");
        System.out.println("The Transfer is " + fileTransfer.isDone());

    }

    /**
     * 
     * set typing state so the currently chat can get the change assuming that
     * the currently chat user uses googletalk
     * 
     * @param state
     *            int that represents different state 1 = active 2 = composing 3
     *            = gone 4 = inactive 5 = paused
     */
    public void setTypingState(int state, String userID)
            throws BadConnectionException, XMPPException {
        ChatStateManager curState = ChatStateManager.getInstance(connection);
        if (lastChat == null) {
            lastChat = connection.getChatManager().createChat(userID,
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
        } else {

        }
    }

    /**
     * 
     * listening for chat state change from server and change that state in
     * model
     * 
     * */
    private class DefaultChatStateListener implements ChatStateListener {

        public void stateChanged(Chat user, ChatState event) {
            System.out.println("Getting called here...");
            System.out.println(user.getParticipant() + " is " + event.name());

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

    public boolean isConferenceChat() {
        return isConferenceChat;
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
