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
	public void addUsers(String service, String email, String password) throws SQLException
	{
	      prep = conn.prepareStatement(
		          "insert into people values (?, ?, ?);");

		      prep.setString(1, service);
		      prep.setString(2, email);
		      prep.setString(3, password);
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
}
