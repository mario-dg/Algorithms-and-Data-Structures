import org.junit.Assert;
import org.junit.Test;


import simplex.Fraction;
import simplex.LinearProgram;
import simplex.SimplexSolver;
import simplex.LinearProgram.Restriction;
import simplex.LinearProgram.SolveType;
import simplex.LinearProgram.Restriction.Type;

/**
 * Beispieltests für die Klassen Fraction und SimplexSolver
 *
 * @author kar, mhe
 */
public class ExampleTests {

    /**
     * Tests fuer diverser Methoden aus Fraction
     */
    @Test
    public void fraction_constructorTest1() {
        Fraction fraction = new Fraction(2, 4);
        Assert.assertEquals(fraction.getNumerator(), 1);
        Assert.assertEquals(fraction.getDenominator(), 2);
    }

    @Test
    public void fraction_constructorTest2() {
        Fraction fraction = new Fraction(0, 3);
        Assert.assertEquals(fraction.getNumerator(), 0);
        Assert.assertEquals(fraction.getDenominator(), 1);
    }

    @Test(expected = AssertionError.class)
    public void fraction_constructorTest3() {
        Fraction fraction = new Fraction(517, 0);
    }

    @Test
    public void fraction_constructorTest4() {
        Fraction fraction = new Fraction(-2, 3);
        Assert.assertEquals(fraction.getNumerator(), -2);
        Assert.assertEquals(fraction.getDenominator(), 3);
    }

    @Test
    public void fraction_constructorTest5() {
        Fraction fraction = new Fraction(-2, -3);
        Assert.assertEquals(fraction.getNumerator(), 2);
        Assert.assertEquals(fraction.getDenominator(), 3);
    }

    @Test
    public void fraction_constructorTest6() {
        Fraction fraction = new Fraction(2, -3);
        Assert.assertEquals(fraction.getNumerator(), -2);
        Assert.assertEquals(fraction.getDenominator(), 3);
    }

    @Test
    public void fraction_constructorTest7() {
        Fraction fraction = new Fraction(9, -3);
        Assert.assertEquals(fraction.getNumerator(), -3);
        Assert.assertEquals(fraction.getDenominator(), 1);
    }

    @Test
    public void fraction_constructorTest8() {
        Fraction fraction = new Fraction(-0, 2);
        Assert.assertEquals(fraction.getNumerator(), 0);
        Assert.assertEquals(fraction.getDenominator(), 1);
    }

    @Test
    public void fraction_add1() {
        Fraction f1 = new Fraction(1, 2);
        Fraction f2 = new Fraction(2, 3);
        Fraction result = f1.add(f2);
        Assert.assertEquals(7, result.getNumerator());
        Assert.assertEquals(6, result.getDenominator());
    }

    @Test
    public void fraction_add2() {
        Fraction f1 = new Fraction(1, 2);
        Fraction f2 = new Fraction(3, 2);
        Fraction result = f1.add(f2);
        Assert.assertEquals(2, result.getNumerator());
        Assert.assertEquals(1, result.getDenominator());
    }

    @Test
    public void fraction_add3() {
        Fraction f1 = new Fraction(-1, 2);
        Fraction f2 = new Fraction(1, 2);
        Fraction result = f1.add(f2);
        Assert.assertEquals(0, result.getNumerator());
        Assert.assertEquals(1, result.getDenominator());
    }


    @Test
    public void fraction_add4() {
        Fraction f1 = new Fraction(1, 2);
        Fraction f2 = new Fraction(-3, 2);
        Fraction result = f1.add(f2);
        Assert.assertEquals(-1, result.getNumerator());
        Assert.assertEquals(1, result.getDenominator());
    }


    @Test
    public void fraction_sub1() {
        Fraction f1 = new Fraction(1, 2);
        Fraction f2 = new Fraction(2, 3);
        Fraction result = f1.subtract(f2);
        Assert.assertEquals(-1, result.getNumerator());
        Assert.assertEquals(6, result.getDenominator());
    }

    @Test
    public void fraction_sub2() {
        Fraction f1 = new Fraction(1, 2);
        Fraction f2 = new Fraction(3, 2);
        Fraction result = f1.subtract(f2);
        Assert.assertEquals(-1, result.getNumerator());
        Assert.assertEquals(1, result.getDenominator());
    }

