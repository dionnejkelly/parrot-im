package view.options;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.SQLException;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import model.Model;
import model.dataType.ChatbotQADataType;
import model.enumerations.UpdatedType;
import view.styles.GPanel;
import view.styles.PopupWindowListener;

public class ChatbotQA extends JFrame implements Observer {
    private ChatbotQADataType QAObject;
    private GPanel mainPanel;
    private GPanel addNewQAPanel;
    private JFrame manageQAFrame;

    private JList QList;
    private JButton addQ;
    private JButton removeQ;

    private JList AList;
    private JButton addA;
    private JButton removeA;

    private JLabel questionLabel, answerLabel;

    private Model model;

    /**
     * if edit is true, use for adding a new Q/A if edit is false, use for
     * editing the existing data
     * 
     * @param dummyQ
     * @param edit
     * */
    public ChatbotQA(ChatbotQADataType QAObject, boolean add, Model model) {
        manageQAFrame = this;
        this.QAObject = QAObject;
        this.model = model;
        this.model.addObserver(this);

        if (add) { // QAObject is empty
            this.setTitle("Add New Questions");
        } else { // QAObject is not empty
            this.setTitle("Edit Questions");
        }

        setPanels();
        this.setPreferredSize(new Dimension(500, 380));
        setResizable(false);
        setLocationRelativeTo(null);

        pack();
        getContentPane().add(mainPanel);
        setVisible(true);
        setIconImage(new ImageIcon("src/images/mainwindow/logo.png").getImage());
    }

    public ChatbotQADataType getQAObject() {
        return QAObject;
    }

    private void setPanels() {

        /* QUESTIONS */
        questionLabel = new JLabel("Questions:");
        questionLabel.setForeground(model.primaryTextColor);
        QList = new JList(QAObject.getQuestions());
        QList.addListSelectionListener(new QuestionsListSelectionListener());
        JScrollPane QListScroll = new JScrollPane(QList);
        QListScroll.setPreferredSize(new Dimension(450, 100));

        addQ =
                new JButton("Add", new ImageIcon(this.getClass().getResource(
                        "/images/chatbot/pencil.png")));
        addQ.addActionListener(new addActionListener('Q'));
        removeQ =
                new JButton("Remove", new ImageIcon(this.getClass()
                        .getResource("/images/chatbot/eraser.png")));
        removeQ.addActionListener(new removeActionListener('Q'));
        removeQ.setEnabled(false);
        JPanel QButtonsPanel = new JPanel();
        QButtonsPanel.setOpaque(false);
        QButtonsPanel.setAlignmentX(LEFT_ALIGNMENT);
        QButtonsPanel.setLayout(new BoxLayout(QButtonsPanel, BoxLayout.X_AXIS));
        QButtonsPanel.add(addQ);
        QButtonsPanel.add(removeQ);

        JPanel questionPanel = new JPanel();
        questionPanel.setAlignmentX(LEFT_ALIGNMENT);
        questionPanel.setLayout(new BorderLayout());
        questionPanel.setOpaque(false);
        questionPanel.add(questionLabel, BorderLayout.NORTH);
        questionPanel.add(QListScroll, BorderLayout.CENTER);
        questionPanel.add(QButtonsPanel, BorderLayout.SOUTH);

        /* ANSWERS */
        answerLabel = new JLabel("Answers:");
        answerLabel.setForeground(model.primaryTextColor);
        AList = new JList(QAObject.getAnswers());
        AList.addListSelectionListener(new AnswersListSelectionListener());
        JScrollPane AListScroll = new JScrollPane(AList);
        AListScroll.setPreferredSize(new Dimension(450, 100));

        addA =
                new JButton("Add", new ImageIcon(this.getClass().getResource(
                        "/images/chatbot/pencil.png")));
        addA.addActionListener(new addActionListener('A'));
        removeA =
                new JButton("Remove", new ImageIcon(this.getClass()
                        .getResource("/images/chatbot/eraser.png")));
        removeA.addActionListener(new removeActionListener('A'));
        removeA.setEnabled(false);
        JPanel AButtonsPanel = new JPanel();
        AButtonsPanel.setAlignmentX(LEFT_ALIGNMENT);
        AButtonsPanel.setLayout(new BoxLayout(AButtonsPanel, BoxLayout.X_AXIS));
        AButtonsPanel.setOpaque(false);
        AButtonsPanel.add(addA);
        AButtonsPanel.add(removeA);

        JPanel answerPanel = new JPanel();
        answerPanel.setAlignmentX(LEFT_ALIGNMENT);
        answerPanel.setLayout(new BorderLayout());
        answerPanel.setOpaque(false);
        answerPanel.add(answerLabel, BorderLayout.NORTH);
        answerPanel.add(AListScroll, BorderLayout.CENTER);
        answerPanel.add(AButtonsPanel, BorderLayout.SOUTH);

        /* WRAP UP */
        mainPanel = new GPanel();
        GridLayout mainLayout = new GridLayout(2, 1);
        mainLayout.setVgap(10);
        mainPanel.setLayout(mainLayout);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setGradientColors(model.primaryColor, model.secondaryColor);
        mainPanel.setAlignmentX(LEFT_ALIGNMENT);
        mainPanel.add(questionPanel);
        mainPanel.add(answerPanel);
    }

    private class QuestionsListSelectionListener implements
            ListSelectionListener {

        public void valueChanged(ListSelectionEvent e) {
            if (QList.getSelectedIndex() > -1) {
                removeQ.setEnabled(true);
            } else {
                removeQ.setEnabled(false);
            }
        }
    }

