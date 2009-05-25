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
		
		String[] fontList = {"Ariel", "Times New Roman", "Comic Sans MS"};
		JComboBox fontSelect = new JComboBox(fontList);
		fontSelect.setEditable(true);
		
		SpinnerModel fontSizemodel = new SpinnerNumberModel(12, 6, 72, 1);
		JSpinner fontSize = new JSpinner(fontSizemodel);

		JTextArea txt1 = new JTextArea();
		txt1.setColumns(25);
		txt1.setRows(9);
		txt1.setLineWrap(true);
		
		JButton sendButton = new JButton("SEND");
		sendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	displayPanel.addMessage("test");
            }
        });
		
		JButton boldButton = new JButton("B");
		JButton italicsButton = new JButton("I");
		JButton underlineButton = new JButton("U");
		JButton colorButton = new JButton("Color");
		
		JToolBar bar1 = new JToolBar();
		bar1.add(fontSelect);
		bar1.add(fontSize);
		bar1.add(boldButton);
		bar1.add(italicsButton);
		bar1.add(underlineButton);
		bar1.add(colorButton);
		
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