package controller.slashCommand;

import java.sql.SQLException;
import java.util.StringTokenizer;

import model.enumerations.UserStateType;

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
		
		str = str.substring(1);
		StringTokenizer tokenizer = new StringTokenizer (str);
		String token = tokenizer.nextToken();
		boolean hasSetPresence = false;
		
//		if (token.compareToIgnoreCase("/online")==0) {
//			hasSetPresence = true;
//			
//		} else if (token.compareToIgnoreCase("/away")==0) {
//			hasSetPresence = true;
//		} else if (token.compareToIgnoreCase("/busy")==0) {
//			hasSetPresence = true;
//		} else if (token.compareToIgnoreCase("/offline")==0) {
//			hasSetPresence = true;
//		}  
		
		// one token
		if (token.compareToIgnoreCase(UserStateType.ONLINE.toString())==0 || //online
				token.compareToIgnoreCase(UserStateType.AWAY.toString())==0 || //away
				token.compareToIgnoreCase(UserStateType.BUSY.toString())==0 || //busy
				token.compareToIgnoreCase(UserStateType.INVISIBLE.toString())==0 || //invisible
				token.compareToIgnoreCase(UserStateType.LUNCH.toString())==0 || //lunch
				token.compareToIgnoreCase(UserStateType.OFFLINE.toString())==0 ){//offline
			hasSetPresence = true;
		} else if (token.compareToIgnoreCase("brb")==0 ) { //brb (be right back)
			token = "be right back";
		}
		//two tokens
		if (token.compareToIgnoreCase(UserStateType.NOT_AVAILABLE.toString())==0){ //not available
			hasSetPresence = true;
		} 
		
		//three tokens
		if (token.compareToIgnoreCase(UserStateType.BRB.toString())==0 || //brb (be right back)
				token.compareToIgnoreCase(UserStateType.PHONE.toString())==0) { //on the phone
			hasSetPresence = true;
		} 
		
		//four tokens
		if (token.compareToIgnoreCase(UserStateType.NOT_BE_DISTURBED.toString())==0){ //not to be disturbed
			hasSetPresence = true;
		} 
		
		System.out.println("hasSetPresence: "+ hasSetPresence);
		
//		if (controller != null) {
			if (hasSetPresence){ //set up the status
				//eg. /away I am away
				//		new presence: away
				//		new status msg: I am away
//				try {
//					controller.setPresence(token);
					System.out.println("Status : " + token);
					
					if (tokenizer.countTokens() > 0){
						// will be used to set the status message
						System.out.println("status msg: " + str.substring(token.length()+1));
//						controller.setStatus(str.substring(token.length()+1), false);
					}
					
//				} catch (ClassNotFoundException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} catch (SQLException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
			} else {
				System.out.println("status msg: " + str);
//				controller.setStatus(str, false);
			}
//		}

		return true;
	}
	
	public static void main (String [] args) {
		SlashCommand cmd = new SlashCommand ();
		
		System.out.println(cmd.isSlashCommand("/online"));
		System.out.println();
		
		System.out.println(cmd.isSlashCommand("/away"));
		System.out.println();
		
		System.out.println(cmd.isSlashCommand("/brb"));
		System.out.println();
		
		System.out.println(cmd.isSlashCommand("/busy"));
		System.out.println();
		
		System.out.println(cmd.isSlashCommand("/invisible"));
		System.out.println();
		
		System.out.println(cmd.isSlashCommand("/lunch"));
		System.out.println();
		
		System.out.println(cmd.isSlashCommand("/offline"));
		System.out.println();
	
	}
}
