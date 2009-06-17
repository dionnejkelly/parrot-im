package chatLog;
import java.awt.Dimension;

import javax.swing.JFrame;

import chatLogModel.modeldummy;


public class chatLogFrame extends JFrame {
	
	public chatLogFrame(modeldummy model){
		super("ParrotIM - Chat Log Viewer");
		
		this.getContentPane().add(new chatLogPanel(model));
		this.setPreferredSize(new Dimension (700,500));
		this.pack();
		this.setVisible(true); // might want to change this
	}
	

}
