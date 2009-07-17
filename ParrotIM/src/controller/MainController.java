/* MainController.java
 * 
 * Programmed By:
 *     Jihoon Choi
 *     
 * Change Log:
 *         
 * Known Issues:
 *     1. Needs to be more modular; functionality needs to be split off.
 *     2. Unclear how to handle multiple connections; are there multiple
 *        controller objects?
 *        
 * Copyright (C) 2009  Pirate Captains
 * 
 * License: GNU General Public License version 2.
 * Full license can be found in ParrotIM/LICENSE.txt.
 */

package controller;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Panel;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.*;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.util.StringUtils;

import com.sun.image.codec.jpeg.ImageFormatException;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import view.options.MusicPlayer;
import view.styles.ProgressMonitorScreen;

import controller.chatbot.Chatbot;
import controller.services.BadConnectionException;
import controller.services.GenericConnection;
import controller.services.GoogleTalkManager;
import controller.services.ICQManager;
import controller.services.JabberManager;
import controller.services.TwitterManager;

import model.Model;
import model.dataType.AccountData;
import model.dataType.ChatCollectionData;
import model.dataType.Conversation;
import model.dataType.ConversationData;
import model.dataType.GoogleTalkAccountData;
import model.dataType.GoogleTalkUserData;
import model.dataType.ICQAccountData;
import model.dataType.ICQUserData;
import model.dataType.JabberAccountData;
import model.dataType.JabberUserData;
import model.dataType.MessageData;
import model.dataType.MultiConversationData;
import model.dataType.ProfileData;
import model.dataType.TwitterAccountData;
import model.dataType.TwitterUserData;
import model.dataType.UserData;
import model.dataType.tempData.FriendTempData;
import model.enumerations.ServerType;
import model.enumerations.TypingStateType;
import model.enumerations.UpdatedType;
import model.enumerations.UserStateType;

/**
 * Handles all connections involving XMPP protocol.
 */
public class MainController {

    /**
     * Allows the ChatClient to store data for the GUI.
     */
    private Model model;

    /**
     * Holds the Chatbot data that is initially set to null.
     */
    private Chatbot chatbot;

    private Vector<String> availableRoom = new Vector<String>();

    private String groupRoom;

    private int countRoom = 0;

    private DecimalFormat progressBar;
    
    /**
     * This is the constructor of Xmpp.
     * 
     * @param model
     */

    public MainController(Model model) {
        this.model = model;

    }

    /**
     * These are setter and getter of Xmpp class.
     * 
     */

    /**
     * This method is using to set the status of the user.
     * 
     * @param status
     */
    public void setStatus(String status, boolean changeTwitter) {
        // Updates status for all accounts
        // TODO may not wanted for twitter?
        for (AccountData a : model.getCurrentProfile().getAccountData()) {
        	if (changeTwitter && a.getServer() != ServerType.TWITTER){
	            try {
	                a.getConnection().changeStatus(
	                        model.getCurrentProfile().getState(), status);
	                model.setStatusMessage(model.getCurrentProfile().getName(), status);
	            } catch (BadConnectionException e) {
	                // TODO Throw something back?
	            } catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}
        }
        model.getCurrentProfile().setStatus(status);

        return;
    }

    public ImageIcon getAvatarPicture(UserData user) throws XMPPException {
        // TODO create an account selection GUI
        AccountData account = null; // Should be passed in!!
        GenericConnection connection = null;
        ImageIcon avatarPicture = null;

        // connection should be found from account!!
        try {
            account = model.findAccountByFriend(user);
            connection = account.getConnection();

            avatarPicture = connection.getAvatarPicture(user.getUserID());
        } catch (Exception e) {
            // (May be called if account is null... think: multi user chat)
            avatarPicture = new ImageIcon(this.getClass().getResource(
                    "/images/chatwindow/personal.png"));
        }

        return avatarPicture;
        // TODO make a more accurate Model.addFriend

    }

    public String getAccount() {
        AccountData account = null; // Should be passed in!!
        GenericConnection connection = null;

        // connection should be found from account!!
        account = model.getCurrentProfile().getAccountData().get(0);

        return account.getNickname();
    }

