package API;

public class BuddyList{
	private Buddy[] buddyList;

	public void setBuddyList(Buddy[] bl)
	{
		this.buddyList = bl;
	}
	public Buddy[] getBuddyList()
	{
		return this.buddyList;
	}
	public void addBuddy(Buddy bl){
		this.buddyList[buddyList.length] = bl;
	}
	public void delBuddy(int n){
		this.buddyList[n] = null;
	}
}