package view.chatLog;
import java.awt.Dimension;

import javax.swing.JFrame;

import model.Model;



public class chatLogFrame extends JFrame {
	
	public chatLogFrame(Model model, String username){
		super("ParrotIM - Chat Log Viewer");
		
		this.getContentPane().add(new chatLogPanel(model, username));
		this.setPreferredSize(new Dimension (700,500));
		this.setMinimumSize(new Dimension(400,300));
		this.pack();
		this.setVisible(true); // might want to change this
	}
}
