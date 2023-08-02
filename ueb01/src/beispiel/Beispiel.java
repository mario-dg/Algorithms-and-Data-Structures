package beispiel;

/**
 * Eine kleine Beispielklasse mit mehr oder weniger sinnigen dafür aber zu
 * Demonstrationszwecken geeigneten Methoden.
 *
 * @author kar
 */
public class Beispiel {

    /**
     * Dividend.
     */
    private final int dividend;

    /**
     * Konstruktor.
     *
     * @param dividend der Dividend
     */
    public Beispiel(int dividend) {
        this.dividend = dividend;
    }

    /**
     * Ganzzahlige Division durch Divisor. Kann durch eine Nulldivison eine
     * LaufzeitException des Typs {@link ArithmeticException} auslösen.
     *
     * @param divisor der Divisor
     * @return Ergebnis
     */
    public int divideByDivisor(int divisor) {
        return dividend / divisor;
    }


    /**
     * Ganzzahlige Division durch Divisor.
     *
     * @pre Der Disvisor darf nicht 0 sein.
     * @param divisor der Divisor
     * @return Ergebnis
     */
    public int divideByNonZeroDivisor(int divisor) {
        assert divisor != 0;

        return dividend / divisor;
    }

    /**
     * Ist der Dividend positiv (größer 0)?
     *
     * @return true, wenn der Dividend positiv ist, sonst false
     */
    public boolean isDividendPositive() {
        return dividend > 0;
    }

    @Override
    public String toString() {
        return "Dividend: " + Integer.toString(dividend);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + dividend;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }

        return (this.getClass() == obj.getClass())
                && (dividend == ((Beispiel) obj).dividend);
    }

}
