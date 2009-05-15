package core;
import org.jivesoftware.smack.*;
public class testing {

	/**
	 * @param args
	 */
	private static final String PASSWORD = "password";;
	private static final String ID = "Username@gmail.com";

	public static void main(String[] args) throws XMPPException {
		// TODO Auto-generated method stub
		ConnectionConfiguration config = new ConnectionConfiguration("talk.google.com", 5222, "gmail.com");
		XMPPConnection conn2 = new XMPPConnection(config);
		try {
		conn2.connect();
			System.out.println("connected to "+conn2.getHost());
		}catch (XMPPException ex){
			System.out.println("failed to connect to "+conn2.getHost());
		}
		try {
			conn2.login(ID, PASSWORD);
			System.out.println("login as " + conn2.getUser());
		}catch (XMPPException ex){
			System.out.println("Failed to login as "+ conn2.getUser());
		}
		conn2.disconnect();


	}

}
