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
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import view.styles.PopupEnableMainWindowListener;

import model.Model;
import model.enumerations.PopupEnableWindowType;

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

    /**
     * aboutFrame constructor. This window describes about ParrotIM
     */
    public AboutFrame(Model model) {
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.model = model;
        this.addWindowListener(new PopupEnableMainWindowListener(
                model, PopupEnableWindowType.ABOUT));
        this.setTitle("About ParrotIM");
        this.setPreferredSize(new Dimension(300, 500));
        this.setResizable(false);

        // our logo + parrotIM label
        JPanel topPanel = new JPanel();
        topPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        topPanel.setLayout(new BorderLayout());
        JLabel avatarDisplay = new JLabel();
        avatarDisplay.setHorizontalAlignment(SwingConstants.CENTER);
        ImageIcon avatar =
                new ImageIcon(getClass().getClassLoader().getResource(
                        "images/buddylist/logoBox.png"));
        avatarDisplay.setIcon(avatar);
        topPanel.add(avatarDisplay, BorderLayout.NORTH);

        JLabel parrotLabel = new JLabel("(c) Pirate Captains 2009");
        parrotLabel.setHorizontalAlignment(SwingConstants.CENTER);
        topPanel.add(parrotLabel, BorderLayout.CENTER);

        // informations
        JPanel informations = new JPanel();
        String[] infoArray =
                new String[] {
                        "This software is developed by", "Rakan Alkheliwi",
                        "William (Wei-Lun) Chen", "Jihoon Choi",
                        "Kevin Fahy", "Jordan Fox", "Chenny Huang",
                        "Vera Lukman", "Ahmad Sidiqi", "Aaron Siu",
                        "Wei Zhang" };
        GridLayout infoLayout = new GridLayout(infoArray.length, 1);
        informations.setLayout(infoLayout);
        // infoLayout.setVgap(3);

        for (int pos = 0; pos < infoArray.length; pos++) {
            JLabel infoLabel = new JLabel(infoArray[pos]);
            infoLabel.setHorizontalAlignment(SwingConstants.CENTER);
            informations.add(infoLabel);
        }

        JPanel infoLabelAll = new JPanel();
        infoLabelAll.setLayout(new BorderLayout());
        infoLabelAll.add(informations, BorderLayout.NORTH);

        // setting aboutPanel
        JPanel aboutPanel = new JPanel();
        aboutPanel.setLayout(new BorderLayout());
        aboutPanel.setBorder(BorderFactory
                .createEmptyBorder(20, 10, 20, 10));
        aboutPanel.add(topPanel, BorderLayout.NORTH);
        aboutPanel.add(infoLabelAll, BorderLayout.CENTER);

        // setting frame
        this.getContentPane().add(aboutPanel);
        this.pack();
        this.setVisible(true);
    }
}
