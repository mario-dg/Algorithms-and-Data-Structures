package simplex;

import java.util.Arrays; // darf nur in "toMatrix" verwendet werden

/**
 * Ein vollständig gekürzter mathematischer Bruch, der aus einem Zähler und einem Nenner besteht.
 * Fraction-Instanzen sind unveränderlich, d.h. für Änderungen werden stets neue Instanzen angelegt.
 *
 * @author kar, mhe, Mario da Graca, Leonhard Brandes
 */
public class Fraction implements Comparable<Fraction> {

    /**
     * Bruch mit Zähler -1 und Nenner 1
     */
    public static final Fraction MINUS_ONE = new Fraction(-1);

    /**
     * Bruch mit Zähler 0 und Nenner 1
     */
    public static final Fraction ZERO = new Fraction(0);

    /**
     * Bruch mit Zähler 1 und Nenner 1
     */
    public static final Fraction ONE = new Fraction(1);


    /**
     * Der Zähler des Bruchs
     */
    private final long numerator;

    /**
     * Der Nenner des Bruchs
     */
    private final long denominator;


    /**
     * Erstellt einen vollständig gekürzten Bruch aus dem übergebenen Zähler und Nenner. Die interne
     * Repräsentation des Nenners ist nicht-negativ. Die Zahl 0 wird durch den Zähler 0 und den
     * Nenner 1 dargestellt.
     *
     * @param numerator   Zu verwendender Zähler (beliebige ganze Zahl)
     * @param denominator Zu verwendender Nenner (beliebige ganze Zahl, außer 0)
     * @pre denominator != 0
     */
    public Fraction(long numerator, long denominator) {
        assert denominator != 0;
        //Bruch kuerzen
        long gcd = gcd(numerator, denominator);
        numerator = numerator / gcd;
        denominator = denominator / gcd;
        //einheitliche darstellung negativer Brueche
        if (denominator < 0) {
            numerator *= -1;
            denominator *= -1;
        }
        this.numerator = numerator;
        this.denominator = denominator;
    }

    /**
     * Erstellt einen Bruch aus dem übergebenen Zähler und dem Nenner 1.
     *
     * @param numerator Zu verwendender Zähler (beliebige ganze Zahl)
     */
    public Fraction(long numerator) {
        this(numerator, 1);
    }

    /**
     * Berechnet den GgT von a und b mit dem euklidischen Algorithmus
     *
     * @param a Erste Zahl
     * @param b Zweite Zahl
     * @return GgT
     */
    private static long gcd(long a, long b) {
        long gcd;
        while (b != 0) {
            gcd = a % b;
            a = b;
            b = gcd;
        }
        return a;
    }

    /**
     * Berechnet das KgV von a und b
     *
     * @param a Erste Zahl
     * @param b Zweite Zahl
     * @return KgV
     */
    private static long lcm(long a, long b) {
        return a * b / gcd(a, b);
    }


    /**
     * Gibt einen neuen Bruch zurück, der aus der Addition des übergebenen Bruchs entsteht.
     *
     * @param other zu addierender Bruch (Summand)
     * @return neuer Bruch (Summe)
     * @pre other != null
     */
    public Fraction add(Fraction other) {
        assert other != null;
        //Hauptnenner
        long cd = lcm(this.denominator, other.getDenominator());
        long n1 = this.numerator * cd / this.denominator;
        long n2 = other.numerator * cd / other.getDenominator();
        long sum = n1 + n2;
        return new Fraction(sum, cd);


    }

    /**
     * Gibt einen neuen Bruch zurück, der aus der Subtraktion des übergebenen Bruchs entsteht.
     *
     * @param other zu subtrahierender Bruch (Subtrahend)
     * @return neuer Bruch (Differenz)
     * @pre other != null
     */
    public Fraction subtract(Fraction other) {
        assert other != null;
        //Subtraktion durch Addition des negierten Bruchs
        Fraction temp = new Fraction(-other.getNumerator(), other.getDenominator());
        return this.add(temp);
    }

    /**
     * Gibt einen neuen Bruch zurück, der aus der Multiplikation mit dem übergebenen Bruch entsteht.
     *
     * @param other zu multiplizierenden Bruch (Faktor)
     * @return neuer Bruch (Produkt)
     * @pre other != null
     */
    public Fraction multiplyBy(Fraction other) {
        assert other != null;
        return new Fraction(this.numerator * other.getNumerator(),
                this.denominator * other.getDenominator());
    }

    /**
     * Gibt einen neuen Bruch zurück, der aus der Division des übergebenen Bruchs entsteht.
     *
     * @param other Bruch, durch den geteilt wird (Divisor)
     * @return neuer Bruch (Quotient)
     * @pre other != null
     * @pre other.getNumerator != 0
     */
    public Fraction divideBy(Fraction other) {
        assert other != null;
        assert other.getNumerator() != 0;
        return new Fraction(this.numerator * other.getDenominator(),
                this.denominator * other.getNumerator());
    }

    /**
     * @return der Zähler des Bruchs
     */
    public long getNumerator() {
        return this.numerator;
    }

    /**
     * @return der Nenner des Bruchs
     */
    public long getDenominator() {
        return this.denominator;
    }

    /**
     * @return der Wert des Bruchs als Gleitkommazahl (floating-point number)
     */
    public double getAsFPN() {
        return (double) this.numerator / this.denominator;
    }

    /**
     * @return Stringrepräsentation des Bruchs
     */
    @Override
    public String toString() {
        return this.numerator + (this.denominator == 1 ? "" : "/" + this.denominator);
    }

    /**
     * Vergleicht diesen Bruch mit dem übergebenen Bruch. Gibt eine Zahl kleiner bzw. größer als 0
     * zurück, wenn die von diesem Bruch repräsentierte Zahl kleiner bzw. größer als die des
     * übergebenen Bruchs ist. Wenn die repräsentierten Zahlen gleich sind, wird 0 zurückgegeben.
     *
     * @param other Bruch, mit dem dieser Bruch verglichen wird
     * @return Vergleichsergebnis (kleiner, gleich, oder größer 0)
     * @pre other != null
     */
    @Override
    public int compareTo(Fraction other) {
        assert other != null;
        Fraction temp = this.subtract(other);
        long num = temp.getNumerator();
        //return temp.getNumerator();
        if (num == 0) {
            return 0;
        } else if (num < 0) {
            return -1;
        } else {
            return 1;
        }
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Fraction)) {
            return false;
        }
        Fraction other = (Fraction) obj;
        return this.numerator == other.numerator && this.denominator == other.denominator;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (numerator ^ (numerator >>> 32));
        result = prime * result + (int) (denominator ^ (denominator >>> 32));
        return result;
    }

    /**
     * Gibt die Stringrepräsentation einer übergebenen Fraction-Matrix zurück.
     *
     * @param fractions Matrix
     * @return Stringrepräsentation von fractions
     * @pre fractions != null
     */
    public static String toMatrix(Fraction[][] fractions) {
        assert fractions != null;

        StringBuilder sb = new StringBuilder();
        for (Fraction[] row : fractions) {
            sb.append(Arrays.toString(row));
            sb.append('\n');
        }
        return sb.toString();
    }

}
