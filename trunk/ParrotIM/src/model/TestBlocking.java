package model;

import java.sql.SQLException;

import model.dataType.AccountData;
import model.dataType.GoogleTalkUserData;
import model.dataType.ServerType;

import junit.framework.TestCase;

public class TestBlocking extends TestCase {

    public void testBlock() throws ClassNotFoundException, SQLException {
        Model model = new Model();
        GoogleTalkUserData user = new GoogleTalkUserData("test@gmail.com");
        AccountData account = new AccountData(ServerType.GOOGLE_TALK, 
                "test", "test");
        model.createCurrentProfile(account, "testProfile");
        
        user.setBlocked(false);
        assertTrue(!user.isBlocked());
        model.blockFriend(user);
        assertTrue(user.isBlocked());
        
        /* Now, create a new user with the same name,
         * and the user should still be blocked.
         */
        
        GoogleTalkUserData user2 = new GoogleTalkUserData("test@gmail.com");
        assertTrue(user2.isBlocked());
        model.unblockFriend(user2);
        assertTrue(!user2.isBlocked());
        assertTrue(user.isBlocked());
        
        account.addFriend(user);
        assertTrue(account.friendExists(user));
        model.removeFriend(user);
        assertTrue(!account.friendExists(user));
    }
}
