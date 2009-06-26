
package view.options;

import javax.swing.*;
import java.awt.*;

/**
 * The OptionMenu is currently responsible for providing option menu for 
 * Parrot IM users with their preference settings.
 * 
 */

public class OptionMenu extends JPanel {
	
	// Section
    // I - Non-Static Data Member

    /**
     * The options window frame.
    */
	private JFrame frame;
	
	// Section
    // II - Constructor

    /**
     * OptionMenu() connects you to the Option Menu handler. 
     * Every time you want to run a OptionMenu window you have to
     * "OptionMenu optionMenu = new OptionMenu();" 
     */
	
	
    public OptionMenu() {
        frame = new JFrame("User Preference");
 
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
 
        frame.getContentPane().add(this.optionMenu());
        frame.pack();
   
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);


        frame.setVisible(true);
    }

    /**
     * Returns the option menu.
     * 
     * @return JPanel
     */ 
    
	private JPanel optionMenu()
	{
		JPanel options = new JPanel();

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setPreferredSize(new Dimension (400,300));
		
        ProfileManage profilePanel = new ProfileManage();
        JComponent panel1 = profilePanel.profile();
        panel1.setPreferredSize(new Dimension(400, 200));
        tabbedPane.addTab("Personal Profile", panel1);

		BuddyManage buddyPanel = new BuddyManage();
        JComponent panel2 = buddyPanel.getAccount();
        panel2.setPreferredSize(new Dimension(400,200));
        tabbedPane.addTab("Account Management", panel2);

		SettingManage settingsPanel = new SettingManage();
        JComponent panel3 = settingsPanel.settings();
        tabbedPane.addTab("Settings", panel3);

        options.add(tabbedPane);

        return options;
	}

}