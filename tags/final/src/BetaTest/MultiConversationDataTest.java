package BetaTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.text.SimpleDateFormat;
import java.util.Date;

import model.dataType.JabberAccountData;
import model.dataType.JabberUserData;
import model.dataType.MessageData;
import model.dataType.MultiConversationData;
import model.dataType.TwitterAccountData;
import model.dataType.TwitterUserData;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MultiConversationDataTest {
    private MultiConversationData md1;
    private MultiConversationData md2;

    @Before
    public void setUp() throws Exception {
        md1 =
                new MultiConversationData("Jim", new TwitterAccountData(
                        "David", "rbc"));
    }

    @After
    public void tearDown() throws Exception {
        md1 = null;
        md2 = null;
    }

    @Test
    public void testHashCode() {

    }

    @Test
    public void testMultiConversationData() {
        md2 =
                new MultiConversationData("Mary", new JabberAccountData("big",
                        "tyt"));
        assertEquals("Mary", md2.getRoomName());
        assertTrue(md2.getUsers().isEmpty());
        assertEquals("big", md2.getAccount().getUserID());
        assertEquals("tyt", md2.getAccount().getPassword());
        assertTrue(md2.getText().isEmpty());
        assertSame(0, md2.getMessageCount());

    }

    @Test
    public void testGetRoomName() {
        assertSame("Jim", md1.getRoomName());
    }

    @Test
    public void testGetUser() {
        md1.addUser(new TwitterUserData("Rayan"));
        assertSame("Rayan", md1.getUser().getUserID());
    }

    @Test
    public void testGetUsers() {
        md1.addUser(new TwitterUserData("Michael"));
        md1.addUser(new JabberUserData("Martin"));
        assertSame("Martin", md1.getUsers().get(1).getUserID());
    }

    @Test
    public void testGetAccount() {
        assertEquals("David", md1.getAccount().getUserID());
        assertEquals("rbc", md1.getAccount().getPassword());
    }

    @Test
    public void testGetText() {
        md1.addMessage(new MessageData("Goerge", "Hawa sultan", "Arial", "4",
                true, false, true, "b", true));
        assertSame("Goerge", md1.getText().get(0).getFromUser());
        assertSame("Arial", md1.getText().get(0).getFont());
    }

    @Test
    public void testFindUserByUserID() {
        md1.addUser(new TwitterUserData("Michael"));
        md1.addUser(new JabberUserData("Martin"));
        assertEquals("Michael", md1.findUserByUserID("Michael").getUserID());
    }

    @Test
    public void testAddUser() {
        md1.addUser(new TwitterUserData("Michael"));
        assertEquals("Michael", md1.getUser().getUserID());
    }

    @Test
    public void testRemoveUser() {
        md1.addUser(new TwitterUserData("Michael"));
        md1.addUser(new JabberUserData("Martin"));
        assertTrue(md1.removeUser(md1.findUserByUserID("Michael")));
    }

    @Test
    public void testAddMessage() {
        md1.addMessage(new MessageData("Goerge", "Hawa sultan", "Arial", "4",
                true, false, true, "b", false));
        assertEquals("Goerge", md1.getText().get(0).getFromUser());
        assertSame("Arial", md1.getText().get(0).getFont());
    }

    @Test
    public void testUserList() {
        String expected1 = "<Empty>";
        assertEquals(expected1, md1.userList());
        md1.addUser(new TwitterUserData("Michael"));
        md1.addUser(new JabberUserData("Martin"));
        String expected = "Michael, Martin";
        assertEquals(expected, md1.userList());
    }

    @Test
    public void testGetMessageCount() {
        md1.addMessage(new MessageData("Goerge", "Hawa sultan", "Arial", "4",
                true, false, true, "b", true));
        assertSame(1, md1.getMessageCount());
    }

    @Test
    public void testDisplayMessages() {
        md1.addMessage(new MessageData("Gina", "Hey, are you coming?", "Arial",
                "4", false, false, false, "#FFFFFF", false));

        Date date1 = new Date();
        String etimeStamp = new SimpleDateFormat("HH:mm--").format(date1);
        String expected =
                etimeStamp + "<U><font face = \"Arial" + "\" color=\""
                        + "#ff00ff"
                        + "\">Gina:</font></U><font face=\"Arial\" "
                        + "size=\"4\" color=\"#FFFFFF\">"
                        + " Hey, are you coming?" + " </font><br><br>";
        // System.out.println(md1.displayMessages());
        assertEquals(expected, md1.displayMessages());
    }

    @Test
    public void testEqualsObject() {
        Object expected =
                new MultiConversationData("Jim", new TwitterAccountData(
                        "David", "rbc"));
        assertTrue(md1.equals(expected));
    }

}
