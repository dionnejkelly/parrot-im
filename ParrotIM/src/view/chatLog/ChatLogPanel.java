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
 *     2009-June-23, KF
 *         Naming convention updates. Changed all class names.
 *     2009-June-25
 *         Fixed chat log bug. Changed TextEditor to JList (might want to reconsider
 *         about this)
 *     2009-July-6
 *         Search bar added.
 *         
 * Known Issues:
 *     1. The scrolling of the text is not very satisfying. Might want to improve it.
 *     		(revert back to TextEditor? But it's more efficient now)
 * 
 * Copyright (C) 2009  Pirate Captains
 * 
 * Full license can be found in ParrotIM/LICENSE.txt.
 */

package view.chatLog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import view.styles.GPanel;
import view.styles.TextListCellRenderer;

import model.DatabaseFunctions;
import model.Model;
import model.dataType.tempData.ChatLogMessageTempData;

/**
 * Sets the GUI component of ChatLogFrame.
 * 
 * This class inherits JSplitPane methods and variables.
 */
public class ChatLogPanel extends GPanel {
	private String searchKey = "";
	/** 
	 * profile describes the name of the currently used profile. 
	 * It is used as one the arguments to extract chatlog data from database*/
    private String profile;
    
    /** model connects ChatLogPanel and the database functions.*/
    private Model model;
    
    /** 
     * logPane is a JSplitPane object that sets the right component of ChatLogWindow.
     * It consists of datesScroll (on the top) and chatLog (on the bottom).
     */
    private JSplitPane logPane;

    // left component
    /** 
     * buddies is a JList object. It is the left component of ChatLogPanel.
     * buddies shows the list of buddies whom the user has talked to.
     */
    private JList buddies;

    // right component
    // top
    /** 
     * datesScroll is a JScrollPane object. It is the scrollPane for dateList.
     */
    private JScrollPane datesScroll;
    
    /** 
     * dateList is a JList that shows the dates of chat history.
     * It shows the dates on dateVectorList.
     */
    private JList dateList;
    // bottom
    private JEditorPane text;
    
    /** 
     * text is a JList that shows the chat log.
     * 
     * Developers note: 
     * text used to be a JEditorPane but the usage of it made the code inefficient. 
     * Right now, the code is efficient but the GUI looks bad. Please help me to 
     * consider which is better.
     */
//    private JList text;
    
    /** 
     * chatLog is a JScrollPane object. It is the scrollPane for text.
     */
    private JScrollPane chatlog;
    
    /** 
     * stub is an array of String with one member.
     * It used if there is no message to display on text.
     */
    private String[] stub = new String[]{"no data is displayed"};
    private JTextField searchField ;
    // Added the database
    private DatabaseFunctions db;
    /** 
     * The constructor of ChatLogPanel. It takes model and currently used profile name as arguments.
     * It sets up the panel, including the nested splitPane. 
     * @param model
     * @param profile
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public ChatLogPanel(Model model, String profile) throws ClassNotFoundException, SQLException {
        // model stub
        this.model = model;
        this.profile = model.getCurrentProfile().getName();
         db = new DatabaseFunctions();
        

        // settings
        this.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        this.setGradientColors(colors.PRIMARY_COLOR_MED, Color.WHITE);

        /*CHATLOG SPLIT PANE*/
        
        /* set left JSplitPane component */
        BuddyPanel buddyList = new BuddyPanel(model, profile);
        buddies = buddyList.buddyList;
        buddies.addListSelectionListener(new buddyListener());

        // top right component shows the list of dates
        dateList = new JList(stub);
        dateList.setEnabled(false);
        dateList.addListSelectionListener(new datesListener());//
        dateList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        datesScroll = new JScrollPane(dateList);
        datesScroll.setMinimumSize(new Dimension(datesScroll.getWidth(), 50));
//        datesScroll
//                .setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        
        // bottom right component shows the chat logs
//        text = new JList (stub);
//        text.setCellRenderer(new TextListCellRenderer());
        text = new JEditorPane();
        text.setEditable(false);
//        text.setEnabled(false);
        
        chatlog = new JScrollPane(text);
        chatlog
                .setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        chatlog.setMinimumSize(new Dimension(chatlog.getWidth(), 100));
        
        /* set right JSplitPane component */
        logPane = new JSplitPane();
        logPane.setBorder(null);
        logPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
        //logPane.setGradientColors(Color.BLACK, Color.WHITE);
        logPane.setBackground(Color.BLACK);
        logPane.setDividerLocation(140);
        logPane.setTopComponent(datesScroll);
        logPane.setBottomComponent(chatlog);
        logPane.setDividerSize(5);

        JSplitPane chatlogPane = new JSplitPane();
        chatlogPane.setBorder(BorderFactory.createEmptyBorder());
        chatlogPane.setLeftComponent(buddyList);
        chatlogPane.setRightComponent(logPane);
        chatlogPane.setDividerLocation(200);
        chatlogPane.setDividerSize(5);

        /*SEARCH BAR*/
        searchField = new JTextField();
        searchField.addKeyListener(new searchFieldKeyListener());
        JButton searchButton = new JButton(new ImageIcon(this.getClass()
                .getResource("/images/buddylist/document_preview.png")));
        searchButton.setToolTipText("Start searching");
        searchButton.setPreferredSize(new Dimension(30,30));
        //
        searchButton.addMouseListener(new searchListener());
        JPanel searchBarPanel = new JPanel();
        searchBarPanel.setBorder(BorderFactory.createEmptyBorder(3,0,0,0));
        searchBarPanel.setLayout(new BorderLayout());
        searchBarPanel.add(searchField, BorderLayout.CENTER);
        searchBarPanel.add(searchButton, BorderLayout.EAST);
        
        /*SETTING THIS SPLITPANE*/
        this.setLayout(new BorderLayout());
        this.add(searchBarPanel, BorderLayout.SOUTH);
        this.add(chatlogPane, BorderLayout.CENTER);
    }
    
    public void updateBuddyList(){
    	
    	try {
			Vector <String> buddiesSearchResult = model.getBuddyLogList(profile, searchKey);
			
			if (buddiesSearchResult.size()>0){
		        buddies.setListData(buddiesSearchResult);
		        buddies.setEnabled(true);
		        buddies.updateUI();
	
		        dateList.setListData(stub);
		        dateList.setEnabled(false);
//		        text.setListData(stub);
		        text.setText(stub[0]);
		        text.setEnabled(false);
			} else{
				buddies.setListData(stub);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void updateDateList(){
    	Vector<String> dateVectorList = model.getBuddyDateList(profile, buddies
                .getSelectedValue().toString(), searchKey);
        

        dateList.setListData(dateVectorList);
        dateList.setEnabled(true);
        dateList.updateUI();

//        text.setListData(stub);
        text.setText(stub[0]);
    }

    /**
     * This method grabs the message from the database and sets the Vector returned
     * by the model as the text's data source. It also update the message shown by text.
     * It takes a String argument that specify the date.
     * @param date
     */
    private void updateLog(String date) {
    	// Grab all message objects from the database
        Vector<ChatLogMessageTempData> messages = model.getLogMessage(profile, buddies.getSelectedValue()
                .toString(), date, searchKey);
        
//        text.setListData(messages);
        text.setText(ChatLogMessageToString(messages));
        text.updateUI();

    }
    
    private String ChatLogMessageToString(Vector<ChatLogMessageTempData> messages){
    	String message = "";
    	
    	for (int i=0; i<messages.size(); i++){
    		message += messages.get(i).toString();
    	}
    	return message;
    }
    
    /**
     * Sets the behaviour when one of the buddies on the buddies JList is selected
     * 
     * This class inherits ListSelectionListener methods and variables.
     */
    private class buddyListener implements ListSelectionListener {

    	/**
         * If the selected buddy of the buddies JList is changed, then regenerate
         * the list of dates on dateList JList.
         * @param e
         */
        public void valueChanged(ListSelectionEvent e) {
            if (buddies.getSelectedIndex() > -1) {

                
//                System.out.println("dateVectorList is null??  "+dateVectorList.size());
                updateDateList();
                	

            }
        }
    }
    

    /**
     * Sets the behaviour when one of the dates on the dateList JList is selected
     * 
     * This class inherits ListSelectionListener methods and variables.
     */
    private class datesListener implements ListSelectionListener {
//
    	/**
         * If user selected a date of the dateList JList changed,
         * text will show the logged message of the date. 
         * It takes a ListSelectionEvent argument.
         * @param e
         */
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
    }
    
    private class searchListener extends MouseAdapter {

        /**
         * Listens for the uesr's event.
         * 
         * @param e
         */
//
        public void mousePressed(MouseEvent event) {

            if (searchField.getText().length() == 0) {
            	String resultMessage = "Please provide a key word in the search field.";
                JOptionPane.showMessageDialog(null, resultMessage);
            }

        }
    }
    
    private class searchFieldKeyListener implements KeyListener{

		//@Override
		public void keyPressed(KeyEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		//@Override
		public void keyReleased(KeyEvent arg0) {
			if (searchField.getText().length() > 0) {
            	searchKey = searchField.getText();
            	System.out.println(searchKey);
			}
			else{
				searchKey = "";
			}
			updateBuddyList();
		}

		//@Override
		public void keyTyped(KeyEvent arg0) {
			// TODO Auto-generated method stub
			
		}
    	
    }
}
