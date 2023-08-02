package expression;

/**
 * Implementiert die Auswertung des Ausdrucksbaums mit Hilfe von evaluateParallel. Ist nur für eine
 * nebenläufige Implementierung mit Threads vorgesehen.
 * <p>
 * Diese Klasse ist bereits komplett vorgegeben und darf nicht verändert werden.
 *
 * @author kar, mhe
 */
public class ExpressionRunner implements Runnable {

    /** der auszuwertende Ausdruck */
    private final Expression expression;

    /** der Kontext für die Auswertung */
    private final Context context;

    /** die Anzahl der Unterknoten, ab der eine parallele Auswertung stattfindet */
    private final int bound;

    /** das Ergebnis der Auswertung */
    private Boolean result = null;

    /** die zu einem aufgetretenen Fehler passende Exception */
    private IncompleteContextException exception;

    /**
     * Konstruktor.
     *
     * @param expression Der auszuwertender Ausdruck
     * @param context    Der Kontext für die Auswertung, oder null, wenn nicht benötigt
     * @param bound      Die Anzahl der Unterknoten (gemäß getChildrenCount), ab der eine parallele
     * Auswertung stattfindet.
     * @pre expression != null
     * @pre bound &ge; 0
     */
    public ExpressionRunner(Expression expression, Context context, int bound) {
        assert expression != null;
        assert bound >= 0;
        
        this.expression = expression;
        this.context = context;
        this.bound = bound;
    }

    /**
     * Liefert das Ergebnis der Auswertung.
     * <p>
     * Darf erst aufgerufen werden, wenn der umschließende Thread beendet ist.
     *
     * @return Ergebnis der Auswertung oder null, falls ein Fehler aufgetreten ist.
     */
    public Boolean getResult() {
        return result;
    }

    /**
     * Liefert die zu dem aufgetretenen Fehler passende Exception.
     * <p>
     * Darf erst aufgerufen werden, wenn der umschließende Thread beendet ist.
     *
     * @return die passende Exception, wenn ein Fehler aufgetreten ist
     * @pre es muss ein Fehler aufgetreten sein
     */
    public IncompleteContextException getException() {
        assert result == null;

        return exception;
    }

    @Override
    public void run() {
        try {
            result = expression.evaluateParallel(context, bound);
        } catch (IncompleteContextException e) {
            result = null;
            exception = e;
        }
    }
}
