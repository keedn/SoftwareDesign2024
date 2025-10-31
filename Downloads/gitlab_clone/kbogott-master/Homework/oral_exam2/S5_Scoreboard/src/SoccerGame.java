import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Soccer SubClass of Game
 */
public class SoccerGame extends Game{
    /**
     * Variable to Track Current Quarter / Half in Soccer Game
     */
    private int currentQuarter;
    /**
     * Int used to keep track of half-length (minutes)
     */
    private int halfLength;
    /**
     * Scoring Methods for Soccer
     */
    private List<ScoringMethod> scoringList = new ArrayList<>(
            Arrays.asList(
                    new ScoringMethod("Goal", 1)
            )
    );
    /**
     * Default constructor that initializes a new game with the game status as "off"
     * and initial scores set to zero for both teams. Team names are also initialized
     * to empty strings.
     *
     * @param teamOne name of Team One
     * @param teamTwo name of Team Two
     */
    public SoccerGame(String teamOne, String teamTwo) {

        super(teamOne, teamTwo);
        this.currentQuarter = 1;
        this.halfLength = 45;
    }

    /**
     * Holds String Value of Scoring Method Name and an Int corresponding to how many points should be added.
     * @return ScoringMethods for each Game Subclass
     */
    public List<ScoringMethod> getScoringMethods() {
        return this.scoringList;
    }

    /**
     * Take's in scoring method and an Integer corresponding to Team which will be modified (1 or 2)
     *
     * @param team Team which score will be modified
     * @param choice What Scoring Method will be used
     */
    public void addScore(int choice,int team) {

        // GET METHOD CHOSEN
        ScoringMethod currentMethod = scoringList.get(choice - 1); // -1 0 indexed
        int addMe = currentMethod.getPoints();
        int newScore = 0;

        //System.out.println("Adding " + addMe + " points to team " + team); // Debugging line

        // ADD for TEAM One
        if(team == 1){
            newScore = getTeamOneScore() + addMe;
            this.setTeamOneScore(newScore);

        }
        // ADD for TEAM TWO
        else if(team == 2){
            newScore = getTeamTwoScore() + addMe;
            this.setTeamTwoScore(newScore);

        }
    }

    /**
     * Method used to increment period for each game
     * @return Returns current period as Int, If game has ended will return -1
     */
    @Override
    public int incrementPeriod(){
        this.halfLength = 45;
        // If game is over return -1
        if(this.currentQuarter == 2){
            return -1 ;
        }
        else{return this.currentQuarter++;}
    }

    /**
     * Gets current period of play
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

        this.halfLength = Math.max(0, halfLength - randomDecrement); // Doesnt go below 0

        return halfLength;
    }

    /**
     * Gets the remaining length of current period
     * Returns length in minutes, set by subclass
     */
    @Override
    public int getLengthofPeriodOfPlay() {
        return halfLength;
    }

    /**
     * Gets name of period of play ( half, quarter, period, etc )
     */
    @Override
    public String getNameOfPeriodOfPlay() {

        if(this.currentQuarter == 1){
            return "First Half";
        }
        else if(this.currentQuarter == 2){
            return "Second Half";
        }
        return null;
    }


}
