package BetaTest;

import static org.junit.Assert.*;

import java.sql.SQLException;

import model.Model;
import model.dataType.ChatbotQADataType;
import model.dataType.tempData.CustomizedChatbotModel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CustomizedChatbotModelTest {
	// This test case is failing and I don't know the reason
	private CustomizedChatbotModel cm1;
	private CustomizedChatbotModel cm2;
	@Before
	public void setUp() throws Exception {
		cm1 = new CustomizedChatbotModel(new Model());
		ChatbotQADataType cd1 = new ChatbotQADataType(new Model(),"Hey, got a minute?","What's up?");
		cm1.addQA(cd1);
		// Why this doesn't work?
		/*
		System.out.println(cm1.getQASize());
		for(int i=0;i<cm1.getQASize();i++){
			System.out.println(cm1.getQAObject(i).getQuestions());
		}
		*/
	}

	@After
	public void tearDown() throws Exception {
		cm1 = null;
		cm2 = null;
	}

	@Test
	public void testCustomizedChatbotModel() throws ClassNotFoundException, SQLException {
		cm2 = new CustomizedChatbotModel(new Model());
		
	}

	@Test
	public void testAddQA() throws SQLException, ClassNotFoundException {
		System.out.println(cm1.getQASize());
		int x = cm1.getQASize();
		ChatbotQADataType cd2 = new ChatbotQADataType(new Model(),"Hey, are you watching a movie?","No");
		cm1.addQA(cd2);
		int expected = x+1;
		
		assertSame(expected,cm1.getQASize());
	}

	@Test
	public void testRemoveQA() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetQAList() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetQAObject() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetQASize() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetChatbotQAList() {
		fail("Not yet implemented");
	}

}
