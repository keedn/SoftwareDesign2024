/**
 * Represents a scoring method in a game, including its name and point value.
 */
public class ScoringMethod {

    /**
     * Name of the scoring method.
     */
    private String name;

    /**
     * Point value of the scoring method.
     */
    private int points;

    /**
     * Constructs a new ScoringMethod with the specified name and point value.
     *
     * @param name  the name of the scoring method
     * @param points the point value associated with the scoring method
     */
    public ScoringMethod(String name, int points) {
        this.name = name;
        this.points = points;
    }

    /**
     * Returns the name of the scoring method.
     *
     * @return the name of the scoring method
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the point value of the scoring method.
     *
     * @return the point value of the scoring method
     */
    public int getPoints() {
        return points;
    }

    /**
     * Returns a string representation of the scoring method, including its name and point value.
     *
     * @return a string representation of the scoring method
     */
    @Override
    public String toString() {
        return name + " " + points;
    }
}