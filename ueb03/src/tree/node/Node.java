package tree.node;

import expression.AbstractExpression;

/**
 * Abstrakte Klasse zur Darstellung eines inneren Baumknotens
 */
public abstract class Node extends AbstractExpression {
    /**
     * Konstruktor
     *
     * @param childrenCount Anzahl der Kindsknoten
     */
    public Node(int childrenCount) {
        super(childrenCount);
    }

    /**
     * liefert den Operator als String
     *
     * @return Stringdarstellung des Operators
     */
    protected abstract String getOpString();

}
