import java.awt.*;
import javax.swing.*;

public class DisplayPanel extends JPanel {

	public DisplayPanel() {
		setLayout(new BorderLayout());
		
		JTextArea txt1 = new JTextArea();
		txt1.setColumns(25);
		txt1.setRows(25);
		txt1.setEditable(false);
		txt1.setLineWrap(true);
		
		JLabel title = new JLabel("Conversation 1 (2 friends)                           view: ");
		
		JButton fullView = new JButton("Full");
		JButton simpleView = new JButton("Simple");
		
		JToolBar bar1 = new JToolBar();
		bar1.setFloatable(false);
		bar1.add(title);
		bar1.add(fullView);
		bar1.add(simpleView);
		
		//add to panel
		add(bar1, BorderLayout.NORTH);
		add(txt1, BorderLayout.CENTER);
	}
}