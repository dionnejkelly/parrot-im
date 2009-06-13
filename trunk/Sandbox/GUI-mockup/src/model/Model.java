/* Parrot IM -- An easy-to-use chat client for multiple protocols.
 * Copyright (C) 2009  Pirate Captains
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  
 * 02110-1301, USA.
 */


package model;

import java.util.*;
import java.sql.*;

/**
 * The model stores all data and provides it for the view and 
 * controllers.
 */
public class Model extends Observable {

    private ArrayList<ConversationData> conversations; 
    private ConversationData activeConversation;
    private CurrentProfileData currentProfile;
    public boolean chatWindowOpen;
	
    public Model() throws ClassNotFoundException, SQLException {
        currentProfile = new CurrentProfileData();
        conversations = new ArrayList<ConversationData>();
}

    public Vector<String> getAccountList() throws ClassNotFoundException, SQLException {
    	DatabaseFunctions db = new DatabaseFunctions();
        return db.getUserList();
    }

    /* Phase this method out in favour of the next one */
    public Vector<String> getServerList() {
       	return ServerType.getServerList();
    }
    
    public Vector<ServerType> getServerListv2() {
    	Vector<ServerType> servers = new Vector<ServerType>();
    	for (ServerType s : ServerType.values()) {
    		servers.add(s);
    	}
    	return servers;
    }
	    
    public String getPassword(String username) throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        Connection conn = DriverManager.getConnection("jdbc:sqlite:test.db");
        Statement stat = conn.createStatement();
	    ResultSet rs = null;
	    rs = stat.executeQuery("select * from people where email='" + username + "'");
		return rs.getString("password");
	    
    }
    
    public int numberOfConversations() {
        return conversations.size();
    }
    
    public void setActiveConversation(UserData user) {
        for (ConversationData c : this.conversations) {
            if (c.getUser() == user) {
                this.activeConversation = c;
                break;
            }
        }
        
        setChanged();
        notifyObservers(UpdatedType.CHAT);
        return;
    }
    
    public void setActiveConversation(ConversationData conversation) {
        this.activeConversation = conversation;
        
        setChanged();
        notifyObservers(UpdatedType.CHAT);
        return;
    }
    
    public ConversationData getActiveConversation() {
        return this.activeConversation;
    }
    
    public void receiveMessage(AccountData account, MessageData message) throws SQLException, ClassNotFoundException {
        ConversationData modifiedConversation = null;
        UserData fromUser = message.getFromUser();
        
        
        DatabaseFunctions db = new DatabaseFunctions();
        db.addChat(fromUser.getAccountName(), account.getAccountName(), message.getMessage());
        db.printChats();
        
        for (ConversationData c : conversations) {
            if (c.getUser() == fromUser) {
                modifiedConversation = c;
                break;
            }
        }
        
        /* Case 1: We found a matching conversation
         * Case 2: No match found; create conversation and add it to the list 
         */
        if (modifiedConversation != null) {
            modifiedConversation.addMessage(message);
        } else {
            modifiedConversation = new ConversationData(account, fromUser);
            this.conversations.add(modifiedConversation);
            this.activeConversation = modifiedConversation;
            modifiedConversation.addMessage(message);
            
        }
        
        setChanged();
        notifyObservers(UpdatedType.CHAT_AND_BUDDY);  
        return;
    }
    
    public void sendMessage(ConversationData modifiedConversation, MessageData message) throws ClassNotFoundException, SQLException {
        modifiedConversation.addMessage(message);
        DatabaseFunctions db = new DatabaseFunctions();
        db.addChat(message.getFromUser().getAccountName(), modifiedConversation.getUser().getAccountName(), message.getMessage());
        setChanged();
        notifyObservers(UpdatedType.CHAT);  
        return;
    }
    
    public ConversationData startConversation(AccountData account, UserData user) {
    	//account = local user (eg. cmpt275testing@gmail.com)
    	//user = buddy
        ConversationData conversation = new ConversationData(account, user);
        System.out.println("account: "+account.getAccountName()+" user:"+user.toString());
        String user_address = user.toString();
        
        //check if the conversation exists
        boolean conversation_found = false;
        int conv;
        for(conv = 0; conv < conversations.size(); conv++){
        	if (conversations.get(conv).getUser().toString().compareTo(user_address)==0){ 
        		conversation_found = true;
        		break; //if conversation found, exit loop
        	}
        }
        
        if (!conversation_found){//if conversation is not found, then add
        	this.conversations.add(conversation);
        } else{ //if found, replace conversation with the found one
        	conversation = conversations.get(conv);
        }
        
        this.activeConversation = conversation;
        
        setChanged();
        notifyObservers(UpdatedType.CHAT);
        return conversation;
    }
    
    public ArrayList<ConversationData> getConversations() {
        return this.conversations;
    }
    
    /* Current Profile manipulation */
    
    public void createCurrentProfile(AccountData account,
                                     String profileName) {
        currentProfile = new CurrentProfileData(account, profileName);
        return; 
    }
    
    public void addAccountToCurrentProfile(AccountData account) {
        currentProfile.addAccount(account);
        return; 
    }
    
    
    public CurrentProfileData getCurrentProfile() {
    	return currentProfile;
    }
    
    public boolean currentProfileExists() {
    	return (currentProfile != null);
    }
    
    public void connectAccount(AccountData accountData) {
        accountData.setConnected(true);
        setChanged();
        notifyObservers();
        return;
    }
    
    public void disconnectAccount(AccountData accountData) {
        accountData.setConnected(false);
        setChanged();
        notifyObservers();
        return;
    }

    public void forceUpdate(UpdatedType updatedType) {
        setChanged();
        notifyObservers(updatedType);
        return;
    }
    
    public UserData findUserByAccountName(String accountName) {
        UserData found = null;
        ArrayList<UserData> friends = this.currentProfile.getAllFriends();       
        for (UserData user : friends) {
            if (user.getAccountName().equals(accountName)) {
                found = user;
                break;
            }
        }
        return found;
    }
}
