///* AccountDataTest.java
// * 
// * Programmed By:
// *     Rakan Alkheliwi
// *     Kevin Fahy
// *          
// * Change Log:
// *     *Update Me*
// *         
// * Known Issues:
// *     none
// * 
// * Copyright (C) 2009  Pirate Captains
// * 
// * License: GNU General Public License version 2.
// * Full license can be found in ParrotIM/LICENSE.txt.
// */
// Will work on that later
package test.unit;
//
import static org.junit.Assert.*;
//
import java.sql.SQLException;
import java.util.ArrayList;
//
import model.Model;
import model.dataType.AccountData;
import model.dataType.GoogleTalkAccountData;
import model.dataType.GoogleTalkUserData;
import model.dataType.JabberAccountData;
import model.dataType.UserData;
import model.enumerations.ServerType;
import model.enumerations.UserStateType;
//
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
//
import controller.MainController;
import controller.services.GoogleTalkManager;
import controller.services.JabberManager;
// This tests GoogleTalk, Twitter, and Jabber Account Data
public class AccountDataTest {
    private AccountData ad1;
    private AccountData ad2;
    private AccountData ad3;
    private AccountData ad4;
    private AccountData ad5;
    private AccountData ad6;
//
    @Before
    public void setUp() throws Exception {
    	// Look at equals
    	//GoogleTalkManager e = new GoogleTalkManager(new MainController(new Model()));
        ad1 = new GoogleTalkAccountData("Rakan", "1234");
        ad2 = new GoogleTalkAccountData("Ross", "abc");
        ad3 = new JabberAccountData("Janice", "ohmygod","With friends","ght",UserStateType.AWAY,new JabberManager(new MainController(new Model()),new Model()));
        ad4 = new JabberAccountData("Joey", "Friends");
        ad5 = new GoogleTalkAccountData("Peter", "familyguy","Eating","sss",UserStateType.ONLINE,new GoogleTalkManager(new MainController(new Model()),new Model()));
    }
//
    @After
    public void tearDown() throws Exception {
        ad1 = null;
        ad2 = null;
        ad3 = null;
        ad4 = null;
        ad5 = null;
        ad6 = null;
    }
    @Test
    public void testAccountDataStringString() throws ClassNotFoundException, SQLException {
        ad6 = new GoogleTalkAccountData("James", "007");
        assertSame(ServerType.GOOGLE_TALK, ad6.getServer());
        assertSame("James", ad6.getNickname());
        assertSame(ad6.getUserID(),ad6.getNickname());
        assertSame("007", ad6.getPassword());
        assertNull(ad6.getConnection());
    }
    @Test
    public void testAccountDataStringStringStringString(){
    	ad6 = new JabberAccountData("Zeina","ZH","Playing COD","r43",UserStateType.BUSY,new JabberManager(new MainController(new Model()),new Model()));
    	assertSame("Zeina", ad6.getUserID());
    	assertSame("ZH",ad6.getNickname());
    	assertSame("Playing COD",ad6.getStatus());
    	assertSame(UserStateType.BUSY,ad6.getState());
    	assertNotNull(ad6.getConnection());
    }
    
    @Test
    public void testGetUserID(){
    	assertSame("Joey",ad4.getUserID());
    	assertSame("Peter",ad5.getUserID());
    }
    
    
    

  
    /*
    public void testSetServer() {
        AccountData expected =
                new GoogleTalkAccountData("Rakan", "Rick",);
        ad1.setServer(ServerType.JABBER);
        assertSame(expected.getServer(), ad1.getServer());
    }*/
//
    @Test
    public void testGetServer() {
        ServerType expected = ServerType.GOOGLE_TALK;
        assertSame(expected, ad2.getServer());
        assertSame(ServerType.JABBER,ad3.getServer());

    }
//
    @Test
    public void testSetNickName() throws ClassNotFoundException, SQLException {
        ad2.setNickname("Chandler");
        assertSame("Chandler", ad2.getNickname());
    }

