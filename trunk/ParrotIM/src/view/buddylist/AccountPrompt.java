package view.buddylist;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

import view.styles.PopupWindowListener;

import model.dataType.AccountData;

public class AccountPrompt extends JFrame {
    
    private DefaultComboBoxModel accountsModel;
    
    private JComboBox accountSelect;
    
    private JFrame popup;
    
    private JButton okButton;
    
    public AccountPrompt(ArrayList<AccountData> accounts, JFrame mainFrame) {

        popup = this;
        Vector<Object> accountList = new Vector<Object>(accounts);
        JPanel mainPanel = new JPanel();
        JPanel accountPanel = new JPanel();
        JPanel buttonsPanel = new JPanel();
        this.addWindowListener(new PopupWindowListener(mainFrame, this));
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(250, 100));
        setTitle("Choose an Account");
        setResizable(false);
        setIconImage(new ImageIcon("imagesimage/mainwindow/logo.png")
                .getImage());
        
        mainPanel.setLayout(new BorderLayout());
        
        accountsModel = new DefaultComboBoxModel(accountList);
        accountSelect = new JComboBox(accountsModel);
        //accountSelect.addActionListener((new CloseMe()));
        accountSelect.setPreferredSize(new Dimension(200, 30));
        //accountSelect.setAlignmentY(Component.CENTER_ALIGNMENT);
        
        accountPanel.setOpaque(false);
        accountPanel.setLayout(new BoxLayout(accountPanel, BoxLayout.Y_AXIS));
        accountPanel.add(accountSelect);
        
        okButton = new JButton("OK", new ImageIcon(this.getClass()
                .getResource("/images/buddylist/button_ok.png")));
        okButton.addActionListener(new CloseMe());

        buttonsPanel.setOpaque(false);
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(0, 90, 0, 0));
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.X_AXIS));
        buttonsPanel.add(okButton);
                
        mainPanel.add(accountPanel, BorderLayout.NORTH);
        mainPanel.add(buttonsPanel, BorderLayout.SOUTH);
        
        this.add(mainPanel);
        
        pack();
        setVisible(true);
    }
    
    public AccountData getSelectedAccount() {
        return (AccountData) accountSelect.getSelectedItem();
    }
    
    public void addAccountListener(ActionListener listener) {
        //accountSelect.addActionListener(listener);
        okButton.addActionListener(listener);
        
        return;
    }
    
    private class CloseMe implements ActionListener {
        public void actionPerformed(ActionEvent evt) {
            popup.removeAll();
            popup.dispose();
        }
    }       

}
