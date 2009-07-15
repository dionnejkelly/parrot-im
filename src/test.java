import net.kano.joustsim.Screenname;
import net.kano.joustsim.oscar.AimSession;
import net.kano.joustsim.oscar.AppSession;
import net.kano.joustsim.oscar.DefaultAimSession;


public class test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
			icqConnection i = new icqConnection();
			i.login("595605824", "testingicq", "login.messaging.aol.com", 5190);
			i.getBuddyList();
			i.sendMessage("595605824","Hi");//It works
	}

}
