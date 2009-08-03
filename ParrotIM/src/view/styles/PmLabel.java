/* pmLabel.java
 * 
 * Programmed By:
 * 	   Vera Lukman
 *     Jihoon Choi
 *     Jordan Fox
 * 	   William Chen
 *     
 * Change Log:
 *     2009-June-6, VL
 *         Initial write.
 *     2009-June-7, JC
 *     	   Integrated with control.
 *     2009-June-8, JF
 *     	   Fixed keyPressed()
 *     2009-June-13, WC/VL
 *         Transferred file over to new project, ParrotIM.
 *         Fixed alignment to left.
 *     2009-June-23, KF
 *         Naming convention updates. Changed all class names.
 *         
 * Known Issues:
 *     1. User should be able to click outside the box to
 *        change personal message
 * 
 * Copyright (C) 2009  Pirate Captains
 * 
 * Full license can be found in ParrotIM/LICENSE.txt.
 */

package view.styles;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JTextField;

import view.buddylist.BuddyList;
import view.options.PersonalProfileTab;
import controller.MainController;

/**
 * This object sets the style of the personal message box on the top part of
 * buddylist window.
 * 
 * It inherits JTextField methods and variables.
 */
public class PmLabel extends JTextField {
    /**
     * core is a MainController object. It helps the user to change their
     * personal message on the server.
     */
    protected MainController core;

    protected JTextField pmLabelTextField;

    /**
     * PmLabel constructor. It takes a MainController object as its argument. It
     * also sets up some settings for the object.
     * 
     * @param c
     */
    public PmLabel(MainController c) {
        core = c;
        this.pmLabelTextField = this;
        // changePM(false);

        // Initialize the window without setting the core
        this.setEditable(false);
        this.setBorder(null);
        this.setText(this.getText());
        this.setOpaque(false);
        this.setText("(Type your status message)");

        this.setForeground(new Color(230, 243, 255));
        this.setToolTipText("Click to edit your personal message");
        this.addMouseListener(new labelMouseListener(this));
        this.addKeyListener(new labelKeyListener(this));
    }
    
    /**
     * changePM take a boolean as its argument. It is responsible for changing
     * the user's status message both on the GUI and the server.
     * 
     * If boolean b is false, then it will commit the change of the status
     * message. - If the message is null, then the status message will be empty
     * on the server. But the GUI will display "(Type your status message)" If
     * boolean b is true, the user will be able to type their status message on
     * the GUI.
     */
    public void changePM(boolean b, boolean changeTwitter) {
        if (b) {// editable
            if (this.getText().equals("(Type your status message)")) {
                this.setText("");
            }
            this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            this.setForeground(new Color(0, 42, 77));
            this.setBackground(new Color(230, 243, 255));
            this.setEditable(true);
            this.setOpaque(true);

        } else {// not editable
            this.setEditable(false);
            this.setBorder(null);
            this.setText(this.getText());
            this.setOpaque(false);

            this.setForeground(new Color(230, 243, 255));
            // send status to core

            if (this.getText() == null || this.getText().length() == 0
                    || this.getText().equals("(Type your status message)")) {
                this.setText("(Type your status message)");
                core.setStatus("", changeTwitter);
            } else {
                core.setStatus(this.getText(), changeTwitter);
            }

        }
    }

    public void setTextDisplay(String str) {
        if (str == null || str.length() == 0
                || str.equals("(Type your status message)")) {
            this.setText("(Type your status message)");
        } else {
            this.setText(str);
        }
    }

    public void synchOptions() {
        if (BuddyList.optionsIsVisible()) {
            PersonalProfileTab.personalMessage.setText(pmLabelTextField
                    .getText());
        }
    }

    /**
     * Sets the behaviour with regard of mouse input and position.
     * 
     * This class inherits MouseListener methods and variables.
     */
    private class labelMouseListener implements MouseListener {
        /** label is a PmLabel object. */
        private PmLabel label;

        /**
         * labelMouseListener constructor. It takes a PmLabel object.
         */
        public labelMouseListener(PmLabel lbl) {
            label = lbl;
        }

        /**
         * When the user clicks on label, they will be able to set the status
         * message. It takes a MouseEvent argument
         */
        public void mouseClicked(MouseEvent e) {
            if (!label.isEditable()) {
                label.changePM(true, true);
            }
        }

        /**
         * When the user hover on label, the label will be set to enable. It
         * takes a MouseEvent argument
         */
        public void mouseEntered(MouseEvent e) {
            label.setForeground(new Color(128, 197, 255));
        }

        /**
         * When the user moves the pointer out of the label area, the label will
         * be disabled. It takes a MouseEvent argument
         */
        public void mouseExited(MouseEvent e) {
            if (!label.isEditable()) {
                label.setForeground(new Color(230, 243, 255));
            }
        }

        public void mousePressed(MouseEvent e) {
        }

        public void mouseReleased(MouseEvent e) {
        }
    }

    /**
     * Sets the behaviour on regard of key input.
     * 
     * This class inherits KeyListener methods and variables.
     */
    class labelKeyListener implements KeyListener {
        /** label is a PmLabel object. */
        private PmLabel label;

        /**
         * labelKeyListener constructor. It takes a PmLabel object.
         */
        public labelKeyListener(PmLabel lbl) {
            label = lbl;
        }

        /**
         * When the user press enter, the change of the status message will be
         * committed . It takes a KeyEvent argument
         */
        public void keyPressed(KeyEvent e) {

        }

        public void keyReleased(KeyEvent e) {
            if (BuddyList.optionsIsVisible()) {
                PersonalProfileTab.personalMessage.setText(pmLabelTextField
                        .getText());
            }

            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                label.changePM(false, true);
            }
        }

        public void keyTyped(KeyEvent e) {
        }

    }
}
