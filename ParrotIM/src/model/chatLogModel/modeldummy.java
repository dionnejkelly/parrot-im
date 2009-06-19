package model.chatLogModel;

public class modeldummy {
	private buddyChatLog [] buddies;
	
	public modeldummy(){
		//dummy model
		//buddies who has logged chat
		buddyChatLog[] buddies = {new buddyChatLog("Lala"), new buddyChatLog("Lili"), new buddyChatLog("Lulu"),
				new buddyChatLog("Lele"), new buddyChatLog("Lolo")}; 
		
		//setting
		this.buddies = buddies;
	}

	public buddyChatLog[] getBuddyList(){
		//returns buddy (that have chat log)
		return buddies;
	}
	
	public chatLog[] getHistoryList(int index){
		//returns history date list 
		return buddies[index].getHistory();
	}
}
