package expression;

/**
 * Abstrakte Klasse für gemeinsame Funktionalitäten von Expression-Instanzen.
 *
 * @author kar, mhe, Mario da Graca, Leonhard Brandes
 */
public abstract class AbstractExpression implements Expression {

    /**
     * Anzahl der Unterknoten dieses Ausdrucks
     */
    protected int childrenCount;

    /**
     * Konstruktor.
     *
     * @param childrenCount Anzahl der Unterknoten dieses Ausdrucks
     */
    public AbstractExpression(int childrenCount) {
        this.childrenCount = childrenCount;
    }

    @Override
    public int getChildrenCount() {
        return childrenCount;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        
        this.toString(sb);
        
        return sb.toString();
    }

    @Override
    public String toGraphviz() {
        StringBuilder sb = new StringBuilder();
        
        sb.append("digraph G {\n");
        this.toGraphviz(sb, "_");
        sb.append("}\n");
        
        return sb.toString();
    }



}
