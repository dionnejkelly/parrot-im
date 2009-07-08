package view.chatwindow;

import java.util.Observable;
import java.util.Observer;

import model.Model;
import model.dataType.ConversationData;
import model.enumerations.UpdatedType;

public class UserDataWrapper implements Observer {

    private ConversationData conversation;

    private int readMessages;

    private Model model;

    public UserDataWrapper(ConversationData conversation, Model model) {
        this.conversation = conversation;
        this.readMessages = 0;
        this.model = model;
        this.model.addObserver(this);
    }

    public ConversationData getConversation() {
        return this.conversation;
    }

    public String toString() {
        String message = "";
        if (this.conversation.getMessageCount() - this.readMessages > 0) {
            message +=
                    "("
                            + (this.conversation.getMessageCount() - this.readMessages)
                            + ") ";
        }
        message += this.conversation.getUser().getNickname();

        return message;
    }

    public void update(Observable o, Object arg) {
        if (arg == UpdatedType.CHAT) {
            if (model.getActiveConversation() == this.conversation) {
                this.readMessages = this.conversation.getMessageCount();
            }
        }

        return;
    }
}
