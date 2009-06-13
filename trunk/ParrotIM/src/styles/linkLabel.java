package styles;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;

public class linkLabel extends JLabel {
	protected String text;
	
	public linkLabel (String text){
		this.setHorizontalAlignment(CENTER);
		this.text = text;
		this.setText(text);
		
		this.addMouseListener(new labelMouseListener(this));
	}
	
	class labelMouseListener implements MouseListener{
		private linkLabel label;
		
		public labelMouseListener (linkLabel lbl){
			label = lbl; 
		}

		public void mouseClicked(MouseEvent e) {
		}

		public void mouseEntered(MouseEvent e) {
			label.setText("<html><u>"+label.text+"</u></html>");
		}

		public void mouseExited(MouseEvent e) {
			label.setText(text);
		}

		public void mousePressed(MouseEvent e) {}

		public void mouseReleased(MouseEvent e) {}
		
	}

}
