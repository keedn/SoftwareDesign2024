public class BJ_SERVER_TEST {
    public static void main(String[] args){
        BJ_SERVER server = new BJ_SERVER();
        server.runServer();

        // Create Game State

        GAME_STATE test = new GAME_STATE();
        Hand hand= new Hand();
        hand.addCard(test.getDeck().takeTopCard());
        hand.addCard(test.getDeck().takeTopCard());

        test.setDealerHand(hand);



    }

}
