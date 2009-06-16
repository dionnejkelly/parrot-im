package view.chatwindow;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import controller.services.Xmpp;

import java.util.Observable;
import java.util.Observer;

import view.styles.chatWindowListener;

import model.Model;
import model.dataType.UpdatedType;

public class chatwindow extends JFrame implements Observer {
	//private ArrayList<Conversation> conversations;
	public JPanel main;
	private Model model;
	private boolean windowIsOpen;
		
	public chatwindow(Xmpp c, Model model)
	{
	    super("chatWindow Mockup");
	    model.addObserver(this);
	    
	    this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		
		this.model = model;
		

		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setIconImage(new ImageIcon("images/mainwindow/logo.png").getImage());
		
		this.addWindowListener(new chatWindowListener(this.model));
		
		//main = new mainPanel(conversations, c, model);
		main = new mainPanel(c, model);
		
		getContentPane().add(main);
		
		pack();
		setVisible(true);
	}
	
	public void setWindowIsOpen(boolean isOpen){
		this.windowIsOpen = isOpen;
	}
	
	public boolean getWindowIsOpen(){
		return this.windowIsOpen;
	}

    public void update(Observable o, Object arg) {
        if (arg == UpdatedType.CHAT || arg == UpdatedType.CHAT_AND_BUDDY) {
            if (!this.isVisible()) {
                this.setVisible(true);
            }
        }
        return;
    }
	
	
	
/* Conversation Type is now in the model.	
	public void createNewConversation(ChatData chatData){
		conversations.add(new Conversation());
	    conversations.get(0).addName(chatData.getYourUsername());  // Temporary
	    conversations.get(0).addName(chatData.getOtherUsername());
	}
	
	public void addToConversation(String name){
		conversations.get(0).addName(name);
	}
 */
}
