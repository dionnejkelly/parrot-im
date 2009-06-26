/* TextListCellRenderer.java
 * 
 * Programmed By:
 *     Vera Lukman
 *     
 * Change Log:
 *     2009-June-25, VL
 *         Initial write. Created for ChatLog window log viewer.
 *         
 * Known Issues:
 *     none
 *     
 * Copyright (C) 2009  Pirate Captains
 * 
 * License: GNU General Public License version 2.
 * Full license can be found in ParrotIM/LICENSE.txt.
 */

package view.styles;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 * This class sets the JList style of text JList on ChatLogPanel.
 * It makes the "illusion" so that the JList looks like a textEditor.
 *
 * This class inherits ListCellRenderer methods and variables.
 */
public class TextListCellRenderer extends JLabel implements ListCellRenderer{

	/**
	 * TextListCellRenderer constructor.
	 */
	public TextListCellRenderer()
	{
		setOpaque( true );
	}
 
	/**
	 * It returns a cell component which background white and foreground are black at all time
	 */
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus)
	{
		setText(value.toString());
		setBackground(Color.WHITE);
		setForeground(Color.BLACK);
		return this;
	}

}
