import java.util.List;

/**
 * The Game class is an abstract base class representing a general sports game.
 * It provides common attributes and methods for handling game state, teams, scores,
 * and period management. Specific sports games will extend this class and implement
 * sport-specific behaviors.
 */
abstract class Game {
    /**
     * Boolean Value that tracks if there is a game currently being played
     */
    private boolean gameOn;
    /**
     * Team One's Score
     */
    private int teamOneScore;
    /**
     * Team Two's Score
     */
    private int teamTwoScore;
    /**
     * Name of Team One
     */
    private String teamOne;
    /**
     * Name of Team Two
     */
    private String teamTwo;

    /**
     * Default constructor that initializes a new game with the game status as "off"
     * and initial scores set to zero for both teams. Team names are also initialized
     * to empty strings.
     * @param teamOne Name for Team One
     * @param teamTwo Name for Team Two
     */
    public Game(String teamOne, String teamTwo) {
        this.gameOn = false;
        this.teamOneScore = 0;
        this.teamTwoScore = 0;
        this.teamOne = teamOne;
        this.teamTwo = teamTwo;
    }

    //------ ABSTRACT METHODS FOR EACH GAME SUBCLASS TO IMPLEMENT ------

    /**
     * Adds score to selected team
     * @param choice Scoring Method Choice
     * @param team What team was Chosen
     */
    public abstract void addScore(int choice, int team);

    /**
     * Method used to increment period for each game
     * @return Returns current period as Int, If game has ended will return -1
     */
    public abstract int incrementPeriod();

    /**
     * Gets current period of play
     * @return Current Period of Game Instance
     */
    public abstract int getCurrentPeriodOfPlay();
    /**
     * Increment Length of period
     * Randomly after an Event has been chosen
     * @return new Length of Period / Quarter / Half
     */
    public abstract  int decrementTimePeriod();

    /**
     * Gets the remaining length of current period
     * Returns length in minutes, set by subclass
     * @return Length of Period
     */
    public abstract int getLengthofPeriodOfPlay();

    /**
     * Gets name of period of play ( half, quarter, period, etc )
     * @return Name of Period of Play
     */
    public abstract String getNameOfPeriodOfPlay();

    /**
     * Holds String Value of Scoring Method Name and an Int corresponding to how many points should be added.
     * @return ScoringMethods for each Game Subclass
     */
    public abstract List<ScoringMethod> getScoringMethods();


    //------ END ABSTRACT METHODS ------------------------------------

    /**
     * Checks if game is over
     * @return boolean gameOn value
     */
    public boolean isGameOver(){
        return gameOn;
    }

    /**
     * Sets value of gameOn
     * False = game is going on | True = game over
     * @param gameOn new gameOn value
     */
    public void isGameOver(boolean gameOn) {
        this.gameOn = gameOn;
    }

    /**
     * Method for determining who is the winning team
     * Returns 1 if TEAM 1 WINS
     * Returns 2 if Team 2 WINS
     * Returns 0 if Draw
     * @return index to which team won
     */
    public int winningTeam() {
        // TEAM ONE WINS
        if (teamOneScore > teamTwoScore) {
            return 1;
            // TEAM TWO WINS ( TEAM 1 LOSE)
        } else if (teamTwoScore > teamOneScore) {
            return 2;
        }
        // DRAW
        return 0;
    }



    // ----------------- GETTER'S SETTER'S FOR TEAMS --------------------

    /**
     * Returns name of Team One
     * @return Team One Name
     */
    public String getTeamOne() {
        return teamOne;
    }

    /**
     * Returns name of Team Two
     * @return Name of Team Two
     */
    public String getTeamTwo() {
        return teamTwo;
    }

    /**
     * Set Name of TeamOne
     * @param teamOne New name for Team One
     */
    public void setTeamOne(String teamOne) {
        this.teamOne = teamOne;
    }

    /**
     * Set Name of TeamTwo
     * @param teamTwo New name for Team Two
     */
    public void setTeamTwo(String teamTwo) {
        this.teamTwo = teamTwo;
    }

    /**
     * Returns Team One Score
     * @return Team One Score
     */
    public int getTeamOneScore() {
        return teamOneScore;
    }

    /**
     * Return Team Two Score
     * @return Team Two Score
     */
    public int getTeamTwoScore() {
        return teamTwoScore;
    }

    /**
     * Updates Team Two Score to new value
     * @param teamTwoScore New score for Team Two
     */
    public void setTeamTwoScore(int teamTwoScore) {
        this.teamTwoScore = teamTwoScore;
    }
    /**
     * Updates Team One Score to new value
     * @param teamOneScore New score for Team One
     */
    public void setTeamOneScore(int teamOneScore) {
        this.teamOneScore = teamOneScore;
    }

    // ----------------- END GETTER'S SETTER's FOR TEAMS -----------------

}
