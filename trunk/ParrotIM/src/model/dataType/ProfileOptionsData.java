/* ProfileOptionsData.java
 * 
 * Programmed By:
 *     Kevin Fahy
 *     
 * Change Log:
 *     2009-July-1, KF
 *         First write. Holds all profile options, such as chatbot
 *         enabler and chat history enabler.
 *         
 * Known Issues:
 *     none
 *     
 * Copyright (C) 2009  Pirate Captains
 * 
 * License: GNU General Public License version 2.
 * Full license can be found in ParrotIM/LICENSE.txt.
 */

package model.dataType;

public class ProfileOptionsData {

    private boolean chatbotEnabled;

    private boolean chatWindowHistoryEnabled;

    private boolean profilePasswordEnabled;

    private String profilePassword;

    private boolean autoSignInEnabled;

    public ProfileOptionsData() {
        this.chatbotEnabled = false;
        this.chatWindowHistoryEnabled = false;
        this.profilePasswordEnabled = false;
        this.profilePassword = null;
        this.autoSignInEnabled = false;
    }

    public boolean isChatbotEnabled() {
        return chatbotEnabled;
    }

    public void setChatbotEnabled(boolean chatbotEnabled) {
        this.chatbotEnabled = chatbotEnabled;

        return;
    }

    public boolean isChatWindowHistoryEnabled() {
        return chatWindowHistoryEnabled;
    }

    public void setChatWindowHistoryEnabled(boolean chatWindowHistoryEnabled) {
        this.chatWindowHistoryEnabled = chatWindowHistoryEnabled;

        return;
    }

    public boolean isProfilePasswordEnabled() {
        return profilePasswordEnabled;
    }

    public void setProfilePasswordEnabled(boolean profilePasswordEnabled) {
        this.profilePasswordEnabled = profilePasswordEnabled;
        this.profilePassword =
                profilePasswordEnabled ? this.profilePassword : null;

        return;
    }

    public String getProfilePassword() {
        return profilePassword;
    }

    public void setProfilePassword(String profilePassword) {
        this.profilePassword = profilePassword;
        
        return;
    }

    public boolean isAutoSignInEnabled() {
        return autoSignInEnabled;
    }

    public void setAutoSignInEnabled(boolean autoSignInEnabled) {
        this.autoSignInEnabled = autoSignInEnabled;
        
        return;
    }

}
