package options;

/*
 * BuddyManage.java
 *
 *
 *
 */


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

/* BuddyManage class
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

public class BuddyManage extends JComponent implements ListSelectionListener, ActionListener
{
	private JPanel pBuddy = new JPanel(false);
    private JPanel pInfo = new JPanel(false);
    private JPanel pButtom = new JPanel(false);
    private JPanel pCheckList = new JPanel(false);
	private JPanel pEdit = new JPanel(false);
	private JPanel pEntry = new JPanel(false);

    private JList lBuddy;
	private JScrollPane listScrollPane;

	private JButton bAdd;
	private JButton bDelete;
	private JButton bBlock;

    private JButton bOK;
	private JButton bCancel;

    public BuddyManage() {

        pBuddy.setLayout(new BorderLayout());
        JLabel filler = new JLabel("Account Manage");
        filler.setHorizontalAlignment(JLabel.CENTER);


		lBuddy = new JList();
		//you can add buddylist here for modification
		lBuddy.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		lBuddy.setSelectedIndex(0);
		lBuddy.addListSelectionListener(this);
		lBuddy.setVisibleRowCount(30);
		listScrollPane = new JScrollPane(lBuddy);



        bAdd = new JButton("Add");
        bAdd.setVerticalTextPosition(AbstractButton.CENTER);
        bAdd.setHorizontalTextPosition(AbstractButton.CENTER);
        bAdd.setActionCommand("Add");
        bDelete = new JButton("Delete");
        bDelete.setVerticalTextPosition(AbstractButton.CENTER);
        bDelete.setHorizontalTextPosition(AbstractButton.CENTER);
        bDelete.setActionCommand("Delete");
        bBlock = new JButton("Block");
        bBlock.setVerticalTextPosition(AbstractButton.CENTER);
        bBlock.setHorizontalTextPosition(AbstractButton.CENTER);
        bBlock.setActionCommand("Block");

        bAdd.addActionListener(this);
		bDelete.addActionListener(this);
        bBlock.addActionListener(this);



        pCheckList.setLayout(new GridLayout(0, 1));
        pCheckList.add(listScrollPane);

        bOK = new JButton("OK");
        bOK.setVerticalTextPosition(AbstractButton.CENTER);
        bOK.setHorizontalTextPosition(AbstractButton.CENTER);
        bOK.setActionCommand("OK");
        bCancel = new JButton("Cancel");
        bCancel.setVerticalTextPosition(AbstractButton.CENTER);
        bCancel.setHorizontalTextPosition(AbstractButton.CENTER);
        bCancel.setActionCommand("Cancel");

        bOK.addActionListener(this);
        bCancel.addActionListener(this);

		pEdit.setLayout(new GridLayout(1,3));
		pEdit.add(bAdd);
		pEdit.add(bDelete);
		pEdit.add(bBlock);


        pEntry.setLayout(new GridLayout(1, 2));
        pEntry.add(bOK);
        pEntry.add(bCancel);

		pButtom.setLayout(new BorderLayout());
        pButtom.add(pEdit, BorderLayout.NORTH);
        pButtom.add(pEntry, BorderLayout.SOUTH);

        pInfo.setLayout(new BorderLayout());
        pInfo.add(pCheckList, BorderLayout.NORTH);
        pInfo.add(pButtom, BorderLayout.SOUTH);

        pBuddy.add(filler, BorderLayout.NORTH);
        pBuddy.add(pInfo, BorderLayout.CENTER);
    }

    public void actionPerformed(ActionEvent e) {
        if ("OK".equals(e.getActionCommand()))
        {

        }
        else if ("Cancel".equals(e.getActionCommand()))
        {

        }
        else if ("Add".equals(e.getActionCommand()))
        {

        }
        else if ("Delete".equals(e.getActionCommand()))
        {

        }
        else if ("Block".equals(e.getActionCommand()))
        {

        }
    }

	public void valueChanged(ListSelectionEvent e) {
    }

    public JComponent account()
    {
        return pBuddy;
    }
//----------------------------------------
}
