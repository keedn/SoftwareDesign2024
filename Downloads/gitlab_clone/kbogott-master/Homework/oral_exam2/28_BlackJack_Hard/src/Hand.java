import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Hand implements Serializable{
    private ArrayList<Card> hand;
    public PlayerAction.Action status;

    public Hand(){
        this.hand = new ArrayList<>();
        this.status = PlayerAction.Action.PLAYING;
    }

    /**
     * Add Card to Hand
     * @param card Card to Be Added
     */
    public void addCard(Card card){
        this.hand.add(card);
    }

    /**
     * Calculates Value of hand
     * @return Number Value of Hand
     */
    public synchronized int calculateValueOfHand(){
        int sum = 0; // Value of Hand
        int numAces = 0; // Track Aces

        // Loop Through each card in hand
        // Check if Ace (11) if so handle cases where it should be 1 or 11
        // IF needed adjust sum for aces ( if over 21 minus 10 from sum [ changing from 11 -> 1] )
        // While sum is greater than 21 and an Ace can be reduced

        for (Card card : hand) {

            int cardValue = card.getCardInfo().cardNum;

            if(cardValue == 11){ numAces++; } // increment num of aces

            sum += cardValue; // Add cards Value
        }

        while(sum > 21 && numAces > 0){
            sum = sum - 10; // use Aces 1 value instead of 11
            numAces--; // Minus Num aces left
        }

        return sum; // Return Sum
    }

    public synchronized boolean isStanding(){
        return this.status == PlayerAction.Action.STAND;
    }
    public synchronized boolean isBust(){
        return this.status == PlayerAction.Action.BUST;
    }
    public synchronized void  setStatus(PlayerAction.Action status){
        this.status = status;
    }

    /**
     * Check if Hand can be Split
     * @return T or F based on if hand is splittable
     */
    public boolean canSplit(){
        // Check if size of hand is 2 and both cards are the same
        return hand.size() == 2 && hand.get(0).getCardInfo().cardNum == hand.get(1).getCardInfo().cardNum;
    }



    /**
     * Return String of Current Hand
     * @return String of Current Hand
     */
    public String showHand(){
        if (hand.isEmpty()) {
            return "No cards in hand"; // Handle empty hand case
        }

        StringBuilder handString = new StringBuilder();
        for (Card card : hand) {
            handString.append(card.toString()).append(", ");
        }

        // Remove the trailing comma and space if needed
        if (handString.length() > 0) {
            handString.setLength(handString.length() - 2); // Remove last ", "
        }

        return handString.toString();
    }

    /**
     * Get the Cards in the Hand
     * @return List of Cards in the Hand
     */
    public List<Card> getCards() {
        return new ArrayList<>(hand);
    }
    public int size(){
        return hand.size();
    }
}
