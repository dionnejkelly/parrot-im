/* ManageAccountFrame.java
 * 
 * Programmed By:
 *     Vera Lukman
 *     Kevin Fahy
 *     William Chen
 *     
 * Change Log:
 *     2009-May-23, VL
 *         Initial write, stub help window. 
 *     2009-June-13, WC
 *         Transferred file over to new project, ParrotIM.
 *     2009-June-23, KF
 *         Naming convention updates. Changed all class names.
 *         
 * Known Issues:
 *     None
 * 
 * Copyright (C) 2009  Pirate Captains
 * 
 * Full license can be found in ParrotIM/LICENSE.txt.
 */

package view.mainwindow;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.swing.JPanel;
/**
 * Sets the GUI component of Help pop up window.
 * 
 * This class inherits JPanel methods and variables.
 */
public class HelpPanel extends JPanel{
	public static final String GOOGLE_URL = "http://www.google.com/custom?client=pub-3922476703716903&forid=1&ie=ISO-8859-1&oe=ISO-8859-1&cof=GALT%3A%23008000%3BGL%3A1%3BDIV%3A%23336699%3BVLC%3A663399%3BAH%3Acenter%3BBGC%3AFFFFFF%3BLBGC%3A336699%3BALC%3A0000FF%3BLC%3A0000FF%3BT%3A000000%3BGFNT%3A0000FF%3BGIMP%3A0000FF%3BFORID%3A1%3B&hl=en&q=";
	/** SignInPanel constructor. It sets up the panel. */
	public HelpPanel() {
		
	}
	public HelpPanel(String url) {
		
		 String browserPath;
		 String osName = System.getProperty("os.name").toLowerCase();
		 
		 if(osName.contains("win")){
			 browserPath = "C:/Program Files/Internet Explorer/IEXPLORE.EXE " + url; //Use your browser path 
//             String[] b = {browserPath, url};
//             browserPath = b;
		 } else if (osName.contains("mac")){
			 browserPath = "open -a Safari ";
		 } else {
			 System.out.println("Unsupported OS");
			 return;
		 }
		 
		 try { 	  
             Runtime.getRuntime().exec(browserPath+url); 
         } 
         catch (Exception exc) { 
        	 exc.printStackTrace(); 
          } 
//		setLayout(new BorderLayout());
//		add (new JLabel("Help here"));
		
	
//		JFrame frame = new JFrame("Help Menu");
//		
//		frame.setIconImage(new ImageIcon(getcwd() + "/images/mainwindow/logo.png").getImage());
//		try {
//			JEditorPane editorPane = new JEditorPane(url);
//			editorPane.setEditable(false);
//
//			HyperlinkListener hyperlinkListener = new ActivatedHyperlinkListener(editorPane);
//			editorPane.addHyperlinkListener(hyperlinkListener);
//
//			JScrollPane scrollPane = new JScrollPane(editorPane);
//			
//			frame.add(scrollPane);
//		} catch (IOException e) {
//			System.err.println("Unable to load: " + e);
//		}
//
//		frame.setSize(640, 480);
//		frame.setVisible(true);
//		frame.setForeground(Color.WHITE);
	}
	
	public void googlePanel(String word) throws UnsupportedEncodingException {
		 String browserPath;
		 String osName = System.getProperty("os.name").toLowerCase();
		 String url = GOOGLE_URL + word;
		 if(osName.contains("win")){
			 browserPath = "C:/Program Files/Internet Explorer/IEXPLORE.EXE "; //Use your browser path 

		 } 
		 else if (osName.contains("mac")){
			 browserPath = "open -a Safari ";
		 } else {
			 System.out.println("Unsupported OS");
			 return;
		 }
		 
		 try { 	  
             Runtime.getRuntime().exec(browserPath+url); 
         } 
         catch (Exception exc) { 
        	 exc.printStackTrace(); 
          } 
	}
	
	
	
//	public static String getcwd() { 
//	    String cwd = System.getProperty("user.dir"); 
//	    return cwd; 
//	}
}


