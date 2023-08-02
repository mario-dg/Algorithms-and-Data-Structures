package db;


import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.HashSet;
import java.util.List;
import java.util.function.Predicate;
import java.util.Collections;

/**
 * Eine Datenbanktabelle hat einen Namen bzw. Bezeichner und eine feste Spaltenanzahl, die ebenso
 * wie die Bezeichner (Datentyp String) der einzelnen Spalten und deren Reihenfolge bei der
 * Erzeugung festgelegt werden. Die Tabelle besteht darüber hinaus noch aus einer flexiblen Anzahl
 * von Zeilen, in denen jeweils genau so viele Werte (Datentyp String) stehen, wie es Spalten gibt.
 * Eine neue Zeile wird immer nach der letzten Zeile an die Tabelle angehängt.
 * <p>
 * Der Bezeichner der Datenbanktabelle und die Bezeichner der Spalten müssen einem vorgegebenen
 * Muster folgen um gültig zu sein. Ein valider Bezeichner besteht stets aus einem Zeichen aus der
 * Menge [a-zA-Z] gefolgt von einer beliebigen Anzahl von Zeichen aus der Menge [a-zA-Z0-9_].
 *
 * @author kar, mhe, Mario da Graca, Leonhard Brandes
 */
public final class DBTable {

    /**
     * Konstante zur Ueberpruefung ob ein Bezeichner gueltig ist
     */
    private static final String REGEX = "[a-zA-Z][a-zA-Z0-9_]*";

    /**
     * Name der Tabelle
     */
    private final String id;

    /**
     * Menge, die alle Spaltenbezeichner enthaelt
     */
    private final LinkedHashSet<String> head;

    /**
     * Menge von Listen, die jeweils eine Zeile darstellen
     */
    private final LinkedHashSet<ArrayList<String>> rows;

    /**
     * Erzeugt eine leere Datenbanktabelle mit dem Bezeichner anId und den Spaltenbezeichnern
     * someColIds. Ein Iterator der Collection someColIds muss die Spaltennamen in der Reihenfolge
     * liefern, in der sie in der Tabelle stehen sollen.
     *
     * @param anId       Bezeichner der Datenbanktabelle, die erzeugt werden soll.
     * @param someColIds Spaltenbezeichner
     * @pre anId != null
     * @pre someColIds != null
     * @pre der Bezeichner anId muss gültig sein.
     * @pre someColIds muss mindestens einen Wert enthalten
     * @pre Alle Werte in someColIds müssen gültige Spaltenbezeichner sein
     * @pre Alle Spaltenbezeichner müssen eindeutig sein
     */
    public DBTable(final String anId, final Collection<String> someColIds) {
        assert anId != null;
        assert someColIds != null;
        assert isValidIdentifier(anId);
        assert !someColIds.isEmpty();
        assert areValidIdentifiers(someColIds);
        assert areOnlyUniqueValues(someColIds);

        this.id = anId;
        this.head = new LinkedHashSet<>(someColIds);
        this.rows = new LinkedHashSet<>();
    }


    /**
     * Liefert den Bezeichner der Datenbanktabelle.
     *
     * @return Bezeichner der Datenbanktabelle.
     */
    public String getId() {
        return this.id;
    }

    /**
     * Prüft, ob die Tabelle eine Spalte mit dem Bezeichner aColId hat.
     * <p>
     * Diese Methode arbeitet in O(f(N)), dabei ist N = Anzahl der Spaltenbezeichner dieser Tabelle
     * und f(N) = 1
     *
     * @param aColId Bezeichner, der geprüft wird
     * @return boolscher Wert, der angibt, ob die Tabelle eine Spalte mit dem Bezeichner aColId hat
     * @pre aColId != null
     * @pre der Bezeichner aColId muss gültig sein
     */
    public boolean hasCol(final String aColId) {
        assert aColId != null;
        assert isValidIdentifier(aColId);
        return this.head.contains(aColId);
    }

