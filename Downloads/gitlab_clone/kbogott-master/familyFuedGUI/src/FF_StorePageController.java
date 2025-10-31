import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class FF_StorePageController {

    public int userCash; // Variable for User Cash      //  TODO: CHANGE TO ACTUAL PLAYER CASH VALUE


    public void setUserCash(int cash){
        this.userCash = cash;
        updateCashDisplay(cash);
    }

    @FXML
    private Label userCashLabel;
    @FXML
    private Label outcomeMessage;
    @FXML
    private Button backToLobbyButton;
    @FXML
    private Button buy2XPointsButton;
    private static final int PRICE_DOUBLE_POINTS = 25;
    @FXML
    private Button buyGreenButton;
    private static final int PRICE_GREEN_BUTTON = 50;
    @FXML
    private Button buyPinkButton;
    private static final int PRICE_PINK_BUTTON = 50;

    @FXML
    void handleBackToLobbyButton(ActionEvent event) {
        try {
            // Load the new FXML file
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FF_Lobby.fxml"));
            Parent loginScreenRoot = fxmlLoader.load();

            // Create a new scene
            Scene loginPopupScene = new Scene(loginScreenRoot);

            // Get the current stage using the event that was passed to the handler
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Set the new scene
            currentStage.setScene(loginPopupScene);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    void handleBuyDoublePoints(ActionEvent event) {

        int playerCash;  //  TODO: CHANGE TO ACTUAL PLAYER CASH VALUE

        // CHECK IF USER CAN BUY
        if (canPurchaseItem(PRICE_DOUBLE_POINTS, userCash)) {
            outcomeMessage.setText("Double Points purchased successfully!");

            userCash -= PRICE_DOUBLE_POINTS; // TAKE AWAY FROM BALANCE

            updateCashDisplay(userCash); // Update Display

            // Logic to give the player the green item

            // player.addItem(DOUBLE_POINTS) ?
        }
    }
    @FXML
    void handleBuyGreenButton(ActionEvent event) {

        // CHECK IF USER CAN BUY
        if (canPurchaseItem(PRICE_GREEN_BUTTON, userCash)) { // Will Print MSG to system if user cannot purchase
            outcomeMessage.setText("Green Button purchased successfully!");

            userCash -= PRICE_GREEN_BUTTON; // TAKE AWAY FROM BALANCE

            updateCashDisplay(userCash); // Update Display

            // Logic to give the player the green item
        }
    }
    @FXML
    void handleBuyPinkButton(ActionEvent event) {

        if (canPurchaseItem(PRICE_PINK_BUTTON,userCash)) {
            outcomeMessage.setText("Pink Button purchased successfully!");

            userCash -= PRICE_PINK_BUTTON; // TAKE AWAY FROM BALANCE

            updateCashDisplay(userCash); // Update Display
            // Logic to give the player the green item
        }
    }

    /**
     * Checks if User Can Actually Buy Item
     * @param cost Cost of Item
     * @param playerCash Player Cash
     * @return True of False if user can buy item
     */
    private boolean canPurchaseItem(int cost, int playerCash) {

        if (playerCash >= cost) {
            // If Player has more cash -> Buy Item and remove total from player cash bank

            playerCash -= cost;

            updateCashDisplay(playerCash); // Update the UI to reflect the new cash amount

            return true;
        } else {
            System.out.println("Not enough cash to buy this item!");
            return false;
        }
    }

    /**
     * Updates Cash Display
     * @param playerCash Players Cash
     */
    private void updateCashDisplay(int playerCash) {
        userCashLabel.setText("Cash: "+ playerCash + "$");   // Cash : 0$
    }

}
