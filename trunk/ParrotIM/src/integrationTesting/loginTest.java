package integrationTesting;

import static org.junit.Assert.*;

import java.sql.SQLException;

import model.DatabaseFunctions;
import model.Model;
import model.dataType.ServerType;

import org.jivesoftware.smack.XMPPException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import controller.services.Xmpp;

public class loginTest {
    
    private Model model;
    private Xmpp controller;
    private DatabaseFunctions db;
    
    
    @Before
    public void setUp() throws ClassNotFoundException, SQLException {
        DatabaseFunctions.setDatabaseName("test.db");
        db = new DatabaseFunctions();
        db.dropTables();
        this.model = new Model();
        this.controller = new Xmpp(model);
    }

    @After
    public void tearDown() {
        this.model = null;
        this.controller = null;
    }
    
    @Test
    public void loginWithProfile() throws XMPPException {
        String profileName = "Matt Damon";
        String userID = "parrotim.test@gmail.com";
        String password = "abcdefghi";
        
        model.addProfile(profileName, "", true);
        model.addAccount(profileName, "talk.google.com", userID, password);
        
        controller.loginProfile(profileName);
        
        assertTrue(model.getCurrentProfile().getProfileName().equals(profileName));
    }

}
