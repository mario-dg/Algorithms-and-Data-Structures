package expression;

import java.util.Map;
import java.util.HashMap;

/**
 * Der Kontext, in dem die Auswertung eines Ausdrucks erfolgt.
 * <p>
 * Diese Klasse ist bereits komplett vorgegeben und darf nicht verändert werden.
 *
 * @author kar, mhe
 */
public final class Context {

    /** Speicher für die Name-Wert Paare */
    private final Map<String, Boolean> values;

    /**
     * Konstruktor für einen neuen Kontext
     */
    public Context() {
        this.values = new HashMap<String, Boolean>();
    }

    /**
     * Prüft, ob ein Bezeichner im Kontext vorhanden ist.
     *
     * @param id Der zu prüfende Bezeichner.
     * @return True, wenn der Bezeichner vorhanden ist, sonst false.
     * @pre Der Bezeichner darf nicht null sein
     * @pre Der Bezeichner ist gemäß der Methode isValidId gültig
     */
    public boolean has(String id) {
        assert id != null;
        assert isValidId(id);

        return values.containsKey(id);
    }

    /**
     * Gibt den Wert eines Bezeichners zurück. Sollte der Bezeichner nicht vorhanden sein, wird
     * "null" zurückgegeben.
     *
     * @param id der Bezeichner, dessen Wert erfragt wird
     * @return den Wert des Bezeichners oder null
     * @pre Der Bezeichner darf nicht null sein
     * @pre Der Bezeichner ist gemäß isValidId gültig
     */
    public Boolean get(String id) {
        assert id != null;
        assert isValidId(id);

        return values.get(id);
    }

    /**
     * Setzt den Wert eines Bezeichners. Wenn der Bezeichner bereits vorhanden ist, wird der alte
     * Wert überschrieben.
     *
     * @param id  der Bezeichner
     * @param val der Wert des Bezeichners
     * @pre Der Bezeichner darf nicht null sein
     * @pre Der Bezeichner ist gemäß der Methode isValidId gültig
     */
    public void set(String id, boolean val) {
        assert id != null;
        assert isValidId(id);

        values.put(id, val);
    }

    /**
     * Prüft, ob ein Bezeichner gültig ist. Ein Bezeichner darf nur aus Buchstaben (mindestens
     * einer) bestehen.
     * 
     * @pre Der Bezeichner darf nicht null sein
     * 
     * @param id zu prüfender Bezeichner
     * @return true wenn der Bezeichner gültig ist, sonst false
     */
    private static boolean isValidId(String id) {
        assert id != null;

        return id.matches("^[a-zA-Z]+$");
    }

}
