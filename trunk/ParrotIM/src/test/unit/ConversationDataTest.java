///* ConversationDataTest.java
// * 
// * Programmed By:
// *     Rakan Alkheliwi
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

//
package test.unit;

import static org.junit.Assert.*;

import java.sql.SQLException;

import model.Model;
import model.dataType.AccountData;
import model.dataType.ConversationData;
import model.dataType.GoogleTalkAccountData;
import model.dataType.GoogleTalkUserData;
import model.dataType.JabberAccountData;
import model.dataType.MessageData;
import model.dataType.UserData;
import model.enumerations.ServerType;
import model.enumerations.UserStateType;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import controller.MainController;
import controller.services.GoogleTalkManager;
import controller.services.JabberManager;

public class ConversationDataTest {
    private ConversationData cd1;
    private ConversationData cd2;
    private ConversationData cd3;

    @Before
    public void setUp() throws Exception {
        cd1 =
                new ConversationData(new GoogleTalkAccountData("Rakan", "Rick","Watching a movie","1234",UserStateType.BUSY,
                		new GoogleTalkManager(new MainController(new Model()))),
                        new GoogleTalkUserData("Rakan", "Rick", "1234",UserStateType.AWAY,false));
        cd2 =
                new ConversationData(new GoogleTalkAccountData("Rocky", "a8"),
                        new GoogleTalkUserData("Rocky", "movie", "a8",UserStateType.BUSY,true));
    }

    @After
    public void tearDown() throws Exception {
        cd1 = null;
        cd2 = null;
        cd3 = null;
    }

    @Test
    public void testConversationData() throws ClassNotFoundException, SQLException {
        cd3 =
                new ConversationData(new JabberAccountData("David","Snake","Playing","mgs",UserStateType.BUSY,new JabberManager(new MainController(new Model()))),
                        new GoogleTalkUserData("Rakan", "Rick", "1234",UserStateType.ONLINE,true));
        assertEquals("David", cd3.getAccount().getUserID());
        assertEquals("Snake",cd3.getAccount().getNickname());
        assertSame("mgs", cd3.getAccount().getPassword());
        assertSame("Rakan",cd3.getUser().getUserID());
        assertSame("Rick",cd3.getUser().getNickname());
        assertSame("Playing",cd3.getAccount().getStatus());
        assertTrue(cd3.getText().isEmpty());
        assertTrue(cd3.getMessageCount() == 0);

    }

    public void testGetUser() {

        UserData expected = new GoogleTalkUserData("Rakan", "Rick", "1234",UserStateType.AWAY,false);
        assertSame(expected.getUserID(), cd1.getUser().getUserID());
        assertSame(expected.getNickname(), cd1.getUser().getNickname());
        assertSame(expected.getStatus(), cd1.getUser().getStatus());
    }

    @Test
    public void testSetUser() {
        UserData expected = new GoogleTalkUserData("Meryam", "Faris", "idle",UserStateType.OFFLINE,true);
        cd2.setUser(expected);
        assertSame(expected.getUserID(), cd2.getUser().getUserID());
        assertSame(expected.getNickname(), cd2.getUser().getNickname());
        assertSame(expected.getStatus(), cd2.getUser().getStatus());

    }

    @Test
    public void testGetAccount() throws ClassNotFoundException, SQLException {
        AccountData expected =
                new GoogleTalkAccountData("Rakan","Rick","Watching a movie","1234",UserStateType.BUSY,
                		new GoogleTalkManager(new MainController(new Model())));
        assertSame(expected.getNickname(), cd1.getAccount().getNickname());
        assertSame(expected.getServer(), cd1.getAccount().getServer());
        assertSame(expected.getPassword(), cd1.getAccount().getPassword());

    }

//    @Test
    public void testSetAccountData() throws ClassNotFoundException, SQLException {
        AccountData expected =
                new GoogleTalkAccountData("Joseph", "staid");
        cd2.setAccountData(expected);
        assertSame(expected.getUserID(),cd2.getUser());
        assertSame(expected.getNickname(), cd2.getAccount().getNickname());
        assertSame(expected.getServer(), cd2.getAccount().getServer());
        assertSame(expected.getPassword(), cd2.getAccount().getPassword());
    }

    @Test
    public void testGetText() {
        // This test should be modified
        MessageData expected =
                new MessageData("Rakan", "Hey how are you doing?",
                        "TimesNewRoman", "13", false, false, false, "#000000");
        cd1.addMessage(expected);
        assertSame(expected, cd1.getText().get(0));
    }

    @Test
    public void testAddMessage() {
        MessageData expected =
                new MessageData("Ray", "Hey, can I see at Coffeshop", "Gothic",
                        "20", false, false, false, "#000000");
        cd1.addMessage(expected);
        assertTrue(cd1.getText().contains(expected));
    }

    @Test
    public void testGetMessageCount() {
        // Modify this test
        int expected = 0;
        assertSame(expected, cd2.getMessageCount());
        MessageData e =
                new MessageData("DeNiro", "Did you saw my movie?", "Italic",
                        "15", false, false, false, "#000000");
        cd2.addMessage(e);
        expected++;
        assertTrue(expected == cd2.getMessageCount());
        expected++;
        assertFalse(expected == cd2.getMessageCount());
    }

    @Test
    public void testDisplayMessages() {
        MessageData e1 =
                new MessageData("Godfather",
                        "I'll give you an offer that you can't refuse",
                        "Calbarie", "18", false, false, false, "#000000");
        MessageData e2 =
                new MessageData("Fannuci", "Meet me at the restuarant",
                        "TimesNewRoman", "16", false, false, false, "#000000");
        cd1.addMessage(e1);
        cd1.addMessage(e2);
        // Assert it contains all messagedata from e1 attributes
        assertTrue(cd1.displayMessages().contains(e1.getFont()));
        assertTrue(cd1.displayMessages().contains(e1.getFromUser()));
        assertTrue(cd1.displayMessages().contains(e1.getMessage()));
        assertTrue(cd1.displayMessages().contains(e1.getSize()));
        // Assert it contains all messageData from e2 attributes
        assertTrue(cd1.displayMessages().contains(e2.getFont()));
        assertTrue(cd1.displayMessages().contains(e2.getFromUser()));
        assertTrue(cd1.displayMessages().contains(e2.getMessage()));
        assertTrue(cd1.displayMessages().contains(e2.getSize()));

//        // from here I got expected value
//        // System.out.println(cd1.displayMessages());
    }
//
}
