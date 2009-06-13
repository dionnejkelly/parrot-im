package view.styles;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

import view.mainwindow.mainwindow;

public class popupWindowListener implements WindowListener {
	mainwindow mainFrame;
	JFrame popup;
	
	public popupWindowListener(JFrame frame, JFrame popup){
		mainFrame = (mainwindow)frame;
		this.popup = popup;
	}

	public void windowClosed(WindowEvent e) {
		mainFrame.setEnabled(true);
		mainFrame.setAlwaysOnTop(true);
	    mainFrame.setAlwaysOnTop(false);
	}

	public void windowClosing(WindowEvent e) {}
	public void windowDeactivated(WindowEvent e) {}
	public void windowDeiconified(WindowEvent e) {}
	public void windowIconified(WindowEvent e) {}
	public void windowOpened(WindowEvent e) {}
	public void windowActivated(WindowEvent e) {}
}
