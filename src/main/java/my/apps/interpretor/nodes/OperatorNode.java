package my.apps.interpretor.nodes;

import my.apps.interpretor.nodes.Node;

public interface OperatorNode extends Node {

    /**
     * 掛け算、割り算のように、より演算の優先順位が高く、
     * 操作の逆転が必要な操作かどうかを取得する。
     * @return
     */
    boolean requireInversion();

    void setLeft(Node left);

    void setRight(Node right);
}
