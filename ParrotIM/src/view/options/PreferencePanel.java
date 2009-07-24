/* FeaturesPanel.java
 * 
 * Programmed By:
 *     Chenny Huang
 *     Vera Lukman
 *     
 * Change Log:
 *     2009-June-20, CH
 *         Initial write.
 *     2009-June-29, VL
 *         Reorganized code.
 *         
 * Known Issues:
 *     incomplete features
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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Model;
import model.dataType.ChatCollectionData;
import model.dataType.ConversationData;
import model.dataType.MultiConversationData;
import model.dataType.UserData;
import model.enumerations.UpdatedType;
import model.enumerations.UserStateType;

import view.chatwindow.ThemeOptionsComboBox;
import view.styles.GPanel;
import view.styles.WindowColors;
import view.theme.LookAndFeelManager;

import controller.MainController;

public class PreferencePanel extends GPanel implements Observer {
    private ThemeOptionsComboBox themeMenu;
    private Model model;
    private JFrame colorChooser;
    private JPanel colorPanel, themePanel, textColorPanel;
    private ArrayList<JLabel> labels = new ArrayList<JLabel>();
    
    public PreferencePanel(MainController c, JFrame mainframe, final Model model)
            throws ClassNotFoundException, SQLException {
    	setGradientColors(model.primaryColor, model.secondaryColor);
    	
    	this.model = model;
    	
        /* THEME SELECTOR */
        themeMenu = new ThemeOptionsComboBox();
        themeMenu.setMaximumSize(new Dimension(200, 30));
        themeMenu.setToolTipText("Select your own Theme");
        themeMenu.setMaximumSize(new Dimension(200,30));
        themeMenu.setAutoscrolls(true);
        themeMenu.addActionListener(new ThemeMenuListener());
        
        JLabel themeLabel = new JLabel("Theme: ");
        themeLabel.setForeground(model.primaryTextColor);
        
        themePanel = new JPanel();
        themePanel.setBorder(BorderFactory.createEmptyBorder(3,3,3,3));
        themePanel.setAlignmentX(LEFT_ALIGNMENT);
        themePanel.setBackground(model.tertiaryColor);
        themePanel.add(themeLabel);
        themePanel.add(themeMenu);
        
        /* colorSelection*/
        colorPanel = new JPanel();
        colorPanel.setBorder(BorderFactory.createEmptyBorder(3,3,3,3));
        colorPanel.setAlignmentX(LEFT_ALIGNMENT);
        colorPanel.setBackground(model.tertiaryColor);
        
        textColorPanel = new JPanel();
        textColorPanel.setBorder(BorderFactory.createEmptyBorder(3,3,3,3));
        textColorPanel.setAlignmentX(LEFT_ALIGNMENT);
        textColorPanel.setBackground(model.tertiaryColor);
        
        JLabel colorLabel = new JLabel("Window Colors:");
        colorLabel.setForeground(model.primaryTextColor);
        
        JLabel textColorLabel = new JLabel("Text Colors:");
        textColorLabel.setForeground(model.primaryTextColor);
        
        colorChooser = new JFrame();
        colorChooser.setVisible(false);
        
        final JButton colorButton = new JButton("1");
        colorButton.setBackground(model.primaryColor);
        colorButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				openColorChooser("Primary Window Color", colorButton, model);
			}
        });
        
        final JButton colorButton2 = new JButton("2");
        colorButton2.setBackground(model.secondaryColor);
        colorButton2.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				openColorChooser("Secondary Window Color", colorButton2, model);
			}
        });
        
        final JButton colorButton3 = new JButton("3");
        colorButton3.setBackground(model.tertiaryColor);
        colorButton3.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				openColorChooser("Tertiary Window Color", colorButton3, model);
			}
        });
        
        final JButton textButton = new JButton("1t");
        textButton.setBackground(model.primaryTextColor);
        textButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				openColorChooser("Primary Text Color", textButton, model);
			}
        });
        
        colorPanel.add(colorLabel);
        colorPanel.add(colorButton);
        colorPanel.add(colorButton2);
        colorPanel.add(colorButton3);
        
        textColorPanel.add(textColorLabel);
        textColorPanel.add(textButton);
        
        model.addObserver(this);
        labels.add(colorLabel);
        labels.add(textColorLabel);
        labels.add(themeLabel);
        
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 225));
        this.add(colorPanel);
        this.add(textColorPanel);
        this.add(themePanel);
    }
    
    private void openColorChooser(String title, JButton buttonPressed, Model model){
    	colorChooser.setTitle(title);
		colorChooser.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		colorChooser.setContentPane(new ThemeColorChooser(colorChooser, buttonPressed, model));
		colorChooser.pack();
		colorChooser.setVisible(true);
		colorChooser.setResizable(false);
    }
    
    private class ThemeMenuListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			System.out.println("Combo = " + themeMenu.getSelectedIndex());
			LookAndFeelManager themeSelector = new LookAndFeelManager();
			themeSelector.setLookAndFeel(themeMenu.getSelectedIndex());
		}
    	
    }

	public void update(Observable o, Object arg) {
		if(arg == UpdatedType.COLOR){
			setGradientColors(model.primaryColor, model.secondaryColor);
			colorPanel.setBackground(model.tertiaryColor);
			textColorPanel.setBackground(model.tertiaryColor);
			themePanel.setBackground(model.tertiaryColor);
			for(JLabel l : labels){
				l.setForeground(model.primaryTextColor);
			}
			this.updateUI();
		}
	}
}
