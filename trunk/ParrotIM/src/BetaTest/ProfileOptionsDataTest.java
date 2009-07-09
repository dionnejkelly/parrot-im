//package BetaTest;
//
//import static org.junit.Assert.*;
//
//import model.dataType.ProfileOptionsData;
//
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//
//public class ProfileOptionsDataTest {
//	private ProfileOptionsData po1;
//	private ProfileOptionsData po2;
//	@Before
//	public void setUp() throws Exception {
//		po2 = new ProfileOptionsData();
//	}
//
//	@After
//	public void tearDown() throws Exception {
//		po1 = null;
//		po2 = null;
//	}
//
//	@Test
//	public void testProfileOptionsData() {
//		po1 = new ProfileOptionsData();
//		assertFalse(po1.isChatbotEnabled());
//		assertFalse(po1.isChatWindowHistoryEnabled());
//		assertFalse(po1.isProfilePasswordEnabled());
//		assertNull(po1.getProfilePassword());
//		assertFalse(po1.isAutoSignInEnabled());
//	}
//
//	@Test
//	public void testIsChatbotEnabled() {
//		assertFalse(po2.isChatbotEnabled());
//		po2.setChatbotEnabled(true);
//		assertTrue(po2.isChatbotEnabled());
//	}
//
//	@Test
//	public void testSetChatbotEnabled() {
//		po2.setChatbotEnabled(true);
//		assertTrue(po2.isChatbotEnabled());
//		po2.setChatbotEnabled(false);
//		assertFalse(po2.isChatbotEnabled());
//	}
//
//	@Test
//	public void testIsChatWindowHistoryEnabled() {
//		assertFalse(po2.isChatWindowHistoryEnabled());
//		po2.setChatWindowHistoryEnabled(true);
//		assertTrue(po2.isChatWindowHistoryEnabled());
//	}
//
//	@Test
//	public void testSetChatWindowHistoryEnabled() {
//		po2.setChatWindowHistoryEnabled(true);
//		assertTrue(po2.isChatWindowHistoryEnabled());
//	}
//
//	@Test
//	public void testIsProfilePasswordEnabled() {
//		po2.setProfilePasswordEnabled(true);
//		assertTrue(po2.isProfilePasswordEnabled());
//		po2.setProfilePasswordEnabled(false);
//		assertFalse(po2.isProfilePasswordEnabled());
//	}
//
//	@Test
//	public void testSetProfilePasswordEnabled() {
//		po2.setProfilePasswordEnabled(true);
//		assertTrue(po2.isProfilePasswordEnabled());
//	}
//
//	@Test
//	public void testGetProfilePassword() {
//		assertNull(po2.getProfilePassword());
//		po2.setProfilePassword("055");
//		assertEquals("055",po2.getProfilePassword());
//	}
//
//	@Test
//	public void testSetProfilePassword() {
//		po2.setProfilePassword("7'lewi");
//		assertEquals("7'lewi",po2.getProfilePassword());
//	}
//
//	@Test
//	public void testIsAutoSignInEnabled() {
//		assertFalse(po2.isAutoSignInEnabled());
//		po2.setAutoSignInEnabled(true);
//		assertTrue(po2.isAutoSignInEnabled());
//	}
//
//	@Test
//	public void testSetAutoSignInEnabled() {
//		po2.setAutoSignInEnabled(true);
//		assertTrue(po2.isAutoSignInEnabled());
//		po2.setAutoSignInEnabled(false);
//		assertFalse(po2.isAutoSignInEnabled());
//	}
//
//}