    @Test
    public void testGetNickName() {
        String expected = "Ross";
        assertSame(expected, ad2.getNickname());
        expected = "ohmygod";
        assertSame(expected,ad3.getNickname());
    }
//
    @Test
    public void testSetPassword() throws ClassNotFoundException, SQLException {
    	ad3.setPassword("qwerty");
        assertSame("qwerty", ad3.getPassword());
    }
//
    @Test
    public void testGetPassword() {
        String expected = "abc";
        assertSame(expected, ad2.getPassword());
    }
//
    @Test
    public void testAddFriend() {
        ArrayList<UserData> expected = new ArrayList<UserData>();
        UserData e = new GoogleTalkUserData("toot", "talal", "Have an exam tommorow",UserStateType.OFFLINE,false);
        expected.add(e);
        assertTrue(ad1.addFriend(new GoogleTalkUserData("toot", "talal", "Have an exam tommorow",UserStateType.OFFLINE,false)));
        
        // Test this
    }

    @Test
    public void testRemoveFriend() {
        GoogleTalkUserData e1 =
                new GoogleTalkUserData("Horton", "tim", "In a coffe shop",UserStateType.AWAY,true);
        GoogleTalkUserData e2 =
                new GoogleTalkUserData("Star", "Bucks", "Studying",UserStateType.BUSY,false);
        ad2.addFriend(e1);
        ad2.addFriend(e2);
        assertTrue(ad2.removeFriend(e2));
        // Review this
        assertTrue(ad2.removeFriend(new GoogleTalkUserData(
                "Horton", "tim", "In a coffe shop",UserStateType.AWAY,true)));

    }
//
    @Test
    public void testGetFriends() {
        GoogleTalkUserData e1 =
                new GoogleTalkUserData("Horton", "tim", "Playing ps3",UserStateType.ONLINE,true);
        GoogleTalkUserData e2 =
                new GoogleTalkUserData("Star", "Bucks", "Cocking",UserStateType.BUSY,false);
        ad4.addFriend(e1);
        ad4.addFriend(e2);
        ArrayList<GoogleTalkUserData> expected =
                new ArrayList<GoogleTalkUserData>();
        expected.add(e1);
        expected.add(e2);
        Object[] array1 = expected.toArray();
        assertSame(((GoogleTalkUserData) array1[0]).getUserID(), e1
                .getUserID());
        assertSame(((GoogleTalkUserData) array1[1]).getUserID(), e2
                .getUserID());
    }

    @Test
    public void testFriendExists() {
        GoogleTalkUserData expected =
                new GoogleTalkUserData("expected", "e", "good",UserStateType.OFFLINE,false);
        ad4.addFriend(expected);
        assertTrue(ad4.friendExists(expected));
    }

 
    /*
    public void testSetConnected() {
        boolean expected = true;
        ad1.setConnected(true);
        assertSame(expected, ad1.isConnected());
        ad1.setConnected(false);
        assertNotSame(expected, ad1.isConnected());
    }

    @Test
    public void testIsConnected() {
        boolean expected = false;
        assertSame(expected, ad2.isConnected());
        ad2.setConnected(true);
        assertNotSame(expected, ad2.isConnected());
    }
//*/
    @Test
    
    public void testFindFriendByUserID() {
    	// Here an error
        UserData expected =
                new GoogleTalkUserData("expected", "e", "good",UserStateType.ONLINE,false);
        assertTrue(ad4.addFriend(expected));
        assertNotNull(ad4.findFriendByUserID(expected.getUserID()));
        assertFalse(ad4.addFriend(expected));
        assertNotNull(ad4.findFriendByUserID("expected"));
    }

////    @Test
    // Something wrong here
    @Test
    public void testEquals() {
    	// Maybe because of GoogleTalkManager object
    	// Look at setup, there is a problem here
        assertTrue(!ad1.equals(ad2));
        ad2.setNickname("Rakan");
        assertTrue(!ad1.equals(ad2));
        ad2.setNickname("Not Rakan!");
        assertTrue(!ad1.equals(ad2));
        ad2.setNickname("Rakan");
        ad1.setPassword("abc");
        assertTrue(ad1.equals(ad2));
    }
    @Test
    public void testHashcode(){
    	System.out.println(ad4.hashCode());
    	assertEquals(-170383538,ad4.hashCode());
    	System.out.println(ad3.hashCode());
    	assertEquals(-1340965975,ad3.hashCode());
    }

}
