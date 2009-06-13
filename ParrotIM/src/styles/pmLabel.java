package styles;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JTextField;

import ChatClient.ChatClient;

public class pmLabel extends JTextField {
	protected ChatClient core;
	
	public pmLabel (ChatClient c){
		core = c;
		this.setHorizontalAlignment(JTextField.CENTER);
		changePM(false);

		this.setToolTipText("Click to edit your personal message");
		this.setPreferredSize(new Dimension (200,20));
		this.addMouseListener(new labelMouseListener(this));
		this.addKeyListener(new labelKeyListener(this));
	}
	protected void changePM(boolean b){
		if (b){//editable
			if (!this.getText().equals("Type your status message")){
				this.setText("");
			}
			this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			this.setBackground(Color.WHITE);
			this.setEditable(true);
			this.setEnabled(true);
			this.setOpaque(true);
			
		}else{//not editable
			this.setBorder(null);
			this.setText(this.getText());
			this.setEditable(false);
			this.setEnabled(false);
			this.setOpaque(false);
			//send status to core
			try {
				if(this.getText().length()==0){
					this.setText("(Type your status message)");
					core.setPresence("");
				}else{
					core.setPresence(this.getText());
				}
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			
		}
	}
	
	class labelMouseListener implements MouseListener{
		private pmLabel label;
		
		public labelMouseListener (pmLabel lbl){
			label = lbl; 
		}

		public void mouseClicked(MouseEvent e) {
			if (!label.isEditable()){
				label.changePM(true);
			}
		}

		public void mouseEntered(MouseEvent e) {
			label.setEnabled(true);
		}

		public void mouseExited(MouseEvent e) {
			if (!label.isEditable()){
				label.setEnabled(false);
			} else{
				/*
				 * PSEUDOCODE
				 * if (mouseclicked)
				 * 	label.setEnabled(false);
				 */
			}
		}

		public void mousePressed(MouseEvent e) {}

		public void mouseReleased(MouseEvent e) {}

		
		
	}
	
	class labelKeyListener implements KeyListener{

		private pmLabel label;
		
		public labelKeyListener (pmLabel lbl){
			label = lbl; 
		}

		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode()==e.VK_ENTER){
				// test printing
				//System.out.println("Pressed Enter!");
				label.changePM(false);
			}
		}

		public void keyReleased(KeyEvent e) {}

		public void keyTyped(KeyEvent e) {}

		
		
	}
}
