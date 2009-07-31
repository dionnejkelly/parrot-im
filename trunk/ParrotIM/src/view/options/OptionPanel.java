/* FeaturesPanel.java
 * 
 * Programmed By:
 *     Chenny Huang
 *     Vera Lukman
 *     
 * Change Log:
 *     2009-June-20, CH
 *         Initial write.
 *     2009-June-29, VL
 *         Reorganized code.
 *         
 * Known Issues:
 *     none
 *     
 * Copyright (C) 2009  Pirate Captains
 * 
 * License: GNU General Public License version 2.
 * Full license can be found in ParrotIM/LICENSE.txt.
 */

package view.options;

import java.awt.BorderLayout;
import java.awt.Color;
import java.sql.SQLException;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import model.Model;
import model.dataType.ProfileData;
import view.buddylist.BuddyList;
import view.styles.GPanel;
import controller.MainController;

public class OptionPanel extends GPanel implements Observer {
    // public WindowColors colors = new WindowColors();
    private int lastSelected;
    private JTabbedPane tabbedOptions;
    private Model model;

    public OptionPanel(MainController c, Model model, JFrame optionframe,
            BuddyList buddyFrame) throws ClassNotFoundException, SQLException {
        this.setLayout(new BorderLayout());
        this.setGradientColors(model.primaryColor, model.secondaryColor);
        this.model = model;
        model.addObserver(this);
        lastSelected = 0;
        ProfileData profile = model.getCurrentProfile();
        // tabbed options
        tabbedOptions = new JTabbedPane(SwingConstants.TOP);
        tabbedOptions.addChangeListener(new OptionChangeListener());

        // tab color settings
        tabbedOptions.setForeground(Color.BLACK);
        tabbedOptions.setBackground(Color.WHITE);

        tabbedOptions.addTab("Personal Profile", new PersonalProfileTab(c,
                buddyFrame.getAccountInfo(), model));
        tabbedOptions.addTab("Manage Accounts", new ManageAccount(profile,
                buddyFrame, model, c));
        tabbedOptions.addTab("Features Settings", new FeaturesPanel(c,
                optionframe, model));
        tabbedOptions.addTab("Colors/Themes", new PreferencePanel(c,
                optionframe, model));
        // setting layout
        this.add(tabbedOptions);
    }

    private class OptionChangeListener implements ChangeListener {

        public void stateChanged(ChangeEvent arg0) {
            // TODO Auto-generated method stub
            if (model.getCurrentProfile().isGuestAccount()
                    && tabbedOptions.getSelectedIndex() == 1) {
                JOptionPane
                        .showMessageDialog(
                                null,
                                "Guest is not allowed to add/remove accounts. \n"
                                        + "Please create a profile to access this feature",
                                "Information", JOptionPane.INFORMATION_MESSAGE);
                tabbedOptions.setSelectedIndex(lastSelected);
            } else {
                lastSelected = tabbedOptions.getSelectedIndex();
            }
        }

    }

	public void update(Observable o, Object arg) {
		setGradientColors(model.primaryColor, model.secondaryColor);
		updateUI();
	}
}
