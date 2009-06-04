package chatwindow;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

public class sidePanel extends JPanel {
	private JPanel chattingWith;
	private Box boxes[] = new Box[1];
	
	public sidePanel(ArrayList<Conversation> conversations) {
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(120, 500));
		
		chattingWith = new JPanel();
		chattingWith.setBackground(Color.WHITE);
		chattingWith.setLayout(new BorderLayout());
		
		//conversations.add(new Conversation());
		//conversations.get(0).addName("You");
		
		boxes[0] = Box.createVerticalBox();
		//for(int i = 0; i < conversations.get(0).getSize(); i++){
			boxes[0].add(new JLabel("You"));
		//}
		
		//for(int i=0; i < boxes[0].getComponentCount(); i++){
			
		//}
		
		chattingWith.add(boxes[0], BorderLayout.NORTH);
			
		//add to panel
		add(chattingWith, BorderLayout.CENTER);
	}
}