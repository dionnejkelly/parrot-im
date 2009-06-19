package APItest;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import API.Buddy;

public class BuddyTest {
	private Buddy b1;
	private Buddy b2;
	private Buddy b3;
	private Buddy b4;
	@Before

	public void setUp() throws Exception {
		
		b1 = new Buddy("Rakan","twitter");
		b2 = new Buddy("Matt","ICQ");
		b3 = new Buddy("John","GoogleTalk");
		b4 = new Buddy("Nancy","GoogleTalk");
	}

	@After
	public void tearDown() throws Exception {
		b1 = null;
		b2 = null;
		b3 = null;
		b4 = null;
	}

	@Test
	public void testSetUsername() {
		Buddy Expected = new Buddy("Sara","twitter");
		b1.setUsername("Sara");
		assertTrue(Expected.equals(b1));
	}

	@Test
	public void testGetUsername() {
		String Expected = "Matt";
		assertSame(Expected,b2.getUsername());
	}

	@Test
	public void testSetChannel() {
		Buddy Expected = new Buddy("John","msn");
		b3.setChannel("msn");
		assertSame(Expected,b3);
	}

	@Test
	public void testGetChannel() {
		String Expected = "GoogleTalk";
		assertSame(Expected,b4.getChannel());
	}

	@Test
	public void testToString() {
		String Expected = "Nancy";
		assertSame(Expected,b4.toString());
	}

}
