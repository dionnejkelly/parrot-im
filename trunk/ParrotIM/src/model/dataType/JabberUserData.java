/* JabberUserData.java
 * 
 * Programmed By:
 *     Kevin Fahy
 *     
 * Change Log:
 *     2009-June-19, KF
 *         Initial write. No fields yet.
 *         
 * Known Issues:
 *     none
 *     
 * Copyright (C) 2009  Pirate Captains
 * 
 * Full license can be found in ParrotIM/LICENSE.txt.
 */

package model.dataType;

/**
 * Represents a friend from the Jabber protocol.
 */
public class JabberUserData extends UserData {

    /**
     * Creates a new friend from an account name.
     * 
     * @param accountName
     *            The email address of the friend.
     */
    public JabberUserData(String accountName) {
        super(accountName);
    }

}
