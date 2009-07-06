package view.mainwindow;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class NewProfile extends JFrame{
	private JTextField profileTextField;
	private JCheckBox passwordCheckBox;
	private JPasswordField passwordField;
	
	public NewProfile(){
		//Profile name
		profileTextField = new JTextField();
		profileTextField.setPreferredSize(new Dimension(250,20));
		
		JPanel profileNamePanel = new JPanel();
		profileNamePanel.setAlignmentX(LEFT_ALIGNMENT);
//		profileNamePanel.setLayout(new BoxLayout(profileNamePanel, BoxLayout.X_AXIS));
		profileNamePanel.add(new JLabel("Profile Name: "));
		profileNamePanel.add(profileTextField);
		
		//password check
		passwordCheckBox = new JCheckBox("Set Password");
		passwordCheckBox.addChangeListener(new passwordCheckBoxChangeListener());
		passwordField = new JPasswordField();
		passwordField.setPreferredSize(new Dimension(250, 20));
//		passwordField.setVisible(false);
		
		JPanel passwordPanel = new JPanel();
		passwordPanel.setAlignmentX(LEFT_ALIGNMENT);
		passwordPanel.setLayout(new BoxLayout(passwordPanel, BoxLayout.Y_AXIS));
		passwordPanel.add(passwordCheckBox);
		passwordPanel.add(passwordField);
		
		//mainPanel layout
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
		mainPanel.add(profileNamePanel);
		mainPanel.add(passwordPanel);
		
		this.setPreferredSize(new Dimension(400,200));
		this.setResizable(false);
		this.getContentPane().add(mainPanel);
		this.pack();
		this.setVisible(true);
	}
	
	private class passwordCheckBoxChangeListener implements ChangeListener{

		public void stateChanged(ChangeEvent e) {
			if (passwordCheckBox.isSelected())
				passwordField.setVisible(true);
			else
				passwordField.setVisible(false);
			
		}
		
	}

}
