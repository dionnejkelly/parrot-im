package BetaTest;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import model.DatabaseFunctions;
import model.dataType.tempData.ChatLogMessageTempData;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
// Not finished yet
public class DatabaseFunctionsTest {
	private DatabaseFunctions db1;
	@Before
	public void setUp() throws Exception {
		db1 = new DatabaseFunctions();
	}

	@After
	public void tearDown() throws Exception {
		db1 = null;
	}

	@Test
	public void testDatabaseFunctions() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetDatabaseName() {
	}

	@Test
	public void testSetDatabaseName() {
		db1.setDatabaseName("test.db");
		assertEquals("test.db",db1.getDatabaseName());
	}

	@Test
	public void testDropTables() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddChat() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetChatNameList() throws SQLException, ClassNotFoundException {
		DatabaseFunctions db = new DatabaseFunctions();
		Vector<String> test = new Vector<String>();
		test = db.getChatNameList("parrot275testing", "");
		System.out.println(test.size());
		for(int i=0;i<test.size();i++){
			System.out.println(test.get(i));
		}
	}

	@Test
	public void testGetChatDatesFromName() throws SQLException, ClassNotFoundException {
		DatabaseFunctions db = new DatabaseFunctions();
		Vector<String> test = db.getChatDatesFromName("cmpt275testing", "rmamnk", "hi");
		for(int i=0;i<test.size();i++){
			System.out.println(test.get(i));
		}
	}

	@Test
	public void testGetMessageFromDate() throws SQLException {
		ArrayList<ChatLogMessageTempData> test = db1.getMessageFromDate("cmpt275testing", "rmamnk", "", "hi");
		for(int i=0;i<test.size();i++){
			System.out.println(test.get(i));
		}
	}

	@Test
	public void testAddProfiles() {
		fail("Not yet implemented");
	}

	@Test
	public void testRemoveProfile() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetDefaultProfile() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetDefaultProfile() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetProfileList() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddUsers() {
		fail("Not yet implemented");
	}

	@Test
	public void testRemoveAccountFromProfile() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetAccountPassword() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetUserList() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetAccountList() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetProfilesUserList() {
		fail("Not yet implemented");
	}

	@Test
	public void testCheckAccountExists() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetFriendListByAccountName() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddFriend() {
		fail("Not yet implemented");
	}

	@Test
	public void testRemoveFriend() {
		fail("Not yet implemented");
	}

	@Test
	public void testChangeBlocked() {
		fail("Not yet implemented");
	}

	@Test
	public void testCheckFriendExists() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddQuestion() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddAnswer() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetResponse() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetQuestionList() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetAnswersList() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetAnswersListString() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddAfter() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetAllAfterQuestions() {
		fail("Not yet implemented");
	}

	@Test
	public void testRemoveChatQuestion() {
		fail("Not yet implemented");
	}

}
