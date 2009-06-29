/* ChatLogMessageTempData.java
 * 
 * Programmed By:
 *     Kevin Fahy
 *     
 * Change Log:
 *     2009-June-22, KF
 *         Initial write. Created for use of database to pass back partial
 *         chat log message data to the controller.
 *     2009-June-23, KF
 *         Completed JavaDoc documentation.
 *         
 * Known Issues:
 *     none
 *     
 * Copyright (C) 2009  Pirate Captains
 * 
 * License: GNU General Public License version 2.
 * Full license can be found in ParrotIM/LICENSE.txt.
 */

package model.dataType.tempData;

/**
 * Holds chat log message data from the database and transfers
 * it to other parts of the program.
 */
public class ChatLogMessageTempData {
    
    /**
     * The time the message was sent. Does not include the date.
     */
    private String time;
    
    /**
     * Who the message is being sent to. May not be used for
     * display in the chat log.
     */
    private String from;
    
    /**
     * Who the message was sent from. The database is set
     * to hold the userID of who it was sent from. This type
     * does not change it to the nickname.
     */
    private String to;
    
    /**
     * The actual content of the message in string format.
     */
    private String text;
    
    /**
     * Creates an object with all parameters.
     * 
     * @param time
     * @param from
     * @param to
     * @param text
     */
    public ChatLogMessageTempData(String time, String from, String to,
            String text) {
        this.time = time;
        this.from = from;
        this.to = to;
        this.text = text;
    }

    /**
     * Gets the time the message was sent.
     * 
     * @return The time in String format.
     */
    public String getTime() {
        return time;
    }

    /**
     * Sets the time for the sent message.
     * 
     * @param time
     */
    public void setTime(String time) {
        this.time = time;      
        return;
    }

    /**
     * Gets who the message was sent from.
     * 
     * @return The userID of who sent the message in String.
     */
    public String getFrom() {
        return from;
    }

    /**
     * Changes who the message is from.
     * 
     * @param from
     */
    public void setFrom(String from) {
        this.from = from;
        return;
    }

    /**
     * Gets who the message was sent to.
     * 
     * @return The userID of the message recipient.
     */
    public String getTo() {
        return to;
    }

    /**
     * Sets who the message was sent to.
     * 
     * @param to
     */
    public void setTo(String to) {
        this.to = to;
        return;
    }

    /** 
     * Gets the content of the message.
     * 
     * @return A string of the message text.
     */
    public String getText() {
        return text;
    }

    /**
     * Change the message text.
     * 
     * @param text
     */
    public void setText(String text) {
        this.text = text;
        return;
    }
    
    /**
     * Returns string.
     * 
     * @return the friend's account, the date, and the message
     */
    
    public String toString(){
		return getFrom() + " (" + getTime() + "): " + getText() + "\n";
    	
    }
}