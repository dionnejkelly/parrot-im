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
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.MalformedURLException;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;

import controller.MainController;

import view.buddylist.AccountInfo;
import view.styles.AvatarLabel;
import view.styles.PmLabel;
import view.styles.StatusCombo;

public class PersonalProfileTab extends JPanel {
	//left components
	private AvatarLabel avatar;
	
	//right components
	private JTextArea personalMessage;
	
	private PmLabel BuddyListPM;
	private StatusCombo BuddyListStatus;
	private StatusCombo status;
	
	public PersonalProfileTab(MainController core, AccountInfo accInfo){
		super(false);
		this.BuddyListStatus = accInfo.presence;
        this.BuddyListPM = accInfo.statusMessage;
        
		/*LEFT COMPONENTS*/
		//avatar
		avatar = new AvatarLabel(core, accInfo.avatarDisplay);
		//browse button
		JButton browseButton = new JButton("browse");
		browseButton.addActionListener(new BrowseActionListener());
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(browseButton);
		
		JPanel avatarPanel = new JPanel();
		avatarPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
		avatarPanel.setLayout(new BorderLayout());
		avatarPanel.add(avatar, BorderLayout.NORTH);
		avatarPanel.add(buttonPanel, BorderLayout.CENTER);
		
		//set left layout
		JPanel leftLayout = new JPanel();
		leftLayout.setLayout(new BorderLayout());
		leftLayout.add(avatarPanel, BorderLayout.WEST);
		leftLayout.add(new JSeparator(SwingConstants.VERTICAL), BorderLayout.CENTER);
		
		/*RIGHT COMPONENTS*/
        //personal message
        personalMessage = new JTextArea();
        personalMessage.setText(accInfo.statusMessage.getText());
        personalMessage.setLineWrap(true);
        personalMessage.addKeyListener(new pmKeyListener());
        JScrollPane pmScroll = new JScrollPane(personalMessage);
        pmScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        pmScroll.setPreferredSize(new Dimension(pmScroll.getWidth(), 150));
		JPanel pmPanel = new JPanel();
		pmPanel.setLayout(new BorderLayout());
		JLabel personalMessage = new JLabel("Personal Message:");
		personalMessage.setForeground(Color.pink.darker());
		pmPanel.add (personalMessage, BorderLayout.NORTH);
		pmPanel.add (pmScroll, BorderLayout.CENTER);
        
        //status
        status = new StatusCombo(core);
        status.setMaximumSize(new Dimension(200, 30));
        status.setSelectedIndex(BuddyListStatus.getSelectedIndex());
        status.addItemListener(new statusItemListener());
        JPanel statusPanel = new JPanel();
        statusPanel.setBorder(BorderFactory.createEmptyBorder(5,0,0,0));
        statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.X_AXIS));
        JLabel personalStatus = new JLabel("Status: ");
        personalStatus.setForeground(Color.pink.darker());
        statusPanel.add(personalStatus);
        statusPanel.add(status);
        
        //set top right layout
        JPanel topRightLayout = new JPanel();
        topRightLayout.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
        topRightLayout.setLayout(new BorderLayout());
        topRightLayout.add (pmPanel, BorderLayout.NORTH);
		topRightLayout.add (statusPanel, BorderLayout.CENTER);
		
        //set right layout
        JPanel rightLayout = new JPanel();
        rightLayout.setLayout(new BorderLayout());
        rightLayout.add (topRightLayout, BorderLayout.NORTH);
		
        /*SET LAYOUT*/
        this.setLayout(new BorderLayout());
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        this.add (leftLayout, BorderLayout.WEST);
		this.add (rightLayout, BorderLayout.CENTER);
	}
	
	private class BrowseActionListener implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			try {
				avatar.changeAvatarWindow();
//				avatar.synchAvatar(BuddyListAvatar);
			} catch (MalformedURLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}
	private class statusItemListener implements ItemListener{

		public void itemStateChanged(ItemEvent e) {
			BuddyListStatus.setSelectedIndex(status.getSelectedIndex());
			BuddyListStatus.updateUI();
		}
		
	}
	
	private class pmKeyListener implements KeyListener{

		//@Override
		public void keyPressed(KeyEvent arg0) {}

		//@Override
		public void keyReleased(KeyEvent arg0) {
			BuddyListPM.setText(personalMessage.getText());
		}

		//@Override
		public void keyTyped(KeyEvent arg0) {}

		
	}
}
