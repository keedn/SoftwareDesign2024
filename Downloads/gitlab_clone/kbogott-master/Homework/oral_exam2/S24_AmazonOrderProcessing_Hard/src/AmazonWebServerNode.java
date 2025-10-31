import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Amazon Web Server Node
 * Receives orders containing (Address, City, State, Zip)
 * Node reads in orders from 'S24_AmazonOrderProcessing_OrdersFile.csv'
 * For each order it receives it will decide which Shipping-Center to send to based on location / logic
 * When node reaches end of file, it WILL (MUST) send some indication to all Shipping-Center
 * Nodes through the shared buffer that the END has been reached and will then terminate
 */
public class AmazonWebServerNode implements Runnable {
    /**
     * File Path of Order List
     */
    private static final String FILE_PATH = "Homework/oral_exam2/S24_AmazonOrderProcessing_Hard/S24_AmazonOrderProcessing_OrdersFile.csv";
    /**
     * Server and Shipping Center One Shared Buffer
     */
    private ArrayBlockingQueue<Order> Server_To_Center1; // Buffer for WebServer Output to shipping-center 1's input
    /**
     * Server and Shipping Center Two Shared Buffer
     */
    private ArrayBlockingQueue<Order> Server_To_Center2; // Buffer for WebServer Output to shipping-center 2's input
    /**
     * Order that will be sent to buffers when there exists no more orders
     */
    private static final Order EndOfOrder = new Order(); // Ensure this is a valid instance


    /**
     * Constructor for Web Server Node
     *
     * @param Server_To_Center1 Output Buffer to Center One
     * @param Server_To_Center2 Output Buffer to Center Two
     */
    public AmazonWebServerNode(ArrayBlockingQueue<Order> Server_To_Center1, ArrayBlockingQueue<Order> Server_To_Center2) {
        this.Server_To_Center1 = Server_To_Center1;
        this.Server_To_Center2 = Server_To_Center2;
    }

    /**
     * AmazonWebServer's run(), called when thread is executed in Driver, reads in Orders from input File
     * For each order until the file ends the server will determine which shipping-center each order needs to be sent to
     * It will append the Shipping-Center number to the order
     * Then send (share) the order with the corresponding input buffer for the Shipping-Centers
     *
     * @see Order
     * @see ShippingCenterNode
     */
    @Override
    public void run() {
        // Read in File when created
        try (BufferedReader infoReader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line = "none";
            infoReader.readLine(); // Skip Header of Text
            int count = 0;

            // Read and print each line as long as there in information in line
            while (true) {

                line = infoReader.readLine();    // Read in line
                if(line == null && infoReader.readLine() == null){
                    break;
                }
                Order order = handleOrder(line); // Handle Current Order

                // set ShippingCenter of current Order to correct Center
                int shippingCenter = whichShippingCenterOne(order);
                order.setShippingCenter(shippingCenter);

                // Send to Correct Center ( add to centers buffer )
                // Send to Center 1
                if (shippingCenter == 1) {
                    Server_To_Center1.put(order);

                }
                // Send to Center 2
                else if (shippingCenter == 2) {
                    Server_To_Center2.put(order);
                }
                // Send end of Order
                else if (shippingCenter == -1) {

                    break;
                }
                // Print out Count
                //count++;
                //System.out.println(line+ " " + count);
            }
            // send END_OF_FILE to both service buffers
            Server_To_Center1.put(EndOfOrder);
            Server_To_Center2.put(EndOfOrder);
            //System.out.println("End of File Recognized, Web Server shutting down....\n");

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Process's Order by line in OrderList
     * [0] street address , [1] city ,[2] state , [3] zip, [4] name, [5] item , [6] category
     * 1234 Iowa Avenue,Denver,Iowa,52234,Jane Doe,Laptop,Computer
     * Use's Order Class to easily take in and return information
     */
    private Order handleOrder(String line) {



        // Split each category by "," and store in orderInfoArray
        String[] orderInfo = line.split(",");

        // STORE EACH INFORMATION FROM ORDER
        String streetAddress = orderInfo[0];
        String city = orderInfo[1];
        String state = orderInfo[2];
        String zip = orderInfo[3];
        String name = orderInfo[4];
        String item = orderInfo[5];
        String category = orderInfo[6];

        // Return new Order Object stored with Order info
        return new Order(streetAddress, city, state, zip, name, item, category);
    }

    /**
     * Returns Integer Value corresponding to which Center the City should point to and appends the number to the Order
     *
     * @return 1 if in Center One, 2 if Center Two, -1 if null
     */
    public int whichShippingCenterOne(Order order) {
        try {
            if (order == null) {
                return -1;
            }
            if (order.getCity().equals("Los Angeles") || order.getCity().equals("San Francisco") || order.getCity().equals("Seattle") || order.getCity().equals("Denver")) {
                order.setShippingCenter(1);
                return 1;
            } else {
                order.setShippingCenter(2);
                return 2;
            }
        } catch (NullPointerException e) {
            return -1;
        }
    }
}
