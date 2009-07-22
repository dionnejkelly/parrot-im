package view.chatwindow;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;

import model.Model;
import model.dataType.Conversation;
import model.dataType.ConversationData;
import model.dataType.MultiConversationData;
import model.enumerations.UpdatedType;

public class UserDataWrapper implements Observer {

    private Conversation conversation;

    private int readMessages;

    private Model model;

    private JLabel labelRepresentation;

    public UserDataWrapper(Conversation conversation, Model model) {
        this.conversation = conversation;
        this.readMessages = 0;
        this.model = model;
        this.model.addObserver(this);
        this.labelRepresentation = new JLabel("  " + this.toString());
    }

    public Conversation getConversation() {
        return this.conversation;
    }

    public JLabel getLabelRepresentation() {
    	String profileText = this.conversation.getUser().getNickname() + "(" 
    						+ this.conversation.getUser().getUserID() + ")";
    						//+ "Status: " + this.conversation.getUser().getStatus() + "\n"
    						//+ "Server: " + this.conversation.getUser().getServer() + "\n"
    						//+ "------------------------------\n"
    						//+ "(Right-click for more options)";
    	this.labelRepresentation.setToolTipText(profileText);
        return this.labelRepresentation;
    }

    public String toString() {
        String message = "";
        if (this.conversation.getMessageCount() - this.readMessages > 0) {
            message += "("
                    + (this.conversation.getMessageCount() - this.readMessages)
                    + ") ";
        }
        if (this.conversation instanceof ConversationData) {
        	String displayedName = this.conversation.getUser().getNickname();
        	if(displayedName == null || displayedName.trim().equals("")){
        		displayedName = this.conversation.getUser().getUserID();
        	}
        	if(displayedName.length() <= 13){
        		message += displayedName;
        	}else{
        		message += displayedName.substring(0, 13);
        		message += "...";
        	}
            
            String typingState = this.conversation.getUser().getTypingState();
            if (!typingState.equals("")) {
                message += "\n (" + typingState + ")";
            }
            message += "\n " + this.conversation.getUser().getTypingState();
        } else if (this.conversation instanceof MultiConversationData) {
            message += ((MultiConversationData) this.conversation).getRoomName();
        }     
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
