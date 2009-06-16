package controller;

import java.util.ArrayList;
import java.util.Collection;

import model.Model;
import model.dataType.AccountData;
import model.dataType.CurrentProfileData;
import model.dataType.GoogleTalkUserData;
import model.dataType.ServerType;
import model.dataType.UserData;

import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.util.StringUtils;

import controller.services.Xmpp;

import view.buddylist.buddylist;

public class MainController {
    private Model model;
    private Xmpp xmpp;
    
    public MainController(Model model) {
        this.model = model;
        this.xmpp = new Xmpp(this.model); // change to remove parameter model
    }
    
    
    public void login(CurrentProfileData currentProfile) throws XMPPException {
        ArrayList<AccountData> accounts = null;
        UserData user = null;
        ArrayList<String> buddyNames = null;
        String buddyStatus = null;
        String buddyBareAddress = null;
    
    
        accounts = currentProfile.getAccountData();
        
        for (AccountData a : accounts) {
            if (a.getServer() == ServerType.GOOGLE_TALK) {
                /* Try to log in each account */
                this.xmpp.login(a);    
                
                /* Set up friends' user data */
                buddyNames = this.xmpp.getBuddyList();
                for (String s : buddyNames) {
                    buddyBareAddress = StringUtils.parseBareAddress(s);
                    buddyStatus = xmpp.getUserPresence(buddyBareAddress);
                    user = new GoogleTalkUserData(s, buddyBareAddress, 
                            buddyStatus, a);
                    System.out.println(buddyStatus);
                    a.addFriend(user);
                }             
            }
        }
        return;
    }
    
    public void login(ServerType server, String username, String password) 
            throws XMPPException {
        AccountData account = null;
        
        account = new AccountData(server, username, password);
        model.createCurrentProfile(account, "Guest");
        this.login(model.getCurrentProfile());
        
        return;
    }

    // TEMP TESTING METHOD
    public Xmpp getXmpp() {
        return this.xmpp;
    }
    
}
