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

import view.styles.PopupWindowListener;

import controller.MainController;
import controller.services.BadConnectionException;
import controller.spellcheck.SpellCheck;

import model.*;

/**
 * The ChatPanel contains the panel that allow users to type messages and set
 * their settings.
 * 
 * This object inherits from JPanel
 */

public class ChatPanel extends JPanel {

    /**
     * Model stores the needed data of the system. It also connects it with
     * database.
     */
    private Model model;

    /** Allows users to select the font type. */

    private JComboBox fontSelect;

    /** Allows users to select the color type. */

    private JButton colorButton;

    /** Allows users to select the color from JColorChooser. */

    private JColorChooser colorChooser;

    /** Allows users to type messages in the JTextArea. */

    private JTextArea txt1;

    /** Allows users to see the messages in the DisplayPanel. */

    private DisplayPanel displayPanel;

    /**
     * Maintains the Parrot IM XMPP Protocol.
     */
    private MainController c;
    private SpinnerModel fontSizemodel;
    public boolean bold, italics, underlined;
    private ColorUserSelect oldContentPane;

    protected JFrame chatFrame;
    
    private JFrame emoticonChooser;

    /**
     * This is the constructor of the ChatPanel.
     * 
     * @param c
     * @param model
     */

    public ChatPanel(MainController c, Model model, JFrame chatFrame) {
        setLayout(new BorderLayout());
        this.chatFrame = chatFrame;

        this.model = model;
        this.c = c;

        bold = false;
        italics = false;
        underlined = false;

        displayPanel = new DisplayPanel(model);

        // Editing Panel
        JPanel editingPanel = new JPanel();
        editingPanel.setLayout(new BorderLayout());
        editingPanel.setSize(new Dimension(20, 20));

        // List of fonts combobox
        String[] fontList = { "Arial", "Times New Roman", "Comic Sans MS" };
        fontSelect = new JComboBox(fontList);
        fontSelect.setEditable(false);
        fontSelect.setMaximumSize(new Dimension(130, 28));

        // The font size spinner
        fontSizemodel = new SpinnerNumberModel(4, 1, 12, 1);
        JSpinner fontSize = new JSpinner(fontSizemodel);
        fontSize.setMaximumSize(new Dimension(45, 30));

        // the input textarea properties
        SpellCheck createTextArea = new SpellCheck();
        txt1 = createTextArea.getTextArea();
//        txt1 = new JTextArea();
//        txt1.setColumns(25);
//        txt1.setRows(2);
//        txt1.setAutoscrolls(true);
//        txt1.setLineWrap(true);
//        txt1.setWrapStyleWord(true);
//        txt1.setToolTipText("Enter text and HTML tags here");
        txt1.addKeyListener(new TextBoxListener());

        JScrollPane chatInputWindowScroller = new JScrollPane(txt1);
        chatInputWindowScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        
        JButton sendButton = new JButton("SEND");
        sendButton.addActionListener(new SendButtonPressed());
        // displayPanel.addMessage(incoming messages); //TODO

        // Editing button properties
        // bold Button
        final JButton boldButton =
                new JButton(new ImageIcon(this.getClass().getResource(
                        "/images/chatwindow/bold.png")));
        boldButton.setSelectedIcon(new ImageIcon(this.getClass().getResource(
                "/images/chatwindow/boldSelected.png")));
        boldButton.setToolTipText("Bold");
        boldButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                boldButton.setSelected(!boldButton.isSelected());
                bold = !bold;
            }
        });

        // Italics Button
        final JButton italicsButton =
                new JButton(new ImageIcon(this.getClass().getResource(
                        "/images/chatwindow/Italics.png")));
        italicsButton.setSelectedIcon(new ImageIcon(this
                .getClass().getResource(
                        "/images/chatwindow/ItalicsSelected.png")));
        italicsButton.setToolTipText("Italic");
        italicsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                italicsButton.setSelected(!italicsButton.isSelected());
                italics = !italics;
            }
        });

        // UnderlineButton
        final JButton underlineButton =
                new JButton(new ImageIcon(this.getClass().getResource(
                        "/images/chatwindow/underLine.png")));
        underlineButton.setSelectedIcon(new ImageIcon(this
                .getClass().getResource(
                        "/images/chatwindow/underLineSelected.png")));
        underlineButton.setToolTipText("Underline");
        underlineButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                underlineButton.setSelected(!underlineButton.isSelected());
                underlined = !underlined;
            }
        });

        // color Button
        JButton colorButton =
                new JButton(new ImageIcon(this.getClass().getResource(
                        "/images/chatwindow/colorscm.png")));
        colorButton.setToolTipText("Change Font Color");
        colorButton.addActionListener(new colorListener());
 
        
        //emoticon button
        JButton emoticons =
                new JButton(new ImageIcon(this.getClass().getResource(
                        "/images/chatwindow/emote.png")));
        emoticons.setToolTipText("Add an Emoticon");
        emoticons.addActionListener(new emoticonListener());
        
        //Emoticon Chooser
        emoticonChooser = new JFrame("Emoticons");
        emoticonChooser.setVisible(false);
        emoticonChooser.setPreferredSize(new Dimension(200, 260));
        emoticonChooser.setResizable(false);
        emoticonChooser.setLayout(new FlowLayout());
        String[][] emoticonImages = {{"happy", ":)"}, {"sad", ":("}, {"neutral", ":|"}, 
        		{"joy", ":D"}, {"laugh", "XD"}, {"cool", "B)"}, {"sick", ":S"}, 
        		{"glasses", "8)"}, {"dead", "XP"}, {"surprise", ":o"}, {"tongue", ":P"}, 
        		{"zipper", ":X"}, {"wink", ";)"}, {"afraid", "=0"}, {"angel", "O:)"}, 
        		{"party","<:)"}, {"heart", "<3"}, {"brokenheart", "</3"}};
        for(final String[] str : emoticonImages){
        	JButton newButton = new JButton(new ImageIcon(this.getClass().getResource(
					"/images/emoticons/" + str[0] + ".png")));
        	newButton.setToolTipText(str[0] + " " + str[1]);
        	newButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent event) {
                	txt1.setText(txt1.getText().substring(0, txt1.getCaretPosition()) +
                			str[1] + txt1.getText().substring(txt1.getCaretPosition()));
                	emoticonChooser.setVisible(false);
                }
            });
        	emoticonChooser.add(newButton);
        }
        emoticonChooser.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        emoticonChooser.pack();
        emoticonChooser.setIconImage(new ImageIcon("src/images/mainwindow/logo.png").getImage());
        
        //Image button
        JButton pic =
                new JButton(new ImageIcon(this.getClass().getResource(
                        "/images/chatwindow/pic.png")));
        
        
        
        pic.setToolTipText("Insert a Picture");
        pic.addActionListener(new pictureListener());

        // Text editing toolbar
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
        editingPanel.add(chatInputWindowScroller, BorderLayout.CENTER);

        // setup and add to split pane
        JSplitPane sPane = new JSplitPane();
        sPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
        sPane.setTopComponent(displayPanel);
        sPane.setBottomComponent(editingPanel);
        sPane.setResizeWeight(1.0);

        // add to chat panel
        add(sPane, BorderLayout.CENTER);
    }

    // Getters

    /**
     * Returns the display panel.
     * 
     * @return DisplayPanel
     */

    public DisplayPanel getDisplayPanel() {
        return displayPanel;
    }

    /**
     * Returns the font select.
     * 
     * @return JComboBox
     */

    public JComboBox getFontSelect() {
        return fontSelect;
    }

    /**
     * The sendMessage Method calls the model to send a message. This function
     * also Updates the display Text to show the message that you just sent.
     */
    public void sendMessage() {
        String msg = txt1.getText();

        if (msg != null && msg.length() > 0) {
            System.out.println(msg);
            System.out.println(msg.length());
            try {
            	if(oldContentPane == null){
	                c.sendMessage(
	                        msg, fontSelect.getSelectedItem().toString(),
	                        fontSizemodel.getValue().toString(), bold, italics,
	                        underlined, "#000000");
            	}else{
            		c.sendMessage(
	                        msg, fontSelect.getSelectedItem().toString(),
	                        fontSizemodel.getValue().toString(), bold, italics,
	                        underlined, oldContentPane.hexColor);
            	}

            } catch (BadConnectionException e) {
                e.printStackTrace();
                System.out.println("failed in sending text");
            }
            txt1.setText("");
        }
    }

    /**
     * This is an emoticon listener class that is responsible for handling
     * user's emoticon preference .
     */

    public class emoticonListener implements ActionListener {

        /**
         * Listens for the uesr's action.
         * 
         * @param evt
         */

        public void actionPerformed(ActionEvent evt) {

            //String resultMessage =
            //        "Sorry for the inconvenience but for the Alpha Version, we are not supporting this feature. Thank you for your co-operation.";
            //JOptionPane.showMessageDialog(null, resultMessage);
            emoticonChooser.setVisible(true);
        }

    }

    /**
     * This is an picture listener class that is responsible for handling user's
     * picture preference .
     */

    public class pictureListener implements ActionListener {

        /**
         * Listens for the uesr's action.
         * 
         * @param evt
         */

        public void actionPerformed(ActionEvent evt) {

            String resultMessage =
                    "Sorry for the inconvenience but for the Alpha Version, we are not supporting this feature. Thank you for your co-operation.";
            JOptionPane.showMessageDialog(null, resultMessage);

        }

    }

    /**
     * This is a color listener class that is responsible for handling user's
     * color preference through JColorChooser.
     */

    public class colorListener implements ActionListener {

        /**
         * Listens for the uesr's action.
         * 
         * @param evt
         */

        public void actionPerformed(ActionEvent evt) {

            JFrame frame = new JFrame("Color Chooser");
            oldContentPane = new ColorUserSelect(frame);
            
//            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.addWindowListener(new PopupWindowListener(chatFrame, frame));

            // Create and set up the content pane.
            JComponent newContentPane = oldContentPane;
            
            newContentPane.setOpaque(true); // content panes must be opaque
            frame.setContentPane(newContentPane);
            
            // Display the window.
            frame.pack();
            frame.setVisible(true);
            frame.setIconImage(new ImageIcon("src/images/mainwindow/logo.png").getImage());
        }
    }

    /**
     * The Button listener for the "send" button.
     */
    public class SendButtonPressed implements ActionListener {
        public void actionPerformed(ActionEvent evt) {
            sendMessage();
        }
    }

    /**
     * This is the keyListener for the textbox where the user inputs there
     * message. Currently handles the "enter" and "shift-enter" keyboard
     * commands.
     */
    public class TextBoxListener implements KeyListener {
        private boolean shiftPressed = false;
        private boolean controlPressed = false;
        
        private boolean twitterEnabled;
        private int keyCount = 0;
        private int keyEvent;

        /**
         * Listens for the key pressed.
         * 
         * @param e
         */

        public void keyPressed(KeyEvent e) {
            // this is functional but somewhat unstable
            // any ideas are welcome to be discussed in the upcoming meeting

  	
            if (e.getKeyCode() == e.VK_SHIFT) {
                shiftPressed = true;
            }
            
            else if (e.getKeyCode() == e.VK_CONTROL) {
            	controlPressed = true;
            }
            
            else if (isMaxed()) {
            	txt1.setEditable(false);
            }
            
            else if (e.getKeyCode() == e.VK_BACK_SPACE) {
            	txt1.setEditable(true);
            }
            
            else if (txt1.getText().length() == 1) {
            	keyCount = 1;
            }
            
            else if (txt1.getText().equals("")) {
            	keyCount = 0;
            	System.out.println("Emptied out!!!!!!!");
            }
            
            keyEvent = e.getKeyCode();
            
           
        }

        /**
         * Listens for the key released.
         * 
         * @param e
         */

        public void keyReleased(KeyEvent e) {

            if (!shiftPressed && e.getKeyCode() == e.VK_ENTER) {
                System.out
                        .println("-------------------NOT PRESSED!!!!!!!!!!!!!!!!!!!!!");
                e.setKeyCode(e.VK_BEGIN);
                txt1.setText(txt1.getText().substring(
                        0, txt1.getText().length() - 1));
                sendMessage();
                keyCount = 0;
            }

            else if (shiftPressed && (e.getKeyCode() == e.VK_ENTER)) {
                System.out
                        .println("-------------------PRESSED!!!!!!!!!!!!!!!!!!!!!");
                txt1.setText(txt1.getText() + "\n");
                shiftPressed = false;
                controlPressed = false;
            }

            else {
                shiftPressed = false;
                controlPressed = false;
            }
        }

        /**
         * Listens for the key typed.
         * 
         * @param e
         */

        public void keyTyped(KeyEvent e) {
        	
        	if (!controlPressed && (keyEvent == e.VK_BACK_SPACE) && (keyCount != 0)) {
        		keyCount--;
        		System.out.println("Key count = " + keyCount);
        	}
        	
        	else if (!controlPressed && keyCount == 0 && (keyEvent == e.VK_BACK_SLASH)) {
        		keyCount = 0;
        		System.out.println("Key count = " + keyCount);
        	}
        	
        	else if (!controlPressed && !isMaxed()) {
        		System.out.println("I shouldn't be getting called");
        		keyCount++;
        		System.out.println("Key count = " + keyCount);
        	}
        	
        	System.out.println("Key character = " + keyEvent);
        	
        	
        }
        
        /**
         * Returns true if the key count reaches 140.
         * 
         * @param e
         */
        
        public boolean isMaxed() {
        	return keyCount == 140;
        }
    }
}