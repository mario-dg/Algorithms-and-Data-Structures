package db;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * In einer Datenbank werden Datenbanktabellen verwaltet, die jeweils durch einen eindeutigen
 * Bezeichner (Datentyp String) identifiziert werden. Auch die Datenbank selbst hat einen Bezeichner
 * (Datentyp String).
 * <p>
 * Ein valider Bezeichner besteht stets aus einem Zeichen aus der Menge [a-zA-Z] gefolgt von einer
 * beliebigen Anzahl von Zeichen aus der Menge [a-zA-Z0-9_].
 *
 * @author kar, mhe, Mario da Graca, Leonhard Brandes
 */
public final class DB {
    /**
     * Name der Datenbank
     */
    private final String anId;
    /**
     * Liste der Tabellen
     */
    private final ArrayList<DBTable> db;

    /**
     * Erzeugt eine leere Datenbank mit dem Bezeichner anId.
     *
     * @param anId Bezeichner der Datenbank, die erzeugt werden soll.
     * @pre anId != null
     * @pre der Bezeichner anId muss gültig sein.
     */
    public DB(final String anId) {
        assert anId != null;
        assert DBTable.isValidIdentifier(anId);

        this.anId = anId;
        this.db = new ArrayList<>();
    }


    /**
     * Fügt die Tabelle tab in die Datenbank ein.
     *
     * @param tab Tabelle, die in die Datenbank eingefügt werden soll.
     * @pre tab != null
     * @pre es darf keine Tabelle mit demselben Bezeichner wie dem von tab in der Datenbank
     * existieren.
     */
    public void addTable(final DBTable tab) {
        assert tab != null;
        assert !tableExists(tab.getId());
        this.db.add(tab);
    }

    /**
     * Entfernt die Tabelle mit dem Bezeichner anId aus der Datenbank.
     *
     * @param anId Bezeichner der Tabelle, die aus der Datenbank entfernt werden soll.
     * @pre anId != null
     * @pre der Bezeichner anId muss gültig sein.
     * @post in der Datenbank befindet sich keine Tabelle mit dem Bezeichner anId.
     */
    public void removeTable(final String anId) {
        assert anId != null;
        assert DBTable.isValidIdentifier(anId);
        this.db.removeIf(tab -> tab.getId().equals(anId));
        assert !tableExists(anId);
    }

    /**
     * Entfernt alle Tabellen aus der Datenbank.
     *
     * @post die Datenbank enthält keine Tabellen.
     */
    public void removeAllTables() {
        this.db.clear();
        assert this.getTableCnt() == 0;
    }

    /**
     * Liefert eine aufsteigend sortierte Liste der Tabellenbezeichner.
     *
     * @return aufsteigend sortierte Liste der Tabellenbezeichner.
     */
    public List<String> getTableIds() {
        ArrayList<String> ids = new ArrayList<>();
        for (DBTable tab : this.db) {
            ids.add(tab.getId());
        }
        Collections.sort(ids);
        return ids;
    }

    /**
     * Gibt an, ob eine Tabelle mit dem Bezeichner anId in der Datenbank existiert.
     *
     * @param anId Bezeichner der Tabelle, deren Existenz geprüft werden soll.
     * @return boolscher Wert, der angibt, ob eine Tabelle mit dem Bezeichner anId in der Datenbank
     * existiert.
     * @pre anId != null
     * @pre der Bezeichner anId muss gültig sein.
     */
    public boolean tableExists(final String anId) {
        assert anId != null;
        assert DBTable.isValidIdentifier(anId);
        for (DBTable tab : this.db) {
            if (tab.getId().equals(anId)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Liefert die Tabelle mit dem Bezeichner anId.
     *
     * @param anId Bezeichner der Tabelle, die geliefert werden soll.
     * @return Tabelle mit dem Bezeichner anId (falls vorhanden, sonst NULL-Referenz).
     * @pre anId != null
     * @pre der Bezeichner anId muss gültig sein.
     */
    public DBTable getTable(final String anId) {
        assert anId != null;
        assert DBTable.isValidIdentifier(anId);
        for (DBTable tab : this.db) {
            if (tab.getId().equals(anId)) {
                return tab;
            }
        }
        return null;
    }

    /**
     * Liefert die Stringrepräsentation der Datenbank. Die Stringrepräsentation ist wie folgt
     * aufgebaut:
     * <ul>
     * <li>In der ersten Zeile steht <code>Datenbankname: </code> gefolgt von dem Bezeichner der
     * Datenbank.</li>
     * <li>Die zweite Zeile ist eine Leerzeile.</li>
     * <li>Es folgen Datenbanktabellen in aufsteigender Reihenfolge ihrer Bezeichner in folgender
     * Form:
     * <ul>
     * <li>Vor jeder Tabelle steht <code>Tabellenname: </code> gefolgt von dem Bezeichner der
     * Datenbanktabelle.</li>
     * <li>Danach folgt eine Leerzeile.</li>
     * <li>Es folgt die Stringrepräsentation der Datenbanktabelle.</li>
     * </ul>
     * </li>
     * <li>Nach jeder Tabelle folgt eine Leerzeile.</li>
     * </ul>
     *
     * @return die Stringrepräsentation der Datenbank.
     */
    @Override
    public String toString() {
        StringBuilder res = new StringBuilder("Datenbankname: " + this.anId + "\n\n");
        ArrayList<String> ids = new ArrayList<>(this.getTableIds());
        for (String id : ids) {
            DBTable temp = this.getTable(id);
            res.append("Tabellenname: ").append(id).append("\n\n").append(temp).append("\n");
        }
        return res.toString();
    }


    /**
     * Liefert die Anzahl der Tabellen in der Datenbank.
     *
     * @return Anzahl der Tabellen in der Datenbank.
     */
    public int getTableCnt() {
        return this.db.size();
    }

    /**
     * Liefert den Bezeichner der Datenbank.
     *
     * @return Bezeichner der Datenbank.
     */
    public String getId() {
        return this.anId;
    }

}
