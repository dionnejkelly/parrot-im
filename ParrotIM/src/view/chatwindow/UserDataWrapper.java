package view.chatwindow;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;

import model.Model;
import model.dataType.ConversationData;
import model.enumerations.UpdatedType;

public class UserDataWrapper implements Observer {

    private ConversationData conversation;

    private int readMessages;

    private Model model;
    
    private JLabel labelRepresentation;

    public UserDataWrapper(ConversationData conversation, Model model) {
        this.conversation = conversation;
        this.readMessages = 0;
        this.model = model;
        this.model.addObserver(this);
        this.labelRepresentation = new JLabel("  " + this.toString());
    }

    public ConversationData getConversation() {
        return this.conversation;
    }
    
    public JLabel getLabelRepresentation() {
        return this.labelRepresentation;
    }

    public String toString() {
        String message = "";
        if (this.conversation.getMessageCount() - this.readMessages > 0) {
            message +=
                    "("
                            + (this.conversation.getMessageCount() - this.readMessages)
                            + ") ";
        }
        message += this.conversation.getUser().getUserID();
        String typingState = this.conversation.getUser().getTypingState();
//        if (this.conversation!=model.getActiveConversation()){
	        if(!typingState.equals("")){
	        	message += "\n ("+typingState+")";
	        }
	        message += "\n "+this.conversation.getUser().getTypingState();
//        }
	        
        return message;
    }

    public void update(Observable o, Object arg) {
        if (arg == UpdatedType.CHAT) {
            if (model.getActiveConversation() == this.conversation) {
                this.readMessages = this.conversation.getMessageCount();
            }
            this.labelRepresentation.setText("  " + this.toString());
        }

        return;
    }
}
