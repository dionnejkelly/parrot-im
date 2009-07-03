/* XMPPTest.java
 * 
 * Programmed By:
 *     Rakan Alkheliwi
 *     
 * Change Log:
 *         
 * Known Issues:
 *     none
 *     
 * Copyright (C) 2009  Pirate Captains
 * 
 * License: GNU General Public License version 2.
 * Full license can be found in ParrotIM/LICENSE.txt.
 */

package test.integration;

import static org.junit.Assert.*;

import model.DatabaseFunctions;
import model.Model;
import model.dataType.tempData.FriendTempData;
import model.enumerations.ServerType;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import controller.MainController;

public class XMPPTest {

    private Model model;
    private MainController controller;
    private DatabaseFunctions db;
    private String profileName;
    private String userID;
    private String password;
    private String friendUserID;

    @Before
    public void setUp() throws Exception {
        DatabaseFunctions.setDatabaseName("test.db");
        db = new DatabaseFunctions();
        db.dropTables();
        this.model = new Model();
        this.controller = new MainController(model);

        profileName = "Phyllis";
        userID = "parrotim.test@gmail.com";
        password = "abcdefghi";
        friendUserID = "cmpt275testing@gmail.com";

        model.addProfile(profileName, "", true);
        model.addAccount(profileName, ServerType.GOOGLE_TALK.toString(), "",
                userID, password);

        controller.loginProfile(profileName);
    }

    @After
    public void tearDown() throws Exception {
        controller.disconnect();

        this.model = null;
        this.controller = null;
    }

    @Test
    public void checkModelConsistency() throws Exception {
        assertTrue(model.getCurrentProfile().getProfileName().equals(
                profileName));
        assertTrue(model.getCurrentProfile().getAccountData().get(0)
                .getAccountName().equals(userID));

        return;
    }

    @Test
    public void testDisconnect() throws Exception {
        assertTrue(model.currentProfileExists());
        controller.disconnect();
        assertTrue(!model.currentProfileExists());

        return;
    }

    /*
     * @Test public void checkFriendAddition() throws Exception { // Ensure that
     * adding a friend makes changes to both the // model and the database.
     * FriendTempData foundFriend = null;
     * 
     * controller.removeFriend(friendUserID);
     * 
     * assertNull(model.findUserByAccountName(friendUserID)); db = new
     * DatabaseFunctions(); for (FriendTempData f :
     * db.getFriendListByAccountName(userID)) {
     * assertTrue(!f.getUserID().equalsIgnoreCase(friendUserID)); }
     * 
     * controller.addFriend(friendUserID);
     * 
     * assertNotNull(model.findUserByAccountName(friendUserID)); db = new
     * DatabaseFunctions(); for (FriendTempData f :
     * db.getFriendListByAccountName(userID)) { if
     * (f.getUserID().equalsIgnoreCase(friendUserID)) {
     * 
     * foundFriend = f; break; } }
     * assertTrue(foundFriend.getUserID().equalsIgnoreCase(friendUserID));
     * 
     * return; }
     * 
     * @Test public void checkFriendRemoval() throws Exception { // Ensure that
     * adding a friend makes changes to both the // model and the database.
     * FriendTempData foundFriend = null;
     * 
     * controller.addFriend(friendUserID);
     * 
     * assertNotNull(model.findUserByAccountName(friendUserID)); db = new
     * DatabaseFunctions(); for (FriendTempData f :
     * db.getFriendListByAccountName(userID)) { if
     * (f.getUserID().equalsIgnoreCase(friendUserID)) {
     * 
     * foundFriend = f; break; } }
     * assertTrue(foundFriend.getUserID().equalsIgnoreCase(friendUserID));
     * 
     * controller.removeFriend(friendUserID);
     * 
     * assertNull(model.findUserByAccountName(friendUserID)); db = new
     * DatabaseFunctions(); for (FriendTempData f :
     * db.getFriendListByAccountName(userID)) {
     * assertTrue(!f.getUserID().equalsIgnoreCase(friendUserID)); }
     * 
     * return; }
     */
}
