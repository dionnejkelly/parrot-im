package view.options;

import java.sql.SQLException;

import model.Model;
import controller.MainController;


public class test {
	
	public static void main (String [] args) throws ClassNotFoundException, SQLException{
		Model model= new Model();
		MainController c = new MainController(model);
		new OptionFrame (c, model, "cmpt275testing");
	}

}
