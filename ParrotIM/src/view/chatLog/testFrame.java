/* testFrame.java
 * 
 * Programmed By:
 *     Vera Lukman
 *     
 * Change Log:
 *     2009-June-16, VL
 *         Initial write. All data was stub, not connected yet to database
 *     2009-June-19, VL
 *         Integrated to access the real database
 *         
 * Known Issues:
 *     1. Missing search bar.
 * 
 * Copyright (C) 2009  Pirate Captains
 * 
 * Full license can be found in ParrotIM/LICENSE.txt.
 */

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
