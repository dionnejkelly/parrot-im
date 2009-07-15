package controller.services;




import java.io.File;
import java.net.URL;
import java.util.*;
import java.util.regex.*;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import net.kano.joscar.rv.RvProcessor;
import net.kano.joustsim.Screenname;
import net.kano.joustsim.oscar.AimConnection;
import net.kano.joustsim.oscar.AimConnectionProperties;
import net.kano.joustsim.oscar.AimSession;
import net.kano.joustsim.oscar.AppSession;
import net.kano.joustsim.oscar.DefaultAimSession;
import net.kano.joustsim.oscar.OpenedServiceListener;
import net.kano.joustsim.oscar.State;
import net.kano.joustsim.oscar.StateEvent;
import net.kano.joustsim.oscar.StateListener;
import net.kano.joustsim.oscar.oscar.service.Service;
import net.kano.joustsim.oscar.oscar.service.icbm.IcbmListener;
import net.kano.joustsim.oscar.oscar.service.ssi.Buddy;
import net.kano.joustsim.oscar.oscar.service.ssi.BuddyList;
import net.kano.joustsim.oscar.oscar.service.ssi.BuddyListLayoutListener;
import net.kano.joustsim.oscar.oscar.service.ssi.Group;
import net.kano.joustsim.oscar.oscar.service.ssi.MutableBuddyList;
import net.kano.joustsim.oscar.oscar.service.ssi.SsiService;

import org.jivesoftware.smack.XMPPException;

import model.dataType.tempData.FriendTempData;
import model.enumerations.ServerType;
import model.enumerations.UserStateType;

public class ICQManager implements GenericConnection {
	private static final String ICQ_SERVER = "login.messaging.aol.com";
    private static final int ICQ_PORT = 5190;
   
    private static final int TIME_OUT = 30; //30 seconds
    
    private AimConnection connection;
    
	private AimConnectionProperties connectionProperties;
	
	private RvProcessor rvProcessor;
	
	private State state;
	
	private IcbmListener lastIcbmListener;
    
    // @Override
    public void addFriend(String userID) throws BadConnectionException {
        // TODO Auto-generated method stub

    }

    // @Override
    public void changeStatus(UserStateType state, String status)
            throws BadConnectionException {
        // TODO Auto-generated method stub

    }

    // @Override
    public void disconnect() {
        // TODO Auto-generated method stub

    }


    // @Override
    public boolean removeFriend(String userID) throws BadConnectionException {
        // TODO Auto-generated method stub
        return false;
    }

