package view.styles;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CustomListPane extends JPanel{
	private Box boxes[] = new Box[1];
	private ImageIcon defaultIcon = new ImageIcon(this.getClass().getResource(
				"/images/chatwindow/personal.png"));
	
	public CustomListPane() {
		setBackground(Color.WHITE);
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(200, 200));
		
		boxes[0] = Box.createVerticalBox();
		
		add(boxes[0], BorderLayout.NORTH);
	}
	
	private JPanel friendPanel(String nickname, ImageIcon img){
		JPanel friendPanel = new JPanel();
		friendPanel.setLayout(new BorderLayout());
		friendPanel.setBackground(Color.WHITE);
		
		friendPanel.add(new JLabel(img), BorderLayout.WEST);
		friendPanel.add(new JLabel(nickname), BorderLayout.CENTER);
		return friendPanel;
	}
	
	public void addElement(String nickname){
		boxes[0].add(friendPanel(nickname, defaultIcon));
		boxes[0].getComponent(boxes[0].getComponentCount() - 1)
				.addMouseListener(new SelectListener());
	}
	
	public Component getElement(int i){
		return boxes[0].getComponent(i);
	}
	
	public void getElement(String nickname){
		
	}
	
	public void removeAllElements(){
		boxes[0].removeAll();
	}
	
    private class SelectListener implements MouseListener {
    	int lastSelected = 0;
    	
        /**
         * highlights clicked element
         */
        public void mouseClicked(MouseEvent event) {
           
        }

        /**
         * change background to color when mouse Entered
         */
        public void mouseEntered(MouseEvent event) {
        	for (int i = 0; i < boxes[0].getComponentCount(); i++) {
                if (event.getSource().equals(boxes[0].getComponent(i))) {
                    boxes[0].getComponent(i).setBackground(
                            new Color(225, 247, 247));
                    lastSelected = i;
                }
            }
        }

        /**
         * change background to white when mouse Exited
         */
        public void mouseExited(MouseEvent event) {
        	boxes[0].getComponent(lastSelected).setBackground(
                    Color.WHITE);
        }

        /**
         * Unused methods 
         */
        public void mousePressed(MouseEvent e) {}
        public void mouseReleased(MouseEvent e) {}
    }
}
