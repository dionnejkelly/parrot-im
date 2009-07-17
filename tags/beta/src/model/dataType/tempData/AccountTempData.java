/* AccountTempData.java
 * 
 * Programmed By:
 *     Kevin Fahy
 *     
 * Change Log:
 *     2009-June-20, KF
 *         Initial write. Created for use of database to pass back partial
 *         account data to the controller.
 *     2009-June-23, KF
 *         Completed JavaDoc documentation.
 *         
 * Known Issues:
 *     none
 *     
 * Copyright (C) 2009  Pirate Captains
 * 
 * License: GNU General Public License version 2.
 * Full license can be found in ParrotIM/LICENSE.txt.
 */

package model.dataType.tempData;

import model.enumerations.ServerType;

/**
 * Account data fetched from the database. Used to return multiple parameters
 * from the database in a single function call.
 */
public class AccountTempData {

    /**
     * The server type, such as Google Talk or AIM. Represented as a String in
     * the database, but will be represented as a ServerType enumeration in the
     * AccountData object.
     */
    private ServerType server; 

    private String serverAddress;
    
    /**
     * The userID is the account name, with the "@server.com" affixed to it.
     */
    private String userID;

    /**
     * The password for each account. If the password is not saved, it will be
     * stored as an empty string in the database.
     */
    private String password;

    /**
     * Construct an AccountTempData with all parameters.
     * 
     * @param server
     * @param userID
     * @param password
     */
    public AccountTempData(String server, String serverAddress, String userID, String password) {
        this.setServer(server);
        this.setServerAddress(serverAddress);
        this.setUserID(userID);
        this.setPassword(password);
    }

    /**
     * Change the server type.
     * 
     * @param server Server as a ServerType enum.
     */
    public void setServer(ServerType server) {
        this.server = server;

        return;
    }
    
    /**
     * Change the server type.
     * 
     * @param server Server as a String.
     */
    public void setServer(String server) {
        this.server = ServerType.serverStringToServerType(server);

        return;
    }

    /**
     * Get the server type.
     * 
     * @return A ServerType enumerated type of the server.
     */
    public ServerType getServer() {
        return server;
    }

    public void setServerAddress(String serverAddress) {
        this.serverAddress = serverAddress;
        
        return;
    }

    public String getServerAddress() {
        return serverAddress;
    }

    /**
     * Change the userID.
     * 
     * @param userID
     */
    public void setUserID(String userID) {
        this.userID = userID;

        return;
    }

    /**
     * Gets the userID as a String.
     * 
     * @return A String of the userID.
     */
    public String getUserID() {
        return userID;
    }

    /**
     * Changes the password. Note that it is not
     * encrypted inside this object.
     * 
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;

        return;
    }

    /** 
     * Gets the password. 
     * 
     * @return The password of the user.
     */
    public String getPassword() {
        return password;
    }
    
}