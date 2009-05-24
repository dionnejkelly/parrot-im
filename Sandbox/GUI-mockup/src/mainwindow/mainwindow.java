package mainwindow;

import java.awt.Dimension;

import javax.swing.JFrame;

public class mainwindow {

	public static void main (String [] args){
		JFrame frame = new JFrame ("Parrot-IM");
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension (300,500));
		frame.setResizable(false);
		//frame.setMinimumSize(new Dimension (300,500)); // how to set the minimum size to be resized?
		
		frame.getContentPane().add(new signinPanel());
		
		frame.pack();
		frame.setVisible(true);
	}

}
