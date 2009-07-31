/* ChatlogMessageTempDataTest.java
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

import static org.junit.Assert.assertSame;
import model.dataType.tempData.ChatLogMessageTempData;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ChatlogMessageTempDataTest {
    private ChatLogMessageTempData cl1;
    private ChatLogMessageTempData cl2;
    private ChatLogMessageTempData cl3;

    @Before
    public void setUp() throws Exception {
        cl1 =
                new ChatLogMessageTempData("Thur13:00", "Ray", "Telia",
                        "Clean it up!");
        cl2 =
                new ChatLogMessageTempData("Fri1:17", "Vito", "Bonasera",
                        "So what do you want?");
    }

    @After
    public void tearDown() throws Exception {
        cl1 = null;
        cl2 = null;
        cl3 = null;
    }

    @Test
    public void testChatLogMessageTempData() {
        cl3 =
                new ChatLogMessageTempData("Sun16:20", "Clemenza", "Michael",
                        "Your father was proud of you");
        assertSame("Sun16:20", cl3.getTime());
        assertSame("Clemenza", cl3.getFrom());
        assertSame("Michael", cl3.getTo());
        assertSame("Your father was proud of you", cl3.getText());

    }

    @Test
    public void testGetTime() {
        String expected = "Thur13:00";
        assertSame(expected, cl1.getTime());
    }

    @Test
    public void testSetTime() {
        String expected = "Sat15:18";
        cl2.setTime(expected);
        assertSame(expected, cl2.getTime());
    }

    @Test
    public void testGetFrom() {
        String expected = "Ray";
        assertSame(expected, cl1.getFrom());
    }

    @Test
    public void testSetFrom() {
        String expected = "Bonasera";
        cl2.setFrom(expected);
        assertSame(expected, cl2.getFrom());
    }

    @Test
    public void testGetTo() {
        String expected = "Telia";
        assertSame(expected, cl1.getTo());
    }

    @Test
    public void testSetTo() {
        String expected = "Vito";
        cl2.setTo(expected);
        assertSame(expected, cl2.getTo());
    }

    @Test
    public void testGetText() {
        String expected = "Clean it up!";
        assertSame(expected, cl1.getText());
    }

    @Test
    public void testSetText() {
        String expected = "I want Justice!";
        cl2.setText(expected);
        assertSame(expected, cl2.getText());
    }

}
