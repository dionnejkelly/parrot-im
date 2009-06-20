package view.chatLog;
import java.sql.SQLException;
import java.util.Vector;

import model.Model;

public class testFrame {
	public static void main (String [] args) throws ClassNotFoundException, SQLException{
		Model model = new Model();
		
		Vector <String> list = model.getBuddyLogList("cmpt275testing@gmail.com");
		//System.out.println(list.size());
		for (int i=0; i<list.size(); i++){
			System.out.println(list.get(i));
		}
		
		Vector <String> datelist = model.getBuddyDateList("cmpt275testing@gmail.com", "littletomato89@gmail.com");
		System.out.println(datelist.size());
		for (int i=0; i<datelist.size(); i++){
			System.out.println(datelist.get(i));
			//System.out.println(model.getMessage(datelist.get(i)));
		}
		//System.out.println("nah");
		new chatLogFrame(model, "cmpt275testing@gmail.com");
		
	}
}
