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
 *         
 * Known Issues:
 *     1. Not documented thoroughly.
 * 
 * Copyright (C) 2009  Pirate Captains
 * 
 * Full license can be found in ParrotIM/LICENSE.txt.
 */


package model.enumerations;

import java.util.Vector;

public enum ServerType {
    GOOGLE_TALK ("Google Talk"),
    JABBER      ("Jabber"),
    TWITTER     ("Twitter"),
    ICQ         ("ICQ"),
    MSN         ("MSN"),
    AIM         ("AIM");

    private static final int TOTAL_VALUES = 6;
    
    private String name;
    
    private ServerType(String name) {
       this.name = name;
    }
   
    @Override
    public String toString() {
    	return this.name;
    }
    
    public static int numberOfValues() {
    	return TOTAL_VALUES;
    }
    
    public static Vector<String> getServerList() {
    	Vector<String> serverList = new Vector<String>();
    	for (ServerType s : ServerType.values()) {
    		serverList.add(s.name);
    	}
    	return serverList;
    }
    
}