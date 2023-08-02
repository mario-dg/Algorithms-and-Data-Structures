package db;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Hilfklasse zum Sortieren von Array-Listen anhand der Eintrage in einer Spalte
 */
public class Sort implements Comparator<ArrayList<String>> {

    /**
     * Sortierrichtung
     */
    private final SortDirection dir;

    /**
     * Index der Spalte, nach der sortiert wird
     */
    private final int pivotIndex;

    /**
     * Erzeugt eine Instanz der Klasse mit den uebergebenen Werten
     *
     * @param dir        Sortierrichtung
     * @param pivotIndex Index der Spalte, nach der sortiert wird
     */
    public Sort(SortDirection dir, int pivotIndex) {
        super(); //TODO macht das Sinn??
        this.dir = dir;
        this.pivotIndex = pivotIndex;
    }


    @Override
    public int compare(ArrayList<String> row1, ArrayList<String> row2) {
        int res = row1.get(this.pivotIndex).compareTo(row2.get(this.pivotIndex));
        return this.dir == SortDirection.ASC ? res : -res;
    }

}
