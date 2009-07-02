/* ChatCollectionData.java
 * 
 * Programmed By:
 *     Kevin Fahy
 *     
 * Change Log:
 *     2009-July-1, KF
 *         First write. Manages all conversations, including tracking which
 *         conversation is currently active.
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

public class ChatCollectionData {

    /**
     * Holds a collection of all conversations. If no conversations are
     * currently active, the collection is simply empty.
     */
    private ArrayList<ConversationData> conversations;

    private ArrayList<ConversationData> hiddenConversations;

    /**
     * Holds a reference to the current active conversation. This conversation
     * should be contained inside data member, conversations.
     */
    private ConversationData activeConversation;

    public ChatCollectionData() {
        this.conversations = new ArrayList<ConversationData>();
        this.hiddenConversations = new ArrayList<ConversationData>();
        this.activeConversation = null;
    }

    public ArrayList<ConversationData> getConversations() {
        return conversations;
    }

    public void setConversations(ArrayList<ConversationData> conversations) {
        this.conversations = conversations;
    }

    public ArrayList<ConversationData> getHiddenConversations() {
        return hiddenConversations;
    }

    public void setHiddenConversations(
            ArrayList<ConversationData> hiddenConversations) {
        this.hiddenConversations = hiddenConversations;
    }

    public void setActiveConversation(ConversationData activeConversation) {
        this.addConversation(activeConversation);
        this.activeConversation = activeConversation;

        return;
    }

    public ConversationData getActiveConversation() {
        return activeConversation;
    }

    // Addition/Removal methods

    public void addConversation(ConversationData conversation) {
        if (!this.conversations.contains(conversation)) {
            this.conversations.add(conversation);
            if (conversations.size() == 1) {
                this.activeConversation = conversation;
            }
            if (this.hiddenConversations.contains(conversation)) {
                this.hiddenConversations.remove(conversation);
            }
        }

        return;
    }

    public boolean removeConversation(ConversationData conversation) {
        boolean removed = false;

        if (this.conversations.contains(conversation)) {
            this.conversations.remove(conversation);
            this.hiddenConversations.add(conversation);
            removed = true;
            this.activeConversation =
                    this.conversations.isEmpty() ? null : this.conversations
                            .get(0);
        }

        return removed;
    }

    public void hideAllConversations() {
        this.hiddenConversations.addAll(this.conversations);
        this.conversations.clear();
        this.activeConversation = null;

        return;
    }

    // Information Methods
    public ArrayList<ConversationData> getAllConversations() {
        ArrayList<ConversationData> allConversations = null;

        allConversations = new ArrayList<ConversationData>();
        allConversations.addAll(this.conversations);
        allConversations.addAll(this.hiddenConversations);

        return allConversations;
    }

}
