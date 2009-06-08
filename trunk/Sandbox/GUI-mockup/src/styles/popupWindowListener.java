package styles;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

import mainwindow.guestAccountFrame;
import mainwindow.mainwindow;
import mainwindow.manageAccountFrame;

public class popupWindowListener implements WindowListener {
	mainwindow mainFrame;
	JFrame popup;
	
	public popupWindowListener(mainwindow frame, manageAccountFrame popup){
		mainFrame = frame;
		this.popup = popup;
	}
	
	public popupWindowListener(mainwindow frame, guestAccountFrame popup){
		mainFrame = frame;
		this.popup = popup;
	}


	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void windowClosed(WindowEvent e) {
		mainFrame.setEnabled(true);
		popup.setVisible(false);
		System.out.println("Entered");
	}

	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

}
