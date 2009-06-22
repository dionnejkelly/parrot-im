package test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import model.dataType.AccountData;
import model.dataType.CurrentProfileData;
import model.dataType.GoogleTalkUserData;
import model.dataType.ServerType;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CurrentProfileDataTest {
	private CurrentProfileData cpd1;
	private CurrentProfileData cpd2;
	private CurrentProfileData cpd3;
	@Before
	public void setUp() throws Exception {
		cpd1 = new CurrentProfileData(new AccountData(ServerType.GOOGLE_TALK,"Rakan","1234"),"Rakan Alkheliwi");
		ArrayList<AccountData> a1 = new ArrayList<AccountData>();
		a1.add(new AccountData(ServerType.MSN,"Monica","mgs"));
		a1.add(new AccountData(ServerType.ICQ,"Jeneifer","gtasa"));
		cpd2 = new CurrentProfileData(a1,"Goodfellas");
		ArrayList<AccountData> a2 = new ArrayList<AccountData>();
		AccountData e1 = new AccountData(ServerType.AIM,"Vito","don");
		GoogleTalkUserData f1 = new GoogleTalkUserData("rayan","ray","Busy");
		GoogleTalkUserData f2 = new GoogleTalkUserData("Abdulhalim","Hafez","away");
		GoogleTalkUserData f3 = new GoogleTalkUserData("John","J","online");
		e1.addFriend(f1);
		a2.add(e1);
		AccountData e2 = new AccountData(ServerType.GOOGLE_TALK,"Michael","son");
		e2.addFriend(f2);
		e2.addFriend(f3);
		a2.add(e2);
		AccountData e3 = new AccountData(ServerType.ICQ,"Clemanza","fat");
		a2.add(e3);
		
		cpd3 = new CurrentProfileData(a2,"Corleone");
	}

	@After
	public void tearDown() throws Exception {
		cpd1 = null;
		cpd2 = null;
		cpd3 = null;
	}

	@Test
	public void testSetAccountData() {
		ArrayList<AccountData> expected = new ArrayList<AccountData>();
		expected.add(new AccountData(ServerType.GOOGLE_TALK,"Rakan","1234"));
		cpd1.setAccountData(expected);
		assertSame(cpd1.getAccountData(),expected);
	}

	@Test
	public void testGetAccountData() {
		ArrayList<AccountData> expected = new ArrayList<AccountData>();
		expected.add(new AccountData(ServerType.MSN,"Monica","mgs"));
		expected.add(new AccountData(ServerType.ICQ,"Jeneifer","gtasa"));
		assertSame(expected,cpd2.getAccountData());
	}

	@Test
	public void testSetProfileName() {
		String expected = "The_Godfather";
		cpd2.setProfileName("The_Godfather");
		assertSame(expected,cpd2.getProfileName());
	}

	@Test
	public void testGetProfileName() {
		String expected = "Rakan Alkheliwi";
		assertSame(expected,cpd1.getProfileName());
	}

	@Test
	public void testToString() {
		String expected = "Corleone";
		assertSame(expected, cpd3.toString());
	}

	@Test
	public void testAddAccount() {
		
		AccountData e = new AccountData(ServerType.GOOGLE_TALK,"Rachel","freinds");
		ArrayList<AccountData> expected = cpd2.getAccountData();
		expected.add(e);
		cpd2.addAccount(e);
		assertSame(expected,cpd2.getAccountData());
	}

	@Test
	public void testGetAllFriends() {
		ArrayList<GoogleTalkUserData> expected = new ArrayList<GoogleTalkUserData>();
		expected.add(new GoogleTalkUserData("rayan","ray","Busy"));
		expected.add(new GoogleTalkUserData("Abdulhalim","Hafez","away"));
		expected.add(new GoogleTalkUserData("John","J","online"));
		assertSame(expected,cpd3.getAllFriends());
		
	}

	@Test
	public void testGetAccountFromServer() {
		AccountData expected = new AccountData(ServerType.GOOGLE_TALK,"Michael","son");
		assertSame(expected,cpd3.getAccountFromServer(ServerType.GOOGLE_TALK));
	}

	@Test
	public void testRemoveFriend() {
		GoogleTalkUserData expected = new GoogleTalkUserData("Abdulhalim","Hafez","away");
		assertTrue(cpd3.removeFriend(expected));
	}

}
