package API;

public class Buddy{
	private String username;
	private String channel;
	//channel : twitter, icq, msn
	public Buddy(String username, String channel) {
		this.username = username;
		this.channel = channel;
	}
	
	public void setUsername(String u)
	{
		this.username = u;
	}
	public String getUsername()
	{
		return this.username;
	}
	public void setChannel(String c)
	{
		this.channel = c;
	}
	public String getChannel()
	{
		return this.channel;
	}
	public String toString(){
		return username;
		
	}

}