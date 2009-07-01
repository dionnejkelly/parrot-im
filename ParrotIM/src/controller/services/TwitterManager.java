package controller.services;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import model.dataType.tempData.FriendTempData;
import model.enumerations.UserStateType;

import winterwell.jtwitter.Twitter;
import winterwell.jtwitter.Twitter.Status;
import winterwell.jtwitter.Twitter.User;

/**
 * Handles all connections involving Twitter protocol.
 */

public class TwitterManager implements GenericConnection {

    /**
     * Connection to twitter.
     */

    private Twitter twitter;

    /**
     * Attempts to log a user into the server based on the given account
     * information. If the current profile already exists, this account
     * information is added to it.
     * 
     * @param userID
     * @param password
     */

    public TwitterManager(String userID, String password) {

        // Make a Twitter object
        twitter = new Twitter(userID, password);

    }

    public TwitterManager() {
        this.twitter = null;
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
            System.err.println("Error in adding friend in Twitter");
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
            throws BadConnectionException {
        // TODO figure out state
        try {
            twitter.updateStatus(status);
        } catch (Exception e) {
            System.err.println("Error posting status in twitter");
            e.printStackTrace();
            throw new BadConnectionException();
        }
        return;
    }

    public void disconnect() {
        twitter = null; // Cannot actually disconnect in Twitter

        return;
    }

    public void login(String userID, String password, String server, int port)
            throws BadConnectionException {
               
        // server and port unneeded for twitter
                
        try {
            twitter = new Twitter(userID, password);
        } catch (Exception e) {
            System.err.println("Error logging into twitter");
            e.printStackTrace();
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
            System.err.println("Error removing a friend in twitter");
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
            System.err.println("Error getting friend list in twitter");
            e.printStackTrace();
            throw new BadConnectionException();
        }

        for (Twitter.User f : friends) {
            localFriends.add(new FriendTempData(f.getScreenName(), f
                    .getScreenName(), f.getStatus().getText(),
                    UserStateType.OFFLINE, false));
        }

        return localFriends;
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
        } catch (Exception e) {
            System.err.println("Error getting friend status in twitter");
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
            System.err.println("Error in sendMessage in Twitter");
            e.printStackTrace();
            throw new BadConnectionException();
        }

    }

    /**
     * This method is used to check if the user is following.
     * 
     * @param userID
     * @return boolean
     */

    private boolean isFollowing(String userID) {
        return twitter.isFollower(userID);
    }

    /**
     * This method is used to check if the user exists.
     * 
     * @param userID
     * @return boolean
     */

    private boolean doesExist(String userID) {
        return twitter.userExists(userID);
    }

    /**
     * Returns a recent updated status.
     * 
     * @return String
     */

    private String getMyRecentStatus() {
        return twitter.getStatus().toString();
    }

    /**
     * Returns the 20 most recent status posted in the last 24 hours from the
     * authenticating user.
     * 
     * @return String
     */

    private List<Status> getMyStatus() {
        return twitter.getUserTimeline();
    }

    /**
     * Returns the 20 most recent replies/mentions in the last 24 hours from the
     * authenticating user.
     * 
     * @return String
     */

    private List<Status> getMyReplies() {
        return twitter.getReplies();
    }

    /**
     * Returns the URL link of the user.
     * 
     * @param userID
     * @return URL
     */

    private URI getTwitterAvatar(String userID) {
        return twitter.getStatus(userID).getUser().getProfileImageUrl();
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

    /**
     * This method is used to return the most recent direct message.
     * 
     * @return String
     */

    private String receiveRecentMessage() {
        return receiveMessage().get(twitter.getDirectMessages().size() - 1)
                .getText();
    }

    /**
     * This method is used to return a list of the followers.
     * 
     * @return List<User>
     */

    private List<User> retrieveFollowerList() {
        return twitter.getFollowers();

    }

    /**
     * This method is used to return the 20 most recent statuses posted in the
     * last 24 hours from the authenticating user.
     * 
     * @return List<Status>
     */

    private List<Status> getFriendsStatus() {
        return twitter.getUserTimeline();

    }

    /**
     * This method is used to return the most recent statuses posted in the last
     * 24 hours from the given user.
     * 
     * @return List<Status>
     */

    private List<Status> getFriendsStatus(String userID) {
        return twitter.getUserTimeline(userID);

    }

    public static void main(String[] args) {

        TwitterManager twitt = new TwitterManager("cmpt275testing", "abcdefghi");

        // twitt.updateTwitterStatus("testing Parrot");
        //		
        // System.out.println("My status = " + twitt.getTwitterStatus());

        // twitt.sendMessage("jfox2",
        // "Hi Jordan, this is a testing and I hope you are having fun with creating a new framework for Twitter.");

        for (int i = 0; i < twitt.getFriendsStatus("Fahelium").size(); i++) {
            // System.out.println("Message = " +
            // twitt.receiveMessage().get(i).getText());
            System.out.println("Friends = "
                    + twitt.getFriendsStatus("Fahelium").get(i).getText());

        }

        // System.out.println("Friends = " +
        // twitt.getFriendsLatestStatus("Fahelium"));

    }

    @Override
    public int hashCode() {
        int hash = 7;

        hash = hash * 31 + "Twitter".hashCode();
        hash = hash * 31 + this.twitter.hashCode();

        return hash;
    }

}
