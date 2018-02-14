
// NO NEED to change anything here!



import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JComponent;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JComboBox;

import java.awt.Color;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.awt.Rectangle;

/*
 * 
 * 
 *   We will keep things NOT too flexible here,
 *   so as to make the design and code simpler.
 * 
 *  Things will be in fixed-location on screen.
 *  This is called Absolute-positioning (as compared to using layouts)
 *  http://docs.oracle.com/javase/tutorial/uiswing/layout/none.html
 *  All the definition are grouped below
 *  The graphic tries to follow the look of:
 *  http://www.bitstorm.org/gameoflife/ 
 * 
 *   Display is responsible for drawing the game.
 *   Display :
 *   	Creates and manages interactions with the game object
 *      Creates and manages the items display (getting the Frame from main)
 *      Handles all non-button events. That means, getting mouse clicks, 
 *      determining which cell they hit, and managing response.
 * 
 * 
 */


@SuppressWarnings("serial")
public class Display extends JComponent {

	int frameWidth, frameHeight;
	boolean loopModeOn = false;
	int stepCounter = 0 ;

	private final static long STEP_TIME_MILLIS = 150 ; // time in milliseconds

	private final static int CELL_COLS = 50 ;   
	private final static int CELL_ROWS = 40 ;
	
	private final static int CELL_SIDE_PIXELS = 10 ;
	private final static int CELL_TOP_X = 20 ;
	private final static int CELL_TOP_Y = 20 ;
	
	private final static Color COLOR_ALIVE = Color.YELLOW ;
	private final static Color COLOR_DEAD  = Color.GRAY ;
	private final static Color COLOR_GRID  = Color.WHITE ;
	
	// Graphics locations
	private final Rectangle MODEL_RECT ;
	private final Rectangle NEXT_RECT ;
	private final Rectangle START_RECT ;
	private final Rectangle STEP_RECT ;
	
	private final JButton startButton = new JButton();
	private final JButton nextButton  = new JButton();
	private final JLabel  stepLabel   = new JLabel(); 

        
        private final JComboBox<String> modelChooser  = new JComboBox<>(ConwaysGame.modelNames);
	

	
	private  ConwaysGame game ;	
	
	
	public Display(int frameWidth, int frameHeight)
	{
		this.frameWidth = frameWidth ;
		this.frameHeight = frameHeight ;

                MODEL_RECT  = new Rectangle(20 ,frameHeight-70,200,20 );
                NEXT_RECT   = new Rectangle(250,frameHeight-70,80,20);
                START_RECT  = new Rectangle(340,frameHeight-70,80,20);
                STEP_RECT   = new Rectangle(frameWidth-50,frameHeight-70,40,20);

                
                
                
                
		// create a game
		game = new ConwaysGame(CELL_ROWS, CELL_COLS);	
		
		putButtons();
		
		// The mouse listener is for event on the Grid.
		// for all the rest, there are individual listeners
		class myMouseListener implements MouseListener
		{

			@Override
			public void mousePressed(MouseEvent e) 
			{
				int x = e.getX();
				int y = e.getY();
				
				Rectangle grid = new Rectangle(
						CELL_TOP_X,CELL_TOP_Y,
						CELL_COLS * CELL_SIDE_PIXELS,
						CELL_ROWS * CELL_SIDE_PIXELS);

				if ( grid.contains(x, y))
				{
					int ii = (x-CELL_TOP_X)/CELL_SIDE_PIXELS ;
					int jj = (y-CELL_TOP_Y)/CELL_SIDE_PIXELS ;
					game.flipCell(jj,ii);
					
					repaint();
				}
			}
			
			public void mouseClicked(MouseEvent e) {}
			public void mouseReleased(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
		
		}
		
		
		myMouseListener listener = new myMouseListener();
		addMouseListener(listener);
		
				
		init();
	}
	
	private void init()
	{
		

		
	}
	
	public void paintComponent(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;
		
		drawCells(g2);
		drawGrid(g2);
		drawButtons();
		
		if (loopModeOn)
		{
			try 
			{
				Thread.sleep(STEP_TIME_MILLIS);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
			game.step() ;
			
			repaint();
		}
		
	}
	
	private void drawGrid(Graphics2D g2)
	{
		g2.setColor(COLOR_GRID);

		int x1 = CELL_TOP_X ;
		int x2 = CELL_TOP_X + CELL_COLS * CELL_SIDE_PIXELS ;
		int y1,y2;
		
		for (int ii=0; ii<=CELL_ROWS ;ii++ )
		{
			y1 = CELL_TOP_Y + ii*CELL_SIDE_PIXELS;
			g2.drawLine(x1, y1, x2, y1);
		}

		y1 = CELL_TOP_Y ;
		y2 = CELL_TOP_Y + CELL_ROWS * CELL_SIDE_PIXELS ;
		for (int jj=0; jj<=CELL_COLS ;jj++ )
		{
			x1 = CELL_TOP_X + jj*CELL_SIDE_PIXELS;
			g2.drawLine(x1, y1, x1, y2);
		}
		
	}

	private void drawCells(Graphics2D g2)
	{
		
		for (int ii=0; ii<CELL_ROWS ; ii++)
		{
			int ytop = CELL_TOP_Y + ii * CELL_SIDE_PIXELS;
		
			for (int jj=0; jj<CELL_COLS; jj++)
			{
				
				int xleft = CELL_TOP_X + jj * CELL_SIDE_PIXELS;
				Color c =  ( game.getCell(ii, jj) == 1 ) ? COLOR_ALIVE : COLOR_DEAD ;

				g2.setColor(c);
				g2.fillRect(xleft, ytop, CELL_SIDE_PIXELS, CELL_SIDE_PIXELS);
			}
		}
		
	}
	private void drawButtons()
	{
		stepLabel.setText( Integer.toString(game.getStepCounter()));
	}
	

	private void putButtons()
	{

		startButton.setText("Start");
		startButton.setBounds(START_RECT);
		class StartListener implements ActionListener
		{
			public void actionPerformed(ActionEvent e)
			{
				loopModeOn = !loopModeOn;
				String str = (loopModeOn) ? "Stop" : "Start" ;
				startButton.setText(str);
				repaint();
			}
		}
		startButton.addActionListener(new StartListener());
		startButton.setVisible(true);
		add(startButton);
		
		nextButton.setText("Next");
		nextButton.setBounds(NEXT_RECT);
		class NextListener implements ActionListener
		{
			public void actionPerformed(ActionEvent e)
			{
				game.step() ;
				repaint();
			}
		}
		nextButton.addActionListener(new NextListener());
		nextButton.setVisible(true);
		add(nextButton);
		
		stepLabel.setText(Integer.toString(game.getStepCounter()));
		stepLabel.setBounds(STEP_RECT);
		stepLabel.setVisible(true);
		add(stepLabel);
		
		modelChooser.setBounds(MODEL_RECT);
		
		// get selected item
		class ComboListener implements ActionListener
		{
			public void actionPerformed(ActionEvent e)
			{	
				String str = (String) modelChooser.getSelectedItem();
				game.setPattern(str);
				repaint();
			}
		}
		
		modelChooser.addActionListener(new ComboListener());
		modelChooser.setVisible(true);
		add(modelChooser);
	}
	
	

	
}
