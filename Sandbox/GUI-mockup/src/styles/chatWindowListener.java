package styles;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

import mainwindow.guestAccountFrame;
import mainwindow.mainwindow;
import mainwindow.manageAccountFrame;

import model.*;

public class chatWindowListener implements WindowListener {
	Model model;
	
	public chatWindowListener(Model model){
		this.model = model;
	}

	public void windowClosed(WindowEvent e) {
		model.chatWindowOpen = false;
	}

	public void windowClosing(WindowEvent e) {
		model.chatWindowOpen = false;
	}
	public void windowDeactivated(WindowEvent e) {}
	public void windowDeiconified(WindowEvent e) {}
	public void windowIconified(WindowEvent e) {}
	public void windowOpened(WindowEvent e) {}
	public void windowActivated(WindowEvent e) {}
}
