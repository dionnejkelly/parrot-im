package chatwindow;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class ChatPanel extends JPanel {

	public ChatPanel() {
		setLayout(new BorderLayout());
		
		final DisplayPanel displayPanel = new DisplayPanel();
		
		//Editing Panel
		JPanel editingPanel = new JPanel();
		editingPanel.setLayout(new BorderLayout());
		
		String[] fontList = {"Arial", "Times New Roman", "Comic Sans MS"};
		JComboBox fontSelect = new JComboBox(fontList);
		fontSelect.setEditable(true);
		fontSelect.setMaximumSize(new Dimension(130,28));
		
		SpinnerModel fontSizemodel = new SpinnerNumberModel(12, 6, 72, 1);
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
            	displayPanel.addMessage(txt1.getText());
            }
        });
		
		JButton boldButton = new JButton("B");
		JButton italicsButton = new JButton("I");
		JButton underlineButton = new JButton("U");
		JButton colorButton = new JButton("Color");
		JButton emoticons = new JButton(":-)");
		JButton link = new JButton("Link");
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
		bar1.add(link);
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