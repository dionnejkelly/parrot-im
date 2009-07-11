package model.dataType;

import java.util.ArrayList;

public class ProfileCollectionData {

    private ArrayList<ProfileData> profiles;

    private ProfileData activeProfile;

    public ProfileCollectionData() {
        this.profiles = new ArrayList<ProfileData>();
        this.activeProfile = null;
    }

    public ArrayList<ProfileData> getProfiles() {
        return this.profiles;
    }

    public ProfileData getActiveProfile() {
        return this.activeProfile;
    }

    public void setActiveProfile(ProfileData profile) {
        if (profile != null && this.profiles.contains(profile)) {
            this.activeProfile = profile;
        }

        return;
    }
    
    public ProfileData getDefaultProfile() {
        return this.profiles.size() > 0 ? this.profiles.get(0) : null;
    }
    
    public void setDefaultProfile(ProfileData profile) {
        if (this.profiles.remove(profile)) {
            // Move to the front
            this.profiles.add(0, profile);
        }
        
        return;
    }
}
