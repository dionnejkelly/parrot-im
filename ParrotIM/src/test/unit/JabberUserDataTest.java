/* JabberUserDataTest.java
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

import model.dataType.JabberUserData;
import model.enumerations.ServerType;
import model.enumerations.UserStateType;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class JabberUserDataTest {
    private JabberUserData jabber1;
    private JabberUserData jabber2;
    private JabberUserData jabber3;

    @Before
    public void setUp() throws Exception {
    	jabber3 = new JabberUserData("Khalid","K","Back home",UserStateType.ONLINE,false);
    }

    @After
    public void tearDown() throws Exception {
        jabber1 = null;
        jabber2 = null;
    }

    @Test
    public void testJabberUserDataString() {
        String expected = "Rakan";
        jabber1 = new JabberUserData(expected);
        assertSame(expected, jabber1.getUserID());
    }

    @Test
    public void testJabberUserDataStringStringString() {
        String expected1 = "rma";
        String expected2 = "ray";
        String expected3 = "away";
        jabber2 = new JabberUserData(expected1, expected2, expected3,UserStateType.AWAY,true);
        assertSame(expected1, jabber2.getUserID());
        assertSame(expected2, jabber2.getNickname());
        assertSame(expected3, jabber2.getStatus());
        assertSame(UserStateType.AWAY,jabber2.getState());
        assertTrue(jabber2.isBlocked());
        assertSame(ServerType.JABBER,jabber2.getServer());

    }
   
    
    public void testGetServer(){
    	assertSame(ServerType.JABBER,jabber3.getServer());
    }

}