    /**
     * Prüft, ob die Zeichenketten in someColIds Spaltenbezeichner dieser Tabelle sind.
     * <p>
     * Diese Methode arbeitet in O(f(N)), dabei ist N = Anzahl der Spaltenbezeichner dieser Tabelle
     * und f(N) = 1   (nur von Laenge m von someColIds abhaenging also O(m)
     *
     * @param someColIds Bezeichner, die geprüft werden
     * @return boolscher Wert, der angibt, ob alle Zeichenketten in someColIds Spaltenbezeichner
     * dieser Tabelle sind
     * @pre someColIds != null
     * @pre someColIds muss mindestens einen Wert enthalten
     * @pre Alle Werte in someColIds müssen gültige Spaltenbezeichner sein
     */
    public boolean hasCols(final Collection<String> someColIds) {
        assert someColIds != null;
        assert !someColIds.isEmpty();
        assert areValidIdentifiers(someColIds);
        return this.head.containsAll(someColIds);
    }

    /**
     * Liefert die Spaltenanzahl der Datenbanktabelle.
     * <p>
     * Diese Methode arbeitet in O(f(N)), dabei ist N = Anzahl der Spaltenbezeichner dieser Tabelle
     * und f(N) = 1
     *
     * @return Spaltenanzahl der Datenbanktabelle.
     */
    public int getColCnt() {
        return this.head.size();
    }

    /**
     * Liefert die Zeilenanzahl der Datenbanktabelle.
     * <p>
     * Diese Methode arbeitet in O(f(N)), dabei ist N = Anzahl der vorhandenen Zeilen in der Tabelle
     * und f(N) = 1
     *
     * @return Zeilenanzahl der Datenbanktabelle.
     */
    public int getRowCnt() {
        return this.rows.size();
    }

    /**
     * Fügt die Werte von row in der angegebenen Reihenfolge als letzte Zeile in die Tabelle ein.
     * Ein Iterator der Collection someColIds muss die Inhalte der Zeile in der Reihenfolge liefern,
     * in der sie in der Tabelle stehen sollen.
     * <p>
     * Diese Methode arbeitet in O(f(N)), dabei ist N = Anzahl der vorhandenen Zeilen in der Tabelle
     * und f(N) = 1
     *
     * @param row Werte, für die letzte Zeile
     * @pre row != null
     * @pre Die Anzahl der Werte in row muss der Spaltenanzahl der Tabelle entsprechen.
     */
    public void appendRow(final Collection<String> row) {
        assert row != null;
        assert row.size() == this.getColCnt();

        this.rows.add(new ArrayList<>(row));
    }

    /**
     * Löscht alle Zeilen der Tabelle.
     *
     * @post die Tabelle enthält keine Zeilen.
     */
    public void removeAllRows() {
        this.rows.clear();
        assert this.getRowCnt() == 0;
    }

    /**
     * Liefert eine Liste der Spaltenbezeichner. Die Reihenfolge entspricht dabei der Reihenfolge
     * der Spalten in der Tabelle.
     * <p>
     * Diese Methode arbeitet in O(f(N)), dabei ist N = Anzahl der Spaltenbezeichner dieser Tabelle
     * und f(N) = N
     *
     * @return Liste der Spaltenbezeichner.
     */
    public List<String> getColIds() {
        return new ArrayList<>(this.head);
    }

    /**
     * liefert den Index zu einem Spaltenbezeichner
     * Laufzeit O(N) mit N = Anzahl Spaltenbezeichner
     *
     * @param aColId Sapltenbezeichner
     * @return Index des Spaltenbezeichners
     */
    private int getColIndex(final String aColId) {
        int cnt = -1;
        boolean brk = false;
        for (String s : this.head) {
            if (!brk) {
                cnt++;
            }
            if (s.equals((aColId))) {
                brk = true;
            }
        }
        return cnt;
    }

