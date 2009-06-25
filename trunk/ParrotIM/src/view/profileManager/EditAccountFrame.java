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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.WindowConstants;

import controller.MainController;

import model.Model;
import model.enumerations.ServerType;

import view.mainwindow.MainWindow;
import view.styles.PopupWindowListener;

public class EditAccountFrame extends JFrame {
    /*
     * LEAVING OUT EDIT ACCOUNT FUNCTION FOR ALPHA
     */

    private JPanel modAcctPanel;
    private Model model;
    private ProfileManager managerFrame;
    protected EditAccountFrame popup;

    private JTextField UNField;
    private JPasswordField pwdField;
    private JComboBox serviceField;
    private MainController controller; 
    
    /**
     * The profile selected from the profile selection window.
     */
    private String profile;

    // Instance 1 -- New Account (empty forms)
    public EditAccountFrame(Model model, ProfileManager pManager, MainController controller, String profile) {
        this.model = model;
        this.controller = controller;
        this.profile = profile;
        managerFrame = pManager;
        popup = this;
        this.setResizable(false);
        this.setLocationRelativeTo(managerFrame);

        setTitle("Add New Account");
        modAcctPanel = new JPanel();
        modAcctPanel.setLayout(new BorderLayout());
        modAcctPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        modAcctPanel.setPreferredSize(new Dimension(300, 300));
        // ////////////TOP PART
        // account setupLabel setting Panel
        JPanel setupLabelPanel = new JPanel();
        GridLayout setupLabelLayout = new GridLayout(3, 1);
        setupLabelLayout.setVgap(10);
        setupLabelPanel.setLayout(setupLabelLayout);
        setupLabelPanel.setPreferredSize(new Dimension(75, 75));
        JLabel serviceLabel = new JLabel("Service:");
        JLabel UNLabel = new JLabel("Username:");
        JLabel pwdLabel = new JLabel("Password:");
        setupLabelPanel.add(serviceLabel);
        setupLabelPanel.add(UNLabel);
        setupLabelPanel.add(pwdLabel);

        // account setupField setting Panel
        JPanel setupFieldPanel = new JPanel();
        // BoxLayout setupFieldLayout = new BoxLayout(setupFieldPanel,
        // BoxLayout.Y_AXIS);
        GridLayout setupFieldLayout = new GridLayout(3, 1);
        setupFieldLayout.setVgap(5);
        setupFieldPanel.setLayout(setupFieldLayout);
        serviceField = new JComboBox(model.getServerList());
        serviceField.setPreferredSize(new Dimension(170, 27));
        UNField = new JTextField();
        UNField.setPreferredSize(new Dimension(85, 20));
        pwdField = new JPasswordField();
        pwdField.setPreferredSize(new Dimension(100, 20));
        setupFieldPanel.add(serviceField);
        setupFieldPanel.add(UNField);
        setupFieldPanel.add(pwdField);

        // account setup Panel
        JPanel setupPanel = new JPanel();
        setupPanel.setLayout(new BoxLayout(setupPanel, BoxLayout.X_AXIS));
        setupPanel.add(setupLabelPanel);
        setupPanel.add(setupFieldPanel);

        // *CENTRE PART : remember password + auto sign in
        // other Checkboxes setup Panel
        JPanel otherCheckPanel = new JPanel();
        otherCheckPanel.setLayout(new GridLayout(2, 1));
        JCheckBox rememberPWDCheck = new JCheckBox();
        JCheckBox autoSignCheck = new JCheckBox();
        otherCheckPanel.add(rememberPWDCheck);
        otherCheckPanel.add(autoSignCheck);

        // other Labels setup Panel
        GridLayout otherLabelLayout = new GridLayout(2, 1);
        JPanel otherLabelPanel = new JPanel();
        otherLabelPanel.setLayout(otherLabelLayout);
        otherLabelLayout.setVgap(7);
        JLabel rememberPWDLabel = new JLabel("Remember password");
        JLabel autoSignLabel = new JLabel("Auto Sign-in");
        otherLabelPanel.add(rememberPWDLabel);
        otherLabelPanel.add(autoSignLabel);

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
        okButton.addActionListener(new AddListener());
        buttonsPanel.add(okButton);

        // Cancel Button
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new CancelListener());
        buttonsPanel.add(cancelButton);

        // Add Content to main Panel and display
        modAcctPanel.add(setupPanel, BorderLayout.NORTH);
        modAcctPanel.add(otherSetupPanel, BorderLayout.CENTER);
        modAcctPanel.add(buttonsPanel, BorderLayout.SOUTH);
        getContentPane().add(modAcctPanel);
        pack();
        setVisible(true);
    }

    // Instance 2 -- Edit Account (User/Pass already filled in, Disable username
    // box)
    /*
     * private editAccountFrame(Model model, String name) {
     * 
     * }
     */

    /*
     * SECTION: Add/Cancel Listeners
     */

    private class AddListener implements ActionListener {
        public void actionPerformed(ActionEvent evt) {
            String accountName = UNField.getText();
            String password = String.copyValueOf(pwdField.getPassword());
            ServerType server = (ServerType) serviceField.getSelectedItem();

            System.out.println("Server = " + server.toString());
            // Only accept non-empty strings for the account. An empty
            // string for the password is perfecty all right.
            if ((accountName != null && accountName.length()> 0) && server.toString().equals("Google Talk")) {
                controller.addAccount(profile, server, accountName,
                        password);
                
                popup.setVisible(false);
                popup.dispose();    
            }
            
            else {
            	String resultMessage = "Sorry for the inconvenience but for the Alpha Version, we are only supporting XMPP Protocol. Thank you for your co-operation.";
            	JOptionPane.showMessageDialog(null, resultMessage);
            }

            return;
        }
    }

    private class CancelListener implements ActionListener {
        public void actionPerformed(ActionEvent evt) {
            popup.setVisible(false);
            popup.dispose();

            return;
        }
    }
}
