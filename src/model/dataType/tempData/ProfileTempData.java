/* ProfileTempData.java
 * 
 * Programmed By:
 *     Kevin Fahy
 *     
 * Change Log:
 *     2009-June-20, KF
 *         Initial write. Created for use of database to pass back partial
 *         profile data to the controller.
 *     2009-June-24, KF
 *         Completed JavaDoc Documentation.
 *         
 * Known Issues:
 *     none
 *     
 * Copyright (C) 2009  Pirate Captains
 * 
 * License: GNU General Public License version 2.
 * Full license can be found in ParrotIM/LICENSE.txt.
 */

package model.dataType.tempData;

/**
 * Ferries profile information from the database to other parts of the program.
 * 
 */
public class ProfileTempData {

    /**
     * The name of the profile. Not necessarily a userID of an account.
     */
    private String name;

    /**
     * An optional password to lock the profile on sign-in. If off, the password
     * is set to the empty string.
     */
    private String password;

    /**
     * A boolean to represent whether the profile is default or not. If default,
     * it should appear as the first profile in the list upon sign-in.
     */
    private boolean defaultProfile;

    private boolean chatWindowHistory;

    private boolean autoSignIn;

    private boolean chatLog;

    private boolean sounds;

    private boolean chatbot;

    /**
     * Builds a ProfileTempData object with all fields.
     * 
     * @param name
     * @param password
     * @param defaultProfile
     */
    public ProfileTempData(String name, String password,
            boolean defaultProfile, boolean chatWindowHistory,
            boolean autoSignIn, boolean chatLog, boolean sounds, boolean chatbot) {
        this.setName(name);
        this.setPassword(password);
        this.setDefaultProfile(defaultProfile);
        this.chatWindowHistory = chatWindowHistory;
        this.autoSignIn = autoSignIn;
        this.chatLog = chatLog;
        this.sounds = sounds;
        this.chatbot = chatbot;
    }

    /**
     * Changes the profile name.
     * 
     * @param name
     */
    public void setName(String name) {
        this.name = name;
        return;
    }

    /**
     * Gets the profile name.
     * 
     * @return A string representing the profile name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the password. Is not encrypted in the object.
     * 
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
        return;
    }

    /**
     * Gets the password.
     * 
     * @return The password in string format.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets whether the profile is default or not.
     * 
     * @param defaultProfile
     */
    public void setDefaultProfile(boolean defaultProfile) {
        this.defaultProfile = defaultProfile;
        return;
    }

    /**
     * Returns the default profile value.
     * 
     * @return true if default; false otherwise.
     */
    public boolean isDefaultProfile() {
        return defaultProfile;
    }

    public boolean isChatWindowHistory() {
        return chatWindowHistory;
    }

    public void setChatWindowHistory(boolean chatWindowHistory) {
        this.chatWindowHistory = chatWindowHistory;
    }

    public boolean isAutoSignIn() {
        return autoSignIn;
    }

    public void setAutoSignIn(boolean autoSignIn) {
        this.autoSignIn = autoSignIn;
    }

    public boolean isChatLog() {
        return chatLog;
    }

    public void setChatLog(boolean chatLog) {
        this.chatLog = chatLog;
    }

    public boolean isSounds() {
        return sounds;
    }

    public void setSounds(boolean sounds) {
        this.sounds = sounds;
    }

    public boolean isChatbot() {
        return chatbot;
    }

    public void setChatbot(boolean chatbot) {
        this.chatbot = chatbot;
    }
}