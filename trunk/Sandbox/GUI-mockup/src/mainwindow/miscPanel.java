package mainwindow;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.border.EmptyBorder;

public class miscPanel extends JPanel{
	public miscPanel(){
		GridLayout miscLayout = new GridLayout(3,1);
		miscLayout.setVgap(10);
		setLayout (miscLayout);
		setBorder(BorderFactory.createEmptyBorder(0, 40, 40, 40));
		this.setOpaque(false);
		
		//line separator
		JSeparator line = new JSeparator();
		
		//TODO : set them to center alignment
		//manage account
		JLabel options = new JLabel ("Options", JLabel.CENTER);
		options.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				options_MouseClicked(evt);
            }
        });
		
		//guest account
		JLabel help = new JLabel ("Help", JLabel.CENTER);
		help.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
            	help_MouseClicked(evt);
            }
        });
		
		add (line);
		add (options);
		add (help);
	}
	private void options_MouseClicked(java.awt.event.MouseEvent evt){
		JFrame accMAN = new JFrame ("Options");
		accMAN.setLocation(100,100);
		accMAN.getContentPane().add(new optionPanel());
		accMAN.setIconImage(new ImageIcon(getcwd() + "/src/mainwindow/logo.png").getImage());
		
		accMAN.pack();
		accMAN.setVisible(true);
	} 
	public void help_MouseClicked(java.awt.event.MouseEvent e) {
		JFrame accMAN = new JFrame ("Help");
		accMAN.setLocation(100,100);
		accMAN.getContentPane().add(new helpPanel());
		accMAN.setIconImage(new ImageIcon(getcwd() + "/src/mainwindow/logo.png").getImage());
		
		accMAN.pack();
		accMAN.setVisible(true);
	} 
	public static String getcwd() { 
	    String cwd = System.getProperty("user.dir"); 
	    return cwd; 
	}
}