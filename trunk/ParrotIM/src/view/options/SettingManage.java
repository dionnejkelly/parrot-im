/*
 * SettingManage.java
 *
 *
 *
 */

package view.options;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

/**
 * The SettingManage is currently responsible for providing the setting
 * management for Parrot IM users with their preference settings.
 * 
 */

public class SettingManage extends JComponent implements ItemListener, ActionListener
{
	// Section
    // I - Non-Static Data Members

    /**
     * The JPanel setting data.
    */
	
	private JPanel pSetting = new JPanel(false);
	
	/**
     * The JPanel info data.
    */
	
	private JPanel pInfo = new JPanel(false);
    
    /**
     * The JPanel checklist data.
    */
    
    private JPanel pCheckList = new JPanel(false);
    
    /**
     * The JPanel entry data.
    */
    
    private JPanel pEntry = new JPanel(false);

	/**
     * The JCheckBox spam filter data.
    */
	
	private JCheckBox cSpamFilter;
    
    /**
     * The JCheckBox sound data.
    */
    
    private JCheckBox cSound;

    /**
     * The OK button.
    */
    
    private JButton bOK;
    
    /**
     * The Cancel button.
    */
    private JButton bCancel;

    // Section
    // II - Constructor

    /**
     * SettingManage() connects you to the Setting Manager handler. 
     * Every time you want to run a SettingManage window you have to
     * "SettingManage settingManage = new SettingManage();" 
     */
    
    public SettingManage() {

        pSetting.setLayout(new BorderLayout());
        JLabel filler = new JLabel("Settings");
        filler.setHorizontalAlignment(JLabel.CENTER);

        pInfo.setLayout(new GridLayout(0, 1));



        cSpamFilter = new JCheckBox("Spam filter");
        cSpamFilter.setSelected(true);
        cSound = new JCheckBox("Sound effect");
        cSound.setSelected(true);

        cSpamFilter.addItemListener(this);
        cSound.addItemListener(this);

        bOK = new JButton("OK");
        bOK.setVerticalTextPosition(AbstractButton.CENTER);
        bOK.setHorizontalTextPosition(AbstractButton.CENTER);
        bOK.setActionCommand("OK");

        bCancel = new JButton("Cancel");
        bCancel.setVerticalTextPosition(AbstractButton.CENTER);
        bCancel.setHorizontalTextPosition(AbstractButton.CENTER);
        bCancel.setActionCommand("Cancel");

        pCheckList.setLayout(new GridLayout(5, 1));
        pCheckList.add(cSpamFilter);
        pCheckList.add(cSound);


        bOK.addActionListener(this);
        bCancel.addActionListener(this);
        pEntry.setLayout(new GridLayout(1, 2));
        pEntry.add(bOK);
        pEntry.add(bCancel);

        pInfo.setLayout(new BorderLayout());
        pInfo.add(pCheckList, BorderLayout.NORTH);
        pInfo.add(pEntry, BorderLayout.SOUTH);



        pSetting.add(filler, BorderLayout.NORTH);
        pSetting.add(pInfo, BorderLayout.CENTER);
    }

    
    /**
     * Checks for user's action.
     * 
     * @param event
     */
    
    
    public void actionPerformed(ActionEvent event) {
        if ("OK".equals(event.getActionCommand()))
        {
        	
        }
        else if ("Cancel".equals(event.getActionCommand()))
        {

        }
        
        String resultMessage =
            "Sorry for the inconvenience but for the Alpha Version, we are not supporting this feature. Thank you for your co-operation.";
        JOptionPane.showMessageDialog(null, resultMessage);

    }

    /**
     * Returns user's setting.
     * 
     * @return JComponent
     */

    public JComponent settings()
    {
        return pSetting;
    }


    /**
     * Changes the item's state.
     * 
     * @param event
     */
    
	public void itemStateChanged(ItemEvent event) {
		
		
	}

}
