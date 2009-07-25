package view.buddylist;

/* AddBuddyFrame.java
 * 
 * Programmed By:
 *     Kevin Fahy
 *     Vera Lukman
 *     
 * Change Log:
 *     2009-July-25, KF
 *         Initialized code.
 *     2009-July-25, VL
 *         Reorganized code.
 *         
 * Copyright (C) 2009  Pirate Captains
 * 
 * License: GNU General Public License version 2.
 * Full license can be found in ParrotIM/LICENSE.txt.
 */

import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import controller.MainController;
import model.Model;

public class AddBuddyFrame extends JFrame {
	
    public AddBuddyFrame(Model model, MainController controller) {
        setLocationRelativeTo(null);

        setTitle("Add Buddy");
        setLocationRelativeTo(null);
        
        setPreferredSize(new Dimension(350, 200));
        
        setResizable(false);
        AddBuddyPanel accountPanel = new AddBuddyPanel(model, controller, this);
        getContentPane().add(accountPanel);
        
        pack();
        setVisible(true);
        setIconImage(new ImageIcon("src/images/mainwindow/logo.png").getImage());
    }
    
}