package model;

/**
 * Holds all information represented in a chat window.
 */
public class ChatWindowData {
    
	/*
	 * The other user that you are talking to, not yourself.
	 */
	private UserData user;
	
	/*
	 * Chat history for just this session.
	 */
    private String text;
    
    public ChatWindowData(UserData user) {
    	this.user = user;
    	this.text = "";
    }
    
    public UserData getUser() {
    	return user;
    }
    
    public void setUser(UserData user) {
    	this.user = user;
    	return;
    }
    
    public String getText() {
    	return text;
    }
    
    public void setText(String text) {
    	this.text = text;
    	return;
    }
    
    public void addMessageToText(String addedText) {
    	this.text += addedText; // TODO sort out where the \n goes.
    	return;
    }
}
