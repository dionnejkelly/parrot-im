package view.styles;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ProgressMonitorScreen implements ActionListener {
    
    static ProgressMonitor pbar;
    public static int counter = 0;
    
    public ProgressMonitorScreen() {
//        super("File Progressing Monitor");
//        setSize(250,100);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        pbar = new ProgressMonitor(null, "Monitoring Progress",
                "Initializing . . .", 0, 100);
        
        
        // Fire a timer every once in a while to update the progress.
        Timer timer = new Timer(500, this);
        timer.start();
       // setVisible(true);
        
    }
    
    
    
    public static void main(String args[]) {
        ProgressMonitorScreen progress = new ProgressMonitorScreen();
        
        while(progress.counter != 100) {
        	try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            progress.counter += 2;
        }
        
    }
    
    public void actionPerformed(ActionEvent e) {
        // Invoked by the timer every half second. Simply place
        // the progress monitor update on the event queue.
        SwingUtilities.invokeLater(new Update());
    }
    
    class Update implements Runnable {
    	
        public void run() {
            if (pbar.isCanceled()) {
                pbar.close();
                System.exit(1);
            }
            pbar.setProgress(counter);
            pbar.setNote("Operation is "+counter+"% complete");    
        }
    }
} 
