import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.List;
import java.util.Map;


public class FF_GameScreenController {

    public void setPlayerTeamOne(Map<String,Integer> playerTeam){
        System.out.print("Team 1" + playerTeam);

    }
    public void setPlayerTeamTwo(Map<String,Integer> playerTeam){
        System.out.print("Team 2" + playerTeam);
    }

    private int teamTurn =1; // Holds int for which team is currently guessing
    private String playerUsername;
    private int playerTeamPoints;
    private int playerTeam;

    int teamOneIncorrect;
    int teamTwoIncorrect;
    int teamOnePoints;
    int teamTwoPoints;

    // Score, Answer Questions Arrays
    private List<String> correctAnswers = Arrays.asList();

    // Get List of Correct Answers
    public List<String> getCorrectAnswers() {
        return correctAnswers;
    }

    // Initializes Correct Answer List for The Round
    public void setCorrectAnswers(List<String> correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    private String[] scores;
    private String[] questions;

    // **** ANSWER / SCORE LABELS ****

    @FXML
    private Label answer1; // "ANSWER 6   : [50]"
    @FXML
    private Label answer2;
    @FXML
    private Label answer3;
    @FXML
    private Label answer4;
    @FXML
    private Label answer5;
    @FXML
    private Label answer6;
    @FXML
    private Label answer7;
    @FXML
    private Label answer8;
    @FXML
    private Label answer9;
    @FXML
    private Label answer10;

    // ************* ANSWER / SCORE LABELS^ *************

    //*************  GUI COMPONENTS  *************

    @FXML
    private Label questionLabel;

    @FXML
    private GridPane answerGridBox;

    @FXML
    private VBox answerVBox;

    @FXML
    private VBox chatBox;

    @FXML
    private TextArea chatTextArea;

    @FXML
    private TextField messageField;
    @FXML
    private TextArea enterGuessTextArea;

    @FXML
    private Button sendButton;
    @FXML
    private Button buzzInButton;
    @FXML
    private Button storeButton;
    @FXML
    private Button showChatButton;
    @FXML
    private Button guessButton;

    @FXML
    private Label teamOnePointsLabel;

    @FXML
    private Label teamTwoPointsLabel; // Label that shows number of Points for team 2

    @FXML
    private Label teamOneXLabel;
    @FXML
    private Label teamTwoXLabel; // Label that shows number of wrong Guess for Team 2
    @FXML
    private Label updateLabel;

    //************* GUI COMPONENTS^  *************

    // CHAT BOX METHODS
    @FXML
    void handleChatSendButton(ActionEvent event) {
        String message = messageField.getText();             // Retrieve Message

        if(message.isEmpty()){                               // Message is Empty
            System.out.println("Can't send Empty Message");
            return;
        }

        // TODO: Send Message to Server (ChatBOX_MSG, message)


        chatTextArea.setText(chatTextArea.getText() +"\n" + "[Username]: " + message); // Update Locally
        messageField.clear();                                                          // Clear Message Field after Sending
    }

    /**
     * Updates Chat Box
     */
    public void updateChatBox(String chatBoxMessages){
        chatTextArea.setText(chatBoxMessages);          // Change Text to New Chat-box Messages
    }


    /**
     * Toggles ChatBox
     */
    @FXML
    void handleShowChatButton(ActionEvent event) {
        System.out.println("[Show Chat Button Pressed]");

        boolean isVisible = chatBox.isVisible();         // Get Current value and flip it
        chatBox.setVisible(!isVisible);
    }
    // CHAT BOX METHODS ^
    @FXML
    void handleBuzzButton(ActionEvent event){

        buzzInButton.setDisable(true);                          // Disabled no multiple buzzing in Same Round
        updateLabel.setText("Team " +teamTurn+ " BUZZED IN");   // Update Label to show which team buzzed first

        // TODO : Send Buzz In Event to Server along with Team who (BUZZ_BUTTON, team)


        guessButton.setDisable(false); // Activate Guess Button For Every Player on Team that got buzzed

    }
    @FXML
    void handleGuessButton(ActionEvent event){

        String guess = enterGuessTextArea.getText();    // Get Guess
        System.out.println("User Guessed: " + guess);   // Output Users Guess to ChatBox
        enterGuessTextArea.clear();

        // Check if empty guess
        if(guess.isEmpty()){
            setUpdateLabel("Guess is Empty..");
            return;
        }

        // TODO : SEND GUESS TO SERVER (CORRECT or INCORRECT, guess)

        if(checkAnswer(guess)){
            // Correct Guess,  Send Result to Server
            incrementTeamPoints(playerTeam,playerTeamPoints,50);

        }
        else{
            // Incorrect Guess, Send Result to Server
            incrementTeamX(playerTeam);  /// Increment X for Player Team

        }
        // TODO: ^
    }

    /**
     * Checks Answer
     */
    private boolean checkAnswer(String userGuess){
        if(userGuess == null){
            return false;
        }

        String guess = userGuess.trim().toLowerCase(); // Make Case insensitive

        return true;
    }

    /**
     * Toggles Functionality of Buzz in Button
     */
    public void toggleBuzzButton(){
        buzzInButton.setDisable(!buzzInButton.isDisabled());
    }

    /**
     * Method to add X's for wrong guesses. Call When Team Gets Wrong Answer
     */
    void incrementTeamX(int team){
        if(team == 1){
            teamOneXLabel.setText(teamOneXLabel.getText() + " [X]");  // Add '[x]' to Team 1 label
            teamOneXLabel.setStyle("-fx-text-fill: red;");            // SET COLOR TO RED
            teamOneIncorrect++;                                       // increment count of wrong guesses
        }
        else if(team == 2){                                                // If Team One Got Question Wrong
            teamTwoXLabel.setText(teamTwoXLabel.getText() + " [X]");  // Add '[x]' to Team 2 label
            teamTwoXLabel.setStyle("-fx-text-fill: red;");            // SET COLOR TO RED
            teamTwoIncorrect++;                                        // increment count of wrong guesses
        }
    }

    /**
     * Updates Teams Points Label
     * @param team Team Getting Points
     * @param questionValue Points Getting Added
     * @param currentTeamPoints Teams Current Points
     */
    void incrementTeamPoints(int team, int currentTeamPoints, int questionValue) {

        int updatedPoints = currentTeamPoints + questionValue;                          // Add the question value to the current points

        if (team == 1) {                                                                // Team One get Points
            System.out.println("Incrementing Team 1 Points by " + questionValue);       // Log Score Change
            teamOnePointsLabel.setText("Points: " + updatedPoints);                     // Update Team 1 points label
        }
        else if (team == 2) {                                                           // Team Two get Points
            System.out.println("Incrementing Team 2 Points by " + questionValue);       // Log Score Change
            teamTwoPointsLabel.setText("Points: " + updatedPoints);                     // Update Team 2 points label
        }
    }


    /**
     * Switches Team Turn,
     */
    public void switchToNextTeam() {
        teamTurn = (teamTurn == 1) ? 2 : 1;                                    // If teamTurn is currently 1, set it to 2; if it's 2, set it to 1
        updateLabel.setText("It is now Team " + teamTurn + " turn now");
        System.out.println("It is now Team " + teamTurn + " turn now" );       // Log Team Change
    }

    /**
     * Resets Team's Incorrect [X] count
     */
    void resetTeams_X(){
        teamOneXLabel.setText(": "); // Resets ALl Teams X
        teamTwoXLabel.setText(": ");
        teamOneIncorrect = 0;
        teamTwoIncorrect = 0;
    }

    /**
     * Sends User to EndGame Screen
     * @param event Event
     */
    void goToEndScreen(ActionEvent event){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FF_GameEndScreen.fxml"));
            Parent lobbyScreenRoot = fxmlLoader.load();

            Scene storeScene = new Scene(lobbyScreenRoot);
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.setScene(storeScene);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates the Question Label
     * @param question New Question
     */
    void updateQuestionLabel(String question){
        questionLabel.setText(question);      // Updates Question Label
        setUpdateLabel("New Question.. ");
    }

    /**
     * Used to Update Message under Answer Grid ( team 2 ran out of guesses etc )
     * @param update Update Message
     */
    void setUpdateLabel(String update){
        updateLabel.setText(update);
    }

    /**
     * Sets the Answers in the GUI
     */
    private void setAnswers(){

    }

}



