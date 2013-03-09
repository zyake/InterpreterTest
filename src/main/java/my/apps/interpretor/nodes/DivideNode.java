package my.apps.interpretor.nodes;

import java.util.Map;

public class DivideNode extends AbstractOperatorNode {

    @Override
    public boolean requireInversion() {
        return true;
    }

    @Override
    public int evalute(Map<String, Integer> variable) {
        return left.evalute(variable) / right.evalute(variable);
    }
}
