/*
 * OptionMenu.java
 *
 * This Class is the main class that controls the interface of whole program.
 * it separate the classes to different panels
 *
 */
package view.options;
import javax.swing.*;
import java.awt.*;
import java.util.*;
import javax.swing.event.*;
import java.awt.event.KeyEvent;




public class OptionMenu extends JPanel {
    public OptionMenu() {
        JFrame frame = new JFrame("User Preference");
        //frame.setSize(800,600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //JComponent newContent = new OptionMenu();
        //newContent.setOpaque(true);
        frame.getContentPane().add(this.optionMenu());
        frame.pack();
        //my mods===============================================================
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        //======================================================================

        frame.setVisible(true);
    }

	private JPanel optionMenu()
	{
		JPanel options = new JPanel();

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.LEFT);

        ProfileManage profilePanel = new ProfileManage();
        JComponent panel1 = profilePanel.profile();
        panel1.setPreferredSize(new Dimension(400, 600));
        tabbedPane.addTab("Personal Profile", panel1);

		BuddyManage buddyPanel = new BuddyManage();
        JComponent panel2 = buddyPanel.account();
        tabbedPane.addTab("Account Management", panel2);

		SettingManage settingsPanel = new SettingManage();
        JComponent panel3 = settingsPanel.settings();
        tabbedPane.addTab("Settings", panel3);

        options.add(tabbedPane);

        return options;
	}

}