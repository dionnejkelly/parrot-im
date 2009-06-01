package buddylist;

import java.awt.*;
import java.awt.event.KeyEvent;

import javax.swing.*;

public class buddylist extends JFrame{
	JMenuBar menu;
	
	public buddylist()
	{
		this.setTitle("Buddy List");
		this.setMinimumSize(new Dimension(300,600));
		// Attach the top text menu
		this.setJMenuBar(this.createMenu());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setIconImage(new ImageIcon(System.getProperty("user.dir") + "/src/logo.png").getImage());

		JPanel buddylistPanel = new JPanel();
		buddylistPanel.setLayout(new BorderLayout());
		buddylistPanel.setPreferredSize(new Dimension(300,600));
		
		
	    //INSIDE PANEL
		JPanel mainListPanel = new buddyPanel();	
	    
	    //add to buddylistPanel
		//buddylistPanel.add(menu, BorderLayout.NORTH);
		//buddylistPanel.add(mainListPanel, BorderLayout.CENTER);
		getContentPane().add(buddylistPanel);   
		pack();
		setVisible(true);
	}

	//Creates top Text Menu
	public JMenuBar createMenu()
	{
	       	JMenuBar menuBar;
			JMenu fileMenu, acctsMenu, connectMenu, optionsMenu, helpMenu;
			JMenuItem menuItem;

	        //Create the menu bar.
	        menuBar = new JMenuBar();

	        fileMenu = new JMenu("File");
		    fileMenu.setMnemonic(KeyEvent.VK_F);
		    menuBar.add(fileMenu);
		    JMenuItem exitItem1 = new JMenuItem("Exit", KeyEvent.VK_N);
		    fileMenu.add(exitItem1);
		    
			acctsMenu = new JMenu("Accounts");
		    acctsMenu.setMnemonic(KeyEvent.VK_A);
		    menuBar.add(acctsMenu);
		    JMenuItem accountsItem1 = new JMenuItem("Edit Accounts", KeyEvent.VK_E);
		    acctsMenu.add(accountsItem1);
			
			connectMenu = new JMenu("Connect");
			connectMenu.setMnemonic(KeyEvent.VK_C);
		    menuBar.add(connectMenu);
		    //JMenuItem exitItem1 = new JMenuItem("Exit", KeyEvent.VK_N);
		    //fileMenu.addSeparator();
		    //fileMenu.add(exitItem1);
		    
			optionsMenu = new JMenu("Options");
		    optionsMenu.setMnemonic(KeyEvent.VK_O);
		    menuBar.add(optionsMenu);
		    JMenuItem optionsItem1 = new JMenuItem("Parrot Preferences", KeyEvent.VK_P);
		    optionsMenu.add(optionsItem1);
		    
			helpMenu = new JMenu("Help");
		    helpMenu.setMnemonic(KeyEvent.VK_H);
		    menuBar.add(helpMenu);
		    JMenuItem helpItem1 = new JMenuItem("User Guide", KeyEvent.VK_G);
		    JMenuItem helpItem2 = new JMenuItem("Report Bug", KeyEvent.VK_R);
		    JMenuItem helpItem3 = new JMenuItem("About Parrot.IM", KeyEvent.VK_A);
		    helpMenu.add(helpItem1);
		    helpMenu.add(helpItem2);
		    helpMenu.addSeparator();
		    helpMenu.add(helpItem3); 
	        
	        return menuBar;
	}
}
	

////CHENNY'S STUFF!!!  

