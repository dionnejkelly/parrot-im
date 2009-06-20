/*
 * ProfileManage.java
 *
 *
 *
 */

package view.options;
//import statements
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.plaf.basic.BasicFileChooserUI;
import java.util.*;
import javax.imageio.*;

/* ProfileManage class
 *
 *
 * Written by : Peng-Tzu Huang
 *
 *
 * Version 20 Jun 2009
 *
 * Revision    Status       Date            By:
 * 1.0         Created      20 Jun 2009     Peng-Tzu Huang
 */

public class ProfileManage extends JComponent implements ActionListener
{
	JPanel pProfile = new JPanel(false);
    JPanel pInfo = new JPanel(false);
    JPanel pCheckList = new JPanel(false);
	JPanel pEntry = new JPanel(false);

    JLabel lName;
    JTextField tName;
    JLabel lPMessage;
	JTextField tPMessage;

    JButton bOK;
	JButton bCancel;

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

    public void actionPerformed(ActionEvent e) {
        if ("OK".equals(e.getActionCommand()))
        {
        }
        else if ("Cancel".equals(e.getActionCommand()))
        {

        }

    }


    public JComponent profile()
    {
        return pProfile;
    }
//----------------------------------------
}