    @Test
    public void fraction_sub3() {
        Fraction f1 = new Fraction(1, 2);
        Fraction f2 = new Fraction(1, 2);
        Fraction result = f1.subtract(f2);
        Assert.assertEquals(0, result.getNumerator());
        Assert.assertEquals(1, result.getDenominator());
    }


    @Test
    public void fraction_sub4() {
        Fraction f1 = new Fraction(1, 2);
        Fraction f2 = new Fraction(-3, 2);
        Fraction result = f1.subtract(f2);
        Assert.assertEquals(2, result.getNumerator());
        Assert.assertEquals(1, result.getDenominator());
    }


    @Test
    public void fraction_mul1() {
        Fraction f1 = new Fraction(1, 2);
        Fraction f2 = new Fraction(1, 2);
        Fraction result = f1.multiplyBy(f2);
        Assert.assertEquals(1, result.getNumerator());
        Assert.assertEquals(4, result.getDenominator());
    }


    @Test
    public void fraction_mul2() {
        Fraction f1 = new Fraction(1, 2);
        Fraction f2 = new Fraction(0, 3);
        Fraction result = f1.multiplyBy(f2);
        Assert.assertEquals(0, result.getNumerator());
        Assert.assertEquals(1, result.getDenominator());
    }

    @Test
    public void fraction_mul3() {
        Fraction f1 = new Fraction(-1, 2);
        Fraction f2 = new Fraction(-1, 3);
        Fraction result = f1.multiplyBy(f2);
        Assert.assertEquals(1, result.getNumerator());
        Assert.assertEquals(6, result.getDenominator());
    }

    @Test
    public void fraction_div1() {
        Fraction f1 = new Fraction(1, 2);
        Fraction f2 = new Fraction(1, 2);
        Fraction result = f1.divideBy(f2);
        Assert.assertEquals(1, result.getNumerator());
        Assert.assertEquals(1, result.getDenominator());
    }


    @Test
    public void fraction_div2() {
        Fraction f1 = new Fraction(1, 2);
        Fraction f2 = new Fraction(-3, 2);
        Fraction result = f1.divideBy(f2);
        Assert.assertEquals(-1, result.getNumerator());
        Assert.assertEquals(3, result.getDenominator());
    }

    @Test(expected = AssertionError.class)
    public void fraction_div3() {
        Fraction f1 = new Fraction(1, 2);
        Fraction f2 = new Fraction(0, 1);
        Fraction result = f1.divideBy(f2);
    }

    @Test
    public void fraction_div4() {
        Fraction f1 = new Fraction(0, 2);
        Fraction f2 = new Fraction(912, 197);
        Fraction result = f1.divideBy(f2);
        Assert.assertEquals(0, result.getNumerator());
        Assert.assertEquals(1, result.getDenominator());
    }

    @Test
    public void fraction_comp1() {
        Fraction f1 = new Fraction(2, 3);
        Fraction f2 = new Fraction(4, 6);
        Assert.assertEquals(0, f1.compareTo(f2));
    }

    @Test
    public void fraction_comp2() {
        Fraction f1 = new Fraction(3, 4);
        Fraction f2 = new Fraction(4, 6);
        Assert.assertEquals(1, f1.compareTo(f2));
    }

    @Test
    public void fraction_comp3() {
        Fraction f1 = new Fraction(1, 3);
        Fraction f2 = new Fraction(4, 7);
        Assert.assertEquals(-1, f1.compareTo(f2));
    }


    /**
     * Hilfsmethode zum Erstellen einer Fraction-Instanz
     */
    public static Fraction f(long numerator) {
        return new Fraction(numerator);
    }

    /**
     * Hilfsmethode zum Erstellen einer Fraction-Instanz
     */
    public static Fraction f(long numerator, long denominator) {
        return new Fraction(numerator, denominator);
    }

    /**
     * Hilfsmethode zum Erstellen eines Fraction-Arrays
     */
    public static Fraction[] fs(Fraction... fs) {
        return fs;
    }

    /**
     * Hilfsmethode zum Erstellen einer Fraction-Matrix
     */
    public static Fraction[][] fss(Fraction[]... fss) {
        return fss;
    }

    /**
     * Hilfsmethode zum Erstellen einer Restriction-Instanz
     */
    public static Restriction r(Fraction[] term, Type type, Fraction rightSide) {
        return new Restriction(term, type, rightSide);
    }

