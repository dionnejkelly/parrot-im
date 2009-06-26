/* MainPanel.java
 *  
 * Copyright (C) 2009  Pirate Captains
 * 
 * License: GNU General Public License version 2.
 * Full license can be found in ParrotIM/LICENSE.txt.
 */

package view.chatwindow;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.*;

import controller.MainController;


import model.Model;
import model.enumerations.UpdatedType;

/**
 * The MainPanel contains the main panel that handles the integrity of
 * the panels and provides option.
 * 
 * This object inherits from JPanel 
 */

public class MainPanel extends JPanel implements Observer {
	/*THIS IS FOR CHAT CLIENT : modified ChatClient c*/
	
	  /** JPanel for side and chat data type.*/
	
	private JPanel side, chat;
	
	/** Model stores the needed data of the system. It also connects it with database.*/
	
	private Model model;
	
	
	 /**
     * This is the constructor of the MainPanel.
     * 
     * @param c
     * @param model
     */
	
	
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
		
	    //Horizontal SplitPane Properties
		JSplitPane sPane = new JSplitPane();
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
	
	  /**
     * Update according to the UpdatedType.
     * 
     * @param o
     * @param arg
     */

    public void update(Observable o, Object arg) {
  //      if (arg == UpdatedType.CHAT || arg == UpdatedType.CHAT_AND_BUDDY) {
  //          ((ChatPanel) this.chat).getDisplayPanel().getTxtPane().setText(
  //                  model.getActiveConversation().
  //                  displayMessages());
   //     }        
    }

}