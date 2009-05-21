import java.awt.*;
import javax.swing.*;

public class sidePanel extends JPanel {

	public sidePanel() {
		setLayout(new BorderLayout());
		
		JLabel title = new JLabel("Friends in this conversation: ");
		
		//add to panel
		add(title, BorderLayout.NORTH);
	}
}
