package API;

/**
 * Provides an array of Profile list that manages user's profile in an array.
 */

public class ProfileList {

    // Section
    // I - Non-Static Data Member

    /**
     * Holds the array of profile list.
     */

    private Profile[] profileList;

    // Section
    // II - Constructors

    /**
     * ProfileList(Profile[] profileList) manages users' profile.
     * 
     * @param profileList
     */

    public ProfileList(Profile[] profileList) {
        this.profileList = profileList;
    }

    /**
     * Sets the profile list.
     * 
     * @param pl
     */

    public void setProfileList(Profile[] pl) {
        this.profileList = pl;
    }

    /**
     * Returns the profile list.
     * 
     * @return Profile[]
     */

    public Profile[] getProfileList() {
        return this.profileList;
    }
}