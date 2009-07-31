package BetaTest;

//
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import model.dataType.ProfileData;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ProfileDataTest {
    private ProfileData po1;
    private ProfileData po2;

    @Before
    public void setUp() throws Exception {
        po2 = new ProfileData("Zeina");
    }

    @After
    public void tearDown() throws Exception {
        po1 = null;
        po2 = null;
    }

    @Test
    public void testProfileData() {
        po1 = new ProfileData("Rakan");
        assertFalse(po1.isChatbotEnabled());
        assertTrue(po1.isChatWindowHistoryEnabled());
        assertFalse(po1.isPasswordEnabled());
        assertNotNull(po1.getPassword());
        assertEquals("", po1.getPassword());
        assertFalse(po1.isAutoSignInEnabled());
    }

    @Test
    public void testIsChatbotEnabled() {
        assertFalse(po2.isChatbotEnabled());
        po2.setChatbotEnabled(true);
        assertTrue(po2.isChatbotEnabled());
    }

    @Test
    public void testSetChatbotEnabled() {
        po2.setChatbotEnabled(true);
        assertTrue(po2.isChatbotEnabled());
        po2.setChatbotEnabled(false);
        assertFalse(po2.isChatbotEnabled());
    }

    @Test
    public void testIsChatWindowHistoryEnabled() {
        assertTrue(po2.isChatWindowHistoryEnabled());
        po2.setChatWindowHistoryEnabled(false);
        assertFalse(po2.isChatWindowHistoryEnabled());
    }

    @Test
    public void testSetChatWindowHistoryEnabled() {
        po2.setChatWindowHistoryEnabled(false);
        assertFalse(po2.isChatWindowHistoryEnabled());
    }

    /*
     * @Test public void testIsProfilePasswordEnabled() {
     * po2.setPasswordEnabled(true); assertTrue(po2.isPasswordEnabled());
     * po2.setProfilePasswordEnabled(false);
     * assertFalse(po2.isPasswordEnabled()); }
     */

    /*
     * @Test public void testSetProfilePasswordEnabled() {
     * po2.setProfilePasswordEnabled(true); assertTrue(po2.isPasswordEnabled());
     * }
     */

    @Test
    public void testGetProfilePassword() {
        assertSame("", po2.getPassword());
        po2.setPassword("055");
        assertEquals("055", po2.getPassword());
    }

    @Test
    public void testSetProfilePassword() {
        po2.setPassword("7'lewi");
        assertEquals("7'lewi", po2.getPassword());
    }

    @Test
    public void testIsAutoSignInEnabled() {
        assertFalse(po2.isAutoSignInEnabled());
        po2.setAutoSignInEnabled(true);
        assertTrue(po2.isAutoSignInEnabled());
    }

    @Test
    public void testSetAutoSignInEnabled() {
        po2.setAutoSignInEnabled(true);
        assertTrue(po2.isAutoSignInEnabled());
        po2.setAutoSignInEnabled(false);
        assertFalse(po2.isAutoSignInEnabled());
    }

}
