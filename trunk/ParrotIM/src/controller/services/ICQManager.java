package controller.services;


import java.io.File;
import java.net.URL;
import java.util.*;
import java.util.regex.*;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.jivesoftware.smack.XMPPException;

import com.itbs.aimcer.commune.ConnectionEventListener;

import net.kano.joscar.ByteBlock;
import net.kano.joscar.FileWritable;
import net.kano.joscar.Writable;
import net.kano.joscar.net.ConnProcessorExceptionEvent;
import net.kano.joscar.net.ConnProcessorExceptionHandler;
import net.kano.joscar.rv.RvProcessor;
import net.kano.joscar.rvcmd.DefaultRvCommandFactory;
import net.kano.joscar.snac.ClientSnacProcessor;
import net.kano.joscar.snac.SnacRequestAdapter;
import net.kano.joscar.snac.SnacRequestListener;
import net.kano.joscar.snac.SnacRequestSentEvent;
import net.kano.joscar.snac.SnacRequestTimeoutEvent;
import net.kano.joscar.snac.SnacResponseEvent;
import net.kano.joscar.snac.SnacResponseListener;
import net.kano.joscar.snaccmd.FullUserInfo;
import net.kano.joscar.snaccmd.auth.AuthCommand;
import net.kano.joscar.snaccmd.auth.AuthResponse;
import net.kano.joscar.snaccmd.conn.ServiceRedirect;
import net.kano.joscar.snaccmd.conn.ServiceRequest;
import net.kano.joscar.snaccmd.conn.SetExtraInfoCmd;
import net.kano.joscar.snaccmd.icon.IconCommand;
import net.kano.joscar.snaccmd.icon.UploadIconCmd;
import net.kano.joscar.snaccmd.loc.GetInfoCmd;
import net.kano.joustsim.Screenname;
import net.kano.joustsim.oscar.AimConnection;
import net.kano.joustsim.oscar.AimConnectionProperties;
import net.kano.joustsim.oscar.AimSession;
import net.kano.joustsim.oscar.AppSession;
import net.kano.joustsim.oscar.BuddyInfo;
import net.kano.joustsim.oscar.DefaultAimSession;
import net.kano.joustsim.oscar.OpenedServiceListener;
import net.kano.joustsim.oscar.State;
import net.kano.joustsim.oscar.StateEvent;
import net.kano.joustsim.oscar.StateListener;
import net.kano.joustsim.oscar.oscar.BasicConnection;
import net.kano.joustsim.oscar.oscar.OscarConnection;
import net.kano.joustsim.oscar.oscar.service.Service;
import net.kano.joustsim.oscar.oscar.service.bos.MainBosService;
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


