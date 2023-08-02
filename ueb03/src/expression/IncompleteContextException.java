package expression;

/**
 * Exception, die ausgelöst werden soll, sobald der Bezeichner einer Variablen nicht im übergebenen
 * Kontext gefunden wurde.
 * <p>
 * Diese Klasse ist bereits komplett vorgegeben und darf nicht verändert werden.
 *
 * @author kar, mhe
 */
public class IncompleteContextException extends Exception {

    /**
     * Aus Konformitätsgründen. Wird nicht benötigt.
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * der Bezeichner der fehlenden Variable
     */
    private String name;

    /**
     * Instantiates a new Incomplete context exception.
     *
     * @param name Der Bezeichner der fehlenden Variable
     */
    public IncompleteContextException(String name) {
        this.name = name;
    }

    /**
     * Gets name.
     *
     * @return Der Bezeichner der fehlenden Variable
     */
    public String getName() {
        return name;
    }

}
