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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import model.Model;
import model.dataType.ChatbotQADataType;
import model.dataType.tempData.CustomizedChatbotModel;

import view.buddylist.BuddyList;
import view.styles.GPanel;
import view.styles.PopupWindowListener;
import view.styles.WindowColors;

import controller.MainController;

public class FeaturesPanel extends GPanel {
	
    private CustomizedChatbotModel chatBotModel;

    private JFrame optionframe;

    private JCheckBox chatbotCheck;
    private JCheckBox soundCheck;
    private JCheckBox chatLogCheck;
    private JCheckBox chatWindowHistoryCheck;

    private JList chatbotList;
    private JButton chatbotAddButton;
    private JButton chatbotEditButton;
    private JButton chatbotRemoveButton;
    private JPanel chatbotOptions;

    private Model model;

    public FeaturesPanel(MainController c, JFrame optionframe, Model model)
            throws ClassNotFoundException, SQLException {
    	this.setGradientColors(colors.PRIMARY_COLOR_DARK, colors.PRIMARY_COLOR_MED);
        this.model = model;
        chatBotModel = model.getCustomizedChatbotModel();

//        chatBotModel = new CustomizedChatbotModel(this.model);
        this.optionframe = optionframe;

        /* CHATBOT */
        
        System.out.println("=================================");
        System.out.println("           From the feature panel");
        System.out.println("Chatbot =  " + model.getCurrentProfile().isChatbotEnabled());
        System.out.println("Chat Log =  " + model.getCurrentProfile().isChatLogEnabled());
        System.out.println("=================================");
        
        if (model.getCurrentProfile().isChatbotEnabled()) {
        	 chatbotCheck = new JCheckBox("Toggle ChatBot",  new ImageIcon(this.getClass().getResource(
             "/images/menu/monitor_add.png")));
             
        }
        
        else {
        	chatbotCheck = new JCheckBox("Toggle ChatBot",  new ImageIcon(this.getClass().getResource(
            "/images/menu/monitor_delete.png")));
        }
        
        chatbotCheck.setBackground(colors.SECONDARY_COLOR_LT);
        chatbotCheck.setForeground(colors.SECONDARY_COLOR_DARK);
        chatbotCheck.setBorder(BorderFactory.createEmptyBorder(5,5,5,101));
        chatbotCheck.setSelected(model.getCurrentProfile().isChatbotEnabled());
        chatbotCheck.addItemListener(new chatbotListener());

        // chatbot list
        chatbotList = new JList(chatBotModel.getQAList());
        chatbotList
                .addListSelectionListener(new chatbotListSelectionListener());
        JScrollPane chatbotListScroll = new JScrollPane(chatbotList);
        chatbotListScroll.setPreferredSize(new Dimension(400, 80));

        // chatbot buttons
        chatbotAddButton = new JButton("Add");
        chatbotAddButton
                .addActionListener(new chatbotManageButtonActionListener('A'));
        chatbotEditButton = new JButton("Edit");
        chatbotEditButton
                .addActionListener(new chatbotManageButtonActionListener('E'));
        chatbotRemoveButton = new JButton("Remove");
        chatbotRemoveButton
                .addActionListener(new chatbotRemoveButtonActionListener());
        setEnabledChatbotButtons(false);

        JPanel chatbotButtonsPanel = new JPanel();
        chatbotButtonsPanel.setBackground(colors.SECONDARY_COLOR_LT2);
        chatbotButtonsPanel.setAlignmentX(LEFT_ALIGNMENT);
        chatbotButtonsPanel.setLayout(new BoxLayout(chatbotButtonsPanel,
                BoxLayout.X_AXIS));
        chatbotButtonsPanel.add(chatbotAddButton);
        chatbotButtonsPanel.add(chatbotEditButton);
        chatbotButtonsPanel.add(chatbotRemoveButton);

        chatbotOptions = new JPanel();
        chatbotOptions.setBackground(colors.SECONDARY_COLOR_LT2);
        chatbotOptions.setAlignmentX(LEFT_ALIGNMENT);
        chatbotOptions.add(chatbotListScroll);
        chatbotOptions.add(chatbotButtonsPanel);
        if (chatbotCheck.isSelected())
        	chatbotOptions.setVisible(true);
        else
        	chatbotOptions.setVisible(false);

        JPanel chatbotPanel = new JPanel();
        chatbotPanel.setBackground(colors.SECONDARY_COLOR_LT2);
        chatbotPanel.setAlignmentX(LEFT_ALIGNMENT);
        chatbotPanel.setLayout(new BoxLayout(chatbotPanel, BoxLayout.Y_AXIS));
        chatbotPanel.add(chatbotCheck);
        chatbotPanel.add(chatbotOptions);

        /* SOUND NOTIFICATION */
        if (model.getCurrentProfile().isSoundsEnabled()) {
        	 soundCheck = new JCheckBox("Toggle Sound Notification",  new ImageIcon(this.getClass().getResource(
             "/images/menu/sound.png")));
            
       }
       
       else {
    	   soundCheck = new JCheckBox("Toggle Sound Notification",  new ImageIcon(this.getClass().getResource(
           "/images/menu/sound_mute.png")));
       }
        
        soundCheck.setBackground(colors.SECONDARY_COLOR_LT);
        soundCheck.setForeground(colors.SECONDARY_COLOR_DARK);
        soundCheck.setBorder(BorderFactory.createEmptyBorder(5,5,5,43));
        soundCheck.setSelected(model.getCurrentProfile().isSoundsEnabled());
        soundCheck.addItemListener(new soundListener());

        /* CHATLOG */
        if (model.getCurrentProfile().isChatLogEnabled()) {
        	 chatLogCheck = new JCheckBox("Toggle Chat Log",  new ImageIcon(this.getClass().getResource(
             "/images/menu/note_add.png")));
            
       }
       
       else {
    	   chatLogCheck = new JCheckBox("Toggle Chat Log",  new ImageIcon(this.getClass().getResource(
           "/images/menu/note_delete.png")));
       }
        
        chatLogCheck.setBackground(colors.SECONDARY_COLOR_LT);
        chatLogCheck.setForeground(colors.SECONDARY_COLOR_DARK);
        chatLogCheck.setBorder(BorderFactory.createEmptyBorder(5,5,5,96));
        chatLogCheck.setSelected(model.getCurrentProfile().isChatLogEnabled());
        chatLogCheck.addItemListener(new chatLogListener());
        
        /* CHAT WINDOW HISTORY */
        if (model.getCurrentProfile().isChatWindowHistoryEnabled()) {
        	chatWindowHistoryCheck =
                new JCheckBox("Toggle Chat Window History",  new ImageIcon(this.getClass().getResource(
                "/images/menu/application_form_add.png")));
            
        }
      
        else {
        	chatWindowHistoryCheck =
                new JCheckBox("Toggle Chat Window History",  new ImageIcon(this.getClass().getResource(
                "/images/menu/application_form_delete.png")));
        }
        
        chatWindowHistoryCheck.setBackground(colors.SECONDARY_COLOR_LT);
        chatWindowHistoryCheck.setForeground(colors.SECONDARY_COLOR_DARK);
        chatWindowHistoryCheck.setBorder(BorderFactory.createEmptyBorder(5,5,5,28));
        chatWindowHistoryCheck.setSelected(model.getCurrentProfile()
                .isChatWindowHistoryEnabled());
        chatWindowHistoryCheck
                .addItemListener(new chatWindowHistoryListener());
        
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        this.add(chatbotPanel);
        this.add(soundCheck);
        this.add(chatLogCheck);
        this.add(chatWindowHistoryCheck);
       
    }

