/* guestAccountFrame.java
 * 
 * Programmed By:
 * 	   Vera Lukman
 *     
 * Change Log:
 *     2009-June-25, VL
 *         Initial write.
 *         
 * Known Issues:
 *     None
 * 
 * Copyright (C) 2009  Pirate Captains
 * 
 * Full license can be found in ParrotIM/LICENSE.txt.
 */

package view.mainwindow;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import model.Model;
import model.enumerations.PopupEnableWindowType;
import view.styles.GPanel;
import view.styles.PopupEnableMainWindowListener;

/**
 * The container frame of About ParrotIM Window. User can view the details about
 * ParrotIM
 * 
 * This object inherits JFrame variables and methods
 */
public class AboutFrame extends JFrame {
    /**
     * model allows aboutFrame to store the state of aboutFrame (ie. whether it
     * is opened or not).
     */
    Model model;

    private JLabel parrotLabel;

    private JPanel informations;

    /**
     * aboutFrame constructor. This window describes about ParrotIM
     */
    public AboutFrame(Model model) {
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.model = model;
        this.addWindowListener(new PopupEnableMainWindowListener(model,
                PopupEnableWindowType.ABOUT));
        this.setTitle("About ParrotIM");
        this.setPreferredSize(new Dimension(300, 500));
        this.setResizable(false);

        // our logo + parrotIM label
        JPanel topPanel = new JPanel();
        topPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        topPanel.setLayout(new BorderLayout());
        topPanel.setOpaque(false);
        JLabel avatarDisplay = new JLabel();
        avatarDisplay.setHorizontalAlignment(SwingConstants.CENTER);
        ImageIcon avatar =
                new ImageIcon(getClass().getClassLoader().getResource(
                        "images/buddylist/logoBox.png"));
        avatarDisplay.setIcon(avatar);
        topPanel.add(avatarDisplay, BorderLayout.NORTH);

        parrotLabel = new JLabel("(c) Pirate Captains 2009");
        parrotLabel.setForeground(model.primaryTextColor);
        parrotLabel.setOpaque(false);
        parrotLabel.setHorizontalAlignment(SwingConstants.CENTER);
        topPanel.add(parrotLabel, BorderLayout.CENTER);

        // informations
        informations = new JPanel();
        String[] infoArray =
                new String[] { "This software is developed by",
                        "Rakan Alkheliwi", "William (Wei-Lun) Chen",
                        "Jihoon Choi", "Kevin Fahy", "Jordan Fox",
                        "Chenny Huang", "Vera Lukman", "Ahmad Sidiqi",
                        "Aaron Siu", "Wei Zhang" };
        GridLayout infoLayout = new GridLayout(infoArray.length, 1);
        informations.setLayout(infoLayout);
        informations.setOpaque(false);
        // infoLayout.setVgap(3);

        for (String element : infoArray) {
            JLabel infoLabel = new JLabel(element);
            infoLabel.setForeground(model.primaryTextColor);
            infoLabel.setHorizontalAlignment(SwingConstants.CENTER);
            informations.add(infoLabel);
        }

        JPanel infoLabelAll = new JPanel();
        infoLabelAll.setLayout(new BorderLayout());
        infoLabelAll.setOpaque(false);
        infoLabelAll.add(informations, BorderLayout.NORTH);

        // setting aboutPanel
        final GPanel aboutPanel = new GPanel();
        aboutPanel.setLayout(new BorderLayout());
        aboutPanel.setGradientColors(model.primaryColor, model.secondaryColor);
        aboutPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        aboutPanel.add(topPanel, BorderLayout.NORTH);
        aboutPanel.add(infoLabelAll, BorderLayout.CENTER);

        // setting frame
        this.getContentPane().add(aboutPanel);
        this.pack();
        this.setVisible(true);
        setIconImage(new ImageIcon("src/images/mainwindow/logo.png").getImage());
    }
}
