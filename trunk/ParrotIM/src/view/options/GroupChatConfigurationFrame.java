package view.options;


import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Vector;

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

import controller.MainController;

import view.styles.PopupEnableMainWindowListener;
import view.styles.PopupWindowListener;

import model.Model;
import model.dataType.UserData;
import model.enumerations.PopupEnableWindowType;


public class GroupChatConfigurationFrame extends JFrame{

	private final String messageTo = "parrotim.bugreport@gmail.com ";
	
	/**
     * variable model for extracting buddy list, each buddy's information and ,
     * conversation
     */
    protected Model model;
    
	private JPanel mainPanel;
	
	private JFrame mainFrame;
	private JFrame popUpFrame;
	
	private JButton inviteButton;
	private JButton cancelButton;
	
	private JLabel groupChatRoomLabel;
	
	private JLabel usersToInvite;


	private JTextArea messageText;
	private JComboBox groupRoom;
	private JComboBox usersGroup;
	
	private MainController controller;
	
	/**
	 * if edit is true, use for adding a new Q/A
	 * if edit is false, use for editing the existing data
	 * @param dummyQ
	 * @param edit
	 * */
	
//	public static void main(String[] args) {
//		GroupChatConfigurationFrame group = new GroupChatConfigurationFrame();
//	}
	
	
//	public GroupChatConfigurationFrame(MainController c, Model model){
//		mainFrame = this;
//		this.model = model;
//		this.controller = c;
//		this.addWindowListener(new PopupEnableMainWindowListener(model, PopupEnableWindowType.GROUPCHAT));
//		this.setTitle("Group Chat Configuration");
//		
//		setRoomPanels();
//		this.setPreferredSize(new Dimension(330,120));
//		setResizable(false);
//		setLocationRelativeTo(null);
//
//		
//		
//		
//		pack();
//		getContentPane().add(mainPanel);
//		setVisible(true);
//		setIconImage(new ImageIcon("src/images/mainwindow/logo.png").getImage());
//		this.setLocation(600 ,200);
//		
//	}
	
	public GroupChatConfigurationFrame(MainController c, Model model, JFrame chatFrame){
		this.mainFrame = chatFrame;
		popUpFrame = this;
		this.model = model;
		this.controller = c;
		
		this.addWindowListener(new PopupEnableMainWindowListener(model, PopupEnableWindowType.GROUPCHAT));
		this.setTitle("Group Chat Configuration");
		
		
		
		setAllPanels();
		
		
		
		this.setPreferredSize(new Dimension(330,180));
		setResizable(false);
		setLocationRelativeTo(null);

		
		
		
		pack();
		getContentPane().add(mainPanel);
		setVisible(true);
		setIconImage(new ImageIcon(this.getClass().getResource("/images/mainwindow/logo.png")).getImage());
		this.setLocation(600 ,200);
		 this.addWindowListener(new PopupWindowListener(this.mainFrame, popUpFrame));
		
	}
	
	
	
	
	private void setAllPanels() {

        this.addWindowListener(new PopupEnableMainWindowListener(model, PopupEnableWindowType.GROUPCHAT));
		usersToInvite = new JLabel("Users to invite: ");
		//String[] usersList = {"Only online users should be displayed here", "kevin.fahy@gmail.com", "parroim.test@gmail.com", "jrfox02@gmail.com"};
		
		usersGroup = new JComboBox(UserData.getOnlineBuddy());
		usersGroup.setPreferredSize(new Dimension(265, 25));
		usersGroup.addActionListener(new StyleListener());
		
		groupChatRoomLabel = new JLabel("Group Chat Room: ");
		

		inviteButton = new JButton ("Invite");
		inviteButton.addActionListener(new inviteActionListener());
		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new cancelActionListener());
		
		// this should be extract from the list of current conferrence rooms
		//String[] listGroupRoom = {"Empty if there exists no conferrence room", "ParrotsOnly@conferrence.jabber.org"};
		
		
		groupRoom = new JComboBox(controller.getAvailableRoom());
		groupRoom.setPreferredSize(new Dimension(265, 25));
		groupRoom.addActionListener(new StyleListener());
		

		
		
		
		JPanel QButtonsPanel = new JPanel();
		QButtonsPanel.setAlignmentX(LEFT_ALIGNMENT);
		QButtonsPanel.setLayout(new BoxLayout(QButtonsPanel, BoxLayout.X_AXIS));
		QButtonsPanel.add(inviteButton);
		QButtonsPanel.add(cancelButton);
		
		
		/*WRAP UP*/
		mainPanel = new JPanel();
		mainPanel.add(usersToInvite);
		mainPanel.add(usersGroup);
		mainPanel.add(groupChatRoomLabel);
		mainPanel.add(groupRoom);
		mainPanel.add(QButtonsPanel);
	}
	

	
//	private void setRoomPanels(){
//		
//	
//		groupChatRoomLabel = new JLabel("Group Chat Room: ");
//	
//
//		inviteButton = new JButton ("Invite");
//		inviteButton.addActionListener(new inviteActionListener());
//		cancelButton = new JButton("Cancel");
//		cancelButton.addActionListener(new cancelActionListener());
//		
//		// this should be extract from the list of current conferrence rooms
//		//String[] listGroupRoom = {"Empty if there exists no conferrence room", "ParrotsOnly@conferrence.jabber.org"};
//		groupRoom = new JComboBox(controller.getAvailableRoom());
//		groupRoom.setPreferredSize(new Dimension(265, 25));
//		groupRoom.addActionListener(new StyleListener());
//		
//
//		
//		JPanel QButtonsPanel = new JPanel();
//		QButtonsPanel.setAlignmentX(LEFT_ALIGNMENT);
//		QButtonsPanel.setLayout(new BoxLayout(QButtonsPanel, BoxLayout.X_AXIS));
//		QButtonsPanel.add(inviteButton);
//		QButtonsPanel.add(cancelButton);
//		
//		
//		/*WRAP UP*/
//		mainPanel = new JPanel();
//		mainPanel.add(groupChatRoomLabel);
//		mainPanel.add(groupRoom);
//		mainPanel.add(QButtonsPanel);
//
//
//	}
	
	
	
	
	private class StyleListener implements ActionListener {

		

		public void actionPerformed(ActionEvent event) {
			
			
		}
			
		
	}
		
	private class cancelActionListener implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			//mainFrame.dispose();
		    popUpFrame.dispose();
		
			
		}
	}
	
	public class TextBoxListener implements KeyListener {

		public void keyPressed(KeyEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		public void keyReleased(KeyEvent arg0) {
			if (messageText.getText().length() > 0) {
				inviteButton.setEnabled(true);
			}
			
			else {
				inviteButton.setEnabled(false);
			}
			
		}

		public void keyTyped(KeyEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	private class inviteActionListener implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			
			Vector<String> onlineBuddy = UserData.getOnlineBuddy();
			
			String roomName = groupRoom.getSelectedItem().toString();
			String friend = onlineBuddy.get(usersGroup.getSelectedIndex());
			
			System.out.println("Room Name = " + roomName);
			System.out.println("Friend to invite = " + friend);
			
			if (roomName.contains("Parrot")) {
				System.out.println("The room does contain Parrot");
				controller.inviteFriend(friend, roomName + "@conference.jabber.org");
			}
				
			else {
				System.out.println("The room doesn't contain Parrot");
				controller.inviteFriend(friend, roomName);
				
			}
			
	
		}
	}
	
	
	

	
	


	
	
}


