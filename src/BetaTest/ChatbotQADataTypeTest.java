package BetaTest;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.Vector;

import model.Model;
import model.dataType.ChatbotQADataType;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ChatbotQADataTypeTest {
	private ChatbotQADataType dt1;
	private ChatbotQADataType dt2;
	@Before
	public void setUp() throws Exception {
		Model e = new Model();
		dt1 = new ChatbotQADataType(e);
	}

	@After
	public void tearDown() throws Exception {
		dt1 = null;
		dt2 = null;
	}

	@Test
	public void testChatbotQADataTypeModel() {
		fail("Not yet implemented");
	}

	@Test
	public void testChatbotQADataTypeModelString() throws SQLException, ClassNotFoundException {
		// The line down here couldn't be processed, it maybe something with model not able to add the question
		// Other constructors have no problem, this one has
		// Maybe because : model.addqustion() there should be this. 
		dt2 = new ChatbotQADataType(new Model(),"Are you coming tommorow?");
		assertSame("Are you coming tommorow?", dt2.getQuestions().get(0));
	}

	@Test
	public void testChatbotQADataTypeModelStringString() throws SQLException, ClassNotFoundException {
		dt2 = new ChatbotQADataType(new Model(),"Are you coming tommorow?","YES");
		assertSame("Are you coming tommorow?", dt2.getQuestions().get(0));
		assertSame("YES",dt2.getAnswers().get(0));
	}

	@Test
	public void testChatbotQADataTypeModelVectorOfStringVectorOfString() {
		Vector<String> expected1 = new Vector<String>();
		expected1.add("Are you coming tommorow?");
		expected1.add("Did you finish the paper?");
		Vector<String> expected2 = new Vector<String>();
		expected2.add("YES");
		expected2.add("NO");
		dt2 = new ChatbotQADataType(new Model(),expected1,expected2);
		assertArrayEquals(expected1.toArray(),dt2.getQuestions().toArray());
	}

	@Test
	public void testAddQuestion() throws SQLException, ClassNotFoundException {
		dt1.addQuestion("Are you home?");
		assertEquals("Are you home?",dt1.getQuestions().get(0));
	}

	@Test
	public void testRemoveQuestion() throws SQLException, ClassNotFoundException {
		dt1.addQuestion("Are you home?");
		dt1.removeQuestion(0);
		assertNull(dt1.getQuestions().get(0));
	}

	@Test
	public void testAddAnswer() throws ClassNotFoundException, SQLException {
		dt1.addAnswer("I AM THERE");
		assertEquals("I AM THERE", dt1.getAnswers().get(0));
	}

	/*@Test
	public void testRemoveAllQA() {
	
	}*/

	@Test
	public void testRemoveAnswer() throws ClassNotFoundException, SQLException {
		dt1.addAnswer("I AM THERE");
		dt1.addAnswer("YES");
		dt1.removeAnswer(0);
		assertNull(dt1.getAnswers().get(0));
	}

	@Test
	public void testToString() {
		Vector<String> expected1 = new Vector<String>();
		expected1.add("Are you coming tommorow?");
		expected1.add("Did you finish the paper?");
		dt1 = new ChatbotQADataType(new Model(),expected1,null);
		assertEquals("Are you coming tommorow?, Did you finish the paper?",dt1.toString());
	}

	@Test
	public void testGetQuestions() {
		
	}

	@Test
	public void testGetAnswers() {
		fail("Not yet implemented");
	}

	@Test
	public void testIsEmpty() {
		fail("Not yet implemented");
	}

}
