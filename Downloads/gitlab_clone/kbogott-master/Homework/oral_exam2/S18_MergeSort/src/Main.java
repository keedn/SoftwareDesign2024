public class Main {
    public static void main(String[] args) {

        MergeSort sort = new MergeSort();

        int[] array = sort.array;   // Get Array
        int beg = 0;                // Get Beginning of
        int end = array.length - 1; // Get End of Array

        // Print out original Array
        sort.printArray(array);

        // Call Merge Sort
        sort.mergeSort(array, beg, end);
        // Print Sorted Array
        sort.printSortedArray(array);
    }
}