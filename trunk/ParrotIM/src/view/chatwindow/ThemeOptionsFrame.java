package view.chatwindow;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ThemeOptionsFrame extends JFrame{
	private final int WIDTH = 150;
	public final int HEIGHT = 120;
	
	private JFrame themeFrame;
	
	public ThemeOptionsFrame(){
		
		themeFrame = this;
		
        ImageIcon themeOriginal = new ImageIcon(this.getClass().getResource("/images/chatwindow/pic.png"));
        ImageIcon themeMetal = new ImageIcon(this.getClass().getResource("/images/chatwindow/pic.png"));
        ImageIcon themeWindows = new ImageIcon(this.getClass().getResource("/images/chatwindow/pic.png"));
        ImageIcon themeWindowsClassic = new ImageIcon(this.getClass().getResource("/images/chatwindow/pic.png"));
        
        JLabel metalicMenuItem = new JLabel("Metalic",themeOriginal, JLabel.LEFT);
        metalicMenuItem.addMouseListener(new MenuMouseListener());
        JLabel motifMenuItem = new JLabel("CDE/Motif",themeMetal, JLabel.LEFT);
        motifMenuItem.addMouseListener(new MenuMouseListener());
        JLabel windowsMenuItem = new JLabel("Windows",themeWindows, JLabel.LEFT);
        windowsMenuItem.addMouseListener(new MenuMouseListener());
        JLabel windowsClassicMenuItem = new JLabel("Windows Classic",themeWindowsClassic, JLabel.LEFT);
        windowsClassicMenuItem.addMouseListener(new MenuMouseListener());

        JPanel themeMenuPanel = new JPanel();
        themeMenuPanel.setLayout(new BoxLayout(themeMenuPanel, BoxLayout.Y_AXIS));
        themeMenuPanel.add(metalicMenuItem);
        themeMenuPanel.add(motifMenuItem);
        themeMenuPanel.add(windowsMenuItem);
        themeMenuPanel.add(windowsClassicMenuItem);

        
		this.setUndecorated(true);
		this.setPreferredSize(new Dimension (WIDTH, HEIGHT));
		this.getContentPane().add(themeMenuPanel);
		this.pack();
		this.setVisible(true);
	}
	
	private class MenuMouseListener implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent arg0) {
			// TODO Auto-generated method stub
			themeFrame.dispose();
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			JLabel lbl = (JLabel)arg0.getSource();
			lbl.setBackground(Color.BLUE);
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			JLabel lbl = (JLabel)arg0.getSource();
			lbl.setBackground(null);
		}

		public void mousePressed(MouseEvent arg0) {}
		public void mouseReleased(MouseEvent arg0) {}
		
	}
}
