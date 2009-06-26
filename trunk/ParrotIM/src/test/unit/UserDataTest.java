package test.unit;

import static org.junit.Assert.*;

import model.dataType.GoogleTalkUserData;
import model.dataType.JabberUserData;
import model.dataType.UserData;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
// Test completed
public class UserDataTest {
	private UserData ud1;
	private UserData ud2;
	private UserData ud3;
	private UserData ud4;
	@Before
	public void setUp() throws Exception {
		ud1 = new JabberUserData("Rakan","rick","away");
		ud2 = new GoogleTalkUserData("Vin","Shiraz","busy");
	}

	@After
	public void tearDown() throws Exception {
		ud1 = null;
		ud2 = null;
		ud3 = null;
		ud4 = null;
	}

	@Test
	public void testUserDataStringStringString() {
		ud3 = new GoogleTalkUserData("cmpt275","SE","It is tough");
		assertSame("cmpt275",ud3.getAccountName());
		assertSame("SE",ud3.getNickname());
		assertSame("It is tough",ud3.getStatus());
		
	}

	@Test
	public void testUserDataString() {
		ud4 = new JabberUserData("Rayan");
		assertSame("Rayan",ud4.getAccountName());
	}

	@Test
	public void testSetAccountName() {
		String expected = "Jamal";
		ud1.setAccountName(expected);
		assertSame(expected,ud1.getAccountName());
	}

	@Test
	public void testGetAccountName() {
		String expected = "Vin";
		assertSame(expected,ud2.getAccountName());
	}

	@Test
	public void testSetNickname() {
		String expected = "Rocky";
		ud1.setNickname(expected);
		assertSame(expected,ud1.getNickname());
	}

	@Test
	public void testGetNickname() {
		String expected = "Shiraz";
		assertSame(expected,ud2.getNickname());
	}

	@Test
	public void testSetStatus() {
		String expected = "online";
		ud1.setStatus(expected);
		assertSame(expected,ud1.getStatus());
	}

	@Test
	public void testGetStatus() {
		String expected = "busy";
		assertSame(expected, ud2.getStatus());
	}

	@Test
	public void testSetBlocked() {
		assertSame(false,ud1.isBlocked());
		ud1.setBlocked(true);
		assertSame(true,ud1.isBlocked());
	}

	@Test
	public void testIsBlocked() {
		assertFalse(ud2.isBlocked());
		ud2.setBlocked(true);
		assertTrue(ud2.isBlocked());
	}

	@Test
	public void testSetState() {
		String expected = "online";
		ud1.setState(expected);
		assertSame(expected,ud1.getState());
	}

	@Test
	public void testGetState() {
		assertSame("Offline",ud2.getState());
		String expected = "busy";
		ud2.setState(expected);
		assertSame(expected,ud2.getState());
	}

	@Test
	public void testIsMoreOnline() {
		ud1.setBlocked(true);
		ud2.setState("Avaliable");
		assertFalse(ud1.isMoreOnline(ud2));
		ud1.setBlocked(false);
		ud1.setState("Available");
		ud2.setState("busy");
		assertTrue(ud1.isMoreOnline(ud2));
		ud2.setState("Online");
		// Being equel will not cause a problem
		assertTrue(ud1.isMoreOnline(ud2));
		assertFalse(ud2.isMoreOnline(ud1));

	}

	@Test
	public void testToString() {
		String expected = "Shiraz";
		assertSame(expected,ud2.toString());
	}

}
