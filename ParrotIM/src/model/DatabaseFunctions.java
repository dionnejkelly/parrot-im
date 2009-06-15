package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Vector;

public class DatabaseFunctions {
	private Vector<String> accountList;
	public Connection conn;
	public Statement stat;
	public PreparedStatement prep;
	public ResultSet rs;
	public DatabaseFunctions() throws ClassNotFoundException, SQLException
	{
		accountList = new Vector<String>();
        Class.forName("org.sqlite.JDBC");
        conn = DriverManager.getConnection("jdbc:sqlite:test.db");
        stat = conn.createStatement();
		
	}
	public void addUsers(String profile, String service, String email, String password, String rememberPassword) throws SQLException
	{
	      stat.executeUpdate("drop table if exists people;");
	      stat.executeUpdate("create table people (profile, service, email, password, rememberPassword);");
	      prep = conn.prepareStatement(
		          "insert into people values (?, ?, ?, ?, ?);");

	      	  prep.setString(1, profile);
		      prep.setString(2, service);
		      prep.setString(3, email);
		      prep.setString(4, password);
		      prep.setString(5, rememberPassword);
		      prep.addBatch();

		      conn.setAutoCommit(false);
		      prep.executeBatch();
		      conn.setAutoCommit(true);
		      conn.close();
	}
	public Vector<String> getUserList() throws SQLException
	{
		ResultSet rs = stat.executeQuery("select * from people;");
	    while (rs.next()) {
	        accountList.add(rs.getString("email"));
	    }
	    return accountList;
	}
    public void addChat(String fromUser, String toUser, String message) throws SQLException
    {

    prep = conn.prepareStatement(
              "insert into chatLog values (?, ?, ?, ?);");

          prep.setString(1, fromUser);
          prep.setString(2, toUser);
          prep.setString(3, message);
          /// remember to make date automatic!!!
          prep.setString(4, new Date().toString());
          prep.addBatch();
         
          conn.setAutoCommit(false);
          prep.executeBatch();
          conn.setAutoCommit(true);
          conn.close();
    }
    public void printChats() throws SQLException, ClassNotFoundException
    {
		accountList = new Vector<String>();
        conn = DriverManager.getConnection("jdbc:sqlite:test.db");
        stat = conn.createStatement();
        ResultSet rs = stat.executeQuery("select * from chatLog;");
          while (rs.next()) {
              System.out.println("From = " + rs.getString("fromUser") + ", To = " + rs.getString("toUser") + ", Message = " + rs.getString("message") + ", Date = " + rs.getString("date"));
          }

    }
	public void addProfiles(String name, String password, String rememberPassword) throws SQLException
	{
	      Connection conn = DriverManager.getConnection("jdbc:sqlite:test.db");
	      Statement stat = conn.createStatement();
	      prep = conn.prepareStatement(
		          "insert into profiles values (?, ?, ?);");

		      prep.setString(1, name);
		      prep.setString(2, password);
		      prep.setString(3, rememberPassword); // either Y or N
		      prep.addBatch();

		      conn.setAutoCommit(false);
		      prep.executeBatch();
		      conn.setAutoCommit(true);
		      conn.close();
	}
	public Vector<String> getProfileList() throws SQLException
	{
		ResultSet rs = stat.executeQuery("select * from profiles;");
		ResultSet rs2;
	    while (rs.next()) {
	        System.out.println("Name: " + rs.getString("name") + ", Password = " + rs.getString("password") + "Chose to remember password = " + rs.getString("rememberPassword"));
	        System.out.println("List of users under this profile: ");
			rs2 = stat.executeQuery("select * from people where profile='" + rs.getString("name") + "';");
		    while (rs2.next()) {
		    	{
		    		System.out.print(rs2.getString("name") + ", ");
		    	}
		    }
	    }
	    return accountList;
	}
}
