

import java.io.Serializable;

/**
 * Main Class For Cards in BlackJack
 */
public class Card implements Serializable {
    private final Suit suit;
    private final CardInfo cardInfo;

    /**
     * Contructor
     * @param suit card suit
     * @param cardinfo card information (name,value)
     */
    public Card(Suit suit, CardInfo cardinfo) {
        this.suit = suit;
        this.cardInfo = cardinfo;
    }
    public CardInfo getCardInfo() {
        return cardInfo;
    }
    /**
     * Returns Card as string [ A {suit}]
     * @return
     */
    @Override
    public String toString() {
        if(cardInfo.cardNum == 11){
            return "["+  "A" + " " + suit+"]";
        }
        else if(cardInfo.cardName.equals("Jack")){
            return "["+  "J" + " " + suit+"]";
        }
        else if(cardInfo.cardName.equals("Queen")){
            return "["+  "Q" + " " + suit+"]";
        }
        else if(cardInfo.cardName.equals("King")){
            return "["+  "K" + " " + suit+"]";
        }
        else{
            return "["+  cardInfo.cardNum + " " + suit+"]";

        }

    }

}