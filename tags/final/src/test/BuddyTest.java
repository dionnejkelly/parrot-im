package test;

import static org.junit.Assert.assertSame;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import API.Buddy;

// Testing complete
public class BuddyTest {
    private Buddy b1;
    private Buddy b2;
    private Buddy b3;
    private Buddy b4;
    private Buddy b5;

    @Before
    public void setUp() throws Exception {

        b1 = new Buddy("Rakan", "twitter");
        b2 = new Buddy("Matt", "ICQ");
        b3 = new Buddy("John", "GoogleTalk");
        b4 = new Buddy("Nancy", "GoogleTalk");
    }

    @After
    public void tearDown() throws Exception {
        b1 = null;
        b2 = null;
        b3 = null;
        b4 = null;
        b5 = null;
    }

    @Test
    public void testBuddy() {
        b5 = new Buddy("Hafez", "msn");
        assertSame("Hafez", b5.getUsername());
        assertSame("msn", b5.getChannel());
    }

    public void testSetUsername() {
        Buddy expected = new Buddy("Sara", "twitter");
        b1.setUsername("Sara");
        assertSame(expected.getUsername(), b1.getUsername());
    }

    @Test
    public void testGetUsername() {
        String expected = "Matt";
        assertSame(expected, b2.getUsername());
    }

    @Test
    public void testSetChannel() {
        Buddy expected = new Buddy("John", "msn");
        b3.setChannel("msn");
        assertSame(expected.getChannel(), b3.getChannel());
    }

    @Test
    public void testGetChannel() {
        String Expected = "GoogleTalk";
        assertSame(Expected, b4.getChannel());
    }

    @Test
    public void testToString() {
        String Expected = "Nancy";
        assertSame(Expected, b4.toString());
    }

}
