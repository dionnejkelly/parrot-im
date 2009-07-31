/* accInfo.java
 * 
 * Programmed By:
 *     Aaron Siu (ANS)
 *     Jordan Fox
 *     Jihoon Choi
 *     Vera Lukman
 *     William Chen
 *     Kevin Fahy
 *     
 * Change Log:
 *     2009-June-1, ANS
 *         Initial write.
 *     2009-June-2, JF
 *         Set the background, added avatar, status message, display name.
 *     2009-June-4, JF
 *         Integrated with control.
 *     2009-June-6, JC
 *         Integrated status message with control. 
 *         Now able to set status message on the actual server.
 *     2009-June-7, VL
 *         Modified status message look and feel.
 *         Now users can edit the status message and hit enter to change it.
 *     2009-June-13, VL
 *         Fixed bug on pmLabel.
 *     2009-June-13, WC
 *         Transferred file over to new project, ParrotIM.
 *     2009-June-17, VL
 *         Added presence JCombobox.
 *     2009-June-17, JC
 *         Integrated presence with control.
 *         Now users are able to set their status mode.
 *     2009-June-19, KF
 *         Updated name to profile name.
 *     2009-June-22, JF
 *         Changed avatar to parrot-IM logo.
 *     2009-June-22, VL
 *         Now users click anywhere outside status message box to change it.
 *     2009-June-23, VL
 *         Now users can clicked on the display picture to modify it.
 *         
 * Known Issues:
 *     None
 * 
 * Copyright (C) 2009  Pirate Captains
 * 
 * Full license can be found in ParrotIM/LICENSE.txt.
 */

package view.buddylist;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import model.Model;
import model.enumerations.ServerType;
import model.enumerations.UpdatedType;
import view.styles.AvatarLabel;
import view.styles.GPanel;
import view.styles.PmLabel;
import view.styles.StatusCombo;
import controller.MainController;
import controller.services.TwitterManager;

/**
 * accountInfo display user name, avatar picture, account information for Parrot
 * IM users.
 */
public class AccountInfo extends GPanel implements Observer {
    /*
     * TODO AccInfo has: WEST -- Avatar (and avatar settings) Center -- New
     * embedded Panel Top: User Display name Bottom: Status, and status messages
     * (if applicable)
     */

    // Selection
    // I - non-static member
    /**
     * avatarDisplay is for displaying user picture.
     */
    public AvatarLabel avatarDisplay;
    /**
     * displayName displays user's name.
     */
    JLabel displayName;
    /**
     * variable to handles all inputs from user interaction
     */
    MainController chatClient;

    /**
     * user status message
     */
    public PmLabel statusMessage;
    /**
     * text area
     */
    protected JTextArea textArea;
    /**
     * text
     */
    protected String text;

    public StatusCombo presence;
    private JPanel presencePanel;
    private Dimension dimension;
    /**
     * variable model for extracting buddy list, each buddy's information and ,
     * conversation
     */
    private Model model;

    // SELECTION
    // II-Constructors
    /**
     * Account information area, display user's information and avatar picture.
     * 
     * @param c
     * @param model
     * @throws SQLException
     */
    public AccountInfo(MainController c, Model model)
            throws ClassNotFoundException, SQLException {
        this.model = model;
        this.chatClient = c;
        setGradientColors(Color.BLACK, model.primaryColor);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
        setLayout(new BorderLayout());

        // user diplay picture
        try {
            avatarDisplay =
                    new AvatarLabel(chatClient, model, model
                            .getAvatarDirectory(model.getCurrentProfile()
                                    .getName()), 80);
        } catch (SQLException e1) {
            e1.printStackTrace();
        }

        JLabel name = new JLabel(model.getCurrentProfile().getName());
        name.setFont(new Font("Arial", Font.BOLD, 17));
        name.setForeground(Color.white);

        // Allowing users to change their status.
        statusMessage = new PmLabel(c);
        statusMessage.setFont(new Font("Arial", Font.BOLD, 15));
        statusMessage.setOpaque(false);
        if (!model.getCurrentProfile().getName().equals("Guest")) {
            statusMessage.setText(model.getStatusMessage(model
                    .getCurrentProfile().getName()));
        }
        if (model.getCurrentProfile().getAccountFromServer(ServerType.TWITTER) != null) {
            TwitterManager twitterMan =
                    (TwitterManager) model.getCurrentProfile()
                            .getAccountFromServer(ServerType.TWITTER)
                            .getConnection();

            statusMessage.setText(twitterMan.getMyRecentStatus());
        }
        statusMessage.changePM(false, false);
        presence = new StatusCombo(chatClient, false);
        presence.setForeground(new Color(38, 112, 140));
        // presence.setBackground(new Color(224, 224, 224));
        if (!model.getCurrentProfile().getName().equals("Guest")) {
            presence.setSelectedIndex(model.getStatus(model.getCurrentProfile()
                    .getName()));
        }

        // name, status message (personal message) and status
        JPanel textInfo = new JPanel();
        textInfo.setBorder(BorderFactory.createEmptyBorder(0, 0, 4, 0));
        textInfo.setLayout(new BoxLayout(textInfo, BoxLayout.Y_AXIS));
        textInfo.setOpaque(false);
        textInfo.add(name);
        textInfo.add(statusMessage);
        // textInfo.add(presence);

        // combobox to change presence
        presencePanel = new JPanel();
        presencePanel.setOpaque(false);
        presencePanel.add(presence);

        JPanel info = new JPanel();
        info.setOpaque(false);
        info.setLayout(new BorderLayout());
        info.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 0));
        info.add(textInfo, BorderLayout.NORTH);
        info.add(presencePanel, BorderLayout.WEST);

        add(avatarDisplay, BorderLayout.WEST);
        add(info, BorderLayout.CENTER);
        this.addMouseListener(new statusMouseListener());
        this.dimension = new Dimension(this.getWidth(), 95);
        this.setMaximumSize(dimension);
        this.setMinimumSize(dimension);
        this.setPreferredSize(dimension);
        model.addObserver(this);
    }

    // Section
    // III - Accessors and Mutators

    public void update(Observable o, Object arg) {
        if (arg == UpdatedType.COLOR) {
            setGradientColors(Color.BLACK, model.primaryColor);
            updateUI();
        }

        return;
    }

    /**
     * mouse listener for mouse to do actions
     * 
     */
    private class statusMouseListener implements MouseListener {

        /*
         * mouse click action
         */
        public void mouseClicked(MouseEvent e) {
            if (statusMessage.isEditable()) {
                statusMessage.changePM(false, true);
            }
        }

        /*
         * mouse entered action mouse exited action mouse pressed action mouse
         * released action
         */
        public void mouseEntered(MouseEvent e) {
        }

        public void mouseExited(MouseEvent e) {
        }

        public void mousePressed(MouseEvent e) {
        }

        public void mouseReleased(MouseEvent e) {
        }

    }
}
