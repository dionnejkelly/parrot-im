/* MessageData.java
 * 
 * Programmed By:
 *     Kevin Fahy
 *     William Chen
 *     
 * Change Log:
 *     2009-June-9, KF
 *         Initial write. Holds all data pertaining to an individual
 *         message in a conversation.
 *     2009-June-13, WC
 *         Moved over to ParrotIM project.
 *     2009-June-24, KF
 *         Added capability to set who the message is being
 *         received by. Added JavaDoc documentation.
 *         
 * Known Issues:
 *     1. Strings are being used to hold all data. It may be possible
 *        to make objects store the data. Need to check the usage
 *        requirements of these MessageData objects.
 *     2. Text transformation into HTML form is done in this object, 
 *        and not the GUI. Could be a design inconsistency; how does
 *        this datatype know what format to output?
 *     
 * Copyright (C) 2009  Pirate Captains
 * 
 * License: GNU General Public License version 2.
 * Full license can be found in ParrotIM/LICENSE.txt.
 */

package model.dataType;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Holds message data from a conversation. Each MessageData object represents
 * one message from a user.
 */
public class MessageData {

    // Section
    // I - Data Members

    /**
     * Who the message was sent from. Stored as a String. Recommended to be the
     * userID.
     */
    private String fromUser;

    /**
     * Who the message was sent to. Stored as a String. Recommended to be the
     * userID.
     */
    private String toUser;

    /**
     * The content of the message in string format.
     */
    private String message;

    /**
     * The font, represented as a String.
     */
    private String font;

    /**
     * The size of the text, represented as a String.
     */
    private String size;

    /** These are the font settings as set by the
     * corresponding buttons on the chatPanel
     */
    private boolean bold, italics, underlined;
    
    /** This is the users font color as a string
     * of 6-digit hex codes ("#RRGGBB)
     */
    private String fontColor;
    
    private String timeStamp;
    
    private boolean isReceiving;
    
    /** String[][] emoticons is a 2-dimensional array of strings with
	 * the first string being the regular expression of shortcuts which
	 * will be replaced by an image, and the second string is the file
	 * name of the image located within the "/images/emoticons/" folder
	 */
    private String[][] emoticons = {{"([^oO0\\<](=|:)[ ]*-?[ ]*([)]|]))|([(][ ]*-?[ ]*(:|=)[^oO0\\>])", "happy.png"},
									{"((:|=)[ ]*-?([(]|c|C))|(([)]|])[ ]*-?[ ]*(:|=))", "sad.png"},
									{"((:|=)-?[|])|([|]-?(:|=))", "neutral.png"},
									{"(:[ ]*-?[ ]*D)", "joy.png"},
									{"((:|=)[ ]*-?[ ]*(X|x|#))|((X|x|#)[ ]*-?[ ]*(:|=))", "zipper.png"},
									{"((X|x)[ ]*-?[ ]*D)", "laugh.png"},
									{"(B[ ]*-?[ ]*[)])", "cool.png"},
									{"((:|=)[ ]*-?[ ]*(S|s))|((S|s)[ ]*-?[ ]*(:|=))", "sick.png"},
									{"(8[ ]*-?[ ]*[)])", "glasses.png"},
									{"((X|x)[ ]*-?[ ]*(P|p|b))|(q[ ]*-?[ ]*(X|x))", "dead.png"},
									{"(;[ ]*-?[ ]*[)])|([(][ ]*-?[ ]*;)", "wink.png"},
									{"([^o0O(][ ]*:[ ]*-?[ ]*(0|O|o))|((0|O|o)[ ]*-?[ ]*:[ ]*[^o0O)])", "surprise.png"},
									{"(=[ ]*-?[ ]*(0|O|o))|((0|O|o)[ ]*-?[ ]*=)", "afraid.png"},
									{"((:|=)[ ]*-?[ ]*(P|p|b))|((q|d)[ ]*-?[ ]*(:|=))", "tongue.png"},
									{"<3", "heart.png"}, {"</3", "brokenheart.png"},
									{"((o|0|O)[ ]*:[ ]*-?[ ]*[)])", "angel.png"},
									{"(<[ ]*(:|=)[ ]*-?[ ]*[)])|([(][ ]*-?[ ]*(:|=)[ ]*>)", "party.png"}};
    
