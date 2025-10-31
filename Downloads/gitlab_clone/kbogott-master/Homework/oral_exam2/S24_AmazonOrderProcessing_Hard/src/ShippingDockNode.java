import java.util.concurrent.ArrayBlockingQueue;
import java.util.Random;
/**
 * Shipping Docks fill up the Trucks as there is room available
 * Start with Truck 1 ... if Truck 1 Full then go to Truck 2 ... if both Full Wait
 */
public class ShippingDockNode implements Runnable{
    private final ArrayBlockingQueue<Order> Section_To_DockInput; // Input Buffer for Shipping-Docks
    private final ArrayBlockingQueue<Order> Dock_To_Truck1; // Input Buffer to Truck One
    private final ArrayBlockingQueue<Order> Dock_To_Truck2; //  Input Buffer to Truck Two



    public ShippingDockNode(ArrayBlockingQueue<Order> inputBuffer, ArrayBlockingQueue<Order> Dock_To_Truck1, ArrayBlockingQueue<Order> Dock_To_Truck2) {
        this.Section_To_DockInput = inputBuffer;
        this.Dock_To_Truck1 = Dock_To_Truck1;
        this.Dock_To_Truck2 = Dock_To_Truck2;
    }


    /**
     * When an object implementing interface {@code Runnable} is used
     * to create a thread, starting the thread causes the object's
     * {@code run} method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method {@code run} is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        Random rand = new Random();

        while(true){
            try {
                // Take order from Buffer
                Order order = Section_To_DockInput.take();
                //System.out.println("Dock Got Order " + order);

                // Check if Order is End of File
                if(order.getItem().equals("END_OF_FILE")){
                    Dock_To_Truck1.put(order);
                    Dock_To_Truck2.put(order);
                    //System.out.println("Dock Received END OF FILE: ");
                    continue;
                }

                boolean addToTruck = false;
                // Start Filling Up Trucks As Room Is Available

                while(!addToTruck){
                    // Try To add to Truck 1 if space is available
                    if (Dock_To_Truck1.remainingCapacity() > 0) {
                        Dock_To_Truck1.put(order);
                        //System.out.println("Order added to Truck 1: " + order);
                        addToTruck = true;
                    }
                    // If Truck 1 is full, try Truck 2
                    else if (Dock_To_Truck2.remainingCapacity() > 0) {
                        Dock_To_Truck2.put(order);
                        //System.out.println("Order added to Truck 2: " + order);
                        addToTruck = true;
                    }
                    // If both trucks are full, wait for space to become available
                    else {
                        System.out.println("Both trucks are full, waiting...");
                        int sleepTime = rand.nextInt(10000); // Random sleep between 0-10 seconds
                        Thread.sleep(sleepTime);
                    }
                }

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
