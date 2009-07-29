package API;

/**
 * Provides Protocol settings that allows users to set their protocols..
 */

public class ProtocolSettings {

    // Section
    // I - Non-Static Data Member

    /**
     * Holds the user's protocol.
     */

    private String protocol;

    /**
     * Holds the user's protocol name.
     */

    private String username;

    /**
     * Holds the user's protocol password.
     */

    private String password;

    // Section
    // II - Constructors

    /**
     * ProtocolSettings(String protocol, String username, String password)
     * manages user's protocol settings.
     * 
     * @param protocol
     * @param username
     * @param password
     */

    public ProtocolSettings(String protocol, String username, String password) {
        this.protocol = protocol;
        this.username = username;
        this.password = password;
    }

    /**
     * Sets the user's profile.
     * 
     * @param p
     */

    public void setProtocol(String p) {
        this.protocol = p;
    }

    /**
     * Returns the user's profile.
     * 
     * @return String
     */

    public String getProtocol() {
        return this.protocol;
    }

    /**
     * Sets the user's protocol name.
     * 
     * @param u
     */

    public void setUsername(String u) {
        this.username = u;
    }

    /**
     * Returns the user's protocol name.
     * 
     * @return String
     */

    public String getUsername() {
        return this.username;
    }

    /**
     * Sets the user's protocol password.
     * 
     * @param p
     */

    public void setPassword(String p) {
        this.password = p;
    }

    /**
     * Returns the user's protocol password.
     * 
     * @return String
     */

    public String getPassword() {
        return this.password;
    }
}