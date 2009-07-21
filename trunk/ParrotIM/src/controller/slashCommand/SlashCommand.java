package controller.slashCommand;

import java.sql.SQLException;
import java.util.StringTokenizer;

import controller.MainController;

public class SlashCommand {
	
	private MainController controller;
	public SlashCommand(){ //for testing
	}
	
	public SlashCommand(MainController controller){
		this.controller = controller;
	}
	
	public boolean isSlashCommand(String str){

		if (str.length()<=0 || str.charAt(0) != '/') return false;
		
		StringTokenizer tokenizer = new StringTokenizer (str);
		String token = tokenizer.nextToken();
		boolean hasSetPresence = false;
		
		if (token.compareToIgnoreCase("/online")==0) {
			hasSetPresence = true;
			
		} else if (token.compareToIgnoreCase("/away")==0) {
			hasSetPresence = true;
		} else if (token.compareToIgnoreCase("/busy")==0) {
			hasSetPresence = true;
		} else if (token.compareToIgnoreCase("/offline")==0) {
			hasSetPresence = true;
		}  
		
		System.out.println("hasSetPresence: "+ hasSetPresence);
		
//		if (controller != null) {
			if (hasSetPresence){ //set up the status
				//eg. /away I am away
				//		new presence: away
				//		new status msg: I am away
				try {
					controller.setPresence(token.substring(1,token.length()));
					
					if (tokenizer.countTokens() > 0){
						// will be used to set the status message
						System.out.println("status msg: " + str.substring(token.length()+1));
						controller.setStatus(str.substring(token.length()+1), false);
					}
					
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				System.out.println("status msg: " + str.substring(1));
				controller.setStatus(str.substring(1), false);
			}
//		}
		
		
		return true;
	}
}
