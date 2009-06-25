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
import java.io.*;
import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.plaf.basic.BasicFileChooserUI;
import java.util.*;
import javax.imageio.*;


public class SettingManage extends JComponent implements ItemListener, ActionListener
{
	JPanel pSetting = new JPanel(false);
    JPanel pInfo = new JPanel(false);
    JPanel pCheckList = new JPanel(false);
	JPanel pEntry = new JPanel(false);

    JCheckBox cChart;
    JCheckBox cSound;

    JButton bOK;
	JButton bCancel;

    public SettingManage() {

        pSetting.setLayout(new BorderLayout());
        JLabel filler = new JLabel("Settings");
        filler.setHorizontalAlignment(JLabel.CENTER);

        pInfo.setLayout(new GridLayout(0, 1));



        cChart = new JCheckBox("Spam filter");
        cChart.setSelected(true);
        cSound = new JCheckBox("Sound effect");
        cSound.setSelected(true);

        cChart.addItemListener(this);
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
        pCheckList.add(cChart);
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

    public void actionPerformed(ActionEvent e) {
        if ("OK".equals(e.getActionCommand()))
        {
        	
        }
        else if ("Cancel".equals(e.getActionCommand()))
        {

        }

    }

    public void itemStateChanged(ItemEvent e) {

    }

    public JComponent settings()
    {
        return pSetting;
    }

}
