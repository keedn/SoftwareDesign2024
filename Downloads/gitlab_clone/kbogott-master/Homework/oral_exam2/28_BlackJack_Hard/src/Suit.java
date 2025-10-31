import java.io.Serializable;

/**
 * Enum to Hold Constants for Suit
 */
public enum Suit implements Serializable {

    CLUB("Clubs", "♣"),
    DIAMOND("Diamonds", "♦"),
    HEART("Hearts", "♥"),
    SPADE("Spades", "♠");

    private final String  suitName;
    private final String symbol;


    Suit(String suitName , String symbol) {
        this.suitName = suitName;
        this.symbol = symbol;
    }

    public String toString(){
        return symbol;
    }
}