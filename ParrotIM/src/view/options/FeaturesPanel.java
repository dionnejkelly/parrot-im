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


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import view.options.modelstub.ChatbotQADataType;
import view.options.modelstub.model;
import view.styles.PopupWindowListener;

import controller.MainController;

public class FeaturesPanel extends JPanel{
	private MainController controller;
	private model modelStub = new model();
	
	private JFrame mainframe;
	private JCheckBox chatbotCheck;
	private JList chatbotList;
	private JButton chatbotAddButton;
	private JButton chatbotEditButton;
	private JButton chatbotRemoveButton;
	private JPanel chatbotOptions;
	
	public FeaturesPanel (MainController c, JFrame mainframe){
		this.mainframe = mainframe;
		controller = c;
		
		/*CHATBOT*/
		chatbotCheck = new JCheckBox("Enable ChatBot");
		chatbotCheck.addChangeListener(new chatbotListener());
		
		//chatbot list
		chatbotList = new JList(modelStub.getQAList());
		chatbotList.addListSelectionListener(new chatbotListSelectionListener());
		JScrollPane chatbotListScroll = new JScrollPane(chatbotList);
		chatbotListScroll.setPreferredSize(new Dimension(400, 80));
		
		//chatbot buttons
		chatbotAddButton = new JButton("Add");
		chatbotAddButton.addActionListener(new chatbotAddButtonActionListener());
		chatbotEditButton = new JButton ("Edit");
		chatbotEditButton.addActionListener(new chatbotEditButtonActionListener());
		chatbotRemoveButton = new JButton ("Remove");
		chatbotRemoveButton.addActionListener(new chatbotRemoveButtonActionListener());
		setEnabledChatbotButtons(false);

		JPanel chatbotButtonsPanel = new JPanel();
		chatbotButtonsPanel.setAlignmentX(LEFT_ALIGNMENT);
		chatbotButtonsPanel.setLayout(new BoxLayout (chatbotButtonsPanel, BoxLayout.X_AXIS));
		chatbotButtonsPanel.add(chatbotAddButton);
		chatbotButtonsPanel.add(chatbotEditButton);
		chatbotButtonsPanel.add(chatbotRemoveButton);
		
		chatbotOptions = new JPanel();
		chatbotOptions.setAlignmentX(LEFT_ALIGNMENT);
		chatbotOptions.add(chatbotListScroll);
		chatbotOptions.add(chatbotButtonsPanel);
		chatbotOptions.setVisible(false);
		
		JPanel chatbotPanel = new JPanel();
		chatbotPanel.setAlignmentX(LEFT_ALIGNMENT);
		chatbotPanel.setLayout(new BoxLayout(chatbotPanel, BoxLayout.Y_AXIS));
		chatbotPanel.add(chatbotCheck);
		chatbotPanel.add(chatbotOptions);
		
		/*SOUND NOTIFICATION*/
		JCheckBox soundCheck = new JCheckBox("Enable Sound Notification");
		soundCheck.addChangeListener(new soundListener());
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		this.add(chatbotPanel);
		this.add(soundCheck);
		this.add(new JCheckBox("another one"));//just a test
	}
	private void setEnabledChatbotButtons(boolean b){
		chatbotEditButton.setEnabled(b);
		chatbotRemoveButton.setEnabled(b);
	}
	private class chatbotListener implements ChangeListener{

		public void stateChanged(ChangeEvent e) {
//			controller.toggleChatbot();
			if (chatbotCheck.isSelected())
				chatbotOptions.setVisible(true);
			else
				chatbotOptions.setVisible(false);
		}
	}
	private class chatbotListSelectionListener implements ListSelectionListener{

		public void valueChanged(ListSelectionEvent e) {
			if (chatbotList.getSelectedIndex() > -1){
				setEnabledChatbotButtons(true);
			} else {
				setEnabledChatbotButtons(false);
			}
		}
		
	}
	private class chatbotAddButtonActionListener implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			ChatbotQA chatbotOptions = new ChatbotQA(new ChatbotQADataType(), true);
			chatbotOptions.addWindowListener(new PopupWindowListener(mainframe ,chatbotOptions));
		}
	}
	private class chatbotEditButtonActionListener implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			ChatbotQA chatbotOptions = new ChatbotQA(modelStub.getQAObject(chatbotList.getSelectedIndex()), false);
			chatbotOptions.addWindowListener(new PopupWindowListener(mainframe ,chatbotOptions));
		}
	}
	private class chatbotRemoveButtonActionListener implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			int selected = chatbotList.getSelectedIndex();
			if (selected > -1){
				modelStub.removeQA(selected);
				chatbotList.setListData(modelStub.getQAList());
				chatbotList.updateUI();
			}
			if (modelStub.getQASize() == 0) {
				setEnabledChatbotButtons(false);
			}
		}
	}
	private class soundListener implements ChangeListener{

		public void stateChanged(ChangeEvent e) {
			//TODO
			
		}
	}
	
}
