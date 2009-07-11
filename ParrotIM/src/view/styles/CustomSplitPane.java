package view.styles;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class CustomSplitPane extends JPanel {
	private JPanel leftPanel, rightPanel, buttonPanel;
	private JButton resizeButton;
	private boolean isMax;
	
	private final ImageIcon rightArrow = new ImageIcon(this.getClass().getResource(
    "/images/buddylist/right.png"));
	private final ImageIcon leftArrow = new ImageIcon(this.getClass().getResource(
    "/images/buddylist/left.png"));
	
	public CustomSplitPane() {
		//Panel Properties
		setLayout(new BorderLayout());
		
		isMax = true;
		
		//button panel properties
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new BorderLayout());
		resizeButton = new JButton(leftArrow);
		resizeButton.setToolTipText("Click to hide Side Panel");
		resizeButton.setPreferredSize(new Dimension(22,5));
		buttonPanel.add(resizeButton, BorderLayout.WEST);
		resizeButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if(isMax == true){
					minimizeLeft();
				}else{
					maximizeLeft();
				}
			}
		});
		
		//add components
		add(buttonPanel, BorderLayout.CENTER);
	}
	
	//Getters
	public JPanel getLeftPanel(){
		return leftPanel;
	}
	
	public JPanel getRightPanel(){
		return rightPanel;
	}
	
	//Setters
	public void setLeftPanel(JPanel panel){
		leftPanel = panel;
		add(leftPanel, BorderLayout.WEST);
	}
	
	public void setRightPanel(JPanel panel){
		rightPanel = panel;
		buttonPanel.add(rightPanel, BorderLayout.CENTER);
	}
	
	private void minimizeLeft(){
		isMax = false;
		resizeButton.setToolTipText("Click to show Side Panel");
		resizeButton.setText(" ");
		remove(leftPanel);
		
		System.out.println("Left");
		resizeButton.setIcon(rightArrow);
	}
	
	private void maximizeLeft(){
		isMax = true;
		resizeButton.setToolTipText("Click to hide Side Panel");
		resizeButton.setText("");
		add(leftPanel, BorderLayout.WEST);
		
		System.out.println("Right");
		resizeButton.setIcon(leftArrow);
		
	}
	
	public boolean isMaximized(){
		return isMax;
	}
}
