package buddylist;

import java.awt.*;
import javax.swing.*;

public class buddyPanel extends JPanel
{
	/*TODO: BUDDY PANEL HAS 
	 * Center: Buddy List
	 * South: Buddy Options 
	 */
	JToolBar options;
	
	public buddyPanel()
	{
		setLayout(new BorderLayout());
		
		//Search bar
        
        
        add(options, BorderLayout.SOUTH);
	}
	
	public JToolBar OptionsBar(){
		JToolBar options = new JToolBar();
		
        JTextField search = new JTextField(); 
        JButton add = new JButton("Add");
        JButton remove = new JButton("Del");
        JButton block = new JButton("Blk");
        
        //add components
        options.add(search);
        
        return options;
	}
}
