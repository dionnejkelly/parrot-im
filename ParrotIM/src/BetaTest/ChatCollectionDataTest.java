package BetaTest;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.ArrayList;

import model.Model;
import model.dataType.ChatCollectionData;
import model.dataType.ConversationData;
import model.dataType.GoogleTalkAccountData;
import model.dataType.GoogleTalkUserData;
import model.enumerations.UserStateType;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import controller.MainController;
import controller.services.GoogleTalkManager;

public class ChatCollectionDataTest {
	private ChatCollectionData cd1;
	private ChatCollectionData cd2;
	private ChatCollectionData cd3;
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
		assertNotNull(cd2.getConversations());
		assertTrue(cd2.getConversations().isEmpty());
		assertTrue(cd2.getHiddenConversations().isEmpty());
		assertNull(cd2.getActiveConversation());
		assertTrue(cd2.isChatWindowHistoryEnabled());
	}

	@Test
	public void testGetConversations() throws ClassNotFoundException, SQLException {
		ConversationData test = new ConversationData(new GoogleTalkAccountData("rmamnk","123"),new GoogleTalkUserData("rma"));
		ArrayList<ConversationData> expected = new ArrayList<ConversationData>();
		expected.add(test);
		cd1.setConversations(expected);
		ArrayList<ConversationData> result = cd1.getConversations();
		for(int i = 0;i<result.size();i++)
		{
			assertEquals(expected.get(i).getAccount().getNickname(),result.get(i).getAccount().getNickname());
			assertEquals(expected.get(i).getAccount().getPassword(),result.get(i).getAccount().getPassword());
			assertEquals(expected.get(i).getUser().getNickname(),result.get(i).getUser().getNickname());
			assertSame(expected.get(i).getUser().getServer(),result.get(i).getUser().getServer());
		}
		
	}

	@Test
	public void testSetConversations() {
		 //Same as getconversations
	}

	@Test
	public void testGetHiddenConversations() throws ClassNotFoundException, SQLException {
		ConversationData test = new ConversationData(new GoogleTalkAccountData("rmamnk","Rakan","Home","abc",UserStateType.AWAY,new GoogleTalkManager(new MainController(new Model()),new Model())),
				new GoogleTalkUserData("rmamnk","rakan","Tired",UserStateType.ONLINE,true));
		ArrayList<ConversationData> expected = new ArrayList<ConversationData>();
		expected.add(test);
		cd1.setHiddenConversations(expected);
		ArrayList<ConversationData> result = cd1.getHiddenConversations();
		for(int i = 0;i<result.size();i++)
		{
			assertEquals(expected.get(i).getAccount().getNickname(),result.get(i).getAccount().getNickname());
			assertEquals(expected.get(i).getAccount().getPassword(),result.get(i).getAccount().getPassword());
			assertEquals(expected.get(i).getUser().getNickname(),result.get(i).getUser().getNickname());
			assertSame(expected.get(i).getUser().getServer(),result.get(i).getUser().getServer());
		}
	}

	@Test
	public void testSetHiddenConversations() {
		 //Same as previous
	}

	@Test
	public void testSetActiveConversation() throws ClassNotFoundException, SQLException {
		ConversationData expected = new ConversationData(new GoogleTalkAccountData("rmamnk","123"),
		new GoogleTalkUserData("rma3"));
		cd1.setActiveConversation(expected);
		ConversationData result = cd1.getActiveConversation();
			assertEquals(expected.getAccount().getNickname(),result.getAccount().getNickname());
			assertEquals(expected.getAccount().getPassword(),result.getAccount().getPassword());
			assertEquals(expected.getUser().getNickname(),result.getUser().getNickname());
			assertSame(expected.getUser().getServer(),result.getUser().getServer());
		}
	

	@Test
	public void testGetActiveConversation() {
		 //Same as before
	}

	@Test
	public void testSetChatWindowHistoryEnabled() {
		cd1.setChatWindowHistoryEnabled(false);
		assertFalse(cd1.isChatWindowHistoryEnabled());
	}

	@Test
	public void testIsChatWindowHistoryEnabled() {
		assertTrue(cd1.isChatWindowHistoryEnabled());
		testSetChatWindowHistoryEnabled();
	}

	@Test
	public void testAddConversation() throws ClassNotFoundException, SQLException {
		ConversationData test = new ConversationData(new GoogleTalkAccountData("rmamnk","Rakan","Home","abc",UserStateType.AWAY,new GoogleTalkManager(new MainController(new Model()),new Model())),
				new GoogleTalkUserData("rmamnk","rakan","Tired",UserStateType.ONLINE,true));
		cd1.addConversation(test);
		ArrayList<ConversationData> result = cd1.getConversations();
		assertEquals(test.getAccount().getNickname(),result.get(0).getAccount().getNickname());
		assertEquals(test.getAccount().getPassword(),result.get(0).getAccount().getPassword());
		assertEquals(test.getUser().getNickname(),result.get(0).getUser().getNickname());
		assertSame(test.getUser().getServer(),result.get(0).getUser().getServer());
		ConversationData result2 = cd1.getActiveConversation();
		assertEquals(result2.getAccount().getNickname(),test.getAccount().getNickname());
		assertEquals(result2.getAccount().getPassword(),test.getAccount().getPassword());
		assertEquals(result2.getUser().getNickname(),test.getUser().getNickname());
		assertSame(result2.getUser().getServer(),test.getUser().getServer());
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
