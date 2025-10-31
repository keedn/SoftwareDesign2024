/**
 * Tests the Functionality of the MazeArray class and its MazeTraversal Method.
 */
public class MazeDriver {
    public static void main(String[] args){

      // Test Original Maze
        String[] mazeStringArray = {

                "############",
                "#...#......#",
                "S.#.#.####.#",
                "###.#....#.#",
                "#....#.#.#.E",
                "##.#.#.#.#.#",
                "#........#.#",
                "######.###.#",
                "#......#...#",
                "############"
        };
        MazeArray mazeArray1 = new MazeArray(mazeStringArray);
        mazeArray1.traverse(2,1);

        // Test maze with no exit
        String[] mazeNoExit = {
                "############",
                "S..........#",
                "#.#.######.#",
                "#.#........#",
                "#.#.######.#",
                "#.#........#",
                "#.#.######.#",
                "#..........#",
                "############"
        };

        MazeArray mazeArray2 = new MazeArray(mazeNoExit);
        mazeArray2.traverse(0,1);


    }
}
