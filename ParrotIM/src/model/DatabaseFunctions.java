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
 *     2009-June-20, KF
 *         Added table-creation checking in the constructor.
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import model.dataType.UserData;
import model.dataType.tempData.FriendTempData;
import model.dataType.tempData.ProfileTempData;

public class DatabaseFunctions {

    private Vector<String> accountList;
    private Vector<String> bannedAccountList;
    public Connection conn;
    public Statement stat;
    public PreparedStatement prep;
    public ResultSet rs;

    /*
     * DatabaseFunctions() connects you to the database. Every time you want to
     * run a query in another file you have to
     * "DatabaseFunctions db = new DatabaseFunctions();" then run things such as
     * db.addUser();
     */
    public DatabaseFunctions() throws ClassNotFoundException, SQLException {
        bannedAccountList = new Vector<String>();
        Class.forName("org.sqlite.JDBC");
        conn = DriverManager.getConnection("jdbc:sqlite:test.db");
        stat = conn.createStatement();

        /*
         * Set up tables. Commented out are commands to delete the table, as to
         * start a new table up fresh. Please do not remove comments from these
         * commands unless necessary.
         */

        // stat.executeUpdate("drop table if exists people;");
        // stat.executeUpdate("drop table if exists chatLog;");
        // stat.executeUpdate("drop table if exists profiles;");
        // stat.executeUpdate("drop table if exists friendList;");
        stat.executeUpdate("create table if not exists people "
                + "(profile, service, email, password, rememberPassword);");
        stat.executeUpdate("create table if not exists chatLog "
                + "(fromUser, toUser, message, date, time, timestamp);");
        stat.executeUpdate("create table if not exists profiles "
                + "(name, password, defaultProfile);");
        stat.executeUpdate("create table if not exists friendList "
                + "(accountName, friendName, blocked);");

    }

    /*
     * addUsers() puts a new account for a specific profile into the Database.
     * You can get the information you added using getUsers;
     */
    public void addUsers(String profile, String service, String email,
            String password, String rememberPassword) throws SQLException {
        stat.executeUpdate("insert into people values('" + profile + "'" + ",'"
                + service + "','" + email + "','" + password + "'" + ",'"
                + rememberPassword + "')");
    }

    public String getPassword(String username) throws ClassNotFoundException,
            SQLException {
        // Connection conn = DriverManager.getConnection("jdbc:sqlite:test.db");
        // Statement stat = conn.createStatement();
        // ResultSet rs = null;
        rs = stat.executeQuery("select * from people where email='" + username
                + "'");
        return rs.getString("password");

    }

    public void addChat(String fromUser, String toUser, String message)
            throws SQLException {
        Date date1 = new Date();
        String timeStamp = new SimpleDateFormat("yyMMddHHmmssS").format(date1);
        String date = new SimpleDateFormat("EEE, MMM d, yyyy").format(date1);
        String time = DateFormat.getTimeInstance(DateFormat.MEDIUM).format(
                date1);
        stat.executeUpdate("insert into chatLog values('" + fromUser + "'"
                + ",'" + toUser + "','" + message + "'" + ",'" + date + "','"
                + time + "','" + timeStamp + "')");

        return;
    }

