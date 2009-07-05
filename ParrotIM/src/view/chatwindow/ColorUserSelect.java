/* ColorUserSelect.java
 * 
 * Programmed By:
 *     Jihoon Choi
 *     
 * Change Log:
 *         
 * Known Issues:
 *     none
 *     
 * Copyright (C) 2009  Pirate Captains
 * 
 * License: GNU General Public License version 2.
 * Full license can be found in ParrotIM/LICENSE.txt.
 */


package view.chatwindow;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


/**
 * The ColorUserSelect contains the panel that holds the integrity of the
 * label and JColorChooser together.
 * 
 * This object inherits from JPanel 
 */


public class ColorUserSelect extends JPanel implements ChangeListener {
	
	
	/** A chooser GUI.*/
	
	protected JColorChooser colorChooser;
	
	/** A banner label.*/
	
    protected JLabel banner;
    
    /** Buttons label.*/
	
    protected JPanel buttonPanel;
    
    /** User's color preference.*/
    protected Color userColor;
    
    /** Default User's font color.*/
    public String hexColor = "#000000";
       
    /** Color Chooser's main frame.*/
    private JFrame mainFrame;
    
    private JButton colorButton;
    
    /**
     * This is the constructor of the ColorUserSelect.
     * 
     */
    
    public ColorUserSelect(JFrame frame, JButton colorButton) {
    	
    	super(new BorderLayout());
    	
    	 this.colorButton = colorButton;
    	
    	 this.mainFrame = frame;
         //Set up the banner at the top of the window
         banner = new JLabel("Welcome to the Parrot IM Color Zone!",JLabel.CENTER);
         banner.setForeground(Color.yellow);
         banner.setBackground(Color.blue);
         banner.setOpaque(true);
         banner.setFont(new Font("SansSerif", Font.BOLD, 24));
         banner.setPreferredSize(new Dimension(540, 5));

         JPanel bannerPanel = new JPanel(new BorderLayout());
         bannerPanel.add(banner, BorderLayout.CENTER);
         bannerPanel.setBorder(BorderFactory.createTitledBorder("Banner"));

         //Set up color chooser for setting text color
         colorChooser = new JColorChooser(banner.getForeground());
         colorChooser.getSelectionModel().addChangeListener(this);
         colorChooser.setBorder(BorderFactory.createTitledBorder(
                                              "Choose Text Color"));

         buttonPanel = new JPanel();
         buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 40, 10, 40));
         GridLayout buttonsLayout = new GridLayout(1, 2);
         buttonsLayout.setHgap(5);
         buttonPanel.setLayout(buttonsLayout);
         buttonPanel.setPreferredSize(new Dimension(170, 35));
         
         JButton okButton = new JButton("OK");
         JButton cancelButton = new JButton("Cancel");
         
         okButton.addActionListener(new okButtonListener());
         cancelButton.addActionListener(new okButtonListener());
         
         buttonPanel.add(okButton);
         buttonPanel.add(cancelButton);
         
         add(bannerPanel, BorderLayout.CENTER);
         add(colorChooser, BorderLayout.AFTER_LINE_ENDS);
         add(buttonPanel, BorderLayout.PAGE_END);
        
    }
    
    /**
     * Check for the user's color preference.
     * 
     * @param e
     */
    
    class okButtonListener implements ActionListener {

        /**
         * Listens for the uesr's event.
         * 
         * @param event
         */

		public void actionPerformed(ActionEvent event) {
			mainFrame.dispose();
		}
    }
    

    
    /**
     * Check for the user's color preference.
     * 
     * @param e
     */
    
    public void stateChanged(ChangeEvent e) {
        userColor = colorChooser.getColor();
        banner.setForeground(userColor);
        
        //System.out.println("User Red color = " + newColor.getRed());
        //System.out.println("User Green color = " + newColor.getGreen());
        //System.out.println("Hex = " + getColorHex());
        
        hexColor = getColorHex();
        colorButton.setBackground(userColor);
    }
    
    /**
     * Returns user's color preference.
     * 
     * @return Color
     */
    
    public Color getUserColor() {
    	return userColor;
    }
    
    
    public String getUserRedHex() {
    	return Integer.toHexString(userColor.getRed());
    }

    public String getUserGreenHex() {
    	return Integer.toHexString(userColor.getGreen());
    }

    public String getUserBlueHex() {
    	return Integer.toHexString(userColor.getBlue());
    }

    public String getColorHex() {
    	String red = getUserRedHex();
    	if(red.length() == 1){
    		red = "0" + red;
    	}
    	
    	String green = getUserGreenHex();
    	if(green.length() == 1){
    		green = "0" + green;
    	}
    	
    	String blue = getUserBlueHex();
    	if(blue.length() == 1){
    		blue = "0" + blue;
    	}
    	
    	return "#" + red + green + blue;
    }
}
