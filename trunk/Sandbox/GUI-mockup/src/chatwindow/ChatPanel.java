package chatwindow;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Observable;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.jivesoftware.smack.XMPPException;

import model.Model;
import ChatClient.ChatClient;

public class ChatPanel extends JPanel {
	/*THIS IS FOR CHAT CLIENT : modified ChatClient c*/
	JComboBox fontSelect;
	JButton colorButton;
	JColorChooser colorChooser;
	
	public ChatPanel(final ArrayList<Conversation> conversations, 
			         final ChatClient c, Model model, int id) {
		setLayout(new BorderLayout());
		
		final DisplayPanel displayPanel = new DisplayPanel(model, id);
		
		//Editing Panel
		JPanel editingPanel = new JPanel();
		editingPanel.setLayout(new BorderLayout());
		
		String[] fontList = {"Arial", "Times New Roman", "Comic Sans MS"};
		fontSelect = new JComboBox(fontList);
		fontSelect.setEditable(true);
		fontSelect.setMaximumSize(new Dimension(130,28));
		
		SpinnerModel fontSizemodel = new SpinnerNumberModel(4, 1, 12, 1);
		JSpinner fontSize = new JSpinner(fontSizemodel);
		fontSize.setMaximumSize(new Dimension(45,30));

		final JTextArea txt1 = new JTextArea();
		txt1.setColumns(25);
		txt1.setRows(9);
		txt1.setLineWrap(true);
		txt1.setToolTipText("Enter text and HTML tags here");
		
		JButton sendButton = new JButton("SEND");
		sendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	String msg = txt1.getText();
            	displayPanel.addMessage(c.getUserName(), msg, fontSelect.getSelectedItem().toString(), "4");
            	try {
            		for(int i = 0; i < conversations.get(0).getSize(); i++){
            			c.sendMessage(msg, conversations.get(0).getName(i));
            		}
					
				} catch (XMPPException e) {
					e.printStackTrace();
					System.out.println("failed in sending text");
				}
				txt1.setText("");
            }
        });
		//displayPanel.addMessage(incoming messages); //TODO
		
		JButton boldButton = new JButton("B");
		JButton italicsButton = new JButton("I");
		JButton underlineButton = new JButton("U");
		JButton colorButton = new JButton("Color");
		colorButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
            	JFrame colorFrame = new JFrame("Color chooser");
            	colorChooser = new JColorChooser();
            	colorChooser.getSelectionModel().addChangeListener(new ChangeListener(){
					public void stateChanged(ChangeEvent event) {
						System.out.print("Helloworld");
					}
            	});
            	colorFrame.add(new JColorChooser());
            	colorFrame.pack();
            	colorFrame.setVisible(true);
            }
		});
		JButton emoticons = new JButton(":-)");
		JButton pic = new JButton("Pic");
		
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
		
		//add to editing panel
		editingPanel.add(bar1, BorderLayout.NORTH);
		editingPanel.add(sendButton, BorderLayout.EAST);
		editingPanel.add(txt1, BorderLayout.CENTER);
		
		//setup and add to split pane
		JSplitPane sPane = new JSplitPane();
		sPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		sPane.setTopComponent(displayPanel);
		sPane.setBottomComponent(editingPanel);
		
		//add to chat panel
		add(sPane, BorderLayout.CENTER);	
	}
	
}