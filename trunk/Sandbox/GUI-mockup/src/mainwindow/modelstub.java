package mainwindow;

import java.util.Vector;

public class modelstub {
	

	//MODEL STUB//
	protected Vector<String> account_list;
	protected Vector<String> serverList;
	protected String username;
	protected String password;
	
	protected modelstub(){
		username = new String ("shichan.karachu@gmail.com");
		password = new String ("cincoijouy!712");
			
		//set server list
		serverList = new Vector<String>();
		serverList.add ("msn");
		serverList.add ("aim");
		serverList.add ("twitter");
		serverList.add ("icq");
		serverList.add ("googleTalk");

		//list of accounts
		account_list = new Vector<String>();
		account_list.add("googleTalk: " + username);
		account_list.add("connect all");
	}
}
