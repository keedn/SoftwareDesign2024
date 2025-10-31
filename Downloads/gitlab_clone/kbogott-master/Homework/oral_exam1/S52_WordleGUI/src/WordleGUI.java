import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

/**
 * A GUI recreation for the Wordle game, providing an interface for players to submit guesses and view previous submissions.
 * This class handles the creation and layout of the game's UI components, including panels, labels, text fields, and text areas.
 * @author kbogott
 * @version 1.0
 * @see WordleDriver
 */
public class WordleGUI extends JFrame implements ActionListener {

    /**
     * Array of possible five-letter words.
     */
    private static final String[] wordArray = {
            "Blaze", "Crisp", "Dwell", "Frost", "Grape",
            "Knack", "Latch", "Quest", "Brisk", "Chant",
            "Drift", "Elbow", "Fable", "Glaze", "Hover",
            "Ivory", "Jolly", "Kneel", "Ledge", "Minty",
            "Nifty", "Orbit", "Plume", "Quirk", "Rhyme",
            "Moist", "Short", "Haste", "Crazy", "Smoke",
            "Start", "Print", "Mourn", "Igloo", "Drive",
            "Strip", "Stink", "Later", "Mourn", "Flint",
            "First", "Third", "Chair", "Solve", "State",
            "Build", "Logic", "Array", "False", "Audio",
            "Video", "Input", "Event", "Level", "Power"

    };

    /**
     * A boolean to determine if user wants to play again or not.
     */
    public boolean playAgain = false;
    /**
     * A boolean to determine is the current game is over or not.
     */
    private boolean isGameOver = false;

    /**
     * A list to store previous answers for display in the answer history pane.
     */
    private final ArrayList<String> previousAnswersPanelList = new ArrayList<>();

    /**
     * The text pane to display the answer history.
     */
    private final JTextPane answerHistoryPane;

    /**
     * The text field for user input.
     */
    private final JTextField inputField;

    /**
     * An array of labels to represent the alphabet, used to display guessed letters.
     */
    private final JLabel[] alphabetLabels = new JLabel[26];

    /**
     * A boolean array to track which letters have been guessed.
     */
    private final boolean[] guessableLetters = new boolean[26];

    /**
     * An array to store previous guesses, used to check for duplicate submissions.
     */
    private  String[] previousGuessesList = new String[6];


    /**
     * A darker shade of green used for UI components.
     */
    public final Color darkerGreen = new Color(0, 40, 0);

    /**
     * A dark shade of green used for UI components.
     */
    public final Color darkGreen = new Color(0, 90, 0);

    /**
     * A light shade of green used for UI components.
     */
    public final Color lightGreen = new Color(0, 130, 0);

    /**
     * A celadon color used for UI components.
     */
    public final Color celadon = new Color(42, 170, 138);

    /**
     * A pastel green color used for UI components.
     */
    public final Color pastelGreen = new Color(193, 225, 193);

    /**
     * A seafoam green color used for UI components.
     */
    public final Color seafoamGreen = new Color(159, 226, 191);

    /**
     * The number of guesses made by the user.
     */
    public int numGuesses = 0;

    /**
     * The correct answer for the current game.
     */
    public String answer;

    /**
     * Set Answer to new answer.
     * @param answer New answer
     */
    public void setAnswer(String answer) {
        this.answer = answer;
    }
    /**
     * Returns Word Array containing all possible five-letter words
     * @return Word Array
     */
    public String[] getWordArray() {
        return wordArray;
    }

    /**
     * Returns isPlayAgain boolean value based on if user wants to replay or not.
     * @return isPlayAgain boolean value
     */
    public boolean getIsPlayAgain() {
        return playAgain;
    }

