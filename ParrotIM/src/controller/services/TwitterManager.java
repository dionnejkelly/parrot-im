package controller.services;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import model.Model;
import model.dataType.tempData.FriendTempData;
import model.enumerations.ServerType;
import model.enumerations.UserStateType;

import org.jivesoftware.smack.XMPPException;

import view.styles.ProgressMonitorScreen;
import winterwell.jtwitter.Twitter;
import winterwell.jtwitter.TwitterException;
import winterwell.jtwitter.Twitter.Status;
import controller.MainController;

/**
 * Handles all connections involving Twitter protocol.
 */

public class TwitterManager implements GenericConnection {

    /**
     * Connection to twitter.
     */
    private Twitter twitter;

    private GenericConnection genericConnection;

    private MainController controller;

    private ArrayList<Long> friendLastUpdates;

    private ArrayList<String> friendList;

    private PollingThread poller;

    private volatile Thread stopBlinker;
    
    private boolean isConnected = false;

//    public static void main(String[] args) throws BadConnectionException {
//        TwitterManager twit = new TwitterManager();
//        twit.login("cmpt275testing", "abcdefghi");
//
//        boolean doesit = twit.doesExist("parrotim");
//
//        // if (twit.isFollowing("parrotimtest")) {
//        //    		
//        // }
//        twit.removeFriend("abc");
//        System.out.println("Does user exists? " + doesit);
//
//    }

    public TwitterManager() {

    }

    /**
     * Instantiates a new TwitterManager that should be associated with one
     * TwitterAccount. Provides all the methods to communicate with the Twitter
     * servers. Also generates a polling method that will search the server for
     * updates on a timer, and then report any changes back to the controller.
     * 
     * @param controller
     *            The MainController object that handles the main program flow.
     */
    public TwitterManager(MainController controller, Model model) {
        // PollingThread poller = null;
        this.twitter = null;
        this.genericConnection = this;
        this.controller = controller;
        this.friendLastUpdates = new ArrayList<Long>();
        this.friendList = new ArrayList<String>();

        // // Check server every 30 seconds for updates
        // poller = new PollingThread();
        // poller.start();
    }

    /**
     * This method is used to add a friend to the friend list.
     * 
     * @param userID
     */
    public void addFriend(String userID) throws BadConnectionException {
        try {
            twitter.follow(userID);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadConnectionException();
        }

        return;
    }

    /**
     * This method is using to set the status of the user.
     * 
     * @param status
     */
    public void changeStatus(UserStateType state, String status)
            throws BadConnectionException, TwitterException {
        // TODO figure out state
        try {
            twitter.updateStatus(status);
        } catch (TwitterException e) {
            e.printStackTrace();
            throw new BadConnectionException();
        }
        return;
    }

    public void disconnect() {
        twitter = null; // Cannot actually disconnect in Twitter

        // stop the thread safely
        stopThread();

        return;
    }

    /**
     * Attempts to log a user into the server based on the given account
     * information. If the current profile already exists, this account
     * information is added to it.
     * 
     * @param userID
     * @param password
     */
    public void login(String userID, String password)
            throws BadConnectionException {

        // server and port unneeded for twitter

        try {
            twitter = new Twitter(userID, password);
            // start the polling only when it is logged in
            // Check server every 30 seconds for updates
            poller = new PollingThread();
            poller.start();
            isConnected = true;
            
            this.getMyRecentStatus();
           
        } catch (Exception e) {
            e.printStackTrace();
            isConnected = false;
            ImageIcon twitterIcon = new ImageIcon(this.getClass().getResource(
            "/images/buddylist/twitter_logo.png"));
            JOptionPane.showMessageDialog(null, "Error signing into Twitter!\nUser name and password do not match.", "Error", JOptionPane.ERROR_MESSAGE);
            throw new BadConnectionException();
        }

        return;
    }

