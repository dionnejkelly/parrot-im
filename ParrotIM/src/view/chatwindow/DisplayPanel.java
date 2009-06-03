package view.chatwindow;
import java.awt.*;
import javax.swing.*;
import javax.swing.text.html.HTMLEditorKit;

public class DisplayPanel extends JPanel {
	private JEditorPane txtPane;
	private String previousText;
	
	public DisplayPanel() {
		setLayout(new BorderLayout());
		
		txtPane = new JEditorPane();
		txtPane.setPreferredSize(new Dimension(250, 300));
		txtPane.setEditable(false);
		txtPane.setEditorKit(new HTMLEditorKit());
		previousText = "<font face=\"Ariel\">" +
				"<U>PersonA:</U>   How is it going?<br><br>" +
				"<U>PersonB:</U><font color=\"blue\"><b>   Good, you?</b></font><br><br>" +
				"<U>PersonA:</U>   not bad, lol...<br><br>";
		txtPane.setText(previousText);
		
		JLabel title = new JLabel("Conversation 1 (2 friends)                           view: ");
		
		JButton fullView = new JButton("Full");
		JButton simpleView = new JButton("Simple");
		
		JToolBar bar1 = new JToolBar();
		bar1.setFloatable(false);
		bar1.add(title);
		bar1.add(fullView);
		bar1.add(simpleView);
		
		JToolBar bar2 = new JToolBar();
		bar2.setFloatable(false);
		JLabel isTyping = new JLabel("PersonA isTyping...");
		bar2.add(isTyping);
		
		//add to panel
		add(bar1, BorderLayout.NORTH);
		add(txtPane, BorderLayout.CENTER);
		add(bar2, BorderLayout.SOUTH);
	}
	
	public void addMessage(String text){
		if(text.length() > 0){
			previousText = previousText + "<U>PersonA:</U> " + text + "<br><br>";
			txtPane.setText(previousText);
		}
	}
}