import java.text.DecimalFormat; // used to make decimal format simpler in system
    /**
     * This Driver class tests the functionality of the RationalNumber class.
     * It includes tests for the default constructor, constructor with parameters,
     * getValue method, getGCD method, simplify method, and exception handling.
     * @author kbogott
     * @version 1.0
     * @see RationalNumber
     * @see RationalNumberTest
     */
public class RationalNumberDriver {
    /**
     * Main constructor for RationalNumberDriver class
     */
    public RationalNumberDriver() {}
    /**
     * Main Program for RationalNumberDriver class
     * Tests default constructor, constructor with parameters, simplify method,
     * @param args Arguments
     */
    public static void main(String[] args) {
        // Test default constructor
        RationalNumber defaultRational = new RationalNumber();
        System.out.println("Default rational number: " + defaultRational);

        // Test constructor with numerator and denominator
        RationalNumber rational1 = new RationalNumber(40, 12);
        System.out.println("Rational number 40/12: " + rational1);

        // Test getValue method
        DecimalFormat df = new DecimalFormat("#.##"); // value with 2 decimal points only
        System.out.println("Value of 40/12: " + df.format(rational1.getValue()));

        // Test getGCD method
        System.out.println("GCD of 12 and 18: " + RationalNumber.getGCD(12, 18));

        //Test getGCD method with gcd of 1
        System.out.println("GCD of 2 and 3: " + RationalNumber.getGCD(2, 3));

        // Test simplify method
        RationalNumber rational2 = new RationalNumber(12, 18);
        System.out.println("Create Rational Number: 12/18");
        rational2.simplify();
        System.out.println("Simplifying 12/18..... \n12/18 after simplify: " + rational2);

        // Test exception handling with denominator as zero / should throw error if denominator is 0
        try {
            RationalNumber rational3 = new RationalNumber(10, 0);
        } catch (IllegalArgumentException e) {
            System.out.println("Testing Constructor with denominator of 0");
            System.out.println("Caught exception: " + e.getMessage());
        }
    }
}