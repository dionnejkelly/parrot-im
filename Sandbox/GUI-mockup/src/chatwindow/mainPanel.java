package chatwindow;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.*;

import ChatClient.ChatClient;


public class mainPanel extends JPanel {
	/*THIS IS FOR CHAT CLIENT : modified ChatClient c*/
	public mainPanel(ArrayList<Conversation> conversations, ChatClient c) {
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
		sPane.setRightComponent(new ChatPanel(conversations, c));
		sPane.setLeftComponent(new sidePanel(conversations));
		sPane.setOneTouchExpandable(true);
		
		//add to panel
		add(menuBar, BorderLayout.NORTH);
		add(sPane, BorderLayout.CENTER);
	}
}