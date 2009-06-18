package view.styles;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

import controller.services.Xmpp;

public class statusCombo extends JComboBox{
	private static String status[] = {"Available", "Away", "Busy", "Chatty"};
	Xmpp chatClient;
	
	public statusCombo(Xmpp c){
		super(status);
		chatClient = c;
		this.addActionListener(new statusComboListener());
		this.setMaximumSize(new Dimension(200, 30));
	}
	
	private class statusComboListener implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			if (getSelectedIndex() > -1){
				System.out.println("-----------------------------------Coming from here");
				String userStatus = getSelectedItem().toString();
				System.out.println("Status changed to: " + userStatus);
				
				
				chatClient.setStatus(userStatus);
					
			
				
				
			}
		}
	}
}
