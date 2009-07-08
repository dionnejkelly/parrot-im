/* avatarLabel.java
 * 
 * Programmed By:
 *     Vera Lukman
 *     Kevin Fahy
 *     
 * Change Log:
 *     2009-June-23, VL
 *         Initial write. Click on the display picture to access file chooser.
 *     2009-June-24, KF
 *         Naming convention updates. Changed all class names.
 *     2009-July-3, VL
 *         Fixed avatar display. Now able to resize.
 *         
 * Known Issues:
 *     not able to resize huge picture (want to resize it to 100x100)
 * 
 * Copyright (C) 2009  Pirate Captains
 * 
 * Full license can be found in ParrotIM/LICENSE.txt.
 */

package view.styles;

import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import javax.swing.filechooser.FileFilter;

import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.util.Base64;
import org.jivesoftware.smack.util.StringUtils;

import controller.MainController;


/**
 * This object sets the style of the avatar image display on buddylist window.
 * 
 * It inherits JLabel methods and variables.
 */
public class AvatarLabel extends JLabel{
	/** avatarlbl is this component itself */
	private AvatarLabel avatarlbl;
	
	private MainController chatClient;
	/**
	 * AvatarLabel constructor. It takes a String that describes
	 * the path of the display picture as its argument.
	 * 
	 * @param url
	 * @param mainControl
	 * @param changeable
	 */
	public AvatarLabel(MainController mainControl,URL url){
		this.setToolTipText("Click to change your display picture");
		this.chatClient = mainControl;
		avatarlbl = this;
		changeAvatar(url.toString());
		this.addMouseListener(new avatarMouseListener());
	}
	
	
	public void changeAvatarWindow() throws MalformedURLException{
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
            avatarlbl.changeAvatar(file.toURL().toString());
            if (file != null)
            	System.out.println(file.getAbsolutePath()+ " is choosen");
            
           
            System.out.println("Setting avatar...");
//            Image image = getToolkit().getImage(fileChooser.getSelectedFile().getName());
//            JOptionPane.showMessageDialog(null, image);
            //try {
            	File testFile = new File("C:\\Documents and Settings\\HP_Administrator\\My Documents\\My Pictures\\Plane.jpg");
//				BufferedImage img = ImageIO.read(testFile);
//				ByteArrayOutputStream bas = new ByteArrayOutputStream();
//				ImageIO.write(img, "pnm", bas);
//				byte[] data = bas.toByteArray();
            	//ImageIcon imageIcon = new ImageIcon("C:\\Documents and Settings\\HP_Administrator\\My Documents\\My Pictures\\Plane.jpg");
            	//String imageData = imageIcon.getImage().toString();
            	//JOptionPane.showMessageDialog(null, imageIcon);
            	//String imageData = file.toURL().toString();
          
    
				try {
					
//					InputStream stream = new FileInputStream(testFile);
//			    	String str = stream.toString();
//			    	byte[] test = StringUtils.decodeBase64(getAvatarEnconded());
			    	
			    	//ImageIcon icon = new ImageIcon(test);
			    	//JOptionPane.showMessageDialog(null, icon);
					chatClient.setAvatarPicture(file);
					ImageIcon icon = chatClient.getAvatarPicture("parrotim.test@gmail.com");
					JOptionPane.showMessageDialog(null, icon);
					
				} catch (XMPPException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("Succesfully uploaded the avatar picture.");

			
			
        	fileChooser.setVisible(false);//DISPOSE!!!
        	
        	
        } else {
        	System.out.println("Attachment cancelled by user.");
        	fileChooser.setVisible(false);//DISPOSE!!!
        }

        //Reset the file chooser for the next time it's shown.
        fileChooser.setSelectedFile(null);
	}
	
