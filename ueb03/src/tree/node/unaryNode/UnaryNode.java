package tree.node.unaryNode;

import expression.Expression;
import tree.node.Node;

/**
 * Abstrakte Klasse zur Darstellung eines Knotens mit nur einem Nachfolger
 */
public abstract class UnaryNode extends Node {
    /**
     * Nachfolgerknoten
     */
    protected final Expression child;

    /**
     * Konstruktor
     *
     * @param child         Kindsknoten
     */
    public UnaryNode(Expression child) {
        super(child.getChildrenCount() + 1);
        this.child = child;
    }

    @Override
    public void toGraphviz(StringBuilder builder, String prefix) {
        assert prefix != null;
        assert builder != null;
        builder.append(prefix).append(" [label=\"").append(this.getOpString()).
                append(" [").append(this.getChildrenCount()).append("]\"]\n");
        builder.append(prefix).append(" -> ").append(prefix).append("_ [label=\"\"]\n");

        this.child.toGraphviz(builder, prefix + "_");
    }
}
