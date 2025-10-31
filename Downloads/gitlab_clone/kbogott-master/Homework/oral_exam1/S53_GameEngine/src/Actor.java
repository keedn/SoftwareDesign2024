/**
 * Represents an Entity with additional value of health.
 * Obstacle is a type of Entity that has health.
 * Extends the Entity class to inherit its name and Transform properties.
 *
 * @author kbogott
 * @version 1.0
 * @see Entity
 * @see Transform
 */
public class Actor extends Entity {
    /**
     * Health of Actor Entity
     * Integer value representing the size.
     * Default set to 1.
     * @see #setHealth(int)
     * @see #getHealth()
     */
    private int health; // default will be set to 1

    /**
     * Returns health of Actor
     * @return health | new health of actor
     */
    public int getHealth() {
        return health;
    }

    /**
     * Sets the health of actor.
     * @param health | the new health of actor
     */
    public void setHealth(int health) {
        this.health = health;
    }

    /**
     * No Argument Constructor
     * Initializes Actor object with default values
     */
    public Actor(){
        this.health = 1;
    }
    /**
     * Constructor for Actor Object with a name as a parameter
     * Initializes Actor with a specified name and a default health.
     * @param name | name of Actor
     */
    public Actor(String name){
        this.name = name;
        this.health = 1;
    }

    /**
     * Constructor for Obstacle with name and health.
     * Initializes Actor with a specified name and health.
     * @param name the name of Actor
     * @param health the size of Actor
     */
    public Actor(String name, int health){
        this.name = name;
        this.health = health;
    }

    /**
     * Constructor for Actor class with name, transform and health, as parameters
     * Calls Super class constructor to help Initialize Actor Object
     * @param name New name Initialized to Actor Object
     * @param transform New Transform Initialized to Actor Object
     * @param health New Health Initialized to Actor Object
     * @see Transform
     * @see Entity
     */
    public Actor(String name, Transform transform, int health) {
        super(name, transform);
        this.health = health;
    }

    /**
     * Returns string representation of Actor Object
     * The string calls the super.toString() to include information from its superclass Entity
     * __Example Output: __
     -I am an Entity, my name is Phil Dunphy
     -I am at 0, 0, facing 0.0 degrees, with a speed of 0
     -My health is 2
     *
     * @see Entity
     * @see Transform
     * @return a string representation of Obstacle
     */

    @Override
    public String toString (){
        return(super.toString() +  "\n-My health is " + health);
    }

} //End Actor Class
