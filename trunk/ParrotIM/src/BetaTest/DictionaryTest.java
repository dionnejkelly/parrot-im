package BetaTest;

import static org.junit.Assert.*;

import java.util.Dictionary;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DictionaryTest {
	private Dictionary d1;
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testDictionary() {
		d1 = new Dictionary();
		assertSame(10,000,d1.size());
		
		
	}

	@Test
	public void testDictionaryCharArray() {
		fail("Not yet implemented");
	}

	@Test
	public void testSaveString() {
		fail("Not yet implemented");
	}

	@Test
	public void testSaveOutputStream() {
		fail("Not yet implemented");
	}

	@Test
	public void testLoadString() {
		fail("Not yet implemented");
	}

	@Test
	public void testLoadInputStream() {
		fail("Not yet implemented");
	}

	@Test
	public void testTrimToSize() {
		fail("Not yet implemented");
	}

	@Test
	public void testAdd() {
		fail("Not yet implemented");
	}

	@Test
	public void testToArray() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetDataSize() {
		fail("Not yet implemented");
	}

}
