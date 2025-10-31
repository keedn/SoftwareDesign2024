/**
 * Class to Hold all Card info
 */
public enum CardInfo {
    ACE("Ace", 11),
    TWO("Two", 2),
    THREE("Three", 3),
    FOUR("Four",4),
    FIVE("Five",5),
    SIX("Six",6),
    SEVEN("Seven",7),
    EIGHT("Eight",8),
    NINE("Nine",9),
    TEN("Ten",10),
    JACK("Jack",10),
    QUEEN("Queen",10),
    KING("King",10);

    // Variables
    final String cardName;
    final int cardNum;

    /**
     * Constructor
     * @param cardName name
     * @param cardNum number
     */
    CardInfo(String cardName, int cardNum) {
        this.cardName = cardName;
        this.cardNum = cardNum;
    }
}
