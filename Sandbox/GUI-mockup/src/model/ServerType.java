package model;

import java.util.Vector;

public enum ServerType {
    GOOGLE_TALK ("Google Talk"),
    JABBER      ("Jabber"),
    TWITTER     ("Twitter"),
    ICQ         ("ICQ"),
    MSN         ("MSN"),
    AIM         ("AIM");

    private String name;
    
    private ServerType(String name) {
       this.name = name;
    }
    
    public static Vector<String> getServerList() {
    	Vector<String> serverList = new Vector<String>();
    	for (ServerType s : ServerType.values()) {
    		serverList.add(s.name);
    	}
    	return serverList;
    }
}
