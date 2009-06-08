package API;

public class ProtocolSettings{

	private String protocol;
	private String username;
	private String password;

	public void setProtocol(String p)
	{
		this.protocol = p;
	}
	public String getProtocol()
	{
		return this.protocol;
	}
	public void setUsername(String u)
	{
		this.username = u;
	}
	public String getUsername()
	{
		return this.username;
	}
	public void setPassword(String p)
	{
		this.password = p;
	}
	public String getPassword()
	{
		return this.password;
	}
}