import javax.print.Doc;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;

/**
 * Delivery Truck Node Class takes in Orders from the Shared Buffer from Shipping Dock
 * Prints Order Delivery To Screen
 */
public class DeliveryTruckNode implements Runnable {
    /**
     * Delivery Truck and Shipping Dock Shared Buffer
     */
    private ArrayBlockingQueue<Order> Dock_To_Truck; // Input Buffer for Delivery Truck
    /**
     * number of truck
     */
    private int truckNumber;
    private int shippingCenterNumber; // used to test shipping center
    private CountDownLatch latch;
    public DeliveryTruckNode(ArrayBlockingQueue<Order> dock_To_Truck, int truckNumber,int shippingCenterNumber, CountDownLatch latch) {
        Dock_To_Truck = dock_To_Truck;
        this.truckNumber = truckNumber;
        this.shippingCenterNumber = shippingCenterNumber;
        this.latch = latch; // tie this latch to
    }
    @Override
    public void run() {

        Random rand = new Random();
        int deliveriesDone = 0;
        boolean END_OF_FILE= false;
        HashSet<Order> deliveries = new HashSet<>();

        while (true) { // While Thread is running / Deliveries Still need to be processed
            try {
                Order order = Dock_To_Truck.take();
                //System.out.println("Truck " + truckNumber + " received order: " + order);

                if(order.getItem().equals("END_OF_FILE")){
                    latch.countDown();
                    //System.out.println("Truck " + truckNumber +  " read in END OF FILE");
                    continue;
                }

                order.setTruckNumber(this.truckNumber); // Append Truck Number
                deliveries.add(order);                  // Add Order to Set

                // wait until trucks have received 4 deliveries or receive notification that there are no more deliveries.
                while(deliveries.size() == 4 || END_OF_FILE){ // While there are less than 4 deliveries in current set

                    for(Order delivery : deliveries){
                        // Sleep random num from 1-10
                        Thread.sleep(1000 + rand.nextInt(9000));
                        // Print Order
                        printOrder(delivery);
                    }

                }
                printOrder(order);
                deliveries.clear(); // Clear Set for next


                if(END_OF_FILE){
                    System.out.println("Truck " + truckNumber + " has no more orders");
                    break;
                }

            } catch (InterruptedException e) {
            }
        } //
        System.out.println("Truck " + truckNumber +" Center " + this.shippingCenterNumber + " Finished Orders.");

    }

    /**
     * Method to Print Each Processed Order to Screen
     * Uses print f to print the orders in an easy readable format
     * @param order Current Order being processed
     */
    public  void printOrder(Order order) {
        String state = order.getCity() + ", " + order.getState() + " " + order.getZip();
        System.out.println("\n_____________________________________");
        System.out.println("         ORDER HAS BEEN PROCESSED      ");
        System.out.println("- - - - - - - - - - - - - - - - - - -  ");
        System.out.printf("%-17s: %s%n", "Address", order.getStreetAddress());
        System.out.printf("%-17s: %s%n", "State", state);
        System.out.printf("%-17s: %s%n", "Name", order.getName());
        System.out.printf("%-17s: %s%n", "Item", order.getItem());
        System.out.printf("%-17s: %s%n", "Category", order.getCategory());
        System.out.printf("%-17s: %d%n", "Shipping Center", order.getShippingCenter());
        System.out.printf("%-17s: %d%n", "Truck Number", order.getTruckNumber());
        System.out.printf("%-17s: %d%n", "Shipping Section", order.getShippingSection());
        System.out.println("_____________________________________");
    }

}
