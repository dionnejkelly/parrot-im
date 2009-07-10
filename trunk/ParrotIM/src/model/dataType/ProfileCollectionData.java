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
}
