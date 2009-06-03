package testPackage;

import java.awt.event.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.jivesoftware.smack.XMPPConnection;

public class Controller {
    //... The Controller needs to interact with both the Model and View.
    private Model model;
    private View  view;
    
    private ChatClient chatClient;
    
    //========================================================== constructor
    /** Constructor */
    Controller(Model theModel, View theView) {
        model = theModel;
        view  = theView;
    
        //... Add listeners to the view.
      	view.addSendListener(new SendListener());
    
      	chatClient = new ChatClient(model, view);
		
		try
		{
			chatClient.login("cmpt275testing@gmail.com", "abcdefghi");
		}
		catch (Exception fdsajfaf)
		{
			System.err.println("Oh really? Ya really!");
		}

		model.setTheirName("Kevin");
		

		//c.disconnect();

    }
    
    class SendListener implements ActionListener
    {
       public void actionPerformed(ActionEvent e) 
       {
	  String messageToSend = "";
	  
	  messageToSend = view.getMessageToSend();
	  model.addMessage(0, messageToSend);
	  view.submitMessage(model.getPreviousText()); // Unfinished
      try 
      {
	     chatClient.sendMessage(messageToSend, "yukie.swli@gmail.com");
      }
      catch (Exception eeee)
      {
    	  System.err.println("Don't " +
    	  		"try this at home, kids.");
    	  eeee.printStackTrace();
      }
       }
    }
    
}
