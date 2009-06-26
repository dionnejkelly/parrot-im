/* avatarLabel.java
 * 
 * Programmed By:
 *     Vera Lukman
 *     
 * Change Log:
 *     2009-June-23, VL
 *         Initial write. Click on the display picture to access file chooser.
 *     2009-June-24, KF
 *         Naming convention updates. Changed all class names.
 *         
 * Known Issues:
 *     not able to resize huge picture (want to resize it to 100x100)
 * 
 * Copyright (C) 2009  Pirate Captains
 * 
 * Full license can be found in ParrotIM/LICENSE.txt.
 */

package view.styles;

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;

import javax.swing.filechooser.FileFilter;


/**
 * This object sets the style of the avatar image display on buddylist window.
 * 
 * It inherits JLabel methods and variables.
 */
public class AvatarLabel extends JLabel{
	/** avatar is an ImageIcon object of the user's display picture */
	protected ImageIcon avatar;
	
	/** avatarlbl is this component itself */
	protected AvatarLabel avatarlbl;
	
	/**
	 * AvatarLabel constructor. It takes a String that describes
	 * the path of the display picture as its argument.
	 * 
	 * @param url
	 */
	public AvatarLabel(URL url){
		avatarlbl = this;
		avatar = new ImageIcon(url); // want to get this from model later
		this.setMaximumSize(new Dimension(100,100));//want to look more into it
		this.setIcon(avatar);
		this.addMouseListener(new avatarMouseListener());
	}
	
	/**
	 * Sets the AvatarLabel to a new image. It takes a String that describes
	 * the path of the display picture as its argument.
	 */
	public void changeAvatar(String path){
		avatar = new ImageIcon (path);
		this.setIcon(avatar);
	}
	
	/**
     * Sets the behaviour with regard of mouse input and position.
     * 
     * This class inherits MouseListener methods and variables.
     */
	private class avatarMouseListener implements MouseListener{

		public void mouseClicked(MouseEvent e) {}
		public void mouseEntered(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}
		public void mousePressed(MouseEvent e) {}
		
		/** When the user click on the display picture label, a file chooser window will pop up.
		 * It takes a MouseEvent argument
		 * 
		 *  @param e*/
		public void mouseReleased(MouseEvent e) {
			
			System.out.println("clicked");
			
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setAcceptAllFileFilterUsed(false);
			ImageFileFilter filefilter = new ImageFileFilter();
			fileChooser.setFileFilter(filefilter);
			fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			fileChooser.setMultiSelectionEnabled(false);
			fileChooser.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
			
			//Show it.
	        int returnVal = fileChooser.showOpenDialog(avatarlbl);

	        //Process the results.
	        if (returnVal == JFileChooser.APPROVE_OPTION) {
	            File file = fileChooser.getSelectedFile();
	            avatarlbl.changeAvatar(file.getAbsolutePath());
	            System.out.println(file.getAbsolutePath()+ " is choosen");
	        	fileChooser.setVisible(false);//DISPOSE!!!
	        } else {
	        	System.out.println("Attachment cancelled by user.");
	        	fileChooser.setVisible(false);//DISPOSE!!!
	        }

	        //Reset the file chooser for the next time it's shown.
	        fileChooser.setSelectedFile(null);
		}
	}
	
	/** This class controls the file types that can be selected for the file browser. 
	 * It can only select either a directory or an image file. */
	private class ImageFileFilter extends FileFilter{

		@Override
		
		/** accept takes a File object argument. If the file is an image file or a directory, then it returns true.
		 * It returns false otherwise 
		 * 
		 * @param f*/
		public boolean accept(File f) {
			
			if (f.isDirectory()) return true; //if directory, return true
			
			//now search of image files
			String[] extentionList = new String[]{"jpg", "gif", "png"};
			String name = f.getName();
			String extention = name.substring(name.indexOf(".")+1, name.length());
			
			for(int pos=0; pos < extentionList.length; pos++){
				if (extention.compareToIgnoreCase(extentionList[pos])==0){
					return true;
				}
			}
			return false;
		}

		@Override
		/** Describes what file types the system will accept. */
		public String getDescription() {
			return "choose image file only";
		}
		
	}
}