    /**
     * Initializes a new instance of the WordleGUI class, creating the main game frame and its components.
     * This constructor sets up the layout, adds panels and components, and configures the game's UI.
     */
    public WordleGUI() {

        // Create Main Frame
        JFrame mainFrame = new JFrame();
        mainFrame.setTitle("S52_WordleGUI");
        mainFrame.setLayout(new BorderLayout()); // set layout manager
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // exit out of app when frame closed
        mainFrame.setResizable(false); // prevents frame from being resized
        int mainFrameHeight = 540;
        int mainFrameWidth = 720;
        mainFrame.setSize(mainFrameWidth, mainFrameHeight); // size of frame
        mainFrame.setBackground(Color.black); // set background controller

        // Create left and right panels
        JPanel leftSidePanel = new JPanel();
        JPanel rightSidePanel = new JPanel();

        leftSidePanel.setBackground(seafoamGreen);
        leftSidePanel.setPreferredSize(new Dimension(160, 300)); // Set preferred size to 200x300
        //leftSidePanel.setBorder(thinSeaFoamyGreen);

        rightSidePanel.setBackground(seafoamGreen);
        rightSidePanel.setPreferredSize(new Dimension(160, 300)); // Set preferred size to 200x300
        //rightSidePanel.setBorder(thinSeaFoamyGreen);

        //Create Top wordle panel

        JPanel topPanel = new JPanel(new BorderLayout());
        Border thinGreenBorder = BorderFactory.createLineBorder(darkerGreen, 1);
        topPanel.setBorder(thinGreenBorder);
        topPanel.setBackground(lightGreen);


        JLabel wordleGameText = new JLabel(" ---- Wordle Game ---- ");
        wordleGameText.setForeground(darkerGreen);
        wordleGameText.setBorder(thinGreenBorder);
        wordleGameText.setFont(new Font("Serif", Font.PLAIN, 18));
        wordleGameText.setHorizontalAlignment(JLabel.CENTER);

        // Add to Top Panel
        topPanel.add(wordleGameText, BorderLayout.PAGE_START);

        // Create input panel for submissions
        JPanel inputPanel = new JPanel(); // create a panel to hold input field
        inputPanel.setBackground(lightGreen);
        inputPanel.setFont(new Font("Arial", Font.BOLD, 14));
        inputPanel.setLayout(new FlowLayout());  // flow layout

        // Create Input Field for User
        inputField = new JTextField(20);  // create input field 20 columns wide
        inputField.setBackground(celadon);
        inputField.addActionListener(this);    // add an action listener to the input field
        inputField.setToolTipText("Enter Guess Here");

        inputField.requestFocusInWindow(); // put focus on this first

        // Create the text label right of input box
        JLabel enterGuessLabel = new JLabel("ENTER GUESS:"); // create a JLabel with the desired text
        enterGuessLabel.setForeground(darkGreen);
        enterGuessLabel.setFont(new Font("Arial", Font.ITALIC, 18));
        enterGuessLabel.setHorizontalAlignment(JLabel.CENTER); // center the label horizontally
        enterGuessLabel.setForeground(Color.BLACK );
        enterGuessLabel.setBackground(Color.GREEN  );

        // Add components to input panel
        inputPanel.add( enterGuessLabel, BorderLayout.NORTH);
        inputPanel.add(inputField, BorderLayout.SOUTH);

        // Create Guessable Letters Panel
        JPanel guessableLettersPanel = new JPanel(); // create panel for all characters
        guessableLettersPanel.setToolTipText("Green: In Word |  Red: Not In Word");


        // Create labels for each letter in alphabet

        for (char c = 'A'; c <= 'Z'; c++) { // loop through each character from A - Z
            JLabel letterLabel = new JLabel(String.valueOf(c));

            letterLabel.setFont(new Font("Arial", Font.BOLD, 15)); // set font for each
            letterLabel.setHorizontalAlignment(JLabel.CENTER); // set to center alignment within its label
            // For 'A', c - 'A' = 65 - 65 = 0 (index 0 in the array) -- Uses ASCII values to loop through each letter
            alphabetLabels[c - 'A'] = letterLabel; // Store the label in the array

            guessableLettersPanel.add(letterLabel); //add each letter to the panel
            guessableLettersPanel.setBackground(celadon);
            guessableLettersPanel.setBorder(thinGreenBorder);
            guessableLetters[c - 'A'] = true; // make every letter true until guessed / will have to set this.guessableLetter[c - "A"] false for each guessed char

        } //End character label creation for-loop

      // Bottom panel for JFrame
        JPanel bottomPanel = new JPanel(new GridLayout(2, 1)); // create new panel for bottom components
        bottomPanel.add(guessableLettersPanel, BorderLayout.NORTH);
        bottomPanel.add(inputPanel, BorderLayout.SOUTH);

        // Create text pane for previous submissions
        answerHistoryPane = new JTextPane();
        answerHistoryPane.setBackground(pastelGreen);
        answerHistoryPane.setEditable(false);
        answerHistoryPane.setFocusable(false); // cant click on words


        // Create a panel to hold the answer history pane
        JPanel answerHistoryPanel = new JPanel(new BorderLayout());
        answerHistoryPanel.add(answerHistoryPane, BorderLayout.CENTER);

        // Add all to mainframe
        mainFrame.add(leftSidePanel, BorderLayout.WEST);
        mainFrame.add(rightSidePanel, BorderLayout.EAST);
        mainFrame.add(topPanel, BorderLayout.PAGE_START);
        mainFrame.add(answerHistoryPanel, BorderLayout.CENTER); // add the newest instanceof answer history
        mainFrame.add(bottomPanel, BorderLayout.SOUTH);
        mainFrame.setVisible(true);


    }// End Constructor

