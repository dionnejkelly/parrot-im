/* AccountTempDataTest.java
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

import model.dataType.tempData.AccountTempData;
import model.enumerations.ServerType;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
	// Done
public class AccountTempDataTest {
    private AccountTempData ad1;
    private AccountTempData ad2;
    private AccountTempData ad3;
    private AccountTempData ad4;

    @Before
    public void setUp() throws Exception {
        ad1 = new AccountTempData(ServerType.GOOGLE_TALK.toString(), "", "Rakan", "1234");
        ad2 = new AccountTempData(ServerType.ICQ.toString(), "", "Ross", "abc");
        ad3 = new AccountTempData(ServerType.MSN.toString(), "", "Janice", "ohmygod");
        ad4 = new AccountTempData(ServerType.TWITTER.toString(), "", "Joey", "Friends");

    }

    @After
    public void tearDown() throws Exception {
        ad1 = null;
        ad2 = null;
        ad3 = null;
        ad4 = null;
    }

    @Test
    public void testAccountTempData() {
        ad4 = new AccountTempData(ServerType.ICQ.toString(), "", "James", "007");
        assertSame(ServerType.ICQ, ad4.getServer());
        assertSame("James", ad4.getUserID());
        assertSame("007", ad4.getPassword());
    }

    @Test
    public void testSetServer() {
        AccountTempData expected =
                new AccountTempData(ServerType.JABBER.toString(), "", "Rakan", "1234");
        ad1.setServer(ServerType.JABBER);
        assertSame(expected.getServer(), ad1.getServer());
    }

    @Test
    public void testGetServer() {
        ServerType expected = ServerType.ICQ;
        assertSame(expected, ad2.getServer());

    }

    @Test
    public void testSetUserID() {
        ad2.setUserID("Chandler");
        AccountTempData expected =
                new AccountTempData(ServerType.ICQ.toString(), "", "Chandler", "abc");
        assertSame(expected.getUserID(), ad2.getUserID());
    }

    @Test
    public void testGetUserID() {
        String expected = "Ross";
        assertSame(expected, ad2.getUserID());
    }

    @Test
    public void testSetPassword() {
        ad3.setPassword("qwerty");
        AccountTempData expected =
                new AccountTempData(ServerType.MSN.toString(), "", "Janice", "qwerty");
        assertSame(expected.getPassword(), ad3.getPassword());
    }

    @Test
    public void testGetPassword() {
        String expected = "abc";
        assertSame(expected, ad2.getPassword());
    }

}
