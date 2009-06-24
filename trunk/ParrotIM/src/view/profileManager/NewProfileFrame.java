package view.profileManager;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.WindowConstants;

import controller.MainController;

import model.Model;

import view.mainwindow.MainWindow;
import view.styles.PopupWindowListener;

public class NewProfileFrame extends JFrame {
    private Model model;
    private MainController controller;
    private ProfileManager managerFrame;
    protected NewProfileFrame popup;

    private JPanel newProfPanel;
    private JTextField ProfNameField;
    private JPasswordField pwdField;
    private JComboBox serviceField;

    public NewProfileFrame(Model model, MainController controller, ProfileManager pManager) {
        this.model = model;
        this.controller = controller;
        managerFrame = pManager;
        popup = this;
        this.setAlwaysOnTop(true);
        this.setResizable(false);
        this.setLocationRelativeTo(managerFrame);

        setTitle("Create new Profile");
        newProfPanel = new JPanel();
        newProfPanel.setLayout(new BorderLayout());
        newProfPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        // ////////////TOP PART
        // account setupLabel setting Panel
        JPanel setupLabelPanel = new JPanel();
        GridLayout setupLabelLayout = new GridLayout(2, 1);
        setupLabelLayout.setVgap(10);
        setupLabelPanel.setLayout(setupLabelLayout);
        setupLabelPanel.setPreferredSize(new Dimension(75, 75));
        JLabel UNLabel = new JLabel("Profile Name:");
        JLabel pwdLabel = new JLabel("Password:");
        setupLabelPanel.add(UNLabel);
        setupLabelPanel.add(pwdLabel);

        pwdLabel.setVisible(false);

        // account setupField setting Panel
        JPanel setupFieldPanel = new JPanel();
        // BoxLayout setupFieldLayout = new BoxLayout(setupFieldPanel,
        // BoxLayout.Y_AXIS);
        GridLayout setupFieldLayout = new GridLayout(2, 1);
        setupFieldLayout.setVgap(5);
        setupFieldPanel.setLayout(setupFieldLayout);
        ProfNameField = new JTextField();
        ProfNameField.setPreferredSize(new Dimension(85, 20));
        pwdField = new JPasswordField();
        pwdField.setPreferredSize(new Dimension(100, 20));
        setupFieldPanel.add(ProfNameField);
        setupFieldPanel.add(pwdField);

        pwdField.setVisible(false);

        // account setup Panel
        JPanel setupPanel = new JPanel();
        setupPanel.setLayout(new BoxLayout(setupPanel, BoxLayout.X_AXIS));
        setupPanel.add(setupLabelPanel);
        setupPanel.add(setupFieldPanel);

        // *CENTRE PART : remember password + auto sign in
        // other Checkboxes setup Panel
        JPanel otherCheckPanel = new JPanel();
        otherCheckPanel.setLayout(new GridLayout(2, 1));
        JCheckBox enablePWDCheck = new JCheckBox();
        JCheckBox defaultProfCheck = new JCheckBox();
        otherCheckPanel.add(enablePWDCheck);
        otherCheckPanel.add(defaultProfCheck);

        // other Labels setup Panel
        GridLayout otherLabelLayout = new GridLayout(2, 1);
        JPanel otherLabelPanel = new JPanel();
        otherLabelPanel.setLayout(otherLabelLayout);
        otherLabelLayout.setVgap(7);
        JLabel enablePWDLabel = new JLabel("Enable Profile Password");
        JLabel defaultProfLabel = new JLabel("Default Profile");
        otherLabelPanel.add(enablePWDLabel);
        otherLabelPanel.add(defaultProfLabel);

        // other setups Panel
        JPanel otherSetupPanel = new JPanel();
        otherSetupPanel
                .setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        otherSetupPanel.setLayout(new FlowLayout());
        otherSetupPanel.add(otherCheckPanel);
        otherSetupPanel.add(otherLabelPanel);

        // BOTTOM PART : OK and Cancel Button
        // set ok-cancel button
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 10, 10));
        GridLayout buttonsLayout = new GridLayout(1, 2);
        buttonsLayout.setHgap(5);
        buttonsPanel.setLayout(buttonsLayout);

        // OK Button
        JButton okButton = new JButton("OK");
        okButton.addActionListener(new OkButtonListener());
        buttonsPanel.add(okButton);

        // Cancel Button
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new CancelButtonListener());
        buttonsPanel.add(cancelButton);

        // Add Content to main Panel and display
        newProfPanel.add(setupPanel, BorderLayout.NORTH);
        newProfPanel.add(otherSetupPanel, BorderLayout.CENTER);
        newProfPanel.add(buttonsPanel, BorderLayout.SOUTH);
        getContentPane().add(newProfPanel);
        pack();
        setVisible(true);
    }

    /*
     * SECTION: Listener classes
     */

    private class OkButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent evt) {
            String name = ProfNameField.getText();
            String password = String.copyValueOf(pwdField.getPassword());
            boolean defaultProfile = false;

            controller.addProfile(name, password, defaultProfile);

            popup.setVisible(false);
            popup.dispose();

            return;
        }
    }

    private class CancelButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent evt) {
            popup.setVisible(false);
            popup.dispose();

            return;
        }
    }
}
