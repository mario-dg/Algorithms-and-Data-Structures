package simplex;

import java.util.Arrays;

/**
 * Ein Automat zum Lösen linearer Optimierungsprobleme
 *
 * @author kar, mhe, Mario da Graca, Leonhard Brandes
 */
public class SimplexSolver {
    /**
     * Simplex-Tableau mit den Zeilen in der ersten und den Spalten in der zweiten Dimension
     */
    private Fraction[][] table;

    /**
     * Indices der Basisvariablen
     */
    private int[] baseVars;

    /**
     * Anzahl der Restriktionen
     */
    private int numConstraints;

    /**
     * Anzahl der Unbekannten
     */
    private int numUnknowns;

    /**
     * Typ der Zielfunktion
     */
    private LinearProgram.SolveType solveType;


    /**
     * Erstellt einen Automaten aus dem übergebenen linearen Optimierungsproblem. Der Automat
     * verbleibt im Ausgangstableau, d.h. es werden noch keine Optimierungsschritte durchgeführt.
     *
     * @param lp lineares Problem, das optimiert werden soll
     * @pre lp != null
     */
    public SimplexSolver(LinearProgram lp) {
        assert lp != null;
        this.solveType = lp.getSolveType();
        this.numConstraints = lp.getRestrictions().length;
        this.numUnknowns = lp.getRestrictions()[0].getTerm().length;
        this.table =
                new Fraction[this.numConstraints + 1]
                        [this.numUnknowns + 2 * this.numConstraints + 1];
        this.baseVars = new int[this.numConstraints];
        LinearProgram.Restriction[] temp = lp.getRestrictions().clone();
        for (int row = 0; row < table.length; row++) {
            for (int col = 0; col < table[0].length; col++) {
                if (col < this.numUnknowns && row < this.numConstraints) { //x-Koeffizienten
                    this.table[row][col] = temp[row].getTerm()[col];
                    //rechte Seite der Restriktionen
                } else if (col == this.table[0].length - 1 && row < this.numConstraints) {
                    this.table[row][col] = temp[row].getRightSide();
                    //Schlupfvariablen
                } else if ((col >= this.numUnknowns)
                        && (this.isValidSolutionVariable(col))
                        && row < this.numConstraints) {
                    //Schlupfvariablen nur auf "Diagonale" ungleich null
                    if (row == col - this.numUnknowns) {
                        switch (temp[row].getType()) {
                            case EQ:
                                table[row][col] = Fraction.ZERO;
                                break;
                            case GE:
                                table[row][col] = Fraction.MINUS_ONE;
                                break;
                            case LE:
                                table[row][col] = Fraction.ONE;
                                break;
                            default:
                                break;
                        }
                    } else { //nicht auf "Diagonale" der Schlupfvariablen
                        table[row][col] = Fraction.ZERO;
                    }
                    //künstliche Variablen
                } else if (col >= (this.numUnknowns * 2)
                        && (col < (table[0].length - 1)) && (row < numConstraints)) {
                    // Auf der "Diagonalen"
                    if (row == col - (this.numUnknowns + this.numConstraints)) {
                        // künstliche Variable nur, wenn GE oder EQ
                        if (temp[row].getType() != LinearProgram.Restriction.Type.LE) {
                            this.table[row][col] = Fraction.ONE;
                        } else {
                            this.table[row][col] = Fraction.ZERO;
                        }
                    } else { //nicht auf der "Diagonalen"
                        this.table[row][col] = Fraction.ZERO;
                    }
                    //Koeffizienten der Zielfunktion
                } else if (col < this.numUnknowns && row == this.numConstraints) {

                    table[row][col] = lp.getObjectiveTerm()[col];
                    //Bei Minimierungsproblemen Zielfunktion negieren
                    if (this.solveType == LinearProgram.SolveType.MIN) {
                        table[row][col] = table[row][col].multiplyBy(Fraction.MINUS_ONE);
                    }
                    //letzte Reihe nicht x-Koeffizienten
                } else if (col >= this.numUnknowns && row == this.numConstraints) {
                    table[row][col] = Fraction.ZERO;
                }
            }
        }

        for (int i = 0; i < this.baseVars.length; i++) {
            //Schlupfvariable bei LE- Restriktion
            if (temp[i].getType() == LinearProgram.Restriction.Type.LE) {
                this.baseVars[i] = i + this.numUnknowns;
            } else { //künstliche Variable bei GE- oder EQ-Restriktionen
                this.baseVars[i] = i + this.numUnknowns + this.numConstraints;
            }
        }
    }

    /**
     * Gibt eine Referenz auf das Simplex-Tableau zurück.
     *
     * @return Referenz auf das Simplex-Tableau
     */
    public Fraction[][] getTable() {
        return this.table;
    }

