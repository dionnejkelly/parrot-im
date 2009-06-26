/*
 * BuddyManage.java
 *
 *
 *
 */

package view.options;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;

/**
 * The BuddyManage is currently responsible for providing the appropriate
 * panels for Parrot IM users with their preference settings.
 * 
 */


public class BuddyManage extends JComponent implements ListSelectionListener, ActionListener
{
	 
    // Section
    // I - Non-Static Data Members

    /**
     * The JPanel buddy data.
    */
	
	private JPanel pBuddy = new JPanel(false);
	
	/**
     * The JPanel buddy info.
    */
	
    private JPanel pInfo = new JPanel(false);
    
    /**
     * The JPanel button data.
    */
    
    private JPanel pButton = new JPanel(false);
    
    /**
     * The JPanel checklist data.
    */
    
    private JPanel pCheckList = new JPanel(false);
    
    /**
     * The JPanel edit data.
    */
    
    
	private JPanel pEdit = new JPanel(false);
	
	/**
     * The JPanel entry data.
    */
	
	private JPanel pEntry = new JPanel(false);
	//private JPanel pEmpty = new JPanel(false);

	/**
     * The JList buddy data.
    */
	
    private JList lBuddy;
    
	/**
     * The JList scrollpane data.
    */
    
	private JScrollPane listScrollPane;

	/**
     * The add button.
    */
	
	private JButton bAdd;
	
	/**
     * The delete button.
    */
	
	private JButton bDelete;
	
	/**
     * The block button.
    */
	
	private JButton bBlock;

	/**
     * The ok button.
    */
	
    private JButton bOK;
    
    /**
     * The cancel button.
    */
    
	private JButton bCancel;

	// Section
    // II - Constructor

    /**
     * BuddyManage() connects you to the Buddy Manager handler. 
     * Every time you want to run a BuddyManager window you have to
     * "BuddyManage buddyManage = new BuddyManage();" 
     */
	
    public BuddyManage() {

        pBuddy.setLayout(new BorderLayout());
        JLabel filler = new JLabel("Account Manage");
        filler.setHorizontalAlignment(JLabel.CENTER);


		lBuddy = new JList();
		//you can add buddylist here for modification
		lBuddy.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		lBuddy.setSelectedIndex(0);
		lBuddy.addListSelectionListener(this);
//		lBuddy.setVisibleRowCount(30);
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

		pButton.setLayout(new BorderLayout());
        pButton.add(pEdit, BorderLayout.NORTH);
        //pButtom.add(pEmpty,BorderLayout.CENTER);
        pButton.add(pEntry, BorderLayout.SOUTH);

        pInfo.setLayout(new BorderLayout());
        pInfo.add(pCheckList, BorderLayout.NORTH);
        pInfo.add(pButton, BorderLayout.SOUTH);

        pBuddy.add(filler, BorderLayout.NORTH);
        pBuddy.add(pInfo, BorderLayout.CENTER);
    }
    
    /**
     * Check for user's action
     * 
     *  @param event
     */

    public void actionPerformed(ActionEvent event) {
        if ("OK".equals(event.getActionCommand()))
        {

        }
        else if ("Cancel".equals(event.getActionCommand()))
        {

        }
        else if ("Add".equals(event.getActionCommand()))
        {

        }
        else if ("Delete".equals(event.getActionCommand()))
        {

        }
        else if ("Block".equals(event.getActionCommand()))
        {

        }
    }
    
    /**
     * Changes the value
     * 
     *  @param event
     */

	public void valueChanged(ListSelectionEvent event) {
    }
	
	 /**
     * Returns the buddy
     * 
     *  @return JComponent
     */

    public JComponent getAccount()
    {
        return pBuddy;
    }

}
