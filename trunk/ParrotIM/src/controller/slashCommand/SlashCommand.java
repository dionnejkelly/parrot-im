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
		StringTokenizer tokenizer = new StringTokenizer (str);
		
		if (tokenizer.countTokens()<=0) return false;
		
		String token = tokenizer.nextToken();
		boolean isCommand = false;
		if (token.charAt(0) == '/'){
			if (token.compareToIgnoreCase("/online")==0) {
				isCommand = true;
			} else if (token.compareToIgnoreCase("/away")==0) {
				isCommand = true;
			} else if (token.compareToIgnoreCase("/busy")==0) {
				isCommand = true;
			} else if (token.compareToIgnoreCase("/chatty")==0) {
				isCommand = true;
			} 
		} 
		
		if (isCommand){ //set up the status
			try {
				controller.setPresence(token.substring(1,token.length()));
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} 
		
		return isCommand;
	}

	public static void main (String[] args){
		SlashCommand cmd = new SlashCommand();
		System.out.println(cmd.isSlashCommand(""));
		System.out.println(cmd.isSlashCommand("/"));
		System.out.println(cmd.isSlashCommand("/online"));
		System.out.println(cmd.isSlashCommand("/away"));
		System.out.println(cmd.isSlashCommand("/busy"));
		System.out.println(cmd.isSlashCommand("/chatty"));
		System.out.println(cmd.isSlashCommand("/bored"));
	}
}
