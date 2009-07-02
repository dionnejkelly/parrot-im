package view.options;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import view.options.modelstub.ChatbotQADataType;
import view.options.modelstub.model;

public class ChatbotQA extends JFrame{
	private ChatbotQADataType QAObject;
	private JPanel mainPanel;
	
	private JList QList;
	private JButton addQ;
	private JButton removeQ;
	
	private JList AList;
	private JButton addA;
	private JButton removeA;
	/**
	 * if edit is true, use for adding a new Q/A
	 * if edit is false, use for editing the existing data
	 * @param dummyQ
	 * @param edit
	 * */
	public ChatbotQA(ChatbotQADataType QAObject, boolean add){
		this.QAObject = QAObject;
		
		if(add){ //QAObject is empty
			this.setTitle("Add New Questions");
		} else { //QAObject is not empty
			this.setTitle("Edit Questions");
		}
		
		setPanels();
		this.setPreferredSize(new Dimension(500,400));
		setResizable(false);
		setLocationRelativeTo(null);

		pack();
		getContentPane().add(mainPanel);
		setVisible(true);
	}
	
	private void setPanels(){
		
		/*QUESTIONS*/
		JLabel questionLabel = new JLabel("Questions:");
		QList = new JList (QAObject.getQuestions());
		QList.addListSelectionListener(new QuestionsListSelectionListener());
		JScrollPane QListScroll = new JScrollPane (QList);
		QListScroll.setPreferredSize(new Dimension(450, 100));
		addQ = new JButton ("Add");
		
		
		JPanel questionPanel = new JPanel();
		questionPanel.setAlignmentX(LEFT_ALIGNMENT);
		questionPanel.setLayout(new BorderLayout());
		questionPanel.add(questionLabel, BorderLayout.NORTH);
		questionPanel.add(QListScroll, BorderLayout.CENTER);	
		
		/*WRAP UP*/
		mainPanel = new JPanel();
		mainPanel.setBorder(BorderFactory.createEmptyBorder(20,10,20,10));
		mainPanel.setAlignmentX(LEFT_ALIGNMENT);
		mainPanel.add(questionPanel);
	}
	
	private class QuestionsListSelectionListener implements ListSelectionListener{

		public void valueChanged(ListSelectionEvent e) {
			if (QList.getSelectedIndex() > -1){
				removeQ.setEnabled(true);
			} else {
				removeQ.setEnabled(false);
			}
		}
		
	}
}
