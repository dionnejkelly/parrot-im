package model;

import java.util.Observable;

/**
 * Holds all data pertaining to a chat window, including names and
 * unique IDs. 
 */ 
public class ChatData extends Observable {
	/* Set the members to protected in case we decide to extend the class. */
    protected int windowID;
    protected String yourUsername;
    protected String otherUsername;
    
    public ChatData(int windowID, String yourUsername, String otherUsername) {
    	this.windowID = windowID;
    	this.yourUsername = yourUsername;
    	this.otherUsername = otherUsername;
    }
    
    public int getWindowID() {
    	return this.windowID;
    }
    
    public String getYourUsername() {
    	return this.yourUsername;
    }
    
    public void setYourUsername(String name) {
    	this.yourUsername = name;
    	setChanged();
    	notifyObservers();
    	return;
    }
    
    public String getOtherUsername() {
    	return this.otherUsername;
    }  
}
