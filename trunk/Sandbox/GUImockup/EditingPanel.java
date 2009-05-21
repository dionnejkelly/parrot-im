import java.awt.*;
import javax.swing.*;

public class EditingPanel extends JPanel {

	public EditingPanel() {
		setLayout(new BorderLayout());
		
		JButton b1 = new JButton("SEND");
		JButton boldButton = new JButton("B");
		JButton italicsButton = new JButton("I");
		JButton underlineButton = new JButton("U");
		JButton colorButton = new JButton("Color");
		
		String[] fontList = {"Arial", "Times New Roman", "Comic Sans MS"};
		JComboBox fontSelect = new JComboBox(fontList);
		fontSelect.setEditable(true);
		
		SpinnerModel fontSizemodel = new SpinnerNumberModel(12, 6, 72, 1);
		JSpinner fontSize = new JSpinner(fontSizemodel);

		JTextArea txt1 = new JTextArea();
		txt1.setColumns(25);
		txt1.setRows(9);
		txt1.setLineWrap(true);
		
		JToolBar bar1 = new JToolBar();
		bar1.add(fontSelect);
		bar1.add(fontSize);
		bar1.add(boldButton);
		bar1.add(italicsButton);
		bar1.add(underlineButton);
		bar1.add(colorButton);
		
		//add to panel
		add(bar1, BorderLayout.NORTH);
		add(b1, BorderLayout.EAST);
		add(txt1, BorderLayout.CENTER);
	}
}
