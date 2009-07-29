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

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileFilter;

import model.Model;
import model.enumerations.ServerType;

import org.jivesoftware.smack.XMPPException;

import controller.MainController;

/**
 * This object sets the style of the avatar image display on buddylist window.
 * 
 * It inherits JLabel methods and variables.
 */
public class AvatarLabel extends JLabel {
    private Model model;
    private String path;
    private AvatarLabel avatarToSynch;
    private boolean synch;
    private int size;

    /** avatarlbl is this component itself */
    // private static AvatarLabel avatarlbl;
    private MainController chatClient;

    /**
     * AvatarLabel constructor. It takes a String that describes the path of the
     * display picture as its argument.
     * 
     * @param path
     */
    public AvatarLabel(String path, int size) {
        changeAvatar(path);
        this.path = path;
        this.size = size;
    }

    /**
     * AvatarLabel constructor. It takes a String that describes the path of the
     * display picture as its argument.
     * 
     * @param path
     * @param mainControl
     */
    public AvatarLabel(MainController mainControl, Model model, String path,
            int size) {
        synch = false;
        setLabel(mainControl, model, path, size);
    }

    /**
     * AvatarLabel constructor. It takes a String that describes the path of the
     * display picture as its argument. USE THIS CONSTRUCTOR IF: you want to
     * synchronize to other avatarLabel
     * 
     * @param url
     * @param mainControl
     * @param ava
     * @throws MalformedURLException
     */
    public AvatarLabel(MainController mainControl, Model model,
            AvatarLabel ava, int size) {
        super("", SwingConstants.CENTER);
        avatarToSynch = ava;
        synch = true;
        setLabel(mainControl, model, ava.getAvatarPath(), size);
    }

    public String getAvatarPath() {
        return path;
    }

    private void setLabel(MainController mainControl, Model model, String path,
            int size) {
        this.model = model;
        this.setToolTipText("Click to change your display picture");
        this.chatClient = mainControl;
        this.addMouseListener(new avatarMouseListener());
        this.path = path;
        this.size = size;
        changeAvatar(path);
    }

    /**
     * use this if you don't need to synch with anything
     * 
     * @throws MalformedURLException
     */
    @SuppressWarnings("deprecation")
    public void changeAvatarWindow() throws MalformedURLException {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setAcceptAllFileFilterUsed(false);
        ImageFileFilter filefilter = new ImageFileFilter();
        fileChooser.setFileFilter(filefilter);
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setMultiSelectionEnabled(false);
        fileChooser.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Show it.
        int returnVal = fileChooser.showOpenDialog(this);

        // Process the results.
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            String avatarPath = file.toURL().toString();

            this.changeAvatar(avatarPath);

            // synch
            if (synch) {
                System.out.println("synching");
                avatarToSynch.changeAvatar(avatarPath);
            } else {
                System.out.println("not synching");
            }

            try {

                if (!model.getCurrentProfile().isEmptyProfile()) {
                    ServerType serverType =
                            chatClient.getConnection().getServerType();
                    if (serverType == ServerType.GOOGLE_TALK
                            || chatClient.getConnection().getServerType() == ServerType.JABBER) {
                        chatClient.setAvatarPicture(file.toURL());
                    }

                    else if (serverType == ServerType.ICQ) {
                        System.out.println("Calling ICQ");
                        chatClient.setAvatarPicture(file);
                    }

                    else {
                        System.out.println("Calling MSN/Twitter");
                    }
                }

                System.out
                        .println("      FROM THE MAIN CONTROLLER I GOT SAVED IN THE DATABASE!!!");
                model.setAvatarDirectory(model.getCurrentProfile().getName(),
                        avatarPath);

                // will change the avatar once the chatClient done uploading
                System.out.println("Succesfully uploaded the avatar picture.");
            } catch (XMPPException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
            System.out.println("Attachment cancelled by user.");
        }

        fileChooser.setVisible(false);// DISPOSE!!!
        // Reset the file chooser for the next time it's shown.
        fileChooser.setSelectedFile(null);

    }

    /**
     * Sets the AvatarLabel to a new image. It takes a String that describes the
     * path of the display picture as its argument.
     */
    public void changeAvatar(String avatarPath) {

        if (avatarPath == null || avatarPath.length() == 0) {
            avatarPath =
                    getClass().getClassLoader().getResource(
                            "images/buddylist/logoBox.png").toString();
        }

        // URL url = new URL(avatarPath);
        // Image img = new Image();
        // ImageIcon icon = new ImageIcon (img);
        this.setText("<html><img src=\"" + avatarPath + "\" height=\"" + size
                + "\" width=\"" + size + "\" ></html>");
        path = avatarPath;
    }

    /**
     * Sets the behaviour with regard of mouse input and position.
     * 
     * This class inherits MouseListener methods and variables.
     */
    private class avatarMouseListener implements MouseListener {

        public void mouseClicked(MouseEvent e) {
        }

        public void mouseEntered(MouseEvent e) {
        }

        public void mouseExited(MouseEvent e) {
        }

        public void mousePressed(MouseEvent e) {
        }

        /**
         * When the user click on the display picture label, a file chooser
         * window will pop up. It takes a MouseEvent argument
         * 
         * @param e
         */
        public void mouseReleased(MouseEvent e) {
            try {
                changeAvatarWindow();
            } catch (MalformedURLException e1) {
                e1.printStackTrace();
            }

        }
    }

    /**
     * This class controls the file types that can be selected for the file
     * browser. It can only select either a directory or an image file.
     */
    private class ImageFileFilter extends FileFilter {

        @Override
        /*
         * * accept takes a File object argument. If the file is an image file
         * or a directory, then it returns true. It returns false otherwise
         * 
         * @param f
         */
        public boolean accept(File f) {

            if (f.isDirectory()) {
                return true; // if directory, return true
            }

            // now search of image files
            String[] extentionList =
                    new String[] { "jpg", "gif", "png", "bmp" };

            String name = f.getName();
            String extention =
                    name.substring(name.indexOf(".") + 1, name.length());

            for (String element : extentionList) {
                if (extention.compareToIgnoreCase(element) == 0
                        && f.length() <= 524288) {
                    return true;
                }
            }
            return false;
        }

        @Override
        /* * Describes what file types the system will accept. */
        public String getDescription() {
            return "choose image file less than 512 kb";
        }

    }
}
