import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FF_Driver extends Application {

    public static void main(String[] args){
        // Launch
        launch(args);
    }

    /**
     *
     * @param mainstage
     * @throws Exception
     */
    @Override
    public void start(Stage mainstage) throws Exception {

        // Load FXML File
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FF_STARTSCREEN.fxml"));
        Parent logInScreen = fxmlLoader.load();

        mainstage.setTitle("Family Feud"); // Set Title

        // Get Controller Instance
        FF_LogIn_RegisterScreenController logIn = fxmlLoader.getController();

        // Create Set -> Set Visible
        Scene scene = new Scene(logInScreen,650,350);

        // Set scene to mainStage and show Scene

        mainstage.setScene(scene); // Set current visible stage -> LogInScreen
        //mainstage.setMaximized(true);
        mainstage.show();

    }
}