    private class AnswersListSelectionListener implements ListSelectionListener {

        public void valueChanged(ListSelectionEvent e) {
            if (AList.getSelectedIndex() > -1) {
                removeA.setEnabled(true);
            } else {
                removeA.setEnabled(false);
            }
        }
    }

    private class addActionListener implements ActionListener {
        char mode;

        public addActionListener(char mode) {
            this.mode = mode;
        }

        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            // execute JDialog
            JFrame popupOption = new addNewQAFrame(mode);
            popupOption.addWindowListener(new PopupWindowListener(
                    manageQAFrame, popupOption));
        }
    }

    private class removeActionListener implements ActionListener {
        char mode;

        public removeActionListener(char mode) {
            this.mode = mode;
        }

        public void actionPerformed(ActionEvent e) {
            if (mode == 'Q') {
                try {
                    QAObject.removeQuestion(QList.getSelectedIndex());
                } catch (SQLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                } catch (ClassNotFoundException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                QList.setListData(QAObject.getQuestions());
                QList.updateUI();
            } else if (mode == 'A') {
                try {
                    QAObject.removeAnswer(AList.getSelectedIndex());
                } catch (ClassNotFoundException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                } catch (SQLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                AList.setListData(QAObject.getAnswers());
                AList.updateUI();
            }
        }
    }

    private class addNewQAFrame extends JFrame implements Observer {
        private JTextField field;
        private JFrame frame;
        private JButton okButton;
        private JLabel label;

        public addNewQAFrame(char mode) {
            frame = this;

            /* PROMPT */
            label = new JLabel();
            label.setOpaque(false);
            label.setForeground(model.primaryTextColor);
            if (mode == 'Q') {
                label.setText("Please type in your question: ");
            } else if (mode == 'A') {
                label.setText("Please type in your answer: ");
            }

            field = new JTextField();
            field.addKeyListener(new newQAfieldKeyListener());
            field.setPreferredSize(new Dimension(260, 20));

            okButton =
                    new JButton("OK", new ImageIcon(this.getClass()
                            .getResource("/images/buddylist/button_ok.png")));
            okButton.addActionListener(new okActionListener(mode));
            okButton.setEnabled(false);
            JButton cancelButton =
                    new JButton("Cancel", new ImageIcon(this.getClass()
                            .getResource("/images/mainwindow/cancel.png")));
            cancelButton.addActionListener(new cancelActionListener());
            JPanel buttonPanel = new JPanel();
            buttonPanel.setOpaque(false);
            buttonPanel.add(okButton);
            buttonPanel.add(cancelButton);

            addNewQAPanel = new GPanel();
            addNewQAPanel.setGradientColors(model.primaryColor,
                    model.secondaryColor);
            addNewQAPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20,
                    20));
            addNewQAPanel.add(label);
            addNewQAPanel.add(field);
            addNewQAPanel.add(buttonPanel);

            // this.addWindowListener(new newQAWindowListener());
            this.getContentPane().add(addNewQAPanel);
            this.setLocationRelativeTo(manageQAFrame);
            this.setResizable(false);
            this.setPreferredSize(new Dimension(300, 150));
            this.pack();
            this.setVisible(true);
            this.setIconImage(new ImageIcon("src/images/mainwindow/logo.png")
                    .getImage());
        }

        // private class newQAWindowListener implements WindowListener {
        //
        // public void windowActivated(WindowEvent e) {}
        // public void windowClosed(WindowEvent e) {
        // }
        // public void windowClosing(WindowEvent e) {}
        // public void windowDeactivated(WindowEvent e) {}
        // public void windowDeiconified(WindowEvent e) {}
        // public void windowIconified(WindowEvent e) {}
        // public void windowOpened(WindowEvent e) {}

        // }
        private class cancelActionListener implements ActionListener {

            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        }

        private class okActionListener implements ActionListener {
            private char mode;

            public okActionListener(char mode) {
                this.mode = mode;
            }

            public void actionPerformed(ActionEvent e) {
                // if (field.getText().length()!=0)
                if (mode == 'Q') {
                    try {
                        QAObject.addQuestion(field.getText());
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    } catch (ClassNotFoundException e1) {
                        e1.printStackTrace();
                    }
                    QList.setListData(QAObject.getQuestions());
                    QList.updateUI();
                } else if (mode == 'A') {
                    try {
                        QAObject.addAnswer(field.getText());
                    } catch (ClassNotFoundException e1) {
                        e1.printStackTrace();
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                    AList.setListData(QAObject.getAnswers());
                    AList.updateUI();
                }
                frame.dispose();
            }
        }

        private class newQAfieldKeyListener implements KeyListener {
            public void keyPressed(KeyEvent arg0) {
            }

            public void keyReleased(KeyEvent arg0) {
                if (field.getText().length() > 0) {
                    okButton.setEnabled(true);
                } else {
                    okButton.setEnabled(false);
                }
            }

            public void keyTyped(KeyEvent arg0) {
            }
        }

        public void update(Observable o, Object arg) {
            if (arg == UpdatedType.COLOR) {
                label.setForeground(model.primaryTextColor);
                label.updateUI();
            }
        }
    }

    public void update(Observable o, Object arg) {
        if (arg == UpdatedType.COLOR) {
            mainPanel.setGradientColors(model.primaryColor,
                    model.secondaryColor);
            addNewQAPanel.setGradientColors(model.primaryColor,
                    model.secondaryColor);
            questionLabel.setForeground(model.primaryTextColor);
            answerLabel.setForeground(model.primaryTextColor);

            mainPanel.updateUI();
            addNewQAPanel.updateUI();
        }
    }
}
