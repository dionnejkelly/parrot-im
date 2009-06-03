package testPackage.chatwindow;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;

import testPackage.Model;

public class chatwindow extends JFrame{
	
	public mainPanel mainPanel;
	
	public chatwindow(Model model)
	{
		super("chatWindow Mockup");
		mainPanel = new mainPanel(model);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setIconImage(new ImageIcon(getcwd() + "/src/mainwindow/logo.png").getImage());
		
		getContentPane().add(mainPanel);
		
		pack();
		setVisible(true);
	}
	
	public void submitMessage(String newText)
	{
	   mainPanel.submitMessage(newText);
	   return;
	}
	
	public JButton gsb()
	{
	   return mainPanel.gsb();	
	}
	
	public void addNewConversation(String title, String[] users){
		
	}
	
	public static String getcwd() { 
		String cwd = System.getProperty("user.dir"); 
		return cwd; 
	}
}
