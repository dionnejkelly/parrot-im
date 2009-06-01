package buddylist;

import java.awt.*;

import javax.swing.*;

public class accInfo extends JPanel
{
	/* TODO AccInfo has:
	 * East -- Avatar (and avatar settings)
	 * Center -- New embedded Panel
	 * 					Top: User Display name
	 * 					Bottom: Status, and status messages (if applicable)			
	 */
	
	public accInfo()
	{
		this.setBackground(Color.DARK_GRAY);
		this.setPreferredSize(new Dimension(300,100));
		setLayout(new BorderLayout());
		
	}

}
