/* GenericConnection.java
 * 
 * Programmed By:
 *     Kevin Fahy
 *     
 * Change Log:
 *     2009-June-27
 *         First write. Created the interface so that it could be placed in
 *         a field in Account Data. This interface would be implemented by
 *         classes as a gateway to access XMPP, Twitter, and other
 *         protocols. For example, an XMPPManager class would implement the
 *         login() method, and would hold a copy of the XMPPConnection object
 *         from the smack API. A TwitterManager class would do the same.
 *         This interface will simplify the program flow, as we can execute
 *         statements like, AccountData.disconnect() and it will work for any
 *         protocol. 
 *         
 * Known Issues:
 *     none
 *        
 * Copyright (C) 2009  Pirate Captains
 * 
 * License: GNU General Public License version 2.
 * Full license can be found in ParrotIM/LICENSE.txt.
 */

package controller.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;

import org.jivesoftware.smack.XMPPException;

import model.dataType.tempData.FriendTempData;
import model.enumerations.ServerType;
import model.enumerations.UserStateType;

/**
 * An interface the dictates the functions that connections can perform.
 * Specifically, this interface defines a set of operations on connections that
 * are common to all types of connections, be it XMPP, Twitter, ICQ, or others.
 */
public interface GenericConnection {
    
    public void login(String userID, String password, String server, int port)
            throws BadConnectionException;

    public void disconnect();

    public void addFriend(String userID) throws BadConnectionException;

    public boolean removeFriend(String userID) throws BadConnectionException;
    
    public void changeStatus(UserStateType state, String status) throws BadConnectionException;
    
    public String retrieveStatus(String userID) throws BadConnectionException;
    
    public UserStateType retrieveState(String userID)  throws BadConnectionException;
    
    public ArrayList<FriendTempData> retrieveFriendList() throws BadConnectionException;
    
    public void sendMessage(String toUserID, String message) throws BadConnectionException;
    
    /**
     * 
     * changing typing state
     * @param state represent different typing state
     * @throws BadConnectionException
     * @throws XMPPException
     */
    public void setTypingState(int state) throws BadConnectionException, XMPPException;
    
    public ImageIcon getAvatarPicture(String userID) throws XMPPException;
   
    public void setAvatarPicture(byte[] dataArray) throws XMPPException;
    public ServerType getServerType();
    
    //@Override
    public int hashCode();
}