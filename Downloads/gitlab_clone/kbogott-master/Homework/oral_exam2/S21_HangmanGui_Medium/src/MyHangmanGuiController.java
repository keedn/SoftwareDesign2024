import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Main Class for Hangman Controller
 * Handles Logic for Guess detection, guess number, displaying hangman state, and updating main scene components
 */
public class MyHangmanGuiController {

    /**
     * Array List of All Possible Words for HangMan
     */
    public ArrayList<String> words = new ArrayList<>(Arrays.asList(
            "apple", "banana", "grape", "orange", "mango",
            "elephant", "giraffe", "dolphin", "kangaroo", "penguin",
            "cryptography", "philosophy", "pneumonia", "xylophone", "synchronous",
            "watermelon", "pineapple", "raspberry", "strawberry", "blueberry",
            "universe", "galaxy", "asteroid", "planet", "comet",
            "python", "java", "javascript", "typescript", "ruby",
            "mountain", "valley", "desert", "forest", "jungle",
            "island", "ocean", "river", "waterfall", "lagoon",
            "clockwork", "blueprint", "mechanism", "circuit", "engine",
            "algorithm", "database", "network", "protocol", "interface"
    ));
    /**
     * Array List of all Different hangman Stages
     */
    public ArrayList<String> hangmanStages = new ArrayList<>(Arrays.asList(
            """
                     +---+
                     |      |
                     |      
                     |      
                     |      
                     |      
                    ======""",
            """
                     +---+
                     |      |
                     |      O
                     |      
                     |      
                     |      
                    ====== """,
            """
                     +---+
                     |      |
                     |      O
                     |      | 
                     |      
                     |      
                    ======""",
            """
                     +---+
                     |      |
                     |      O
                     |     /|
                     |      
                     |     
                    ======""",
            """
                     +---+
                     |      |
                     |      O
                     |     /|\\
                     |     
                     |     
                    ======""",
            """
                     +---+
                     |      |
                     |      O
                     |     /|\\
                     |     /   
                     |      
                    ======""",

            """
                     +---+
                     |      |
                     |      O
                     |     /|\\
                     |     / \\
                     |      
                    ======"""
    ));
    /**
     * List to Track previous user single character guesses
     */
    public List<Character> userGuesses = new ArrayList<>();

    @FXML
    public Text hangmanStage;
    @FXML
    public TextField userInputField;
    @FXML
    private Label guessesLeft;

    @FXML
    private Button submitButton;
    private int numGuesses = 6;
    @FXML
    public Text wordPreview;
    public String word;
    @FXML
    private HBox alphabetPreview;
    @FXML
    private TextField userWordGuessField;
    /**
     * List of all user Word guesses
     */
    public List<String> wordGuesses = new ArrayList<>();

    @FXML
    public DialogPane startPopUp;
    @FXML
    private TextField customHangmanWord;
    @FXML
    private Button playHangManButton;
    @FXML
    private VBox gameComponents2;

    @FXML
    private VBox gameComponents1;
    @FXML
    private AnchorPane anchorPane;
    /**
     * String containing alphabet
     */
    private String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    /**
     * List to track previous incorrect guesses
     */
    public List<Character> incorrectGuesses = new ArrayList<>();


    /**
     * Method to set word
     * @param word New word to be guessed
     */
    public void setWord(String word) {
        this.word = word;
    }

    /**
     * sets hangman stage text to new stage
     * @param stage Stage of hangman
     */
    public void setHangmanStage(int stage ){
        this.hangmanStage.setText(hangmanStages.get(stage));
    }

    /**
     * Takes in users input and handles logic connected to checking user's guess and updating Scene components
     * @param event submit button pressed
     * @charGuess
     * @wordGuess
     */
    @FXML
    void submitButtonPressed(ActionEvent event) {

        // Take in user input
        String userCharGuess = userInputField.getText().toUpperCase();
        String userWordGuess = userWordGuessField.getText().toUpperCase();

        userInputField.clear();
        userWordGuessField.clear();

        // Check for character guess
        if (!userCharGuess.isEmpty() && userCharGuess.length() == 1) {
            charGuess(userCharGuess);
        }
        // Check if it's a full word guess
        else if (!userWordGuess.isEmpty() && userWordGuess.length() > 1) {
            wordGuess(userWordGuess);
        }

        // Set guessesLeft text with new num of guesses
        guessesLeft.setText("Guesses left: " + numGuesses);
        // Set focus to char input field
        userInputField.requestFocus();
    }

    /**
     * Logic for handling single char guesses
     * @param guess User Guess as a char
     */
    private void charGuess(String guess) {
        char c = guess.charAt(0);

        // Check if the character has already been guessed
        if (!userGuesses.contains(c)) {
            // If not guessed before, add the character to the user guesses
            userGuesses.add(c);

            // If guessed Character is NOT in word
            if (!word.contains(String.valueOf(c))) {
                // Incorrect and not in incorrectGuessList add to list and - guess #
                if (!incorrectGuesses.contains(c)) {
                    incorrectGuesses.add(c);
                    numGuesses--;
                }
            }
        }

        updateWordState(userGuesses, word);
        updateAlphabetPreview();
        updateHangMan();

        // Check for win/loss conditions
        if (numGuesses <= 0) {
            System.out.println("You LOSE");
            wordPreview.setFont(javafx.scene.text.Font.font("Monaco", 20));
            wordPreview.setText("YOU LOSE  \n The word was: "+word);
            gameOver();

            // If guessed word matches word
        } else if (word.chars().allMatch(ch -> userGuesses.contains((char) ch))) {

            System.out.println("You WIN!");
            wordPreview.setFont(javafx.scene.text.Font.font("Monaco", 20));
            wordPreview.setText("YOU WIN !!! \n You Guessed the Word with "+ numGuesses + " guesses left. \n Word was:" + word);
            gameOver();

        }
    }