    public void setAvatarPicture(URL url) throws XMPPException, IOException,
            ClassNotFoundException, SQLException {
        ImageIcon imageIcon = new ImageIcon(url);
        byte[] byeArray = toByte(imageIcon);

        // TODO create an account selection GUI
        AccountData account = null; // Should be passed in!!
        GenericConnection connection = null;

        // connection should be found from account!!
        account = model.getCurrentProfile().getAccountData().get(0);
        connection = account.getConnection();

        System.out.println("Which connection = "
                + connection.getServerType().getServerList().get(0));
        connection.setAvatarPicture(byeArray);
        model.setAvatarDirectory(model.getCurrentProfile().getName(), url
                .toString());

        // TODO make a more accurate Model.addFriend

    }

    private byte[] toByte(ImageIcon i) throws ImageFormatException, IOException {
        // iconData is the original array of bytes

        Image img = i.getImage();

        Image imageResize = img.getScaledInstance(100, 100, 0);

        ImageIcon imageIconResize = new ImageIcon(imageResize);

        int resizeWidth = imageIconResize.getIconWidth();
        int resizeHeight = imageIconResize.getIconHeight();

        Panel p = new Panel();
        BufferedImage bi = new BufferedImage(resizeWidth, resizeHeight,
                BufferedImage.TYPE_INT_RGB);

        Graphics2D big = bi.createGraphics();
        big.drawImage(imageIconResize.getImage(), 0, 0, p);

        ByteArrayOutputStream os = new ByteArrayOutputStream();

        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(os);
        encoder.encode(bi);
        byte[] byteArray = os.toByteArray();

        return byteArray;

    }

    public void setAvatarPicture(File file) throws XMPPException {
        // TODO create an account selection GUI
        AccountData account = null; // Should be passed in!!
        GenericConnection connection = null;

        // connection should be found from account!!
        account = model.getCurrentProfile().getAccountData().get(0);
        connection = account.getConnection();

        System.out.println("Which connection = "
                + connection.getServerType().getServerList().get(0));
        connection.setAvatarPicture(file);

        // TODO make a more accurate Model.addFriend

    }

    // public void setAvatarPicture(URL url) throws XMPPException {
    // // TODO create an account selection GUI
    // AccountData account = null; // Should be passed in!!
    // GenericConnection connection = null;
    //
    // // connection should be found from account!!
    // account = model.getCurrentProfile().getAccountData().get(0);
    // connection = account.getConnection();
    //
    // System.out.println("Which connection = "
    // + connection.getServerType().getServerList().get(0));
    // connection.setAvatarPicture(url);
    //
    // // TODO make a more accurate Model.addFriend
    //
    // }

    /**
     * This method actually set presence of the user.
     * 
     * @param presenceStatus
     * @throws InterruptedException
     */
    public void setPresence(String stateString) {
        // Iterates over all accounts and sets the state
        // TODO may a user wants to go "invisible" in just one account?
        // TODO handle input from GUI, maybe enum type input?
        UserStateType state = null;

        if (stateString.equalsIgnoreCase("Online")) {
            state = UserStateType.ONLINE;
        } else if (stateString.equalsIgnoreCase("Away")) {
            state = UserStateType.AWAY;
        } else if (stateString.equalsIgnoreCase("Busy")) {
            state = UserStateType.BUSY;
        } else {
            // TODO implement me
            state = UserStateType.ONLINE;
        }

        for (AccountData a : model.getCurrentProfile().getAccountData()) {
            try {
                a.getConnection().changeStatus(state,
                        model.getCurrentProfile().getStatus());
            } catch (BadConnectionException e) {
                // TODO Throw something back?
            }
        }
        model.getCurrentProfile().setState(state);

        return;
    }

