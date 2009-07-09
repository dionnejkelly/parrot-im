package view.mainwindow;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import view.styles.PopupWindowListener;

import model.Model;

import controller.MainController;

public class PasswordPrompt extends JFrame{
	private JFrame popup;
	private JFrame mainFrame;
	private JPasswordField passwordPrompt;
	private JButton OKbutton;
	
	private boolean manage;
	
	private Model model;
	private MainController core;
	private String profileName;
	
	/**
	 * asks for password
	 * use this for managing the account
	 * @param profileName
	 * @param model
	 * @param core
	 * @param mainFrame
	 * */
	public PasswordPrompt
			(String profileName, Model model, MainController core, JFrame mainFrame){
		popup = this;
		this.manage = true;
		this.model = model;
		this.core = core;
		this.profileName = profileName;
		this.mainFrame = mainFrame;
		
		constructFrame();
	}
	/**
	 * asks for password
	 * use this for deleting the account
	 * @param profileName
	 * @param model
	 * @param mainFrame
	 * */
	public PasswordPrompt
			(String profileName, Model model, JFrame mainFrame){
		popup = this;
		this.manage = false;
		this.model = model;
		this.profileName = profileName;	
		this.mainFrame = mainFrame;

		constructFrame();
	}
	
	private void constructFrame(){
		this.addWindowListener(new PopupWindowListener(mainFrame, this));
		
		String prompt="";
		if (manage)
			prompt = "Enter password to manage " + profileName + "\'s profile";
		else
			prompt = "Enter password to delete " + profileName + "\'s profile";
		
		//password prompt
		passwordPrompt = new JPasswordField();
		passwordPrompt.addKeyListener(new passwordKeyListener());
		passwordPrompt.setPreferredSize(new Dimension(355, 20));
		JPanel passwordPanel = new JPanel();
		passwordPanel.add(passwordPrompt);
	
		//buttons
		OKbutton = new JButton ("OK");
		OKbutton.addActionListener(new OKActionListener());
		OKbutton.setEnabled(false);
		JButton cancelButton = new JButton ("Cancel");
		cancelButton.addActionListener(new cancelActionListener());
		JPanel buttonsPanel = new JPanel ();
		buttonsPanel.add (OKbutton);
		buttonsPanel.add(cancelButton);
		
		//panel
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		mainPanel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
		mainPanel.add(new JLabel(prompt), BorderLayout.NORTH);
		mainPanel.add(passwordPanel, BorderLayout.CENTER);
		mainPanel.add(buttonsPanel, BorderLayout.SOUTH);
		
		//frame
		this.addWindowListener(new PopupWindowListener(mainFrame, this));
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setPreferredSize(new Dimension(400,150));
		this.getContentPane().add(mainPanel);
		this.pack();
		this.setVisible(true);
	}

	private class passwordKeyListener implements KeyListener{

		public void keyPressed(KeyEvent e) {}
		public void keyReleased(KeyEvent e) {}

		public void keyTyped(KeyEvent e) {
			if (passwordPrompt.getPassword().length > 0)
				OKbutton.setEnabled(true);
			else
				OKbutton.setEnabled(false);
		}
	}
	private class cancelActionListener implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			popup.dispose();
		}
	}
	private class OKActionListener implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			//if (password is correct) {
				if (manage){
					try {
						ManageAccountFrame manageAccount = new ManageAccountFrame(model, core, profileName);
						manageAccount.addWindowListener(new PopupWindowListener(mainFrame, manageAccount));
					} catch (ClassNotFoundException e1) {
						e1.printStackTrace();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				} else {
					model.removeProfile(profileName);
				}
				popup.dispose();
			//} else {
				//give a warning
			//}
		}
	}
}
