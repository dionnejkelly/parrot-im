package styles;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

public class statusCombo extends JComboBox{
	private static String status[] = {"Available", "Away", "Busy", "Offline"};
	
	public statusCombo(){
		super(status);
		this.addActionListener(new statusComboListener());
		this.setPreferredSize(new Dimension(30, 15));
	}
	
	private class statusComboListener implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			if (getSelectedIndex() > -1){
				System.out.println("Status changed to: " + getSelectedItem());
			}
		}
	}
}
