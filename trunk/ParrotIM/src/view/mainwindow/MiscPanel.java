package view.mainwindow;

import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import view.styles.LinkLabel;

public class MiscPanel extends JPanel{
	public MiscPanel(){
		GridLayout miscLayout = new GridLayout(2,1);
		miscLayout.setVgap(5);
		setLayout (miscLayout);
		setBorder(BorderFactory.createEmptyBorder(0, 40, 40, 40));
		this.setOpaque(false);
		
		//line separator
		JSeparator line = new JSeparator();
		
		//guest account
		LinkLabel help = new LinkLabel ("Help");
		help.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
			public void mouseClicked(java.awt.event.MouseEvent evt) {
            	help_MouseClicked(evt);
            }
        });
		
		add (line);
		add (help);
	}
//	private void options_MouseClicked(java.awt.event.MouseEvent evt){
//		JFrame accMAN = new JFrame ("Options");
//		accMAN.setLocation(100,100);
//		accMAN.getContentPane().add(new optionPanel());
//		accMAN.setIconImage(new ImageIcon(getcwd() + "/images/mainwindow/logo.png").getImage());
//		
//		accMAN.pack();
//		accMAN.setVisible(true);
//	} 
	private void help_MouseClicked(java.awt.event.MouseEvent e) {
		JFrame accMAN = new JFrame ("Help");
		accMAN.setLocation(100,100);
		accMAN.getContentPane().add(new HelpPanel());
		accMAN.setIconImage(new ImageIcon(getcwd() + "/images/mainwindow/logo.png").getImage());
		
		accMAN.pack();
		accMAN.setVisible(true);
	} 
	public static String getcwd() { 
	    String cwd = System.getProperty("user.dir"); 
	    return cwd; 
	}
}