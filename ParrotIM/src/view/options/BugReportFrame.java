package view.options;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;

import view.styles.GPanel;
import view.styles.PopupEnableMainWindowListener;

import model.Model;
import model.enumerations.PopupEnableWindowType;


public class BugReportFrame extends JFrame{

	private final String messageTo = "parrotim.bugreport@gmail.com ";
	
	/**
     * variable model for extracting buddy list, each buddy's information and ,
     * conversation
     */
    protected Model model;
    
	private GPanel mainPanel;
	private JFrame mainFrame;
	
	private JButton sendButton;
	private JButton cancelButton;
	
	private String frequencyReport = "";
	private String severityReport = "";
	
	private JLabel questionLabel;
	private JLabel subjectLabel;
	private JLabel messageLabel;
	private JLabel frequencyLabel;
	private JLabel severityLabel;
	
	private JRadioButton criticalImpact;
	private JRadioButton majorImpact;
	private JRadioButton minorImpact;
	private JRadioButton noImpact;
	
	private JTextField subjectText;
	private JTextArea messageText;
	private JComboBox frequency;
	
	
	/**
	 * if edit is true, use for adding a new Q/A
	 * if edit is false, use for editing the existing data
	 * @param dummyQ
	 * @param edit
	 * */
	public BugReportFrame(Model model){
		mainFrame = this;
		this.model = model;
		this.addWindowListener(new PopupEnableMainWindowListener(model, PopupEnableWindowType.BUGREPORT));
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setTitle("Bug Report");
		
		setPanels();
		this.setPreferredSize(new Dimension(400,500));
		setResizable(false);
		setLocationRelativeTo(null);

		
		
		
		pack();
		getContentPane().add(mainPanel);
		setVisible(true);
		setIconImage(new ImageIcon("src/images/mainwindow/logo.png").getImage());
		this.setLocation(600 ,200);
		
	}
	
	
	
	private void setPanels(){
		
		Dimension preferredDimension = new Dimension (400,20);
		Border preferredBorder = BorderFactory.createEmptyBorder(0,20,0,20);
		questionLabel = new JLabel("<html>To: <FONT COLOR=WHITE>" + messageTo +"</FONT></html>", JLabel.LEFT);
		questionLabel.setPreferredSize(new Dimension(400,40));
		questionLabel.setBorder(BorderFactory.createEmptyBorder(15,20,0,20));
		subjectLabel = new JLabel("Title: " , JLabel.LEFT);
		subjectLabel.setPreferredSize(preferredDimension);
		subjectLabel.setBorder(preferredBorder);
		messageLabel = new JLabel("Problem Description: (please be specific)" );
		messageLabel.setPreferredSize(preferredDimension);
		messageLabel.setBorder(preferredBorder);
		frequencyLabel = new JLabel("Frequency: ");
		severityLabel = new JLabel("Severity: ");

		subjectText = new JTextField(29);
		subjectText.setToolTipText("Enter a one line summary of your report.");
		messageText = new JTextArea(10,20);
		messageText.addKeyListener(new TextBoxListener());
		messageText.setToolTipText("Enter a detailed description of the problem. Please be specific.");
		
		JScrollPane QListScroll = new JScrollPane (messageText);
		QListScroll.setPreferredSize(new Dimension(362, 180));
		
		sendButton = new JButton ("Send", new ImageIcon(this.getClass()
                .getResource("/images/buddylist/email_go.png")));
		sendButton.addActionListener(new sendActionListener());
		cancelButton = new JButton("Cancel", new ImageIcon(this.getClass()
                .getResource("/images/buddylist/email_delete.png")));
		cancelButton.addActionListener(new cancelActionListener());
		sendButton.setEnabled(false);
		
		
		String[] listFrequency = {"Always", "Often", "Occasionally", "Rarely"};
		frequency = new JComboBox(listFrequency);
		frequency.setPreferredSize(new Dimension(265, 25));
		frequency.addActionListener(new StyleListener());
		
		criticalImpact = new JRadioButton("Critical");
		majorImpact = new JRadioButton("Major");
		minorImpact = new JRadioButton("Minor");
		noImpact = new JRadioButton("No Impact");
		
		criticalImpact.setOpaque(false);
		majorImpact.setOpaque(false);
		minorImpact.setOpaque(false);
		noImpact.setOpaque(false);
		
		criticalImpact.setActionCommand("Critical");
		majorImpact.setActionCommand("Major");
		minorImpact.setActionCommand("Minor");
		noImpact.setActionCommand("No Impact");
		
		StyleListener listener = new StyleListener();
		
		criticalImpact.addActionListener(listener);
		majorImpact.addActionListener(listener);
		minorImpact.addActionListener(listener);
		noImpact.addActionListener(listener);
		
		
		JPanel frequencyPanel = new JPanel();
		frequencyPanel.setOpaque(false);
		frequencyPanel.add(frequency);
		
		 //Group the radio buttons.
	    ButtonGroup group = new ButtonGroup();
	    group.add(criticalImpact);
	    group.add(majorImpact);
	    group.add(minorImpact);
	    group.add(noImpact);

	    
	    
		JPanel severityPanel = new JPanel();
		severityPanel.setOpaque(false);
		
		severityPanel.add(criticalImpact);
		severityPanel.add(majorImpact);
		severityPanel.add(minorImpact);
		severityPanel.add(noImpact);
		
		JPanel QButtonsPanel = new JPanel();
		QButtonsPanel.setOpaque(false);
		QButtonsPanel.setAlignmentX(LEFT_ALIGNMENT);
		QButtonsPanel.setLayout(new BoxLayout(QButtonsPanel, BoxLayout.X_AXIS));
		QButtonsPanel.add(sendButton);
		QButtonsPanel.add(cancelButton);
		
		
		/*WRAP UP*/
		mainPanel = new GPanel();
		mainPanel.setGradientColors(model.primaryColor, model.secondaryColor);
		mainPanel.add(questionLabel);
//		mainPanel.add(questionText, BorderLayout.AFTER_LINE_ENDS);
		mainPanel.add(subjectLabel);
		mainPanel.add(subjectText);
		mainPanel.add(messageLabel);
//		mainPanel.add(messageHelpLabel);
		mainPanel.add(QListScroll);
		mainPanel.add(frequencyLabel);
		mainPanel.add(frequencyPanel);
		mainPanel.add(severityLabel, frequencyPanel.getLayout());
		mainPanel.add(severityPanel);
		mainPanel.add(QButtonsPanel);

	}
	
	
	
	
	private class StyleListener implements ActionListener {

		

		public void actionPerformed(ActionEvent event) {
			
			frequencyReport = frequency.getSelectedItem().toString();
			System.out.println(frequencyReport);
			severityReport =  event.getActionCommand();
			
			
			if (severityReport.contains("comboBoxChanged")) {
				severityReport = "Unspecified";
			}
			
			System.out.println(severityReport);
		}
		
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
			System.out.println("From: " + model.getCurrentProfile().getAccountData().get(0));
			BugReport sendAemail = new BugReport("cmpt275testing@gmail.com", "abcdefghi");
			try {
				sendAemail.sendReport(subjectText.getText(), "Frequency: " + frequencyReport + "\n" + "Severity: " + severityReport + "\n" + "Message:\n" + messageText.getText(), messageTo);
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

