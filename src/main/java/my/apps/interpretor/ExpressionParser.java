package my.apps.interpretor;

import my.apps.interpretor.nodes.Node;

public interface ExpressionParser {

    Node parse(String text);
}
