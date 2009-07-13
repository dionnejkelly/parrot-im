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
 *     2009-June-23, KF
 *         Set the database name as a static variable so that test cases
 *         could use a separate database, defined at the start of
 *         execution of the program.
 *     2009-June-24, KF
 *         Added JavaDoc documentation. Phased out the storage of blocked
 *         users in this class. Now stored in the database, and accessed
 *         through the Model by looking through the UserData objects.
 *         
 * Known Issues:
 *     1. Each time the database is accessed, a new DatabaseFunctions
 *        object is instantiated. This may not be necessary. Currently,
 *        the constructor sets up the connection, and each method 
 *        closes both the connection and the result set if it's opened.
 *        If these aren't closed, the database has a chance of staying 
 *        in a locked state. If this bug is resolved, we can use one
 *        DatabaseFunctions for all access.
 * 
 * Sections:
 *     I    - Static Data Members
 *     II   - Non-Static Data Members
 *     III  - Constructors
 *     IV   - Accessors and Mutators
 *     V    - Database Utility Methods
 *     VI   - Chat manipulation
 *     VII  - Profile Manipulation
 *     VIII - Account Manipulation
 *     IX   - Friend List Manipulation
 * 
 * Copyright (C) 2009  Pirate Captains
 * 
 * License: GNU General Public License version 2.
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
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;
import java.util.Random;

import model.dataType.tempData.AccountTempData;
import model.dataType.tempData.ChatLogMessageTempData;
import model.dataType.tempData.FriendTempData;
import model.dataType.tempData.ProfileTempData;
import model.enumerations.ServerType;

/**
 * Interfaces between the program and the SQLite database. Handles all SQL
 * queries, and maintains the integrity of the database. All methods that access
 * the database are in this class.
 */
public class DatabaseFunctions {

    private static final String YES = "yes";
    private static final String NO = "no";

    // Section
    // I - Static Data Members

    /**
     * Holds the name of the database. Each database call will access the
     * database name specified here. Should include the ".db" file extenstion,
     * as this is not automatically set.
     */
    private static String databaseName;

    // Section
    // II - Non-Static Data Members

    /**
     * The connection to the database.
     */
    public Connection conn;

    /**
     * A statement for communicating with the database.
     */
    public Statement stat;

    /**
     * A prepared statement for adding values to the database.
     */
    public PreparedStatement prep;

    /**
     * Will be set to iterate over retrieved data from the database.
     */
    public ResultSet rs;
    public ResultSet r2;
    public ResultSet r3;

    // Section
    // III - Constructors

