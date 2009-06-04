package chatwindow;

import java.util.ArrayList;

public class Conversation {
	ArrayList<String> names;
	
	public Conversation() {
		names = new ArrayList<String>();
	}
	
	public int getSize(){
		return names.size();
	}
	
	public String getName(int i){
		return names.get(i);
	}
	
	public void addName(String name){
		names.add(name);
	}
}
