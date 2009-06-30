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
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;

import view.styles.AvatarLabel;

import model.enumerations.StatusType;

public class PersonalProfileTab extends JPanel {
	//left components
	protected AvatarLabel avatar;
	
	//right components
	protected JTextArea personalMessage;
	protected JComboBox status;
	
	public PersonalProfileTab(){
		super(false);
        
		/*LEFT COMPONENTS*/
		//avatar
		avatar = new AvatarLabel(getClass().getClassLoader().getResource("images/buddylist/logoBox.png"));
		//browse button
		JButton browseButton = new JButton("browse");
		browseButton.addActionListener(new BrowseActionListener());
		JPanel avatarPanel = new JPanel();
		avatarPanel.setLayout(new BoxLayout(avatarPanel, BoxLayout.Y_AXIS));
		avatarPanel.add(avatar);
		avatarPanel.add(browseButton);
		
		//set left layout
		JPanel leftLayout = new JPanel();
		leftLayout.setLayout(new BorderLayout());
		leftLayout.add(avatarPanel, BorderLayout.WEST);
		leftLayout.add(new JSeparator(SwingConstants.VERTICAL), BorderLayout.CENTER);
		
		/*RIGHT COMPONENTS*/
        //personal message
        personalMessage = new JTextArea();
        personalMessage.setLineWrap(true);
        JScrollPane pmScroll = new JScrollPane(personalMessage);
        pmScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        pmScroll.setPreferredSize(new Dimension(pmScroll.getWidth(), 150));
		JPanel pmPanel = new JPanel();
		pmPanel.setLayout(new BorderLayout());
		pmPanel.add (new JLabel("Personal Message:"), BorderLayout.NORTH);
		pmPanel.add (pmScroll, BorderLayout.CENTER);
        
        //status
        status = new JComboBox(StatusType.getStatusList());
        status.setMaximumSize(new Dimension(200, 30));
        JPanel statusPanel = new JPanel();
        statusPanel.setBorder(BorderFactory.createEmptyBorder(5,0,0,0));
        statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.X_AXIS));
        statusPanel.add(new JLabel("Status: "));
        statusPanel.add(status);
        
        //set top right layout
        JPanel topRightLayout = new JPanel();
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
			avatar.changeAvatarWindow();
			
		}
		
	}
}
