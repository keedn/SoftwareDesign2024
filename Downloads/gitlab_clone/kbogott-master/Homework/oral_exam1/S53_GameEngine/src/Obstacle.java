/**
 * Represents an Entity with additional value of size.
 * Obstacle is a type of Entity that has a size.
 * Extends the Entity class to inherit its name and Transform properties.
 *
 * @author kbogott
 * @version 1.0
 * @see Entity
 * @see Transform
 */
public class Obstacle extends Entity {
    /**
     * Size of Obstacle Entity
     * Integer value representing the size.
     * @see #setSize(int)
     * @see #getSize()
     */

    private int size = 1;

    /**
     * Returns size of Obstacle
     * @return size |  Obstacle size
     */
    public int getSize() {
        return size;
    }

    /**
     * Sets the size of Obstacle
     * @param size | new Obstacle size
     *
     * @throws IllegalArgumentException if the size is negative or zero throw an exception
     */
    public void setSize(int size) {
        // check for if size is negative or 0, throw  error
        if(size < 0){
            throw new IllegalArgumentException("Size cannot be NEGATIVE or ZERO");
        }
        this.size = size;
    }

    /**
     * No Argument Constructor
     * Initializes Obstacle object with default values
     */
    public Obstacle(){
        this.name = "Default Obstacle";
    }

    /**
     * Constructor for Obstacle Object with a name as a parameter
     * Initializes Obstacle with a specified name and a default size.
     * @param name | name of Obstacle
     */
    public Obstacle(String name){
        this.name = name;
    }
    

    /**
     * Constructor for Obstacle with name and size.
     * Initializes Obstacle with a specified name and size.
     * @param name the name of Obstacle
     * @param size the size of Obstacle
     */
    public Obstacle(String name, int size){
        this.name = name;
        this.size = size;
    }

    /**
     * Constuctor with name and transform as parameters
     * @param name | Obstacles Name
     * @param transform | Obstacles new Transform
     * Calls Parent Constructor from Entity Class
     * @see Entity
     */
    public Obstacle(String name, Transform transform){
        super(name,transform);
    }

    /**
     * Constructor with name, transform, and size and parameters
     * Calls Parent Constructor from Entity class
     * @param name Name of Obstacle
     * @param transform Transform of Obstacle
     * @param size Size of Obstacle
     * @see Entity 
     */
    public Obstacle(String name, Transform transform, int size) {
        super(name, transform);
        this.size = size;
    }

    /**
     * Returns string representation of Obstacle Object
     * The string calls the super.toString() to include information from its superclass Entity
     * __Example Output: __
     * -I am an Entity, my name is Tower
     * -I am at 0, 0, facing 0.0 degrees, with a speed of 0
     * -My size is 0
     *
     * @see Entity
     * @see Transform
     * @return a string representation of Obstacle
     */
    @Override
    public String toString(){
        //call super to get previous information from Entity + size of obstacle
        return(super.toString() + "\n-My size is " + size);
    }

}//End Obstacle Class
