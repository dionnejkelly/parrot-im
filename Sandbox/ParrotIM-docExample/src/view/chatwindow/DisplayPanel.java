package view.chatwindow;
import java.awt.*;
import java.util.*;
import model.*;
import model.dataType.UpdatedType;

import javax.swing.*;
import javax.swing.text.html.HTMLEditorKit;

public class DisplayPanel extends JPanel implements Observer {
	private JEditorPane txtPane;
	private String previousText;
	private Model model;
	
	public DisplayPanel(Model model) {
	    this.model = model;
	    this.model.addObserver(this);
		
		setLayout(new BorderLayout());
		
		txtPane = new JEditorPane();
		txtPane.setPreferredSize(new Dimension(250, 300));
		txtPane.setEditable(false);
		txtPane.setEditorKit(new HTMLEditorKit());
	        if (model.getActiveConversation() != null) {
		    txtPane.setText(model.getActiveConversation().displayMessages());
		} else {
		    txtPane.setText("");
		}
	        
		
		JLabel title = new JLabel("Conversation 1                           view: ");
		
		JButton fullView = new JButton("Full");
		JButton simpleView = new JButton("Simple");
		
		JToolBar bar1 = new JToolBar();
		bar1.setFloatable(false);
		bar1.add(title);
		bar1.add(fullView);
		bar1.add(simpleView);
		
		JToolBar bar2 = new JToolBar();
		bar2.setFloatable(false);
		JLabel isTyping = new JLabel("Ready...");
		bar2.add(isTyping);
		
		//add to panel
		add(bar1, BorderLayout.NORTH);
		add(txtPane, BorderLayout.CENTER);
		add(bar2, BorderLayout.SOUTH);
	}

    public void update(Observable o, Object arg) {
	/* May want to update this to update line-per-line */
        if (arg == UpdatedType.CHAT || arg == UpdatedType.ALL || arg == UpdatedType.CHAT_AND_BUDDY) {
        	txtPane.setText(model.getActiveConversation().displayMessages());
        }
    	return;	
    }
    
 /* Superseeded by model classes
    public void addMessage(String userName,String text, String font, String size){
	String user = userName;
        if(text.length() > 0){
            previousText = previousText + "<U>" + user + ":</U> " 
                + "<font face=\"" + font + "\" size=\"" + size + "\">" 
                + text + "</font><br><br>";
            txtPane.setText(previousText);
	}
    }
 */
}