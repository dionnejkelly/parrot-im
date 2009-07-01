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
 *     2009-June-23, KF
 *         Naming convention updates. Changed all class names.
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

import javax.swing.BorderFactory;
import javax.swing.JLabel;

/**
 * This object sets the style of the clickable labels on mainwindow (sign in window).
 * 
 * It inherits JLabel methods and variables.
 */
public class LinkLabel extends JLabel {
	/** text is a String with the name of the label that will be shown on the GUI*/
	protected String text;
	
	/**
	 * LinkLabel constructor. It takes a String object as its argument.
	 * It sets up the String to be underlined and show it on the GUI.
	 * @param text */
	public LinkLabel (String text){
		this.setHorizontalAlignment(CENTER);
		this.text = text;
		this.setText("<html><u>"+ text +"</u></html>");

        this.setBorder(BorderFactory.createEmptyBorder(3, 0, 3, 0));
		
		this.addMouseListener(new labelMouseListener(this));
	}
	
	/**
     * Sets the behaviour with regard of mouse input and position.
     * 
     * This class inherits MouseListener methods and variables.
     */
	private class labelMouseListener implements MouseListener{
		/** label is a LinkLabel object. */
		private LinkLabel label;
		
		/**
		 * labelMouseListener constructor.
		 * It takes a PmLabel object.
		 * @param lbl
		 */
		public labelMouseListener (LinkLabel lbl){
			label = lbl; 
		}

		public void mouseClicked(MouseEvent e) {
		}

		/** When the user hover on label, the text will be bolded.
		 * It takes a MouseEvent argument
		 * @param e */
		public void mouseEntered(MouseEvent e) {
			label.setText("<html><b>"+label.text+"</b></html>");
		}
		
		/** When the user hover on label, the text will be underlined.
		 * It takes a MouseEvent argument 
		 * @param e */
		public void mouseExited(MouseEvent e) {
			label.setText("<html><u>"+ text +"</u></html>");
		}

		public void mousePressed(MouseEvent e) {}

		public void mouseReleased(MouseEvent e) {}
		
	}

}
