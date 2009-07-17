package view.chatwindow;

import java.awt.Component;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;


public class ThemeOptionsComboBox extends JComboBox{
    private ImageIcon[] images = {new ImageIcon(this.getClass().getResource("/images/theme/metal.png")),
    						new ImageIcon(this.getClass().getResource("/images/theme/motif.png")),
    						new ImageIcon(this.getClass().getResource("/images/theme/windows.png")),
    						new ImageIcon(this.getClass().getResource("/images/theme/classic.png"))};
    private String[] themes = {"Metalic", "CDE/Motif", "Windows", "Windows Classic"};
    private static Integer[] intArray = {0,1,2,3};
    
	public ThemeOptionsComboBox(){
        //Create the combo box.
        super(intArray);
        ComboBoxRenderer renderer= new ComboBoxRenderer();
        this.setRenderer(renderer);
		
		//Load the pet images and create an array of indexes.
        
        for (int i = 0; i < themes.length; i++) {
            if (images[i] != null) {
                images[i].setDescription(themes[i]);
            }
        }
        
	}
	
	private class ComboBoxRenderer extends JLabel implements ListCellRenderer {
		private Font uhOhFont;
		
		public ComboBoxRenderer() {
			setOpaque(true);
			setHorizontalAlignment(LEFT);
			setVerticalAlignment(CENTER);
		}
		
		/*
		* This method finds the image and text corresponding
		* to the selected value and returns the label, set up
		* to display the text and image.
		*/
		public Component getListCellRendererComponent
			(JList list, Object value,int index,boolean isSelected,boolean cellHasFocus) {
			//Get the selected index. (The index param isn't
			//always valid, so just use the value.)
			int selectedIndex = ((Integer)value).intValue();
			
			if (isSelected) {
				setBackground(list.getSelectionBackground());
				setForeground(list.getSelectionForeground());
			} else {
				setBackground(list.getBackground());
				setForeground(list.getForeground());
			}
			
			//Set the icon and text.  If icon was null, say so.
			ImageIcon icon = images[selectedIndex];
			String pet = themes[selectedIndex];
			setIcon(icon);
			if (icon != null) {
				setText(pet);
				setFont(list.getFont());
			} else {
				setUhOhText(pet + " (no image available)", list.getFont());
			}
			
			return this;
		}
		
		//Set the font and text when no image was found.
		protected void setUhOhText(String uhOhText, Font normalFont) {
			if (uhOhFont == null) { //lazily create this font
				uhOhFont = normalFont.deriveFont(Font.ITALIC);
			}
			setFont(uhOhFont);
			setText(uhOhText);
		}
	}
}