    // @Override
    public ArrayList<FriendTempData> retrieveFriendList()
            throws BadConnectionException {
    	
    	//assume that this method is called after logging in and connected
    	ArrayList<FriendTempData> friends = new ArrayList<FriendTempData>();
        FriendTempData friendToAdd = null;
        String userID = null;
        
        int count = 0;
        while(state != State.ONLINE && count < TIME_OUT){
        	//wait until the client is connected and online
        	try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				//Do nothing
			}
			count++;
        }
        if(count>=30){
			System.out.println("Waiting for too long!!");
			return null;
		}
        MutableBuddyList bList = connection.getSsiService().getBuddyList();
        
        
        return null;
    }

    // @Override
    public UserStateType retrieveState(String userID)
            throws BadConnectionException {
    	UserStateType userState = UserStateType.OFFLINE;
    	
    	Long bitFlag = connection.getBuddyInfoManager()
		.getBuddyInfo(new Screenname(userID)).getIcqStatus();
    	
    	if((bitFlag & 0x0001) != 0){
			System.out.println("User is away");
		}else if((bitFlag & 0x0002) != 0){
			System.out.println("User should not be disturbed");
		}else if((bitFlag & 0x0004) != 0){
			System.out.println("User is not available");
		}else if((bitFlag & 0x0010) != 0){
			System.out.println("User is occupied");
		}else if((bitFlag & 0x0020) != 0){
			System.out.println("User is free for chat");
		}else if((bitFlag & 0x0100) != 0){
			System.out.println("User is marked as invisible");
		}else{
			System.out.println("User is offline");
		}
    	return userState;
    }

    // @Override
    public String retrieveStatus(String userID) throws BadConnectionException {
    	return null;
    	
    }

    // @Override
    public void sendMessage(String toUserID, String message)
            throws BadConnectionException {
        // TODO Auto-generated method stub

    }

    public ImageIcon getAvatarPicture(String userID) throws XMPPException {
        // TODO Auto-generated method stub
        return null;
    }

    public ServerType getServerType() {
        return ServerType.ICQ;
    }

	public void setTypingState(int state, String UserID) throws BadConnectionException,
			XMPPException {
		// TODO Auto-generated method stub
		
	}

	public void setAvatarPicture(byte[] byeArray) throws XMPPException {
		// TODO Auto-generated method stub
		
	}

	public void setAvatarPicture(File file) throws XMPPException {
		// TODO Auto-generated method stub
		
	}

	public void setAvatarPicture(URL url) throws XMPPException {
		// TODO Auto-generated method stub
		
	}

    public void login(String userID, String password)
            throws BadConnectionException {
    	AppSession app = new AppSession() {
            public AimSession openAimSession(Screenname screenName) {
                return new DefaultAimSession(this, screenName) {
                	//for security stuff
                	//Do nothing for now
                };
            }
        };
        Screenname screenName = new Screenname(userID);
        AimSession session = app.openAimSession(screenName);
        
        connectionProperties = new AimConnectionProperties(null, null);
        connectionProperties.setScreenname(screenName);
        connectionProperties.setPassword(password);
        connectionProperties.setLoginHost(System.getProperty("OSCAR_HOST", ICQ_SERVER));
        connectionProperties.setLoginPort(Integer.getInteger("OSCAR_PORT", ICQ_PORT));
        
        connection = session.openConnection(connectionProperties);
        
        connection.addOpenedServiceListener(new DefaultOpenedServiceListener());
        connection.addStateListener(new DefaultStateListener());
        connection.connect();
    }

	public String getUserEmailHome() throws XMPPException {
		// TODO Auto-generated method stub
		return null;
	}

	public String getUserEmailWork() throws XMPPException {
		// TODO Auto-generated method stub
		return null;
	}

	public String getUserFirstName() throws XMPPException {
		// TODO Auto-generated method stub
		return null;
	}

	public String getUserLastName() throws XMPPException {
		// TODO Auto-generated method stub
		return null;
	}

	public String getUserMiddleName() throws XMPPException {
		// TODO Auto-generated method stub
		return null;
	}

	public String getUserNickName() throws XMPPException {
		// TODO Auto-generated method stub
		return null;
	}

	public String getUserOrganization() throws XMPPException {
		// TODO Auto-generated method stub
		return null;
	}

	public String getUserOrganizationUnit() throws XMPPException {
		// TODO Auto-generated method stub
		return null;
	}

	public String getUserPhoneHome() throws XMPPException {
		// TODO Auto-generated method stub
		return null;
	}

	public String getUserPhoneWork() throws XMPPException {
		// TODO Auto-generated method stub
		return null;
	}

	public void load(String userID) throws XMPPException {
		// TODO Auto-generated method stub
		
	}

	public void load() throws XMPPException {
		// TODO Auto-generated method stub
		
	}

	public void setUserEmailHome(String name) throws XMPPException {
		// TODO Auto-generated method stub
		
	}

	public void setUserEmailWork(String name) throws XMPPException {
		// TODO Auto-generated method stub
		
	}

	public void setUserFirstName(String name) throws XMPPException {
		// TODO Auto-generated method stub
		
	}

	public void setUserLastName(String name) throws XMPPException {
		// TODO Auto-generated method stub
		
	}

	public void setUserMiddleName(String name) throws XMPPException {
		// TODO Auto-generated method stub
		
	}

	public void setUserNickName(String name) throws XMPPException {
		// TODO Auto-generated method stub
		
	}

	public void setUserOrganization(String name) throws XMPPException {
		// TODO Auto-generated method stub
		
	}

	public void setUserOrganizationUnit(String name) throws XMPPException {
		// TODO Auto-generated method stub
		
	}

	public void setUserPhoneHome(String name) throws XMPPException {
		// TODO Auto-generated method stub
		
	}

	public void setUserPhoneWork(String name) throws XMPPException {
		// TODO Auto-generated method stub
		
	}
	/* *********************** Listeners***************************/
	private class BuddyListFunctionListener implements BuddyListLayoutListener{

		@Override
		public void buddiesReordered(BuddyList bList, Group group,
				List<? extends Buddy> oldList, List<? extends Buddy> newList) {
			
			System.out.println("buddiesRe: "+group.getName());
			for (Buddy b:group.getBuddiesCopy()){
				System.out.println("buddiesRe: "+group.getName()+": "+b.getScreenname().toString());
			}
			for(Buddy b:newList){
				System.out.println("buddiesRe: "+b.getScreenname().toString());
			}
		}

		@Override
		public void buddyAdded(BuddyList bList, Group group,
				List<? extends Buddy> oldList, List<? extends Buddy> newList,
				Buddy buddy) {
			String subscriptionRequest =
                buddy.getScreenname()
                        + " wants to add you as a friend. Add as a friend?";
			JOptionPane.showMessageDialog(null, subscriptionRequest);
			for (Group g:buddy.getBuddyList().getGroups()){
				for(Buddy b:g.getBuddiesCopy()){
					System.out.println("badded: "+g.getName()+": "+b.getScreenname().toString());
				}
			}
			for(Buddy b:newList){
				System.out.println("buddyadded: "+b.getScreenname().toString());
			}
		}

		@Override
		public void buddyRemoved(BuddyList bList, Group group,
				List<? extends Buddy> oldList, List<? extends Buddy> newList,
				Buddy buddy) {
			
			
		}

		@Override
		public void groupAdded(BuddyList arg0, List<? extends Group> arg1,
				List<? extends Group> arg2, Group arg3,
				List<? extends Buddy> arg4) {
			System.out.println("gadded: "+arg3.toString()+"\n"+arg4.toString()+"\n"+arg2.toString());
			for(Buddy b:arg4){
				System.out.println("gadded2: "+b.getScreenname().toString());
			}
			for(Group g:arg1){
				for (Buddy b:g.getBuddiesCopy()){
					System.out.println("gadded3: "+g.getName()+": "+b.getScreenname().toString());
				}
			}
		}

		@Override
		public void groupRemoved(BuddyList arg0, List<? extends Group> arg1,
				List<? extends Group> arg2, Group arg3) {
			
			System.out.println("gRemove: "+arg3.toString());
		}

		@Override
		public void groupsReordered(BuddyList arg0, List<? extends Group> arg1,
				List<? extends Group> arg2) {
			
			System.out.println("gReorder: "+arg1.toString());
			for(Group g:arg1){
				for (Buddy b:g.getBuddiesCopy()){
					System.out.println("gReorder2: "+g.getName()+": "+b.getScreenname().toString());
				}
				
			}
		}
		private void update(){
			
		}
	}
	private class DefaultOpenedServiceListener implements OpenedServiceListener{

		@Override
		public void closedServices(AimConnection arg0,
				Collection<? extends Service> arg1) {
			// Do Nothing when closing services
			
		}

		@Override
		public void openedServices(AimConnection arg0,
				Collection<? extends Service> arg1) {
			
			for (Service service : arg1) {
                if (service instanceof SsiService) {
                	//if this services is a SsiService(child of service)
                    ((SsiService) service).getBuddyList()
                    .addRetroactiveLayoutListener(new BuddyListFunctionListener());
                }
			}
			
		}
		
	}
	private class DefaultStateListener implements StateListener{

		@Override
		public void handleStateChange(StateEvent event) {
			state = event.getNewState();
			if(state == State.ONLINE){
				System.out.println("is now online");
				//System.out.println(connection.getSsiService().getBuddyList().getGroups());
			}
			
		}
		
	}

	public void sendFile(String filePath, String userID) throws XMPPException {
		// TODO Auto-generated method stub
		
	}

	public boolean isValidUserID(String userID) {
		// TODO Auto-generated method stub
		return false;
	}

}
