/* chatLogPanel.java
 * 
 * Programmed By:
 *     Vera Lukman
 *     Jordan Fox
 *     
 * Change Log:
 *     2009-June-16, VL
 *         Initial write. All data was stub, not connected yet to database.
 *         Skeleton of the GUI was provided, but not fully functioning yet.
 *     2009-June-17, JF
 *         Added functionality. The GUI was fully functioning with the stub data.
 *         All components were connected and linked together.
 *     2009-June-19, VL
 *         Integrated to access the real database
 *         
 * Known Issues:
 *     1. Missing search bar.
 * 
 * Copyright (C) 2009  Pirate Captains
 * 
 * Full license can be found in ParrotIM/LICENSE.txt.
 */

package view.chatLog;

import java.awt.Dimension;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JEditorPane;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import model.Model;
import model.dataType.tempData.ChatLogMessageTempData;

public class ChatLogPanel extends JSplitPane {
    private String profile;
    private Model model;
    private JSplitPane logPane;

    // left component
    private JList buddies;

    // right component
    // top
    private JScrollPane datesScroll;
    private Vector<String> dateVectorList;
    private JList dateList;
    // bottom
    private JEditorPane text;
    private JScrollPane chatlog;

    public ChatLogPanel(Model model, String profile) {
        // model stub
        this.model = model;
        this.profile = profile;

        // settings
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        /* set left JSplitPane component */
        BuddyPanel buddyList = new BuddyPanel(model, profile);
        buddies = buddyList.buddyList;
        buddies.addListSelectionListener(new buddyListener());
        this.setLeftComponent(buddyList);

        /* set right JSplitPane component */
        logPane = new JSplitPane();
        logPane.setBorder(null);
        logPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
        logPane.setDividerLocation(140);

        // bottom right component shows the chat logs
        text = new JEditorPane();
        text.setEditable(false);
        chatlog = new JScrollPane(text);
        chatlog
                .setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        chatlog.setMinimumSize(new Dimension(chatlog.getWidth(), 100));
        logPane.setBottomComponent(chatlog);

        // top right component shows the list of dates
        dateVectorList = new Vector<String>();
        dateList = new JList(dateVectorList);
        dateList.addListSelectionListener(new datesListener());
        dateList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        datesScroll = new JScrollPane(dateList);
        datesScroll.setMinimumSize(new Dimension(datesScroll.getWidth(), 50));
        datesScroll
                .setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        logPane.setTopComponent(datesScroll);

        this.setRightComponent(logPane);
        this.setDividerLocation(200);
    }

    private class buddyListener implements ListSelectionListener {

        public void valueChanged(ListSelectionEvent e) {
            if (buddies.getSelectedIndex() > -1) {

                dateVectorList = model.getBuddyDateList(profile, buddies
                        .getSelectedValue().toString());

                for(int i=0; i< dateVectorList.size(); i++){
                	System.out.println(dateVectorList.get(i));
                }
                // recreate the JList
                dateList = new JList(dateVectorList);
                dateList.addListSelectionListener(new datesListener());
                dateList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                datesScroll = new JScrollPane(dateList);
                datesScroll.setMinimumSize(new Dimension(
                        datesScroll.getWidth(), 50));
                datesScroll
                        .setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                logPane.setTopComponent(datesScroll);

                // clean the right textbox
                text.setText("");

            }
        }
    }

    private class datesListener implements ListSelectionListener {

        public void valueChanged(ListSelectionEvent e) {
            JList source = (JList) e.getSource();
            String date = source.getSelectedValue().toString();
            updateLog(date);
        }

        protected void updateLog(String date) {
            ArrayList<ChatLogMessageTempData> messages = null;
            String log = "";

            // Grab all message objects from the database
            messages = model.getLogMessage(profile, buddies.getSelectedValue()
                    .toString(), date);
            
            // Turn the messages into text
            for (ChatLogMessageTempData m : messages) {
                log += m.toString();
            }

            text.setText(log);
        }
    }
}
