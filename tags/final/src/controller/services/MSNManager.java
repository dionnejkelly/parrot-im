package controller.services;

import java.awt.HeadlessException;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import model.Model;
import model.dataType.tempData.FriendTempData;
import model.enumerations.ServerType;
import model.enumerations.TypingStateType;
import model.enumerations.UserStateType;

import org.jivesoftware.smack.XMPPException;

import rath.msnm.BuddyList;
import rath.msnm.MSNMessenger;
import rath.msnm.SwitchboardSession;
import rath.msnm.UserStatus;
import rath.msnm.entity.MsnFriend;
import rath.msnm.event.MsnAdapter;
import rath.msnm.ftp.VolatileDownloader;
import rath.msnm.ftp.VolatileTransferServer;
import rath.msnm.msg.MimeMessage;
import view.mainwindow.HelpPanel;
import view.styles.ProgressMonitorScreen;

import com.itbs.aimcer.bean.Contact;
import com.itbs.aimcer.bean.Group;
import com.itbs.aimcer.bean.Message;
import com.itbs.aimcer.bean.Nameable;
import com.itbs.aimcer.commune.AbstractMessageConnection;
import com.itbs.aimcer.commune.ConnectionEventListener;
import com.itbs.aimcer.commune.FileTransferListener;
import com.itbs.aimcer.commune.FileTransferSupport;

import controller.MainController;

