package chatLog;

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JEditorPane;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import chatLogModel.chatLog;
import chatLogModel.modeldummy;


public class chatLogPanel extends JSplitPane{
	private modeldummy model;
	
	//left component
	private JList buddies;
	
	//right component
	//top
	private JScrollPane datesScroll;
	private chatLog[] dateArray;
	private JList dateList;
	//bottom
	private JEditorPane text;
	private JScrollPane chatlog;
	
	public chatLogPanel(modeldummy model){
		//model stub
		this.model = model;
		
		//settings
		this.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		
		/*set left component*/
		buddyPanel buddyList = new buddyPanel(model);
		buddies = buddyList.buddyList;
		buddies.addListSelectionListener(new buddyListener());
		this.setLeftComponent(buddyList);
		
		/*set right component*/
		JSplitPane logPane = new JSplitPane();
		logPane.setBorder(null);
		logPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		logPane.setDividerLocation(200);
		
		//bottom component shows the chat logs
		chatlog = new JScrollPane(text);
		text = new JEditorPane();
		text.setEditable(false);
		chatlog.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		logPane.setBottomComponent(chatlog);
		
		//top component shows the list of dates
		dateArray = model.getHistoryList(0); 
		dateList = new JList (dateArray);
		dateList.addListSelectionListener(new datesListener());
		dateList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		dateList.setSelectedIndex(0);
		datesScroll = new JScrollPane(dateList);
		datesScroll.setMinimumSize(new Dimension(datesScroll.getWidth(), 50));
		datesScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		logPane.setTopComponent(datesScroll);
		
		this.setRightComponent(logPane);
		this.setDividerLocation(120);
	}
	
	private class buddyListener implements ListSelectionListener{

		public void valueChanged(ListSelectionEvent e) {
			int index = buddies.getSelectedIndex();
			if (index > -1){
				dateArray = model.getHistoryList(index);
//				dates.repaint();
//				history.repaint();
				System.out.println(index);
				System.out.println("real list");
				for (int i=0; i<dateArray.length; i++){
					System.out.println(dateArray[i]);
				}
			}
		}
	}

	private class datesListener implements ListSelectionListener{
		
		public void valueChanged(ListSelectionEvent e){
			JList source = (JList) e.getSource();
			int dateIndex = source.getSelectedIndex();
			updateLog(dateIndex);
		}
		
		protected void updateLog (int dateIndex) {
			String log = dateArray[dateIndex].getLog();
        	text.setText(log);
        	System.out.println(log);
        	
    	}
	}
}
