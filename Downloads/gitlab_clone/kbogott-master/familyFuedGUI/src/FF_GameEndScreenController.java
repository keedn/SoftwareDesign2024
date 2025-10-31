import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.util.Optional;

public class FF_GameEndScreenController {
    @FXML
    private Button leaveButton;
    @FXML
    private Button playAgainButton;
    @FXML
    private Button storeButton;
    @FXML
    private Label teamOneScoreLabel;
    @FXML
    private Label teamTwoScoreLabel;
    @FXML
    private Label teamWinMessageLabel;
    @FXML
    private Label cashEarnedLabel;

    @FXML
    void handleLeaveButton(ActionEvent event) {
        // Handle Leave
        Platform.exit();
    }

    /**
     * Sends User To Lobby if press Play Again
     * @param event
     */
    @FXML
    void handlePlayAgainButton(ActionEvent event) {
        // If User Wants to Play Again Send Back to Lobby Screen
        goBackToLobby(event);
    }

    @FXML
    void handleStoreButton(ActionEvent event) {
        // Send User to Store
        System.out.println("[Store Button Pressed]");
        goToStore(event);
    }

    /**
     * Sends User To Store ( needs to have users cash to update in store before sending )
     */
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

    /**
     * Sends User to Lobby
     */
    private void goBackToLobby(ActionEvent event){
        try {
            // Load the new FXML file
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FF_Lobby.fxml"));
            Parent loginScreenRoot = fxmlLoader.load();

            // Create a new scene
            Scene loginPopupScene = new Scene(loginScreenRoot);

            // Get the current stage using the event that was passed to the handler
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();


            currentStage.setScene(loginPopupScene);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }




}
