package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import API.Buddy;
import API.BuddyList;
import API.Profile;

// Test completed
public class ProfileTest {
	private Profile p1;
	private Profile p2;
	private Profile p3;
	private Profile p4;
	@Before
	public void setUp() throws Exception {
		Buddy[] b1 = new Buddy[4];
		b1[0] = new Buddy("Rakan","twitter");
		 b1[1] = new Buddy("Matt","ICQ");
		 b1[2] = new Buddy("John","GoogleTalk");
		 b1[3] = new Buddy("Nancy","GoogleTalk");
		 BuddyList bl1 = new BuddyList(b1);
		p1 = new Profile(bl1,"Default","Joey","1234",true);
		p2 = new Profile(bl1,"Jabber","Maria","mex45",false);
	}

	@After
	public void tearDown() throws Exception {
		p1 = null;
		p2 = null;
		p3 = null;
		p4 = null;
	}

	@Test
	public void testProfileBuddyListStringString(){
		Buddy[] expected = new Buddy[1];
		BuddyList expected1 = new BuddyList(expected);
		expected[0] = new Buddy("roy","msn");
		p3 = new Profile(expected1,"Advance","Allan");
		assertArrayEquals(expected,p3.getBuddyList().getBuddyList());
		assertSame("Advance",p3.getSettings());
		assertSame("Allan",p3.getName());
	}
	public void testProfileBuddyListStringStringStringBoolean(){
		Buddy[] expected = new Buddy[1];
		BuddyList expected1 = new BuddyList(expected);
		expected[0] = new Buddy("roy","msn");
		p3 = new Profile(expected1,"Advance","Allan","bus",false);
		assertArrayEquals(expected,p3.getBuddyList().getBuddyList());
		assertSame("Advance",p3.getSettings());
		assertSame("Allan",p3.getName());
		assertSame("bus",p3.getPassword());
		assertFalse(p3.getChkpassword());
	}
	public void testSetBuddyList() {
		Buddy[] expected = new Buddy[1];
		BuddyList expected1 = new BuddyList(expected);
		expected[0] = new Buddy("roy","msn");
		p1.setBuddyList(expected1);
		assertArrayEquals(expected,p1.getBuddyList().getBuddyList());
	}

	@Test
	public void testGetBuddyList() {
		Buddy[] e = new Buddy[4];
		e[0] = new Buddy("Rakan","twitter");
		 e[1] = new Buddy("Matt","ICQ");
		 e[2] = new Buddy("John","GoogleTalk");
		 e[3] = new Buddy("Nancy","GoogleTalk");
		 BuddyList expected = new BuddyList(e);
		 for(int i=0;i<e.length;i++){
			 assertSame(expected.getBuddyList()[i].getUsername(),p1.getBuddyList().getBuddyList()[i].getUsername());
		 }
	}

	@Test
	public void testGetName() {
		String expected = "Joey";
		assertSame(expected,p1.getName());
	}

	@Test
	public void testSetName() {
		String expected = "Snake";
		p2.setName(expected);
		assertSame(expected,p2.getName());
	}

	@Test
	public void testGetPassword() {
		String expected = "1234";
		assertSame(expected,p1.getPassword());
	}

	@Test
	public void testSetPassword() {
		String expected = "moizlajuve";
		p2.setPassword(expected);
		assertSame(expected,p2.getPassword());
	}

	@Test
	public void testGetChkpassword() {
		boolean expected = true;
		assertSame(expected,p1.getChkpassword());
	}

	@Test
	public void testSetChkpassword() {
		p2.setChkpassword(true);
		assertTrue(p2.getChkpassword());
		assertSame(true,p2.getChkpassword());
	}

	@Test
	public void testSetSettings() {
		String expected = "Default";
		p2.setSettings(expected);
		assertSame(expected,p2.getSettings());
	}

	@Test
	public void testGetSettings() {
		String expected = "Default";
		assertSame(expected,p1.getSettings());
	}

}
