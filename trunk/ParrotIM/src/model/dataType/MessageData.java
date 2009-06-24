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
     * The content of the message in string fromat.
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
    public MessageData(String fromUser, String message, String font, String size) {
        this.fromUser = fromUser;
        this.message = message;
        this.font = font;
        this.size = size;
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
        String text =
                "<U>"
                        + this.fromUser + ":</U> " + "<font face=\""
                        + this.font + "\" size=\"" + this.size + "\">"
                        + this.message + "</font><br><br>";

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
    @Override
    public String toString() {
        return text();
    }
}