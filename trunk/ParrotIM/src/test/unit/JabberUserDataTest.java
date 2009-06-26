package test.unit;

import static org.junit.Assert.*;

import model.dataType.JabberUserData;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class JabberUserDataTest {
	private JabberUserData jabber1;
	private JabberUserData jabber2;
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
		jabber1 = null;
		jabber2 = null;
	}

	@Test
	public void testJabberUserDataString() {
		String expected = "Rakan";
		jabber1 = new JabberUserData(expected);
		assertSame(expected, jabber1.getAccountName());
	}

	@Test
	public void testJabberUserDataStringStringString() {
		String expected1 = "rma";
		String expected2 = "ray";
		String expected3 = "away";
		jabber2 = new JabberUserData(expected1,expected2,expected3);
		assertSame(expected1,jabber2.getAccountName());
		assertSame(expected2,jabber2.getNickname());
		assertSame(expected3,jabber2.getStatus());

	}

}
