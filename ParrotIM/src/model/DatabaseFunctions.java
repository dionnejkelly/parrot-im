/* DatabaseFunctions.java
 * 
 * Programmed By:
 *     Ahmad Sidiqi
 *     William Chen
 *     Kevin Fahy
 *     
 * Change Log:
 *     2009-June-8, AS
 *         Initial write. Added access to accounts via SQL.
 *     2009-June-13, WC
 *         Moved over to ParrotIM project.
 *     2009-June-15, AS
 *         Provided chat log functionality.
 *     2009-June-18, KF
 *         Added functionality to store local data on friends, including
 *         their accountName and blockedStatus.
 *     2009-June-19, KF
 *     	   Fixed some code on getChatDatesFromName(), getChatNameList()
 *         
 * Known Issues:
 *     1. The way how database records chat should be fixed.
 *     	  Right now it only shows the recorded message from a buddy, it doesn't show who is talking either.
 *        Also, it doesn't show the replies from the user.
 * 
 * Copyright (C) 2009  Pirate Captains
 * 
 * Full license can be found in ParrotIM/LICENSE.txt.
 */

package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Vector;

public class DatabaseFunctions {

    private Vector<String> accountList;
    private Vector<String> bannedAccountList;
    public Connection conn;
    public Statement stat;
    public PreparedStatement prep;
    public ResultSet rs;
/*
 * DatabaseFunctions() connects you to the database.
 * Every time you want to run a query in another file you have
 * to "DatabaseFunctions db = new DatabaseFunctions();"
 * then run things such as db.addUser();
 */
    public DatabaseFunctions() throws ClassNotFoundException, SQLException {
        bannedAccountList = new Vector<String>();
        Class.forName("org.sqlite.JDBC");
        conn = DriverManager.getConnection("jdbc:sqlite:test.db");
        stat = conn.createStatement();     
        
    }
/*
 * addUsers() puts a new account for a specific profile into
 * the Database. You can get the information you added using getUsers;
 */
    public void addUsers(String profile, String service, String email,
            String password, String rememberPassword) throws SQLException {
        prep = conn
                .prepareStatement("insert into people values (?, ?, ?, ?, ?);");
        prep.setString(1, profile);
        prep.setString(2, service);
        prep.setString(3, email);
        prep.setString(4, password);
        prep.setString(5, rememberPassword);
        prep.addBatch();

        conn.setAutoCommit(false);
        prep.executeBatch();
        conn.setAutoCommit(true);
        conn.close();
    }

    public String getPassword(String username) throws ClassNotFoundException,
    SQLException {
		Connection conn = DriverManager.getConnection("jdbc:sqlite:test.db");
		Statement stat = conn.createStatement();
		ResultSet rs = null;
		rs = stat.executeQuery("select * from people where email='" + username
		        + "'");
		return rs.getString("password");

}
    public void addChat(String fromUser, String toUser, String message)
            throws SQLException {
        String date = new Date().toString();
    	
    	prep = conn
                .prepareStatement("insert into chatLog values (?, ?, ?, ?);");
        prep.setString(1, fromUser);
        prep.setString(2, toUser);
        prep.setString(3, message);
        // / remember to make date automatic!!!
        prep.setString(4, date);
        prep.addBatch();

        conn.setAutoCommit(false);
        prep.executeBatch();
        conn.setAutoCommit(true);
        conn.close();

        return;
    }
/*
 * getChatNameList() gives you an Vector<String> of every user you've
 * chatted with. Input of user name.
 */
    public Vector<String> getChatNameList(String username) throws SQLException {
        accountList = new Vector<String>();
        conn = DriverManager.getConnection("jdbc:sqlite:test.db");
        stat = conn.createStatement();
        ResultSet rs = stat.executeQuery("select * from chatLog where fromUser='" + username + "';");
        while (rs.next()) {
            if (!accountList.contains(rs.getString("toUser"))) {
                accountList.add(rs.getString("toUser"));
            }
        }
        return accountList;
    }
/*
 * getChatDatesFromName(String name) gives you a Vector<String> with the
 * dates of all the chats a certain profile has chatted with a certain user.
 */
    public Vector<String> getChatDatesFromName(String username, String buddyname) throws SQLException {
        accountList = new Vector<String>();
        conn = DriverManager.getConnection("jdbc:sqlite:test.db");
        stat = conn.createStatement();
        ResultSet rs = stat.executeQuery("select * from chatLog where (toUser='"
                + buddyname + "' AND fromUser='" + username + "') || (toUser='"
                + username + "' AND fromUser='" + buddyname + "');");
        while (rs.next()) {
            accountList.add(rs.getString("date"));
        }
        return accountList;
    }
/*
 * Given a certain date, it gives you the unique chat made on that specific time
 * in a String format.
 */
    public String getMessageFromDate(String date) throws SQLException {
        accountList = new Vector<String>();
        conn = DriverManager.getConnection("jdbc:sqlite:test.db");
        stat = conn.createStatement();
        ResultSet rs = stat.executeQuery("select * from chatLog where date='"
                + date + "';");
        rs.next();
        return rs.getString("message");
    }


/*
 * Adds a simple profile to the database.
 */
    public void addProfiles(String name, String password,
            String rememberPassword) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:sqlite:test.db");
        Statement stat = conn.createStatement();
        prep = conn.prepareStatement("insert into profiles values (?, ?, ?);");

