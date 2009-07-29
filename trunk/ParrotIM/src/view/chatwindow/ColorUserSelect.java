/* ColorUserSelect.java
 * 
 * Programmed By:
 *     Jihoon Choi
 *     
 * Change Log:
 *         
 * Known Issues:
 *     none
 *     
 * Copyright (C) 2009  Pirate Captains
 * 
 * License: GNU General Public License version 2.
 * Full license can be found in ParrotIM/LICENSE.txt.
 */

package view.chatwindow;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import model.Model;
import model.enumerations.UpdatedType;
import view.styles.GPanel;

/**
 * The ColorUserSelect contains the panel that holds the integrity of the label
 * and JColorChooser together.
 * 
 * This object inherits from JPanel
 */

public class ColorUserSelect extends GPanel implements ChangeListener, Observer {

    /** A chooser GUI. */

    protected JColorChooser colorChooser;

    /** A banner label. */

    protected JLabel banner;

    /** Buttons label. */

    protected JPanel buttonPanel;

    /** User's color preference. */
    protected Color userColor;

    /** Default User's font color. */
    public String hexColor = "#000000";

    /** Color Chooser's main frame. */
    private JFrame mainFrame;

    private JButton colorButton;

    private Model model;

    /**
     * This is the constructor of the ColorUserSelect.
     */

    public ColorUserSelect(JFrame frame, JButton colorButton, Model model) {

        super(new BorderLayout());

        this.colorButton = colorButton;
        this.mainFrame = frame;
        this.model = model;
        model.addObserver(this);

        this.setGradientColors(model.primaryColor, model.secondaryColor);

        // Set up the banner at the top of the window
        banner =
                new JLabel("Welcome to the Parrot IM Color Zone!",
                        SwingConstants.CENTER);
        banner.setForeground(Color.BLACK);
        banner.setBackground(Color.WHITE);
        banner.setOpaque(true);
        banner.setFont(new Font("SansSerif", Font.BOLD, 21));
        banner.setPreferredSize(new Dimension(400, 50));

        JPanel bannerPanel = new JPanel(new BorderLayout());
        bannerPanel.setOpaque(false);
        bannerPanel.add(banner, BorderLayout.CENTER);
        bannerPanel.setBorder(BorderFactory.createTitledBorder("Font Preview"));

        // Set up color chooser for setting text color
        colorChooser = new JColorChooser(banner.getForeground());
        colorChooser.setOpaque(false);
        colorChooser.getSelectionModel().addChangeListener(this);
        colorChooser.setBorder(BorderFactory
                .createTitledBorder("Choose Text Color"));
        colorChooser.setPreviewPanel(banner);

        buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 40, 10, 40));
        GridLayout buttonsLayout = new GridLayout(1, 2);
        buttonsLayout.setHgap(5);
        buttonPanel.setLayout(buttonsLayout);
        buttonPanel.setPreferredSize(new Dimension(170, 35));

        JButton okButton =
                new JButton("DONE", new ImageIcon(this.getClass().getResource(
                        "/images/buddylist/button_ok.png")));

        okButton.addActionListener(new okButtonListener());

        buttonPanel.add(okButton);

        add(bannerPanel, BorderLayout.CENTER);
        add(colorChooser, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.PAGE_END);
    }

    /**
     * Check for the user's color preference.
     * 
     * @param e
     */

    class okButtonListener implements ActionListener {

        /**
         * Listens for the uesr's event.
         * 
         * @param event
         */

        public void actionPerformed(ActionEvent event) {
            mainFrame.dispose();
        }
    }

    /**
     * Check for the user's color preference.
     * 
     * @param e
     */

    public void stateChanged(ChangeEvent e) {
        userColor = colorChooser.getColor();
        banner.setForeground(userColor);

        // System.out.println("User Red color = " + newColor.getRed());
        // System.out.println("User Green color = " + newColor.getGreen());
        // System.out.println("Hex = " + getColorHex());

        hexColor = getColorHex();
        colorButton.setBackground(userColor);
    }

    /**
     * Returns user's color preference.
     * 
     * @return Color
     */

    public Color getUserColor() {
        return userColor;
    }

    public String getUserRedHex() {
        return Integer.toHexString(userColor.getRed());
    }

    public String getUserGreenHex() {
        return Integer.toHexString(userColor.getGreen());
    }

    public String getUserBlueHex() {
        return Integer.toHexString(userColor.getBlue());
    }

    public String getColorHex() {
        String red = getUserRedHex();
        if (red.length() == 1) {
            red = "0" + red;
        }

        String green = getUserGreenHex();
        if (green.length() == 1) {
            green = "0" + green;
        }

        String blue = getUserBlueHex();
        if (blue.length() == 1) {
            blue = "0" + blue;
        }

        return "#" + red + green + blue;
    }

    public void update(Observable o, Object arg) {
        if (arg == UpdatedType.COLOR) {
            setGradientColors(model.primaryColor, model.secondaryColor);
            updateUI();
        }
    }
}
