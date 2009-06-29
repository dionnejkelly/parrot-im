/* UserStateType.java
 * 
 * Programmed By:
 *     Kevin Fahy
 *     
 * Change Log:
 *     2009-June-28, KF
 *         First write.
 *                  
 * Known Issues:
 *     none
 * 
 * Copyright (C) 2009  Pirate Captains
 * 
 * License: GNU General Public License version 2.
 * Full license can be found in ParrotIM/LICENSE.txt.
 */

package model.enumerations;

public enum UserStateType {
    ONLINE("Online"),

    AWAY("Away"),

    BUSY("Busy"),

    OFFLINE("Offline");

    /**
     * The name of the enumerated type for GUI output.
     */
    private String name;

    /**
     * Default constructor. Assigns a String name to each type for output on the
     * GUI.
     * 
     * @param name
     */
    private UserStateType(String name) {
        this.name = name;
    }

    /**
     * Converts the enumerated type to a String.
     * 
     * @return The name of the enumerated type.
     */
    @Override
    public String toString() {
        return this.name;
    }
}
