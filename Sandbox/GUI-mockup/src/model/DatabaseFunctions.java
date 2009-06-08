package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
	
}
