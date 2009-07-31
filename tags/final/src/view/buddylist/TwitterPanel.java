package view.buddylist;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.ScrollPaneConstants;

import model.Model;
import model.dataType.ProfileData;
import model.dataType.TwitterAccountData;
import model.dataType.UserData;
import model.enumerations.ServerType;
import model.enumerations.UpdatedType;

import org.jivesoftware.smack.XMPPException;

import view.chatwindow.ChatWindow;
import view.options.MusicPlayer;
import view.styles.GPanel;
import view.styles.GroupedListPane;
import controller.MainController;
import controller.services.BadConnectionException;

/**
 * TwitterPanel display Twitter feeds and status panels for Parrot IM users.
 */
public class TwitterPanel extends GPanel implements Observer {
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

    GroupedListPane buddyListPane;

    /**
     * list of buddies
     */
    private ArrayList<UserData> tweets;
    private ArrayList<UserData> buddies;

    private ArrayList<ArrayList<UserData>> buddyArray;

    private long lastUpdate;

    private PictureUpdateThread pictureUpdateThread;

    private ArrayList<FriendWrapper> friendWrappers;

    private SelectListener selectListener;

    private JLabel tempLabel;

    private boolean twitterPanelEnabled;

    /**
     * BuddyPanel , display friend contact list in buddy panel.
     * 
     * @param c
     * @param model
     * @param buddyWindow
     */
    // SELECTION
    // II-Constructors
    public TwitterPanel(Model model) {
        this.model = model;
        model.addObserver(this);
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        setGradientColors(model.primaryColor, model.secondaryColor);

        tempLabel = new JLabel("Please Login to a Twitter Account first");
        tempLabel.setForeground(model.primaryTextColor);
        add(tempLabel);
    }

    public TwitterPanel(MainController c, Model model, JFrame buddyWindow) {
        this.buddyWindow = buddyWindow;
        model.getCurrentProfile().addObserver(this);
        if (model.getCurrentProfile().hasTwitter()) {
            this.twitterPanelEnabled = true;
            model.getCurrentProfile().getAccountFromServer(ServerType.TWITTER)
                    .addObserver(this);
            model.addObserver(this);
        } else {
            this.twitterPanelEnabled = false;
        }
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        setGradientColors(model.primaryColor, model.secondaryColor);

        this.chatClient = c;
        this.model = model;
        this.chat = null;
        this.lastUpdate = System.currentTimeMillis();
        this.friendWrappers = new ArrayList<FriendWrapper>();
        this.selectListener = new SelectListener();

        buddyArray = new ArrayList<ArrayList<UserData>>();
        for (int i = 0; i < 2; i++) {
            buddyArray.add(new ArrayList<UserData>());
        }

        // Test code, make it hide at the start
        tweets = null;

        tweetList = new JPanel();
        tweetList.setBackground(Color.WHITE);
        tweetList.setLayout(new BorderLayout());

        // add friends to the buddy list
        boxes[0] = Box.createVerticalBox();
        boxes[1] = Box.createVerticalBox();

        ImageIcon twitterImage =
                new ImageIcon(this.getClass().getResource(
                        "/images/buddylist/twitter_logo.png"));

        buddyListPane = new GroupedListPane(model);
        buddyListPane.addGroup("     Recent Tweets", twitterImage);
        buddyListPane.addGroup("     Twitter Friends", twitterImage);

        if (model.getCurrentProfile().hasTwitter()) {
            pictureUpdateThread = new PictureUpdateThread();
            pictureUpdateThread.start();

            listRepopulate();
        }

        scroller = new JScrollPane(buddyListPane);
        scroller
                .setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        add(scroller);
    }

    private void initializeUponAccountAddition() {
        this.twitterPanelEnabled = true;
        model.getCurrentProfile().getAccountFromServer(ServerType.TWITTER)
                .addObserver(this);
        model.addObserver(this);

        pictureUpdateThread = new PictureUpdateThread();
        pictureUpdateThread.start();

        return;
    }

