package test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import model.dataType.tempData.ProfileTempData;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ProfileTempDataTest {
    private ProfileTempData pd1;
    private ProfileTempData pd2;

    @Before
    public void setUp() throws Exception {
        pd1 =
                new ProfileTempData("Forrest", "Gump", true, true, true, true,
                        true, true);

    }

    @After
    public void tearDown() throws Exception {
        pd1 = null;
        pd2 = null;
    }

    @Test
    public void testProfileTempData() {
        pd2 =
                new ProfileTempData("Action", "SavingRayan", false, true,
                        false, true, false, true);
        assertSame("Action", pd2.getName());
        assertSame("SavingRayan", pd2.getPassword());
        assertFalse(pd2.isDefaultProfile());
        assertTrue(pd2.isChatWindowHistory());
        assertFalse(pd2.isAutoSignIn());
        assertTrue(pd2.isChatLog());
        assertFalse(pd2.isSounds());
        assertTrue(pd2.isChatbot());
    }

    @Test
    public void testSetName() {
        String expected = "Tom";
        pd1.setName(expected);
        assertSame(expected, pd1.getName());
    }

    @Test
    public void testGetName() {
        String expected = "Forrest";
        assertSame(expected, pd1.getName());
    }

    @Test
    public void testSetPassword() {
        String expected = "Hanks";
        pd1.setPassword(expected);
        assertSame(expected, pd1.getPassword());
    }

    @Test
    public void testGetPassword() {
        String expected = "Gump";
        assertSame(expected, pd1.getPassword());
    }

    @Test
    public void testSetDefaultProfile() {
        pd1.setDefaultProfile(false);
        assertFalse(pd1.isDefaultProfile());
        pd1.setDefaultProfile(true);
        assertTrue(pd1.isDefaultProfile());
    }

    @Test
    public void testIsDefaultProfile() {
        assertTrue(pd1.isDefaultProfile());
        pd1.setDefaultProfile(false);
        assertFalse(pd1.isDefaultProfile());
    }

    @Test
    public void testSetChatwindowhistory() {
        pd1.setChatWindowHistory(false);
        assertFalse(pd1.isChatWindowHistory());
        pd1.setChatWindowHistory(true);
        assertTrue(pd1.isChatWindowHistory());
    }

    @Test
    public void testIsChatwindowHistory() {
        assertTrue(pd1.isChatWindowHistory());
        pd1.setChatWindowHistory(false);
        assertFalse(pd1.isChatWindowHistory());
    }

    @Test
    public void testSetAutosignin() {
        pd1.setAutoSignIn(false);
        assertFalse(pd1.isAutoSignIn());
        pd1.setAutoSignIn(true);
        assertTrue(pd1.isAutoSignIn());
    }

    @Test
    public void testIsAutosignin() {
        assertTrue(pd1.isAutoSignIn());
        pd1.setAutoSignIn(false);
        assertFalse(pd1.isAutoSignIn());
    }

    @Test
    public void teestSetChatlog() {
        pd1.setChatLog(false);
        assertFalse(pd1.isChatLog());
        pd1.setChatLog(true);
        assertTrue(pd1.isChatLog());
    }

    @Test
    public void testIsChatlog() {
        assertTrue(pd1.isChatLog());
        pd1.setChatLog(false);
        assertFalse(pd1.isChatLog());
    }

    @Test
    public void testSetSounds() {
        pd1.setSounds(false);
        assertFalse(pd1.isSounds());
        pd1.setSounds(true);
        assertTrue(pd1.isSounds());
    }

    @Test
    public void testIsSounds() {
        assertTrue(pd1.isSounds());
        pd1.setSounds(false);
        assertFalse(pd1.isSounds());
    }

    @Test
    public void testSetChatbot() {
        pd1.setChatbot(false);
        assertFalse(pd1.isChatbot());
        pd1.setChatbot(true);
        assertTrue(pd1.isChatbot());
    }

    @Test
    public void testIsChatbot() {
        assertTrue(pd1.isChatbot());
        pd1.setChatbot(false);
        assertFalse(pd1.isChatbot());
    }
}
