package test.unit;

import static org.junit.Assert.*;

import java.util.ArrayList;

import model.dataType.AccountData;
import model.dataType.GoogleTalkUserData;
import model.dataType.UserData;
import model.dataType.tempData.AccountTempData;
import model.enumerations.ServerType;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
// Finished testing this
// Maybe need changes in removeFriend
public class AccountDataTest {
	private AccountData ad1;
	private AccountData ad2;
	private AccountData ad3;
	private AccountData ad4;
	private AccountData ad5;
	private AccountData ad6;
	@Before
	public void setUp() throws Exception {
		ad1 = new AccountData(ServerType.GOOGLE_TALK,"Rakan","1234");
		ad2 = new AccountData(ServerType.ICQ,"Ross","abc");
		ad3 = new AccountData(ServerType.MSN,"Janice","ohmygod");
		ad4 = new AccountData(ServerType.TWITTER,"Joey","Friends");
		ad5 = new AccountData(ServerType.AIM,"Peter","familyguy");
	}

	@After
	public void tearDown() throws Exception {
		ad1=null;
		ad2 = null;
		ad3 = null;
		ad4 = null;
		ad5 = null;
	}
	public void testAccountTempData() {
		ad6 = new AccountData(ServerType.ICQ,"James","007");
		assertSame(ServerType.ICQ,ad4.getServer());
		assertSame("James",ad4.getAccountName());
		assertSame("007",ad4.getPassword());
	}
	@Test
	public void testSetServer() {
		AccountData expected = new AccountData(ServerType.JABBER,"Rakan","1234");
		ad1.setServer(ServerType.JABBER);
		assertSame(expected.getServer(), ad1.getServer());
	}

	@Test
	public void testGetServer() {
		ServerType expected = ServerType.ICQ;
		assertSame(expected,ad2.getServer());
		
	}

	@Test
	public void testSetAccountName() {
		ad2.setAccountName("Chandler");
		AccountData expected = new AccountData(ServerType.ICQ,"Chandler","abc");
		assertSame(expected.getAccountName(),ad2.getAccountName());
	}

	@Test
	public void testGetAccountName() {
		String expected = "Ross";
		assertSame(expected,ad2.getAccountName());
	}

	@Test
	public void testSetPassword() {
		ad3.setPassword("qwerty");
		AccountData expected = new AccountData(ServerType.MSN,"Janice","qwerty");
		assertSame(expected.getPassword(),ad3.getPassword());
	}

	@Test
	public void testGetPassword() {
		String expected = "abc";
		assertSame(expected,ad2.getPassword());
	}

	@Test
	public void testAddFriend() {
		ArrayList<UserData> expected = new ArrayList<UserData>();
		UserData e = new GoogleTalkUserData ("toot","talal","offline");
		expected.add(e);
		ad1.addFriend(new GoogleTalkUserData("toot","talal","offline"));
		
		}
	

	@Test
	public void testRemoveFriend() {
		GoogleTalkUserData e1 = new GoogleTalkUserData("Horton","tim","away");
		GoogleTalkUserData e2 = new GoogleTalkUserData("Star","Bucks","Busy");
		ad2.addFriend(e1);
		ad2.addFriend(e2);
		// You can see what is wrong here
		assertTrue(ad2.removeFriend(e2));
		assertTrue(ad2.removeFriend(new GoogleTalkUserData("Horton","tim","away")));
		
	}

	@Test
	public void testGetFriends() {
		GoogleTalkUserData e1 = new GoogleTalkUserData("Horton","tim","away");
		GoogleTalkUserData e2 = new GoogleTalkUserData("Star","Bucks","Busy");
		ad4.addFriend(e1);
		ad4.addFriend(e2);
		ArrayList<GoogleTalkUserData> expected = new ArrayList<GoogleTalkUserData>();
		expected.add(e1);
		expected.add(e2);
		Object[] array1 = expected.toArray();
		assertSame(((GoogleTalkUserData) array1[0]).getAccountName(),e1.getAccountName());
		assertSame(((GoogleTalkUserData) array1[1]).getAccountName(),e2.getAccountName());
	}

	@Test
	public void testFriendExists() {
		GoogleTalkUserData expected = new GoogleTalkUserData("expected","e","good");
		ad4.addFriend(expected);
		assertTrue(ad4.friendExists(expected));
	}

	@Test
	public void testSetOwnUserData() {
		GoogleTalkUserData expected = new GoogleTalkUserData("Griffen","Peter","online");
		ad5.setOwnUserData(expected);
		assertSame(expected,ad5.getOwnUserData());
	}

	@Test
	public void testGetOwnUserData() {
		GoogleTalkUserData expected = new GoogleTalkUserData("Stewie","Stew","online");
		ad1.setOwnUserData(expected);
		assertSame(expected,ad1.getOwnUserData());
	}

	@Test
	public void testSetConnected() {
		boolean expected = true;
		ad1.setConnected(true);
		assertSame(expected,ad1.isConnected());
		ad1.setConnected(false);
		assertNotSame(expected,ad1.isConnected());
	}

	@Test
	public void testIsConnected() {
		boolean expected = false;
		assertSame(expected,ad2.isConnected());
		ad2.setConnected(true);
		assertNotSame(expected,ad2.isConnected());
	}
	public void testFindFriendByUserID() {
		GoogleTalkUserData expected = new GoogleTalkUserData("expected","e","good");
		ad4.addFriend(expected);
		assertTrue(ad4.findFriendByUserID(expected.getAccountName()));
		ad4.addFriend(expected);
		assertTrue(ad4.findFriendByUserID("expected"));
	}

}