    // Section
    // II - Constructors

    /**
     * Constructor for all data except the toUser.
     * 
     * @param fromUser
     * @param message
     * @param font
     * @param size
     */
    public MessageData(String fromUser, String message, String font, String size,
    		boolean bold, boolean italics, boolean underlined, String color, boolean receiving) {
        this.fromUser = fromUser;
        this.message = message;
        this.font = font;
        this.size = size;
        this.bold = bold;
        this.italics = italics;
        this.underlined = underlined;
        this.fontColor = color;
        this.isReceiving = receiving;
        
        Date date1 = new Date();
        timeStamp = new SimpleDateFormat("HH:mm--").format(date1);
    }

    public MessageData(String fromUser, String message) {
        this.fromUser = fromUser;
        this.message = message;
        
        Date date1 = new Date();
        timeStamp = new SimpleDateFormat("HH:mm--").format(date1);
    }
    
    // Section
    // III - Accessors and Mutators

    /**
     * Gets who the message was sent from. Could be the userID, but no
     * guarantee.
     * 
     * @return A string reprsenting the sender.
     */
    public String getFromUser() {
        return this.fromUser;
    }

    /**
     * Gets the message content.
     * 
     * @return The text of the message.
     */
    public String getMessage() {
        return this.message;
    }

    /**
     * Gets the font of the message.
     * 
     * @return Font of the message.
     */
    public String getFont() {
        return this.font;
    }

    /**
     * Gets the font size.
     * 
     * @return The size of the text of the message.
     */
    public String getSize() {
        return this.size;
    }

    /**
     * Changes who the message is sent to.
     * 
     * @param toUser
     */
    public void setToUser(String toUser) {
        this.toUser = toUser;

        return;
    }

    /**
     * Gets the recipient of the message.
     * 
     * @return A String of the receiver of the message.
     */
    public String getToUser() {
        return toUser;
    }

    // Section
    // IV - Message Access

    /**
     * Returns the HTML version of the message.
     * 
     * @return The message with HTML tags attached for display.
     */
    public String text() {
        String toUserText = timeStamp + "<U><font face = \"Arial\">"
            			+ this.fromUser + ":</font></U>";
           
        if (!isReceiving) {
        	System.out.println("Never getting called...");
        	toUserText = timeStamp + "<U><font face = \"Arial" + "\" color=\"" + "#ff00ff"
            + "\">" + this.fromUser + ":</font></U>";
        }
        
        else {
        	toUserText = timeStamp + "<U><font face = \"Arial" + "\" color=\"" + fontColor
            + "\">" + this.fromUser + ":</font></U>";
        }
        
        
        
        
        String text =   "<font face=\"" + this.font + "\" size=\"" + this.size +
        				"\" color=\"" + fontColor + "\"> " 
                        + this.message + " </font><br><br>";
        
        if(this.bold){ 
        	text = "<b>" + text + "</b>"; 
        }
        if(this.italics){ 
        	text = "<i>" + text + "</i>"; 
        }
        if(this.underlined){ 
        	text = "<u>" + text + "</u>"; 
        }
        text = toUserText + text;
        text = addEmoticons(text);
        return text;
    }
    
    /**
     * Searches for matches to emoticons using regular expressions and
     * replaces with HTML tag for the emoticon
     *  
     * @param text
     * @return The text with HTML tags for the emoticons added.
     */
    private String addEmoticons(String text){
    	for(int i = 0; i < emoticons.length; i++){
	    	text = text.replaceAll(emoticons[i][0], "<img src=\"" + this.getClass().getResource(
	        "/images/emoticons/" + emoticons[i][1]).toString() + "\" alt=\":)\" />");
    	}
    	return text;
    }

    /**
     * Returns the message attached with who it's from.
     * 
     * @return The plain text version of the message without the HTML tags
     */
    public String plainText() {
        String plainText = this.fromUser + ":" + this.message + "\n";
        return plainText;
    }

    /**
     * Converts the message to a string.
     * 
     * @return The message text.
     */
    public String toString() {
        return text();
    }
}