/**
 * Class that holds all cell information and relevant methods to help Traverse through the Array
 */
public class GridSpace {
    public boolean beenTraveledTo = false;
    public String gridspace; // Store current character in cell
    private String OGspace; // stores original space -> so it can be switched back when backtracking
    /**
     * Constructor for Grid
     * @param what What grid will be
     */
    public GridSpace(String what){
        this.gridspace = what; // set text to what got passed in
        this.beenTraveledTo = false;
        this.OGspace = what; // Store old what to print later when backtracking
    }

    /**
     * Return True if current space is a Wall
     * @return True if Path False if not
     */
    public boolean isWall(){
        return gridspace.equals("#");
    }


    /**
     * Check if Space is Exit Spot
     */
    public boolean isExit(){
        return gridspace.equals("E");

    }
    public void win(){
        gridspace ="W";
    }


    /**
     * Switch's GridSpace to x and beenTraveled True
     */
    public void newlyVisited(){
        this.beenTraveledTo = true;
        this.gridspace = "\u001B[32mx\u001B[0m"; // after spot has been visited mark an x to remember path. AND SET COLOR TO GREEN :)
    }
    /**
     * Resets Space, used with recursive backtracking, if no spaces are found go backwards
     * sets space char to old char
     */
    public void reset(){
        this.beenTraveledTo =false;
        this.gridspace =OGspace;
    }

    /**
     * @return Return String value of gridspace
     */
    public String toString(){
        return gridspace;
    }

}
