/* ThemeColorChooser.java
 * 
 * Programmed By:
 *     Jihoon Choi
 *     Jordan Fpx
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

package view.options;

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
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import view.styles.GPanel;

import model.Model;

/**
 * The ThemeColorChooser contains the panel that holds the integrity of the
 * label and JColorChooser together.
 * 
 * This object inherits from JPanel
 */

public class ThemeColorChooser extends GPanel implements ChangeListener  {
	/** A chooser GUI.*/
	protected JColorChooser colorChooser;
	
	/** A banner label.*/
    protected JLabel banner;
    
    /** Buttons label.*/
    protected JPanel buttonPanel;
    
    /** User's color preference.*/
    protected Color userColor;
    
    /** pointer to the model */
    protected Model model;
    
    /** Color Chooser's main frame.*/
    private JFrame mainFrame;
    
    private JButton colorButton;
    
	public ThemeColorChooser(JFrame frame, JButton colorButton, Model model) {
    	super(new BorderLayout());
    	setGradientColors(model.primaryColor, model.secondaryColor);
    	
    	this.model = model;
    	this.mainFrame = frame;
    	this.colorButton = colorButton;
    	
        //Set up the banner at the top of the window
        banner = new JLabel("Welcome to the Parrot IM Color Zone!", JLabel.CENTER);
        banner.setForeground(Color.BLACK);
        banner.setBackground(Color.WHITE);
        banner.setOpaque(true);
        banner.setFont(new Font("SansSerif", Font.BOLD, 21));
        banner.setPreferredSize(new Dimension(400, 50));

        JPanel bannerPanel = new JPanel(new BorderLayout());
        bannerPanel.add(banner, BorderLayout.CENTER);
        bannerPanel.setBorder(BorderFactory.createTitledBorder("Font Preview"));

        //Set up color chooser for setting text color
        colorChooser = new JColorChooser(banner.getForeground());
        colorChooser.getSelectionModel().addChangeListener(this);
        colorChooser.setBorder(BorderFactory.createTitledBorder(
                                             "Choose Text Color"));
        colorChooser.setOpaque(false);
        colorChooser.setPreviewPanel(banner);

        buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 40, 10, 40));
        GridLayout buttonsLayout = new GridLayout(1, 2);
        buttonsLayout.setHgap(5);
        buttonPanel.setLayout(buttonsLayout);
        buttonPanel.setPreferredSize(new Dimension(170, 35));
        
        JButton okButton = new JButton("DONE", new ImageIcon(this.getClass()
                .getResource("/images/buddylist/button_ok.png")));
        
        okButton.addActionListener(new okButtonListener());
        
        buttonPanel.add(okButton);
        
        //add(bannerPanel, BorderLayout.CENTER);
        add(colorChooser, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.PAGE_END);
	}
	
    /**
     * Check for the user's color preference.
     * 
     * @param e
     */
    
    class okButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			mainFrame.setVisible(false);
		}
    }

	public void stateChanged(ChangeEvent arg0) {
		if(colorButton.getText().equals("1")){
			model.primaryColor = colorChooser.getColor();
			model.setColor(1, Integer.toHexString(
					colorChooser.getColor().getRGB()).substring(2, 8));
		}else if(colorButton.getText().equals("2")){
			model.secondaryColor = colorChooser.getColor();
			model.setColor(2, Integer.toHexString(
					colorChooser.getColor().getRGB()).substring(2, 8));
		}else if(colorButton.getText().equals("3")){
			model.tertiaryColor = colorChooser.getColor();
			model.setColor(3, Integer.toHexString(
					colorChooser.getColor().getRGB()).substring(2, 8));
		}else if(colorButton.getText().equals("text")){
			model.primaryTextColor = colorChooser.getColor();
			model.setColor(4, Integer.toHexString(
					colorChooser.getColor().getRGB()).substring(2, 8));
		}else if(colorButton.getText().equals("chat")){
			model.textPaneColor = colorChooser.getColor();
			model.setColor(5, Integer.toHexString(
					colorChooser.getColor().getRGB()).substring(2, 8));
		}
		
		colorButton.setBackground(colorChooser.getColor());
		model.updateColors();
		
		setGradientColors(model.primaryColor, model.secondaryColor);
		this.updateUI();
	}
}
