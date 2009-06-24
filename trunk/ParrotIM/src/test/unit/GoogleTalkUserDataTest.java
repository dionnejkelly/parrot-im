package test.unit;

import static org.junit.Assert.*;

import model.dataType.GoogleTalkUserData;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GoogleTalkUserDataTest {
	private GoogleTalkUserData u1;
	@Before
	public void setUp() throws Exception {
		u1 = new GoogleTalkUserData("mac999","mohd","idle");
	}

	@After
	public void tearDown() throws Exception {
		u1 = null;
	}

	
	

	@Test
	public void testSetOnline() {
		boolean expected = true;
		u1.setOnline(true);
		assertSame(expected,u1.isOnline());
		u1.setOnline(false);
		assertNotSame(expected,u1.isOnline());
	}

	@Test
	public void testIsOnline() {
		u1.setOnline(false);
		assertFalse(u1.isOnline());
	}

}