    /**
     * Gibt eine Referenz auf die Indices der Basisvariablen zurück.
     *
     * @return Referenz auf die Indices der Basisvariablen
     */
    public int[] getBaseVars() {
        return this.baseVars;
    }

    /**
     * Gibt zurück, ob das aktuelle Simplex-Tableau eine gültige Lösung repräsentiert.
     *
     * @return true, wenn die aktuelle Lösung gültig ist, ansonsten false
     */
    public boolean isValidSolution() {
        for (int i : this.baseVars) {
            if (!this.isValidSolutionVariable(i)) {
                return false;
            }
        }
        return true;
    }

    /**
     * bestimmt, ob das aktuelle Tableau eine optimale Loesung darstellt
     *
     * @return true, wenn das Tableau eine optimale Loesung darstellt
     */
    private boolean isOptimalSolution() {
        for (int i = 0; this.isValidSolutionVariable(i); i++) {
            if (this.table[this.table.length - 1][i].compareTo(Fraction.ZERO) > 0) {
                return false;
            }
        }
        return this.isValidSolution();
    }

    /**
     * Versucht, das lineare Optimierungsproblem zu lösen. Führt dazu - wenn nötig - wiederholt
     * einen Simplexschritt aus, bis das Tableau eine optimale Lösung anzeigt oder es sich als
     * unlösbar erweist. Bei einer optimalen Lösung werden die Koeffizienten und der Wert der
     * Zielfunktion zurückgegeben, andernfalls die null-Referenz.
     *
     * @return optimale Koeffizienten und Wert der Zielfunktion (in gegebener Reihenfolge, also x1,
     * x2, ..., xn, z) oder null, wenn unlösbar
     */
    public Fraction[] solve() {
        int indexPivotRow = -1;
        int indexPivotCol = -1;
        boolean error = false;
        boolean optimal = false;
        while (!error && !optimal) {
            indexPivotCol = findPivotCol();
            if (indexPivotCol < 0) {
                error = true;
            } else {
                indexPivotRow = findPivotRow(indexPivotCol);
                if (indexPivotRow < 0) {
                    error = true;
                }
            }
            if (!error) {
                this.normalizePivotRow(indexPivotCol, indexPivotRow);
                this.switchBase(indexPivotCol, indexPivotRow);
                this.subtractRows(indexPivotCol, indexPivotRow);
            }
            optimal = this.isOptimalSolution();
        }
        if (optimal) {
            return getResult();
        } else {
            return null;
        }
    }

    /**
     * liefert das Ergebnis der Optimierung zurueck
     *
     * @return Ergebnis der Optimierung in der Reihenfolge x1, x2, ..., xn, z)
     * @pre aktuelles Tableau muss eine gueltige Loesung darstellen
     */
    private Fraction[] getResult() {
        assert (this.isValidSolution());
        Fraction[] result = new Fraction[this.numUnknowns + 1]; //x und Z
        Arrays.fill(result, Fraction.ZERO);
        for (int i = 0; i < this.baseVars.length; i++) {
            //Werte der Schlupfvariabeln fuer die Loesung uninteressant
            if (this.baseVars[i] < this.numUnknowns) {
                result[this.baseVars[i]] = this.table[i][this.table[0].length - 1];
            }
        }
        result[result.length - 1] = this.table[this.table.length - 1][this.table[0].length - 1];
        //Bei Maximierungsproblemen Ergebnis negieren
        if (this.solveType == LinearProgram.SolveType.MAX) {
            result[result.length - 1] = result[result.length - 1].multiplyBy(Fraction.MINUS_ONE);
        }
        return result;
    }


    /**
     * findet die Pivotspalte
     *
     * @return Index der Pivotspalte,<br>
     * falls keine Pivotspalte ausgewaehlt werden kann wird -1 zurueckgegeben
     */
    private int findPivotCol() {
        Fraction[] temp = new Fraction[this.numConstraints + this.numUnknowns];
        Arrays.fill(temp, Fraction.ZERO);

        //gueltige Loesung liegt vor
        if (isValidSolution()) {
            for (int i = 0; i < temp.length; i++) {
                temp[i] = this.table[this.table.length - 1][i];
            }
            //noch keine gueltige Loesung
        } else {
            for (int col = 0; col < temp.length; col++) {
                for (int row = 0; row < this.baseVars.length; row++) {
                    if (!this.isValidSolutionVariable(this.baseVars[row])) {
                        temp[col] = temp[col].add(this.table[row][col]);
                    }
                }
            }

        }
        return findMaxIndex(temp);
    }

