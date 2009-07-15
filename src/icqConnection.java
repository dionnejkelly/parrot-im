import java.util.Collection;
import java.util.List;

import javax.swing.JOptionPane;

import net.kano.joscar.JoscarTools;
import net.kano.joscar.logging.Logger;
import net.kano.joscar.net.ClientConn;
import net.kano.joscar.net.ClientConnEvent;
import net.kano.joscar.net.ClientConnListener;
import net.kano.joscar.net.ConnDescriptor;
import net.kano.joscar.rv.RvProcessor;
import net.kano.joscar.snac.SnacResponseEvent;
import net.kano.joscar.snac.SnacResponseListener;
import net.kano.joscar.snaccmd.auth.AuthCommand;
import net.kano.joscar.snaccmd.auth.AuthResponse;
import net.kano.joustsim.Screenname;
import net.kano.joustsim.oscar.AimConnection;
import net.kano.joustsim.oscar.AimConnectionProperties;
import net.kano.joustsim.oscar.AimSession;
import net.kano.joustsim.oscar.AppSession;
import net.kano.joustsim.oscar.DefaultAimSession;
import net.kano.joustsim.oscar.DefaultAppSession;
import net.kano.joustsim.oscar.OpenedServiceListener;
import net.kano.joustsim.oscar.State;
import net.kano.joustsim.oscar.StateEvent;
import net.kano.joustsim.oscar.StateListener;
import net.kano.joustsim.oscar.oscar.service.Service;
import net.kano.joustsim.oscar.oscar.service.icbm.Conversation;
import net.kano.joustsim.oscar.oscar.service.icbm.IcbmListener;
import net.kano.joustsim.oscar.oscar.service.icbm.Message;
import net.kano.joustsim.oscar.oscar.service.icbm.SimpleMessage;
import net.kano.joustsim.oscar.oscar.service.ssi.Buddy;
import net.kano.joustsim.oscar.oscar.service.ssi.BuddyList;
import net.kano.joustsim.oscar.oscar.service.ssi.BuddyListLayoutListener;
import net.kano.joustsim.oscar.oscar.service.ssi.Group;
import net.kano.joustsim.oscar.oscar.service.ssi.MutableBuddyList;
import net.kano.joustsim.oscar.oscar.service.ssi.SsiService;


public class icqConnection {

