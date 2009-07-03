/* JabberUserData.java
 * 
 * Programmed By:
 *     Kevin Fahy
 *     
 * Change Log:
 *     2009-June-19, KF
 *         Initial write. No fields yet.
 *     2009-June-24, KF
 *         Added JavaDoc documentation.
 *         
 * Known Issues:
 *     1. Currently only holds the data that's in UserData. Not too
 *        useful.
 *     
 * Copyright (C) 2009  Pirate Captains
 *
 * License: GNU General Public License version 2.
 * Full license can be found in ParrotIM/LICENSE.txt.
 */

package model.dataType;

import model.enumerations.ServerType;

/**
 * Represents a friend from the Jabber protocol.
 */
public class JabberUserData extends UserData {

    // Section
    // I - Data Members

    
    // Section
    // II - Constructors
    
    /**
     * Creates a new friend from an account name.
     * 
     * @param accountName
     *            The email address of the friend.
     */
    public JabberUserData(String accountName) {
        super(accountName);
    }

    /**
     * Creates a new Jabber user with a userID, nickname, and
     * status.
     * 
     * @param accountName
     * @param nickname
     * @param status
     */
    public JabberUserData(String accountName, String nickname, String status) {
        super(accountName, nickname, status);
    }
    
    public String serverTypeToString() {
        return ServerType.JABBER.toString();
    }
    
}