    /**
     * Sortiert die Zeilen dieser Tabelle anhand der Werte in der Spalte mit dem Bezeichner aColId
     * in der Sortierreihenfolge sortDir.
     *
     * @param aColId  Bezeichner der Spalte, nach der sortiert werden soll.
     * @param sortDir Reihenfolge, nach der sortiert werden soll.
     * @pre aColId != null
     * @pre sortDir != null
     * @pre der Bezeichner aColId muss gültig sein
     * @pre die Tabelle muss eine Spalte mit dem Bezeichner aColId haben
     */
    public void sort(final String aColId, final SortDirection sortDir) {
        assert aColId != null;
        assert sortDir != null;
        assert isValidIdentifier(aColId); //eigentlich sinnlos, macht hasCol auch
        assert hasCol(aColId);
        Sort comp = new Sort(sortDir, this.getColIndex(aColId));
        /* LinkedHashSet ist nicht sortierbar, daher werden die Werte aus this.rows
           in eine ArrayList uebernommen. Daraufhin wird diese sortiert und die Zeilen
           werden in der sortierten Reihenfolge wieder in this.rows eingefuegt */
        ArrayList<ArrayList<String>> temp = new ArrayList<>(this.rows);
        this.removeAllRows();
        temp.sort(comp);
        for (ArrayList<String> l : temp) {
            this.appendRow(l);
        }
    }


    /**
     * Entfernt alle Zeilen aus dieser Tabelle, bei denen ein Test über dem Wert in der Spalte, die
     * mit aColId bezeichnet ist, erfolgreich ist.
     * <p>
     * Diese Methode arbeitet in O(f(N)), dabei ist N = Anzahl der vorhandenen Zeilen in der Tabelle
     * und f(N) = N
     *
     * @param aColId Bezeichner der Spalte, deren Werte für den Test herangezogen werden sollen.
     * @param p      Ein Predicate-Objekt zum Testen des jeweiligen Spaltenwertes
     * @pre aColId != null
     * @pre p != null
     * @pre der Bezeichner aColId muss gültig sein
     * @pre die Tabelle muss eine Spalte mit dem Bezeichner aColId haben
     */
    public void removeRows(final String aColId, final Predicate<String> p) {
        assert aColId != null;
        assert p != null;
        assert isValidIdentifier(aColId); //eigentlich sinnlos, macht hasCol auch
        assert hasCol(aColId);

        int index = this.getColIndex(aColId);
        HashSet<ArrayList<String>> toDelete = new HashSet<>();
        for (ArrayList<String> row : this.rows) { //O(N)
            if (p.test(row.get(index))) {  //O(1)
                toDelete.add(row);       //O(1)
            }
        }
        this.rows.removeAll(toDelete); //O(N)
    }

    /**
     * Erzeugt eine Tabelle mit dem Bezeichner newTableID, die alle Zeilen enthält, bei denen ein
     * Test über dem Wert in der Spalte, die mit aColId bezeichnet ist, erfolgreich ist.
     * <p>
     * Diese Methode arbeitet in O(f(N)), dabei ist N = Anzahl der vorhandenen Zeilen in der Tabelle
     * und f(N) = N
     *
     * @param aColId     Bezeichner der Spalte, deren Werte für den Vergleich herangezogen werden
     *                   sollen.
     * @param p          Ein Predicate-Objekt zum Testen des jeweiligen Spaltenwertes
     * @param newTableId Bezeichner der erzeugten Tabelle.
     * @return erzeugte Tabelle.
     * @pre aColId != null
     * @pre p != null
     * @pre newTableId != null
     * @pre der Bezeichner aColId muss gültig sein
     * @pre die Tabelle muss eine Spalte mit dem Bezeichner aColId haben
     * @pre der Bezeichner newTableId muss gültig sein
     */
    public DBTable select(final String aColId, final Predicate<String> p, final String newTableId) {
        assert aColId != null;
        assert p != null;
        assert newTableId != null;
        assert isValidIdentifier(aColId);
        assert hasCol(aColId);
        assert isValidIdentifier(newTableId);

        DBTable newTab = new DBTable(newTableId, this.head);
        int index = this.getColIndex(aColId);
        for (ArrayList<String> row : this.rows) { //O(N)
            if (p.test(row.get(index))) {  //O(1)
                newTab.appendRow(row);     //O(1)
            }
        }
        return newTab;
    }

