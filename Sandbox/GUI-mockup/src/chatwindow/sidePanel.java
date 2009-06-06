package chatwindow;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.*;
import model.Model;
import ChatClient.ChatClient;
import java.util.*;

public class sidePanel extends JPanel implements Observer {
	private JPanel chattingWith;
	private Box boxes[] = new Box[1];
	private ArrayList<Conversation> conversations;
	private Model model;
	
	public sidePanel(ArrayList<Conversation> conversations, ChatClient c, Model model) {
	    this.model = model;
		
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(150, 500));
		setBackground(Color.WHITE);
		setBorder(BorderFactory.createEmptyBorder(5,10,5,10));
		
		this.conversations = conversations;
		
		chattingWith = new JPanel();
		chattingWith.setBackground(Color.WHITE);
		chattingWith.setLayout(new BorderLayout());
		
		boxes[0] = Box.createVerticalBox();
		for(int i = 0; i < conversations.get(0).getSize(); i++){
			JPanel namePanel = new JPanel();
			namePanel.setLayout(new BorderLayout());
			namePanel.setBackground(Color.WHITE);
			namePanel.add(new JLabel(conversations.get(0).getName(i)), BorderLayout.WEST);
			boxes[0].add(namePanel);
		}
		
		for(int i=0; i < boxes[0].getComponentCount(); i++){
			boxes[0].getComponent(i).addMouseListener(new SelectListener());
		}
		
		chattingWith.add(boxes[0], BorderLayout.NORTH);
			
		//add to panel
		add(chattingWith, BorderLayout.CENTER);
	}
	
	public void update(Observable t, Object o) {
	    // Add code here to change the namePanels
		return;	
	}
	
	private class SelectListener implements MouseListener{
		public void mouseEntered(MouseEvent event) {
			for(int i=0; i < boxes[0].getComponentCount(); i++){
				if(event.getSource().equals(boxes[0].getComponent(i))){
					boxes[0].getComponent(i).setBackground(new Color(225, 247, 247));
				}
			}
		}
		public void mouseExited(MouseEvent event) {
			for(int i=0; i < boxes[0].getComponentCount(); i++){
				if(event.getSource().equals(boxes[0].getComponent(i))){
					boxes[0].getComponent(i).setBackground(Color.WHITE);
				}
			}
		}
		public void mouseClicked(MouseEvent event){	}
		public void mousePressed(MouseEvent e) {}
		public void mouseReleased(MouseEvent e) {}
	}
}