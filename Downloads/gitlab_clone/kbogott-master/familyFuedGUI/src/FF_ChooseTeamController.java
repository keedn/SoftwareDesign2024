import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class FF_ChooseTeamController {
    private int teamOneSize = 0;
    private int teamTwoSize = 0;
    private int count = 1; // amount of players in game
    private Map<String,Integer> playerTeamOne = new HashMap<>(); // Holds Player Team Info ( USER, TEAM )
    private Map<String,Integer> playerTeamTwo = new HashMap<>(); // Holds Player Team Info ( USER, TEAM )


    @FXML
    private Button backButton;
    @FXML
    private Button joinTeamOneButton;
    @FXML
    private Button joinTeamTwoButton;

    @FXML
    private Label player_11; // Team 1 , Player 1
    @FXML
    private Label player_12;
    @FXML
    private Label player_13;
    @FXML
    private Label player_14;
    @FXML
    private Label player_15;
    @FXML
    private Label player_21; // Team 2, Player 1
    @FXML
    private Label player_22;
    @FXML
    private Label player_23;
    @FXML
    private Label player_24;
    @FXML
    private Label player_25;
    @FXML
    private Label sizeLabe1;
    @FXML
    private Label sizeLabel2;
    @FXML
    private Label teamFullLabel;

    /**
     * Player Presses Team 1 Button
     */
    @FXML
    void handleTeamOneJoinButton(ActionEvent event) {

        String username = "[userName]";

        if(teamOneSize < 5){    // If not full
            teamOneSize++;

            addPlayerTeamOne(teamOneSize  , username);  // Add Player to Team
            playerTeamOne.put(username,1);              // Add to Team 1 Map

            // TODO: APPEND PLAYER TEAM TO PLAYER

            count++;
            updateTeamSizeLabel(); // update Team Size label
        }
        else{
            teamFullLabel.setText("Team 1 Is Already Full");
        }
        updateTeamSizeLabel();
        checkTeamsFull(event); // Check teams are full, if yes ->  send to game screen
    }

    /**
     * Player Presses Team 2 Button
     */
    @FXML
    void handleJoinTeamTwoButton(ActionEvent event) {
        String username = "[userName]";

        if(teamTwoSize < 5){
            teamTwoSize++;

            addPlayerTeamTwo(teamTwoSize  , username);  // Add Player to Team
            playerTeamTwo.put(username, 2);            // Put User into Team Map

            // TODO: APPEND PLAYER TEAM TO PLAYER
            count++;
            updateTeamSizeLabel(); // update Team Size label
        }
        else{
            teamFullLabel.setText("Team 2 Is Already Full");
        }

        updateTeamSizeLabel(); // Update Size Label
        checkTeamsFull(event); // Check teams are full, if yes ->  send to game screen //
    }

    /**
     * Send Clients to Game Once Teams Are Full
     * @param event Event Source
     */
    private void checkTeamsFull(ActionEvent event) {
        if (teamOneSize == 5 && teamTwoSize == 5) {
            System.out.println("Both teams are full. Starting the game!");
            goToMainGame(event); // temp send player to game when full
        }
    }

    /**
     * Sends Players To Game
     */
    private void sendPlayersToGame(){

    }
    @FXML
    void handleBackButton(ActionEvent event) {
        System.out.println("[Back Button Pressed]");
        goBackToLobby(event);
    }

    /**
     * Returns user to lobby
     * @param event Event
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

    /**
     * Sends Users to Main Game Screen\
     * Also Sets GameScreen Controller's playerTeams/
     * @param event Event
     */
    private void goToMainGame(ActionEvent event){
        try {
            // Load the new FXML file
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FF_GameScreen.fxml"));
            Parent loginScreenRoot = fxmlLoader.load();

            // Create a new scene
            Scene loginPopupScene = new Scene(loginScreenRoot);

            // Get Controller Instance
            FF_GameScreenController gameScreenController = fxmlLoader.getController();

            // Pass in User Teams
            gameScreenController.setPlayerTeamOne(playerTeamOne);
            gameScreenController.setPlayerTeamTwo(playerTeamTwo);

            // Get the current stage using the event that was passed to the handler
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Set the new scene
            //currentStage.setMaximized(true);

            currentStage.setScene(loginPopupScene);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds Player to Team Two
     * @param spot Spot on Team
     * @param name User's name
     */
    private void addPlayerTeamOne(int spot,String name){
        // Add player to Team 1
        switch (spot) {
            case 1:
                player_11.setText(name);
                break;
            case 2:
                player_12.setText(name);
                break;
            case 3:
                player_13.setText(name);
                break;
            case 4:
                player_14.setText(name);
                break;
            case 5:
                player_15.setText(name);
                break;
            default:
                break;
        }
    }

    /**
     * Adds Player to Team Two
     * @param spot Spot on Team
     * @param name User's name
     */
    private void addPlayerTeamTwo(int spot, String name){
        switch (spot) {
            case 1:
                player_21.setText(name);
                break;
            case 2:
                player_22.setText(name);
                break;
            case 3:
                player_23.setText(name);
                break;
            case 4:
                player_24.setText(name);
                break;
            case 5:
                player_25.setText(name);
                break;
            default:
                break;
        }
    }

    /**
     * Updates Size Text for Each team
     */
    private void updateTeamSizeLabel(){
        sizeLabe1.setText("Size: "+ teamOneSize + " / 5");
        sizeLabel2.setText("Size: "+ teamTwoSize + " / 5");
    }
}
