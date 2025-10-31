import java.util.concurrent.ArrayBlockingQueue;

/**
 * The Shipping Center must decide which part of the warehouse the item is in and send a command to the appropriate forklift to go get it and deliver it to the shipping dock.
 * The Shipping Center organizes items based on the first letter of their category.  It will also append the Shipping Center number to the order for tracking purposes.
 * The processing is as follows:
 * Orders with categories beginning with A-P go to Section 1
 * Orders with categories beginning with Q-Z go to Section 2
 */
public class ShippingCenterNode implements Runnable{
    /**
     * Shared Buffer from Web Server to Shipping-Center
     */
    private ArrayBlockingQueue<Order> Server_To_CenterInput; // Input Buffer for Shipping-Center
    /**
     * Shipping-Center and Shipping Section 1 Shared Buffer
     */
    private ArrayBlockingQueue<Order> Center_To_Section1; // Output Buffer for Shipping-Center 1
    /**
     * Shopping-Center and Shipping Section 2 Shared Buffer
     */
    private ArrayBlockingQueue<Order> Center_To_Section2; // Output Buffer for Shipping-Center 2
    /**
     * Number of Shipping-Center
     */
    private int shippingCenterID; // Int to track which center each order needs to go to
    /**
     * Order that will be sent to buffers when there exists no more orders
     */
    private static final Order EndOfOrder = new Order(); // Ensure this is a valid instance

    /**
     * Constructor for Shipping Center Node
     * @param inputBuffer Input From Web Server (order)
     * @param sectionONEOutputBuffer Output to Section One
     * @param sectionTWOOutputBuffer Output to Section Two
     * @param shippingCenterID Number of Shipping-Center
     */
    public ShippingCenterNode(ArrayBlockingQueue<Order> inputBuffer, ArrayBlockingQueue<Order> sectionONEOutputBuffer, ArrayBlockingQueue<Order> sectionTWOOutputBuffer, int shippingCenterID) {
        this.Server_To_CenterInput = inputBuffer;
        this.Center_To_Section1 = sectionONEOutputBuffer;
        this.Center_To_Section2 = sectionTWOOutputBuffer;
        this.shippingCenterID = shippingCenterID;
    }

    /**
     * When the Thread gets called in takes the orders from the Web Server and determines which Section they should be sent to
     *  A -> P gets send to Section 1
     *  Q -> Z gets send to Section 2
     */
    @Override
    public void run() {
        while(true){
            try {

                Order order = Server_To_CenterInput.take(); // Take Order
                //System.out.println("Center " + shippingCenterID + " got Order:" + order.toString());

                // If no more orders then close out thread / break loop
                if(order.getItem().equals( "END_OF_FILE")){
                    // Send null order ( End of File Message to all Nodes )
                    // End
                    Center_To_Section1.put(order);
                    Center_To_Section2.put(order);
                    break;
                }

                // Check first letter
                char firstLetter = order.getCategory().toUpperCase().charAt(0);

                // Go to Section One
                if(firstLetter >= 'A' && firstLetter <= 'P'){
                    Center_To_Section1.put(order); // put on section one input buffer
                }

                // Go to Section Two
                else if(firstLetter >= 'Q' && firstLetter <= 'Z'){
                    Center_To_Section2.put(order); //put on section two input buffer
                }
            } catch (InterruptedException e) {throw new RuntimeException(e);}
        }
        //System.out.println("Shipping Center Found End of File " + shippingCenterID + " is shutting down...");

    }
}
