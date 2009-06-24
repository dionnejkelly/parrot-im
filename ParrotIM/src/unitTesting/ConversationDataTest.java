package unitTesting;

import static org.junit.Assert.*;

import java.util.ArrayList;

import model.dataType.AccountData;
import model.dataType.ConversationData;
import model.dataType.GoogleTalkUserData;
import model.dataType.MessageData;
import model.dataType.ServerType;
import model.dataType.UserData;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ConversationDataTest {
	private ConversationData cd1;
	@Before
	public void setUp() throws Exception {
		cd1 = new ConversationData(new AccountData(ServerType.GOOGLE_TALK,"Rakan","1234"),new GoogleTalkUserData("Rakan","Rick","1234"));
		
	}

	@After
	public void tearDown() throws Exception {
		cd1 = null;
	}

	@Test
	public void testGetUser() {
		
		UserData expected = new GoogleTalkUserData("Rakan","Rick","1234");
		assertSame(expected,cd1.getUser());
	}

	@Test
	public void testSetUser() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetAccount() {
		AccountData expected = new AccountData(ServerType.GOOGLE_TALK,"Rakan","1234");
		assertSame(expected,cd1.getAccount());
	}

	@Test
	public void testSetAccountData() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetText() {
		// This test should be modified
		ArrayList<MessageData> expected = new ArrayList<MessageData>();
		assertSame(expected,cd1.getText());
	}

	@Test
	public void testAddMessage() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetMessageCount() {
		// Modify this test
		int expected = 0;
		assertSame(expected,cd1.getMessageCount());
	}

	@Test
	public void testDisplayMessages() {
		fail("Not yet implemented");
	}

}
