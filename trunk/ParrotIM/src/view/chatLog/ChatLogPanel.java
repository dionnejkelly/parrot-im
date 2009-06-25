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
 *     2009-June-25
 *         Fixed chat log bug. Changed TextEditor to JList (might want to reconsider
 *         about this)
 *         
 * Known Issues:
 *     1. Missing search bar.
 *     2. The scrolling of the text is not very satisfying. Might want to improve it.
 *     		(revert back to TextEditor? But it's more efficient now)
 * 
 * Copyright (C) 2009  Pirate Captains
 * 
 * Full license can be found in ParrotIM/LICENSE.txt.
 */

package view.chatLog;

import java.awt.Dimension;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import view.styles.textListCellRenderer;

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
//    private JEditorPane text;
    private JList text;
    private JScrollPane chatlog;
    private String[] stub;

    public ChatLogPanel(Model model, String profile) throws ClassNotFoundException, SQLException {
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
        stub = new String[]{"<html><i>no chat log is displayed</i></html>"};
        text = new JList (stub);
        text.setEnabled(false);
        text.setCellRenderer(new textListCellRenderer());
        
        chatlog = new JScrollPane(text);
//        chatlog
//                .setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        chatlog.setMinimumSize(new Dimension(chatlog.getWidth(), 100));
        logPane.setBottomComponent(chatlog);

        // top right component shows the list of dates
        dateVectorList = new Vector<String>();
        dateList = new JList(dateVectorList);
        dateList.addListSelectionListener(new datesListener());
        dateList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        datesScroll = new JScrollPane(dateList);
        datesScroll.setMinimumSize(new Dimension(datesScroll.getWidth(), 50));
//        datesScroll
//                .setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        logPane.setTopComponent(datesScroll);

        this.setRightComponent(logPane);
        this.setDividerLocation(200);
    }

    private class buddyListener implements ListSelectionListener {

        public void valueChanged(ListSelectionEvent e) {
            if (buddies.getSelectedIndex() > -1) {

                dateVectorList = model.getBuddyDateList(profile, buddies
                        .getSelectedValue().toString());
                

//                System.out.println("dateVectorList is null??  "+dateVectorList.size());
                dateList.setListData(dateVectorList);
                dateList.updateUI();
                	
                text.setListData(stub);

            }
        }
    }

    private class datesListener implements ListSelectionListener {

        public void valueChanged(ListSelectionEvent e) {
            JList source = (JList) e.getSource();
            
            if (source.getSelectedValue() == null){
            	return;
            }
//            System.out.println("selected date??  "+source.getSelectedValue());
            String date = source.getSelectedValue().toString();
//            System.out.println("date is null??  "+date.length());
            updateLog(date);
        }

        protected void updateLog(String date) {
        	// Grab all message objects from the database
            Vector<ChatLogMessageTempData> messages = model.getLogMessage(profile, buddies.getSelectedValue()
                    .toString(), date);
            
            text.setListData(messages);
            text.updateUI();

        }
    }
}
