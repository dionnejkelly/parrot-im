/* CurrentProfileDataTest.java
 * 
 * Programmed By:
 *     Rakan Alkheliwi
 *     
 * Change Log:
 *         
 * Known Issues:
 *     none
 *     
 * Copyright (C) 2009  Pirate Captains
 * 
 * License: GNU General Public License version 2.
 * Full license can be found in ParrotIM/LICENSE.txt.
 */

package test.unit;

import static org.junit.Assert.*;

import java.util.ArrayList;

import model.dataType.AccountData;
import model.dataType.ProfileData;
import model.dataType.GoogleTalkUserData;
import model.dataType.UserData;
import model.enumerations.ServerType;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CurrentProfileDataTest {
    private ProfileData cpd1;
    private ProfileData cpd2;
    private ProfileData cpd3;
    private ProfileData cpd4;
    private ProfileData cpd5;
    private ProfileData cpd6;

    @Before
    public void setUp() throws Exception {
        cpd1 =
                new ProfileData(new AccountData("Rakan", "1234"),
                        "Rakan Alkheliwi");
        ArrayList<AccountData> a1 = new ArrayList<AccountData>();
        a1.add(new AccountData("Monica", "mgs"));
        a1.add(new AccountData("Jeneifer", "gtasa"));
        cpd2 = new ProfileData(a1, "Goodfellas");
        ArrayList<AccountData> a2 = new ArrayList<AccountData>();
        AccountData e1 = new AccountData("Vito", "don");
        GoogleTalkUserData f1 = new GoogleTalkUserData("rayan", "ray", "Busy");
        UserData f2 = new GoogleTalkUserData("Abdulhalim", "Hafez", "away");
        UserData f3 = new GoogleTalkUserData("John", "J", "online");
        e1.addFriend(f1);
        a2.add(e1);
        AccountData e2 = new AccountData("Michael", "son");
        e2.addFriend(f2);
        e2.addFriend(f3);
        a2.add(e2);
        AccountData e3 = new AccountData("Clemanza", "fat");
        a2.add(e3);

        cpd3 = new ProfileData(a2, "Corleone");
        AccountData a3 = new AccountData("AlPacino", "insider");
        UserData g = new GoogleTalkUserData("Grand", "GTA", "busy");
        a3.addFriend(g);
        cpd4 = new ProfileData(a3, "Al Pacino");

    }

    @After
    public void tearDown() throws Exception {
        cpd1 = null;
        cpd2 = null;
        cpd3 = null;
        cpd4 = null;
        cpd5 = null;
        cpd6 = null;
    }

    @Test
    public void testCurrentProfileData() {
        cpd5 = new ProfileData();
        assertTrue(cpd5.getAccountData().isEmpty());
        assertSame("<empty>", cpd5.getProfileName());
        assertFalse(cpd5.isChatbotEnabled());
    }

    public void testCurrentProfileDataAccountDataString() {
        AccountData a3 = new AccountData("AlPacino", "insider");
        UserData g = new GoogleTalkUserData("Grand", "GTA", "busy");
        a3.addFriend(g);
        cpd6 = new ProfileData(a3, "Al Pacino");
        assertSame(g.getUserID(), cpd6.getAccountData().get(0)
                .getAccountName());
        assertSame("Al Pacino", cpd6.getProfileName());
        assertFalse(cpd6.isChatbotEnabled());

    }

    public void testSetAccountData() {
        ArrayList<AccountData> expected = new ArrayList<AccountData>();
        expected.add(new AccountData("Rakan", "1234"));
        cpd1.setAccountData(expected);
        assertSame(cpd1.getAccountData(), expected);
    }

    @Test
    public void testGetAccountData() {
        ArrayList<AccountData> expected = new ArrayList<AccountData>();
        expected.add(new AccountData("Monica", "mgs"));
        expected.add(new AccountData("Jeneifer", "gtasa"));
        for (int i = 0; i < expected.size(); i++) {
            assertSame(expected.get(i).getAccountName(), cpd2.getAccountData()
                    .get(i).getAccountName());
            assertSame(expected.get(i).getServer(), cpd2.getAccountData()
                    .get(i).getServer());
            assertSame(expected.get(i).getPassword(), cpd2.getAccountData()
                    .get(i).getPassword());

        }
    }

    @Test
    public void testSetProfileName() {
        String expected = "The_Godfather";
        cpd2.setProfileName("The_Godfather");
        assertSame(expected, cpd2.getProfileName());
    }

    @Test
    public void testGetProfileName() {
        String expected = "Rakan Alkheliwi";
        assertSame(expected, cpd1.getProfileName());
    }

    @Test
    public void testToString() {
        String expected = "Corleone";
        assertSame(expected, cpd3.toString());
    }

    @Test
    public void testAddAccount() {

        AccountData e = new AccountData("Rachel", "freinds");
        ArrayList<AccountData> expected = cpd2.getAccountData();
        expected.add(e);
        cpd2.addAccount(e);
        assertSame(expected, cpd2.getAccountData());
    }

    @Test
    public void testGetAllFriends() {
        ArrayList<GoogleTalkUserData> expected =
                new ArrayList<GoogleTalkUserData>();
        expected.add(new GoogleTalkUserData("rayan", "ray", "Busy"));
        expected.add(new GoogleTalkUserData("Abdulhalim", "Hafez", "away"));
        expected.add(new GoogleTalkUserData("John", "J", "online"));
        ArrayList<UserData> result = new ArrayList<UserData>();
        result = cpd3.getAllFriends();
        for (int i = 0; i < expected.size(); i++) {
            assertSame(expected.get(i).getUserID(), result.get(i)
                    .getUserID());
            assertSame(expected.get(i).getNickname(), result.get(i)
                    .getNickname());
            assertSame(expected.get(i).getStatus(), result.get(i).getStatus());
        }

    }

    @Test
    public void testGetAccountFromServer() {
        AccountData expected = new AccountData("Michael", "son");
        assertSame(expected.getServer(), cpd3.getAccountFromServer(
                ServerType.GOOGLE_TALK).getServer());
        assertSame(expected.getAccountName(), cpd3.getAccountFromServer(
                ServerType.GOOGLE_TALK).getAccountName());
        assertSame(expected.getPassword(), cpd3.getAccountFromServer(
                ServerType.GOOGLE_TALK).getPassword());

    }

    @Test
    public void testRemoveFriend() {
        UserData expected = new GoogleTalkUserData("Grand", "GTA", "busy");

        assertTrue(cpd4.removeFriend(expected));
    }
}
