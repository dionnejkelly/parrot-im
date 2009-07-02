package view.options;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import view.options.modelstub.ChatbotQADataType;
import view.styles.PopupWindowListener;

public class ChatbotQA extends JFrame{
	private ChatbotQADataType QAObject;
	private JPanel mainPanel;
	private JFrame mainFrame;
	
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
		mainFrame = this;
		this.QAObject = QAObject;
		
		if(add){ //QAObject is empty
			this.setTitle("Add New Questions");
		} else { //QAObject is not empty
			this.setTitle("Edit Questions");
		}
		
		setPanels();
		this.setPreferredSize(new Dimension(500,380));
		setResizable(false);
		setLocationRelativeTo(null);

		pack();
		getContentPane().add(mainPanel);
		setVisible(true);
		setIconImage(new ImageIcon("src/images/mainwindow/logo.png").getImage());
	}
	
	public ChatbotQADataType getQAObject(){
		return QAObject;
	}
	
	private void setPanels(){
		
		/*QUESTIONS*/
		JLabel questionLabel = new JLabel("Questions:");
		QList = new JList (QAObject.getQuestions());
		QList.addListSelectionListener(new QuestionsListSelectionListener());
		JScrollPane QListScroll = new JScrollPane (QList);
		QListScroll.setPreferredSize(new Dimension(450, 100));
		
		addQ = new JButton ("Add");
		addQ.addActionListener(new addActionListener('Q'));
		removeQ = new JButton("Remove");
		removeQ.addActionListener(new removeActionListener('Q'));
		removeQ.setEnabled(false);
		JPanel QButtonsPanel = new JPanel();
		QButtonsPanel.setAlignmentX(LEFT_ALIGNMENT);
		QButtonsPanel.setLayout(new BoxLayout(QButtonsPanel, BoxLayout.X_AXIS));
		QButtonsPanel.add(addQ);
		QButtonsPanel.add(removeQ);
		
		JPanel questionPanel = new JPanel();
		questionPanel.setAlignmentX(LEFT_ALIGNMENT);
		questionPanel.setLayout(new BorderLayout());
		questionPanel.add(questionLabel, BorderLayout.NORTH);
		questionPanel.add(QListScroll, BorderLayout.CENTER);
		questionPanel.add(QButtonsPanel, BorderLayout.SOUTH);
		
		/*ANSWERS*/
		JLabel answerLabel = new JLabel("Answers:");
		AList = new JList (QAObject.getAnswers());
		AList.addListSelectionListener(new AnswersListSelectionListener());
		JScrollPane AListScroll = new JScrollPane (AList);
		AListScroll.setPreferredSize(new Dimension(450, 100));
		
		addA = new JButton ("Add");
		addA.addActionListener(new addActionListener('A'));
		removeA = new JButton("Remove");
		removeA.addActionListener(new removeActionListener('A'));
		removeA.setEnabled(false);
		JPanel AButtonsPanel = new JPanel();
		AButtonsPanel.setAlignmentX(LEFT_ALIGNMENT);
		AButtonsPanel.setLayout(new BoxLayout(AButtonsPanel, BoxLayout.X_AXIS));
		AButtonsPanel.add(addA);
		AButtonsPanel.add(removeA);
		
		JPanel answerPanel = new JPanel();
		answerPanel.setAlignmentX(LEFT_ALIGNMENT);
		answerPanel.setLayout(new BorderLayout());
		answerPanel.add(answerLabel, BorderLayout.NORTH);
		answerPanel.add(AListScroll, BorderLayout.CENTER);
		answerPanel.add(AButtonsPanel, BorderLayout.SOUTH);
		
		/*WRAP UP*/
		mainPanel = new JPanel();
		GridLayout mainLayout = new GridLayout(2,1);
		mainLayout.setVgap(10);
		mainPanel.setLayout(mainLayout);
		mainPanel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
		mainPanel.setAlignmentX(LEFT_ALIGNMENT);
		mainPanel.add(questionPanel);
		mainPanel.add(answerPanel);
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
	private class AnswersListSelectionListener implements ListSelectionListener{

		public void valueChanged(ListSelectionEvent e) {
			if (AList.getSelectedIndex() > -1){
				removeA.setEnabled(true);
			} else {
				removeA.setEnabled(false);
			}
		}
	}
	private class addActionListener implements ActionListener{
		char mode;
		
		public addActionListener (char mode){
			this.mode = mode;
		}
		
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			//execute JDialog
			JFrame popupOption = new addNewQAFrame(mode);
			popupOption.addWindowListener(new PopupWindowListener(mainFrame, popupOption));
		}
	}
	
	private class removeActionListener implements ActionListener{
		char mode;
		
		public removeActionListener (char mode){
			this.mode = mode;
		}
		
		public void actionPerformed(ActionEvent e) {
			if (mode == 'Q'){
				QAObject.removeQuestion(QList.getSelectedIndex());
				QList.setListData(QAObject.getQuestions());
				QList.updateUI();
			} else if (mode == 'A'){
				QAObject.removeAnswer(AList.getSelectedIndex());
				AList.setListData(QAObject.getAnswers());
				AList.updateUI();
			}
		}
	}
	private class addNewQAFrame extends JFrame{
		private JTextField field;
		private JFrame frame;
		
		public addNewQAFrame(char mode){
			frame =this;
			
			/*PROMPT*/
			JLabel label = new JLabel();
			if (mode == 'Q') 
				label.setText("Please type in your question: ");
			else if (mode == 'A')
				label.setText("Please type in your answer: ");
			
			field = new JTextField();
			field.setPreferredSize(new Dimension(260, 20));
			
			JButton okButton = new JButton ("OK");
			okButton.addActionListener(new okActionListener(mode));
			JButton cancelButton = new JButton ("Cancel");
			cancelButton.addActionListener(new cancelActionListener());
			JPanel buttonPanel = new JPanel ();
			buttonPanel.add(okButton);
			buttonPanel.add(cancelButton);
			
			JPanel addNewQAPanel = new JPanel();
			addNewQAPanel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
			addNewQAPanel.add(label);
			addNewQAPanel.add(field);
			addNewQAPanel.add(buttonPanel);

			this.getContentPane().add(addNewQAPanel);
			this.setLocationRelativeTo(mainFrame);
			this.setResizable(false);
			this.setPreferredSize(new Dimension (300,150));
			this.pack();
			this.setVisible(true);
			this.setIconImage(new ImageIcon("src/images/mainwindow/logo.png").getImage());
		}
		private class cancelActionListener implements ActionListener{
			
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}	
		}
		private class okActionListener implements ActionListener{
			private char mode;
			
			public okActionListener(char mode){
				this.mode = mode;
			}
			public void actionPerformed(ActionEvent e) {
				if (field.getText().length()!=0)
				if (mode == 'Q'){
					QAObject.addQuestion(field.getText());
					QList.setListData(QAObject.getQuestions());
					QList.updateUI();
				} else if (mode == 'A'){
					QAObject.addAnswer(field.getText());
					AList.setListData(QAObject.getAnswers());
					AList.updateUI();
				}
				frame.dispose();
				
			}	
		}
	}
}
