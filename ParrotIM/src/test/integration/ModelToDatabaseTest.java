///* ModelToDatabaseTest.java
// * 
// * Programmed By:
// *     Rakan Alkheliwi
// *     Kevin Fahy
// *     
// * Change Log:
// *         
// * Known Issues:
// *     none
// *     
// * Copyright (C) 2009  Pirate Captains
// * 
// * License: GNU General Public License version 2.
// * Full license can be found in ParrotIM/LICENSE.txt.
// */
//
package test.integration;

//
//import static org.junit.Assert.*;
//
//import java.util.ArrayList;
//
//import model.DatabaseFunctions;
//import model.Model;
//import model.dataType.AccountData;
//import model.dataType.ConversationData;
//import model.dataType.GoogleTalkUserData;
//import model.dataType.MessageData;
//import model.dataType.UserData;
//import model.dataType.tempData.AccountTempData;
//import model.enumerations.ServerType;
//
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//
public class ModelToDatabaseTest {
    //
    // private Model model;
    // private DatabaseFunctions db;
    // private String profileName;
    // private String userID;
    // private String password;
    // private ServerType server;
    // private String serverString;
    // private AccountData account;
    //
    // @Before
    // public void setUp() throws Exception {
    // DatabaseFunctions.setDatabaseName("test.db");
    // db = new DatabaseFunctions();
    // db.dropTables();
    // this.model = new Model();
    //
    // profileName = "TestProfile";
    // userID = "me@gmail.com";
    // password = "";
    // server = ServerType.GOOGLE_TALK;
    // serverString = "talk.google.com";
    // account = new AccountData("tim@gmail.com", password);
    //
    // this.model.createCurrentProfile(account, profileName);
    // }
    //
    // @After
    // public void tearDown() throws Exception {
    // this.model = null;
    // }
    //
    // @Test
    // public void testAddRemoveAccount() throws Exception {
    // ArrayList<AccountTempData> accountList = null;
    //
    // db = new DatabaseFunctions();
    // assertTrue(db.getUserList().size() == 0);
    // assertTrue(model.getAccountList().size() == 0);
    // this.model.addAccount(profileName, serverString, "", userID, password);
    // assertTrue(model.getAccountList().size() == 1);
    // db = new DatabaseFunctions();
    // accountList = db.getAccountList(profileName);
    // assertEquals(accountList.get(0).getUserID(), model
    // .getAccountsForProfile(profileName).get(0).getUserID());
    //
    // assertTrue(model.getAccountsForProfile("false").size() == 0);
    // this.model.addAccount(profileName, serverString, "", userID, password);
    // assertTrue(model.getAccountList().size() == 1);
    //
    // model.removeAccount(profileName, "do not remove me!");
    // assertTrue(model.getAccountList().size() == 1);
    // model.removeAccount(profileName, userID);
    // assertTrue(model.getAccountList().size() == 0);
    //
    // return;
    // }
    //
    // @Test
    // public void testAddRemoveFriend() throws Exception {
    // UserData user = null;
    //
    // assertTrue(model.getCurrentProfile().getAllFriends().size() == 0);
    // user = new GoogleTalkUserData(userID);
    // account.addFriend(user);
    // assertTrue(model.getCurrentProfile().getAllFriends().size() == 1);
    // assertEquals(model.findUserByAccountName(userID), user);
    //
    // account.removeFriend(new GoogleTalkUserData(userID));
    // assertTrue(model.getCurrentProfile().getAllFriends().size() == 0);
    //
    // return;
    // }
    //
    // @Test
    // public void testAddGetChats() throws Exception {
    // MessageData message = null;
    // ConversationData conversation = null;
    //        
    // message = new MessageData("test", "me", "and", "win", false, false,
    // false, "#FFFFFF");
    // conversation = new ConversationData(account, new
    // GoogleTalkUserData(userID));
    // db = new DatabaseFunctions();
    // assertTrue(db.getChatNameList(profileName, "").size() == 0);
    // model.sendMessage(conversation, message);
    //        
    // db = new DatabaseFunctions();
    // assertTrue(db.getChatNameList(profileName, "").size() > 0);
    //        
    // return;
    // }
}