package view.chatwindow;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.*;

import controller.MainController;


import model.Model;
import model.enumerations.UpdatedType;

public class MainPanel extends JPanel implements Observer {
	/*THIS IS FOR CHAT CLIENT : modified ChatClient c*/
	private JPanel side, chat;
	private Model model;
	
	//public mainPanel(ArrayList<Conversation> conversations, ChatClient c,
	//		         Model model) {
	
	public MainPanel(MainController c, Model model) {
                   
	    this.model = model;
	    this.model.addObserver(this);
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
		side = new SidePanel(c, model);
		side.setMinimumSize(new Dimension(500, 300));
		sPane.setRightComponent(chat);
		sPane.setLeftComponent(side);
		sPane.setOneTouchExpandable(true);
		
		//add to panel
		add(menuBar, BorderLayout.NORTH);
		add(sPane, BorderLayout.CENTER);
	}

    public void update(Observable o, Object arg) {
  //      if (arg == UpdatedType.CHAT || arg == UpdatedType.CHAT_AND_BUDDY) {
  //          ((ChatPanel) this.chat).getDisplayPanel().getTxtPane().setText(
  //                  model.getActiveConversation().
  //                  displayMessages());
   //     }        
    }

}