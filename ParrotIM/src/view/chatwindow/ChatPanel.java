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
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileFilter;

import view.options.GroupChatConfigurationFrame;
import view.styles.PopupWindowListener;
//import view.theme.LookAndFeelManager;

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
    
    /** Allows users to send the message. */
    
    private JButton sendButton;
    
    private JButton emoticons;

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

//    private JButton themeMenu;
    private JButton groupChatAddButton;
    
    private JButton groupChatButton;
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
//        txt1 = new JTextArea();
//        txt1.setColumns(25);
//        txt1.setRows(2);
//        txt1.setAutoscrolls(true);
//        txt1.setLineWrap(true);
//        txt1.setWrapStyleWord(true);
//        txt1.setToolTipText("Enter text and HTML tags here");
        txt1.addKeyListener(new TextBoxListener());
        txt1.getDocument().addDocumentListener(new TextAreaDocListener());
        txt1.addFocusListener(new TextAreaFocusListener());
        JScrollPane chatInputWindowScroller = new JScrollPane(txt1);
        chatInputWindowScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        
        sendButton = new JButton("Send");
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
        colorButton =
                new JButton(new ImageIcon(this.getClass().getResource(
                        "/images/chatwindow/colorscm.png")));
        colorButton.setBackground(Color.BLACK);
        colorButton.setToolTipText("Change Font Color");
        colorButton.addActionListener(new colorListener());
 
        
        //emoticon button
        emoticons =
                new JButton(new ImageIcon(this.getClass().getResource(
                        "/images/chatwindow/emote.png")));
        emoticons.setToolTipText("Add an Emoticon");
        emoticons.addMouseListener(new emoticonListener());
        
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
       pic.setToolTipText("Send a file");
       pic.addActionListener(new sendFileListener());
        
        groupChatButton = new JButton(new ImageIcon(this.getClass().getResource(
        "/images/popup/comments.png")));
        
      groupChatButton.addActionListener(new GroupChatActionListener());
      
        groupChatAddButton = new JButton(new ImageIcon(this.getClass().getResource(
        "/images/popup/comments_add.png")));
        
      groupChatAddButton.addActionListener(new GroupChatAddActionListener());
        
      //themeMenu = new ThemeOptionsComboBox();
      //  themeMenu.setToolTipText("Select your own Theme");

