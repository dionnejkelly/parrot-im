
package view.options;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * The ProfileManage is currently responsible for providing the profile
 * management for Parrot IM users with their preference settings.
 * 
 */


public class ProfileManage extends JComponent implements ActionListener
{
	// Section
    // I - Non-Static Data Members

    /**
     * The JPanel profile data.
    */
	
	private JPanel pProfile = new JPanel(false);
	
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
     * The JLabel name data.
    */
	
	private JLabel lName;
    
    /**
     * The JLabel message data.
    */
	
    
	private JLabel lPMessage;
    
    /**
     * The JTextField name data.
    */
    
	private JTextField tName;
	
	/**
     * The JTextField message data.
    */
  
	private JTextField tPMessage;

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
     * ProfileManage() connects you to the Profile Manager handler. 
     * Every time you want to run a ProfileManage window you have to
     * "ProfileManage profileManage = new ProfileManage();" 
     */
	
    public ProfileManage() {

        pProfile.setLayout(new BorderLayout());
        JLabel filler = new JLabel("Personal Profile");
        filler.setHorizontalAlignment(JLabel.CENTER);



        pInfo.setLayout(new GridLayout(0, 1));


		lName = new JLabel("Name:");
        tName = new JTextField();
		lPMessage = new JLabel("Personal Message");
		tPMessage = new JTextField();


        bOK = new JButton("OK");
        bOK.setVerticalTextPosition(AbstractButton.CENTER);
        bOK.setHorizontalTextPosition(AbstractButton.CENTER);
        bOK.setActionCommand("OK");
        bCancel = new JButton("Cancel");
        bCancel.setVerticalTextPosition(AbstractButton.CENTER);
        bCancel.setHorizontalTextPosition(AbstractButton.CENTER);
        bCancel.setActionCommand("Cancel");

        pCheckList.setLayout(new GridLayout(5, 1));
        pCheckList.add(lName);
        pCheckList.add(tName);
        pCheckList.add(lPMessage);
        pCheckList.add(tPMessage);


        bOK.addActionListener(this);
        bCancel.addActionListener(this);
        pEntry.setLayout(new GridLayout(1, 2));
        pEntry.add(bOK);
        pEntry.add(bCancel);

        pInfo.setLayout(new BorderLayout());
        pInfo.add(pCheckList, BorderLayout.NORTH);
        pInfo.add(pEntry, BorderLayout.SOUTH);



        pProfile.add(filler, BorderLayout.NORTH);
        pProfile.add(pInfo, BorderLayout.CENTER);
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

    }

    /**
     * Returns user's action.
     * 
     * @return JComponent
     */

    public JComponent profile()
    {
        return pProfile;
    }

}
