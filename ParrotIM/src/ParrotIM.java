/* ParrotIM.java
 * 
 * Programmed By:
 *     
 * Change Log:
 *         
 * Known Issues:
 *     1. Should be no need to handle SQL exceptions; should be handled by
 *        the Model.
 *     
 * Copyright (C) 2009  Pirate Captains
 * 
 * License: GNU General Public License version 2.
 * Full license can be found in ParrotIM/LICENSE.txt.
 */

import java.sql.SQLException;

import javax.swing.JOptionPane;

import controller.MainController;

import view.mainwindow.MainWindow;
import model.DatabaseFunctions;
import model.Model;
import model.dataType.ProfileData;

public class ParrotIM {

    public static void main(String[] args) throws ClassNotFoundException,
            SQLException {

        DatabaseFunctions.setDatabaseName("parrot.db");
        Model model = new Model();
        MainController controller = new MainController(model);
        MainWindow mainWindow = new MainWindow(controller, model);

        // Mac OS Menubar optimization
        String lcOSName = System.getProperty("os.name").toLowerCase();

        if (lcOSName.startsWith("mac"))
            System.setProperty("apple.laf.useScreenMenuBar", "true");
        
        // XMPPConnection.DEBUG_ENABLED = true;

        return;
    }
}