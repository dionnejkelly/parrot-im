import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class chatwindow {
	
	   public static void main (String[] args){
		   JFrame frame = new JFrame("ChatWindow Mockup");
		   frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		   frame.setIconImage(new ImageIcon(getcwd() + "/src/mainwindow/logo.png").getImage());
		   
		   frame.getContentPane().add(new mainPanel());
		   
		   frame.pack();
		   frame.setVisible(true);
	   }
	   
	   public static String getcwd() { 
		    String cwd = System.getProperty("user.dir"); 
		    return cwd; 
		}
}
