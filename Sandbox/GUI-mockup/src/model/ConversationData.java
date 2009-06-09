package model;

import java.util.ArrayList;

/**
 * Holds all information represented in a conversation.
 */
public class ConversationData {
    
    /*
     * The other user that you are talking to, not yourself.
     */
    private UserData user;
    
    /*
     * Your own account data. Enclosed is a UserData class.
     */
    private AccountData account;
    
    /*
     * Chat history for just this session.
     */
    private ArrayList<MessageData> text;
    
    public ConversationData(AccountData account, UserData user) {
        this.user = user;
        this.account = account;
        this.text = new ArrayList<MessageData>();
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
        return;
    }
    
    public String displayMessages() {
        String messages = "";
        for (MessageData m : this.text) {
            messages += m.text();
        }
        return messages;
    }
}