        prep.setString(1, name);
        prep.setString(2, password);
        prep.setString(3, rememberPassword); // either Y or N
        prep.addBatch();

        conn.setAutoCommit(false);
        prep.executeBatch();
        conn.setAutoCommit(true);
        conn.close();
    }

    /*
     * Gives you a Vector<String> of every profile in the database
     */
    public Vector<String> getProfileList() throws SQLException {
        accountList = new Vector<String>();
    	ResultSet rs = stat.executeQuery("select * from profiles;");
        while (rs.next()) {
            accountList.add(rs.getString("name"));
        }
        return accountList;
    }
    
    
    /*
     * Gives you a Vector<String> of every user in the database
     */
    public Vector<String> getUserList() throws SQLException {
        accountList = new Vector<String>();
    	ResultSet rs = stat.executeQuery("select * from people;");
        while (rs.next()) {
            accountList.add(rs.getString("email"));
        }
        return accountList;
    }
    /*
     * Gives you a Vector<String> of every user in the database
     * UNDER a specific profile
     */
    public Vector<String> getProfilesUserList(String name) throws SQLException {
        accountList = new Vector<String>();
    	ResultSet rs = stat.executeQuery("select * from people where name='" + name + "';");
        while (rs.next()) {
            accountList.add(rs.getString("email"));
        }
        return accountList;
    }

    public Vector<String> getBannedUserList() throws SQLException {
        // ResultSet rs = stat.executeQuery("select * from people;");
        // while (rs.next()) {
        // bannedAccountList.add("");

        // }

        return bannedAccountList;

    }

    public void setBannedUserList(String userID) {
        bannedAccountList.add(userID);

    }

    public static boolean friendExists(String accountName) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:sqlite:test.db");
        Statement stat = conn.createStatement();
        PreparedStatement prep = null;
        ResultSet rs = null;

        boolean exists = false;
        stat = conn.createStatement();
        rs = stat.executeQuery("SELECT * FROM friendList WHERE accountName='"
                + accountName + "';");

        /* Only check resultSet once */
        if (rs.next()) {
            exists = true;
        }

        return exists;
    }

    public static void addFriend(String accountName, boolean blocked)
            throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:sqlite:test.db");
        PreparedStatement prep = null;

        prep = conn.prepareStatement("INSERT INTO friendList VALUES (?, ?);");
        prep.setString(1, accountName);
        prep.setBoolean(2, blocked);

        prep.addBatch();

        conn.setAutoCommit(false);
        prep.executeBatch();
        conn.setAutoCommit(true);
        conn.close();

        return;
    }

    public static void changeBlocked(String accountName, boolean blocked)
            throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:sqlite:test.db");
        PreparedStatement prep = null;

        prep = conn.prepareStatement("UPDATE friendList SET blocked = blocked " +
        		"WHERE accountName ='" + accountName + "';");
        
        /* are these commands necessary now? */
        prep.addBatch();

        conn.setAutoCommit(false);
        prep.executeBatch();
        conn.setAutoCommit(true);
        conn.close();

        return;
    }

    public static boolean checkBlockedByAccountName(String accountName)
            throws SQLException {
        /*
         * Ahmad, please check this to see if it could be made more efficient.
         * Basically, I want to check the table of friends to find a friend's
         * blocked status. If it doesn't exist, I want to return false (default
         * state of blocked). Thanks! --KF
         */
        Connection conn = DriverManager.getConnection("jdbc:sqlite:test.db");
        Statement stat = conn.createStatement();
        PreparedStatement prep = null;
        ResultSet rs = null;

        boolean blocked = false; // If account isn't saved, false is default.
        stat = conn.createStatement();
        rs = stat.executeQuery("SELECT * FROM friendList WHERE accountName='"
                + accountName + "';");

        /* Only check resultSet once */
        if (rs.next()) {
            blocked = rs.getBoolean("blocked");
        }

        return blocked;
    }

}
