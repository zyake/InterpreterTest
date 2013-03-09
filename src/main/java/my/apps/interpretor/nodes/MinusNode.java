package my.apps.interpretor.nodes;

import java.util.Map;

public class MinusNode extends AbstractOperatorNode {

    @Override
    public boolean requireInversion() {
        return false;
    }

    @Override
    public int evalute(Map<String, Integer> variable) {
        return left.evalute(variable) - right.evalute(variable);
    }
}
