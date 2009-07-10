package BetaTest;

import static org.junit.Assert.*;

import model.dataType.ChatCollectionData;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ChatCollectionDataTest {
	private ChatCollectionData cd1;
	private ChatCollectionData cd2;
	@Before
	public void setUp() throws Exception {
		cd1 = new ChatCollectionData();
	}

	@After
	public void tearDown() throws Exception {
		cd1 = null;
		cd2 = null;
	}

	@Test
	public void testChatCollectionData() {
		cd2 = new ChatCollectionData();
		assertNull(cd2.getConversations());
		assertNull(cd2.getHiddenConversations());
		assertNull(cd2.getActiveConversation());
		assertTrue(cd2.isChatWindowHistoryEnabled());
	}

	@Test
	public void testGetConversations() {
		
	}

	@Test
	public void testSetConversations() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetHiddenConversations() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetHiddenConversations() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetActiveConversation() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetActiveConversation() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetChatWindowHistoryEnabled() {
		fail("Not yet implemented");
	}

	@Test
	public void testIsChatWindowHistoryEnabled() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddConversation() {
		fail("Not yet implemented");
	}

	@Test
	public void testRemoveConversation() {
		fail("Not yet implemented");
	}

	@Test
	public void testHideAllConversations() {
		fail("Not yet implemented");
	}

	@Test
	public void testRemoveAllConversations() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetAllConversations() {
		fail("Not yet implemented");
	}

}
