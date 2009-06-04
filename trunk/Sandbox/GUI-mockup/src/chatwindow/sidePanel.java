package chatwindow;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.*;

import ChatClient.ChatClient;

public class sidePanel extends JPanel {
	private JPanel chattingWith;
	private Box boxes[] = new Box[1];
	private ArrayList<Conversation> conversations;
	
	public sidePanel(ArrayList<Conversation> conversations) {
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