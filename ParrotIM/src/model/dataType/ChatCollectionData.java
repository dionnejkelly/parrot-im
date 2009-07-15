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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

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

    private boolean chatWindowHistoryEnabled;

    public ChatCollectionData() {
        this.conversations = new ArrayList<ConversationData>();
        this.hiddenConversations = new ArrayList<ConversationData>();
        this.activeConversation = null;
        this.chatWindowHistoryEnabled = true;

        // Debug code
        ChatDebugThread chatDebugThread = new ChatDebugThread();
        chatDebugThread.start();
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

    public void setChatWindowHistoryEnabled(boolean chatWindowHistoryEnabled) {
        this.chatWindowHistoryEnabled = chatWindowHistoryEnabled;
    }

    public boolean isChatWindowHistoryEnabled() {
        return chatWindowHistoryEnabled;
    }

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

    public void removeAllConversations() {
        this.conversations.clear();
        this.hiddenConversations.clear();
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

    // Debug code
    private class ChatDebugThread extends Thread {
        public void run() {
            BufferedReader br =
                    new BufferedReader(new InputStreamReader(System.in));
            String selection = "";
            System.out.println("*** ChatCollectionData Dev Tools ***");
            System.out.println("chat info - show info, chat quit - quit");
            System.out.println("************************************");
            try {
                while (!selection.equals("chat quit")) {
                    selection = "";
                    if (br.ready()) {
                        selection = br.readLine();
                    }
                    
                    if (selection.equals("chat info")) {
                        System.out.print("Active Conversation: ");
                        if (getActiveConversation() != null) {
                            System.out.println(getActiveConversation()
                                    .getUser()
                                    + " ("
                                    + getActiveConversation().getAccount()
                                    + ")");
                        } else {
                            System.out.println("<null>");
                        }

                        System.out.println("Conversation list ("
                                + getConversations().size() + "):");
                        for (ConversationData c : getConversations()) {
                            System.out.println("  " + c.getUser() + " ("
                                    + c.getAccount() + ")");
                        }
                        System.out.println("Hidden Conversation list ("
                                + getHiddenConversations().size() + "):");
                        for (ConversationData c : getHiddenConversations()) {
                            System.out.println("  " + c.getUser() + " ("
                                    + c.getAccount() + ")");
                        }
                    } else if (selection.equals("chat quit")) {
                        System.out
                                .println("Exiting ChatCollectionData Dev Tools...");
                    }
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return;
        }
    }

}
