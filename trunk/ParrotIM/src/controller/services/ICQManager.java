package controller.services;






import java.io.File;
import java.net.URL;
import java.util.*;
import java.util.regex.*;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import net.kano.joscar.rv.RvProcessor;
import net.kano.joscar.rvcmd.DefaultRvCommandFactory;
import net.kano.joscar.snac.ClientSnacProcessor;
import net.kano.joscar.snac.SnacResponseEvent;
import net.kano.joscar.snac.SnacResponseListener;
import net.kano.joscar.snaccmd.FullUserInfo;
import net.kano.joscar.snaccmd.auth.AuthCommand;
import net.kano.joscar.snaccmd.auth.AuthResponse;
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
import net.kano.joustsim.oscar.oscar.service.buddy.BuddyService;
import net.kano.joustsim.oscar.oscar.service.buddy.BuddyServiceListener;
import net.kano.joustsim.oscar.oscar.service.icbm.Conversation;
import net.kano.joustsim.oscar.oscar.service.icbm.ConversationAdapter;
import net.kano.joustsim.oscar.oscar.service.icbm.IcbmBuddyInfo;
import net.kano.joustsim.oscar.oscar.service.icbm.IcbmListener;
import net.kano.joustsim.oscar.oscar.service.icbm.IcbmService;
import net.kano.joustsim.oscar.oscar.service.icbm.Message;
import net.kano.joustsim.oscar.oscar.service.icbm.MessageInfo;
import net.kano.joustsim.oscar.oscar.service.icbm.SimpleMessage;
import net.kano.joustsim.oscar.oscar.service.icbm.TypingInfo;
import net.kano.joustsim.oscar.oscar.service.icbm.TypingListener;
import net.kano.joustsim.oscar.oscar.service.icbm.TypingState;
import net.kano.joustsim.oscar.oscar.service.ssi.Buddy;
import net.kano.joustsim.oscar.oscar.service.ssi.BuddyList;
import net.kano.joustsim.oscar.oscar.service.ssi.BuddyListLayoutListener;
import net.kano.joustsim.oscar.oscar.service.ssi.Group;
import net.kano.joustsim.oscar.oscar.service.ssi.MutableBuddyList;
import net.kano.joustsim.oscar.oscar.service.ssi.SsiService;

import org.jivesoftware.smack.XMPPException;

import controller.MainController;

