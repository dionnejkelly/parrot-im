/* GoogleTalkUserDataTest.java
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

import model.dataType.GoogleTalkUserData;
import model.enumerations.ServerType;
import model.enumerations.UserStateType;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GoogleTalkUserDataTest {
    private GoogleTalkUserData u1;
    private GoogleTalkUserData u2;
  //  private GoogleTalkUserData u3;

    @Before
    public void setUp() throws Exception {
        u1 = new GoogleTalkUserData("mac999", "mohd", "At home",UserStateType.ONLINE,false);
    }

    @After
    public void tearDown() throws Exception {
        u1 = null;
        u2 = null;
       // u3 = null;
    }

    public void testGoogleTalkUserDataStringStringString() {
        u2 = new GoogleTalkUserData("Win", "vista", "away",UserStateType.BUSY,false);
        assertSame("Win", u2.getUserID());
        assertSame("vista", u2.getNickname());
        assertSame("away", u2.getStatus());
        assertFalse(u2.isBlocked());
        assertSame(UserStateType.BUSY, u2.getState());
    }

   /* public void testGoogleTalkUserDataString() {
        u3 = new GoogleTalkUserData("Fahad");
        assertSame("Fahad", u3.getUserID());
        assertSame(u3.getUserID(), u3.getNickname());
        assertSame("", u3.getStatus());
        assertFalse(u3.isBlocked());
        //assertSame("Offline", u3.getState());
    }

    @Test
    public void testSetOnline() {
        boolean expected = true;
        u1.setOnline(true);
        assertSame(expected, u1.isOnline());
        u1.setOnline(false);
        assertNotSame(expected, u1.isOnline());
    }

    @Test
    public void testIsOnline() {
        u1.setOnline(false);
        assertFalse(u1.isOnline());
    }*/
    public void testGetServer(){
    	assertSame(ServerType.GOOGLE_TALK,u1.getServer());
    }

}