/*
 * public class buddylist extends JFrame{

	public buddylist()
	{
		
		this.setTitle("Buddy List");
		this.setJMenuBar(this.createMenu());
		setMinimumSize(new Dimension(400,600));
		setIconImage(new ImageIcon(System.getProperty("user.dir") + "/src/logo.png").getImage());
		
		//MENU BAR
		JPanel menuPanel = new JPanel();
		menuPanel.setBackground(Color.green);
		menuPanel.setMinimumSize(new Dimension(300,50));
		menuPanel.setPreferredSize(new Dimension(300,50));
		menuPanel.setLayout(new GridLayout(1,5,10,10));
		
		ImageIcon icon = createImageIcon("image/Hello.gif");
		//JLabel Label1 = new JLabel(icon);
		//menuPanel.add(Label1);
		
		JLabel L1 = new JLabel("<User name>, <Account>", icon, JLabel.LEFT);
		menuPanel.add(L1);
		//JLabel L2 = new JLabel("<Account>");
		//menuPanel.add(L2);
		
		//Contact List
		JPanel contactPanel = new JPanel();
		contactPanel.setBackground(Color.white);
		contactPanel.setLayout(new BoxLayout(contactPanel,BoxLayout.Y_AXIS));
		ImageIcon icon1 = createImageIcon("image/38.png");
		JLabel l1 = new JLabel("Contact 1 -- status text", icon1 , JLabel.LEFT);
		JLabel l2 = new JLabel("Contact 2 -- status text", icon1 , JLabel.LEFT);
		JLabel l3 = new JLabel("Contact 3 -- status text", icon1 , JLabel.LEFT);
		JLabel l4 = new JLabel("Contact 4 -- status text", icon1 , JLabel.LEFT);
		JLabel l5 = new JLabel("Contact 5 -- status text", icon1 , JLabel.LEFT);
		JLabel l6 = new JLabel("Contact 6 -- status text", icon1 , JLabel.LEFT);
		TextField tf4;
		tf4 = new TextField("Hello",1);
		ImageIcon icon5 = createImageIcon("image/75.png");
		JButton searchButton = new JButton("12345",icon5);
		contactPanel.add(searchButton);
		contactPanel.add(l1);
		contactPanel.add(l2);
		contactPanel.add(l3);
		contactPanel.add(l4);
		contactPanel.add(l5);
		contactPanel.add(l6);
		
		
		
		//Contact Options
		JPanel bottomPanel = new JPanel();
		bottomPanel.setBackground(Color.blue);
		bottomPanel.setMinimumSize(new Dimension(300,90));
		bottomPanel.setPreferredSize(new Dimension(300,90));
		bottomPanel.setLayout(new BoxLayout(bottomPanel,BoxLayout.X_AXIS));
		ImageIcon icon2 = createImageIcon("image/add.png");
		ImageIcon icon3 = createImageIcon("image/remove.png");
		ImageIcon icon4 = createImageIcon("image/block.png");
		JButton addButton = new JButton("Add", icon2 );
		addButton.setMinimumSize(new Dimension(100,50));
		addButton.setPreferredSize(new Dimension(100,50));
		
		JButton removeButton = new JButton("Remove",icon3);
		removeButton.setMinimumSize(new Dimension(100,50));
		removeButton.setPreferredSize(new Dimension(100,50));
		
		JButton blockButton = new JButton("Block",icon4);
		removeButton.setMinimumSize(new Dimension(100,50));
		removeButton.setPreferredSize(new Dimension(100,50));
		
		bottomPanel.add(Box.createRigidArea(new Dimension(10,50)));
		bottomPanel.add(addButton);
		bottomPanel.add(Box.createRigidArea(new Dimension(10,50)));
		bottomPanel.add(removeButton);
		bottomPanel.add(Box.createRigidArea(new Dimension(10,50)));
		bottomPanel.add(blockButton);
		bottomPanel.add(Box.createHorizontalGlue());
		
		JPanel test = new JPanel();
		test.setBackground(Color.red);
		test.setPreferredSize(new Dimension(300,40));
		bottomPanel.setLayout(new BoxLayout(bottomPanel,BoxLayout.X_AXIS));

		
		//SETUP MAIN PANEL
		JPanel mainPanel = new JPanel();

		mainPanel.setLayout(new BorderLayout());
		mainPanel.setBackground(Color.gray);
		
		mainPanel.add(menuPanel, BorderLayout.NORTH);
		mainPanel.add(contactPanel, BorderLayout.CENTER);
		mainPanel.add(test, BorderLayout.SOUTH);
		mainPanel.add(bottomPanel, BorderLayout.SOUTH);
		
		getContentPane().add(mainPanel);
		pack();
		setVisible(true);
	}
	// Returns an ImageIcon, or null if the path was invalid. 
    protected static ImageIcon createImageIcon(String path) {
	    java.net.URL imgURL = buddylist.class.getResource(path);
	    if (imgURL != null) {
	       return new ImageIcon(imgURL);
	        } else {
	            System.err.println("Couldn't find file: " + path);
	            return null;
	        }
    }
    
	public JMenuBar createMenu()
	{
	       	JMenuBar menuBar;
			JMenu menuFile, submenu, menuEdit, menuConn, menuOpts, menuHelp;
			JMenuItem menuItem;

	        //Create the menu bar.
	        menuBar = new JMenuBar();

	        //Build the first menu.
	        menuFile = new JMenu("File");
	        menuFile.setMnemonic(KeyEvent.VK_F);
	        menuBar.add(menuFile);

	        //a group of JMenuItems
	        menuItem = new JMenuItem("Something1",
	                                 KeyEvent.VK_S);
	        menuItem.setAccelerator(KeyStroke.getKeyStroke(
	                KeyEvent.VK_1, ActionEvent.ALT_MASK));
	        menuFile.add(menuItem);

	        menuFile.addSeparator();
	        menuItem = new JMenuItem("Something2",
                    KeyEvent.VK_S);
	        menuItem.setAccelerator(KeyStroke.getKeyStroke(
	        		KeyEvent.VK_2, ActionEvent.ALT_MASK));
	        menuFile.add(menuItem);
	        
	        submenu = new JMenu("Edit");
	        submenu.setMnemonic(KeyEvent.VK_E);

	        menuItem = new JMenuItem("An item in the submenu");
	        menuItem.setAccelerator(KeyStroke.getKeyStroke(
	                KeyEvent.VK_2, ActionEvent.ALT_MASK));
	        submenu.add(menuItem);

	        menuItem = new JMenuItem("Another item");
	        submenu.add(menuItem);
	        menuFile.add(submenu);

	        //Build second menu in the menu bar.
	        menuEdit = new JMenu("Edit");
	        menuEdit.setMnemonic(KeyEvent.VK_E);
	        menuEdit.getAccessibleContext().setAccessibleDescription(
	                "This menu does nothing");
	        menuBar.add(menuEdit);

	        menuConn = new JMenu("Connect");
	        menuConn.setMnemonic(KeyEvent.VK_C);
	        menuConn.getAccessibleContext().setAccessibleDescription(
	                "This menu does nothing");
	        menuBar.add(menuConn);
	        
	        menuOpts = new JMenu("Options");
	        menuOpts.setMnemonic(KeyEvent.VK_O);
	        menuOpts.getAccessibleContext().setAccessibleDescription(
	                "This menu does nothing");
	        menuBar.add(menuOpts); 

	        menuHelp = new JMenu("Help");
	        menuHelp.setMnemonic(KeyEvent.VK_H);
	        menuHelp.getAccessibleContext().setAccessibleDescription(
	                "This menu does nothing");
	        menuBar.add(menuHelp); 
	        
	        return menuBar;
	}
	
}
 */
