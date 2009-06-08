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
	
    private Vector<String> accountList;
    private Vector<String> serverList;
    private String username;
    private String password;
    private int openChatWindows;
    private ArrayList<ChatData> chatParticipants; /* temporary */
    
    private CurrentProfileData currentProfile;

	
    public Model() throws ClassNotFoundException, SQLException {
        serverList = new Vector<String>();
        accountList = new Vector<String>();
	    int openChatWindows = 0;
	    chatParticipants = new ArrayList<ChatData>();
        currentProfile = null;
	    
	    
	    int i = 0;
        ArrayList<String> usernames = new ArrayList<String>(); 
	    ArrayList<String> passwords = new ArrayList<String>();

	    /* Set-up for SQL database */
        Class.forName("org.sqlite.JDBC");
        Connection conn = DriverManager.getConnection("jdbc:sqlite:test.db");
        Statement stat = conn.createStatement();
	    ResultSet rs = null;

	    /* Default values for test variables */
	    this.password = "abcdefghi";
	    this.username = "cmpt275testing@gmail.com";

	    // ahmad test
	    rs = stat.executeQuery("select * from people;");
	    while (rs.next()) {
	        usernames.add(rs.getString("email"));
	        passwords.add("abcdefghi");
	    }

	   	// ahmad test
	    // set server list
	    serverList.add ("msn");
	    serverList.add ("aim");
	    serverList.add ("twitter");
	    serverList.add ("icq");
	    serverList.add ("googleTalk");

	    //list of accounts
        for (int k = 0; k < usernames.size(); k++) {
            accountList.add(usernames.get(k));
        }
    }

    public Vector<String> getAccountList() {
        return accountList;
    }

    /* Phase this method out in favour of the next one */
    public Vector<String> getServerList() {
       	return ServerType.getServerList();
    	// Old Code: return serverList;
    }
    
    public Vector<ServerType> getServerListv2() {
    	Vector<ServerType> servers = new Vector<ServerType>();
    	for (ServerType s : ServerType.values()) {
    		servers.add(s);
    	}
    	return servers;
    }
	    
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
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
    
    public void useGuestAccount(ServerType server, String accountName,
    		                    String password) {
    	// TODO throw exception if currentProfile already exists.
    	AccountData guestAccount = null;
    	ArrayList<AccountData> accounts = null;
    	CurrentProfileData currentProfile = null;
    	
    	guestAccount = new AccountData(server, accountName,
    	                               password);
    	accounts = new ArrayList<AccountData>();
    	accounts.add(guestAccount);
    	currentProfile = new CurrentProfileData(accounts);
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
}
