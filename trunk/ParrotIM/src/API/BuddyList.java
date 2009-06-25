package API;

public class BuddyList{
	private Buddy[] buddyList;
	
	public BuddyList(Buddy[] buddyList) {
		this.buddyList = buddyList;
	}
	
	public void setBuddyList(Buddy[] bl)
	{
		this.buddyList = bl;
	}
	public Buddy[] getBuddyList()
	{
		return this.buddyList;
	}
	public void addBuddy(Buddy bl){
		// I subtract 1
		this.buddyList[buddyList.length-1] = bl;
	}
	public void delBuddy(int n){
		this.buddyList[n] = null;
	}

}