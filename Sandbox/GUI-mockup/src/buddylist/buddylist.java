package buddylist;

import java.awt.*;
import javax.swing.*;

public class buddylist extends JFrame{

	public buddylist(){
		
		JFrame mainFrame = new JFrame("Buddy List");
		mainFrame.setMinimumSize(new Dimension(300,600));
		mainFrame.setIconImage(new ImageIcon(System.getProperty("user.dir") + "/src/logo.png").getImage());
		
		//MENU BAR
		JPanel menuPanel = new JPanel();
		menuPanel.setBackground(Color.green);
		menuPanel.setMinimumSize(new Dimension(300,50));
		menuPanel.setPreferredSize(new Dimension(300,50));
		menuPanel.setLayout(new GridLayout(1,5,10,10));
		
		JButton connectButton = new JButton("Connect..");
		connectButton.setMinimumSize(new Dimension(50,50));
		menuPanel.add(connectButton);
		
		JButton editButton = new JButton("Edit");
		editButton.setMinimumSize(new Dimension(50,50));
		editButton.setMaximumSize(new Dimension(50,50));
		menuPanel.add(editButton);
		
		JButton optionButton = new JButton("Options");
		optionButton.setPreferredSize(new Dimension(50,50));
		menuPanel.add(optionButton);
		
		JButton logButton = new JButton("Chatlog");
		logButton.setPreferredSize(new Dimension(50,50));
		menuPanel.add(logButton);
		
		JButton helpButton = new JButton("Help");
		helpButton.setPreferredSize(new Dimension(50,50));
		menuPanel.add(helpButton);
		
		
		//Contact List
		JPanel contactPanel = new JPanel();
		contactPanel.setBackground(Color.white);
		contactPanel.setLayout(new BoxLayout(contactPanel,BoxLayout.Y_AXIS));
		JLabel l1 = new JLabel("Contact 1 -- status text");
		JLabel l2 = new JLabel("Contact 2 -- status text");
		JLabel l3 = new JLabel("Contact 3 -- status text");
		JLabel l4 = new JLabel("Contact 4 -- status text");
		JLabel l5 = new JLabel("Contact 5 -- status text");
		JLabel l6 = new JLabel("Contact 6 -- status text");
		contactPanel.add(l1);
		contactPanel.add(l2);
		contactPanel.add(l3);
		contactPanel.add(l4);
		contactPanel.add(l5);
		contactPanel.add(l6);
		
		//Contact Options
		JPanel bottomPanel = new JPanel();
		bottomPanel.setBackground(Color.blue);
		bottomPanel.setMinimumSize(new Dimension(300,50));
		bottomPanel.setPreferredSize(new Dimension(300,50));
		
		bottomPanel.setLayout(new BoxLayout(bottomPanel,BoxLayout.X_AXIS));
		
		JButton addButton = new JButton("Add");
		addButton.setMinimumSize(new Dimension(50,50));
		addButton.setPreferredSize(new Dimension(50,50));
		JButton removeButton = new JButton("Remove");
		removeButton.setMinimumSize(new Dimension(50,50));
		removeButton.setPreferredSize(new Dimension(50,50));
		
		bottomPanel.add(Box.createRigidArea(new Dimension(10,50)));
		bottomPanel.add(addButton);
		bottomPanel.add(Box.createRigidArea(new Dimension(20,50)));
		bottomPanel.add(removeButton);
		bottomPanel.add(Box.createHorizontalGlue());
		
		JPanel test = new JPanel();
		test.setBackground(Color.red);
		test.setPreferredSize(new Dimension(300,40));
		
		//SETUP MAIN PANEL
		JPanel mainPanel = new JPanel();

		mainPanel.setLayout(new BorderLayout());
		mainPanel.setBackground(Color.gray);
		
		mainPanel.add(menuPanel, BorderLayout.NORTH);
		mainPanel.add(contactPanel, BorderLayout.CENTER);
		mainPanel.add(test, BorderLayout.SOUTH);
		mainPanel.add(bottomPanel, BorderLayout.SOUTH);
		
		mainFrame.getContentPane().add(mainPanel);
		mainFrame.pack();
		mainFrame.setVisible(true);
	}

}
