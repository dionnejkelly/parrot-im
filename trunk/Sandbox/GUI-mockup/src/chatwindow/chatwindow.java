package chatwindow;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class chatwindow extends JFrame{
	
	public chatwindow()
	{
		super("chatWindow Mockup");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setIconImage(new ImageIcon(getcwd() + "/src/mainwindow/logo.png").getImage());
		
		getContentPane().add(new mainPanel());
		
		pack();
		setVisible(true);
	}
	
	public void addNewConversation(String title, String[] users){
		
	}
	
	public static String getcwd() { 
		String cwd = System.getProperty("user.dir"); 
		return cwd; 
	}
}
