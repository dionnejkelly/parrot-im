package API;

public class ProfileList{
	private Profile[] profileList;
	
	public ProfileList(Profile[] profileList) {
		this.profileList = profileList;
	}
	public void setProfileList(Profile[] pl)
	{
		this.profileList = pl;
	}
	public Profile[] getProfileList()
	{
		return this.profileList;
	}
}