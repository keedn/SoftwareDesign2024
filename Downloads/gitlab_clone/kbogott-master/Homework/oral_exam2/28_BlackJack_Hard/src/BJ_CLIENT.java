import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class BJ_CLIENT extends JFrame {

    private int clientID;
    private Socket clientSocket;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;
    JButton hitButton,standButton,leaveButton;
    JPanel scorePanel;
    JPanel finalMessagePanel;
    JLabel playerScoreLabel,dealerScoreLabel,finalMessageLabel;
    GAME_STATE gameState;
    JPanel playerHandPanel, dealerHandPanel;


    // Set up GUI

    /**
     * Constructor for Client
     * Sets up GUI
     */
    public BJ_CLIENT(){
        super("Client");
        setLayout(new GridLayout(3,1));

        // Create Main Hand Panel
        JPanel handPanel = new JPanel(new GridLayout(1,2));

        // Create Player Hand Panel
        playerHandPanel = new JPanel(new FlowLayout());
        playerHandPanel.setBorder(BorderFactory.createTitledBorder("Player's Hand:")); // add cool border

        // Create Dealers hand Panel
        dealerHandPanel = new JPanel(new FlowLayout());
        dealerHandPanel.setBorder(BorderFactory.createTitledBorder("Dealer's Hand:")); // add cool border

        // Add to hand panel
        handPanel.add(playerHandPanel,BorderLayout.NORTH);
        handPanel.add(dealerHandPanel,BorderLayout.WEST);

        // Create Hand Score Panel
        scorePanel = new JPanel();
        scorePanel.setLayout(new GridLayout(1,2));
        scorePanel.setBorder(BorderFactory.createTitledBorder("Hand Values:")); // add cool border

        // Create Player Label
        playerScoreLabel = new JLabel("Player: ");
        // Create Dealer Label
        dealerScoreLabel = new JLabel("Dealer: ");

        // Add Player and Dealer Labels
        scorePanel.add(playerScoreLabel);
        scorePanel.add(dealerScoreLabel);

        //Create Final Message Outcome Panel / Label
        finalMessagePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        finalMessageLabel = new JLabel("text");

        finalMessagePanel.add(finalMessageLabel);
        finalMessageLabel.setBorder(BorderFactory.createTitledBorder("Hand Outcome: ")); // add cool border

        // Create Final Message Panel
        JPanel scoreFinalMessagePanel = new JPanel(new GridLayout(1,2));
        scoreFinalMessagePanel.add(scorePanel);
        scoreFinalMessagePanel.add(finalMessageLabel,BorderLayout.CENTER);

        // Create Panel for Buttons
        JPanel buttonPanel = new JPanel(new GridLayout(1,3));
        buttonPanel.setSize(600,125);

        // Create Buttons
        hitButton = new JButton("Hit");
        standButton = new JButton("Stand");
        leaveButton = new JButton("Leave");

        addButtonActionListeners();

        // Add to button Panel
        buttonPanel.add(hitButton);
        buttonPanel.add(standButton);
        buttonPanel.add(leaveButton);

        // Add to Main Frame
        add(handPanel,BorderLayout.CENTER);
        add(scoreFinalMessagePanel,BorderLayout.SOUTH);
        add(buttonPanel,BorderLayout.SOUTH);

        setSize(800,425);
        setVisible(true);

    }

    /**
     * Main Constructor
     */
    public void startClient(){
        try {
            clientSocket = new Socket("localhost",23825); // Set Client Socket
            System.out.print("Client>>> Client Started.. Connected\n");

            // Get IO Streams
            outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            inputStream = new ObjectInputStream(clientSocket.getInputStream());

            // Receive Client ID from Server
            String idMessage = (String) inputStream.readObject();
            clientID = Integer.parseInt(idMessage.split(": ")[1]); // Take in only the number

            // Handle Interactions With Server
            handleInteraction();


        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection();
        }
    }

    /**
     * Handles and processes incoming GAME_STATE and any information being read from input stream
     */
    private void handleInteraction(){
        boolean gameRunning = true;

        while(gameRunning){
            // Read Game State from Sever
            try {
                System.out.println("\nWaiting for Action.. ");
                Object recievedObject = inputStream.readObject();

                // IF Game State --> TODO update Display
                if(recievedObject instanceof GAME_STATE){

                    GAME_STATE recievedState = (GAME_STATE) recievedObject; // Take in game state
                    gameState = recievedState; // Store i
                    System.out.println("GAME_STATE instance hash: " + System.identityHashCode(recievedState));

                    // Get Action and ID
                    PlayerAction.Action action = recievedState.getAction().getActionType();
                    int recievedID = recievedState.getAction().getClientID();
                    System.out.println("\nRecieved ID:" + recievedID);

                    // Start Game Message
                    if(action == PlayerAction.Action.START_GAME){
                        // Set Button Active
                        hitButton.setEnabled(true);
                        standButton.setEnabled(true);
                        hitButton.setBackground(Color.white);
                        standButton.setBackground(Color.white);
                        playerHandPanel.setBackground(Color.WHITE);
                        dealerHandPanel.setBackground(Color.WHITE);
                        finalMessageLabel.setText("");

                        Hand newHand = recievedState.getHand(clientID);
                        System.out.println("Received Hand: " + newHand.showHand());

                        recievedState.updateHand(clientID,newHand); // store hand in client ID
                        updatePlayerHand(newHand);

                        updateDealerFirstHand(recievedState.getDealerHand()); // update dealer hand
                    }

                    // CLIENT HIT
                    if(action == PlayerAction.Action.HIT && recievedID == clientID){
                        Hand newHand = recievedState.getHand(clientID);

                        // If User Bust
                        if(checkBust(newHand)){
                            //Create bust action
                            PlayerAction bust = new PlayerAction(PlayerAction.Action.BUST,clientID);

                            synchronized (recievedState){
                                recievedState.setAction(bust);
                                sendGameState(recievedState);
                            }

                        }

                        System.out.println("Received Hand from Hit : " + newHand.showHand());
                        updatePlayerHand(newHand);

                        // Store Hand


                    }
                    // Check Dealer BJ
                    if(action == PlayerAction.Action.DEALER_BJ){
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                finalMessageLabel.setText("Dealer has BlackJack , dealing new hand..");
                                finalMessageLabel.revalidate();
                                finalMessageLabel.repaint();
                                System.out.println("Label updated: " + finalMessageLabel.getText());

                            }
                        });
                    }

                    if (action == PlayerAction.Action.END_HAND) {
                        Hand newDlrHand = recievedState.getDealerHand(); // Take New Hand
                        Hand myHand = recievedState.getHand(clientID);

                        System.out.println("New Dealer Hand: " + newDlrHand.showHand());
                        updateFinalDealerHand(newDlrHand);

                        int myHandVal = myHand.calculateValueOfHand();
                        updateFinal(newDlrHand, myHandVal);

                    }

                    if (action == PlayerAction.Action.FLIP) {
                        Hand newDlrHand = recievedState.getDealerHand(); // Take New Hand

                        System.out.println("New Dealer Hand: " + newDlrHand.showHand());
                        updateFinalDealerHand(newDlrHand);

                        // Get Players Score
                        String playerScore =  playerScoreLabel.getText();
                        String scoreString = playerScore.replaceAll("[^0-9]", ""); // Remove non-numeric characters
                        int playerScoreINT = Integer.parseInt(scoreString);
                        updateFinal(newDlrHand,playerScoreINT);
                    }


                    setGameState(recievedState);
                   //displayGameState(recievedState);

                }
                else if(recievedObject instanceof String){
                    String message = (String) recievedObject;
                    System.out.println(message);
                }

            } catch (ClassNotFoundException | IOException e) {
                throw new RuntimeException(e);
            }


        }
    }

    /**
     * Updates Player Hand
     * @param hand new hand
     */
    private synchronized void updatePlayerHand(Hand hand){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                playerHandPanel.removeAll(); // clear

                // Add each cards string to hand panel
                for(Card card : hand.getCards()){
                    JLabel cardLabel = new JLabel(card.toString());
                    playerHandPanel.add(cardLabel);
                }

                playerScoreLabel.setText("Player: " + hand.calculateValueOfHand());
                playerHandPanel.revalidate();
                playerHandPanel.repaint();
            }
        });
    }

    /**
     * Updates Dealers First Hand
     * @param hand
     */
    private void updateDealerFirstHand(Hand hand){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                dealerHandPanel.removeAll(); // clear

                JLabel cardLabel1 = new JLabel(hand.getCards().get(0).toString());
                JLabel cardLabel2 = new JLabel("[ ? ]");

                dealerHandPanel.add(cardLabel1);
                dealerHandPanel.add(cardLabel2);

                dealerScoreLabel.setText("Dealer: " + hand.getCards().get(0).getCardInfo().cardNum + " + ?");
                dealerHandPanel.revalidate();
                dealerHandPanel.repaint();
            }
        });
    }

    /**
     * Updates Final Dealers Hand
     * @param hand hand
     */
    private synchronized void updateFinalDealerHand(Hand hand){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                dealerHandPanel.removeAll(); // clear

                // Add each cards string to hand panel
                for(Card card : hand.getCards()){
                    JLabel cardLabel = new JLabel(card.toString());
                    dealerHandPanel.add(cardLabel);
                }
                dealerScoreLabel.setText("Dealer: " + hand.calculateValueOfHand());
                dealerHandPanel.revalidate();
                dealerHandPanel.repaint();
            }
        });
    }

    /**
     * Determines who wins and updates final message label to winner
     * @param dealerHand Final Hand
     * @param userVal User final value
     */
    private synchronized void updateFinal(Hand dealerHand, int userVal){
        int dealerVal = dealerHand.calculateValueOfHand();

        // If User Busts and Dealer Wins
        if(userVal > 21){
            dealerWin("User Bust .... Dealer Wins");
            return;
        }
        // Dealer Bust User Wins
        if(dealerVal > 21){
            userWin("Dealer Busted... You Win ");
            return;
        }
        if(userVal > dealerVal && userVal <= 21){
            userWin("You Beat the House");
        }
        if(userVal < dealerVal && dealerVal <=21){
            dealerWin("Dealer Wins");
        }
        if(userVal == dealerVal){
            push("Push.. boring");
        }
    }

    /**
     * User Win
     */
    private void userWin(String message){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
               playerHandPanel.setBackground(Color.GREEN);
               dealerHandPanel.setBackground(Color.RED);
                finalMessageLabel.setText(message);

            }
        });
    }

    /**
     * User Win
     */
    private void dealerWin(String message){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                playerHandPanel.setBackground(Color.RED);
                dealerHandPanel.setBackground(Color.GREEN);
                finalMessageLabel.setText(message);
            }
        });
    }

    /**
     * Push Message
     * @param message Message
     */
    private void push(String message){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                playerHandPanel.setBackground(Color.GRAY);
                dealerHandPanel.setBackground(Color.GRAY);
                finalMessageLabel.setText(message);
            }
        });
    }

    /**
     * Adds Action Listeners to Buttons
     */
    private void addButtonActionListeners(){
        // Hit Button Logic
        // Get Card and Check for Bust
        hitButton.addActionListener(new ActionListener() {
            @Override
            public synchronized void actionPerformed(ActionEvent e) {

                    PlayerAction hit = new PlayerAction(PlayerAction.Action.HIT,clientID);
                    gameState.setAction(hit);
                    sendGameState(gameState);

            }
        });
        // STAND BUTTON LOGIC
        standButton.addActionListener(new ActionListener() {
            @Override
            public synchronized void  actionPerformed(ActionEvent e) {
                PlayerAction stand = new PlayerAction(PlayerAction.Action.STAND,clientID);
                gameState.setAction(stand);

                Hand hand = gameState.getHand(clientID);
                hand.setStatus(PlayerAction.Action.STAND);

                // Disable Buttons
                hitButton.setEnabled(false);
                standButton.setEnabled(false);

                sendGameState(gameState);

            }
        });
        // LEAVE BUTTON LOGIC
        leaveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                synchronized (gameState){
                    gameState.setActionTYPE(PlayerAction.Action.LEAVE);
                    sendGameState(gameState);
                    dispose();
                }
            }
        });
    }

    /**
     * Checks if User Busts
     */
    private synchronized boolean checkBust(Hand hand){

        if (hand.calculateValueOfHand() > 21) {
            System.out.println("Player " + clientID + " busts!");

            // Disable Buttons From Being Pressed Again
            hitButton.setEnabled(false);
            hitButton.setBackground(Color.RED);
            standButton.setEnabled(false);
            standButton.setBackground(Color.RED );

            PlayerAction action = new PlayerAction(PlayerAction.Action.BUST,clientID);
            gameState.setAction(action);

            hand.setStatus(PlayerAction.Action.BUST); //

            return true;
        }
        return false;
    }

    /**
     * Sends GAME_STATE object to the server
     *
     * @param gameState The game state to send
     */
    private void sendGameState(GAME_STATE gameState) {
        try {
            outputStream.writeObject(gameState);
            outputStream.flush();
        } catch (IOException e) {
            System.err.println("Failed to send GAME_STATE to server: " + e.getMessage());
        }
    }
    private synchronized void setGameState(GAME_STATE gamestate){
        synchronized (gamestate){
            this.gameState = gamestate;
        }
        System.out.print("Current Action: " + gamestate.getActionTYPE());

    }

    /**
     * Closes the client connection and resources
     */
    private void closeConnection() {
        try {
            if (inputStream != null) {
                inputStream.close();
            }
            if (outputStream != null) {
                outputStream.close();
            }
            if (clientSocket != null) {
                clientSocket.close();
            }
            System.out.println("Connection closed.");
            dispose();
        } catch (IOException e) {
            System.err.println("Error closing connection: " + e.getMessage());
        }
    }

}