    private void setEnabledChatbotButtons(boolean b) {
        chatbotEditButton.setEnabled(b);
        chatbotRemoveButton.setEnabled(b);
    }

    private class chatbotListener implements ItemListener {
        

		public void itemStateChanged(ItemEvent event) {
			if (event.getStateChange() == ItemEvent.SELECTED) {
                model.getCurrentProfile().setChatbotEnabled(true);
                BuddyList.chatbotEnabler.setSelected(true);
                chatbotOptions.setVisible(true);
                chatbotCheck.setIcon( new ImageIcon(this.getClass().getResource(
                "/images/menu/monitor_add.png")));
                BuddyList.chatbotEnabler.setIcon( new ImageIcon(this.getClass().getResource(
                "/images/menu/monitor_add.png")));
            } else {
                model.getCurrentProfile().setChatbotEnabled(false);
                chatbotOptions.setVisible(false);
                BuddyList.chatbotEnabler.setSelected(false);
                chatbotCheck.setIcon( new ImageIcon(this.getClass().getResource(
                "/images/menu/monitor_delete.png")));
                BuddyList.chatbotEnabler.setIcon( new ImageIcon(this.getClass().getResource(
                "/images/menu/monitor_delete.png")));
                
            }
            
			System.out.println("=================================");
	        System.out.println("           From the chatbotListener");
	        System.out.println("Chatbot =  " + model.getCurrentProfile().isChatbotEnabled());
	        System.out.println("Chat Log =  " + model.getCurrentProfile().isChatLogEnabled());
	        System.out.println("=================================");
            return;
			
		}
    }

    private class chatbotListSelectionListener implements ListSelectionListener {

        public void valueChanged(ListSelectionEvent e) {
            if (chatbotList.getSelectedIndex() > -1) {
                setEnabledChatbotButtons(true);
            } else {
                setEnabledChatbotButtons(false);
            }
        }

    }

