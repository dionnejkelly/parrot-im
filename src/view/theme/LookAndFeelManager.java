package view.theme;

import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.Window;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class LookAndFeelManager {
    static {
        try {
            Class.forName("org.jvnet.substance.SubstanceLookAndFeel");
            UIManager.LookAndFeelInfo substance =
                    new UIManager.LookAndFeelInfo("Substance",
                            "org.jvnet.substance.SubstanceLookAndFeel");
            UIManager.installLookAndFeel(substance);
        } catch (ClassNotFoundException e) {
            // Class is not present
        }

    }

    private static UIManager.LookAndFeelInfo[] THEMESAVAILABLE =
            UIManager.getInstalledLookAndFeels();

    public static void setLookAndFeel(final int option) {
        if (option > -1 && option < THEMESAVAILABLE.length) {
            Runnable runnable = new Runnable() {
                public void run() {
                    try {
                        UIManager.setLookAndFeel(THEMESAVAILABLE[option]
                                .getClassName());

                        // Update all existing frames
                        Frame[] existingFrames = Frame.getFrames();
                        for (Frame frame : existingFrames) {
                            SwingUtilities.updateComponentTreeUI(frame);
                            Window[] windows = frame.getOwnedWindows();
                            for (Window window : windows) {
                                SwingUtilities.updateComponentTreeUI(window);
                                window.validate();
                            }
                        }
                    } catch (Exception ex) {

                    }
                }
            };

            runOnAWT(runnable);

        }
    }

    public static void runOnAWT(Runnable runnable) {
        if (EventQueue.isDispatchThread()) {
            runnable.run();
        } else {
            try {
                EventQueue.invokeLater(runnable);
            } catch (Exception e) {

            }
        }
    }

}
