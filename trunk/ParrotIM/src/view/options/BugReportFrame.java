package view.options;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import model.Model;


public class BugReportFrame extends JFrame{

	private final String messageTo = "parrotim.test@gmail.com";
	
	/**
     * variable model for extracting buddy list, each buddy's information and ,
     * conversation
     */
    protected Model model;
    
	private JPanel mainPanel;
	private JFrame mainFrame;
	
	private JButton sendButton;
	private JButton cancelButton;
	
	
	
	private JLabel questionLabel;
	private JLabel subjectLabel;
	private JLabel messageLabel;
	
	private JTextField questionText;
	private JTextField subjectText;
	private JTextArea messageText;
	
	
	
	/**
	 * if edit is true, use for adding a new Q/A
	 * if edit is false, use for editing the existing data
	 * @param dummyQ
	 * @param edit
	 * */
	public BugReportFrame(Model model){
		mainFrame = this;
		this.model = model;
		this.setTitle("Bug Report");
		
		setPanels();
		this.setPreferredSize(new Dimension(380,480));
		setResizable(false);
		setLocationRelativeTo(null);

		pack();
		getContentPane().add(mainPanel);
		setVisible(true);
		setIconImage(new ImageIcon("src/images/mainwindow/logo.png").getImage());
		
	}
	
	
	
	private void setPanels(){
		
		questionLabel = new JLabel("To: " );
		subjectLabel = new JLabel("Title: " );
		messageLabel = new JLabel("Message: " );
	
		questionText = new JTextField(30);
		questionText.setText(messageTo);
		questionText.setEditable(false);
		subjectText = new JTextField(29);
		messageText = new JTextArea(10,20);
		messageText.addKeyListener(new TextBoxListener());
		JScrollPane QListScroll = new JScrollPane (messageText);
		QListScroll.setPreferredSize(new Dimension(350, 280));
		
		sendButton = new JButton ("Send");
		sendButton.addActionListener(new sendActionListener());
		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new cancelActionListener());
		sendButton.setEnabled(false);
		
		JPanel QButtonsPanel = new JPanel();
		QButtonsPanel.setAlignmentX(LEFT_ALIGNMENT);
		QButtonsPanel.setLayout(new BoxLayout(QButtonsPanel, BoxLayout.X_AXIS));
		QButtonsPanel.add(sendButton);
		QButtonsPanel.add(cancelButton);
		
		
		/*WRAP UP*/
		mainPanel = new JPanel();
		mainPanel.add(questionLabel, BorderLayout.NORTH);
		mainPanel.add(questionText, BorderLayout.AFTER_LINE_ENDS);
		mainPanel.add(subjectLabel,  BorderLayout.CENTER);
		mainPanel.add(subjectText);
		mainPanel.add(messageLabel);
		mainPanel.add(QListScroll);
		mainPanel.add(QButtonsPanel, BorderLayout.SOUTH);
		mainPanel.setBackground(Color.yellow);

	}
		
	private class cancelActionListener implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			mainFrame.dispose();
		
			
		}
	}
	
	public class TextBoxListener implements KeyListener {

		public void keyPressed(KeyEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		public void keyReleased(KeyEvent arg0) {
			if (messageText.getText().length() > 0) {
				sendButton.setEnabled(true);
			}
			
			else {
				sendButton.setEnabled(false);
			}
			
		}

		public void keyTyped(KeyEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	private class sendActionListener implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			System.out.println("From: " + model.getAccountList().get(0));
			BugReport sendAemail = new BugReport(model.getAccountList().get(0), model.getPassword(model.getAccountList().get(0)));
			try {
				sendAemail.sendReport(subjectText.getText(), messageText.getText(), messageTo);
				String resultMessage =
                    "Your bug report has been succesfully delivered. Thank you for your co-operation.";
				JOptionPane.showMessageDialog(null, resultMessage);
				mainFrame.dispose();
			} catch (Exception e1) {
				System.out.println("An error has occured in the event of reporting.");
				e1.printStackTrace();
			}
	
		}
	}
	
	
	

	
	


	
	
}

