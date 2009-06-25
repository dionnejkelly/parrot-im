/* ChatPanel.java
 *  
 * Copyright (C) 2009  Pirate Captains
 * 
 * License: GNU General Public License version 2.
 * Full license can be found in ParrotIM/LICENSE.txt.
 */

package view.chatwindow;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.jivesoftware.smack.XMPPException;

import controller.MainController;

import model.*;

public class ChatPanel extends JPanel {
    /* THIS IS FOR CHAT CLIENT : modified ChatClient c */
	private Model model;
    private JComboBox fontSelect;
    private JButton colorButton;
    private JColorChooser colorChooser;
    private JTextArea txt1;
    private DisplayPanel displayPanel;
    private MainController c;

    public ChatPanel(MainController c, Model model) {
        setLayout(new BorderLayout());

        this.model = model;
        this.c = c;

        displayPanel = new DisplayPanel(model);

        // Editing Panel
        JPanel editingPanel = new JPanel();
        editingPanel.setLayout(new BorderLayout());
        editingPanel.setSize(new Dimension(20, 20));

        //List of fonts combobox
        String[] fontList = { "Arial", "Times New Roman", "Comic Sans MS" };
        fontSelect = new JComboBox(fontList);
        fontSelect.setEditable(true);
        fontSelect.setMaximumSize(new Dimension(130, 28));

        //The font size spinner
        SpinnerModel fontSizemodel = new SpinnerNumberModel(4, 1, 12, 1);
        JSpinner fontSize = new JSpinner(fontSizemodel);
        fontSize.setMaximumSize(new Dimension(45, 30));

        //the input textarea properties
        txt1 = new JTextArea();
        txt1.setColumns(25);
        txt1.setRows(2);
        txt1.setAutoscrolls(true);
        txt1.setLineWrap(true);
        txt1.setWrapStyleWord(true);
        txt1.setToolTipText("Enter text and HTML tags here");
        txt1.addKeyListener(new TextBoxListener());

        JButton sendButton = new JButton("SEND");
        sendButton.addActionListener(new SendButtonPressed());
        // displayPanel.addMessage(incoming messages); //TODO

        //Editing button properties
        JButton boldButton =
                new JButton(new ImageIcon(this.getClass().getResource(
                        "/images/chatwindow/bold.png")));
        boldButton.setToolTipText("Bold");
        JButton italicsButton =
                new JButton(new ImageIcon(this.getClass().getResource(
                        "/images/chatwindow/italics.png")));
        italicsButton.setToolTipText("Italic");
        JButton underlineButton =
                new JButton(new ImageIcon(this.getClass().getResource(
                        "/images/chatwindow/underLine.png")));
        underlineButton.setToolTipText("Underline");
        JButton colorButton =
                new JButton(new ImageIcon(this.getClass().getResource(
                        "/images/chatwindow/colorscm.png")));
        colorButton.setToolTipText("Change Font Color");
        colorButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                JFrame colorFrame = new JFrame("Color chooser");
                colorChooser = new JColorChooser();
                colorChooser.getSelectionModel().addChangeListener(
                        new ChangeListener() {
                            public void stateChanged(ChangeEvent event) {
                                System.out.print("Helloworld");
                            }
                        });
                colorFrame.add(new JColorChooser());
                colorFrame.pack();
                colorFrame.setVisible(true);
            }
        });
        JButton emoticons =
                new JButton(new ImageIcon(this.getClass().getResource(
                        "/images/chatwindow/emote.png")));
        emoticons.setToolTipText("Add an Emoticon");
        JButton pic =
                new JButton(new ImageIcon(this.getClass().getResource(
                        "/images/chatwindow/pic.png")));
        pic.setToolTipText("Insert a Picture");

        //Text editing toolbar
        JToolBar bar1 = new JToolBar();
        bar1.add(fontSelect);
        bar1.addSeparator();
        bar1.add(fontSize);
        bar1.addSeparator();
        bar1.add(boldButton);
        bar1.add(italicsButton);
        bar1.add(underlineButton);
        bar1.add(colorButton);
        bar1.add(emoticons);
        bar1.add(pic);

        // add to editing panel
        editingPanel.add(bar1, BorderLayout.NORTH);
        editingPanel.add(sendButton, BorderLayout.EAST);
        editingPanel.add(txt1, BorderLayout.CENTER);

        // setup and add to split pane
        JSplitPane sPane = new JSplitPane();
        sPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
        sPane.setTopComponent(displayPanel);
        sPane.setBottomComponent(editingPanel);
        sPane.setResizeWeight(1.0);

        // add to chat panel
        add(sPane, BorderLayout.CENTER);
    }

    //Getters
    public DisplayPanel getDisplayPanel() { return displayPanel; }
    public JComboBox getFontSelect() { return fontSelect; }

    /** The sendMessage Method calls the model to send a message.
     * This function also Updates the display Text to show the
     * message that you just sent.
     */
    public void sendMessage() {
        String msg = txt1.getText();

        try {
            c.sendMessage(msg, fontSelect.getSelectedItem().toString(), "4");

        } catch (XMPPException e) {
            e.printStackTrace();
            System.out.println("failed in sending text");
        }
        txt1.setText(null);
    }

    /** The Button listener for the "send" button.
     */
    public class SendButtonPressed implements ActionListener {
        public void actionPerformed(ActionEvent evt) {
            sendMessage();
        }
    }

    /** This is the keyListener for the textbox where the user
     * inputs there message. Currently handles the "enter" and "shift-enter"
     * keyboard commands.
     */
    public class TextBoxListener implements KeyListener {
        private boolean shiftPressed = false;

        public void keyPressed(KeyEvent e) {
            // this is functional but somewhat unstable
            // any ideas are welcome to be discussed in the upcoming meeting

            if (e.getKeyCode() == e.VK_SHIFT) {
                shiftPressed = true;
            }
        }
        
        public void keyReleased(KeyEvent e) {

            if (!shiftPressed && e.getKeyCode() == e.VK_ENTER) {
                System.out
                        .println("-------------------NOT PRESSED!!!!!!!!!!!!!!!!!!!!!");
                e.setKeyCode(e.VK_BEGIN);
                sendMessage();
            }

            else if (shiftPressed && (e.getKeyCode() == e.VK_ENTER)) {
                System.out
                        .println("-------------------PRESSED!!!!!!!!!!!!!!!!!!!!!");
                txt1.setText(txt1.getText() + "\n");
                shiftPressed = false;
            }

            else {
                shiftPressed = false;
            }
        }
        public void keyTyped(KeyEvent e) {}
    }
}