    /*
     * getChatNameList() gives you an Vector<String> of every user you've
     * chatted with. Input of user name.
     */
    public Vector<String> getChatNameList(String username) throws SQLException {
        accountList = new Vector<String>();
        // conn = DriverManager.getConnection("jdbc:sqlite:test.db");
        // stat = conn.createStatement();
        rs = stat.executeQuery("select * from chatLog where fromUser='"
                + username + "';");
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
    public Vector<String> getChatDatesFromName(String username, String buddyname)
            throws SQLException {
        accountList = new Vector<String>();
        // conn = DriverManager.getConnection("jdbc:sqlite:test.db");
        // stat = conn.createStatement();
        rs = stat.executeQuery("select * from chatLog where (toUser='"
                + buddyname + "' AND fromUser='" + username + "') || (toUser='"
                + username + "' AND fromUser='" + buddyname
                + "') order by timestamp;");
        while (rs.next()) {
            if (!accountList.contains(rs.getString("date"))) {
                accountList.add(rs.getString("date"));
            }
        }
        return accountList;
    }

    /*
     * Given a certain date, it gives you the unique chat made on that specific
     * time in a String format.
     */
    public Vector<String> getMessageFromDate(String username, String buddyname,
            String date) throws SQLException {
        accountList = new Vector<String>();
        // conn = DriverManager.getConnection("jdbc:sqlite:test.db");
        // stat = conn.createStatement();
        rs = stat.executeQuery("select * from chatLog where date='" + date
                + "' order by timestamp;");
        rs = stat.executeQuery("select * from chatLog where (toUser='"
                + buddyname + "' AND fromUser='" + username + "') || (toUser='"
                + username + "' AND fromUser='" + buddyname + "') AND date='"
                + date + "' order by timestamp;");
        while (rs.next()) {
            accountList.add(rs.getString("time") + ", From: "
                    + rs.getString("fromUser") + " To: "
                    + rs.getString("toUser") + " Message: "
                    + rs.getString("message"));
        }
        return accountList;
    }

    /**
     * Adds a simple profile to the database.
     */
    public void addProfiles(String name, String password,
            boolean isDefault) throws SQLException {
        String defaultProfile = null;
        
        if (isDefault) {
            defaultProfile = "yes";
        } else {
            defaultProfile = "no";
        }
        
        stat.executeUpdate("insert into profiles values('" + name + "'" + ",'"
                + password + "','" + defaultProfile + "')");
        System.out.println("I'm added");
        return;
    }
    
    public void removeProfile(String name) throws SQLException {
        stat.executeUpdate("DELETE FROM profiles WHERE name = '"
                + name + "';");
        
        return;
    }
    
    public void setDefaultProfile(String name) throws SQLException {
        stat.executeUpdate("UPDATE friendList SET defaultProfile = 'no';");

        stat.executeUpdate("UPDATE friendList SET defaultProfile = 'yes' "
                + "' WHERE name ='" + name + "';");
        
        return;
    }
    
    public ProfileTempData getDefaultProfile() throws SQLException {
        ProfileTempData profile = null;
        String name = null;
        String password = null;
        boolean defaultProfile = false;
        
        rs = stat.executeQuery("SELECT * FROM defaultProfile WHERE "
                + "defaultProfile = 'yes';");
        
        if (rs.next()) {
            name = rs.getString("name");
            password = rs.getString("password");
            if (rs.getString("defaultProfile").equals("yes")) {
                defaultProfile = true;
            } else {
                defaultProfile = false;
            }
            profile = new ProfileTempData(name, password, defaultProfile);
        }
        
        return profile;
    }

    /*
     * Gives you a Vector<String> of every profile in the database
     */
    public Vector<String> getProfileList() throws SQLException {
        accountList = new Vector<String>();
        rs = stat.executeQuery("select * from profiles;");
        System.out.println("I want some");
        while (rs.next()) {
            System.out.println("I got some");
            accountList.add(rs.getString("name"));
        }
        return accountList;
    }

    /*
     * Gives you a Vector<String> of every user in the database
     */
    public Vector<String> getUserList() throws SQLException {
        accountList = new Vector<String>();
        rs = stat.executeQuery("select * from people;");
        while (rs.next()) {
            accountList.add(rs.getString("email"));
        }
        return accountList;
    }

    /*
     * Gives you a Vector<String> of every user in the database UNDER a specific
     * profile
     */
    public Vector<String> getProfilesUserList(String name) throws SQLException {
        accountList = new Vector<String>();
        rs = stat.executeQuery("select * from people where profile='" + name
                + "';");
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

    public Vector<FriendTempData> getFriendListByAccountName(String accountName)
            throws SQLException {
        Vector<FriendTempData> friendsToReturn = new Vector<FriendTempData>();
        FriendTempData friend = null;
        boolean blocked = false;

        stat = conn.createStatement();
        rs = stat.executeQuery("SELECT * FROM friendList WHERE accountName='"
                + accountName + "';");

        /* Only check resultSet once */
        while (rs.next()) {
            if (rs.getString("blocked").equals("yes")) {
                blocked = true;
            } else {
                blocked = false;
            }
            friend = new FriendTempData(rs.getString("friendName"), blocked);
            friendsToReturn.add(friend);
        }

        return friendsToReturn;
    }

    public void addFriend(String accountName, FriendTempData friend)
            throws SQLException {
        String friendName = friend.getUserID();
        String blocked = null;
        
        if (friend.isBlocked()) {
            blocked = "yes";
        } else {
            blocked = "no";
        }

        stat.executeUpdate("INSERT INTO friendList VALUES ('" + accountName
                + "'" + ",'" + friendName + "','" + blocked + "')");

        return;
    }

    public void removeFriend(String accountName, String friendName)
            throws SQLException {
        stat
                .executeUpdate("DELETE FROM friendList WHERE "
                        + "accountName = '" + accountName + "' and "
                        + "friendName = '" + friendName + "';");
    }

    public void changeBlocked(String friendName, boolean blocked)
            throws SQLException {
        String isBlocked = null;
        
        if (blocked) {
            isBlocked = "yes";
        } else {
            isBlocked = "no";
        }
     
        stat.executeUpdate("UPDATE friendList SET blocked = '" + isBlocked
                + "' WHERE friendName ='" + friendName + "';");

        return;
    }

    public boolean checkFriendExists(String accountName, String friendName)
            throws SQLException {
        boolean exists = false;
        stat = conn.createStatement();
        rs = stat.executeQuery("SELECT * FROM friendList WHERE accountName='"
                + accountName + "' and friendName='" + friendName + "';");

        /* Only check resultSet once */
        if (rs.next()) {
            exists = true;
        }

        return exists;
    }

}