    /**
     * Logic for handling Word Guesses
     * @param guess User Guess
     */
    private void wordGuess(String guess) {
        wordGuesses.add(guess);

        if (guess.equals(word)) {
            wordPreview.setFont(javafx.scene.text.Font.font("Monaco", 20));
            wordPreview.setText("YOU WIN !!! \n You Guessed the Word with "+ numGuesses + " guesses left. \n Word was:" + word);

            gameOver();


            // Could reset the game here
        } else {
            System.out.println("You Guessed WRONG");
            numGuesses--;
            updateHangMan();

            // Check for loss condition
            if (numGuesses <= 0) {
                System.out.println("You LOSE");
                wordPreview.setFont(javafx.scene.text.Font.font("Monaco", 20));
                wordPreview.setText("YOU LOSE  \n The word was: "+word);
                gameOver();
            }
        }

        updateAlphabetPreview();
    }

    /**
     * Gameover method, allows for certain components to not be interacted with after game is over
     */
    public void gameOver(){
        userInputField.setEditable(false);
        userWordGuessField.setEditable(false);
        submitButton.setDisable(true);
        //alphabetPreview.setVisible(false);
        //wordPreview.setText(word);
    }


    /**
     * Update State of Word and reveals correct characters in the word
     * _ _ _ _ --> W O R D
     * @param userGuesses character list of user guesses
     * @param word word wanted to be guessed
     */
    public void updateWordState(List<Character> userGuesses , String word){
        String newPreview= "";
        for(int i= 0; i < word.length();i++){
            //char currentChar = word.charAt(i);


            if(userGuesses.contains(word.charAt(i))){
                newPreview += word.charAt(i);
                newPreview += " ";
            }
            else{
                newPreview += "-";
                newPreview += " ";
            }

        }
        //System.out.print(newPreview );
        this.wordPreview.setText(newPreview);
    }

    /**
     * Updates the hangman stage based on number of user guesses
     */
    public void updateHangMan(){

        if(numGuesses == 5){
            hangmanStage.setText(hangmanStages.get(1));
        }
        if(numGuesses == 4){
            hangmanStage.setText(hangmanStages.get(2));
        }
        if(numGuesses == 3){
            hangmanStage.setText(hangmanStages.get(3));
        }
        if(numGuesses == 2){
            hangmanStage.setText(hangmanStages.get(4));
        }
        if(numGuesses == 1){
            hangmanStage.setText(hangmanStages.get(5));
        }
        if(numGuesses == 0){
            hangmanStage.setText(hangmanStages.get(6));

        }
    }

    /**
     * Update Alphabet Preview by changing incorrect letters to color RED
     * Creates Text object for each letter
     */
    private void updateAlphabetPreview() {

        // get all Text boxes and call clear on each
        alphabetPreview.getChildren().clear();
        // Loop through alphabet to create text object for each letter to be able to change later
        for (char letter : alphabet.toCharArray()) {
            Text letterText = new Text(String.valueOf(letter));

            /*
            if(userGuesses.contains(String.valueOf(letter))){
                letterText.setFill(Color.GREEN);
            }
            */

            // Check if has been guessed and incorrect = redf
             if(incorrectGuesses.contains(letter)){
                // If guess contains letter set strikethrough true
                letterText.setFill(Color.RED);
            }
            // Not been guessed = black
            else {
                letterText.setFill(Color.BLACK);
            }

            Text space = new Text(" ");

            // Add letter and space to alphabetPreview
            alphabetPreview.getChildren().addAll(letterText,space);

        }
    }

    /**
     * Sets alphabet preview visible
     */
    public void initializeAlphabetPreview() {
        alphabetPreview.getChildren().clear(); // Clear any existing content

        // Loop through the alphabet
        for (char letter : alphabet.toCharArray()) {
            Text letterText = new Text(String.valueOf(letter));

            // Set the default color to black
            letterText.setFill(Color.BLACK);

            // Add space for better separation
            Text space = new Text(" ");

            // Add the letter and space to the alphabetPreview
            alphabetPreview.getChildren().addAll(letterText, space);
        }
    }

    /**
     * Handles Start Game Logic
     * Can take in custom word from user or grabs random number from Word List
     * Shows beginning popup then transitions to main game scene when user is ready
     * @param event Start game Button Pressed
     */
    @FXML
    void handlePlayHangman(ActionEvent event) {
        // Take in custom word if any
        String customWord = customHangmanWord.getText().trim().toUpperCase();

        System.out.println(customWord);
        // If no custom word is wanted
        if (this.customHangmanWord.getText().isEmpty()) {
            // Get a new random word for game
            Random rand = new Random();
            this.setWord(words.get(rand.nextInt(words.size())).toUpperCase());
        } else {
            // Set the custom word
            this.setWord(customWord);
        }

        // Ensure the word is set before proceeding
        if (word == null || word.isEmpty()) {
            System.out.println("Error: No word set for the game.");
            return; // Exit the method to prevent further errors
        }

        // Update the game state to start the game
        setHangmanStage(0);
        initializeAlphabetPreview();

        numGuesses = 6; // Reset number of guesses

        hangmanStage.setFont(javafx.scene.text.Font.font("Monaco", 30));

        updateWordState(userGuesses, word); // Update the displayed word
        updateHangMan(); // Reset the hangman state

        startPopUp.setVisible(false); // Hide the popup


        gameComponents1.setVisible(true);
        gameComponents2.setVisible(true);


        userInputField.requestFocus(); // Focus on the input field
        System.out.println("Word is:" + word);
    }
}
