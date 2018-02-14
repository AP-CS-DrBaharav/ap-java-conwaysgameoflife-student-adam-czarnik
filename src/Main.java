/***********
 * 
 *  There are 3 files for the program:
 *    Main.java - This file. 
 *    Display.java - Taking care of display.
 *    ConwayGame.java -  All the logic happens here, and this is the ONLY
 *                    file you will need to change.
 * 
 * 
 * 
**************/
import javax.swing.JFrame;



// NO NEED to change anything here!

public class Main 
{
	
	public static void main(String[] args)
	{
		final int DISPLAY_WIDTH = 600 ;
		final int DISPLAY_HEIGHT = 500 ;
		
		JFrame frame = new JFrame();
		
		frame.setSize(DISPLAY_WIDTH, DISPLAY_HEIGHT);
		frame.setTitle("Conway's Game of Life");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//frame.setLayout(null); // we are using absolute location
		
		Display display = new Display(DISPLAY_WIDTH, DISPLAY_HEIGHT);
		
		frame.add(display);				
		frame.setVisible(true);
		
	}
	
	
}
