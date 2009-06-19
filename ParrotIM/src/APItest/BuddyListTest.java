package APItest;

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
		 BuddyList Expected = new BuddyList(e);
		 bl1.setBuddyList(e);
		 assertSame(Expected,bl1);

	}

	@Test
	public void testGetBuddyList() {
		Buddy[] Expected = new Buddy[4];
		Expected[0] = new Buddy("Rayan","twitter");
		 Expected[1] = new Buddy("Moo","ICQ");
		 Expected[2] = new Buddy("Jack","GoogleTalk");
		 Expected[3] = new Buddy("Haifa","GoogleTalk");
		 assertSame(Expected,bl2.getBuddyList());
	}

	@Test
	public void testAddBuddy() {
		Buddy[] e = new Buddy[5];
		e[0] = new Buddy("Rayan","twitter");
		 e[1] = new Buddy("Moo","ICQ");
		 e[2] = new Buddy("Jack","GoogleTalk");
		 e[3] = new Buddy("Haifa","GoogleTalk");
		 e[4] = new Buddy("Joey","msn");
		 BuddyList Expected = new BuddyList(e);
		 bl3.addBuddy(new Buddy("Joey","msn"));
		 assertEquals(Expected,bl3);
	}

	@Test
	public void testDelBuddy() {
		Buddy[] e = new Buddy[5];
		e[0] = new Buddy("Rayan","twitter");
		 e[1] = new Buddy("Moo","ICQ");
		 e[2] = new Buddy("Jack","GoogleTalk");
		 e[3] = new Buddy("Haifa","GoogleTalk");
		 BuddyList Expected = new BuddyList(e);
		 bl4.delBuddy(4);
		 assertEquals(Expected,bl4);
		
	}

}