	/**
	 * @param args
	 */
	private AimConnection connection;
	private AimConnectionProperties connectionProperties;
	private State state;
	private RvProcessor rvProcessor;
	private IcbmListener lastIcbmListener;
	public icqConnection() 
	{
	}
	public void login(String userID, String password, String server, int port){
		AppSession appSession = new AppSession() {
            public AimSession openAimSession(Screenname sn) {
                return new DefaultAimSession(this, sn) {
                    // todo finish off secure stuff
                    //                    public TrustPreferences getTrustPreferences() {
                    //                        return new PermanentSignerTrustManager(screenName);
                    //                    }
                };
            }
        };

        
		Screenname screenName = new Screenname(userID);
		AimSession session = appSession.openAimSession(screenName);
		
		connectionProperties = new AimConnectionProperties(null, null);
        connectionProperties.setScreenname(screenName);
        connectionProperties.setPassword(password);
        connectionProperties.setLoginHost(System.getProperty("OSCAR_HOST", server));
        connectionProperties.setLoginPort(Integer.getInteger("OSCAR_PORT", port));
        connection = session.openConnection(connectionProperties);
        connection.addOpenedServiceListener(new DefaultOpenedServiceListener());
        connection.addStateListener(new DefaultStateListener());
        
        connection.connect();
        
        //this.getBuddyList();
        int count = 0;
		while(state!=State.ONLINE && count<30){
			try {
				if(state == State.FAILED){
					
				}
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				//Do nothing
			}
		}
        
        }
	public void getBuddyList(){
		
		//assume that getBuddyList is called after logging in and connected
		int count = 0;
		while(state!=state.ONLINE && count<30){
			try {
				System.out.println(state);
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				//Do nothing
			}
		}
		System.out.println("after connection");
		if(count>=30){
			System.out.println("Waiting for too long!!");
			return;
		}
		if(state != State.ONLINE){
			System.out.println("It's not ONLINE");
			return;
		}
		
		System.out.println(connection.getBuddyInfoManager()
				.getBuddyInfo(new Screenname("595605824")).getAwayMessage());
		System.out.println(connection.getBuddyInfoManager()
				.getBuddyInfo(new Screenname("595605824")).getIcqStatus());
		Long l = connection.getBuddyInfoManager()
		.getBuddyInfo(new Screenname("595605824")).getIcqStatus();
		System.out.println(l.toHexString(connection.getBuddyInfoManager()
		.getBuddyInfo(new Screenname("595605824")).getIcqStatus()));
		System.out.println(connection.getBuddyInfoManager()
				.getBuddyInfo(new Screenname("595605824")).getStatusMessage());
		System.out.println(connection.getBuddyInfoManager()
				.getBuddyInfo(new Screenname("595605824")).getStatusMessage());
		if((l & 0x0001) != 0){
			System.out.println("User is away");
		}else if((l & 0x0002) != 0){
			System.out.println("User should not be disturbed");
		}else if((l & 0x0004) != 0){
			System.out.println("User is not available");
		}else if((l & 0x0010) != 0){
			System.out.println("User is occupied");
		}else if((l & 0x0020) != 0){
			System.out.println("User is free for chat");
		}else if((l & 0x0100) != 0){
			System.out.println("User is marked as invisible");
		}else{
			System.out.println("User is offline");
		}
		MutableBuddyList BList = connection.getSsiService().getBuddyList();
		for(Group g:BList.getGroups()){
			for(Buddy b:g.getBuddiesCopy()){
				System.out.println("buddyList works: "+b.getAlias());
			}
		}
		return;
		
	}
	
	/* *********************** Listeners***************************/
	private class BuddyListFunctionListener implements BuddyListLayoutListener{

		@Override
		public void buddiesReordered(BuddyList bList, Group group,
				List<? extends Buddy> oldList, List<? extends Buddy> newList) {
			// TODO Auto-generated method stub
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
		public void buddyRemoved(BuddyList arg0, Group arg1,
				List<? extends Buddy> arg2, List<? extends Buddy> arg3,
				Buddy arg4) {
			// TODO Auto-generated method stub
			System.out.println("bRemoved"+arg4.getScreenname() + " from entriesUpdated");
			
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
			// TODO Auto-generated method stub
			System.out.println("gRemove: "+arg3.toString());
		}

		@Override
		public void groupsReordered(BuddyList arg0, List<? extends Group> arg1,
				List<? extends Group> arg2) {
			// TODO Auto-generated method stub
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
                service.getOscarConnection().getSnacProcessor().addGlobalResponseListener(new SnacResponseListener() {

					@Override
					public void handleResponse(SnacResponseEvent arg0) {
						if(arg0.getSnacCommand() instanceof AuthResponse){
							System.out.println("yes!");
							System.out.println(((AuthResponse)((AuthCommand)arg0.getSnacCommand())).getErrorCode());
						}else{
							System.out.println("no!");
						}
						System.out.println(arg0.getSnacPacket().getFamily() );
						System.out.println(arg0.getSnacPacket().getCommand());
						System.out.println(arg0.getSnacPacket().getReqid());
						
						
					}
                	
                });
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
			}else if(state == State.CONNECTINGAUTH){
				System.out.println(state);
			}else {
				System.out.println(state);
			}

			
		}
		
	}
	public void sendMessage(String toUserID, String message){
		Screenname receiver = new Screenname(toUserID);;
		Conversation conversation = connection.getIcbmService().getImConversation(receiver);
		SimpleMessage outgoingMessage = new SimpleMessage(message);
		conversation.sendMessage(outgoingMessage);
	}
	 public String retrieveStatus(String userID)
	 {
		 String actualStatus="";
		 return actualStatus;
	 }
	 public String retrieveState(String userID)
	 {
		 String state="online";
		 return state;
		 
	 }

}
