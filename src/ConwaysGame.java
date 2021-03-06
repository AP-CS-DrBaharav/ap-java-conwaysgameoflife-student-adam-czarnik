/*

This is the file where you need to change things.

Three places:

1. Logic: search for  the phrase    Place1   below.

2. Adding a new shape: search for  the phrase    Place2   below.

3. Adding a new shape: search for  the phrase    Place3   below.


To submit, you will need to include 3 images:
1. Similar to my 10CellRow_Step_100.png, only in step 120.
2. Similar to my Glider_Step_200.png, only at step 220.
3. Image of your own structure implemented!





*/

public class ConwaysGame {

    private int stepCounter;

    // We need to count neighbors, so the easiest thing is to keep them in Int
    // 1, true   -> Alive
    // 0, false  -> Dead
    private static int[][] A;
    private static int[][] B;

    // see the interplay of A, B and cellsNow and cellsNext later on below.
    private int[][] cellsNow;
    private int[][] cellsNext;
    private int[][] tmp;

    private int rows;
    private int cols;

    
    // -- Start of Place2
    // These are used also by Display, to disaply all possible options
    // These are set in setPattern() below
    // Give a new name to your shape, and put it here.
    public final static String[] modelNames = new String[]
			{"Clear","Glider","Exploder","10 Cell Row","Adam's New Shape"};    
    
    // -- End of Place2
    
    public ConwaysGame(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;

        A = new int[rows][cols];
        B = new int[rows][cols];

        cellsNow = A;
        cellsNext = B;
        
        // The idea:
        // A and B are the only ones with actual memory space.
        // cellsNow and cellsNext are only 'pointers' to which one is which,
        // and we move between them every turn.

        stepCounter = 0;
    }

    public void step() {

        // -- Start of Place1
        // apply the rules
        
        /*
        *  The rules:
        *  Each Cell has 8 neighbors.
        *	For a space that is 'populated':
        *           Each cell with one or no neighbors dies, as if by solitude.
        *           Each cell with four or more neighbors dies, as if by over-population.
        *           Each cell with two or three neighbors survives.
        *	For a space that is 'empty' or 'un-populated'
        *           Each cell with three neighbors becomes populated.
        *
        *
        *  Keep in mind: Using wrap-around rules!!
        *
        *
        *
        *  Your code below should look at the values in cellsNow, and set the
        *  approriate values in cellsNext.
        *
        *  Keep in mind:
        *   cell value is 1, true   -> Alive
        *   cell value is 0, false  -> Dead
        * For example, let's say the values in cellsNow around the point [ii][jj] are
        *    
        *     0 1 0
        *     1 1 0
        *     0 0 0
        *
        *   :  the cell is Alive (center one, cellsNow[ii][jj]==1), and has 2 neighbours ==>
        *     cellNext[ii][jj] = 1;
        * 
        *
        *  Hint: Just summing all the neighbours will make life easy for you.
        */
        clearCells(cellsNext);
        for (int rr=0; rr<cellsNow.length; ++rr) {
            for (int cc=0; cc<cellsNow[0].length; ++cc) {
                int sum = wrapAround(rr-1, cc-1) + wrapAround(rr-1, cc) + wrapAround(rr-1, cc+1) + wrapAround(rr, cc-1) + wrapAround(rr, cc+1) + wrapAround(rr+1, cc-1) + wrapAround(rr+1, cc) + wrapAround(rr+1, cc+1);
                if (cellsNow[rr][cc] == 1) {
                    if (sum < 2) {
                        cellsNext[rr][cc] = 0;
                    } else if (sum > 3) {
                        cellsNext[rr][cc] = 0;
                    } else {
                        cellsNext[rr][cc] = 1;
                    }
                } else {
                    if (sum == 3) {
                        cellsNext[rr][cc] = 1;
                    }
                }
            }
        }
        
        
        // -- END of Place1

        
        // Flip the arrays now.
        stepCounter++;
	tmp = cellsNow;
        cellsNow = cellsNext;
	cellsNext  = tmp;
    }
    
