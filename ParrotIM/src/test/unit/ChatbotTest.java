package test.unit;

import static org.junit.Assert.*;

import java.util.Vector;

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
	public void testGet_input() throws Exception {
		String expected = "WHAT IS YOUR NAME ";
		
		cb1.get_input("What is your name?");
		
		assertSame(true, cb1.check(expected));
	}

	@Test
	public void testRespond() throws Exception {
		// Show responses
		String q1 = "What is your name?";
		cb1.get_input(q1);
		String expected = cb1.respond();
		for(int i=0;i<cb1.get_knowledgebase()[0].length;i++){
			if(expected.contains(cb1.get_knowledgebase()[0][i])){
				assertSame(expected,cb1.get_knowledgebase()[0][i]);
				break;
			}
		}
		
	}

	@Test
	public void testQuit() {
		assertFalse(cb1.quit());
		
	}

	@Test
	public void testFind_match() throws Exception {
		String q1 = "What is your name?";
		cb1.get_input(q1);
		Vector<String> expected = cb1.getRespList();
		
		Vector<String> check = new Vector<String>();
		
		for (int i = 0; i < expected.size(); i++) {
			check.add(cb1.get_knowledgebase()[0][i]);
		}
		
		assertSame(expected,check);
		
	}

	@Test
	public void testHandle_repetition() throws Exception {
		
//		String q1 = "What is your name?";
//		cb1.get_input(q1);
//		String expected = cb1.handle_repetition();
//		
//		System.out.println("Expected = " + expected);
//		
//		for(int i=0;i<cb1.get_knowlegebase()[0].length;i++){
//			if(expected.contains(cb1.get_knowlegebase()[0][i])){
//				assertSame(expected,cb1.get_knowlegebase()[0][i]);
//				break;
//			}
//		}
		
	}

	@Test
	public void testHandle_user_repetition() throws Exception {
		String q1 = "What is your name?";
		String expected = "WHAT IS YOUR NAME";
		
		cb1.get_input(q1);
		String result = cb1.getHandle_user_repetion();
		
		assertSame(expected, result);
		
		
		
	}

	@Test
	public void testHandle_event() throws Exception {
		String q1 = "What is your name?";
		String expected = "WHAT IS YOUR NAME";
		
		cb1.get_input(q1);
		String result = cb1.getHandle_event();
		
		assertSame(expected, result);
	}

	@Test
	public void testSelect_response() throws Exception {
//		String q1 = "What is your name?";
//		cb1.get_input(q1);
//		String expected = cb1.select_response();
//		
//		for(int i=0;i<cb1.get_knowlegebase()[0].length;i++){
//			if(expected.contains(cb1.get_knowlegebase()[0][i])){
//				assertSame(expected,cb1.get_knowlegebase()[0][i]);
//				break;
//			}
//		}
		
		
		
		
	}

	@Test
	public void testSave_prev_input() throws Exception {
		String q1 = "What is your name?";
		String expected = "WHAT IS YOUR NAME";
		
		cb1.get_input(q1);
		String result = cb1.getSave_prev_input();
		
		assertSame(expected, result);
	}

	@Test
	public void testSave_prev_response() throws Exception {
		String q1 = "What is your name?";

		cb1.get_input(q1);
		
		String expected = cb1.get_response();
		
		String result = cb1.getSave_preve_response();
		
		assertSame(expected, result);
	}

	@Test
	public void testSave_prev_event() throws Exception {
		String q1 = "What is your name?";

		cb1.get_input(q1);
		
		String expected = "";
		
		String result = cb1.getSave_prev_event();
		
		assertSame(expected, result);
	}

	@Test
	public void testSet_event() throws Exception {
		
		cb1.set_event("repetition");
		
		String expected = "repetition";

		assertSame(expected, cb1.getSet_event());
	}

	@Test
	public void testSave_input() throws Exception {
		String q1 = "What is your name?";
		cb1.get_input(q1);
		
		cb1.save_input();
		
		String expected = "WHAT IS YOUR NAME";
		
		
		assertSame(expected, cb1.getSave_input());
	}

	@Test
	public void testSet_input() {
		
		String q1 = "What is your name?";
		
		cb1.set_input(q1);
		
		String expected = cb1.getSet_input();
		assertSame(expected, q1);
	}

	@Test
	public void testRestore_input() {
	
		cb1.restore_input();
		
		String expected = "";
		assertSame(expected, cb1.getRestore_input());
	}

	@Test
	public void testGet_response() throws Exception {
		String q1 = "What is your name?";
		cb1.get_input(q1);
		
		String expected = cb1.get_response();
		
		assertSame(expected, cb1.respond());
		
		
	}

	@Test
	public void testPreprocess_input() throws Exception {
		String q1 = "What is your name?";
		cb1.set_input(q1);
		cb1.preprocess_input();
	
		
		String expected = "WHAT IS YOUR NAME";
		
		assertSame(expected, cb1.respond());
	}

	@Test
	public void testBot_repeat() throws Exception {
		String q1 = "What is your name?";
		
		cb1.get_input(q1);
		
		assertSame(true, !cb1.bot_repeat());
	}

	@Test
	public void testUser_repeat() throws Exception {
		String q1 = "What is your name?";
		
		cb1.get_input(q1);
		cb1.get_input(q1);
		cb1.get_input(q1);
		cb1.get_input(q1);
		
		assertSame(true, cb1.user_repeat());
	}

	@Test
	public void testBot_understand() throws Exception {
		String q1 = "Abc";
		
		cb1.get_input(q1);
	
		
		assertSame(true, !cb1.bot_understand());
	}

	@Test
	public void testNull_input() throws Exception {
		String q1 = "Hi";
		cb1.get_input(q1);
		
		String q2 = "";
		cb1.get_input(q2);
		
	
		
		assertSame(true, cb1.null_input());
	}

	@Test
	public void testNull_input_repetition() throws Exception {
		String q1 = "";
		cb1.get_input(q1);
		
		String q2 = "";
		cb1.get_input(q2);
		
	
		
		assertSame(true, cb1.null_input_repetition());
	}

	@Test
	public void testSame_event() throws Exception {
		String q1 = "Hi";
		cb1.get_input(q1);
		
		String q2 = "";
		cb1.get_input(q2);
		
	
		assertSame(true, !cb1.same_event());
	}

	@Test
	public void testNo_response() throws Exception {
		String q1 = "Hi";
		cb1.get_input(q1);
		
		assertSame(true, !cb1.no_response());
	}

	@Test
	public void testSame_input() throws Exception {
		String q1 = "Hi";
		cb1.get_input(q1);
		cb1.get_input(q1);
		cb1.get_input(q1);
		cb1.get_input(q1);
		
		assertSame(true, cb1.same_input());
	}

	@Test
	public void testSimilar_input() throws Exception {
		String q1 = "Hi";
		cb1.get_input(q1);
		
		String q2 = "Hi?";
		cb1.get_input(q2);
		
		
		assertSame(true, cb1.similar_input());
	}

	@Test
	public void testCleanString() {
		String expected = "What is your age ";
		assertSame(expected,cb1.cleanString("What is your age?"));
	}

}
