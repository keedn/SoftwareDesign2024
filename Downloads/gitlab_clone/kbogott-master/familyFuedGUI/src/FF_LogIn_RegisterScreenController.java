import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class FF_LogIn_RegisterScreenController {

    //***************************** Start Screen ***********************************

    @FXML
    private Button loginButton; // Login Button
    @FXML
    private Button registerButton; // Register Button

    /**
     * Handle Logic when log in button pressed
     */
    @FXML
    private void handleLogInClick(){
        System.out.println("[Log In Button Pressed]"); // Log Button Press
        showLogInPopUp();                              // Send to Login Screen

    }

    /**
     * Handle logic when Register Button Pressed
     */
    @FXML
    private void handleRegisterClick(){
        System.out.println("[Register Button Pressed]"); // Log Pressed
        showRegisterPopUp();                             // Send to Register Screen
    }
    //***************************** Start Screen^ ***********************************


    //***************************** Log In Screen ***********************************

    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField passwordTextField;
    @FXML
    private CheckBox showPasswordCheckBox;
    @FXML
    private TextField usernameTextField;
    @FXML
    private Button submitButton;
    @FXML
    private Label outcomeMessageLabel;
    @FXML
    private Button backButton;

    /**
     * Flips Between a hidden and unhidden password
     * @param event Checkbox toggled
     */
    @FXML
    void handleShowPassCheckBox(ActionEvent event) {
        System.out.println("[Show Password Checkbox Toggled]");

        // Check if the "Show Password" checkbox is selected
        if (showPasswordCheckBox.isSelected()) {

            passwordTextField.setText(passwordField.getText()); // Set the visible text field's content to match the hidden password field's content

            passwordTextField.setVisible(true);                 // Make the text field visible and managed
            passwordTextField.setManaged(true);

            passwordField.setVisible(false);                    // Hide the password field (dots/hidden text) and unmanage it from the layout
            passwordField.setManaged(false);
        } else {

            passwordField.setText(passwordTextField.getText()); // Set the hidden password field's content to match the visible text field's content

            passwordField.setVisible(true);                     // Make the password field (dots/hidden text) visible and managed
            passwordField.setManaged(true);

            passwordTextField.setVisible(false);                // Hide the plain text field and unmanage it from the layout
            passwordTextField.setManaged(false);
        }


    }

    /**
     * Handles Submission of Log In
     *
     * @param event
     */
    @FXML
    void handleSubmitButton(ActionEvent event) {
        System.out.println("[Submit Button Pressed]");

        String usernameText = usernameTextField.getText();      // Get Username
        String passwordText = "";

        if(passwordField.isVisible()){                          // Check Which Field was active during Submission and Store Password
            passwordText = passwordField.getText();
        }
        else if(passwordTextField.isVisible()){
            passwordText = passwordTextField.getText();
        }

        // Check if Username and Password are valid ( has 8 characters ) -> can modify what makes a Valid user and pass in checkCredentials
        if(!checkCredentials(usernameText,passwordText)){
            outcomeMessageLabel.setText("Username and Password must be at least 8 characters long");
            outcomeMessageLabel.setTextFill(javafx.scene.paint.Color.RED);
            outcomeMessageLabel.setVisible(true);
        } else{

            if(doesAccountExist(usernameText, passwordText)){                      // If Account Exists in Database

                outcomeMessageLabel.setText("Account Found - Log in Successful");  // Successful Log In Messages
                System.out.println("Account Found - Log in Successful...");

                outcomeMessageLabel.setTextFill(javafx.scene.paint.Color.GREEN);    // Set Message Fill Green + Visible
                outcomeMessageLabel.setVisible(true);

                // TODO:  Send Information to Server

                System.out.println("Username Submitted:" + usernameText);        // Confirm Submission
                System.out.println("Password Submitted:" + passwordText);

                goToLobby(event);                                                // Send User to Game Lobby

            } else{
                // If Account does not exist
                outcomeMessageLabel.setText("No Account Found with that Information");        // Successful Log In Messages
                System.out.println("No Account Found - Log in Unsuccessful...");
            }
        }
    }
    @FXML
    void handleBackButton(ActionEvent event) {
        System.out.println("[Back Button Pressed]");
        returnToStartScreen(event);

    }

    //***************************** Log In Screen^ ***********************************

    //***************************** Register Screen ***********************************

    @FXML
    private Button registerBackButton;

    @FXML
    private Label registerOutcomeMessageLabel;

    @FXML
    private PasswordField registerPasswordField;

    @FXML
    private TextField registerPasswordTextField;

    @FXML
    private Button registerSubmitButton;

    @FXML
    private TextField registerUsernameTextField;

    @FXML
    private CheckBox showRegisterPasswordCheckBox;

    @FXML
    void handleRegisterBackButton(ActionEvent event) {
        System.out.println("[Back Button Pressed]");
        returnToStartScreen(event);
    }

    @FXML
    void handleRegisterSubmitButton(ActionEvent event) {
        System.out.println("[Submit Button Pressed]");

        String usernameText = registerUsernameTextField.getText();  // Retrieve Username
        String passwordText = "";

        // Determine Which field was active and store password
        if(registerPasswordField.isVisible()){
            passwordText = registerPasswordField.getText();
        }
        else if(registerPasswordTextField.isVisible()){
            passwordText = registerPasswordTextField.getText();
        }

        // Validate the Username , Password
        if(!checkCredentials(usernameText,passwordText)){
            registerOutcomeMessageLabel.setText("Username and Password must be at least 8 characters long");
            registerOutcomeMessageLabel.setTextFill(javafx.scene.paint.Color.RED);
            registerOutcomeMessageLabel.setVisible(true);
        } else{
            registerOutcomeMessageLabel.setText("Account Created Successfully");
            registerOutcomeMessageLabel.setTextFill(javafx.scene.paint.Color.GREEN);
            registerOutcomeMessageLabel.setVisible(true);

            // TODO:  Send Information to Server

            System.out.println("Username Registered:" + usernameText);              // Log Username, Password
            System.out.println("Password Registered:" + passwordText);

            goToLobby(event);                                                       // Send Client to Server
        }
    }

    @FXML
    void handleRegisterShowPassCheckBox() {
        System.out.println("[Show Password Checkbox Toggled]");

        // If Box if Checked -> Show Password
        if(showRegisterPasswordCheckBox.isSelected()){
            registerPasswordTextField.setText((registerPasswordField.getText())); // set text = current text
            registerPasswordTextField.setVisible(true);
            registerPasswordTextField.setManaged(true);

            // Hide Password Field
            registerPasswordField.setVisible(false);
            registerPasswordField.setManaged(false);
        }
        else {
            // Hide the password
            registerPasswordField.setText(registerPasswordTextField.getText());
            registerPasswordField.setVisible(true);
            registerPasswordField.setManaged(true);

            // Hide Text Field
            registerPasswordTextField.setVisible(false);
            registerPasswordTextField.setManaged(false);
        }
    }

    //***************************** Register Screen^ ***********************************

    private void returnToStartScreen(ActionEvent event){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FF_STARTSCREEN.fxml"));     // Load the new FXML file
            Parent loginScreenRoot = fxmlLoader.load();

            Scene loginPopupScene = new Scene(loginScreenRoot);                                             // Create a new scene
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();                 // Get the current stage using the event that was passed to the handler
            currentStage.setScene(loginPopupScene);                                                         // Set the new scene
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Helper Methods

    /**
     * Switch to Log In Screen
     */
    private void showLogInPopUp(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FF_LogInScreen.fxml"));            // Load the new FXML file
            Parent loginPopupRoot = fxmlLoader.load();
            Scene loginPopupScene = new Scene(loginPopupRoot);                                                      // Create a new scene


            Stage currentStage = (Stage) loginButton.getScene().getWindow();            // Get the current stage using the login button
            //currentStage.setFullScreen(true);                                         // Set to Fullscreen
            currentStage.setScene(loginPopupScene);                                     // Set the new scene
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Go to Register Screen
     */
    private void showRegisterPopUp(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FF_RegisterScreen.fxml"));
            Parent loginPopupRoot = fxmlLoader.load();

            Scene loginPopupScene = new Scene(loginPopupRoot);
            Stage currentStage = (Stage) loginButton.getScene().getWindow();
            //currentStage.setFullScreen(true);

            currentStage.setScene(loginPopupScene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *  Checks the Username and Password
     * @param username username
     * @param password password
     * @return True if Both pass requirements for Valid Password , False Otherwise
     */
    private boolean checkCredentials(String username, String password){
        if(username.length() > 8 || password.length() > 8 ){            // Check if length is above 8 characters

            return true;
        }
        System.out.println("Username or Password Invalid...");
        return false;
    }

    // Check if Account Exists
    private boolean doesAccountExist(String username, String password){
        System.out.println("Checking if Account: " + username + ": " + password + " exists.."); // Log


        return true;                                                                            // Return true until can check database
    }

    /**
     * Sends User to Lobby
     * @param event Event
     */
    private void goToLobby(ActionEvent event){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FF_Lobby.fxml"));
            Parent lobbyRoot = fxmlLoader.load();

            Scene lobbyScene = new Scene(lobbyRoot);
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.setScene(lobbyScene);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}

