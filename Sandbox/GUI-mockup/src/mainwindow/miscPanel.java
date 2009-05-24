package mainwindow;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.border.EmptyBorder;

public class miscPanel extends JPanel{
	public miscPanel(){
		
		setLayout (new BorderLayout());
		setBorder(BorderFactory.createEmptyBorder(10, 40, 50, 40));
		
		
		//line separator
		JSeparator line = new JSeparator();
		line.setBorder(new EmptyBorder(20,0,0,0));
		
		//manage account
		JButton options = new JButton ("Options");//underline?
		options.setBorderPainted(false);
		options.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	options_ActionPerformed(evt);
            }
        });
		
		//guest account
		JButton help = new JButton ("Help"); //underline?
		help.setBorderPainted(false);
		help.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                help_ActionPerformed(evt);
            }
        });
		
		add (options, BorderLayout.CENTER);
		add (help, BorderLayout.SOUTH);
		add (line, BorderLayout.NORTH);
		
	}
	public void options_ActionPerformed(ActionEvent e) {
		JFrame accMAN = new JFrame ("Options");
		accMAN.getContentPane().add(new optionPanel());
		
		
		accMAN.pack();
		accMAN.setVisible(true);
	} 
	public void help_ActionPerformed(ActionEvent e) {
		JFrame accMAN = new JFrame ("Help");
		accMAN.getContentPane().add(new helpPanel());
		
		
		accMAN.pack();
		accMAN.setVisible(true);
	} 
}
