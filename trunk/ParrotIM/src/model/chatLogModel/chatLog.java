package model.chatLogModel;

public class chatLog {
	private String buddy;
	private String date;
	
	private String log;
	
	public chatLog(String buddy, String date){
		//buddy = name of buddy
		//date = time of chat (may include time start and time end)
		//date format: DD/MM/YY
		this.buddy = buddy;
		this.date = date;
		
		log = this.toString() + ": blah blah blah"; //from database
	}
	
	public String toString(){
		return buddy+" "+date;
	}
	
	public String getLog(){
		//returns the chatlog of a certain date
		return log;
	}
}
