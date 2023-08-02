package tree.node.binaryNode;

import expression.Context;
import expression.Counter;
import expression.Expression;
import expression.ExpressionRunner;
import expression.IncompleteContextException;

/**
 * Klasse zur Darstellung eines Knotens mit Aequivalenz-Verknüpfung
 */
public class EquiNode extends BinaryNode {

    /**
     * Konstruktor
     *
     * @param left          linker Kindsknoten
     * @param right         rechter Kindknoten
     */
    public EquiNode(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public boolean evaluateShort(Context c) throws IncompleteContextException {
        return this.left.evaluateShort(c) == this.right.evaluateShort(c);
    }

    @Override
    public boolean evaluateComplete(Context c) throws IncompleteContextException {
        return this.left.evaluateComplete(c) == this.right.evaluateComplete(c);
    }

    @Override
    public boolean evaluateParallel(Context c, int bound) throws IncompleteContextException {
        if (this.childrenCount >= bound) {

            ExpressionRunner expr = new ExpressionRunner(this.right, c, bound);
            Thread t = new Thread(expr);
            Counter.increment();
            t.start();
            try {
                t.join();
            } catch (InterruptedException e) {
                throw new AssertionError(e);
            }
            Boolean exprRes = expr.getResult();
            if (exprRes == null) {
                throw expr.getException();
            }
            boolean leftRes = this.left.evaluateParallel(c, bound);
            return leftRes == exprRes;

        } else {
            return this.evaluateComplete(c);
        }
    }

    @Override
    protected String getOpString() {
        return "<->";
    }
}
