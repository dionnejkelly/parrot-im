/* ConversationData.java
 * 
 * Programmed By:
 *     Jihoon Choi
 *     Kevin Fahy
 *     
 *     Change Log:
 *     2009-July-15 JC, KF
 *         - First write.
 *         
 * Known Issues:
 *     none
 * 
 * Copyright (C) 2009  Pirate Captains
 * 
 * License: GNU General Public License version 2.
 * Full license can be found in ParrotIM/LICENSE.txt.
 */

package model.dataType;

import java.util.ArrayList;
import java.util.Observable;

public class MultiConversationData extends Observable implements Conversation {

    private String roomName;

    /**
     * The external user being talked to.
     */
    private ArrayList<UserData> users;

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
    public MultiConversationData(String roomName, AccountData account) {
        this.roomName = roomName;
        this.users = new ArrayList<UserData>();
        this.account = account;
        this.text = new ArrayList<MessageData>();
        this.messageCount = 0;
    }

    public String getRoomName() {
        return this.roomName;
    }

    public UserData getUser() {
        return this.users.size() >= 1 ? this.users.get(0) : null;
    }
    
    /**
     * Gets the UserData of the external user.
     * 
     * @return The external user in UserData form.
     */
    public ArrayList<UserData> getUsers() {
        return this.users;
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

    public UserData findUserByUserID(String userID) {
        UserData foundUser = null; // Default return value

        for (UserData u : this.users) {
            if (u.getUserID().equalsIgnoreCase(userID)) {
                foundUser = u;
                break;
            }
        }

        return foundUser;
    }

    public void addUser(UserData user) {

        if (!this.users.contains(user)) {
            this.users.add(user);
        }

        super.setChanged();
        super.notifyObservers();

        return;
    }

    public boolean removeUser(UserData user) {
        boolean removed = false;

        removed = this.users.remove(user);

        super.setChanged();
        super.notifyObservers();

        return removed;
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

        super.setChanged();
        super.notifyObservers();

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
        return messages;
    }

    public boolean equals(Object o) {
        boolean areEqual = false;
        MultiConversationData externalConversation = null;

        if (o != null && o instanceof MultiConversationData) {
            externalConversation = (MultiConversationData) o;
            areEqual = (this.roomName
                    .equals(externalConversation.getRoomName()) && this.account
                    .equals(externalConversation.getAccount()));
        }

        return areEqual;
    }

    @Override
    public int hashCode() {
        int hash = 7;

        hash = hash * 31 + this.roomName.hashCode();
        hash = hash * 31 + this.account.hashCode();

        return hash;
    }
}