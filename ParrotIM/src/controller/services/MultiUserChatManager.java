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
	}
	
	public void createRoom() throws XMPPException {
		super.create(roomName);
		super.sendConfigurationForm(new Form(Form.TYPE_SUBMIT));
	}
	
	public void inviteFriend(String userID) throws XMPPException {
		super.invite(userID, "Let's have fun");
	}
	
	public void leaveRoom() {
		super.leave();
	}
	
	



}

