

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the RationalNumber class.
 * This class contains test methods to verify the functionality of the RationalNumber class.
 *
 * @author kbogott
 * @version 1.0
 * @see RationalNumber
 * @see RationalNumberDriver
 */
public class RationalNumberTest {
    /**
     * Main constructor for RationalNumberTest class
     */
    public RationalNumberTest() {}

    /**
     * Tests the constructor with parameters.
     * Verifies that the constructor correctly initializes the numerator and denominator.
     */
    @Test
    public void testConstructorWithParameters() {
        RationalNumber rationalNumber = new RationalNumber(2, -5);
        assertEquals(-0.4, rationalNumber.getValue(), 0.01);
        assertEquals("-/5", rationalNumber.toString());
    }

    /**
     * Tests the constructor with a zero denominator.
     * Verifies that the constructor throws an IllegalArgumentException when the denominator is zero.
     */
    @Test
    public void testConstructorWithZeroDenominator() {
        assertThrows(IllegalArgumentException.class, () -> new RationalNumber(2, 0));
    }

    /**
     * Tests the simplify method.
     * Verifies that the simplify method correctly simplifies the rational number.
     */
    @Test
    public void testSimplify() {
        RationalNumber rationalNumber = new RationalNumber(40, 12);
        rationalNumber.simplify();
        assertEquals("10/3", rationalNumber.toString());
    }

    /**
     * Tests the getGCD method.
     * Verifies that the getGCD method correctly calculates the greatest common divisor.
     */
    @Test
    public void testGetGCD() {
        assertEquals(4, RationalNumber.getGCD(40, 12));
    }

    /**
     * Tests the getValue method.
     * Verifies that the getValue method correctly returns the value of the rational number.
     */
    @Test
    public void testGetValue() {
        RationalNumber rationalNumber = new RationalNumber(2, -5);
        assertEquals(-0.4, rationalNumber.getValue(), 0.01);
    }

    /**
     * Tests the toString method.
     * Verifies that the toString method correctly returns the string representation of the rational number.
     */
    @Test
    public void testToString() {
        RationalNumber rationalNumber = new RationalNumber(2, -5);
        assertEquals("-2/5", rationalNumber.toString());
    }

    /**
     * Tests the no-argument constructor.
     * Verifies that the no-argument constructor correctly initializes the numerator and denominator.
     */
    @Test
    public void testNoArgumentConstructor() {
        RationalNumber rationalNumber = new RationalNumber();
        assertEquals("0/1", rationalNumber.toString());
    }

    /**
     * Tests the simplify method with negative numbers.
     * Verifies that the simplify method correctly simplifies the rational number with negative numbers.
     */
    @Test
    public void testSimplifyWithNegativeNumbers() {
        RationalNumber rationalNumber = new RationalNumber(-40, -12);
        rationalNumber.simplify();
        assertEquals("10/3", rationalNumber.toString());
    }

    /**
     * Tests the simplify method with a GCD of 1.
     * Verifies that the simplify method correctly simplifies the rational number with a GCD of 1.
     */
    @Test
    public void testSimplifyWithGCDOf1() {
        RationalNumber rationalNumber = new RationalNumber(2, 3);
        rationalNumber.simplify();
        assertEquals("2/3", rationalNumber.toString());
    }
}