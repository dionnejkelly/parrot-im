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
 *         
 * Known Issues:
 *     1. Difficult for chatlog to get information about receiver,
 *        and a MessageData does not hold this information. 
 *     2. Not documented thoroughly.
 * 
 * Copyright (C) 2009  Pirate Captains
 * 
 * Full license can be found in ParrotIM/LICENSE.txt.
 */


package model.dataType;

public class MessageData {

    private String fromUser;
    private String message;
    private String font;
    private String size;
    
    public MessageData(String fromUser, String message, String font, 
                       String size) {
        this.fromUser = fromUser;
        this.message = message;
        this.font = font;
        this.size = size;
    }
    
    public String getFromUser() {
        return this.fromUser;
    }
    
    public String getMessage() {
        return this.message;
    }
    
    public String getFont() {
        return this.font;
    }
    
    public String getSize() {
        return this.size;
    }
    
    public String text() {
        String text = "<U>" + this.fromUser + ":</U> " + "<font face=\"" 
                      + this.font + "\" size=\"" + this.size + "\">" 
                      + this.message + "</font><br><br>";
        return text;
    }
    
    public String plainText() {
        String plainText = this.fromUser + ":" + this.message + "\n";
        return plainText;
    }
    
    @Override
    public String toString() {
        return text();
    }
}