    /**
     * findet die Pivotzeile
     *
     * @param indexPivotCol Index der Pivotspalte
     * @return Index der Pivotzeile, <br>
     * falls keine Pivotzeile ausgewaehlt werden kann wird -1 zurueckgegeben
     */
    private int findPivotRow(int indexPivotCol) {
        Fraction[] quotients = new Fraction[this.numConstraints];
        for (int i = 0; i < quotients.length; i++) {
            Fraction temp = this.table[i][indexPivotCol];
            if (temp.compareTo(Fraction.ZERO) <= 0) {
                quotients[i] = Fraction.MINUS_ONE;
            } else {
                quotients[i] = this.table[i][this.table[0].length - 1].divideBy(temp);
            }
        }
        return findMinIndex(quotients);
    }

    /**
     * normalisiert eine Zeile
     *
     * @param indexPivotCol Index der Spalte
     * @param indexPivotRow Index der zu normalisierenden Zeile
     * @pre indexPivCol muss Index einer gueltigen Variable sein
     * @pre indexPivotRow muss &ge 0 und &le this.baseVars.length sein
     */
    private void normalizePivotRow(int indexPivotCol, int indexPivotRow) {
        assert (isValidSolutionVariable(indexPivotCol));
        assert (indexPivotRow >= 0 && indexPivotRow < this.baseVars.length);
        Fraction pivotElem = this.table[indexPivotRow][indexPivotCol];
        for (int i = 0; i < this.table[0].length; i++) {
            this.table[indexPivotRow][i] = this.table[indexPivotRow][i].divideBy(pivotElem);
        }
    }

    /**
     * bestimmt, ob eine Variable zu den normalen oder Schlupfvariablen gehoert
     *
     * @param index Index der Variablen
     * @return true, wenn die Variable zu den normalen oder den Schlupfvariablen gehoert
     */
    private boolean isValidSolutionVariable(int index) {
        return index < (this.numUnknowns + this.numConstraints);
    }

    /**
     * Passt die Basisvariablen an
     *
     * @param indexPivotCol Index der Pivotspalte, neuer Basiswert
     * @param indexPivotRow Index der Pivotzeile, alter Basiswert
     * @pre indexPivCol muss Index einer gueltigen Variable sein
     * @pre indexPivotRow muss &ge 0 und &le this.baseVars.length sein
     */
    private void switchBase(int indexPivotCol, int indexPivotRow) {
        assert (isValidSolutionVariable(indexPivotCol));
        assert (indexPivotRow >= 0 && indexPivotRow < this.baseVars.length);
        this.baseVars[indexPivotRow] = indexPivotCol;
    }

    /**
     * reduziert die anderen Zeilen
     *
     * @param indexPivotCol Index der Pivotspalte
     * @param indexPivotRow Index der Pivotzeile
     * @pre indexPivCol muss Index einer gueltigen Variable sein
     * @pre indexPivotRow muss &ge 0 und &le this.baseVars.length sein
     */
    private void subtractRows(int indexPivotCol, int indexPivotRow) {
        assert (isValidSolutionVariable(indexPivotCol));
        assert (indexPivotRow >= 0 && indexPivotRow < this.baseVars.length);
        for (int row = 0; row < this.table.length; row++) {
            if (row != indexPivotRow) {
                //Pivotelement ist schon eins, braucht nicht extra dadurch teilen
                Fraction a = this.table[row][indexPivotCol];
                for (int col = 0; col < this.table[0].length; col++) {
                    this.table[row][col] =
                            this.table[row][col].subtract(
                                    a.multiplyBy(this.table[indexPivotRow][col]));
                }
            }
        }
    }


    /**
     * liefert den Index des kleinsten Bruchs, der groesser als 0 ist aus einem Array
     *
     * @param fraction Array mit den Brüchen
     * @return Index des kleinsten Bruchs, ist kein Bruch groesser als 0, wird -1 zurueckgegeben
     */
    private int findMinIndex(Fraction[] fraction) {
        Fraction currMin = new Fraction(Long.MAX_VALUE);
        int minIndex = -1;
        for (int i = 0; i < fraction.length; i++) {
            if ((fraction[i].compareTo(Fraction.ZERO) >= 0)
                    && (fraction[i].compareTo(currMin) < 0)) {
                currMin = fraction[i];
                minIndex = i;
            }
        }
        return minIndex;
    }

    /**
     * liefert den Index des groessten Bruchs aus einem Array
     *
     * @param fraction Array mit den Brüchen
     * @return Index des groessten Bruchs
     */
    private int findMaxIndex(Fraction[] fraction) {
        Fraction currMax = Fraction.ZERO;
        int maxIndex = -1;
        for (int i = 0; i < fraction.length; i++) {
            if (fraction[i].compareTo(currMax) > 0) {
                currMax = fraction[i];
                maxIndex = i;
            }
        }
        return maxIndex;
    }


    @Override
    public String toString() {
        String str = "";
        for (Fraction[] temp : this.table) {
            for (Fraction frac : temp) {
                str += frac + " ";
            }
            str += "\n";
        }
        return str;
    }
}