    /**
     * Handles the event when the user presses the Enter key.
     * <p>
     * This method takes in the user's input, checks if it's a valid 5-letter word,
     * and checks if it's been submitted before. If the input is valid, it adds it to the list of previous guesses,
     * changes the color of the inputted letters based on if they belong in the word or not,
     * and checks if the guess is correct or not. If the guess is correct, it displays a win message.
     * If the guess is incorrect, it displays a message indicating the number of attempts left.
     *
     * @param e the event to be processed , when the enter button is pressed
     */
    @Override
    public void actionPerformed(ActionEvent e) { // event gets called each time enter is pressed

        //Take in Input
        String userInput = inputField.getText().toUpperCase();

        // Check if each character is a valid character in guesss if not throw message
        for(char c: userInput.toCharArray()) {

            if(!Character.isLetter(c)) {
                JOptionPane.showMessageDialog(this,"Invalid Guess\nPlease Submit another Guess");
                inputField.setText("");
                return;
            }
        }

        if(hasBeenSubmitted(userInput)){
            JOptionPane.showMessageDialog(this, "YOU ALREADY ENTERED THIS SILLY");
            inputField.setText("");
            return;
        }

        //Check if Length of word is Valid
        if (userInput.length() != 5) {
            JOptionPane.showMessageDialog(this, "MUST ENTER A 5 LETTER WORD SILLY"); //throw an error pane to user
            this.inputField.setText("");
            return;
        }

        // Call Method to change color of inputted letters based on if they belong in the word or not
        changeShowcaseLetterColor(userInput);
        // Calls method to Add previous guess to panel and apply the corresponding color to the character as it is being added
        addPrevInputToPanel(userInput);

        // set blank for next response and iterate num of guesses (for next submission)
        this.inputField.setText("");
        previousGuessesList[numGuesses] = userInput; // store userInput in previousGuess list
        numGuesses++;


        //Check is Guess is Correct
        if (isRight(answer, userInput)){

            // Win Messages
            if(numGuesses == 1){
                JOptionPane.showMessageDialog(this, "FIRST TRY!!!!!" + "\nIt took you " + numGuesses + " guess.");
                isGameOver = true;
                System.out.println("You Win");
            }
            else{
                JOptionPane.showMessageDialog(this, "YOU WIN!" + "\nIt took you " + numGuesses + " guesses.");
               isGameOver = true;
                System.out.println("You Win");

            }
        }

        // If Guess is Incorrect
        else if(!isRight(answer, userInput)){
            int numGuessesLeft = 6 - numGuesses;

            System.out.println("Your answer is incorrect, you have " + (numGuessesLeft) + " attempts left." );
        }

        // If User has no more Guesses
        if(numGuesses == 6 ){
            this.inputField.setEditable(false);
            JOptionPane.showMessageDialog(this, "You Lose :( \nThe Word was: " + this.answer);
            isGameOver = true;
        }

        // If game is over prompt user on if they want to play again. If yes call restartGame(); else leave
        if(isGameOver){
          // show yes no buttons ...
            int response = JOptionPane.showConfirmDialog(this, "Do you want to play again?", "Game Over", JOptionPane.YES_NO_OPTION);
            // Yes pressed --> restart game
            if (response == JOptionPane.YES_OPTION) {
                restartGame();
            }
            // No pressed --> end game / frame
            else if (response == JOptionPane.NO_OPTION) {
                this.dispose();
                System.exit(0);
            }
        }

    } // End actionPerformed (Enter)

