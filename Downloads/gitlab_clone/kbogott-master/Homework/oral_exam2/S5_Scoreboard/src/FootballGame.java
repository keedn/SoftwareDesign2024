import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Football SubClass of Game
 */
public class FootballGame extends Game {
    /**
     * Variable to Track Current Quarter in Football Game
     */
    private int currentQuarter;
    /**
     * Int used to track quarter length
     */
    private int quarterLength = 15;
    /**
     * Name of Period
     */
    private String namePeriod;
    /**
     * Scoring Methods for Football
     */
    private List<ScoringMethod> scoringList = new ArrayList<>(
            Arrays.asList(
                    new ScoringMethod("TouchDown", 6),
                    new ScoringMethod("Field Goal", 3),
                    new ScoringMethod("Extra Point", 1),
                    new ScoringMethod("Two Point Conversion", 2),
                    new ScoringMethod("Safety", 2)
            )
    );


    /**
     * Default constructor that initializes a new game with the game status as "off"
     * and initial scores set to zero for both teams. Team names are also initialized
     * to empty strings.
     *
     * @param teamOne name of Team One
     * @param teamTwo name of Team one
     */
    public FootballGame(String teamOne, String teamTwo) {
        super(teamOne, teamTwo);
        this.currentQuarter = 1;
        this.quarterLength = 15;
        this.namePeriod = "Quarter";
    }


    /**
     * Take's in scoring method and an Integer corresponding to Team which will be modified (1 or 2)
     *
     * @param team Team which score will be modified
     * @param choice What Scoring Method will be used
     */
    @Override
    public void addScore(int choice, int team) {

        // GET METHOD CHOSEN
        ScoringMethod currentMethod = scoringList.get(choice - 1); // -1 0 indexed
        int addMe = currentMethod.getPoints();
        int newScore = 0;

        //System.out.println("Adding " + addMe + " points to team " + team); // Debugging line

        // ADD for TEAM One
        if (team == 1) {
            newScore = getTeamOneScore() + addMe;
            this.setTeamOneScore(newScore);

        }
        // ADD for TEAM TWO
        else if (team == 2) {
            newScore = getTeamTwoScore() + addMe;
            this.setTeamTwoScore(newScore);

        }
    }

    /**
     * Return scoring list methods for Football
     *
     * @return Football ScoringMethods
     */
    @Override
    public List<ScoringMethod> getScoringMethods() {
        return this.scoringList;
    }

    /**
     * Returns
     *
     * @return Current Quarter
     */
    @Override
    public int getCurrentPeriodOfPlay() {
        return this.currentQuarter;
    }

    /**
     * Decrement Length of period
     * Randomly after an Event has been chosen
     *
     * @return new quarter length after it has been decremented
     */
    @Override
    public int decrementTimePeriod() {

        Random random = new Random();
        int[] timeDecrement = {1, 2, 3, 4, 5, 6};
        int randomDecrement = timeDecrement[random.nextInt(timeDecrement.length)];

        this.quarterLength = Math.max(0, quarterLength - randomDecrement); // Doesnt go below 0

        return quarterLength;
    }

    /**
     * Increment Period
     *
     * @return New Period
     */
    @Override
    public int incrementPeriod() {
        // If game is over return -1
        this.quarterLength = 15;
        if (this.currentQuarter == 4) {
            return -1;
        } else {
            return this.currentQuarter++;
        }
    }

    /**
     * Returns Length of Quarter
     *
     * @return Length of Quarter
     */
    @Override
    public int getLengthofPeriodOfPlay() {
        return this.quarterLength;
    }

    /**
     * Returns Current Quarter of Football Game
     *
     * @return String of what Quarter the Game is In
     */
    @Override
    public String getNameOfPeriodOfPlay() {
        if (this.currentQuarter == 1) {
            return "First Quarter";
        } else if (this.currentQuarter == 2) {
            return "Second Quarter";
        } else if (this.currentQuarter == 3) {
            return "Third Quarter";
        } else if (this.currentQuarter == 4) {
            return "Fourth Quarter";
        }
        return null;
    }
}
