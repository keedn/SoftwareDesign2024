/**
 * Represents a rational number as the ratio of two integer values, a and b, where b is not zero.
 * The class stores rational numbers in their simplest form, with their common factor removed.
 * For example, the rational number 40/12 is stored as 10/3.
 *
 * @author kbogott
 * @version 1.0
 * @see RationalNumberDriver
 */
public class RationalNumber {

    /**
     * The numerator of the rational number.
     */
    private int numerator;

    /**
     * The denominator of the rational number.
     */
    private int denominator;

    /**
     * Constructs a new rational number with default values (0/1).
     */
    public RationalNumber() {
        this.numerator = 0;
        this.denominator = 1;
    }

    /**
     * Constructs a new rational number with the specified numerator and denominator.
     * If the denominator is zero, an IllegalArgumentException is thrown.
     *
     * @param numerator the numerator of the rational number
     * @param denominator the denominator of the rational number
     * @throws IllegalArgumentException if the denominator is zero
     */
    public RationalNumber(int numerator, int denominator) {
        if (denominator == 0) {
            throw new IllegalArgumentException("Denominator cannot be zero"); // throw error if denominator zero
        }
        this.numerator = numerator;
        this.denominator = denominator;
        simplify();
    }

    /**
     * Simplifies the rational number by dividing both the numerator and denominator by their greatest common divisor.
     */
    public void simplify() {
        int gcd = getGCD(numerator, denominator);
        numerator /= gcd;
        denominator /= gcd;
    }

    /**
     * Returns the greatest common divisor of two positive integers, x and y.
     * This method uses the Euclidean algorithm to find the GCD.
     *
     * @param x the first integer
     * @param y the second integer
     * @return the greatest common divisor of x and y
     */
    public static int getGCD(int x, int y) {
        if (y == 0) {
            return x;
        } else {
            int rem = x % y;
            return getGCD(y, rem);
        }
    }

    /**
     * Returns the value of the rational number as a double.
     *
     * @return the value of the rational number as a double
     */
    public double getValue() {
        return (double) numerator / (double) denominator;
    }

    /**
     * Returns the rational number as a string in the form "a/b".
     *
     * @return the rational number as a string
     */
    @Override
    public String toString() {
        return numerator + "/" + denominator;
    }
}