	private String getAvatarEnconded() {
        return "/9j/4AAQSkZJRgABAQEASABIAAD/4QAWRXhpZgAATU0AKgAAAAgAAAAAAAD/2wBDAAUDBAQEAwUE\n" +
                "BAQFBQUGBwwIBwcHBw8LCwkMEQ8SEhEPERETFhwXExQaFRERGCEYGh0dHx8fExciJCIeJBweHx7/\n" +
                "2wBDAQUFBQcGBw4ICA4eFBEUHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4e\n" +
                "Hh4eHh4eHh4eHh7/wAARCABQAFADASIAAhEBAxEB/8QAHAAAAgIDAQEAAAAAAAAAAAAABwgFBgID\n" +
                "BAkB/8QAORAAAgEDAwIDBwIDBwUAAAAAAQIDBAURAAYSITEHE0EIFBUiMlFxYbEjUqEkQoGR0eHw\n" +
                "M0NicsH/xAAZAQADAQEBAAAAAAAAAAAAAAACAwQBAAX/xAAgEQACAgMAAwADAAAAAAAAAAAAAQIR\n" +
                "AxIhBBMxMmGR/9oADAMBAAIRAxEAPwDOor6ir6RqwhH0hfX9fx++t1FbGmYRUyEg4A6k5Ot9staw\n" +
                "ny4FP8R+RDNkE9s6s1TR2yzW0190QVGOiq/0k/bj21Ko2/0Miv6bKSOKyW1aeAqzjq5B+pvXXKdy\n" +
                "BRyYkYOqVd9xw1crSQWiCKnXIXCDl/nj9tUu80016u8dPPdKyC3ypzMMT4ZmGAUz9hkHJz3xqlTa\n" +
                "4ilRk/oYJd8WunJjlr6NJT2RplB/fWUO7AwBDhhjIIPTVSsXhltF6FXlslLKGHzNLlmb9e+uC8bC\n" +
                "t9muNHJa2qKeJ5eJhErFGABbA69Ppx+M6KUnR3Y/UFa17pilK8I5JSTjIIA/rqJ3TYWeve8UlH5a\n" +
                "VKjzgGGCw7N+cd/wNDykNdBKI5KgD5sjI6aJW3qyueDyJI/MjIwSDlW/00vdPjMyRlVFMqoOMhjZ\n" +
                "WR/5WGD/AIffUVUUoZ8EaIlDQJXVr0VTGfLlbA/8WJ6ah9zbdms1XGkh5JMnJGx9uhB/UHQShy0T\n" +
                "X2iatSxSX96RXTIYRL64Oev761+L7UduTlc3ZII8BEHdjj0GrPZbRTVV5MskKJ5vE5Ax17Hr/wA9\n" +
                "NUv2p57BtHbluul4q55qjzpFo7fM4Z6h1CgovqEGQWbOACO5KqdriDxy1fQSVO8DXF4LfZ3SmQdW\n" +
                "diCfX0H21Xqu+Ri726oWadY3ZgyDDBBhcgEfc4z+NBi7XGqula9VVPlmJIUdFQfZR6D/AIdc8Ukk\n" +
                "MqSxO0ciMGR1OCpHYg+h0aib7h69rCoa2RK7FSVGVHpqq+KNS1NV2aGeOsZ0qTxkhcqEVhxYnH5H\n" +
                "X0xoXeDfjlNZsWnejz1dGSiwV0cYaSEDCkSAYLrj5uXV8g/VkYZyJbRfrRDdqCWiudG2QskTpLFK\n" +
                "uSGAIJBwQR+Rps6cEGpbWAzdFpv07T8I63hEAIwwPXPc4Hr+dTnh8246CzPdUmm8mneNJ6eo+vkx\n" +
                "IIH3HTP40cK+009SvvMYCiTv9gfXX21USUswWWKCcN0yy9QNI1oZJ7dIinSasus7UsL8iiuxxhQD\n" +
                "+v37nXd4g2mtjstFVVlQ0s5qWV1KBRllznH7/jVlsdsaTckwY8YXwf0C46n/AC1xeLknvtdQW2PJ\n" +
                "bLSOq+nLB/Yf10VtRaJH+RYLrZaSyxz1k9XFT0VPG0ss8zBI4kUFmLMegUKCST0AGvNvxs35W+JH\n" +
                "iRdN0VUk3u8r+TQRSEjyaZOka8eTBSR8zBTjm7kd9Nr7fPiDd7LsW0bZs881Ku4pJxWzxS8S1PEq\n" +
                "coCMZw5mXJBHRCpyHI0i2iquAXfSV2rYLnuW8xWq1QiSaTqzMcJEg7u59FGf2AySASJv3wVu1ktE\n" +
                "V0sM816jBVJ6dIP46HAHNVBPJS2eg6qCPqALC5+DO2327sVLpMh9+uwWpIDdocfwh0JByCWz0Pz4\n" +
                "PbRXscVQLYWqj8zDOMems7ZbHxl69m+iOa6fiFf8L+Fe/VPw/wA/3j3XzW8nzePHzOGccuPTljOO\n" +
                "mmO8TPDSy7qc1dseC1Xnk7M6wgRVGcn+IB2bkf8AqDJwTkN0wud5oJrVd622VDxvNR1EkEjRklSy\n" +
                "MVJGQDjI+w0TVE08cofQneylfrlafF2gt9NXSQ2+5RzR11PnMc4SGR05A+oYDBHUZIzhiC5lPV07\n" +
                "SBlmHQ9j/rpV/ZB2tSXw7pu3u6SXS1rS+5yN1KLJ53mADsCQijPfGR2Jywe3qoeeUcYcdMY7aXKT\n" +
                "TLfGxp47YSTc/crcayni8xuisxOPxqFo6ee43ISVEhWpq34tIf8Atqx/c6kaFTLZ5CygoHQnp07j\n" +
                "UxV0kFPNNIsfFoqlXBX8jQyl0kyJKXBS/boqZrpZtk3CKCY00T1sckvA8UZxAUUnsCQjED14t9jp\n" +
                "W9ej1bbrbuKxVtnvlFFWUFbmOaGQfKQT0P3BBAIIwQQCCCAdKn4kezjuayxz3Pacvx+2qSwp8BKy\n" +
                "NfmOOPaXACjK4ZmPRNV5MTXUIj8Iza/jfclaODdlL8QiUn+1UyKk3949U6I390dOOAM/MdT27vaF\n" +
                "5U4ptq2Tjzw0k9xHUd8qqI3/AKnkW+44+ugPV01RR1c1JVwS09RBI0csUqFXjdTgqwPUEEEEHWrS\n" +
                "KH+/JVWXCbxM3nJVvULdhGWYkKtPGVUfYZUnA/Uk6gNxXu5bguJuN2mjnqigRpFgSMsB25cAMnHT\n" +
                "J64AHYDVs234Q75vfkyfDIrbTy8szXCdYfLxn6kyZBkjA+X1B7ddWOP2e94StxhvO25TnrwqJiF/\n" +
                "J8rWnOOWa7ZXtgeMO/djW2ntW3rnSwW2Kfz3pGoICs7Egt5j8PMbIAXPLkFAAIwMNB4d7xsW/bdS\n" +
                "3iyAwVYZYq+hZ8yUrkdc/wAynB4t2IB7EMoTbeG3rjtXctbt+6iL3ujcK5ifmjggMrKfsVIIyAev\n" +
                "UA5GurZ28dwbRW5fAK+Sje40vu0siMQyDkDzTrgSABlDd1DtjBIIySs7HkeN9HFvftPeGFjWp2/D\n" +
                "T326SU8oV6yhghemkYYzwZpVLAHI5YwcZBIIJLuyN5WDxB2jJubbVX59FUModJFCy08gC8opFyeL\n" +
                "rkZGSCCCCVIJ8vdO97EsZtfgZWS148lbjeZZ6Y8gecYSKItgHp88bjBwemexBIuKF3bCZMDTgggg\n" +
                "GZSNStuhLRlyAAGP9P8AfOoKW6Udbeqe38i0kANQwHoFHrq0WpG9yp+fdkBb8nrr1GhexDbk2zaN\n" +
                "x0vul8tlHcaZG8xI6qBZVVwCOYDAjOCRn9Toe1GwNsWyqBpduWihqkBaKogoo43AIwcMoBHQkaNP\n" +
                "lgxYx6ai9xWb4lQfwQBURLyjP3HqupM2NfUPwZNWAi4WmvimKxvLxB6FW1O7XpK1VXzeROe7tqSq\n" +
                "/PilaGWNkkU4ZWHUayo5nV8Fv8MakU2uHr+1uIvHtW+Hl5oNy1G+6fFZaK4RLO0a/NRyKixgOP5W\n" +
                "4jD9snicHiWBGvTnaFtnnmSeZCsQIKgj6v8AbV5jlDS1AXsqBRqqGJyVs8bM0pcEL9mz2e7pvivi\n" +
                "3BvCirLZteMLLDHKjRS3QlQyiPsRCQQTIO4PFDnLI9NBZKKgpaCjtdPDR0YaPhBGgRI1UfKiqOgA\n" +
                "CgADtrKoqPLpKaXPVXUdPtnXTNUBLlTQR4xHlj+gHT/7pjw8oTsf/9k=";
    }

	
	

	/**
	 * Sets the AvatarLabel to a new image. It takes a String that describes
	 * the path of the display picture as its argument.
	 */
	private void changeAvatar(String path){
		this.setText("<html><img src=\""+ path +"\" height=\"100\" width=\"100\" ></html>");
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
			try {
				changeAvatarWindow();
			} catch (MalformedURLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	/** This class controls the file types that can be selected for the file browser. 
	 * It can only select either a directory or an image file. */
	protected class ImageFileFilter extends FileFilter{

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
