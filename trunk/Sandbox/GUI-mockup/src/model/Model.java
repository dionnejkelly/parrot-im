package model;
import java.util.Vector;

public class Model {
	

	//MODEL STUB//
	protected Vector<String> accountList;
	protected Vector<String> serverList;
	protected String username;
	protected String password;
	
	public Model(){
		username = new String ("cmpt275testing@gmail.com");
		password = new String ("abcdefghi");
			
		//set server list
		serverList = new Vector<String>();
		serverList.add ("msn");
		serverList.add ("aim");
		serverList.add ("twitter");
		serverList.add ("icq");
		serverList.add ("googleTalk");

		//list of accounts
		accountList = new Vector<String>();
		accountList.add("googleTalk: " + username);
		accountList.add("connect all");
	}
	
	public Vector<String> getAccountList() {
		return accountList;
	}
	
	public Vector<String> getServerList() {
		return serverList;
	}
	
	public String getUsername() {
		return username;
	}

    public String getPassword() {
    	return password;
    }
}