    /**
     * Erzeugt eine Tabelle mit dem Bezeichner newTableID, die alle Spalten enthält, deren
     * Bezeichner in someColIds aufgeführt sind, dabei wird die Reihenfolge der Spalten aus
     * someColIds übernommen. Ein Iterator der Collection someColIds muss demzufolge die
     * Spaltennamen in der Reihenfolge liefern, in der sie in der erzeugten Tabelle stehen sollen.
     * <p>
     * Diese Methode arbeitet in O(f(N)), dabei ist N = Anzahl der vorhandenen Zeilen in der Tabelle
     * und f(N) = N
     *
     * @param someColIds Bezeichner der Spalten, die in die erzeugte Tabelle übernommen werden. Die
     *                   Reihenfolge der Spalten in someColIds entspricht der in der erzeugten
     *                   Tabelle.
     * @param newTableId Bezeichner der Tabelle, die erzeugt wird.
     * @return die erzeugte Tabelle
     * @pre someColIds != null
     * @pre newTableId != null
     * @pre someCoIds muss mindestens einen Spaltenbezeichner enthalten
     * @pre zu allen Einträgen in someColIds gibt es eine entsprechende Spalte in der Tabelle
     * @pre der Bezeichner newTableId muss gültig sein
     */
    public DBTable project(final Collection<String> someColIds, final String newTableId) {
        assert someColIds != null;
        assert newTableId != null;
        assert !someColIds.isEmpty();
        assert hasCols(someColIds);
        assert isValidIdentifier(newTableId);

        DBTable res = new DBTable(newTableId, someColIds);
        ArrayList<String> resRow = new ArrayList<>();
        //Aus jeder Zeile die entsprechenden Eintraege uebernehmen
        for (ArrayList<String> row : this.rows) { //O(N)
            for (String s : someColIds) { //Laufzeit nicht von Anzahl Zeilen abhaengig
                int index = getColIndex(s); //Laufzeit nicht von Anzahl Zeilen abhaengig
                resRow.add(row.get(index)); //O(1)
            }
            res.appendRow(resRow); //O(1)
            resRow.clear(); //Laufzeit nicht von Anzahl Zeilen abhaengig
        }
        return res;
    }


    /**
     * Führt eine join-Operation mit der aktuellen und der übergebenen Tabelle other durch. Hierbei
     * wird eine neue Tabelle mit dem Bezeichner newTableID erzeugt, die alle Spalten beider
     * Tabellen enthält: zunächst die Spalten der aktuellen Tabelle und danach die Spalten der
     * übergebenen Tabelle. In der neuen Tabelle befinden sich alle Zeilen, in denen die Werte an
     * den Positionen der übergebenen Spaltenbezeichner (thisColId für die aktuelle Tabelle und
     * otherColId für die übergebene Tabelle) übereinstimmen. Es bleibt die Reihenfolge der Zeilen
     * aus der aktuellen Tabelle bzw. der Tabelle other erhalten.
     * <p>
     * Die Spaltenbezeichner der neuen Tabelle werden aus den Spaltenbezeichnern der beiden
     * vorhandenen Tabellen erzeugt und zwar nach dem Schema, dass vor jeden vorhandenen Bezeichner
     * der Name der entsprechenden Ursprungstabelle gefolgt von einem Unterstrich geschrieben wird.
     *
     * @param other      die Tabelle, mit der this gejoint werden soll
     * @param newTableId Bezeichner der Tabelle, die erzeugt wird.
     * @param thisColId  Spaltenbezeichner der Spalte deren Werte in this verglichen werden.
     * @param otherColId Spaltenbezeichner der Spalte deren Werte in other verglichen werden.
     * @return die erzeugte Tabelle
     * @pre other != null
     * @pre thisColId != null
     * @pre otherColId != null
     * @pre newTableId != null
     * @pre der Bezeichner newTableId muss gültig sein.
     * @pre die Tabellennamen der beiden Tabellen this und other müssen verschieden sein.
     * @pre der Bezeichner thisColId muss in der Tabelle this vorhanden sein
     * @pre der Bezeichner otherColId muss in der Tabelle other vorhanden sein
     * @post die Bezeichner der neuen Tabellenspalten müssen gültig sein
     * @post die Bezeichner der neuen Tabellenspalten müssen eindeutig sein
     */
    public DBTable equijoin(final DBTable other, final String thisColId, final String otherColId,
                            final String newTableId) {
        assert other != null;
        assert thisColId != null;
        assert otherColId != null;
        assert newTableId != null;
        assert isValidIdentifier(newTableId);
        assert !other.getId().equals(this.id);
        assert hasCol(thisColId);
        assert other.hasCol(otherColId);

        DBTable res = new DBTable(newTableId, this.createEquijoinHead(other));
        int thisColIndex = this.getColIndex(thisColId);
        //ueber 1. Tabelle laufen
        for (ArrayList<String> row : this.rows) {
            String value = row.get(thisColIndex);
            //temp enthaelt alle Zeilen der 2. Tabelle, die gejoint werden sollen
            DBTable temp = other.select(otherColId, new EqualsPredicate(value), "temp");
            //ueber temp laufen
            for (ArrayList<String> otherRow : temp.rows) {
                ArrayList<String> newRow = new ArrayList<>(row); //Eintrage aus dieser Tabelle
                newRow.addAll(otherRow); //Eintraege aus anderer Tabelle
                res.appendRow(newRow);
            }
        }

        assert areValidIdentifiers(res.getColIds());
        assert areOnlyUniqueValues(res.getColIds());

        return res;
    }

