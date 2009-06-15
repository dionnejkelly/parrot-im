package view.chatwindow;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.*;

import controller.services.Xmpp;


import model.Model;
import model.dataType.UpdatedType;

public class mainPanel extends JPanel implements Observer {
	/*THIS IS FOR CHAT CLIENT : modified ChatClient c*/
	private JPanel side, chat;
	private Model model;
	
	//public mainPanel(ArrayList<Conversation> conversations, ChatClient c,
	//		         Model model) {
	
	public mainPanel(Xmpp c, Model model) {
                   
	    this.model = model;
	    
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

    @Override
    public void update(Observable o, Object arg) {
        if (arg == UpdatedType.CHAT) {
            this.chat.getTxtPane().setText(model.getActiveConversation().
                    displayMessages());
        }        
    }

}