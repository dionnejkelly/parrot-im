/* avatarLabel.java
 * 
 * Programmed By:
 *     Vera Lukman
 *     
 * Change Log:
 *     2009-June-23, VL
 *         Initial write. Initial write. Click on the 
 *         display picture to access file chooser.
 *         
 * Known Issues:
 *     None
 * 
 * Copyright (C) 2009  Pirate Captains
 * 
 * Full license can be found in ParrotIM/LICENSE.txt.
 */

package view.styles;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;

import javax.swing.filechooser.FileFilter;


public class avatarLabel extends JLabel{
	protected ImageIcon avatar;
	protected avatarLabel avatarlbl;
	
	public avatarLabel(String defaultImage){
		avatarlbl = this;
		avatar = new ImageIcon(defaultImage); // want to get this from model later
		this.setIcon(avatar);
		this.addMouseListener(new avatarMouseListener());
	}
	
	public void changeAvatar(String path){
		avatar = new ImageIcon (path);
		this.setIcon(avatar);
	}

	private class avatarMouseListener implements MouseListener{

		public void mouseClicked(MouseEvent e) {}
		public void mouseEntered(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}
		public void mousePressed(MouseEvent e) {}
		public void mouseReleased(MouseEvent e) {
			JFrame fileChooserPopup = new JFrame("Change Display Picture");
			fileChooserPopup.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			
			System.out.println("clicked");
			
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setAcceptAllFileFilterUsed(false);
			ImageFileFilter filefilter = new ImageFileFilter();
			fileChooser.setFileFilter(filefilter);
			fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			fileChooser.setMultiSelectionEnabled(false);
			fileChooser.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
			
			fileChooserPopup.getContentPane().add(fileChooser);
			fileChooserPopup.pack();
			fileChooserPopup.setVisible(true);
			
			//Show it.
	        int returnVal = fileChooser.showOpenDialog(fileChooserPopup);

	        //Process the results.
	        if (returnVal == JFileChooser.APPROVE_OPTION) {
	            File file = fileChooser.getSelectedFile();
	            avatarlbl.changeAvatar(file.getAbsolutePath());
	            System.out.println(file.getAbsolutePath()+ " is choosen");
	            fileChooserPopup.dispose();
	        	fileChooser.setVisible(false);//DISPOSE!!!
	        } else {
	        	System.out.println("Attachment cancelled by user.");
	        	fileChooserPopup.dispose();
	        	fileChooser.setVisible(false);//DISPOSE!!!
	        }

	        //Reset the file chooser for the next time it's shown.
	        fileChooser.setSelectedFile(null);
		}
	}
	private class ImageFileFilter extends FileFilter{

		@Override
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
		public String getDescription() {
			return "choose image file only";
		}
		
	}
}
