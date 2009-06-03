package testPackage.chatwindow;

import java.awt.*;
import java.awt.event.KeyEvent;
import javax.swing.*;

import testPackage.Model;


public class mainPanel extends JPanel {

    public sidePanel sidePanel;
    public ChatPanel chatPanel;
	
	public mainPanel(Model model) {
		sidePanel = new sidePanel(model);
		chatPanel = new ChatPanel(model);
		
		setLayout(new BorderLayout());
		
		JMenuBar menuBar = new JMenuBar();
		
		JMenu fileMenu = new JMenu("File");
	    fileMenu.setMnemonic(KeyEvent.VK_F);
	    menuBar.add(fileMenu);
	    JMenuItem exitItem1 = new JMenuItem("Exit", KeyEvent.VK_N);
	    fileMenu.addSeparator();
	    fileMenu.add(exitItem1);
	    
	    JMenu helpMenu = new JMenu("Help");
	    fileMenu.setMnemonic(KeyEvent.VK_H);
	    menuBar.add(helpMenu);
	    JMenuItem helpItem1 = new JMenuItem("Help Contents");
	    JMenuItem helpItem2 = new JMenuItem("About");
	    helpMenu.add(helpItem1);
	    helpMenu.addSeparator();
	    helpMenu.add(helpItem2);
		
		JSplitPane sPane = new JSplitPane();
		sPane.setRightComponent(chatPanel);
		sPane.setLeftComponent(sidePanel);
		sPane.setOneTouchExpandable(true);
		
		//add to panel
		add(menuBar, BorderLayout.NORTH);
		add(sPane, BorderLayout.CENTER);
	}

	public void submitMessage(String newText)
	{ 
		chatPanel.submitMessage(newText);
		return;
	}

	public JButton gsb()
	{
	   return chatPanel.getSendButton();	
	}
}
