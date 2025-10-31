/**
 * Represents an Entity within the Game Engine
 * An Entity can be a character,vehicle, building or any other object
 * Each entity has a name and a transform (xPos, yPos, rotation, speed)
 * @see Transform
 * @author kbogott
 * @version 1.0
 */
public class Entity {

    /** Entity's name | Protected to extend visibility to child classes ( Actor, Obstacle )
     * All Entity's name are initialized to "" if no name is given during construction
     * @see #getName()
     * @see #setName(String)
     */
    protected String name;

    /** Entity's Transform : (xPos, yPos, rotation, speed)
     * Represents position, rotation, and speed inside Engine
     * @see Transform
     * @see #getTransform()
     */
    private Transform transform;

    /**
     * Sets the name of the Entity
     * @param name the new name of the Entity
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * Gets the name of Entity
     * @return name
     */
    public String getName(){
            return name;
        }

    /**
     *  Gets transform of Entity
     * @return transform
     */
    public Transform getTransform(){
            return this.transform;
        }

    /**
     * No argument constructor, initializes Entity with chosen default values.
     * Calls Transform Constructor to set default values within its transform
     * @see Transform
     */
        public Entity(){
                this.name = "";
                this.transform = new Transform();
        }
    /**
     * Creates a new Entity with name as parameter
     * Entity is created and given default transform values.
     *
     * @param name The new name of Entity
     * @see Transform
     */
        public Entity(String name){
            this.name =name;
            // With name already initialized, set the transform = to default transform of (0,0,0.0f,0)
            this.transform = new Transform();
        }

    /**
     * Creates Entity with transform and name as parameters
     * @param name Name of Entity
     * @param transform The transform data for the Entity
     */
    public Entity(String name, Transform transform){
                //set name to default ("")
                this.name =name;
                this.transform = transform;
            }
    /** public String toString()
     * Returns the string of the Entity
     * The returned string contains Entity's name, xPos, y Pos rotation, and speed.
     * Position, Rotation, Speed, are obtained from the Entity's Transform Object
     * __Example Output:__
     * -I am an Entity, my name is ""
     * -I am at 0, 0, facing 0.0 degrees, with a speed of 0
     *
     * @see Transform
     * @return a String representation on Entity
     */
    @Override
    public String toString(){
           return("-I am an Entity, my name is " + name + "\n-I am at " + transform.getxPos() + ", "
                   + transform.getyPos() + ", facing " + transform.getRotation() + " degrees, with a speed of " + transform.getSpeed());
        }
}// End Entity Class

