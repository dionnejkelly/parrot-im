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

import org.jivesoftware.smack.PacketCollector;
import org.jivesoftware.smack.SmackConfiguration;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.PacketIDFilter;
import org.jivesoftware.smack.packet.IQ;

import model.dataType.tempData.FriendTempData;
import model.enumerations.ServerType;
import model.enumerations.UserStateType;

/**
 * An interface the dictates the functions that connections can perform.
 * Specifically, this interface defines a set of operations on connections that
 * are common to all types of connections, be it XMPP, Twitter, ICQ, or others.
 */
public interface GenericConnection {
    
    public void login(String userID, String password)
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
    public void setTypingState(int state, String userID) throws BadConnectionException, XMPPException;
    
    public ImageIcon getAvatarPicture(String userID) throws XMPPException;
   
    public void setAvatarPicture(byte[] bytes) throws XMPPException;
    
    public void setAvatarPicture(File file) throws XMPPException;
	
	public void setAvatarPicture(URL url) throws XMPPException;
    public ServerType getServerType();
    
    //@Override
    public int hashCode();
    public void setUserNickName(String name) throws XMPPException;


    public void setUserEmailHome(String name) throws XMPPException;


    public void setUserEmailWork(String name) throws XMPPException;


    public void setUserFirstName(String name) throws XMPPException;

    public void setUserLastName(String name) throws XMPPException;

    public void setUserMiddleName(String name) throws XMPPException;

    public void setUserOrganization(String name) throws XMPPException;

    public void setUserOrganizationUnit(String name) throws XMPPException;
    
    public void setUserPhoneHome(String name) throws XMPPException;

    public void setUserPhoneWork(String name) throws XMPPException;

    public String getUserNickName() throws XMPPException;

    public String getUserEmailHome() throws XMPPException;


    public String getUserEmailWork() throws XMPPException;

    public String getUserFirstName() throws XMPPException;

    public String getUserLastName() throws XMPPException;

    public String getUserMiddleName() throws XMPPException;

    public String getUserOrganization() throws XMPPException;

    public String getUserOrganizationUnit() throws XMPPException;

    public String getUserPhoneHome() throws XMPPException;
   
    

    public String getUserPhoneWork() throws XMPPException;
    
    public void load(String userID) throws XMPPException;
    
    
    public void load() throws XMPPException;
    
   
}