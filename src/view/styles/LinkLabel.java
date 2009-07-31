/* linkLabel.java
 * 
 * Programmed By:
 * 	   Vera Lukman
 * 	   William Chen
 *     
 * Change Log:
 *     2009-June-6, VL
 *         Initial write.The label is underlined when hovered.
 *     2009-June-13, WC
 *         Transferred file over to new project, ParrotIM.
 *     2009-June-19, VL
 *     	   The label is underlined on mouse out. It is bolded when hovered.
 *     2009-June-23, KF
 *         Naming convention updates. Changed all class names.
 *     2009-July-27, JF
 *     	   Changed texted colors to values from the model to be consistant with
 *     	   the colors in the rest of the program
 *         
 * Known Issues:
 *     1. The best look and feel for linkLabel is still on dispute
 * 
 * Copyright (C) 2009  Pirate Captains
 * 
 * Full license can be found in ParrotIM/LICENSE.txt.
 */

package view.styles;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

import model.Model;
import model.enumerations.UpdatedType;

/**
 * This object sets the style of the clickable labels on mainwindow (sign in
 * window).
 * 
 * It inherits JLabel methods and variables.
 */
public class LinkLabel extends JLabel implements Observer {
    /**
     * text is a String with the name of the label that will be shown on the GUI
     */
    private String text;

    private boolean enabled;

    private Model model;

    /**
     * LinkLabel constructor. It takes a String object as its argument. It sets
     * up the String to be underlined and show it on the GUI.
     * 
     * @param text
     */
    public LinkLabel(String text, boolean enable, Model model) {
        this.model = model;
        model.addObserver(this);

        this.enabled = enable;
        this.setHorizontalAlignment(CENTER);
        this.text = text;
        this.setEnabled(enabled);
        this.setBorder(BorderFactory.createEmptyBorder(3, 0, 3, 0));

        this.addMouseListener(new labelMouseListener(this));
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        if (enabled) {
            String hex =
                    Integer.toHexString(model.primaryTextColor.getRGB())
                            .substring(2, 8);
            this.setText("<html><Font Color=#" + hex + "><u>" + text
                    + "</u></Font></html>");
        } else {
            String hex =
                    Integer.toHexString(Color.GRAY.getRGB()).substring(2, 8);
            this.setText("<html><Font Color=#" + hex + "><u>" + text
                    + "</u></Font></html>");
        }
    }

    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Sets the behaviour with regard of mouse input and position.
     * 
     * This class inherits MouseListener methods and variables.
     */
    private class labelMouseListener implements MouseListener {
        /** label is a LinkLabel object. */
        private LinkLabel label;

        /**
         * labelMouseListener constructor. It takes a PmLabel object.
         * 
         * @param lbl
         */
        public labelMouseListener(LinkLabel lbl) {
            label = lbl;
        }

        /**
         * When the user hover on label, the text will be bolded. It takes a
         * MouseEvent argument
         * 
         * @param e
         */
        public void mouseEntered(MouseEvent e) {
            if (enabled) {
                String hex =
                        Integer.toHexString(model.primaryTextColor.getRGB())
                                .substring(2, 8);
                label.setText("<html><Font Color=#" + hex + "><b>" + label.text
                        + "</b></Font></html>");
            }
        }

        /**
         * When the user hover on label, the text will be underlined. It takes a
         * MouseEvent argument
         * 
         * @param e
         */
        public void mouseExited(MouseEvent e) {
            label.setEnabled(enabled);
            label.setBackground(model.primaryTextColor);
        }

        public void mousePressed(MouseEvent e) {
        }

        public void mouseReleased(MouseEvent e) {
        }

        public void mouseClicked(MouseEvent e) {
        }

    }

    public void update(Observable o, Object arg) {
        if (arg == UpdatedType.COLOR) {
            String hex =
                    Integer.toHexString(model.primaryTextColor.getRGB())
                            .substring(2, 8);
            this.setText("<html><Font Color=#" + hex + "><u>" + text
                    + "</u></Font></html>");
        }
    }
}
