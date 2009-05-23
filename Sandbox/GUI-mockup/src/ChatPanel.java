import java.awt.*;
import javax.swing.*;

public class ChatPanel extends JPanel {

	public ChatPanel() {
		setLayout(new BorderLayout());
		
		JSplitPane sPane = new JSplitPane();
		sPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		sPane.setTopComponent(new DisplayPanel());
		sPane.setBottomComponent(new EditingPanel());
		
		add(sPane, BorderLayout.CENTER);
	}
}