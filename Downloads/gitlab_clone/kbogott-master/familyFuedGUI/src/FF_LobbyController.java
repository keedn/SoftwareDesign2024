import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class FF_LobbyController{
    @FXML
    private Button playButton;
    @FXML
    private Button storeButton;
    @FXML
    void handlePlayButton(ActionEvent event) {
        // Send User to Choose Team
        System.out.println("[Play Button Pressed]");
        goToChooseTeamScreen(event);
    }
    @FXML
    void handleStoreButton(ActionEvent event){
        System.out.println("[Store Button Pressed]");
        goToStore(event);
    }
    @FXML
    void handleLeaveButton(ActionEvent event){
        System.out.println("[Leave Button Pressed]");
        Platform.exit();
    }

    private void goToStore(ActionEvent event){
        try {
            // Load the new FXML file
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FF_StorePage.fxml"));
            Parent storeScreenRoot = fxmlLoader.load();

            // TODO: PASS ACTUAL USER CASH ? INFO
            // Get Controller and Pass User Cash to Store page controller
            FF_StorePageController storePageController = fxmlLoader.getController();

            // player.getCash
            int cash = 750;
            storePageController.setUserCash(cash);

            // Create a new scene
            Scene storeScene = new Scene(storeScreenRoot);

            // Get the current stage using the event that was passed to the handler
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Set the new scene
            currentStage.setScene(storeScene);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void goToChooseTeamScreen(ActionEvent event){
        try {
            // Load the new FXML file
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FF_ChooseTeam.fxml"));
            Parent lobbyScreenRoot = fxmlLoader.load();

            // Create a new scene
            Scene chooseTeamScene = new Scene(lobbyScreenRoot);

            // Get the current stage using the event that was passed to the handler
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Set the new scene
            currentStage.setScene(chooseTeamScene);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
