import java.io.Serializable;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Game State that will be sent between Client and Server
 */
public class GAME_STATE implements Serializable {
    private PlayerAction action; // Hold Actions and which player wants said act
    private String currentPlayer; // Which Player is being passed
    private String gameStatus; // Status of Game
    private Hand dealerHand; // Dealer Hand
    private HashMap<Integer, Hand> playerHands;  // Map to Store [ Player (key)  <-> Hand (value)
    private Deck deck; // Current Deck in Game
    private int clientID; // current ID
    private AtomicInteger standBustCount = new AtomicInteger(0);

    /**
     * Game State Constructor
     */
    public GAME_STATE(){
        // Initialize Hands
        playerHands = new HashMap<>();
        dealerHand = new Hand();
        deck = new Deck();
        playerHands = new HashMap<>();

    }

    /**
     * Checks if all users have ended their turn
     * @return
     */
    public synchronized boolean areAllDone(){
        // Loop through each hand in hashmap
        for (HashMap.Entry<Integer, Hand> entry : playerHands.entrySet()) {
            Hand hand = entry.getValue();

            // Check if any player ha snot busted or stood
            if (hand.status != PlayerAction.Action.BUST && hand.status != PlayerAction.Action.STAND) {
                return false; // At least one player is still playing
            }
        }
        return true; // All players have stood or busted
    }

    /**
     * Handles Game Reset
     */
    public void reset() {
        // Clear hands, scores, and any other game-specific data
        this.clearPlayerHands(); // Example: Clear player hands
        this.dealerHand = new Hand(); // Reset dealer hand
        this.deck = new Deck(); // Reset deck if needed

    }

    // Getter Setters

    public synchronized PlayerAction getAction() {
        return action;
    }
    public synchronized void addHand(int clientId, Hand hand) {
        playerHands.put(clientId, hand);
    }
    public synchronized Hand getHand(int clientId) {
        return playerHands.get(clientId);
    }
    public synchronized void updateHand(int clientId, Hand hand) {
        playerHands.put(clientId, hand);
    }
    public synchronized void setAction(PlayerAction action) {
        this.action = action;
    }
    public  synchronized PlayerAction.Action getActionTYPE() {
        return action.getActionType();
    }
    public synchronized void setActionTYPE(PlayerAction.Action action){
        this.action.setAction(action);
    }
    public synchronized Deck getDeck() {
        return deck;
    }
    public synchronized Hand getDealerHand() {
        return dealerHand;
    }
    public synchronized void setDealerHand(Hand dealerHand) {
        this.dealerHand = dealerHand;
    }
    public synchronized HashMap<Integer,Hand> getPlayerHands() {
        return playerHands;
    }
    public synchronized void clearPlayerHands() {
        playerHands.clear(); // Clear all player hands for a new round
    }
}



