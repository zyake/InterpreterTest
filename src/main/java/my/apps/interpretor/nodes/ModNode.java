package my.apps.interpretor.nodes;

import java.util.Map;

public class ModNode extends AbstractOperatorNode {

    @Override
    public boolean requireInversion() {
        return true;
    }

    @Override
    public int evalute(Map<String, Integer> variable) {
        int leftValue = left.evalute(variable);
        int rightValue = right.evalute(variable);

        int modValue = leftValue % rightValue;

        return modValue;
    }
}
