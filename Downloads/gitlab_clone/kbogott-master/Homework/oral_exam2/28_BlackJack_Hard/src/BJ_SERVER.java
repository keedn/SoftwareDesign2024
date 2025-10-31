import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BJ_SERVER extends JFrame {
    private final ExecutorService executor; // Thread pool to handle clients
    private ServerSocket server; // ServerSocket to accept connections
    private List<SockServer> clientsList; // Array of SockServer instances
    JButton dealButton,endButton;
    private int counter = 1; // Connection counter]
    private GAME_STATE gameState;

    /**
     * Creates Servers GUI
     */
    public BJ_SERVER(){
        super("Dealer's Server"); // create JFrame
        setLayout(new GridLayout(2,1));

        // Create Buttons
        dealButton = new JButton("Deal to Players");
        endButton = new  JButton("End Game");
        // Add Buttons
        add(dealButton);
        add(endButton);

        //Logic For DealButton
        dealButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                startGame();
            }
        });
        // Logic for End Game Button
        endButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(SockServer clientConnection : clientsList){
                    clientConnection.closeConnection();
                }

            }
        });

        setSize(400,200);
        setVisible(true);

        clientsList = new ArrayList<>();
        executor = Executors.newFixedThreadPool(5); // Create Thread Pool

    }
    /**
     * Run Server accepts connections from clients and adds clients to serverList and executes the client on a different thread
     */
    public void runServer(){

        try {
            server = new ServerSocket(23825,100);
            System.out.print("Server Started.. Running\n");

            while(true){
                // Accept Connection
                System.out.print("Waiting for Connection...\n");
                Socket connection =server.accept();

                // Add Connection to a SockServer with client count , connection, and link to server
                SockServer sockServer = new SockServer(counter,connection,this );
                // Add new sockServer to Client List
                clientsList.add(sockServer);
                // Execute
                executor.execute(sockServer);
                System.out.println("Client " + counter + " connected. Total clients: " + counter);

                counter++; // increment client number
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Handles Start of Game Logic
     */
    private void startGame(){
        // Create Game
        gameState = new GAME_STATE();

        // Deal To All Clients
        for(SockServer client: clientsList){
            dealHand(client,gameState);
        }
        // Deal to Dealer
        dealDealer(gameState);

        // Share game with all clients
        shareGameState(gameState);

    }
    /**
     * Deal First Hand to Players
     * @param client Player getting Dealt
     */
    private void dealHand(SockServer client, GAME_STATE gameState){
        int clientID = client.getMyConID(); // Get ID

        PlayerAction action = new PlayerAction(PlayerAction.Action.START_GAME, clientID);
        gameState.setAction(action);

        // Create Hand
        Hand hand = new Hand();
        hand.addCard(gameState.getDeck().takeTopCard());
        hand.addCard(gameState.getDeck().takeTopCard());

        gameState.addHand(clientID,hand);

        System.out.println("Hand Dealt to Player " + clientID +" | Hand: " + hand.showHand());
    }
    /**
     * Deals Hand to Dealer
     * @param gamestate Game State
     */
    private synchronized void  dealDealer(GAME_STATE gamestate){
        Hand hand = new Hand();
        hand.addCard(gamestate.getDeck().takeTopCard());
        hand.addCard(gamestate.getDeck().takeTopCard());

        gamestate.setDealerHand(hand); // Update Dealer Hand
        System.out.println("Hand Dealt to Dealer.. | Hand: " + hand.showHand());

        // Check if Dealer Card has BlackJack -> Reset Hand
        if(checkDealerBJ(hand)){
            // Create new Action to Be Sent to Clients
            PlayerAction dealerBJ = new PlayerAction(PlayerAction.Action.DEALER_BJ,-1);
            gamestate.setAction(dealerBJ);

            shareGameState(gameState);
            startGame(); // Start New Game ?
        }
    }
    /**
     * Share Game State with all Clients
     * @param gameState State getting shared
     */
    public synchronized void shareGameState(GAME_STATE gameState){
        // Share with each client
        for(SockServer clientConnection : clientsList){
            if(clientConnection.isAlive()){
                clientConnection.sendGameState(gameState); // send updated game state
            }
        }
    }
    /**
     * Checks Dealers Hand For BlackJack
     * @param hand Dealers Hand
     * @return True or False based on if dealer hand value = 21
     */
    public boolean checkDealerBJ(Hand hand){
        Card card1 = hand.getCards().get(0);
        Card card2 = hand.getCards().get(1);

        // Get N
        int card1Num=card1.getCardInfo().cardNum;
        int card2Num =card2.getCardInfo().cardNum;

        // If either Card equals 11 (ace)
        if(card1Num == 11 || card2Num == 11){
            // If other card is Face card
            if(card2Num == 10 || card1Num == 10){
                System.out.println("Dealer Black Jack");

                return true;
            }
        }
        // Same but starting with Ten
        if(card1Num == 10 || card2Num == 10){
            // If other card is Face card
            if(card2Num == 11 || card1Num == 11){
                System.out.println("Dealer Black Jack");

                return true;
            }
        }

        // If not check passes no dealer blackjack
        System.out.println("No Dealer Black Jack");
        return false;

    }

}