    private class chatbotManageButtonActionListener implements ActionListener {
        ChatbotQA addChatbotOptions;
        char mode;

        public chatbotManageButtonActionListener(char mode) {
            this.mode = mode;
        }

        public void actionPerformed(ActionEvent e) {
            if (mode == 'A') { // add
                addChatbotOptions =
                        new ChatbotQA(new ChatbotQADataType(model), true);
            } else if (mode == 'E') { // edit
                addChatbotOptions =
                        new ChatbotQA(chatBotModel.getQAObject(chatbotList
                                .getSelectedIndex()), false);
            }
            addChatbotOptions.addWindowListener(new PopupWindowListener(
                    optionframe, addChatbotOptions));
            addChatbotOptions
                    .addWindowListener(new chatbotPopupWindowListener());
        }

        private class chatbotPopupWindowListener implements WindowListener {

            public void windowActivated(WindowEvent e) {
            }

            public void windowClosed(WindowEvent e) {
            }

            public void windowClosing(WindowEvent e) {
                if (mode == 'A') {
                    ChatbotQADataType QAObject =
                            addChatbotOptions.getQAObject();

//                    if (!QAObject.isEmpty()) {
                        try {
                            chatBotModel.addQA(QAObject);
                        } catch (ClassNotFoundException e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        } catch (SQLException e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        }
//                    }
                }

                chatbotList.setListData(chatBotModel.getQAList());
                chatbotList.updateUI();
            }

            public void windowDeactivated(WindowEvent e) {
            }

            public void windowDeiconified(WindowEvent e) {
            }

            public void windowIconified(WindowEvent e) {
            }

            public void windowOpened(WindowEvent e) {
            }

        }
    }

    private class chatbotRemoveButtonActionListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            int selected = chatbotList.getSelectedIndex();
            if (selected > -1) {
                try {
					chatBotModel.removeQA(selected);
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
                chatbotList.setListData(chatBotModel.getQAList());
                chatbotList.updateUI();
            }
            if (chatBotModel.getQASize() == 0) {
                setEnabledChatbotButtons(false);
            }
        }
    }

    private class soundListener implements ItemListener {
   

		public void itemStateChanged(ItemEvent event) {
			if (event.getStateChange() == ItemEvent.SELECTED) {
                model.getCurrentProfile().setSoundsEnabled(true);
                soundCheck.setIcon( new ImageIcon(this.getClass().getResource(
                "/images/menu/sound.png")));
            } else {
                model.getCurrentProfile().setSoundsEnabled(false);
                soundCheck.setIcon( new ImageIcon(this.getClass().getResource(
                "/images/menu/sound_mute.png")));
            }
			
			
			
		}
    }

    private class chatLogListener implements ItemListener {
    	public void itemStateChanged(ItemEvent event) {
			if (event.getStateChange() == ItemEvent.SELECTED) {
				 model.getCurrentProfile().setChatLogEnabled(true);
				 BuddyList.chatLogEnabler.setSelected(true);
				 chatLogCheck.setIcon( new ImageIcon(this.getClass().getResource(
	                "/images/menu/note_add.png")));
				 BuddyList.chatLogEnabler.setIcon( new ImageIcon(this.getClass().getResource(
	                "/images/menu/note_add.png")));
            } else {
            	model.getCurrentProfile().setChatLogEnabled(false);
            	BuddyList.chatLogEnabler.setSelected(false);
            	chatLogCheck.setIcon( new ImageIcon(this.getClass().getResource(
                "/images/menu/note_delete.png")));
            	BuddyList.chatLogEnabler.setIcon( new ImageIcon(this.getClass().getResource(
                "/images/menu/note_delete.png")));
            }
			
			   System.out.println("=================================");
		        System.out.println("           From the chatLogListener");
		        System.out.println("Chatbot =  " + model.getCurrentProfile().isChatbotEnabled());
		        System.out.println("Chat Log =  " + model.getCurrentProfile().isChatLogEnabled());
		        System.out.println("=================================");
			
		}
       
    }

    private class chatWindowHistoryListener implements ItemListener {
        

		public void itemStateChanged(ItemEvent event) {
			if (event.getStateChange() == ItemEvent.SELECTED) {
                model.getCurrentProfile().setChatWindowHistoryEnabled(true);
                chatWindowHistoryCheck.setIcon( new ImageIcon(this.getClass().getResource(
                "/images/menu/application_form_add.png")));
            } else {
                model.getCurrentProfile().setChatWindowHistoryEnabled(false);
                chatWindowHistoryCheck.setIcon( new ImageIcon(this.getClass().getResource(
                "/images/menu/application_form_delete.png")));
            }

            return;
			
		}
    }

}