    /**
     * Resets the game state to its initial state.
     * This method resets the number of guesses, gameOver, previous guesses list, showcase letter colors,
     * and answer history pane. It also re-enables the input field for user input.
     * @see #restartGame()
     */
    public void resetGameState() {
        numGuesses = 0;
        previousGuessesList = new String[6];
        previousAnswersPanelList.clear();
        isGameOver = false;

        inputField.setEditable(true);
        answerHistoryPane.setText("");

        // Reset the colors of the showcase letters
        for (int i = 0; i < 26; i++) {
            JLabel correspondingLabel = alphabetLabels[i];
            correspondingLabel.setForeground(Color.BLACK); // Set to black
            guessableLetters[i] = true; // Reset the guessableLetters array so
        }

    }
    /**
     * Restarts the game by resetting the game state and generating a new random word.
     * This method calls resetGameState to reset all game variables and then generates
     * a new random word from the word array.
     * @see #resetGameState()
     */
    public void restartGame() {
        //call resetGameState to reset each var in game and determine a new random word from the list
        resetGameState();
        Random rand = new Random();
        answer = wordArray[rand.nextInt(wordArray.length)].toUpperCase();
    }
    /**
     * Checks if the user's input is correct or not.
     * @param answer the correct 5-letter word
     * @param userInput the user's 5-letter word submission
     * @return true if the input is correct, false otherwise
     */
    public boolean isRight(String answer, String userInput){
        // if answer = input and you win
        return answer.equals(userInput);
    } // END isRight

    /**
     * Changes the color of the letters that have been guessed. Based on if the letter is in the target word.
     * Changed to Green if letter is in word
     * Changed to Gray if not in word
     * @param userInput The user's five-letter word submission | String
     */
    public void changeShowcaseLetterColor(String userInput){
        // For each char in userInput
        for(int i = 0; i < userInput.length(); i++){
            char letter = userInput.charAt(i);

            // If the boolean value of the character is True and its it the usersInput change the color to Red
            if(this.guessableLetters[letter - 'A']) {
                this.guessableLetters[letter - 'A'] = false;
                JLabel correspondingLabel = this.alphabetLabels[letter - 'A'];  //set the label = to character being iterated through
                correspondingLabel.setForeground(Color.RED); // Set to red
            }

            // Check if the letter is in the answer
            if (answer.indexOf(letter) != -1) {
                // Check if the letter is in the correct position
                JLabel correspondingLabel = this.alphabetLabels[letter - 'A'];  //set the label = to character being iterated through
                //set the label = to character being iterated through
                if (answer.charAt(i) == letter) {
                    correspondingLabel.setForeground(Color.GREEN); // set to green
                } else {
                    correspondingLabel.setForeground(Color.YELLOW); // set to yellow
                }
            }
        }
    }

    /**
     * Adds the user's previous input to the panel, with each letter colored based on its correctness.
     * Green indicates a correct letter in the correct position, yellow indicates a correct letter in an incorrect position,
     * and red indicates an incorrect letter.
     * @param userInput the user's 5-letter word submission
     */
    public void addPrevInputToPanel(String userInput){
        // new Colored user input to be added
        StringBuilder coloredUserInput = new StringBuilder();

        // Check what color each should be then assign html color tags before each to change the color before there are put into coloredUserInput
        for (int i = 0; i < userInput.length(); i++) {

            char letter = userInput.charAt(i);
            // Change color to Green
            if(answer.charAt(i) == letter){
                coloredUserInput.append("<font color='green'>").append(letter).append("</font>");
            }
            //Change color to Yellow
            else if (answer.indexOf(letter) != -1) {
                coloredUserInput.append("<font color='yellow'>").append(letter).append("</font>");
            }
            // Change Color to Red
            else {
                coloredUserInput.append("<font color='red'>").append(letter).append("</font>");
            }
        }
        // add new colored input
        this.previousAnswersPanelList.add(coloredUserInput.toString());

        // Create the text to be printed to JFrame
        StringBuilder answerHistoryText = new StringBuilder();

        // for each answer create the string and new line after each using html tags
        for (String answer : previousAnswersPanelList) {
            answerHistoryText.append(answer).append("<br>"); // <br> is the line break
        }

        answerHistoryPane.setContentType("text/html"); // Set content type to HTML to support color change above
        answerHistoryPane.setText("<html><center><font size='16'>" + answerHistoryText + "</font></center></html>");  // set text to new text with all previous submissions and center with HTML tags
    }

    /**
     * Checks if the user's input has been previously submitted or not.
     * @param userInput the user's 5-letter word submission
     * @return true if the input has been previously submitted, false otherwise
     */
    public boolean hasBeenSubmitted(String userInput){
        if(previousGuessesList != null) {
            for(String guess: previousGuessesList){
                if(guess != null && guess.equals(userInput)){ // check if
                    return true;
                }
            }
        }
        return false;
    }


}//end WorldleGUI class