import view.styles.ProgressMonitorScreen;

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
	
	private State connectionState;
	private UserStateType curState;
	
	private MainController controller;
	private Model model;
	private GenericConnection genericConnection;
	
	private IcbmListener lastIcbmListener;
	
	private  BasicConnection iconConnection;
    
	public static void main(String[] args) {
		ICQManager i = new ICQManager(null, null);
		try {
			i.login("595605824", "testingicq");
		} catch (BadConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		try {
//			i.retrieveFriendList();
//		} catch (BadConnectionException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		//i.getAvatarPicture("565914305");
		//i.getAvatarPicture("cmpt275");
		try {
			i.changeStatus(UserStateType.NOT_AVAILABLE, "lolololol");
		} catch (BadConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while (true) {
			System.out.println("Type your state: ");
			Scanner msgInput = new Scanner(System.in);
			String msg = msgInput.nextLine();
			try {
				i.changeStatus(UserStateType.NOT_AVAILABLE, msg);
			} catch (BadConnectionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	

	 public void setPresence(String status) {
		 
	     if (connection != null && connection.getInfoService() != null) {
	    	 System.out.println("Trying to change the presence...");
	    	 connection.getInfoService().setAwayMessage(status);
	     }
	            
	        
	 }
	 
	 public void setProfile(String profile) {
	       
		  
	      if (connection != null && connection.getInfoService() != null) {
	    	  System.out.println("Trying to change the profile...");
	    	  connection.getInfoService().setUserProfile(profile);
	      }
	        	
	 }
	 
	 public void setStatus(String status) {
		 if (connection != null && connection.getInfoService() != null) {
			 System.out.println("Trying to change the status...");
			 connection.getBosService().setStatusMessage(status);
			 connection.getBosService().setStatusMessageSong("¸ô¶ó1", status, "¸ô¶ó3", "¸ô¶ó4");
		 }
	 }
	 
	 public void setOnline(boolean online) {
		 if (connection != null && connection.getInfoService() != null) {
			 connection.getBosService().setVisibleStatus(online);
		 }
	 }
	 
	 public void setState(long state) {
		 if (connection != null && connection.getInfoService() != null) {
			 connection.getBosService().setIcqStatus(state);
			
		 }
	 }
	 
//	 public void setIdle(long idle) {
//		 if (connection != null && connection.getInfoService() != null) {
//			 Date date = new Date();
//			 connection.getBosService().setIdleSince(date);
//		 }
//	 }


	
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
        chkConnection();
        System.out.println("Passed the check connection test and trying to change the status...");
        MainBosService bos = connection.getBosService();
        
        long icqState = stateToLong(state); 
        //icqmanager doesn't use an integer to set status
        //check out the private method called stateToLong , it would convert UserStateType
        //into a Long value that ICQ server would understand
        //Not availible state is in there as well
        
        
        
        
        if (state == UserStateType.INVISIBLE ||state == UserStateType.OFFLINE){
        	//make user invisible to other contacts
        	bos.setVisibleStatus(false);
        }else{
        	bos.setVisibleStatus(true);
        }
        
        //setting status message and changing state to away
        connection.getInfoService().setAwayMessage(status);

        //this one doesn't work for some how
        bos.setStatusMessage(status);
        try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        //state has to be set after setting away message
        bos.getOscarConnection().sendSnac(new SetExtraInfoCmd(icqState));
//        }
        	//connection.getInfoService().setAwayMessage(status);
        	//setOnline(true);
//        if (state == UserStateType.ONLINE) {
//        	setOnline(true);
//        }
//        
//        else if (state == UserStateType.INVISIBLE ||state == UserStateType.OFFLINE) {
//        	setOnline(false);
//        }
//        
//        else if (state == UserStateType.AWAY) {
//        	 setState(1);
////        	 connection.getInfoService().setAwayMessage(status);
////        	 bos.setStatusMessage(status);
//        } 
//        
//        else if (state == UserStateType.BUSY) {
//        	setState(2);
//        } 
//        
//        else if (state == UserStateType.BRB) {
//        	setState(4);
//        }
//	 
//        else if (state == UserStateType.PHONE) {
//        	setState(2);
//        }
//	 
//        else if (state == UserStateType.LUNCH) {
//        	setState(4);
//        }
//        
//        else{
//        	bos.setStatusMessage(status);
//        }
        
        
//        setPresence(status);
//        setProfile(status);
//        setStatus(status);
//        bos.getOscarConnection().sendSnac(new SetExtraInfoCmd(icqState));
    }
    /**
     * helper method for changeStatus
     * it converts UserStateType into Joscar specific datatyype
     * return Online if UserStateType is not stated in if statements
     */
    private long stateToLong(UserStateType state){
    	if(state == UserStateType.ONLINE){
    		return FullUserInfo.ICQSTATUS_DEFAULT;
    	}else if(state == UserStateType.AWAY || state == UserStateType.BRB){
    		return FullUserInfo.ICQSTATUS_AWAY;
    	}else if(state == UserStateType.NOT_AVAILABLE){
    		System.out.println("not availible");
    		return FullUserInfo.ICQSTATUS_NA;
    	}
    	else if(state == UserStateType.BUSY ||state == UserStateType.LUNCH
    			||state == UserStateType.PHONE){
    		return FullUserInfo.ICQSTATUS_OCCUPIED;
    	}
    	else if(state == UserStateType.NOT_BE_DISTURBED){
    		return FullUserInfo.ICQSTATUS_DND;
    	}
		return FullUserInfo.ICQSTATUS_DEFAULT;
    	
    }
    // @Override
    public void disconnect() {
        connection.disconnect();

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
//    	StatusResponseRetriever responseRetriever =
//            new StatusResponseRetriever();
//    	UserStateType userState = UserStateType.OFFLINE;
//    	
//    	Long bitFlag = connection.getBuddyInfoManager()
//		.getBuddyInfo(new Screenname(userID)).getIcqStatus();
//    	

//    	GetInfoCmd getInfoCmd =
//            new GetInfoCmd(GetInfoCmd.CMD_USER_INFO, contactIdentifier);
//    	connection.getInfoService().getOscarConnection()
//        .sendSnacRequest();

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

  

    public ServerType getServerType() {
        return ServerType.ICQ;
    }

	public void setTypingState(int state, String UserID) throws BadConnectionException,
			XMPPException {
		// TODO Auto-generated method stub
		
	}

	 public ImageIcon getAvatarPicture(String userID) {
	    	BuddyInfo binfo = connection.getBuddyInfoManager().getBuddyInfo(new Screenname(userID));
	        ByteBlock byteBlock = binfo.getIconData();
	        ImageIcon icon =  new ImageIcon(byteBlock.toByteArray());
	        
	        if (icon == null) {
	        	icon = new ImageIcon(this.getClass().getResource(
		        "/images/chatwindow/personal.png"));
	        }
	        
	        JOptionPane.showMessageDialog(null, icon);
	        
	        return icon;
	  }
	  

	public void setAvatarPicture(final File file) throws XMPPException {
		
		final SnacRequestListener listener = new SnacRequestListener() {
            public void handleResponse(SnacResponseEvent e) {
                e.getRequest(); // need to?
            }

            public void handleSent(SnacRequestSentEvent e) {
                // great!
                e.getRequest(); // need to?
            }

            public void handleTimeout(SnacRequestTimeoutEvent event) {
//                for (ConnectionEventListener eventHandler : eventHandlers) {
//                    eventHandler.errorOccured("Timed out trying to set an icon.", null);
//                }
            }
        };
        
        iconConnection = new BasicConnection(ICQ_SERVER, ICQ_PORT);
        iconConnection.sendSnacRequest(new UploadIconCmd(ByteBlock.createByteBlock(
        		new FileWritable(file.getAbsolutePath()))), listener);

		connection.getMyBuddyIconManager().requestSetIcon(ByteBlock.createByteBlock(
                new FileWritable(file.getAbsolutePath())));
		
		 //connection.getExternalServiceManager().getIconServiceArbiter().uploadIcon(new Writable(""));
		
	}

	public void setAvatarPicture(URL url) throws XMPPException {
		
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
        
        System.out.println("Screen Name = " + screenName);
        System.out.println("Screen Normal = " + screenName.getNormal());
        System.out.println("Screen Formatted = " + screenName.getFormatted());
        
        AimSession session = app.openAimSession(screenName);
        
        connectionProperties = new AimConnectionProperties(null, null);
        connectionProperties.setScreenname(screenName);
        if (password.length()>8){
        	//icq server doesn't allow password to be more than 8 characters
        	//but still allow user to have more than 8 characters
        	//it only takes the first 8 characters
        	password = password.substring(0, 8);
        }
        connectionProperties.setPassword(password);
        connectionProperties.setLoginHost(System.getProperty("OSCAR_HOST", ICQ_SERVER));
        connectionProperties.setLoginPort(Integer.getInteger("OSCAR_PORT", ICQ_PORT));
        
        connection = session.openConnection(connectionProperties);
        
        connection.addOpenedServiceListener(new DefaultOpenedServiceListener());
        connection.addStateListener(new DefaultStateListener());
      

        connection.connect();

        chkConnection();
        
        ClientSnacProcessor processor =  connection.getBosService()
        .getOscarConnection().getSnacProcessor();
        rvProcessor = new RvProcessor(processor);
        rvProcessor.registerRvCmdFactory(new DefaultRvCommandFactory());
        
        IcbmService icbmService = connection.getIcbmService();
        icbmService.removeIcbmListener(lastIcbmListener);
        lastIcbmListener = new IcbmListener() {

			public void buddyInfoUpdated(IcbmService arg0, Screenname arg1,
					IcbmBuddyInfo arg2) {
				//runs when a new message has been received
				
			}

			public void newConversation(IcbmService service, Conversation conversation) {
				conversation.addConversationListener(new TypingAdapter());
			}

			public void sendAutomaticallyFailed(IcbmService arg0, Message arg1,
					Set<Conversation> arg2) {
				// TODO Auto-generated method stub
				
			}
        	
        };
        icbmService.addIcbmListener(lastIcbmListener);
        
        connection.getBuddyService().addBuddyListener(new BuddyServiceListener(){

			public void buddyOffline(BuddyService service, Screenname arg1) {
				curState = UserStateType.OFFLINE;
				System.out.println(curState);
				controller.friendUpdated(genericConnection, arg1.getFormatted());
			}

			public void gotBuddyStatus(BuddyService service, Screenname buddy,
					FullUserInfo info) {
				curState = longToStatus(info.getIcqStatus());
				System.out.println(curState);
				controller.friendUpdated(genericConnection, buddy.getFormatted());
				
			}
        	
        });
    }

//    private class SnacRequestAdapterListener extends SnacRequestAdapter {
//    	public void handleResponse(SnacResponseEvent e) {
//            try {
//                if (e.getSnacCommand() instanceof ServiceRedirect) {
//                    ServiceRedirect sr = (ServiceRedirect) e.getSnacCommand();
//                    iconConnection = new BasicConnection(sr.getRedirectHost(),
//                            sr.getRedirectPort() > 0 ? sr.getRedirectPort() : connectionProperties.getLoginPort());
//                    iconConnection.setCookie(sr.getCookie());
//                    
//                    iconConnection.getClientFlapConn().getFlapProcessor().addExceptionHandler(new ConnProcessorExceptionHandler() {
//                        public void handleException(ConnProcessorExceptionEvent event) {
//                            event.getException().printStackTrace();
//                        }
//
//						
//                    });
//                    iconConnection.connect();
//                }
//            } catch (Exception e1) {
//                
//            }
//        }
//    
//
//    }

	/*private methods*/
	private void chkConnection(){

        int count = 0;
        while(connectionState != State.ONLINE && count < TIME_OUT){
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
	}
	private UserStateType longToStatus(Long l){
		//TO DO have to figure out signal value for each state
		if(l == -1){
			return UserStateType.OFFLINE;
		}
		
		else if(l==(long)268435457){
//		else if((l & FullUserInfo.ICQSTATUS_AWAY) != 0){
			return UserStateType.AWAY;
		}else if((l & FullUserInfo.ICQSTATUS_DND) != 0){
			return UserStateType.NOT_BE_DISTURBED;
//		}else if(l==(long)268435461){
		}else if((l & FullUserInfo.ICQSTATUS_NA)!=0){
			return UserStateType.NOT_AVAILABLE;
//		}else if(l==(long)268435473){
		}else if((l & FullUserInfo.ICQSTATUS_OCCUPIED)!=0){
			return UserStateType.BUSY;
		}else if((l & FullUserInfo.ICQSTATUS_FFC)!=0){
//		}else if(l==(long)268435456){
			return UserStateType.ONLINE;
		}else if((l & FullUserInfo.ICQSTATUS_INVISIBLE) != 0){
			return UserStateType.INVISIBLE;
		}else{
			return UserStateType.ONLINE;
		}
	}
	/* *********************** Listeners***************************/
private class TypingAdapter extends ConversationAdapter implements TypingListener{
		
		public void gotMessage(Conversation c, final MessageInfo minfo){
			System.out.println("Who are you? " + c.getBuddy().getNormal());
			System.out.println("To who " + connection.getScreenname().getNormal());
			System.out.println("Message = " +minfo.getMessage().getMessageBody());
			//connection.getLocalPrefs().getScreenname().getNormal()
			controller.messageReceived(c.getBuddy().getNormal(), connection.getScreenname().getNormal()
					, minfo.getMessage().getMessageBody());
			String text = minfo.getMessage().getMessageBody();
			System.out.println(text);
		}
		public void gotTypingState(Conversation conversation, TypingInfo typingInfo) {
			if (typingInfo.getTypingState().equals(TypingState.TYPING)){
				System.out.println(conversation.getBuddy()+" is typing");
				controller.setTypingState(2);
			}else if (typingInfo.getTypingState().equals(TypingState.PAUSED)){
				controller.setTypingState(5);
			}else if (typingInfo.getTypingState().equals(TypingState.NO_TEXT)){
				controller.setTypingState(1);
			}
			
		}
		
	}
	private class BuddyListFunctionListener implements BuddyListLayoutListener{

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

		public void buddyAdded(BuddyList bList, Group group,
				List<? extends Buddy> oldList, List<? extends Buddy> newList,
				Buddy buddy) {
			String subscriptionRequest =
                buddy.getScreenname()
                        + " wants to add you as a friend. Add as a friend?";
			//JOptionPane.showMessageDialog(null, subscriptionRequest);
			for (Group g:buddy.getBuddyList().getGroups()){
				for(Buddy b:g.getBuddiesCopy()){
					System.out.println("badded: "+g.getName()+": "+b.getScreenname().toString());
				}
			}
			for(Buddy b:newList){
				System.out.println("buddyadded: "+b.getScreenname().toString());
			}
		}

		public void buddyRemoved(BuddyList bList, Group group,
				List<? extends Buddy> oldList, List<? extends Buddy> newList,
				Buddy buddy) {
			
			
		}

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

		public void groupRemoved(BuddyList arg0, List<? extends Group> arg1,
				List<? extends Group> arg2, Group arg3) {
			
			System.out.println("gRemove: "+arg3.toString());
		}

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

		public void closedServices(AimConnection arg0,
				Collection<? extends Service> arg1) {
			// Do Nothing when closing services
			
		}

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

		public void handleStateChange(StateEvent event) {
			connectionState = event.getNewState();
			if(connectionState == State.ONLINE){
				System.out.println("is now online");
				
				//System.out.println(connection.getSsiService().getBuddyList().getGroups());
			}
			
		}
		
	}
	private class DefaultSnacResponseListener implements SnacResponseListener{

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
				}else{
					System.out.println(errorCode);
				}
			}else{
				
			}
			
		}
		
	}

	public void sendFile(String filePath, String userID, ProgressMonitorScreen progress) throws XMPPException {
		// TODO Auto-generated method stub
		
	}

	public boolean isValidUserID(String userID) {
		// TODO Auto-generated method stub
		return false;
	}
    
	public void inviteFriend(String userID, String roomName) throws XMPPException {
		// TODO Auto-generated method stub
		
	}
	public void createRoom(String room) throws XMPPException {
		// TODO Auto-generated method stub
		
	}
	
	public boolean isConferenceChat() {
    	return false;
    }
	public void sendMultMessage(String message, String roomName)
			throws BadConnectionException {
		// TODO Auto-generated method stub
		
	}
	public boolean doesExist(String userID) {
		// TODO Auto-generated method stub
		return false;
	}
	public boolean isFollowing(String userID) {
		// TODO Auto-generated method stub
		return false;
	}


	public void setAvatarPicture(byte[] bytes) throws XMPPException {
		// TODO Auto-generated method stub
		
	}




}
