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

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.SQLException;

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

import model.Model;

import view.options.modelstub.ChatbotQADataType;
import view.options.modelstub.model;
import view.styles.PopupWindowListener;

import controller.MainController;

public class FeaturesPanel extends JPanel {
    private model modelStub;

    private JFrame mainframe;

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

    public FeaturesPanel(MainController c, JFrame mainframe, Model model)
            throws ClassNotFoundException, SQLException {
        this.model = model;
        modelStub = new model(this.model);
        this.mainframe = mainframe;

        /* CHATBOT */
        chatbotCheck = new JCheckBox("Enable ChatBot");
        chatbotCheck.setSelected(model.getCurrentProfile().isChatbotEnabled());
        chatbotCheck.addChangeListener(new chatbotListener());

        // chatbot list
        chatbotList = new JList(modelStub.getQAList());
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
        chatbotButtonsPanel.setAlignmentX(LEFT_ALIGNMENT);
        chatbotButtonsPanel.setLayout(new BoxLayout(chatbotButtonsPanel,
                BoxLayout.X_AXIS));
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

        /* SOUND NOTIFICATION */
        soundCheck = new JCheckBox("Enable Sound Notification");
        soundCheck.setSelected(model.getCurrentProfile().isSoundsEnabled());
        soundCheck.addChangeListener(new soundListener());

        /* CHATLOG */
        chatLogCheck = new JCheckBox("Enable Chat Log");
        chatLogCheck.setSelected(model.getCurrentProfile().isChatLogEnabled());
        chatLogCheck.addChangeListener(new chatLogListener());

        /* CHAT WINDOW HISTORY */
        this.chatWindowHistoryCheck =
                new JCheckBox("Enable Chat Window History");
        this.chatWindowHistoryCheck.setSelected(model.getCurrentProfile()
                .isChatWindowHistoryEnabled());
        this.chatWindowHistoryCheck
                .addChangeListener(new chatWindowHistoryListener());

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

    private class chatbotListener implements ChangeListener {
        public void stateChanged(ChangeEvent e) {
            if (chatbotCheck.isSelected()) {
                model.getCurrentProfile().setChatbotEnabled(true);
                chatbotOptions.setVisible(true);
            } else {
                model.getCurrentProfile().setChatbotEnabled(false);
                chatbotOptions.setVisible(false);
            }
            
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
                        new ChatbotQA(modelStub.getQAObject(chatbotList
                                .getSelectedIndex()), false);
            }
            addChatbotOptions.addWindowListener(new PopupWindowListener(
                    mainframe, addChatbotOptions));
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

                    if (!QAObject.isEmpty()) {
                        try {
                            modelStub.addQA(QAObject);
                        } catch (ClassNotFoundException e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        } catch (SQLException e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        }
                    }
                }

                chatbotList.setListData(modelStub.getQAList());
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
					modelStub.removeQA(selected);
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
                chatbotList.setListData(modelStub.getQAList());
                chatbotList.updateUI();
            }
            if (modelStub.getQASize() == 0) {
                setEnabledChatbotButtons(false);
            }
        }
    }

    private class soundListener implements ChangeListener {
        public void stateChanged(ChangeEvent e) {
            if (soundCheck.isSelected()) {
                model.getCurrentProfile().setSoundsEnabled(true);
            } else {
                model.getCurrentProfile().setSoundsEnabled(false);
            }
        }
    }

    private class chatLogListener implements ChangeListener {
        public void stateChanged(ChangeEvent e) {
            if (soundCheck.isSelected()) {
                model.getCurrentProfile().setChatLogEnabled(true);
            } else {
                model.getCurrentProfile().setChatLogEnabled(false);
            }

        }
    }

    private class chatWindowHistoryListener implements ChangeListener {
        public void stateChanged(ChangeEvent e) {
            if (chatWindowHistoryCheck.isSelected()) {
                model.getCurrentProfile().setChatWindowHistoryEnabled(true);
            } else {
                model.getCurrentProfile().setChatWindowHistoryEnabled(false);
            }

            return;
        }
    }

}
