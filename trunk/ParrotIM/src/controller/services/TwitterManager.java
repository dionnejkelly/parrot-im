package controller.services;

import java.net.URI;
import java.util.List;

import winterwell.jtwitter.Twitter;
import winterwell.jtwitter.Twitter.Status;
import winterwell.jtwitter.Twitter.User;

/**
 * Handles all connections involving Twitter protocol.
 */

public class TwitterManager {
	
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
		twitter = new Twitter(userID,password);

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
     * This method is using to set the status of the user.
     * 
     * @param status
     */
	
	private void changeStatus(String status) {
		twitter.updateStatus(status);
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
     * Returns the 20 most recent status posted in 
     * the last 24 hours from the authenticating user.
     * 
     * @return String
     */
	
	private List<Status> getMyStatus() {
		return twitter.getUserTimeline();
	}
	
	/**
     * Returns the 20 most recent replies/mentions in 
     * the last 24 hours from the authenticating user.
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
     * Sends message to a user. Maximum of 140 characters allowed.
     * 
     * @param userId
     * @param message
     */
	
	private void sendMessage(String userID, String message) {
		
		twitter.sendMessage(userID, message);
		
	}
	
	/**
     * This method is used to add a friend to the friend list.
     * 
     * @param userID
     */
	
	private void addFriend(String userID) {
		twitter.follow(userID);
	}
	
	/**
     * This method is used to remove a friend from the friend list.
     * 
     * @param userID
     */
	
	private void removeFriend(String userID) {
		twitter.stopFollowing(userID);
	
	}
	
	/**
     * This method is used to return a list of the direct 
     * messages sent to the authenticating user.
     * 
     * @return List<Twitter.Message>
     */
	
	private List<Twitter.Message> receiveMessage() {
		 return twitter.getDirectMessages();
		
	}
	
	/**
     * This method is used to return the most
     * recent direct message.
     * 
     * @return String
     */
	
	private String receiveRecentMessage() {
		return receiveMessage().get(twitter.getDirectMessages().size() - 1).getText();
	}
	
	/**
     * This method is used to return a list of the  
     * followers.
     * 
     * @return List<User>
     */
	
	private List<User> retrieveFollowerList() {
		return twitter.getFollowers();
		
	}
	
	/**
     * This method is used to return the authenticating 
     * user's (latest 100) friends, each with current status 
     * inline.
     * (Both followers and a user who wants to follow you)
     * 
     * @return List<User>
     */
	
	private List<User> retrieveFriendList() {
		return twitter.getFriends();
		
	}
	
	/**
     * This method is used to return the 20 most recent statuses 
     * posted in the last 24 hours from the authenticating user.
     * 
     * @return List<Status>
     */
	
	private List<Status> getFriendsStatus() {
		return twitter.getUserTimeline();
		
	}
	
	/**
     * This method is used to return the most recent statuses 
     * posted in the last 24 hours from the given user.
     * 
     * @return List<Status>
     */
	
	private List<Status> getFriendsStatus(String userID) {
		return twitter.getUserTimeline(userID);
		
	}
	
	/**
     * This method is used to return the most recent status 
     * posted in the last 24 hours from the given user.
     * 
     * @return List<Status>
     */
	
	private String getFriendsRecentStatus(String userID) {
		return twitter.getStatus(userID).getText();
	}
	
	
	
	
	
	public static void main(String[] args) {
		
		TwitterManager twitt = new TwitterManager("cmpt275testing","abcdefghi");
		
//		twitt.updateTwitterStatus("testing Parrot");
//		
//		System.out.println("My status = " + twitt.getTwitterStatus());
		
		
		//twitt.sendMessage("jfox2", "Hi Jordan, this is a testing and I hope you are having fun with creating a new framework for Twitter.");
		
		
		for(int i = 0; i < twitt.getFriendsStatus("Fahelium").size(); i++) {
			//System.out.println("Message = " + twitt.receiveMessage().get(i).getText());
			System.out.println("Friends = " + twitt.getFriendsStatus("Fahelium").get(i).getText());
			
		}
		
		
		//System.out.println("Friends = " + twitt.getFriendsLatestStatus("Fahelium"));
		
		
		
		
		
	}
	
	
	
	


}
