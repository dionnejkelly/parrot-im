package styles;


import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JTextField;


public class pmLabel extends JTextField {
	protected String text;
	
	public pmLabel (String text){
		this.text = text;
		this.setHorizontalAlignment(JTextField.CENTER);
		this.setEditable(false);
		this.setEnabled(false);
		this.setText(text);
		this.setToolTipText("Click to edit your personal message");
		this.setPreferredSize(new Dimension (200,20));
		this.addMouseListener(new labelMouseListener(this));
	}
	
	class labelMouseListener implements MouseListener{
		private pmLabel label;
		
		public labelMouseListener (pmLabel lbl){
			label = lbl; 
		}

		public void mouseClicked(MouseEvent e) {
			label.setEditable(true);
			label.setText(label.getText());
		}

		public void mouseEntered(MouseEvent e) {
			label.setEnabled(true);
		}

		public void mouseExited(MouseEvent e) {
			label.setEditable(false);
			label.setEnabled(false);
		}

		public void mousePressed(MouseEvent e) {}

		public void mouseReleased(MouseEvent e) {}
		
	}

}
