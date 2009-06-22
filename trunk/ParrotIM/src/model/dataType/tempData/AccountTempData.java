/* AccountTempData.java
 * 
 * Programmed By:
 *     Kevin Fahy
 *     
 * Change Log:
 *     2009-June-20, KF
 *         Initial write. Created for use of database to pass back partial
 *         account data to the controller.
 *         
 * Known Issues:
 *     none
 *     
 * Copyright (C) 2009  Pirate Captains
 * 
 * Full license can be found in ParrotIM/LICENSE.txt.
 */


package model.dataType.tempData;

import model.dataType.ServerType;

public class AccountTempData {
    private ServerType server;
    private String userID;
    private String password;
    
    public AccountTempData(ServerType server, String userID, String password) {
        this.setServer(server);
        this.setUserID(userID);
        this.setPassword(password);
    }

    public void setServer(ServerType server) {
        this.server = server;
        
        return;
    }

    public ServerType getServer() {
        return server;
    }

    public void setUserID(String userID) {
        this.userID = userID;

        return;
    }

    public String getUserID() {
        return userID;
    }

    public void setPassword(String password) {
        this.password = password;

        return;
    }

    public String getPassword() {
        return password;
    }
}
