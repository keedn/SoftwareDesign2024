import java.util.ArrayList;
import java.util.List;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Random;
/**
 * The ScoreBoardDriver class is responsible for driving the scoreboard application.
 * It handles user input for team names, sport selection, and scoring during the game.
 * @see Game
 * @author kbogott
 * @version 1.0
 */
public class ScoreBoardDriver  {

    /**
     * The main method is the entry point of the ScoreBoard application.
     * It initializes the scoreboard and manages user interactions for team names, sport selection, and scoring throughout the game.
     * @param args Command-line arguments
     */
    public static void main(String[] args) {

        // CREATE SCOREBOARD AND SCANNER OBJECT
        Scanner inputScanner = new Scanner(System.in);
        ScoreBoardDriver mainScoreBoard = new ScoreBoardDriver();

        // GET NAMES FOR TEAMS
        System.out.println("------------------------------------------");
        System.out.print("Enter Name for Team 1: ");
        String teamNameOne = inputScanner.nextLine().toUpperCase();
        System.out.print("Enter Name for Team 2: ");
        String teamNameTwo = inputScanner.nextLine().toUpperCase();
        System.out.println("------------------------------------------");

        // GET WHAT SPORT USER WILL BE PLAYING + CHECK IF THEIR INPUT IS VALID
        System.out.println("What Sport would you like to play? "+"\n1. Football\n2. Basketball\n3. Soccer\n4. Hockey");
        System.out.println("------------------------------------------");

        // GET USERS GAME CHOICE
        int choice = 0;

        // CHECK IF CHOICE IS VALID
        try{
            choice = inputScanner.nextInt();

            // If CHOICE OUT OF INDEX
            if(choice < 1 || choice > 4) {
                throw new InputMismatchException();
            }

        } catch (InputMismatchException e) {
            System.out.println("Invalid Input. Please choose a Sport.");
            inputScanner.next(); // Clear the invalid input and get another input from user
        }

        // GET INSTANCE OF CORRECT GAME
        Game mainGame = mainScoreBoard.setGame(teamNameOne,teamNameTwo,choice);
        mainGame.setTeamOne(teamNameOne);
        mainGame.setTeamTwo(teamNameTwo);

        // ------------------------------ GAME WHILE LOOP ------------------------------

        while(!mainGame.isGameOver()) {


            // IF THE QUARTER ENDS
            if(mainGame.getLengthofPeriodOfPlay() == 0){
                mainGame.incrementPeriod();
            }
            // ELSE IF GAME ENDS AFTER INCREMENTING RETURN
            else if(mainGame.getCurrentPeriodOfPlay() == 4){
                mainScoreBoard.endGame(mainGame);
                return;
            }


            // WHICH TEAM GETS POINTS
            System.out.println("----CHOOSE WHICH TEAM DOES WHAT-----\n");
            System.out.println("      "+mainGame.getTeamOne() +" (1) or " + mainGame.getTeamTwo() + " (2)");
            System.out.println("------------------------------------");


            // Get Team Choice and Check if Valid
            int teamChoice = 0;
            try{
                teamChoice = inputScanner.nextInt();
                if(teamChoice < 1 || teamChoice > 2) {
                    throw new InputMismatchException();

                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid Input. Please choose a Valid Team");
                inputScanner.next();
            }

            System.out.println("-------------------------------------");

            //  PRINT CHOICE MENU TO USER
            System.out.println("Scoring Menu:");
            int count = mainScoreBoard.printScoring(mainGame); // GET INPUT
            System.out.println(count + ".  End " + mainGame.getNameOfPeriodOfPlay());
            System.out.println("-------------------------------------");

            // PRINT MIN LEFT
            System.out.println("Minutes Left in " + mainGame.getNameOfPeriodOfPlay() + ": " + mainGame.getLengthofPeriodOfPlay());
            System.out.println("-------------------------------------");

            //  INITIALIZE USER CHOICE AND VALID CHOICE BOOL
            int scoringChoice;
            boolean validChoice = false;

            // GO UNTIL USER INPUTS VALID CHOICE
            while (!validChoice) {
                 System.out.print("\nPlease enter your choice: \n");

                 try {    // Try user input
                     scoringChoice = inputScanner.nextInt();

                     // CHECK IF USER ASKED TO SKIP QUARTER / PERIOD
                     // LOGIC FOR ITERATING PERIOD LENGTHS HERE
                     if(scoringChoice == mainGame.getScoringMethods().size() + 1) { // + 1 for added period option

                         System.out.println("\n" + mainGame.getNameOfPeriodOfPlay() + " has ended..");

                         // STORE CURRENT PERIOD
                         int currentPeriod;
                         currentPeriod = mainGame.incrementPeriod();

                         // IF GAME OVER / LAST PERIOD ENDED ---> END GAME
                         if(currentPeriod == -1){
                             mainScoreBoard.endGame(mainGame);
                             return;
                         }

                         break;

                     } // END PERIOD ITERATOR LOGIC

                     // CHECK IF USER CHOICE IS VALID
                     if (scoringChoice < 1 || scoringChoice > mainGame.getScoringMethods().size()   ) {
                         System.out.println("Invalid Input. Input a Valid Response");
                     } else {
                         validChoice = true; // Exit the loop if valid

                         // OUTPUT WHAT SCORE HAPPENED
                         if(teamChoice == 1){
                             ScoringMethod chosenScore = mainGame.getScoringMethods().get(scoringChoice - 1 );
                             System.out.println(mainGame.getTeamOne() + " just scored a " + chosenScore.getName()+ "!");
                         }
                         if(teamChoice == 2){
                             ScoringMethod chosenScore = mainGame.getScoringMethods().get(scoringChoice - 1);
                             System.out.println(mainGame.getTeamTwo() + " just scored a " + chosenScore.getName()+ "!");
                         }

                         // ADD SCORE TO SCOREBOARD
                         mainGame.addScore(scoringChoice,teamChoice);
                         mainGame.decrementTimePeriod();
                     }

                     // IF INVALID ASK FOR ANOTHER INPUT
                 } catch (InputMismatchException e) {
                     System.out.println("Invalid Input. Please enter a number.");
                     inputScanner.next(); // Clear the invalid input
                 }
            } // END !validChoice while loop for POINT CHOICE

            System.out.println("\n------------SCOREBOARD--------------");
            System.out.println( mainGame.getTeamOne()  + ": " + mainGame.getTeamOneScore());
            System.out.println( mainGame.getTeamTwo()  + ": " + mainGame.getTeamTwoScore());
            System.out.println();

              }
        //------------------------------ END GAME WHILE LOOP ------------------------------
    }

    /**
     * Returns instance of Game with both names of teams
     * Outputs Starting Game Message
     * @param teamNameOne Name of Team One
     * @param teamNameTwo Name of Team Two
     * @param choice What Game was picked
     * @return instance of correct game picked
     */
    public Game setGame(String teamNameOne, String teamNameTwo,int choice) {
        System.out.println("\n\n\n\n\n\n\n\n");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

        // Football
        if(choice == 1) {
            Game game = new FootballGame(teamNameOne,teamNameTwo);
            System.out.println("    FOOTBALL GAME STARTING.....\n       " + teamNameOne + " VS " + teamNameTwo );
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");

            return game;
        }

        // Basketball
        else if(choice == 2) {
            Game game = new BasketballGame(teamNameOne,teamNameTwo);
            System.out.println("    BASKETBALL GAME STARTING.....\n      " + teamNameOne + " VS " + teamNameTwo );
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");

            return game;
        }

        // Soccer
        else if(choice == 3) {
            Game game = new SoccerGame(teamNameOne, teamNameTwo);
            System.out.println("    SOCCER GAME STARTING.....\n       " + teamNameOne + " VS " + teamNameTwo );
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");

            return game;
        }

        // Hockey
        else if(choice == 4) {
            Game game = new HockeyGame(teamNameOne,teamNameTwo);
            System.out.println("    HOCKEY GAME STARTING.....\n       " + teamNameOne + " VS " + teamNameTwo );
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");

            return game;
        }
        return null;
    }

    /**
     * Handles End of Game Logic
     * Prints Final Scoreboard
     * @param mainGame Games Instance
     */
    public void endGame(Game mainGame){
        // END GAME
        mainGame.isGameOver(true);
        System.out.println("-------------------------------------");
        System.out.println("GAME OVER | The " + mainGame.getNameOfPeriodOfPlay() + " has ended.");

        System.out.println("------------FINAL SCOREBOARD--------------");
        System.out.println( mainGame.getTeamOne()  + ": " + mainGame.getTeamOneScore());
        System.out.println( mainGame.getTeamTwo()  + ": " + mainGame.getTeamTwoScore());
        System.out.println("------------------------------------------");

        int winner = mainGame.winningTeam(); // Returns : 1 = Team One Win | 2 = Team Two Win | O Draw

        // LIST OF WORDS FOR OUTCOME
        List<String> words = new ArrayList<>();
        words.add("DESTROYED");
        words.add("JUST BEAT");
        words.add("OWNED");
        words.add("WON OVER");
        words.add("EMBARRASSED");
        words.add("OWNS");

        // GRAB RANDOM
        Random random = new Random();
        String randomOutcomeWord = words.get(random.nextInt(words.size()));

        // TEAM ONE WIN
        if(winner == 1){
            System.out.println(mainGame.getTeamOne()+ " WON!\n");
            System.out.println(mainGame.getTeamOne() + " " +randomOutcomeWord + " " +  mainGame.getTeamTwo());
        }
        // TEAM TWO WIN
        if(winner == 2){
            System.out.println(mainGame.getTeamTwo()+ " WON!\n");
            System.out.println(mainGame.getTeamTwo() +  " " + randomOutcomeWord + " " + mainGame.getTeamOne());
        }
        // DRAW
        if(winner == 0) {
            System.out.println("GAME ENDED IN A DRAW :( ");
        }

    }

    /**
     * Prints Scoring Methods in List View
     * @param game Current Game
     * @return Count of Current Period
     */
    public int printScoring(Game game) {
        int count = 1;
        //System.out.println("Option  Type"  + "  Points");
        for(ScoringMethod score : game.getScoringMethods()) {

            System.out.println(count + ".  " + score);
            count++;
        }
        return count;
    }

}