package controller.services;



import java.util.Vector;

import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.util.StringUtils;
import org.jivesoftware.smackx.Form;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.jivesoftware.smackx.muc.ParticipantStatusListener;



public class MultiUserChatManager extends MultiUserChat  {
	private Vector<String> users = new Vector<String>();
	
	private int countUsers = 0;
	private String roomName;
	
	private XMPPConnection xmppConnection;
	
	
	public MultiUserChatManager(XMPPConnection connection, String room) {
		super(connection, room);
		this.xmppConnection = connection;
		this.roomName = room;
	}
	
	public String getRoomName() {
		return roomName;
	}
	
	public void sendMessage(String message) throws XMPPException {
		super.sendMessage(message);
	}
	
	public int getCountUsers() {
		return countUsers;
	}
	
	public Vector<String> getUsers() {
		return users;
	}
	
	public void joinRoom() throws XMPPException {
		super.join(roomName);
		addParticipantStatusListener(new joinedListener());
	}
	
	public void createRoom() throws XMPPException {
		super.create(roomName);
		super.sendConfigurationForm(new Form(Form.TYPE_SUBMIT));
		addParticipantStatusListener(new joinedListener());
	}
	
	public void inviteFriend(String userID) throws XMPPException {
		super.invite(userID, "Let's have fun");
	}
	
	public void leaveRoom() {
		super.leave();
	}
	
	
	private class joinedListener implements ParticipantStatusListener {

		public void adminGranted(String arg0) {
			// TODO Auto-generated method stub
			
		}

		public void adminRevoked(String arg0) {
			// TODO Auto-generated method stub
			
		}

		public void banned(String arg0, String arg1, String arg2) {
			// TODO Auto-generated method stub
			
		}

		public void joined(String user) {
			// Kevin is there a better function we can use from the StringUtil ?
			System.out.println("Who joined in the room: " +  delimitUserBack(user));
			users.add(user);
			countUsers++;
			//int roomCount = multiUserChat.getOccupantsCount();
			System.out.println("Count: " + countUsers);
			
		}

		public void kicked(String arg0, String arg1, String arg2) {
			// TODO Auto-generated method stub
			
		}

		public void left(String user) {
			// Kevin is there a better function we can use from the StringUtil ?
			System.out.println("Who left in the room: " + delimitUserBack(user));
			users.remove(user);
			countUsers--;
			//int roomCount = multiUserChat.getOccupantsCount();
			System.out.println("Count: " + countUsers);
			
		}

		public void membershipGranted(String arg0) {
			// TODO Auto-generated method stub
			
		}

		public void membershipRevoked(String arg0) {
			// TODO Auto-generated method stub
			
		}

		public void moderatorGranted(String arg0) {
			// TODO Auto-generated method stub
			
		}

		public void moderatorRevoked(String arg0) {
			// TODO Auto-generated method stub
			
		}

		public void nicknameChanged(String arg0, String arg1) {
			// TODO Auto-generated method stub
			
		}

		public void ownershipGranted(String arg0) {
			// TODO Auto-generated method stub
			
		}

		public void ownershipRevoked(String arg0) {
			// TODO Auto-generated method stub
			
		}

		public void voiceGranted(String arg0) {
			// TODO Auto-generated method stub
			
		}

		public void voiceRevoked(String arg0) {
			// TODO Auto-generated method stub
			
		}
    	
    	
    	
    }
	
	 private String delimitUserBack(String from) {
	        String subString = from.substring(from.indexOf('/') + 1);

	        return subString;
	    }
	



}

