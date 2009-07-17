package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import API.ProtocolSettings;
// Test completed
public class ProtocolSettingsTest {
	private ProtocolSettings ps1;
	private ProtocolSettings ps2;
	private ProtocolSettings ps3;
	@Before
	public void setUp() throws Exception {
		ps1 = new ProtocolSettings("Jabber","Chandler","hey");
		ps2 = new ProtocolSettings("ICQ","Ross","Spodneck");
	}

	@After
	public void tearDown() throws Exception {
		ps1 = null;
		ps2 = null;
		ps3  = null;
	}

	@Test
	public void testProtocolSettings(){
		ps3 = new ProtocolSettings("AIM","Joey","how you doin'?");
		assertSame("AIM",ps3.getProtocol());
		assertSame("Joey",ps3.getUsername());
		assertSame("how you doin'?",ps3.getPassword());
	}
	public void testSetProtocol() {
		String expected = "msn";
		ps2.setProtocol(expected);
		assertSame(expected,ps2.getProtocol());
	}

	@Test
	public void testGetProtocol() {
		String expected = "Jabber";
		assertSame(expected,ps1.getProtocol());
	}

	@Test
	public void testSetUsername() {
		String expected = "Frank";
		ps2.setUsername(expected);
		assertSame(expected,ps2.getUsername());
	}

	@Test
	public void testGetUsername() {
		String expected = "Chandler";
		assertSame(expected,ps1.getUsername());
	}

	@Test
	public void testSetPassword() {
		String expected = "Omega";
		ps2.setPassword(expected);
		assertSame(expected,ps2.getPassword());
	}

	@Test
	public void testGetPassword() {
		String expected = "hey";
		assertSame(expected,"hey");
	}

}