    /**
     * This method is used to remove a friend from the friend list.
     * 
     * @param userID
     */
    public boolean removeFriend(String userID) throws BadConnectionException {
        Twitter.User friend = null;

        try {
            friend = twitter.stopFollowing(userID);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadConnectionException();
        }

        return friend != null;
    }

    /**
     * This method is used to return the authenticating user's (latest 100)
     * friends, each with current status inline. (Both followers and a user who
     * wants to follow you)
     * 
     * @return List<User>
     */
    public ArrayList<FriendTempData> retrieveFriendList()
            throws BadConnectionException {
        List<Twitter.User> friends = null; // from server
        ArrayList<FriendTempData> localFriends = null;

        localFriends = new ArrayList<FriendTempData>();
        try {
            friends = twitter.getFriends();
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadConnectionException();
        }

        for (Twitter.User f : friends) {
            localFriends.add(new FriendTempData(new Long(f.getId()).toString(),
                    f.getScreenName(), f.getStatus().getText(),
                    UserStateType.OFFLINE, false));
        }

        return localFriends;
    }

    public ArrayList<Twitter.Status> retrieveRecentTweets()
            throws BadConnectionException {
        List<Twitter.Status> tweets = null;
        // ArrayList<FriendTempData> recentTweets = null;

        // recentTweets = new ArrayList<FriendTempData>();
        try {
            tweets = twitter.getFriendsTimeline();
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadConnectionException();
        }

        /*
         * for (Twitter.Status s : tweets) { recentTweets.add(new
         * FriendTempData(s.user.getName(),s.user.getScreenName(),
         * s.getText(),UserStateType.OFFLINE,false)); } return recentTweets;
         */
        return (ArrayList<Status>) tweets;
    }

    public UserStateType retrieveState(String userID) {
        // no real "states" in Twitter. Maybe remove from the interface spec?
        return UserStateType.OFFLINE;
    }

    /**
     * This method is used to return the most recent status posted in the last
     * 24 hours from the given user.
     * 
     * @return List<Status>
     */
    public String retrieveStatus(String userID) throws BadConnectionException {
        String status = "";

        try {
            status = twitter.getStatus(userID).getText();
        } catch (TwitterException e) {

            e.printStackTrace();
            throw new BadConnectionException();
        }

        return status;
    }

    /**
     * Sends message to a user. Maximum of 140 characters allowed.
     * 
     * @param userId
     * @param message
     */
    public void sendMessage(String toUserID, String message)
            throws BadConnectionException {
        try {
            twitter.sendMessage(toUserID, message);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadConnectionException();
        }

    }

    public int getMinutesSinceStatusChange(String userID)
            throws BadConnectionException {
        long minutesAgo = 16384;
        Date createdAt = null;
        Date now = null;

        // Get the times in milliseconds.
        try {
            createdAt = twitter.getStatus(userID).getCreatedAt();
        } catch (Exception e) {
//            System.err.println("No user to get minutes of.");
            e.printStackTrace();
            throw new BadConnectionException();
        }
        now = new Date();

        // Computes the difference in milliseconds
        minutesAgo = (now.getTime() - createdAt.getTime());

        // Transfer from milliseconds to minutes
        minutesAgo = minutesAgo / 60000;

        return (int) minutesAgo;
    }

    public ServerType getServerType() {
        return ServerType.TWITTER;
    }

    private void stopThread() {
        stopBlinker = null;
    }

    // Section
    // Polling methods

    private class PollingThread extends Thread {

        public void run() {
            try {
                sleep(10000); // Delay for 5 seconds
            } catch (InterruptedException e) {
                //System.err.println("Threading error");
                e.printStackTrace();
            }
            Thread thisThread = Thread.currentThread();

            while (stopBlinker == thisThread) {

                try {
                    // Thread.currentThread();
                    Thread.sleep(30000);
                    controller.refreshFriends(genericConnection);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } // Delay for 30 seconds

                if (twitter != null) {
                    updateLocalFriendList();
                }

            }
        }
    }

