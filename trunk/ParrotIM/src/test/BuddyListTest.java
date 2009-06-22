package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import API.Buddy;
import API.BuddyList;

public class BuddyListTest {
	private BuddyList bl1;
	private BuddyList bl2;
	private BuddyList bl3;
	private BuddyList bl4;
	@Before
	public void setUp() throws Exception {
		Buddy[] b1 = new Buddy[4];
				b1[0] = new Buddy("Rakan","twitter");
				b1[1] = new Buddy("Matt","ICQ");
				b1[2] = new Buddy("John","GoogleTalk");
				b1[3] = new Buddy("Nancy","GoogleTalk");
		 bl1 = new BuddyList(b1);
		 Buddy[] b2 = new Buddy[4];
		 		b2[0] = new Buddy("Rayan","twitter");
		 		b2[1] = new Buddy("Moo","ICQ");
		 		b2[2] = new Buddy("Jack","GoogleTalk");
		 		b2[3] = new Buddy("Haifa","GoogleTalk");
		bl2 = new BuddyList(b2);
		Buddy[] b3 = new Buddy[5];
				b3[0] = new Buddy("Rayan","twitter");
				b3[1] = new Buddy("Moo","ICQ");
				b3[2] = new Buddy("Jack","GoogleTalk");
				b3[3] = new Buddy("Haifa","GoogleTalk");
				bl3 = new BuddyList(b3);
		Buddy[] b4 = new Buddy[5];
				b4[0] = new Buddy("Rayan","twitter");
				b4[1] = new Buddy("Moo","ICQ");
				b4[2] = new Buddy("Jack","GoogleTalk");
				b4[3] = new Buddy("Haifa","GoogleTalk");
				b4[4] = new Buddy("Nancy","msn");
		bl4 = new BuddyList(b4);

	}

	@After
	public void tearDown() throws Exception {
		bl1 = null;
		bl2 = null;
		bl3 = null;
		bl4 = null;
	}

	@Test
	public void testSetBuddyList() {
		Buddy[] e = new Buddy[4];
		e[0] = new Buddy("Rakan","twitter");
		 e[1] = new Buddy("Moiz","ICQ");
		 e[2] = new Buddy("Jane","GoogleTalk");
		 e[3] = new Buddy("Rakan","GoogleTalk");
		 BuddyList expected = new BuddyList(e);
		 bl1.setBuddyList(e);
		 assertArrayEquals(expected.getBuddyList(),bl1.getBuddyList());

	}

	@Test
	public void testGetBuddyList() {
		Buddy[] expected = new Buddy[4];
		expected[0] = new Buddy("Rayan","twitter");
		expected[1] = new Buddy("Moo","ICQ");
		expected[2] = new Buddy("Jack","GoogleTalk");
		expected[3] = new Buddy("Haifa","GoogleTalk");
		assertSame(expected.length,bl2.getBuddyList().length);
		assertSame(expected[0].getUsername(),bl2.getBuddyList()[0].getUsername());
		}
	

	@Test
	public void testAddBuddy() {
		Buddy expected = new Buddy("Joey","msn");
		 bl3.addBuddy(expected);
		 assertSame(expected,bl3.getBuddyList()[bl3.getBuddyList().length]);
	}

	@Test
	public void testDelBuddy() {
		Buddy[] e = new Buddy[5];
		e[0] = new Buddy("Rayan","twitter");
		 e[1] = new Buddy("Moo","ICQ");
		 e[2] = new Buddy("Jack","GoogleTalk");
		 e[3] = new Buddy("Haifa","GoogleTalk");
		 BuddyList expected = new BuddyList(e);
		 bl4.delBuddy(4);
		assertSame(expected.getBuddyList().length,bl4.getBuddyList().length);
		
	}

}
