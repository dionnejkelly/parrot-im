/* ChatPanel.java
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
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.WindowConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import model.Model;
import model.dataType.ChatCollectionData;
import model.dataType.ConversationData;
import model.dataType.MultiConversationData;
import model.enumerations.ServerType;
import model.enumerations.UpdatedType;
import model.enumerations.UserStateType;
import view.buddylist.AccountInfo;
import view.buddylist.BuddyList;
import view.options.GroupChatConfigurationFrame;
import view.styles.GPanel;
import view.styles.PopupWindowListener;
import controller.MainController;
import controller.services.BadConnectionException;
import controller.slashCommand.SlashCommand;
import controller.spellcheck.SpellCheck;

/**
 * The ChatPanel contains the panel that allow users to type messages and set
 * their settings.
 * 
 * This object inherits from JPanel
 */

public class ChatPanel extends GPanel implements Observer {

    /**
     * Model stores the needed data of the system. It also connects it with
     * database.
     */
    private Model model;

    /** Allows users to select the font type. */

    private JComboBox fontSelect;

    /** Allows users to select the color type. */

    private JButton colorButton;

    /** Allows users to send the message. */

    private JButton sendButton;

    private JButton emoticons;

    /** Allows users to type messages in the JTextArea. */

    private JTextArea txt1;
    private BuddyList buddyFrame;
    private AccountInfo accinfo;
    /** Allows users to see the messages in the DisplayPanel. */

    private DisplayPanel displayPanel;
    
    protected int keyCount = 0;

    /**
     * Maintains the Parrot IM XMPP Protocol.
     */
    private MainController c;
    private SpinnerModel fontSizemodel;
    public boolean bold, italics, underlined;
    private ColorUserSelect oldContentPane;

    protected JFrame chatFrame;

    private JFrame emoticonChooser;

    // private JButton themeMenu;
    private JButton groupChatAddButton;

    private JButton groupChatButton;

    private GPanel emoticonPanel;

    private SlashCommand slashCommand;

    private Long previousTime = System.currentTimeMillis();

    /**
     * This is the constructor of the ChatPanel.
     * 
     * @param c
     * @param model
     */

