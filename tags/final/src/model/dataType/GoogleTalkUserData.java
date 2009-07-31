/* GoogleTalkUserData.java
 * 
 * Programmed By:
 *     Kevin Fahy
 *     William Chen
 *     
 * Change Log:
 *     2009-June-7, KF
 *         Initial write. Inherits UserData
 *     2009-June-13, WC
 *         Moved over to ParrotIM project.
 *     2009-June-24, KF
 *         Added JavaDoc Documentation
 *         
 * Known Issues:
 *     1. Currently not too useful. Should be revised after other
 *        UserData classes are created for other protocols.
 *     2. No toString() method.
 * 
 * Copyright (C) 2009  Pirate Captains
 * 
 * License: GNU General Public License version 2.
 * Full license can be found in ParrotIM/LICENSE.txt.
 */

package model.dataType;

import model.enumerations.ServerType;
import model.enumerations.UserStateType;

/**
 * Holds data about an external user on Google Talk.
 */
public class GoogleTalkUserData extends UserData implements GoogleTalkPerson {

    // Section
    // I - Data Members

    // Section
    // II - Constructors

    /**
     * Creates a new user with only a userID.
     * 
     * @param accountName
     */
    public GoogleTalkUserData(String userID) {
        super(userID);
    }

    /**
     * Creates a new user with a userID, nickname, and status.
     * 
     * @param accountName
     * @param nickname
     * @param status
     */
    public GoogleTalkUserData(String userID, String nickname, String status,
            UserStateType state, boolean blocked) {
        super(userID, nickname, status, state, blocked);
    }

    // Section
    // III - Accessors and Mutators

    public ServerType getServer() {
        return ServerType.GOOGLE_TALK;
    }
}