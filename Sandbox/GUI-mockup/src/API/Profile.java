package API;

public class Profile{

	private BuddyList buddyList;
	private String settings;
	private String name;
	private String password;
	private Boolean chkpassword;
	
	public Profile(BuddyList buddyList,String settings, String name){
		this(buddyList,settings, name, "",false);
	}
	
	public Profile(BuddyList buddyList,String settings, String name, String password, Boolean chkpassword){
		this.buddyList = buddyList;
		this.settings = settings;
		this.name = name;
		this.password = password;
		this.chkpassword = chkpassword;
	}
	
	public void setBuddyList(BuddyList bl)
	{
		this.buddyList = bl;
	}
	public BuddyList getBuddyList()
	{
		return this.buddyList;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getChkpassword() {
		return chkpassword;
	}

	public void setChkpassword(Boolean chkpassword) {
		this.chkpassword = chkpassword;
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