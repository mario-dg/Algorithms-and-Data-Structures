package api;

import expression.Expression;
import tree.leaf.ConstantLeaf;
import tree.leaf.VariableLeaf;
import tree.node.binaryNode.AndNode;
import tree.node.binaryNode.EquiNode;
import tree.node.binaryNode.ImplNode;
import tree.node.binaryNode.OrNode;
import tree.node.binaryNode.XorNode;
import tree.node.unaryNode.IdNode;
import tree.node.unaryNode.NotNode;

/**
 * API zur Erstellung von Expression-Instanzen.
 *
 * @author kar, mhe, Mario da Graca, Leonhard Brandes
 */
public class ExpressionAPI {

    /**
     * Erzeugt einen neuen konstanten Ausdruck, dessen Wert entweder "true" oder "false" ist.
     *
     * @param value Der Wert der Konstanten
     * @return Ein neuer konstanter Ausdruck.
     */
    public Expression makeConstantExpression(boolean value) {
        return new ConstantLeaf(value);

    }

    /**
     * Erzeugt einen neuen variablen Ausdruck.
     *
     * @param name Der Bezeichner der Variablen.
     * @return Ein neuer variabler Ausdruck.
     * @pre Der Bezeichner darf nicht null sein.
     * @pre Der Bezeichner darf nur Buchstaben enthalten.
     * @pre Der Bezeichner darf nicht leer sein.
     */
    public Expression makeVariableExpression(String name) {
        assert (name != null);
        assert (name.matches("^[a-zA-Z]+$"));

        return new VariableLeaf(name);

    }

    /**
     * Erzeugt einen neuen Ausdruck, der die Identitätsoperation auf einen bestehenden Ausdruck
     * anwendet.
     *
     * @param operand Der Ausdruck, auf den die Operation angewendet wird.
     * @return Ein neuer ID-Ausdruck.
     * @pre operand ist nicht null.
     */
    public Expression makeIdExpression(Expression operand) {
        assert (operand != null);
        return new IdNode(operand);
    }

    /**
     * Erzeugt einen neuen Ausdruck, der einen bestehenden Ausdruck negiert.
     *
     * @param operand Der Ausdruck, der negiert werden soll.
     * @return Ein neuer Negationsausdruck.
     * @pre operand ist nicht null.
     */
    public Expression makeNotExpression(Expression operand) {
        assert (operand != null);
        return new NotNode(operand);
    }

    /**
     * Erzeugt einen neuen Ausdruck mit einer binären Operation, die eine Und-Verknüpfung darstellt.
     *
     * @param left  Der linke Operand.
     * @param right Der rechte Operand.
     * @return Ein neuer Konjunktionsausdruck.
     * @pre left und right sind nicht null.
     */
    public Expression makeAndExpression(Expression left, Expression right) {
        assert (left != null) && (right != null);
        return new AndNode(left, right);
    }

    /**
     * Erzeugt einen neuen Ausdruck mit einer binären Operation, die eine Oder-Verknüpfung
     * darstellt.
     *
     * @param left  Der linke Operand.
     * @param right Der rechte Operand.
     * @return Ein neuer Disjunktionsausdruck.
     * @pre left und right sind nicht null.
     */
    public Expression makeOrExpression(Expression left, Expression right) {
        assert (left != null) && (right != null);
        return new OrNode(left, right);
    }

    /**
     * Erzeugt einen neuen Ausdruck mit einer binären Operation, die eine Exklusiv-Oder-Verknüpfung
     * darstellt.
     *
     * @param left  Der linke Operand.
     * @param right Der rechte Operand.
     * @return Ein neuer Alternativausdruck.
     * @pre left und right sind nicht null.
     */
    public Expression makeXorExpression(Expression left, Expression right) {
        assert (left != null) && (right != null);
        return new XorNode(left, right);
    }

    /**
     * Erzeugt einen neuen Ausdruck mit einer binären Operation, die eine Äquivalenz darstellt.
     *
     * @param left  Der linke Operand.
     * @param right Der rechte Operand.
     * @return Ein neuer Äquivalenzausdruck.
     * @pre left und right sind nicht null.
     */
    public Expression makeEquivalenceExpression(Expression left, Expression right) {
        assert (left != null) && (right != null);
        return new EquiNode(left, right);
    }

    /**
     * Erzeugt einen neuen Ausdruck mit einer binären Operation, die eine Implikation darstellt.
     *
     * @param left  Der linke Operand.
     * @param right Der rechte Operand.
     * @return Ein neuer Implikationsausdruck.
     * @pre left und right sind nicht null.
     */
    public Expression makeConsequenceExpression(Expression left, Expression right) {
        assert (left != null) && (right != null);
        return new ImplNode(left, right);
    }

}
