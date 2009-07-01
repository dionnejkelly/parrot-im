/* ServerType.java
 * 
 * Programmed By:
 *     Kevin Fahy
 *     William Chen
 *     
 * Change Log:
 *     2009-June-7, KF
 *         Initial write. Holds all protocol names.
 *     2009-June-13, WC
 *         Moved over to ParrotIM project.
 *     2009-June-24, KF
 *         Added JavaDoc Documentation.
 *         
 * Known Issues:
 * 
 * Copyright (C) 2009  Pirate Captains
 * 
 * License: GNU General Public License version 2.
 * Full license can be found in ParrotIM/LICENSE.txt.
 */

package model.enumerations;

import java.util.Vector;

/**
 * Holds all protocols that the chat client can connect to. Provides a
 * standardized means of referencing them in a way that's easy for programmers
 * to understand.
 */
public enum ServerType {

    // Section
    // I - Enumerated Types
     JABBER("Jabber"),GOOGLE_TALK("Google Talk"), TWITTER("Twitter"),
    ICQ("ICQ"), MSN("MSN"), AIM("AIM");

    // Section
    // II - Constants

    private static final int TOTAL_VALUES = 6;

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
    private ServerType(String name) {
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
    @Override
    public String toString() {
        return this.name;
    }

    /**
     * Gets a list of all the ServerTypes.
     * 
     * @return A list of all ServerTypes in a Vector.
     */
    public static Vector<String> getServerList() {
        Vector<String> serverList = new Vector<String>();
        for (ServerType s : ServerType.values()) {
            serverList.add(s.name);
        }
        return serverList;
    }
    
    public static ServerType serverStringToServerType(String server) {
        ServerType serverToReturn = null; // Default return value

        if (server.equals(ServerType.GOOGLE_TALK.toString())) {
            serverToReturn = ServerType.GOOGLE_TALK;
        } else if (server.equals(ServerType.JABBER.toString())) {
            serverToReturn = ServerType.JABBER;
        } else if (server.equals(ServerType.TWITTER.toString())) {
            serverToReturn = ServerType.JABBER;
        } else if (server.equals(ServerType.ICQ.toString())) {
            serverToReturn = ServerType.JABBER;
        } else if (server.equals(ServerType.AIM.toString())) {
            serverToReturn = ServerType.JABBER;
        } else if (server.equals(ServerType.MSN.toString())) {
            serverToReturn = ServerType.JABBER;
        } else { // invalid server data is stored
            serverToReturn = null;
        }

        return serverToReturn;
    }
}