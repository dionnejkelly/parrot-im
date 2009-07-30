/* DisplayPanel.java
 *  
 * Copyright (C) 2009  Pirate Captains
 * 
 * License: GNU General Public License version 2.
 * Full license can be found in ParrotIM/LICENSE.txt.
 */

package view.chatwindow;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.Observable;
import java.util.Observer;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.ScrollPaneConstants;
import javax.swing.text.html.HTMLEditorKit;

import model.Model;
import model.dataType.ChatCollectionData;
import model.dataType.ConversationData;
import model.dataType.MultiConversationData;
import model.dataType.UserData;
import model.enumerations.ServerType;
import model.enumerations.UpdatedType;
import model.enumerations.UserStateType;
import controller.MainController;

/**
 * The DisplayPanel contains the panel that allow users to read the messages in
 * the chat window.
 * 
 * This object inherits from JPanel
 */

public class DisplayPanel extends JPanel implements Observer {

    /** Allows users to read messages in the JEditorPane. */

    private JEditorPane txtPane;

    /**
     * Model stores the needed data of the system. It also connects it with
     * database.
     */

    private Model model;

    /** Sets buddies status. */
    private JLabel title;
    private JLabel avatarLabel;

    private JScrollPane chatWindowScroller;

    private JToolBar bar2;

    private JLabel twitterLimit;

    private JLabel chatState;

    /**
     * This is the constructor of the DisplayPanel.
     * 
     * @param model
     */

    public DisplayPanel(MainController c, Model model) {
        setLayout(new BorderLayout());

        this.model = model;
        this.model.addObserver(this);
        this.model.getChatCollection().addObserver(this);
        // textPane's Properties
        txtPane = new JEditorPane();
        txtPane.setPreferredSize(new Dimension(250, 300));
        txtPane.setEditable(false);
        txtPane.setEditorKit(new HTMLEditorKit());

        if (model.getActiveConversation() != null) {
        	//using a html table to do the text wrap 
            txtPane.setText("<table width="+txtPane.getWidth()+" border='0' cellpadding='0' cellspacing='0'" +
            		"> <tr><td style='word-break:break-all;'>"+model.getActiveConversation()
            		.displayMessages()+"</td></tr></table>");
        } else {
            txtPane.setText("");
        }

        // ScrollPane's Properties
        chatWindowScroller = new JScrollPane();
        chatWindowScroller.getViewport().setView(txtPane);
        chatWindowScroller
                .setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        chatWindowScroller.setPreferredSize(new Dimension(250, 300));
        chatWindowScroller.setMinimumSize(new Dimension(10, 10));
        chatWindowScroller.getInputContext();

        // Title Properties
        title = new JLabel();

        // title.setText(model.getActiveConversation().getUser().getNickname());
        // if (model.getActiveConversation().getUser().isBlocked()) {
        // title.setForeground(Color.LIGHT_GRAY.darker());
        // }
        // else if
        // (model.getActiveConversation().getUser().getState().toString().equals("Available"))
        // {
        // title.setForeground(Color.GREEN.darker());
        // }
        // else if
        // (model.getActiveConversation().getUser().getState().toString().equals("dnd"))
        // {
        // title.setForeground(Color.ORANGE.darker());
        // }
        // else if
        // (model.getActiveConversation().getUser().getState().toString().equals("away"))
        // {
        // title.setForeground(Color.ORANGE.darker());
        // }
        // else {
        // title.setForeground(Color.RED.darker());
        // }

        // Upper toolBar
        /*
         * JToolBar bar1 = new JToolBar(); bar1.setFloatable(false);
         * bar1.add(title);
         */
        Box bar1 = Box.createHorizontalBox();
        bar1.add(title);
        bar1.add(Box.createGlue());
        ImageIcon avatar = new ImageIcon();
        avatarLabel = new JLabel(avatar);
        bar1.add(avatarLabel);

        // JButton fullView = new JButton("Full");
        // JButton simpleView = new JButton("Simple");
        // bar1.add(fullView);
        // bar1.add(simpleView);

        // Lower toolBar
        // JToolBar bar2 = new JToolBar();
        bar2 = new JToolBar();
        bar2.setFloatable(false);
        bar2.setBackground(model.tertiaryColor);

        chatState = new JLabel();

        bar1.add(chatState);
        // JLabel isTyping = new JLabel("140");
        
	    twitterLimit = new JLabel("Character Limits: 140");
	    twitterLimit.setForeground(model.primaryTextColor);
        twitterLimit.setVisible(false);
        bar2.add(twitterLimit);

        // add to panel
        add(bar1, BorderLayout.NORTH);
        add(bar2, BorderLayout.SOUTH);
        add(chatWindowScroller, BorderLayout.CENTER);
    }

