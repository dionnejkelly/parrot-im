
package view.options;

import javax.swing.*;

import java.awt.*;

public class OptionMenu extends JPanel {
	private JFrame frame;
	
    public OptionMenu() {
        frame = new JFrame("User Preference");
 
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
 
        frame.getContentPane().add(this.optionMenu());
        frame.pack();
   
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);


        frame.setVisible(true);
    }

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
        JComponent panel2 = buddyPanel.account();
        panel2.setPreferredSize(new Dimension(400,200));
        tabbedPane.addTab("Account Management", panel2);

		SettingManage settingsPanel = new SettingManage();
        JComponent panel3 = settingsPanel.settings();
        tabbedPane.addTab("Settings", panel3);

        options.add(tabbedPane);

        return options;
	}

}