import model.Model;
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
	private UserStateType curState;
	
	private MainController controller;
	private Model model;
	private GenericConnection genericConnection;
	
	private IcbmListener lastIcbmListener;
    
	public ICQManager(MainController controller, Model model){
		this.controller = controller;
		this.model = model;
		this.genericConnection = this;

	}
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
        
        
        MutableBuddyList bList = connection.getSsiService().getBuddyList();
        for(Group group:bList.getGroups()){
        	for (Buddy buddy:group.getBuddiesCopy()){
        		userID = buddy.getScreenname().getNormal();
        		System.out.println(userID);
        		friendToAdd =
                    new FriendTempData(buddy.getScreenname().getNormal(), buddy.getAlias(), this
                            .retrieveStatus(userID),
                            UserStateType.OFFLINE,group.getName(), false);
        		friends.add(friendToAdd);
        	}
        }
        
        return friends;
    }

    // @Override
    public UserStateType retrieveState(String userID)
            throws BadConnectionException {
//    	UserStateType userState = UserStateType.OFFLINE;
//    	
//    	Long bitFlag = connection.getBuddyInfoManager()
//		.getBuddyInfo(new Screenname(userID)).getIcqStatus();
//    	
    	

    	return curState;
    }

    // @Override
    public String retrieveStatus(String userID) throws BadConnectionException {
    	return connection.getBuddyInfoManager()
		.getBuddyInfo(new Screenname(userID)).getStatusMessage();
    	
    }

    // @Override
    public void sendMessage(String toUserID, String message)
            throws BadConnectionException {
    	Screenname receiver = new Screenname(toUserID);;
		Conversation conversation = connection.getIcbmService().getImConversation(receiver);
		SimpleMessage outgoingMessage = new SimpleMessage(message);
		conversation.sendMessage(outgoingMessage);

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
			return;
		}
        
        ClientSnacProcessor processor =  connection.getBosService()
        .getOscarConnection().getSnacProcessor();
        rvProcessor = new RvProcessor(processor);
        rvProcessor.registerRvCmdFactory(new DefaultRvCommandFactory());
        
        IcbmService icbmService = connection.getIcbmService();
        icbmService.removeIcbmListener(lastIcbmListener);
        lastIcbmListener = new IcbmListener() {

			@Override
			public void buddyInfoUpdated(IcbmService arg0, Screenname arg1,
					IcbmBuddyInfo arg2) {
				//runs when a new message has been received
				
			}

			@Override
			public void newConversation(IcbmService service, Conversation conversation) {
				conversation.addConversationListener(new TypingAdapter());
				
			}

			@Override
			public void sendAutomaticallyFailed(IcbmService arg0, Message arg1,
					Set<Conversation> arg2) {
				// TODO Auto-generated method stub
				
			}
        	
        };
        icbmService.addIcbmListener(lastIcbmListener);
        
        connection.getBuddyService().addBuddyListener(new BuddyServiceListener(){

			@Override
			public void buddyOffline(BuddyService service, Screenname arg1) {
				curState = UserStateType.OFFLINE;
				controller.friendUpdated(genericConnection, arg1.getNormal());
			}

			@Override
			public void gotBuddyStatus(BuddyService service, Screenname buddy,
					FullUserInfo info) {
				curState = longToStatus(info.getIcqStatus());
				controller.friendUpdated(genericConnection, buddy.getNormal());
				
			}
        	
        });
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
	
	private UserStateType longToStatus(Long l){
		//TO DO have to figure out signal value for each state
		if(l == -1){
			return UserStateType.OFFLINE;
		}
		else if((l & FullUserInfo.ICQSTATUS_AWAY) != 0){
			return UserStateType.AWAY;
		}else if((l & FullUserInfo.ICQSTATUS_DND) != 0){
			return UserStateType.NOT_BE_DISTURBED;
		}else if((l & FullUserInfo.ICQSTATUS_NA) != 0){
			return UserStateType.NOT_AVAILABLE;
		}else if((l & FullUserInfo.ICQSTATUS_OCCUPIED) != 0){
			return UserStateType.BUSY;
		}else if((l & FullUserInfo.ICQSTATUS_FFC) != 0){
			return UserStateType.ONLINE;
		}else if((l & FullUserInfo.ICQSTATUS_INVISIBLE) != 0){
			return UserStateType.INVISIBLE;
		}else{
			return UserStateType.OFFLINE;
		}
	}
	/* *********************** Listeners***************************/
private class TypingAdapter extends ConversationAdapter implements TypingListener{
		
		public void gotMessage(Conversation c, final MessageInfo minfo){
			controller.messageReceived(c.getBuddy().getNormal(), connection.getLocalPrefs().getScreenname().getNormal()
					, minfo.getMessage().getMessageBody());
			String text = minfo.getMessage().getMessageBody();
			System.out.println(text);
		}
		@Override
		public void gotTypingState(Conversation conversation, TypingInfo typingInfo) {
			if (typingInfo.getTypingState().equals(TypingState.TYPING)){
				controller.setTypingState(2);
			}else if (typingInfo.getTypingState().equals(TypingState.PAUSED)){
				controller.setTypingState(5);
			}else if (typingInfo.getTypingState().equals(TypingState.NO_TEXT)){
				controller.setTypingState(1);
			}
			
		}
		
	}
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
                service.getOscarConnection().getSnacProcessor()
                .addGlobalResponseListener(new DefaultSnacResponseListener());
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
	private class DefaultSnacResponseListener implements SnacResponseListener{

		@Override
		public void handleResponse(SnacResponseEvent arg0) {
			if(arg0.getSnacCommand() instanceof AuthResponse){
				System.out.println("yes!");
				int errorCode = ((AuthResponse)((AuthCommand)arg0.getSnacCommand())).getErrorCode();
				if(errorCode == 5){
					//TO DO: have a qui popup window to show these error message
					System.out.println("ERROR Invalid screenname or wrong password");
				}else if(errorCode == 17){
					System.out.println("ERROR Account has been suspended temporarily");
				}else if(errorCode == 20){
					System.out.println("ERROR Account temporarily unavailable");
				}else if(errorCode == 24){
					System.out.println("ERROR Connecting too frequently, Try" +
							" waiting a few minutes to reconnect - AIM recommends 10 minutes");
				}else if(errorCode == 28){
					System.out.println("ERROR Client software is too old to connect");
				}
			}else{
				
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
