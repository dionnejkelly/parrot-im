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

import javax.swing.JPanel;
/**
 * Sets the GUI component of Help pop up window.
 * 
 * This class inherits JPanel methods and variables.
 */
public class HelpPanel extends JPanel{
	
	/** SignInPanel constructor. It sets up the panel. */
	public HelpPanel(String url) {
		
		 String browserPath = "C:/Program Files/Internet Explorer/IEXPLORE.EXE"; //Use your browser path 
       
         try { 
        	 
             String[] b = {browserPath, url}; 
             Runtime.getRuntime().exec(b); 
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
	
	
//	public static String getcwd() { 
//	    String cwd = System.getProperty("user.dir"); 
//	    return cwd; 
//	}
}

//	class ActivatedHyperlinkListener implements HyperlinkListener {
//		
//		JEditorPane editorPane;
//
//		public ActivatedHyperlinkListener(JEditorPane editorPane) {
//			this.editorPane = editorPane;
//		}
//
//		public void hyperlinkUpdate(HyperlinkEvent hyperlinkEvent) {
//			HyperlinkEvent.EventType type = hyperlinkEvent.getEventType();
//			final URL url = hyperlinkEvent.getURL();
//			if (type == HyperlinkEvent.EventType.ENTERED) {
//				System.out.println("URL: " + url);
//			} else if (type == HyperlinkEvent.EventType.ACTIVATED) {
//				System.out.println("Activated");
//				Document doc = editorPane.getDocument();
//				try {
//					editorPane.setPage(url);
//				} catch (IOException ioException) {
//					System.out.println("Error following link");
//					editorPane.setDocument(doc);
//				}
//			}
//	}
//}
