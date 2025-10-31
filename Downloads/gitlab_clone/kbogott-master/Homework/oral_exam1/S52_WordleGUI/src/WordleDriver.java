import java.util.Random;
import javax.swing.*;


/**
 * Driver Class for WordleGUI.java.
 * @see WordleGUI
 */
public class WordleDriver {
    /**
     * No Argument Constructor for WordleDriver Class.
     */
    WordleDriver() {
    }


    /**
     * Main method for WordleDriver Class.
     * Responsible for Calling WordleGUI constructor to create the GUI.
     * Sets each random answer before creation of each instance of the game.
     * Contains a main while loop that runs until the user wants to stop playing.
     *
     * @param args Arguments being passed in.
     * @see WordleGUI
     */
    public static void main(String[] args) {

        startGameMessage();


        //Call constructor
        WordleGUI GUI = new WordleGUI();
        Random rand = new Random();


        // Get Word Array and set answer to a random five-letter word.
        String[] wordArray = GUI.getWordArray();
        GUI.setAnswer(wordArray[rand.nextInt(wordArray.length)].toUpperCase());
        //System.out.println(GUI.answer);

        // While user wants to keep playing
        while (true) {
            //if user wants to play again create new instance of GUI and get a new five-letter word.
            if (GUI.getIsPlayAgain()) {
                GUI.dispose();
                GUI = new WordleGUI();
                GUI.setAnswer(wordArray[rand.nextInt(wordArray.length)].toUpperCase());
                System.out.println(GUI.answer);

            }
            // User does NOT want to play again
            else {
                break; // leave while loop and leave program.
            }
        }
    }

    /**
     *

     */
    /**
     * Displays a message dialog at the start of the game, explaining the rules of Wordle.
     */
    public static void startGameMessage() {
        /**
         * The rules of the game, displayed in a message dialog.
         */
        String rules = "Welcome to Wordle!\n" +
                "The goal is to guess a random 5-letter word.\n" +
                "You have 6 attempts to guess the word.\n" +
                "Green tiles indicate correct letters in the correct position.\n" +
                "Yellow tiles indicate correct letters in incorrect positions.\n" +
                "Red tiles indicate incorrect letters.\n";

        /**
         * Displays the rules of the game in a message dialog.
         * @param null the parent component of the dialog.
         * @param rules the text to be displayed in the dialog.
         * @param "Wordle Rules" the title of the dialog.
         * @param JOptionPane.INFORMATION_MESSAGE the type of message to be displayed.
         */
        JOptionPane.showMessageDialog(null, rules, "Wordle Rules", JOptionPane.INFORMATION_MESSAGE);
    }
}