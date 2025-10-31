import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

/**
 * Class for Deck
 */
public class Deck implements Serializable {
    private Stack<Card> cards; // Hold Stack of Cards\

    /**
     * Creates new Deck and Shuffles
     */
    public Deck(){
        cards = new Stack<>(); // Create new Stack for Cards
        createDeck();          // Create Deck
        shuffleDeck();         // Shuffle Deck
    }

    // Create Deck and Add one of each card and suit

    /**
     * Creates Deck
     */
    private void createDeck(){

        // Loop through each suit and num and add to deck
        for(Suit suit : Suit.values()){
            for(CardInfo rank : CardInfo.values()){

                Card card = new Card(suit,rank);
                cards.push(card);
                //System.out.println(card + " has been added ");
            }
        }
    }

    public int size() {
        return cards.size();
    }
    // Shuffle Deck
    private void shuffleDeck(){
        Collections.shuffle(cards);
    }
    // Return Top Card
    public Card takeTopCard() {
        return cards.pop();
    }

    /**
     * Prints out all cards in deck
     * @return  String of all cards in deck
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Card card : cards) {
            sb.append(card).append("\n");
        }
        return sb.toString();
    }


}

