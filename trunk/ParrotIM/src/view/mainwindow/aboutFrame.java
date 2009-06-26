package view.mainwindow;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * The container frame of About ParrotIM Window. User can view the details about ParrotIM
 * 
 * This object inherits JFrame variables and methods
 */
public class aboutFrame extends JFrame{
	
	public aboutFrame(){
		this.setTitle("About ParrotIM");
		this.setPreferredSize(new Dimension(300,500));
		this.setResizable(false);
		
		//our logo + parrotIM label
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new BorderLayout());
		JLabel avatarDisplay = new JLabel ();
		avatarDisplay.setHorizontalAlignment(SwingConstants.CENTER);
		ImageIcon avatar = new ImageIcon (getClass().getClassLoader().getResource("images/buddylist/logoBox.png"));
		avatarDisplay.setIcon(avatar);
		topPanel.add(avatarDisplay, BorderLayout.NORTH);
		
		JLabel parrotLabel = new JLabel("ParrotIM");
		parrotLabel.setHorizontalAlignment(SwingConstants.CENTER);
		topPanel.add(parrotLabel, BorderLayout.CENTER);
		
		//informations
		JLabel informations = new JLabel();
		informations.setText("<html>This software is developed by<br>" +
				"* Rakan Alkheliwi <br>* William (Wei-Lun) Chen <br>* Jihoon Choi <br>* Kevin Fahy <br>" +
				"* Jordan Fox <br>* Chenny Huang <br>* Vera Lukman <br>* Ahmad Sidiqi <br>* Aaron Siu <br>* Wei Zhang</html>");
		informations.setHorizontalAlignment(SwingConstants.CENTER);
		
		//pirate captains
		JLabel author = new JLabel ("(c) Pirate Captains 2009");
		author.setHorizontalAlignment(SwingConstants.CENTER);
		
		//setting aboutPanel
		JPanel aboutPanel = new JPanel();
		aboutPanel.setLayout(new BorderLayout());
		aboutPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
		aboutPanel.add(topPanel, BorderLayout.NORTH);
		aboutPanel.add(informations, BorderLayout.CENTER);
		aboutPanel.add(author, BorderLayout.SOUTH);
		
		//setting frame
		this.getContentPane().add(aboutPanel);
		this.pack();
		this.setVisible(true);
	}

}