    private void listRepopulate() {
        FriendPanel tempPanel = null;
        try {
            tweets = model.getCurrentProfile().getTweets();
            buddies = model.getCurrentProfile().getTwitterFriends();
        } catch (BadConnectionException e1) {
            e1.printStackTrace();
        }

        for (int i = 0; i < boxes.length; i++) {
            buddyArray.get(i).clear();
            buddyListPane.removeAllElements(i);
            boxes[i].removeAll();
        }

        // Create the friend wrapper
        // Compare the model friends with the GUI friends
        for (UserData u : this.tweets) {
            tempPanel = TweetItem(u);
            buddyArray.get(0).add(u);
            boxes[0].add(TweetItem(u));
            buddyListPane.addElement(0, tempPanel);
        }
        for (UserData u : this.buddies) {
            tempPanel = FriendItem(u);
            buddyArray.get(1).add(u);
            boxes[1].add(FriendItem(u));
            buddyListPane.addElement(1, tempPanel);
        }

        // add mouse listeners
        // Note: Do not add listeners to j = 0
        for (int j = 1; j < 2; j++) {
            for (int i = 0; i < boxes[j].getComponentCount(); i++) {
                buddyListPane.addExternalMouseListener(j, i,
                        this.selectListener);
            }
        }

    }

    /**
     * FriendItem to check friend status.
     * 
     * @param user
     * @return friendItem
     */
    public FriendPanel FriendItem(UserData user) {
        ImageIcon defaultIcon =
                new ImageIcon(this.getClass().getResource(
                        "/images/chatwindow/personal.png"));
        JLabel label = null;
        FriendPanel friendItem = new FriendPanel();
        FriendWrapper tempWrapper = null;

        friendItem.setLayout(new BorderLayout());
        friendItem.setBackground(Color.WHITE);

        friendItem.setName(user.getNickname());

        // end it
        friendItem.setToolTipText("<html>  " + user.getNickname() + "("
                + user.getUserID() + ")" + "<br>" + user.getStatus()
                + "<br> Status:" + user.getState() + "<br>" + user.getServer()
                + "<hr>" + "Right-click for more options");

        tempWrapper = new FriendWrapper(user, true);
        this.friendWrappers.add(tempWrapper);

        ImageIcon avatarImage = defaultIcon;
        Image img = avatarImage.getImage();
        img = img.getScaledInstance(25, 25, java.awt.Image.SCALE_SMOOTH);
        ImageIcon newIcon = new ImageIcon(img);
        label = new JLabel(newIcon);
        friendItem.add(label, BorderLayout.WEST);
        pictureUpdateThread.addUserAndLabel(user, label);

        friendItem.add(tempWrapper.getLabelRepresentation(),
                BorderLayout.CENTER);
        friendItem.setWrapper(tempWrapper);

        return friendItem;
    }

    public FriendPanel TweetItem(UserData user) {
        ImageIcon defaultIcon =
                new ImageIcon(this.getClass().getResource(
                        "/images/chatwindow/personal.png"));
        JLabel label = null;
        FriendPanel friendItem = new FriendPanel();
        FriendWrapper tempWrapper2 = null;

        friendItem.setLayout(new BorderLayout());
        friendItem.setBackground(Color.WHITE);

        friendItem.setName(user.getNickname() + " - " + user.getStatus());

        // end it
        friendItem.setToolTipText("<html>  " + user.getNickname() + "("
                + user.getUserID() + ")" + "<br>" + user.getStatus()
                + "<br> Status:" + user.getState() + "<br>" + user.getServer()
                + "<hr>" + "Right-click for more options");

        tempWrapper2 = new FriendWrapper(user);
        this.friendWrappers.add(tempWrapper2);

        ImageIcon avatarImage = defaultIcon;
        Image img = avatarImage.getImage();
        img = img.getScaledInstance(25, 25, java.awt.Image.SCALE_SMOOTH);
        ImageIcon newIcon = new ImageIcon(img);
        label = new JLabel(newIcon);
        friendItem.add(label, BorderLayout.WEST);
        pictureUpdateThread.addUserAndLabel(user, label);

        friendItem.add(tempWrapper2.getLabelRepresentation(),
                BorderLayout.CENTER);
        friendItem.setWrapper(tempWrapper2);

        return friendItem;
    }

    public class PictureUpdateThread extends Thread {
        private ArrayList<JLabel> labels;
        private ArrayList<UserData> users;

        public void addUserAndLabel(UserData user, JLabel label) {
            if (users != null && labels != null) {
                users.add(user);
                labels.add(label);
            }

            return;
        }

        public void clearUpdateQueue() {
            this.users.clear();
            this.labels.clear();

            return;
        }

