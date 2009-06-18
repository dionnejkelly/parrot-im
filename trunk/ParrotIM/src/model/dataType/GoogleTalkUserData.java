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
 *         
 * Known Issues:
 *     1. Currently not too useful. Should be revised after other
 *        UserData classes are created for other protocols.
 *     2. Not documented thoroughly.
 *     3. No toString() method.
 * 
 * Copyright (C) 2009  Pirate Captains
 * 
 * Full license can be found in ParrotIM/LICENSE.txt.
 */


package model.dataType;

public class GoogleTalkUserData extends UserData {

    private boolean online;
	
    public GoogleTalkUserData(String accountName, String nickname, 
                              String status, AccountData friendOf) {
       super(accountName, nickname, status, friendOf);
    }
	
    public GoogleTalkUserData(String accountName, AccountData friendOf) {
        super(accountName, friendOf);
    }
    
    public GoogleTalkUserData(String accountName) {
        super(accountName);
    }

    public void setOnline(boolean online) {
        this.online = online;
        return;
    }

    public boolean isOnline() {
        return online;
    }
    
}
