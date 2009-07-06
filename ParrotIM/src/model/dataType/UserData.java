/* UserData.java
 * 
 * Programmed By:
 *     Kevin Fahy
 *     William Chen
 *     Rakan Alkheliwi
 *     
 * Change Log:
 *     2009-June-7, KF
 *         Initial write. Holds all information about a user that could
 *         appear on the friend list.
 *     2009-June-13, WC
 *         Moved over to ParrotIM project.
 *     2009-June-18, KF
 *         Added a boolean field, blocked, to show whether the friend
 *         can see your status and chat with you.
 *     2009-June-24, KF
 *         Added JavaDoc documentation.
 *     2009-June-25, KF, RA
 *         Added equals() method to resolve an issue found in 
 *         testing by Rakan.
 *     2009-June-28, KF
 *         Implemented a unique ID as a means to identify each UserData.
 *     2009-July-2, KF
 *         Removed the unique ID; the updated equals method is more effective.
 *         Moved the sort algorithms to this class--they are now static 
 *         methods.
 *         
 * Known Issues:
 *     1. The data members may not apply to every protocol.
 * 
 * Copyright (C) 2009  Pirate Captains
 * 
 * License: GNU General Public License version 2.
 * Full license can be found in ParrotIM/LICENSE.txt.
 */

package model.dataType;

import java.util.ArrayList;

import model.enumerations.UserStateType;

/**
 * Holds all data pertaining to users, including the account name, nickname, and
 * status message. This is an abstract class that must be inherited by other
 * classes that implement users for different protocols. This class should only
 * hold the common data members for all protocols. Also, this class holds static
 * methods to aid the sorting of friends.
 */
public abstract class UserData extends PersonData {

    // Section I
    // Data Members

    /**
     * The blocked status of the user.
     */
    protected boolean blocked;

    // Section II
    // Constructors

    /**
     * Creates a new user with only a userID defined.
     * 
     * @param userID
     *            A string representing the username. This is the name used to
     *            communicate to the server.
     */
    public UserData(String userID) {
        super(userID);
        this.blocked = false;
    }

    public UserData(String userID, String nickname, String status,
            UserStateType state, boolean blocked) {
        super(userID, nickname, status, state);
        this.blocked = blocked;
    }

    // Section III
    // Accessors and Mutators

    /**
     * Changes the blocked property of the user.
     * 
     * @param blocked
     *            If true, this external user is unaware of the local account's
     *            online status or messages
     */
    public void setBlocked(boolean blocked) {
        this.blocked = blocked;

        return;
    }

    /**
     * Gets the blocked status.
     * 
     * @return True if blocked, false otherwise.
     */
    public boolean isBlocked() {
        return this.blocked;
    }

    // Section IV
    // Utility Methods

    // Section V
    // Sort Methods

    /**
     * Determines a hierarchy of states, that is, returns true if this user has
     * a more online state than the passed-in user. The hierarchy is: online -->
     * away/busy --> offline --> blocked. This method is useful in ordering the
     * buddy list to show the more online people at the top.
     * 
     * @param user
     *            The other user we are comparing.
     * @return true if this user is more online than the passed-in user
     */
    public boolean isMoreOnline(UserData user) {
        int ownPriority = 0;
        int userPriority = 0;

        if (this.blocked) {
            ownPriority = 1;
        } else if (this.getState() == UserStateType.ONLINE) {
            ownPriority = 4;
        } else if (this.getState() == UserStateType.OFFLINE) {
            ownPriority = 2;
        } else {
            ownPriority = 3;
        }

        if (user.isBlocked()) {
            userPriority = 1;
        } else if (user.getState() == UserStateType.ONLINE) {
            userPriority = 4;
        } else if (user.getState() == UserStateType.OFFLINE) {
            userPriority = 2;
        } else {
            userPriority = 3;
        }

        return (ownPriority > userPriority);
    }

    /**
     * Takes in a friend list and returns back only the friends that are
     * unblocked.
     * 
     * @param unsorted
     *            The friend list to pass in.
     * @return A friend list without any blocked users.
     */
    public static ArrayList<UserData> cullBlocked(ArrayList<UserData> unsorted) {
        ArrayList<UserData> friends = new ArrayList<UserData>();

        try {
            for (UserData user : unsorted) {
                if (!user.isBlocked()) {
                    friends.add(user);
                }
            }
        } catch (NullPointerException e) {
            System.err.println("Null unsorted getting passed in.");
            e.printStackTrace();
            friends.clear();
        }

        return friends;
    }

    /**
     * Takes in a friend list and returns back a friend list sorted
     * alphabetically by nickname.
     * 
     * @param unsorted
     *            The friend list to pass in.
     * @return A friend list sorted alphabetically by nickname.
     */
    public static ArrayList<UserData> sortAlphabetical(
            ArrayList<UserData> unsorted) {
        UserData candidate = null;
        ArrayList<UserData> friends = new ArrayList<UserData>();

        try {
            while (!unsorted.isEmpty()) {
                for (UserData user : unsorted) {
                    if (candidate == null) {
                        candidate = user;
                    } else if (user.getNickname().compareToIgnoreCase(
                            candidate.getNickname()) < 0) {
                        candidate = user;
                    } else {
                        // Do nothing, look at next user.
                    }
                }
                unsorted.remove(candidate);
                friends.add(candidate);
                candidate = null;
            }
        } catch (NullPointerException e) {
            System.err.println("Null unsorted getting passed in.");
            e.printStackTrace();
            friends.clear();
        }

        return friends;
    }

    /**
     * Takes in an unsorted list of friends and returns a list with more
     * "online" friends at the top. This means that online friends will be
     * placed first, followed by busy or away friends, followed by offline
     * friends, etc.
     * 
     * @param unsorted
     *            Any list of friends in an ArrayList of UserData.
     * @return A list of friends sorted by "more online" in ArrayList of
     *         UserData format.
     */
    public static ArrayList<UserData> sortMostOnline(
            ArrayList<UserData> unsorted) {
        ArrayList<UserData> friends = new ArrayList<UserData>();
        UserData candidate = null;

        // Sort with regard to online/busy/offline/blocked
        try {
            while (!unsorted.isEmpty()) {
                for (UserData user : unsorted) {
                    if (candidate == null) {
                        candidate = user;
                    } else if (user.isMoreOnline(candidate)) {
                        candidate = user;
                    } else {
                        // do nothing, next iteration
                    }
                }
                unsorted.remove(candidate);
                friends.add(candidate);
                candidate = null;
            }
        } catch (NullPointerException e) {
            System.err.println("Null unsorted getting passed in.");
            e.printStackTrace();
            friends.clear();
        }

        return friends;
    }

    public static ArrayList<UserData> sortByStringMatch(
            ArrayList<UserData> unsorted, String match) {

        ArrayList<UserData> friends = new ArrayList<UserData>();
        UserData candidate = null;

        try {
            while (!unsorted.isEmpty()) {
                for (UserData user : unsorted) {
                    if (candidate == null) {
                        candidate = user;
                    } else if (user.getNickname().matches(".*" + match + ".*")) {
                        candidate = user;
                    } else if (user.getUserID().matches(".*" + match + ".*")) {
                        candidate = user;
                    } else if (user.getStatus().matches(".*" + match + ".*")) {
                        candidate = user;
                    } else {
                        // do nothing, next iteration please
                    }
                }
                unsorted.remove(candidate);
                friends.add(candidate);
                candidate = null;
            }
        } catch (NullPointerException e) {
            System.err.println("Null unsorted getting passed in.");
            e.printStackTrace();
            friends.clear();
        }

        return friends;
    }

}