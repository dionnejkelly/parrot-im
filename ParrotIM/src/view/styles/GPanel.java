package view.styles;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

/**
 * 
 * GPanel is a special type of JPanel. The only difference between
 * a GPanel and a JPanel is that a GPanel can have a vertical
 * gradient background.
 * 
 * @author Jordan Fox
 *
 */
public class GPanel extends JPanel{
	private Color color1, color2;
	
	public void setGradientColors(Color color1, Color color2){
		this.color1 = color1;
		this.color2 = color2;
	}
	
	protected void paintComponent( Graphics g ) 
	{
		//calls to the supre class
	    if ( !isOpaque( ) )
	    {
	        super.paintComponent( g );
	        return;
	    }
	 
	    Graphics2D g2d = (Graphics2D)g;
	    
	    //paints custom background
	    int w = getWidth( );
	    int h = getHeight( );
	     
	    // Paint a gradient from top to bottom
	    GradientPaint gp = new GradientPaint(
	        0, 0, color1,
	        0, h, color2 );

	    g2d.setPaint( gp );
	    g2d.fillRect( 0, 0, w, h );
	    
	    //calls to the super class
	    setOpaque( false );
	    super.paintComponent( g );
	    setOpaque( true );
	}
}
