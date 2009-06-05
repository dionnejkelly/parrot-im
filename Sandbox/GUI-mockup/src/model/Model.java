package model;
import java.util.Vector;
import java.sql.*;

public class Model {
	

	//MODEL STUB//
	protected Vector<String> accountList;
	protected Vector<String> serverList;
	protected String username;
	protected String password;
	
	public Model() throws ClassNotFoundException, SQLException{
      Class.forName("org.sqlite.JDBC");
      Connection conn = DriverManager.getConnection("jdbc:sqlite:test.db");
      Statement stat = conn.createStatement();
    


		String[] username = new String[55];
		String[] password = new String[55];
		username[0] = new String ("cmpt275testing@gmail.com");
		password[0] = new String ("abcdefghi");
		// ahmad test
		  int i = 0;
	      ResultSet rs = stat.executeQuery("select * from people;");
	      while (rs.next()) {
	    	  i++;
	    	  username[i] = rs.getString("email");
	    	  password[i] = "abcdefghi";
		}
		// ahmad test
		//set server list
		serverList = new Vector<String>();
		serverList.add ("msn");
		serverList.add ("aim");
		serverList.add ("twitter");
		serverList.add ("icq");
		serverList.add ("googleTalk");

		//list of accounts
		accountList = new Vector<String>();
		for (int k=0; k<i; k++)
		{
		accountList.add("googleTalk: " + username[k]);
		}
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
