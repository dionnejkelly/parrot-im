package view.options;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import model.Model;
import model.dataType.UserData;
import model.enumerations.PopupEnableWindowType;
import view.styles.GPanel;
import view.styles.PopupEnableMainWindowListener;
import view.styles.PopupWindowListener;
import controller.MainController;

public class GroupChatConfigurationFrame extends JFrame {

    /**
     * variable model for extracting buddy list, each buddy's information and ,
     * conversation
     */
    protected Model model;

    private GPanel mainPanel;

    private JFrame mainFrame;
    private JFrame popUpFrame;

    private JButton inviteButton;
    private JButton cancelButton;

    private JLabel groupChatRoomLabel;

    private JLabel usersToInvite;

    private JTextArea messageText;
    private JComboBox groupRoom;
    private JComboBox usersGroup;

    private MainController controller;

    /**
     * if edit is true, use for adding a new Q/A if edit is false, use for
     * editing the existing data
     * 
     * @param dummyQ
     * @param edit
     * */

    public GroupChatConfigurationFrame(MainController c, Model model,
            JFrame chatFrame, JFrame buddyFrame) {
        this.mainFrame = chatFrame;
        popUpFrame = this;
        this.model = model;
        this.controller = c;

        this.addWindowListener(new PopupWindowListener(chatFrame, this));

        this.addWindowListener(new PopupWindowListener(buddyFrame, this));
        this.setTitle("Group Chat Configuration");

        setAllPanels();

        this.setPreferredSize(new Dimension(330, 180));
        setResizable(false);
        setLocationRelativeTo(null);

        pack();
        getContentPane().add(mainPanel);
        setVisible(true);
        setIconImage(new ImageIcon(this.getClass().getResource(
                "/images/mainwindow/logo.png")).getImage());
        this.setLocation(600, 200);
        this.addWindowListener(new PopupWindowListener(this.mainFrame,
                popUpFrame));
    }

    private void setAllPanels() {

        this.addWindowListener(new PopupEnableMainWindowListener(model,
                PopupEnableWindowType.GROUPCHAT));
        usersToInvite = new JLabel("Users to invite: ");
        usersToInvite.setForeground(model.primaryTextColor);

        usersGroup = new JComboBox(UserData.getOnlineBuddy());
        usersGroup.setPreferredSize(new Dimension(265, 25));
        usersGroup.addActionListener(new StyleListener());

        groupChatRoomLabel = new JLabel("Group Chat Room: ");
        groupChatRoomLabel.setForeground(model.primaryTextColor);

        inviteButton = new JButton("Invite");
        inviteButton.addActionListener(new inviteActionListener());
        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new cancelActionListener());

        groupRoom = new JComboBox(controller.getAvailableRoom());
        groupRoom.setPreferredSize(new Dimension(265, 25));
        groupRoom.addActionListener(new StyleListener());

        JPanel QButtonsPanel = new JPanel();
        QButtonsPanel.setAlignmentX(LEFT_ALIGNMENT);
        QButtonsPanel.setLayout(new BoxLayout(QButtonsPanel, BoxLayout.X_AXIS));
        QButtonsPanel.add(inviteButton);
        QButtonsPanel.add(cancelButton);

        /* WRAP UP */
        mainPanel = new GPanel();
        mainPanel.setGradientColors(model.primaryColor, model.secondaryColor);
        mainPanel.add(usersToInvite);
        mainPanel.add(usersGroup);
        mainPanel.add(groupChatRoomLabel);
        mainPanel.add(groupRoom);
        mainPanel.add(QButtonsPanel);
    }

    private class StyleListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {

        }
    }

    private class cancelActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            popUpFrame.dispose();
        }
    }

    public class TextBoxListener implements KeyListener {
        public void keyReleased(KeyEvent arg0) {
            if (messageText.getText().length() > 0) {
                inviteButton.setEnabled(true);
            }

            else {
                inviteButton.setEnabled(false);
            }

        }

        public void keyTyped(KeyEvent arg0) {
        }

        public void keyPressed(KeyEvent arg0) {
        }
    }

    private class inviteActionListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {

            Vector<String> onlineBuddy = UserData.getOnlineBuddy();

            String roomName = groupRoom.getSelectedItem().toString();

            // make sure that there are enough online buddies
            if (onlineBuddy.size() > 0) {
                String friend = onlineBuddy.get(usersGroup.getSelectedIndex());

                if (roomName.contains("Parrot")) {
                    controller.inviteFriend(friend, roomName
                            + "@conference.jabber.org");
                } else {
                    controller.inviteFriend(friend, roomName);
                }
            }
        }
    }
}
