/* MessageDataTest.java
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

import java.text.SimpleDateFormat;
import java.util.Date;

import model.dataType.MessageData;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MessageDataTest {
    private MessageData md1;
    private MessageData md2;

    @Before
    public void setUp() throws Exception {
        md1 =
                new MessageData(
                        "Gina", "Hey, are you coming?", "Arial",
                        "4", false, false, false, "#FFFFFF",true);

    }

    @After
    public void tearDown() throws Exception {
        md1 = null;
        md2 = null;
    }

    @Test
    public void testMessageData() {
        md2 =
                new MessageData(
                        "Niko", "What did you say?", "Arial", "4", false,
                        false, false, "#FFFFFF",true);
        assertSame("Niko", md2.getFromUser());
        assertSame("What did you say?", md2.getMessage());
        assertSame("Arial", md2.getFont());
        assertSame("4", md2.getSize());
     
    }

    @Test
    public void testGetFromUser() {
        String expected = "Gina";
        assertSame(expected, md1.getFromUser());
    }

    @Test
    public void testGetMessage() {
        String expected = "Hey, are you coming?";
        assertSame(expected, md1.getMessage());
    }

    @Test
    public void testGetFont() {
        String expected = "Arial";
        assertSame(expected, md1.getFont());
    }

    @Test
    public void testGetSize() {
        String expected = "4";
        assertSame(expected, md1.getSize());
    }

    @Test
    public void testSetToUser() {
        String expected = "Rakan";
        md1.setToUser(expected);
        assertSame(expected, md1.getToUser());
    }

    @Test
    public void testGetToUser() {
        String expected = "Jack";
        md1.setToUser(expected);
        assertSame(expected, md1.getToUser());
    }

    @Test
    public void testText() {
    	Date date1 = new Date();
        String etimeStamp = new SimpleDateFormat("HH:mm--").format(date1);
        String expected =
        	etimeStamp+"<U><font face = \"Arial"
			 +"\" color=\"" + "#FFFFFF" + "\">Gina:</font></U><font face=\"Arial\" "+"size=\"4\" color=\"#FFFFFF\">" 
            + " Hey, are you coming?" + " </font><br><br>";
        
        assertEquals(expected, md1.text());
        //System.out.println(expected);
        //System.out.println(md1.text());
    }

    @Test
    public void testPlainText() {
        String expected = md1.getFromUser() + ":" + md1.getMessage() + "\n";
        assertEquals(expected, md1.plainText());
    }

    @Test
    public void testToString() {
    	Date date1 = new Date();
        String etimeStamp = new SimpleDateFormat("HH:mm--").format(date1);
        String expected =
        	etimeStamp+"<U><font face = \"Arial"
			 +"\" color=\"" + "#FFFFFF" + "\">Gina:</font></U><font face=\"Arial\" "+"size=\"4\" color=\"#FFFFFF\">" 
            + " Hey, are you coming?" + " </font><br><br>";
        assertEquals(expected, md1.toString());
        
    }

}