public class MSNManager extends AbstractMessageConnection implements
        GenericConnection { // implements FileTransferSupport {
    private static Logger log = Logger.getLogger(MSNManager.class.getName());
    private final int maximumCapacity = 115;

    private MSNMessenger connection = null;
    private Map<String, SwitchboardSession> sessions =
            new ConcurrentHashMap<String, SwitchboardSession>();

    private SwitchboardSession session;

    private GenericConnection genericConnection;

    private MainController controller;

    private BuddyList buddyList;

    private Model model;

    private PollingThread poller;
    
    private boolean isConnected = false;

    /**
     * Non-blocking call.
     * 
     * @throws Exception
     */

    public static void main(String[] args) throws Exception {
        MSNManager msn = new MSNManager();
        msn.connect("tuna_shichan@hotmail.com", "aloha0712?!");
        msn.retrieveFriendList();
        System.out.println(">");
        Scanner optionScanner = new Scanner(System.in);
        String option = optionScanner.nextLine();

        MsnFriend friend = msn.msnFriend("littletomato89@hotmail.com");
        while ((option).equals("1")) {

            System.out
                    .println("Type your message to littletomato89@gmail.com: "
                            + msn.getUserStatus(friend));
            Scanner msgInput = new Scanner(System.in);
            msgInput.nextLine();

            // msn.removeFriend(msg);
            // msn.getBuddies();
            // System.out.println("=====================");
            // System.out.println("User status = " +
            // msn.getUserStatus("littletomato89@hotmail.com"));

            // msn.setPresence(msg);
            // msn.sendMessage("littletomato89@hotmail.com", msg);
            // if (msg.equals("1")) {
            // msn.setAway(true);
            // }
            //				
            // if (msg.equals("2")) {
            // msn.setBRB(true);
            // }
            //				
            // if (msg.equals("3")) {
            // msn.setBusy(true);
            // }
            //				
            // if (msg.equals("4")) {
            // msn.setIdle(true);
            // }
            //				
            // if (msg.equals("5")) {
            // msn.setLunch(true);
            // }
            //				
            // if (msg.equals("6")) {
            // msn.setPhone(true);
            // }
            //				
            // System.out.println("My status = " + msn.getMyStatus());

        }
        //	
    }

    public MSNManager() {

    }

    public void buddyListModified() {
        BuddyList blist = connection.getBuddyGroup().getForwardList();
        for (int i = 0; i < blist.size(); i++) {
            MsnFriend friend = blist.get(i);


        }

    }

    public MsnFriend msnFriend(String userID) {

        return buddyList.get(userID);
    }

    public MSNManager(MainController control, Model model) {
        this.genericConnection = this;
        this.controller = control;
        this.model = model;

    }

    public void addFriend(String userID) throws BadConnectionException {
        try {
            connection.addFriend(userID);


        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public boolean removeFriend(String userID) throws BadConnectionException {
        try {
            connection.removeFriend(userID);


        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return true;
    }

    public void sendMessage(String userID, String message)
            throws BadConnectionException {
        MimeMessage mimeMsg = new MimeMessage(message);

        mimeMsg.setKind(2);

        MsnFriend msnFriend = this.msnFriend(userID);

        if (!this.getUserStatus(msnFriend).equals("FLN")) {

            try {
                session = getSession(userID);
                connection.sendMessage(userID, mimeMsg, session.getSessionId());
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

        else {
            controller.messageReceived(userID, connection.getLoginName(),
                    " is offline right now.");
        }

        return;

    }

    public String getUserStatus(MsnFriend userID) {

        if (connection.isLoggedIn()) {
            return userID.getStatus();
        }

        return "Offline";
    }

    public String getUserFriendlyName(String userID) {

        return connection.getBuddyGroup().getAllowList().get(userID)
                .getFriendlyName();
    }

    public String getUserFormattedFriendlyName(MsnFriend userID) {

        return userID.getFormattedFriendlyName();
    }

    public void login(String userID, String password)
            throws BadConnectionException {

   
    		this.connect(userID, password);

            poller = new PollingThread();
            poller.start();
            

        return;
    }
    
    public boolean isConnected() {
    	return isConnected;
    }
    // Section
    // Polling methods

    private class PollingThread extends Thread {

        public void run() {
            try {
                sleep(8000); // Delay for 8 seconds
            } catch (InterruptedException e) {
                System.err.println("Threading error");
                e.printStackTrace();
            }

            controller.refreshFriends(controller.getConnection());

        }
    }

    public void connect(String userID, String password)
            throws BadConnectionException {
        try {
            super.connect();
            sessions.clear();
            notifyConnectionInitiated();

            connection = new MSNMessenger(userID, password);

            connection.login(userID, password);

            connection.addMsnListener(new ConnectionListener());
            // connection.setInitialStatus(UserStatus.INVISIBLE); // todo switch
            // to this when working
            connection.setInitialStatus(UserStatus.ONLINE);

            // connection.login(userID, password);

            buddyList = connection.getBuddyGroup().getForwardList();
            // contacts = getContactFactory();

        } 
        
        catch (Exception e) {
            // TODO Auto-generated catch block

            e.printStackTrace();
        }
    }

    public void getBuddies() {
        int target = connection.getBuddyGroup().getAllowList().size();

        int count = 0;

        while (count < target) {

//            System.out.println("Friends = "
//                    + connection.getBuddyGroup().getAllowList().get(count)
//                            .getLoginName());
            count++;
        }
    }

    public ArrayList<FriendTempData> retrieveFriendList() {
        ArrayList<FriendTempData> localFriends =
                new ArrayList<FriendTempData>();

        ArrayList<String> buddies = new ArrayList<String>();

        int target = connection.getBuddyGroup().getForwardList().size();

        int count = 0;

        String msnFriendLoginName;

        MsnFriend msnFriend;

        while (count < target && count < maximumCapacity) {

            msnFriendLoginName =
                    connection.getBuddyGroup().getForwardList().get(count)
                            .getLoginName();

            msnFriend = msnFriend(msnFriendLoginName);
            buddies.add(connection.getBuddyGroup().getForwardList().get(count)
                    .getLoginName());

            localFriends.add(new FriendTempData(msnFriendLoginName,
                    msnFriendLoginName, this.retrieveStatus(msnFriend
                            .getLoginName()), this.retrieveState(msnFriend
                            .getLoginName()), false));

            count++;
        }


        return localFriends;
    }

    private class ConnectionListener extends MsnAdapter {
    	
    	public void loginComplete(MsnFriend own) {
            // tell everyone we are now running connected
            // use itertors b/c the size will change
    		isConnected = true;
 
        }

    	
    	
        public void progressTyping(SwitchboardSession ss, MsnFriend friend,
                String typingUser) {
     
            TypingStateType typingState = TypingStateType.TYPING;

            controller.typingStateUpdated(genericConnection, typingState,
                    friend.getLoginName());
        }

        public void instantMessageReceived(SwitchboardSession ss,
                MsnFriend friend, MimeMessage mime) {
            String fromUserID = friend.getLoginName();
            String message = mime.getMessage();
            String toUserID = connection.getLoginName();

            if (message != null) {
                controller.messageReceived(fromUserID, toUserID, message);
                TypingStateType typingState = TypingStateType.GONE;

                controller.typingStateUpdated(genericConnection, typingState,
                        friend.getLoginName());
            }
        }

        public void userOnline(MsnFriend friend) {
            // Contact contact = getContactFactory().get(friend.getLoginName(),
            // MSNManager.this);
            String user = friend.getLoginName();

            // if (contact != null) {
            // contact.setDisplayName(GeneralUtils.stripHTML(friend.getFormattedFriendlyName()));
            // Status oldStatus = (Status) contact.getStatus().clone();
            // contact.getStatus().setOnline(true);
            // contact.getStatus().setAway(false);
            // contact.getStatus().setIdleTime(0);
            // notifyStatusChanged(contact, oldStatus);
            // System.out.println("Who has changed the status = " +
            // friend.getLoginName());
            // } else {
            // log.fine("got MSN contact status w/o it being in the list");
            // }
            controller.friendUpdated(genericConnection, user);
        }

        public void userOffline(String loginName) {

            controller.friendUpdated(genericConnection, loginName);
        }

        public void filePosted(SwitchboardSession ss, int cookie,
                String filename, int filesize) {
            super.filePosted(ss, cookie, filename, filesize); // Todo change
            log.fine("MSNConnection$ConnectionListener.filePosted");

        }

        public void fileSendAccepted(SwitchboardSession ss, int cookie) {
            super.fileSendAccepted(ss, cookie); // Todo change
            log.fine("MSNConnection$ConnectionListener.fileSendAccepted");
        }

        public void fileSendRejected(SwitchboardSession ss, int cookie,
                String reason) {
            super.fileSendRejected(ss, cookie, reason); // Todo change
            log.fine("MSNConnection$ConnectionListener.fileSendRejected");
//            System.out.println("File is rejected by: "
//                    + ss.getMsnFriend().getLoginName());
        }

        public void fileSendStarted(VolatileTransferServer server) {
            super.fileSendStarted(server); // Todo change
            log.fine("MSNConnection$ConnectionListener.fileSendStarted");
//            System.out.println("File is started by: "
//                    + server.getCommitPercent());
        }

        public void fileSendEnded(VolatileTransferServer server) {
            super.fileSendEnded(server); // Todo change
            log.fine("MSNConnection$ConnectionListener.fileSendEnded");
        }

        public void fileReceiveStarted(VolatileDownloader downloader) {
            log.fine("MSNConnection$ConnectionListener.fileReceiveStarted");
            // Todo verify

//            System.out.println(downloader.getFilename()
//                    + "is sending a file...");
            Contact contact =
                    getContactFactory().get(downloader.getName(),
                            MSNManager.this);
            for (ConnectionEventListener eventHandler : eventHandlers) {
                eventHandler.fileReceiveRequested(
                        (FileTransferSupport) MSNManager.this, contact,
                        downloader.getFilename(), "", downloader);
            }
        }

        public void switchboardSessionStarted(SwitchboardSession ss) {
            sessions.put(ss.getMsnFriend().getLoginName(), ss);
//            System.out.println("Session started: "
//                    + ss.getMsnFriend().getLoginName());
            session = ss;
        }

        public void switchboardSessionEnded(SwitchboardSession ss) {
            sessions.remove(ss.getName());
//            System.out.println("Session ended: "
//                    + ss.getMsnFriend().getLoginName());
        }

        public void notifyUnreadMail(Properties Prop, int unread) {
    

            int option;

            try {
                if (model.getEmailNotification().equals("Y")) {
                    if (0 < unread && unread <= 1) {
                    	option =
                            JOptionPane
                                    .showConfirmDialog(
                                            null,
                                            "You have "
                                                    + unread
                                                    + " unread email.\nWould you like to read this email right now?",
                                            "Unread Email",
                                            JOptionPane.INFORMATION_MESSAGE);
               
                    }

                    else {
                        option =
                            JOptionPane
                                    .showConfirmDialog(
                                            null,
                                            "You have "
                                                    + unread
                                                    + " unread emails.\nWould you like to read these emails right now?",
                                            "Unread Email",
                                            JOptionPane.INFORMATION_MESSAGE);
                    }

                    if (option == JOptionPane.OK_OPTION) {
                        new HelpPanel("http://www.hotmail.com");
                    }
                }
            } catch (HeadlessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

        public void addFailed(int errorCode) {
            notifyErrorOccured("Failed to add.  Error code " + errorCode, null);
        }

    }


    public void disconnect(boolean intentional) {
        sessions.clear();
        if (connection != null) {
            connection.logout();
        }
        super.disconnect(intentional);
    }
    

    public void reconnect() {
        disconnect(false);
        try {
            connect();
        } catch (Exception e) {
            // GeneralUtils.sleep(1000);
            log.log(Level.INFO, "Failed to reconnect", e);
        }
    }

    public boolean isLoggedIn() {
        return connection != null && connection.isLoggedIn();
    }

    /**
     * Cancel login.
     */
    public void cancel() {
        disconnect(false);
    }

    public void addContact(Nameable contact, Group group) {
        // String[] groupNames = new String[1];
        // groupNames[0] = group.getName();
        try {
            connection.addGroup(group.getName());
            connection.addFriend(contact.getName());
        } catch (IOException e) {
            for (ConnectionEventListener connectionEventListener : eventHandlers) {
                connectionEventListener.errorOccured("Failed to add a contact",
                        e);
            }
            return;
        }
        group.add(contact);
        Thread.yield();
        String index = null;
        Iterator target = connection.getBuddyGroup().getGroupList().iterator();
        while (target.hasNext()) {
            rath.msnm.entity.Group targetGroup =
                    (rath.msnm.entity.Group) target.next();
            if (group.getName().equalsIgnoreCase(targetGroup.getName())) {
                index = targetGroup.getIndex();
                break;
            }
        }
        if (index != null) {
            BuddyList blist = connection.getBuddyGroup().getForwardList();
            for (int i = 0; i < blist.size(); i++) {
                MsnFriend friend = blist.get(i);
                if (contact.getName().equalsIgnoreCase(friend.getLoginName())) {
                    friend.setGroupIndex(index);
                    break;
                }
            }
        } else {
            log.fine("Never found the index.");
        }

    }// addContact()

    public boolean removeContact(Nameable contact, Group group) {
        // todo protocol: would be nice to delete from a group
        try {
            connection.removeFriend(contact.getName());
            cleanGroup(group, contact);
        } catch (IOException e) {
            notifyErrorOccured("ERROR removing a contact " + contact, e);
            return false;
        }
        return true;
    }

    public void addContactGroup(Group group) {
        try {
            connection.addGroup(group.getName());
        } catch (IOException e) {
            for (ConnectionEventListener eventHandler : eventHandlers) {
                eventHandler.errorOccured("ERROR adding a contact.", e);
            }
        }
    }

    public void removeContactGroup(Group group) {
        try {
            connection.removeGroup(group.getName());
        } catch (IOException e) {
            for (ConnectionEventListener eventHandler : eventHandlers) {
                eventHandler.errorOccured("ERROR removing a contact group.", e);
            }
        }
    }

    public void moveContact(Nameable contact, Group oldGroup, Group newGroup) {
        try {
            connection.moveGroupAsFriend(new MsnFriend(contact.getName()),
                    oldGroup.getName(), newGroup.getName());
            oldGroup.remove(contact);
            newGroup.add(contact);
        } catch (IOException e) {
            for (ConnectionEventListener eventHandler : eventHandlers) {
                eventHandler.errorOccured(
                        "ERROR moving a contact between groups.", e);
            }
        }
    }

    /**
     * Returns a short name for the service. "AIM", "ICQ" etc.
     * 
     * @return service name
     */
    public String getServiceName() {
        return "MSN";
    }

    /**
     * True if this is a system message.
     * 
     * @param contact
     *            to check
     * @return true if a system message
     */
    public boolean isSystemMessage(Nameable contact) {
        return false;
    }

    public boolean isAway() {
        if (connection == null) {
            return super.isAway();
        }
        return !UserStatus.ONLINE.equals(connection.getMyStatus());
    }

    public String getMyStatus() {
        return connection.getMyStatus();
    }

    /**
     * Sets the online flag.
     * 
     * @param away
     *            true if so
     */
    public void setOnline(boolean away) {
        if (connection != null) {
            try {
                connection.setMyStatus(away ? UserStatus.ONLINE
                        : UserStatus.ONLINE);
            } catch (IOException e) {
                log.log(Level.SEVERE, "Failed to set status", e);
            }
        }
        super.setAway(away);
    }

    /**
     * Sets the away flag.
     * 
     * @param away
     *            true if so
     */
    public void setAway(boolean away) {
        if (connection != null) {
            try {
                connection.setMyStatus(away ? UserStatus.AWAY_FROM_COMPUTER
                        : UserStatus.ONLINE);
            } catch (IOException e) {
                log.log(Level.SEVERE, "Failed to set status", e);
            }
        }
        super.setAway(away);
    }

    /**
     * Sets the away flag.
     * 
     * @param busy
     *            true if so
     */
    public void setBusy(boolean busy) {
        if (connection != null) {
            try {
                connection.setMyStatus(busy ? UserStatus.BUSY
                        : UserStatus.ONLINE);
            } catch (IOException e) {
                log.log(Level.SEVERE, "Failed to set status", e);
            }
        }
        super.setAway(busy);
    }

    /**
     * Sets the away flag.
     * 
     * @param idle
     *            true if so
     */
    public void setIdle(boolean idle) {
        if (connection != null) {
            try {
                connection.setMyStatus(idle ? UserStatus.IDLE
                        : UserStatus.ONLINE);
            } catch (IOException e) {
                log.log(Level.SEVERE, "Failed to set status", e);
            }
        }
        super.setAway(idle);
    }

    /**
     * Sets the away flag.
     * 
     * @param phone
     *            true if so
     */
    public void setPhone(boolean phone) {
        if (connection != null) {
            try {
                connection.setMyStatus(phone ? UserStatus.ON_THE_PHONE
                        : UserStatus.ONLINE);
            } catch (IOException e) {
                log.log(Level.SEVERE, "Failed to set status", e);
            }
        }
        super.setAway(phone);
    }

    /**
     * Sets the away flag.
     * 
     * @param phone
     *            true if so
     */
    public void setLunch(boolean lunch) {
        if (connection != null) {
            try {
                connection.setMyStatus(lunch ? UserStatus.ON_THE_LUNCH
                        : UserStatus.ONLINE);
            } catch (IOException e) {
                log.log(Level.SEVERE, "Failed to set status", e);
            }
        }
        super.setAway(lunch);
    }

    /**
     * Sets the offline flag.
     * 
     * @param phone
     *            true if so
     */
    public void setInvisible(boolean offline) {
        if (connection != null) {
            try {
                connection.setMyStatus(offline ? UserStatus.INVISIBLE
                        : UserStatus.ONLINE);
            } catch (IOException e) {
                log.log(Level.SEVERE, "Failed to set status", e);
            }
        }
        super.setAway(offline);
    }

    /**
     * Sets the away flag.
     * 
     * @param phone
     *            true if so
     */
    public void setBRB(boolean brb) {
        if (connection != null) {
            try {
                connection.setMyStatus(brb ? UserStatus.BE_RIGHT_BACK
                        : UserStatus.ONLINE);
            } catch (IOException e) {
                log.log(Level.SEVERE, "Failed to set status", e);
            }
        }
        super.setAway(brb);
    }

    public void setStatus(String status) throws IOException {
        connection.setMyStatus(status);
    }

    public void setPresence(String friendlyName) throws IOException {
        connection.setMyFriendlyName(friendlyName);
    }

    public void setInitialStatus(String initStatus) throws IOException {
        connection.setInitialStatus(initStatus);
    }

    /**
     * Overide this message with code that sends the message out.
     * 
     * @param message
     *            to send
     * @throws java.io.IOException
     *             problems
     */
    protected void processMessage(Message message) throws IOException {

        final MimeMessage msg = new MimeMessage(message.getText());
        msg.setKind(MimeMessage.KIND_MESSAGE);
        SwitchboardSession ss = getSession("");

        if (ss == null) {
            for (ConnectionEventListener eventHandler : eventHandlers) {
                eventHandler
                        .errorOccured(
                                "Failed to send your message, try again. (Creating Session failed)",
                                null);
            }
        } else {
            try {
                ss.sendInstantMessage(msg);
            } catch (NullPointerException e) { // this catches both problems
                sessions.remove(message.getContact().getName());
                for (ConnectionEventListener eventHandler : eventHandlers) {
                    eventHandler.errorOccured(
                            "Failed to send your message, try again.", null);
                }
            }
        }
    } // processMessage()

    private SwitchboardSession getSession(String name) throws IOException {
        SwitchboardSession ss = sessions.get(name);
        if (ss == null) {
            try {
                ss = connection.doCallWait(name);
                if (ss != null) {
                    sessions.put(name, ss);
                }
            } catch (InterruptedException e) { //
            }
        }
        return ss;
    }

    /**
     * Overide this message with code that sends the message out.
     * 
     * @param message
     *            to send
     * @throws java.io.IOException
     *             problems
     */
    protected void processSecureMessage(Message message) throws IOException {
        processMessage(message); // todo go secure at some point
    }

    /**
     * Starts a file transfer.
     * 
     * @param ftl
     *            listener
     * @throws java.io.IOException
     *             on error
     */
    public void initiateFileTransfer(FileTransferListener ftl)
            throws IOException {
        SwitchboardSession session = getSession(ftl.getContactName());
        if (session != null) {
            try {
                connection.sendFileRequest(getUserName(), ftl.getFile(),
                        session);
            } catch (IOException e) {
                ftl.notifyFail();
            }
        }
    }

    /**
     * Sets up file for receival
     * 
     * @param ftl
     *            param
     * @param connectionInfo
     *            connection's info needed for transfer
     */
    public void acceptFileTransfer(FileTransferListener ftl,
            Object connectionInfo) {
        // Todo verify
        if (connectionInfo instanceof VolatileDownloader) {
            connection
                    .fireFileReceiveStartedEvent((VolatileDownloader) connectionInfo);
        } else {
            log.fine("MSNConnection.acceptFileTransfer not the right class "
                    + connectionInfo.getClass().getName());
        }
    }

    public void rejectFileTransfer() {
        // Todo change
    }

    public void setTimeout(int arg0) {
        // TODO Auto-generated method stub

    }

    public void changeStatus(UserStateType state, String status)
            throws BadConnectionException {


        if (connection.isLoggedIn()) {
            try {
                setPresence(status);
                if (state == UserStateType.ONLINE) {
                    setOnline(true);
                } else if (state == UserStateType.AWAY) {
                    setAway(true);
                } else if (state == UserStateType.BUSY) {
                    setBusy(true);
                } else if (state == UserStateType.BRB) {
                    setBRB(true);
                }

                else if (state == UserStateType.PHONE) {
                    setPhone(true);
                }

                else if (state == UserStateType.LUNCH) {
                    setLunch(true);
                }

                else if (state == UserStateType.INVISIBLE) {
                    setInvisible(true);
                } else {
                    setIdle(true);
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

    }

    public void createRoom(String room) throws XMPPException {
        // TODO Auto-generated method stub

    }

    public void disconnect() {
       this.disconnect(true);

    }

    public boolean doesExist(String userID) {
        // TODO Auto-generated method stub
        return false;
    }

    public ImageIcon getAvatarPicture(String userID) throws XMPPException {
        // TODO Auto-generated method stub
        return new ImageIcon(this.getClass().getResource(
                "/images/buddylist/statusIcons/MSN/MSN-Available.png"));
    }

    public ServerType getServerType() {
        // TODO Auto-generated method stub
        return null;
    }

    public void inviteFriend(String userID, String roomName)
            throws XMPPException {
        // TODO Auto-generated method stub

    }

    public boolean isConferenceChat() {
        // TODO Auto-generated method stub
        return false;
    }

    public boolean isFollowing(String userID) {
        // TODO Auto-generated method stub
        return false;
    }

    public boolean isValidUserID(String userID) {
        // TODO Auto-generated method stub
        return false;
    }

    public UserStateType retrieveState(String userID) {
        MsnFriend friend = msnFriend(userID);

        UserStateType userState = UserStateType.OFFLINE; // default return value

        if (connection.isLoggedIn()) {
            String userStateFromServer = getUserStatus(friend);

            if (userStateFromServer.equals("BSY")) {
                userState = UserStateType.BUSY;
            } else if (userStateFromServer.equals("AWY")) {
                userState = UserStateType.AWAY;
            } else if (userStateFromServer.equals("NLN")) {
                userState = UserStateType.ONLINE;
            } else if (userStateFromServer.equals("BRB")) {
                userState = UserStateType.BRB;
            } else if (userStateFromServer.equals("PHN")) {
                userState = UserStateType.PHONE;
            } else if (userStateFromServer.equals("LUN")) {
                userState = UserStateType.LUNCH;
            }

            else { // user is offline
                userState = UserStateType.OFFLINE;
            }
        }

        return userState;

    }

    public String retrieveStatus(String userID) {
        MsnFriend msnFriend = msnFriend(userID);

        return getUserFormattedFriendlyName(msnFriend);
    }

    public void sendFile(String filePath, String userID,
            ProgressMonitorScreen progress) throws XMPPException {
        // TODO Auto-generated method stub

    }

    public void sendMultMessage(String message, String roomName)
            throws BadConnectionException {
        // TODO Auto-generated method stub

    }

    public void setAvatarPicture(byte[] bytes) throws XMPPException {
        // TODO Auto-generated method stub

    }

    public void setAvatarPicture(File file) throws XMPPException {
        // TODO Auto-generated method stub

    }

    public void setAvatarPicture(URL url) throws XMPPException {
        // TODO Auto-generated method stub

    }

    public void setTypingState(int state, String userID)
            throws BadConnectionException, XMPPException {
        // TODO Auto-generated method stub

    }

}