    public void updateChar(int count) {
        twitterLimit.setText("Character Limits: " + count);

        if (20 <= count && count <= 140) {
            twitterLimit.setForeground(Color.GRAY.darker());
        }

        else if (10 <= count && count <= 19) {
            twitterLimit.setForeground(Color.ORANGE.darker());
        }

        else {
            twitterLimit.setForeground(Color.RED.darker());
        }

    }

    /**
     * Returns the JEditorPane.
     * 
     * @return JEditorPane
     */

    public JEditorPane getTxtPane() {
        return txtPane;
    }

    /**
     * Update according to the UpdatedType.
     * 
     * @param o
     * @param arg
     */

    public void update(Observable o, Object arg) {
        ChatCollectionData chatCollection = model.getChatCollection();
        String text = "";

        if ((arg == UpdatedType.BUDDY || arg == UpdatedType.CHAT
                || arg == UpdatedType.CHATNOTSIDEPANEL || o instanceof ChatCollectionData)
                && model.getActiveConversation() != null) {
            txtPane.setText(model.getActiveConversation().displayMessages());

            if (chatCollection.getActiveConversation() instanceof ConversationData) {
                // A blank space is add to the start of each name because
                // otherwise
                // the first letter may look cutoff for some names.
                if (model.getActiveConversation().getUser().getNickname()
                        .length() > 0) {
                    title.setText("  "
                            + model.getActiveConversation().getUser()
                                    .getNickname());
                } else {
                    title.setText("  "
                            + model.getActiveConversation().getUser()
                                    .getUserID());
                }
                if(model.getActiveConversation().getAccount().getServer()
                		== ServerType.TWITTER){
                	twitterLimit.setVisible(true);
                }else{
                	twitterLimit.setVisible(false);
                }

                // VCard vcard = new VCard();
                // vcard.load(connection,model.getActiveConversation().getUser().getUserID());

                if (model.getActiveConversation().getUser().isBlocked()) {
                    title.setForeground(Color.LIGHT_GRAY.darker());
                } else if (model.getActiveConversation().getUser().getState() == UserStateType.ONLINE) {
                    title.setForeground(Color.GREEN.darker());
                } else if (model.getActiveConversation().getUser().getState() == UserStateType.BUSY
                        || model.getActiveConversation().getUser().getState() == UserStateType.AWAY) {
                    title.setForeground(Color.ORANGE.darker());
                } else if (model.getActiveConversation().getUser().getState() == UserStateType.BRB
                        || model.getActiveConversation().getUser().getState() == UserStateType.LUNCH
                        || model.getActiveConversation().getUser().getState() == UserStateType.PHONE) {
                    title.setForeground(Color.CYAN.darker());
                } else {
                    title.setForeground(Color.RED.darker());
                }
            } else if (chatCollection.getActiveConversation() instanceof MultiConversationData) {
                for (UserData u : ((MultiConversationData) chatCollection
                        .getActiveConversation()).getUsers()) {
                    text += u.toString() + ", ";
                }
                if (text.equals("")) {
                    title.setText("<empty>");
                } else {
                    title.setText(text.substring(0, text.length() - 2));
                }

            }

            // this auto-scrolls the chat window to the bottom when an incoming
            // message is received
            int x;
            txtPane.selectAll();
            x = txtPane.getSelectionEnd();
            txtPane.select(x, x);
        }

        else if (arg == UpdatedType.CHAT_STATE) {// TODO
            if (!model.getActiveConversation().getUser().getTypingState()
                    .equals(chatState.getText())) {
                chatState.setText(model.getActiveConversation().getUser()
                        .getTypingState());
            }
        } else if (arg == UpdatedType.COLOR) {
            txtPane.setBackground(model.textPaneColor);
            bar2.setBackground(model.tertiaryColor);
            twitterLimit.setForeground(model.primaryTextColor);

            txtPane.updateUI();
            bar2.updateUI();
        }


        return;
    }
}