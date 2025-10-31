import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Shipping Section Receives the orders from the Categories and will add the shipping the shipping-section number
 * Pause from 0-5 Seconds (randomly) then put the order on the Shipping Dock's Buffer
 **/
public class ShippingSection implements Runnable{

    /**
     * Shipping Sections Input Buffer [ Shipping-Center Output ]
     */
    private ArrayBlockingQueue<Order> Center_To_SectionInput ;
    /**
     * Shipping Sections Output Buffer [ Shipping Dock Input ]
     */
    private ArrayBlockingQueue<Order> Section_To_Dock ;

    private int shippingSectionNumber;

    /**
     * Constructor for Shipping Section
     * @param inputBuffer Shipping Center and Section Shared Input
     * @param outputBuffer Shipping Section to Dock
     */
    public ShippingSection(ArrayBlockingQueue<Order> inputBuffer, ArrayBlockingQueue<Order> outputBuffer,int shippingSectionNumber) {
        this.Center_To_SectionInput = inputBuffer;
        this.Section_To_Dock = outputBuffer;
        this.shippingSectionNumber = shippingSectionNumber;
    }


    /**
     * When the Thread is run :
     * - receive order from shipping-center thread
     * - append number to order
     * - Put on Shared Buffer with Shipping Docks
     */
    @Override
    public void run() {
        Random rand = new Random();

        while (true) {
            try {

                // Receive Order
                Order order = Center_To_SectionInput.take();
                //System.out.println("Section " + shippingSectionNumber + " got Order:" + order.toString());

                // Check For End of File
                if(order.getItem().equals( "END_OF_FILE")){
                    Section_To_Dock.put(order); // send null order to dock to show no more orders
                    break;
                }

                // Append Shipping Section Number
                order.setShippingSection(this.shippingSectionNumber);

                // Pause for Random Time 0-5
                int sleeptime = rand.nextInt(6000) ;      // get a random number from 0s -> 5s (1000ms = 1s)
                Thread.sleep(sleeptime);
                //System.out.println("Section " + shippingSectionNumber + " sleeping for " + sleeptime + " ms");


                //Put order on the Shipping Dock's buffer
                Section_To_Dock.put(order);
                //System.out.println("Section " + shippingSectionNumber + " Sent Order to Dock:" + order.toString());

            } catch (InterruptedException e) {throw new RuntimeException(e);}
        }
        //System.out.println("Shipping Section " + shippingSectionNumber + " is shutting down...");

    }
}