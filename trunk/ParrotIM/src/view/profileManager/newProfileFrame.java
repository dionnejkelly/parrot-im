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

public class newProfileFrame extends JFrame
{
	private Model model;
	private profileManager managerFrame;
	protected JFrame popup;
	
	private JPanel mainProfilePanel;
	
	private newProfileFrame(Model model,profileManager pManager)
	{
		//Popup-frame identification, for use with OK/Cancel buttons
		this.model = model;
		managerFrame = pManager;
		popup = this;
		
		setTitle("Add new Profile");
		mainProfilePanel = new JPanel();
		mainProfilePanel.setLayout(new BorderLayout());
		
		//Top Part: Profile Name
		
		
		//Middle Part: CheckBox - Enable Password Protection
		//				CheckBox - Default Profile  (Will sign in automatically using this profile)
		
		//Bottom Part: OK/CANCEL buttons
		
	}

}
