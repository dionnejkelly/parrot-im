package view.profileManager;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.WindowConstants;

import model.Model;

import view.mainwindow.mainwindow;
import view.styles.popupWindowListener;

public class editAccountFrame extends JFrame
{
	/*
	 * LEAVING OUT EDIT ACCOUNT FUNCTION FOR ALPHA
	 */
	
	private JPanel accMANPanel;
	private Model model;
	private mainwindow mainFrame;
	protected JFrame popup;
	
	//Instance 1 -- New Account (empty forms)
	private editAccountFrame(Model model)
	{
		this.model = model;
		setTitle("add New Account");
		
		
	}
	
	//Instance 2 -- Edit Account (User/Pass already filled in, Disable username box)
	/*private editAccountFrame(Model model, String name)
	{
		
	}*/

}
