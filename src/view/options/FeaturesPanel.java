/* FeaturesPanel.java
 * 
 * Programmed By:
 *     Chenny Huang
 *     Vera Lukman
 *     
 * Change Log:
 *     2009-June-20, CH
 *         Initial write.
 *     2009-June-29, VL
 *         Reorganized code.
 *         
 * Known Issues:
 *     incomplete features
 *     
 * Copyright (C) 2009  Pirate Captains
 * 
 * License: GNU General Public License version 2.
 * Full license can be found in ParrotIM/LICENSE.txt.
 */

package view.options;


import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import controller.MainController;

public class FeaturesPanel extends JPanel{
	MainController controller;
	public FeaturesPanel (MainController c){
		controller = c;
		JCheckBox chatbotCheck = new JCheckBox("Enable ChatBot");
		chatbotCheck.addChangeListener(new chatbotListener());
		
		JCheckBox soundCheck = new JCheckBox("Enable Sound Notification");
		soundCheck.addChangeListener(new soundListener());
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		this.add(chatbotCheck);
		this.add(soundCheck);
	}
	
	private class chatbotListener implements ChangeListener{

		public void stateChanged(ChangeEvent e) {
			controller.toggleChatbot();
			
		}
	}
	private class soundListener implements ChangeListener{

		public void stateChanged(ChangeEvent e) {
			//TODO
			
		}
	}
}
