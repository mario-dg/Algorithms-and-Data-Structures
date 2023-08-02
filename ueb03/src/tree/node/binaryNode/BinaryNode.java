package tree.node.binaryNode;

import expression.Expression;
import tree.node.Node;

/**
 * Abstrakte Klasse zur Darstekkung eines Knotens mit zwei Kindsknoten
 */
public abstract class BinaryNode extends Node {
    /**
     * linker Kindsknoten
     */
    protected final Expression left;
    /**
     * Rechter Kindsknoten
     */
    protected final Expression right;


    /**
     * Konstruktor
     *
     * @param left          linker Kindsknoten
     * @param right         rechter Kindknoten
     */
    public BinaryNode(Expression left, Expression right) {
        super(left.getChildrenCount() + right.getChildrenCount() + 2);
        this.left = left;
        this.right = right;
    }

    @Override
    public void toGraphviz(StringBuilder builder, String prefix) {
        assert prefix != null;
        assert builder != null;
        builder.append(prefix).append(" [label=\"").append(this.getOpString()).
                append(" [").append(this.getChildrenCount()).append("]\"]\n");
        builder.append(prefix).append(" -> ").append(prefix).append("l [label=\"\"]\n");
        builder.append(prefix).append(" -> ").append(prefix).append("r [label=\"\"]\n");
        this.left.toGraphviz(builder, prefix + "l");
        this.right.toGraphviz(builder, prefix + "r");
    }

    @Override
    public void toString(StringBuilder builder) {
        assert builder != null;
        builder.append("(");
        this.left.toString(builder);
        builder.append(" ").append(this.getOpString()).append(" ");
        this.right.toString(builder);
        builder.append(")");
    }




}