    public ChatPanel(MainController c, Model model, JFrame chatFrame,
            BuddyList buddyFrame) {
        setLayout(new BorderLayout());
        this.accinfo = buddyFrame.getAccountInfo();
        this.chatFrame = chatFrame;
        this.buddyFrame = buddyFrame;
        this.slashCommand = new SlashCommand(c);

        this.model = model;
        model.addObserver(this);
        this.c = c;

        setGradientColors(model.primaryColor, model.secondaryColor);
        setBorder(BorderFactory.createEmptyBorder(3, 0, 3, 3));

        bold = false;
        italics = false;
        underlined = false;

        displayPanel = new DisplayPanel(c, model);

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
        txt1.addKeyListener(new TextBoxListener());
        txt1.getDocument().addDocumentListener(new TextAreaDocListener());
        txt1.addFocusListener(new TextAreaFocusListener());
        JScrollPane chatInputWindowScroller = new JScrollPane(txt1);
        chatInputWindowScroller
                .setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        typingThread typingT = new typingThread();
        typingT.start();
        sendButton = new JButton("Send");
        sendButton.addActionListener(new SendButtonPressed());
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
        italicsButton.setSelectedIcon(new ImageIcon(this.getClass()
                .getResource("/images/chatwindow/ItalicsSelected.png")));
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
        underlineButton.setSelectedIcon(new ImageIcon(this.getClass()
                .getResource("/images/chatwindow/underLineSelected.png")));
        underlineButton.setToolTipText("Underline");
        underlineButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                underlineButton.setSelected(!underlineButton.isSelected());
                underlined = !underlined;
            }
        });

        // color Button
        colorButton =
                new JButton(new ImageIcon(this.getClass().getResource(
                        "/images/chatwindow/colorscm.png")));
        colorButton.setBackground(Color.BLACK);
        colorButton.setToolTipText("Change Font Color");
        colorButton.addActionListener(new colorListener());

        // emoticon button
        emoticons =
                new JButton(new ImageIcon(this.getClass().getResource(
                        "/images/chatwindow/emote.png")));
        emoticons.setToolTipText("Add an Emoticon");
        emoticons.addMouseListener(new emoticonListener());

        // Emoticon Chooser
        emoticonChooser = new JFrame("Emoticons");
        emoticonChooser.setVisible(false);
        emoticonChooser.setPreferredSize(new Dimension(200, 260));
        emoticonChooser.setResizable(false);
        emoticonChooser.setLayout(new FlowLayout());
        emoticonPanel = new GPanel();
        emoticonPanel.setGradientColors(model.primaryColor,
                model.secondaryColor);
        String[][] emoticonImages =
                { { "happy", ":)" }, { "sad", ":(" }, { "neutral", ":|" },
                        { "joy", ":D" }, { "laugh", "XD" }, { "cool", "B)" },
                        { "sick", ":S" }, { "glasses", "8)" },
                        { "dead", "XP" }, { "surprise", ":o" },
                        { "tongue", ":P" }, { "zipper", ":X" },
                        { "wink", ";)" }, { "afraid", "=0" },
                        { "angel", "O:)" }, { "party", "<:)" },
                        { "heart", "<3" }, { "brokenheart", "</3" } };
        for (final String[] str : emoticonImages) {
            JButton newButton =
                    new JButton(new ImageIcon(this.getClass().getResource(
                            "/images/emoticons/" + str[0] + ".png")));
            newButton.setToolTipText(str[0] + " " + str[1]);
            newButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent event) {
                    txt1
                            .setText(txt1.getText().substring(0,
                                    txt1.getCaretPosition())
                                    + str[1]
                                    + txt1.getText().substring(
                                            txt1.getCaretPosition()));
                    emoticonChooser.setVisible(false);
                }
            });
            emoticonPanel.add(newButton);
        }
        emoticonChooser.setContentPane(emoticonPanel);
        emoticonChooser.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        emoticonChooser.pack();
        emoticonChooser.setIconImage(new ImageIcon(
                "src/images/mainwindow/logo.png").getImage());

        // Image button
        JButton pic =
                new JButton(new ImageIcon(this.getClass().getResource(
                        "/images/chatwindow/pic.png")));
        pic.setToolTipText("Send a file");
        pic.addActionListener(new sendFileListener());

        groupChatButton =
                new JButton(new ImageIcon(this.getClass().getResource(
                        "/images/popup/comments.png")));

        groupChatButton.addActionListener(new GroupChatActionListener());
        groupChatButton.setToolTipText("Start a conference chat");
        groupChatAddButton =
                new JButton(new ImageIcon(this.getClass().getResource(
                        "/images/popup/comments_add.png")));

        groupChatAddButton.addActionListener(new GroupChatAddActionListener());
        groupChatAddButton
                .setToolTipText("Add users to an already existing conference chat");
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
        bar1.add(groupChatButton);
        bar1.add(groupChatAddButton);

        // add to editing panel
        editingPanel.add(bar1, BorderLayout.NORTH);
        editingPanel.add(sendButton, BorderLayout.EAST);
        editingPanel.add(chatInputWindowScroller, BorderLayout.CENTER);

        // setup and add to split pane
        JSplitPane sPane = new JSplitPane();
        sPane.setDividerSize(5);
        sPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
        sPane.setTopComponent(displayPanel);
        sPane.setBottomComponent(editingPanel);
        sPane.setResizeWeight(1.0);

        // add to chat panel
        add(sPane, BorderLayout.CENTER);
        txt1.requestFocus();

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
            // if (c.isConferenceChat()) {

            msg = Model.removeNewLinesAndSpaces(msg);

            if (slashCommand.isSlashCommand(msg)) {
                accinfo.statusMessage.setTextDisplay(model.getCurrentProfile()
                        .getStatus());
                accinfo.statusMessage.synchOptions();

                if (model.getCurrentProfile().getState() == UserStateType.ONLINE
                        || model.getCurrentProfile().getState() == UserStateType.AWAY
                        || model.getCurrentProfile().getState() == UserStateType.BUSY) {
                    accinfo.presence.setSelectedIndex(model.getCurrentProfile()
                            .getState().ordinal());
                } else if (model.getCurrentProfile().getState() == UserStateType.NOT_AVAILABLE) {
                    accinfo.presence.setSelectedIndex(1);
                } else if (model.getCurrentProfile().getState() == UserStateType.NOT_BE_DISTURBED) {
                    accinfo.presence.setSelectedIndex(2);
                } else if (model.getCurrentProfile().getState() == UserStateType.PHONE) {
                    accinfo.presence.setSelectedIndex(3);
                } else if (model.getCurrentProfile().getState() == UserStateType.LUNCH) {
                    accinfo.presence.setSelectedIndex(4);
                } else if (model.getCurrentProfile().getState() == UserStateType.BRB) {
                    accinfo.presence.setSelectedIndex(5);
                } else if (model.getCurrentProfile().getState() == UserStateType.INVISIBLE) {
                    accinfo.presence.setSelectedIndex(6);
                } else {
                    accinfo.presence.setSelectedIndex(0);
                }
            } else {

                if (model.getChatCollection().activeIsMulti()) {
                    // we need to know which room is selected in the Chat Side
                    // Panel
                    // for now I'll assume it only sends to the room
                    // "Parrot0@conference.jabber.org"
                    // as a Proof of concept

                    try {
                        c.sendMultMessage(msg, ((MultiConversationData) model
                                .getChatCollection().getActiveConversation())
                                .getRoomName(), fontSelect.getSelectedItem()
                                .toString(), fontSizemodel.getValue()
                                .toString(), bold, italics, underlined,
                                "#000000");
                    } catch (BadConnectionException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                } else { // Single Conversation
                    try {
                        if (oldContentPane == null) {
                            c.sendMessage(msg, fontSelect.getSelectedItem()
                                    .toString(), fontSizemodel.getValue()
                                    .toString(), bold, italics, underlined,
                                    "#000000");
                        } else {
                            c.sendMessage(msg, fontSelect.getSelectedItem()
                                    .toString(), fontSizemodel.getValue()
                                    .toString(), bold, italics, underlined,
                                    oldContentPane.hexColor);
                        }

                    } catch (BadConnectionException e) {
                        e.printStackTrace();
                    }
                }
            }
            txt1.setText("");
        }

    }

    /**
     * This is an emoticon listener class that is responsible for handling
     * user's emoticon preference .
     */

    public class emoticonListener implements MouseListener {

        /**
         * Listens for the uesr's action.
         * 
         * @param evt
         */
        public void mouseClicked(MouseEvent m) {
            emoticonChooser.setLocation(300, 300);

            emoticonChooser.setVisible(true);
        }

        public void mouseEntered(MouseEvent e) {
        }

        public void mouseExited(MouseEvent e) {
        }

        public void mousePressed(MouseEvent e) {
        }

        public void mouseReleased(MouseEvent e) {
        }
    }

    /**
     * This is an picture listener class that is responsible for handling user's
     * picture preference .
     */

    public class sendFileListener implements ActionListener {
        /**
         * Listens for the uesr's action.
         * 
         * @param evt
         */

        public void actionPerformed(ActionEvent evt) {
            c.sendFile();
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
            oldContentPane = new ColorUserSelect(frame, colorButton, model);

            frame.addWindowListener(new PopupWindowListener(chatFrame, frame));

            // Create and set up the content pane.
            ColorUserSelect newContentPane = oldContentPane;

            newContentPane.setOpaque(true); // content panes must be opaque
            frame.setContentPane(newContentPane);

            // Display the window.
            frame.pack();
            frame.setVisible(true);
            frame.setIconImage(new ImageIcon("src/images/mainwindow/logo.png")
                    .getImage());
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
        private int keyEvent;

        /**
         * Listens for the key pressed.
         * 
         * @param e
         */

        public void keyPressed(KeyEvent e) {
            // this is functional but somewhat unstable
            // any ideas are welcome to be discussed in the upcoming meeting

            if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
                shiftPressed = true;
            }

            else if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
            }

            keyEvent = e.getKeyCode();

        }

        /**
         * Listens for the key released.
         * 
         * @param e
         */

        public void keyReleased(KeyEvent e) {
            previousTime = System.currentTimeMillis();
            if (!shiftPressed && e.getKeyCode() == KeyEvent.VK_ENTER
                    && sendButton.isEnabled()) {
                e.setKeyCode(KeyEvent.VK_BEGIN);
                sendMessage();
                
                if(model.getActiveConversation().getAccount().getServer()
            			== ServerType.TWITTER){
                	System.out.println("************ Hey I am a twitter");
                }
                sendButton.setEnabled(true);
            }

            else if (shiftPressed && (e.getKeyCode() == e.VK_ENTER)) {
                txt1.setText(txt1.getText() + "\n");
                shiftPressed = false;
            }

            else {
                shiftPressed = false;
                c.setTypingState(2);

            }

            if (isMaxed()) {
                sendButton.setEnabled(false);

            }

            else {
                sendButton.setEnabled(true);

            }

        }

        /**
         * Listens for the key typed.
         * 
         * @param e
         */

        public void keyTyped(KeyEvent e) {

        }

        /**
         * Returns true if the key count reaches 140.
         * 
         * @param e
         */

        private boolean isMaxed() {
            // return keyCount >= 140;
        	if(model.getActiveConversation().getAccount().getServer()
        			==ServerType.TWITTER){
        		
        		return txt1.getText().length() > 140;
        		
        	}
        	return false;
        }

    }

    private class typingThread extends Thread {
        public typingThread() {

        }

        public void run() {
            while (true) {
                if ((System.currentTimeMillis() - previousTime) > 2000) {
                    c.setTypingState(1);
                }
                try {
                    this.sleep(1000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 
     * Check content of the textarea
     * 
     */
    private class TextAreaDocListener implements DocumentListener {

        public void changedUpdate(DocumentEvent arg0) {
            // send isTyping signal
        	
            //System.out.println("hey, I am typing");

        }

        public void insertUpdate(DocumentEvent e) {
//        	if(model.getActiveConversation().getAccount().getServer()
//        			== ServerType.TWITTER){
	            keyCount = keyCount + e.getLength();
	            displayPanel.updateChar(140 - keyCount);
//        	}
        }

        public void removeUpdate(DocumentEvent e) {
//        	if(model.getActiveConversation().getAccount().getServer()
//        			== ServerType.TWITTER){
	            keyCount = keyCount - e.getLength();
	            displayPanel.updateChar(140 - keyCount);
//        	}
        }

    }

    private class TextAreaFocusListener implements FocusListener {

        public void focusGained(FocusEvent arg0) {
            // send active signal
            // c.setTypingState(5);
            c.setTypingState(1);
        }

        public void focusLost(FocusEvent arg0) {
            // send inactive signal
            // c.setTypingState(5);
            if (txt1.getText().length() > 0) {
                c.setTypingState(5);
            } else if (txt1.getText().length() == 0) {
                c.setTypingState(4);
            }

        }

    }

    private class GroupChatActionListener implements ActionListener {

        // @Override
        public void actionPerformed(ActionEvent event) {

            // if the profile contains XMPP protocols
            if (c.isXMPP()) {
                c.create(model.getCurrentProfile().getName());

                JOptionPane
                        .showMessageDialog(
                                null,
                                "Multiple conversation room has been added to the chat window.",
                                "Conference Room Created",
                                JOptionPane.INFORMATION_MESSAGE);
            }

            else {
                JOptionPane
                        .showMessageDialog(
                                null,
                                "Sorry for the inconvenience but you need an XMPP account to create a conference room.",
                                "Conference Room Created",
                                JOptionPane.INFORMATION_MESSAGE);
            }
        }

    }

    private class GroupChatAddActionListener implements ActionListener {

        // @Override
        public void actionPerformed(ActionEvent event) {
            // if the group chat conference room doesn't exist create one

            if (c.getAvailableRoom().size() > 0) {
                if (!model.groupChatWindowOpen) {
                    new GroupChatConfigurationFrame(c, model, chatFrame,
                            buddyFrame);
                }
            }

            else {
                JOptionPane.showMessageDialog(null,
                        "Could not find any conference room.", "Failed",
                        JOptionPane.ERROR_MESSAGE);
            }

        }

    }

    public void update(Observable o, Object arg) {
        if (arg == UpdatedType.COLOR) {
            emoticonPanel.setGradientColors(model.primaryColor,
                    model.secondaryColor);
            emoticonPanel.updateUI();
        }
        else if(arg == UpdatedType.CHAT|| arg == UpdatedType.CHATNOTSIDEPANEL){
        	if(model.getChatCollection().getActiveConversation()
        			instanceof ConversationData){
        		if(model.getActiveConversation().getAccount().getServer()
                		== ServerType.TWITTER){
        			System.out.println(" ********* Key count == " + keyCount);
            			if(txt1.getDocument().getLength() > 140){
            				
            				sendButton.setEnabled(false);
            			}
            		}
        		
        		else {
        			sendButton.setEnabled(true);
        		}
        		
        	}
        }
    }
}