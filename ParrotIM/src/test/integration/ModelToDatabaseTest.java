package test.integration;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Vector;

import model.DatabaseFunctions;
import model.Model;
import model.dataType.tempData.AccountTempData;
import model.enumerations.ServerType;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import controller.MainController;

public class ModelToDatabaseTest {

    private Model model;
    private DatabaseFunctions db;
    private String profileName;
    private String userID;
    private String password;
    private ServerType server;
    private String serverString;

    @Before
    public void setUp() throws Exception {
        DatabaseFunctions.setDatabaseName("test.db");
        db = new DatabaseFunctions();
        db.dropTables();
        this.model = new Model();

        profileName = "TestProfile";
        userID = "me@gmail.com";
        password = "";
        server = ServerType.GOOGLE_TALK;
        serverString = "talk.google.com";
    }

    @After
    public void tearDown() throws Exception {
        this.model = null;
    }

    @Test
    public void testAddRemoveAccount() throws Exception {
        ArrayList<AccountTempData> accountList = null;

        db = new DatabaseFunctions();
        assertTrue(db.getUserList().size() == 0);
        assertTrue(model.getAccountList().size() == 0);
        this.model.addAccount(profileName, serverString, userID, password);
        assertTrue(model.getAccountList().size() == 1);
        db = new DatabaseFunctions();
        accountList = db.getAccountList(profileName);
        assertEquals(accountList.get(0).getUserID(), model.getAccountsForProfile(profileName).get(0).getUserID());

        assertTrue(model.getAccountsForProfile("false").size() == 0);
        this.model.addAccount(profileName, serverString, userID, password);
        assertTrue(model.getAccountList().size() == 1);

        return;
    }
}
