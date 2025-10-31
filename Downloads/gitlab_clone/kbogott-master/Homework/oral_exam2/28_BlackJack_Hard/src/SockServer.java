import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class SockServer implements Runnable {
    private final Socket connectionSocket; // Connection to client
    private ObjectOutputStream outputStream; // To send data
    private ObjectInputStream inputStream; // To receive data
    private int myConID; // Unique ID for the thread
    private boolean alive; // Connection state
    private GAME_STATE gameState; // Game State
    private BJ_SERVER server; // Link to Server

    /**
     * Constructor for Sock Server
     * @param counterNum number of client (ID)
     * @param connection connection socket to client
     * @param server Main Server
     */
    public SockServer(int counterNum, Socket connection,BJ_SERVER server) {
        this.myConID = counterNum;
        this.connectionSocket = connection;
        this.server = server;
        this.alive = true;
        this.gameState = new GAME_STATE(); // Create New Game
    }

    /**
     *  Gets input output streams
     *  sends a connection message to Client
     *  Handles and proccesses all objects read from input stream such as GAME_STATES
     */
    @Override
    public  void run() {

        try {
            getStreams(); // Get IO streams
            connectionMessage(); // send connection message
            sendData("You are Client " + myConID);
            sendClientID(myConID);

            // Run While Server Connection is Alive
            while(alive){
                try {
                    // Take in Game State
                    Object recievedObject = inputStream.readObject();

                    // If Game State -> Process
                    if(recievedObject instanceof GAME_STATE recievedState){
                        synchronized (recievedState){
                            processGameState(recievedState);
                        }
                    }

                } catch (ClassNotFoundException e) {
                    System.err.println("Unexpected object received from Client " + myConID);
                }
            }
        } catch (IOException e) {
            System.err.println("Error handling Client " + myConID + ": " + e.getMessage());
        } finally{
            closeConnection();
        }

    }


    /**
     * Get IO Streams
     *
     * @throws IOException if unable to connect
     */
    private synchronized void getStreams() throws IOException {
        outputStream = new ObjectOutputStream(connectionSocket.getOutputStream());
        inputStream = new ObjectInputStream(connectionSocket.getInputStream());
    }

    /** Send Message to Output Stream
     * @param message Message Being Sent
     */
    private synchronized void  sendData(String message) {
        try {
            outputStream.writeObject("SockServer>>>" + message);
            outputStream.flush();
        } catch (IOException e) {
            System.out.println("ERROR SENDING DATA FROM SOCKSERVER");
        }
    }

    /**
     * Processes Recieved Game State
      * @param recievedState Current Game State
     */
    private void processGameState(GAME_STATE recievedState){


            PlayerAction.Action action = recievedState.getActionTYPE();
            int playerID = recievedState.getAction().getClientID();
            System.out.println("Client " + myConID + " performed action: " + action);

            // TODO: Update the server's game logic and prepare a response

            // IF Player Hit
            if(action == PlayerAction.Action.HIT){
                // Draw Top Card
                Card newCard = recievedState.getDeck().takeTopCard();

                // Add To Correct Hand
                Hand hand = recievedState.getHand(playerID);
                hand.addCard(newCard); // Add Top Card

                // Update Map with new hand
                recievedState.getPlayerHands().put(playerID,hand);
                System.out.println("Player " + playerID + " new Hand : " + hand.showHand());

            }

            // If Stand or Bust
            if(action == PlayerAction.Action.STAND || action == PlayerAction.Action.BUST){

                // Get Hand
                Hand hand = recievedState.getHand(playerID);

                // If User Stand
                if (action == PlayerAction.Action.STAND) {
                    // Change users hand status to STAND and update GameState
                        hand.setStatus(PlayerAction.Action.STAND);
                        System.out.println("Player " + playerID + " Stood:  " + hand.showHand());
                }
                // If User Bust
                else if (action == PlayerAction.Action.BUST) {
                        // Change users hand status to Bust and update GameState
                    hand.setStatus(PlayerAction.Action.BUST);
                    System.out.println("Player " + playerID + " Busted : " + hand.showHand());
                }

                //checkAllUsersDone(recievedState);


                // Print All Users Hand Status
                for (HashMap.Entry<Integer, Hand> entry : recievedState.getPlayerHands().entrySet()) {
                    Hand outhand = entry.getValue();
                    System.out.println("\nPlayer" + entry.getKey() + "'s hand status is " + outhand.status);
                }

                // If all player turns are over
                if(recievedState.areAllDone()){
                    System.out.println("All Players Turns are Over");

                    // Handle End Game Logic
                    Hand dealerHand = recievedState.getDealerHand();

                    // Dealer must Draw until goes above 17
                    while(dealerHand.calculateValueOfHand()  < 17){
                        Card newCard = recievedState.getDeck().takeTopCard();
                        dealerHand.addCard(newCard);
                        System.out.println("Dealers New Hand: " + dealerHand.showHand());
                    }

                    recievedState.setDealerHand(dealerHand);
                    recievedState.setActionTYPE(PlayerAction.Action.END_HAND);
                }
            }

        // Send updated game state back to ALL clients
        server.shareGameState(recievedState);

    }

    /**
     * Sends Game State to Client through output-stream
     * @param gameState GameState
     */
    public synchronized void sendGameState(GAME_STATE gameState){
        try {
            outputStream.writeObject(gameState);
            outputStream.flush();
            sendData("Sent GameState to Player " + myConID);
        } catch (IOException e) {
            System.err.println("Failed to send GAME_STATE to Client " + myConID);
        }
    }

    /**
     * Sends Client ID to be stored
     * @param clientId Client ID
     */
    public synchronized void sendClientID(int clientId){
        try {
            outputStream.writeObject(clientId);
            outputStream.flush();
        } catch (IOException e) {
            System.err.println("Failed to send ID to Client " + myConID);
        }

    }

    /**
     * Closes Connection
     */
    public void closeConnection(){
        try {
            inputStream.close();
            outputStream.close();
            connectionSocket.close();
            alive = false;
        } catch (IOException e) {
            System.out.println("ERROR CLOSING SOCKSERVER");
        }
    }

    /**
     * Sends Message when connected
     */
    public void connectionMessage(){
        sendData("Welcome to Blackjack! Your game ID: " + myConID);
        System.out.println("Welcome to Blackjack! Your game ID: " + myConID);
    }

    //GETTER SETTERS

    public int getMyConID() {
        return myConID;
    }
    public boolean isAlive() {
        return alive;
    }
}