    private void updateLocalFriendList() {
        List<Twitter.User> friends = null; // from server
        int index = -1;

        try {
            friends = twitter.getFriends();
        } catch (Exception e) {
            //System.err.println("Error getting friend list in twitter");
            e.printStackTrace();
        }

        // messages = twitter.getDirectMessages();

        for (Twitter.User f : friends) {

            // Set up index
            index = -1;
            for (int i = 0; i < friendList.size(); i++) {
                if (friendList.get(i).equalsIgnoreCase(f.getScreenName())) {
                    index = i;
                    break;
                }
            }
            if (index == -1) {
                friendList.add(f.getScreenName());
                friendLastUpdates.add(0L);
                index = friendList.size() - 1;
            }

            /*
             * statuses = twitter.getUserTimeline(f.getScreenName()); for (int i
             * = statuses.size() - 1; i >= 0; i--) { for (int j =
             * messages.size() - 1; j >= 0 &&
             * messages.get(j).getCreatedAt().compareTo(
             * statuses.get(i).getCreatedAt()) < 0; j--) { if
             * (messages.get(j).getUser().getScreenName()
             * .equalsIgnoreCase(friendList.get(index)) &&
             * friendLastUpdates.get(index) < messages.get(j)
             * .getCreatedAt().getTime()) { friendLastUpdates.set(index,
             * messages.get(j) .getCreatedAt().getTime());
             * controller.hiddenMessageReceived(f.getScreenName(),
             * thisScreenName, "@" + thisScreenName + " " +
             * messages.get(j).getText()); } } if (friendLastUpdates.get(index)
             * < statuses.get(i) .getCreatedAt().getTime()) {
             * friendLastUpdates.set(index, statuses.get(i).getCreatedAt()
             * .getTime()); controller.hiddenMessageReceived(f.getScreenName(),
             * thisScreenName, statuses.get(i).getText()); } }
             */
        }

        return;
    }

    /**
     * This method is used to check if the user is following.
     * 
     * @param userID
     * @return boolean
     */

    public boolean isFollowing(String userID) {
        return twitter.isFollowing(userID);
    }

    /**
     * This method is used to check if the user is a follower.
     * 
     * @param userID
     * @return boolean
     */

    public boolean isFollower(String userID) {
        return twitter.isFollower(userID);
    }

    /**
     * This method is used to check if the user exists.
     * 
     * @param userID
     * @return boolean
     */

    public boolean doesExist(String userID) {
        return twitter.userExists(userID);
    }

    /**
     * Returns a recent updated status.
     * 
     * @return String
     */

    public String getMyRecentStatus() {
        return twitter.getStatus().toString();
    }

    /**
     * This method is used to return a list of the direct messages sent to the
     * authenticating user.
     * 
     * @return List<Twitter.Message>
     */

    private List<Twitter.Message> receiveMessage() {
        return twitter.getDirectMessages();

    }

    @Override
    public int hashCode() {
        int hash = 7;

        hash = hash * 31 + "Twitter".hashCode();
        hash = hash * 31 + this.twitter.hashCode();

        return hash;
    }

    // @Override

    public ImageIcon getAvatarPicture(String userID) throws XMPPException {

        URI userAvatar =
                twitter.getStatus(userID).getUser().getProfileImageUrl();
        // JOptionPane.showInputDialog(userAvatar);
       // System.out.println("URL: " + userAvatar);
        try {
            URL where = new URL(userAvatar.toString());
            ImageIcon userImageAvatar = new ImageIcon(where);
            return userImageAvatar;
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return new ImageIcon(this.getClass().getResource(
                "/images/chatwindow/personal.png"));

    }

    public void setTypingState(int state, String UserID)
            throws BadConnectionException, XMPPException {
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

	public boolean isConnected() {
		// TODO Auto-generated method stub
		return isConnected;
	}

    // public boolean isTyping() {
    // // TODO Auto-generated method stub
    // return false;
    // }

}
