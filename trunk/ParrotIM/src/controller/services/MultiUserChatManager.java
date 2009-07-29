package controller.services;



import java.util.Vector;

import model.Model;
import model.dataType.ChatCollectionData;
import model.dataType.MultiConversationData;
import model.enumerations.UpdatedType;

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
	
	private Model model;
	
	
	public MultiUserChatManager(XMPPConnection connection, String room, Model model) {
		super(connection, room);
		this.xmppConnection = connection;
		this.roomName = room;
		this.model = model;
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
			System.out.println("ADMIN GRANTED!!!");
			
		}

		public void adminRevoked(String arg0) {
			System.out.println("ADMIN REVOKED!!!");
			
		}

		public void banned(String arg0, String arg1, String arg2) {
			// TODO Auto-generated method stub
			
		}

		public void joined(String user) {
		    MultiConversationData conversation = null;
		    int index = 0;

		    // Kevin is there a better function we can use from the StringUtil ? 
			//System.out.println("Who joined in the room: " +  delimitUserBack(user));
		        System.out.println("User before surgery " +  user);
		        user = delimitUserBack(user);
		        System.out.println("User = " + user);
		        
		        
		        if (user.contains("_gmail.com")) {
		        	user = user.replace("_gmail.com", "@gmail.com");
		        }
		        
		        else {
		        	user = user + "@gmail.com";
		        }
//		        index = user.lastIndexOf('_');
//		        user = user.substring(0, index) + "@" + user.substring(index + 1, user.length());
		        
		        System.out.println("Who joined in the room: " +  user);
			users.add(user);
			countUsers++;
			conversation = model.getChatCollection().findByRoomName(delimitRoom(roomName));
			conversation.addUser(model.findUserByUserID(user));
			//int roomCount = multiUserChat.getOccupantsCount();
			model.getChatCollection().forceUpdate();
			System.out.println("Count: " + countUsers);
			
		}

		public void kicked(String arg0, String arg1, String arg2) {
			// TODO Auto-generated method stub
			
		}

		public void left(String user) {
		    MultiConversationData conversation = null;
		    int index = 0;
			// Kevin is there a better function we can use from the StringUtil ?
			System.out.println("Who left in the room: " + delimitUserBack(user));
		    user = delimitUserBack(user);
//                    index = user.lastIndexOf('_');
//                    user = user.substring(0, index) + "@" + user.substring(index + 1, user.length());		    
                  
                    
                    if (user.contains("_gmail.com")) {
    		        	user = user.replace("_gmail.com", "@gmail.com");
    		        }
    		        
    		        else {
    		        	user = user + "@gmail.com";
    		        }
                    
            System.out.println("Who left the room: " +  user);     
			users.remove(user);
			countUsers--;
			conversation = model.getChatCollection().findByRoomName(delimitRoom(roomName));
			conversation.removeUser(conversation.findUserByUserID(user));
			//int roomCount = multiUserChat.getOccupantsCount();
			model.getChatCollection().forceUpdate();
			System.out.println("Count: " + countUsers);
			
		}

		public void membershipGranted(String arg0) {
			System.out.println("MEMBERSHIP GRANTED!!!");
			
		}

		public void membershipRevoked(String arg0) {
			System.out.println("MEMBERSHIP GRANTED!!!");
			
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
	 
	 private String delimitRoom(String from) {
             String subString = from.substring(0, from.lastIndexOf('@'));

             return subString;
         }
	



}

