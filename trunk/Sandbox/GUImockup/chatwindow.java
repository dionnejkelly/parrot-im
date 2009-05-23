import javax.swing.*;

public class chatwindow {
	   public static void main(String[] args){
		   JFrame frame = new JFrame("ChatWindow Mockup");
		   frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		   
		   frame.getContentPane().add(new mainPanel());
		   
		   frame.pack();
		   frame.setVisible(true);
	   }
}
