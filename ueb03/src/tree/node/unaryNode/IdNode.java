package tree.node.unaryNode;


import expression.Context;
import expression.Expression;
import expression.IncompleteContextException;

/**
 * Klasse zur Darstellung eines Knotens mit Identität
 */
public class IdNode extends UnaryNode {

    /**
     * Konstruktor
     *
     * @param child         Kindsknoten
     */
    public IdNode(Expression child) {
        super(child);
    }

    @Override
    public boolean evaluateShort(Context c) throws IncompleteContextException {
        return this.child.evaluateShort(c);
    }

    @Override
    public boolean evaluateComplete(Context c) throws IncompleteContextException {
        return this.child.evaluateComplete(c);
    }

    @Override
    public boolean evaluateParallel(Context c, int bound) throws IncompleteContextException {
        return this.child.evaluateParallel(c, bound);
    }

    @Override
    public void toString(StringBuilder builder) {
        assert builder != null;

        builder.append("(");
        this.child.toString(builder);
        builder.append(")");

    }

    @Override
    protected String getOpString() {
        return "()";
    }
}
