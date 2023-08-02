package tree.leaf;

import expression.Context;
import expression.IncompleteContextException;

/**
 * Abstrakte Klasse zur Darstellung eines Blattes mit einer Variablen
 */
public class VariableLeaf extends Leaf {

    private final String name;

    /**
     * Konstruktor.
     *
     * @param name Name der Variablen
     */
    public VariableLeaf(String name) {
        super();
        this.name = name;
    }

    @Override
    public boolean evaluateShort(Context c) throws IncompleteContextException {
        if (c != null && c.has(this.name)) {
            return c.get(this.name);
        } else {
            throw new IncompleteContextException(this.name);
        }
    }


    @Override
    public void toString(StringBuilder builder) {
        assert builder != null;
        builder.append(this.name);
    }


}
