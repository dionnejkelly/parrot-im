package view.buddylist;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.ScrollPaneConstants;

import org.jivesoftware.smack.XMPPException;

import controller.MainController;

import view.blockManager.BlockManager;
import view.mainwindow.HelpPanel;
import view.options.GroupChatConfigurationFrame;
import view.options.MusicPlayer;
import view.styles.GroupedListPane;
import view.styles.PopupWindowListener;
import view.chatwindow.ChatWindow;
import winterwell.jtwitter.Twitter.User;

import model.Model;
import model.dataType.TwitterUserData;
import model.dataType.UserData;
import model.enumerations.ServerType;
import model.enumerations.UpdatedType;
import model.enumerations.UserStateType;

/**
 * TwitterPanel display Twitter feeds and status panels for Parrot IM users.
 */
public class TwitterPanel extends JPanel implements Observer {
    /*
     * TODO: BUDDY PANEL HAS Center: Buddy List South: Buddy Options
     */
    // Selection
    // I - non-static member
    /**
     * selectedIndex of Buddylist
     */
    /**
     * select source of Buddylist
     */
    protected Object lastSelectedSource;
    /**
     * buddy window frame
     */
    protected JFrame buddyWindow;
    /**
     * chat window
     */
    ChatWindow chat;
    /**
     * options tool
     */
    JToolBar options;
    /**
     * scroller of Buddy list
     */
    JScrollPane scroller;
    /**
     * friend contact list.
     */
    JPanel tweetList;
    /**
     * right click pop up menu.
     */
    JPopupMenu rightClickMenu;
    /**
     * menuItems in pop up menu
     */
    JMenuItem menuItem1, menuItem2, menuItem3, menuItem4, menuItem5;
    /**
     * box to store friend list
     */
    Box boxes[] = new Box[2];

    GroupedListPane buddyListPane = new GroupedListPane();

    /**
     * selected name
     */
    String selectedName;
    /**
     * variable to handles all inputs from user interaction
     */
    MainController chatClient;
    /**
     * model for extracting buddy list, each buddy's information and ,
     * conversation
     */
    Model model;
    /**
     * selected friend
     */
    UserData selectedFriend;

    /**
     * list of buddies
     */
    private ArrayList<User> tweets;
    
    private ArrayList<ArrayList<UserData>> buddyArray;

    private JTextField search;

    private boolean searchEnabled;

    private JButton searchButton;

    private JButton googleSearchButton;

    private long lastUpdate;

    /**
     * BuddyPanel , display friend contact list in buddy panel.
     * 
     * @param c
     * @param model
     * @param buddyWindow
     */
    // SELECTION
    // II-Constructors
    public TwitterPanel(MainController c, Model model, JFrame buddyWindow) {
        this.buddyWindow = buddyWindow;
        model.addObserver(this);
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        this.chatClient = c;
        this.model = model;
        this.chat = null;
        this.searchEnabled = false;
        this.lastUpdate = System.currentTimeMillis();

        // Test code, make it hide at the start
        // this.chat = new ChatWindow(chatClient, model);
        tweets = null;

        tweetList = new JPanel();
        tweetList.setBackground(Color.WHITE);
        tweetList.setLayout(new BorderLayout());

        // Place all friends from currentProfile into buddy list
        //tweets = model.getCurrentProfile().getAllFriends();

        // add friends to the buddy list
        boxes[0] = Box.createVerticalBox();
        boxes[1] = Box.createVerticalBox();

        ImageIcon twitterImage =
                new ImageIcon(this.getClass().getResource(
                        "/images/buddylist/twitter_logo.png"));

        buddyListPane.addGroup("     Twitter", twitterImage);

        //pictureUpdateThread = new PictureUpdateThread();
        //pictureUpdateThread.start();

        //listRepopulate();

        // friendList.add(boxes[0], BorderLayout.NORTH);
        scroller = new JScrollPane(buddyListPane);
        scroller
                .setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        add(scroller);
    }

	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}

}