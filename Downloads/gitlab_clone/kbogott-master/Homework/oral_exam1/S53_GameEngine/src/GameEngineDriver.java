/**
 * This class serves as the driver for the Game Engine.
 * It creates instances of entities, demonstrates their features, and prints their data.
 *
 * @author kbogott
 * @version 1.0
 */
public class GameEngineDriver {

        /**
         * The main method that runs the program.
         *
         * @param args Command line arguments (not used in this program)
         */
        public static void main(String[] args) {
                // Create a Transform object
            Transform transform1 = new Transform(10, 20, 30.0f, 40);
            Transform transform2 = new Transform(50, 60, 70.0f, 80);

                // Create an Actor object
            Actor actor = new Actor("Phil Dunphy", transform1, 100);
            System.out.println(actor.toString());

                // Create an Obstacle object
            Obstacle obstacle = new Obstacle("Tower", transform2, 5);
                System.out.println(obstacle.toString());

                // Create an Entity object
            Entity entity = new Entity("Entity", transform1);
            System.out.println(entity.toString());

                // Demonstrate setting and getting health for the Actor
            actor.setHealth(200);
                System.out.println("Changing Actor's health to 200");

            System.out.println("Actor's new health: " + actor.getHealth());

                // Demonstrate setting and getting size for the Obstacle
            obstacle.setSize(10);
                System.out.println("Changing Obstacle Size to 10");

            System.out.println("Obstacle's new size: " + obstacle.getSize());
        }
}