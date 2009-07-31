/* ManageAccountFrame.java
 * 
 * Programmed By:
 *     Vera Lukman
 *     Kevin Fahy
 *     William Chen
 *     
 * Change Log:
 *     2009-May-23, VL
 *         Initial write, stub help window. 
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

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.swing.JPanel;

import no.geosoft.cc.io.Browser;

/**
 * Sets the GUI component of Help pop up window.
 * 
 * This class inherits JPanel methods and variables.
 */
public class HelpPanel extends JPanel {
    /** SignInPanel constructor. It sets up the panel. */
    public HelpPanel() {

    }

    public HelpPanel(String url) {

        try {
            Browser.openUrl(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void googlePanel(String word) throws UnsupportedEncodingException {
    	word = word.replace(' ', '+');
        String url = 
        	"http://www.google.ca/search?client=firefox-a&rls=org.mozilla%3Aen-US%3Aofficial&channel=s&hl=en&q="+word+"&meta=&btnG=Google+Search";
        try {
            Browser.openUrl(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
