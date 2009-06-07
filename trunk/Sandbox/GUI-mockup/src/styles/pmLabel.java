package styles;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JTextField;

public class pmLabel extends JTextField {
	protected String text;
	
	public pmLabel (String text){
		this.text = text;
		this.setHorizontalAlignment(JTextField.CENTER);
		editable(false);

		this.setText(text);
		this.setToolTipText("Click to edit your personal message");
		this.setPreferredSize(new Dimension (200,20));
		this.addMouseListener(new labelMouseListener(this));
	}
	protected void editable(boolean b){
		if (b){//editable
			this.setEditable(true);
			this.setOpaque(true);
			this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			this.setBackground(Color.WHITE);
		}else{//not editable
			this.setBorder(null);
			this.setEditable(false);
			this.setEnabled(false);
			this.setOpaque(false);			
		}
		
	}
	class labelMouseListener implements MouseListener{
		private pmLabel label;
		
		public labelMouseListener (pmLabel lbl){
			label = lbl; 
		}

		public void mouseClicked(MouseEvent e) {
			label.editable(true);
			label.setText(label.getText());
		}

		public void mouseEntered(MouseEvent e) {
			label.setEnabled(true);
		}

		public void mouseExited(MouseEvent e) {
			label.editable(false);
		}

		public void mousePressed(MouseEvent e) {}

		public void mouseReleased(MouseEvent e) {}
		
	}

}
