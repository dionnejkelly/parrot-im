package view.mainwindow;

import java.awt.Dimension;
import java.sql.SQLException;
import java.util.*;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import controller.MainController;
import controller.services.Xmpp;


import model.*;
import model.dataType.CurrentProfileData;
import model.dataType.UpdatedType;

public class mainwindow extends JFrame implements Observer {

	public mainwindow (Xmpp chatClient, Model model) 
	        throws ClassNotFoundException, SQLException {

		//set Main Window Frame
		setTitle("Parrot-IM");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setMinimumSize(new Dimension (300,500));
		setPreferredSize(new Dimension (300,600));
		setIconImage(new ImageIcon(getcwd() + "/images/mainwindow/logo.png").getImage());

		//call SignIn Panel
		getContentPane().add(new signinPanel(this, chatClient, model));
		
		pack();
		setVisible(true);
		
		// Testing for model observers
		model.addObserver(this);
		CurrentProfileData a = new CurrentProfileData();
	}

	public String getcwd() {
	    String cwd = System.getProperty("user.dir");
	    return cwd;
	}
	
	public void update(Observable t, Object o) {
	    if (o == UpdatedType.ALL && o == UpdatedType.MAIN) {
	        System.out.println("Observed!" + o);
	    }	    
	}
}