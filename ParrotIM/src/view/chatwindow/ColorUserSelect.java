package view.chatwindow;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
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
    
    /** User's color preference.*/
    protected Color userColor;
    
    
    /**
     * This is the constructor of the ColorUserSelect.
     * 
     */
    
    
    public ColorUserSelect() {
    	
    	super(new BorderLayout());
    	

         //Set up the banner at the top of the window
         banner = new JLabel("Welcome to the Parrot IM Color Zone!",JLabel.CENTER);
         banner.setForeground(Color.yellow);
         banner.setBackground(Color.blue);
         banner.setOpaque(true);
         banner.setFont(new Font("SansSerif", Font.BOLD, 24));
         banner.setPreferredSize(new Dimension(570, 65));

         JPanel bannerPanel = new JPanel(new BorderLayout());
         bannerPanel.add(banner, BorderLayout.CENTER);
         bannerPanel.setBorder(BorderFactory.createTitledBorder("Banner"));

         //Set up color chooser for setting text color
         colorChooser = new JColorChooser(banner.getForeground());
         colorChooser.getSelectionModel().addChangeListener(this);
         colorChooser.setBorder(BorderFactory.createTitledBorder(
                                              "Choose Text Color"));

         add(bannerPanel, BorderLayout.CENTER);
         add(colorChooser, BorderLayout.PAGE_END);
        
    }
    
    /**
     * Check for the user's color preference.
     * 
     * @param e
     */
    
    public void stateChanged(ChangeEvent e) {
        Color newColor = colorChooser.getColor();
        banner.setForeground(newColor);
        
        System.out.println("User Red color = " + newColor.getRed());
        System.out.println("User Green color = " + newColor.getGreen());
        System.out.println("User Blue color = " + newColor.getBlue());
    }
    
    /**
     * Returns user's color preference.
     * 
     * @return Color
     */
    
    public Color getUserColor() {
    	return userColor;
    }

}
