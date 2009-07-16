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
import java.util.Observable;

import java.util.Scanner;

public class ChatCollectionData extends Observable {

    /**
     * Holds a collection of all conversations. If no conversations are
     * currently active, the collection is simply empty.
     */
    private ArrayList<ConversationData> conversations;

    private ArrayList<ConversationData> hiddenConversations;

    private ArrayList<MultiConversationData> multiConversations;

    private ArrayList<MultiConversationData> hiddenMultiConversations;

    /**
     * Holds a reference to the current active conversation. This conversation
     * should be contained inside data member, conversations.
     */
    private Conversation activeConversation;

    private boolean chatWindowHistoryEnabled;

    public ChatCollectionData() {
        this.conversations = new ArrayList<ConversationData>();
        this.hiddenConversations = new ArrayList<ConversationData>();
        this.multiConversations = new ArrayList<MultiConversationData>();
        this.hiddenMultiConversations = new ArrayList<MultiConversationData>();
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

    public ArrayList<MultiConversationData> getMultiConversations() {
        return multiConversations;
    }

    public ArrayList<MultiConversationData> getHiddenMultiConversations() {
        return hiddenMultiConversations;
    }

    public void setActiveConversation(Conversation activeConversation) {
        this.activeConversation = activeConversation;
        if (this.isHidden(this.activeConversation)) {
            this.activateConversation(this.activeConversation);
        }

        super.setChanged();
        super.notifyObservers();

        return;
    }

    public Conversation getActiveConversation() {
        return activeConversation;
    }

    public boolean isHidden(Conversation conversation) {
        return hiddenConversations.contains(conversation)
                || hiddenMultiConversations.contains(conversation);
    }


    public boolean isVisible(Conversation conversation) {
        return this.conversations.contains(conversation)
                || this.multiConversations.contains(conversation);
    }
    
    public void activateConversation(Conversation conversation) {

        if (this.hiddenConversations.contains(conversation)) {
            this.conversations.add((ConversationData) conversation);
            this.hiddenConversations.remove(conversation);
        } else if (this.hiddenMultiConversations.contains(conversation)) {
            this.multiConversations.add((MultiConversationData) conversation);
            this.hiddenMultiConversations.remove(conversation);
        } else {
            throw new IllegalArgumentException();
        }

        return;
    }

    // Addition/Removal methods

    public void setChatWindowHistoryEnabled(boolean chatWindowHistoryEnabled) {
        this.chatWindowHistoryEnabled = chatWindowHistoryEnabled;
    }

    public boolean isChatWindowHistoryEnabled() {
        return chatWindowHistoryEnabled;
    }

    public void addConversation(Conversation conversation) {
        if (conversation instanceof ConversationData) {
            if (!this.conversations.contains(conversation)) {
                this.conversations.add((ConversationData) conversation);
                if (conversations.size() == 1) {
                    this.activeConversation = conversation;
                }
                if (this.hiddenConversations.contains(conversation)) {
                    this.hiddenConversations.remove(conversation);
                }
            }
        } else if (conversation instanceof MultiConversationData) {
            if (!this.multiConversations.contains(conversation)) {
                this.multiConversations
                        .add((MultiConversationData) conversation);
                if (multiConversations.size() == 1) {
                    this.activeConversation = conversation;
                }
                if (this.hiddenMultiConversations.contains(conversation)) {
                    this.hiddenMultiConversations.remove(conversation);
                }
            }
        }

        super.setChanged();
        super.notifyObservers();

        return;
    }

    public boolean removeConversation(Conversation conversation) {
        boolean removed = false;

        if (conversation instanceof ConversationData) {
            if (this.conversations.contains(conversation)) {
                this.conversations.remove(conversation);
                this.hiddenConversations.add((ConversationData) conversation);
                removed = true;
                this.activeConversation = this.conversations.isEmpty() ? null
                        : this.conversations.get(0);
            }
        } else if (conversation instanceof MultiConversationData) {
            if (this.multiConversations.contains(conversation)) {
                this.multiConversations.remove(conversation);
                this.hiddenMultiConversations
                        .add((MultiConversationData) conversation);
                removed = true;
                this.activeConversation = this.multiConversations.isEmpty() ? null
                        : this.multiConversations.get(0);
            }
        }

        super.setChanged();
        super.notifyObservers();

        return removed;
    }

    public void hideAllConversations() {
        this.hiddenConversations.addAll(this.conversations);
        this.conversations.clear();
        this.hiddenMultiConversations.addAll(this.multiConversations);
        this.multiConversations.clear();
        this.activeConversation = null;

        return;
    }

    public void removeAllConversations() {
        this.conversations.clear();
        this.hiddenConversations.clear();
        this.hiddenMultiConversations.clear();
        this.multiConversations.clear();
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

    public ArrayList<MultiConversationData> getAllMultiConversations() {
        ArrayList<MultiConversationData> allConversations = null;

        allConversations = new ArrayList<MultiConversationData>();
        allConversations.addAll(this.multiConversations);
        allConversations.addAll(this.hiddenMultiConversations);

        return allConversations;
    }

    public ArrayList<Conversation> getVisibleConversations() {
        ArrayList<Conversation> allConversations = null;

        allConversations = new ArrayList<Conversation>();
        allConversations.addAll(this.conversations);
        allConversations.addAll(this.multiConversations);

        return allConversations;
    }

    public boolean activeIsMulti() {
        return this.activeConversation != null
                && this.activeConversation instanceof MultiConversationData;
    }

    public void forceUpdate() {
        super.setChanged();
        super.notifyObservers();
        
        return;
    }
    
    public MultiConversationData findByRoomName(String roomName) {
        MultiConversationData found = null; // Default return value

        for (MultiConversationData c : this.multiConversations) {
            if (c.getRoomName().equalsIgnoreCase(roomName)) {
                found = c;
                break;
            }
        }

        if (found == null) {
            for (MultiConversationData c : this.hiddenMultiConversations) {
                if (c.getRoomName().equalsIgnoreCase(roomName)) {
                    found = c;
                    break;
                }
            }
        }

        return found;
    }

    // Debug code
    private class ChatDebugThread extends Thread {
        public void run() {
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    System.in));
            String selection = "";
            System.out.println("****** ChatCollectionData Dev Tools *******");
            System.out.println("* chat info - show info, chat quit - quit *");
            System.out.println("*******************************************");
            try {
                while (!selection.equals("chat quit")) {
                    selection = "";
                    if (br.ready()) {
                        selection = br.readLine();
                    }

                    if (selection.equals("chat info")) {
                        System.out.print("Active Conversation: ");
                        if (getActiveConversation() != null) {
                            if (getActiveConversation() instanceof ConversationData) {
                                System.out.println(getActiveConversation()
                                        .getUser()
                                        + " ("
                                        + getActiveConversation().getAccount()
                                        + ")");
                            } else {
                                System.out
                                        .println(((MultiConversationData) getActiveConversation())
                                                .getRoomName()
                                                + " ("
                                                + getActiveConversation()
                                                        .getAccount() + ")");
                            }

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
                        System.out.println("MultiConversation list ("
                                + getMultiConversations().size() + "):");
                        for (MultiConversationData c : getMultiConversations()) {
                            System.out.println("  " + c.userList() + " ("
                                    + c.getAccount() + ")");
                        }
                        System.out.println("Hidden MultiConversation list ("
                                + getHiddenMultiConversations().size() + "):");
                        for (MultiConversationData c : getHiddenMultiConversations()) {
                            System.out.println("  " + c.userList() + " ("
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
