/* ProfileTempData.java
 * 
 * Programmed By:
 *     Kevin Fahy
 *     
 * Change Log:
 *     2009-June-20, KF
 *         Initial write. Created for use of database to pass back partial
 *         profile data to the controller.
 *         
 * Known Issues:
 *     none
 *     
 * Copyright (C) 2009  Pirate Captains
 * 
 * Full license can be found in ParrotIM/LICENSE.txt.
 */

package model.dataType.tempData;

public class ProfileTempData {
    private String name;
    private String password;
    private boolean defaultProfile;
    
    public ProfileTempData(String name, String password, 
            boolean defaultProfile) {
        this.setName(name);
        this.setPassword(password);
        this.setDefaultProfile(defaultProfile);
    }

    public void setName(String name) {
        this.name = name;
        return;
    }

    public String getName() {
        return name;
    }

    public void setPassword(String password) {
        this.password = password;
        return;
    }

    public String getPassword() {
        return password;
    }

    public void setDefaultProfile(boolean defaultProfile) {
        this.defaultProfile = defaultProfile;
        return;
    }

    public boolean isDefaultProfile() {
        return defaultProfile;
    }
}
