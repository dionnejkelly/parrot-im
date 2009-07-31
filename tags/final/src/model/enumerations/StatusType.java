/* PopupEnableWindowType.java
 * 
 * Programmed By:
 * 	   Vera Lukman
 *     
 * Change Log:
 *     2009-June-29, VL
 *         Initial write to clean up redundant code.
 *         
 * Known Issues:
 *     None
 * 
 * Copyright (C) 2009  Pirate Captains
 * 
 * Full license can be found in ParrotIM/LICENSE.txt.
 */

package model.enumerations;

import java.util.Vector;

/**
 * Holds all popup windows that still enable the calling frame but do not allow
 * the user to call multiple windows of each of the popup window type. Provides
 * a standardized means of referencing them in a way that's easy for programmers
 * to understand.
 */
public enum StatusType {

    // Section
    // I - Enumerated Types
    AVAILABLE("Available"), AWAY("Away"), BUSY("Busy"), PHONE("On the phone"),
    LUNCH("Lunch"), BRB("Be right back"), INVISIBLE("Invisible");

    // Section
    // II - Constants

    private static final int TOTAL_VALUES = 7;

    // Section
    // III - Data Members

    /**
     * The name of the enumerated type for GUI output.
     */
    private String name;

    // Section
    // IV - Constructors

    /**
     * Default constructor. Assigns a String name to each type for output on the
     * GUI.
     * 
     * @param name
     */
    private StatusType(String name) {
        this.name = name;
    }

    // Section
    // V - Accessors and Mutators

    /**
     * Returns how many enumerated types there are. This is currently set as a
     * constant that needs to be updated every type a type is added.
     * 
     * @return The number of different protocols.
     */
    public static int numberOfValues() {
        return TOTAL_VALUES;
    }

    // Section
    // VI - Information Request Methods

    /**
     * Converts the enumerated type to a String.
     * 
     * @return The name of the enumerated type.
     */
    public String toString() {
        return this.name;
    }

    /**
     * Gets a list of all the ServerTypes.
     * 
     * @return A list of all ServerTypes in a Vector.
     */
    public static Vector<String> getStatusList() {
        Vector<String> statusList = new Vector<String>();
        for (StatusType s : StatusType.values()) {
            statusList.add(s.name);
        }
        return statusList;
    }

    public static String intToStatusType(int ordinal) {
        return getStatusList().get(ordinal);

    }
}
