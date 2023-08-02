package tree.leaf;

import expression.AbstractExpression;
import expression.Context;
import expression.IncompleteContextException;

/**
 * Klasse zur Darstellung eines Blattes des Baumes
 */
public abstract class Leaf extends AbstractExpression {

    /**
     * Konstruktor
     */
    public Leaf() {
        super(0);
    }


    @Override
    public boolean evaluateComplete(Context c) throws IncompleteContextException {
        //Bei einem Blatt sind alle Auswertungsarten identisch
        return this.evaluateShort(c);
    }

    @Override
    public boolean evaluateParallel(Context c, int bound) throws IncompleteContextException {
        //Bei einem Blatt sind alle Auswertungsarten identisch
        return this.evaluateShort(c);
    }


    @Override
    public void toGraphviz(StringBuilder builder, String prefix) {
        assert prefix != null;
        assert builder != null;

        builder.append(prefix).append(" [label=\"");
        this.toString(builder);
        builder.append("\"]\n");

    }


}
