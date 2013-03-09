package my.apps.interpretor.nodes;

import java.util.Map;

public class VariableNode implements Node {

    private String variableKey;

    public VariableNode(String variableKey) {
        this.variableKey = variableKey;
    }

    @Override
    public String toString() {
        return "{ " + variableKey + " }";
    }

    @Override
    public int evalute(Map<String, Integer> variable) {
        return variable.get(variableKey);
    }
}
