package view.styles;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.itbs.aimcer.bean.Group;

import model.Model;
import model.enumerations.UpdatedType;

public class GroupedListPane extends JPanel implements Observer {
	private static int lastClickedGroup;
    Box boxes[] = new Box[1];
    private WindowColors colors = new WindowColors();
    private static ArrayList<CustomListPane> groups = new ArrayList<CustomListPane>();
    private ImageIcon arrowIconUp =
            new ImageIcon(this.getClass().getResource(
                    "/images/buddylist/up.png"));
    private ImageIcon arrowIconDown =
            new ImageIcon(this.getClass().getResource(
                    "/images/buddylist/down.png"));
    private ArrayList<JLabel> arrows = new ArrayList<JLabel>();
    private ArrayList<GPanel> gPanels = new ArrayList<GPanel>();
    private ArrayList<JLabel> labels = new ArrayList<JLabel>();
    JLabel arrowPanel;
    private Model model;

    public GroupedListPane(Model model) {
    	lastClickedGroup = -2;
    	this.model = model;
    	model.addObserver(this);
    	
        setBackground(Color.WHITE);
        setLayout(new BorderLayout());

        boxes[0] = Box.createVerticalBox();

        add(boxes[0], BorderLayout.NORTH);
    }

    public static int getLastClickedGroup(){
    	return lastClickedGroup;
    }
    
    public static void setLastClickedGroup(int index){
    	lastClickedGroup = index;
    }
    public static void resetSelectionLastClickedGroup(){
    	if (lastClickedGroup >= 0)
    		groups.get(lastClickedGroup).resetClickedSelection();
    }
    public int getGroupCount() {
        return groups.size();
    }

    public void addGroup(String name, ImageIcon img) {
    	//Panel Settings
        GPanel newPanel = new GPanel();
        newPanel.setGradientColors(model.tertiaryColor, model.tertiaryColor);
        newPanel.setLayout(new BorderLayout());
        
        //imageLabel
        newPanel.add(new JLabel(img), BorderLayout.WEST);
        
        //textLabel
        JLabel textLabel = new JLabel(name);
        labels.add(textLabel);
        textLabel.setForeground(model.primaryTextColor);
        newPanel.add(textLabel, BorderLayout.CENTER);

        arrowPanel = new JLabel(arrowIconDown);
        newPanel.add(new JLabel(arrowIconDown), BorderLayout.EAST);
        gPanels.add(newPanel);

        CustomListPane collapsableListPane = new CustomListPane(groups.size());
        groups.add(collapsableListPane);

        newPanel.addMouseListener(new CollapseListner());
        boxes[0].add(newPanel);
        boxes[0].add(collapsableListPane);
    }
    
    public void addGroup(JLabel textLabel, ImageIcon img) {
        //Panel Settings
        GPanel newPanel = new GPanel();
        newPanel.setGradientColors(model.tertiaryColor, model.tertiaryColor);
        newPanel.setLayout(new BorderLayout());
        
        //imageLabel
        newPanel.add(new JLabel(img), BorderLayout.WEST);
        
        //textLabel
        //JLabel textLabel = new JLabel(name);
        labels.add(textLabel);
        textLabel.setForeground(model.primaryTextColor);
        newPanel.add(textLabel, BorderLayout.CENTER);

        arrowPanel = new JLabel(arrowIconDown);
        newPanel.add(new JLabel(arrowIconDown), BorderLayout.EAST);
        gPanels.add(newPanel);

        CustomListPane collapsableListPane = new CustomListPane(groups.size());
        groups.add(collapsableListPane);

        newPanel.addMouseListener(new CollapseListner());
        boxes[0].add(newPanel);
        boxes[0].add(collapsableListPane);
    }


    public void addGroupTop(String name) {
        GPanel newPanel = new GPanel();
        JLabel textLabel = new JLabel(name);
        textLabel.setForeground(model.primaryTextColor);
        newPanel.add(textLabel);
        labels.add(textLabel);
        newPanel.setGradientColors(model.tertiaryColor, model.tertiaryColor);

        CustomListPane collapsableListPane = new CustomListPane(0);
        groups.add(0, collapsableListPane);
        setAllGroupIndex();

        newPanel.addMouseListener(new CollapseListner());
        boxes[0].add(newPanel, 0);
        boxes[0].add(collapsableListPane, 1);
    }

    private void setAllGroupIndex(){
    	for (int index = 0; index < groups.size(); index++){
    		groups.get(index).setGroupIndex(index);
    	}
    }
    public void addElement(int group, String name, ImageIcon img) {
        groups.get(group).addElement(name, img);
    }

    public void addElement(int group, JPanel externalFriendPanel) {
        groups.get(group).addElement(externalFriendPanel);
    }
    
    public void addElement(int group, JPanel externalFriendPanel, int index) {
        groups.get(group).addElement(externalFriendPanel, index);
    }
    
    public void removeElement(int group, JPanel externalFriendPanel) {
        groups.get(group).removeElement(externalFriendPanel);

        return;
    }

    public CustomListPane getListPane(int group) {
        return groups.get(group);
    }

    public void addExternalMouseListener(int group, int componentIndex,
            MouseListener externalListener) {
        getComponent(group, componentIndex).addMouseListener(externalListener);
    }

    public Component getComponent(int group, int componentIndex) {
        return groups.get(group).boxes[0].getComponent(componentIndex);
    }
    
    public CustomListPane getGroup(int group) {
        return groups.get(group);
    }

    public void removeAllElements(int group) {
        groups.get(group).removeAllElements();
    }

    private void setGroupVisible(int group, boolean visible) {
        groups.get(group).setVisible(visible);
    }

    private boolean isGroupVisible(int group) {
        return groups.get(group).isVisible();
    }

    /**
     * @author Jordan
     * 
     */
    private class CollapseListner implements MouseListener {
        /**
         * toggles the minizations of the sublists
         */
        public void mouseClicked(MouseEvent event) {
            for (int i = 0; i < boxes[0].getComponentCount(); i++) {
                if (event.getSource().equals(boxes[0].getComponent(i))) {
                    setGroupVisible(i / 2, !isGroupVisible(i / 2));
                }
            }
        }

        /**
         * Unused methods
         */
        public void mouseEntered(MouseEvent event) {}
        public void mouseExited(MouseEvent event) {}
        public void mousePressed(MouseEvent e) {}
        public void mouseReleased(MouseEvent e) {}
    }

	public void update(Observable o, Object arg) {
		if(arg == UpdatedType.COLOR){
			for(GPanel g : gPanels){
				g.setGradientColors(model.tertiaryColor, model.tertiaryColor);
				g.updateUI();
			}
			for(JLabel l : labels){
				l.setForeground(model.primaryTextColor);
				l.updateUI();
			}
		}
	}
}
