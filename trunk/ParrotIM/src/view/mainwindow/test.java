package view.mainwindow;

import java.sql.SQLException;

import model.Model;

public class test {
	public static void main (String [] args) throws ClassNotFoundException, SQLException{
		new NewProfileFrame(new Model());
	}

}
