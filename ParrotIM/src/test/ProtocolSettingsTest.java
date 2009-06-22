package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import API.ProtocolSettings;

public class ProtocolSettingsTest {
	private ProtocolSettings ps1;
	@Before
	public void setUp() throws Exception {
		ps1 = new ProtocolSettings("Jabber","Chandler","hey");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testSetProtocol() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetProtocol() {
		String expected = "Jabber";
		assertSame(expected,ps1.getProtocol());
	}

	@Test
	public void testSetUsername() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetUsername() {
		String expected = "Chandler";
		assertSame(expected,ps1.getUsername());
	}

	@Test
	public void testSetPassword() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetPassword() {
		String expected = "hey";
		assertSame(expected,"hey");
	}

}
