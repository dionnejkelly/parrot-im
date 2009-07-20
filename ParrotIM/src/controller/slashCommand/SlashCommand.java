package controller.slashCommand;

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
		
		if (token.charAt(0) == '/'){
			if (token.compareToIgnoreCase("/online")==0) {
				return true;
			} else if (token.compareToIgnoreCase("/away")==0) {
				return true;
			} else if (token.compareToIgnoreCase("/busy")==0) {
				return true;
			} else if (token.compareToIgnoreCase("/chatty")==0) {
				return true;
			} 
		} 
		
		return false;
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
