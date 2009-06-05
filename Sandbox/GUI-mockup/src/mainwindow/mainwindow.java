package mainwindow;

import java.awt.Dimension;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import ChatClient.ChatClient;

public class mainwindow extends JFrame{
	/*THIS IS FOR CHAT CLIENT*/
	String username;
	String password;
	/*THIS IS FOR CHAT CLIENT*/

	public mainwindow (ChatClient c){	

		//set Main Window Frame
		setTitle("Parrot-IM");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setPreferredSize(new Dimension (300,500));
		//setResizable(false);
		setIconImage(new ImageIcon(getcwd() + "/src/mainwindow/logo.png").getImage());

		//call SignIn Panel
		getContentPane().add(new signinPanel(this, c));
		
		pack();
		setVisible(true);
	}

	public String getcwd() {
	    String cwd = System.getProperty("user.dir");
	    return cwd;
	}
}