package API;

/**
 * Provides Buddy data type that allows users to set their buddy's name and the
 * channel.
 */

public class Buddy {

    // Section
    // I - Non-Static Data Member

    /**
     * Holds the user name.
     */

    private String username;

    /**
     * Holds the user's channel: twitter, icq, msn.
     */

    private String channel;

    // Section
    // II - Constructors

    /**
     * Buddy(String username, String channel) allows users to create their
     * buddy.
     * 
     * @param username
     * @param channel
     */

    public Buddy(String username, String channel) {
        this.username = username;
        this.channel = channel;
    }

    /**
     * Sets user's name
     * 
     * @param username
     */

    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Returns user's name
     * 
     * @return String
     */

    public String getUsername() {
        return this.username;
    }

    /**
     * Sets user's channel
     * 
     * @param channel
     */

    public void setChannel(String channel) {
        this.channel = channel;
    }

    /**
     * Returns user's channel
     * 
     * @return String
     */

    public String getChannel() {
        return this.channel;
    }

    @Override
    /*
     * * Returns user's name
     * 
     * @return String
     */
    public String toString() {
        return username;

    }

}