//        themeMenu.setAutoscrolls(true);
        
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

    public class emoticonListener implements MouseListener {

        /**
         * Listens for the uesr's action.
         * 
         * @param evt
         */
		public void mouseClicked(MouseEvent m) {
			emoticonChooser.setLocation(300, 300);
			System.out.println(m.getX() + " " + m.getY());
			
			emoticonChooser.setVisible(true);
		}
		public void mouseEntered(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}
		public void mousePressed(MouseEvent e) {}
		public void mouseReleased(MouseEvent e) {}
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
        	String userID = "solidworktesting@gmail.com";
        	
        	if (c.isValidUserID(userID)) {
        		System.out.println("Start Transfering File... " + userID);
           		
            	ImageFileFilter filefilter = new ImageFileFilter();
            	JFileChooser fileChooser = new JFileChooser();
            	fileChooser.setFileFilter(filefilter);
        		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        		fileChooser.setMultiSelectionEnabled(false);
        		
            	int fileConfirmation = fileChooser.showOpenDialog(null);
            	
            	if(fileConfirmation == JFileChooser.APPROVE_OPTION) {
            		String filePath = fileChooser.getSelectedFile().getPath();
            		c.sendFile(userID, filePath);
            	}
        	}
        	
        	else {
        		JOptionPane.showMessageDialog(null, "Cannot send file because " + userID + "does not support file receiving.",
                        "Failed", JOptionPane.ERROR_MESSAGE);
        	}
        	
       		

        }

    }
    
    /** This class controls the file types that can be selected for the file browser. 
	 * It can only select either a directory or an image file. */
	private class ImageFileFilter extends FileFilter{

		@Override
		
		/** accept takes a File object argument. If the file is an image file or a directory, then it returns true.
		 * It returns false otherwise 
		 * 
		 * @param f*/
		public boolean accept(File f) {
			
			if (f.isDirectory()) return true; //if directory, return true
			
			//now search of image files
			String[] extentionList = new String[]{"jpg", "gif", "png", "bmp"};

			String name = f.getName();
			String extention = name.substring(name.indexOf(".")+1, name.length());
			
			for(int pos=0; pos < extentionList.length; pos++){
				if (extention.compareToIgnoreCase(extentionList[pos])==0 && f.length() <= 524288){
					return true;
				}
			}
			return false;
		}

		@Override
		/** Describes what file types the system will accept. */
		public String getDescription() {
			return "choose image file less than 512 kb";
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
            oldContentPane = new ColorUserSelect(frame, colorButton);
            
//            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.addWindowListener(new PopupWindowListener(chatFrame, frame));

            // Create and set up the content pane.
            ColorUserSelect newContentPane = oldContentPane;
            
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
        private final int keyCapacity = 140;
        
        private int keyEvent;
        
        private int keyCount;
        

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
            
          
            keyEvent = e.getKeyCode();
           
        }

        /**
         * Listens for the key released.
         * 
         * @param e
         */

        public void keyReleased(KeyEvent e) {

            if (!shiftPressed && e.getKeyCode() == e.VK_ENTER && sendButton.isEnabled()) {
                System.out.println("-------------------NOT PRESSED!!!!!!!!!!!!!!!!!!!!!");
                e.setKeyCode(e.VK_BEGIN);
//                txt1.setText(txt1.getText().substring(
//                        0, txt1.getText().length() - 1));
                sendMessage();
                sendButton.setEnabled(true);
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
                c.setTypingState(2);

            }
            
            
        	if (isMaxed()) {
        		System.out.println("Maxed out");
        		sendButton.setEnabled(false);
        		
        	}
        	
        	else {
        		System.out.println("Haven't reached the capacity");
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
        	//return keyCount >= 140;
        	return txt1.getText().length() > 140;
        }
        

    }
    /**
     * 
     * Check content of the textarea
     *
     */
    private class TextAreaDocListener implements DocumentListener{
    	private int keyCount = 0;
		public void changedUpdate(DocumentEvent arg0) {
			//send isTyping signal
			System.out.println("hey, I am typing");
			
		}

		public void insertUpdate(DocumentEvent e) {
			keyCount = keyCount + e.getLength();
			System.out.println("Key count = " + keyCount);
			displayPanel.updateChar(140 - keyCount);
		}

		public void removeUpdate(DocumentEvent e) {
			keyCount = keyCount - e.getLength();
			System.out.println("Key count = " + keyCount);
			displayPanel.updateChar(140 - keyCount);
		}
		
    }
    private class TextAreaFocusListener implements FocusListener{

		public void focusGained(FocusEvent arg0) {
			//send active signal
			//c.setTypingState(5);
			c.setTypingState(1);
		}

		public void focusLost(FocusEvent arg0) {
			//send inactive signal
			//c.setTypingState(5);
			if (txt1.getText().length()>0){
				c.setTypingState(5);
			}else if(txt1.getText().length()==0){
				c.setTypingState(4);
			}
			
			
		}
    	
    }
    
    private class GroupChatActionListener implements ActionListener{
    	
    	
		//@Override
		public void actionPerformed(ActionEvent event) {
			System.out.println("Name = " + model.getCurrentProfile().getName());
			
			c.create(model.getCurrentProfile().getName());
			
			JOptionPane.showMessageDialog(null, "This should open a new chat window to invite users into that room.");
			
			// need to create a new conference chat room here
//			c.messageReceived("the left side panel in the chat window should be empty"
//			,model.getCurrentProfile().getName(),
//                    " has created a conference chat room!");
		}
    	
    }
    
    private class GroupChatAddActionListener implements ActionListener{
    	
    	
		//@Override
		public void actionPerformed(ActionEvent event) {
			// if the group chat conference room doesn't exist create one
			System.out.println("Invite a friend...");
			
//			for (String room: c.getAvailableRoom()) {
//				System.out.println("From main controller Room = " + room);
//			}
			
			if (c.getAvailableRoom().size() > 0) {
				GroupChatConfigurationFrame groupChat = new GroupChatConfigurationFrame(c, model, 0);
			}
			
			else {
				JOptionPane.showMessageDialog(null, "Could not find any conference room.", "Failed", JOptionPane.ERROR_MESSAGE);
			}
			
			
		}
    	
    }
}