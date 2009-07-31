/* ServerTypeTest.java
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import model.enumerations.ServerType;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ServerTypeTest {
    private ServerType server1;

    @Before
    public void setUp() throws Exception {
        server1 = ServerType.ICQ;
    }

    @After
    public void tearDown() throws Exception {
        server1 = null;
    }

    @Test
    public void testNumberOfValues() {
        int expected = 6;
        assertSame(expected, ServerType.numberOfValues());
    }

    @Test
    public void testToString() {
        String expected = "ICQ";
        assertSame(expected, server1.toString());
    }

    @Test
    public void testGetServerList() {
        String[] expected = new String[6];
        expected[0] = "Jabber";
        expected[1] = "Google Talk";
        expected[2] = "Twitter";
        expected[3] = "ICQ";
        expected[4] = "MSN";
        expected[5] = "AIM";
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i].toString(), ServerType.getServerList()
                    .get(i).toString());
        }

    }

}
