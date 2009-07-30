/* ConversationData.java
 * 
 * Programmed By:
 *     Kevin Fahy
 *     William Chen
 *     
 *     Change Log:
 *     2009-June-9, KF
 *         Initial write. Holds all data for a specific conversation with
 *         one person.
 *     2009-June-13, WC
 *         Moved over to ParrotIM project.
 *     2009-June-24, KF
 *         Completed JavaDoc documentation of the class.
 *         
 * Known Issues:
 *     1. Only functional for one user; multi-user chats are not
 *        implemented by this class.
 * 
 * Copyright (C) 2009  Pirate Captains
 * 
 * License: GNU General Public License version 2.
 * Full license can be found in ParrotIM/LICENSE.txt.
 */

package model.dataType;

import java.util.ArrayList;
import java.util.Observable;

/**
 * Holds all information represented in a conversation. Typically, each
 * ConversationData object represents a new chat. When the number of or status
 * of an object changes, the GUI should update accordingly.
 */
public class ConversationData extends Observable implements Conversation {

    /**
     * The external user being talked to.
     */
    private UserData user;

    /**
     * The local user, classified by AccountData.
     */
    private AccountData account;

    /**
     * Chat history for just this session.
     */
    private ArrayList<MessageData> text;

    /**
     * Counts how many messages are currently in the conversation.
     */
    private int messageCount;

    /**
     * Creates a new empty conversation with both users set.
     * 
     * @param account
     *            The local user.
     * @param user
     *            The external user.
     */
    public ConversationData(AccountData account, UserData user) {
        this.user = user;
        this.account = account;
        this.text = new ArrayList<MessageData>();
        this.messageCount = 0;
    }

    /**
     * Gets the UserData of the external user.
     * 
     * @return The external user in UserData form.
     */
    public UserData getUser() {
        return user;
    }

    /**
     * Changes the external user of the conversation.
     * 
     * @param user
     */
    public void setUser(UserData user) {
        this.user = user;
        return;
    }

    /**
     * Gets the local user of the conversation.
     * 
     * @return AccountData object of the local user.
     */
    public AccountData getAccount() {
        return account;
    }

    /**
     * Changes the local user.
     * 
     * @param account
     */
    public void setAccountData(AccountData account) {
        this.account = account;
    }

    /**
     * Get all messages from the conversation.
     * 
     * @return All messages. Each is a MessageData object inside of an
     *         ArrayList.
     */
    public ArrayList<MessageData> getText() {
        return this.text;
    }

    /**
     * Add a message to the conversation. Useful for when sending or receiving a
     * new message. This method can be called to update the conversation.
     * 
     * @param message
     */
    public void addMessage(MessageData message) {
        this.text.add(message);
        this.messageCount++;

        setChanged();
        notifyObservers();

        return;
    }

    /**
     * Gets the number of messages in the conversation.
     * 
     * @return The message count of the conversation.
     */
    public int getMessageCount() {
        return this.messageCount;
    }

    /**
     * Returns a string holding all messages.
     * 
     * @return All messages into a string.
     */
    public String displayMessages() {
        String messages = "";
        for (MessageData m : this.text) {
            messages += m.text();
        }
        return "<table width='300' border='0' cellpadding='0' cellspacing='0'" +
        		"> <tr><td style='word-break:break-all;'>"+messages+"</td>" +
        				"</tr></table>";
    }

    public boolean equals(Object o) {
        boolean areEqual = false;
        ConversationData externalConversation = null;

        if (o != null && o instanceof ConversationData) {
            externalConversation = (ConversationData) o;
            areEqual =
                    (this.user.equals(externalConversation.getUser()) && this.account
                            .equals(externalConversation.getAccount()));
        }

        return areEqual;
    }

    public int hashCode() {
        int hash = 7;

        hash = hash * 31 + this.user.hashCode();
        hash = hash * 31 + this.account.hashCode();

        return hash;
    }
}