    /**
     * DatabaseFunctions() connects you to the database. Every time you want to
     * run a query in another file you have to
     * "DatabaseFunctions db = new DatabaseFunctions();" then run things such as
     * db.addUser();
     */
    public DatabaseFunctions() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        conn =
                DriverManager.getConnection("jdbc:sqlite:"
                        + DatabaseFunctions.getDatabaseName());
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
        // stat.executeUpdate("drop table if exists chatBotQuestions;");
        // stat.executeUpdate("drop table if exists chatBotAnswers;");
        stat.executeUpdate("create table if not exists people "
                + "(profile, serverType, serverAddress, "
                + "accountName, password);");
        stat.executeUpdate("create table if not exists chatLog "
                + "(profile, fromUser, toUser, message, "
                + "date, time, timestamp);");
        stat.executeUpdate("create table if not exists profiles "
                + "(name, password, defaultProfile, chatWindowHistory, "
                + "autoSignIn, chatLog, sounds, chatbot, avatarDirectory);");
        stat.executeUpdate("create table if not exists friendList "
                + "(accountName, friendName, blocked);");
        stat.executeUpdate("create table if not exists chatBotQuestions "
                + "(profile, question, afterQuestion);");
        stat.executeUpdate("create table if not exists chatBotAnswers "
                + "(profile, question, answer);");
    }

    // Section
    // IV - Accessors and Mutators

    /**
     * Gets the database filename.
     * 
     * @return the filename of the database to access.
     */
    public static String getDatabaseName() {
        return databaseName;
    }

    /**
     * Sets the database name.
     * 
     * @param username
     * @throws SQLException
     */
    public static void setDatabaseName(String databaseName) {
        DatabaseFunctions.databaseName = databaseName;
        return;
    }

    // Section
    // V - Database Utility Methods

    /**
     * Drops all tables in the database; stored data is lost in all dropped
     * tables for the file.
     * 
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public void dropTables() throws ClassNotFoundException, SQLException {
        stat.executeUpdate("drop table if exists people;");
        stat.executeUpdate("drop table if exists chatLog;");
        stat.executeUpdate("drop table if exists profiles;");
        stat.executeUpdate("drop table if exists friendList;");

        conn.close();
        return;
    }

    // Section
    // VI - Chat manipulation

    /**
     * Adds a chat to the database.
     * 
     * @param profile
     * @param fromUser
     * @param toUser
     * @param message
     * @throws SQLException
     */
    public void addChat(String profile, String fromUser, String toUser,
            String message) throws SQLException {
        Date date1 = new Date();
        String timeStamp = new SimpleDateFormat("yyMMddHHmmssS").format(date1);
        String date = new SimpleDateFormat("EEE, MMM d, yyyy").format(date1);
        String time =
                DateFormat.getTimeInstance(DateFormat.MEDIUM).format(date1);

        prep =
                conn
                        .prepareStatement("insert into chatLog values (?, ?, ?, ?, ?, ?, ?);");
        conn.setAutoCommit(false);

        prep.setString(1, profile);
        prep.setString(2, fromUser);
        prep.setString(3, toUser);
        prep.setString(4, message);
        prep.setString(5, date);
        prep.setString(6, time);
        prep.setString(7, timeStamp);
        prep.executeUpdate();

        conn.commit();
        conn.close();

        return;
    }

    /**
     * 
     * getChatNameList() gives you an Vector<String> of every user you've
     * chatted with. Input of user name.
     * 
     * @param username
     * @return A Vector of Strings holding 1 name per String.
     * @throws SQLException
     */
    public Vector<String> getChatNameList(String profile, String searched)
            throws SQLException {
        Vector<String> accountList = new Vector<String>();
        Vector<String> profilesAccountList = new Vector<String>();
        r3 =
                stat.executeQuery("select * from people where profile='"
                        + profile + "';");
        while (r3.next()) {
            profilesAccountList.add(r3.getString("accountName"));
        }
        rs =
                stat.executeQuery("select * from chatLog where profile='"
                        + profile + "' AND (message LIKE '%" + searched + "%'"
                        + " OR fromUser LIKE '%" + searched + "%' OR"
                        + " toUser LIKE '%" + searched + "%');");
        while (rs.next()) {
            if (!accountList.contains(rs.getString("toUser"))) {
                if (!profilesAccountList.contains(rs.getString("toUser"))) {
                    accountList.add(rs.getString("toUser"));
                }
            }
        }
        rs.close();
        conn.close();
        for (int i = 0; i < accountList.size(); i++) {
            System.out.println(accountList.get(i));
        }
        return accountList;

    }

    /**
     * 
     * getChatDatesFromName(String name) gives you a Vector<String> with the
     * dates of all the chats a certain profile has chatted with a certain user.
     * 
     * @param profile
     * @param buddyname
     * @return A Vector of dates represented as Strings.
     * @throws SQLException
     */
    public Vector<String> getChatDatesFromName(String profile,
            String buddyname, String searched) throws SQLException {
        Vector<String> accountList = new Vector<String>();
        stat = conn.createStatement();
        rs =
                stat.executeQuery("select * from chatLog where profile = '"
                        + profile + "' AND (toUser='" + buddyname
                        + "' OR fromUser='" + buddyname
                        + "') AND (message LIKE '%" + searched
                        + "%' OR fromUser LIKE '%" + searched
                        + "%' OR toUser LIKE '%" + searched
                        + "%') order by timestamp;");
        while (rs.next()) {
            if (!accountList.contains(rs.getString("date"))) {
                accountList.add(rs.getString("date"));
            }
        }
        rs.close();
        conn.close();

        return accountList;
    }

    /**
     * 
     * Given a certain date, it gives you the unique chat made on that specific
     * time in a String format.
     * 
     * @param username
     * @param buddyname
     * @param date
     * @return An ArrayList of objects holding chat log messages.
     * @throws SQLException
     */
    public ArrayList<ChatLogMessageTempData> getMessageFromDate(
            String username, String buddyname, String date, String searched)
            throws SQLException {
        ArrayList<ChatLogMessageTempData> messageList =
                new ArrayList<ChatLogMessageTempData>();
        ChatLogMessageTempData message = null;

        rs =
                stat.executeQuery("select * from chatLog where (toUser='"
                        + buddyname + "' AND fromUser='" + username
                        + "') OR (toUser='" + username + "' AND fromUser='"
                        + buddyname + "') AND date='" + date
                        + "'AND (message LIKE '%" + searched + "%'"
                        + " OR fromUser LIKE '%" + searched + "%' OR"
                        + " toUser LIKE '%" + searched + "%')"
                        + " order by timestamp;");

        while (rs.next()) {
            message =
                    new ChatLogMessageTempData(rs.getString("time"), rs
                            .getString("fromUser"), rs.getString("toUser"), rs
                            .getString("message"));
            messageList.add(message);
        }
        rs.close();
        conn.close();

        return messageList;
    }

    // Section
    // VII - Profile Manipulation

    /**
     * 
     * Adds a simple profile to the database.
     * 
     * @param name
     * @param password
     * @param isDefault
     * @throws SQLException
     */
    public void addProfiles(String name, String password, boolean isDefault,
            boolean isChatWindowHistory, boolean isAutoSignIn,
            boolean isChatLog, boolean isSounds, boolean isChatbot)
            throws SQLException {
        String defaultProfile = isDefault ? YES : NO;
        String chatWindowHistory = isChatWindowHistory ? YES : NO;
        String autoSignIn = isAutoSignIn ? YES : NO;
        String chatLog = isChatLog ? YES : NO;
        String sounds = isSounds ? YES : NO;
        String chatbot = isChatbot ? YES : NO;

        prep =
                conn.prepareStatement("insert into profiles values "
                        + "(?, ?, ?, ?, ?, ?, ?, ?, ?);");
        conn.setAutoCommit(false);

        prep.setString(1, name);
        prep.setString(2, password);
        prep.setString(3, defaultProfile);
        prep.setString(4, chatWindowHistory);
        prep.setString(5, autoSignIn);
        prep.setString(6, chatLog);
        prep.setString(7, sounds);
        prep.setString(8, chatbot);
        prep.setString(9, "images/buddylist/logoBox.png");
        prep.executeUpdate();

        conn.commit();
        conn.close();

        return;
    }

    /**
     * Removes a profile from the database. Searches by profile name.
     * 
     * @param name
     * @throws SQLException
     */
    public void removeProfile(String name) throws SQLException {
        // Delete the profile
        stat.executeUpdate("DELETE FROM profiles WHERE name = '" + name + "';");

        // Delete the profile's accounts
        stat
                .executeUpdate("DELETE FROM people WHERE profile = '" + name
                        + "';");

        conn.close();
        return;
    }

    /**
     * Changes the default profile to the profile specified by the passed-in
     * name parameter. No effect if the profile does not exist. Sets all other
     * "defaultProfile" fields to "no", so that only 0 or 1 profiles may be a
     * default profile at any one time.
     * 
     * @param name
     * @throws SQLException
     */
    public void setDefaultProfile(String name) throws SQLException {
        stat.executeUpdate("UPDATE profiles SET defaultProfile = 'no';");

        stat.executeUpdate("UPDATE profiles SET defaultProfile = 'yes' "
                + "WHERE name = '" + name + "';");

        conn.close();
        return;
    }

    /**
     * 
     * Gives you a Vector<String> of every profile in the database. Very useful
     * for placing the profiles in a drop-down box.
     * 
     * @return A Vector of profile names represented by Strings.
     * @throws SQLException
     */
    public Vector<String> getProfileList() throws SQLException {
        Vector<String> profileList = new Vector<String>();
        rs = stat.executeQuery("select * from profiles;");
        while (rs.next()) {
            profileList.add(rs.getString("name"));
        }
        rs.close();
        conn.close();
        return profileList;
    }

    public ArrayList<ProfileTempData> getProfiles() throws SQLException {
        ArrayList<ProfileTempData> profiles = new ArrayList<ProfileTempData>();
        boolean isDefaultProfile = false;
        boolean isChatWindowHistory = false;
        boolean isAutoSignIn = false;
        boolean isChatLog = false;
        boolean isSounds = false;
        boolean isChatbot = false;

        rs = stat.executeQuery("SELECT * FROM profiles;");
        while (rs.next()) {
            isDefaultProfile =
                    rs.getString("defaultProfile").equals(YES) ? true : false;
            isChatWindowHistory =
                    rs.getString("chatWindowHistory").equals(YES) ? true
                            : false;
            isAutoSignIn =
                    rs.getString("autoSignIn").equals(YES) ? true : false;
            isChatLog = rs.getString("chatLog").equals(YES) ? true : false;
            isSounds = rs.getString("sounds").equals(YES) ? true : false;
            isChatbot = rs.getString("chatbot").equals(YES) ? true : false;

            profiles.add(new ProfileTempData(rs.getString("name"), rs
                    .getString("password"), isDefaultProfile,
                    isChatWindowHistory, isAutoSignIn, isChatLog, isSounds,
                    isChatbot));
        }
        
        rs.close();
        conn.close();

        return profiles;
    }
    public void setAvatarDirectory(String profile, String directory) 
    throws SQLException
    {
    	stat.executeQuery("update profiles set directory='" 
    			+ directory + "' where profile='" + profile + "'");
    	conn.close();
    }
    public String getAvatarDirectory(String profile) throws SQLException
    {
    	rs = stat.executeQuery("select * from profiles where profile='" + profile + "';");
    	rs.next();
    	String directory = rs.getString("avatarDirectory");
    	rs.close();
    	conn.close();
    	return directory;
    }

    // Section
    // VIII - Account Manipulation

    /**
     * This class puts a new account for a specific profile into the Database.
     * You can get the information you added using getUsers;
     * 
     * @param profile
     * @param server
     * @param accountName
     * @param password
     * @throws SQLException
     */
    public void addUsers(String profile, String serverType,
            String serverAddress, String accountName, String password)
            throws SQLException {
        prep =
                conn
                        .prepareStatement("insert into people values (?, ?, ?, ?, ?);");
        conn.setAutoCommit(false);

        prep.setString(1, profile);
        prep.setString(2, serverType);
        prep.setString(3, serverAddress);
        prep.setString(4, accountName);
        prep.setString(5, password);
        prep.executeUpdate();

        conn.commit();
        conn.close();

        return;
    }

    /**
     * Deletes an account from a given profile.
     * 
     * @param profile
     * @param accountName
     * @throws SQLException
     */
    public void removeAccountFromProfile(String profile, String accountName)
            throws SQLException {
        stat.executeUpdate("DELETE FROM people WHERE profile = '" + profile
                + "' AND accountName = '" + accountName + "'");

        conn.close();
        return;
    }

    /**
     * Gets the password associated with an account.
     * 
     * @param accountName
     * @return The password for the given account.
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public String getAccountPassword(String accountName)
            throws ClassNotFoundException, SQLException {
        String password = null;

        rs =
                stat.executeQuery("select * from people where accountName = '"
                        + accountName + "'");

        if (rs.next()) {
            password = rs.getString("password");
        }
        rs.close();
        conn.close();

        return password;
    }

    /**
     * Gives you a Vector<String> of every user in the database. Useful for
     * listing accounts.
     * 
     * @return A Vector of users represented by Strings.
     * @throws SQLException
     */
    public Vector<String> getUserList() throws SQLException {
        Vector<String> accountList = new Vector<String>();
        rs = stat.executeQuery("select * from people;");
        while (rs.next()) {
            accountList.add(rs.getString("accountName"));
        }
        rs.close();
        conn.close();
        return accountList;
    }

    /**
     * Gets all accounts and returns an object holding all account data. Returns
     * accounts only for the passed-in profile name.
     * 
     * @param profile
     * @return A Vector of objects containing account data.
     * @throws SQLException
     */
    public ArrayList<AccountTempData> getAccountList(String profile)
            throws SQLException {
        String server = null;
        String accountName = null;
        String password = null;
        String serverAddress = null;
        AccountTempData account = null;
        ArrayList<AccountTempData> accountList =
                new ArrayList<AccountTempData>();

        rs =
                stat.executeQuery("SELECT * FROM people WHERE profile = '"
                        + profile + "';");
        while (rs.next()) {
            accountName = rs.getString("accountName");
            password = rs.getString("password");
            server = rs.getString("serverType");
            serverAddress = rs.getString("serverAddress");

            account =
                    new AccountTempData(server, serverAddress, accountName,
                            password);
            accountList.add(account);
        }
        rs.close();
        conn.close();
        return accountList;
    }

    /**
     * Gives you a Vector<String> of every user in the database UNDER a specific
     * profile.
     * 
     * @param name
     * @return A vector of account names represented by Strings.
     * @throws SQLException
     */
    public Vector<String> getProfilesUserList(String name) throws SQLException {
        Vector<String> accountList = new Vector<String>();
        rs =
                stat.executeQuery("select * from people where profile='" + name
                        + "';");
        while (rs.next()) {
            accountList.add(rs.getString("accountName"));
        }
        rs.close();
        conn.close();
        return accountList;
    }

    /**
     * Checks to see if a account exists in the database for a certain account.
     * 
     * @param profile
     * @param account
     * @return True if the account is found under the account, false otherwise.
     * @throws SQLException
     */
    public boolean checkAccountExists(String profile, String account)
            throws SQLException {
        boolean exists = false;
        stat = conn.createStatement();
        rs =
                stat.executeQuery("SELECT * FROM people WHERE profile='"
                        + profile + "' and accountName='" + account + "';");

        // Only check resultSet once
        if (rs.next()) {
            exists = true;
        }
        rs.close();
        conn.close();
        return exists;
    }

    // Section
    // IX - Friend List Manipulation

    /**
     * Gets all friends under a certain account name.
     * 
     * @param accountName
     * @return A Vector of friend objects.
     * @throws SQLException
     */
    public Vector<FriendTempData> getFriendListByAccountName(String accountName)
            throws SQLException {
        Vector<FriendTempData> friendsToReturn = new Vector<FriendTempData>();
        FriendTempData friend = null;
        boolean blocked = false;

        stat = conn.createStatement();
        rs =
                stat
                        .executeQuery("SELECT * FROM friendList WHERE accountName='"
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
        rs.close();
        conn.close();

        return friendsToReturn;
    }

    /**
     * Adds a friend to the database.
     * 
     * @param accountName
     * @param friend
     * @throws SQLException
     */
    public void addFriend(String accountName, FriendTempData friend)
            throws SQLException {
        String friendName = friend.getUserID();
        String blocked = null;

        if (friend.isBlocked()) {
            blocked = "yes";
        } else {
            blocked = "no";
        }

        prep =
                conn
                        .prepareStatement("insert into friendList values (?, ?, ?);");
        conn.setAutoCommit(false);

        prep.setString(1, accountName);
        prep.setString(2, friendName);
        prep.setString(3, blocked);
        prep.executeUpdate();

        conn.commit();
        conn.close();

        return;
    }

    /**
     * Removes a friend from the database.
     * 
     * @param accountName
     * @param friendName
     * @throws SQLException
     */
    public void removeFriend(String accountName, String friendName)
            throws SQLException {
        stat
                .executeUpdate("DELETE FROM friendList WHERE "
                        + "accountName = '" + accountName + "' and "
                        + "friendName = '" + friendName + "';");

        conn.close();
        return;
    }

    /**
     * Changes the blocked status for a friend.
     * 
     * @param friendName
     * @param blocked
     * @throws SQLException
     */
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

        conn.close();
        return;
    }

    /**
     * Checks to see if a friend exists in the database for a certain account.
     * It is OK to have the same friend convered by different accounts.
     * 
     * @param accountName
     * @param friendName
     * @return True if the friend is found under the account, false otherwise.
     * @throws SQLException
     */
    public boolean checkFriendExists(String accountName, String friendName)
            throws SQLException {
        boolean exists = false;
        stat = conn.createStatement();
        rs =
                stat
                        .executeQuery("SELECT * FROM friendList WHERE accountName='"
                                + accountName
                                + "' and friendName='"
                                + friendName + "';");

        // Only check resultSet once
        if (rs.next()) {
            exists = true;
        }
        rs.close();
        conn.close();
        return exists;
    }

    // Section
    // X - Chatbot Responses

    public void addQuestion(String profile, String question)
            throws SQLException {
        prep =
                conn
                        .prepareStatement("insert into chatBotQuestions values (?, ?, ?);");
        conn.setAutoCommit(false);

        prep.setString(1, profile);
        prep.setString(2, question);
        prep.setString(3, null);
        prep.executeUpdate();

        conn.commit();
        conn.close();
    }

    public void addAnswer(String profile, String question, String answer)
            throws SQLException {
        prep =
                conn
                        .prepareStatement("insert into chatBotAnswers values (?, ?, ?);");
        conn.setAutoCommit(false);

        prep.setString(1, profile);
        prep.setString(2, question);
        prep.setString(3, answer);
        prep.executeUpdate();

        conn.commit();
        conn.close();
    }

    public String getResponse(String profile, String question)
            throws SQLException {
        Vector<String> responseList = new Vector<String>();
        int counter = 0;
        int whichResponse = 0;
        rs =
                stat
                        .executeQuery("SELECT * FROM chatBotAnswers WHERE profile='"
                                + profile
                                + "'"
                                + " AND question='"
                                + question
                                + "';");

        // Only check resultSet once
        while (rs.next()) {
            counter++;
            responseList.add(rs.getString("answer"));

        }
        whichResponse = new Random().nextInt(counter);
        rs.close();
        conn.close();
        return responseList.get(whichResponse);
    }

    public Vector<String> getQuestionList(String profile) throws SQLException {
        Vector<String> questionList = new Vector<String>();
        rs = stat.executeQuery("select * from chatBotQuestions where profile='" 
        		+ profile + "';");
        while (rs.next()) {
            questionList.add(rs.getString("question"));
        }
        rs.close();
        conn.close();
        return questionList;
    }

    public Vector<String> getAnswersList(String profile) throws SQLException {
        Vector<String> answersList = new Vector<String>();
        rs = stat.executeQuery("select * from chatBotAnswers where profile='" 
        		+ profile + "';");
        while (rs.next()) {
            answersList.add(rs.getString("answer"));
        }
        rs.close();
        conn.close();
        return answersList;
    }

    public Vector<String> getAnswersList(String profile, String question) 
    throws SQLException {
        Vector<String> answersList = new Vector<String>();
        rs =
                stat.executeQuery("select * from chatBotAnswers "
                        + "where question='" + question + "' AND profile ='" 
                        + profile + "';");
        while (rs.next()) {
            answersList.add(rs.getString("answer"));
        }
        rs.close();
        conn.close();
        return answersList;
    }

    // db.addAfter(questions.lastElement(), question);
    public void addAfter(String profile, String beforeQuestion, String afterQuestion)
            throws SQLException {
        stat.executeUpdate("update chatBotQuestions set afterQuestion = '"
                + afterQuestion + "' where question = '" + beforeQuestion
                + "' AND profile = '" + profile + "';");

        conn.close();

        return;
    }

    public Vector<String> getAllAfterQuestions(String profile, String question)
            throws SQLException {
        Vector<String> questionsList = new Vector<String>();
        questionsList.add(question);
        String afterQuestion = "";
        stat = conn.createStatement();
        rs =
                stat.executeQuery("select * from chatBotQuestions "
                        + "where question='" + question + "' AND profile ='" 
                        + profile + "';");
        while (rs.getString("afterQuestion") != null
                && !rs.getString("afterQuestion").equals("null")) {
            questionsList.add(rs.getString("afterQuestion"));
            afterQuestion = rs.getString("afterQuestion");
            stat = conn.createStatement();
            rs.close();
            rs =
                    stat.executeQuery("select * from chatBotQuestions "
                            + "where question='"
                            + afterQuestion + "' AND profile='" + profile + "';");
            rs.next();
        }

        rs.close();
        conn.close();

        return questionsList;
    }

    public void removeChatQuestion(String profile, String question) throws SQLException {
        boolean wasFirstQuestion = false;
        rs =
                stat.executeQuery("select * from chatBotQuestions "
                        + "where question='" + question + "' AND profile='" 
                        + profile + "';");
        rs.next();
        String questionAfter = rs.getString("afterQuestion");
        rs =
                stat.executeQuery("select * from chatBotQuestions "
                        + "where afterQuestion='" + question + "' AND profile='" 
                        + profile + "';");
        rs.next();
        try {
            System.out.println(rs.getString("question"));
        } catch (SQLException e) {
            wasFirstQuestion = true;
        }
        stat = conn.createStatement();
        if (wasFirstQuestion != true) {
            String beforeQuestion = rs.getString("question");
            rs.close();
            if (questionAfter != null) {
                System.out.println("bleh meh wuhh?");
                stat
                        .executeUpdate("update chatBotQuestions set afterQuestion = '"
                                + questionAfter
                                + "' where question = '"
                                + beforeQuestion + "' AND profile='" + profile + "';");
            } else {
                stat
                        .executeUpdate("update chatBotQuestions set afterQuestion = '"
                                + null
                                + "' where question = '"
                                + beforeQuestion + "' AND profile='" + profile + "';");
            }
        }
        stat = conn.createStatement();
        stat.executeUpdate("delete from chatBotQuestions where question='"
                + question + "' AND profile='" + profile + "';");
        stat = conn.createStatement();
        stat.executeUpdate("delete from chatBotAnswers where question='"
                + question + "' AND profile='" + profile + "';");

        conn.close();
        rs.close();

        return;
    }

    public void printQADbContents() throws SQLException {
        // stat.executeUpdate("create table if not exists chatBotQuestions "
        // + "(profile, question, afterQuestion);");
        // stat.executeUpdate("create table if not exists chatBotAnswers "
        // + "(profile, question, answer);");
        r2 = stat.executeQuery("select * from chatBotQuestions;");
        while (r2.next()) {
            System.out.println("Profile: " + r2.getString("profile")
                    + "Question: " + r2.getString("question")
                    + "afterQuestion: " + r2.getString("afterQuestion"));

        }
        System.out.println("");
        r2 = stat.executeQuery("select * from chatBotAnswers;");
        while (r2.next()) {
            System.out.println("Profile: " + r2.getString("profile")
                    + "Question: " + r2.getString("question") + "Answer: "
                    + r2.getString("answer"));

        }
    }
    
    public void removeAnswer(String profile, String question, String answer)
    throws SQLException {
    	
    	stat.executeUpdate("delete from chatBotAnswers where question='" 
    			+ question + "' AND answer='" 
    			+ answer + "' AND profile='" 
    			+ profile + "'");
    	
    	conn.close();
    	
    	return;    	
    }
    public boolean isDefaultProfile(String profile) throws SQLException {
        rs = stat.executeQuery("select * from profiles;");
        rs.next();
        String name = rs.getString("name");
    	rs.close();
    	conn.close();
    	
    	return name.contentEquals(profile);
    }
}