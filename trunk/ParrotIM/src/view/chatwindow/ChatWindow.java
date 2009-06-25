/* ChatWindow.java
 *  
 * Copyright (C) 2009  Pirate Captains
 * 
 * License: GNU General Public License version 2.
 * Full license can be found in ParrotIM/LICENSE.txt.
 */

package view.chatwindow;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import controller.MainController;

import java.util.Observable;
import java.util.Observer;

import view.styles.ChatWindowListener;

import model.Model;
import model.enumerations.UpdatedType;

public class ChatWindow extends JFrame implements Observer {
    public JPanel main;
    private Model model;
    private boolean windowIsOpen;

    public ChatWindow(MainController c, Model model) {
        super("Chat Window");
        
        model.addObserver(this);

        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        this.model = model;

        setIconImage(new ImageIcon(this.getClass().getResource(
                "/images/mainwindow/logo.png")).getImage());

        this.addWindowListener(new ChatWindowListener(this.model));

        main = new MainPanel(c, model);
        
        getContentPane().add(main);

        pack();
        setVisible(true);
    }

    //Getters
    public boolean getWindowIsOpen() {
        return this.windowIsOpen;
    }
    
    //Setters
    public void setWindowIsOpen(boolean isOpen) {
        this.windowIsOpen = isOpen;
    }

    //Update Method
    public void update(Observable o, Object arg) {
        if (arg == UpdatedType.CHAT || arg == UpdatedType.CHAT_AND_BUDDY) {
            if (!this.isVisible()) {
                this.setVisible(true);
            }
        }
        return;
    }
}
