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

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import model.Model;

import view.chatwindow.ThemeOptionsComboBox;
import view.options.modelstub.ChatbotQADataType;
import view.options.modelstub.model;
import view.styles.PopupWindowListener;

import controller.MainController;

public class PreferencePanel extends JPanel {
    private model modelStub;

    private JFrame mainframe;

    private JCheckBox chatbotCheck;
    private JCheckBox soundCheck;
    private JCheckBox chatLogCheck;
    private JCheckBox chatWindowHistoryCheck;
    private ThemeOptionsComboBox themeMenu;

    private JList chatbotList;
    private JButton chatbotAddButton;
    private JButton chatbotEditButton;
    private JButton chatbotRemoveButton;
    private JPanel chatbotOptions;

    private Model model;

    public PreferencePanel(MainController c, JFrame mainframe, Model model)
            throws ClassNotFoundException, SQLException {
        this.model = model;
        modelStub = new model(this.model);
        this.mainframe = mainframe;

        
        /* THEME SELECTOR */
        themeMenu = new ThemeOptionsComboBox();
        themeMenu.setToolTipText("Select your own Theme");
        themeMenu.setAutoscrolls(true);
        
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        this.add(themeMenu);
       
    }

    

}
