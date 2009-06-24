/* linkLabel.java
 * 
 * Programmed By:
 * 	   Vera Lukman
 * 	   William Chen
 *     
 * Change Log:
 *     2009-June-6, VL
 *         Initial write.The label is underlined when hovered.
 *     2009-June-13, WC
 *         Transferred file over to new project, ParrotIM.
 *     2009-June-19, VL
 *     	   The label is underlined on mouse out. It is bolded when hovered.
 *         
 * Known Issues:
 *     1. The best look and feel for linkLabel is still on dispute
 * 
 * Copyright (C) 2009  Pirate Captains
 * 
 * Full license can be found in ParrotIM/LICENSE.txt.
 */

package view.styles;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;

public class LinkLabel extends JLabel {
	protected String text;
	
	public LinkLabel (String text){
		this.setHorizontalAlignment(CENTER);
		this.text = text;
		this.setText("<html><u>"+ text +"</u></html>");
		
		this.addMouseListener(new labelMouseListener(this));
	}
	
	class labelMouseListener implements MouseListener{
		private LinkLabel label;
		
		public labelMouseListener (LinkLabel lbl){
			label = lbl; 
		}

		public void mouseClicked(MouseEvent e) {
		}

		public void mouseEntered(MouseEvent e) {
			label.setText("<html><b>"+label.text+"</b></html>");
		}

		public void mouseExited(MouseEvent e) {
			label.setText("<html><u>"+ text +"</u></html>");
		}

		public void mousePressed(MouseEvent e) {}

		public void mouseReleased(MouseEvent e) {}
		
	}

}
