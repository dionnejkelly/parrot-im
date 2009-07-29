package test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import model.dataType.tempData.FriendTempData;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FriendTempDataTest {
    private FriendTempData fd1;
    private FriendTempData fd2;
    private FriendTempData fd3;

    @Before
    public void setUp() throws Exception {
        fd1 = new FriendTempData("Faisel", false);
    }

    @After
    public void tearDown() throws Exception {
        fd1 = null;
        fd2 = null;
        fd3 = null;
    }

    @Test
    public void testFriendTempData() {
        fd2 = new FriendTempData();
        assertNull(fd2.getUserID());
        assertFalse(fd2.isBlocked());
    }

    @Test
    public void testFriendTempDataStringBoolean() {
        fd3 = new FriendTempData("Shamar", true);
        assertSame("Shamar", fd3.getUserID());
        assertTrue(fd3.isBlocked());
    }

    @Test
    public void testSetUserID() {
        String expected = "King";
        fd1.setUserID(expected);
        assertSame(expected, fd1.getUserID());
    }

    @Test
    public void testGetUserID() {
        String expected = "Faisel";
        assertSame(expected, fd1.getUserID());
    }

    @Test
    public void testSetBlocked() {
        fd1.setBlocked(true);
        assertTrue(fd1.isBlocked());
        fd1.setBlocked(false);
        assertFalse(fd1.isBlocked());
    }

    @Test
    public void testIsBlocked() {
        assertFalse(fd1.isBlocked());
        fd1.setBlocked(true);
        assertTrue(fd1.isBlocked());
    }

}
