import java.util.Collection;
import java.util.List;

import javax.swing.JOptionPane;

import net.kano.joscar.net.ClientConn;
import net.kano.joscar.net.ClientConnEvent;
import net.kano.joscar.net.ClientConnListener;
import net.kano.joscar.net.ConnDescriptor;
import net.kano.joscar.rv.RvProcessor;
import net.kano.joustsim.Screenname;
import net.kano.joustsim.oscar.AimConnection;
import net.kano.joustsim.oscar.AimConnectionProperties;
import net.kano.joustsim.oscar.AimSession;
import net.kano.joustsim.oscar.AppSession;
import net.kano.joustsim.oscar.DefaultAimSession;
import net.kano.joustsim.oscar.OpenedServiceListener;
import net.kano.joustsim.oscar.oscar.service.Service;
import net.kano.joustsim.oscar.oscar.service.icbm.IcbmListener;
import net.kano.joustsim.oscar.oscar.service.ssi.Buddy;
import net.kano.joustsim.oscar.oscar.service.ssi.BuddyList;
import net.kano.joustsim.oscar.oscar.service.ssi.BuddyListLayoutListener;
import net.kano.joustsim.oscar.oscar.service.ssi.Group;
import net.kano.joustsim.oscar.oscar.service.ssi.SsiService;


public class icqConnection {

	/**
	 * @param args
	 */
	static AimConnection connection;
	static AimConnectionProperties connectionProperties = new AimConnectionProperties(null, null);
	RvProcessor rvProcessor;
	IcbmListener lastIcbmListener;
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
        connectionProperties.setScreenname(screenName);
        connectionProperties.setPassword(password);
        connectionProperties.setLoginHost(System.getProperty("OSCAR_HOST", server));
        connectionProperties.setLoginPort(Integer.getInteger("OSCAR_PORT", port));
        connection = session.openConnection(connectionProperties);
        getBuddyList();
        connection.connect();
	}
	private void getBuddyList(){
		connection.addOpenedServiceListener(new OpenedServiceListener(){

			@Override
			public void closedServices(AimConnection arg0,
					Collection<? extends Service> arg1) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void openedServices(AimConnection arg0,
					Collection<? extends Service> arg1) {
				for (Service service : arg1) {
                    if (service instanceof SsiService) {
                        ((SsiService) service).getBuddyList()
                        .addRetroactiveLayoutListener(new BuddyListFunctionListener());
				
                    }
				}
			}
		});
		
	}
	
	private class BuddyListFunctionListener implements BuddyListLayoutListener{

		@Override
		public void buddiesReordered(BuddyList bList, Group group,
				List<? extends Buddy> oldList, List<? extends Buddy> newList) {
			// TODO Auto-generated method stub
			System.out.println(group.getName());
		}

		@Override
		public void buddyAdded(BuddyList bList, Group group,
				List<? extends Buddy> oldList, List<? extends Buddy> newList,
				Buddy buddy) {
			String subscriptionRequest =
                buddy.getScreenname()
                        + " wants to add you as a friend. Add as a friend?";
			JOptionPane.showMessageDialog(null, subscriptionRequest);
		}

		@Override
		public void buddyRemoved(BuddyList arg0, Group arg1,
				List<? extends Buddy> arg2, List<? extends Buddy> arg3,
				Buddy arg4) {
			// TODO Auto-generated method stub
			System.out.println(arg4.getScreenname() + " from entriesUpdated");
			
		}

		@Override
		public void groupAdded(BuddyList arg0, List<? extends Group> arg1,
				List<? extends Group> arg2, Group arg3,
				List<? extends Buddy> arg4) {
			System.out.println(arg3.toString()+"\n"+arg4.toString()+"\n"+arg2.toString());
			
		}

		@Override
		public void groupRemoved(BuddyList arg0, List<? extends Group> arg1,
				List<? extends Group> arg2, Group arg3) {
			// TODO Auto-generated method stub
			System.out.println(arg3.toString());
		}

		@Override
		public void groupsReordered(BuddyList arg0, List<? extends Group> arg1,
				List<? extends Group> arg2) {
			// TODO Auto-generated method stub
			System.out.println(arg1.toString());
		}
		private void update(){
			
		}
	}
}
