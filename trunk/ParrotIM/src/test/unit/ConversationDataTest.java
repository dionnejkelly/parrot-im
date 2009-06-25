package test.unit;

import static org.junit.Assert.*;

import java.util.ArrayList;

import model.dataType.AccountData;
import model.dataType.ConversationData;
import model.dataType.GoogleTalkUserData;
import model.dataType.MessageData;
import model.dataType.UserData;
import model.enumerations.ServerType;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
// Test completed
public class ConversationDataTest {
	private ConversationData cd1;
	private ConversationData cd2;
	@Before
	public void setUp() throws Exception {
		cd1 = new ConversationData(new AccountData(ServerType.GOOGLE_TALK,"Rakan","1234"),new GoogleTalkUserData("Rakan","Rick","1234"));
		cd2 = new ConversationData (new AccountData (ServerType.AIM,"Rocky","a8"), new GoogleTalkUserData ("Rocky","movie","a8"));
	}

	@After
	public void tearDown() throws Exception {
		cd1 = null;
		cd2 = null;
	}

	@Test
	public void testGetUser() {
		
		UserData expected = new GoogleTalkUserData("Rakan","Rick","1234");
		assertSame(expected.getAccountName(),cd1.getUser().getAccountName());
		assertSame(expected.getNickname(),cd1.getUser().getNickname());
		assertSame(expected.getStatus(),cd1.getUser().getStatus());
	}

	@Test
	public void testSetUser() {
		UserData expected = new GoogleTalkUserData("Meryam","Faris","idle");
		cd2.setUser(expected);
		assertSame(expected.getAccountName(),cd2.getUser().getAccountName());
		assertSame(expected.getNickname(),cd2.getUser().getNickname());
		assertSame(expected.getStatus(),cd2.getUser().getStatus());
		
	}

	@Test
	public void testGetAccount() {
		AccountData expected = new AccountData(ServerType.GOOGLE_TALK,"Rakan","1234");
		assertSame(expected.getAccountName(),cd1.getAccount().getAccountName());
		assertSame(expected.getServer(),cd1.getAccount().getServer());
		assertSame(expected.getPassword(),cd1.getAccount().getPassword());

	}

	@Test
	public void testSetAccountData() {
		AccountData expected = new AccountData(ServerType.GOOGLE_TALK,"Joseph","staid");
		cd2.setAccountData(expected);
		assertSame(expected.getAccountName(),cd2.getAccount().getAccountName());
		assertSame(expected.getServer(),cd2.getAccount().getServer());
		assertSame(expected.getPassword(),cd2.getAccount().getPassword());
	}

	@Test
	public void testGetText() {
		// This test should be modified
		MessageData expected = new MessageData("Rakan","Hey how are you doing?","TimesNewRoman","13");
		cd1.addMessage(expected);
		assertSame(expected,cd1.getText().get(0));
		}

	@Test
	public void testAddMessage() {
		MessageData expected = new MessageData("Ray","Hey, can I see at Coffeshop","Gothic","20");
		cd1.addMessage(expected);
		assertTrue(cd1.getText().contains(expected));
	}

	@Test
	public void testGetMessageCount() {
		// Modify this test
		int expected = 0;
		assertSame(expected,cd2.getMessageCount());
		MessageData e = new MessageData("DeNiro","Did you saw my movie?","Italic","15");
		cd2.addMessage(e);
		expected++;
		assertTrue(expected==cd2.getMessageCount());
		expected++;
		assertFalse(expected==cd2.getMessageCount());
	}

	@Test
	public void testDisplayMessages() {
		MessageData e1 = new MessageData("Godfather","I'll give you an offer that you can't refuse","Calbarie","18");
		MessageData e2 = new MessageData("Fannuci","Meet me at the restuarant","TimesNewRoman","16");
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

		

		// from here I got expected value System.out.println(cd1.displayMessages());
	}

}
