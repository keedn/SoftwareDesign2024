import java.lang.reflect.Executable;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Driver Class for Amazon Order Processing
 * Creates All Threads and Buffers
 * Uses Executor Service to manage all different threads
 */
public class AmazonOrderProcessingDriver {

    public static void main(String[] args) {

        //**************************** BUFFERS **************************************************************

        // Create WEB SERVER OUTPUT BUFFERS [ SHIPPING CENTER INPUT ] Web Server --> Shipping Center 1 , 2
        ArrayBlockingQueue<Order> Server_To_Center1 = new ArrayBlockingQueue<>(10);
        ArrayBlockingQueue<Order> Server_To_Center2 = new ArrayBlockingQueue<>(10);

        // Create SHIPPING CENTER OUTPUT BUFFERS [ SHIPPING SECTION INPUT ] Shipping Center --> Shipping Section
        ArrayBlockingQueue<Order> Center1_To_Section1 = new ArrayBlockingQueue<>(10);
        ArrayBlockingQueue<Order> Center1_To_Section2 = new ArrayBlockingQueue<>(10);
        ArrayBlockingQueue<Order> Center2_To_Section1 = new ArrayBlockingQueue<>(10);
        ArrayBlockingQueue<Order> Center2_To_Section2 = new ArrayBlockingQueue<>(10);

        // Create SHIPPING CENTER SECTIONS OUTPUT BUFFERS [ SHIPPING DOCK INPUT ] Shipping Section --> Shipping Dock
        ArrayBlockingQueue<Order>  ShippingSection1_To_Dock1  = new ArrayBlockingQueue<>(20);
        ArrayBlockingQueue<Order>  ShippingSection2_To_Dock2  = new ArrayBlockingQueue<>(20);

        // Create SHIPPING DOCK BUFFER [ DELIVERY TRUCK INPUT ] Shipping Dock --> Delivery Truck 4 max deliveries
        ArrayBlockingQueue<Order> Dock1_To_Truck1 = new ArrayBlockingQueue<>(4);
        ArrayBlockingQueue<Order> Dock1_To_Truck2 = new ArrayBlockingQueue<>(4);
        ArrayBlockingQueue<Order> Dock2_To_Truck1 = new ArrayBlockingQueue<>(4);
        ArrayBlockingQueue<Order> Dock2_To_Truck2 = new ArrayBlockingQueue<>(4);

        //**************************** ^BUFFERS^ ************************************************************

        //**************************** Threads **************************************************************

        // Create web server to send orders to correct Shipping-Centers
        AmazonWebServerNode WebServer = new AmazonWebServerNode(Server_To_Center1,Server_To_Center2);

        // Create Shipping-Centers Thread
        ShippingCenterNode shippingCenter1 = new ShippingCenterNode( Server_To_Center1, Center1_To_Section1,Center1_To_Section2,1);
        ShippingCenterNode shippingCenter2 = new ShippingCenterNode( Server_To_Center2,Center2_To_Section1,Center2_To_Section2,2);

        // Create Shipping Sections Thread
        ShippingSection center1_section1 = new ShippingSection( Center1_To_Section1, ShippingSection1_To_Dock1,1 );
        ShippingSection center1_section2 = new ShippingSection( Center1_To_Section2, ShippingSection1_To_Dock1,2 );

        ShippingSection center2_section1 = new ShippingSection( Center2_To_Section1, ShippingSection2_To_Dock2,1 );
        ShippingSection center2_section2 = new ShippingSection( Center2_To_Section2, ShippingSection2_To_Dock2 ,2);

        // Create Shipping Dock Threads
        ShippingDockNode shippingDock1 = new ShippingDockNode(ShippingSection1_To_Dock1, Dock1_To_Truck1, Dock1_To_Truck2);
        ShippingDockNode shippingDock2 = new ShippingDockNode(ShippingSection2_To_Dock2, Dock2_To_Truck1, Dock2_To_Truck2);

        //Create Latch for Trucks to allow them to shut down
        CountDownLatch latch = new CountDownLatch(4);  // 4 trucks

        // Create Delivery Truck Threads
        DeliveryTruckNode center1_truck1 = new DeliveryTruckNode(Dock1_To_Truck1,1,1,latch);
        DeliveryTruckNode center1_truck2 = new DeliveryTruckNode(Dock1_To_Truck2,2,1,latch);
        DeliveryTruckNode center2_truck1 = new DeliveryTruckNode(Dock2_To_Truck1,1,2,latch);
        DeliveryTruckNode center2_truck2 = new DeliveryTruckNode(Dock2_To_Truck2,2,2,latch);

        //**************************** ^Threads^ ************************************************************




        //********************************************************************************************
        // Create ExecutorService to manage threads
        ExecutorService executorService = Executors.newCachedThreadPool();

        executorService.execute(WebServer);
        executorService.execute(shippingCenter1);
        executorService.execute(shippingCenter2);
        executorService.execute(center1_section1);
        executorService.execute(center1_section2);
        executorService.execute(center2_section1);
        executorService.execute(center2_section2);
        executorService.execute(shippingDock1);
        executorService.execute(shippingDock2);
        executorService.execute(center1_truck1);
        executorService.execute(center1_truck2);
        executorService.execute(center2_truck1);
        executorService.execute(center2_truck2);

        //Wait for latch to shut down programs
        try {
            latch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        //shut down ExecutorService
        executorService.shutdown();
        System.out.println("Threads shutting down after final order is complete...");
        //********************************************************************************************
    }
}
