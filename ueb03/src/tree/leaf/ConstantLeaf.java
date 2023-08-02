package tree.leaf;

import expression.Context;
import expression.IncompleteContextException;

/**
 * Abstrakte Klasse zur Darstellung eines Blattes mit einer Konstante
 */
public class ConstantLeaf extends Leaf {

    /**
     * Wert der Konstanten
     */
    private final boolean value;

    /**
     * Konstruktor
     *
     * @param value Wert der Konstanten
     */
    public ConstantLeaf(boolean value) {
        super();
        this.value = value;
    }

    @Override
    public boolean evaluateShort(Context c) throws IncompleteContextException {
        return value;
    }


    @Override
    public void toString(StringBuilder builder) {
        assert builder != null;
        builder.append(value ? "T" : "F");
    }

}
