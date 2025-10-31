import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * Main Driver class for Hangman Application
 */
public class HangmanDriver extends Application {
    /**
     * Main method for Driver
     * launches start method to load FXML file
     * @param args arguments being passed in
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     *
     * @param mainStage main stage in hangman
     * @throws Exception Exception to be thrown
     */
    @Override
    public void start(Stage mainStage) throws Exception {

        // Load FXML file
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("myHangmanGui.fxml"));
        Parent hangman = fxmlLoader.load();

        mainStage.setTitle("Hangman");
        // Get the controller instance from the FXMLLoader
        MyHangmanGuiController game = fxmlLoader.getController();

        // Create Scene
        Scene scene = new Scene(hangman); // Width px Height px

        // Set start game popup visible
        game.startPopUp.setVisible(true);

        // Set scene to mainStage and show scene
        mainStage.setFullScreen(true);
        mainStage.setScene(scene);
        mainStage.show();
    }
}