package API;

/**
 * Provides an array of Buddy data that allows users to set their buddies in the array. 
 */

public class BuddyList{
	
	// Section
    // I - Non-Static Data Member

    /**
     * Holds the array of buddy list.
     */
	
	private Buddy[] buddyList;
	
	// Section
    // II - Constructors

    /**
     * BuddyList(Buddy[] buddyList) allows users to create their list of buddies. 
     * 
     * @param buddyList
     */
	
	public BuddyList(Buddy[] buddyList) {
		this.buddyList = buddyList;
	}
	
	/**
     * Sets the buddy list.
     * 
     * @param buddyList
     */
	
	public void setBuddyList(Buddy[] buddyList)
	{
		this.buddyList = buddyList;
	}
	
	/**
     * Returns the buddy list.
     * 
     * @return Buddy[]
     */
	
	public Buddy[] getBuddyList()
	{
		return this.buddyList;
	}
	
	/**
     * Adds a buddy into the buddy list.
     * 
     * @param buddy
     */
	
	public void addBuddy(Buddy buddy){
		// I subtract 1
		this.buddyList[buddyList.length-1] = buddy;
	}
	
	/**
     * Deletes a buddy into the buddy list.
     * 
     * @param n
     */
	
	public void delBuddy(int n){
		this.buddyList[n] = null;
	}

}