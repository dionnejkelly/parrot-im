package testPackage;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

import testPackage.chatwindow.*;

class View extends JFrame {
    
    //... Components
    private JTextField m_userInputTf = new JTextField(5);
    private JTextField m_totalTf     = new JTextField(20);
    private JButton    m_multiplyBtn = new JButton("Multiply");
    private JButton    m_clearBtn    = new JButton("Clear");
    
    private Model model;

    private chatwindow chatwindow;
    
    //======================================================= constructor
    /** Constructor */
    View(Model aModel) {
        //... Set up the logic
        model = aModel;
        model.reset();

	chatwindow = new chatwindow(model);
        
        //... Initialize components
        m_totalTf.setText(model.getPreviousText());
        m_totalTf.setEditable(false);
        
        //... Layout the components.      
        JPanel content = new JPanel();
        content.setLayout(new FlowLayout());
        content.add(new JLabel("Input"));
        content.add(m_userInputTf);
        content.add(m_multiplyBtn);
        content.add(new JLabel("Total"));
        content.add(m_totalTf);
        content.add(m_clearBtn);
        
        //... finalize layout
        this.setContentPane(content);
        this.pack();
        
        this.setTitle("Simple Calc - MVC");
        // The window closing event should probably be passed to the 
        // Controller in a real program, but this is a short example.
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    void reset() {
        submitMessage("");
    }
    
    String getUserInput() {
        return m_userInputTf.getText();
    }
    
    void submitMessage(String newText) {
        chatwindow.submitMessage(newText);
    }
    
    void showError(String errMessage) {
        JOptionPane.showMessageDialog(this, errMessage);
    }
    
    void addSendListener(ActionListener sendAL) {
        chatwindow.mainPanel.chatPanel.getSendButton().addActionListener(sendAL);
    }
    
    public String getMessageToSend() {
       return chatwindow.mainPanel.chatPanel.getMessageToSend();
    }
}
