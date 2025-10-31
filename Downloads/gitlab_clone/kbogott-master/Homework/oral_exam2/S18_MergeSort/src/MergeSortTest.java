
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * JUNIT Testing Class that tests the functionality of the Merge Sort Class
 */
public class MergeSortTest {
    private MergeSort mergeSort = new MergeSort(); // create mergesort object to be called upon


    /**
     * Test Sorting a Random Array
     */
    @Test
    public void testSortRandomArray() {
        int[] originalArray = mergeSort.array.clone(); // Clone original array
        System.out.println("Original Array before sort:" + printArray(originalArray));

        mergeSort.mergeSort(originalArray, 0, originalArray.length - 1);
        System.out.println("Original Array after Sort:" + printArray(originalArray));
        System.out.println();

        // Call method to check if sorted
        assertTrue(isSorted(originalArray));
    }

    /**
     * Test Sorting an Empty Array
     */
    @Test
    public void testSortEmptyArray() {
        int[] emptyArray = {};
        System.out.println("Original Empty Array: " + printArray(emptyArray));

        mergeSort.mergeSort(emptyArray, 0, emptyArray.length - 1);
        System.out.println("New Empty Array: " + printArray(emptyArray));
        System.out.println();

        assertArrayEquals(new int[]{}, emptyArray);
    }


    /**
     * Test running sort on an already sorted Array
     */
    @Test
    public void testSortAlreadySortedArray() {
        int[] sortedArray = {1, 2, 3, 4, 5};
        System.out.println("Sorted Array Before:" + printArray(sortedArray));

        mergeSort.mergeSort(sortedArray, 0, sortedArray.length - 1);
        System.out.println("Sorted Array After:" + printArray(sortedArray));
        System.out.println();

        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, sortedArray);
    }

    /**
     * Test a Array with Duplicate Values
     */
    @Test
    public void testSortArrayWithDuplicates() {
        int[] arrayWithDuplicates = {3, 1, 2, 3, 2, 1,4 ,4,4,5, 5,5}; // Create array to check
        System.out.println("Sorted Array Before:" + printArray(arrayWithDuplicates) );

        mergeSort.mergeSort(arrayWithDuplicates, 0, arrayWithDuplicates.length - 1);
        System.out.println("Sorted Array Before:" + printArray(arrayWithDuplicates));
        System.out.println();

        assertArrayEquals(new int[]{1, 1, 2,2,3 ,3,4,4,4,5,5,5}, arrayWithDuplicates);
    }

    /**
     * Help Method
     * @param array Array being checked
     *
     * @return True or false if array is sorted or not
     */
    private boolean isSorted(int[] array) {
        for (int i = 1; i < array.length; i++) {
            // if number behind is not smaller return false
            if (array[i - 1] > array[i]) {
                return false;
            }
        }
        // Return true if all numbers after i are bigger
        return true;
    }

    /**
     * Print Method to see if check is correct
     * @param array Array to be printed
     * @return String of array
     */
    private String printArray(int[] array) {
        String result = "";
        // add each num to result and return
        for (int num : array) {
            result += num + " ";
        }
        return result;
    }
}