package view.styles;

import java.awt.Color;

/**
 * 
 * This window colours class sets the standard colours to be
 * used throughout the program as global variables. This
 * will allow simple adjustments of window colours by only
 * modifying this one class
 * 
 * @author Jordan Fox
 *
 */
public class WindowColors {
	public static Color PRIMARY_COLOR_MED = new Color(87, 166, 196);
	public static Color PRIMARY_COLOR_LT = new Color(244, 244, 244);
	public static Color PRIMARY_COLOR_DARK = new Color(12, 69, 91);
	
	public static Color SECONDARY_COLOR_MED = new Color(233, 195, 53);
	public static Color SECONDARY_COLOR_LT = new Color(238, 222, 136);
	public static Color SECONDARY_COLOR_LT2 = new Color(238, 222, 136).brighter();
	public static Color SECONDARY_COLOR_DARK = new Color(145, 124, 17).darker().darker();
	
	public static Color TERTIARY_COLOR_MED = new Color(136, 235, 93);
	public static Color TERTIARY_COLOR_LT = new Color(136, 235, 93).brighter();
	public static Color TERTIARY_COLOR_DARK = new Color(42, 124, 7);
}
