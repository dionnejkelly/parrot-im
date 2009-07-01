package API;

/**
 * Provides a Profile setting that allows users to set their buddies, settings, name
 * and password 
 */

public class Profile{

	// Section
    // I - Non-Static Data Member

    /**
     * Holds the array of buddy list.
     */
	
	private BuddyList buddyList;
	
	/**
     * Holds the settings.
     */
	
	private String settings;
	
	/**
     * Holds the buddy's name.
     */
	
	private String name;
	
	/**
     * Holds the buddy's password.
     */
	
	private String password;
	
	/**
     * Holds the buddy's check password flag.
     */
	
	private Boolean chkpassword;
	
	
	// Section
    // II - Constructors

    /**
     * Profile(BuddyList buddyList,String settings, String name) allows users to create their profile. 
     * 
     * @param buddyList
     * @param settings
     * @param name
     */
	
	
	public Profile(BuddyList buddyList,String settings, String name){
		this(buddyList,settings, name, "",false);
	}
	
	/**
     * Profile(BuddyList buddyList,String settings, String name, String password, Boolean chkpassword) allows users to create their profile. 
     * 
     * @param buddyList
     * @param settings
     * @param name
     * @param password
     * @param chkpassword
     */
	
	
	public Profile(BuddyList buddyList,String settings, String name, String password, Boolean chkpassword){
		this.buddyList = buddyList;
		this.settings = settings;
		this.name = name;
		this.password = password;
		this.chkpassword = chkpassword;
	}
	
	/**
     * Sets the buddy list.
     * 
     * @param bl
     */
	
	public void setBuddyList(BuddyList bl)
	{
		this.buddyList = bl;
	}
	
	/**
     * Returns the buddy list.
     * 
     * @return BuddyList
     */
	
	public BuddyList getBuddyList()
	{
		return this.buddyList;
	}
	
	/**
     * Returns the user's name.
     * 
     * @return String
     */
	
	public String getName() {
		return name;
	}
	
	/**
     * Set's the user's name.
     * 
     * @param name
     */
	

	public void setName(String name) {
		this.name = name;
	}
	
	/**
     * Returns the user's password.
     * 
     * @return String
     */
	
	public String getPassword() {
		return password;
	}
	
	/**
     * Set's the user's password.
     * 
     * @param password
     */
	

	public void setPassword(String password) {
		this.password = password;
	}

	
	/**
     * Returns the user's check password flag.
     * 
     * @return Boolean
     */
	
	public Boolean getChkpassword() {
		return chkpassword;
	}
	
	/**
     * Set's the user's check password flag.
     * 
     * @param chkpassword
     */

	public void setChkpassword(Boolean chkpassword) {
		this.chkpassword = chkpassword;
	}

	
	/**
     * Set's the user's setting.
     * 
     * @param s
     */
	
	public void setSettings(String s)
	{
		this.settings = s;
	}
	
	/**
     * Returns the user's setting.
     * 
     * @return String
     */
	
	
	public String getSettings()
	{
		return this.settings;
	}
}