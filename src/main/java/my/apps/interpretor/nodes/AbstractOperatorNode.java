package my.apps.interpretor.nodes;

public abstract class AbstractOperatorNode implements OperatorNode {

    protected Node left;

    protected Node right;

    @Override
    public void setLeft(Node left) {
        this.left = left;
    }

    @Override
    public void setRight(Node right) {
        this.right = right;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "[ left=" + left + ",  right=" + right + " ]";
    }
}