    /**
     * Hilfsmethode zum Erstellen eines Restriction-Arrays
     */
    public static Restriction[] rs(Restriction... rs) {
        return rs;
    }

    /**
     * Hilfsmethode zum Erstellen eines int-Arrays
     */
    public static int[] ints(int... ints) {
        return ints;
    }


    @Test
    public void fraction_multiplyBy() {
        Assert.assertEquals("2/3 * 3/4", f(1, 2), f(2, 3).multiplyBy(f(3, 4)));
    }

    @Test
    public void fraction_compareTo() {
        Assert.assertTrue("2/3 > 3/7", f(2, 3).compareTo(f(3, 7)) > 0);
    }

    @Test
    public void simplex() {
        SimplexSolver s = new SimplexSolver(new LinearProgram(rs(
                r(fs(f(4), f(3)), Type.LE, f(320)),
                r(fs(f(2), f(4)), Type.GE, f(100)),
                r(fs(f(3), f(3)), Type.EQ, f(270))

        ), SolveType.MIN, f(2), f(8)));

        Assert.assertFalse("Beispiel 1 (Ausgangstableau): isValidSolution",
                s.isValidSolution());
        Assert.assertArrayEquals("Beispiel 1 (Ausgangstableau): getTable", fss(
                fs(f(4), f(3), f(1), f(0), f(0), f(0), f(0), f(0), f(320)),
                fs(f(2), f(4), f(0), f(-1), f(0), f(0), f(1), f(0), f(100)),
                fs(f(3), f(3), f(0), f(0), f(0), f(0), f(0), f(1), f(270)),
                fs(f(-2), f(-8), f(0), f(0), f(0), f(0), f(0), f(0), f(0))
        ), s.getTable());
        Assert.assertArrayEquals("Beispiel 1 (Ausgangstableau): getBaseVars", ints(2, 6, 7),
                s.getBaseVars());

        Assert.assertArrayEquals("Beispiel 1: solve", fs(f(50), f(40), f(420)),
                s.solve());

        Assert.assertTrue("Beispiel 1 (Lösung): isValidSolution",
                s.isValidSolution());
        Assert.assertArrayEquals("Beispiel 1 (Lösung): getBaseVars", ints(3, 0, 1), s.getBaseVars());
        Assert.assertArrayEquals("Beispiel 1 (Lösung): getTable", fss(
                fs(f(0), f(0), f(-2), f(1), f(0), f(0), f(-1), f(10, 3), f(160)),
                fs(f(1), f(0), f(1), f(0), f(0), f(0), f(0), f(-1), f(50)),
                fs(f(0), f(1), f(-1), f(0), f(0), f(0), f(0), f(4, 3), f(40)),
                fs(f(0), f(0), f(-6), f(0), f(0), f(0), f(0), f(26, 3), f(420))
        ), s.getTable());
    }

    @Test
    public void simplex_no_solution() {
        SimplexSolver s = new SimplexSolver(new LinearProgram(rs(
                r(fs(f(1), f(0)), Type.EQ, f(10)),
                r(fs(f(1), f(0)), Type.EQ, f(5)),
                r(fs(f(1), f(1)), Type.EQ, f(15))

        ), SolveType.MIN, f(2), f(8)));

        Assert.assertFalse("Beispiel 1 (Ausgangstableau): isValidSolution",
                s.isValidSolution());
        Assert.assertNull("Beispiel 2: solve", s.solve());
    }

    @Test
    public void simplex_simpleMax() {
        SimplexSolver s = new SimplexSolver(new LinearProgram(rs(
                r(fs(f(10)), Type.LE, f(10))

        ), SolveType.MAX, f(10)));

        Assert.assertArrayEquals("Beispiel 1: solve", fs(f(1), f(10)),
                s.solve());

        Assert.assertTrue("Beispiel 1 (Lösung): isValidSolution",
                s.isValidSolution());


    }



    @Test
    public void simplex_solution_max() {
        SimplexSolver s = new SimplexSolver(new LinearProgram(rs(
                r(fs(f(4), f(3)), Type.LE, f(24)),
                r(fs(f(2), f(4)), Type.LE, f(24)),
                r(fs(f(4), f(5)), Type.LE, f(32))

        ), SolveType.MAX, f(250), f(450)));


        Assert.assertArrayEquals("Beispiel 1: solve", fs(f(4, 3), f(16, 3), f(8200, 3)),
                s.solve());
    }


}
