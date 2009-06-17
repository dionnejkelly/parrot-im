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
 *         
 * Known Issues:
 *     1. Only functional for one user; multi-user chats are not
 *        implemented by this class.
 *     2. Not documented thoroughly.
 * 
 * Copyright (C) 2009  Pirate Captains
 * 
 * Full license can be found in ParrotIM/LICENSE.txt.
 */


package model.dataType;

import java.util.ArrayList;

/**
 * Holds all information represented in a conversation.
 */
public class ConversationData {
    
    /**
     * The other user that you are talking to, not yourself.
     */
    private UserData user;
    
    /**
     * Your own account data. Enclosed is a UserData class.
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
    
    public ConversationData(AccountData account, UserData user) {
        this.user = user;
        this.account = account;
        this.text = new ArrayList<MessageData>();
        this.messageCount = 0;
    }
    
    public UserData getUser() {
        return user;
    }
    
    public void setUser(UserData user) {
        this.user = user;
        return;
    }
    
    public AccountData getAccount() {
        return account;
    }
    
    public void setAccountData(AccountData account) {
        this.account = account;
    }
    
    public ArrayList<MessageData> getText() {
        return this.text;
    }
    
    public void addMessage(MessageData message) {
        this.text.add(message);
        this.messageCount++;
        return;
    }
    
    public int getMessageCount() {
        return this.messageCount;
    }
    
    public String displayMessages() {
        String messages = "";
        for (MessageData m : this.text) {
            messages += m.text();
        }
        return messages;
    }
    
}
