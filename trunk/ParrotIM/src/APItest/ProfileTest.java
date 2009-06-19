package APItest;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import API.Buddy;
import API.BuddyList;
import API.Profile;

public class ProfileTest {
	private Profile p1;
	@Before
	public void setUp() throws Exception {
		Buddy[] b1 = new Buddy[4];
		b1[0] = new Buddy("Rakan","twitter");
		 b1[1] = new Buddy("Matt","ICQ");
		 b1[2] = new Buddy("John","GoogleTalk");
		 b1[3] = new Buddy("Nancy","GoogleTalk");
		 BuddyList bl1 = new BuddyList(b1);
		p1 = new Profile(bl1,"Default","Joey","1234",true);
	}

	@After
	public void tearDown() throws Exception {
		p1 = null;
	}

	@Test
	public void testSetBuddyList() {
		//fail("Not yet implemented");
	}

	@Test
	public void testGetBuddyList() {
		Buddy[] e = new Buddy[4];
		e[0] = new Buddy("Rakan","twitter");
		 e[1] = new Buddy("Matt","ICQ");
		 e[2] = new Buddy("John","GoogleTalk");
		 e[3] = new Buddy("Nancy","GoogleTalk");
		 BuddyList Expected = new BuddyList(e);
		 assertSame(Expected,p1.getBuddyList());
	}

	@Test
	public void testGetName() {
		String expected = "Joey";
		assertSame(expected,p1.getName());
	}

	@Test
	public void testSetName() {
		//fail("Not yet implemented");
	}

	@Test
	public void testGetPassword() {
		String expected = "1234";
		assertSame(expected,p1.getPassword());
	}

	@Test
	public void testSetPassword() {
		//fail("Not yet implemented");
	}

	@Test
	public void testGetChkpassword() {
		boolean expected = true;
		assertSame(expected,p1.getChkpassword());
	}

	@Test
	public void testSetChkpassword() {
		//fail("Not yet implemented");
	}

	@Test
	public void testSetSettings() {
		//fail("Not yet implemented");
	}

	@Test
	public void testGetSettings() {
		String expected = "Default";
		assertSame(expected,p1.getSettings());
	}

}
