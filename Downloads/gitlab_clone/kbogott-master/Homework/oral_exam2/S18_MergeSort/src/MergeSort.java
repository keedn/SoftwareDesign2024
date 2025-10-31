import java.util.Random;

public class MergeSort {
    public int[] array; // this array

    /**
     * Constructor sets the merge sorts array to a randomly generated array with each number in range of 0 - > 1000
     */
    public MergeSort() {
        this.array = generateRandomArray();
    }

    /**
     * Generate Array with 100 Random Integers
     * @return
     */
    public int[] generateRandomArray(){
        // Create new Array
        int[] array = new int[100];
        Random rand = new Random();
        // Generate random numbers from 0 -> 1000
        for(int i = 0; i < array.length; i++){
            array[i] = rand.nextInt(1001);
        }

        return array;
    }

    /**
     * Print Original array
     * @param array array to be printed
     */
    public void printArray(int[] array){

        System.out.println("_____________________Original array_________________________");
        for(int i = 0; i < array.length; i++){
            System.out.print(array[i] + " ");
        }
        System.out.println("\n____________________________________________________________");
    }

    /**
     * Print Sorted Array
     * @param array Sorted Array
     */
    public void printSortedArray(int[] array){

        System.out.println("_____________________Sorted array_________________________");
        for(int i = 0; i < array.length; i++){
            System.out.print(array[i] + " ");
        }
        System.out.println("\n____________________________________________________________");
    }


    /**
     * Merge Sort
     * @param array Array to be sorted
     * @param beginning First element of array
     * @param ending Second element of array
     */
    public void mergeSort(int[] array,int beginning, int ending){
        // If beginning is less than end
        if(beginning < ending){

            // Find middle of Array
            int middle = (beginning + ending) / 2;

            // Sort First Half  ( start from 0 to middle )
            mergeSort(array, beginning, middle);

            // Sort Second Half ( start from 1 from middle to end )
            mergeSort(array, middle + 1, ending);

            // Merge Sorted Halfs Together
            merge(array, beginning, middle, ending);

        }
    }
    public void merge(int[] array, int beginning,int middle, int ending){

        // Create temp array to hold left half
        int[] tempArray = new int[middle - beginning + 1];

        // Copy Array
        for(int i = 0; i < tempArray.length; i++){
            tempArray[i] = array[beginning + i];
        }

        // Declare Variables
        int i = 0; // index left subarray
        int j = middle + 1; // index for right subarray
        int k = beginning;

        // Merge the two halves
        while (i < tempArray.length && j <= ending) {
            // Check if element in right subarray is less than in left subarray
            array[k++] = (array[j] < tempArray[i]) ? array[j++] : tempArray[i++];

            // Invariant: array[left..k] is in final position
        }

        while (i < tempArray.length) {
            array[k++] = tempArray[i++];

            // Invariant: array[left..k] is in final position
        }
    }
    }
