/* pmLabel.java
 * 
 * Programmed By:
 * 	   Vera Lukman
 *     Jihoon Choi
 *     Jordan Fox
 * 	   William Chen
 *     
 * Change Log:
 *     2009-June-6, VL
 *         Initial write.
 *     2009-June-7, JC
 *     	   Integrated with control.
 *     2009-June-8, JF
 *     	   Fixed keyPressed()
 *     2009-June-13, WC/VL
 *         Transferred file over to new project, ParrotIM.
 *         Fixed alignment to left.
 *         
 * Known Issues:
 *     1. User should be able to click outside the box to
 *        change personal message
 * 
 * Copyright (C) 2009  Pirate Captains
 * 
 * Full license can be found in ParrotIM/LICENSE.txt.
 */

package view.styles;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JTextField;

import controller.MainController;


public class PmLabel extends JTextField {
	protected MainController core;
	
	public PmLabel (MainController c){
		core = c;
		//this.setHorizontalAlignment(JTextField.CENTER);
		changePM(false);

		this.setToolTipText("Click to edit your personal message");
		this.addMouseListener(new labelMouseListener(this));
		this.addKeyListener(new labelKeyListener(this));
	}
	public void changePM(boolean b){
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
		private PmLabel label;
		
		public labelMouseListener (PmLabel lbl){
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
				 * 	label.changePM(false);
				 */
			}
		}

		public void mousePressed(MouseEvent e) {}

		public void mouseReleased(MouseEvent e) {}

		
		
	}
	
	class labelKeyListener implements KeyListener{

		private PmLabel label;
		
		public labelKeyListener (PmLabel lbl){
			label = lbl; 
		}

		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode()==KeyEvent.VK_ENTER){
				// test printing
				//System.out.println("Pressed Enter!");
				label.changePM(false);
			}
		}

		public void keyReleased(KeyEvent e) {}

		public void keyTyped(KeyEvent e) {}

		
		
	}
}
