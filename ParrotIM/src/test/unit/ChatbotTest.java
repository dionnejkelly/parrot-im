package test.unit;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import controller.chatbot.Chatbot;

public class ChatbotTest {
	private Chatbot cb1;
	@Before
	public void setUp() throws Exception {
		cb1 = new Chatbot();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGet_input() {
		String expected = "WHAT IS YOUR NAME";
		try {
			cb1.get_input("What is your name?");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertSame(expected,cb1.getsInput());
	}

	@Test
	public void testRespond() throws Exception {
		// Show responses
		String q1 = "What is your name?";
		cb1.get_input(q1);
		String expected = cb1.respond();
		for(int i=0;i<cb1.get_knowlegebase()[0].length;i++){
			if(expected.contains(cb1.get_knowlegebase()[0][i])){
				assertSame(expected,cb1.get_knowlegebase()[0][i]);
				break;
			}
		}
		
	}

	@Test
	public void testQuit() {
		assertFalse(cb1.quit());
		
	}

	@Test
	public void testFind_match() {
		fail("Not yet implemented");
	}

	@Test
	public void testHandle_repetition() {
		fail("Not yet implemented");
	}

	@Test
	public void testHandle_user_repetition() {
		fail("Not yet implemented");
	}

	@Test
	public void testHandle_event() {
		fail("Not yet implemented");
	}

	@Test
	public void testSelect_response() {
		fail("Not yet implemented");
	}

	@Test
	public void testSave_prev_input() {
		fail("Not yet implemented");
	}

	@Test
	public void testSave_prev_response() {
		fail("Not yet implemented");
	}

	@Test
	public void testSave_prev_event() {
		fail("Not yet implemented");
	}

	@Test
	public void testSet_event() {
		fail("Not yet implemented");
	}

	@Test
	public void testSave_input() {
		fail("Not yet implemented");
	}

	@Test
	public void testSet_input() {
		fail("Not yet implemented");
	}

	@Test
	public void testRestore_input() {
		fail("Not yet implemented");
	}

	@Test
	public void testGet_response() {
		fail("Not yet implemented");
	}

	@Test
	public void testPreprocess_input() {
		fail("Not yet implemented");
	}

	@Test
	public void testBot_repeat() {
		fail("Not yet implemented");
	}

	@Test
	public void testUser_repeat() {
		fail("Not yet implemented");
	}

	@Test
	public void testBot_understand() {
		fail("Not yet implemented");
	}

	@Test
	public void testNull_input() {
		fail("Not yet implemented");
	}

	@Test
	public void testNull_input_repetition() {
		fail("Not yet implemented");
	}

	@Test
	public void testSame_event() {
		fail("Not yet implemented");
	}

	@Test
	public void testNo_response() {
		fail("Not yet implemented");
	}

	@Test
	public void testSame_input() {
		fail("Not yet implemented");
	}

	@Test
	public void testSimilar_input() {
		fail("Not yet implemented");
	}

	@Test
	public void testCleanString() {
		String expected = "Whatisyourage";
		assertSame(expected,cb1.cleanString("What is your age?"));
	}

}
