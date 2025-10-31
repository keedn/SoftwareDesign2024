/**
 * MazeArray represents a maze constructed from a 2D array of GridSpace objects.
 * It provides functionality to traverse the maze recursively, searching for an exit.
 * The maze is initialized from a string array where each character represents a
 *  different type of cell (wall, path, start, exit).
 */
public class MazeArray {
    GridSpace[][] mazeArray; // Maze Grid with gridspace objects
    boolean exitFound = false;

    /**
     * Constructor Will create the array by determining what each cell is and then
     * creating a new Array will the GridSpace objects (wall or path)
     * @param mazeStringArray array being created (String)
     */
    public MazeArray(String[] mazeStringArray){
        //Change String Array to array comprised of GridSpace objects
        changeStringToGridSpace(mazeStringArray);
    }

    /**
     * Traverses Maze Recursively
     * Follows Algorithm:
     * from current location, try to move one space in any possible direction( down right up left )
     * If it possible to move in at LEAST ONE direction, call mazeTraversal recursively
     * passing new spot on maze as current spot
     * @return True of False if maze has open spot to travel to
     * @param row row being checked
     * @param column coloumn being checked
     */
    public boolean traverseMazeRecursively(int row, int column){
        // Check if cell is outside current Maze (invalid spot)
        if(row < 0 || row >= mazeArray.length || column < 0 || column >= mazeArray[0].length){
            return false;
        }

        // Get Current Cell
        GridSpace cell = mazeArray[row][column];

        // Check if Cell is Exit and print maze with path created
        if(cell.isExit()){
            exitFound = true; // found exit
            return true;
        }

        // If Wall or Already been to return false
        if(cell.isWall() || cell.beenTraveledTo == true){
            return false;
        }

        // If Not exit or not been visted = new cell
        cell.beenTraveledTo = true;
        //System.out.println("Visiting: (" + row + ", " + column + "): " + cell.toString());
        cell.newlyVisited();


        // TRY TO MOVE IN ALL DIRECTIONS IF POSSIBLE CALL METHOD AGAIN

        // Try moving DOWN
        if(traverseMazeRecursively(row + 1,column)){
            return true;
        }

        // Try moving UP
        if(traverseMazeRecursively(row - 1,column)){
            return true;
        }

        // Try moving RIGHT
        if(traverseMazeRecursively(row ,column + 1)){
            return true;
        }

        // Try moving LEFT
        if(traverseMazeRecursively(row,column - 1)){
            return true;
        }

        // If all paths lead to dead ends BackTrack

        // Reset cell and return
        cell.beenTraveledTo = false;
        cell.reset();

        return false;
    }

    /**
     * Method That runs the traverse maze method and determines if an exit can be found or not
     * @param startRow Starting Row in array
     * @param startColumn Starting Column in array
     */
    public void traverse(int startRow, int startColumn){
        // Traverse Through Array
        traverseMazeRecursively(startRow,startColumn);

        // If exitFound is never changed(no exit)
        if(!exitFound){
            System.out.println("~~~~~~~~~~~~~~~");
            System.out.println("No Exit found");
            System.out.println("~~~~~~~~~~~~~~~");

            printMaze(); // print maze
        }
        // Exit found
        else{
            System.out.println("~~~~~~~~~~~~~~~");
            System.out.println("Exit found!");
            System.out.println("~~~~~~~~~~~~~~~");

            printMaze(); // print maze
        }
    }

    /**
     * Changes String Array to Array with each cell as a GridSpace Object
     * @param mazeStringArray String Array
     * @return New Maze Array with each cell as GridSpace Object
     */
    public GridSpace[][] changeStringToGridSpace(String[] mazeStringArray){
        // Get Column, Row
        int column = mazeStringArray[0].length(); // Get Length of First String (top of array length = 12)
        int row = mazeStringArray.length;         // Get how many rows, (number of strings in array [12])
        mazeArray = new GridSpace[row][column];   //Create mazeArray


        // Loop through each string and set the correct GridSpace object to said string (wall = #)(path = .)
        for (int i = 0; i < row; i++) {                                 // EX "# . . . # . . . . . . #"
            for (int j = 0; j < column; j++) {

                // Get character at each GridSpace
                char gridspace = mazeStringArray[i].charAt(j);

                // Wall
                if(gridspace =='#'){
                    mazeArray[i][j] = new GridSpace("#");
                }
                // Path
                if(gridspace =='.'){
                    mazeArray[i][j] = new GridSpace(".");
                }
                // Starting Spot
                if(gridspace =='S'){
                    mazeArray[i][j] = new GridSpace("S");
                }
                // Exit Spot
                if(gridspace =='E'){
                    mazeArray[i][j] = new GridSpace("E");
                }

            }
        }


        return mazeArray;
    }


    /**
     * Prints the current state of the maze
     */
    public void printMaze() {
        System.out.println("___________MAZE___________");

        for (GridSpace[] row : mazeArray) {
            for (GridSpace space : row) {
                System.out.print(space + " ");
            }
            System.out.println();
        }
        System.out.println("__________________________");
    }


}
