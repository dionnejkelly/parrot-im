package view.styles;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class CustomSplitPane extends JPanel {
	private JPanel leftPanel, rightPanel, buttonPanel;
	private JButton resizeButton;
	private boolean isMax;
	
	public CustomSplitPane() {
		//Panel Properties
		setLayout(new BorderLayout());
		
		isMax = true;
		
		//button panel properties
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new BorderLayout());
		resizeButton = new JButton("<");
		resizeButton.setPreferredSize(new Dimension(15,5));
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
		resizeButton.setText(">");
		remove(leftPanel);
	}
	
	private void maximizeLeft(){
		isMax = true;
		resizeButton.setText("<");
		add(leftPanel, BorderLayout.WEST);
	}
	
	public boolean isMaximized(){
		return isMax;
	}
}