    /**
     * Erstellt die neuen Spaltenbezeichner fuer die Methode equijoin.
     * Die Spaltenbezeichner der neuen Tabelle werden aus den Spaltenbezeichnern der beiden
     * vorhandenen Tabellen erzeugt und zwar nach dem Schema, dass vor jeden vorhandenen Bezeichner
     * der Name der entsprechenden Ursprungstabelle gefolgt von einem Unterstrich geschrieben wird.
     *
     * @param other andere Tabelle
     * @return Liste mit den neuen Spaltenbezeichnern
     */
    private ArrayList<String> createEquijoinHead(DBTable other) {
        String otherId = other.getId();

        ArrayList<String> newHead = new ArrayList<>();
        for (String s : this.head) {
            StringBuilder sb = new StringBuilder();
            sb.append(this.id).append("_").append(s);
            newHead.add(sb.toString());
        }
        for (String s : other.getColIds()) {
            StringBuilder sb = new StringBuilder();
            sb.append(otherId).append("_").append(s);
            newHead.add(sb.toString());
        }
        return newHead;
    }

    /**
     * Liefert die Stringrepräsentation der Datenbanktabelle. Die Stringrepräsentation ist wie folgt
     * aufgebaut:
     * <ul>
     * <li>In der ersten Zeile stehen durch Kommata getrennt die Bezeichner der Spalten der
     * Datenbanktabelle.</li>
     * <li>Es folgen die Zeilen der Datenbanktabelle in der Reihenfolge, in der sie in der
     * Datenbanktabelle gespeichert sind. Die einzelnen Werte einer Zeile der Datenbanktabelle
     * werden durch Kommata getrennt hintereinander gereiht.</li>
     * </ul>
     * <p>
     * Zeilenumbrüche (Unix, DOS, MacOs) innerhalb der Felder der Tabelle werden zugunsten der
     * besseren Lesbarkeit hierbei entfernt.
     * <p>
     * Es werden stets Unix-Zeilenumbrüche (\n) verwendet.
     *
     * @return Stringrepräsentation der Datenbanktabelle.
     */
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (String s : this.head) {
            str.append(s).append(",");
        }
        str.deleteCharAt(str.length() - 1);
        str.append("\n");
        for (ArrayList<String> row : this.rows) {
            for (String s : row) {
                StringBuilder rowStr = new StringBuilder(s);
                //Zeilenumbrueche entfernen
                removeChar('\n', rowStr);
                removeChar('\r', rowStr);

                str.append(rowStr).append(",");
            }
            str.deleteCharAt(str.length() - 1); //Am Ende kein Komma
            str.append("\n");
        }