    /**
     * Attempts to log a user into the server based on the given account
     * information. If the current profile already exists, this account
     * information is added to it.
     * 
     * @param server
     * @param accountName
     * @param password
     * @throws XMPPException
     */
    public void login(AccountData account) throws BadConnectionException {
        GenericConnection connection = null;
        MainController controller = this;
        int port = 5223;

        // Determine which type of connection the account requires, and add it
        if (account instanceof GoogleTalkAccountData) {
            connection = new GoogleTalkManager(controller, model);
        } else if (account instanceof JabberAccountData) {
            // userID += "@" + serverAddress;
            connection = new JabberManager(controller, model);
        } else if (account instanceof TwitterAccountData) {
            connection = new TwitterManager(controller, model);
        } else if (account instanceof ICQAccountData) {
            connection = new ICQManager(controller, model);
        } else {
            // Other servers
        }
        account.setConnection(connection);

        connection.login(account.getUserID(), account.getPassword());

        // Set up friends' user data
        this.populateBuddyList(account);
        
        //set up chatbot
		try {
			model.initiateChatbotModel();
			chatbot = new Chatbot(model);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//	catch (Exception e){
//		
//		//INVESTIGATE: getQuestions in model
//		this.chatbot = new Chatbot();
//	}
        return;
    }

    /**
     * This method is used to login to XMPP through a profile .
     * 
     * @param profile
     * @throws XMPPException
     */
    public void loginProfile(ProfileData profile) throws BadConnectionException {
        model.getProfileCollection().setActiveProfile(profile);
        System.out.println("we're here!");
        // Log-in account by account
        for (AccountData a : profile.getAccountData()) {
            System.out.println("we're here2!");
            login(a);
        }

        return;
    }

    public void loginAsGuest(ServerType server, String userID, String password,
            String serverAddress) throws BadConnectionException {
        ProfileData createdProfile = null;
        AccountData createdAccount = null;

        createdProfile = new ProfileData("Guest");
        createdProfile.setGuestAccount(true);

        model.getProfileCollection().addProfile(createdProfile);
        model.getProfileCollection().setActiveProfile(createdProfile);

        createdAccount = Model.createAccount(userID, password, server);
        createdProfile.addAccount(createdAccount);

        login(createdAccount);

        return;
    }

    public void loginAsGuest(ServerType server, String userID, String password)
            throws BadConnectionException {
        loginAsGuest(server, userID, password, null);
    }

    /**
     * Disconnects all accounts associated with the current profile from the
     * server, and sets the current profile to null.
     */
    public void disconnect() {
        if (model.getCurrentProfile() != null) {
            for (AccountData a : model.getCurrentProfile().getAccountData()) {
                a.getConnection().disconnect();
            }
        }

        this.model.initializeAllVariables();

        return;
    }

    /**
     * This method is used to add a friend to the friend list.
     * 
     * @param userID
     */

    public void addFriend(String userID) {
        // TODO create an account selection GUI
        AccountData account = null; // Should be passed in!!
        GenericConnection connection = null;

        try {
            // connection should be found from account!!
            account = model.getCurrentProfile().getAccountData().get(0);
            connection = account.getConnection();
            connection.addFriend(userID);

            // TODO make a more accurate Model.addFriend
            model.addFriend(account.getServer(), userID);
        } catch (BadConnectionException e) {
            // TODO throw another exception to prompt for re-entry
            e.printStackTrace();
        }

        return;
    }
    
    public void modelAddFriend(String userID) {
    	AccountData account = account = model.getCurrentProfile().getAccountData().get(0);
    	model.addFriend(account.getServer(), userID);
    }

    /**
     * This method is used to remove a friend to the friend list.
     * 
     * @param userID
     */
    public void removeFriend(UserData friendToRemove) {
        boolean removed = false;
        GenericConnection connection = null;

        connection = model.findAccountByFriend(friendToRemove).getConnection();

        try {
            removed = connection.removeFriend(friendToRemove.getUserID());
        } catch (BadConnectionException e) {
            // TODO Make the GUI know the friend doesn't exist?
            e.printStackTrace();
        }
        if (removed || friendToRemove.isBlocked()) {
            model.removeFriend(friendToRemove);
        }

        return;
    }

    /**
     * This method is used to block a friend to the friend list.
     * 
     * @param userID
     */
    public void blockFriend(UserData friendToBlock) {
        // TODO convert this to use privacy list for XMPP
        boolean removed = false;
        GenericConnection connection = null;

        connection = model.findAccountByFriend(friendToBlock).getConnection();

        try {
            removed = connection.removeFriend(friendToBlock.getUserID());
        } catch (BadConnectionException e) {
            // TODO Make the GUI know the friend doesn't exist?
            e.printStackTrace();
        }
        if (removed) {
            model.blockFriend(friendToBlock);
        }

        return;
    }

    /**
     * This method is used to unblock a friend to the friend list.
     * 
     * @param userID
     */

    public void unblockFriend(UserData friendToUnblock) {
        // TODO convert this to use privacy list for XMPP
        AccountData account = null; // Should be passed in!!
        GenericConnection connection = null;

        try {
            account = model.findAccountByFriend(friendToUnblock);
            connection = account.getConnection();
            connection.addFriend(friendToUnblock.getUserID());

            model.unblockFriend(friendToUnblock);
        } catch (BadConnectionException e) {
            System.err.println("Error in unblocking friend.");
            e.printStackTrace();
        }

        return;
    }

    /**
     * This method is used to populate the buddy list of the user.
     * 
     * @param account
     */
    public void populateBuddyList(AccountData account) {
        Vector<FriendTempData> savedFriends = null;
        ArrayList<FriendTempData> friendList = null;
        UserData user = null;
        String userID = null;
        String nickname = null;
        String group = null;
        GenericConnection connection = null;

        // Get friends from the database to cross reference against
        // those found in Roster.
        savedFriends = model.getSavedFriends(account.getUserID());

        connection = account.getConnection();

        try {
            friendList = connection.retrieveFriendList();
        } catch (BadConnectionException e) {
            friendList = new ArrayList<FriendTempData>(); // make empty list
        }
        for (FriendTempData f : friendList) {
            // Decide which type of user to use
            userID = f.getUserID();
            nickname = f.getNickname();
            group = f.getGroup();

            if (nickname == null || nickname.equals("")
                    || nickname.equalsIgnoreCase(userID)) {
                nickname = StringUtils.parseName(userID);
            }

            if (account.getServer() == ServerType.GOOGLE_TALK) {
                user = new GoogleTalkUserData(userID);
                user.setNickname(f.getNickname());
            } else if (account.getServer() == ServerType.JABBER) {
                user = new JabberUserData(userID);
                user.setNickname(f.getNickname());
            } else if (account.getServer() == ServerType.TWITTER) {
                user = new TwitterUserData(userID);
                user.setNickname(f.getNickname());
            } else if (account.getServer() == ServerType.ICQ) {
                user = new ICQUserData(userID);
                user.setNickname(f.getNickname());
                user.setGroup(group);
            } else { // some other user
                // TODO implement me!
            }
            this.updateStateAndStatus(user, connection);

            /* Search the savedFriends to find if was saved locally */
            for (FriendTempData databaseFriend : savedFriends) {
                if (userID.equalsIgnoreCase(databaseFriend.getUserID())) {
                    user.setBlocked(databaseFriend.isBlocked());
                    savedFriends.remove(databaseFriend);
                    break;
                }
            }

            model.addFriend(account, user);
        }

        // Check if there are still saved friends to be added
        // and adds the roster friend if not blocked.
        for (FriendTempData f : savedFriends) {
            if (!f.isBlocked()) {
                // Temp fix for the smack "name is null" error

                // this.addFriend(f.getUserID());
            } else { // is blocked, need to add not on server

                // TODO, separate the strings better
                if (StringUtils.parseServer(f.getUserID()).equals("gmail.com")) {
                    user = new GoogleTalkUserData(f.getUserID());
                } else {
                    user = new JabberUserData(f.getUserID());
                }

                user.setBlocked(true);
                model.addFriend(account, user);
            }
        }

        model.forceNotify(UpdatedType.BUDDY);

        return;
    }

    public void startConversation(UserData friend, boolean focus) {
        ConversationData conversation = null;
        AccountData account = null;

        account = model.findAccountByFriend(friend);
        conversation = model.startConversation(account, friend);
        if (focus || model.numberOfConversations() < 2) {
            model.setActiveConversation(conversation);
        }

        return;
    }

    /**
     * changing typing state and send it to friends
     * 
     * @param state
     *            numbers that represent different state, it depends on the
     *            service
     */
    public void setTypingState(int state) {

        try {
            if (model.getActiveConversation() != null) {
                if (model.getActiveConversation().getUser() != null) {
                    GenericConnection connection = model
                            .getActiveConversation().getAccount()
                            .getConnection();
                    connection.setTypingState(state, model
                            .getActiveConversation().getUser().getUserID());
                }
            }

        } catch (BadConnectionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (XMPPException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return;

    }

    /**
     * update typingState in model
     * 
     * @param connection
     * @param typingState
     *            changing to this state
     * @param fromUserID
     */
    public void typingStateUpdated(GenericConnection connection,
            TypingStateType typingState, String fromUserID) {
        AccountData account = null;
        UserData userToUpdate = null;
        account = model.findAccountByConnection(connection);
        if (account != null) {
            userToUpdate = model.findUserByUserID(fromUserID);
        }
        model.setTypingState(userToUpdate, typingState);

        return;
    }

    /**
     * This method is using to send the message to friend. Explicitly sets a
     * receiver. If the receiver is unknown, then use the overloaded
     * sendMessage() which automatically sends the reply to the open
     * conversation.
     * 
     * @param messageString
     * @param to
     * @throws XMPPException
     * 
     */
    public void sendMessage(String messageString, String to, AccountData account)
            throws BadConnectionException {
        // TODO we may want a conversation object to be passed in
        ConversationData conversation = null;
        MessageData messageObject = null;
        String fromUser = null;
        UserData toUser = null;
        String font = "Arial"; // temp values
        String size = "4";
        boolean bold = false;
        boolean italics = false;
        boolean underlined = false;
        String color = "#000000";
        GenericConnection connection = null;

        toUser = model.findUserByUserID(to);
        conversation = model.findConversation(account, toUser);

        if (conversation == null) {
            model.startConversation(account, toUser);
            conversation = model.findConversation(account, toUser);
        }
        connection = conversation.getAccount().getConnection();

        fromUser = conversation.getAccount().getUserID();
        to = conversation.getUser().getUserID();

        messageObject = new MessageData(fromUser, messageString, font, size,
                bold, italics, underlined, color);

        connection.sendMessage(to, messageString);
        model.sendMessage(conversation, messageObject);

        return;
    }

    /**
     * Sends a message to the person in the current active conversation.
     * 
     * @param messageString
     * @param font
     * @param size
     * @throws XMPPException
     */
    public void sendMessage(String messageString, String font, String size,
            boolean bold, boolean italics, boolean underlined, String color)
            throws BadConnectionException {
        String to = null;
        MessageData messageObject = null;
        Conversation conversation = null;
        String fromUser = null;
        GenericConnection connection = null;

        // Default to sending to the active user
        conversation = model.getActiveConversation();
        connection = conversation.getAccount().getConnection();

        fromUser = conversation.getAccount().getUserID();

        to = conversation.getUser().getUserID();
        System.out.println("---------------------------------------------------- Who am I sending it to = " + to);

        to = conversation.getUser().getUserID();


        messageObject = new MessageData(fromUser, messageString, font, size,
                bold, italics, underlined, color);

        connection.sendMessage(to, messageString);
        model.sendMessage(conversation, messageObject);

        return;
    }

    /* Manipulation of conversations */

    /**
     * Changes a conversation to another user.
     * 
     * @param accountName
     */

    public void changeConversation(Conversation conversation) {
        if (conversation != null) {
            model.setActiveConversation(conversation);
        }

        return;
    }

    /**
     * 
     * @param userToUpdate
     * @param connection
     */

    public void updateStateAndStatus(UserData userToUpdate,
            GenericConnection connection) {
        String status = null;
        String userID = userToUpdate.getUserID();

        try {
            status = connection.retrieveStatus(userID);

            userToUpdate.setStatus(status);
            if (connection instanceof GoogleTalkManager) {
                userToUpdate.setState(((GoogleTalkManager) connection)
                        .retrieveState(userID));
            } else if (connection instanceof JabberManager) {
                userToUpdate.setState(((JabberManager) connection)
                        .retrieveState(userID));
            } else if (connection instanceof TwitterManager) {
                ((TwitterUserData) userToUpdate)
                        .setMinutesSinceUpdate(((TwitterManager) connection)
                                .getMinutesSinceStatusChange(userID));
            }
        } catch (BadConnectionException e) {
            status = "(loading...)";
        }

        model.forceNotify(UpdatedType.BUDDY);

        return;
    }

    /*
     * SECTION: Profile manipulation
     */

    /**
     * Adds profile to the database.
     * 
     * @param name
     * @param password
     * @param defaultProfile
     */

    public void addProfile(String name, String password, boolean defaultProfile) {
        this.model.addProfile(name, password, defaultProfile);

        return;
    }

    /**
     * Removes profile from the database.
     * 
     * @param name
     */

    public void removeProfile(String name) {
        this.model.removeProfile(name);

        return;
    }

    /**
     * Adds account to the database.
     * 
     * @param profile
     * @param server
     * @param account
     * @param password
     */
    public void addAccount(String profile, ServerType server, String account,
            String password) {
        String serverName = null;

        serverName = server.toString();

        this.model.addAccount(profile, serverName, "", account, password);
    }

    public void addAccount(String profile, ServerType server,
            String serverAddress, String account, String password) {
        String serverName = null;

        serverName = server.toString();

        this.model.addAccount(profile, serverName, serverAddress, account,
                password);
    }

    /**
     * Adds account to the database.
     * 
     * @param profile
     * @param account
     */

    public void removeAccount(String profile, String account) {
        this.model.removeAccount(profile, account);

        return;
    }

    // Response methods

    public void friendUpdated(GenericConnection connection, String userID) {
        AccountData account = null;
        UserData userToUpdate = null;

        account = model.findAccountByConnection(connection);
        if (account != null) {
            userToUpdate = account.findFriendByUserID(userID);
        }
        updateStateAndStatus(userToUpdate, connection);

        return;
    }

    public void messageReceived(String fromUserID, String toUserID,
            String message) {
        MessageData messageData = null;
        AccountData account = null;

        messageData = new MessageData(fromUserID, message, "font", "4", false,
                false, false, "#000000");
        account = model.findAccountByUserID(toUserID);

        MusicPlayer receiveMusic = new MusicPlayer(
                "src/audio/message/receiveMessage.wav", model);
        model.receiveMessage(account, messageData);

        // Automatically add chatbot reply, if enabled
        if (model.getCurrentProfile().isChatbotEnabled()) {
            try {
                chatbot.get_input(message);
                String response = chatbot.respond();

                if (response.equals("")) {
                    response = chatbot.signon();
                }
                sendMessage(response, fromUserID, account);
            } catch (Exception e) {
                System.err.println("Error with chatbot: messageReceived()");
                e.printStackTrace();
            }
        }

        return;
    }

    public void refreshFriends(GenericConnection connection) {
        AccountData account = null;

        account = model.findAccountByConnection(connection);

        if (account != null) {
            for (UserData u : account.getFriends()) {
                this.updateStateAndStatus(u, connection);
            }
        }

        model.forceNotify(UpdatedType.BUDDY);

        return;
    }

    public GenericConnection getConnection() {
        AccountData account = null; // Should be passed in!!
        GenericConnection connection = null;

        // connection should be found from account!!
        account = model.getCurrentProfile().getAccountData().get(0);
        connection = account.getConnection();

        return connection;
    }

    public void sendFile() {
    	
    	 AccountData account = null; // Should be passed in!!
         Conversation conversation = null;

         GenericConnection connection = null;

         // Default to sending to the active user
         conversation = model.getActiveConversation();


     
        String to = conversation.getUser().getUserID();
         
    
        if (isValidUserID(to)) {
        	  // connection should be found from account!!
            account = model.getCurrentProfile().getAccountData().get(0);
            connection = account.getConnection();
            System.out.println("Start Transfering File... " + to);
       		
        
        	JFileChooser fileChooser = new JFileChooser();
    		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
    		fileChooser.setMultiSelectionEnabled(false);
    		
        	int fileConfirmation = fileChooser.showOpenDialog(null);
        	
        	if(fileConfirmation == JFileChooser.APPROVE_OPTION) {
        	
        		File selectedFile = fileChooser.getSelectedFile();

        		 
 
        		 long fileSize = selectedFile.length() / 1000;
        		System.out.println("The file size = " + fileSize + " KB");
        		
        		if (fileSize < 64) {
        			ProgressMonitorScreen progress = new ProgressMonitorScreen();
            		try {
        			String filePath = fileChooser.getSelectedFile().getPath();
					connection.sendFile(to, filePath, progress);
            		} catch (XMPPException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
            		}
        			
        		}
        		
        		else {
        			JOptionPane.showMessageDialog(null, "Please choose a file that does not exceed more than 60 KB.",
                            "Failed", JOptionPane.ERROR_MESSAGE);
        		}

				
        	}
           
        }
        
        else {
        	JOptionPane.showMessageDialog(null, "Cannot send file because " + to + " does not support file receiving.",
                    "Failed", JOptionPane.ERROR_MESSAGE);
        }
        
       
        
    }
    
  
	

    public boolean isValidUserID(String userID) {
        AccountData account = null; // Should be passed in!!
        GenericConnection connection = null;

        // connection should be found from account!!
        account = model.getCurrentProfile().getAccountData().get(0);
        connection = account.getConnection();

        return connection.isValidUserID(userID);
    }

    public void create(String nickname) {
        MultiConversationData multiConversation = null;
        groupRoom = "Parrot" + countRoom;
        availableRoom.add(groupRoom);

        System.out.println("From main controller = "
                + availableRoom.get(countRoom));
        countRoom++;

        AccountData account = null; // Should be passed in!!
        GenericConnection connection = null;

        // connection should be found from account!!
        account = model.getCurrentProfile().getAccountData().get(0);
        connection = account.getConnection();

        try {
			connection.createRoom(groupRoom);
		} catch (XMPPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        multiConversation = new MultiConversationData(groupRoom, account);
        model.getChatCollection().addConversation(multiConversation);

        return;
    }

    public void inviteFriend(String userID, String roomName) {
        AccountData account = null; // Should be passed in!!
        GenericConnection connection = null;

        // connection should be found from account!!
        account = model.getCurrentProfile().getAccountData().get(0);
        connection = account.getConnection();

        try {
            connection.inviteFriend(userID, roomName);
        } catch (XMPPException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public Vector<String> getAvailableRoom() {
        return availableRoom;
    }

    public void setAvailableRoom(String roomName) {
        availableRoom.add(roomName);
        countRoom++;
    }
    
    public boolean isConferenceChat() {
    	  AccountData account = null; // Should be passed in!!
          GenericConnection connection = null;

          // connection should be found from account!!
          account = model.getCurrentProfile().getAccountData().get(0);
          connection = account.getConnection();
          
          return connection.isConferenceChat();
    }
    
    public void sendMultMessage(String message, String roomName, AccountData account) throws BadConnectionException {
    	 ConversationData conversation = null;
         MessageData messageObject = null;
         String fromUser = null;
         UserData toUser = null;
         String font = "Arial"; // temp values
         String size = "4";
         boolean bold = false;
         boolean italics = false;
         boolean underlined = false;
         String color = "#000000";
         GenericConnection connection = null;

         toUser = model.findUserByUserID(roomName);
         conversation = model.findConversation(account, toUser);

         if (conversation == null) {
             model.startConversation(account, toUser);
             conversation = model.findConversation(account, toUser);
         }
         connection = conversation.getAccount().getConnection();

         fromUser = conversation.getAccount().getUserID();
         roomName = conversation.getUser().getUserID();

         messageObject = new MessageData(fromUser, message, font, size,
                 bold, italics, underlined, color);

         connection.sendMultMessage(message, roomName);
         model.sendMessage(conversation, messageObject);

         
        
        
        return;
    	
    }
    
    public void sendMultMessage(String message, String roomName) throws BadConnectionException {
    	AccountData account = null; // Should be passed in!!
        GenericConnection connection = null;

        // connection should be found from account!!
        account = model.getCurrentProfile().getAccountData().get(0);
        connection = account.getConnection();
        
        connection.sendMultMessage(message, roomName);
        
        
    }
    
    public void sendMultMessage(String messageString, String roomName, String font, String size,
            boolean bold, boolean italics, boolean underlined, String color)
            throws BadConnectionException {
        String to = null;
        MessageData messageObject = null;
        MultiConversationData conversation = null;
        String fromUser = null;
        GenericConnection connection = null;
        ChatCollectionData chatCollection = null;

        // Default to sending to the active user
        chatCollection = model.getChatCollection();
        try {
            conversation = (MultiConversationData) chatCollection.getActiveConversation();
        } catch (ClassCastException e) {
            System.err.println("sendMultMessage took in a single conversation. Oops?");
            e.printStackTrace();
            throw new IllegalArgumentException();
        }
        connection = conversation.getAccount().getConnection();

        fromUser = conversation.getAccount().getUserID();
        //to = conversation.getUser().getUserID();

        messageObject = new MessageData(fromUser, messageString, font, size,
                bold, italics, underlined, color);

        sendMultMessage(messageString, roomName);
        if (chatCollection.isHidden(conversation)) {
            chatCollection.activateConversation(conversation);
        }
        chatCollection.forceUpdate();
        conversation.addMessage(messageObject);

        return;
    }
}
