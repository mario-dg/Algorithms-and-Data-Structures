package tree.node.unaryNode;


import expression.Context;
import expression.Expression;
import expression.IncompleteContextException;

/**
 * Klasse zur Darstellung eines Knotens mit Negation
 */
public class NotNode extends UnaryNode {
    /**
     * Konstruktor
     *
     * @param child         Kindsknoten
     */
    public NotNode(Expression child) {
        super(child);
    }

    @Override
    public boolean evaluateShort(Context c) throws IncompleteContextException {
        return !this.child.evaluateShort(c);
    }
    @Override
    public boolean evaluateComplete(Context c) throws IncompleteContextException {
        return !this.child.evaluateComplete(c);
    }

    @Override
    public boolean evaluateParallel(Context c, int bound) throws IncompleteContextException {
        return !this.child.evaluateParallel(c, bound);
    }

    @Override
    public void toString(StringBuilder builder) {
        assert builder != null;

        builder.append("(!");
        this.child.toString(builder);
        builder.append(")");

    }

    @Override
    protected String getOpString() {
        return "!";
    }
}
