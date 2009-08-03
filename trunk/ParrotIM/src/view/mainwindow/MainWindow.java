/* mainwindow.java
 * 
 * Programmed By:
 * 	   Vera Lukman
 *     Jordan Fox
 *     Kevin Fahy
 * 	   William Chen
 * 	   Aaron Siu (ANS)
 *     
 * Change Log:
 *     2009-May-23, VL (r443)
 *         Initial write.
 *     2009-May-24, VL 
 *     	   Added more components: user combobox, manage account,
 *         guest account login, help, options, sign in button
 *         (manage account and guest account login frame were
 *         provided as well)
 *     2009-May-24, JF
 *     	   Set icon to default parrotIM icon.
 *     2009-May-26, VL
 *     	   Changed the class into JFrame's child.
 *         Connected to buddylist (by clicking sign in button)
 *     2009-May-27, VL
 *         Fixed layout, changed buttons into labels
 *     2009-June-1, ANS | 2009-June-3, VL
 *         Reorganized code.
 *     2009-June-4, JF
 *     	   Integrated sign in panel with control. Now able to sign
 *         in using the GUI and connect it to the buddylist
 *     2009-June-4, VL
 *         Reorganized code, separated some components into different
 *         classes.
 *     2009-June-5, KF
 *     	   Integrated GUI with model.
 *     2009-June-8, KF/WC
 *     	   Implemented observer
 *     2009-June-8, AS
 *     	   Integrated with database
 *     2009-June-13, WC
 *         Transferred file over to new project, ParrotIM.
 *     2009-June-23, KF
 *         Naming convention updates. Changed all class names.
 *         
 * Known Issues:
 *     None
 * 
 * Copyright (C) 2009  Pirate Captains
 * 
 * Full license can be found in ParrotIM/LICENSE.txt.
 */

package view.mainwindow;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.Point;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import model.Model;
import model.enumerations.UpdatedType;
import controller.MainController;

/**
 * The container frame of SignInPanel.
 * 
 * This class inherits JFrame methods and variables, and implements Observer.
 */
public class MainWindow extends JFrame implements Observer {
    private boolean allowAutoSignIn;
    private JFrame mainwindow;
    private TrayIcon trayIcon;
    private SystemTray tray;
    /**
     * Sets the title of the window, size, and default close operation. called
     * upon program start up
     * 
     * @param chatClient
     * @param model
     */
    public MainWindow(MainController chatClient, Model model) {
        allowAutoSignIn = true;
        setFrame(chatClient, model);
    }

    /**
     * sets the location to be at location called upon sign out
     * 
     * @param chatClient
     * @param model
     * @param location
     * */
    public MainWindow(MainController chatClient, Model model, Point location) {
        // set Main Window Frame
        this.setLocation(location);
        allowAutoSignIn = false;
        setFrame(chatClient, model);
    }

    private void setFrame(MainController chatClient, Model model) {
        setTitle("Parrot-IM");
        mainwindow = this;
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setMinimumSize(new Dimension(300, 500));
        setIconImage(new ImageIcon(this.getClass().getResource(
                "/images/mainwindow/logo.png")).getImage());

        SignInPanel signinPanel =
                new SignInPanel(this, chatClient, model, allowAutoSignIn);
        // call SignIn Panel
        getContentPane().add(signinPanel);
        if (signinPanel.dontInstantiate) {
            this.dispose();
        } else {
            pack();
            setVisible(true);

            // Testing for model observers
            model.addObserver(this);
            

            //SYSTEM TRAY
            if (SystemTray.isSupported()) {

                tray = SystemTray.getSystemTray();
                Image image = new ImageIcon(this.getClass().getResource("/images/mainwindow/logo.png")).getImage();
       
                PopupMenu popup = new PopupMenu();
                MenuItem defaultItem = new MenuItem("Exit");
                defaultItem.addActionListener(new SysTrayExitListener());
                popup.add(defaultItem);

                trayIcon = new TrayIcon(image, "Parrot IM", popup);  
                trayIcon.setImageAutoSize(true);
                trayIcon.addMouseListener(new SysTrayMouseListener());
                try {
                    tray.add(trayIcon);
                } catch (AWTException e) {
                    System.err.println("TrayIcon could not be added.");
                }
            }
        }
    }
    
    private class SysTrayExitListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }
    private class SysTrayMouseListener implements MouseListener{
        public void mouseClicked(MouseEvent e) {
        	trayIcon.displayMessage("ParrotIM", "ParrotIM is not Signed in",
                    TrayIcon.MessageType.INFO);
        	System.out.println("Tray Icon - Mouse clicked!"); 
        }

        public void mouseEntered(MouseEvent e) {
        	trayIcon.displayMessage("ParrotIM", "ParrotIM is not Signed in",
                    TrayIcon.MessageType.INFO);
        	System.out.println("Tray Icon - Mouse entered!");
        }

        public void mouseExited(MouseEvent e) {
        	System.out.println("Tray Icon - Mouse exited!");
        }

        public void mousePressed(MouseEvent e) {
        	System.out.println("Tray Icon - Mouse pressed!"); 
        }

        public void mouseReleased(MouseEvent e) {
        	mainwindow.setVisible(true);      
        	System.out.println("Tray Icon - Mouse released!"); 
        }
    }
    
    protected void removeTray (){
    	if (tray != null){
            tray.remove(trayIcon);
    	}
    }

    /**
     * Links the MainWindow with the observer.
     * 
     * @param t
     * @param o
     */
    public void update(Observable t, Object o) {
        if (o == UpdatedType.ALL && o == UpdatedType.MAIN) {
            System.out.println("Observed!" + o);
        } else if (o == UpdatedType.PROFILE) {

        }
    }
}