package chatLogModel;

import java.util.Random;

public class buddyChatLog {
	private String name;
	private chatLog[] history;
	
	
	public buddyChatLog(String name){
		this.name = name;
		history = new chatLog[5];
		
		//I generate this randomly
		//should get this from database! (get them HERE for faster access)
		Random rand = new Random ();
		for (int i=0; i<5; i++){
			String date = (rand.nextInt(30)+1) + "/" + (rand.nextInt(12)+1) + "/2009"; //format : DD/MM/YY
			history[i] = new chatLog(name, date);
		}
	}
	
	public chatLog[] getHistory(){
		//gets the list of past chatlogs (sorted by date)
		return history;
	}

	public String toString(){
		return name;
	}
}