    private int wrapAround(int row, int col) {
        if (row < 0) {
            row = cellsNow.length-1;
        }
        if (row >= cellsNow.length) {
            row = 0;
        }
        if (col < 0) {
            col = cellsNow[0].length-1;
        }
        if (col >= cellsNow[0].length) {
            col = 0;
        }
        return cellsNow[row][col];
    }

    private void clearCells(int[][] array) {
        for (int ii = 0; ii < rows; ii++) {
            for (int jj = 0; jj < cols; jj++) {
                array[ii][jj] = 0;
            }
        }
    }

    public int getCell(int row, int col) {
        return cellsNow[row][col];
    }

    public void setCell(int row, int col, int val) {
        cellsNow[row][col] = val;
    }

    public void flipCell(int row, int col) {
        cellsNow[row][col] = 1 - cellsNow[row][col];
    }

    public int getStepCounter() {
        return stepCounter;
    }

    // -- Start of Place3
    // Follow the idea below, and put a new pattern.
    // all patterns are defined in modelNames
    
    public void setPattern(String pattern) {
        
        // Clear everything
        clearCells(cellsNow);
        stepCounter = 0;

        if (pattern.equals("Clear")) ;

        if (pattern.equals("Glider")) {
            cellsNow[20][20] = 1;
            cellsNow[20][21] = 1;
            cellsNow[20][22] = 1;
            cellsNow[19][22] = 1;
            cellsNow[18][21] = 1;
        }

        if (pattern.equals("Exploder")) {
            cellsNow[20][20] = 1;
            cellsNow[20][22] = 1;
            cellsNow[20][24] = 1;
            cellsNow[21][20] = 1;
            cellsNow[21][24] = 1;
            cellsNow[22][20] = 1;
            cellsNow[22][24] = 1;
            cellsNow[23][20] = 1;
            cellsNow[23][24] = 1;
            cellsNow[24][20] = 1;
            cellsNow[24][22] = 1;
            cellsNow[24][24] = 1;
        }
        if (pattern.equals("10 Cell Row")) {
            for (int jj = 0; jj < 10; jj++) {
                cellsNow[30][30 + jj] = 1;
            }
        }
        if (pattern.equals("Adam's New Shape")) {
            // A
            cellsNow[20][4] = 1;
            cellsNow[19][4] = 1;
            cellsNow[18][4] = 1;
            cellsNow[17][4] = 1;
            cellsNow[16][4] = 1;
            cellsNow[15][4] = 1;
            cellsNow[14][5] = 1;
            cellsNow[14][6] = 1;
            cellsNow[14][7] = 1;
            cellsNow[15][8] = 1;
            cellsNow[16][8] = 1;
            cellsNow[17][8] = 1;
            cellsNow[18][8] = 1;
            cellsNow[19][8] = 1;
            cellsNow[20][8] = 1;
            cellsNow[17][5] = 1;
            cellsNow[17][6] = 1;
            cellsNow[17][7] = 1;
            // D
            cellsNow[20][12] = 1;
            cellsNow[19][11] = 1;
            cellsNow[18][12] = 1;
            cellsNow[20][13] = 1;
            cellsNow[19][13] = 1;
            cellsNow[18][13] = 1;
            cellsNow[17][13] = 1;
            cellsNow[16][13] = 1;
            cellsNow[15][13] = 1;
            // A
            cellsNow[20][17] = 1;
            cellsNow[19][16] = 1;
            cellsNow[18][17] = 1;
            cellsNow[20][18] = 1;
            cellsNow[19][18] = 1;
            cellsNow[18][18] = 1;
            // M
            cellsNow[20][21] = 1;
            cellsNow[19][21] = 1;
            cellsNow[18][21] = 1;
            cellsNow[18][22] = 1;
            cellsNow[19][23] = 1;
            cellsNow[18][24] = 1;
            cellsNow[18][25] = 1;
            cellsNow[19][25] = 1;
            cellsNow[20][25] = 1;
        }
    }
    // -- End of Place3

}
