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

import java.util.Vector;
import java.util.ArrayList;
import java.util.Observable;
import java.sql.*;

/**
 * The model stores all data and provides it for the view and 
 * controllers.
 */
public class Model extends Observable {
	
    private int openChatWindows;
    private ArrayList<ChatData> chatParticipants; /* temporary */
    private ArrayList<ChatWindowData> chatWindows; /* Replaces ChatData */
    
    private CurrentProfileData currentProfile;

	
    public Model() throws ClassNotFoundException, SQLException {
        int openChatWindows = 0;
        chatParticipants = new ArrayList<ChatData>();
        currentProfile = new CurrentProfileData();
        chatWindows = new ArrayList<ChatWindowData>();
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
	    
    //public String getUsername() {
    //    return username;
    //}

    public String getPassword(String username) throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        Connection conn = DriverManager.getConnection("jdbc:sqlite:test.db");
        Statement stat = conn.createStatement();
	    ResultSet rs = null;
	    rs = stat.executeQuery("select * from people where email='" + username + "'");
		return rs.getString("password");
	    
    }
    
    public int getOpenChatWindows() {
    	return openChatWindows;
    }
    
    public void incrementOpenChatWindows() {
    	this.openChatWindows++;
    	setChanged();
    	notifyObservers();
    	return;
    }

    public void storeChatParticipants(ChatData chatData) {
    	this.chatParticipants.add(chatData);
    	return;
    }

    public ChatData findChatDataByID(int id) {
    	ChatData toReturn = null;
    	for (ChatData chatData : this.chatParticipants) {
    		if (chatData.getWindowID() == id) {
    			toReturn = chatData;
    			break;
    		}
    	}
    	return toReturn; /* Could be null, fix! */
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
        notifyObservers();
        return;
    }
}
