/** Transform
 * Represents the Entity's xPos, yPos, rotation, and speed
 * The transform is used to track and update the Entity's xPos, yPos, rotation, and speed
 *
 * @see Entity
 * @author kbogott
 * @version 1.0
 */
public class Transform {
    /**
     * xPos : Entity's x position
     *      > Integer value representing Entity's position on x-axis.
     *
     * @see #getxPos()
     * @see #setxPos(int)
     */
    private int xPos = 0;
    /**
     * yPos : Entity's y position
     *      > Integer value representing Entity's position on y-axis.
     *
     * @see #getyPos()
     * @see #setyPos(int)
     */
    private int yPos = 0;
    /**
     * rotation : Entity's rotation
     *      > Float value representing Entity's rotation in degrees.
     *
     * @see #getRotation()
     * @see #setRotation(float)
     */
    private float rotation = 0;
    /**
     * speed : Entity's Speed
     *      > Integer value representing Entity's speed.
     */
    private int speed = 0;

    /**
     * Return Entity's x position
     * @return xPos | Entity's int x position
     *
     * @see #getSpeed()
     * @see #setSpeed(int)
     */
    public int getxPos() {
        return xPos;
    }

    /**
     * Set's Entity's x position
     * @param xPos new x coordinate position
     */
    public void setxPos(int xPos) {
        this.xPos = xPos;
    }

    /**
     * Return's Entities y coordinate
     * @return yPos | Entity's int y coordinate
     */
    public int getyPos() {
        return yPos;
    }
    /**
     * Set's Entity's y position
     * @param yPos new y coordinate position
     */
    public void setyPos(int yPos) {
        this.yPos = yPos;
    }
    /**
     * Return's Entities rotation in degrees.
     * @return rotation | Entity's rotation in degrees.
     */
    public float getRotation() {
        return rotation;
    }

    /**
     * Sets the Entity's Rotation in degrees(float).
     * @param rotation the new rotation in degrees
     */
    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    /**
     * Returns Entity's speed
     * @return speed | Entity's speed.
     */
    public int getSpeed() {
        return speed;
    }
    /**
     * Sets Entity's speed
     * @param speed | Entity's new speed
     */
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    /**
     * No argument constructor for Transform Object
     * Initializes Transform Object with default values ( int 0, int 0, float 0.0f, int 0)
     *
     * @see #xPos
     * @see #yPos
     * @see #rotation
     * @see #speed
     */
    public Transform() {
    }

    //public constructor for transform object that will be in each Entity object

    /**
     * Constructor for Transform Object with ALL parameters
     *Initializes Transform Object with specified parameters
     * xPos : Entity's x position
     *      > Integer value representing Entity's position on x-axis.
     * yPos : Entity's y position
     *      > Integer value representing Entity's position on y-axis.
     * rotation : Entity's rotation
     *      > Float value representing Entity's rotation in degrees.
     * speed : Entity's Speed
     *      > Integer value representing Entity's speed.
     * @param xPos Entity's new position.
     * @param yPos Entity's new position.
     * @param rotation Entity's new rotation.
     * @param speed  Entity's new speed.
     */
    public Transform(int xPos, int yPos, float rotation, int speed) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.rotation = rotation;
        this.speed = speed;
    }

}// End Transform Class