        private boolean more() {
            return users.size() > 0 && labels.size() > 0;
        }

        public void run() {
            this.setName("Picture thread muahha!");
            users = new ArrayList<UserData>();
            labels = new ArrayList<JLabel>();
            UserData user = null;
            JLabel label = null;

            while (true) {
                if (more()) {
                    user = users.remove(0);
                    label = labels.remove(0);
                    ImageIcon avatarImage = null;
                    try {
                        avatarImage = chatClient.getAvatarPicture(user);
                        Image img = avatarImage.getImage();
                        img =
                                img.getScaledInstance(25, 25,
                                        java.awt.Image.SCALE_SMOOTH);
                        ImageIcon newIcon = new ImageIcon(img);
                        label.setIcon(newIcon);
                    } catch (XMPPException e) {
                        e.printStackTrace();
                    }
                } else {
                    // Rests thread to prevent cpu overload
                    try {
                        Thread.currentThread();
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void update(Observable o, Object arg) {
        boolean breakLoop = false;

        if (arg == UpdatedType.COLOR) {
            setGradientColors(model.primaryColor, model.secondaryColor);
            // tempLabel.setForeground(model.primaryTextColor);
            updateUI();
        } else if (o instanceof TwitterAccountData) {
            // Refresh if add/remove friends
            listRepopulate();
        } else if (o instanceof ProfileData && !this.twitterPanelEnabled
                && model.getCurrentProfile().hasTwitter()) {
            this.initializeUponAccountAddition();
        }
    }

    /**
     * SelectListener, select friend from buddy list.
     * 
     */
    private class SelectListener implements MouseListener {
        /**
         * boolean variable, indicate whether selected.
         */
        protected boolean selected;

        /**
         * SelectListener()
         */
        public SelectListener() {
            selected = false;
        }

        /**
         * mouseClicked to unhighlight the last selected
         */
        public void mouseClicked(MouseEvent event) {
            for (int j = 0; j < buddyArray.size(); j++) {
                for (int i = 0; i < boxes[j].getComponentCount(); i++) {
                    if (event.getSource().equals(
                            buddyListPane.getComponent(j, i))) {
                        if (event.getButton() == MouseEvent.BUTTON1) {
                            // Left Click
                            selected = true;

                            /* Fix this to directly reference the GUI */
                            selectedFriend = buddyArray.get(j).get(i);

                            if (event.getClickCount() == 2) {
                                selected = false;
                                chatClient.startConversation(selectedFriend,
                                        true);
                            }
                        } else if (event.getSource().equals(
                                buddyListPane.getComponent(j, i))) {

                            // Right Click
                            rightClickMenu.show(buddyListPane
                                    .getComponent(j, i), event.getX(), event
                                    .getY());
                            // + 25 * i);
                            selectedName =
                                    buddyListPane.getComponent(j, i).getName();
                            selectedFriend = buddyArray.get(j).get(i);
                            if (selectedFriend.isBlocked()) {
                                menuItem4.setText("Unblock Friend");
                                menuItem4
                                        .setIcon(new ImageIcon(
                                                this
                                                        .getClass()
                                                        .getResource(
                                                                "/images/buddylist/unblock_user.png")));
                            } else { // is not blocked; block it
                                menuItem4.setText("Block Friend");
                                menuItem4
                                        .setIcon(new ImageIcon(
                                                this
                                                        .getClass()
                                                        .getResource(
                                                                "/images/buddylist/block_user.png")));
                            }
                        }
                    }
                }
            }

            new MusicPlayer("/audio/buddy/buddyHighlightedSound.wav", model);
        }

        // unimplemented mouselistener methods
        public void mouseEntered(MouseEvent event) {
        }

        public void mouseExited(MouseEvent event) {
        }

        public void mousePressed(MouseEvent e) {
        }

        public void mouseReleased(MouseEvent e) {
        }
    }

    /**
     * RightCickMenuListener , user right click mouse to do actions.
     * 
     */
    class RightCickMenuListener extends MouseAdapter {
        public void mousePressed(MouseEvent event) {
            if (event.getSource().equals(menuItem1)) {
                /* Is the chat window already open? */
                chatClient.startConversation(selectedFriend, true);
            } else if (event.getSource().equals(menuItem2)) {
                // chat.addToConversation(selectedName);
                // TODO Group chat not yet implemented.
            }

        }
    }

}