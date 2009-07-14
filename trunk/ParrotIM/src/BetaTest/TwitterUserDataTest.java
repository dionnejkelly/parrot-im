package BetaTest;

import static org.junit.Assert.*;

import model.dataType.TwitterUserData;
import model.enumerations.ServerType;
import model.enumerations.UserStateType;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TwitterUserDataTest {
	private TwitterUserData tu1;
	private TwitterUserData tu2;
	private TwitterUserData tu3;
	@Before
	public void setUp() throws Exception {
		tu3 = new TwitterUserData("Montana","Busy");
	}

	@After
	public void tearDown() throws Exception {
		tu1 = null;
		tu2 = null;
		tu3 = null;
	}

	@Test
	public void testServerTypeToString() {
		assertSame(ServerType.TWITTER,tu3.getServer());
	}

	@Test
	public void testTwitterUserDataStringString() {
		tu1 = new TwitterUserData("Scarface","Idle");
		assertEquals("Scarface",tu1.getUserID());
		assertEquals(tu1.getUserID(),tu1.getNickname());
		assertEquals("Idle",tu1.getStatus());
		assertFalse(tu1.isBlocked());
		assertSame(UserStateType.OFFLINE,tu1.getState());
	}

	@Test
	public void testTwitterUserDataString() {
		tu2 = new TwitterUserData("Tony");
		assertEquals(tu2.getNickname(),tu2.getUserID());
		assertEquals("Tony",tu2.getNickname());
		assertEquals("",tu2.getStatus());
		assertFalse(tu2.isBlocked());
		assertSame(UserStateType.OFFLINE,tu2.getState());
	}

}
