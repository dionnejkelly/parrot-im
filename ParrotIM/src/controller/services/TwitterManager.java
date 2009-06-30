package controller.services;

import java.net.URI;

import winterwell.jtwitter.Twitter;


public class TwitterManager {
	
	Twitter twitter;
	
	
	
	public TwitterManager(String userID, String password) {
		
		// Make a Twitter object
		twitter = new Twitter(userID,password);

	}
	
	
	private void updateTwitterStatus(String status) {
		twitter.updateStatus(status);
	}
	
	
	private String getTwitterStatus() {
		return twitter.getStatus().toString();
	}
	
	
	private URI getTwitterAvatar(String userID) {
		return twitter.getStatus(userID).getUser().getProfileImageUrl();
	}
	
	
	
	
	
	public static void main(String[] args) {
		
		TwitterManager twitt = new TwitterManager("cmpt275testing","abcdefghi");
		
		twitt.updateTwitterStatus("testing Parrot");
		
		System.out.println("My status = " + twitt.getTwitterStatus());
		
		
	}
	
	
	
	


}
