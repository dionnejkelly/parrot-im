package API;

public class Profile{

	private BuddyList buddyList;
	private String accountData;
	private String settings;

	public void setBuddyList(BuddyList bl)
	{
		this.buddyList = bl;
	}
	public BuddyList getBuddyList()
	{
		return this.buddyList;
	}
	public void setAccountData(String a)
	{
		this.accountData = a;
	}
	public String getAccountData()
	{
		return this.accountData;
	}
	public void setSettings(String s)
	{
		this.settings = s;
	}
	public String getSettings()
	{
		return this.settings;
	}
}