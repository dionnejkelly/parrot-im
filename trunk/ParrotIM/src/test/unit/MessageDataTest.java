package test.unit;

import static org.junit.Assert.*;

import model.dataType.MessageData;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
// Test completed
public class MessageDataTest {
	private MessageData md1;
	private MessageData md2;
	@Before
	public void setUp() throws Exception {
		md1 = new MessageData("Gina","Hey, are you coming?","TimesNewRoman","17",
				false, false, false);
		
	}

	@After
	public void tearDown() throws Exception {
		md1 = null;
		md2 = null;
	}

	@Test
	public void testMessageData() {
		md2 = new MessageData("Niko","What did you say?","Bold","20", false,
				false, false);
		assertSame("Niko",md2.getFromUser());
		assertSame("What did you say?",md2.getMessage());
		assertSame("Bold",md2.getFont());
		assertSame("20",md2.getSize());
	}

	@Test
	public void testGetFromUser() {
		String expected = "Gina";
		assertSame(expected,md1.getFromUser());
	}

	@Test
	public void testGetMessage() {
		String expected = "Hey, are you coming?";
		assertSame(expected,md1.getMessage());
	}

	@Test
	public void testGetFont() {
		String expected = "TimesNewRoman";
		assertSame(expected,md1.getFont());
	}

	@Test
	public void testGetSize() {
		String expected = "17";
		assertSame(expected,md1.getSize());
	}

	@Test
	public void testSetToUser() {
		String expected = "Rakan";
		md1.setToUser(expected);
		assertSame(expected,md1.getToUser());
	}

	@Test
	public void testGetToUser() {
		String expected = "Jack";
		md1.setToUser(expected);
		assertSame(expected,md1.getToUser());
	}

	@Test
	public void testText() {
		String expected = "<U>"+ "Gina" + ":</U> " + "<font face=\""+ "TimesNewRoman" + "\" size=\"" + "17" + "\">"+ md1.getMessage() + "</font><br><br>";
		assertEquals(expected,md1.text());
	}

	@Test
	public void testPlainText() {
		String expected = md1.getFromUser()+":"+md1.getMessage()+"\n";
		assertEquals(expected,md1.plainText());
	}

	@Test
	public void testToString() {
			String expected = "<U>"
	            + md1.getFromUser() + ":</U> " + "<font face=\""
	            + md1.getFont() + "\" size=\"" + md1.getSize() + "\">"
	            + md1.getMessage() + "</font><br><br>";
			assertEquals(expected,md1.toString());
	}

}
