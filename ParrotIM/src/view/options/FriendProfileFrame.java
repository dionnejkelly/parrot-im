package view.options;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;

import view.styles.AvatarLabel;


public class FriendProfileFrame extends JFrame{
	
	public FriendProfileFrame(){
		
		/*LEFT COMPONENTS*/
		//avatar	
		JPanel avatarPanel = new JPanel();
		avatarPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));
		avatarPanel.setLayout(new BorderLayout());
		avatarPanel.add(new AvatarLabel
				(getClass().getClassLoader().getResource("images/buddylist/logoBox.png")), BorderLayout.NORTH);
		
		//set left layout
		JPanel leftLayout = new JPanel();
		leftLayout.setLayout(new BorderLayout());
		leftLayout.add(avatarPanel, BorderLayout.WEST);
		leftLayout.add(new JSeparator(SwingConstants.VERTICAL), BorderLayout.CENTER);
		
		/*RIGHT COMPONENTS*/
        //set right layout
        JPanel rightLayout = new JPanel();
        rightLayout.setBorder(BorderFactory.createEmptyBorder(0,10,0,0));
//        rightLayout.setLayout(new BoxLayout(rightLayout, BoxLayout.Y_AXIS));
        rightLayout.setLayout(new GridLayout(14,1));
        rightLayout.setAlignmentY(CENTER_ALIGNMENT);
        //get these from 
        rightLayout.add (new JLabel("<html><font color = GRAY>Informations</font></html>"));
        rightLayout.add (new JLabel("Full name: "));
        rightLayout.add (new JLabel("Nick name: "));
        rightLayout.add (new JLabel("Organization: "));
        rightLayout.add (new JSeparator(SwingConstants.HORIZONTAL));
        rightLayout.add (new JLabel("<html><font color = GRAY>Home</font></html>"));
        rightLayout.add (new JLabel("Address: "));
        rightLayout.add (new JLabel("Phone: "));
        rightLayout.add (new JLabel("Email: "));
        rightLayout.add (new JSeparator(SwingConstants.HORIZONTAL));
        rightLayout.add (new JLabel("<html><font color = GRAY>Office</font></html>"));
        rightLayout.add (new JLabel("Address: "));
        rightLayout.add (new JLabel("Phone: "));
        rightLayout.add (new JLabel("Email: "));
        
		
        /*SET LAYOUT*/
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.add (leftLayout, BorderLayout.WEST);
        mainPanel.add (rightLayout, BorderLayout.CENTER);
		
		
		this.getContentPane().add(mainPanel);
		this.setResizable(false);
		this.pack();
		this.setVisible(true);
		setIconImage(new ImageIcon("src/images/mainwindow/logo.png").getImage());
	}
	

}
