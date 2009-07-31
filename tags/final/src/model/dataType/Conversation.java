package model.dataType;

import java.util.ArrayList;

public interface Conversation {

    public UserData getUser();

    public AccountData getAccount();

    public ArrayList<MessageData> getText();

    public void addMessage(MessageData message);

    public int getMessageCount();

    public String displayMessages();
}
