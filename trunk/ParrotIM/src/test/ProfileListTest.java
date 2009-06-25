package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import API.Buddy;
import API.BuddyList;
import API.Profile;
import API.ProfileList;
// Test completed
public class ProfileListTest {
	private ProfileList pl1;
	private ProfileList pl2;
	@Before
	public void setUp() throws Exception {
		Buddy[] b1 = new Buddy[4];
			b1[0] = new Buddy("Rakan","twitter");
			b1[1] = new Buddy("Matt","ICQ");
			b1[2] = new Buddy("John","GoogleTalk");
			b1[3] = new Buddy("Nancy","GoogleTalk");
				BuddyList bl1 = new BuddyList(b1);
		Profile[] p1 = new Profile[2];
		Profile[] p2 = new Profile[1];
		p1[0] = new Profile(bl1,"Default","Falcon");
		Buddy[] b2 = new Buddy[4];
 			b2[0] = new Buddy("Rayan","twitter");
 			b2[1] = new Buddy("Moo","ICQ");
 			b2[2] = new Buddy("Jack","GoogleTalk");
 			b2[3] = new Buddy("Haifa","GoogleTalk");
 		BuddyList bl2 = new BuddyList(b2);
		p1[1] = new Profile(bl2,"away","Raven");
		
		pl1 = new ProfileList(p1);
		
		p2[0] = new Profile(bl1,"Idle","Zidan","fraMadrid",false);
		pl2 = new ProfileList(p2);
		
	}

	@After
	public void tearDown() throws Exception {
		pl1 = null;
		pl2 = null;
	}

	@Test
	public void testSetProfileList() {
		Buddy[] e1 = new Buddy[5];
		e1[0] = new Buddy("Rayan","twitter");
		e1[1] = new Buddy("Moo","ICQ");
		e1[2] = new Buddy("Jack","GoogleTalk");
		e1[3] = new Buddy("Haifa","GoogleTalk");
		BuddyList exp1 = new BuddyList(e1);
		Profile[] expected = new Profile[2];
		expected[0] = new Profile(exp1,"busy","Eagle");
		Buddy[] e2 = new Buddy[5];
		e2[0] = new Buddy("Rayan","twitter");
		e2[1] = new Buddy("Moo","ICQ");
		e2[2] = new Buddy("Jack","GoogleTalk");
		e2[3] = new Buddy("Haifa","GoogleTalk");
		e2[4] = new Buddy("Nancy","msn");
		BuddyList exp2 = new BuddyList(e2);
		expected[1] = new Profile(exp2,"home","Bird");
		pl1.setProfileList(expected);
		assertArrayEquals(expected,pl1.getProfileList());
		
	}

	@Test
	public void testGetProfileList() {
		Buddy[] e = new Buddy[4];
		e[0] = new Buddy("Rakan","twitter");
		e[1] = new Buddy("Matt","ICQ");
		e[2] = new Buddy("John","GoogleTalk");
		e[3] = new Buddy("Nancy","GoogleTalk");
			BuddyList exp = new BuddyList(e);
			Profile[] expected = new Profile[1];
			expected[0] = new Profile(exp,"Idle","Zidan","fraMadrid",false);
			assertSame(expected[0].getPassword(),pl2.getProfileList()[0].getPassword());
			for(int i=0;i<e.length;i++){
				assertSame(expected[0].getBuddyList().getBuddyList()[i].getUsername(),pl2.getProfileList()[0].getBuddyList().getBuddyList()[i].getUsername());
			}
	}

}