        return str.toString();
    }

    /**
     * entfernt alle VOrkommen von einem Zeichen aus dem uebergebene StringBuilder
     *
     * @param c  Zeichen, das entfernt wird
     * @param sb StringBuilder, aus dem das Zeichen entfernt wird
     */
    private void removeChar(char c, StringBuilder sb) {
        while (sb.lastIndexOf(String.valueOf(c)) >= 0) {
            int index = sb.lastIndexOf(String.valueOf(c));
            sb.deleteCharAt(index);
        }
    }

    /**
     * Liefert die Stringrepräsentation der Datenbanktabelle so wie sie in eine Datei geschrieben
     * wird.
     * <p>
     * Diese Stringrepräsentation ist wie folgt aufgebaut:
     * <ul>
     * <li>In der ersten Zeile steht nur der Bezeichner der Datenbanktabelle.</li>
     * <li>In der zweiten Zeile stehen durch Kommata getrennt die Bezeichner der Spalten der
     * Datenbanktabelle.</li>
     * <li>Es folgen die Zeilen der Datenbanktabelle in der Reihenfolge, in der sie in der
     * Datenbanktabelle gespeichert sind, in einer einzigen Zeile. Die Felder der Zeilen werden
     * durch Kommata voneinander getrennt und hintereinander gereiht.</li>
     * </ul>
     * <p>
     * Da in den Tabellenfeldern alle Zeichen erlaubt sind, werden sowohl Kommata (",") als auch
     * Backslashes ("\") bei dieser Ausgabe jeweils mit einem Backslash ("\") gequotet.
     * <p>
     * Es werden stets Unix-Zeilenumbrüche (\n) verwendet.
     *
     * @return Dateirepräsentation der Datenbanktabelle.
     */
    public String toFile() {
        StringBuilder str = new StringBuilder();
        str.append(this.id).append("\n");
        for (String s : this.head) {
            str.append(s).append(",");
        }
        str.deleteCharAt(str.length() - 1);
        str.append("\n"); //Kopfzeile

        for (ArrayList<String> row : this.rows) {
            for (String s : row) {
                StringBuilder sb = new StringBuilder(s);
                int minIndex = 0;
                while (sb.indexOf("\\", minIndex) >= 0) { //Backslash quoten
                    minIndex = sb.indexOf("\\", minIndex);
                    sb.insert(minIndex, "\\");
                    minIndex += 2; //Durch das eingefuegte '\' verschiebt sich der minIndex
                }
                minIndex = 0;
                while (sb.indexOf(",", minIndex) >= 0) { //Kommas quoten
                    minIndex = sb.indexOf(",", minIndex);
                    sb.insert(minIndex, "\\");
                    minIndex += 2; //Durch das eingefuegte '\' verschiebt sich der minIndex
                }
                str.append(sb).append(",");
            }
        }
        str.deleteCharAt(str.length() - 1); //Am Ende kein Komma
        return str.toString();
    }


    /**
     * Prüft, ob die Zeichenkette str ein gültiger Bezeichner für eine Datenbanktabelle bzw. die
     * Spalte einer Datenbanktabelle ist. Ein valider Bezeichner besteht aus einem Zeichen aus der
     * Menge [a-zA-Z] gefolgt von einer beliebigen Anzahl von Zeichen aus der Menge [a-zA-Z0-9_].
     *
     * @param str Zeichenkette, die geprüft wird.
     * @return boolscher Wert, der angibt, ob die Zeichenkette str ein gültiger Bezeichner ist.
     * @pre str != null
     */
    public static boolean isValidIdentifier(final String str) {
        assert str != null;
        return str.matches(REGEX);
    }

    /**
     * Prüft, ob in dem Array aus Zeichenketten strs nur gültige Bezeichner für eine
     * Datenbanktabelle bzw. die Spalte einer Datenbanktabelle ist. Ein valider Bezeichner besteht
     * aus einem Zeichen aus der Menge [a-zA-Z] gefolgt von einer beliebigen Anzahl von Zeichen aus
     * der Menge [a-zA-Z0-9_].
     *
     * @param strs Bezeichner, die geprüft werden
     * @return boolscher Wert, der angibt, ob das Array strs nur gültige Bezeichner enthält.
     * @pre strs != null
     */
    public static boolean areValidIdentifiers(final Collection<String> strs) {
        assert strs != null;
        for (String s : strs) {
            if (!isValidIdentifier(s)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Prüft, ob alle übergebenen Strings eindeutig sind. Sollte mindestens eine Zeichenkette
     * doppelt vorkommen, so gibt die Methode false zurück.
     *
     * @param strs Bezeichner, die geprüft werden
     * @return true, wenn alle Zeichenketten einmalig sind
     * @pre strs != null
     */
    public static boolean areOnlyUniqueValues(final Collection<String> strs) {
        assert strs != null;
        for (String s : strs) {
            if (Collections.frequency(strs, s) > 1) {
                return false;
            }
        }
        return true;
    }

}
