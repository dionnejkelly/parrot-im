package model.dataType;

import java.util.ArrayList;
import java.util.Observable;

import model.DatabaseFunctions;
import model.dataType.tempData.ProfileTempData;

public class ProfileCollectionData extends Observable {

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
        DatabaseFunctions db = null;

        if (this.profiles.remove(profile)) {
            // Move to the front
            this.profiles.add(0, profile);

            try {
                db = new DatabaseFunctions();
                db.setDefaultProfile(profile.getName());
            } catch (Exception e) {
                System.err.println("Database error setting default");
                e.printStackTrace();
            }
        }
        
        this.setChanged();
        this.notifyObservers();

        return;
    }

    public void addProfile(ProfileData profile) {
        DatabaseFunctions db = null;

        if (!this.duplicateName(profile.getName())) {
            this.profiles.add(profile);

            if (!profile.isGuestAccount()) {
                try {
                    db = new DatabaseFunctions();
                    db.addProfiles(profile.getName(), profile
                            .getPassword(), false, profile
                            .isChatWindowHistoryEnabled(), profile
                            .isAutoSignInEnabled(), profile.isChatLogEnabled(),
                            profile.isSoundsEnabled(), profile
                                    .isChatbotEnabled());
                } catch (Exception e) {
                    System.err.println("Database error loading profiles.");
                    e.printStackTrace();
                }
            }
        }

        this.setChanged();
        this.notifyObservers();

        return;
    }

    public boolean removeProfile(ProfileData profile) {
        boolean removed = false;
        DatabaseFunctions db = null;

        removed = this.profiles.remove(profile);

        if (removed) {
            try {
                db = new DatabaseFunctions();
                db.removeProfile(profile.getName());
            } catch (Exception e) {
                System.err.println("Database error removing profile.");
                e.printStackTrace();
            }
        }

        this.setChanged();
        this.notifyObservers();

        return this.profiles.remove(profile);
    }

    public boolean removeProfile(String name) {
        boolean removed = false;
        DatabaseFunctions db = null;
        ProfileData profileToRemove = null;

        profileToRemove = this.findProfile(name);
        if (profileToRemove != null) {
            removed = true;
            this.profiles.remove(profileToRemove);

            try {
                db = new DatabaseFunctions();
                db.removeProfile(name);
            } catch (Exception e) {
                System.err.println("Database error removing profile.");
                e.printStackTrace();
            }
        }

        this.setChanged();
        this.notifyObservers();

        return removed;
    }

    public void loadProfiles() {
        DatabaseFunctions db = null;
        ArrayList<ProfileTempData> dbProfiles = null;
        ProfileData profileToAdd;

        // Grab the profiles from the database
        try {
            db = new DatabaseFunctions();
            dbProfiles = db.getProfiles();
        } catch (Exception e) {
            System.err.println("Error loading profiles");
            e.printStackTrace();
            dbProfiles = new ArrayList<ProfileTempData>();
        }

        // Move the profiles into the collection--watch for
        // duplicate profiles!
        for (ProfileTempData p : dbProfiles) {
            if (!this.duplicateName(p.getName())) {
                profileToAdd =
                        new ProfileData(p.getName(), p.getPassword(), p
                                .isChatWindowHistory(), p.isAutoSignIn(),
                                false, p.isChatLog(), p.isSounds(), p
                                        .isChatbot());
                if (p.isDefaultProfile()) {
                    this.profiles.add(0, profileToAdd);
                } else {
                    this.profiles.add(profileToAdd);
                }
            }
        }

        return;
    }

    public boolean duplicateName(String otherName) {
        boolean duplicate = false;

        for (ProfileData p : this.profiles) {
            if (p.getName().equals(otherName)) {
                duplicate = true;
                break;
            }
        }

        return duplicate;
    }

    public ProfileData findProfile(String name) {
        ProfileData foundProfile = null;

        for (ProfileData p : this.profiles) {
            if (p.getName().equals(name)) {
                foundProfile = p;
                break;
            }
        }

        return foundProfile;
    }
}
