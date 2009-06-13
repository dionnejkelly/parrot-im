package view.chatwindow;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.*;

import controller.services.ChatClient;

import model.Model;

public class mainPanel extends JPanel {
	/*THIS IS FOR CHAT CLIENT : modified ChatClient c*/
	private JPanel side, chat;
	
	//public mainPanel(ArrayList<Conversation> conversations, ChatClient c,
	//		         Model model) {
	
	public mainPanel(ChatClient c, Model model) {
                   
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
		//chat = new ChatPanel(conversations, c, model, id);
		//side = new sidePanel(conversations, c, model);
		chat = new ChatPanel(c, model);
		side = new sidePanel(c, model);
		sPane.setRightComponent(chat);
		sPane.setLeftComponent(side);
		sPane.setOneTouchExpandable(true);
		
		//add to panel
		add(menuBar, BorderLayout.NORTH);
		add(sPane, BorderLayout.CENTER);
	}
}