package view.mainwindow;


import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.image.ImageObserver;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import view.styles.linkLabel;

public class miscPanel extends JPanel{
	public miscPanel(){
		GridLayout miscLayout = new GridLayout(3,1);
		miscLayout.setVgap(10);
		setLayout (miscLayout);
		setBorder(BorderFactory.createEmptyBorder(0, 40, 40, 40));
		this.setOpaque(false);
		
		//line separator
		JSeparator line = new JSeparator();
		
		//manage account
		linkLabel options = new linkLabel ("Options");
		options.setPreferredSize(new Dimension(30,ImageObserver.HEIGHT));
		options.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				options_MouseClicked(evt);
            }
        });
		
		//guest account
		linkLabel help = new linkLabel ("Help");
		help.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
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
		accMAN.setIconImage(new ImageIcon(getcwd() + "/images/mainwindow/logo.png").getImage());
		
		accMAN.pack();
		accMAN.setVisible(true);
	} 
	private void help_MouseClicked(java.awt.event.MouseEvent e) {
		JFrame accMAN = new JFrame ("Help");
		accMAN.setLocation(100,100);
		accMAN.getContentPane().add(new helpPanel());
		accMAN.setIconImage(new ImageIcon(getcwd() + "/images/mainwindow/logo.png").getImage());
		
		accMAN.pack();
		accMAN.setVisible(true);
	} 
	public static String getcwd() { 
	    String cwd = System.getProperty("user.dir"); 
	    return cwd; 
	}
}