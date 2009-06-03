package testPackage;

import java.util.*;
import java.io.*;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;


public class ChatClient implements MessageListener
{
	private XMPPConnection connection;
	private Model model;
	private View view;
	
	public ChatClient(Model theModel, View theView)
	{
		model = theModel;
		view = theView;
	}
	
	public void login(String userName, String password) throws XMPPException
	{
		ConnectionConfiguration config = new ConnectionConfiguration("talk.google.com", 5222, "gmail.com");
		connection = new XMPPConnection(config);

		connection.connect();
		connection.login(userName, password);
	}
	
	public void sendMessage(String message, String to) throws XMPPException
	{
		Chat chat = connection.getChatManager().createChat(to, this);
		chat.sendMessage(message);
	}
	
	public void displayBuddyList()
	{
		Roster roster = connection.getRoster();
		Collection<RosterEntry> entries = roster.getEntries();
		
		System.out.println("\n\n" + entries.size() + " buddy(ies):");
		for(RosterEntry r:entries)
		{
			System.out.println(r.getUser());
		}
	}

	public void disconnect()
	{
		connection.disconnect();
	}
	
	public void processMessage(Chat chat, Message message) 
	{
		String msg = "";
		if(message.getType() == Message.Type.chat)
		{
		   msg = message.getBody();	
			
		   model.addMessage(1, msg);
		   view.submitMessage(model.getPreviousText()); // Unfinished
		}

	}

}